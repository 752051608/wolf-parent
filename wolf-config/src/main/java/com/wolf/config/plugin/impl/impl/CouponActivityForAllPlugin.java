package com.wolf.config.plugin.impl.impl;

import com.wolf.config.plugin.impl.IOrderConfirmPlugin;
import com.wolf.config.plugin.impl.IOrderCreatePlugin;
import com.wolf.config.plugin.impl.PluginRequest;
import com.wolf.config.plugin.impl.impl.vo.OrderConfirmRequest;
import org.springframework.stereotype.Repository;

@Repository("couponActivityForAllPlugin")
public class CouponActivityForAllPlugin implements IOrderConfirmPlugin, IOrderCreatePlugin {
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
//        Object allCouponsObj = shoppingCart.getExtProps().get(Constants.ALL_COUPONS);
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
//        shoppingCart.getExtProps().put(Constants.ALL_COUPONS, allCoupons);
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
//
//        //过滤商家券
//        List<WmEsCoupon> wmEsCouponsUseable = wmEsCoupons.stream().filter(coupon -> !ObjectUtils.equals(2, coupon
//                .getCoupon()
//                .getCouponKind())).collect(Collectors.toList());
//        if (CollectionUtils.isEmpty(wmEsCouponsUseable)) {
//            return;
//        }
//        // 填充优惠卷列表到购物车对象中
//        sellers.forEach(cart -> fillCouponToOrderConfirm(cart, wmEsCouponsUseable, this::addCouponInfo));
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
//        /**
//         * 优惠券Map，内容结构<优惠券编号,优惠券信息>
//         */
//        Map<Long, CouponInfo> couponInfoMap = new HashMap<>();
//        /**
//         * 同一优惠券的购物项Map，内容结构<优惠券编号,购物项列表>
//         */
//        Map<Long, List<WmShoppingCartVo>> couponShopCartMap = new HashMap<>();
//
//        /**
//         * 前端订单提交的券码Map
//         */
//        Map<Long, CouponCode> couponMap = confirmRequest.getCouponCodeMap();
//        boolean isHavCoupon = false;
//        if (MapUtils.isNotEmpty(couponMap)) {
//            isHavCoupon = true;
//        }
//        //将非营销下的购物车，根据优惠券聚合购物项并统一计算进行放入相应的优惠券购物项
//        for (OrderConfirmSeller seller : sellers) {
//            List<WmShoppingCartVo> shoppingCarts = seller.getShoppingCartList();
//            /**
//             * 仅提取含全场优惠券的购物项，并放至Map
//             */
//            for (WmShoppingCartVo shoppingCart : shoppingCarts) {
//                Map<String, Object> extProp = shoppingCart.getExtProps();
//                //过滤没有含有全场优惠券
//                if (extProp == null || extProp.get(Constants.ALL_COUPONS) == null) {
//                    continue;
//                }
//
//                List<CouponInfo> allCoupons = (List<CouponInfo>) (extProp.get(Constants.ALL_COUPONS));
//                if (CollectionUtils.isEmpty(allCoupons)) {
//                    continue;
//                }
//
//                for (CouponInfo couponInfo : allCoupons) {
//                    if (!(couponInfoMap.containsKey(couponInfo.getCouponCodeId()))) {
//                        couponInfoMap.put(couponInfo.getCouponCodeId(), couponInfo);
//                    }
//
//                    List<WmShoppingCartVo> shoppingCartList = couponShopCartMap.get(couponInfo.getCouponCodeId());
//                    if (shoppingCartList == null) {
//                        shoppingCartList = new ArrayList<>();
//                    }
//                    shoppingCartList.add(shoppingCart);
//                    couponShopCartMap.put(couponInfo.getCouponCodeId(), shoppingCartList);
//                }
//            }
//
//            //遍历营销Map，将同一营销下的购物车汇总，通过判断满足条件来计算优惠金额
//            for (CouponInfo couponInfo : couponInfoMap.values()) {
//                List<WmShoppingCartVo> shoppingCartList = couponShopCartMap.get(couponInfo.getCouponCodeId());
//                if (CollectionUtils.isEmpty(shoppingCartList)) {
//                    continue;
//                }
//                //记录可用商品信息
//                HashMap<String, Object> others = new HashMap<>();
//                List<Long> filteredIds = shoppingCartList.stream().map(WmShoppingCartVo::getGoodsInfoId).
//                        collect(Collectors.toList());
//                List<String> filteredImages = shoppingCartList.stream().map(WmShoppingCartVo::getGoodsInfo).
//                        map(GoodsInfo::getImage).collect(Collectors.toList());
//                if (!CollectionUtils.isEmpty(filteredImages)) {
//                    others.put("productimgs", filteredImages);
//                    others.put("products", filteredIds);
//                    couponInfo.setOthers(others);
//                }
//
//                //将符合的平摊小计进行合计
//                BigDecimal amountTotal = BigDecimal.ZERO;
//                for (WmShoppingCartVo shoppingCart : shoppingCartList) {
//                    amountTotal = amountTotal.add(shoppingCart.getMarketingPrice());
//                }
//
//                //三个金额 信息 集合处理,取最小值,作为赠券金额
//                List<BigDecimal> checkAmountList = new ArrayList<>();
//                //走原有逻辑--不走大通
//                if (confirmRequest.getConsumeMaxCouponPrice() != null && confirmRequest.getConsumeMaxCouponPrice().compareTo(new BigDecimal(0)) > 0) {
//                    checkAmountList.add(confirmRequest.getConsumeMaxCouponPrice());
//                }
//                checkAmountList.add(couponInfo.getReducePrice());
//                checkAmountList.add(amountTotal);
//                BigDecimal minAmount = Collections.min(checkAmountList);
//
//
//                if (amountTotal.compareTo(BigDecimal.ZERO) > 0 && amountTotal.compareTo(couponInfo.getFullbuyPrice()) >= 0) {
//                    //放开--限制
//                    couponInfo.getExtProps().put("realDiscount", minAmount);
//                    seller.putCouponInfo(couponInfo);
//                }
//
//                //计算每个优惠券还缺多少可用、可用商品
//                if (amountTotal.compareTo(BigDecimal.ZERO) > 0 && amountTotal.compareTo(couponInfo.getFullbuyPrice()) < 0) {
//                    couponInfo.getExtProps().put("disDiscount", couponInfo.getFullbuyPrice().subtract(amountTotal)); //还差多少可用
//                    couponInfo.getOthers().put("disDiscount", couponInfo.getFullbuyPrice().subtract(amountTotal));
//                    seller.putUnableCouponInfos(couponInfo);
//                    sellers.get(0).putUnableCouponInfos(couponInfo);
//                }
//            }
//
//            //此处是订单提交前验证优惠券并计算优惠券折扣
//            if (isHavCoupon && couponMap.containsKey(seller.getSellerId())) {
//                //2019-0902避免NPE错误
//                if (MapUtils.isEmpty(couponShopCartMap)) {
//                    return;
//                }
//                CouponCode couponCode = couponMap.get(seller.getSellerId());
//                //如果该商家没有符合的优惠券，或者传递的优惠券不在符合优惠券的范围之内，则表示优惠券不是正确传递
//                if (MapUtils.isEmpty(seller.getCouponInfos()) || (!seller.getCouponInfos().containsKey(couponCode.getCouponCodeId()))) {
//                    confirmRequest.error("请选择正确的优惠券");
//                    //强行停止运行，反馈于前端
//                    return;
//                }
//
//                //根据正在使用中的优惠券提取相关符合条件的购物项
//                List<WmShoppingCartVo> shoppingCartList = couponShopCartMap.get(couponCode.getCouponCodeId());
//
//                //合计优惠券商品小计
//                BigDecimal amountTotal = BigDecimal.ZERO;
//                for (WmShoppingCartVo shoppingCart : shoppingCartList) {
//                    amountTotal = amountTotal.add(shoppingCart.getMarketingPrice());
//                }
//
//                Object realDiscount = seller.getCouponInfos().get(couponCode.getCouponCodeId()).getExtProps().get("realDiscount");
//                BigDecimal couponDiscount = (BigDecimal) realDiscount;
//                //计算新平滩价格= 原平滩价格 - (原平滩价格/优惠券商品小计)*优惠券抵扣
//                BigDecimal ptDiscountTotal = BigDecimal.ZERO;
//                int size = shoppingCartList.size();
//                for (int i = 0; i < size; i++) {
//                    WmShoppingCartVo cart = shoppingCartList.get(i);
//                    BigDecimal marketGoodsPrice = cart.getMarketingPrice();
//                    //设定平摊价小计
//                    //最后一位，取剩余值
//                    if (i == size - 1) {
//                        cart.setMarketingPrice(marketGoodsPrice.subtract(couponDiscount.subtract(ptDiscountTotal)));
//                    } else {
//                        BigDecimal ptDiscount = null;
//                        if (amountTotal.compareTo(couponDiscount) == 0) {
//                            ptDiscount = marketGoodsPrice;
//                        } else {
//                            ptDiscount = marketGoodsPrice.divide(amountTotal, 6, BigDecimal.ROUND_DOWN).multiply(couponDiscount).setScale(2, BigDecimal.ROUND_HALF_UP);
//                        }
//                        cart.setMarketingPrice(marketGoodsPrice.subtract(ptDiscount));
//                        ptDiscountTotal = ptDiscountTotal.add(ptDiscount);
//                    }
//
//                    //表示该购物项是使用于优惠券
//                    cart.setHasCoupon(Boolean.TRUE);
//                }
//
//                //设置商家的优惠券抵扣
//                OrderAmount orderAmount = seller.getOrderAmount();
//                if (orderAmount == null) {
//                    orderAmount = new OrderAmount();
//                }
//                orderAmount.setCouponNo(couponCode.getCode());
//                orderAmount.setCouponDiscount(couponDiscount);
//                seller.setOrderAmount(orderAmount);
//            }
//            //清理
//            couponInfoMap.clear();
//            couponShopCartMap.clear();
//        }
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
//
//        List<Order> orders = confirmRequest.getOrders();
//        for (Order order : orders) {
//            if (StringUtils.isNotBlank(order.getCouponNo())) {
//                CouponCode couponCode = couponMap.get(order.getBusinessId());
//                if (couponCode == null || (!order.getCouponNo().equals(couponCode.getCode()))) {
//                    continue;
//                }
//                List<OrderConfirmSeller> sellers = confirmRequest.getSellerList();
//                Long businessId = CollectionUtils.isEmpty(sellers) ? 0L : sellers.get(0).getSellerId();
//                //修改索引
//                couponCodeRepository.useCoupon(order.getOrderCode(), couponCode.getCouponCodeId(), businessId);
////                //更新索引
////                wmCouponService.useCoupon(couponCode.getActivityId(), order.getCustomerId());
//            }
//        }
//    }
}
