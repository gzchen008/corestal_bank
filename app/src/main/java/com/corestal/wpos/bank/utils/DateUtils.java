package com.corestal.wpos.bank.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Stack;

/**
 * Created by cgz on 16-5-13.
 * 日期 Date 字符串转换工具集合
 */
public class DateUtils {
    public static final String HHMMSS = "HH:mm:ss";

    public static String getDate(String format) {
        return new SimpleDateFormat(format).format(new Date());
    }

    public static String getDate(Date date, String format) {
        return new SimpleDateFormat(format).format(date);
    }

}
