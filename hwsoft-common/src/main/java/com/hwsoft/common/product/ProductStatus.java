/**
 * 
 */
package com.hwsoft.common.product;

/**
 * @author tzh
 *
 */
public enum ProductStatus {
	
	THIRD_AUDIT(){
		@Override
		public String toString() {
			return "第三方审核中";
		}
	},
	DRAFTS(){
		@Override
		public String toString() {
			return "草稿中";
		}
	},
	AUDIT(){
		@Override
		public String toString() {
			return "审核中";
		}
	},
	PREPARATION(){
		@Override
		public String toString() {
			return "筹备中";
		}
	},
	SALES(){
		@Override
		public String toString() {
			return "销售中";
		}
	},
	SALES_OVER(){
		@Override
		public String toString() {
			return "售罄";
		}
	},
	LOCK_UP(){
		@Override
		public String toString() {
			return "锁定中";
		}
	},

	OPEN_REDEMPTION(){
		@Override
		public String toString() {
			return "开放赎回期";
		}
	},
	REDEMPTION(){
		@Override
		public String toString() {
			return "赎回中";
		}
	},
	ENDED(){
		@Override
		public String toString() {
			return "已结束";
		}
	},
	CANCEL(){
		@Override
		public String toString() {
			return "已取消（已流标）";
		}
	},
	LENDING(){
		@Override
		public String toString() {
			return "还款中";
		}
	},
	OVER_DUE(){
		@Override
		public String toString() {
			return "逾期中";
		}
	};

}
