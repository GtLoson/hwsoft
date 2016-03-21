package com.hwsoft.common.systemsetting;

/**
 * @author tzh
 */
public enum SystemSettingType {

    FILE_SERVER_URI {
        @Override
        public String toString() {
            return "HTTP文件服务器地址";
        }

        @Override
        public Class<?> valueType() {
            return String.class;
        }
    },
    MOBILE_CODE_TIME_OUT_SECONED(){
    	@Override
        public String toString() {
            return "手机验证码超时时间（秒）";
        }

        @Override
        public Class<?> valueType() {
            return Integer.class;
        }
    },
    MOBILE_CODE_SEND_INTERVAL(){
    	@Override
        public String toString() {
            return "手机验证码发送间隔时间（秒）";
        }

        @Override
        public Class<?> valueType() {
            return Integer.class;
        }
    },
    FTP_FILE_SERVER_URL(){
    	@Override
        public String toString() {
            return "FTP文件服务器地址";
        }

        @Override
        public Class<?> valueType() {
            return String.class;
        }
    },
    FTP_FILE_SERVER_USERNAME(){
    	@Override
        public String toString() {
            return "FTP文件服务器用户名";
        }

        @Override
        public Class<?> valueType() {
            return String.class;
        }
    },
    FTP_FILE_SERVER_PASSWORD(){
    	@Override
        public String toString() {
            return "FTP文件服务器密码";
        }

        @Override
        public Class<?> valueType() {
            return String.class;
        }
    }
    ;

    public Class<?> valueType() {
        return Double.class;

    }
}
