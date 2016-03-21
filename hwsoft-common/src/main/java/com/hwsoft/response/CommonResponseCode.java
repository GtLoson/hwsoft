package com.hwsoft.response;

/**
 * 通用响应code
 */
public enum CommonResponseCode {

    COMMON_SUCCESS(                       0,         "成功"),
    COMMON_ILLEGAL_PARAM(                 1,         "参数异常"),
    COMMON_SERVER_ERROR(                  2,         "系统异常"),
    COMMON_INVALID_STATUS(                3,         "状态异常"),
    COMMON_NOT_IMPLEMENTED(               4,         "方法没有实现"),
    COMMON_IO_ERROR(                      5,         "文件读写发生错误");



    private int value;
    private String info;

    private CommonResponseCode(int value, String info) {
        this.value = value;
        this.info = info;
    }

    public static CommonResponseCode fromInt(int value) {
        for (CommonResponseCode type : CommonResponseCode.values()) {
            if (type.value == value) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid Status type code: " + value);
    }

    public String getInfo() {
        return info;
    }

    @Override
    public String toString() {
        return "[" + Integer.toString(this.value) + "] " + info;
    }

    public int value() {
        return this.value;
    }
}
