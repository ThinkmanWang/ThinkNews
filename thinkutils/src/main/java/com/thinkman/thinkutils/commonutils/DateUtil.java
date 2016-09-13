package com.thinkman.thinkutils.commonutils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Administrator on 2016/1/13.
 */
public class DateUtil
{
	//返回当前年月日
    public static String getNowDate()
    {
        Date date = new Date();
        String nowDate = new SimpleDateFormat("yyyy年MM月dd日").format(date);
        return nowDate;
    }

    public static int getDay() {
        return Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
    }

    public static int getYear() {
        return Calendar.getInstance().get(Calendar.YEAR);
    }

    public static int getMonth() {
        return Calendar.getInstance().get(Calendar.MONTH) + 1;
    }

    public static int getCurrentMonthDay() {
        return Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
    }

    //判断闰年
    public static boolean isLeap(int year)
    {
        if (((year % 100 == 0) && year % 400 == 0) || ((year % 100 != 0) && year % 4 == 0))
            return true;
        else
            return false;
    }

    //返回当月天数
    public static int getMonthDays(int year, int month)
    {
        Calendar a = Calendar.getInstance();
        a.set(Calendar.YEAR, year);
        a.set(Calendar.MONTH, month - 1);
        a.set(Calendar.DATE, 1);
        a.roll(Calendar.DATE, -1);
        int maxDate = a.get(Calendar.DATE);
        return maxDate;
    }

    //判断月数第一天为星期几
    public static int  getMonthForWeek(int year, int month, int day){
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, day);
        int i = calendar.get(Calendar.DAY_OF_WEEK);
      //  SimpleDateFormat format = new SimpleDateFormat("E");
      //  return format.format(calendar.getTime());
        return i;
    }

    //将long字符串转换成格式时间输出
    public static String  getTime(long timestamp){
        return getTime(timestamp * 1000,"yyyy-MM-dd HH:mm:ss");
    }

    //将long字符串转换成格式时间输出
    public static String  getTime(long timestamp,String format){
        Date date=new Date(timestamp * 1000);
        SimpleDateFormat formatter=new SimpleDateFormat(format);
        return formatter.format(date);
    }

    //将String字符串转换成时间输出
    public static Long  getTime(String time,String format){
        Date date = null;
        SimpleDateFormat formatter=new SimpleDateFormat(format);
        try {
            date=formatter.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(date == null){
            return Long.valueOf(0);
        }
        return date.getTime() / 1000;
    }


    public static Long getTime(String time){
        return getTime(time, "yyyy-MM-dd HH:mm:ss");
    }

    public static long startOfDay(long timestamp) {
        String szDayStart = String.format("%s 00:00:00", getTime(timestamp, "yyyy-MM-dd"));
        return getTime(szDayStart);
    }

    public static long endOfDay(long timestamp) {
        String szDayStart = String.format("%s 23:59:59", getTime(timestamp, "yyyy-MM-dd"));
        return getTime(szDayStart);
    }

}
