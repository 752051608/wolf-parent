package com.wolf.common.util;

import org.apache.commons.lang3.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * @author honglou
 * @date 2019-12-05 15:28
 */
public class DateUtils {
    /**
     * 计算当前时间距离当天结束相差多少秒
     *
     * @param currentDate
     * @return
     */
    public static Integer getRemainSecondsOneDay(Date currentDate) {
        LocalDateTime midnight = LocalDateTime.ofInstant(currentDate.toInstant(),
                ZoneId.systemDefault()).plusDays(1).withHour(0).withMinute(0)
                .withSecond(0).withNano(0);
        LocalDateTime currentDateTime = LocalDateTime.ofInstant(currentDate.toInstant(),
                ZoneId.systemDefault());
        long seconds = ChronoUnit.SECONDS.between(currentDateTime, midnight);
        return (int) seconds;
    }

    /**
     * 计算当前时间距离当天结束相差多少秒
     *
     * @param currentDate 当前时间
     * @param endDate     结束时间
     * @return
     */
    public static Integer getRemainSecondsToTime(Date currentDate, Date endDate) {
        LocalDateTime midnight = date2LocalDateTime(endDate);
        LocalDateTime currentDateTime = LocalDateTime.ofInstant(currentDate.toInstant(),
                ZoneId.systemDefault());
        long seconds = ChronoUnit.SECONDS.between(currentDateTime, midnight);
        return (int) seconds;
    }


    /**
     * Date转换为LocalDateTime
     *
     * @param date
     */
    public static LocalDateTime date2LocalDateTime(Date date) {
        if(date == null){
            return null;
        }
        Instant instant = date.toInstant();//An instantaneous point on the time-line.(时间线上的一个瞬时点。)
        ZoneId zoneId = ZoneId.systemDefault();//A time-zone ID, such as {@code Europe/Paris}.(时区)
        return instant.atZone(zoneId).toLocalDateTime();
    }

    /**
     * LocalDateTime转换为Date
     *
     * @param localDateTime
     */
    public static Date localDateTime2Date(LocalDateTime localDateTime) {
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zdt = localDateTime.atZone(zoneId);//Combines this date-time with a time-zone to create a  ZonedDateTime.
        return Date.from(zdt.toInstant());
    }

    /**
     * 间隔的天数
     *
     * @param endTime
     * @param startTime
     * @return
     */
    public static int betweenDays(LocalDateTime endTime, LocalDateTime startTime) {
        int daysNum = (int) (endTime.toLocalDate().toEpochDay() - startTime.toLocalDate().toEpochDay());
        return daysNum;
    }

    /**
     * 间隔的天数
     *
     * @param endTime
     * @param startTime
     * @return
     */
    public static int betweenDays(LocalDateTime endTime, Date startTime) {
        int daysNum = (int) (endTime.toLocalDate().toEpochDay() - date2LocalDateTime(startTime).toLocalDate().toEpochDay());
        return daysNum;
    }

    public static final String SIMPLE_DATE_PARTEN = "yyyy-MM-dd";
    public static final String DATE_PARTEN = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_PARTEN_Chinese = "yyyy年MM月dd日HH:mm";
    public static final String DATE_PARTEN_Ch = "MM月dd日";
    public static final String DATE_PARTEN_MILLI = "yyyy-MM-dd HH:mm:ss.SSS";
    public static final String DATE_PARTEN_YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
    public static final String DATE_PARTEN_YYMMDD = "yyMMdd";
    public static final String DATE_PARTEN_YYMMDDHHMMSS = "yyMMddHHmmss";
    public static final String formateStr19 = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_PARTEN_ENGLISH = "MMM d, yyyy K:m:s a";

    /**
     * 时间字符串转Date类对象，结果类似2017-09-09 11:11:11
     *
     * @param date
     * @return
     * @throws ParseException
     */
    public static Date transferStringToDate(String date) throws ParseException {
        DateFormat format = new SimpleDateFormat(formateStr19);
        return format.parse(date);
    }

    /**
     * 时间字符串转Date类对象，结果类似2017-09-09
     *
     * @param date
     * @return
     * @throws ParseException
     */
    public static Date transferStringToDay(String date) throws ParseException {
        DateFormat format = new SimpleDateFormat(SIMPLE_DATE_PARTEN);
        return format.parse(date);
    }

    /**
     * @param date
     * @return
     */
    public static String transferDateToString(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(formateStr19);
        return sdf.format(date);
    }


    /**
     * 增加秒
     *
     * @param d
     * @param count
     * @return
     */
    public static Date addSecond(Date d, int count) {
        return org.apache.commons.lang3.time.DateUtils.addSeconds(d, count);
    }


    /**
     * 将当前日期升级到当天最大值  23:59:59
     *
     * @param dateTime
     * @return
     */
    public static Date upperDateForSecond(Date dateTime) {
        Date nextMinDate = formatDate2Date(addDays(dateTime, 1), "yyyy-MM-dd");
        return addSecond(nextMinDate, -1);
    }


    public static Date formatDate2Date(Date date, String format) {
        return parseDate(formatDate(date, format), format);
    }


    public static Date parseDate(String dateStr, String format) {
        if (StringUtils.isEmpty(dateStr)) {
            return null;
        }
        DateFormat df = new SimpleDateFormat(format);
        try {
            return df.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            System.out.println("日期格式错误:");
            return null;
        }
    }

    /**
     * 一个日期加days天
     *
     * @param startDate 原来的日期
     * @param days      天数
     * @return 处理后的日期yyyy-MM-dd
     */
    public static Date addSomeDays(Date startDate, int days) {
        return new Date(startDate.getTime() + days * 24 * 60 * 60 * 1000L);
    }

    /**
     * 以某一天为基础，增加count天
     * count可以为负数,代表减少count天
     *
     * @param d
     * @param count
     * @return
     */
    public static Date addDays(Date d, int count) {
        return org.apache.commons.lang3.time.DateUtils.addDays(d, count);
    }

    public static boolean isToday(Date date) {

        if (date != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            Calendar today = Calendar.getInstance();
            if (calendar.get(Calendar.YEAR) == today.get(Calendar.YEAR)
                    && calendar.get(Calendar.MONTH) == today
                    .get(Calendar.MONTH)
                    && calendar.get(Calendar.DAY_OF_MONTH) == today
                    .get(Calendar.DAY_OF_MONTH)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param nowTime   当前时间
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return
     * @author sunran   判断当前时间在时间区间内
     */
    public static boolean isEffectiveDate(Date nowTime, Date startTime, Date endTime) {
        if (nowTime.getTime() == startTime.getTime()
                || nowTime.getTime() == endTime.getTime()) {
            return true;
        }

        Calendar date = Calendar.getInstance();
        date.setTime(nowTime);

        Calendar begin = Calendar.getInstance();
        begin.setTime(startTime);

        Calendar end = Calendar.getInstance();
        end.setTime(endTime);

        if (date.after(begin) && date.before(end)) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isAfterToday(Date date) {
        if (date != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);

            Calendar today = Calendar.getInstance();
            today.set(Calendar.HOUR_OF_DAY, 23);
            today.set(Calendar.MINUTE, 59);
            today.set(Calendar.SECOND, 59);

            return calendar.after(today);
        }
        return false;
    }

    public static Date parseDate(String dateStr) {
        if (dateStr != null) {
            try {
                if (dateStr.length() <= 10) {
                    return getSimpleFormat().parse(dateStr);
                } else {
                    return getFormat().parse(dateStr);
                }
            } catch (Exception e) {
                return null;
            }

        }
        return null;
    }

    public static String formatDate(Long date) {
        if (date == null) {
            return null;
        }
        return formatDate(new Date(date));
    }

    public static String formatDate(Date date) {
        if (date == null) {
            return null;
        }
        try {
            return getFormat().format(date);
        } catch (Exception e) {
            return null;
        }
    }


    public static String formatChineseDate(Date date) {
        if (date == null) {
            return null;
        }
        try {
            return getChineseFormat().format(date);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 返回当前时间的秒数
     *
     * @return
     */
    public static int unixTime() {
        return (int) (System.currentTimeMillis() / 1000);
    }

    public static Long getTimeLong() {

        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println(localDateTime.toInstant(ZoneOffset.ofHours(8)).toEpochMilli());


        return localDateTime.toInstant(ZoneOffset.ofHours(8)).toEpochMilli();
    }

    /**
     * 把表转换为Date
     *
     * @param seconds
     * @return
     */
    public static Date fromUnixTime(Integer seconds) {
        return new Date(seconds * 1000L);
    }

    public static Date fromUnixTime(Long seconds) {
        return new Date(seconds * 1000L);
    }


    public static String getTodayString() {
        Calendar cal = Calendar.getInstance();
        String today = new SimpleDateFormat("yyyyMMdd").format(cal.getTime());
        System.out.println(today);
        return today;
    }

    public static String formatSimpleDate(Date date) {
        if (date == null) {
            return null;
        }
        try {
            return getSimpleFormat().format(date);
        } catch (Exception e) {
            return null;
        }
    }

    public static String formatMillisecondDate(Date date) {
        if (date == null) {
            return null;
        }

        try {
            return getMilliFormat().format(date);
        } catch (Exception e) {
            return null;
        }

    }

    private static ThreadLocal<SimpleDateFormat> threadLocal = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat(DATE_PARTEN);
        }
    };

    private static ThreadLocal<SimpleDateFormat> threadLocal_simple = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat(SIMPLE_DATE_PARTEN);
        }
    };
    private static ThreadLocal<SimpleDateFormat> threadLocal_milli = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat(DATE_PARTEN_MILLI);
        }
    };
    private static ThreadLocal<SimpleDateFormat> threadLocal_chinese = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat(DATE_PARTEN_Chinese);
        }
    };

    private static DateFormat getFormat() {
        return threadLocal.get();
    }

    private static DateFormat getSimpleFormat() {
        return threadLocal_simple.get();
    }

    private static DateFormat getMilliFormat() {
        return threadLocal_milli.get();
    }

    private static DateFormat getChineseFormat() {
        return threadLocal_chinese.get();
    }

    public static Date getDate(Long millisecond) {
        return millisecond != null && millisecond > 0 ? new Date(millisecond)
                : null;
    }

    public static Date getDay(Integer dayNumOffet) {
        if (dayNumOffet == null) {
            return null;
        }
        return getDay(Calendar.getInstance().getTime(), dayNumOffet);
    }

    public static Date getDay(Date theDate, int dayNumOffet) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(theDate);

        calendar.add(Calendar.DAY_OF_MONTH, dayNumOffet);
        Date date = calendar.getTime();

        return date;
    }

    public static Date convertStr2Date(String str, String format) throws ParseException {
        if (StringUtils.isEmpty(str)) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.parse(str);
    }

    public static Date convertEnglishStr2Date(String str, String format) throws ParseException {
        if (StringUtils.isEmpty(str)) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.ENGLISH);
        return sdf.parse(str);
    }

    public static String convertDate2Str(Date date, String format) {
        if (date == null || StringUtils.isEmpty(format)) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    /**
     * @param date
     * @return
     */
    public static final String ymdFormat(Date date) {
        if (date == null) {
            return "";
        }

        DateFormat ymdFormat = new SimpleDateFormat("yyyy-MM-dd");
        return ymdFormat.format(date);
    }

    public static final String hmsFormat(Date date) {
        if (date == null) {
            return "";
        }

        DateFormat hmsFormat = new SimpleDateFormat("HH:mm");
        return hmsFormat.format(date);
    }

    public static String formatDate(Date showTime, String dateformat) {
        if (showTime == null) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(dateformat);
        return sdf.format(showTime);
    }

    public static int getField(Date date, int field) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(field);
    }

    /**
     * 指定日期00:00:00
     *
     * @param date      指定日期
     * @param dayAmount 日期偏移量
     * @return
     */
    public static Date getDateLeft(Date date, int dayAmount) {
        if (date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, dayAmount);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    /**
     * 指定日期23:59:59
     *
     * @param date      指定日期
     * @param dayAmount 日期偏移量
     * @return
     */
    public static Date getDateRight(Date date, int dayAmount) {
        if (date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, dayAmount);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        return calendar.getTime();
    }

    /**
     * @param date       基准日期
     * @param type       计算类型，比如根据小时来计算，就是 Calendar.HOUR_OF_DAY
     * @param hourAmount 计算量
     * @return
     */
    public static Date getDateByCalculate(Date date, int type, int hourAmount) {
        Calendar Cal = Calendar.getInstance();
        Cal.setTime(date);
        Cal.add(type, hourAmount);
        return Cal.getTime();
    }


    /**
     * 判断time是否在from之后
     *
     * @param time 指定日期
     * @param from 开始日期
     * @return
     * @author xiajun
     */
    public static boolean afterCalendar(Date time, Date from) {
        Calendar date = Calendar.getInstance();
        date.setTime(time);

        Calendar after = Calendar.getInstance();
        after.setTime(from);

        return date.after(after);
    }


    /**
     * 自定义时间
     *
     * @param type     年，月，日
     * @param internal 推前或推后时间间隔，一年，一个月，一个星期，一天等，推前用负数
     * @return
     */
    public static Date getSelfDefyDate(int type, Integer internal) {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        if (Calendar.YEAR == type) {
            calendar.add(calendar.YEAR, internal);
        } else if (Calendar.DAY_OF_MONTH == type) {
            calendar.add(calendar.DAY_OF_MONTH, internal);
        } else if (Calendar.MONTH == type) {
            calendar.add(calendar.MONTH, internal);
        }
        date = calendar.getTime();
        return date;
    }


    /**
     * 获取过去第几天的日期
     *
     * @param past
     * @return
     */
    public static String getPastDate(int past) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - past);
        Date today = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String result = format.format(today);
        return result;
    }

    /**
     * 得到几天后的时间
     *
     * @param d
     * @param day
     * @return
     */
    public static Date getDateAfter(Date d, int day) {
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.DATE, now.get(Calendar.DATE) + day);
        return now.getTime();
    }

    /**
     * 获取几天后的工作日时间
     *
     * @param transDate
     * @param i
     * @return
     */
    public static Date getBetweenWorkDay(Date transDate, int i) {
        Date endTime = getDateAfter(transDate, i);
        Calendar cal = Calendar.getInstance();
        cal.setTime(endTime);
        if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
            return getDateAfter(endTime, 2);
        } else if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            return getDateAfter(endTime, 1);
        }
        return endTime;
    }

    /**
     * 判断是否是weekend
     *
     * @param sdate
     * @return
     * @throws ParseException
     */
    public static boolean isWeekend(String sdate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = sdf.parse(sdate);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            return true;
        } else {
            return false;
        }

    }

    /**
     * date2比date1多的天数
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int differentDays(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        int day1 = cal1.get(Calendar.DAY_OF_YEAR);
        int day2 = cal2.get(Calendar.DAY_OF_YEAR);

        int year1 = cal1.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);
        if (year1 != year2)   //同一年
        {
            int timeDistance = 0;
            for (int i = year1; i < year2; i++) {
                if (i % 4 == 0 && i % 100 != 0 || i % 400 == 0)    //闰年
                {
                    timeDistance += 366;
                } else    //不是闰年
                {
                    timeDistance += 365;
                }
            }

            return timeDistance + (day2 - day1);
        } else    //不同年
        {
//            System.out.println("判断day2 - day1 : " + (day2 - day1));
            return day2 - day1;
        }
    }

    /**
     * 通过时间秒毫秒数判断两个时间的间隔
     * 对于小于24小时的是0天
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int differentDaysByMillisecond(Date date1, Date date2) {
        int days = (int) ((date2.getTime() - date1.getTime()) / (1000 * 3600 * 24));
        return days;
    }

    /**
     * 当前日期是否在xx-xx点 之间
     * 用于 判断是否 可以下单
     *
     * @return
     * @throws ParseException
     */
    public static boolean isTimeRangePayOrder(String startHMStr, String endHMStr) {
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");
        Date now = null;
        Date begin = null;
        Date end = null;
        try {
            now = df.parse(df.format(new Date()));
            begin = df.parse(startHMStr);
            end = df.parse(endHMStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar nowTime = Calendar.getInstance();
        nowTime.setTime(now);
        Calendar beginTime = Calendar.getInstance();
        beginTime.setTime(begin);
        Calendar endTime = Calendar.getInstance();
        endTime.setTime(end);
        if (nowTime.before(endTime) && nowTime.after(beginTime)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Date类型日期
     *
     * @param date Date类型日期
     * @return String类型日期yyyy-MM-dd
     */
    public static Date dateToDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        synchronized (sdf) {
            String format = sdf.format(date);
            return stringToDate(format);
        }
    }

    public static Date stringToDate(String strDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {

            synchronized (sdf) {
                return sdf.parse(strDate);
            }
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * todo belongCalendar1 存在bug
     * 判断当前时间是否在当天的开始时间到隔天的结束时间内
     *
     * @param nowString
     * @param beginTime
     * @param endTime
     * @return
     * @throws ParseException
     */
    public static boolean belongCalendar1(String nowString, String beginTime, String endTime) throws ParseException {
        //设置当前时间
        Calendar date = Calendar.getInstance();
        Date nowTime = new Date();
        date.setTime(nowTime);
        //设置开始时间
        Calendar begin = Calendar.getInstance();
        begin.setTime(nowTime);
        //设置结束时间
        Calendar end = Calendar.getInstance();
        end.setTime(nowTime);
        end.add(end.DATE, 1);//把日期往后增加一天.整数往后推,负数往前移动
        Date endDate = end.getTime();
        //设置日期格式
        SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
        SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm");//设置日期格式
        String dayStr = df1.format(endDate);
        if (StringUtils.isNotBlank(nowString)) {
            date.setTime(df2.parse(df1.format(nowTime) + " " + nowString));
        }
        begin.setTime(df2.parse(df1.format(nowTime) + " " + beginTime));
        //设置截止时间
        end.setTime(df2.parse(dayStr + " " + endTime));
        //处于开始时间之后，和结束时间之前的判断
        if (date.after(begin) && date.before(end)) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean belongCalendar(String nowString, String beginTime, String endTime) throws ParseException {
        //设置日期格式
        SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
        SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm");//设置日期格式
        Date nowTime = new Date();
        //两种情况,当前时间是今天 当前时间是隔天
        //1.当前时间是隔天时,与昨天做比对,date1与beginTime比较
        Calendar date1 = Calendar.getInstance();
        date1.setTime(nowTime);
        date1.add(date1.DATE, -1);//把日期往后减一天.整数往后推,负数往前移动
        if (StringUtils.isNotBlank(nowString)) {
            date1.setTime(df2.parse(df1.format(date1.getTime()) + " " + nowString));
        }
        //设置开始时间
        Calendar begin1 = Calendar.getInstance();
        begin1.setTime(nowTime);
        begin1.add(date1.DATE, -1);
        //设置结束时间
        Calendar end1 = Calendar.getInstance();
        end1.setTime(nowTime);

        begin1.setTime(df2.parse(df1.format(begin1.getTime()) + " " + beginTime));
        //设置截止时间
        end1.setTime(df2.parse(df1.format(end1.getTime()) + " " + endTime));
        boolean flag1= date1.after(begin1) && date1.before(end1);
//        System.out.println("=======1========="+flag1);

        //2.当前时间是今天时,与隔天做比对,date2与endTime比较
        Calendar date2 = Calendar.getInstance();
        date2.setTime(nowTime);
        date2.add(date2.DATE, 1);//把日期往后增加一天.整数往后推,负数往前移动
        //设置开始时间
        Calendar begin2 = Calendar.getInstance();
        begin2.setTime(nowTime);
        //设置结束时间
        Calendar end2 = Calendar.getInstance();
        end2.setTime(nowTime);
        end2.add(end2.DATE, 1);//把日期往后增加一天.整数往后推,负数往前移动
        Date endDate = end2.getTime();
        String dayStr = df1.format(endDate);
        if (StringUtils.isNotBlank(nowString)) {
            date2.setTime(df2.parse(df1.format(date2.getTime()) + " " + nowString));
        }
        begin2.setTime(df2.parse(df1.format(nowTime) + " " + beginTime));
        //设置截止时间
        end2.setTime(df2.parse(dayStr + " " + endTime));
        boolean flag2= date2.after(begin2) && date2.before(end2);
//        System.out.println("=======2========="+flag2);
        //处于开始时间之后，和结束时间之前的判断
        if (flag1 || flag2) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 默认是当前时间
     *
     * @param beginTime
     * @param endTime
     * @return
     * @throws ParseException
     */
    public static boolean belongCalendar(String beginTime, String endTime) throws ParseException {
        return belongCalendar(null, beginTime, endTime);
    }

    public static void main(String[] args) throws ParseException {
//        String timeStr = "2018-12-23 20:30:26";
//        Date tempDate = DateUtil.convertStr2Date(timeStr, "yyyy-MM-dd HH:mm:ss");
//        Date endDate = DateUtil.getBetweenWorkDay(tempDate, 1);
//        System.out.println(DateUtil.convertDate2Str(endDate, "yyyy-MM-dd HH:mm:ss"));
//
//        System.out.println(differentDays(endDate, new Date()));
//        SimpleDateFormat df = new SimpleDateFormat("HH:mm");//设置日期格式
//        //初始化
//        Date nowTime = null;
//        Date beginTime = null;
//        Date endTime = null;
////        nowTime = df.parse("22:00");
////        beginTime = df.parse();
////        endTime = df.parse();
//        boolean flag = belongCalendar("08:10", "08:00", "08:00");
////        boolean flag = belongCalendar("21:00", "03:00");
////        System.out.println("====能否在时间段内下单======" + isTimeRangePayOrder("07:00", "08:00"));
//        System.out.println("====能否在时间段内下单======" + flag);

        Boolean flag= true;
        System.out.println(flag==false);
    }
}
