package com.corestal.wpos.bank.utils;

import com.lidroid.xutils.util.LogUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Stack;

/**
 * Created by cgz on 16-5-13.
 * 日期 Date 字符串转换工具集合
 */
public class DateUtils {
    public static final String HHMMSS = "HH:mm:ss";
    public static final String YYYYMMDDHHMM = "yyyy-MM-dd HH:mm";

    public static String getDate(String format) {
        return new SimpleDateFormat(format).format(new Date());
    }

    public static String getDate(Date date, String format) {
        return new SimpleDateFormat(format).format(date);
    }

    /**
     * 与当前时间比较是否为当天
     *
     * @param orderTime
     * @return
     */
    public static boolean isToday(Date orderTime) {
        Calendar calNow = Calendar.getInstance();
        calNow.setTime(new Date());
        Calendar calCum = Calendar.getInstance();
        calCum.setTime(orderTime);
        LogUtils.d("orderTime:" +  orderTime);
        //LogUtils.d("date:" +  calCum.get(Calendar.DAY_OF_YEAR) + ":" + calNow.get(Calendar.DAY_OF_YEAR));
        return calCum.get(Calendar.DAY_OF_YEAR) == calNow.get(Calendar.DAY_OF_YEAR);
    }
}
