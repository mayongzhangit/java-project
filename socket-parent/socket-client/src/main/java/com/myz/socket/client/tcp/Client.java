package com.myz.socket.client.tcp;

import com.myz.common.util.PrintUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Client {

        public static void main(String[] args) throws IOException, InterruptedException {
            Socket socket = new Socket("localhost", 12345);
//            socket.setKeepAlive(true);
            socket.setSendBufferSize(8192);
            socket.setReceiveBufferSize(8192);
            InputStream is = socket.getInputStream();
            OutputStream os = socket.getOutputStream();
            os.write("get test-key".getBytes("UTF-8"));
            os.flush(); // 写完后server立马返回，这里直接sleep 不读取流中的数据

            Thread.sleep(155 * 1000L);
            os.write("get test-key".getBytes("UTF-8"));
            os.flush();

            byte[] bytes = new byte[1024];
//            is.read(bytes);// 读取一次
//            PrintUtil.print(" received from server: " + new String(bytes, "UTF-8").trim());
            while (is.read(bytes) > -1) { // 上面两次写入的流的数据，server立刻返回了，只是未读而已，这里开始读取，由于Server返回的数据少，直接一次读取了
                PrintUtil.print(" received from server: " + new String(bytes, "UTF-8").trim());
                bytes = new byte[1024];
            }
            if (!socket.isOutputShutdown()) {
                socket.shutdownOutput();
            }
            if (!socket.isInputShutdown()) {
                socket.shutdownInput();
            }
            if (!socket.isClosed()) {
                socket.close();
            }
        }
    }