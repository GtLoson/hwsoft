/**
 *
 */
package com.hwsoft.common.product;

import java.util.EnumSet;

/**
 * 产品购买记录状态，即产品订单状态
 * 
 * @author tzh
 */
public enum ProductBuyerRecordStatus {

	// 0-下单中
	PURCHASE() {
		@Override
		public String toString() {
			return "申购中";
		}
	},

	// 1-已下单
	HAVE_PLACE_ORDER() {
		@Override
		public String toString() {
			return "已下单";
		}
	},

	// 2-订单失效
	ORDER_TERMINATED() {
		@Override
		public String toString() {
			return "订单失效";
		}
	},
	// 3-已支付
	HAS_PAIED() {
		@Override
		public String toString() {
			return "订单已支付";
		}
	},
	// 4-已投标
	HAS_BID() {
		@Override
		public String toString() {
			return "已投标";
		}
	},
	// 5-投标中
	BIDING() {
		@Override
		public String toString() {
			return "投标中";
		}
	},
	// 6-债权转让中
	TRANSFERRING() {
		@Override
		public String toString() {
			return "债权转让中";
		}
	},
	// 7-已赎回
	REDEEMED() {
		@Override
		public String toString() {
			return "等待银行处理";
		}
	},
	// 8-已提现
	HAS_WITHDRAWAL() {
		@Override
		public String toString() {
			return "已提现";
		}
	},
	// 9-待退款
	WAITTING_REFUND() {
		@Override
		public String toString() {
			return "待退款";
		}
	},
	// 10-已退款
	HAS_REFUND() {
		@Override
		public String toString() {
			return "已退款";
		}
	},
	REDEMPTION() {
		@Override
		public String toString() {
			return "赎回中";
		}
	},
	FAILED() {
		@Override
		public String toString() {
			return "已流标";
		}
	},
	// 收益中
	PROFITING() {
		@Override
		public String toString() {
			return "收益中";
		}
	},
	OVER(){
		@Override
		public String toString() {
			return "已结束";
		}
	},
	BID_SUCCESS(){
		@Override
		public String toString() {
			return "投标成功";
		}
	};

	/**
	 * 订单未结束状态的状态
	 */
	public static final EnumSet<ProductBuyerRecordStatus> USING_STATUS = EnumSet
			.of(PURCHASE, HAVE_PLACE_ORDER, HAS_PAIED, HAS_BID, BIDING,
					TRANSFERRING, REDEEMED, WAITTING_REFUND, REDEMPTION,PROFITING,BID_SUCCESS);

	/**
	 * 订单未结束状态的状态Interest bearing (收益中的订单)
	 */
	public static final EnumSet<ProductBuyerRecordStatus> INTEREST_BEARING_STATUS = EnumSet
			.of(HAS_PAIED, HAS_BID, BIDING, TRANSFERRING,PROFITING);

	/**
	 * 订单失败状态
	 */
	public static final EnumSet<ProductBuyerRecordStatus> FAILED_STATUS = EnumSet
			.of(ORDER_TERMINATED, WAITTING_REFUND, HAS_REFUND,FAILED);

	public static ProductBuyerRecordStatus fromString(String str) {
		for (ProductBuyerRecordStatus productBuyerRecordStatus : ProductBuyerRecordStatus
				.values()) {
			if (productBuyerRecordStatus.name().equals(str)) {
				return productBuyerRecordStatus;
			}
		}
		return null;
	}

	public static boolean isContain(ProductBuyerRecordStatus status,
			EnumSet<ProductBuyerRecordStatus> statuss) {
		for (ProductBuyerRecordStatus productBuyerRecordStatus : statuss) {
			if (status.equals(productBuyerRecordStatus)) {
				return true;
			}
		}
		return false;
	}
	
	
	
}
