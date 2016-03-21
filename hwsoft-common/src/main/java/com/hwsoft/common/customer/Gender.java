/**
 * 
 */
package com.hwsoft.common.customer;

/**
 * @author tzh
 *
 */
public enum Gender {
	MALE {
		@Override
		public String toString() {
			return "男";
		}
	},
	FEMALE {
		@Override
		public String toString() {
			return "女";
		}
	}
}
