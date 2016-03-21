/**
 * 
 */
package com.hwsoft.common.order;

/**
 * @author tzh
 *
 */
public enum OrderType {

	BUY_PLAN(){
		@Override
		public String toString() {
			return "申购共鸣产品";
		}
	},
	BUY_PLAN_ORDER_NOTICE(){
		@Override
		public String toString() {
			return "申购产品订单状态通知";
		}
	},
	BIND_BANK_CARD(){
		@Override
		public String toString() {
			return "绑定银行卡";
		}
	},
	CREATE_USER_ACCOUNT(){
		@Override
		public String toString() {
			return "开户";
		}
	},
	UPDATE_USER_ACCOUNT(){
		@Override
		public String toString() {
			return "更新用户信息";
		}
	},
	BID_hwsoft_LOAN(){
		@Override
		public String toString() {
			return "散标投标";
		}
	},
	FAILED_hwsoft_LOAN(){
		@Override
		public String toString() {
			return "流标";
		}
	},
	LENDING_hwsoft_LOAN(){
		@Override
		public String toString() {
			return "放款";
		}
	},
	REPAYMENT_hwsoft_NORMAL_HANLDLE(){
		@Override
		public String toString() {
			return "手动正常还款";
		}
	},
	REPAYMENT_hwsoft_NORMAL_AUTO(){
		@Override
		public String toString() {
			return "自动正常还款";
		}
	},
	REPAYMENT_hwsoft_ADVANCE_ALL_HANDLE(){
		@Override
		public String toString() {
			return "手动提前全部还款";
		}
	},
	REPAYMENT_hwsoft_OVERDUE_AUTO(){
		@Override
		public String toString() {
			return "自动逾期还款";
		}
	},
	REPAYMENT_hwsoft_OVERDUE_HANDLE(){
		@Override
		public String toString() {
			return "手动逾期还款";
		}
	},
	RISK_POINT_GUARANTE(){
		@Override
		public String toString() {
			return "风险金垫付";
		}
	},
	RISK_POINT_APPEND(){
		@Override
		public String toString() {
			return "补充风险金";
		}
	},
	RECHARGE(){
		@Override
		public String toString() {
			return "充值";
		}
	},
	CUSTOMER_APPLY_WITHDRAWAL(){
		@Override
		public String toString() {
			return "用户申请提现";
		}
	},
	AGENCY_WITHDRAWAL(){
		@Override
		public String toString() {
			return "机构提现";
		}
	},
	LENDING_WITHDRAWAL(){
		@Override
		public String toString() {
			return "放款提现";
		}
	},
	IDCARD_AUTH(){
		@Override
		public String toString() {
			return "实名认证";
		}
	}
}
