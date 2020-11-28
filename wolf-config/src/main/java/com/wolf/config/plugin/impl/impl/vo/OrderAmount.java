package com.wolf.config.plugin.impl.impl.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 订单金额结构
 * @author Daiyitian
 * @since 2014年12月3日16:34:38
 */
@Data
public class OrderAmount {

    /**
     * 原始总金额(止于分仓价的总计)
     */
    private BigDecimal originalAmount;

    /**
     * 购买商品总数
     */
    private Long buyGoodsNum;

    /**
     * 营销优惠总额（仅含满减/满折）
     */
    private BigDecimal marketingDiscount;

    /**
     * 优惠券券号
     */
    private String couponNo;

    /**
     * 优惠券抵扣
     */
    private BigDecimal couponDiscount;

    /**
     * 会员优惠抵扣
     */
    private BigDecimal memberDiscount;

    /**
     * 积分优惠抵扣
     */
    private BigDecimal pointDiscount;

    /**
     * 是否包邮
     */
    private Boolean freeFreightFlag;

    /**
     * 运费
     */
    private BigDecimal freightAmount = BigDecimal.ZERO;

    //商友,线下电子券金额
    private BigDecimal offlineCouponAmount;
    //商友,线下折扣金额
    private BigDecimal offlineDiscountAmount;
    //商友,线下满减金额
    private BigDecimal offlineReduceAmount;


    /**
     * 订单总额
     * 商品总金额-营销优惠金额-优惠券抵扣-积分抵扣+运费（非包邮）
     * 新加商友 券 折扣 满减 金额
     * 新加的 会员折扣
     */
    public BigDecimal getOrderAmount(){
        BigDecimal orderAmount = this.originalAmount;
        if(orderAmount == null){
            orderAmount = BigDecimal.ZERO;
        }
        if(this.marketingDiscount != null){
            orderAmount = orderAmount.subtract(this.marketingDiscount);
        }
        if(this.couponDiscount != null){
            orderAmount = orderAmount.subtract(this.couponDiscount);
        }
        if(this.pointDiscount != null){
            orderAmount = orderAmount.subtract(this.pointDiscount);
        }
        if(this.memberDiscount != null){
            orderAmount = orderAmount.subtract(this.memberDiscount);
        }

        if((this.freeFreightFlag == null || Boolean.FALSE.equals(this.freeFreightFlag)) && this.freightAmount != null){
            orderAmount = orderAmount.add(this.freightAmount);
        }
        //商友 电子券 会员折扣 满减 金额
        if(this.offlineCouponAmount != null){
            orderAmount = orderAmount.subtract(this.offlineCouponAmount);
        }
        if(this.offlineDiscountAmount != null){
            orderAmount = orderAmount.subtract(this.offlineDiscountAmount);
        }
        if(this.offlineReduceAmount != null){
            orderAmount = orderAmount.subtract(this.offlineReduceAmount);
        }
        return orderAmount;
    }
}
