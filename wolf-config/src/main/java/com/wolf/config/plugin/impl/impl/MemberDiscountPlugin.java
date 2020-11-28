package com.wolf.config.plugin.impl.impl;

import com.wolf.config.plugin.impl.IOrderConfirmPlugin;
import com.wolf.config.plugin.impl.IOrderCreatePlugin;
import com.wolf.config.plugin.impl.PluginRequest;
import com.wolf.config.plugin.impl.impl.vo.OrderConfirmRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.*;


/**
 * 会员货品折扣金额计算
 * Created by dyt on 2020/10/26.
 */
@Repository("memberDiscountPlugin")
@Slf4j
public class MemberDiscountPlugin implements IOrderConfirmPlugin, IOrderCreatePlugin {
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
//    private SecKillCommonService secKillCommonService;
//    @Autowired
//    private GoodsInfoDiscountService goodsInfoDiscountService;
//    @Autowired
//    private CustomerInfoService customerInfoService;
//
//    @Autowired
//    private GoodsStockRepository goodsStockRepository;
//
//    @Autowired
//    private GoodsInfoRepository goodsInfoRepository;
//
//    /**
//     * 订单一级处理
//     * 为每个购物明细分配相应的营销活动
//     *
//     * @param confirmRequest 商户购物项参数
//     * @param form           营销信息
//     * @throws Exception
//     */
//    @Override
//    public void confirmOrderFilter(OrderConfirmRequest confirmRequest, PluginRequest form) {
//        if (OrderBizTypeEnum.GENERAL_ORDER.ordinal() != confirmRequest.getOrderBizType()) {
//            log.info("=====>confirmOrderFilter, 不是普通订单");
//            return;
//        }
//
//    }
//
//    /**
//     * 订单二级处理
//     * 聚合营销活动，分组计算购物项
//     *
//     * @param confirmRequest 商户购物项参数
//     * @param form           营销信息
//     * @throws Exception
//     */
//    @Override
//    public void confirmOrderCalFilter(OrderConfirmRequest confirmRequest, PluginRequest form) {
//        if (OrderBizTypeEnum.GENERAL_ORDER.ordinal() != confirmRequest.getOrderBizType()) {
//            log.info("=====>confirmOrderCalFilter, 不是普通订单");
//            return;
//        }
//        List<OrderConfirmSeller> sellers = confirmRequest.getSellerList();
//        //将非营销下的购物车，根据营销聚合购物项并统一计算进行放入相应的营销项
//        for (OrderConfirmSeller seller : sellers) {
//            //统计商家的商品总金额和商品购买数
//            BigDecimal originalAmount = BigDecimal.ZERO;
//            Long buyGoodsNum = 0L;
//            //统计商家的营销抵扣总金额
//            OrderAmount orderAmount = seller.getOrderAmount();
//            if (orderAmount == null) {
//                orderAmount = new OrderAmount();
//            }
//            //应付金额计算
//            BigDecimal memberDiscount = BigDecimal.ZERO;
//            List<WmShoppingCartVo> shoppingCarts = seller.getShoppingCartList();
//            for (WmShoppingCartVo shoppingCart : shoppingCarts) {
//                long goodsNum = shoppingCart.getGoodsNum();
//                buyGoodsNum = buyGoodsNum + goodsNum;
//
//                GoodsInfo goodsInfo = shoppingCart.getGoodsInfo();
//                BigDecimal oldPrice = goodsInfo.getPreferPrice();
//                Long goodsInfoId = shoppingCart.getGoodsInfoId();
//                //20201102 分仓价格处理,目前砂之船没有自营商品,暂时不处理
////                processWarePrice(confirmRequest, form, shoppingCart, goodsInfo);
//                BigDecimal price = goodsInfo.getPreferPrice();
//                shoppingCart.getGoodsInfo().setPreferPrice(price);//设定销售价
//                shoppingCart.getGoodsInfo().setCostPrice(price);//设定原价
//                //拿到会员折扣率
//                BigDecimal goodsInfoDiscountRate = null;
//                Optional<CustomerInfo> customerInfoOpt = customerInfoService.getByCustomerId(confirmRequest.getCustomerId());
//                if (!customerInfoOpt.isPresent()) {
//                    log.info("会员折扣插件中,会员信息不存在,customerId={},goodsInfoId={}", confirmRequest.getCustomerId(), goodsInfoId);
//                    return;
//                }
//                //todo 拼团除外,待拼团接入
//                SecKillStockPrice secKillStockPrice = secKillCommonService.selectActivityInSecKilling(goodsInfoId);
//                //秒杀库存为0，即该sku不参与秒杀
//                if (secKillStockPrice == null || (secKillStockPrice != null && secKillStockPrice.getSecKillStock() == 0L)) {
//                    //此处是普通商品,拼团商品--todo待处理
//                    goodsInfoDiscountRate = goodsInfoDiscountService.getGoodsInfoDiscount(customerInfoOpt.get().getPointLevelName(), goodsInfoId,
//                            GoodsBizTypeEnum.GENERAL_GOODS.ordinal(), null);
//                }
//                if (secKillStockPrice != null && secKillStockPrice.getSecKillStock() != 0L) {
//                    continue;
//                }
//                if (goodsInfoDiscountRate == null) {
//                    //设定平摊小计,可退款金额--退货时使用
//                    shoppingCart.setMarketingPrice(oldPrice.multiply(BigDecimal.valueOf(goodsNum)));
//                    //计算商品原始价格,用于商品显示
//                    originalAmount = originalAmount.add(oldPrice.multiply(BigDecimal.valueOf(goodsNum)));
//                    continue;
//                }
//                BigDecimal newPrice = oldPrice.multiply(goodsInfoDiscountRate).setScale(2, BigDecimal.ROUND_HALF_UP);
//                //计算是累积 + (原价-会员货品价)*购买数量
//                memberDiscount = memberDiscount.add(oldPrice.subtract(newPrice).multiply(BigDecimal.valueOf(goodsNum)));
//                //设定平摊小计,可退款金额--退货时使用
//                shoppingCart.setMarketingPrice(newPrice.multiply(BigDecimal.valueOf(goodsNum)));
//                //计算商品原始价格,用于商品显示
//                originalAmount = originalAmount.add(oldPrice.multiply(BigDecimal.valueOf(goodsNum)));
//            }
//            orderAmount.setMemberDiscount(memberDiscount);
//            orderAmount.setOriginalAmount(originalAmount);
//            orderAmount.setBuyGoodsNum(buyGoodsNum);
//            seller.setOrderAmount(orderAmount);
//        }
//    }
//
//    private void processWarePrice(OrderConfirmRequest confirmRequest, PluginRequest form, WmShoppingCartVo shoppingCart, GoodsInfo goodsInfo) {
//        BigDecimal price = goodsInfo.getPreferPrice();
//        if (shoppingCart.getThirdShopId().equals(Long.valueOf(0))) {
//            List<GoodsStock> wares = shoppingCart.getGoodsInfo().getGoodsStocks();
//            if (!CollectionUtils.isEmpty(wares)) {
//                for (GoodsStock productWare : wares) {
//                    if (form.getWareId().equals(productWare.getWareHouseId())) {
//                        price = productWare.getPrice();
//                        shoppingCart.getGoodsInfo().setStock(productWare.getStock());
//                        //会员取会员价格,vip价格必须大于0,可以售卖;因大通返回的可能是null或0
//                        if (confirmRequest.getMemberId() != null && productWare.getVipPrice() != null && productWare.getVipPrice().compareTo(BigDecimal.ZERO) == 1) {
//                            price = productWare.getVipPrice();
//                        }
//                    }
//                }
//            }
//        }
//        shoppingCart.getGoodsInfo().setPreferPrice(price);//设定销售价
//        shoppingCart.getGoodsInfo().setCostPrice(price);//设定原价
//    }
//
//    @Override
//    public void createOrderAfter(OrderConfirmRequest confirmRequest, PluginRequest pluginRequest) throws Exception {
//        /**
//         * 批量扣除库存
//         * 将Map<商品编号，购买数>进行分组为<购买数,List<商品编号>>
//         * 批量更新于库存
//         */
//        if (OrderBizTypeEnum.GENERAL_ORDER.ordinal() != confirmRequest.getOrderBizType()) {
//            log.info("=====>createOrderAfter, 普通订单");
//            return;
//        }
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
//            log.info("=====>createOrderAfter, productIds=" + productIds + ",buyNum=" + buyNum);
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


