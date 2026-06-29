package com.smc.smccloud.core.utils;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @Author lyc
 * @Date 2024/6/11 10:06
 * @Descripton TODO
 */
public class UIDGenerator {
    private static final AtomicLong counter = new AtomicLong(0);
    private static final Random random = new Random();

    private static final AtomicLong nextId = new AtomicLong(ThreadLocalRandom.current().nextLong(100000, 999999));

    public static String generateUID() {
        long uniqueNumber = counter.incrementAndGet();
        StringBuilder uid = new StringBuilder();

        for (int i = 0; i < 20; i++) {
            int randomCharIndex = random.nextInt(62);
            char randomChar;
            if (randomCharIndex < 10) {
                randomChar = (char) ('0' + randomCharIndex);
            } else if (randomCharIndex < 36) {
                randomChar = (char) ('A' + randomCharIndex - 10);
            } else {
                randomChar = (char) ('a' + randomCharIndex - 36);
            }
            uid.append(randomChar);
        }
        String uuid = UUID.randomUUID().toString();
        uid.append(uuid.substring(uuid.length()-6));

        return uid.toString();
    }



    public static String generateUIDLen6(String prefix) {
        while (true) {
            long id = nextId.getAndIncrement();
            if (id >= 100000 && id <= 999999) {
                return prefix+String.format("%06d", id)+"-1";
            }
            return "underfind";
        }
    }
}
