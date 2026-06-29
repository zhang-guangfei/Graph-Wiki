package com.sales.ops.redisson;

import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ：C14717
 * @version: 1.0$
 * @description：分布式锁
 * @date ：Created in 2022/2/11 9:45
 */

@Configuration
@Slf4j
public class RedissonConfig {

   @Value("${spring.redis.ops-redis.cluster.nodes}")
   private String opsRedisCluster;
   @Value("${spring.redis.ops-redis.password}")
   private String opsRedisPassword;

    @Bean
    public RedissonClient redissonClient(){
        RedissonClient redissonClient;

        Config config = new Config();
        String[] clusterNodes = opsRedisCluster.split(",");
        String[] nodes = new String[clusterNodes.length];
        for (int i = 0; i < clusterNodes.length; i++) {
            nodes[i] = "redis://" + clusterNodes[i];
        }
        config.useClusterServers()
                .setScanInterval(2000)
                .addNodeAddress(nodes)
                .setPassword(opsRedisPassword)
                // 默认是 3*50 * 5 = 750
                // 修改为 3 个主节点master 3（masters） × 60（订阅连接/节点） = 180 个订阅连接 180 × 10 = 1800 个并发订阅
                .setSubscriptionsPerConnection(10).setSubscriptionConnectionPoolSize(60);
        try {
            redissonClient = Redisson.create(config);
            return redissonClient;
        } catch (Exception e) {
            log.error(e.toString());
            return null;
        }
    }
}

