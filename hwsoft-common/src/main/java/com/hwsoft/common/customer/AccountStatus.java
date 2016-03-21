/**
 * 
 */
package com.hwsoft.common.customer;

/**
 * @author tzh
 *
 */
public enum AccountStatus {

	USING(){
		@Override
		public String toString() {
			return "使用中";
		}
	},
	STOP(){
		@Override
		public String toString() {
			return "已停止";
		}
	},
	PAUSE(){
		@Override
		public String toString() {
			return "暂停";
		}
	}
	
}
