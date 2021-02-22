package com.myz.socket.server.nio;

import com.myz.common.util.PrintUtil;
import com.myz.socket.api.common.SocketConstant;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author yzMa
 * @desc
 * @date 2021/1/11 14:45
 * @email 2641007740@qq.com
 */
public class NioServer {

    private static Map<SocketChannel,String> CHANNEL_MAP = new ConcurrentHashMap<>();
    private static AtomicInteger UID_GENERATOR = new AtomicInteger(0);

    public static void main(String[] args) {

        try{
            ServerSocketChannel ssc = ServerSocketChannel.open();
            ssc.configureBlocking(false);

            ServerSocket ss = ssc.socket();
            ss.bind(new InetSocketAddress(SocketConstant.SERVER_PORT),50);

            Selector selector = Selector.open();
            ssc.register(selector, SelectionKey.OP_ACCEPT); // 将ssc注册到selector上
            PrintUtil.print("NioServer listener on port "+SocketConstant.SERVER_PORT);

            while(true){

                selector.select();// 阻塞

                Set<SelectionKey> selectionKeys = selector.selectedKeys();//有感兴趣的事件发生
                Iterator<SelectionKey> skIterator = selectionKeys.iterator();
                while (skIterator.hasNext()){

                    SelectionKey sk = skIterator.next();
                    if (sk.isAcceptable()){
                        ServerSocketChannel sscAccept = (ServerSocketChannel)sk.channel();
                        SocketChannel socketChannel = sscAccept.accept();
                        PrintUtil.print("socketChannel connected "+socketChannel+" uid="+UID_GENERATOR.get());
                        CHANNEL_MAP.putIfAbsent(socketChannel,UID_GENERATOR.getAndIncrement()+"");

                        socketChannel.configureBlocking(false);
                        socketChannel.register(selector,SelectionKey.OP_READ);// 注册读取事件
                    }else if (sk.isReadable()){
                        SocketChannel socketChannel = (SocketChannel)sk.channel();
                        ByteBuffer readBuffer = ByteBuffer.allocate(1024);

                        try { // 在这里try的原因是因为客户端手动杀进程，会出现io异常，导致退出 selector#select的while 导致服务端进程退出
                            while (true){

                                readBuffer.clear();
                                int readCount = socketChannel.read(readBuffer);
                                if (readCount==0){ // 为啥老是读取到0 没有写数据时得到0 这里要小于等于0
                                    PrintUtil.print("read to buffer 0");
                                    break;
                                }
                                if (readCount < 0){
                                    PrintUtil.print("read to buffer -1 client invoke  shutdown or show down input");
                                    socketChannel.close();
                                    CHANNEL_MAP.remove(socketChannel);
                                    break;
                                }
                                readBuffer.flip();

//                                String content = new String(readBuffer.array()); // 这样相当于是解码了吧！！！但是此时string的数组还是1024长
                                String content = new String(readBuffer.array(),0,readCount);
                                PrintUtil.print("readCount="+readCount+" content="+content);

                                String uid = CHANNEL_MAP.get(socketChannel);

                                //TODO 不应该读完一次立即写，而应该读完所有在写！
                                Set<Map.Entry<SocketChannel, String>> entrySet = CHANNEL_MAP.entrySet();
                                ByteBuffer writeBuffer = ByteBuffer.allocate(1024);// 为啥用1024装不上？？

                                for (Map.Entry<SocketChannel, String> entry : entrySet) {
                                    SocketChannel otherChannel = entry.getKey();
                                    if (otherChannel.equals(socketChannel)){
                                        continue;
                                    }
                                    // ************** 必须 重新构建一个新的 或者 rest或者clear干净，在写。  否则广播完第一个用户 此时limit == position，第二写在广播同样的内容，但是limit == position 相当于什么也没写出去
                                    writeBuffer.clear();
                                    writeBuffer.putInt(Integer.parseInt(uid));
                                    writeBuffer.put(content.getBytes());
                                    writeBuffer.flip();

                                    otherChannel.write(writeBuffer);
                                }
                            }
                        }catch (Exception e){
                            socketChannel.close();
                            CHANNEL_MAP.remove(socketChannel);
                            PrintUtil.print("ex="+e);
                            e.printStackTrace();
                        }
                    }

                    //TODO CHANNEL_MAP 何时移除

                    skIterator.remove(); // 感兴趣事件处理完一定要移除掉
                }

            }

        }catch (IOException exception){
            PrintUtil.print("ioEx="+exception);
            exception.printStackTrace();
        }

    }
}
