package com.myz.jvm.jstack;

import com.myz.common.util.PrintUtil;

/**
 * @author yzMa
 * @desc
 * @date 2021/1/4 14:40
 * @email 2641007740@qq.com
 */
public class ThreadStatusInJstack {

    public static void main(String[] args) throws InterruptedException {

        new Thread(new Runnable() {
            @Override
            public void run() {
                PrintUtil.print("start sleep");
                try {
                    Thread.sleep(100000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                PrintUtil.print("end sleep");
            }
        }).start();

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                PrintUtil.print("start wait");
//                try {
//                    this.wait(10000);// 再次验证wait需要与synchronize连用
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                PrintUtil.print("end wait");
//            }
//        }).start();

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                PrintUtil.print("running");
//                int i=0;
//                while(true){
//                    i++;
//                }
//            }
//        }).start();

    }
}
