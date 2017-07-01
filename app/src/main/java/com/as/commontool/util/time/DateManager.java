package com.as.commontool.util.time;

import android.content.Context;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class DateManager {
    public static final String TAG = "DateManager";
    //一般格式
    public static final String DEFAULT_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    private static DateManager mInstance;
    private Context context;

    private DateManager(Context context) {
        this.context = context;
    }

    public static DateManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new DateManager(context.getApplicationContext());
        }
        return mInstance;
    }

    /**
     * 使用预设格式提取字符串日期
     * @param strDate 日期字符串
     * @return
     */
    public  Date parse(String strDate) {
        try {
            return Date2String(strDate,DEFAULT_TIME_PATTERN);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 使用用户格式提取字符串日期
     * @param date 日期字符串
     * @param pattern 日期格式
     * @return
     */
    public  Date Date2String(String date, String pattern) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        try {
            return sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }


    public  String Date2String(Date date, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        String result = sdf.format(date);
        return result;
    }

    /**
     * 两个时间比较
     * @param date
     * @return
     */
    public  int compareDateWithNow(Date date1){
        Date date2 = new Date();
        int rnum =date1.compareTo(date2);
        return rnum;
    }

    /**
     * 获取系统当前时间
     * @return
     */
    public  String getNowTime() {
        SimpleDateFormat df = new SimpleDateFormat(DEFAULT_TIME_PATTERN);
        return df.format(new Date());
    }

    /**
     * 获取系统当前时间
     * @return
     */
    public  String getNowTime(String type) {
        SimpleDateFormat df = new SimpleDateFormat(type);
        return df.format(new Date());
    }
}
