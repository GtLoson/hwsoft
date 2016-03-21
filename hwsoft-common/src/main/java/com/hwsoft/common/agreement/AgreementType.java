/**
 * 
 */
package com.hwsoft.common.agreement;

/**
 * @author tzh
 * 
 */
public enum AgreementType {

	/**
    * 	
    */
	PRODUCT_AGREEMENT() {
		@Override
		public String toString() {
			return "产品协议";
		}

//		public boolean isOnlyOne() {
//			return false;
//		}
	},
	/**
	 * 债权转让合同
	 */
	ASSIGNMENT_TRANSFER() {
		@Override
		public String toString() {
			return "债权转让合同";
		}

//		public boolean isOnlyOne() {
//			return false;
//		}
	},
	/**
	 * 注册服务协议
	 */
	REGISTER_AGREEMENT() {
		@Override
		public String toString() {
			return "注册服务协议";
		}
	},
	/**
	 * 投资服务协议
	 */
	INVEST_AGREEMENT() {
		@Override
		public String toString() {
			return "投资服务协议";
		}
	},	
	/**
	 * 隐私条款 Privacy policy
	 */
	PRIVACY_POLICY() {
		@Override
		public String toString() {
			return "隐私条款";
		}
	},
	UMPAY_SEQUESTRATE (){
		@Override
		public String toString() {
			return "联动扣款协议";
		}
	},
	REPAY_AMOUNT_AND_TRANSFER() {
		@Override
		public String toString() {
			return "应收账款转让及回购协议";
		}

//		public boolean isOnlyOne() {
//			return false;
//		}
	},
	
	;
	

	/**
	 * 是否只允许只有一份同类型的合同模板
	 * 
	 * @return
	 */
	public boolean isOnlyOne() {
		return true;
	}
}
