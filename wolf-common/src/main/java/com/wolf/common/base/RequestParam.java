package com.wolf.common.base;

import lombok.Data;

import java.io.Serializable;

/**
 * 请求参数基类
 * Created by aqlu on 15/11/26.
 */
@Data
public class RequestParam implements Serializable {
    private String v;
}
