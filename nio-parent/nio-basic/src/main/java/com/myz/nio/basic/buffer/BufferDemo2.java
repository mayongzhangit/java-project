package com.myz.nio.basic.buffer;

import com.myz.common.util.PrintUtil;

import java.nio.ByteBuffer;

/**
 * @author yzMa
 * @desc
 * @date 2020/4/6 1:17 AM
 * @email 2641007740@qq.com
 */
public class BufferDemo2 {

    // 不管按个方法从来没有删除过，都是通过limit来控制能不能读取到
    // 不管按个方法从来没有删除过，都是通过limit来控制能不能读取到
    // 不管按个方法从来没有删除过，都是通过limit来控制能不能读取到

    public static void main(String[] args) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(10);
        PrintUtil.print("[ init ]"+byteBuffer);

        int count = 5;
        for(int i = 0;i < 5; i++){
            byteBuffer.put((byte)i);
        }
        PrintUtil.print("[ put "+count+" times ]"+byteBuffer);

        byteBuffer.flip();
        PrintUtil.print("[ flip ]"+byteBuffer);

        int getCount = 2;
        for(int i=0;i<getCount;i++){
            byteBuffer.get();
        }
        PrintUtil.print("[ get "+getCount+" times ]"+byteBuffer);

        byteBuffer.mark();
        byteBuffer.get();
        PrintUtil.print("[ mark then get ]"+byteBuffer);

        byteBuffer.reset();
        PrintUtil.print("[ reset ]"+byteBuffer);

        byteBuffer.compact();//将position~limit的拷贝到前边
        // [0,1,2,3,4] --> 此时读了getCount=2个，即将后三个2，3，4拷贝到前边[2,3,4,3,4]，此时position在第三个位置
        PrintUtil.print("[ compact "+getCount+" ]"+byteBuffer);
        PrintUtil.print(byteBuffer.get());
        PrintUtil.print(byteBuffer.get());
        PrintUtil.print(byteBuffer.get());

        byteBuffer.clear();// 依然为数据
        PrintUtil.print("[ clear ]"+byteBuffer);
        PrintUtil.print(byteBuffer.get());
        PrintUtil.print(byteBuffer.get());
        PrintUtil.print(byteBuffer.get());
        PrintUtil.print(byteBuffer.get());
        PrintUtil.print(byteBuffer.get());
        PrintUtil.print(byteBuffer.get());

    }
}
