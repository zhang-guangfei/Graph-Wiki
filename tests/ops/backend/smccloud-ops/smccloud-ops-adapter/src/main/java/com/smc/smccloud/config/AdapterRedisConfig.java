package com.smc.smccloud.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.*;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashSet;
import java.util.Set;

/**
 * @Author lyc
 * @Date 2022/3/22 18:20
 * @Descripton TODO
 */
@Slf4j
@Configuration
@EnableCaching
public class AdapterRedisConfig {

//    @Value("${spring.redis.door-redis.cluster.nodes}")
//    private String host;
//    @Value("${spring.redis.door-redis.port}")
//    private Integer port;
//    @Value("${spring.redis.door-redis.password}")
//    private String password;
//    @Value("${spring.redis.door-redis.database}")
//    private Integer database;

    @Value("${spring.redis.door-redis.cluster.nodes}")
    private String nodes;
    @Value("${spring.redis.door-redis.password}")
    private String password;
    @Value("${spring.redis.door-redis.cluster.max-redirects}")
    private Integer maxRedirects;

    @Value("${spring.profiles.active}")
    private String profiles;


    @Value("${spring.redis.uat-redis.hostName}")
    private String uatHost;
    @Value("${spring.redis.uat-redis.port}")
    private Integer uatPort;
    @Value("${spring.redis.uat-redis.password}")
    private String uatPassword;
    @Value("${spring.redis.uat-redis.database}")
    private Integer database;
    @Value("${spring.redis.lettuce.pool.max-idle}")
    private Integer maxIdle;  // 最大空闲连接
    @Value("${spring.redis.lettuce.pool.max-active}")
    private Integer maxActive; // 最大连接数
    @Value("${spring.redis.lettuce.pool.maxWaitMillis}")
    private Integer maxWait;  // 连接池最大阻塞等待时间

    public AdapterRedisConfig() {
    }

    /**
     * 配置门户redis数据源-pord
     */
    @Bean("adapterRedisConfiguration")
    public RedisConfiguration adapterRedisConfiguration() {
        String[] doorRedisNodes = nodes.split(",");
        Set<RedisNode> clusterNodes = new HashSet<>(doorRedisNodes.length);
        String[] hp;
        for (String node : doorRedisNodes) {
            hp = node.split(":");
            clusterNodes.add(new RedisNode(hp[0], Integer.parseInt(hp[1])));
        }

        RedisClusterConfiguration redisClusterConfiguration = new RedisClusterConfiguration();
        redisClusterConfiguration.setClusterNodes(clusterNodes);
        redisClusterConfiguration.setPassword(password);
        return redisClusterConfiguration;
    }



    /**
     * 配置门户redis数据源-dev
     */
    /*@Bean("adapterRedisConfiguration")
    public RedisConfiguration adapterRedisConfiguration() {
        String host = environment.getProperty("spring.redis.door-redis.host");
        String password = environment.getProperty("spring.redis.door-redis.password");
        String database = environment.getProperty("spring.redis.door-redis.database");
        String port = environment.getProperty("spring.redis.door-redis.port");
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        //设置redis服务器的host或者ip地址
        redisStandaloneConfiguration.setHostName(host);
        //设置默认使用的数据库
        redisStandaloneConfiguration.setDatabase(Integer.parseInt(database));
        //设置密码
        redisStandaloneConfiguration.setPassword(RedisPassword.of(password));
        //设置redis的服务的端口号
        redisStandaloneConfiguration.setPort(Integer.parseInt(port));
        return redisStandaloneConfiguration;
    }*/

    /**
     * 配置门户redis数据源的连接工厂
     */
    @Bean("adapterLettuceConnectionFactory")
    public LettuceConnectionFactory adapterLettuceConnectionFactory(GenericObjectPoolConfig redisPool,
                                                                    @Qualifier("adapterRedisConfiguration") RedisConfiguration redisConfig) {

        LettuceClientConfiguration clientConfig = LettucePoolingClientConfiguration
                .builder().poolConfig(redisPool).build();
        return new LettuceConnectionFactory(redisConfig, clientConfig);
    }

    @Bean(value = "adapterRedisTemplate")
    public RedisTemplate<String, Object> redisTemplate(
            @Qualifier("adapterLettuceConnectionFactory") RedisConnectionFactory redisConnectionFactory) {

        if("uat".equals(profiles)){
            return uatRedisTemplate();
        }else {
            RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
            RedisSerializer stringSerializer = new StringRedisSerializer();
            redisTemplate.setKeySerializer(stringSerializer);
            redisTemplate.setHashKeySerializer(stringSerializer);
            //redisTemplate.setValueSerializer(stringSerializer);
            //redisTemplate.setHashValueSerializer(stringSerializer);
            redisTemplate.setConnectionFactory(redisConnectionFactory);
            return redisTemplate;
        }
    }



    /**---------------------------------------------uat 单节点redis------------------------------------------------------------*/
    //连接池配置
    public JedisPoolConfig poolConfig(int maxIdle, int maxTotal, long maxWaitMillis, boolean testOnBorrow) {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxIdle(maxIdle);
        poolConfig.setMaxTotal(maxTotal);
        poolConfig.setMaxWaitMillis(maxWaitMillis);
        poolConfig.setTestOnBorrow(testOnBorrow);
        return poolConfig;
    }

    public RedisConnectionFactory connectionFactory(String host, int port, String password, int maxIdle,
                                                    int maxTotal, long maxWaitMillis, int index) {
        RedisStandaloneConfiguration redisStandaloneConfiguration =
                new RedisStandaloneConfiguration();
        //设置redis服务器的host或者ip地址
        redisStandaloneConfiguration.setHostName(host);
        //设置默认使用的数据库
        redisStandaloneConfiguration.setDatabase(index);
        //设置密码
        redisStandaloneConfiguration.setPassword(RedisPassword.of(password));
        //设置redis的服务的端口号
        redisStandaloneConfiguration.setPort(port);
        //获得默认的连接池构造器
        JedisClientConfiguration.JedisPoolingClientConfigurationBuilder jpcb =
                (JedisClientConfiguration.JedisPoolingClientConfigurationBuilder)JedisClientConfiguration.builder();
        //指定jedisPoolConifig来修改默认的连接池构造器
        jpcb.poolConfig(poolConfig(maxIdle, maxTotal, maxWaitMillis, false));
        //通过构造器来构造jedis客户端配置
        JedisClientConfiguration jedisClientConfiguration = jpcb.build();
        //单机配置 + 客户端配置 = jedis连接工厂
        return new JedisConnectionFactory(redisStandaloneConfiguration, jedisClientConfiguration);
    }

    public RedisTemplate<String, Object> uatRedisTemplate() {
        RedisTemplate<String, Object>  template = new RedisTemplate<String, Object>();
        template.setConnectionFactory(
                connectionFactory(uatHost, uatPort, uatPassword, maxIdle, maxActive, maxWait, database));
        RedisSerializer stringSerializer = new StringRedisSerializer();
        template.setKeySerializer(stringSerializer);
        template.setHashKeySerializer(stringSerializer);
        return template;
    }

}
