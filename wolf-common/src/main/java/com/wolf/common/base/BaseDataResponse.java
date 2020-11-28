package com.wolf.common.base;

import lombok.Data;

/**
 * 包含数据的响应基类
 * Created by pingchen on 15/12/30.
 */
@Data
public class BaseDataResponse<T> extends BaseResponse{
    public BaseDataResponse(String code, T data) {
        super(code);
        this.data  = data;
    }

    private T data;
}
