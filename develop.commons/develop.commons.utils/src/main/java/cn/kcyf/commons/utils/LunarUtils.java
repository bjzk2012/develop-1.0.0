package cn.kcyf.commons.utils;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 农历计算器
 */
public class LunarUtils {
    private final static String chineseNumber[] = {"一", "二", "三", "四", "五", "六", "七", "八", "九", "十", "十一", "十二"};
    private final static String chineseTen[] = {"初", "十", "廿", "卅"};
    private final static String chineseYearNumber[] = {"〇", "一", "二", "三", "四", "五", "六", "七", "八", "九"};
    private final static int[] monthNum = {2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 1};
    private final static String ganzhiMonthNumber[][] = {
            {"丙寅", "戊寅", "庚寅", "壬寅", "甲寅"},
            {"丁卯", "己卯", "辛卯", "癸卯", "乙卯"},
            {"戊辰", "庚辰", "壬辰", "甲辰", "丙辰"},
            {"己巳", "辛巳", "癸巳", "乙巳", "丁巳"},
            {"庚午", "壬午", "甲午", "丙午", "戊午"},
            {"辛未", "癸未", "乙未", "丁未", "己未"},
            {"壬申", "甲申", "丙申", "戊申", "庚申"},
            {"癸酉", "乙酉", "丁酉", "己酉", "辛酉"},
            {"甲戌", "丙戌", "戊戌", "庚戌", "壬戌"},
            {"乙亥", "丁亥", "己亥", "辛亥", "癸亥"},
            {"丙子", "戊子", "庚子", "壬子", "甲子"},
            {"丁丑", "己丑", "辛丑", "癸丑", "乙丑"}};
    private final static String[] animals = new String[]{"鼠", "牛", "虎", "兔", "龙", "蛇", "马", "羊", "猴", "鸡", "狗", "猪"};
    private final static String[] gan = new String[]{"甲", "乙", "丙", "丁", "戊", "己", "庚", "辛", "壬", "癸"};
    private final static String[] zhi = new String[]{"子", "丑", "寅", "卯", "辰", "巳", "午", "未", "申", "酉", "戌", "亥"};
    private final static long[] lunarInfo = new long[]
            {0x04bd8, 0x04ae0, 0x0a570, 0x054d5, 0x0d260, 0x0d950, 0x16554, 0x056a0, 0x09ad0, 0x055d2,
                    0x04ae0, 0x0a5b6, 0x0a4d0, 0x0d250, 0x1d255, 0x0b540, 0x0d6a0, 0x0ada2, 0x095b0, 0x14977,
                    0x04970, 0x0a4b0, 0x0b4b5, 0x06a50, 0x06d40, 0x1ab54, 0x02b60, 0x09570, 0x052f2, 0x04970,
                    0x06566, 0x0d4a0, 0x0ea50, 0x06e95, 0x05ad0, 0x02b60, 0x186e3, 0x092e0, 0x1c8d7, 0x0c950,
                    0x0d4a0, 0x1d8a6, 0x0b550, 0x056a0, 0x1a5b4, 0x025d0, 0x092d0, 0x0d2b2, 0x0a950, 0x0b557,
                    0x06ca0, 0x0b550, 0x15355, 0x04da0, 0x0a5d0, 0x14573, 0x052d0, 0x0a9a8, 0x0e950, 0x06aa0,
                    0x0aea6, 0x0ab50, 0x04b60, 0x0aae4, 0x0a570, 0x05260, 0x0f263, 0x0d950, 0x05b57, 0x056a0,
                    0x096d0, 0x04dd5, 0x04ad0, 0x0a4d0, 0x0d4d4, 0x0d250, 0x0d558, 0x0b540, 0x0b5a0, 0x195a6,
                    0x095b0, 0x049b0, 0x0a974, 0x0a4b0, 0x0b27a, 0x06a50, 0x06d40, 0x0af46, 0x0ab60, 0x09570,
                    0x04af5, 0x04970, 0x064b0, 0x074a3, 0x0ea50, 0x06b58, 0x055c0, 0x0ab60, 0x096d5, 0x092e0,
                    0x0c960, 0x0d954, 0x0d4a0, 0x0da50, 0x07552, 0x056a0, 0x0abb7, 0x025d0, 0x092d0, 0x0cab5,
                    0x0a950, 0x0b4a0, 0x0baa4, 0x0ad50, 0x055d9, 0x04ba0, 0x0a5b0, 0x15176, 0x052b0, 0x0a930,
                    0x07954, 0x06aa0, 0x0ad50, 0x05b52, 0x04b60, 0x0a6e6, 0x0a4e0, 0x0d260, 0x0ea65, 0x0d530,
                    0x05aa0, 0x076a3, 0x096d0, 0x04bd7, 0x04ad0, 0x0a4d0, 0x1d0b6, 0x0d250, 0x0d520, 0x0dd45,
                    0x0b5a0, 0x056d0, 0x055b2, 0x049b0, 0x0a577, 0x0a4b0, 0x0aa50, 0x1b255, 0x06d20, 0x0ada0};
    private final static Map<Integer, Map<Integer, String>> monthTable = new HashMap<>();
    private final static Map<Integer, String> ganMap = new HashMap<>();
    private final static Map<Integer, String> zhiMap = new HashMap<>();

    /**
     * 初始化各静态变量
     */
    static {
        putMap(ganMap, gan);
        putMap(zhiMap, zhi);
        for (int i = 0; i < monthNum.length; i++) {
            Map<Integer, String> map1 = new HashMap<>();
            for (int j = 0; j < ganzhiMonthNumber[i].length; j++) {
                map1.put(j + 1, ganzhiMonthNumber[i][j]);
            }
            monthTable.put(monthNum[i], map1);
        }
    }

    public static void main(String[] args) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.YEAR, 2014);
        calendar.set(Calendar.MONTH, 10);
        calendar.set(Calendar.DATE, 1);
        System.out.println(LunarUtils.ganzhiYear(calendar.getTime()));
        System.out.println(LunarUtils.lunar(calendar.getTime()));
        System.out.println(LunarUtils.animals(calendar.get(Calendar.YEAR)));
    }

    /**
     * 将天干、地支数组添加到Map集合
     *
     * @param map
     * @param arr
     */
    private static void putMap(Map<Integer, String> map, String[] arr) {
        for (int i = 0; i < arr.length; i++) {
            if (i != arr.length - 1) {
                map.put(i + 1, arr[i]);
            } else {
                map.put(0, arr[arr.length - 1]);
            }
        }
    }

    /**
     * 传回农历 y年的总天数
     *
     * @param y
     * @return
     */
    private static int yearDays(int y) {
        int i, sum = 348;
        for (i = 0x8000; i > 0x8; i >>= 1) {
            if ((lunarInfo[y - 1900] & i) != 0) sum += 1;
        }
        return (sum + leapDays(y));
    }

    /**
     * 传回农历 y年闰月的天数
     *
     * @param y
     * @return
     */
    private static int leapDays(int y) {
        if (leapMonth(y) != 0) {
            if ((lunarInfo[y - 1900] & 0x10000) != 0)
                return 30;
            else
                return 29;
        } else
            return 0;
    }

    /**
     * 传回农历 y年闰哪个月 1-12 , 没闰传回 0
     *
     * @param y
     * @return
     */
    private static int leapMonth(int y) {
        return (int) (lunarInfo[y - 1900] & 0xf);
    }

    /**
     * 传回农历 y年m月的总天数
     *
     * @param y
     * @param m
     * @return
     */
    private static int monthDays(int y, int m) {
        if ((lunarInfo[y - 1900] & (0x10000 >> m)) == 0)
            return 29;
        else
            return 30;
    }

    /**
     * 传回农历 y年的生肖
     *
     * @param y
     * @return
     */
    private static String animals(int y) {
        return animals[(y - 4) % 12];
    }

    /**
     * 获取y年的中文
     *
     * @param year
     * @return
     */
    private static String getChinaYearString(int year) {
        String yearStr = year + "";
        String result = "";
        for (char c : yearStr.toCharArray()) {
            result += chineseYearNumber[Integer.parseInt(String.valueOf(c))];
        }
        return result;
    }

    /**
     * 获取month月的中文
     *
     * @param month
     * @return
     */
    private static String getChinaMonthString(int month) {
        return chineseNumber[month - 1];
    }

    /**
     * 获取day天的中文
     *
     * @param day
     * @return
     */
    private static String getChinaDayString(int day) {
        switch (day) {
            case 10:
                return chineseTen[0] + chineseNumber[day - 1];
            case 20:
            case 30:
                return chineseTen[day / 10];
            default:
                int n = day % 10 == 0 ? 9 : day % 10 - 1;
                return chineseTen[day / 10] + chineseNumber[n];
        }
    }

    /**
     * 年的天干对应月地支的位置
     *
     * @param tianGanOfYear
     * @return
     */
    private static int indexOfmonthTable(String tianGanOfYear) {
        String[] tmp = {"", "甲己", "乙庚", "丙辛", "丁壬", "戊癸"};
        int num = 0;
        for (int i = 0; i < tmp.length; i++) {
            if (tmp[i].contains(tianGanOfYear)) {
                num = i;
            }
        }
        return num;
    }

    /**
     * 获取闰年
     *
     * @param time
     * @return
     * @throws ParseException
     */
    public static String lunar(Date time) throws ParseException {
        int year;
        boolean leap = false;
        int leapMonth;
        Calendar calendar = Calendar.getInstance();
        calendar.set(1900, 0, 31);
        Date baseDate = calendar.getTime();
        //求出和1900年1月31日相差的天数
        int offset = (int) ((time.getTime() - baseDate.getTime()) / 86400000L);
        //用offset减去每农历年的天数
        // 计算当天是农历第几天
        //i最终结果是农历的年份
        //offset是当年的第几天
        int daysOfYear = 0;
        for (year = 1900; year < 2050 && offset > 0; year++) {
            daysOfYear = yearDays(year);
            offset -= daysOfYear;
        }
        if (offset < 0) {
            offset += daysOfYear;
            year--;
        }
        // 农历年份
        leapMonth = leapMonth(year);
        // 闰哪个月,1-12
        // 用当年的天数offset,逐个减去每月（农历）的天数，求出当天是本月的第几天
        int month, daysOfMonth = 0;
        for (month = 1; month < 13 && offset > 0; month++) {
            // 闰月
            if (leapMonth > 0 && month == (leapMonth + 1) && !leap) {
                --month;
                leap = true;
                daysOfMonth = leapDays(year);
            } else
                daysOfMonth = monthDays(year, month);

            offset -= daysOfMonth;
            //解除闰月
            if (leap && month == (leapMonth + 1)) leap = false;
        }
        // offset为0时，并且刚才计算的月份是闰月，要校正
        if (offset == 0 && leapMonth > 0 && month == leapMonth + 1) {
            if (leap) {
                leap = false;
            } else {
                leap = true;
                --month;
            }
        }
        // offset小于0时，也要校正
        if (offset < 0) {
            offset += daysOfMonth;
            --month;
        }
        return getChinaYearString(year) + "年" + (leap ? "闰" : "") + getChinaMonthString(month) + "月" + getChinaDayString(offset + 1);
    }

    /**
     * 计算天干纪年月日
     *
     * @param time
     * @return
     */
    public static String ganzhiYear(Date time) {
        // 获取日期
        Calendar ca = Calendar.getInstance();
        ca.setTime(time);
        int days = ca.get(Calendar.DAY_OF_MONTH);
        int month = ca.get(Calendar.MONTH);
        int year = ca.get(Calendar.YEAR);
        int last_year = year % 100;//获取年的后2位
        int first_year = (year - last_year) / 100;//获取年的前2位
        // 计算年的天干地支
        int indexTian = (year - 3) % 10;  //天
        int indexDi = (year - 3) % 12;    //地
        String yearOfTianDi = ganMap.get(indexTian) + zhiMap.get(indexDi); //得到年的天干地支
        // 计算日的天干地支
        // 日的干
        int g = (int) (4 * first_year + Math.floor(first_year / 4) + 5 * last_year + Math.floor(last_year / 4) + Math.floor(3 * (month + 1) / 5) + days - 3);
        g = g % 10;
        // 日的支
        int i = 0;
        if (month % 2 == 0) {
            i = 6;
        }
        int z = (int) (8 * first_year + Math.floor(first_year / 4) + 5 * last_year + Math.floor(last_year / 4) + Math.floor(3 * (month + 1) / 5) + days + 7 + i);
        z = z % 12;
        String c = ganMap.get(g) + zhiMap.get(z);    //得到日的天干地支
        // 计算月的天干地支
        String tianGanOfYear = ganMap.get(indexTian); //获取年的天干
        // 判断输入日期年的天干对应月地支的位置
        int indexOfmonth = indexOfmonthTable(tianGanOfYear);
        if (monthTable.containsKey(month)) {
            return yearOfTianDi + "年" + monthTable.get(month).get(indexOfmonth) + "月" + c + "日";
        }
        return null;
    }
}
