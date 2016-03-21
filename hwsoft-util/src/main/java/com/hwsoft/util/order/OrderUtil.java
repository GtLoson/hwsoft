package com.hwsoft.util.order;

import java.util.Date;

import com.hwsoft.util.date.DateTools;


/**
 * 订单工具类
 * @author tangzhi
 *
 * 2014-10-4
 */
public class OrderUtil {

	private static final Integer TYPE_SIZE = 2;
	
	private static final Integer DATE_SIZE = 8;
	
	
	/**
	 * 生成订单号，(20141004+00+00000001)
	 * @param typeId	订单类型的序号（从0开始,2位）
	 * @param orderId	订单表的编号       （剩余位数）
	 * @param length	订单长度
	 * @return
	 */
	public static String getOrderFormId(int typeId,int orderId,int length){
		
		String dateString = DateTools.dateToString(new Date(), DateTools.DATE_PATTERN_NUMBER_DAY);
		String typeString = numberToStringBySize(typeId, TYPE_SIZE);
		String orderString = numberToStringBySize(orderId, length-DATE_SIZE-TYPE_SIZE);
		
		return dateString+typeString+orderString;
	}
	
	/**
	 * 将数字转换成指定位数的字符串
	 * @param num
	 * @param size
	 * @return
	 */
	private static String numberToStringBySize(Integer num,int size){
		
		String numString = num.toString();
		if(numString.length() > size){
			throw new RuntimeException("参数异常");
		}
		if(numString.length() == size){
			return numString;
		}
		int diff = size - numString.length();
		for(int i = 0 ; i < diff ; i++){
			numString = "0"+numString;
		}
		return numString;
	}
	
	public static void main(String[] args) {
		System.out.println(getOrderFormId(1, 234356, 18));
	}
	
}
