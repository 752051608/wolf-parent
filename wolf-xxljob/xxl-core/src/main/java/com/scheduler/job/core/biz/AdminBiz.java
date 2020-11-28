package com.scheduler.job.core.biz;

import com.scheduler.job.core.biz.model.HandleCallbackParam;
import com.scheduler.job.core.biz.model.RegistryParam;
import com.scheduler.job.core.biz.model.ReturnT;

import java.util.List;

/**
 * @author xuxueli 2017-07-27 21:52:49
 */
public interface AdminBiz {

    String MAPPING = "/api";


    // ---------------------- callback ----------------------

    /**
     * callback
     *
     * @param callbackParamList
     * @return
     */
    ReturnT<String> callback(List<HandleCallbackParam> callbackParamList);


    // ---------------------- registry ----------------------

    /**
     * registry
     *
     * @param registryParam
     * @return
     */
    ReturnT<String> registry(RegistryParam registryParam);


    /**
     * 注册的机器,宕机或jvm退出时,去除对应的ip;
     * 更新对应的操作(逻辑变动,新增的方法)
     * @param registryParam
     * @return
     */
    ReturnT<String> registryRemoveUpdate(RegistryParam registryParam);

    /**
     * registry remove
     *
     * @param registryParam
     * @return
     */
    ReturnT<String> registryRemove(RegistryParam registryParam);

}
