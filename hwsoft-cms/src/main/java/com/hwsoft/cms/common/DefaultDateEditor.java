/**
 * 
 */
package com.hwsoft.cms.common;

import java.beans.PropertyEditorSupport;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.common.base.Strings;
import org.springframework.expression.ParseException;


/**
 * @author tzh
 *
 */
public class DefaultDateEditor extends PropertyEditorSupport {
	private static final String DATE_PATTERN_MINUTE = "yyyy-MM-dd HH:mm";
	private static final String DATE_PATTERN_HOUR = "yyyy-MM-dd HH";
	private static final String DATE_PATTERN_DEFAULT = "yyyy-MM-dd HH:mm:ss";
	private static final String DATE_PATTERN_DAY = "yyyy-MM-dd";
    private boolean allowEmpty = true;  
  
    public DefaultDateEditor() {  
   
    }  
  
    /** 
     * Parse the Date from the given text, using the specified DateFormat. 
     */  
    @Override  
    public void setAsText(String text) throws IllegalArgumentException {  
        if (this.allowEmpty && Strings.isNullOrEmpty(text)) {
            setValue(null);  
        } else {  
            try {  
            	Date date = null;
            	boolean flag = false;
            	try {
					date = stringToDate(text, DATE_PATTERN_DEFAULT);
					flag = true;
				} catch (Exception e) {
				}
				if(!flag){
					try {
						date = stringToDate(text, DATE_PATTERN_DAY);
						flag = true;
					} catch (Exception e) {
					}
				}
				if(!flag){
					try {
						date = stringToDate(text, DATE_PATTERN_MINUTE);
						flag = true;
					} catch (Exception e) {
					}
				}
				if(!flag){
					try {
						date = stringToDate(text, DATE_PATTERN_HOUR);
						flag = true;
					} catch (Exception e) {
					}
				}
				
            	setValue(date);
            } catch (ParseException ex) {  
                throw new IllegalArgumentException("Could not parse date: " + ex.getMessage(), ex);  
            }  
        }  
    }  
  
    /** 
     * Format the Date as String, using the specified DateFormat. 
     */  
    @Override  
    public String getAsText() {  
    	if(null == getValue()){
    		return null;
    	}
        Date value = (Date) getValue();  
        return dateToString(value, DATE_PATTERN_DEFAULT);  
    }  
    
    /**
     * 将字符串转化为日期。 字符串格式("YYYY-MM-DD")。
     * 例如："2012-07-01"或者"2012-7-1"或者"2012-7-01"或者"2012-07-1"是等价的。
     * @throws java.text.ParseException 
     */
    public static Date stringToDate(String str, String pattern) throws java.text.ParseException {
        Date dateTime = null;
        SimpleDateFormat formater = new SimpleDateFormat(pattern);
        dateTime = formater.parse(str);
        return dateTime;
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
        }
        return str;
    }
}
