package com.wolf.config.plugin.impl.impl;

import com.wolf.config.plugin.impl.IOrderConfirmPlugin;
import com.wolf.config.plugin.impl.IOrderCreatePlugin;
import com.wolf.config.plugin.impl.PluginRequest;
import com.wolf.config.plugin.impl.impl.vo.OrderConfirmRequest;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;

@Repository("couponActivityForAllComPlugin")
public class CouponActivityForAllComPlugin implements IOrderConfirmPlugin, IOrderCreatePlugin {
    @Override
    public void confirmOrderFilter(OrderConfirmRequest orderConfirmRequest, PluginRequest form) throws Exception {

    }

    @Override
    public void confirmOrderCalFilter(OrderConfirmRequest orderConfirmRequest, PluginRequest form) throws Exception {

    }

    @Override
    public void createOrderAfter(OrderConfirmRequest orderConfirmForm, PluginRequest form) throws Exception {

    }

//    @Autowired
//    private CouponCodeRepository couponCodeRepository;
//
////    @Autowired
////    private WmCouponService wmCouponService;
//
//    @Autowired
//    private CouponsService couponsService;
//
//    /**
//     * 添加优惠卷到购物车对象的extProp中，由于一个商品可能适用多个优惠卷，针对同一个购物车对象，此方法会多次调用
//     *
//     * @param shoppingCart 购物车对象
//     * @param couponInfo   优惠卷信息
//     */
//    @SuppressWarnings("unchecked")
//    private void addCouponInfo(WmShoppingCartVo shoppingCart, CouponInfo couponInfo) {
//        //已经下架和删除的商品不参与营销
//        if (shoppingCart.getGoodsInfo() == null || (!String.valueOf(DeleteFlag.NO.toValue()).equals(shoppingCart.getGoodsInfo().getDeleteFlag()))
//                || (!String.valueOf(AddedStatus.ADDED.toValue()).equals(shoppingCart.getGoodsInfo().getAddedStatus()))) {
//            return;
//        }
//
//        Object allCouponsObj = shoppingCart.getExtProps().get(Constants.ALL_COM_COUPONS);
//
//        List<CouponInfo> allCoupons;
//        if (null == allCouponsObj) {
//            allCoupons = new ArrayList<>();
//        } else {
//            allCoupons = (List<CouponInfo>) allCouponsObj;
//        }
//
//        // 如果购物车对象上没有添加过这个优惠卷，添加之
//        if (allCoupons.stream().noneMatch(coupon -> coupon.getCouponCodeId().equals(couponInfo.getCouponCodeId()))) {
//            allCoupons.add(couponInfo);
//        }
//
//        shoppingCart.getExtProps().put(Constants.ALL_COM_COUPONS, allCoupons);
//    }
//
//    /**
//     * 订单一级处理
//     * 为每个购物明细分配相应的优惠券
//     *
//     * @param confirmRequest 商户购物项参数
//     * @param form           营销信息
//     * @throws Exception
//     */
//    @Override
//    public void confirmOrderFilter(OrderConfirmRequest confirmRequest, PluginRequest form) throws Exception {
//        //秒杀活动 不可以使用优惠券
//        if (confirmRequest.getOrderBizType() != null && OrderBizTypeEnum.SEC_KILL_ORDER.ordinal() == confirmRequest.getOrderBizType()) {
//            return;
//        }
//        List<OrderConfirmSeller> sellers = confirmRequest.getSellerList();
//        if (CollectionUtils.isEmpty(sellers)) {
//            return;
//        }
//        //查询线上券和通用券,不查询门店券
//        List<WmEsCoupon> wmEsCoupons = couponsService.findByProgress(confirmRequest.getCustomerId());
//        if (CollectionUtils.isEmpty(wmEsCoupons)) {
//            return;
//        }
//        //过滤通用券
//        List<WmEsCoupon> wmEsCouponsUseable = wmEsCoupons.stream().filter(coupon -> ObjectUtils.equals(2, coupon.getCoupon()
//                .getCouponKind())).collect(Collectors.toList());
//        if (CollectionUtils.isEmpty(wmEsCouponsUseable)) {
//            return;
//        }
//
//        List<WmShoppingCartVo> allShoper = sellers.stream().map(OrderConfirmSeller::getShoppingCartList).flatMap(Collection::stream).collect(Collectors.toList());
//        confirmRequest.getSeller().setShoppingCartList(allShoper);
////        // 填充优惠卷列表到购物车对象中
////        sellers.forEach(cart -> fillCouponToOrderConfirm(cart, wmEsCouponsUseable, this::addCouponInfo));
//        // 填充优惠卷列表到购物车对象中
//        fillCouponToOrderConfirm(confirmRequest.getSeller(), wmEsCouponsUseable, this::addCouponInfo);
//    }
//
//
//    /**
//     * 订单二级处理
//     * 聚合优惠券，分组计算购物项
//     *
//     * @param confirmRequest 商户购物项参数
//     * @param form           营销信息
//     * @throws Exception
//     */
//    @Override
//    @SuppressWarnings("unchecked")
//    public void confirmOrderCalFilter(OrderConfirmRequest confirmRequest, PluginRequest form) throws Exception {
//        //秒杀活动 不可以使用优惠券
//        if (confirmRequest.getOrderBizType() != null && OrderBizTypeEnum.SEC_KILL_ORDER.ordinal() == confirmRequest.getOrderBizType()) {
//            return;
//        }
//        List<OrderConfirmSeller> sellers = confirmRequest.getSellerList();
//        if (CollectionUtils.isEmpty(sellers)) {
//            return;
//        }
//        //将非营销下的购物车，根据优惠券聚合购物项并统一计算进行放入相应的优惠券购物项
//        List<WmShoppingCartVo> shoppingCartsall = sellers.stream().map(OrderConfirmSeller::getShoppingCartList).flatMap(Collection::stream).collect(Collectors.toList());
//        if (CollectionUtils.isEmpty(shoppingCartsall)) {
//            return;
//        }
//        /**
//         * 优惠券Map，内容结构<优惠券编号,优惠券信息>
//         */
//        Map<Long, CouponInfo> couponInfoMap = new HashMap<>();
//        /**
//         * 同一优惠券的购物项Map，内容结构<优惠券编号,购物项列表>
//         */
//        Map<Long, List<WmShoppingCartVo>> couponShopCartMap = new HashMap<>();
//        /**
//         * 前端订单提交的券码Map
//         */
//        Map<Long, CouponCode> couponMap = confirmRequest.getCouponCodeMap();
//        boolean isHavCoupon = false;
//        if (MapUtils.isNotEmpty(couponMap) && null != couponMap.get(Long.valueOf(Constants.COUPON_KIND_COM_THIRDID))) {
//            isHavCoupon = true;
//        }
//
//        OrderConfirmSeller sellerall = confirmRequest.getSeller();
//
//        /**
//         * 仅提取含全场优惠券的购物项，并放至Map
//         */
//        for (WmShoppingCartVo shoppingCart : shoppingCartsall) {
//            Map<String, Object> extProp = shoppingCart.getExtProps();
//            //过滤没有含有全场优惠券
//            if (extProp == null || extProp.get(Constants.ALL_COM_COUPONS) == null) {
//                continue;
//            }
//            List<CouponInfo> allCoupons = (List<CouponInfo>) (extProp.get(Constants.ALL_COM_COUPONS));
//            if (CollectionUtils.isEmpty(allCoupons)) {
//                continue;
//            }
//            for (CouponInfo couponInfo : allCoupons) {
//                if (!(couponInfoMap.containsKey(couponInfo.getCouponCodeId()))) {
//                    couponInfoMap.put(couponInfo.getCouponCodeId(), couponInfo);
//                }
//
//                List<WmShoppingCartVo> shoppingCartList = couponShopCartMap.get(couponInfo.getCouponCodeId());
//                if (shoppingCartList == null) {
//                    shoppingCartList = new ArrayList<>();
//                }
//                shoppingCartList.add(shoppingCart);
//                couponShopCartMap.put(couponInfo.getCouponCodeId(), shoppingCartList);
//            }
//        }
//        //遍历营销Map，将同一营销下的购物车汇总，通过判断满足条件来计算优惠金额
//        for (CouponInfo couponInfo : couponInfoMap.values()) {
//            List<WmShoppingCartVo> shoppingCartList = couponShopCartMap.get(couponInfo.getCouponCodeId());
//            if (CollectionUtils.isEmpty(shoppingCartList)) {
//                continue;
//            }
//            //将符合的平摊小计进行合计
//            BigDecimal amountTotal = BigDecimal.ZERO;
//            for (WmShoppingCartVo shoppingCart : shoppingCartList) {
//                amountTotal = amountTotal.add(shoppingCart.getMarketingPrice());
//            }
//            //记录可用商品信息
//            HashMap<String, Object> others = new HashMap<>();
//            List<Long> filteredIds = shoppingCartList.stream().map(WmShoppingCartVo::getGoodsInfoId).
//                    collect(Collectors.toList());
//            List<String> filteredImages = shoppingCartList.stream().map(WmShoppingCartVo::getGoodsInfo).
//                    map(GoodsInfo::getImage).collect(Collectors.toList());
//            if (!CollectionUtils.isEmpty(filteredImages)) {
//                others.put("productimgs", filteredImages);
//                others.put("products", filteredIds);
//                couponInfo.setOthers(others);
//            }
//            // 对无门槛券(通用券)做特殊处理;商品金额必须大于满减的 金额 才可使用;1107支持 商品金额小于券金额,也可以使用
//            boolean zeroFlag = new BigDecimal(BigInteger.ZERO).compareTo(couponInfo.getFullbuyPrice()) == 0;
//            //三个金额 信息 集合处理,取最小值,作为赠券金额
//            List<BigDecimal> checkAmountList = new ArrayList<>();
//            //走原有逻辑--不走大通
//            if (confirmRequest.getConsumeMaxCouponPrice() != null && confirmRequest.getConsumeMaxCouponPrice().compareTo(new BigDecimal(0)) > 0) {
//                checkAmountList.add(confirmRequest.getConsumeMaxCouponPrice());
//            }
//            checkAmountList.add(couponInfo.getReducePrice());
//            checkAmountList.add(amountTotal);
//            BigDecimal minAmount = Collections.min(checkAmountList);
//            //计算 couponDiscount
//            BigDecimal couponDiscount = BigDecimal.ZERO;
//
//            if (zeroFlag || (amountTotal.compareTo(BigDecimal.ZERO) > 0 && amountTotal.compareTo(couponInfo.getFullbuyPrice()) >= 0)) {
//                //放开--限制
//                couponInfo.getExtProps().put("realDiscount", minAmount);
//                couponDiscount = minAmount;
//
//                //存放购物项通用优惠券优惠金额
//                Map<Long, BigDecimal> shoppingCartComCouponPriceMap = new HashMap<>();
//                //计算新平滩价格= 原平滩价格 - (原平滩价格/优惠券商品小计)*优惠券抵扣
//                BigDecimal ptDiscountTotal = BigDecimal.ZERO;
//                int size = shoppingCartList.size();
//                for (int i = 0; i < size; i++) {
//                    WmShoppingCartVo cart = shoppingCartList.get(i);
//                    BigDecimal marketGoodsPrice = cart.getMarketingPrice();
//                    //设定平摊价小计
//                    //最后一位，取剩余值
//                    if (i == size - 1) {
//                        BigDecimal ptDiscount = couponDiscount.subtract(ptDiscountTotal);
//                        shoppingCartComCouponPriceMap.put(cart.getShoppingCartId(), ptDiscount);//通用优惠券平摊金额
//                    } else {
//                        BigDecimal ptDiscount = null;
//                        if (amountTotal.compareTo(couponDiscount) == 0) {
//                            ptDiscount = marketGoodsPrice;
//                        } else {
//                            ptDiscount = marketGoodsPrice.divide(amountTotal, 6, BigDecimal.ROUND_DOWN).multiply(couponDiscount).setScale(2, BigDecimal.ROUND_HALF_UP);
//                        }
//                        shoppingCartComCouponPriceMap.put(cart.getShoppingCartId(), ptDiscount);//通用优惠券平摊金额
//                        ptDiscountTotal = ptDiscountTotal.add(ptDiscount);
//                    }
//                }
//                //将非营销下的购物车，根据优惠券聚合购物项并统一计算进行放入相应的优惠券购物项
//                for (OrderConfirmSeller seller : sellers) {
//                    List<WmShoppingCartVo> shoppingCarts = seller.getShoppingCartList();
//                    BigDecimal sellerDiscountTotal = BigDecimal.ZERO;
//                    //由ShoppingCart推算各seller分摊的通用优惠券金额
//                    for (WmShoppingCartVo shoppingCart : shoppingCarts) {
//                        if (shoppingCartComCouponPriceMap.containsKey(shoppingCart.getShoppingCartId())) {
//                            BigDecimal ptDiscount = shoppingCartComCouponPriceMap.get(shoppingCart.getShoppingCartId());
//                            sellerDiscountTotal = sellerDiscountTotal.add(ptDiscount);
//                        }
//                    }
//                    couponInfo.getExtProps().put("sellerDiscount" + seller.getSellerId(), sellerDiscountTotal);
//                }
//                sellerall.putCouponInfo(couponInfo);
//                //H5在更新购物项时处理优惠券信息，app有差异
//                sellers.get(0).putCouponInfo(couponInfo);
//            }
//
//            //计算每个优惠券还缺多少可用、可用商品
//            if (amountTotal.compareTo(BigDecimal.ZERO) > 0 && amountTotal.compareTo(couponInfo.getFullbuyPrice()) < 0) {
//                couponInfo.getExtProps().put("disDiscount", couponInfo.getFullbuyPrice().subtract(amountTotal)); //还差多少可用
//                couponInfo.getOthers().put("disDiscount", couponInfo.getFullbuyPrice().subtract(amountTotal));
//                sellerall.putUnableCouponInfos(couponInfo);
//                sellers.get(0).putUnableCouponInfos(couponInfo);
//            }
//        }
//
//        //此处是订单提交前验证优惠券并计算优惠券折扣
//        if (isHavCoupon && couponMap.containsKey(sellerall.getSellerId())) {
//
//            CouponCode couponCode = couponMap.get(Long.valueOf(Constants.COUPON_KIND_COM_THIRDID));//通用券使用一张
//            //如果该商家没有符合的优惠券，或者传递的优惠券不在符合优惠券的范围之内，则表示优惠券不是正确传递
//            if (MapUtils.isEmpty(sellerall.getCouponInfos()) || (!sellerall.getCouponInfos().containsKey(couponCode.getCouponCodeId()))) {
//                confirmRequest.error("请选择正确的优惠券");
//                //强行停止运行，反馈于前端
//                return;
//            }
//
//            //根据正在使用中的优惠券提取相关符合条件的购物项
//            List<WmShoppingCartVo> shoppingCartList = couponShopCartMap.get(couponCode.getCouponCodeId());
//
//            //合计优惠券商品小计
//            BigDecimal amountTotal = BigDecimal.ZERO;
//            for (WmShoppingCartVo shoppingCart : shoppingCartList) {
//                amountTotal = amountTotal.add(shoppingCart.getMarketingPrice());
//            }
//
//            Object realDiscount = sellerall.getCouponInfos().get(couponCode.getCouponCodeId()).getExtProps().get("realDiscount");
//            BigDecimal couponDiscount = (BigDecimal) realDiscount;
//
//            //存放购物项通用优惠券优惠金额
//            Map<Long, BigDecimal> shoppingCartComCouponPriceMap = new HashMap<>();
//            //计算新平滩价格= 原平滩价格 - (原平滩价格/优惠券商品小计)*优惠券抵扣
//            BigDecimal ptDiscountTotal = BigDecimal.ZERO;
//            int size = shoppingCartList.size();
//            for (int i = 0; i < size; i++) {
//                WmShoppingCartVo cart = shoppingCartList.get(i);
//                BigDecimal marketGoodsPrice = cart.getMarketingPrice();
//                //设定平摊价小计
//                //最后一位，取剩余值
//                if (i == size - 1) {
//                    BigDecimal ptDiscount = couponDiscount.subtract(ptDiscountTotal);
//                    shoppingCartComCouponPriceMap.put(cart.getShoppingCartId(), ptDiscount);//通用优惠券平摊金额
//                } else {
//                    BigDecimal ptDiscount = null;
//                    if (amountTotal.compareTo(couponDiscount) == 0) {
//                        ptDiscount = marketGoodsPrice;
//                    } else {
//                        ptDiscount = marketGoodsPrice.divide(amountTotal, 6, BigDecimal.ROUND_DOWN).multiply(couponDiscount).setScale(2, BigDecimal.ROUND_HALF_UP);
//                    }
//                    shoppingCartComCouponPriceMap.put(cart.getShoppingCartId(), ptDiscount);//通用优惠券平摊金额
//                    ptDiscountTotal = ptDiscountTotal.add(ptDiscount);
//                }
//
//            }
//
//            //将非营销下的购物车，根据优惠券聚合购物项并统一计算进行放入相应的优惠券购物项
//            for (OrderConfirmSeller seller : sellers) {
//                List<WmShoppingCartVo> shoppingCarts = seller.getShoppingCartList();
//
//                BigDecimal sellerDiscountTotal = BigDecimal.ZERO;
//                //由ShoppingCart推算各seller分摊的通用优惠券金额
//                for (WmShoppingCartVo shoppingCart : shoppingCarts) {
//                    BigDecimal marketGoodsPrice = shoppingCart.getMarketingPrice();
//                    if (shoppingCartComCouponPriceMap.containsKey(shoppingCart.getShoppingCartId())) {
//                        BigDecimal ptDiscount = shoppingCartComCouponPriceMap.get(shoppingCart.getShoppingCartId());
//                        shoppingCart.setMarketingPrice(marketGoodsPrice.subtract(ptDiscount));
//                        shoppingCart.setCommonCouponPrice(ptDiscount);
//                        sellerDiscountTotal = sellerDiscountTotal.add(ptDiscount);
//                        //表示该购物项是使用于优惠券
//                        shoppingCart.setHasCoupon(Boolean.TRUE);
//                    }
//                }
//
//                //设置商家的优惠券抵扣
//                OrderAmount orderAmount = seller.getOrderAmount();
//                if (orderAmount == null) {
//                    orderAmount = new OrderAmount();
//                }
//
//                //如果使用优惠券
//                if (sellerDiscountTotal.compareTo(BigDecimal.ZERO) > 0) {
//                    orderAmount.setCouponNo(couponCode.getCode());
//                }
//                orderAmount.setCouponDiscount(sellerDiscountTotal);
//                seller.setOrderAmount(orderAmount);
//            }
//
//        }
//
//        //清理
//        couponInfoMap.clear();
//        couponShopCartMap.clear();
//        sellerall.setShoppingCartList(null);//清理不需要展示项
//    }
//
//    /**
//     * 优惠券使用更新
//     *
//     * @param confirmRequest 商户购物项参数
//     * @param form           营销信息
//     * @throws Exception
//     */
//    @Override
//    public void createOrderAfter(OrderConfirmRequest confirmRequest, PluginRequest form) throws Exception {
//        /**
//         * 前端订单提交的券码Map
//         */
//        Map<Long, CouponCode> couponMap = confirmRequest.getCouponCodeMap();
//        if (MapUtils.isEmpty(couponMap)) {
//            return;
//        }
//        //订单标记通用券
//        CouponCode couponCode = couponMap.get(Long.valueOf(Constants.COUPON_KIND_COM_THIRDID));//通用券使用一张
//        if (null == couponCode) {
//            return;
//        }
//        //遍历所有使用通用优惠券订单
//        List<String> orderCodeList = confirmRequest.getOrders().stream().filter(order -> StringUtils.isNotBlank(order
//                .getCouponNo())).map(order -> order.getOrderCode()).collect(ArrayList<String>::new, ArrayList::add,
//                ArrayList::addAll);
//
//        //多笔订单编号以逗号分隔
//        String orderCodes = CollectionUtils.isEmpty(orderCodeList) ? "" : StringUtils.join(orderCodeList, ",");
//
//        if (StringUtils.isNotBlank(orderCodes)) {
//
//            List<OrderConfirmSeller> sellers = confirmRequest.getSellerList();
//            Long businessId = CollectionUtils.isEmpty(sellers) ? 0L : sellers.get(0).getSellerId();
//            //修改索引
//            couponCodeRepository.useCoupon(orderCodes, couponCode.getCouponCodeId(), businessId);
//            //更新索引
////            wmCouponService.useCoupon(couponCode.getActivityId(), confirmRequest.getCustomerId());
//        }
//
//    }
}
