/**
 * 
 */
package com.hwsoft.common.customer;

/**
 * @author tzh
 *
 */
public enum UserType {
	NORMAL_USER{
		@Override
        public String toString() {
            return "普通用户";
        }
	},
	CHANNEL_USER{
		@Override
        public String toString() {
            return "机构用户";
        }
	},
}
