package com.wolf.common.base;

import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;

/**
 * 前端分页请求参数
 * Created by zhangjin on 15/12/10.
 */
@Data
public class PageRequestParam implements Serializable {
    //当前页
    private int pageNum = 0;
    //分页
    private int pageSize = 10;

    //开始
    private int start;

    public int getStart() {
        return (pageNum) * pageSize;
    }

    public Pageable getRequest() {
        return new PageRequest(pageNum, pageSize);
    }
}
