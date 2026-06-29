package com.smc.smccloud.core.redis.redisson;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @Author lyc
 * @Date 2021/12/22 12:28
 * @Descripton RedissonUtil
 */
@Slf4j
@Component
public class RedissonUtil {

    @Autowired
    private RedissonClient redissonClient;

    /**
     * 加锁
     *
     * @param key lockKey
     * @return RLock
     */
    public RLock lock(String key) {
        RLock lock = redissonClient.getLock(key);
        lock.lock();
        // log.info(">>>>> 主动加锁【{}】", key);
        return lock;
    }

    /**
     * 带超时的锁
     *
     * @param key     lockKey
     * @param timeout 超时时间   单位：秒
     * @return RLock
     */
    public RLock lock(String key, int timeout) {
        RLock lock = redissonClient.getLock(key);
        lock.lock(timeout, TimeUnit.SECONDS);
        // log.info(">>>>> 主动加锁【{}】", key);
        return lock;
    }

    /**
     * 带超时的锁
     *
     * @param key     lockKey
     * @param unit    时间单位
     * @param timeout 超时时间
     * @return RLock
     */
    public RLock lock(String key, int timeout, TimeUnit unit) {
        RLock lock = redissonClient.getLock(key);
        lock.lock(timeout, unit);
        // log.info(">>>>> 主动加锁【{}】", key);
        return lock;

    }

    /**
     * 尝试获取锁 （当waitTime == 0 && leaseTime == 0时, 锁会自动延期）
     *
     * @param key       lockKey
     * @param waitTime  最多等待时间
     * @param leaseTime 上锁后自动释放锁时间
     * @return boolean
     */
    public boolean tryLock(String key, long waitTime, long leaseTime) {
        RLock lock = redissonClient.getLock(key);
        try {
            if (waitTime == 0 && leaseTime == 0) {
                return lock.tryLock();
            } else {
                return lock.tryLock(waitTime, leaseTime, TimeUnit.SECONDS);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return false;
    }

    /**
     * 尝试获取锁 （当waitTime == 0 && leaseTime == 0时, 锁会自动延期）
     *
     * @param key       lockKey
     * @param unit      时间单位
     * @param waitTime  最多等待时间
     * @param leaseTime 上锁后自动释放锁时间
     * @return boolean
     */
    public boolean tryLock(String key, long waitTime, long leaseTime, TimeUnit unit) {
        RLock lock = redissonClient.getLock(key);
        try {
            if (waitTime == 0 && leaseTime == 0) {
                return lock.tryLock();
            } else {
                return lock.tryLock(waitTime, leaseTime, unit);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return false;
    }

    /**
     * 判断是否已被锁住
     *
     * @param key lockKey
     * @return boolean
     */
    public boolean isLock(String key) {
        if (key != null) {
            RLock lock = redissonClient.getLock(key);
            return lock.isLocked();
        }
        return false;
    }

    /**
     * 释放锁
     *
     * @param key lockKey
     */
    public void unlock(String key) {
        if (key != null) {
            RLock lock = redissonClient.getLock(key);
            this.unlock(lock);
        }
    }

    /**
     * 释放锁
     *
     * @param lock 锁
     */
    public void unlock(RLock lock) {
        if (lock != null && lock.isLocked() && lock.isHeldByCurrentThread()) {
            lock.unlock();
            // log.info("<<<<< 主动解锁【{}】", lock.getName());
        }
    }

}
