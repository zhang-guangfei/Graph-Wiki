package com.smc.smccloud.config;

import org.joda.time.DateTime;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
public class AdapterRedisManager {

    @Resource(name = "adapterRedisTemplate")
    private RedisTemplate<String, Object> redisTemplate;

    public AdapterRedisManager() {
    }

    public String getCacheCreateTime(String key, long saveTime) {
        Long expireTime = this.redisTemplate.getExpire(key, TimeUnit.MILLISECONDS);
        String createDate = "";
        DateTime nowDate = new DateTime();
        if (expireTime != null && expireTime >= 0L) {
            long createDateTime = nowDate.getMillis() + expireTime - saveTime * 1000L;
            DateTime dateTime = nowDate.withMillis(createDateTime);
            createDate = dateTime.toString("yyyy-MM-dd HH:mm:ss");
        }

        return createDate;
    }

    public void expire(String key, long time) {
        this.redisTemplate.expire(key, time, TimeUnit.SECONDS);
    }

    public Boolean hasKey(String key) {
        return this.redisTemplate.hasKey(key);
    }

    public void del(String... key) {
        if (key != null && key.length > 0) {
            if (key.length == 1) {
                this.redisTemplate.delete(key[0]);
            } else {
                this.redisTemplate.delete(CollectionUtils.arrayToList(key));
            }
        }

    }

    public boolean delete(String key) {
        return this.redisTemplate.delete(key);
    }

    public void delScan(String key) {
        this.redisTemplate.delete(this.scan(key));
    }

    public void del(Collection keys) {
        this.redisTemplate.delete(keys);
    }

    public Object get(String key) {
        return this.redisTemplate.opsForValue().get(key);
    }

    public void set(String key, Object value) {
        this.redisTemplate.opsForValue().set(key, value);
    }

    public void set(String key, Object value, long time) {
        if (time > 0L) {
            this.redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
        } else {
            this.set(key, value);
        }

    }

    public Set<String> scan(String key) {
        Set<String> execute = this.redisTemplate.execute((RedisCallback<Set<String>>) connection -> {
            Set<String> binaryKeys = new HashSet();
            Cursor cursor = connection.scan((new ScanOptions.ScanOptionsBuilder()).match(key).count(1000L).build());

            while (cursor.hasNext()) {
                binaryKeys.add(new String((byte[]) cursor.next()));
            }

            return binaryKeys;
        });
        return execute;
    }

    public Long scanSize(String key) {
        long dbSize = this.redisTemplate.execute((RedisCallback<Long>) connection -> {
            long count = 0L;

            for (Cursor cursor = connection.scan(ScanOptions.scanOptions().match(key).count(1000L).build()); cursor.hasNext(); ++count) {
                cursor.next();
            }

            return count;
        });
        return dbSize;
    }
}