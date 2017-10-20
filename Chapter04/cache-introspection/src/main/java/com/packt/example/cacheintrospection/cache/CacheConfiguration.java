package com.packt.example.cacheintrospection.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Arrays;

@Configuration
@EnableCaching
public class CacheConfiguration {

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @Bean
    public CacheManager cacheManager() {
        RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate);
        cacheManager.setCacheNames(Arrays.asList("oauth2"));
        cacheManager.setUsePrefix(true);
        cacheManager.setDefaultExpiration(60);

        return cacheManager;
    }

}
