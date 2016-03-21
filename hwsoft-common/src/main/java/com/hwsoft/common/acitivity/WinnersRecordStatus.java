/**
 * 
 */
package com.hwsoft.common.acitivity;

/**
 * @author tzh
 *
 */
public enum WinnersRecordStatus {
	
	HAS_APPLIED(){
		@Override
		public String toString() {
			return "已申请";
		}
	},
	HAS_GIVE(){
		@Override
		public String toString() {
			return "已处理";
		}
	}

}
