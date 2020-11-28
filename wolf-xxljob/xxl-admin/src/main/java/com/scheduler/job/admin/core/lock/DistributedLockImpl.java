package com.scheduler.job.admin.core.lock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Random;


@Service
public class DistributedLockImpl implements DistributedLock {

    private final Logger logger = LoggerFactory.getLogger(DistributedLockImpl.class);

    /**重试时间*/
    private static final int DEFAULT_RETRY_MILLIS = 100;
    /**锁的前缀*/
    private static final String LOCK_PREFIX = "wq_quartz_redis_lock_";
    /**锁的key*/
    private String lockKey;
    /**锁超时时间，防止线程在入锁以后，防止阻塞后面的线程无法获取锁*/
    private int expireTimeSecs = 60 * 1000;
    /**线程获取锁的等待时间*/
    private int timeoutWaitSecs = 50 * 1000;
    /**是否锁定标志*/
    private volatile boolean locked = false;
    private final Random r = new Random();

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public boolean lock(String key) {
        int timeout = timeoutWaitSecs;
        lockKey=LOCK_PREFIX+key;
        logger.info("lock key={}",lockKey);
        while (timeout >= 0) {
            long expires = System.currentTimeMillis() + expireTimeSecs + 1;
            String expiresStr = String.valueOf(expires); // 锁到期时间
            if (this.setNX(lockKey, expiresStr)) {
                locked = true;
                return true;
            }
            // redis里key存活的时间
            String currentValue = this.get(lockKey);
            // 判断锁是否已经过期，过期则重新设置并获取
            //a.锁不在了,即currentValue为null,服务器释放了锁。进行下次遍历的操作
            //b.锁还在，且时间比当前时间要小,却超时了; 老的锁还在,且超时; 老的锁不在,oldValue为空,当前锁获取成功
            if (currentValue != null && Long.parseLong(currentValue) < System.currentTimeMillis()) {
                // 设置锁并返回旧值
                String oldValue = this.getSet(lockKey, expiresStr);
                //只有一个线程才能获取上一个线上的设置时间，因为getSet是原子性的
                // 若不相等，说明有个线程 已经早一步执行了getSet，把它时间后延一点，影响不大因此继续等待
                if (oldValue != null && oldValue.equals(currentValue)) {
                    locked = true;
                    logger.info("lock key success");
                    return true;
                }
                //key所在的锁已经被释放，此时被此线程设置值了，获得锁
                if(oldValue==null){
                    return true;
                }
            }
            timeout -= DEFAULT_RETRY_MILLIS;
            // 延时
            try {
                //短暂休眠,nano为了防止饥饿进程的出现
                Thread.sleep(DEFAULT_RETRY_MILLIS,r.nextInt(500));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return false;
//        throw new CommonException("服务器压力过大,操作失败,请重试");
    }

    @Override
    public void unlock() {
        if (locked) {
            logger.info("unlock key={}",lockKey);
            try {
                redisTemplate.delete(lockKey);
                locked = false;
                logger.info("unlock key success");
            }catch (Exception e){
                logger.info("unlock key fail,key={}",lockKey);
            }
        }
    }



    /**
     * 封装和jedis方法
     * @param key
     * @return
     */
    private String get(final String key) {
        Object obj = redisTemplate.opsForValue().get(key);
        return obj != null ? obj.toString() : null;
    }

    /**
     * 封装和jedis方法
     * @param key
     * @param value
     * @return
     */
    private boolean setNX(final String key, final String value) {
        return redisTemplate.opsForValue().setIfAbsent(key, value);
    }

    /**
     * 封装和jedis方法
     *
     * @param key
     * @param value
     * @return
     */
    private String getSet(final String key, final String value) {
        Object obj = redisTemplate.opsForValue().getAndSet(key, value);
        return obj != null ? (String) obj : null;
    }
}
