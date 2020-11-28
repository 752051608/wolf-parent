package com.scheduler.job.admin.core.route.strategy;

import com.scheduler.job.admin.core.route.ExecutorRouter;
import com.scheduler.job.core.biz.model.ReturnT;
import com.scheduler.job.core.biz.model.TriggerParam;

import java.util.List;

/**
 * Created by xuxueli on 17/3/10.
 */
public class ExecutorRouteFirst extends ExecutorRouter {

    @Override
    public ReturnT<String> route(TriggerParam triggerParam, List<String> addressList){
        return new ReturnT<String>(addressList.get(0));
    }

}
