package com.snapcart.user_service.config;

import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;

public class SharedSessionCache {
    private final RedissonClient redissonClient;
    private final RMap<String, Session> sessions;
    public SharedSessionCache(RedissonClient redissonClient, String cacheName) {
        this.redissonClient = redissonClient;
        sessions = this.redissonClient.getMap(cacheName);
    }

    public void put(String key, Session session) {
        sessions.put(key, session);
    }
    public void remove(String key) {
        sessions.remove(key);
    }
    public Session get(String key) {
        return sessions.get(key);
    }
}
