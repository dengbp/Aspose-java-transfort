package com.yrnet.appweb.common.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * 时间工具类
 *
 * @author dengbp
 */
public class DateUtil {

    public static final String FULL_TIME_PATTERN = "yyyyMMddHHmmss";

    public static final String year_PATTERN = "yyyy";

    public static final String YYYY_MM_DD_PATTERN = "yyyyMMdd";

    public static final String FULL_TIME_SPLIT_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final String FULL_TIME_SPLIT_PATTERN2 = "yyyy.MM.dd HH:mm:ss";

    public static final String CST_TIME_PATTERN = "EEE MMM dd HH:mm:ss zzz yyyy";

    public static String formatFullTime(LocalDateTime localDateTime) {
        return formatFullTime(localDateTime, FULL_TIME_PATTERN);
    }

    public static String formatFullTime(LocalDateTime localDateTime, String pattern) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
        return localDateTime.format(dateTimeFormatter);
    }

    public static String getDateFormat(Date date, String dateFormatType) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormatType, Locale.CHINA);
        return simpleDateFormat.format(date);
    }

    public static String formatCSTTime(String date, String format) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(CST_TIME_PATTERN, Locale.US);
        Date usDate = simpleDateFormat.parse(date);
        return DateUtil.getDateFormat(usDate, format);
    }

    public static String formatInstant(Instant instant, String format) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        return localDateTime.format(DateTimeFormatter.ofPattern(format));
    }

    public static Date addTime(int time){
        Date date = new   Date();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR,time);
        date=calendar.getTime();
        return date;
    }

    public static Date LocalDateTimeToDate(LocalDateTime localDateTime){
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zdt = localDateTime.atZone(zoneId);
        Date date = Date.from(zdt.toInstant());
        return date;
    }

    /**
     * Description todo
     * @param date	date
     * @return java.lang.String HHmmss
     * @Author dengbp
     * @Date 14:49 2019-12-03
     **/
    public static String getHHmmss(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int hour=cal.get(Calendar.HOUR);
        int minute=cal.get(Calendar.MINUTE);
        int second=cal.get(Calendar.SECOND);
        return hour + "" + "" + minute + "" + second;
    }

    public static String current_yyyyMMdd(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(YYYY_MM_DD_PATTERN, Locale.CHINA);
        return simpleDateFormat.format(new Date());
    }

    public static String current_yyyyMMddHHmmss(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(FULL_TIME_PATTERN, Locale.CHINA);
        return simpleDateFormat.format(new Date());
    }

    public static Date stringToDate(String dateStr,String format) throws ParseException {
        DateFormat fmt =new SimpleDateFormat(format);
        Date date = fmt.parse(dateStr);
        return date;
    }

    /**
     * Description 计算两个日期相差年数
     * @param startDate	 startDate
 * @param endDate	 endDate
     * @return int v
     * @Author dengbp
     * @Date 12:54 2019-12-13
     **/
    public static int yearDateDiff(Date startDate,Date endDate){
        Calendar calBegin = Calendar.getInstance();
        Calendar calEnd = Calendar.getInstance();
        calBegin.setTime(startDate);
        calEnd.setTime(endDate);
        return calEnd.get(Calendar.YEAR) - calBegin.get(Calendar.YEAR);
    }



    /**
     * date2比date1多的天数
     * @param date1
     * @param date2
     * @return
     */
    public static int differentDays(Date date1,Date date2)
    {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        int day1= cal1.get(Calendar.DAY_OF_YEAR);
        int day2 = cal2.get(Calendar.DAY_OF_YEAR);

        int year1 = cal1.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);
        if(year1 != year2)   //同一年
        {
            int timeDistance = 0 ;
            for(int i = year1 ; i < year2 ; i ++)
            {
                if(i%4==0 && i%100!=0 || i%400==0)    //闰年
                {
                    timeDistance += 366;
                }
                else    //不是闰年
                {
                    timeDistance += 365;
                }
            }

            return timeDistance + (day2-day1) ;
        }
        else    //不同年
        {
            System.out.println("判断day2 - day1 : " + (day2-day1));
            return day2-day1;
        }
    }



    /**
     * Description 当前时间加N个月后
     * @param inputDate
 * @param number
     * @return java.lang.Long
     * @Author dengbp
     * @Date 10:09 AM 5/14/22
     **/

    public static Long  getAfterMonth(Long inputDate,int number) {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(YYYY_MM_DD_PATTERN);
        Date date = null;
        try{
            date = sdf.parse(inputDate.toString());
        }catch(Exception e){

        }
        c.setTime(date);
        c.add(Calendar.MONTH,number);
        return Long.parseLong(sdf.format(c.getTime()));
    }
}
