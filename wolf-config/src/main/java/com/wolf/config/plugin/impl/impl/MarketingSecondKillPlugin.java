package com.wolf.config.plugin.impl.impl;

import com.wolf.config.plugin.impl.IOrderConfirmPlugin;
import com.wolf.config.plugin.impl.IOrderCreatePlugin;
import com.wolf.config.plugin.impl.PluginRequest;
import com.wolf.config.plugin.impl.impl.vo.OrderConfirmRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

/**
 * 秒杀插件
 * 此处 独立
 * Created by hong on 2020/06/10.
 */
@Repository("marketingSecondKillPlugin")
@Slf4j
public class MarketingSecondKillPlugin implements IOrderConfirmPlugin, IOrderCreatePlugin {
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
//    private SecKillCustomerRepository secKillCustomerRepository;
//    @Autowired
//    private SecKillActivityRepository secKillActivityRepository;
//    @Autowired
//    private SecKillStockPriceRepository secKillStockPriceRepository;
//    @Autowired
//    private GoodsInfoDiscountService goodsInfoDiscountService;
//    @Autowired
//    private CustomerInfoService customerInfoService;
//
//    @Autowired
//    private SecKillCommonService secKillCommonService;
//    @Autowired
//    private SecKillCustomerService secKillCustomerService;
//
//    /**
//     * 订单一级处理--此方法待测试,是否在用
//     * 为每个购物明细分配相应的营销活动
//     *
//     * @param confirmRequest 商户购物项参数
//     * @param pluginRequest  营销信息
//     * @throws Exception
//     */
//    @Override
//    public void confirmOrderFilter(OrderConfirmRequest confirmRequest, PluginRequest pluginRequest) {
//        if (OrderBizTypeEnum.SEC_KILL_ORDER.ordinal() != confirmRequest.getOrderBizType()) {
//            log.info("=====>confirmOrderFilter, 不是秒杀订单");
//            return;
//        }
//        //当前日期 正在进行的秒杀活动,而且活动是通过的
//        List<SecKillActivity> secKillActivities = secKillActivityRepository.selectActivities4secKilling();
//        if (CollectionUtils.isEmpty(secKillActivities)) {
//            return;
//        }
//        List<Long> marketingIds = secKillActivities.stream().map(SecKillActivity::getMarketingId).collect(Collectors.toList());
//        //上架的spu
//        List<SecKillStockPrice> secKillStockPriceList = secKillStockPriceRepository.findByMarketingIds(marketingIds);
//        Map<Long, List<SecKillStockPrice>> productIdMap = secKillStockPriceList.stream().collect(Collectors.groupingBy(SecKillStockPrice::getProductId));
//        List<OrderConfirmSeller> sellers = confirmRequest.getSellerList();
//        if (CollectionUtils.isEmpty(sellers)) {
//            return;
//        }
//        //重要
//        for (OrderConfirmSeller seller : sellers) {
//            List<WmShoppingCartVo> shoppingCartList = seller.getShoppingCartList();
//            if (CollectionUtils.isEmpty(shoppingCartList)) {
//                continue;
//            }
//            //遍历购物项
//            for (WmShoppingCartVo cart : shoppingCartList) {
//                for (Map.Entry<Long, List<SecKillStockPrice>> map : productIdMap.entrySet()) {
//                    Long productId = map.getKey();
//                    List<SecKillStockPrice> value = map.getValue();
//                    value.stream().filter(a -> {
//                        //比对货品是否是秒杀货品
//                        if (cart.getGoodsInfoId().equals(productId)) {
//                            this.allotMarketing(cart, a);
//                        }
//                        return true;
//                    });
//                }
//            }
//        }
//    }
//
//    /**
//     * 订单一级处理内部方法
//     * 为每个购物明细分配相应的营销活动
//     */
//    private WmShoppingCartVo allotMarketing(WmShoppingCartVo shoppingCart, SecKillStockPrice secKillStockPrice) {
//        //套装商品优先参与
//        if (shoppingCart.getFitId() != null) {
//            return shoppingCart;
//        }
//        //已经下架和删除的商品不参与营销,或者商品从秒杀活动中下架了
//        //商品状态--0待审 1通过 2不通过 3秒杀下架 4秒杀上架
//        if (shoppingCart.getGoodsInfo() == null || (!String.valueOf(DeleteFlag.NO.toValue()).equals(shoppingCart.getGoodsInfo().getDeleteFlag()))
//                || (!String.valueOf(AddedStatus.ADDED.toValue()).equals(shoppingCart.getGoodsInfo().getAddedStatus())) ||
//                (!"1".equals(secKillStockPrice.getGoodsAuditStatus()))) {
//            return shoppingCart;
//        }
//        return shoppingCart;
//    }
//
//    /**
//     * 订单二级处理
//     * 聚合营销活动，分组计算购物项
//     *
//     * @param confirmRequest 商户购物项参数
//     * @param pluginRequest  营销信息
//     * @throws Exception
//     */
//    @Override
//    public void confirmOrderCalFilter(OrderConfirmRequest confirmRequest, PluginRequest pluginRequest) throws Exception {
//        if (OrderBizTypeEnum.SEC_KILL_ORDER.ordinal() != confirmRequest.getOrderBizType()) {
//            log.info("=====>confirmOrderCalFilter, 不是秒杀订单");
//            return;
//        }
//        //当前日期 正在进行的秒杀活动,而且活动是通过的
//        List<SecKillActivity> secKillActivities = secKillActivityRepository.selectActivities4secKilling();
//        if (CollectionUtils.isEmpty(secKillActivities)) {
//            return;
//        }
//        List<Long> marketingIds = secKillActivities.stream().map(SecKillActivity::getMarketingId).collect(Collectors.toList());
//        //上架的spu
//        List<SecKillStockPrice> secKillStockPriceList = secKillStockPriceRepository.findByMarketingIds(marketingIds);
//        Map<Long, List<SecKillStockPrice>> productIdMap = secKillStockPriceList.stream().collect(Collectors.groupingBy(SecKillStockPrice::getProductId));
//        List<OrderConfirmSeller> sellers = confirmRequest.getSellerList();
//        List<String> moreStockPro = new ArrayList<>();
//        /**超限查询,取消订单或者订单关闭 SecKillCustomer对应的delFlag删除标志*/
//        List<SecKillCustomer> secKillCustomers = secKillCustomerRepository.selectByCustomers(confirmRequest.getCustomerId(), marketingIds);
//        //将非营销下的购物车，根据营销聚合购物项并统一计算进行放入相应的营销项
//        for (OrderConfirmSeller seller : sellers) {
//            //统计商家的商品总金额和商品购买数,秒杀商品单独处理,不和其他商品一起下单
//            BigDecimal originalAmount = BigDecimal.ZERO;
//            BigDecimal totalDiscount = BigDecimal.ZERO;
//            Long buyGoodsNum = 0L;
//            List<WmShoppingCartVo> shoppingCarts = seller.getShoppingCartList();
//            for (WmShoppingCartVo shoppingCart : shoppingCarts) {
//                Long goodsInfoId = shoppingCart.getGoodsInfoId();
//                long goodsNum = shoppingCart.getGoodsNum();
//                buyGoodsNum = buyGoodsNum + goodsNum;
//                //拿到会员折扣率
//                BigDecimal goodsInfoDiscountRate = null;
//                Optional<CustomerInfo> customerInfoOpt = customerInfoService.getByCustomerId(confirmRequest.getCustomerId());
//                if (!customerInfoOpt.isPresent()) {
//                    log.info("会员折扣插件中,会员信息不存在,customerId={},goodsInfoId={}", confirmRequest.getCustomerId(), goodsInfoId);
//                    return;
//                }
//                for (Map.Entry<Long, List<SecKillStockPrice>> map : productIdMap.entrySet()) {
//                    Long productId = map.getKey();
//                    List<SecKillStockPrice> value = map.getValue();
//                    for (SecKillStockPrice secKillStockPrice : value) {
//                        //秒杀货品,对应的仓库,进行价格处理
//                        if (shoppingCart.getGoodsInfoId().compareTo(productId) == 0) {
//                            BigDecimal price = secKillStockPrice.getSecKillPrice();
//                            shoppingCart.getGoodsInfo().setPreferPrice(price);//设定销售价
//                            shoppingCart.getGoodsInfo().setCostPrice(price);//设定原价
//                            goodsInfoDiscountRate = goodsInfoDiscountService.getGoodsInfoDiscount(customerInfoOpt.get().getPointLevelName(), goodsInfoId,
//                                    GoodsBizTypeEnum.SEC_KILL_GOODS.ordinal(), secKillStockPrice.getMarketingId());
//                            BigDecimal preferPrice = secKillStockPrice.getSecKillPrice();
//                            //获取秒杀价
//                            BigDecimal newPrice = preferPrice;
//                            if (goodsInfoDiscountRate != null) {
//                                newPrice = preferPrice.multiply(goodsInfoDiscountRate).setScale(2, BigDecimal.ROUND_HALF_UP);
//                            }
//                            shoppingCart.getGoodsInfo().setPreferPrice(preferPrice);
//                            //todo 设定平摊价小计
//                            shoppingCart.setMarketingPrice(newPrice.multiply(BigDecimal.valueOf(shoppingCart.getGoodsNum())));
//                            //计算商品原始价格,用于商品显示
//                            originalAmount = originalAmount.add(preferPrice.multiply(BigDecimal.valueOf(shoppingCart.getGoodsNum())));
//                            //累积折扣
//                            totalDiscount = totalDiscount.add(preferPrice.subtract(newPrice).multiply(BigDecimal.valueOf(shoppingCart.getGoodsNum())));
//                            //超限查询验证
//                            int secKillCount = 0;
//                            if (!CollectionUtils.isEmpty(secKillCustomers)) {
//                                secKillCount = secKillCustomers.stream().filter(secKillCustomer -> shoppingCart.getGoodsInfoId().equals(secKillCustomer.getProductId()) && secKillStockPrice.getMarketingId().equals(secKillCustomer.getMarketingId()))
//                                        .mapToInt(SecKillCustomer::getGoodsNum).sum();
//                            }
//                            if ("1".equals(secKillStockPrice.getLimitFlag()) && secKillCount + shoppingCart.getGoodsNum().intValue() > secKillStockPrice.getLimitCount()) {
//                                shoppingCart.setHasSecKillLimit(Constants.YES);
//                                moreStockPro.add("秒杀超过限购数: " + shoppingCart.getGoodsInfo().getName());
//                            }
//                        }
//                    }
//                }
//            }
//            //秒杀价的总折扣在商品总金额中扣除
//            OrderAmount orderAmount = seller.getOrderAmount();
//            if (orderAmount == null) {
//                orderAmount = new OrderAmount();
//            }
//            orderAmount.setBuyGoodsNum(buyGoodsNum);
//            if (originalAmount == null) {
//                originalAmount = BigDecimal.ZERO;
//            }
//            //秒杀商品单独下单,不与普通商品一起
//            if (BigDecimal.ZERO.compareTo(originalAmount) != 0L) {
//                orderAmount.setOriginalAmount(originalAmount);
//                orderAmount.setMemberDiscount(totalDiscount);
//                seller.setOrderAmount(orderAmount);
//            }
//            if (!moreStockPro.isEmpty()) {
//                //超限了
//                confirmRequest.moreError(-3, moreStockPro);
//            }
//        }
//    }
//
//    /**
//     * 订单后续处理，批量新增秒杀记录表
//     *
//     * @param confirmRequest 商户购物项参数
//     * @param pluginRequest  营销信息
//     * @throws Exception
//     */
//    @Override
//    public void createOrderAfter(OrderConfirmRequest confirmRequest, PluginRequest pluginRequest) {
//        if (OrderBizTypeEnum.SEC_KILL_ORDER.ordinal() != confirmRequest.getOrderBizType()) {
//            log.info("=====>createOrderAfter, 不是秒杀订单");
//            return;
//        }
//        List<OrderGoods> orderGoodsList = confirmRequest.getOrderGoodsList();
//        if (CollectionUtils.isEmpty(orderGoodsList)) {
//            return;
//        }
//        for (OrderGoods orderGoods : orderGoodsList) {
//            SecKillStockPrice secKillStockPrice = secKillCommonService.selectActivityInSecKilling(orderGoods.getGoodsInfoId());
//            if (secKillStockPrice == null) {
//                return;
//            }
//            //锁定秒杀库存
//            Long buyNumber = orderGoods.getGoodsInfoNum();
//            secKillCommonService.addSecKillLockStock(secKillStockPrice.getId(), buyNumber, orderGoods.getGoodsInfoId());
//            //用户秒杀记录表,此处对限购判断要求高
//            Long orderId = orderGoods.getOrderId();
//            Long goodsInfoNum = orderGoods.getGoodsInfoNum();
//            SecKillCustomer secCustomer = new SecKillCustomer();
//            secCustomer.setMarketingId(secKillStockPrice.getMarketingId());
//            secCustomer.setCustomerId(confirmRequest.getCustomerId());
//            secCustomer.setGoodsId(orderGoods.getGoodsId());
//            secCustomer.setProductId(orderGoods.getGoodsInfoId());
//            secCustomer.setGoodsNum(goodsInfoNum.intValue());
//            secCustomer.setDelFlag(0_STRING);
//            secCustomer.setCreateTime(new Date());
//            secCustomer.setOrderId(orderId);
//            secKillCustomerService.save(secCustomer);
//        }
//    }
}