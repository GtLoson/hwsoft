/**
 * 
 */
package com.hwsoft.common.product;

import com.hwsoft.exception.BaseException;

/**
 * 产品时间单位
 * @author tzh
 *
 */
public enum ProductDateUnit {

	DAY(){
		@Override
		public String toString() {
			return "天";
		}
		public String suffix(){
			return "天";
		}
	},
//	WEEK(){
//		@Override
//		public String toString() {
//			return "周";
//		}
//		public String suffix(){
//			return "周";
//		}
//	},
	MONTHS(){
		@Override
		public String toString() {
			return "月";
		}
		public String suffix(){
			return "个月";
		}
	},
	YEAR(){
		@Override
		public String toString() {
			return "年";
		}
		public String suffix(){
			return "年";
		}
		
	},
	ALL(){
		@Override
		public String toString() {
			return "不区分";
		}
		public String suffix(){
			return "不区分";
		}
	}
	;

	public abstract String suffix();
	
	public static ProductDateUnit fromExcelUnit(String unit){
		
		for(ProductDateUnit productDateUnit:ProductDateUnit.values()){
			if(productDateUnit.toString().equals(unit)){
				return productDateUnit;
			}
		}
		throw new BaseException("产品期限单位转换异常");
	}
}
