package com.wolf.config.plugin.impl.impl.vo;

import lombok.Data;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 订单商家对象，结构同结算页面展示
 * Created by dyt on 2017/1/16.
 */
@Data
public class OrderConfirmSeller {

    /**
     * 卖家编号，boss是0，第三方是对应的store_id
     */
    private Long sellerId;

    /**
     * 商家名称
     */
    private String sellerName;

    /**
     * 订单金额
     */
    private OrderAmount orderAmount;

    /**
     * 默认配送方式
     */
//    private FreightTemplate freightTemplate;

    /**
     * 商家相关的营销
     */
//    private Map<Long, OrderMarketing> orderMarketings;

    /**
     * 订单可以使用优惠券ids
     */
    private List<Long> couponIds;

    /**
     * 商家相关的优惠券--couponCodeId 放入券码id
     */
    private Map<Long, CouponInfo> couponInfos;

    /**
     * 商家相关的不可用优惠券信息--couponCodeId 放入券码id
     */
    private Map<Long, CouponInfo> unableCouponInfos;

    /**
     * 非营销下的购物车货品信息
     */
    private List<WmShoppingCartVo> shoppingCartList;

    /**
     * 响应扁平化数据-购物车编号
     */
    private List<Long> shoppingCartIds;

    /**
     * 响应扁平化数据-营销编号
     */
    private List<Long> orderMarketingIds;



    /**
     * 配送方式
     */
//    private List<FreightExpress> freightExpress;


//    /**
//     * 填放营销信息
//     * @param orderMarketing
//     */
//    public void putOrderMarketing(OrderMarketing orderMarketing) {
//        if (orderMarketings == null) {
//            orderMarketings = new LinkedHashMap<>();
//        }
//        orderMarketings.put(orderMarketing.getMarketing().getMarketingId(), orderMarketing);
//    }

    /**
     * 填放优惠券信息
     * @param couponInfo
     */
    public void putCouponInfo(CouponInfo couponInfo) {
        if (couponInfos == null) {
            couponInfos = new LinkedHashMap<>();
        }
        couponInfos.put(couponInfo.getCouponCodeId(), couponInfo);
    }

    /**
     * 填放优惠券信息
     * @param couponInfo
     */
    public void putUnableCouponInfos(CouponInfo couponInfo) {
        if (unableCouponInfos == null) {
            unableCouponInfos = new LinkedHashMap<>();
        }
        unableCouponInfos.put(couponInfo.getCouponCodeId(), couponInfo);
    }

}
