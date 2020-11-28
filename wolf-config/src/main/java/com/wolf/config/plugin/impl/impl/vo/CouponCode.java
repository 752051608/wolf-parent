package com.wolf.config.plugin.impl.impl.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 优惠券券码表
 * Created by songhanlin on 16/12/21.
 */
@Data
@Entity
@Table(name = "qm_coupon_code")
@Accessors(chain = true)
public class CouponCode {

    /**
     * 优惠券码id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coupon_code_id")
    private Long couponCodeId;

    /**
     * 券码
     */
    @Column(name = "code")
    private String code;

    /**
     * 优惠券id
     */
    @Column(name = "coupon_id")
    private Long couponId;

    /**
     * 优惠券活动id
     */
    @Column(name = "activity_id")
    private Long activityId;

    /**
     * 领取人id,同时表示领取状态
     */
    @Column(name = "customer_id")
    private Long customerId;

    /**
     * 使用状态
     * 0：未使用 1：使用
     */
    @Column(name = "use_status")
    private Integer useStatus;

//    /**
//     * 获得优惠券时间
//     */
//    @Column(name = "acquire_time", insertable = true, updatable = true)
//    @Convert(converter = LocalDateTimeConverter.class)
//    @JsonSerialize(using = CustomCommonDateSerializer.class)
//    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
//    private LocalDateTime acquireTime;
//
//    /**
//     * 使用时间
//     */
//    @Column(name = "use_date", insertable = false, updatable = true)
//    @Convert(converter = LocalDateTimeConverter.class)
//    @JsonSerialize(using = CustomCommonDateSerializer.class)
//    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
//    private LocalDateTime useDate;
//
//    /**
//     * 使用的订单编号
//     */
//    @Column(name = "order_code")
//    private String orderCode;
//
//    /***核销店铺(商户id,标示boss与第三方)**/
//    @Column(name = "verify_business_id")
//    private Long verifyBusinessId;
//    /**核销人,核销手机号(有可能店员核销)***/
//    @Column(name = "verify_mobile")
//    private String verifyMobile;
//    /**真实券适用场景：0线上、1线下***/
//    @Column(name = "use_real_scene")
//    private Integer useRealScene;
//    /***券码-条形码url**/
//    @Column(name = "coupon_bar_code")
//    private String couponBarCode;
//
//
//    /**
//     * 开始时间
//     */
//    @Column(name = "start_time", insertable = true, updatable = true)
//    @Convert(converter = LocalDateTimeConverter.class)
//    @JsonSerialize(using = CustomCommonDateSerializer.class)
//    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
//    private  LocalDateTime startTime;
//
//
//    /**
//     * 结束时间
//     */
//    @Column(name = "end_time", insertable = true, updatable = true)
//    @Convert(converter = LocalDateTimeConverter.class)
//    @JsonSerialize(using = CustomCommonDateSerializer.class)
//    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
//    private  LocalDateTime endTime;

}
