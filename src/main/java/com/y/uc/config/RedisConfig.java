package com.y.uc.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * Created by zhangyong on 2017/7/18.
 */
@Configuration
@EnableCaching
public class RedisConfig {

    @Value("${redis.host}")
    private String host;

    @Value("${redis.port}")
    private int port;

    @Value("${redis.db}")
    private int db;

    private static final long DEFAULT_EXPIRE_SECONDS = 60 * 30L;

    public static final String USER_NAMESPACE = "uc:user";

    @Bean("jedisConnectionFactory")
    public JedisConnectionFactory jedisConnectionFactory(@Value("${redis.host}") String host,
                                                         @Value("${redis.port}") int port,
                                                         @Value("${redis.db}") int db){
        JedisConnectionFactory factory = new JedisConnectionFactory();
        factory.setHostName(host);
        factory.setPort(port);
        factory.setDatabase(db);
        return factory;
    }

    @Bean("redisTemplate")
    public RedisTemplate<Object, Object> redisTemplate(@Autowired @Qualifier("jedisConnectionFactory") JedisConnectionFactory jedisConnectionFactory) {
        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(jedisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        return redisTemplate;
    }

    @Bean
    public RedisCacheManager redisCacheManager(@Autowired @Qualifier("redisTemplate") RedisTemplate<Object, Object> redisTemplate) {
        RedisCacheManager redisCacheManager = new RedisCacheManager(redisTemplate);
        redisCacheManager.setUsePrefix(true);
        redisCacheManager.setDefaultExpiration(DEFAULT_EXPIRE_SECONDS);
        return redisCacheManager;
    }

}
