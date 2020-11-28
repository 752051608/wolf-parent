package com.wolf.config.plugin.impl;

import com.wolf.config.plugin.impl.impl.vo.OrderConfirmRequest;

/**
 * 订单创建插件
 * Created by dyt on 2016/12/2.
 */
public interface IOrderCreatePlugin {

    /**
     * 订单提交后续处理
     * @param orderConfirmForm   商户购物项参数
     * @param form      营销信息
     * @throws Exception
     */
    void createOrderAfter(OrderConfirmRequest orderConfirmForm, PluginRequest form) throws Exception;
}
