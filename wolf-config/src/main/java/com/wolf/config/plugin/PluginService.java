package com.wolf.config.plugin;
import com.wolf.config.SpringContextHolder;
import com.wolf.config.plugin.impl.IOrderConfirmPlugin;
import com.wolf.config.plugin.impl.IOrderCreatePlugin;
import com.wolf.config.plugin.impl.PluginRequest;
import com.wolf.config.plugin.impl.impl.vo.OrderConfirmRequest;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.util.List;

;

/**
 * 主插件服务
 * Created by dyt on 2016/12/2.
 */
@Data
public class PluginService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PluginService.class);

    /**
     * 订单确认插件集
     */
    private List<String> orderConfirmPlugins;

    /**
     * 海狐订单确认插件集
     */
    private List<String> haihuOrderConfirmPlugins;
    /**
     * 海狐 订单提交插件集
     */
    private List<String> haihuOrderCreatePlugins;

    /**
     * 订单提交插件集
     */
    private List<String> orderCreatePlugins;

    /**
     * 购物车列表插件集
     */
    private List<String> shopCartPlugins;

    /**
     * 商品列表插件集
     */
    private List<String> goodsListPlugins;

    /**
     * 商品详情插件集
     */
    private List<String> goodsDetailPlugins;

    /**
     * 订单确认处理
     *
     * @param orderConfirmRequest
     * @param pluginRequest
     * @throws Exception orderConfirmFilter
     */
    public void orderConfirmFilter(OrderConfirmRequest orderConfirmRequest, PluginRequest pluginRequest) throws Exception {
        if (CollectionUtils.isEmpty(orderConfirmPlugins)) {
            return;
        }
        //为每个购物项的商品分配参与的营销信息
        for (String plugin : orderConfirmPlugins) {
            Object pluginObj = SpringContextHolder.getBean(plugin);
            if (pluginObj instanceof IOrderConfirmPlugin) {
                long start = System.currentTimeMillis();
                ((IOrderConfirmPlugin) pluginObj).confirmOrderFilter(orderConfirmRequest, pluginRequest);
                long end = System.currentTimeMillis();
                LOGGER.debug("订单确认处理分配营销【".concat(plugin).concat("】执行时间:").concat(String.valueOf((end - start))).concat("毫秒"));
            } else {
                LOGGER.error("订单确认处理分配营销【".concat(plugin).concat("】没有实现IOrderConfirmPlugin"));
            }
        }
        /**
         * 聚合同一个营销信息，并计算规则优惠
         * 将seller->shopCart，聚合为seller->marketing(计算优惠)->shopCart
         */
        for (String plugin : orderConfirmPlugins) {
            Object pluginObj = SpringContextHolder.getBean(plugin);
            if (pluginObj instanceof IOrderConfirmPlugin) {
                long start = System.currentTimeMillis();
                ((IOrderConfirmPlugin) pluginObj).confirmOrderCalFilter(orderConfirmRequest, pluginRequest);
                long end = System.currentTimeMillis();
                LOGGER.debug("订单确认处理计算营销【".concat(plugin).concat("】执行时间:").concat(String.valueOf((end - start))).concat("毫秒"));
                //如果插件中有错误，直接停止
                if (orderConfirmRequest.getCode() == 0) {
                    return;
                }
            } else {
                LOGGER.error("订单确认处理计算营销【".concat(plugin).concat("】没有实现IOrderConfirmPlugin"));
            }
        }
    }

    /**
     * 创建订单后
     *
     * @param orderConfirmRequest
     * @param pluginRequest
     * @throws Exception
     */
    public void createOrderAfter(OrderConfirmRequest orderConfirmRequest, PluginRequest pluginRequest) throws Exception {
        if (CollectionUtils.isEmpty(orderCreatePlugins)) {
            return;
        }
        for (String plugin : orderCreatePlugins) {
            Object pluginObj = SpringContextHolder.getBean(plugin);
            if (pluginObj instanceof IOrderCreatePlugin) {
                long start = System.currentTimeMillis();
                ((IOrderCreatePlugin) pluginObj).createOrderAfter(orderConfirmRequest, pluginRequest);
                long end = System.currentTimeMillis();
                LOGGER.debug("创建订单后【".concat(plugin).concat("】执行时间:").concat(String.valueOf((end - start))).concat("毫秒"));
            } else {
                LOGGER.error("创建订单后【".concat(plugin).concat("】没有实现IOrderCreatePlugin接口"));
            }
        }
    }







}
