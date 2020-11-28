package com.wolf.config.plugin.impl.impl;

import com.wolf.config.plugin.impl.IOrderConfirmPlugin;
import com.wolf.config.plugin.impl.IOrderCreatePlugin;
import com.wolf.config.plugin.impl.PluginRequest;
import com.wolf.config.plugin.impl.impl.vo.OrderConfirmRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

;

/**
 * 分仓价插件
 * Created by dyt on 2016/12/8.
 *
 * 砂之船项目--不使用此插件,使用memberDiscountPlugin
 */
@Repository("warePricePlugin")
public class WarePricePlugin implements IOrderConfirmPlugin, IOrderCreatePlugin {
    private static final Logger LOGGER = LoggerFactory.getLogger(WarePricePlugin.class);

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
//    private GoodsStockRepository goodsStockRepository;
//
//    @Autowired
//    private GoodsInfoRepository goodsInfoRepository;
//
//    @Autowired
//    private SecKillCommonService secKillCommonService;
//
//
//    /**
//     * 订单一级处理
//     * 1.为每个购物明细的价格设为分仓价
//     * 2.合计商品总金额
//     *
//     * @param confirmRequest 商户购物项参数
//     * @param pluginRequest  营销信息
//     * @throws Exception
//     */
//    @Override
//    public void confirmOrderFilter(OrderConfirmRequest confirmRequest, PluginRequest pluginRequest) throws Exception {
//        List<OrderConfirmSeller> sellers = confirmRequest.getSellerList();
//
//        for (OrderConfirmSeller seller : sellers) {
//            //统计商家的商品总金额和商品购买数
//            BigDecimal originalAmount = BigDecimal.ZERO;
//            Long buyGoodsNum = 0L;
//            if (!CollectionUtils.isEmpty(seller.getShoppingCartList())) {
//                for (WmShoppingCartVo cart : seller.getShoppingCartList()) {
//                    BigDecimal price = cart.getGoodsInfo().getPreferPrice();
//
//                    if (cart.getThirdShopId().equals(Long.valueOf(0))) {
//                        List<GoodsStock> wares = cart.getGoodsInfo().getGoodsStocks();
//                        if (!CollectionUtils.isEmpty(wares)) {
//                            for (GoodsStock productWare : wares) {
//                                if (pluginRequest.getWareId().equals(productWare.getWareHouseId())) {
//                                    price = productWare.getPrice();
//                                    cart.getGoodsInfo().setStock(productWare.getStock());
//                                    //会员取会员价格,vip价格必须大于0,可以售卖;因大通返回的可能是null或0
//                                    if (confirmRequest.getMemberId() != null && productWare.getVipPrice() != null && productWare.getVipPrice().compareTo(BigDecimal.ZERO) == 1) {
//                                        price = productWare.getVipPrice();
//                                    }
//                                }
//                            }
//                        }
//                    }
//
//                    cart.getGoodsInfo().setPreferPrice(price);//设定销售价
//                    cart.getGoodsInfo().setCostPrice(price);//设定原价
//
//                    long t_buyGoodsNum = cart.getGoodsNum();
//                    //如果是套装，真实购买数=购买数*套装数
//                    if (cart.getFitId() != null && cart.getFitId().compareTo(0L) > 0) {
//                        t_buyGoodsNum = t_buyGoodsNum * (cart.getFitNum() == null ? 1 : cart.getFitNum());
//                    }
//
//                    //设定平摊价小计
//                    cart.setMarketingPrice(price.multiply(BigDecimal.valueOf(t_buyGoodsNum)));
//
//                    buyGoodsNum = buyGoodsNum + t_buyGoodsNum;
//                    originalAmount = originalAmount.add(cart.getMarketingPrice());
//                }
//
//                //设定统计商家的商品总金额
//                OrderAmount orderAmount = seller.getOrderAmount();
//                if (orderAmount == null) {
//                    orderAmount = new OrderAmount();
//                }
//                orderAmount.setOriginalAmount(originalAmount);
//                orderAmount.setBuyGoodsNum(buyGoodsNum);
//                seller.setOrderAmount(orderAmount);
//            }
//        }
//    }
//
//    /**
//     * 订单二级处理
//     *
//     * @param confirmRequest 商户购物项参数
//     * @param pluginRequest  营销信息
//     * @throws Exception
//     */
//    @Override
//    public void confirmOrderCalFilter(OrderConfirmRequest confirmRequest, PluginRequest pluginRequest) throws Exception {
//
//    }
//
//    /**
//     * 订单提交后续处理(扣除库存)
//     *
//     * @param confirmRequest 商户购物项参数
//     * @param pluginRequest  营销信息
//     * @throws Exception
//     */
//    @Override
//    public void createOrderAfter(OrderConfirmRequest confirmRequest, PluginRequest pluginRequest) throws Exception {
//        /**
//         * 批量扣除库存
//         * 将Map<商品编号，购买数>进行分组为<购买数,List<商品编号>>
//         * 批量更新于库存
//         */
//        Map<Long, Long> goodsBuyNumMap = confirmRequest.getGoodsBuyNumMap();
//        Map<Long, List<Long>> bossProNum = new HashMap<>();
//        Map<Long, List<Long>> thirdProNum = new HashMap<>();
//        Long bossId = Long.valueOf(0_STRING);
//
//        //验证所有提取的购买商品
//        for (Map.Entry<Long, Long> entry : goodsBuyNumMap.entrySet()) {
//            Long goodsInfoId = entry.getKey();
//            Long buyNum = entry.getValue();
//            List<Long> productIds = bossProNum.get(buyNum);
//            if (bossId.equals(confirmRequest.getGoodsThirdIdMap().get(goodsInfoId))) {
//                if (productIds == null) {
//                    productIds = new ArrayList<>();
//                }
//                productIds.add(goodsInfoId);
//                bossProNum.put(buyNum, productIds);
//            } else {
//                List<Long> productsIds = thirdProNum.get(buyNum);
//                if (productsIds == null) {
//                    productsIds = new ArrayList<>();
//                }
//                productsIds.add(goodsInfoId);
//                thirdProNum.put(buyNum, productsIds);
//            }
//            //打印下当时下单的库存，检查并发时的库存是否正常
//            LOGGER.info("=====>createOrderAfter, productIds=" + productIds + ",buyNum=" + buyNum);
//        }
//        //0615 是否是秒杀商品,进行特殊处理,不考虑购物车非秒杀商品
//        Map<Long, List<Long>> secKIllNum = new HashMap<>();
//        secKIllNum.putAll(bossProNum);
//        secKIllNum.putAll(thirdProNum);
//        boolean hasSecKillFlag = false;
//        for (Map.Entry<Long, List<Long>> entry : secKIllNum.entrySet()) {
//            List<Long> productIds = entry.getValue();
//            for (Long productId : productIds) {
//                SecKillStockPrice secKillStockPrice = secKillCommonService.selectActivityInSecKilling(productId);
//                if (secKillStockPrice != null) {
//                    hasSecKillFlag = true;
//                    //锁定秒杀库存
//                    Long buyNumber = entry.getKey();
//                    secKillCommonService.addSecKillLockStock(secKillStockPrice.getId(), buyNumber,productId);
//                }
//            }
//        }
//        if(hasSecKillFlag){
//            return;
//        }
//        //BOSS商家扣货品库存表
//        if (MapUtils.isNotEmpty(bossProNum)) {
//            for (Map.Entry<Long, List<Long>> entry : bossProNum.entrySet()) {
//                this.goodsStockRepository.batchSubStockByProduct(entry.getValue(), pluginRequest.getWareId(), entry.getKey());
//            }
//        }
//        //第三方商家扣货品表
//        if (MapUtils.isNotEmpty(thirdProNum)) {
//            for (Map.Entry<Long, List<Long>> entry : thirdProNum.entrySet()) {
//                this.goodsInfoRepository.subThirdStock(entry.getValue(), entry.getKey());
//            }
//        }
//    }
}


