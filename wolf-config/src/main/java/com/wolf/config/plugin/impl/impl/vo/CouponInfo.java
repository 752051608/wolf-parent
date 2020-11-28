package com.wolf.config.plugin.impl.impl.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import javax.persistence.Convert;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * 优惠券表
 * Created by hong on 19/12/21.
 */
@Data
public class CouponInfo {

    /**
     * 优惠券主键Id
     */
    private Long couponId;

    /**
     * 优惠券名称
     */
    private String couponName;
    /**优惠券名称备注**/
    private String couponNameRemark;
    /**使用规则**/
    private String couponUseRule;
    /**券适用场景：0线上券、1门店券、2通用券；默认为线上券**/
    private Integer couponUseScene;
//    //券适用场景 返回前端使用,先保留
//    private Integer couponUseSceneDesc;
//    //线上券：仅线上购物使用,线下券：仅线下核销使用,通用券：线上购物，或线下核销都能使用
//    private Integer couponUseSceneDetail;

    /**
     * 优惠券分类
     */
    private Long couponCateId;

    /**
     * 优惠券生效时间
     */
    @Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
//    @JsonSerialize(using = CustomCommonDateSerializer.class)
//    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime startTime;

    /**
     * 优惠券过期时间
     */
    @Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
//    @JsonSerialize(using = CustomCommonDateSerializer.class)
//    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime endTime;

    /**
     * 购满多少钱
     */
    private BigDecimal fullbuyPrice;

    /**
     * 优惠减去多少钱
     */
    private BigDecimal reducePrice;

    /**
     * 商户id,标示boss与第三方
     */
    private Long thirdId;

    /**
     * 第三方优惠券标示
     */
    private Integer isThird;

    /**
     * 创建时间
     */
    @Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
//    @JsonSerialize(using = CustomCommonDateSerializer.class)
//    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime createTime;

    /**
     * 创建人
     */
    private Long createPerson;

    /**
     * 更新人
     */
    private Long updatePerson;

    /**
     * 更新日期
     */
    @Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
//    @JsonSerialize(using = CustomCommonDateSerializer.class)
//    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime updateTime;

    /**
     * 删除标示位(0,1),0未删除,1删除
     */
    private Integer delFlag;

    /**
     * 惠券覆盖范围种类0自营券1商家券2通用券
     */
    private int couponKind;

    /**
     * 存放其他信息
     */
    private Map others;

    //hashMap
    private Map<String, Object> extProps = new HashMap<>();

    public Map<String, Object> getExtProps() {
        return extProps;
    }
    public void setExtProps(Map<String, Object> extProps) {
        this.extProps = extProps;
    }

    /**
     * 有效时间类型(0全部，1品牌，2分类，3自定义货品)
     */
    private  int usefulTimeType;

    /**
     * 领取后几天失效
     */
    private  Long usefulTimeDay;

    /**
     * 最优优惠券 标志 true表示最优 false不是最优
     */
    private boolean bestOfCoupon;

    /**
     * 优惠券券码 id
     */
    private Long couponCodeId;
}
