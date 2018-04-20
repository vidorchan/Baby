package com.vidor.Baby.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

/**
 * Redis configuration
 */
@Configuration("RedisConfig")
@Profile("RedisSentinel")
public class RedisSentinelConfig implements RedisConfig {

    private static final Logger logger = LoggerFactory.getLogger(RedisSentinelConfig.class);

    @Autowired
    private ConfigService configService;

    @Bean
    @Override
    public RedisConnectionFactory getConnectionFactory() {
        RedisConnectionFactory factory = null;
        try {
            //Sentinels are added in the spring config as comma seperated sentinelIP1:port1,sentinelIP2:port2
            RedisSentinelConfiguration sentinelConfig = new RedisSentinelConfiguration();
            sentinelConfig.setMaster(configService.getRedisMaster());
            String[] sentinelsArray = configService.getRedisSentinels().split(",");
            for (String sentinel : sentinelsArray) {
                int delimiterIndex = sentinel.indexOf(':');
                String sentinelIP = sentinel.substring(0, delimiterIndex);
                String sentinelPort = sentinel.substring(delimiterIndex + 1);
                RedisNode redisSentinel = new RedisNode(sentinelIP, Integer.parseInt(sentinelPort));
                sentinelConfig.addSentinel(redisSentinel);
            }
            factory = new JedisConnectionFactory(sentinelConfig); //LettuceConnectionFactory
        } catch(Exception ex) {
            logger.error("Error creating RedisConnectionFactory", ex);
        }
        return factory;
    }
}
