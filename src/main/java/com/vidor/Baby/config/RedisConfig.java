package com.vidor.Baby.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.lang.reflect.Method;
import java.time.Duration;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Redis configuration
 */
@FunctionalInterface
public interface RedisConfig {
//extends CachingConfigurerSupport
    static final Logger logger = LoggerFactory.getLogger(RedisConfig.class);

//    @Value("${spring.redis.host}")
//    private String host;
//
//    @Value("${spring.redis.port}")
//    private int port;
//
//    @Value("${spring.redis.timeout}")
//    private int timeout;
//
//    @Value("${spring.redis.password}")
//    private String password;
//
//    @Value("${spring.redis.database}")
//    private int database;
//
//    /**
//     * If configure JedisConnectionFactory here, then need to set the params.
//     * Suggest not to add JedisConnectionFactory
//     * @return
//     */
//    @Bean
//    public JedisConnectionFactory redisConnectionFactory() {
//        JedisConnectionFactory redisConnectionFactory = new JedisConnectionFactory();
//        redisConnectionFactory.setHostName(this.host);
//        redisConnectionFactory.setPort(this.port);
//        redisConnectionFactory.setTimeout(this.timeout);
//        redisConnectionFactory.setPassword(this.password);
//        redisConnectionFactory.setDatabase(this.database);
//        return redisConnectionFactory;
//    }

    /**
     *  注解@Cache key生成规则
     */
//    @Bean
//    public KeyGenerator keyGenerator() {
//        return new KeyGenerator() {
//            @Override
//            public Object generate(Object target, Method method, Object... params) {
//                StringBuilder sb = new StringBuilder();
//                String[] value = new String[1];
//                Cacheable cacheable = method.getAnnotation(Cacheable.class);
//                if (cacheable != null) {
//                    value = cacheable.value();
//                }
//                CachePut cachePut = method.getAnnotation(CachePut.class);
//                if (cachePut != null) {
//                    value = cachePut.value();
//                }
//                CacheEvict cacheEvict = method.getAnnotation(CacheEvict.class);
//                if (cacheEvict != null) {
//                    value = cacheEvict.value();
//                }
//                sb.append(value[0]);
//                for (Object obj : params) {
//                    sb.append(":")
//                        .append(obj.toString());
//                }
//                return sb.toString();
//            }
//        };
//    }

//    @Bean
//    //缓存管理器
//    public CacheManager cacheManager(RedisConnectionFactory connectionFactory) {
//        Set<String> cacheNames = new HashSet<String>() {{
//            add("baby");
//        }};
//        RedisCacheManager redisCacheManager = RedisCacheManager.builder(connectionFactory)
//                .cacheDefaults(RedisCacheConfiguration.defaultCacheConfig())
//                .initialCacheNames(cacheNames)
//                .build();
//        return redisCacheManager;
//        /*RedisCacheManager rcm = new RedisCacheManager(redisTemplate);
//        // 多个缓存的名称,目前只定义了一个
//        rcm.setCacheNames(Arrays.asList("thisredis"));
//        //设置缓存默认过期时间(秒)
//        rcm.setDefaultExpiration(600);
//        return rcm;*/
//    }

    RedisConnectionFactory getConnectionFactory();

    // 以下两种redisTemplate自由根据场景选择
    @Bean
    default RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(getConnectionFactory());

        //使用Jackson2JsonRedisSerializer来序列化和反序列化redis的value值（默认使用JDK的序列化方式）
        //JdkSerializationRedisSerializer jdkRedisSerializer=new JdkSerializationRedisSerializer();
        Jackson2JsonRedisSerializer serializer = new Jackson2JsonRedisSerializer(Object.class);

        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        serializer.setObjectMapper(mapper);

        template.setValueSerializer(serializer);
        //使用StringRedisSerializer来序列化和反序列化redis的key值
        template.setKeySerializer(new StringRedisSerializer());
        template.afterPropertiesSet();
        return template;
    }

    @Bean
    default CacheManager cacheManager(RedisConnectionFactory connectionFactory) {
        Set<String> cacheNames = new HashSet<String>() {{
            add("baby");
        }};
        RedisCacheManager redisCacheManager = RedisCacheManager.builder(connectionFactory)
                .cacheDefaults(RedisCacheConfiguration.defaultCacheConfig())
                .initialCacheNames(cacheNames)
                .build();
        return redisCacheManager;
        /*RedisCacheManager rcm = new RedisCacheManager(redisTemplate);
        // 多个缓存的名称,目前只定义了一个
        rcm.setCacheNames(Arrays.asList("thisredis"));
        //设置缓存默认过期时间(秒)
        rcm.setDefaultExpiration(600);
        return rcm;*/
    }

//    @Bean
//    public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory factory) {
//        StringRedisTemplate stringRedisTemplate = new StringRedisTemplate();
//        stringRedisTemplate.setConnectionFactory(factory);
//        return stringRedisTemplate;
//    }

    /**
     * redis数据操作异常处理
     * 这里的处理：在日志中打印出错误信息，但是放行
     * 保证redis服务器出现连接等问题的时候不影响程序的正常运行，使得能够出问题时不用缓存
     * @return
     */
//    @Bean
//    @Override
//    public CacheErrorHandler errorHandler() {
//        CacheErrorHandler cacheErrorHandler = new CacheErrorHandler() {
//            @Override
//            public void handleCacheGetError(RuntimeException e, Cache cache, Object key) {
//                logger.error("redis异常：key=[{}]",key,e);
//            }
//
//            @Override
//            public void handleCachePutError(RuntimeException e, Cache cache, Object key, Object value) {
//                logger.error("redis异常：key=[{}]",key,e);
//            }
//
//            @Override
//            public void handleCacheEvictError(RuntimeException e, Cache cache, Object key)    {
//                logger.error("redis异常：key=[{}]",key,e);
//            }
//
//            @Override
//            public void handleCacheClearError(RuntimeException e, Cache cache) {
//                logger.error("redis异常：",e);
//            }
//        };
//        return cacheErrorHandler;
//    }
}
