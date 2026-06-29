/*
 * @Descripttion:
 * @version:
 * @Author: zhang rui
 * @Date: 2019-11-21 12:03:14
 * @LastEditors: zhang rui
 * @LastEditTime: 2019-11-21 12:05:08
 */
package com.smc.smccloud.core.utils;

import com.alibaba.ttl.TransmittableThreadLocal;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/**
 * ThreadLocalMap
 */

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ThreadLocalMapUtil {

    /**
     * The constant threadContext.
     */
    private static final TransmittableThreadLocal<Map<String, Object>> THREAD_CONTEXT = new TransmittableThreadLocal<Map<String, Object>>() {
        @Override
        protected Map<String, Object> initialValue() {
            return new HashMap<>();
        }

        // 解决普通线程池中使用TTL可能造成数据污染的问题
        @Override
        protected Map<String, Object> childValue(Map<String, Object> parentValue) {
            return initialValue();
        }

        // 父子线程使用拷贝对象, 而非简单对象的引用
        @Override
        public Map<String, Object> copy(Map<String, Object> parentValue) {
            return new HashMap<>(parentValue);
        }
    };

    /**
     * Put.
     *
     * @param key   the key
     * @param value the value
     */
    public static void put(String key, Object value) {
        getContextMap().put(key, value);
    }

    /**
     * Remove object.
     *
     * @param key the key
     * @return the object
     */
    public static Object remove(String key) {
        return getContextMap().remove(key);
    }

    /**
     * Get object.
     *
     * @param key the key
     * @return the object
     */
    public static Object get(String key) {
        return getContextMap().get(key);
    }

    public static Map<String, Object> getContents() {
        return getContextMap();
    }

    /**
     * 取得thread context Map的实例。
     *
     * @return thread context Map的实例
     */
    private static Map<String, Object> getContextMap() {
        return THREAD_CONTEXT.get();
    }

    /**
     * 清理线程所有被hold住的对象,以便重用Map！
     */
    public static void remove() {
        getContextMap().clear();
    }

    /**
     * 释放内存占用
     */
    public static void clear() {
        THREAD_CONTEXT.remove();
    }
}