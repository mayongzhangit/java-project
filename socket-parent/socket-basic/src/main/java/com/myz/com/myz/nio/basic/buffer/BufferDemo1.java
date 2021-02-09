package com.myz.com.myz.nio.basic.buffer;

import com.myz.common.util.PrintUtil;

import java.nio.IntBuffer;

/**
 * @author yzMa
 * @desc
 * @date 2020/4/6 1:15 AM
 * @email 2641007740@qq.com
 */
public class BufferDemo1 {

    public static void main(String[] args) {

        int size = 10;

        IntBuffer intBuffer = IntBuffer.allocate(size);

        for (int i= 0; i < size; i++){
            intBuffer.put(i);
        }

        intBuffer.flip();

        while (intBuffer.hasRemaining()){
            PrintUtil.print(intBuffer.get());
        }

    }
}
