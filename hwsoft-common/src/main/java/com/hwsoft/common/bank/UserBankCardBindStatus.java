/**
 * 
 */
package com.hwsoft.common.bank;

/**
 * 银行卡绑定状态
 * @author tzh
 *
 */
public enum UserBankCardBindStatus {

	NO_BIND(){
		@Override
		public String toString() {
			return "未绑定";
		}
	},
	PEDDING(){
		@Override
		public String toString() {
			return "待确认";
		}
	},
	BINDED(){
		@Override
		public String toString() {
			return "已经绑定";
		}
	},
	BINDED_FAILED(){
		@Override
		public String toString() {
			return "绑定失败";
		}
	},
	HAS_UNBIND(){
		@Override
		public String toString() {
			return "已经解除绑定";
		}
	}
	
}
