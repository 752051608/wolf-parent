package com.wolf.common.exception;

/**
 * @author honglou
 * @date 2019-08-21 14:38
 */
public class RestApiException extends RuntimeException {
    private String errorCode;
    private String errorMsg;

    public RestApiException(String errorMsg) {
        super(errorMsg);
        this.errorMsg = errorMsg;
    }

    public RestApiException(String errorCode, Exception e) {
        this.errorMsg = e.getMessage();
        this.errorCode = errorCode;
    }

    public RestApiException(String errorCode, String errorMsg) {
        this.errorMsg = errorMsg;
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
