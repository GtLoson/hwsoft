/**
 * 
 */
package com.hwsoft.common.product;

/**
 * 产品赎回方式
 * @author tzh
 *
 */
public enum ProductRedeemType {
	
	EXPIRE_AUTOMATICALLY_REDEMPTION(){
		@Override
		public String toString() {
			return "到期自动赎回";
		}
	},
	EXPIRE_HANDLE_REDEMPTION(){
		@Override
		public String toString() {
			return "到期可选择赎回";
		}
	},

}
