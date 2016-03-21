/*
 * Copyright(c) 2010-2012 by RenRenDai.
 * All Rights Reserved
 */
package com.hwsoft.util.date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 日期工具类
 *
 * @author tangzhi
 */
public class DateTools {

  public static final String DATE_PATTERN_DEFAULT = "yyyy-MM-dd HH:mm:ss";
  public static final String DATE_PATTERN_DAY = "yyyy-MM-dd";
  public static final String DATE_PATTERN_MONTH = "yyyy-MM";
  public static final String DAY_LAST_TIME = " 23:59:59";
  public static final String DATE_PATTERN_DAY_NUM = "yyyyMMdd";
  public static final String DAY_FIRST_TIME = " 00:00:00";
  public static final String DATE_PATTERN_DAY_CHINNESS = "【yyyy】年【MM】月【dd】";
  public static final String DATE_PATTERN_DAY_CHINNESS_DEFAULT = "yyyy年MM月dd日";
  public static final String DATE_PATTERN_JUEST_DAY = "dd";
  public static final String DATE_PATTERN_NUMBER_DAY = "yyyyMMdd";
  public static final String DATE_PATTERN_STRING = "yyyyMMddHHmmss";
  private static final Log log = LogFactory.getLog(DateTools.class);

  /**
   * 获取上个月的天数
   *
   * @return 上个月的天数
   */
  public static int getMaxDayOfLastMonth() {
    Date now = new Date();
    Date lastMonth = DateUtils.addMonths(now, -1);
    lastMonth = getMaxDateOfMonth(lastMonth);
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(lastMonth);
    int maxDay = calendar.get(Calendar.DAY_OF_MONTH);
    return maxDay;
  }

  /**
   * 获取上个月所在的年
   *
   * @return 上个月所在的年
   */
  public static int getYearOfLastMonth() {
    Calendar calendar = Calendar.getInstance();
    calendar.add(Calendar.MONTH, -1);
    int yearOfLastMonth = calendar.get(Calendar.YEAR);
    return yearOfLastMonth;
  }

  /**
   * 获取上个月所在的月
   * <p/>
   * 注意：calendar.get(Calendar.MONTH) 返回的值是从0开始的，这里进行了加一操作
   *
   * @return 上个月所在的月
   */
  public static int getMonthOfLastMonth() {
    Calendar calendar = Calendar.getInstance();
    calendar.add(Calendar.MONTH, -1);
    int lastMonth = calendar.get(Calendar.MONTH) + 1;
    return lastMonth;
  }

  /**
   * 获取当前月对应的年
   *
   * @return 当前月对应的年
   */
  public static int getCurrentStateYear() {
    Calendar cal = Calendar.getInstance();
    int year = cal.get(Calendar.YEAR);
    return year;
  }

  /**
   * 当前月所在的月数
   * 注意：calendar.get(Calendar.MONTH) 返回的值是从0开始的，这里进行了加一操作，
   * 获取当前月份，cal.get(Calendar.MONTH)是从零开始。
   *
   * @return 当前月所在的月数
   */
  public static int getCurrentStateMonth() {
    Calendar cal = Calendar.getInstance();
    int month = cal.get(Calendar.MONTH) + 1;
    return month;
  }

  /**
   * 当前这一天在本月的天数
   *
   * @return 当前这一天在本月的天数
   */
  public static int getCurrentStateDay() {
    Calendar cal = Calendar.getInstance();
    int day = cal.get(Calendar.DAY_OF_MONTH);
    return day;
  }

  /**
   * 当前月所在的小时
   *
   * @return 当前月所在的小时
   */
  public static int getCurrentStateHour() {
    Calendar cal = Calendar.getInstance();
    int month = cal.get(Calendar.HOUR_OF_DAY);
    return month;
  }

  /**
   * 当前月所在的分钟
   *
   * @return 当前月所在的小时
   */
  public static int getCurrentStateMinute() {
    Calendar cal = Calendar.getInstance();
    int month = cal.get(Calendar.MINUTE);
    return month;
  }

  /**
   * 时间校验: 开始时间不能大于当前时间.取传入时间和当前时间中比较早的那个
   *
   * @param startDate 判断的日期
   * @return 用当前时间和传入时间比较，获取到比较早得那个时间
   */
  public static Date validateStartDate(Date startDate) {
    Date today = new Date();
    // 开始时间不能大于当前时间.
    if (startDate.compareTo(today) == 1) {
      log.warn("startDate.compareTo(today)==1, set startDate = today:" + today);
      startDate = today;
    }
    return startDate;
  }

  /**
   * 时间校验: 不能晚于当前时间(如果晚于当前时间，则替换为当前时间)
   */
  public static Date notAfterNow(Date myDate) {
    Date today = new Date();
    if (myDate.after(today)) {
      log.warn("myDate.after(today), set myDate = today:" + today);
      myDate = today;
    }
    return myDate;
  }

  /**
   * 时间校验: 不能晚于昨天(如果晚于昨天，则替换为昨天)
   */
  public static Date notAfterYesterday(Date myDate) {
    Date today = new Date();
    Date yesterday = DateUtils.addDays(today, -1);
    ;
    // 3. 结束时间不能大于昨天.
    if (myDate.after(yesterday)) {
      log.warn("myDate.after(yesterday), set myDate = yesterday:" + yesterday);
      myDate = yesterday;
    }
    return myDate;
  }

  /**
   * 时间校验: 不能晚于上一个月(如果晚于上一个月，则替换为上一个月)
   */
  public static Date notAfterLastMonth(Date myDate) {
    Date today = new Date();
    Date lastMonth = DateUtils.addMonths(today, -1);
    lastMonth = DateTools.getMaxDateOfMonth(lastMonth);
    // 3. 结束时间不能大于上一个月.
    if (myDate.after(lastMonth)) {
      log.warn("myDate.after(lastMonth), set myDate = lastMonth:" + lastMonth);
      myDate = lastMonth;
    }
    return myDate;
  }

  /**
   * 时间校验: 不能晚于上一年(如果晚于上一年，则替换为上一年)
   */
  public static Date notAfterLastYear(Date myDate) {
    Date today = new Date();
    Date lastYear = DateUtils.addYears(today, -1);
    lastYear = DateTools.getMaxDateOfYear(lastYear);
    // 3. 结束时间不能大于上一年.
    if (myDate.after(lastYear)) {
      log.warn("myDate.after(lastYear), set myDate = lastYear:" + lastYear);
      myDate = lastYear;
    }
    return myDate;
  }

  /**
   * 时间校验: myDate不能早于basicDate(如果早于basicDate，则替换为basicDate)
   *
   * @throws Exception
   */
  public static Date notBefore(Date myDate, String basicStr) throws Exception {
    Date basicDate = DateTools.stringToDateTime(basicStr);
    // Date today = new Date();
    // Date yesterday = DateUtils.addDays(today, -1);;
    // 3. 结束时间不能大于昨天.
    if (myDate.before(basicDate)) {
      log.warn("myDate.before(basicDate), set myDate = basicDate:" + basicDate);
      myDate = basicDate;
    }
    return myDate;
  }

  /**
   * 将日期转化为字符串。 字符串格式("yyyy-MM-dd HH:mm:ss")。
   */
  public static String dateTime2String(Date date) {
    return dateToString(date, DATE_PATTERN_DEFAULT);
  }

  /**
   * 将日期转化为字符串。 字符串格式("yyyy-MM-dd")，小时、分、秒被忽略。
   */
  public static String dateToString(Date date) {
    return dateToString(date, DATE_PATTERN_DAY);
  }

  /**
   * 将日期转化为字符串
   */
  public static String dateToString(Date date, String pattern) {
    String str = "";
    try {
      SimpleDateFormat formater = new SimpleDateFormat(pattern);
      str = formater.format(date);
    } catch (Throwable e) {
    	e.printStackTrace();
      log.error(e);
    }
    return str;
  }

  /**
   * 将传入的年月日转化为Date类型
   */
  public static Date YmdToDate(int year, int month, int day) {
    Calendar calendar = Calendar.getInstance();
    calendar.set(year, month, day);
    return calendar.getTime();
  }

  /**
   * 将字符串转化为日期
   */
  public static Date stringToDateTime(String str) throws Exception {
    if(null == str) {
      return null;
    }
    return getDateFormatOfDefault().parse(str);
  }

  public static Date stringToDateDay(String str) throws Exception {
    return stringToDate(str, DATE_PATTERN_DAY);
  }

  /**
   * 将字符串转化为日期
   */
  public static Date stringToMediumDateTime(String str) throws ParseException {
    DateFormat format = DateFormat.getDateInstance(DateFormat.MEDIUM);
    return format.parse(str);
  }

  /**
   * 获取默认的DateFormat
   */
  public static DateFormat getDateFormatOfDefault() {
    return new SimpleDateFormat(DATE_PATTERN_DEFAULT);
  }

  /**
   * 将字符串转化为日期。 字符串格式("YYYY-MM-DD")。
   * 例如："2012-07-01"或者"2012-7-1"或者"2012-7-01"或者"2012-07-1"是等价的。
   */
  public static Date stringToDate(String str, String pattern) {
    Date dateTime = null;
    try {
      SimpleDateFormat formater = new SimpleDateFormat(pattern);
      dateTime = formater.parse(str);
    } catch (Exception e) {
      e.printStackTrace();
      log.error(e);
    }
    return dateTime;
  }
 

  /**
   * 将字符串转化为日期(从一种格式到另一种格式)。
   */
  public static String StringPatternToPattern(String str, String pattern1, String pattern2) {
    Date dateTime = null;
    String productStr = "";
    try {
      if (!(str == null || str.equals(""))) {
        SimpleDateFormat formater = new SimpleDateFormat(pattern1);
        dateTime = formater.parse(str);

        SimpleDateFormat formater1 = new SimpleDateFormat(pattern2);
        productStr = formater1.format(dateTime);
      }
    } catch (Exception ex) {
      log.error(ex);
    }
    return productStr;
  }

  /**
   * 日期时间带时分秒的Timestamp表示
   */
  public static Timestamp stringToDateHMS(String str) {
    Timestamp time = null;
    try {
      time = java.sql.Timestamp.valueOf(str);
    } catch (Exception ex) {
      log.error(ex);
    }
    return time;

  }

  /**
   * 取得一个date对象对应的日期的0分0秒时刻的Date对象。
   */
  public static Date getMinDateOfHour(Date date) {
    if (date == null) {
      return null;
    }
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.set(Calendar.MINUTE, calendar.getActualMinimum(Calendar.MINUTE));
    calendar.set(Calendar.SECOND, calendar.getActualMinimum(Calendar.SECOND));
    calendar.set(Calendar.MILLISECOND, calendar.getActualMinimum(Calendar.MILLISECOND));
    return calendar.getTime();
  }

  /**
   * 取得一个date对象对应的日期的0点0分0秒时刻的Date对象。
   */
  public static Date getMinDateOfDay(Date date) {
    if (date == null) {
      return null;
    }
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(getMinDateOfHour(date));
    calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMinimum(Calendar.HOUR_OF_DAY));
    return calendar.getTime();
  }

  /**
   * 取得一年中的最早一天。
   */
  public static Date getMinDateOfYear(Date date) {
    if (date == null) {
      return null;
    }
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.set(Calendar.DAY_OF_YEAR, calendar.getActualMinimum(Calendar.DAY_OF_YEAR));
    calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMinimum(Calendar.HOUR_OF_DAY));
    calendar.set(Calendar.MINUTE, calendar.getActualMinimum(Calendar.MINUTE));
    calendar.set(Calendar.SECOND, calendar.getActualMinimum(Calendar.SECOND));
    calendar.set(Calendar.MILLISECOND, calendar.getActualMinimum(Calendar.MILLISECOND));

    return calendar.getTime();
  }

  /**
   * 取得一年中的最后一天
   */
  public static Date getMaxDateOfYear(Date date) {
    if (date == null) {
      return null;
    }
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.set(Calendar.DAY_OF_YEAR, calendar.getActualMaximum(Calendar.DAY_OF_YEAR));
    calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMaximum(Calendar.HOUR_OF_DAY));
    calendar.set(Calendar.MINUTE, calendar.getActualMaximum(Calendar.MINUTE));
    calendar.set(Calendar.SECOND, calendar.getActualMaximum(Calendar.SECOND));
    calendar.set(Calendar.MILLISECOND, calendar.getActualMaximum(Calendar.MILLISECOND));

    return calendar.getTime();
  }

  /**
   * 取得一周中的最早一天。
   */
  public static Date getMinDateOfWeek(Date date, Locale locale) {
    if (date == null)
      return null;

    Calendar calendar = Calendar.getInstance();
    int day_of_week = calendar.get(Calendar.DAY_OF_WEEK);

    calendar.setTime(date);
    calendar.set(Calendar.DAY_OF_WEEK, calendar.getActualMinimum(Calendar.DAY_OF_WEEK));
    calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMinimum(Calendar.HOUR_OF_DAY));
    calendar.set(Calendar.MINUTE, calendar.getActualMinimum(Calendar.MINUTE));
    calendar.set(Calendar.SECOND, calendar.getActualMinimum(Calendar.SECOND));
    calendar.set(Calendar.MILLISECOND, calendar.getActualMinimum(Calendar.MILLISECOND));

    if (locale == null)
      locale = Locale.CHINESE;
    Date tmpDate = calendar.getTime();
    if (Locale.CHINESE.getLanguage().equals(locale.getLanguage())) {
      if (day_of_week == 1) {// 星期天
        tmpDate = DateUtils.addDays(tmpDate, -6);
      } else {
        tmpDate = DateUtils.addDays(tmpDate, 1);
      }
    }

    return tmpDate;
  }

  /**
   * 取得一周中的最后一天
   */
  public static Date getMaxDateOfWeek(Date date, Locale locale) {
    if (date == null)
      return null;

    Calendar calendar = Calendar.getInstance();
    int day_of_week = calendar.get(Calendar.DAY_OF_WEEK);

    calendar.setTime(date);
    calendar.set(Calendar.DAY_OF_WEEK, calendar.getActualMaximum(Calendar.DAY_OF_WEEK));
    calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMaximum(Calendar.HOUR_OF_DAY));
    calendar.set(Calendar.MINUTE, calendar.getActualMaximum(Calendar.MINUTE));
    calendar.set(Calendar.SECOND, calendar.getActualMaximum(Calendar.SECOND));
    calendar.set(Calendar.MILLISECOND, calendar.getActualMaximum(Calendar.MILLISECOND));

    if (locale == null)
      locale = Locale.CHINESE;
    Date tmpDate = calendar.getTime();
    if (Locale.CHINESE.getLanguage().equals(locale.getLanguage())) {
      if (day_of_week == 1) {// 星期天
        tmpDate = DateUtils.addDays(tmpDate, -6);
      } else {
        tmpDate = DateUtils.addDays(tmpDate, 1);
      }
    }

    return tmpDate;
  }

  /**
   * 取得一月中的最早一天。
   */
  public static Date getMinDateOfMonth(Date date) {
    if (date == null) {
      return null;
    }
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
    calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMinimum(Calendar.HOUR_OF_DAY));
    calendar.set(Calendar.MINUTE, calendar.getActualMinimum(Calendar.MINUTE));
    calendar.set(Calendar.SECOND, calendar.getActualMinimum(Calendar.SECOND));
    calendar.set(Calendar.MILLISECOND, calendar.getActualMinimum(Calendar.MILLISECOND));

    return calendar.getTime();
  }

  /**
   * 取得一月中的最后一天
   */
  public static Date getMaxDateOfMonth(Date date) {
    if (date == null) {
      return null;
    }
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
    calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMaximum(Calendar.HOUR_OF_DAY));
    calendar.set(Calendar.MINUTE, calendar.getActualMaximum(Calendar.MINUTE));
    calendar.set(Calendar.SECOND, calendar.getActualMaximum(Calendar.SECOND));
    calendar.set(Calendar.MILLISECOND, calendar.getActualMaximum(Calendar.MILLISECOND));

    return calendar.getTime();
  }

  /**
   * 取得一个date对象对应的日期的23点59分59秒时刻的Date对象。
   */
  public static Date getMaxDateOfDay(Date date) {
    if (date == null) {
      return null;
    }
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMaximum(Calendar.HOUR_OF_DAY));
    calendar.set(Calendar.MINUTE, calendar.getActualMaximum(Calendar.MINUTE));
    calendar.set(Calendar.SECOND, calendar.getActualMaximum(Calendar.SECOND));
    calendar.set(Calendar.MILLISECOND, calendar.getActualMaximum(Calendar.MILLISECOND));

    return calendar.getTime();
  }

  /**
   * 取得一个date对象对应的小时的59分59秒时刻的Date对象。
   */
  public static Date getMaxDateOfHour(Date date) {
    if (date == null) {
      return null;
    }
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.set(Calendar.MINUTE, calendar.getActualMaximum(Calendar.MINUTE));
    calendar.set(Calendar.SECOND, calendar.getActualMaximum(Calendar.SECOND));
    calendar.set(Calendar.MILLISECOND, calendar.getActualMaximum(Calendar.MILLISECOND));

    return calendar.getTime();
  }

  /**
   * 获取2个时间相隔几秒
   */
  public static int getBetweenSecondNumber(Date startDate, Date endDate) {
    if (startDate == null || endDate == null)
      return -1;

    if (startDate.after(endDate)) {
      Date tmp = endDate;
      endDate = startDate;
      startDate = tmp;
    }

    long timeNumber = -1L;
    long TIME = 1000L;
    try {
      timeNumber = (endDate.getTime() - startDate.getTime()) / TIME;

    } catch (Exception e) {
      log.error(e);
    }
    return (int) timeNumber;
  }

  /**
   * 获取2个时间相隔几分钟
   */
  public static int getBetweenMinuteNumber(Date startDate, Date endDate) {
    if (startDate == null || endDate == null)
      return -1;

    if (startDate.after(endDate)) {
      Date tmp = endDate;
      endDate = startDate;
      startDate = tmp;
    }

    long timeNumber = -1l;
    long TIME = 60L * 1000L;
    try {
      timeNumber = (endDate.getTime() - startDate.getTime()) / TIME;

    } catch (Exception e) {
      e.printStackTrace();
    }
    return (int) timeNumber;
  }

  /**
   * 获取2个时间相隔几小时
   */
  public static int getBetweenHourNumber(Date startDate, Date endDate) {
    if (startDate == null || endDate == null)
      return -1;

    if (startDate.after(endDate)) {
      Date tmp = endDate;
      endDate = startDate;
      startDate = tmp;
    }

    long timeNumber = -1l;
    long TIME = 60L * 60L * 1000L;
    try {
      timeNumber = (endDate.getTime() - startDate.getTime()) / TIME;

    } catch (Exception e) {
      e.printStackTrace();
    }
    return (int) timeNumber;
  }

  /**
   * 获取2个时间相隔几天
   */
  public static int getBetweenDayNumber(Date startDate, Date endDate) {
    if (startDate == null || endDate == null)
      return -1;

    if (startDate.after(endDate)) {
      Date tmp = endDate;
      endDate = startDate;
      startDate = tmp;
    }

    long dayNumber = -1L;
    long DAY = 24L * 60L * 60L * 1000L;
    try {
      // "2010-08-01 00:00:00 --- 2010-08-03 23:59:59"算三天
      dayNumber = (endDate.getTime() + 1000 - startDate.getTime()) / DAY;

    } catch (Exception e) {
      log.error(e);
    }
    return (int) dayNumber;
  }
  
  /**
   * 获取2个时间相隔几天(不足一天按一天算)
   */
  public static int getBetweenMaxDayNumber(Date startDate, Date endDate) {
    if (startDate == null || endDate == null)
      return -1;

    if (startDate.after(endDate)) {
      Date tmp = endDate;
      endDate = startDate;
      startDate = tmp;
    }

    long dayNumber = -1L;
    long DAY = 24L * 60L * 60L * 1000L;
    
    
    try {
    	
    	if(isTheSameDay(startDate, endDate)){
    		return 0 ;
    	}
      // "2010-08-01 00:00:00 --- 2010-08-03 23:59:59"算三天
      dayNumber = (endDate.getTime() + 1000 - startDate.getTime()) / DAY;
      if(((endDate.getTime() + 1000 - startDate.getTime()) % DAY > 0) ){
    	  dayNumber = dayNumber+1;
      }
    } catch (Exception e) {
      log.error(e);
    }
    return (int) dayNumber;
  }

  /**
   * 获取2个时间相隔几月
   */
  public static int getBetweenMonthNumber(Date startDate, Date endDate) {
    int result = 0;
    try {
      if (startDate == null || endDate == null)
        return -1;

      // swap start and end date
      if (startDate.after(endDate)) {
        Date tmp = endDate;
        endDate = startDate;
        startDate = tmp;
      }

      Calendar calendar = Calendar.getInstance();
      calendar.setTime(startDate);

      int monthS = calendar.get(Calendar.MONTH);
      int yearS = calendar.get(Calendar.YEAR);

      calendar.setTime(endDate);
      int monthE = calendar.get(Calendar.MONTH);
      int yearE = calendar.get(Calendar.YEAR);

      if (yearE - yearS == 0) {
        result = monthE - monthS;
      } else {
        result = (yearE - yearS - 1) * 12 + (12 - monthS) + monthE;
      }

    } catch (Exception e) {
      log.error(e);
    }
    return result;
  }

  /**
   * 获取2个时间相隔几年
   */
  public static int getBetweenYearNumber(Date startDate, Date endDate) {
    int result = 0;
    try {
      if (startDate == null || endDate == null)
        return -1;

      // swap start and end date
      if (startDate.after(endDate)) {
        Date tmp = endDate;
        endDate = startDate;
        startDate = tmp;
      }

      Calendar calendar = Calendar.getInstance();
      calendar.setTime(startDate);
      int yearS = calendar.get(Calendar.YEAR);

      calendar.setTime(endDate);
      int yearE = calendar.get(Calendar.YEAR);

      result = yearE - yearS;

    } catch (Exception e) {
      log.error(e);
    }
    return result;
  }

  /**
   * 按天拆分时间
   */
  public static List<Date> splitDateByDay(Date startDate, Date endDate) {
    if (startDate == null || endDate == null)
      return null;

    List<Date> dateList = new ArrayList<Date>();
    dateList.add(startDate);

    int num = getBetweenDayNumber(startDate, endDate);
    for (int i = 1; i <= num; i++) {
      dateList.add(DateUtils.addDays(startDate, i));
    }

    return dateList;
  }

  /**
   * 按月拆分时间
   */
  public static List<Date> splitDateByMonth(Date startDate, Date endDate) {
    List<Date> dateList = new ArrayList<Date>();

    if (startDate == null || endDate == null) {
      return dateList;
    }

    dateList.add(startDate);
    int num = getBetweenMonthNumber(startDate, endDate);
    for (int i = 1; i <= num; i++) {
      dateList.add(DateUtils.addMonths(startDate, i));
    }

    return dateList;
  }

  /**
   * 按年拆分时间
   */
  public static List<Date> splitDateByYear(Date startDate, Date endDate) {
    List<Date> dateList = new ArrayList<Date>();

    if (startDate == null || endDate == null) {
      return dateList;
    }

    dateList.add(startDate);
    int num = getBetweenYearNumber(startDate, endDate);
    for (int i = 1; i <= num; i++) {
      dateList.add(DateUtils.addYears(startDate, i));
    }

    return dateList;
  }

  /**
   * 本季度
   */
  public static List<Date> getCurrentQuarter() {
    List<Date> dateList = new ArrayList<Date>();
    Date date = new Date(System.currentTimeMillis());
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    int month = calendar.get(Calendar.MONTH);// 一月为0

    dateList.add(1, calendar.getTime());// 结束时间设置为当前时间

    if (month >= 0 && month <= 2) {// 第一季度
      calendar.set(Calendar.MONTH, 0);
    } else if (month >= 3 && month <= 5) {// 第二季度
      calendar.set(Calendar.MONTH, 3);
    } else if (month >= 6 && month <= 8) {// 第三季度
      calendar.set(Calendar.MONTH, 6);
    } else {// 第四季度
      calendar.set(Calendar.MONTH, 9);
    }

    calendar.set(Calendar.DAY_OF_MONTH, 1);
    calendar.set(Calendar.HOUR_OF_DAY, 0);
    calendar.set(Calendar.MINUTE, 0);
    calendar.set(Calendar.SECOND, 0);
    dateList.add(0, calendar.getTime());

    return dateList;
  }

  /**
   * 上季度
   */
  public static List<Date> getLastQuarter() {
    List<Date> dateList = new ArrayList<Date>();
    Date date = new Date(System.currentTimeMillis());
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    int month = calendar.get(Calendar.MONTH);// 一月为0

    // 如果是第一季度则返回去年的第四季度
    if (month >= 0 && month <= 2) {// 当前第一季度
      calendar.add(Calendar.YEAR, -1);// 退到去年
      calendar.set(Calendar.MONTH, 9);// 去年十月
    } else if (month >= 3 && month <= 5) {// 当前第二季度
      calendar.set(Calendar.MONTH, 0);
    } else if (month >= 6 && month <= 8) {// 当前第三季度
      calendar.set(Calendar.MONTH, 3);
    } else {// 当前第四季度
      calendar.set(Calendar.MONTH, 6);
    }
    calendar.set(Calendar.DAY_OF_MONTH, 1);
    calendar.set(Calendar.HOUR_OF_DAY, 0);
    calendar.set(Calendar.MINUTE, 0);
    calendar.set(Calendar.SECOND, 0);

    dateList.add(0, calendar.getTime());

    calendar.add(Calendar.MONTH, 3);// 加3个月，到下个季度的第一天
    calendar.add(Calendar.DAY_OF_MONTH, -1);// 退一天，得到上季度的最后一天
    calendar.set(Calendar.HOUR_OF_DAY, 23);
    calendar.set(Calendar.MINUTE, 59);
    calendar.set(Calendar.SECOND, 59);

    dateList.add(1, calendar.getTime());

    return dateList;
  }

  /**
   * 返回2个日期中的大者
   */
  public static Date max(Date date1, Date date2) {
    if (date1 == null && date2 == null) {
      return null;
    }
    if (date1 == null) {
      return date2;
    }
    if (date2 == null) {
      return date1;
    }
    if (date1.after(date2)) {
      return date1;
    } else {
      return date2;
    }
  }

  /**
   * 返回不大于date2的日期 如果 date1 >= date2 返回date2 如果 date1 < date2 返回date1
   */
  public static Date ceil(Date date1, Date date2) {
    if (date1 == null && date2 == null) {
      return null;
    }
    if (date1 == null) {
      return date2;
    }
    if (date2 == null) {
      return date1;
    }
    if (date1.after(date2)) {
      return date2;
    } else {
      return date1;
    }
  }

  /**
   * 返回不小于date2的日期 如果 date1 >= date2 返回date1 如果 date1 < date2 返回date2
   */
  public static Date floor(Date date1, Date date2) {
    if (date1 == null && date2 == null) {
      return null;
    }
    if (date1 == null) {
      return date2;
    }
    if (date2 == null) {
      return date1;
    }
    if (date1.after(date2)) {
      return date1;
    } else {
      return date2;
    }
  }

  /**
   * 返回2个日期中的小者
   */
  public static Date min(Date date1, Date date2) {
    if (date1 == null && date2 == null) {
      return null;
    }
    if (date1 == null) {
      return date2;
    }
    if (date2 == null) {
      return date1;
    }
    if (date1.after(date2)) {
      return date2;
    } else {
      return date1;
    }
  }

  /**
   * 判断输入日期是否是一天中的最大时刻
   */
  public static boolean isMaxDayOfDay(Date date1, String precision) {
    if (date1 == null)
      return false;
    Date date2 = getMaxDateOfDay(date1);
    int diffNum = 0;
    if ("HH".equals(precision)) {
      diffNum = getBetweenHourNumber(date1, date2);
    } else if ("mm".equals(precision)) {
      diffNum = getBetweenMinuteNumber(date1, date2);
    } else {
      diffNum = getBetweenSecondNumber(date1, date2);
    }
    return diffNum == 0;
  }

  /**
   * 判断输入日期是否是一天中的最小时刻
   */
  public static boolean isMinDayOfDay(Date date1, String precision) {
    if (date1 == null)
      return false;
    Date date2 = getMinDateOfDay(date1);
    int diffNum = 0;
    if ("HH".equals(precision)) {
      diffNum = getBetweenHourNumber(date1, date2);
    } else if ("mm".equals(precision)) {
      diffNum = getBetweenMinuteNumber(date1, date2);
    } else {
      diffNum = getBetweenSecondNumber(date1, date2);
    }
    return diffNum == 0;
  }

  /**
   * 判断输入日期是否是一天中的最大时刻
   */
  public static boolean isMaxDayOfDay(Date date1) {
    if (date1 == null)
      return false;
    Date date2 = getMaxDateOfDay(date1);
    int secondNum = getBetweenSecondNumber(date1, date2);
    return secondNum == 0;
  }

  /**
   * 判断输入日期是否是一天中的最小时刻
   */
  public static boolean isMinDayOfDay(Date date1) {
    if (date1 == null)
      return false;
    Date date2 = getMinDateOfDay(date1);
    int secondNum = getBetweenSecondNumber(date1, date2);
    return secondNum == 0;
  }

  /**
   * 判断输入日期是否是一月中的最大时刻
   */
  public static boolean isMaxDayOfMonth(Date date1) {
    if (date1 == null)
      return false;
    Date date2 = getMaxDateOfMonth(date1);
    int secondNum = getBetweenSecondNumber(date1, date2);
    return secondNum == 0;
  }

  /**
   * 判断输入日期是否是一月中的最小时刻
   */
  public static boolean isMinDayOfMonth(Date date1) {
    if (date1 == null)
      return false;
    Date date2 = getMinDateOfMonth(date1);
    int secondNum = getBetweenSecondNumber(date1, date2);
    return secondNum == 0;
  }

  /**
   * 输入日期是否为同一天.
   */
  public static boolean isTheSameDay(Date startDate, Date endDate) {
    String startDateStr = dateToString(startDate);
    String endDateStr = dateToString(endDate);
    return startDateStr.equals(endDateStr);
  }

  /**
   * 功能：获取昨天最大时间。 输入: 2010-01-31 00:00:00 返回：2010-01-30 23:59:59
   */
  public static Date getLastMaxDay(Date startDate) {
    if (startDate == null) {
      return null;
    }
    startDate = DateUtils.addDays(startDate, -1);
    return DateTools.getMaxDateOfDay(startDate);
  }

  /**
   * 根据字符串时间,返回Calendar
   */
  public static Calendar getCalendar(String datetimeStr) {
    Calendar cal = Calendar.getInstance();
    if (StringUtils.isNotBlank(datetimeStr)) {
      Date date = DateTools.stringToDate(datetimeStr, DATE_PATTERN_DEFAULT);
      cal.setTime(date);
    }
    return cal;
  }

  /**
   * startStr 或者 startStr-endStr
   */
  public static String getDifferentStr(String startStr, String endStr) {
    String dateRangeStr = "";
    if (startStr.equals(endStr)) {
      dateRangeStr = startStr;
    } else {
      dateRangeStr = startStr + "-" + endStr;
    }
    return dateRangeStr;
  }

  /**
   * 给定一个日期和天数，得到这个日期+天数的日期
   *
   * @param date 指定日期
   * @param num  天数
   * @return
   * @author tangzhi, 2012-11-28
   */
  public static String getNextDay(String date, int num) {
    Date d = stringToDate(date, DATE_PATTERN_DAY);
    Calendar ca = Calendar.getInstance();
    ca.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
    ca.setTime(d);

    int day = ca.get(Calendar.DATE);
    day = day + num;
    ca.set(Calendar.DATE, day);
    return getFormatDateTime(ca.getTime(), DATE_PATTERN_DAY);

  }
  
  /**
   * 给定一个日期和天数，得到这个日期+天数的日期
   *
   * @param date 指定日期
   * @param num  天数
   * @return
   * @author tangzhi, 2012-11-28
   */
  public static String getNextDayTime(String date, int num) {
    Date d = stringToDate(date, DATE_PATTERN_DEFAULT);
    Calendar ca = Calendar.getInstance();
    ca.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
    ca.setTime(d);

    int day = ca.get(Calendar.DATE);
    day = day + num;
    ca.set(Calendar.DATE, day);
    return getFormatDateTime(ca.getTime(), DATE_PATTERN_DEFAULT);

  }

  /**
   * 根据指定格式获取日期数据
   *
   * @param date    ：指定日期
   * @param pattern ：日期格式
   * @return
   * @author tangzhi, 2012-11-28
   */
  private static String getFormatDateTime(Date date, String pattern) {
    if (null == date) {
      return "";
    }
    SimpleDateFormat format = new SimpleDateFormat(pattern);
    format.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
    return format.format(date);
  }

  /**
   * 获取给定日期的下一个月的日期的最晚时间
   *
   * @param startDate
   * @return
   */
  public static Date getNextMonthDay(Date startDate) {
    // 是不是
    // int month = startDate.getMonth();
    Date monthEndDate = getMaxDateOfMonth(startDate);
    Date nextMonth = DateUtils.addMonths(startDate, 1);
    nextMonth = stringToDate(dateToString(nextMonth, DATE_PATTERN_DAY) + DAY_LAST_TIME, DATE_PATTERN_DEFAULT);
    if (isTheSameDay(startDate, monthEndDate)) {
      nextMonth = getMaxDateOfMonth(nextMonth);
    }
    return nextMonth;
  }
  
  /**
   * 获取给定日期的下num个月的日期
   *
   * @param startDate
   * @return
   */
  public static Date getNextMonthtime(Date startDate,int num) {
	  Date monthEndDate = getMaxDateOfMonth(startDate);
	  Date nextMonth = DateUtils.addMonths(startDate, num);
	  nextMonth = stringToDate(dateToString(nextMonth, DATE_PATTERN_DEFAULT) , DATE_PATTERN_DEFAULT);
	  if (isTheSameDay(startDate, monthEndDate)) {
		  nextMonth = getMaxDateOfMonth(nextMonth);
	  }
	  return nextMonth;
  }
  
  /**
   * 给定一个日期和天数，得到这个日期+天数的日期
   *
   * @param date 指定日期
   * @param num  天数
   * @return
   * @author tangzhi, 
   */
  public static Date getNextDay(Date startDate, int num) {
	  
//	  Date monthEndDate = getMaxDateOfMonth(startDate);
//	  System.out.println(DateTools.dateTime2String(monthEndDate)+"---"+num);
	  Date nextDay = DateUtils.addDays(startDate, num);
//	  System.out.println(DateTools.dateTime2String(nextDay));
//	  nextDay = stringToDate(dateToString(nextDay, DATE_PATTERN_DEFAULT) , DATE_PATTERN_DEFAULT);
//	  System.out.println(DateTools.dateTime2String(nextDay));
//	  if (isTheSameDay(startDate, monthEndDate)) {
//		  nextDay = getMaxDateOfMonth(nextDay);
//	  }
	  return nextDay;
  }
  
  public static void main(String[] args) {
	    try {
	    	System.out.println(DateTools.getNextDay(DateTools.stringToDateTime("2015-05-31 00:00:00"), 62));
//			Date lastRepayDate = null;
//			try {
//				lastRepayDate = DateTools.stringToDateTime("2015-05-30 01:59:59");
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//	    		
//	    	System.out.println(DateTools.getBetweenMaxDayNumber(new Date(), lastRepayDate) );
//	    	Date now = new Date();
//	    	String yestoday = DateTools.dateToString(now,DateTools.DATE_PATTERN_DEFAULT);
//	    	System.out.println(yestoday);
//	    	String string = DateTools.getNextDayTime(
//					yestoday, -1);
//	    	System.out.println(string);
//	        //TODO 有其他收益方式的时候需要进行判断处理
////	    		if(product.getProductIncomeType().equals(ProductIncomeType.DAILY_INTEREST)){
//	        Date date = DateTools.stringToDate(
//	        		string, DateTools.DATE_PATTERN_DEFAULT);
//
////	      System.out.println(stringToDate("2014-01-09", ));
////	        	System.out.println(getCurrentStateHour());
////	        	System.out.println(getCurrentStateMinute());
////	            System.out.println(new Date().getTime());
//	//
////	            System.out.println(dateToString(getFirstMonthFirstDay(new Date()), DATE_PATTERN_DEFAULT));
	    } catch (Throwable e) {
	      System.out.println(e);
	    }
	  }

  
  /**
   * 给定一个日期和年份，得到这个日期+年的日期
   *
   * @param date 指定日期
   * @param num  年
   * @return
   * @author tangzhi, 
   */
  public static Date getNextYear(Date startDate, int num) {
	  Date monthEndDate = getMaxDateOfMonth(startDate);
	  Date nextDay = DateUtils.addYears(startDate, num);
	  nextDay = stringToDate(dateToString(nextDay, DATE_PATTERN_DEFAULT) , DATE_PATTERN_DEFAULT);
	  if (isTheSameDay(startDate, monthEndDate)) {
		  nextDay = getMaxDateOfMonth(nextDay);
	  }
	  return nextDay;
  }

  /**
   * 获取给定日期的上一个月的最晚时间
   *
   * @param startDate
   * @return
   */
  public static Date getLastMonthDay(Date startDate) {
    // 是不是
    // int month = startDate.getMonth();
    Date monthEndDate = getMaxDateOfMonth(startDate);
    Date nextMonth = DateUtils.addMonths(startDate, -1);
    nextMonth = stringToDate(dateToString(nextMonth, DATE_PATTERN_DAY) + DAY_LAST_TIME, DATE_PATTERN_DEFAULT);
    if (isTheSameDay(startDate, monthEndDate)) {
      nextMonth = getMaxDateOfMonth(nextMonth);
    }
    return nextMonth;
  }

  /**
   * 获取给定日期的上个月的日期的最后一天
   *
   * @param startDate
   * @return
   */
  public static Date getLastMonthLastDay(Date startDate) {


    return getMaxDateOfMonth(getLastMonthDay(startDate));
  }

  /**
   * 获取给定日期的上一个月的日期的最早时间
   *
   * @param startDate
   * @return
   */
  public static Date getFristMonthDay(Date startDate) {
    // 是不是
    // int month = startDate.getMonth();
    Date monthEndDate = getMaxDateOfMonth(startDate);
    Date nextMonth = DateUtils.addMonths(startDate, -1);
    nextMonth = stringToDate(dateToString(nextMonth, DATE_PATTERN_DAY) + DAY_FIRST_TIME, DATE_PATTERN_DEFAULT);
    if (isTheSameDay(startDate, monthEndDate)) {
      nextMonth = getMaxDateOfMonth(nextMonth);
    }
    return nextMonth;
  }

  /**
   * 获取给定日期的上个月的最早
   *
   * @param startDate
   * @return
   */
  public static Date getFirstMonthFirstDay(Date startDate) {

    return getMinDateOfMonth(getFristMonthDay(startDate));
  }

  
  public static Date dateAddSeconds(Date date, Integer seconds) {
    Long dateStamp = date.getTime() + seconds * 1000;
    Date dateAdded = new Date(dateStamp);
    return dateAdded;
  }
}
