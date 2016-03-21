/**
 * 
 */
package com.hwsoft.common.product;

/**
 *  产品收益率类型
 * @author tzh
 *
 */
public enum ProductInterestType {
	FIXED_INCOME(){

		@Override
		public String toString() {
			return "固定收益";
		}
	},
	FLOATING_INCOME(){
		@Override
		public String toString() {
			return "浮动收益";
		}
	}
}
