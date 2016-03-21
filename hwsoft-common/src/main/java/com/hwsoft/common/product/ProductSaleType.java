/**
 * 
 */
package com.hwsoft.common.product;

/**
 * 产品销售类型
 * @author tzh
 *
 */
public enum ProductSaleType {

	DISTRIBUTION(){
		@Override
		public String toString() {
			return "分销";
		}
	},
	UNDERWRITING(){
		@Override
		public String toString() {
			return "承销";
		}
	},
	PROPRIETARY(){
		@Override
		public String toString() {
			return "自营";
		}
	}
	
}
