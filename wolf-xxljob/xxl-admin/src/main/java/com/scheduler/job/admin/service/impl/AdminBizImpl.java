package com.scheduler.job.admin.service.impl;

import com.scheduler.job.admin.core.lock.DistributedLock;
import com.scheduler.job.admin.core.model.XxlJobInfo;
import com.scheduler.job.admin.core.model.XxlJobLog;
import com.scheduler.job.admin.core.model.XxlJobRegistry;
import com.scheduler.job.admin.core.thread.JobTriggerPoolHelper;
import com.scheduler.job.admin.core.trigger.TriggerTypeEnum;
import com.scheduler.job.admin.core.util.I18nUtil;
import com.scheduler.job.admin.dao.XxlJobInfoDao;
import com.scheduler.job.admin.dao.XxlJobLogDao;
import com.scheduler.job.admin.dao.XxlJobRegistryDao;
import com.scheduler.job.core.biz.AdminBiz;
import com.scheduler.job.core.biz.model.HandleCallbackParam;
import com.scheduler.job.core.biz.model.RegistryParam;
import com.scheduler.job.core.biz.model.ReturnT;
import com.scheduler.job.core.handler.IJobHandler;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.MessageFormat;
import java.util.*;

/**
 * @author xuxueli 2017-07-27 21:54:20
 */
@Service
public class AdminBizImpl implements AdminBiz {
    private static Logger logger = LoggerFactory.getLogger(AdminBizImpl.class);

    @Resource
    public XxlJobLogDao xxlJobLogDao;
    @Resource
    private XxlJobInfoDao xxlJobInfoDao;
    @Resource
    private XxlJobRegistryDao xxlJobRegistryDao;

    @Autowired
    private DistributedLock distributedLockImpl;


    @Override
    public ReturnT<String> callback(List<HandleCallbackParam> callbackParamList) {
        for (HandleCallbackParam handleCallbackParam: callbackParamList) {
            ReturnT<String> callbackResult = callback(handleCallbackParam);
            logger.info(">>>>>>>>> JobApiController.callback {}, handleCallbackParam={}, callbackResult={}",
                    (callbackResult.getCode()==IJobHandler.SUCCESS.getCode()?"success":"fail"), handleCallbackParam, callbackResult);
        }

        return ReturnT.SUCCESS;
    }

    private ReturnT<String> callback(HandleCallbackParam handleCallbackParam) {
        // valid log item
        XxlJobLog log = xxlJobLogDao.load(handleCallbackParam.getLogId());
        if (log == null) {
            return new ReturnT<String>(ReturnT.FAIL_CODE, "log item not found.");
        }
        if (log.getHandleCode() > 0) {
            return new ReturnT<String>(ReturnT.FAIL_CODE, "log repeate callback.");     // avoid repeat callback, trigger child job etc
        }

        // trigger success, to trigger child job
        String callbackMsg = null;
        if (IJobHandler.SUCCESS.getCode() == handleCallbackParam.getExecuteResult().getCode()) {
            XxlJobInfo xxlJobInfo = xxlJobInfoDao.loadById(log.getJobId());
            if (xxlJobInfo!=null && StringUtils.isNotBlank(xxlJobInfo.getChildJobId())) {
                callbackMsg = "<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>"+ I18nUtil.getString("jobconf_trigger_child_run") +"<<<<<<<<<<< </span><br>";

                String[] childJobIds = xxlJobInfo.getChildJobId().split(",");
                for (int i = 0; i < childJobIds.length; i++) {
                    int childJobId = (StringUtils.isNotBlank(childJobIds[i]) && StringUtils.isNumeric(childJobIds[i]))?Integer.valueOf(childJobIds[i]):-1;
                    if (childJobId > 0) {

                        JobTriggerPoolHelper.trigger(childJobId, TriggerTypeEnum.PARENT, -1, null, null);
                        ReturnT<String> triggerChildResult = ReturnT.SUCCESS;

                        // add msg
                        callbackMsg += MessageFormat.format(I18nUtil.getString("jobconf_callback_child_msg1"),
                                (i+1),
                                childJobIds.length,
                                childJobIds[i],
                                (triggerChildResult.getCode()==ReturnT.SUCCESS_CODE?I18nUtil.getString("system_success"):I18nUtil.getString("system_fail")),
                                triggerChildResult.getMsg());
                    } else {
                        callbackMsg += MessageFormat.format(I18nUtil.getString("jobconf_callback_child_msg2"),
                                (i+1),
                                childJobIds.length,
                                childJobIds[i]);
                    }
                }

            }
        }

        // handle msg
        StringBuffer handleMsg = new StringBuffer();
        if (log.getHandleMsg()!=null) {
            handleMsg.append(log.getHandleMsg()).append("<br>");
        }
        if (handleCallbackParam.getExecuteResult().getMsg() != null) {
            handleMsg.append(handleCallbackParam.getExecuteResult().getMsg());
        }
        if (callbackMsg != null) {
            handleMsg.append(callbackMsg);
        }

        // success, save log
        log.setHandleTime(new Date());
        log.setHandleCode(handleCallbackParam.getExecuteResult().getCode());
        log.setHandleMsg(handleMsg.toString());
        xxlJobLogDao.updateHandleInfo(log);

        return ReturnT.SUCCESS;
    }

    @Override
    public ReturnT<String> registry(RegistryParam registryParam) {

//        int ret = xxlJobRegistryDao.registryUpdate(registryParam.getRegistGroup(), registryParam.getRegistryKey(), registryParam.getRegistryValue());
//        if (ret < 1) {
//            xxlJobRegistryDao.registrySave(registryParam.getRegistGroup(), registryParam.getRegistryKey(), registryParam.getRegistryValue());
//        }
//        return ReturnT.SUCCESS;

        try {
            if(distributedLockImpl.lock(registryParam.getRegistryKey())){
                XxlJobRegistry registry = xxlJobRegistryDao.registrySelect(registryParam.getRegistGroup(), registryParam.getRegistryKey());
                if(registry==null){
                    xxlJobRegistryDao.registrySave(registryParam.getRegistGroup(), registryParam.getRegistryKey(), registryParam.getRegistryValue());
                    return ReturnT.SUCCESS;
                }
                logger.info("===============registry={}",registry);
                Set<String> set=new HashSet<>();
                set.add(registryParam.getRegistryValue());
                if(registry!=null&&registry.getRegistryValue()!=null){
                    logger.info("===============registryValue={}",registry.getRegistryValue());
                    String[] ipAndPort=registry.getRegistryValue().split(",");
                    for (String ipt : ipAndPort) {
                        set.add(ipt);
                    }
                }
                StringBuilder sb=new StringBuilder();
                for (String s : set) {
                    sb.append(s).append(",");
                }
                registryParam.setRegistryValue(sb.toString().substring(0,sb.toString().length()-1));
                logger.info("--------------retValue={}",registryParam.getRegistryValue());
                int ret = xxlJobRegistryDao.registryUpdateValue(registryParam.getRegistGroup(), registryParam.getRegistryKey(), registryParam.getRegistryValue());
                logger.info("--------------ret={}",ret);
            }
        }finally {
            distributedLockImpl.unlock();
        }
        return ReturnT.SUCCESS;
    }

    @Override
    public ReturnT<String> registryRemoveUpdate(RegistryParam registryParam) {
        XxlJobRegistry registry = xxlJobRegistryDao.registrySelect(registryParam.getRegistGroup(), registryParam.getRegistryKey());
        if(registry==null){
            xxlJobRegistryDao.registryDelete(registryParam.getRegistGroup(), registryParam.getRegistryKey(), registryParam.getRegistryValue());
            return ReturnT.SUCCESS;
        }
        List<String> address=new ArrayList<>();
        if(registry!=null&&registry.getRegistryValue()!=null){
            String[] ipAndPort=registry.getRegistryValue().split(",");
            for (String s : ipAndPort) {
                address.add(s);
            }
            if(address.contains(registryParam.getRegistryValue())){
                address.remove(registryParam.getRegistryValue());
            }
        }
        if(address!=null&&address.size()>0){
            StringBuilder sb=new StringBuilder();
            for (String s : address) {
                sb.append(s).append(",");
            }
            registryParam.setRegistryValue(sb.toString().substring(0,sb.toString().length()-1));
            xxlJobRegistryDao.registryUpdateValue(registryParam.getRegistGroup(), registryParam.getRegistryKey(), registryParam.getRegistryValue());
            return ReturnT.SUCCESS;
        }
        xxlJobRegistryDao.registryDelete(registryParam.getRegistGroup(), registryParam.getRegistryKey(), registryParam.getRegistryValue());
        return ReturnT.SUCCESS;
    }

    @Override
    public ReturnT<String> registryRemove(RegistryParam registryParam) {
        xxlJobRegistryDao.registryDelete(registryParam.getRegistGroup(), registryParam.getRegistryKey(), registryParam.getRegistryValue());
        return ReturnT.SUCCESS;
    }

}
