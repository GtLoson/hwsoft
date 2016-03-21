package com.hwsoft.common.product;


/**
 * 投标类型
 * @author tzh
 *
 */
public enum ProductBidType {

	// 手动投标 
	HANDLE_BID(){
		@Override
		public String toString(){
			return "手动投标";
		}
	},
	// 自动投标
	AUTO_BID(){
		@Override
		public String toString(){
			return "自动投标";
		}
	},
	// 债权转让
	TRANSFER_BID(){
		@Override
		public String toString(){
			return "债权转让";
		}
	},
	GONGMING_PRODUCT_BUY(){
		@Override
		public String toString(){
			return "共鸣产品购买";
		}
	};
}
