package com.wolf.config.plugin.impl.impl.vo;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 购物车信息
 * create by guoguangnan 2015-12-01
 */
@Data
@Entity
@Table(name = "np_shopping_cart")
public class ShoppingCart implements Serializable {

    /**
     * 购物车ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shopping_cart_id")
    private Long shoppingCartId;

    /**
     * 货品id
     */
    @Column(name = "goods_info_id")
    private Long goodsInfoId;

    /**
     * 优惠价格
     */
    @Column(name = "goods_pre_price")
    private Long goodsPrePrice;

    /**
     * 货品价格
     */
    @Column(name = "goods_price")
    private Integer goodsPrice;

    /**
     * 货品数量
     */
    @Column(name = "goods_num")
    private Long goodsNum;

    /**
     * 套装ID
     */
    @Column(name = "fit_id")
    private Long fitId;

    /**
     * 用户id
     */
    @Column(name = "customer_id")
    private Long customerId;

    /**
     * 促销id
     */
    @Column(name = "marketing_id")
    private Long marketingId;

    /**
     * 创建时间
     */
    @Column(name = "shopping_cart_time")
    private Date shoppingCartTime;

    /**
     * 删除标记
     */
    @Column(name = "del_flag")
    private String delFlag;

    /**
     * 仓库Id
     */
    @Column(name = "distinct_id")
    private Long distinctId;

    /**
     * 促销活动id
     */
    @Column(name = "marketing_activity_id")
    private Long marketingActivityId;

    /**
     * 订单优惠id
     */
    @Column(name = "order_marketing_id")
    private Long orderMarketingId;

    /**
     * 团购促销id
     */
    @Column(name = "goods_group_id")
    private Long goodsGroupId;

    /**
     * 购物车修改赠品id
     */
    @Column(name = "present_scope_ids")
    private String presents;

    /**
     * 套装数量
     */
    @Column(name = "fit_num")
    private Long fitNum;

    /**
     * 商品所属商家编号
     */
    @Transient
    private Long storeId;

    /**
     * 通用优惠券平摊金额
     */
    @Transient
    private BigDecimal commonCouponPrice;


    public boolean isFit() {
        return fitId != null;
    }



}