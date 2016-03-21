package com.hwsoft.common.customer;

public enum RechargeRecordStatus {
	
	WAITING_PAY(){
		@Override
		public String toString() {
			return "等待支付";
		}
	},
	
	RECHARGEING(){
		@Override
		public String toString() {
			return "充值处理中";
		}
	},
	RECHARGE_SUCCESS(){
		@Override
		public String toString() {
			return "充值成功";
		}
		
	},RECHARGE_FAILED(){
		@Override
		public String toString() {
			return "充值失败";
		}
		
	},
	RECHARGE_REFUND(){
		@Override
		public String toString() {
			return "退款";
		}
		
	};

	public RechargeRecordStatus fromNameString(String name){
		for(RechargeRecordStatus rechargeRecordStatus: RechargeRecordStatus.values()){
				if(rechargeRecordStatus.name().equals(name)){
					return rechargeRecordStatus;				
				}	
		}
		throw new RuntimeException("充值状态转换异常");
	}
	

}
