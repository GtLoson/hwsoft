package com.hwsoft.exception;

import com.hwsoft.response.CommonResponseCode;

/**
 * 基础异常
 */
public class APIBaseException extends RuntimeException {
    final CommonResponseCode errorCode;
    private String details;

    public APIBaseException(APIBaseException e) {
        super(e);
        this.errorCode = e.errorCode;
    }

    public APIBaseException(CommonResponseCode errorCode) {
        this.errorCode = errorCode;
    }

    public APIBaseException(CommonResponseCode errorCode, Throwable e) {
        super(e);
        if (e instanceof APIBaseException) {
            this.errorCode = ((APIBaseException) e).getErrorCode();
            this.details = ((APIBaseException) e).getDetails();
        } else {
            this.errorCode = errorCode;
        }
    }

    public APIBaseException(CommonResponseCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
        this.details = message;
    }

    public APIBaseException(CommonResponseCode errorCode, String message, Throwable e) {
        super(message, e);
        if (e instanceof APIBaseException) {
            this.errorCode = ((APIBaseException) e).getErrorCode();
            this.details = ((APIBaseException) e).getDetails();
        } else {
            this.errorCode = errorCode;
            this.details = message;
        }
    }

    public CommonResponseCode getErrorCode() {
        return errorCode;
    }

    public String getDetails() {
        return details;
    }

    public String getMessage() {
        if (details != null) {
            return details;
        } else {
            return getLocalizedMessage();
        }
    }
}
