package com.wolf.config.plugin.impl;


import lombok.Data;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

;

/**
 * 插件公共Form
 * Created by dyt on 2017/11/12.
 *
 * @RequiredArgsConstructor: An argument is required if (it's not initialized inline and `final`) or `@NonNull`
 */
@Data
public class PluginRequest {

    /**
     * 当前登陆会员Id
     */
    private Long customerId;

    /**
     * 当前会员相关地区的仓库
     */
    private Long wareId;

    //区县id
    private Long districtId;

    //城市id
    private Long cityId;

    /**
     * 当前登陆会员等级
     */
//    private CustomerLevel customerLevel;
//
//    /**
//     * 所有进行中的营销
//     * 注：插件尽量避免改变内部数据
//     */
//    private List<WmEsMarketing> marketings;
//
//    /**
//     * 积分设置营销
//     * 注：插件尽量避免改变内部数据
//     */
//    private PointSet pointSet;
//
//    /**
//     * 优惠券活动
//     * 注：插件尽量避免改变内部数据
//     */
//    private List<WmEsCoupon> coupons;
//
//    /**
//     * 返回指定类型的优惠券列表
//     * @param couponType
//     * @return
//     */
//    public List<WmEsCoupon> getCouponActivityByType(int couponType) {
//        if (CollectionUtils.isEmpty(coupons)) {
//            return Collections.emptyList();
//        }
//        return coupons.stream()
//                .filter(couponActivity -> couponActivity.getCouponType().intValue() == couponType)
//                .sorted(
//                        (o1, o2) -> {
//                            final LocalDateTime createTime1 = o1.getCreateTime();
//                            final LocalDateTime createTime2 = o2.getCreateTime();
//                            if (createTime1 == null || createTime2 == null) {   // 如果createTime 不存在，id小的item小（即o1）
//                                return -1;
//                            } else {
//                                return createTime1.compareTo(createTime2);
//                            }
//                        })
//                .collect(Collectors.toList());
//    }
//
//    /**
//     * 返回指定类型的营销列表
//     *
//     * @param marketingType
//     * @return
//     */
//    public List<WmEsMarketing> getMarketingByType(int marketingType) {
//        if (CollectionUtils.isEmpty(marketings)) {
//            return Collections.emptyList();
//        }
//        return marketings.stream()
//                .filter(esMarketing -> new Integer(marketingType).equals(esMarketing.getMarketingType()))
//                .sorted(
//                        (o1, o2) -> {
//                            final LocalDateTime createTime1 = o1.getCreateTime();
//                            final LocalDateTime createTime2 = o2.getCreateTime();
//                            if (createTime1 == null || createTime2 == null) {   // 如果createTime 不存在，id小的item小（即o1）
//                                return -1;
//                            } else {
//                                return createTime1.compareTo(createTime2);
//                            }
//                        })
//                .collect(Collectors.toList());
//    }
//
//    /**
//     * 当前用户倾向的促销信息
//     */
//    private CusTendMarketing cusTendMarketing;
}
