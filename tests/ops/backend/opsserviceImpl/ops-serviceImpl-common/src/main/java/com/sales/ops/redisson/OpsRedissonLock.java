package com.sales.ops.redisson;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;


/**
 * @author ：C14717
 * @version: 1.0$
 * @description：分布式锁
 * @date ：Created in 2022/2/11 9:55
 */

@Component
@Slf4j
public class OpsRedissonLock {
    @Resource
    private RedissonClient redissonClient;


/**
     * 加锁
     * @param lockKey
     * @return
     */

    public boolean addLock(String lockKey){

        try {
            if (redissonClient == null){
                log.warn("redisson client is null");
                return false;
            }

            RLock lock = redissonClient.getLock(lockKey);

            //设置锁超时时间为10分钟，到期自动释放
            lock.lock(600, TimeUnit.SECONDS);

            log.info(Thread.currentThread().getName()+":  获取到锁");

            //加锁成功
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }


    public boolean addLock(String lockKey,long timeout){

        try {
            if (redissonClient == null){
                log.warn("redisson client is null");
                return false;
            }

            RLock lock = redissonClient.getLock(lockKey);

            //设置锁超时时间为10分钟，到期自动释放
            lock.lock(timeout, TimeUnit.SECONDS);

            log.info(Thread.currentThread().getName()+":  获取到锁");

            //加锁成功
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }

    public boolean releaseLock(String lockKey){

        try{
            if (redissonClient == null){
                log.info("redisson client is null");
                return false;
            }

            RLock lock = redissonClient.getLock(lockKey);
            lock.unlock();
            log.info(Thread.currentThread().getName()+":  释放锁");
            return true;
        }catch (Exception e){
            log.error(e.getMessage());
            return false;
        }
    }
}

