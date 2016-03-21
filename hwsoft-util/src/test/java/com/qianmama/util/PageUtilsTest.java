package com.hwsoft.util;

import com.hwsoft.util.page.PageUtils;
import junit.framework.Assert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * 分页工具类的单元测试
 */
public class PageUtilsTest {

    @Test
    public void getPageStartIndexTest() {
        int index1 = PageUtils.getPageStartIndex(1, 10);
        assertEquals(0, index1);

        int index2 = PageUtils.getPageStartIndex(2, 10);
        assertEquals(10, index2);
    }

    @Test
    public void getPageStartIndexZeroTest() {
        int index = PageUtils.getPageStartIndex(0, 0);
        assertEquals(0, index);
    }
}
