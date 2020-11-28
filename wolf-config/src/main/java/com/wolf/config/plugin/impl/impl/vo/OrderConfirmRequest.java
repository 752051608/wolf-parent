package com.wolf.config.plugin.impl.impl.vo;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 订单交互FORM
 * Created by dyt on 2017/1/20.
 */
@Data
public class OrderConfirmRequest {

    /**
     * 购物车数据集
     */
    private List<WmShoppingCartVo> shoppingCarts;

    /**
     * 收货人地址编号
     */
    private Long addressId;

    /**
     * 自提点编号
     */
    private Long deliveryId;

    /**
     * 当前会员id
     */
    private Long customerId;

    /**
     * 购物车编号
     */
    private Long[] shoppingCartIds;

    /**
     * 是否是立即购买
     * 1:是，从session提取购物项
     * 0:否，从购物车中提取购物项
     */
    private Integer isAgainBuy;

    /**
     * 使用积分
     */
    private Long point;

    /**
     * 支付方式
     */
    private Long payType;


    /**
     * id
     */
    private Long distributionId;

    /**
     * 订单确认结果结果
     */
    List<OrderConfirmSeller> sellerList;

    /**
     * 备注Map
     * 内容为：<商家编号,备注>，页面可以提交多个备注
     */
    Map<Long, String> remarkMap;

    /**
     * 发票类型
     */
    private Integer invoiceType;

    /**
     * 发票抬头
     */
    private String invoiceTitle;

    /**
     * 发票内容
     */
    private String invoiceContent;

    /**
     * 发票信息
     */
//    private OrderTaxInvoice orderTaxInvoice;

    /**
     * 金额验证
     * 比对订单金额是否一致
     */
    private String amt;

    /**
     * 订单类型
     * 0：PC订单 1：手机订单 2：微信订单
     * 订单终端来源 0：PC 1：h5 2：移动 3:小程序
     */
    private String orderSource;

    /**
     * 优惠券Map
     * 内容为：<商家编号,优惠券码>，页面可以提交多个优惠券
     */
    Map<Long, CouponCode> couponCodeMap;

    /**
     * 同一货品购买总数集
     * 内容为：<货品编号,库存数>，主要考虑赠品、商品、套装商品可能会是同一种
     * 聚合同一商品的购买数，用于验证库存、减库存
     */
    Map<Long, Long> goodsBuyNumMap;

    /**
     * 同一货品的名称集
     */
    Map<Long, String> goodsNameMap;

    /**
     * 记录货品的商家编号
     */
    Map<Long, Long> goodsThirdIdMap;

    /**
     * 同一货品的可用库存集
     */
    Map<Long, Long> goodsUsedStockMap;

    /**
     * 结果标志
     * 1:成功
     * 0:失败， errorMsg为错误消息
     * -1:库存不足，moreErrorMsg为多个商品名称
     * -2:套装失效，moreErrorMsg为消息内容
     * -3:抢购超限，moreErrorMsg为多个商品名称
     * -4:商品下架或商品已被删除，moreErrorMsg为多个商品名称
     * -5:金额验证不正确，errorMsg为错误消息
     */
    int code;

    /**
     * 结果标志
     * <p>
     * 用于购物车->填写订单页面获取数据的返回值
     * <p>
     * 1:成功
     * 0:失败， errorMsg为错误消息
     * -1:库存不足，moreErrorMsg为多个商品名称
     * -2:套装失效，moreErrorMsg为消息内容
     * -3:抢购超限，moreErrorMsg为多个商品名称
     * -4:商品下架或商品已被删除，moreErrorMsg为多个商品名称
     * -5:金额验证不正确，errorMsg为错误消息
     */
    int errorCode;

    /**
     * 成功结果
     * 当code为1时，定义结果
     */
    private String parentOrderNo;

    /**
     * 错误结果
     * 当code为0时，定义结果
     */
    private String errorMsg;

    /**
     * 多个错误结果
     */
    private List<String> moreErrorMsg;

    /**
     * 提交的订单数据
     */
//    private List<Order> orders = new ArrayList<>();

    /**
     * 提交的订单明细数据
     */
//    private List<OrderGoods> orderGoodsList;

    /**
     * 区域id
     */
    private Long districtId;

    /**
     * 订单备注
     */
    private Map<Long, String> orderRemarkMap;

    /**
     * 优惠券信息---券码id 商家id
     */
    private Map<Long, Long> couponMap;

    /**
     * 商友系统返回--最大可用消费赠券/抵扣金额
     */
    private BigDecimal consumeMaxCouponPrice;

    /**
     * 立即购买使用skuId
     */
    private Long skuId;

    /**
     * 立即购买使用购买数量
     */
    private Long buyNum = 0L;

    /**
     * 配送方式Map
     * 内容<商户ID,配送类型>
     * 配送类型：0:快递1:自提
     */
    private Map<Long, Integer> freightTypeMap;
    /**
     * 配送方式Id
     * 0:快递 1:自提
     */
    private Integer freightType;

    /**
     * 自提点Map<商户Id,自提点Id>
     */
    private Map<Long, Long> deliveryMap;

    /**
     * 达人编号
     */
    private Long promoteId;

//    private CusTendMarketing cusTendMarketing;


    //增加一个商友系统的会员id
    private Long memberId;
    //增加一个商友系统的会员类型
    private String typeCode;

    //如果用户选择了电子券后，电子券的id
    private Integer offlineId;
    //商友,线下电子券金额
    private BigDecimal offlineCouponAmount;
    //商友,线下折扣金额
    private BigDecimal offlineDiscountAmount;
    //商友,线下满减金额
    private BigDecimal offlineReduceAmount;

    //会员手机号--用于订单详情自提人的手机号展示
    private String infoMobile;
    //会员手机号--用于订单详情自提人的姓名展示
    private String infoRealName;

    /**
     * 汇总同一商品购买数
     *
     * @param goodInfoId
     * @param buyNum
     */
    public void putGoodsBuyNum(Long goodInfoId, Long buyNum) {
        if (this.goodsBuyNumMap == null) {
            this.goodsBuyNumMap = new HashMap<>();
        }
        goodsBuyNumMap.put(goodInfoId, (goodsBuyNumMap.getOrDefault(goodInfoId, 0L) + buyNum));
    }

    /**
     * 记录商品的相关商家编号
     *
     * @param goodInfoId
     * @param thirdId
     */
    public void putGoodsThirdId(Long goodInfoId, Long thirdId) {
        if (this.goodsThirdIdMap == null) {
            this.goodsThirdIdMap = new HashMap<>();
        }
        goodsThirdIdMap.put(goodInfoId, thirdId);
    }

    /**
     * 获取当前同一商品的最终购买数
     *
     * @param goodInfoId
     */
    public Long getGoodsBuyNum(Long goodInfoId) {
        if (this.goodsBuyNumMap == null) {
            this.goodsBuyNumMap = new HashMap<>();
        }
        return goodsBuyNumMap.getOrDefault(goodInfoId, 0L);
    }

    /**
     * 汇总同一商品购买数
     *
     * @param goodInfoId
     * @param goodName
     */
    public void putGoodsName(Long goodInfoId, String goodName) {
        if (StringUtils.isBlank(goodName)) {
            return;
        }
        if (this.goodsNameMap == null) {
            this.goodsNameMap = new HashMap<>();
        }
        goodsNameMap.put(goodInfoId, goodName);
    }

    /**
     * 获取当前同一商品的最终购买数
     *
     * @param goodInfoId
     */
    public String getGoodsName(Long goodInfoId) {
        if (this.goodsNameMap == null) {
            this.goodsNameMap = new HashMap<>();
        }
        return goodsNameMap.getOrDefault(goodInfoId, "商品不存在");
    }

    /**
     * 设定同一商品库存数
     *
     * @param goodInfoId
     * @param stock
     */
    public void setUseGoodsStock(Long goodInfoId, Long stock) {
        if (this.goodsUsedStockMap == null) {
            this.goodsUsedStockMap = new HashMap<>();
        }
        goodsUsedStockMap.put(goodInfoId, stock);
    }

    /**
     * 错误
     *
     * @param msg
     */
    public void error(String msg) {
        this.code = 0;
        this.errorMsg = msg;
    }

    /**
     * 多个错误
     *
     * @param code
     * @param moreErrorMsg
     */
    public void moreError(int code, List<String> moreErrorMsg) {
        this.code = code;
        this.moreErrorMsg = moreErrorMsg;
    }

    /**
     * 成功
     */
    public void success() {
        this.code = 1;
    }

    /**
     * 订单确认结果（顶级通用）
     */
    private OrderConfirmSeller seller;


    /**
     * 获取商家信息（顶级通用）
     */
    public OrderConfirmSeller getSeller() {
        if (this.seller == null) {
            this.seller = new OrderConfirmSeller();
            seller.setSellerId(0L);
        }
        return seller;
    }

    /**
     * 是否发送短信
     */
    private String canSendMessage;

    /**
     * 后端计算,是否是新人首单,默认false不是首单
     */
    private Boolean isFirstOrder = false;

    /**
     * 是否含有生鲜商品
     */
    private  Boolean freshFlag = false;

    /**订单业务类型 0普通订单1拼团订单2秒杀订单**/
    private Integer orderBizType;
    /**订单业务类型 秒杀订单关闭时间,分钟**/
    private Integer closeOrderMinute;
}
