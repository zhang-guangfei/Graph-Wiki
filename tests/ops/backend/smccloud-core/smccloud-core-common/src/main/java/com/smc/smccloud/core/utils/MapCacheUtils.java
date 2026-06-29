//package com.smc.smccloud.core.utils;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//import java.util.concurrent.ConcurrentHashMap;
//
///**
// * @Author lyc
// * @Date 2022/4/24 13:38
// * @Descripton 使用Map做缓存
// */
//
//public class MapCacheUtils {
//
//    // 用来存放数据
//    private static final Map<String, ExpireData> CACHE_OBJECT_MAP = new ConcurrentHashMap<String, ExpireData>();
//
//    /**
//     * 普通缓存放入
//     *
//     * @param key   键
//     * @param value 值
//     * @return true成功 false失败
//     */
//    public boolean set(String key, Object value) {
//        try {
//            ExpireData expireData = new ExpireData(key, value);
//            CACHE_OBJECT_MAP.put(key, expireData);
//            return true;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    /**
//     * 普通缓存放入并设置时间
//     *
//     * @param key               键
//     * @param value             值
//     * @param expireMillisecond 过期时长(毫秒),要大于0,如果小于等于0,将设置无限期
//     * @return
//     */
//    public boolean set(String key, Object value, long expireMillisecond) {
//        try {
//            if (expireMillisecond > 0) {
//                ExpireData expireData = new ExpireData(key, value, expireMillisecond);
//                CACHE_OBJECT_MAP.put(key, expireData);
//            } else {
//                set(key, value);
//            }
//            return true;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    /**
//     * 根据key获取value
//     *
//     * @param key 键
//     * @return 值
//     */
//    public Object get(String key) {
//        ExpireData expireData = CACHE_OBJECT_MAP.get(key);
//        if (expireData == null) {
//            return null;
//        }
//        if (expireData.getExpireMillisecond() == 0) {
//            return expireData.getValue();
//        }
//        long nowTime = System.currentTimeMillis();
//        if (nowTime < expireData.getEndTime()) {
//            return expireData.getValue();
//        } else {
//            return null;
//        }
//    }
//
//    /**
//     * 根据key删除
//     *
//     * @param key 键
//     * @return true 成功，false 不成功,key不存在也是true 成功
//     */
//    public boolean delete(String key) {
//        try {
//            CACHE_OBJECT_MAP.remove(key);
//            return true;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    /**
//     * 删除所有
//     *
//     * @return true 成功，false 不成功
//     */
//    public boolean flush() {
//        try {
//            CACHE_OBJECT_MAP.clear();
//            return true;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    /**
//     * 判断key是否存在
//     *
//     * @param key 键
//     * @return true 存在，false不存在
//     */
//    public boolean hasKey(String key) {
//        try {
//            return get(key) != null;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//
//    /**
//     * 根据key获取还有多长时间过期
//     *
//     * @param key 键
//     * @return 还有多长时间过期（毫秒）
//     */
//    public long getExpire(String key) {
//        ExpireData expireData = CACHE_OBJECT_MAP.get(key);
//        if (null == expireData) {
//            return 0;
//        }
//        // 这个代表没有过期时长
//        if (expireData.getExpireMillisecond() == 0) {
//            return 999999999999999L;
//        }
//        long expire = expireData.getEndTime() - System.currentTimeMillis();
//        if (expire < 0) {
//            return 0;
//        } else {
//            return expire;
//        }
//    }
//
//    /**
//     * 指定缓存失效时间
//     *
//     * @param key               键
//     * @param expireMillisecond 过期时长(毫秒),要大于0,如果小于等于0,将设置无限期
//     * @return true 成功，false 不成功
//     */
//    public boolean expire(String key, long expireMillisecond) {
//        try {
//            ExpireData expireData = CACHE_OBJECT_MAP.get(key);
//            if (null == expireData) {
//                return false;
//            }
//            if (expireMillisecond > 0) {
//                expireData = new ExpireData(key, expireData.getValue(), expireMillisecond);
//                CACHE_OBJECT_MAP.put(key, expireData);
//            } else {
//                set(key, expireData.getValue());
//            }
//            return true;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    /**
//     * 获取所有的键
//     *
//     * @return
//     */
//    public List<String> getKeys() {
//        List<String> list = new ArrayList<String>();
//        for (String key : CACHE_OBJECT_MAP.keySet()) {
//            if (hasKey(key)) {
//                list.add(key);
//            }
//        }
//        return list;
//    }
//
//    /**
//     * 删除过期的缓存(该方法需要根据实际业务定时调用，不然可能导致内存泄露)
//     */
//    public void deleteTimeOut() {
//        System.out.println(CACHE_OBJECT_MAP.keySet());
//        for (String key : CACHE_OBJECT_MAP.keySet()) {
//            if (!hasKey(key)) {
//                delete(key);
//            }
//        }
//        System.out.println(CACHE_OBJECT_MAP.keySet());
//    }
//
//    /**
//     * 保存数据实体类
//     */
//    private static class ExpireData {
//        private String key; // 键
//        private Object value; // 值
//        private long expireMillisecond;//过期时长
//        private long startTime;//保存时间
//        private long endTime;//过期时间
//
//        public ExpireData(String key, Object value) {
//            this.key = key;
//            this.value = value;
//        }
//
//        public ExpireData(String key, Object value, Long expireMillisecond) {
//            this.key = key;
//            this.value = value;
//            this.expireMillisecond = expireMillisecond;
//            this.startTime = System.currentTimeMillis();
//            this.endTime = startTime + expireMillisecond;
//        }
//
//        public String getKey() {
//            return key;
//        }
//
//        public void setKey(String key) {
//            this.key = key;
//        }
//
//        public Object getValue() {
//            return value;
//        }
//
//        public void setValue(Object value) {
//            this.value = value;
//        }
//
//        public long getExpireMillisecond() {
//            return expireMillisecond;
//        }
//
//        public void setExpireMillisecond(long expireMillisecond) {
//            this.expireMillisecond = expireMillisecond;
//        }
//
//        public long getStartTime() {
//            return startTime;
//        }
//
//        public void setStartTime(long startTime) {
//            this.startTime = startTime;
//        }
//
//        public long getEndTime() {
//            return endTime;
//        }
//
//        public void setEndTime(long endTime) {
//            this.endTime = endTime;
//        }
//    }
//
//    /**
//     * 私有构造方法，为了防止被别的类new出来
//     */
//    private MapCacheUtils() {
//        // 反射破解单例模式需要添加的代码
//        if (SingletonHolder.hashMapCache != null) {
//            throw new RuntimeException();
//        }
//    }
//
//    /**
//     * 在成员位置创建该类的对象
//     */
//    private static class SingletonHolder {
//        private static final MapCacheUtils hashMapCache = new MapCacheUtils();
//    }
//
//    /**
//     * 对外提供静态方法获取该对象
//     *
//     * @return
//     */
//    public static MapCacheUtils getHashMapCache() {
//        return SingletonHolder.hashMapCache;
//    }
//
//    /**
//     * 下面是为了解决序列化反序列化破解单例模式
//     *
//     * @return
//     */
//    private Object readResolve() {
//        return SingletonHolder.hashMapCache;
//    }
//
//
//}
