/**
 * 
 */
package com.hwsoft.common.product;

import com.google.common.base.Strings;

/**
 * 收益处理方式
 * @author tzh
 *
 */
public enum ProductProfitProgressType {

	RETURN_AVAILEABLE_ACCOUNT(){

		@Override
		public String toString() {
			return "回到可用账户";
		}
		
	},
	RETURN_BANK(){
		@Override
		public String toString() {
			return "自动提现到银行卡";
		}
	},
	REINVESTMENT_OF_EARNINGS(){
		@Override
		public String toString() {
			return "收益再投资";
		}
	};

	/**
	 * build type from String
	 * @param productProfitProcess string type
	 * @return type
	 */
	public static ProductProfitProgressType buildFromString(String productProfitProcess){
		if(!Strings.isNullOrEmpty(productProfitProcess)){
			for (ProductProfitProgressType productProfitProgressType:ProductProfitProgressType.values()){
				if(productProfitProgressType.toString().equals(productProfitProcess)){
					return productProfitProgressType;
				}
			}	
		}
		return null;
	} 
}
