package com.scheduler.job.admin.core.lock;

public interface DistributedLock {

    boolean lock(String key);

    void unlock();

}
