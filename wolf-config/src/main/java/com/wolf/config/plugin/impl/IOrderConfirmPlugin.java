package com.wolf.config.plugin.impl;

import com.wolf.config.plugin.impl.impl.vo.CouponInfo;
import com.wolf.config.plugin.impl.impl.vo.OrderConfirmRequest;
import com.wolf.config.plugin.impl.impl.vo.OrderConfirmSeller;
import com.wolf.config.plugin.impl.impl.vo.WmShoppingCartVo;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

/**
 * 订单创建插件
 * Created by dyt on 2016/12/2.
 */
public interface IOrderConfirmPlugin {


    /**
     * 订单一级处理
     * @param orderConfirmRequest   商户购物项参数
     * @param form      营销信息
     * @throws Exception
     */
    void confirmOrderFilter(OrderConfirmRequest orderConfirmRequest, PluginRequest form) throws Exception;

    /**
     * 订单二级处理
     * @param orderConfirmRequest   商户购物项参数
     * @param form      营销信息
     * @throws Exception
     */
    void confirmOrderCalFilter(OrderConfirmRequest orderConfirmRequest, PluginRequest form) throws Exception;

    /**
     * 填充购物车对象
     *
     * @param seller     订单确认对象
     * @param marketings 营销列表
     * @param fillFn     填充方法
     * @return 填充过的购物车对象
     */
//    default void fillOrderConfirm(OrderConfirmSeller seller, List<WmEsMarketing> marketings, BiFunction<WmShoppingCartVo, WmEsMarketing, WmShoppingCartVo> fillFn) {
//        final Long thirdId = seller.getSellerId();
//
//        List<WmShoppingCartVo> shoppingCartList = seller.getShoppingCartList();
//        for (WmShoppingCartVo shoppingCart : shoppingCartList) {
//            for (WmEsMarketing esMarketing : marketings) {
//                if (esMarketing.getThirdId().equals(thirdId)) {
//                    final List<Long> scopeIds = esMarketing.buildScopeIds();
//                    final Integer scopeType = esMarketing.getScopeType();
//                    if (orderConfirmMatchScope(shoppingCart, scopeIds, scopeType)) {
//                        fillFn.apply(shoppingCart, esMarketing);
//                        // 每个cart对象匹配到第一个活动后就跳出，继续下一个cart
//                        break;
//                    }
//                }
//            }
//        }
//    }
//
//    /**
//     * 填充优惠券到购物车对象中
//     *
//     * @param seller             订单确认对象
//     * @param esCoupon           优惠券活动
//     * @param fillFn             填充方法
//     */
//    default void fillCouponToOrderConfirm(OrderConfirmSeller seller, List<WmEsCoupon> esCoupon, BiConsumer<WmShoppingCartVo, CouponInfo> fillFn) {
//        final Long thirdId = seller.getSellerId();
//        for (WmEsCoupon wmEsCoupon : esCoupon) {
//            if (Objects.equals(wmEsCoupon.getCoupon().getThirdId(),thirdId)||wmEsCoupon.getCoupon().getCouponKind()==Constants.COUPON_KIND_COMMON) {
//                for (CouponMarketingScope scope : wmEsCoupon.getScopes()) {
//                    final List<Long> scopeIds = Collections.singletonList(scope.getScopeId());
//                    final Integer scopeType = scope.getScopeType();
//                    List<WmShoppingCartVo> shoppingCartList = seller.getShoppingCartList();
//                    for(WmShoppingCartVo shoppingCart:shoppingCartList){
//                        if (cartMatchCouponScope(shoppingCart, scopeIds, scopeType)) {
//                            fillFn.accept(shoppingCart, wmEsCoupon.getCoupon());
//                        }
//                    }
//                }
//            }
//        }
//    }
//
//    /**
//     * 购物车的商品对象和营销范围匹配
//     *
//     * @param shoppingCartVo     购物车对象
//     * @param scopeIds  营销范围值
//     * @param scopeType 营销范围类型
//     * @return 是否匹配
//     */
//    default boolean orderConfirmMatchScope(WmShoppingCartVo shoppingCartVo, List<Long> scopeIds, Integer scopeType) {
//        switch (scopeType) {
//            case Constants.SCOPE_ALL:
//                return true;
//            case Constants.SCOPE_BRAND://品牌
//                return orderConfirmMatchScope(scopeIds, shoppingCartVo.getSpu().getBrandId());
//            case Constants.SCOPE_CATE:
//                //BOSS分类
//                if (Long.valueOf(0).equals(shoppingCartVo.getGoodsInfo().getThirdShopId())) {
//                    return orderConfirmMatchScope(scopeIds, shoppingCartVo.getSpu().getCatId());
//                }
//                //第三方分类
//                else {
//                    return orderConfirmMatchScope(scopeIds, shoppingCartVo.getSpu().getThirdCateId());
//                }
//            case Constants.SCOPE_SPU: //指定商品
//                return orderConfirmMatchScope(scopeIds, shoppingCartVo.getGoodsInfo().getId());
//            default:
//                return false;
//        }
//    }
//
//    /**
//     * 购物车的商品对象和优惠券范围匹配
//     *
//     * @param shoppingCartVo    购物车对象
//     * @param scopeIds  优惠券范围值
//     * @param scopeType 优惠券范围类型
//     * @return 是否匹配
//     */
//    default boolean cartMatchCouponScope(WmShoppingCartVo shoppingCartVo, List<Long> scopeIds, Integer scopeType) {
//        switch (scopeType) {
//            case Constants.SCOPE_ALL:
//                return true;
//            case Constants.COUPON_SCOPE_BRAND://品牌
//                return orderConfirmMatchScope(scopeIds, shoppingCartVo.getSpu().getBrandId());
//            case Constants.COUPON_SCOPE_CATE:
//                    return orderConfirmMatchScope(scopeIds, shoppingCartVo.getSpu().getCatId())||orderConfirmMatchScope(scopeIds, shoppingCartVo.getSpu().getThirdCateId());
//            case Constants.COUPON_SCOPE_SPU: //指定商品
//                return orderConfirmMatchScope(scopeIds, shoppingCartVo.getGoodsInfo().getId());
//            default:
//                // logger.error("Unrecognized marketing.ScopeType:"+scopeType);
//                return false;
//        }
//    }

    /**
     * 值匹配比较
     *
     * @param scopeIds 营销的scopeId[]
     * @param scopeId  货品 对应的 scopeId
     * @return
     */
    default boolean orderConfirmMatchScope(List<Long> scopeIds, Long scopeId) {
        return null != scopeId && scopeIds.stream().anyMatch(scopeId::equals);
    }
}
