package com.wolf.common.exception;

/**
 * 资源未找到异常类
 * Created by angus on 15/9/28.
 */
public class ResourceNotFoundException extends ServiceException {

    public ResourceNotFoundException(String... args) {
        super("K-000003", args);
    }
}
