package com.yrnet.transfer.common.utils;

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

    public static final String CST_TIME_PATTERN = "EEE MMM dd HH:mm:ss zzz yyyy";

    public static final String fullFormat2 = "yyyyMMddHHmmss";

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

    public static String getCurrentDateTimeToStr() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        return sdf.format(getCurrentDateTime());
    }


    public static String format( Date date, String format ) {
        String result = "";
        try {
            if(date != null) {
                DateFormat df = new SimpleDateFormat(format);
                result = df.format(date);
            }
        }
        catch(Exception e) {}
        return result;
    }

    public static String getDateTime( Date date ) {
        return format(date, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 获得当前时间
     *
     * @return
     */
    public static Date getCurrentDateTime() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String result = DateUtil.getDateTime(date);
        try {
            return sdf.parse(result);
        }
        catch(ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;

    }
}
