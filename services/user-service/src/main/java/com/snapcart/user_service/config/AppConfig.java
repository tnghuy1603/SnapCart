package com.snapcart.user_service.config;

import org.redisson.api.RedissonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public SharedSessionCache sharedSessionCache(RedissonClient redissonClient) {
        return new SharedSessionCache(redissonClient, "user-session-cache");
    }
}
