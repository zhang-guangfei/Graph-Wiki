package com.smc.ops.delivery.config.redisson;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

/**
 * @Author lyc
 * @Date 2021/12/22 11:21
 * @Descripton TODO
 */
@Configuration
public class RedissonConfig {

//    @Value("${spring.redis.ops-redis.hostName}")
//    private String host;
//    @Value("${spring.redis.ops-redis.port}")
//    private String port;
//    @Value("${spring.redis.ops-redis.password}")
//    private String password;

    @Value("${spring.redis.ops-redis.cluster.nodes}")
    private String opsRedisCluster;
    @Value("${spring.redis.ops-redis.password}")
    private String opsRedisPassword;

    @Bean
    public RedissonClient redissonClient() {
        Config config = new Config();
        // 单节点
//        config.useSingleServer().setAddress("redis://" + host + ":" + port);
//        config.useSingleServer().setPassword(password);
        // 添加主从配置
        // config.useMasterSlaveServers().setMasterAddress("").setPassword("").addSlaveAddress(new String[]{"",""});
        // 集群模式配置 setScanInterval()扫描间隔时间，单位是毫秒, //可以用"rediss://"来启用SSL连接
        String[] clusterNodes = opsRedisCluster.split(",");
        String[] nodes = new String[clusterNodes.length];

        for (int i = 0; i < clusterNodes.length; i++) {
            nodes[i] = "redis://" + clusterNodes[i];
        }
        System.out.println("redissonClient clusterNodes = " + Arrays.deepToString(nodes));
        config.useClusterServers()
                .setScanInterval(2000)
                .addNodeAddress(nodes)
                .setPassword(opsRedisPassword);
        return Redisson.create(config);
    }


}
