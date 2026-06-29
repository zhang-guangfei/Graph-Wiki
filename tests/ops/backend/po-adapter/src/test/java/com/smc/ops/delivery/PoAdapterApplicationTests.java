package com.smc.ops.delivery;

import com.smc.ops.delivery.config.RedisManager;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class PoAdapterApplicationTests {
    @Resource
    private RedisManager redisManager;
    @Test
    void contextLoads() {
        redisManager.set("testA","aa");
        System.out.println("wwwwwwwwwwwwwwwwwwwwww");
        System.out.println(redisManager.get("testA"));
    }

}
