/**
 * 
 */
package com.hwsoft.common.product;

/**
 * @author tzh
 *
 */
public enum ProductSubAccountPointLogType {

	/**
	 * 购买产品
	 */
	BUY_PRODUCT(){

		@Override
		public String toString() {
			return "购买产品";
		}
		@Override
		public boolean plus() {
			return true;
		}
		
	},
	/**
	 * 回收收益
	 */
	RECEIVE_INTEREST(){

		@Override
		public String toString() {
			return "回收收益";
		}
		
		@Override
		public boolean plus() {
			return true;
		}
		
	},
	/**
	 * 提现
	 */
	WITH_DRAW_CASH(){

		@Override
		public String toString() {
			return "提现";
		}

		@Override
		public boolean plus() {
			return false;
		}
		
	},
	REFUND(){

		@Override
		public String toString() {
			return "退款";
		}

		@Override
		public boolean plus() {
			return false;
		}
	},
	/**
	 * 回收收益
	 */
	CONUNT_INTEREST(){

		@Override
		public String toString() {
			return "回收收益";
		}
		
		@Override
		public boolean plus() {
			return true;
		}
		
	}
	;
	
	public abstract boolean plus ();
}
