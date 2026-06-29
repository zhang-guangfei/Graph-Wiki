package com.smc.smccloud.core.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 基于spring和redis的redisTemplate工具类
 */
@Component
public class RedisManager {

    @Autowired
    @Qualifier(value = "opsRedisTemplate")
    private RedisTemplate<String, Object> redisTemplate;

    //=============================common============================

    /**
     * 指定缓存失效时间
     *
     * @param key  键
     * @param time 时间(秒)
     */
    public boolean expire(String key, long time) {
        try {
            if (time > 0) {
                redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean expire(String key, long time, TimeUnit timeUnit) {
        try {
            if (time > 0) {
                redisTemplate.expire(key, time, timeUnit);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 判断key是否存在
     *
     * @param key 键
     * @return true 存在 false不存在
     */
    public Boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 删除缓存
     *
     * @param key 可以传一个值 或多个
     */
    @SuppressWarnings("unchecked")
    public void del(String... key) {
        if (key != null && key.length > 0) {
            if (key.length == 1) {
                redisTemplate.delete(key[0]);
            } else {
                redisTemplate.delete(CollectionUtils.arrayToList(key));
            }
        }
    }

    /**
     * 删除缓存
     *
     * @param key 一个值
     */
    public boolean delete(String key) {
        return redisTemplate.delete(key);
    }


    /**
     * 批量删除key
     *
     * @param keys
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public void del(Collection keys) {
        redisTemplate.delete(keys);
    }

    //============================String=============================

    /**
     * 普通缓存获取
     *
     * @param key 键
     * @return 值
     */
    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 普通缓存放入
     *
     * @param key   键
     * @param value 值
     */
    public boolean set(String key, Object value) {
        try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 普通缓存放入并设置时间
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒) time要大于0 如果time小于等于0 将设置无限期
     */
    public void set(String key, Object value, long time) {
        if (time > 0) {
            redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
        } else {
            set(key, value);
        }
    }

    /**
     * 使用scan命令 查询某些前缀的key
     *
     * @param key
     * @return
     */
    public Set<String> scan(String key) {
        Set<String> execute = this.redisTemplate.execute(new RedisCallback<Set<String>>() {

            @Override
            public Set<String> doInRedis(RedisConnection connection) throws DataAccessException {

                Set<String> binaryKeys = new HashSet<>();

                Cursor<byte[]> cursor = connection.scan(new ScanOptions.ScanOptionsBuilder().match(key).count(1000).build());
                while (cursor.hasNext()) {
                    binaryKeys.add(new String(cursor.next()));
                }
                return binaryKeys;
            }
        });
        return execute;
    }

    /**
     * 使用scan命令 查询某些前缀的key 有多少个
     * 用来获取当前session数量,也就是在线用户
     *
     * @param key
     * @return
     */
    public Long scanSize(String key) {
        long dbSize = this.redisTemplate.execute(new RedisCallback<Long>() {

            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                long count = 0L;
                Cursor<byte[]> cursor = connection.scan(ScanOptions.scanOptions().match(key).count(1000).build());
                while (cursor.hasNext()) {
                    cursor.next();
                    count++;
                }
                return count;
            }
        });
        return dbSize;
    }


    // -----------------------------------SET------------------------------------
    // 存储Set相关操作

    /**
     * 往Set中存入数据
     *
     * @param key    Redis键
     * @param values 值
     * @return 存入的个数
     */
    public long sSet(String key, Object... values) {
        Long count = redisTemplate.opsForSet().add(key, values);
        return count == null ? 0 : count;
    }

    /**
     * 删除Set中的数据
     *
     * @param key    Redis键
     * @param values 值
     * @return 移除的个数
     */
    public long sDel(String key, Object... values) {
        Long count = redisTemplate.opsForSet().remove(key, values);
        return count == null ? 0 : count;
    }

    /**
     * 获取set集合的大小
     */
    public long sGetSize(String key) {
        Long size = redisTemplate.opsForSet().size(key);
        return size == null ? 0 : size;
    }

    /**
     * 判断集合中是否存在某值
     *
     * @param key
     * @param value
     */
    public Boolean sContains(String key, String value) {
        return redisTemplate.opsForSet().isMember(key, value);
    }

    /**
     * 随机获取set集合中的一个元素
     */
    public Object sGetVal(String key) {
        return redisTemplate.opsForSet().randomMember(key);
    }

    /**
     * 移除并返回集合中的一个随机元素
     */
    public Object sPop(String key) {
        return redisTemplate.opsForSet().pop(key);
    }

    /**
     * 获取键的所有值
     *
     * @param key 键
     * @return 值
     */
    public Set<Object> members(String key) {
        return redisTemplate.opsForSet().members(key);
    }

    //  -------------------------------Hash--------------------------

    /**
     * 往Hash中存入数据
     *
     * @param key   Redis键
     * @param hKey  Hash键
     * @param value 值
     */
    public void hPut(final String key, final String hKey, final Object value) {

        redisTemplate.opsForHash().put(key, hKey, value);
    }


    /**
     * 往Hash中存入多个数据
     *
     * @param key    Redis键
     * @param values Hash键值对
     */
    public void hPutAll(final String key, final Map<String, Object> values) {

        redisTemplate.opsForHash().putAll(key, values);
    }

    /**
     * 获取Hash中的数据
     *
     * @param key  Redis键
     * @param hKey Hash键
     * @return Hash中的对象
     */
    public Object hGet(final String key, final String hKey) {

        return redisTemplate.opsForHash().get(key, hKey);
    }

    /**
     * 根据key获取所有 HashKey
     *
     * @param key
     * @return
     */
    public Object getHashKeyValue(String key) {
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        HashOperations<String, Object, Object> ops = redisTemplate.opsForHash();
        return ops.entries(key);
    }

    /**
     * 获取多个Hash中的数据
     *
     * @param key   Redis键
     * @param hKeys Hash键集合
     * @return Hash对象集合
     */
    public List<Object> hMultiGet(final String key, final Collection<Object> hKeys) {

        return redisTemplate.opsForHash().multiGet(key, hKeys);
    }

    /**
     * 删除hash表中的值
     *
     * @param key  键 不能为null
     * @param item 项 可以使多个 不能为null
     */
    public void hdel(String key, Object... item) {
        redisTemplate.opsForHash().delete(key, item);
    }

    /**
     * 判断hash表中是否有该项的值
     *
     * @param key  键 不能为null
     * @param item 项 不能为null
     * @return true 存在 false不存在
     */
    public boolean hHasKey(String key, String item) {
        return redisTemplate.opsForHash().hasKey(key, item);
    }


    //  -----------------------------List--------------------

    /**
     * 往list的右边（队列尾部）单个添加值
     *
     * @param key   键
     * @param value 值
     */
    public boolean lRightPush(String key, Object value) {
        Long size = redisTemplate.opsForList().rightPush(key, value);
        return size != null && size > 0;
    }

    /**
     * 往list的右边（队列尾部）批量添加值
     *
     * @param key   键
     * @param value 值
     */
    public boolean lRightPush(String key, Object... value) {
        Long size = redisTemplate.opsForList().rightPushAll(key, value);
        return size != null && size > 0;
    }

    /**
     * 从List尾部获取一个元素（后进先出）并移除,如果超出下标返回NULL
     *
     * @param key 键
     * @return 值
     */
    public Object lRightPop(String key) {
        return redisTemplate.opsForList().rightPop(key);
    }

    /**
     * 从List头部获取一个元素（先进先出）并移除,如果超出下标返回NULL
     *
     * @param key 键
     * @return 值
     */
    public Object lLeftPop(String key) {
        return redisTemplate.opsForList().leftPop(key);
    }

    /**
     * 获取List的长度
     *
     * @param key 键
     * @return 长度
     */
    public long lSize(String key) {
        Long size = redisTemplate.opsForList().size(key);
        return Optional.ofNullable(size).orElse(0L);
    }
}
