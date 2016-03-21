/**
 * 
 */
package com.hwsoft.common.product;

/**
 * 产品期限类型
 * @author tzh
 *
 */
public enum ProductDateType {

	FIXED_TERM(){
		@Override
		public String toString() {
			return "固定期限";
		}
	},
	UN_FIXED_TERM(){
		@Override
		public String toString() {
			return "非固定期限";
		}
	}
}
