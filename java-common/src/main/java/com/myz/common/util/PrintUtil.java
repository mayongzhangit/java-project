package com.myz.common.util;


import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author yzMa
 * @desc
 * @date 2020/2/8 5:15 PM
 * @email 2641007740@qq.com
 */
public class PrintUtil {

    public static void print(Object obj){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(sdf.format(new Date())+" "+Thread.currentThread().getName()+" "+obj);
    }
}
