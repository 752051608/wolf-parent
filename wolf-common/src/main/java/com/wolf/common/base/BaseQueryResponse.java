package com.wolf.common.base;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.io.Serializable;
import java.util.List;

/**
 * 查询返回对象基类
 * Created by aqlu on 15/12/2.
 */
@Data
@Builder
@AllArgsConstructor
public class BaseQueryResponse<T> implements Serializable {
    private Long total;

    private List<T> data;

    public BaseQueryResponse(){}

    public BaseQueryResponse(Page<T> page){
        if(null != page){
            total = page.getTotalElements();
            data = page.getContent();
        }
    }
}
