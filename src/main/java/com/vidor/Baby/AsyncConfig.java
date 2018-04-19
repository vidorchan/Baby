package com.vidor.Baby;

import com.vidor.Baby.config.ConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.lang.reflect.Method;
import java.util.concurrent.Executor;

@Configuration
public class AsyncConfig implements AsyncConfigurer, ApplicationListener<ContextClosedEvent> {

    private static final Logger logger = LoggerFactory.getLogger(AsyncConfig.class);

    private ConfigService configService;

    private  ThreadPoolTaskExecutor threadPoolTaskExecutor;


    public AsyncConfig(ConfigService configService) {
        this.configService = configService;
    }

    @Override
//    @Bean if exists, then no need initialize
    public Executor getAsyncExecutor() {
        threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(configService.getSpringCorePoolSize());
        threadPoolTaskExecutor.setMaxPoolSize(configService.getSpringMaxPoolSize());
        threadPoolTaskExecutor.setQueueCapacity(configService.getSpringQueueCapacity());
        threadPoolTaskExecutor.setThreadNamePrefix(configService.getSpringThreadNamePrefix());
        threadPoolTaskExecutor.initialize();
        return threadPoolTaskExecutor;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        AsyncUncaughtExceptionHandler asyncUncaughtExceptionHandler = new AsyncUncaughtExceptionHandler() {
            @Override
            public void handleUncaughtException(Throwable throwable, Method method, Object... objects) {
                logger.error("Asynchronous call caused an uncaught exception in method {}", method.getName(), throwable);
            }
        };
        return asyncUncaughtExceptionHandler;
    }

    /**
     * 在一些业务场景中，当容器初始化完成之后，需要处理一些操作，比如一些数据的加载、初始化缓存、特定任务的注册等等。
     * 这个时候我们就可以使用Spring提供的ApplicationListener来进行操作。
     * @param contextClosedEvent 容器关闭时
     */
    @Override
    public void onApplicationEvent(ContextClosedEvent contextClosedEvent) {
        if (threadPoolTaskExecutor != null) {
            threadPoolTaskExecutor.shutdown();
        }
    }
}
