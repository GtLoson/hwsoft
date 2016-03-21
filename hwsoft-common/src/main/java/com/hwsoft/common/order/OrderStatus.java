/**
 * 
 */
package com.hwsoft.common.order;

/**
 * @author tzh
 *
 */
public enum OrderStatus {

	/**
	 * 等待处理
	 */
	WAITING_HANDLE(){

		@Override
		public String toString() {
			return "等待处理";
		}
	},
	
	/**
	 * 请求同步中
	 */
	IN_HANDLE(){

		@Override
		public String toString() {
			return "请求同步中";
		}
	},
	
	/**
	 * 请求成功
	 */
	HANDLE_SUCESS(){
		@Override
		public String toString() {
			return "请求成功";
		}
	},
	
	/**
	 * 请求失败
	 */
	HANDLE_FAILED(){
		@Override
		public String toString() {
			return "请求失败";
		}
	},
	
	/**
	 * 请求过期
	 */
	TIME_OUT(){
		@Override
		public String toString() {
			return "请求过期";
		}
	},
	
	/**
	 * 请求请求失败
	 */
	REQUEST_FAILED(){
		@Override
		public String toString() {
			return "请求失败";
		}
	} ,
	DEAL_FAILED(){
		@Override
		public String toString() {
			return "支付处理失败";
		}
	},
	DEAL_SUCCESS(){
		@Override
		public String toString() {
			return "支付处理成功";
		}
	}
	
}
