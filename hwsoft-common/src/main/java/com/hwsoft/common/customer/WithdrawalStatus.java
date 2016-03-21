package com.hwsoft.common.customer;

/**
 * 提现状态
 * @author tzh
 *
 */
public enum WithdrawalStatus {
	
	AUDITING(){
		@Override
		public String toString() {
			return "审核中";
		}
		
	},
	AUDITED(){
		@Override
		public String toString() {
			return "审核通过";
		}
	},
	AUDITED_FAILED(){
		@Override
		public String toString() {
			return "审核未通过";
		}
	},
	AUDITED_AGAIN(){
		@Override
		public String toString() {
			return "复核通过";
		}
	},
	AUDITED_AGAIN_FAILED(){
		@Override
		public String toString() {
			return "复核未通过";
		}
	},
	WITHDRAWALING(){
		@Override
		public String toString() {
			return "提现处理中";
		}
	},
	BANK_DEALING(){
		@Override
		public String toString() {
			return "银行处理中";
		}
	},

	WITHDRAWAL_SUCCESS(){
		@Override
		public String toString() {
			return "提现成功";
		}
	},
	WITHDRAWAL_FAILED(){
		@Override
		public String toString() {
			return "提现失败";
		}
	},
	WITHDRAWAL_CANCEL(){
		@Override
		public String toString() {
			return "提现撤销";
		}
	};
	
	public static WithdrawalStatus fromStatusName(String name){
		for(WithdrawalStatus status: WithdrawalStatus.values()){
			if(status.name().equals(name)){
				return status;
			}		
		}
		throw new RuntimeException("提现申请状态转换异常");
	}
	
}
