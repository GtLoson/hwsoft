package com.hwsoft.common.customer;

import com.hwsoft.common.order.OrderType;


/**
 * 提现类型
 * @author tzh
 *
 */
public enum WithdrawalType {

	CUSTOMER_APPLY_HANDLE(){

		@Override
		public String toString() {
			return "投资用户申请提现";
		}

		@Override
		public OrderType getOrderType() {
			return OrderType.CUSTOMER_APPLY_WITHDRAWAL;
		}
		
		
	},
	LENGDING_AUTO(){
		@Override
		public String toString() {
			return "放款自动提现";
		}
		
		@Override
		public OrderType getOrderType() {
			return OrderType.LENDING_WITHDRAWAL;
		}
	},
	CHANNEL_AGENCE_APPLY(){
		@Override
		public String toString() {
			return "渠道机构提现";
		}
		
		@Override
		public OrderType getOrderType() {
			return OrderType.AGENCY_WITHDRAWAL;
		}
	}
	
	;
	
	public abstract OrderType getOrderType();
	
}
