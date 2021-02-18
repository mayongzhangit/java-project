package com.myz.socket.client.nio;

import com.myz.common.util.PrintUtil;
import com.myz.socket.api.common.SocketConstant;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Socket;
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
                            sc.finishConnect();
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
                        while (true){
                            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                            int readCount = sc.read(byteBuffer);
                            if (readCount<=0){
                                break;
                            }
                            byteBuffer.clear();
                            byteBuffer.flip();

                            PrintUtil.print("receiveFrom Server： "+ byteBuffer.toString());
                        }

                    }
                }
            }
        }catch (Exception exception){
            exception.printStackTrace();
        }

    }
}
