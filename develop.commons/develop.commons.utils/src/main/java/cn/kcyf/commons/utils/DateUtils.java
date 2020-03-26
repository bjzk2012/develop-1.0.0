package cn.kcyf.commons.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间帮助类
 */
public class DateUtils {

    /**
     * 构造函数
     */
    private DateUtils() {
    }

    /**
     * 获取Calendar
     *
     * @param time 时间
     * @return 日历
     */
    public static Calendar getCalendar(Date time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        return calendar;
    }

    /**
     * 设置时间
     *
     * @param time  时间
     * @param flag  类型
     * @param value 值
     * @return 时间
     */
    public static Date setTime(Date time, int flag, int value) {
        Calendar c = getCalendar(time);
        c.set(flag, value);
        return c.getTime();
    }

    /**
     * 设置时间
     *
     * @param time  时间
     * @param year  年
     * @param month 月
     * @param day   日
     * @return 时间
     */
    public static Date setTime(Date time, int year, int month, int day) {
        Calendar c = getCalendar(time);
        c.set(year, month, day);
        return c.getTime();
    }

    /**
     * 获取时间索引
     *
     * @param time 时间
     * @param flag 类型
     * @return 索引
     */
    public static int getTimeIndex(Date time, int flag) {
        Calendar c = getCalendar(time);
        return c.get(flag);
    }

    /**
     * 时间计算
     *
     * @param time  时间
     * @param flag  类型
     * @param value 值
     * @return 时间
     */
    public static Date addTime(Date time, int flag, int value) {
        Calendar c = getCalendar(time);
        c.add(flag, value);
        return c.getTime();
    }

    /**
     * 获取季度
     *
     * @param time 时间
     * @return 索引
     */
    public static int getSeason(Date time) {
        int season = 0;
        Calendar c = Calendar.getInstance();
        c.setTime(time);
        int month = c.get(Calendar.MONTH);
        switch (month) {
            case Calendar.JANUARY:
            case Calendar.FEBRUARY:
            case Calendar.MARCH:
                season = 1;
                break;
            case Calendar.APRIL:
            case Calendar.MAY:
            case Calendar.JUNE:
                season = 2;
                break;
            case Calendar.JULY:
            case Calendar.AUGUST:
            case Calendar.SEPTEMBER:
                season = 3;
                break;
            case Calendar.OCTOBER:
            case Calendar.NOVEMBER:
            case Calendar.DECEMBER:
                season = 4;
                break;
            default:
                break;
        }
        return season;
    }

    /**
     * 格式化时间
     *
     * @param time 时间
     * @param fmt  格式
     * @return 时间字符串
     */
    public static String format(Date time, String fmt) {
        SimpleDateFormat sdf = new SimpleDateFormat(fmt);
        return sdf.format(time);
    }

    /**
     * 格式化时间
     *
     * @param time 时间字符串
     * @param fmt  格式
     * @return 时间
     */
    public static Date parse(String time, String fmt) {
        SimpleDateFormat sdf = new SimpleDateFormat(fmt);
        try {
            return sdf.parse(time);
        } catch (ParseException e) {
            return new Date();
        }
    }

    /**
     * 格式化时间
     *
     * @param time   时间
     * @param fmt    格式
     * @param tagret 目标格式
     * @return 时间
     */
    public static Date convert(Date time, String fmt, String tagret) {
        return DateUtils.parse(DateUtils.format(time, fmt), tagret);
    }

    /**
     * 获取指定日期开始时间
     *
     * @param time
     * @return
     */
    public static Date getStartTime(Date time) {
        Calendar c = getCalendar(time);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    /**
     * 获取指定日期结束时间
     *
     * @param time
     * @return
     */
    public static Date getEndTime(Date time) {
        Calendar c = getCalendar(time);
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        c.set(Calendar.MILLISECOND, 999);
        return c.getTime();
    }

    /**
     * 计算2个时间间隔内的天数
     *
     * @param startTime
     * @param endTime
     * @return
     */
    public static int differentDays(Date startTime, Date endTime) {
        int days = (int) ((endTime.getTime() - startTime.getTime()) / (1000 * 3600 * 24));
        return days;
    }
}
