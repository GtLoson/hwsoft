/**
 * 
 */
package com.hwsoft.common.product;

/**
 * 产品投资品种类型
 * @author tzh
 *
 */
public enum ProductInvestType {
	
	SMALL_LOAN(){

		@Override
		public String toString() {
			return "小贷";
		}
		
	},
	FACTORING(){
		@Override
		public String toString() {
			return "保理";
		}
	},
	P2P () {
		@Override
		public String toString() {
			return "p2p";
		}
	}
	
}
