package com.wolf.config.plugin;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;


@Configuration
public class PluginServiceConfig {

    @Value("${moyu.datong.sendInfo}")
    private boolean sendInfo;

    @Bean
    public PluginService pluginService() {
        PluginService marketingPluginService = new PluginService();
        //确认订单插件
        List<String> orderConfirmPlugins = new ArrayList<>();
        //订单创建后插件集
        List<String> orderCreatePlugins = new ArrayList<>();
        //大通
        if (sendInfo) {
            //=========================确认订单插件
            /**1.1 计入运费**/
            orderConfirmPlugins.add("warePricePlugin");
            orderConfirmPlugins.add("pointLevelPlugin");
            //减去活动价,便于运费处理;此插件在运费插件之前
            orderConfirmPlugins.add("datongSysPricePlugin");
            //秒杀商品价--可能需要邮费,不支持优惠券,设置优惠券为空
            orderConfirmPlugins.add("marketingSecondKillPlugin");
            //券前（以活动价为准）满39包邮,运费处理
            orderConfirmPlugins.add("freightPlugin");
            /**1.2 不计入运费**/
            //优惠券
            orderConfirmPlugins.add("couponActivityForAllPlugin");
            //优惠券-通用券
            orderConfirmPlugins.add("couponActivityForAllComPlugin");
            orderConfirmPlugins.add("pointPlugin");
            //=========================订单创建后插件集
            orderCreatePlugins.add("warePricePlugin");
            orderCreatePlugins.add("pointPlugin");
            orderCreatePlugins.add("couponActivityForAllPlugin");
            orderCreatePlugins.add("couponActivityForAllComPlugin");
            orderCreatePlugins.add("marketingSecondKillPlugin");
        }
        //魔宇新零售,走满减满折满赠
        if (!sendInfo) {
            //=========================确认订单插件
            /**1.1 计入运费**/
//            orderConfirmPlugins.add("pointLevelPlugin");
            //减去会员等级货品折扣
            orderConfirmPlugins.add("memberDiscountPlugin");
            //秒杀商品价--可能需要邮费,不支持优惠券,设置优惠券为空
            orderConfirmPlugins.add("marketingSecondKillPlugin");
            //券前（以活动价为准）满39包邮,运费处理
            orderConfirmPlugins.add("freightPlugin");
            /**1.2 不计入运费**/
            //优惠券
            orderConfirmPlugins.add("couponActivityForAllPlugin");
            //优惠券-通用券
            orderConfirmPlugins.add("couponActivityForAllComPlugin");
//            orderConfirmPlugins.add("pointPlugin");
            //走魔宇新零售,后面应该放入 计入运费
//            orderConfirmPlugins.add("marketingFullReductionPlugin");
//            orderConfirmPlugins.add("marketingFullDiscountPlugin");
//            orderConfirmPlugins.add("marketingFullGiftPlugin");
//            orderConfirmPlugins.add("marketingSuitPlugin");
//            //目前抢购活动优先级最高
//            orderConfirmPlugins.add("marketingRushPlugin");
//            orderConfirmPlugins.add("marketingGrouponPlugin");
//            orderConfirmPlugins.add("marketingFreeDeliveryPlugin");
            //=========================订单创建后插件集
            orderCreatePlugins.add("memberDiscountPlugin");
            orderCreatePlugins.add("marketingSecondKillPlugin");
            orderCreatePlugins.add("couponActivityForAllPlugin");
            orderCreatePlugins.add("couponActivityForAllComPlugin");
//            orderCreatePlugins.add("marketingRushPlugin");
//            orderCreatePlugins.add("marketingGrouponPlugin");

        }
        marketingPluginService.setOrderConfirmPlugins(orderConfirmPlugins);
        marketingPluginService.setOrderCreatePlugins(orderCreatePlugins);

        //海狐订单确认插件集
        List<String> haihuOrderConfirmPlugins = new ArrayList<>();
        haihuOrderConfirmPlugins.add("warePricePlugin");
        marketingPluginService.setHaihuOrderConfirmPlugins(haihuOrderConfirmPlugins);
        //海狐订单创建后插件集
        List<String> haihuOrderCreatePlugins = new ArrayList<>();
        haihuOrderCreatePlugins.add("warePricePlugin");
        marketingPluginService.setHaihuOrderCreatePlugins(haihuOrderCreatePlugins);
        return marketingPluginService;

    }
}
