package com.hwsoft.util;

import com.hwsoft.util.date.DateTools;
import com.hwsoft.util.validate.IdentityValidate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * 身份证工具单元测试
 */
public class IdentityValidateTest {

    private static final String id18String = "370921198704043318";

    private static final String id15String = "110105197109235829";

    private static final String id18XString = "21010619670312529X";

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void test18IdentityCheckOK() {
        boolean result = IdentityValidate.isIdentityNumberValid(id18String);
        assertTrue(result);
    }

    @Test
    public void test18IdentityCheckWrong() {
        boolean result = IdentityValidate.isIdentityNumberValid("370921198704043319");
        assertTrue(!result);
    }

    @Test
    public void test15IdentityCheckOK() {
        boolean result = IdentityValidate.isIdentityNumberValid(id15String);
        assertTrue(result);
    }

    @Test
    public void test15IdentityCheckWrong() {
        boolean result = IdentityValidate.isIdentityNumberValid("110105197109235820");
        assertTrue(!result);
    }

    @Test
    public void test18XIdentityCheckOK() {
        boolean result = IdentityValidate.isIdentityNumberValid(id18XString);
        assertTrue(result);
    }

    @Test
    public void test18xIdentityCheckOK() {
        boolean result = IdentityValidate.isIdentityNumberValid("21010619670312529x");
        assertTrue(result);
    }

    @Test
    public void test18XIdentityCheckWrong() {
        boolean result = IdentityValidate.isIdentityNumberValid("21010619670312528X");
        assertTrue(!result);
    }

    @Test
    public void test18IdentityProperty() {
        IdentityValidate identityUtil = new IdentityValidate(id18String);
        String province = identityUtil.getProvince();
        int year = identityUtil.getYear();
        int month = identityUtil.getMonth();
        int day = identityUtil.getDay();
        String gender = identityUtil.getGender();
        Date birthday = identityUtil.getBirthday();
        assertEquals(1987, year);
        assertEquals(04, month);
        assertEquals(04, day);
        assertEquals("山东", province);
        assertEquals("男", gender);
        try {
            Date expectDate = DateTools.stringToDateDay("1987-04-04");
            assertEquals(expectDate, birthday);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test15IdentityProperty() {
        IdentityValidate identityUtil = new IdentityValidate(id15String);
        String province = identityUtil.getProvince();
        int year = identityUtil.getYear();
        int month = identityUtil.getMonth();
        int day = identityUtil.getDay();
        String gender = identityUtil.getGender();
        Date birthday = identityUtil.getBirthday();
        assertEquals(1971, year);
        assertEquals(9, month);
        assertEquals(23, day);
        assertEquals("北京", province);
        assertEquals("女", gender);
        try {
            Date expectDate = DateTools.stringToDateDay("1971-09-23");
            assertEquals(expectDate, birthday);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
