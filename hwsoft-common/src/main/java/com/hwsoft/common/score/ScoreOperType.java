/**
 * 
 */
package com.hwsoft.common.score;

/**
 * @author tzh
 *
 */
public enum ScoreOperType {
	
	REG_SEND(){

		@Override
		public String toString() {
			return "注册赠送";
		}
		@Override
		public boolean isPlus() {
			return true;
		}
		
		@Override
		public Integer score() {
			return 500;
		}
	},
	AUTH_SEND(){
		@Override
		public String toString() {
			return "实名认证赠送";
		}
		@Override
		public boolean isPlus() {
			return true;
		}

		@Override
		public Integer score() {
			return 500;
		}
		
	},
	BIND_SEND(){
		@Override
		public String toString() {
			return "绑定银行卡赠送";
		}
		@Override
		public boolean isPlus() {
			return true;
		}

		@Override
		public Integer score() {
			return 500;
		}

	},
	
	INVITE_SEND (){
		@Override
		public String toString() {
			return "投资赠送";
		}
		@Override
		public boolean isPlus() {
			return true;
		}
		
		@Override
		public Integer score() {
			return 1000;
		}
	},
	EXCHANGE(){
		@Override
		public String toString() {
			return "积分兑换";
		}

		@Override
		public boolean isPlus() {
			return false;
		}

		@Override
		public Integer score() {
			return null;
		}
		
	},
	BACK_ADD(){
		@Override
		public String toString() {
			return "后台添加";
		}

		@Override
		public boolean isPlus() {
			return true;
		}

		@Override
		public Integer score() {
			return null;
		}
		
	}
	;
	
	public abstract boolean isPlus();
	
	
	public abstract Integer score();
}
