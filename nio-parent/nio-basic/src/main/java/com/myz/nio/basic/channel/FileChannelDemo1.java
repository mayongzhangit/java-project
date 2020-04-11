package com.myz.nio.basic.channel;

import com.myz.common.util.PrintUtil;

import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author yzMa
 * @desc
 * @date 2020/4/6 12:11 PM
 * @email 2641007740@qq.com
 */
public class FileChannelDemo1 {

    // channel是双向的，能反应底层操作操作系统的情况，在linux中，底层操作系统的通道就是双向的
    public static void main(String[] args) throws Exception{

        PrintUtil.print("[ user.dir ] "+System.getProperty("user.dir"));

        FileInputStream fis = new FileInputStream("nio-parent/nio-basic/channel1.txt");
        FileChannel fileChannel = fis.getChannel();// 输入流能获取channel

        ByteBuffer byteBuffer = ByteBuffer.allocate(512);// channel和buffer配合使用
        fileChannel.read(byteBuffer);

        byteBuffer.flip();

        while(byteBuffer.hasRemaining()){
            PrintUtil.print(byteBuffer.get());
        }

        fileChannel.close();

    }
}
