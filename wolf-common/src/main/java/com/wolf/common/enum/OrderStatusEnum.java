package com.moyu.platform.common.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 订单状态枚举
 *
 * @author 朱路
 */
public enum OrderStatusEnum {
    /**
     * 待付款
     */
    INIT(0, "待付款"),
    /**
     * 新建
     */
    NEW(6, "新建"),

	/**
	 * 待采购状态不在订单流转
	 */
	WAIT_PURCHASE(7, "待采购"),
    /**
     * 已付款待发货
     */
    PAID(3, "已付款待发货"),
    /**
     * 部分发货
     */
    PART_SENT(1, "部分发货"),
    /**
     * 全部发货
     */
    SENT(2, "全部发货"),
    /**
     * 关闭
     */
    CLOSE(-1, "关闭"),
    /**
     * 售后处理中
     */
    RETURNING(-3, "售后处理中"),
    /**
     * 售后完成
     */
    RETURNDONE(-4, "售后完成"),
    /**
     * 订单完成
     */
    SUCCESS(4, "订单完成"),
    /**
     * 已签收
     */
    COMFIRM(5, "已签收");


    private int code;
    private String description;

    OrderStatusEnum(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static OrderStatusEnum of(int code) {
        for (OrderStatusEnum status : OrderStatusEnum.values()) {
            if (status.code == code) {
                return status;
            }
        }
        throw new IllegalArgumentException("invalid status code " + code);
    }

	public static Integer getCodeByDes(String description) {
		for (OrderStatusEnum status : OrderStatusEnum.values()) {
			if (status.description.equals(description)) {
				return status.code;
			}
		}
		return null;
	}

	public static String getDescByCode(Integer code) {
		for (OrderStatusEnum status : OrderStatusEnum.values()) {
			if (status.code==code) {
				return status.description;
			}
		}
		return null;
	}

	//付款前状态
	public static List<Integer> beforeShippingStatus() {
		List<Integer> beforeShippingStatus = new ArrayList<Integer>();
		beforeShippingStatus.add(NEW.code);
		beforeShippingStatus.add(INIT.code);
		return beforeShippingStatus;
	}

    //已付款状态的集合
    public static List<Integer> paidList() {
        List<Integer> paidStatus = new ArrayList<Integer>();
        paidStatus.add(NEW.code);
        paidStatus.add(PAID.code);
        paidStatus.add(PART_SENT.code);
        paidStatus.add(SENT.code);
        paidStatus.add(RETURNING.code);
        paidStatus.add(RETURNDONE.code);
        paidStatus.add(SUCCESS.code);
        paidStatus.add(COMFIRM.code);
        return paidStatus;
    }

    //已付款状态(不含确定退款)状态集合
    public static List<Integer> paidListNotReturn() {
        List<Integer> paidStatus = new ArrayList<Integer>();
        paidStatus.add(NEW.code);
        paidStatus.add(PAID.code);
        paidStatus.add(PART_SENT.code);
        paidStatus.add(SENT.code);
        paidStatus.add(SUCCESS.code);
        paidStatus.add(COMFIRM.code);
        return paidStatus;
    }

    //待发货状态集合
    public static List<Integer> waitSendList() {
        List<Integer> paidStatus = new ArrayList<Integer>();
        paidStatus.add(NEW.code);
        paidStatus.add(PAID.code);
        paidStatus.add(PART_SENT.code);
        return paidStatus;
    }

	public static List<Integer> mainOrderStatusList() {
		List<Integer> mainStatus = new ArrayList<Integer>();
		mainStatus.add(NEW.code); //新建
		mainStatus.add(INIT.code); //代付款
		mainStatus.add(PAID.code); //代发货
		mainStatus.add(SENT.code); //已发货
		mainStatus.add(RETURNDONE.code); //售后完成
		mainStatus.add(SUCCESS.code); //已完成
		mainStatus.add(CLOSE.code); //已关闭
		return mainStatus;
	}

	public static List<Integer> canShippingStatusList() {
		List<Integer> canShippingStatusList = new ArrayList<Integer>();
		canShippingStatusList.add(NEW.code); //新建
		canShippingStatusList.add(PAID.code); //代发货
		canShippingStatusList.add(PART_SENT.code); //部分发货
		return canShippingStatusList;
	}

	public static List<Integer> hasBendPaidStatusList() {
		List<Integer> hasBendPaidStatusList = new ArrayList<Integer>();
		hasBendPaidStatusList.add(NEW.code); //新建
		hasBendPaidStatusList.add(PAID.code); //代发货
		return hasBendPaidStatusList;
	}

	/* 可以进行采购退款状态 */
	public static List<Integer> canPurchaseRequrnStatusList() {
		List<Integer> hasBendPaidStatusList = new ArrayList<Integer>();
		hasBendPaidStatusList.add(RETURNING.code); //退款中
		hasBendPaidStatusList.add(RETURNDONE.code); //退款完成
		return hasBendPaidStatusList;
	}

	/* 订单状态顺序优先级 */
	public static Map<Integer,Integer> statusSequenceMap = new HashMap<Integer,Integer>() {
		{
			put(OrderStatusEnum.INIT.getCode(),1);//代付款
			put(OrderStatusEnum.WAIT_PURCHASE.getCode(),2);//待采购
			put(OrderStatusEnum.NEW.getCode(),3);//新建
			put(OrderStatusEnum.PAID.getCode(),3);//已支付待发货
			put(OrderStatusEnum.PART_SENT.getCode(),3);//部分发货
			put(OrderStatusEnum.SENT.getCode(),4);//全部发货
			put(OrderStatusEnum.COMFIRM.getCode(),4);//已签收
			put(OrderStatusEnum.RETURNING.getCode(),5);//订单售后中
			put(OrderStatusEnum.RETURNDONE.getCode(),6);//订单售后完成
			put(OrderStatusEnum.SUCCESS.getCode(),7);//订单完成
			put(OrderStatusEnum.CLOSE.getCode(),8);//订单关闭
		}
	};

	/** 子订单优先级最高的状态，对应的主订单状态描述 */
	public static Map<Integer,String> subStatusMainDescMap = new HashMap<Integer,String>() {
		{
			put(OrderStatusEnum.INIT.getCode(),"等待买家付款");//代付款
			put(OrderStatusEnum.WAIT_PURCHASE.getCode(),"待采购");//待采购
			put(OrderStatusEnum.NEW.getCode(),"待发货");//新建
			put(OrderStatusEnum.PAID.getCode(),"待发货");//已支付待发货
			put(OrderStatusEnum.PART_SENT.getCode(),"待收货");//部分发货
			put(OrderStatusEnum.SENT.getCode(),"待收货");//全部发货
			put(OrderStatusEnum.RETURNING.getCode(),"售后中");//订单售后中
			put(OrderStatusEnum.RETURNDONE.getCode(),"交易完成");//订单售后完成
			put(OrderStatusEnum.SUCCESS.getCode(),"交易完成");//订单完成
			put(OrderStatusEnum.CLOSE.getCode(),"已关闭");//订单关闭
			put(OrderStatusEnum.COMFIRM.getCode(),"已签收");//订单已签收
		}
	};

	/** 是否可以修改物流状态---查看物流接口使用 */
	public static Boolean isCanLotteryChanceLogistic(Integer orderStatus){
		Map<Integer,String> notLotteryChanceLogisticMap = new HashMap<Integer,String>();
		notLotteryChanceLogisticMap.put(OrderStatusEnum.INIT.getCode(),"待付款");
		notLotteryChanceLogisticMap.put(OrderStatusEnum.CLOSE.getCode(),"已关闭");
		notLotteryChanceLogisticMap.put(OrderStatusEnum.RETURNING.getCode(),"售后处理中");
		notLotteryChanceLogisticMap.put(OrderStatusEnum.RETURNDONE.getCode(),"售后完成");
		notLotteryChanceLogisticMap.put(OrderStatusEnum.SUCCESS.getCode(),"交易完成");
		notLotteryChanceLogisticMap.put(OrderStatusEnum.COMFIRM.getCode(),"已签收");
		//不可改变物流对应的订单状态,若状态不存在,是可以改的,返回true
		if(notLotteryChanceLogisticMap.get(orderStatus)==null){
			return true;
		}
		return false;
	}


	/* 真实销量状态 */
	public static List<Integer>  realSaleStatusList() {
		List<Integer> realSaleStatusList = new ArrayList<Integer>();
		realSaleStatusList.add(NEW.code); //新建
		realSaleStatusList.add(PAID.code); //已支付
		realSaleStatusList.add(PART_SENT.code); // 部分发货
		realSaleStatusList.add(SENT.code); //全部发货
		realSaleStatusList.add(SUCCESS.code); //交易完成
		realSaleStatusList.add(COMFIRM.code); //已签收
		return realSaleStatusList;
	}



}
