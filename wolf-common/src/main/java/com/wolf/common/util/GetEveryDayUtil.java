package com.wolf.common.util;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GetEveryDayUtil {
    private static transient int gregorianCutoverYear = 1582;

    /** 闰年中每月天数 */
    private static final int[] DAYS_P_MONTH_LY= {31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    /** 非闰年中每月天数 */
    private static final int[] DAYS_P_MONTH_CY= {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    /** 代表数组里的年、月、日 */
    private static final int Y = 0, M = 1, D = 2;

    /**
     * 将代表日期的字符串分割为代表年月日的整形数组
     * @param date
     * @return
     */
    public static int[] splitYMD(String date){
        date = date.replace("-", "");
        int[] ymd = {0, 0, 0};
        ymd[Y] = Integer.parseInt(date.substring(0, 4));
        ymd[M] = Integer.parseInt(date.substring(4, 6));
        ymd[D] = Integer.parseInt(date.substring(6, 8));
        return ymd;
    }

    /**
     * 检查传入的参数代表的年份是否为闰年
     * @param year
     * @return
     */
    public static boolean isLeapYear(int year) {
        return year >= gregorianCutoverYear ?
                ((year%4 == 0) && ((year%100 != 0) || (year%400 == 0))) : (year%4 == 0);
    }

    /**
     * 日期加1天
     * @param year
     * @param month
     * @param day
     * @return
     */
    private static int[] addOneDay(int year, int month, int day){
        if(isLeapYear( year )){
            day++;
            if( day > DAYS_P_MONTH_LY[month -1 ] ){
                month++;
                if(month > 12){
                    year++;
                    month = 1;
                }
                day = 1;
            }
        }else{
            day++;
            if( day > DAYS_P_MONTH_CY[month -1 ] ){
                month++;
                if(month > 12){
                    year++;
                    month = 1;
                }
                day = 1;
            }
        }
        int[] ymd = {year, month, day};
        return ymd;
    }

    /**
     * 将不足两位的月份或日期补足为两位
     * @param decimal
     * @return
     */
    public static String formatMonthDay(int decimal){
        DecimalFormat df = new DecimalFormat("00");
        return df.format( decimal );
    }

    /**
     * 将不足四位的年份补足为四位
     * @param decimal
     * @return
     */
    public static String formatYear(int decimal){
        DecimalFormat df = new DecimalFormat("0000");
        return df.format( decimal );
    }

    /**
     * 计算两个日期之间相隔的天数
     * @param begin
     * @param end
     * @return
     * @throws ParseException
     */
    public static long countDay(String begin,String end){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date beginDate , endDate;
        long day = 0;
        try {
            beginDate= format.parse(begin);
            endDate= format.parse(end);
            day=(endDate.getTime()-beginDate.getTime())/(24*60*60*1000);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return day;
    }

    /**
     * 以循环的方式计算日期
     * @param beginDate endDate
     * @param
     * @return
     */
    public static List<String> getEveryday(String beginDate , String endDate){
        long days = countDay(beginDate, endDate);
        int[] ymd = splitYMD( beginDate );
        List<String> everyDays = new ArrayList<>();
        everyDays.add(beginDate);
        for(int i = 0; i < days; i++){
            ymd = addOneDay(ymd[Y], ymd[M], ymd[D]);
            everyDays.add(formatYear(ymd[Y])+"-"+formatMonthDay(ymd[M])+"-"+formatMonthDay(ymd[D]));
        }
        return everyDays;
    }

    public static void main(String[] args) {
//        List<String> list = GetEveryDayUtil.getEveryday("2019-07-29", "2020-09-02");
////        for (String result : list) {
////            System.out.println(result);
////        }
//
//        Integer totalItemCount = 33;
//        Integer totalSpentTimes = 11;
//        BigDecimal relateRate = BigDecimal.valueOf(totalItemCount / totalSpentTimes);
//        System.out.println("===11111====="+relateRate);
//        BigDecimal totalSpentAmount =new BigDecimal(509.99);
////        BigDecimal test = totalSpentAmount.divide(BigDecimal.valueOf(11));
////        System.out.println("===2222====="+test);
//        BigDecimal test2 = totalSpentAmount.divide(BigDecimal.valueOf(11),2, RoundingMode.HALF_UP);
//        System.out.println("===3333====="+test2);
//
//        String recentConsumeLow = "1000001";
        String recentConsumeLow = "10000000000000000001000";
        int recentConsumeDays = recentConsumeLow.length() - (recentConsumeLow.lastIndexOf('1') == -1 ? 1 : recentConsumeLow.lastIndexOf('1'));
        int recentNotConsumeDays = recentConsumeLow.length() - (recentConsumeLow.lastIndexOf('0') == -1 ? 1 : recentConsumeLow.lastIndexOf('0'));
        recentNotConsumeDays = (recentConsumeDays < recentNotConsumeDays) ? 0 : recentNotConsumeDays;
        System.out.println("========="+recentConsumeDays);
        System.out.println("========="+recentNotConsumeDays);

//        [{"amount":1.50,"code":"10269619110002","dept_sale":"01010404","inx":"0","quantity":16},{"amount":68.00,"code":"147552","dept_sale":"01010404","inx":"1","quantity":1},{"amount":10.80,"code":"159454","dept_sale":"01010403","inx":"2","quantity":1},{"amount":7.80,"code":"14636119110001","dept_sale":"01010403","inx":"3","quantity":2},{"amount":7.80,"code":"146361","dept_sale":"01010403","inx":"4","quantity":2}]

//        DaTongOrderVo daTongOrderVo = new DaTongOrderVo();
//        String test = "10269619110002";
//        daTongOrderVo.setCode(test.trim().substring(0, 6));
//        System.out.println(daTongOrderVo.getCode());
    }



}
