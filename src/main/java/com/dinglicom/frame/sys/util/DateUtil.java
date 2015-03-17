/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinglicom.frame.sys.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author panzhen
 */
public class DateUtil {
    private static SimpleDateFormat formatday = new SimpleDateFormat("yyyy-MM-dd");
    private static SimpleDateFormat formatmin = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private static SimpleDateFormat formatsec = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static SimpleDateFormat formatdaynum = new SimpleDateFormat("yyyyMMdd");
    /**
     * 获取下一个月第一天
     *
     * @param c
     * @return
     */
    public static Date getNextMonthFirstDay(Calendar c) {
        c.add(Calendar.MONTH, 1);
        c.set(Calendar.DAY_OF_MONTH, 1);
        c = getOneDayMintime(c);
        return c.getTime();
    }

    public static Date getNextMonthFirstDay(Date d) {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH, 1);
        return getNextMonthFirstDay(c);
    }

    /**
     * 获取下一天最小时刻
     *
     * @return
     */
    public static Date getNextDay() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH, 1);
        getOneDayMintime(c);
        return c.getTime();
    }

    /**
     * 获取下一个月第一天
     *
     * @return
     */
    public static Date getNextMonthFirstDay() {
        return getNextMonthFirstDay(Calendar.getInstance());
    }

    /**
     * 获取下一个月的首个工作日
     *
     * @param c
     * @return
     */
    public static Date getNextMonthFirstWorkDay(Calendar c) {
        c.add(Calendar.MONTH, 1);
        c.set(Calendar.DAY_OF_MONTH, 1);
        c = getLastWorkday(c);
        return c.getTime();
    }

    public static Date getNextMonthFirstWorkDay(Date d) {
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        return getNextMonthFirstWorkDay(c);
    }
    
    public static Date getNextMonthFirstWorkEndDay(Calendar c) {
        c.add(Calendar.MONTH, 1);
        c.set(Calendar.DAY_OF_MONTH, 1);
        c = getLastWorkEndday(c);
        return c.getTime();
    }

    public static Date getNextMonthFirstWorkEndDay(Date d) {
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        return getNextMonthFirstWorkEndDay(c);
    }

    /**
     * 获取参照时间day天以后的日期
     *
     * @param c 参照时间
     * @param day 天数
     * @return
     */
    public static Date getAfterDay(Calendar c, int day) {
        c.add(Calendar.DAY_OF_MONTH, day);
        c = getOneDayMaxtime(c);
        return c.getTime();
    }

    /**
     * 获取参照时间day天以后的日期
     *
     * @param c1 参照时间
     * @param day 天数
     * @return
     */
    public static Date getAfterCronDay(Calendar c1, int day) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(c1.getTimeInMillis());
        c.add(Calendar.DAY_OF_MONTH, day);
        c = getOneDayMaxtime(c);
        return c.getTime();
    }
    
    public static Calendar getPreDay(Calendar c, int day) {
        c.add(Calendar.DAY_OF_MONTH, -day);
        return c;
    }

    /**
     * 获取参照时间day天以后的日期
     *
     * @param d 参照时间
     * @param day 天数
     * @return
     */
    public static Date getAfterDay(Date d, int day) {
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        return getAfterDay(c, day);
    }

    /**
     * 获取最近的工作日，如果当前时间是工作日，直接返回
     *
     * @param c
     * @return
     */
    public static Calendar getLastWorkday(Calendar c) {
        switch (c.get(Calendar.DAY_OF_WEEK)) {
            case Calendar.SUNDAY:
                c.add(Calendar.DAY_OF_MONTH, 1);
                break;
            case Calendar.SATURDAY:
                c.add(Calendar.DAY_OF_MONTH, 2);
                break;
        }
        c = getOneDayMintime(c);
        return c;
    }

    /**
     * 获取最近的非工作日，如果当前时间是非工作日，直接返回
     *
     * @param c
     * @return
     */
    public static Calendar getLastWorkEndday(Calendar c) {
        switch (c.get(Calendar.DAY_OF_WEEK)) {
            case Calendar.SUNDAY:
            case Calendar.SATURDAY:
                break;
            default :
                c.add(Calendar.DAY_OF_MONTH, Calendar.SATURDAY - c.get(Calendar.DAY_OF_WEEK));
        }
        c = getOneDayMintime(c);
        return c;
    }

    /**
     * 获取最近的非工作日，如果当前时间是非工作日，直接返回
     *
     * @param d
     * @return
     */
    public static Date getLastWorkEndday(Date d) {
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        return getLastWorkEndday(c).getTime();
    }

    /**
     * 获取最近的工作日，如果当前时间是工作日，直接返回
     *
     * @param d
     * @return
     */
    public static Date getLastWorkday(Date d) {
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        return getLastWorkday(c).getTime();
    }

    /**
     * 获取一天中最大时刻的时间
     *
     * @param c
     * @return
     */
    public static Calendar getOneDayMaxtime(Calendar c) {
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        c.set(Calendar.MILLISECOND, 999);
        return c;
    }

    /**
     * 获取一天中最小时刻的时间
     *
     * @param c
     * @return
     */
    public static Calendar getOneDayMintime(Calendar c) {
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c;
    }

    /**
     * 获取参照时间同一天的最大时刻
     *
     * @param c
     * @return
     */
    public static Date getCronDayMaxDate(Calendar c) {
        return getCronDayMaxCalendar(c).getTime();
    }

    /**
     * 获取参照时间同一天的最大时刻
     *
     * @param c
     * @return
     */
    public static Calendar getCronDayMaxCalendar(Calendar c) {
        Calendar max = Calendar.getInstance();
        max.set(Calendar.YEAR, c.get(Calendar.YEAR));
        max.set(Calendar.MONTH, c.get(Calendar.MONTH));
        max.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH));
        max = getOneDayMaxtime(max);
        return max;
    }

    /**
     * 获取参照时间同一天的最小时刻
     *
     * @param c
     * @return
     */
    public static Date getCronDayMinDate(Calendar c) {
        Calendar min = Calendar.getInstance();
        min.set(Calendar.YEAR, c.get(Calendar.YEAR));
        min.set(Calendar.MONTH, c.get(Calendar.MONTH));
        min.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH));
        min = getOneDayMintime(min);
        return min.getTime();
    }

    /**
     * 获取参照时间同一天的最小时刻
     *
     * @param d
     * @return
     */
    public static Date getCronDayMinDate(Date d) {
        Calendar min = Calendar.getInstance();
        min.setTime(d);
        min = getOneDayMintime(min);
        return min.getTime();
    }

    /**
     * 获取参照时间以后的工作日的时间
     *
     * @param c
     * @param day
     * @return
     */
    public static Date getAfterWorkDay(Calendar c, int day) {
        c = getLastWorkday(c);
        int weekday = c.get(Calendar.DAY_OF_WEEK);
        int allday;
        int firstweekday = Calendar.SATURDAY - weekday;
        int leftday = day - firstweekday;
        if (day <= firstweekday) {
            allday = firstweekday;
        } else {
            allday = firstweekday + leftday % 5 + (leftday / 5 * 7) + (leftday % 5 == 0 ? 0 : 2);
        }
        c.add(Calendar.DAY_OF_MONTH, allday);
        c = getOneDayMaxtime(c);
        return c.getTime();
    }
    
    public static Date getAfterWorkEndDay(Date d, int day) {
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        return getAfterWorkEndDay(c, day);
    }

    public static Date getAfterWorkEndDay(Calendar c, int day) {
        int weeknum = c.get(Calendar.DAY_OF_WEEK);
        int totaldays = 0;
        int weddays;
        switch(weeknum) {
            case Calendar.SATURDAY:
                weddays = 2;
                break;
            case Calendar.SUNDAY:
                weddays = 1;
                break;
            default:
                totaldays = Calendar.SATURDAY - weeknum;
                if(day <= 2) {
                    totaldays += day;
                    weddays = day;
                } else {
                    weddays = 2;
                }
        }
        if(day <= weddays) {
            totaldays = weddays;
        } else {
            totaldays += weddays;
            while(true) {
                totaldays += 6;
                weddays++;
                if(weddays == day) {
                    break;
                }
                
                weddays++;
                totaldays ++;
                if(weddays == day) {
                    break;
                }
            }
        }
        c.add(Calendar.DAY_OF_MONTH, totaldays);
        return c.getTime();
    }

    /**
     * 获取参照时间以后的工作日的时间
     *
     * @param d
     * @param day
     * @return
     */
    public static Date getAfterWorkDay(Date d, int day) {
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        return getAfterWorkDay(c, day);
    }

    public static int getYear(Calendar c) {
        return c.get(Calendar.YEAR);
    }

    public static int getQuarter(Calendar c) {
        int quarter;
        switch (c.get(Calendar.MONTH)) {
            case Calendar.JANUARY:
            case Calendar.FEBRUARY:
            case Calendar.MARCH:
                quarter = 1;
                break;
            case Calendar.APRIL:
            case Calendar.MAY:
            case Calendar.JUNE:
                quarter = 2;
                break;
            case Calendar.JULY:
            case Calendar.AUGUST:
            case Calendar.SEPTEMBER:
                quarter = 3;
                break;
            default:
                quarter = 4;
        }
        return quarter;
    }

    public static int getMonth(Calendar c) {
        return c.get(Calendar.MONTH) + 1;
    }

    public static int getDay(Calendar c) {
        return c.get(Calendar.DAY_OF_MONTH);
    }

    public static boolean isWorkDay(Calendar c) {
        int week = c.get(Calendar.DAY_OF_WEEK);
        return !(Calendar.SUNDAY == week || Calendar.SATURDAY == week);
    }

    public static int getQuarterByMonth(int month) {
        int m;
        switch (month) {
            case 1:
            case 2:
            case 3:
                m = 1;
                break;
            case 4:
            case 5:
            case 6:
                m = 2;
                break;
            case 7:
            case 8:
            case 9:
                m = 3;
                break;
            default:
                m = 4;
        }
        return m;
    }
    
    public static Calendar getReportTime(String reportTime, Calendar c) {
        if(null == reportTime || reportTime.isEmpty()) {
            return c;
        }
        int p = reportTime.indexOf(":");
        if(p > 0) {
            int h = Integer.valueOf(reportTime.substring(0, p).trim());
            int m = Integer.valueOf(reportTime.substring(p + 1));
            c.set(Calendar.HOUR_OF_DAY, h);
            c.set(Calendar.MINUTE, m);
            c.set(Calendar.SECOND, 59);
        }
        return c;
    }
    
    public static String formatToDay(Date d) {
        return formatday.format(d);
    }
    
    public static Date parserString2Date(String datestr) {
        try {
            return formatday.parse(datestr);
        } catch (ParseException ex) {
        }
        return new Date();
    }
    
    public static String formatToDayNum(Date d) {
        return formatdaynum.format(d);
    }
    
    public static String formatToSec(Date d) {
        return formatsec.format(d);
    }

    public static void main(String[] args) {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH, 2);
        System.out.println(format.format(c.getTime()));
//        System.out.println("After or current work day:" + format.format(getLastWorkday(c).getTime()));
//        System.out.println("After 30 work day:" + format.format(getAfterWorkDay(c, 6).getTime()));
        
        getAfterWorkEndDay(c, 7);
    }
}
