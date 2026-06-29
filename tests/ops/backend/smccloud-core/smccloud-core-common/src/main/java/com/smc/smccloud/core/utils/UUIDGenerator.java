package com.smc.smccloud.core.utils;

import java.util.UUID;

// @Slf4j
public class UUIDGenerator {
    public UUIDGenerator() {
    }

    public static String getUUID() {
        String s = UUID.randomUUID().toString();
        return s.substring(0, 8) + s.substring(9, 13) + s.substring(14, 18) + s.substring(19, 23) + s.substring(24);
    }

    public static String getRandomNum() {
        return (int) ((Math.random() * 9 + 1) * 100000) + "";
    }

    // smccms
    // 77a80fc29857485e93c9a2d4c9fdfc94
    public static void main(String[] args) {
        String uuid = getUUID();
        System.out.println("uuid = " + uuid);
        String uuid2 = getUUID();
        System.out.println("uuid2 = " + uuid2);

    }
}
