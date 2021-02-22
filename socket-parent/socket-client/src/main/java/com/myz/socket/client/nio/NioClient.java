package com.myz.socket.client.nio;

import com.myz.common.util.PrintUtil;
import com.myz.socket.api.common.SocketConstant;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author yzMa
 * @desc
 * @date 2021/2/18 11:59
 * @email 2641007740@qq.com
 */
public class NioClient {

    private static ExecutorService pool = Executors.newFixedThreadPool(1);

    public static void main(String[] args) {

        try {
            SocketChannel socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false);

            socketChannel.connect(new InetSocketAddress("127.0.0.1",SocketConstant.SERVER_PORT));

            Selector selector = Selector.open();
            socketChannel.register(selector, SelectionKey.OP_CONNECT);

            while(true){
                selector.select();// 阻塞

                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> skIterator = selectionKeys.iterator();
                while(skIterator.hasNext()){
                    SelectionKey sk = skIterator.next();
                    if (sk.isConnectable()){
                        SocketChannel sc = (SocketChannel) sk.channel();
                        if(sc.isConnectionPending()){
                            try {
                                sc.finishConnect(); // 服务端如果是关闭的，会抛出java.net.ConnectException: Connection refused: no further information
                            }catch (Exception exception){
                                exception.printStackTrace();
                                // TODO 服务端关闭稍后再试  sleep
                            }


                        }

                        sc.register(selector,SelectionKey.OP_READ);

                        pool.submit(new Callable<Object>() { // 防止阻塞当前循环吗，这里使用线程池
                            @Override
                            public Object call() throws Exception {

                                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                                String lineContent = null;
                                ByteBuffer writeBuffer = ByteBuffer.allocate(1024);
                                PrintUtil.print("请输入：");
                                while((lineContent = br.readLine())!=null){

                                    if ("close".equals(lineContent)){
                                        sc.shutdownOutput();
//                                        sc.close();
                                        return null;
                                    }

                                    writeBuffer.clear();
                                    writeBuffer.put(lineContent.getBytes());
                                    writeBuffer.flip();
                                    sc.write(writeBuffer);
                                }
                                return null;
                            }
                        });

                    }else if (sk.isReadable()){
                        SocketChannel sc = (SocketChannel) sk.channel();
                        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                        while (true){

                            try {
                                byteBuffer.clear();
                                int readCount = sc.read(byteBuffer);
                                if (readCount==0){
                                    break;
                                }
                                if (readCount == -1){
                                    int randomSleep = (int)Math.random()*100;
                                    PrintUtil.print("read -1 server close or shut down output  请稍后重新连接 random sleep"+randomSleep);
                                    Thread.sleep(randomSleep);
                                    break;
                                }
                                byteBuffer.flip();
                                int uid = byteBuffer.getInt();

                                PrintUtil.print("["+uid+"] "+ new String(byteBuffer.array()));
                            }catch (Exception exception){
                                socketChannel.close();
                                // TODO 重新连接
                                PrintUtil.print("client 需要重新连接");
                                exception.printStackTrace();
                            }


                        }

                    }
                }
            }
        }catch (Exception exception){
            exception.printStackTrace();
        }

    }
}
