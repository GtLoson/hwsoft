/**
 * 
 */
package com.hwsoft.common.product;

import java.util.EnumSet;

/**
 * 产品子账户状态
 * @author tzh
 *
 */
public enum ProductBuyerStatus {

	
	NORMAL(){
		@Override
		public String toString() {
			return "正常";
		}
	},
	OVER(){
		@Override
		public String toString() {
			return "已结束";
		}
	}
	,
//	FAILED(){
//		@Override
//		public String toString() {
//			return "已流标";
//		}
//	}
	;
	
	
	/**
	 * 未结束状态
	 */
	public static EnumSet<ProductBuyerStatus> PROGREESSING =  EnumSet
			.of(NORMAL);
	
	/**
	 * 未结束状态
	 */
	public static EnumSet<ProductBuyerStatus> ALL =  EnumSet
			.of(NORMAL,OVER);
	
	
}
