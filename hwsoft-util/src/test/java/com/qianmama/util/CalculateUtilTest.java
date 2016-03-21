package com.hwsoft.util;

import com.hwsoft.util.math.CalculateUtil;
import org.junit.Assert;
import org.junit.Test;

/**
 * 计算工具类单元测试
 */
public class CalculateUtilTest {

    @Test
    public void DoubleDivide() {
        double b = 202000;
        double b2 = 20000;
        double result = CalculateUtil.doubleDivide(b, b2, 0);
        Assert.assertEquals(10.0, result, 0.01);
    }
}
