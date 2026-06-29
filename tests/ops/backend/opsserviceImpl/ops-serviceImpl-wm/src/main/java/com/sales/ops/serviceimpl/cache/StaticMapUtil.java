package com.sales.ops.serviceimpl.cache;

import com.sales.ops.db.entity.*;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;

/**
 * @author ：C14717
 * @version: $
 * @description：
 * @date ：Created in 2026/4/23 10:07
 */
public class StaticMapUtil {

    // 出库规则
    public static HashMap<String, OpsInventoryMatchConfig> invMatchConfig = new HashMap<String, OpsInventoryMatchConfig>();

    // 出库规则
    public static HashMap<String, OpsInventoryRuleConfig> invRuleConfig = new HashMap<String, OpsInventoryRuleConfig>();

    // 出库规则
    public static HashMap<String, List<OpsInventoryRuleConfigItem>> invRuleConfigItemMap = new HashMap<String, List<OpsInventoryRuleConfigItem>>();

    // 出库规则
    public static HashMap<Integer, List<OpsOrderInventoryRuleConfig>> orderRuleConfigMap = new HashMap<Integer, List<OpsOrderInventoryRuleConfig>>();

    // 出库规则
    public static HashMap<String, OpsInventoryType> invTypeMap = new HashMap<String, OpsInventoryType>();

    public static HashMap<Long, List<String>> warehouseListHashMap = new HashMap<Long, List<String>>();
    // <deptNo,ksh:list>
    public static HashMap<String, List<OpsWarehouseSalesbranchConfig>> salesbranchConfigMap = new HashMap<String, List<OpsWarehouseSalesbranchConfig>>();


    public static void clearAllMap() {
        invMatchConfig.clear();
        invRuleConfig.clear();
        invRuleConfigItemMap.clear();
        orderRuleConfigMap.clear();
        invTypeMap.clear();
        warehouseListHashMap.clear();
        salesbranchConfigMap.clear();
    }

    public static long hash(String key) {

        // md5 byte
        MessageDigest md5;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5 not supported", e);
        }
        md5.reset();
        byte[] keyBytes = null;
        try {
            keyBytes = key.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Unknown string :" + key, e);
        }

        md5.update(keyBytes);
        byte[] digest = md5.digest();

        // hash code, Truncate to 32-bits
        long hashCode = ((long) (digest[3] & 0xFF) << 24) | ((long) (digest[2] & 0xFF) << 16)
                | ((long) (digest[1] & 0xFF) << 8) | (digest[0] & 0xFF);

        long truncateHashCode = hashCode & 0xffffffffL;
        return truncateHashCode;
    }
}
