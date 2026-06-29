package com.sales.ops.serviceimpl.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author ：C14717
 * @version: $
 * @description：
 * @date ：Created in 2025/3/24 16:19
 */
public class HashCodeUtil {

    public static Long hashCodeInventoryKey(Long propertyId,String modelNo,String warehouseCode,String inventoryStatus) {
        final int prime = 31;
        Long result = 1L;
        result = prime * result + (propertyId.hashCode());
        result = prime * result + (hash(modelNo));
        result = prime * result + (warehouseCode.hashCode());
        result = prime * result + (inventoryStatus.hashCode());
        return result;
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
