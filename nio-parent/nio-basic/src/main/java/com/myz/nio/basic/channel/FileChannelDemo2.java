package com.myz.nio.basic.channel;

import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author yzMa
 * @desc
 * @date 2020/4/6 1:42 PM
 * @email 2641007740@qq.com
 */
public class FileChannelDemo2 {

    // channel是双向的，能反应底层操作操作系统的情况，在linux中，底层操作系统的通道就是双向的
    public static void main(String[] args) throws Exception {

        FileOutputStream fos = new FileOutputStream("nio-parent/nio-basic/channel2.txt",false);

        FileChannel fileChannel = fos.getChannel();// 输出流也能获取channel

        ByteBuffer byteBuffer = ByteBuffer.allocate(512);
        byteBuffer.put("hello,nihao".getBytes());
        byteBuffer.flip();

        fileChannel.write(byteBuffer);
        fileChannel.close();
    }
}
