/**
 * 
 */
package com.hwsoft.common.product;

/**
 * 	计划类别
 * @author tzh
 *
 */
public enum ProductType {
	
	
	FIXED_TERM_PRODUCT(){
		@Override
		public String toString() {
			return "固定期限产品";
		}
	}, 
	FINANCE_PLAN(){
		@Override
		public String toString() {
			return "理财计划";
		}
	},
	CAR_LOAN(){
		@Override
		public String toString() {
			return "汽车抵押贷";
		}

		public boolean isLoan(){
			return true;
		}
	};
	
	/**
	 * 是否是散标
	 * @return
	 */
	public boolean isLoan(){
		return false;
	}
}
