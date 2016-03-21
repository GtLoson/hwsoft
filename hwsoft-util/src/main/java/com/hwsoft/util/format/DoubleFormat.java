package com.hwsoft.util.format;

import java.math.BigDecimal;
import java.text.DecimalFormat;


/**
 * 格式化
 * @author tangzhi
 *
 * 2014-11-16
 */
public class DoubleFormat {

	public static final String ONLY_DECIMAL_FORMAT_PATTERN = "##0.00";
	
	public static final String DEFAULE_DECIMAL_FORMAT_PATTERN = "##,###.00";
	
	public static final String NO_DECIMAL_FORMAT_PATTERN = "###";
	
	public static String format(String pattern,double amount){
		DecimalFormat fmt = new DecimalFormat(pattern);   
        return fmt.format(amount);   
	}
	
	/**
	 * 简单的金额转换，银行卡限额实用
	 * @param amount
	 * @return
	 */
	public static String forMatNumberUnit(double amount){
		
		if(amount >= 10000 ){
			return format(NO_DECIMAL_FORMAT_PATTERN, amount/10000)+"万";
		} else {
			return format(NO_DECIMAL_FORMAT_PATTERN, amount);
		}
	}
	
	public static void main(String[] args) {
		String str = DoubleFormat.roundFloorFormatWithScale(0.0,2);
		System.out.println(str);
	}
	
	public static String roundFloorFormatWithScale(double amount,int scale){
		BigDecimal bd = new BigDecimal(amount);
		bd = bd.setScale(scale,BigDecimal.ROUND_FLOOR);
		return bd.toString();
	}
}
