package com.myz.socket.server.udp;

import com.myz.common.util.PrintUtil;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketAddress;

/**
 * @author yzMa
 * @desc
 * @date 2021/1/4 16:43
 * @email 2641007740@qq.com
 */
public class UdpServer {
    public static void main(String[] args) throws IOException {

        DatagramSocket server = new DatagramSocket(9999);// 端口绑定到这里

        while (true){
            byte[] receiveBuf = new byte[1024];
            DatagramPacket receivePacket = new DatagramPacket(receiveBuf, 0, receiveBuf.length);
            server.receive(receivePacket); // 接收不到阻塞
            SocketAddress clientAddress = receivePacket.getSocketAddress();
            PrintUtil.print("服务端接收到客户端"+clientAddress.toString()+"信息："+new String(receiveBuf));

            byte[] data = "hello, i am server".getBytes();
            server.send(new DatagramPacket(data,data.length,clientAddress));
        }

    }
}
