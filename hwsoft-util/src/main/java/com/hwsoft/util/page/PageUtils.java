package com.hwsoft.util.page;

/**
 * 分页工具类
 *
 * @author tzh
 */
public class PageUtils {

    /**
     * 计算分页信息---开始索引号
     */
    public static int getPageStartIndex(int page, int pageSize) {
        int index = (page - 1) * pageSize;
        return index < 0 ? 0 : index;
    }
}
