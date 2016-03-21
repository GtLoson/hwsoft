package com.hwsoft.common.product;

/**
 * 产品份额状态
 * @author tzh
 *
 */
public enum ProductOfThatStatus {

	// 份额确认中
	CONFIRMING(){
		@Override
		public String toString(){
			return "份额确认中";
		}
	},
	// 收益中
	PROFITING(){
		@Override
		public String toString(){
			return "收益中";
		}
	},
	// 转让中
	TRANSGERING(){
		@Override
		public String toString(){
			return "转让中";
		}
	},
	// 已转让
	TRANSFERED(){
		@Override
		public String toString(){
			return "已转让";
		}
	},
	// 退出中
	EXITING(){
		@Override
		public String toString(){
			return "退出中";
		}
	},
	// 已退出
	EXITED(){
		@Override
		public String toString(){
			return "已退出";
		}
	},
	//已流标
	FAILED(){
		@Override
		public String toString(){
			return "已流标";
		}
	},
}
