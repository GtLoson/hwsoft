/**
 * 
 */
package com.hwsoft.common.validate;

/**
 * @author tzh
 *
 */
public enum ValidateType {

	REGISTER(){

		@Override
		public String toString() {
			return "注册验证码";
		}
		@Override
		public int level() {
			return 99;
		}
	},
	RESET_LOGIN_PASSWORD(){

		@Override
		public String toString() {
			return "找回密码验证码";
		}
		@Override
		public int level() {
			return 99;
		}
	},
	RESET_PAY_PASSWORD(){

		@Override
		public String toString() {
			return "找回支付密码验证码";
		}
		@Override
		public int level() {
			return 99;
		}
	},
	
	OTHER() {
		@Override
		public String toString() {
			return "其他";
		}
		@Override
		public int level() {
			return 1;
		}
	},
	
	MODIFY_MOBILE() {
		@Override
		public String toString() {
			return "修改手机";
		}
		@Override
		public int level() {
			return 99;
		}
	},
	BINDING_MOBILE_NEW{
		@Override
		public String toString() {
			return "绑定手机新";
		}
		@Override
		public int level() {
			return 99;
		}
	},
	MODIFY_MOBILE_NEW{
		@Override
		public String toString() {
			return "修改手机新";
		}

		@Override
		public int level() {
			return 99;
		}
		
	}
	;
	
	public abstract int level();
}
