package com.myz.socket.server.tcp;

import com.myz.common.util.PrintUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

public class Server {

    private static int port = 12345;

    public static void main(String[] args) throws IOException {
        ServerSocket ss = new ServerSocket(port);
        Set<Socket> sockets = new HashSet<>();
        PrintUtil.print("server socket start on port "+port);

        while (true) {
            Socket socket = ss.accept();
            sockets.add(socket);
            PrintUtil.print("socket="+socket+" size="+sockets.size());

            new Thread(() -> {
                try {
//                    socket.setKeepAlive(true);
                    socket.setReceiveBufferSize(8 * 1024);
                    socket.setSendBufferSize(8 * 1024);
                    InputStream is = socket.getInputStream();
                    OutputStream os = socket.getOutputStream();
                    try {
                        byte[] bytes = new byte[1024];
                        while (is.read(bytes) > -1) {// 不管怎么样，这个线程因为read不到数据一致会阻塞，直到超时，或者链接断开
                            PrintUtil.print(" received from client: " + new String(bytes, "UTF-8").trim());
                            os.write("ok".getBytes("UTF-8"));
                            os.flush();
                            bytes = new byte[1024];
                        }
                        PrintUtil.print("socket="+socket+" close read() return -1");
                    } catch (IOException e) {
                        e.printStackTrace();
                        // 客户端杀进程，Connection rest
                    } finally {
                        if (!socket.isInputShutdown()) {
                            socket.shutdownInput();
                        }
                        if (!socket.isOutputShutdown()) {
                            socket.shutdownOutput();
                        }
                        if (!socket.isClosed()) {
                            socket.close();
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}