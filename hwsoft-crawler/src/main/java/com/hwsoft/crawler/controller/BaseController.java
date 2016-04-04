package com.hwsoft.crawler.controller;

import java.util.List;

public class BaseController {

  public static final String REQUEST_SCOPE = "prototype";

  protected Integer total = 0;

  protected List<?> list;

  public static int getBegin(Integer pageNum, Integer pageSize) {
    return null == pageNum ? 0 : getOffset(pageSize) * (pageNum - 1);
  }

  public static int getOffset(Integer pageSize) {
    return null == pageSize ? 10 : pageSize;
  }

}

	