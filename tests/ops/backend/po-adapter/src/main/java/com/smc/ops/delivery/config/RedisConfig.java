package com.smc.ops.delivery.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.lang.reflect.Method;
import java.time.Duration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Configuration
@EnableCaching // 开启缓存支持
public class RedisConfig extends CachingConfigurerSupport {

//    /**
//     * ops-redis
//     */
//    @Value("${spring.redis.ops-redis.hostName}")
//    private String host;
//    @Value("${spring.redis.ops-redis.port}")
//    private Integer port;
//    @Value("${spring.redis.ops-redis.password}")
//    private String password;
//    @Value("${spring.redis.ops-redis.database}")
//    private Integer database;
//
//    @Value("${spring.redis.lettuce.pool.max-idle}")
//    private Integer maxIdle;  // 最大空闲连接
//    @Value("${spring.redis.lettuce.pool.max-active}")
//    private Integer maxActive; // 最大连接数
//    @Value("${spring.redis.lettuce.pool.maxWaitMillis}")
//    private Integer maxWait;  // 连接池最大阻塞等待时间

    @Value("${spring.redis.ops-redis.cluster.nodes}")
    private String nodes;
    @Value("${spring.redis.ops-redis.password}")
    private String password;
    @Value("${spring.redis.ops-redis.cluster.max-redirects}")
    private Integer maxRedirects;

    /**
     * 配置lettuce连接池
     */
    @Primary
    @Bean
    @ConfigurationProperties("spring.redis.lettuce.pool")
    public GenericObjectPoolConfig adapterRedisPool() {
        return new GenericObjectPoolConfig();
    }

//    //连接池配置
//    public JedisPoolConfig poolConfig(int maxIdle, int maxTotal, long maxWaitMillis, boolean testOnBorrow) {
//        JedisPoolConfig poolConfig = new JedisPoolConfig();
//        poolConfig.setMaxIdle(maxIdle);
//        poolConfig.setMaxTotal(maxTotal);
//        poolConfig.setMaxWaitMillis(maxWaitMillis);
//        poolConfig.setTestOnBorrow(testOnBorrow);
//        return poolConfig;
//    }

//    /**
//     * 配置门户redis数据源
//     */
//    @Primary
//    @Bean("opsRedisConfig")
//    public RedisStandaloneConfiguration opsRedisConfig() {
//        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
//        //设置redis服务器的host或者ip地址
//        redisStandaloneConfiguration.setHostName(host);
//        //设置默认使用的数据库
//        redisStandaloneConfiguration.setDatabase(database);
//        //设置密码
//        redisStandaloneConfiguration.setPassword(RedisPassword.of(password));
//        //设置redis的服务的端口号
//        redisStandaloneConfiguration.setPort(port);
//        return redisStandaloneConfiguration;
//    }

    /**
     * 配置门户redis数据源
     */
    @Primary
    @Bean("opsRedisConfig")
    public RedisClusterConfiguration opsRedisConfig() {

        String[] opsRedisNodes = nodes.split(",");
        Set<RedisNode> clusterNodes = new HashSet<>(opsRedisNodes.length);
        String[] hp;
        for (String node : opsRedisNodes) {
            hp = node.split(":");
            clusterNodes.add(new RedisNode(hp[0], Integer.parseInt(hp[1])));
        }

        RedisClusterConfiguration redisClusterConfiguration = new RedisClusterConfiguration();
        redisClusterConfiguration.setClusterNodes(clusterNodes);
        redisClusterConfiguration.setPassword(password);
        redisClusterConfiguration.setMaxRedirects(maxRedirects);
        return redisClusterConfiguration;
    }

//    @Primary
//    @Bean("opsRedisConnectionFactory")
//    public RedisConnectionFactory opsRedisConnectionFactory(GenericObjectPoolConfig poolConfig,
//                                                            @Qualifier("opsRedisConfig") RedisClusterConfiguration redisConfig) {
//        //获得默认的连接池构造器
//        JedisClientConfiguration.JedisPoolingClientConfigurationBuilder jpcb =
//                (JedisClientConfiguration.JedisPoolingClientConfigurationBuilder) JedisClientConfiguration.builder();
//        //指定jedisPoolConifig来修改默认的连接池构造器
//        jpcb.poolConfig(poolConfig);
//        //通过构造器来构造jedis客户端配置
//        JedisClientConfiguration jedisClientConfiguration = jpcb.build();
//        //单机配置 + 客户端配置 = jedis连接工厂
//        return new JedisConnectionFactory(redisConfig, jedisClientConfiguration);
//    }

    /**
     * 配置门户redis数据源的连接工厂
     */
    @Primary
    @Bean("opsRedisConnectionFactory")
    public LettuceConnectionFactory adapterLettuceConnectionFactory(GenericObjectPoolConfig redisPool,
                                                                    @Qualifier("opsRedisConfig") RedisConfiguration redisConfig) {

        LettuceClientConfiguration clientConfig = LettucePoolingClientConfiguration
                .builder().poolConfig(redisPool).build();
        return new LettuceConnectionFactory(redisConfig, clientConfig);
    }

    /**
     * ---------------------------------------------------------------------------------------------------------
     */

    // 缓存管理器
    @Bean
    public CacheManager cacheManager(@Qualifier("opsRedisConnectionFactory") RedisConnectionFactory redisConnectionFactory) {
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(keySerializer())) // key序列化方式  StringRedisSerializer
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(valueSerializer())) // value序列化方式 Jackson2JsonRedisSerializer
                .disableCachingNullValues()
                .entryTtl(Duration.ofSeconds(60 * 60 * 8)); // 缓存过期时间 8小时


        RedisCacheManager.RedisCacheManagerBuilder builder = RedisCacheManager.RedisCacheManagerBuilder
                .fromConnectionFactory(redisConnectionFactory)
                .cacheDefaults(config)
                .transactionAware()
                .withInitialCacheConfigurations(getRedisCacheConfigurationMap());

        return builder.build();
    }

    private RedisSerializer<String> keySerializer() {
        return new StringRedisSerializer();
    }

    private RedisSerializer<Object> valueSerializer() {
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        //om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        //objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        om.activateDefaultTyping(
                LaissezFaireSubTypeValidator.instance,
                ObjectMapper.DefaultTyping.NON_FINAL,
                JsonTypeInfo.As.WRAPPER_ARRAY);
        om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        jackson2JsonRedisSerializer.setObjectMapper(om);
        return jackson2JsonRedisSerializer;
    }


    private Map<String, RedisCacheConfiguration> getRedisCacheConfigurationMap() {
        Map<String, RedisCacheConfiguration> redisCacheConfigurationMap = new HashMap<>();
        /**
         * ////@CacheConfig(cacheNames = "SsoCache")
         public class SsoCache{
         ////@Cacheable(keyGenerator = "cacheKeyGenerator")
         public String getTokenByGsid(String gsid)
         }
         //二者选其一,可以使用value上的信息，来替换类上cacheNames的信息
         ////@Cacheable(value = "BasicDataCache",keyGenerator = "cacheKeyGenerator")
         public String getTokenByGsid(String gsid)
         */
        //SsoCache和BasicDataCache进行过期时间配置
        redisCacheConfigurationMap.put("menuCache", this.getRedisCacheConfigurationWithTtl(24 * 60 * 60));
        redisCacheConfigurationMap.put("BasicDataCache", this.getRedisCacheConfigurationWithTtl(30 * 60));
        return redisCacheConfigurationMap;
    }

    private RedisCacheConfiguration getRedisCacheConfigurationWithTtl(Integer seconds) {

        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig();
        redisCacheConfiguration = redisCacheConfiguration.entryTtl(Duration.ofSeconds(seconds))
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(keySerializer())) // key序列化方式
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(valueSerializer())) // value序列化方式;
        ;
        return redisCacheConfiguration;
    }

    @Bean(name = "cacheKeyGenerator")
    public KeyGenerator cacheKeyGenerator() {
        return new KeyGenerator() {
            @Override
            public Object generate(Object target, Method method, Object... params) {

                StringBuffer sb = new StringBuffer();
                sb.append(target.getClass().getName());
                sb.append(method.getName());
                for (Object obj : params) {
                    sb.append(obj.toString());
                }
                return sb.toString();
            }
        };
    }

    @Primary
    @Bean(name = "opsRedisTemplate")
    public RedisTemplate<String, Object> redisTemplate(@Qualifier("opsRedisConnectionFactory") RedisConnectionFactory redisConnectionFactory) {
        return this.getRedisTemplate(redisConnectionFactory);
    }

    /**
     * RedisTemplate配置
     */
    public RedisTemplate<String, Object> getRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        // 设置序列化
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setKeySerializer(keySerializer()); // StringRedisSerializer
        redisTemplate.setHashKeySerializer(keySerializer()); //  StringRedisSerializer
        redisTemplate.setHashValueSerializer(valueSerializer()); // Jackson2JsonRedisSerializer
        redisTemplate.setValueSerializer(valueSerializer()); // Jackson2JsonRedisSerializer
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        return redisTemplate;
    }

//
//    @Bean("redisConfigLocation")
//    @ConfigurationProperties(prefix = "spring.redis.ops-redis-two")
//    public RedisStandaloneConfiguration redisConfigLocation() {
//        return new RedisStandaloneConfiguration();
//    }
//
//
//    @Bean(name = "factoryOpsRedisTwo")
//    public RedisConnectionFactory factoryOpsRedisTwo(GenericObjectPoolConfig config,
//                                                     @Qualifier("redisConfigLocation") RedisStandaloneConfiguration redisConfigLocation)
//    {
////        LettuceClientConfiguration clientConfiguration = LettucePoolingClientConfiguration.builder().poolConfig(config)
////                .commandTimeout(Duration.ofMillis(config.getMaxWaitMillis())).build();
////        return new LettuceConnectionFactory(redisConfigLocation, clientConfiguration);
//        //获得默认的连接池构造器
//        JedisClientConfiguration.JedisPoolingClientConfigurationBuilder jpcb =
//                (JedisClientConfiguration.JedisPoolingClientConfigurationBuilder) JedisClientConfiguration.builder();
//        //指定jedisPoolConifig来修改默认的连接池构造器
//        jpcb.poolConfig(config);
//        //通过构造器来构造jedis客户端配置
//        JedisClientConfiguration jedisClientConfiguration = jpcb.build();
//        //单机配置 + 客户端配置 = jedis连接工厂
//        return new JedisConnectionFactory(redisConfigLocation, jedisClientConfiguration);
//    }
//
//    @Bean(name = "opsTwoRedisTemplate")
//    public RedisTemplate<String, Object> opsOneRedisTemplate(@Qualifier("factoryOpsRedisTwo") RedisConnectionFactory factoryOpsRedisTwo) {
//        return this.getRedisTemplate(factoryOpsRedisTwo);
//    }


}