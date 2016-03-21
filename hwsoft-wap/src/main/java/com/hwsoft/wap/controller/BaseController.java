package com.hwsoft.wap.controller;

import java.util.List;


/**
 * 基础controller
 *
 * @author tzh
 */
public class BaseController {

  public static final String REQUEST_SCOPE = "prototype";

  protected Integer total = 0;

  protected List<?> list;

  public static int getFrom(Integer pageNum, Integer pageSize) {
    return null == pageNum ? 0 : getPageSize(pageSize) * (pageNum - 1);
  }

  public static int getPageSize(Integer pageSize) {
    return null == pageSize ? 10 : pageSize;
  }

}

	