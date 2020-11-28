package com.wolf.common.exception;

import com.wolf.common.util.MessageSourceUtil;
import org.springframework.context.i18n.LocaleContextHolder;

/**
 * 业务异常类
 * Created by angus on 15/9/28.
 */
public class ServiceException extends RuntimeException {
    private String errorCode;

    public ServiceException(String errorCode, String... args) {
        this(errorCode, MessageSourceUtil.getMessage(errorCode, args, LocaleContextHolder.getLocale()));
    }

    private ServiceException(String errorCode, String message) {
        super(message == null ? errorCode : message);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return this.errorCode;
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return null;
    }
}
