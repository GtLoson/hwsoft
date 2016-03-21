package com.hwsoft.common.customer;

import java.util.EnumSet;

import com.hwsoft.common.product.ProductBuyerRecordStatus;

/**
 * 用户来源
 */
public enum CustomerSource {

	MOBILE_REGISTER() {
		@Override
		public String toString() {
			return "手机注册";
		}
	},

	BACKGROUND_ADD() {
		@Override
		public String toString() {
			return "后台添加";
		}
	},

	SYSTMER_CUSTOMER() {
		@Override
		public String toString() {
			return "营收账户";
		}
	},
	hwsoft_MEDIATOR() {
		@Override
		public String toString() {
			return "钱妈妈居间人";
		}
	},

	WAP_REGISTER() {
		@Override
		public String toString() {
			return "wap注册";
		}
	},
	hwsoft_SYSTEM_CUSTOMER_BALANCE(){
		@Override
		public String toString() {
			return "平衡金";
		}
	},
	hwsoft_SYSTEM_CUSTOMER_RISK(){
		@Override
		public String toString() {
			return "风险金";
		}
	};

	/**
	 * 系统用户（系统用户和居间人账户）
	 */
	public static final EnumSet<CustomerSource> SYSTEM_CUSTOMER = EnumSet.of(
			hwsoft_MEDIATOR, SYSTMER_CUSTOMER,hwsoft_SYSTEM_CUSTOMER_BALANCE,hwsoft_SYSTEM_CUSTOMER_RISK);

	/**
	 * 普通用户
	 */
	public static final EnumSet<CustomerSource> NORMAL_CUSTOMER = EnumSet.of(
			MOBILE_REGISTER, WAP_REGISTER, BACKGROUND_ADD);

}
