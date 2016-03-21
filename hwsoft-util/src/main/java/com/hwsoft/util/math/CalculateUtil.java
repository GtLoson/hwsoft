/**
 * Copyright(c) 2010-2013 by XiangShang Inc.
 * All Rights Reserved
 */
package com.hwsoft.util.math;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * 数字工具类
 *
 * @author tangzhi
 */
public class CalculateUtil {

    /**
     * double 相加
     *
     * @param dd
     * @return
     */
    public static double doubleAdd(double... dd) {
        BigDecimal result = BigDecimal.ZERO;
        for (double n : dd) {
            result = result.add(new BigDecimal(Double.toString(n)));
        }
        return result.doubleValue();
    }

    /**
     * double 相加 HALF_EVEN
     *
     * @param scale
     * @param dd
     * @return
     */
    public static double doubleAddWithScale(int scale, double... dd) {
        BigDecimal result = BigDecimal.ZERO;
        for (double n : dd) {
            result = result.add(new BigDecimal(Double.toString(n)));
        }
        return result.setScale(scale, RoundingMode.HALF_EVEN).doubleValue();
    }

    /**
     * double 相加
     *
     * @param scale
     * @param mode
     * @param dd
     * @return
     */
    public static double doubleAdd(int scale, RoundingMode mode, double... dd) {
        BigDecimal result = BigDecimal.ZERO;
        for (double n : dd) {
            result = result.add(new BigDecimal(Double.toString(n)));
        }
        return result.setScale(scale, mode).doubleValue();
    }

    /**
     * double相加
     *
     * @param d1
     * @param d2
     * @return
     */
    public static double doubleAdd(double d1, double d2) {
        BigDecimal bd1 = new BigDecimal(Double.toString(d1));
        BigDecimal bd2 = new BigDecimal(Double.toString(d2));
        return bd1.add(bd2).doubleValue();

    }

    /**
     * 相加 取位 d1+d2 n.scale HALF_EVEN
     *
     * @param d1
     * @param d2
     * @param scale
     * @return
     */
    public static double doubleAdd(double d1, double d2, int scale) {
        BigDecimal bd1 = new BigDecimal(Double.toString(d1));
        BigDecimal bd2 = new BigDecimal(Double.toString(d2));
        return bd1.add(bd2).setScale(scale, RoundingMode.HALF_EVEN).doubleValue();

    }

    /**
     * 相加 取位 d1+d2 n.scale
     *
     * @param d1
     * @param d2
     * @param scale
     * @param mode
     * @return
     */
    public static double doubleAdd(double d1, double d2, int scale, RoundingMode mode) {
        BigDecimal bd1 = new BigDecimal(Double.toString(d1));
        BigDecimal bd2 = new BigDecimal(Double.toString(d2));
        return bd1.add(bd2).setScale(scale, mode).doubleValue();

    }

    /**
     * double 相减
     *
     * @param d1
     * @param d2
     * @return
     */
    public static double doubleSubtract(double d1, double d2) {
        return new BigDecimal(Double.toString(d1)).subtract(new BigDecimal(Double.toString(d2))).doubleValue();
    }

    /**
     * double 相减
     *
     * @param d1
     * @param d2
     * @param scale
     * @return
     */
    public static double doubleSubtract(double d1, double d2, int scale) {
        BigDecimal b = new BigDecimal(Double.toString(d1)).subtract(new BigDecimal(Double.toString(d2)));
        return b.setScale(scale, RoundingMode.HALF_EVEN).doubleValue();
    }

    /**
     * double 相减
     *
     * @param d1
     * @param d2
     * @param scale
     * @param mode
     * @return
     */
    public static double doubleSubtract(double d1, double d2, int scale, RoundingMode mode) {
        BigDecimal b = new BigDecimal(Double.toString(d1)).subtract(new BigDecimal(Double.toString(d2)));
        return b.setScale(scale, mode).doubleValue();
    }

    /**
     * 格式化数字
     *
     * @param d1
     * @param scale
     * @return
     */
    public static double formatNubmer(double d1, int scale) {
        BigDecimal bd = new BigDecimal(Double.toString(d1));
        return bd.setScale(scale, RoundingMode.HALF_EVEN).doubleValue();
    }

    /**
     * 格式化数字 含取舍条件
     *
     * @param d
     * @param mode
     * @param d1
     * @return
     */
    public static double formatNubmer(double d1, int scale, RoundingMode mode) {
        BigDecimal bd = new BigDecimal(Double.toString(d1));
        return bd.setScale(scale, mode).doubleValue();
    }

    /**
     * 格式化数字
     *
     * @param d1
     * @param scale
     * @param formatter
     * @return
     */
    public static String formatNubmer(double d1, int scale, String formatter) {
        BigDecimal bd = new BigDecimal(Double.toString(d1));
        double dd = bd.setScale(scale, RoundingMode.HALF_EVEN).doubleValue();
        DecimalFormat df = new DecimalFormat(formatter);
        return df.format(dd);
    }

    /**
     * 格式化数字
     *
     * @param d1
     * @param scale
     * @param mode
     * @param formatter
     * @return
     */
    public static String formatNubmer(double d1, int scale, RoundingMode mode, String formatter) {
        BigDecimal bd = new BigDecimal(Double.toString(d1));
        double dd = bd.setScale(scale, mode).doubleValue();
        DecimalFormat df = new DecimalFormat(formatter);
        return df.format(dd);
    }

    /**
     * 格式字符串
     *
     * @param ####,###0.00
     * @param value
     */
    public static String formatNumberWithString(String formatter, String value) {
        BigDecimal bd = new BigDecimal(value);
        DecimalFormat df = new DecimalFormat(formatter);
        return df.format(bd);
    }

    /**
     * 相除
     *
     * @param d1
     * @param d2
     * @return
     */
    public static double doubleDivide(double d1, double d2) {
        BigDecimal bd1 = new BigDecimal(Double.toString(d1));
        BigDecimal bd2 = new BigDecimal(Double.toString(d2));
        return bd1.divide(bd2).doubleValue();
    }

    /**
     * 相除 取位 d1/d2 n.scale HALF_EVEN
     *
     * @param d1
     * @param d2
     * @param scale
     * @return
     */
    public static double doubleDivide(double d1, double d2, int scale) {
        BigDecimal bd1 = new BigDecimal(Double.toString(d1));
        BigDecimal bd2 = new BigDecimal(Double.toString(d2));
        BigDecimal bd3 = bd1.divide(bd2, 15, RoundingMode.HALF_EVEN);
        return bd3.setScale(scale, RoundingMode.HALF_EVEN).doubleValue();
    }

    /**
     * 相除 取位 d1/d2 n.scale
     *
     * @param d1
     * @param d2
     * @param scale
     * @param mode
     * @return
     */
    public static double doubleDivide(double d1, double d2, int scale, RoundingMode mode) {
        BigDecimal bd1 = new BigDecimal(Double.toString(d1));
        BigDecimal bd2 = new BigDecimal(Double.toString(d2));
        BigDecimal bd3 = bd1.divide(bd2, 15, mode);
        return bd3.setScale(scale, mode).doubleValue();
    }

    /**
     * 相乘
     *
     * @param d1
     * @param d2
     * @return
     */
    public static double doubleMultiply(double d1, double d2) {
        BigDecimal bd1 = new BigDecimal(Double.toString(d1));
        BigDecimal bd2 = new BigDecimal(Double.toString(d2));
        return bd1.multiply(bd2).doubleValue();
    }

    /**
     * 相乘 取位 d1*d2 n.scale HALF_EVEN
     *
     * @param d1
     * @param d2
     * @param scale
     * @return
     */
    public static double doubleMultiply(double d1, double d2, int scale) {
        BigDecimal bd1 = new BigDecimal(Double.toString(d1));
        BigDecimal bd2 = new BigDecimal(Double.toString(d2));
        return bd1.multiply(bd2).setScale(scale, RoundingMode.HALF_EVEN).doubleValue();
    }

    /**
     * 相乘 取位 d1*d2 n.scale
     *
     * @param d1
     * @param d2
     * @param scale
     * @param mode
     * @return
     */
    public static double doubleMultiply(double d1, double d2, int scale, RoundingMode mode) {
        BigDecimal bd1 = new BigDecimal(Double.toString(d1));
        BigDecimal bd2 = new BigDecimal(Double.toString(d2));
        return bd1.multiply(bd2).setScale(scale, mode).doubleValue();
    }

    /**
     * 将整数转化为四位数字
     *
     * @param number
     * @return
     */
    public static String formatFourNumber(int number) {
        NumberFormat nf = new DecimalFormat("0000");
        return nf.format(number);
    }

    public static void main(String[] args) {
//        double b = 100000000;
//        double b2 = 3000;
//        System.out.println(doubleDivide(b2, b, 4));
//        System.out.println((int) b);


    }
}
