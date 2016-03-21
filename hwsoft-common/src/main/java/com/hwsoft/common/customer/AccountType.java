/**
 * 
 */
package com.hwsoft.common.customer;

/**
 * @author tzh
 *
 */
public enum AccountType {

	GONGMING(){
		@Override
		public String toString() {
			return "共鸣账户";
		}
	} ,
	hwsoft(){
		@Override
		public String toString() {
			return "钱妈妈账户";
		}
	}
	
	
}
