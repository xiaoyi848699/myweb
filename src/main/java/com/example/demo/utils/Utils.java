package com.example.demo.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class Utils {
    public static boolean isEmpty(String data){
        if(null == data || "".equals(data.trim())||"null".equals(data.trim())||"NULL".equals(data.trim())){
            return true;
        }
        return false;
    }
    //方法1：length为产生的位数
    public static String getRandomString(int length){
        //定义一个字符串（A-Z，a-z，0-9）即62位；QWERTYUIOPASDFGHJKLZXCVBNM
        String str="zxcvbnmlkjhgfdsaqwertyuiop1234567890";
        //由Random生成随机数
        Random random=new Random();
        StringBuffer sb=new StringBuffer();
        //长度为几就循环几次
        for(int i=0; i<length; ++i){
            //产生0-61的数字
            int number=random.nextInt(36);
            //将产生的数字通过length次承载到sb中
            sb.append(str.charAt(number));
        }
        //将承载的字符转换成字符串
        return sb.toString();
    }
    /**
     * 不同一周的显示时间格式
     * @param time
     * @param timeFormat
     * @return
     */
    public static String getTime(long time,String timeFormat) {
        SimpleDateFormat format = new SimpleDateFormat(timeFormat);
        return format.format(new Date(time));
    }
}
