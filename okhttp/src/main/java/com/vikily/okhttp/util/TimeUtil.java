package com.vikily.okhttp.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.UUID;

/**
 * Created by Administrator on 2018-12-05.  不建议用单例模式
 */

public class TimeUtil {
    private final static SimpleDateFormat mCurrentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    private static volatile TimeUtil mInstance = null;


    private TimeUtil() {

    }

    public static TimeUtil getInstance() {
        if (mInstance == null) {
            synchronized (TimeUtil.class) {
                if (mInstance == null) {
                    mInstance = new TimeUtil();
                }
            }
        }
        return mInstance;
    }

    /*
     * 将时间戳转换为时间
     */
    public String stampToDate(String s) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return sdf.format(new Date(Long.valueOf(s + "000")));
    }

    public static String getCurrentData() {
        Date d = new Date();
        return mCurrentDate.format(d);
    }

    public static String getCurrentDateTime() {
        Date d = new Date();
        SimpleDateFormat sf = new SimpleDateFormat("MM-dd  hh:mm:ss", Locale.getDefault());
        return sf.format(d);
    }




    /*
     * 将时间戳转换为时间
     */
    public String getCurrentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        return sdf.format(new Date());
    }

    public String getCurrentHourTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        return sdf.format(new Date());
    }

    public long getCurrentTimes() {
        long epoch = System.currentTimeMillis() / 1000;
        DateFormat df = new SimpleDateFormat("HH:mm");
        String dateString = df.format(new Date(epoch * 1000));
        try {
            Date dt = df.parse(dateString);
            VLog.d(dt.getTime() + "当前时间" + dateString);
            return dt.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }

    /*
     * 将时间戳转换为时间
     */
    public String stampToDates(String s) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(new Date(Long.valueOf(s + "000")));
    }



    /*
     * 将时间戳转换为时间
     */
    public String stampToDateThree(String s) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH,mm");
        return sdf.format(new Date(Long.valueOf(s + "000")));
    }

    /*
     * 将时间转换为时间戳
     */
    public long dateToStamp(String s) {
        try {
            DateFormat df = new SimpleDateFormat("HH:mm");
            Date dt = df.parse(s);
            VLog.d(dt.getTime() + "转换时间" + s);
            return dt.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 10位13位时间戳转Date
     *
     * @param timestamp 参数时间戳
     * @return
     */
    public String numberDateFormatToDate(String timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//要转换的时间格式
        Date date = null;
        try {
            if (timestamp.length() == 13) {
                date = sdf.parse(sdf.format(Long.parseLong(timestamp)));
            } else {
                date = sdf.parse(sdf.format(Long.parseLong(timestamp) * 1000));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sdf.format(date);
    }

    public String getTime() {
        Date dt = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmssSSS");
        return sdf.format(dt);
    }

    public String getTimeName() {
        Date dt = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmssSSS");
        return sdf.format(dt) + UUID.randomUUID().toString().replace("-", "");
    }

    public long getUnixStamp() {
        return System.currentTimeMillis() / 1000;
    }

    private String valueOfString(String str, int len) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < len - str.length(); i++) {
            sb.append("0");
        }
        return (sb.length() == 0) ? (str) : (sb.toString() + str);
    }

    public String getDateFormat() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(new Date());
    }

    public Date getDateFormat(String time) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return df.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getTimeString(Calendar calendar) {
        StringBuffer sb = new StringBuffer();
        sb.append(String.valueOf(calendar.get(Calendar.YEAR)))
                .append(this.valueOfString(String.valueOf(calendar.get(Calendar.MONTH) + 1), 2))
                .append(this.valueOfString(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)), 2))
                .append(this.valueOfString(String.valueOf(calendar.get(Calendar.HOUR_OF_DAY)), 2))
                .append(this.valueOfString(String.valueOf(calendar.get(Calendar.MINUTE)), 2))
                .append(this.valueOfString(String.valueOf(calendar.get(Calendar.SECOND)), 2))
                .append(this.valueOfString(String.valueOf(calendar.get(Calendar.MILLISECOND)), 3));
        return sb.toString();
    }

    public String getTimeString(String time) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(this.getDateFormat(time));
        return this.getTimeString(calendar);
    }

    public String getTimeFileName() {
        return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
    }

    public String getTimeString() {
        Calendar calendar = new GregorianCalendar();
        return this.getTimeString(calendar);
    }

}
