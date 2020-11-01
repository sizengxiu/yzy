package com.yzy.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @user szx
 * @date 2020/11/1 18:49
 */
public class DateUtil {

    public static final DateTimeFormatter formatterDay= DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static final DateTimeFormatter formatterSecond= DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    /**
     * 格式化时间
     * @param:
     * @return:
     * @auther: szx
     * @date: 2020/11/1 18:51
     */
    public static String formartDateMonth(LocalDateTime date,DateTimeFormatter formatter){
        if(date==null){
            return null;
        }
        return date.format(formatter);
    }

    /**
     * 获取格式化后当前时间字符串
     * @param:
     * @return:
     * @auther: szx
     * @date: 2020/11/1 18:52
     */
    public static String getNowTime(){
        return LocalDateTime.now().format(formatterSecond);
    }
    /**
     * 当日日期字符串
     * @param:
     * @return:
     * @auther: szx
     * @date: 2020/11/1 18:55
     */
    public static String getToday(){
        return LocalDateTime.now().format(formatterDay);
    }
}
