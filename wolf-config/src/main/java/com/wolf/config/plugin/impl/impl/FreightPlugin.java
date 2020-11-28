package com.wolf.config.plugin.impl.impl;

import com.wolf.config.plugin.impl.IOrderConfirmPlugin;
import com.wolf.config.plugin.impl.PluginRequest;
import com.wolf.config.plugin.impl.impl.vo.OrderConfirmRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

;

/**
 * 运费插件
 */
@Slf4j
@Repository("freightPlugin")
public class FreightPlugin implements IOrderConfirmPlugin {
    @Override
    public void confirmOrderFilter(OrderConfirmRequest orderConfirmRequest, PluginRequest form) throws Exception {

    }

    @Override
    public void confirmOrderCalFilter(OrderConfirmRequest orderConfirmRequest, PluginRequest form) throws Exception {

    }

//    @Autowired
//    private FreightTemplateService freightTemplateService;
//
//    /**
//     * 运费设置
//     */
//    @Autowired
//    private FreightExpressRepository freightExpressRepository;
//
//    /**
//     * 运费模版
//     */
//    @Autowired
//    private FreightTemplateRepository freightTemplateRepository;
//
//    @Autowired
//    private SysDeliverypointService sysDeliverypointService;
//
//    @Autowired
//    private DistrictService districtService;
//    /**
//     * 订单一级处理
//     * 对不含包邮的购物项进行计算运费
//     * @param confirmRequest 商户购物项参数
//     * @param form    营销信息
//     * @throws Exception
//     */
//    @Override
//    public void confirmOrderFilter(OrderConfirmRequest confirmRequest, PluginRequest form) throws Exception {
//
//    }
//
//    /**
//     * 订单二级处理
//     * 对不含包邮的购物项进行计算运费
//     * @param confirmRequest 商户购物项参数
//     * @param form    营销信息
//     * @throws Exception
//     */
//    @Override
//    public void confirmOrderCalFilter(OrderConfirmRequest confirmRequest, PluginRequest form) throws Exception {
//        List<OrderConfirmSeller> sellers = confirmRequest.getSellerList();
//        if (CollectionUtils.isEmpty(sellers)) {
//            return;
//        }
//        log.info("计算运费插件=========");
//        //如果是自提订单不走下面逻辑 0快递 1自提
//        if (confirmRequest.getFreightType() != null && confirmRequest.getFreightType() ==1){
//
//            for (OrderConfirmSeller seller : sellers){
//                //运费调整
//                BigDecimal expressFee = BigDecimal.ZERO;
//                //统计商家的营销抵扣总金额
//               OrderAmount orderAmount = seller.getOrderAmount();
//                if (orderAmount == null) {
//                    orderAmount = new OrderAmount();
//                }
//                orderAmount.setFreightAmount(expressFee);
//                seller.setOrderAmount(orderAmount);
//                log.info("自提商品运费为0");
//            }
//            return;
//        }
//
//        for (OrderConfirmSeller seller : sellers){
//            BigDecimal expressFee=BigDecimal.ZERO;
//            //查询配送方式
//            FreightTemplate freightTemplate =freightTemplateRepository.selectByStoreIdAndDefault(seller.getSellerId());
//            if(freightTemplate==null){
//                throw new Exception("No any freight template available");
//            }
//            List<FreightExpress> freightExpress =freightExpressRepository.selectByFreightTemplateId(freightTemplate.getFreightTemplateId());
//            //如果包邮，跳过
//            if( CollectionUtils.isEmpty(seller.getShoppingCartList()) ){
//                expressFee=BigDecimal.ZERO;
//            }else{
//                Integer buyGoodsNum = 0;
//                BigDecimal buyGoodsWeight = BigDecimal.ZERO;
//                List<WmShoppingCartVo> shoppingCartList = seller.getShoppingCartList();
//                for (WmShoppingCartVo shoppingCart : shoppingCartList) {
//                    //如果商品包邮不计算购买数和重量
//                    if (null == shoppingCart.getExtProps() || null != shoppingCart.getExtProps()
//                            && Boolean.TRUE.equals(shoppingCart.getExtProps().get(Constants.CART_FREEDELIVERY))) {
//                        continue;
//                    }
//                    Integer t_buyGoodsNum = shoppingCart.getGoodsNum().intValue();
//                    //如果是套装，真实购买数=购买数*套装数
//                    if(shoppingCart.getFitId() != null && shoppingCart.getFitId().compareTo(0L) > 0){
//                        t_buyGoodsNum = t_buyGoodsNum * (shoppingCart.getFitNum()==null?1:shoppingCart.getFitNum().intValue());
//                    }
//                    //累计购买数
//                    buyGoodsNum += t_buyGoodsNum;
//                    //累计重量
//                    if(shoppingCart.getGoodsInfo().getWeight()!=null && shoppingCart.getGoodsInfo().getWeight().compareTo(BigDecimal.ZERO) == 1){
//                        buyGoodsWeight = buyGoodsWeight.add(shoppingCart.getGoodsInfo().getWeight().multiply(BigDecimal.valueOf(t_buyGoodsNum)));
//                    } else {
//                        buyGoodsWeight = buyGoodsWeight.add(new BigDecimal(0.25).multiply(BigDecimal.valueOf(t_buyGoodsNum)));
//                    }
//                }
//                if(null==seller.getFreightTemplate()){
//                    log.error("商家缺少运费模板信息" + seller.getSellerName());
//                    return;
//                }
//                Integer freightType = freightExpress.get(0).getExpressType();
//
//                if(MapUtils.isNotEmpty(confirmRequest.getFreightTypeMap()) && confirmRequest.getFreightTypeMap().get(seller.getSellerId()) != null){
//                    freightType = confirmRequest.getFreightTypeMap().get(seller.getSellerId());
//                }
//
//
//                //快递
//                if(0 == freightType){
//                    expressFee = freightTemplateService.calcExpressPrice(confirmRequest.getDistributionId(), seller.getSellerId(), new BigDecimal(String.valueOf(buyGoodsNum)),  buyGoodsWeight, form.getCityId(), freightType, seller.getOrderAmount().getOrderAmount(),confirmRequest.getIsFirstOrder());
//                }else{//自提
//                    Long cityId = form.getCityId();
//                    Long districtId = form.getDistrictId();
//
//                    //通过自提点获取地区信息
//                    Long deliveryId = confirmRequest.getDeliveryMap().get(seller.getSellerId());
//                    if(deliveryId != null) {
//                        //查询自提点信息
//                        SysDeliverypoint deliveryPoint = sysDeliverypointService.selectDeliveryPointById(deliveryId);
//
//                        //根据自提点，查询市区信息
//                        Optional<District> districtOptional = districtService.findDistrictById(deliveryPoint.getDistrictId());
//                        if(districtOptional.isPresent()){
//                            cityId = districtOptional.get().getCityId();
//                        }
//                        districtId = deliveryPoint.getDistrictId();
//                    }
//                    expressFee = freightTemplateService.calcExpressPrice(districtId, seller.getSellerId(), new BigDecimal(String.valueOf(buyGoodsNum)),  buyGoodsWeight, cityId, freightType,seller.getOrderAmount().getOrderAmount(), confirmRequest.getIsFirstOrder());
//                }
//            }
//
//
//            seller.setFreightExpress(freightExpress);
//            //统计商家的营销抵扣总金额
//            OrderAmount orderAmount = seller.getOrderAmount();
//            if (orderAmount == null) {
//                orderAmount = new OrderAmount();
//            }
//            orderAmount.setFreightAmount(expressFee);
//            seller.setOrderAmount(orderAmount);
//        }
//    }
}
