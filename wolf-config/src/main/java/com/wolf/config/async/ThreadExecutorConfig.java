package com.wolf.config.async;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

@Configuration
@EnableAsync
@Slf4j
public class ThreadExecutorConfig {
    @Autowired
    private AsyncTaskProperties asyncTaskProperties;

    @Bean("threadPool")
    public ThreadPoolTaskExecutor asyncServiceExecutor() {
        log.info("start asyncServiceExecutor");
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //配置核心线程数
        executor.setCorePoolSize(asyncTaskProperties.getCorePoolSize());
        //配置最大线程数
        executor.setMaxPoolSize(asyncTaskProperties.getMaxPoolSize());
        //配置队列大小
        executor.setQueueCapacity(asyncTaskProperties.getQueueCapacity());
        //线程池维护线程所允许的空闲时间
        executor.setKeepAliveSeconds(asyncTaskProperties.getKeepAliveSeconds());
        //配置线程池中的线程的名称前缀
        executor.setThreadNamePrefix("async-service-");
        //程序关闭时等待线程执行完毕
        executor.setWaitForTasksToCompleteOnShutdown(true);
        // rejection-policy：当pool已经达到max size的时候，如何处理新任务
        // CALLER_RUNS：不在新线程中执行任务，而是有调用者所在的线程来执行
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        //执行初始化
        executor.initialize();
        return executor;

    }
}
