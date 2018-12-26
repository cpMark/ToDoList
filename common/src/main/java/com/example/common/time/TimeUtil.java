package com.example.common.time;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * function：时间转换工具类
 * author by cpMark
 * create on 2018/5/15.
 */
public class TimeUtil {

    private ThreadLocal<SimpleDateFormat> dateFormat;
    private ThreadLocal<SimpleDateFormat> albumFormat;
    private ThreadLocal<SimpleDateFormat> movieFormat;
    private ThreadLocal<SimpleDateFormat> periodFormat;

    private TimeUtil(){
        dateFormat = new ThreadLocal<SimpleDateFormat>() {
            @Override
            protected SimpleDateFormat initialValue() {
                return new SimpleDateFormat("yy-MM-dd");
            }
        };
        dateFormat.get().setTimeZone(TimeZone.getTimeZone("GMT+8"));

        albumFormat = new ThreadLocal<SimpleDateFormat>(){
            @Override
            protected SimpleDateFormat initialValue() {
                return new SimpleDateFormat("MM-dd 更新");
            }
        };

        movieFormat = new ThreadLocal<SimpleDateFormat>(){
            @Override
            protected SimpleDateFormat initialValue() {
//                return new SimpleDateFormat("MM-dd 发布");
                return new SimpleDateFormat("MM-dd");
            }
        };

        periodFormat = new ThreadLocal<SimpleDateFormat>(){
            @Override
            protected SimpleDateFormat initialValue() {
                return new SimpleDateFormat("yy-MM-dd HH:mm");
            }
        };

        dateFormat.get().setTimeZone(TimeZone.getTimeZone("GMT+8"));
    }

    private final static class INSTANCE{
        public static final TimeUtil instance = new TimeUtil();
    }

    public static TimeUtil obtain(){
        return INSTANCE.instance;
    }

    public String toTime(long ldate) {
        return toTime(new Date(ldate));
    }

    /**
     * 转换时间。
     *
     * @param time 时间。
     * @return 结果。
     */
    public String toTime(Date time) {
        if (time == null) {
            return "";
        }

        String ftime = "";
        Calendar cal = Calendar.getInstance();
        // 判断是否是同一天

        String curDate = dateFormat.get().format(cal.getTime());
        String paramDate = dateFormat.get().format(time);
        if (curDate.equals(paramDate)) {// 同一天
            int hour = (int) ((cal.getTimeInMillis() - time.getTime()) / 3600000);
            if (hour == 0) {
                if ((cal.getTimeInMillis() - time.getTime()) / 60000 < 1) {
                    ftime = "刚刚";
                }
                else {
                    ftime = Math.max((cal.getTimeInMillis() - time.getTime()) / 60000,1) + "分钟前";
                }
            } else {
                ftime = hour + "小时前";
            }
            return ftime;
        }
        else{
            int days = (int) ((cal.getTimeInMillis() - time.getTime())/(86400000 + 0.5));
            if (days <= 0){
                days = 1;
            }
            if (days < 7){
                return days + "天前";
            }
            if(days<30){
                return days/7+"周前";
            }

            if(days<365){
                int n = days/30;
                if(n == 1)
                    return "上个月";
                if( n != 6)
                    return n+"月前";
                else
                    return "半年前";
            }else {
                return days/365+"年前";
            }


//            Date now = new Date();
//            System.out.println("现在时间："+now.toLocaleString());
//            Calendar calendar = new GregorianCalendar();
//            //将Date设置到Calendar中
//            calendar.setTime(now);
//            calendar.set(Calendar.DATE, calendar.get(Calendar.MONTH) - 1);
//            if(date2Long(calendar.getTime()) < cal.getTimeInMillis()){
//                return "一月前";
//            }else {
//                return "半年前";
//            }

//            else{
//                SimpleDateFormat format = new SimpleDateFormat("MM-dd");
//                format.setTimeZone(TimeZone.getTimeZone("GMT+8"));
//                ftime = format.format(time);
//                return ftime;
//            }
        }
    }


    public String getTimeDifference(String time){
        return toTime2(string2LongMillis(time));
    }


    /**
     * 转换时间。
     *
     * @param time 时间。
     * @return 结果。
     */
    public String toTime2(long time) {

        String ftime = "";
        Calendar cal = Calendar.getInstance();
        // 判断是否是同一天

        long calMills = cal.getTimeInMillis();
        String curDate = dateFormat.get().format(cal.getTime());
        String paramDate = dateFormat.get().format(new Date(time));
        if (curDate.equals(paramDate)) {// 同一天
            int hour = (int) ((calMills - time) / 3600000);
            if (hour == 0) {
                if ((calMills - time) / 60000 < 1) {
                    ftime = "刚刚";
                }
                else {
                    ftime = Math.max((calMills - time) / 60000,1) + "分钟前";
                }
            } else {
                SimpleDateFormat format = new SimpleDateFormat("HH:mm");
                format.setTimeZone(TimeZone.getTimeZone("GMT+8"));
                ftime = format.format(new Date(time));
            }
            return ftime;
        }
        else{
            SimpleDateFormat format = new SimpleDateFormat("MM-dd HH:mm");
            format.setTimeZone(TimeZone.getTimeZone("GMT+8"));
            ftime = format.format(new Date(time));
            return ftime;
        }
    }


    /**
     * 转换时间。
     *
     * @param time 时间。
     * @return 结果。
     */
    public String toTime1(long time) {

        String ftime = "";
        Calendar cal = Calendar.getInstance();
        // 判断是否是同一天

        long calMills = cal.getTimeInMillis();
        String curDate = dateFormat.get().format(cal.getTime());
        String paramDate = dateFormat.get().format(new Date(time));
        if (curDate.equals(paramDate)) {// 同一天
            int hour = (int) ((calMills - time) / 3600000);
            if (hour == 0) {
                if ((calMills - time) / 60000 < 1) {
                    ftime = "刚刚";
                }
                else {
                    ftime = Math.max((calMills - time) / 60000,1) + "分钟前";
                }
            } else {
                ftime = hour + "小时前";
            }
            return ftime;
        }
        else{
            int days = (int) ((calMills - time)/(86400000 + 0.5));
            if (days <= 0){
                days = 1;
            }
            if (days < 7){
                return days + "天前";
            }
            if(days<30){
                return days/7+"周前";
            }

            if(days<365){
                int n = days/30;
                if(n == 1)
                    return "上个月";
                if( n != 6)
                    return n+"月前";
                else
                    return "半年前";
            }else {
                return days/365+"年前";
            }
        }
    }

    public String getTimeDifference(long difference){
        int hour,min,second ;
        if(difference > 0 ){
            second = (int)difference%60;
            min = ((int)difference/60)%60;
            hour = (int)difference/3600;
            return formatTimeUnit(hour)+":"+formatTimeUnit(min)+":"+formatTimeUnit(second);
        }else {
            return "00:00:00";
        }
    }


    public String getTimeDifferenceBegin(long difference){
        int hour,min,second ;
        if(difference > 0 ){
            second = (int)difference%60;
            min = ((int)difference/60)%60;
            return formatTimeUnit(min)+":"+formatTimeUnit(second);
        }else {
            return "00:00";
        }
    }

    public String getBlindTime(long time){
        int hour,min,second ;
        long difference ;
        difference = time;
        if(difference > 0 ){
            second = (int)difference%60;
            min = ((int)difference/60)%60;
            hour = (int)difference/3600;
            return formatTimeUnit(hour)+":"+formatTimeUnit(min)+":"+formatTimeUnit(second);
        }else {
            return "00:00:00";
        }
    }

    public String formatTimeUnit(int unit) {
        if(unit > 0){
            return unit < 10 ? "0" + String.valueOf(unit) : String.valueOf(unit);
        }else {
            return "00";
        }

    }
    public String toChatTime(Date time){
        if (time == null) {
            return "";
        }

        String ftime = "";
        Calendar cal = Calendar.getInstance();

        String curDate = dateFormat.get().format(cal.getTime());
        String paramDate = dateFormat.get().format(time);
        if (curDate.equals(paramDate)) {// 同一天
            SimpleDateFormat format = new SimpleDateFormat("HH:mm");
            format.setTimeZone(TimeZone.getTimeZone("GMT+8"));
            ftime = format.format(time);
            return ftime;
        }
        else{//不同的天
            SimpleDateFormat format = new SimpleDateFormat("MM-dd");
            format.setTimeZone(TimeZone.getTimeZone("GMT+8"));
            ftime = format.format(time);
            return ftime;
        }
    }

    public boolean isToday(long time){
        Calendar cal = Calendar.getInstance();
        String curDate = dateFormat.get().format(cal.getTime());
        String inputDate = dateFormat.get().format(new Date(time));
        return curDate.equals(inputDate);
    }

    public String toHandleTime(Date time){
        if (time == null) {
            return "";
        }
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        format.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        return format.format(time);
    }

    public String toTime(String time) {
        Date date = string2Date(time);
        return toTime(date);
    }

    public String toHourMin(String time) {
        Date date = string2Date(time);
        return toHandleTime(date);
    }
    public String toAlbumTime(String time){
        Date date = string2Date(time);
        String ftime = albumFormat.get().format(date);
        return ftime;
    }

    public String toMovieTime(String time){
        Date date = string2Date(time);
        String ftime = movieFormat.get().format(date);
        return ftime;
    }

    public String toMovieTime(long time){
        Date date = new Date(time);
        String ftime = movieFormat.get().format(date);
        return ftime;
    }



    public String getTimePeriod(int year){
        try {
            periodFormat.get().setTimeZone(TimeZone.getTimeZone("GMT+8"));
            Date start = periodFormat.get().parse(year +"-01-01 00:00");
            Date end = periodFormat.get().parse(year +"-12-31 23:59");
            long startTime = date2Long(start);
            long endTime = date2Long(end);
            return startTime + "-"  + endTime;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 将字符串的时间转化为long  精确到毫秒钟
     * @return
     */
    public long string2LongMillis(String time){
        Date date = string2LastDate(time);
        return date.getTime();
    }
    /**
     * 将字符串的时间转化为long  精确到秒钟
     * @return
     */
    public long string2Long(String time){
        Date date = string2LastDate(time);
        return date2Long(date);
    }

    public long date2Long(Date date){
        long time = date.getTime()/1000 + (date.getTime() % 1000 > 0 ? 1:0);
        return time;
    }

    private Date string2LastDate(String time){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        format.setTimeZone(TimeZone.getTimeZone("GMT+0"));
        Date date ;
        try {
            date = format.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
            date = new Date();
        }

        return date;
    }



    private Date string2Date(String time){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        format.setTimeZone(TimeZone.getTimeZone("GMT"));
        Date date ;
        try {
            date = format.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
            date = new Date();
        }

        return date;
    }

    public String toString(Date date, String pattern) {
        try {
            SimpleDateFormat df = new SimpleDateFormat(pattern);
            df.setTimeZone(TimeZone.getTimeZone("GMT+0"));
            return df.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *  判断两个日期是否同一天
     */
    public boolean isSameDay(String time1, String time2){
        Date date1 = string2Date(time1);
        Date date2 = string2Date(time2);
        String paramDate1 = dateFormat.get().format(date1);
        String paramDate2 = dateFormat.get().format(date2);

        return paramDate1.equals(paramDate2);
    }
    public String getNewsPublishTime(String time){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");//设置日期格式
        String dateNow = df.format(new Date());
        Date date = string2Date(time);
        if(isSameDay(time, dateNow)){
            SimpleDateFormat format = new SimpleDateFormat("HH:mm");
            format.setTimeZone(TimeZone.getTimeZone("GMT+8"));
            String ftime = format.format(date);
            return  ftime ;
        }else {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            format.setTimeZone(TimeZone.getTimeZone("GMT+8"));
            String ftime = format.format(date);
            return  ftime ;
        }
    }

    public String getCreateTime(String time){
        Date date = string2Date(time);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd  创建");
        format.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        String ftime = format.format(date);
//        return  ftime + "  " + getWeekday(date);
        return  ftime ;
    }

    public String getCoinTime(String time){
        Date date = string2Date(time);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
        format.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        String ftime = format.format(date);
//        return  ftime + "  " + getWeekday(date);
        return  ftime ;
    }

    public String getTodayTime(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String time = dateFormat.format(new Date()) + " 00:00:00";
        return time;
    }


    public String getLeftDate(long time){
        Date date = new Date(time);
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
        format.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        return format.format(date);
    }
    public String getRecordTime(String time){
        if (TextUtils.isEmpty(time)){
            return "";
        }

        Date date = new Date();
        date.setTime(Long.valueOf(time) * 1000);
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd  HH:mm");
        format.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        return format.format(date);
    }

    public String getEndTime(String time){
        if (TextUtils.isEmpty(time)){
            return "";
        }

        Date date = string2Date(time);
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
        format.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        return format.format(date);
    }

    public String getWeekday(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int weekDay = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (weekDay == 1){
            return "星期一";
        }
        else if (weekDay == 2){
            return "星期二";
        }
        else if (weekDay == 3){
            return "星期三";
        }
        else if (weekDay == 4){
            return "星期四";
        }
        else if (weekDay == 5){
            return "星期五";
        }
        else if (weekDay == 6){
            return "星期六";
        }
        else {
            return "星期日";
        }
    }

}
