package com.hwsoft.common.point;


/**
 * 资金记录类型
 * @author tzh
 *
 */
public enum CustomerSubAccountPointType {
	BID_FREEZEN(){

		@Override
		public String toString() {
			return "投标冻结";
		}
		
	},	
	FAILED(){

		@Override
		public String toString() {
			return "流标";
		}
		
	},	
	LENDING(){

		@Override
		public String toString() {
			return "放款";
		}
	},
	REPAYMENT_INPUT_BALANCE(){
		@Override
		public String toString() {
			return "还款资金划入平衡金";
		}
	},
	REPAYMENT_PRICIPAL(){
		@Override
		public String toString() {
			return "还款本金";
		}
	},
	REPAYMENT_INTEREST(){
		@Override
		public String toString() {
			return "还款利息";
		}
	},
	REPAYMENT_DEFAULT_INTEREST(){
		@Override
		public String toString() {
			return "还款罚息";
		}
	},
	REPAYMENT_DUE_APPEND_RISK(){
		@Override
		public String toString() {
			return "逾期还款，补充风险金";
		}
	},
	APPLY_WITHDRAWAL(){
		@Override
		public String toString() {
			return "申请提现";
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
	RECHARGE(){
		@Override
		public String toString() {
			return "充值";
		}
	},
	WITHDRAWAL_FEE_FREEZON(){
		@Override
		public String toString() {
			return "提现手续费冻结";
		}
	},
	WITHDRAWAL_SUCCESS_FEE(){
		@Override
		public String toString() {
			return "提现手续费";
		}
	},
	WITHDRAWAL_FAILE_FEE(){
		@Override
		public String toString() {
			return "提现失败，解冻提现手续费";
		}
	},
	WITHDRAWAL_CANCEL(){
		@Override
		public String toString() {
			return "提现撤销";
		}
	},
	WITHDRAWAL_CANCEL_FEE(){
		@Override
		public String toString() {
			return "提现撤销,解冻提现手续费";
		}
	},
	SYSTEM_TRANSFER(){
		@Override
		public String toString() {
			return "系统转账";
		}
	},
	SYSTEM_RECEIVE(){
		@Override
		public String toString() {
			return "系统收款";
		}
	},
	CASH_REWARD(){
		@Override
		public String toString() {
			return "现金奖励";
		}
	},
}
