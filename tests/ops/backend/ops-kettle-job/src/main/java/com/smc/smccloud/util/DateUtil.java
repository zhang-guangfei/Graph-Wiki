package com.smc.smccloud.util;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;

import java.math.BigDecimal;
import java.nio.file.attribute.FileTime;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateUtil {

    public static Date getNow;
    // private static String YEAR = "yyyy";
    // private static String MONTH = "MM";
    // private static String DAY = "dd";
    private static String HOUR = "HH";
    private static String MINUTE = "mm";
    private static String SECOND = "ss";

    private static String DATE = "yyyy-MM-dd";
    // private static String TIME = "HH:mm:ss";
    private static String DATE_TIME = "yyyy-MM-dd HH:mm:ss";

    private static String DATE_TIME2 = "yyyy-MM-dd HH:mm:ss:ms";

    private static String DATE_TIME3 = "yyyy-MM-dd HH:mm:ss.S";


    private static String DATE_HOUR_MINUTE = "yyyy-MM-dd HH:mm";
    private static String YEAR_MONTH = "yyyy-MM";
    private static String MONTH_DAY = "MM-dd";
    private static String YYYYMMDD = "yyyyMMdd";
    private static String YYYYMM = "yyyyMM";
    private static String YYMM = "yyMM";
    private static String ISO_8601_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ";
    private static String DATE_T_HOUR_MINUTE = "yyyy-MM-dd'T'HH:mm:ss";

    private static TimeZone GTM8 = TimeZone.getTimeZone("GMT+8");

    public static boolean isValidDateTime(String date) {
        try {
            // 定义日期格式化器
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME);
            // 将字符串转换为本地日期对象
            LocalDate localDate = LocalDate.parse(date, formatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    /**
     * 校验两种日期格式，任何一种正确返回成功
     * 2024-06-15 2024-07-25 00:00:00
     *
     * @param date
     * @return
     */
    public static boolean isValidDateTimeOrDate(String date) {
        boolean dateTimeFlag = true;
        boolean dateFlag = true;
        try {
            // 定义日期格式化器
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME);
            // 将字符串转换为本地日期对象
            LocalDate localDate = LocalDate.parse(date, formatter);
        } catch (DateTimeParseException e) {
            dateTimeFlag = false;
        }
        try {
            // 定义日期格式化器
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE);
            // 将字符串转换为本地日期对象
            LocalDate localDate = LocalDate.parse(date, formatter);
        } catch (DateTimeParseException e) {
            dateFlag = false;
        }
        return dateTimeFlag || dateFlag;
    }


    /**
     * 校验两种日期格式，任何一种正确返回成功 增加毫秒
     * 2024-06-15 2024-07-25 00:00:00
     *
     * @param date
     * @return
     */
    public static boolean isValidDateTimeOrDate2(String date) {
        boolean dateTimeFlag = true;
        boolean dateTimeFlag2 = true;
        boolean dateFlag = true;
        try {
            // 定义日期格式化器
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME);
            // 将字符串转换为本地日期对象
            LocalDate localDate = LocalDate.parse(date, formatter);
        } catch (DateTimeParseException e) {
            dateTimeFlag = false;
        }
        try {
            // 定义日期格式化器
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE);
            // 将字符串转换为本地日期对象
            LocalDate localDate = LocalDate.parse(date, formatter);
        } catch (DateTimeParseException e) {
            dateFlag = false;
        }

        try {
            // 定义日期格式化器
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME3);
            // 将字符串转换为本地日期对象
            LocalDate localDate = LocalDate.parse(date, formatter);
        } catch (DateTimeParseException e) {
            dateTimeFlag2 = false;
        }

        return dateTimeFlag || dateFlag || dateTimeFlag2;
    }

    /**
     * 得到现在时间
     *
     * @return
     */
    public static Date getNow() {
        Calendar calendar = Calendar.getInstance(GTM8);
        return calendar.getTime();
    }


    public static String getDateWithyyyymmddhhmmss(Date date) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            return sdf.format(date);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取当前日期
     *
     * @return
     */
    public static Date getCurrentDate() {
        Date now = getNow();
        SimpleDateFormat format = new SimpleDateFormat(DATE);
        String dateString = format.format(now);
        ParsePosition pos = new ParsePosition(0);
        return format.parse(dateString, pos);
    }

    public static String getYYYYMMDDHHMMSS(Date date) {
        if (date == null) {
            return null;
        }
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            return sdf.format(date);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取日期部分,不要小时分钟 yyyy-MM-dd
     *
     * @param date
     * @return
     */
    public static Date getDate(Date date) {
        Calendar cd = Calendar.getInstance(GTM8);
        cd.setTime(date);
        cd.set(Calendar.HOUR_OF_DAY, 0);
        cd.set(Calendar.MINUTE, 0);
        cd.set(Calendar.SECOND, 0);
        cd.set(Calendar.MILLISECOND, 0);
        return cd.getTime();
    }

    /**
     * 获取一天的起始时间 yyyy-MM-dd 00:00:00:000
     *
     * @param date
     * @return
     */
    public static Date getBeginTime(Date date) {
        Calendar cd = Calendar.getInstance(GTM8);
        cd.setTime(date);
        cd.set(Calendar.HOUR_OF_DAY, 0);
        cd.set(Calendar.MINUTE, 0);
        cd.set(Calendar.SECOND, 0);
        cd.set(Calendar.MILLISECOND, 0);
        return cd.getTime();
    }

    /**
     * 获取一天的结束时间 yyyy-MM-dd 23:59:59:999
     *
     * @param date
     * @return
     */
    public static Date getEndTime(Date date) {
        Calendar cd = Calendar.getInstance(GTM8);
        cd.setTime(date);
        cd.set(Calendar.HOUR_OF_DAY, 23);
        cd.set(Calendar.MINUTE, 59);
        cd.set(Calendar.SECOND, 59);
        cd.set(Calendar.MILLISECOND, 999);
        return cd.getTime();
    }

    /**
     * 将长时间格式字符串转换为时间 yyyy-MM-dd HH:mm:ss
     * @param dateTime
     * @return
     */
    public  static  Date dateTime2ToDatetime(Date dateTime){
        Calendar cd = Calendar.getInstance(GTM8);
        cd.setTime(dateTime);
        cd.set(Calendar.MILLISECOND, 0);
        return cd.getTime();

    }
    /**
     * 将长时间格式字符串转换为时间 yyyy-MM-dd HH:mm:ss
     *
     * @param strDateTime
     * @return
     */
    public static Date stringToDateTime(String strDateTime) {
        if(StringUtils.isBlank(strDateTime)) {
            return null;
        }
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_TIME);
        ParsePosition pos = new ParsePosition(0);
        return formatter.parse(strDateTime, pos);
    }

    public static Date stringToDateTimeByMs(String strDateTime) {
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_TIME2);
        ParsePosition pos = new ParsePosition(0);
        return formatter.parse(strDateTime, pos);
    }

    public static String dateToString(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(date);
    }

    /**
     * 将短时间格式字符串转换为时间 yyyy-MM-dd
     *
     * @param strDate
     * @return
     */
    public static Date stringToDate(String strDate) {
        if (StringUtils.isBlank(strDate)) {
            return null;
        }
        SimpleDateFormat formatter = new SimpleDateFormat(DATE);
        ParsePosition pos = new ParsePosition(0);
        return formatter.parse(strDate, pos);
    }

    public  static Date stringToDate(String strDate,String format)
    {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        ParsePosition pos = new ParsePosition(0);
        return formatter.parse(strDate, pos);
    }
    public  static java.sql.Date  toSqlDate(Date date)
    {

        try {
            SimpleDateFormat formatter = new SimpleDateFormat(DATE_TIME2);
            Date sqlDate = formatter.parse(formatter.format(date));
            return new java.sql.Date(sqlDate.getTime());
        }catch (Exception e) {
            new RuntimeException(e.getMessage());
        }
        return null;
    }

    /**
     * 将长时间格式时间转换为字符串 yyyy-MM-dd HH:mm:ss
     *
     * @param dateDate
     * @return
     */
    public static String dateToDateTimeString(Date dateDate) {
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_TIME);
        return formatter.format(dateDate);
    }

    /**
     * 将短时间格式时间转换为字符串 yyyy-MM-dd
     *
     * @param dateDate
     * @return
     */
    public static String dateToDateString(Date dateDate) {
        SimpleDateFormat formatter = new SimpleDateFormat(DATE);
        return formatter.format(dateDate);
    }


    /**
     * 将yyyy-MM-dd 转换为 yyyy年MM月dd日
     */
    public static String dateToDateStringWithYMD(String dateStr) {
        if (StringUtils.isBlank(dateStr)) {
            return null;
        }
        Date date = stringToDate(dateStr);
        int year = getYear(date);
        int month = getMonth(date);
        int day = getDay(date);
        return year+"年"+month+"月"+day+"日";
    }



    /**
     * 获取日期的年份
     */
    public static int getYear(Date date) {
        Calendar cd = Calendar.getInstance(GTM8);
        cd.setTime(date);
        return cd.get(Calendar.YEAR);
    }

    /**
     * 获取日期的月份
     */
    public static int getMonth(Date date) {
        Calendar cd = Calendar.getInstance(GTM8);
        cd.setTime(date);
        return cd.get(Calendar.MONTH) + 1;
    }

    /**
     * 获取日期的天数
     */
    public static int getDay(Date date) {
        Calendar cd = Calendar.getInstance(GTM8);
        cd.setTime(date);
        return cd.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取时间的小时
     */
    public static int getHour(Date date) {
        Calendar cd = Calendar.getInstance(GTM8);
        cd.setTime(date);
        return cd.get(Calendar.HOUR_OF_DAY);
    }

    public static Long diffMs(Date d1,Date d2) {
        String yyyyMMddHHmmssSSS1 = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(d1);
        String yyyyMMddHHmmssSSS2 = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(d2);
        return Long.parseLong(yyyyMMddHHmmssSSS1) - Long.parseLong(yyyyMMddHHmmssSSS2);
    }

    /**
     * 获取时间的分钟
     */
    public static int getMinute(Date date) {
        Calendar cd = Calendar.getInstance(GTM8);
        cd.setTime(date);
        return cd.get(Calendar.MINUTE);
    }

    /**
     * 将时间格式转换为字符串: yyyy-MM-dd HH:mm
     *
     * @param date
     * @return
     */
    public static String getDateHourMinute(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_HOUR_MINUTE);
        return formatter.format(date);
    }

    /**
     * 判断两个日期是否是同一天
     */
    public static boolean isSameDay(Date d1, Date d2)
    {
        if (null == d1 || null == d2) {
            return false;
        }
        return DateUtils.isSameDay(d1, d2);
    }


    /**
     * 将时间格式转换为字符串: yyyy-MM
     *
     * @param date
     * @return
     */
    public static String getYearMonth(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat(YEAR_MONTH);
        return formatter.format(date);
    }

    /**
     * 将时间转换为字符串 MM-dd
     *
     * @param dateDate
     * @return
     */
    public static String getMonthDay(Date dateDate) {
        SimpleDateFormat formatter = new SimpleDateFormat(MONTH_DAY);
        return formatter.format(dateDate);
    }

    /**
     * 获得一个月中的第一天
     *
     * @param date
     * @return
     */
    public static Date getMonthFirstDate(Date date) {
        Calendar cd = Calendar.getInstance(GTM8);
        cd.clear();
        cd.setTime(date);
        cd.set(Calendar.DAY_OF_MONTH, 1);
        cd.set(Calendar.HOUR_OF_DAY, 0);
        cd.set(Calendar.MINUTE, 0);
        cd.set(Calendar.SECOND, 0);
        cd.set(Calendar.MILLISECOND, 0);
        return cd.getTime();
    }

    /**
     * 获得一个月中的最后一天
     *
     * @param date
     * @return
     */
    public static Date getMonthEndDate(Date date) {
        Calendar cd = Calendar.getInstance(GTM8);
        cd.clear();
        cd.setTime(date);
        cd.set(Calendar.DAY_OF_MONTH, cd.getActualMaximum(Calendar.DAY_OF_MONTH));
        cd.set(Calendar.HOUR_OF_DAY, 0);
        cd.set(Calendar.MINUTE, 0);
        cd.set(Calendar.SECOND, 0);
        cd.set(Calendar.MILLISECOND, 0);
        return cd.getTime();
    }

    /***
     * 获得上个月中的第一天
     *
     * @param date
     * @return
     */
    public static Date getLastMonthFirstDate(Date date) {
        Date lastMonth = addMonth(date, -1);
        return getMonthFirstDate(lastMonth);
    }

    /***
     * 获得上个月中的最后一天
     *
     * @param date
     * @return
     */
    public static Date getLastMonthEndDate(Date date) {
        Date lastMonth = addMonth(date, -1);
        return getMonthEndDate(lastMonth);
    }

    /**
     * 获得当周的第一天(周一)
     */
    public static Date getFirstDayOfWeek(Date date) {
        Calendar cd = Calendar.getInstance(GTM8);
        cd.clear();
        cd.setTime(date);
        // Calendar 以 Sunday 为一周的第一天
        if (cd.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            cd.add(Calendar.DAY_OF_MONTH, -6);
        } else {
            cd.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        }
        cd.set(Calendar.HOUR_OF_DAY, 0);
        cd.set(Calendar.MINUTE, 0);
        cd.set(Calendar.SECOND, 0);
        cd.set(Calendar.MILLISECOND, 0);
        return cd.getTime();
    }

    /**
     * 根据日期获取星期几
     *
     * @param date 日期
     * @return 1-星期一; 2-星期二; 3-星期三; 4-星期四; 5-星期五; 6-星期六; 7-星期日
     */
    public static int getWeekDay(Date date) {
        Calendar cd = Calendar.getInstance(GTM8);
        cd.clear();
        cd.setTime(date);
        boolean isFirstSunday = cd.getFirstDayOfWeek() == Calendar.SUNDAY;
        int weekDay = cd.get(Calendar.DAY_OF_WEEK);
        if (isFirstSunday) {
            weekDay--;
            if (weekDay == 0) {
                weekDay = 7;
            }
        }
        return weekDay;
    }

    /**
     * 得到现在时间
     *
     * @return 字符串 yyyyMMdd HHmmss
     */
    public static String getStringToday() {
        Calendar calendar = Calendar.getInstance(GTM8);
        Date currentTime = calendar.getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd HHmmss");
        return formatter.format(currentTime);
    }

    /**
     * 获取当前日期
     */
    public static Date getToday() {
        Calendar cd = Calendar.getInstance(GTM8);
        cd.set(Calendar.HOUR_OF_DAY, 0);
        cd.set(Calendar.MINUTE, 0);
        cd.set(Calendar.SECOND, 0);
        cd.set(Calendar.MILLISECOND, 0);
        return cd.getTime();
    }

    // /**
    // * 根据用户传入的时间表示格式，返回当前时间的格式 如果是yyyyMMdd，注意字母y不能大写。
    // *
    // * @param sformat yyyyMMddhhmmss
    // * @return
    // */
    // public static String getFormatDate(String sformat) {
    // Date currentTime = getNow();
    // SimpleDateFormat formatter = new SimpleDateFormat(sformat);
    // String dateString = formatter.format(currentTime);
    // return dateString;
    // }

    public static String getFormatDate(Date date, String pattern) {
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        return formatter.format(date);
    }

    public static Date getFormatDate(String date, String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.parse(date, new ParsePosition(0));
    }

    /**
     * 清空分钟(mm)后面的时间位
     *
     * @param date 时间 yyyy-MM-dd HH:mm:ss
     * @return yyyy-MM-dd HH:mm:00
     */
    public static Date clearTimeBitAfterMinute(Date date) {
        Calendar cd = Calendar.getInstance(GTM8);
        cd.clear();
        cd.setTime(date);
        cd.set(Calendar.SECOND, 0);
        cd.set(Calendar.MILLISECOND, 0);
        return cd.getTime();
    }

    /**
     * 在输入日期上增加（+）或减去（-）年份
     *
     * @param date    输入日期
     * @param addYear 要增加或减少的年数
     */
    public static Date addYear(Date date, int addYear) {
        Calendar cd = Calendar.getInstance(GTM8);
        cd.setTime(date);
        cd.add(Calendar.YEAR, addYear);
        return cd.getTime();
    }

    /**
     * 在输入日期上增加（+）或减去（-）月份
     *
     * @param date     输入日期
     * @param addMonth 要增加或减少的月分数
     */
    public static Date addMonth(Date date, int addMonth) {
        Calendar cd = Calendar.getInstance(GTM8);
        cd.setTime(date);
        cd.add(Calendar.MONTH, addMonth);
        return cd.getTime();
    }

    /**
     * 在输入日期上增加（+）或减去（-）天数
     *
     * @param date   输入日期
     * @param addDay 要增加或减少的天数
     */
    public static Date addDay(Date date, int addDay) {
        Calendar cd = Calendar.getInstance(GTM8);
        cd.setTime(date);
        cd.add(Calendar.DAY_OF_YEAR, addDay);
        return cd.getTime();
    }

    /**
     * 在输入日期上增加（+）或减去（-）小时数
     *
     * @param date    输入日期
     * @param addHour 要增加或减少的小时数
     */
    public static Date addHour(Date date, int addHour) {
        Calendar cd = Calendar.getInstance(GTM8);
        cd.setTime(date);
        cd.add(Calendar.HOUR_OF_DAY, addHour);
        return cd.getTime();
    }

    /**
     * 在输入日期上增加（+）或减去（-）分钟数
     *
     * @param date      输入日期
     * @param addMinute 增加或减少的分钟数
     * @return
     */
    public static Date addMinute(Date date, int addMinute) {
        Calendar cd = Calendar.getInstance(GTM8);
        cd.setTime(date);
        cd.add(Calendar.MINUTE, addMinute);
        return cd.getTime();
    }

    /**
     * 计算 fromDate 到 toDate 相差多少个月
     *
     * @param fromDate
     * @param toDate
     * @return
     */
    public static int getDiffMonth(Date fromDate, Date toDate) {
        int diffMonth;
        Calendar fc = Calendar.getInstance(GTM8);
        Calendar tc = Calendar.getInstance(GTM8);

        fc.setTime(fromDate);
        tc.setTime(toDate);
        int[] d1 = {fc.get(Calendar.YEAR), fc.get(Calendar.MONTH)};
        int[] d2 = {tc.get(Calendar.YEAR), tc.get(Calendar.MONTH)};

        diffMonth = (d2[0] * 12 + d2[1]) - (d1[0] * 12 + d1[1]);
        return diffMonth;
    }

    /**
     * 计算 fromDate 到 toDate 相差多少天
     *
     * @param fromDate
     * @param toDate
     * @return 天数
     */
    public static Long getDiffDay(Date fromDate, Date toDate) {
        return getDiffHour(fromDate, toDate) / 24;
    }

    /**
     * 计算 fromDate 到 toDate 相差多少小时
     *
     * @param fromDate
     * @param toDate
     * @return
     */
    public static Long getDiffHour(Date fromDate, Date toDate) {
        return getDiffMinute(fromDate, toDate) / 60;
    }

    /**
     * 计算 fromDate 到 toDate 相差多少分钟
     */
    public static Long getDiffMinute(Date fromDate, Date toDate) {
        return (getDiffSecond(fromDate, toDate) / 60);
    }

    /**
     * 计算 fromDate 到 toDate 相差多少秒
     */
    public static Long getDiffSecond(Date fromDate, Date toDate) {
        return ((toDate.getTime() - fromDate.getTime()) / 1000);
    }

    /**
     * 时间戳转换成日期格式字符串
     *
     * @param seconds 精确到秒的字符串
     * @param format
     * @return
     */
    public static Date timeStamp2Date(String seconds, String format) {
        if (seconds == null || seconds.isEmpty() || seconds.equals("null")) {
            return null;
        }
        if (format == null || format.isEmpty()) {
            format = DATE_TIME;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String str = sdf.format(new Date(Long.valueOf(seconds + "000")));
        return stringToDateTime(str);
    }

    /**
     * 将长时间格式时间转换为字符串yyyyMMdd
     *
     * @param dateDate 日期
     * @return yyyyMMdd
     */
    public static String getYearMonthDay(Date dateDate) {
        SimpleDateFormat formatter = new SimpleDateFormat(YYYYMMDD);
        return formatter.format(dateDate);
    }

    /**
     * 获取年月(4位)
     *
     * @param date 日期
     * @return yyMM (如: 2103)
     */
    public static String getSimpleYearMonthCode(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat(YYMM);
        return formatter.format(date);
    }

    /**
     * 获取年月(6位)
     *
     * @param date 日期
     * @return yyyyMM (如: 202103)
     */
    public static String getYearMonthCode(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat(YYYYMM);
        return formatter.format(date);
    }

    public static String getHHmmss() {
        LocalTime currentTime = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HHmmss");
        return currentTime.format(formatter);
    }

    /*
     * 将时间戳转换为时间
     */
    public static Date stampToDate(Long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time_Date = sdf.format(new Date(time * 1000L));
        return stringToDateTime(time_Date);

    }

    public static Boolean isAfterDate(Date d1, Date d2) {
        return d1.compareTo(d2) > 0;
    }

    /**
     * 将长时间格式时间转换为字符串HHmmss
     *
     * @param dateDate
     * @return
     */
    public static int getHourMinuteSecond(Date dateDate) {
        SimpleDateFormat formatter = new SimpleDateFormat(HOUR + MINUTE + SECOND);
        String hourminutesecond = formatter.format(dateDate);
        return Integer.parseInt(hourminutesecond);
    }

    public static String DateToISOString(Date date) {
        SimpleDateFormat format = new SimpleDateFormat(ISO_8601_FORMAT);
        return format.format(date);
    }


    public static Date DATE_T_TimeToDate(String str) {
        SimpleDateFormat format = new SimpleDateFormat(DATE_T_HOUR_MINUTE);
        try {
            return format.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date ISOStringToDate(String str) {
        SimpleDateFormat format = new SimpleDateFormat(ISO_8601_FORMAT);
        try {
            return format.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param date 时间
     * @return HH:mm (String)
     */
    public static String getHHmm(Date date) {
        Calendar calendar = Calendar.getInstance(GTM8);
        calendar.setTime(date);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        return String.format("%02d:%02d", hour, minute);
    }


    /**
     * 获取指定年月的第一天
     * @param year
     * @param month
     * @return
     */
    public static String getFirstDayOfMonth1(int year, int month) {
        Calendar cal = Calendar.getInstance();
        //设置年份
        cal.set(Calendar.YEAR, year);
        //设置月份
        cal.set(Calendar.MONTH, month-1);
        //获取某月最小天数
        int firstDay = cal.getMinimum(Calendar.DATE);
        //设置日历中月份的最小天数
        cal.set(Calendar.DAY_OF_MONTH,firstDay);
        //格式化日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(cal.getTime());
    }

    /**
     * 获取指定年月的最后一天
     * @param year
     * @param month
     * @return
     */
    public static String getLastDayOfMonth1(int year, int month) {
        Calendar cal = Calendar.getInstance();
        //设置年份
        cal.set(Calendar.YEAR, year);
        //设置月份
        cal.set(Calendar.MONTH, month-1);
        //获取某月最大天数
        int lastDay = cal.getActualMaximum(Calendar.DATE);
        //设置日历中月份的最大天数
        cal.set(Calendar.DAY_OF_MONTH, lastDay);
        //格式化日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(cal.getTime());
    }


    /**
     * 精确计算两个日期之间相差的年数（小数点后3位） 计算到日期的天数
     *
     * @param fromDate
     * @param toDate
     * @return 年数
     */
    public static double getYearComparePrecise(Date fromDate, Date toDate) {
        double diffYear = 0d;
        Calendar fc = Calendar.getInstance(GTM8);
        Calendar tc = Calendar.getInstance(GTM8);
        fc.setTime(fromDate);
        tc.setTime(toDate);

        int fromYear = fc.get(Calendar.YEAR);
        int fromMont = fc.get(Calendar.MONTH);
        int fromDay;

        int toYear = tc.get(Calendar.YEAR);
        int toMont = tc.get(Calendar.MONTH);
        int toDay = tc.get(Calendar.DAY_OF_MONTH);

        int year = 0;
        int month = 0;
        int days = 0;

        if (toYear == fromYear) {
            // 1 同年
            month = toMont - fromMont;
            fc.add(Calendar.MONTH, month);
            fromDay = fc.get(Calendar.DAY_OF_MONTH);

            days = toDay - fromDay;

        } else if (toYear > fromYear) {
            // 2 不同年
            year = toYear - fromYear;
            // 放入同一年来计算
            fc.add(Calendar.YEAR, year);

            month = toMont - fromMont;
            fc.add(Calendar.MONTH, month);
            fromDay = fc.get(Calendar.DAY_OF_MONTH);

            if (month == 0) {
                days = toDay - fromDay;
            } else if (month > 0) {
                days = toDay - fromDay;
            } else {
                year--;
                month = 12 - fromMont + toMont;
                days = toDay - fromDay;
            }
        }

        BigDecimal bdYear = new BigDecimal(String.valueOf(year));
        BigDecimal bdMonth = new BigDecimal(String.valueOf(month)).divide(new BigDecimal("12"), 3,
                BigDecimal.ROUND_HALF_UP);
        BigDecimal bdDays;
        if (checkLeapYear(toYear)) {
            bdDays = new BigDecimal(String.valueOf(days)).divide(new BigDecimal(String.valueOf(366)), 3,
                    BigDecimal.ROUND_HALF_UP);
        } else {
            bdDays = new BigDecimal(String.valueOf(days)).divide(new BigDecimal(String.valueOf(365)), 3,
                    BigDecimal.ROUND_HALF_UP);
        }
        diffYear = bdYear.add(bdMonth).add(bdDays).doubleValue();

        return diffYear;
    }

    /**
     * @param year 年份
     * @return true-闰年, false-非闰年
     */
    public static boolean checkLeapYear(int year) {
        return (year % 4 == 0) && (year % 100 != 0) || (year % 400 == 0);
    }

    /**
     * @param minute
     * @return 时间转换，分钟转格式为天小时分钟
     */
    public static String turnDayHourMinuteString(int minute) {
        // 如果传入的分钟是0，默认24小时
        if (0 == minute) {
            return 24 + "小时";
        }
        // 如果分钟小于60，默认返回分钟
        if (0 < minute && minute < 60) {
            return minute + "分钟";
        }
        // 如果分钟小于24小时（1440分钟），返回小时和分钟
        if (60 <= minute && minute < 1440) {

            if (minute % 60 == 0) {
                int h = minute / 60;
                return h + "小时";
            } else {
                int h = minute / 60;
                int m = minute % 60;
                return h + "小时" + m + "分钟";
            }

        }
        // 如果分钟大于1天
        if (minute >= 1440) {

            int d = minute / 60 / 24;
            int h = minute / 60 % 24;
            int m = minute % 60;
            String s1 = null;
            if (d > 0) {
                s1 = d + "天";
            }
            // h如果计算大于等于1再展示，否则只展示天和分钟
            if (h >= 1) {
                s1 += h + "小时";
            }
            if (m > 0) {
                s1 += m + "分钟";
            }

            return s1;
        }
        return null;
    }

    public static String formatDateTime(FileTime fileTime) {

        LocalDateTime localDateTime = fileTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

        return localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public static String getStartDateTimeStr(Date date) {
        return DateUtil.dateToDateString(date)+" 00:00:00";
    }

    public static String getEndDateTimeStr(Date date) {
        return DateUtil.dateToDateString(date)+" 23:59:59";
    }


    /**
     * 将长时间格式时间转换为字符串yyMMdd
     * 采购发单日本用到
     * @param dateDate 日期
     * @return yyMMdd
     */
    public static String getAs400YearMonthDay(Date dateDate) {
        SimpleDateFormat ft = new SimpleDateFormat("yyMMdd");
        return ft.format(dateDate);
    }





}
