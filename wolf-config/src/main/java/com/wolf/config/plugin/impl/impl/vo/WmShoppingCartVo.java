package com.wolf.config.plugin.impl.impl.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;

/**
 * 购物车视图对象
 * Created by daiyitian on 2017/2/23.
 */
@Data
public class WmShoppingCartVo extends  ShoppingCart {

    //货品信息
//    private GoodsInfo goodsInfo;

    //商品信息
//    private Spu spu;

    //扩展信息
    private Map<String,Object> extProps;

    //是否有库存
    private Integer hasStock;

    //是否有优惠券
    private Boolean hasCoupon;

    //第三方商铺编号
    private Long thirdShopId;

    //平摊积分
    private Long usedPoint;

    private Integer hasRushLimit;
    private Integer hasSecKillLimit;

    //平摊价
    private BigDecimal marketingPrice;

    private Integer hasGrouponLimit;
}
