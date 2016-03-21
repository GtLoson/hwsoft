/**
 * 
 */
package com.hwsoft.common.product;

/**
 * 产品渠道
 * @author tzh
 *
 */
public enum ProductChannelType {

	GONGMING(){

		@Override
		public String toString() {
			return "共鸣";
		}
		
	},
	hwsoft(){

		@Override
		public String toString() {
			return "钱妈妈";
		}
	}
	
}
