package com.myz.socket.client.udp;

import com.myz.common.util.PrintUtil;

import java.io.IOException;
import java.net.*;

/**
 * @author yzMa
 * @desc
 * @date 2021/1/4 16:43
 * @email 2641007740@qq.com
 */
public class UdpClient {

    public static void main(String[] args) throws IOException {

        DatagramSocket client = new DatagramSocket();

        byte[] sendBuf = "hello, i am client".getBytes();
        DatagramPacket sendPacket = new DatagramPacket(sendBuf, 0, sendBuf.length,InetAddress.getLocalHost(),9999);// 数据包中包含ip和port
        client.send(sendPacket);// 也是阻塞的？

        byte[] receiveBuf = new byte[1024];
        DatagramPacket receivePacket = new DatagramPacket(receiveBuf, 0, receiveBuf.length);// 数据包中包含ip和port
        client.receive(receivePacket);
        PrintUtil.print("客户端接收到信息："+new String(receiveBuf));

    }
}
