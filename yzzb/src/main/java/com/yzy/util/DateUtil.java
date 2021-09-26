package com.yzy.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @user szx
 * @date 2021/5/17 22:26
 */
public class DateUtil {

    public static final SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    public static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd ");

    /**
     * 获取指定日期当月的第一天
     * @param:
     * @return:
     * @auther: szx
     * @date: 2021/5/18 20:05
     */
    public static Date getMonthFirstDay(int year,int month){
        // 获取前月的第一天
        Calendar  calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR,year);
        calendar.set(Calendar.MONTH,month-1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);
        return calendar.getTime();
    }
    /**
     * 获取指定日期当月的最后一天
     * @param:
     * @return:
     * @auther: szx
     * @date: 2021/5/18 20:05
     */
    public static Date getMonthLastDay(int year,int month){
        // 获取前月的第一天
        Calendar  calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR,year);
        calendar.set(Calendar.MONTH,month);
        calendar.set(Calendar.DAY_OF_MONTH, 0);
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);
        return calendar.getTime();
    }

    /**
     * 获取指定月份的第一天是周几
     * @param:
     * @return:
     * @auther: szx
     * @date: 2021/6/6 16:47
     */
    public static int getFirstDayWeekIndex(int year,int month){
        Date monthFirstDay = getMonthFirstDay(year, month);
        Calendar  calendar = Calendar.getInstance();
        calendar.setTime(monthFirstDay);
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 获取指定月份的天数
     * @param:
     * @return:
     * @auther: szx
     * @date: 2021/6/6 16:49
     */
    public static int getMonthDays(int year,int month){
        Calendar  calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR,year);
        calendar.set(Calendar.MONTH,month);
        return  calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }



    public static void main(String[] args){
        System.out.println(getMonthLastDay(2021,1));
    }
}
