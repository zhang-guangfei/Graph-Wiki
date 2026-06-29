package com.sales.ops.common.until;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Redis工具类
 */
@Component
public class OPSRedisUtils {
    @Autowired
    private RedisTemplate redisTemplate;


    public Boolean hasKey(String key) {
        try {
            return redisTemplate.hasKey(key);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Set<String> keys(String prefix) {
        try {
            return redisTemplate.keys(prefix + "*");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 指定缓存失效时间
     */
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

    public boolean delete(String key) {
        try {
            redisTemplate.delete(key);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    public boolean set(String key, Object obj) {
        try {
            redisTemplate.opsForValue().set(key, obj);
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
    public boolean set(String key, Object value, long time) {
        try {
            if (time > 0) {
                redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
                return true;
            } else {
                set(key, value);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 设置指定 key 的值
     */
    public boolean setKeyAndTimeoutDays(String key, Object value, long timeOut) {
        try {
            redisTemplate.opsForValue().set(key, value, timeOut, TimeUnit.DAYS);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean setKeyAndTimeout(String key, Object value, long timeOut, TimeUnit timeUnit) {
        try {
            redisTemplate.opsForValue().set(key, value, timeOut, timeUnit);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 获取指定 key 的值
     *
     * @param key
     * @return
     */
    public Object get(String key) {
        try {
            return redisTemplate.opsForValue().get(key);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * --------------------Hash相关操作--------------------------
     */
    public boolean hPut(String key, String Hkey, Object value) {
        try {
            redisTemplate.opsForHash().put(key, Hkey, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean hPutAll(String key, Map<String, Object> maps) {
        try {
            redisTemplate.opsForHash().putAll(key, maps);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Object hGet(String key, String Hkey) {
        try {
            return redisTemplate.opsForHash().get(key, Hkey);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Map<Object, Object> hGetAll(String key) {
        try {
            return redisTemplate.opsForHash().entries(key);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 删除一个或多个哈希表字段
     *
     * @param key
     * @param fields
     * @return
     */
    public Long hDelete(String key, Object... fields) {
        try {
            return redisTemplate.opsForHash().delete(key, fields);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

/** --------------------set相关操作-------------------------- */
    /**
     * set添加元素
     *
     * @param key
     * @param values
     * @return
     */
    public Long sAdd(String key, String... values) {
        try {
            return redisTemplate.opsForSet().add(key, values);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * set移除元素
     *
     * @param key
     * @param values
     * @return
     */
    public Long sRemove(String key, Object... values) {
        try {
            return redisTemplate.opsForSet().remove(key, values);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 获取集合的大小
     *
     * @param key
     * @return
     */
    public Long sSize(String key) {
        try {
            return redisTemplate.opsForSet().size(key);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 判断集合是否包含value
     *
     * @param key
     * @param value
     * @return
     */
    public Boolean sIsMember(String key, Object value) {
        try {
            return redisTemplate.opsForSet().isMember(key, value);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}
