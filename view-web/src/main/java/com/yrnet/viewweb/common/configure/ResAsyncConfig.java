package com.yrnet.viewweb.common.configure;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * @author dengbp
 * @ClassName ResAsyncConfig
 * @Description TODO
 * @date 2020-04-22 10:23
 */
@Configuration
@EnableAsync
public class ResAsyncConfig implements AsyncConfigurer {

    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(1);
        taskExecutor.setMaxPoolSize(2);
        taskExecutor.setQueueCapacity(5);
        taskExecutor.setThreadNamePrefix("res-async-thread-");
        taskExecutor.initialize();
        return taskExecutor;
    }
}
