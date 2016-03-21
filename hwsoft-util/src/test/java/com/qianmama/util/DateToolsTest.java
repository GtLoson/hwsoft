package com.hwsoft.util;

import com.hwsoft.util.date.DateTools;
import org.junit.Assert;
import org.junit.Test;

import java.util.Date;

/**
 * 日期工具类单元测试
 */
public class DateToolsTest {

    @Test
    public void getMaxDayOfMonthTest() {
        int days = DateTools.getMaxDayOfLastMonth();
        Assert.assertEquals(31, days);
    }

    @Test
    public void getYearOfLastMonthTest() {
        int year = DateTools.getYearOfLastMonth();
        Assert.assertEquals(2014, year);
    }

    @Test
    public void getMonthOfLastMonthTest() {
        int month = DateTools.getMonthOfLastMonth();
        Assert.assertEquals(10, month);
    }

    @Test
    public void getCurrentStateYearTest() {
        int year = DateTools.getCurrentStateYear();
        Assert.assertEquals("当前时间的年已经不是2014或者出错了", 2014, year);
    }

    @Test
    public void getCurrentStateMonthTest() {
        int month = DateTools.getCurrentStateMonth();
        Assert.assertEquals("当前时间的年已经不是11或者出错了", 11, month);
    }

    @Test
    public void getCurrentStateDayTest() {
        int day = DateTools.getCurrentStateDay();
        Assert.assertEquals("当前时间的年已经不是4或者出错了", 4, day);
    }

    @Test
    public void validateStartDateTest() {
        // Date date = DateTools.validateStartDate();
        // TODO 用户时间计算方法测试
    }


}
