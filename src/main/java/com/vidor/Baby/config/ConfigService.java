package com.vidor.Baby.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;

@RefreshScope
@Service
public class ConfigService {

    @Value("${spring.core.pool.size}")
    private Integer springCorePoolSize;

    @Value("${spring.max.pool.size}")
    private Integer springMaxPoolSize;

    @Value("${spring.queue.capacity}")
    private Integer springQueueCapacity;

    @Value("${spring.thread.name.prefix}")
    private String springThreadNamePrefix;

    @Value("${spring.redis.sentinel.master:#{null}}")
    private String redisMaster;

    @Value("${spring.redis.sentinel.nodes:#{null}}")
    private String redisSentinels;

    public Integer getSpringCorePoolSize() {
        return springCorePoolSize;
    }

    public void setSpringCorePoolSize(Integer springCorePoolSize) {
        this.springCorePoolSize = springCorePoolSize;
    }

    public Integer getSpringMaxPoolSize() {
        return springMaxPoolSize;
    }

    public void setSpringMaxPoolSize(Integer springMaxPoolSize) {
        this.springMaxPoolSize = springMaxPoolSize;
    }

    public Integer getSpringQueueCapacity() {
        return springQueueCapacity;
    }

    public void setSpringQueueCapacity(Integer springQueueCapacity) {
        this.springQueueCapacity = springQueueCapacity;
    }

    public String getSpringThreadNamePrefix() {
        return springThreadNamePrefix;
    }

    public void setSpringThreadNamePrefix(String springThreadNamePrefix) {
        this.springThreadNamePrefix = springThreadNamePrefix;
    }

    public String getRedisMaster() {
        return redisMaster;
    }

    public void setRedisMaster(String redisMaster) {
        this.redisMaster = redisMaster;
    }

    public String getRedisSentinels() {
        return redisSentinels;
    }

    public void setRedisSentinels(String redisSentinels) {
        this.redisSentinels = redisSentinels;
    }
}
