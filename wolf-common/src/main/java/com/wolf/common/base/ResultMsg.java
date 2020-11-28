package com.wolf.common.base;

import lombok.Data;

/**
 * Created by daiyitian on 16/11/11.
 */
@Data
public class ResultMsg<T> {

    private int code;
    private T context;
    private String errorMsg;
    private String msg;
    private int errorCode;
    private Object errorContext;
}
