package com.myz.socket.server.nio;

import com.myz.common.util.PrintUtil;
import com.myz.socket.api.common.SocketConstant;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * @author yzMa
 * @desc
 * @date 2021/1/11 14:45
 * @email 2641007740@qq.com
 */
public class NioServer {


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
                        PrintUtil.print("socketChannel connected "+socketChannel);

                        socketChannel.configureBlocking(false);
                        socketChannel.register(selector,SelectionKey.OP_READ);// 注册读取事件
                    }else if (sk.isReadable()){
                        SocketChannel socketChannel = (SocketChannel)sk.channel();
                        while (true){
                            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                            int readCount = socketChannel.read(byteBuffer);
                            if (readCount<=0){ // 为啥老是读取到0 没有写数据时得到0 这里要小于等于0
                                break;
                            }
                            byteBuffer.clear();
                            byteBuffer.flip();
                            PrintUtil.print("readCount="+readCount+" content="+new String(byteBuffer.array()));

                            socketChannel.write(byteBuffer);
                        }
                    }

                    skIterator.remove(); // 感兴趣事件处理完一定要移除掉
                }
            }

        }catch (Exception exception){
            exception.printStackTrace();
        }

    }
}
