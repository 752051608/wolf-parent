package com.wolf.common.exception;

/**
 * HTTP异常，统一封装需要框架统一处理的HTTP错误
 * 此异常只应该用于Controller层，面向RESTFul接口
 * Created by liuzhaoming on 15/12/1.
 */
public class HttpException extends ServiceException {

    /**
     * http状态码
     */
    private int httpStatus = 500;

    @Deprecated
    public HttpException(String errorCode, String... args) {
        super(errorCode, args);
    }

    public HttpException(int httpStatus) {
        super("K-000004");
        this.httpStatus = isHttpStatusValid(httpStatus) ? httpStatus : this.httpStatus;
    }

    public HttpException(int httpStatus, String errorCode) {
        super(errorCode);
        this.httpStatus = isHttpStatusValid(httpStatus) ? httpStatus : this.httpStatus;
    }

    public HttpException(int httpStatus, String errorCode, String... args) {
        super(errorCode, args);
        this.httpStatus = isHttpStatusValid(httpStatus) ? httpStatus : this.httpStatus;
    }

    private boolean isHttpStatusValid(int httpStatus) {
        return httpStatus >= 400 && httpStatus < 600;
    }

    public int getHttpStatus() {
        return httpStatus;
    }
}
