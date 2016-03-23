package com.hwsoft.cms.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hwsoft.cms.common.DefaultDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;


public class BaseController {

    protected Integer total;

    protected String message;

    protected List<?> list;
    protected boolean result;
    private Map<String, Object> resultMap;

    public int getFrom(Integer page, int rows) {
        return null == page ? 0 : getPageSize(rows) * (page - 1);
    }

    public int getPageSize(Integer rows) {
        return null == rows ? 10 : rows;
    }

    public Map<String, Object> getResultMap() {
        if (null == resultMap) {
            resultMap = new HashMap<String, Object>();
        }
        resultMap.put("total", total);
        resultMap.put("rows", list);
        resultMap.put("result", result);
        resultMap.put("message", message);
        return resultMap;
    }

    public void addResultMap(String key, Object value) {
        if (null == resultMap) {
            resultMap = new HashMap<String, Object>();
        }
        resultMap.put(key, value);
    }
    
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new DefaultDateEditor());//true:允许输入空值，false:不能为空值
    }
}

	