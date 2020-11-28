package com.wolf.common.util;

import java.io.Serializable;

public class JsonResult<T> implements Serializable {

    private static final long serialVersionUID = -3789653118513746686L;
    private static final String DEFAULT_SUCCESS_MSG = "操作成功";
    private static final String DEFAULT_FAILED_MSG = "操作失败";

    protected boolean success;

    protected String msg;

    protected T data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }


    public static JsonResult<Object> buildSuccess(Object data) {
        JsonResult<Object> result = new JsonResult<>();
        result.setSuccess(true);
        result.setData(data);
        result.setMsg(DEFAULT_SUCCESS_MSG);
        return result;
    }

    public static JsonResult<Object> buildSuccess() {
        JsonResult<Object> result = new JsonResult<>();
        result.setSuccess(true);
        result.setMsg(DEFAULT_SUCCESS_MSG);
        return result;
    }

    public static JsonResult<Object> buildFailed(String msg) {
        JsonResult<Object> result = new JsonResult<>();
        result.setSuccess(false);
        result.setMsg(msg);
        return result;
    }

    public static JsonResult<Object> buildFailed(Object data) {
        JsonResult<Object> result = new JsonResult<>();
        result.setSuccess(false);
        result.setData(data);
        result.setMsg(DEFAULT_FAILED_MSG);
        return result;
    }

    // 增加链式调用方法
    public JsonResult<T> buildData(T data) {
        this.data = data;
        return this;
    }

    public JsonResult<T> buildIsSuccess(boolean success) {
        this.success = success;
        return this;
    }

    public JsonResult<T> buildMsg(String msg) {
        this.msg = msg;
        return this;
    }

//    @Override
//    public String toString() {
//        return "JsonResult{" +
//                "success=" + success +
//                ", msg='" + msg + '\'' +
//                ", data=" + data +
//                '}';
//    }
}

