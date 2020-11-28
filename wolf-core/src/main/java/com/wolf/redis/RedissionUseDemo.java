package com.wolf.redis;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.TimeUnit;

/**
 * @Description TODO
 * @Author zhanghong
 * @Date 2020/11/28 3:04 PM
 * @Version 1.0
 */
public class RedissionUseDemo {

    @Autowired
    private RedissonClient redissonClient;

    public void test(){
        String lockKey = "customer_registry" + 00000;
        RLock lock = redissonClient.getLock(lockKey);
        try {
            boolean lockResult = lock.tryLock(5, 3, TimeUnit.SECONDS);
            if (lockResult) {
                //business 处理
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (lock.isHeldByCurrentThread() && lock.isLocked()) {
                lock.unlock();
            }
        }
    }
}
