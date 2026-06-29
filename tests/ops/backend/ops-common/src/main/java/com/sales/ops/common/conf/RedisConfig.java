package com.sales.ops.common.conf;

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
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.*;

import java.lang.reflect.Method;
import java.time.Duration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
@Slf4j
@Configuration
@EnableCaching // 开启缓存支持
public class RedisConfig extends CachingConfigurerSupport {

	@Value("${spring.redis.ops-redis.cluster.nodes}")
	private String nodes;
	@Value("${spring.redis.ops-redis.password}")
	private String password;
	@Value("${spring.redis.ops-redis.cluster.max-redirects}")
	private Integer maxRedirects;

	/**
	 * 解析配置文件中的redis集群节点
	 * @return
	 */
	private Set<RedisNode> getRedisNodes() {
		String[] opsRedisNodes = nodes.split(",");
		Set<RedisNode> clusterNodes = new HashSet<>(opsRedisNodes.length);
		String[] hp;
		for (String node : opsRedisNodes) {
			hp = node.split(":");
			clusterNodes.add(new RedisNode(hp[0], Integer.parseInt(hp[1])));
		}
		return clusterNodes;
	}

	/**
	 * 配置redis集群节点
	 */
	@Primary
	@Bean("opsRedisConfig")
	public RedisClusterConfiguration opsRedisConfig() {
		Set<RedisNode> clusterNodes = getRedisNodes();
		RedisClusterConfiguration redisClusterConfiguration = new RedisClusterConfiguration();
		redisClusterConfiguration.setClusterNodes(clusterNodes);
		redisClusterConfiguration.setPassword(password);
		redisClusterConfiguration.setMaxRedirects(maxRedirects);
		return redisClusterConfiguration;
	}

	/**
	 * 配置lettuce连接池
	 */
	@Primary
	@Bean
	@ConfigurationProperties("spring.redis.lettuce.pool")
	public GenericObjectPoolConfig adapterRedisPool() {
		return new GenericObjectPoolConfig();
	}

	@Primary
	@Bean("opsRedisConnectionFactory")
	public RedisConnectionFactory opsRedisConnectionFactory(GenericObjectPoolConfig poolConfig,
															@Qualifier("opsRedisConfig") RedisClusterConfiguration redisConfig) {
		//获得默认的连接池构造器
		JedisClientConfiguration.JedisPoolingClientConfigurationBuilder jpcb =
				(JedisClientConfiguration.JedisPoolingClientConfigurationBuilder) JedisClientConfiguration.builder();
		//指定jedisPoolConfig来修改默认的连接池构造器
		jpcb.poolConfig(poolConfig);
		//通过构造器来构造jedis客户端配置
		JedisClientConfiguration jedisClientConfiguration = jpcb.build();
		//单机配置 + 客户端配置 = jedis连接工厂
		return new JedisConnectionFactory(redisConfig, jedisClientConfiguration);
	}

	/**
	 * redis key序列化和反序列化配置
	 */
	private RedisSerializer<String> keySerializer() {
		return new StringRedisSerializer();
	}

	/**
	 * redis序列化和反序列化配置
	 * GenericJackson2JsonRedisSerializer和Jackson2JsonRedisSerializer的对比
	 * Jackson2JsonRedisSerializer:
	 * 序列化时，存数据会采用JSON字符串方式,获取时结果为LinkedHashMap,无法直接使用JDK强转方法转为对象
	 * 需借助ObjectMapper实现对象直接序列化存储到Redis,从Redis读取反序列化对象
	 * GenericJackson2JsonRedisSerializer:
	 * 序列化时，会保存序列化的对象的完全限定名，从redis获取数据时即可直接反序列化成指定的对象
	 * redisTemplate.opsForValue().set(student.getName(), student);
	 * Student student = (Student)redisTemplate.opsForValue().get(student.getName());
	 */
	private RedisSerializer<Object> valueSerializer() {
		return new GenericJackson2JsonRedisSerializer();
	}
	private RedisSerializer<Object> valueSerializer2() {
		Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
		// 自定义配置序列化和反序列化的规则
		ObjectMapper om = new ObjectMapper();
		// 设置ObjectMapper的可见性，使其能够访问所有属性，包括私有属性。
		om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
		//激活默认的类型推断功能，允许将JSON数据反序列化为Java对象时自动推断类型。这里使用了LaissezFaireSubTypeValidator作为类型验证器，将类型推断为非final类型，并将结果包装在数组中。
		om.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.WRAPPER_ARRAY);
		om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);//忽略未知字段
		jackson2JsonRedisSerializer.setObjectMapper(om);
		return jackson2JsonRedisSerializer;
	}

	/**
	 * redisTemplate配置
	 * @param redisConnectionFactory
	 * @return
	 */
	@Primary
	@Bean(name = "opsRedisTemplate")
	public RedisTemplate<String, Object> redisTemplate(@Qualifier("opsRedisConnectionFactory") RedisConnectionFactory redisConnectionFactory) {
		// 设置序列化
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
		redisTemplate.setKeySerializer(keySerializer()); // StringRedisSerializer
		redisTemplate.setHashKeySerializer(keySerializer()); //  StringRedisSerializer
		redisTemplate.setHashValueSerializer(valueSerializer()); // Jackson2JsonRedisSerializer
		redisTemplate.setValueSerializer(valueSerializer()); // Jackson2JsonRedisSerializer
		redisTemplate.setConnectionFactory(redisConnectionFactory);
		return redisTemplate;
	}

	/**
	 * -----------------缓存管理器----------------------------------------------------------------------------------------
	 */


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
		redisCacheConfigurationMap.put("ops:organization", this.getRedisCacheConfigurationWithTtl(60 * 60 * 24 * 7));
		redisCacheConfigurationMap.put("ops:organization:salesDept", this.getRedisCacheConfigurationWithTtl(60 * 60 * 24 * 7));
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


	@Bean
	public CacheManager cacheManager(@Qualifier("opsRedisConnectionFactory") RedisConnectionFactory redisConnectionFactory) {
		RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
				.serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(keySerializer())) // key序列化方式  StringRedisSerializer
				.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(valueSerializer())) // value序列化方式 Jackson2JsonRedisSerializer
				.disableCachingNullValues()
				.entryTtl(Duration.ofSeconds(30 * 60));//缓存过期时间

		RedisCacheManager.RedisCacheManagerBuilder builder = RedisCacheManager.RedisCacheManagerBuilder
				.fromConnectionFactory(redisConnectionFactory)
				.cacheDefaults(config)
				.transactionAware()
				.withInitialCacheConfigurations(getRedisCacheConfigurationMap());

		return builder.build();
	}

	/**
	 * 如果Redis服务器挂了,可以继续向下执行方法，从相关的数据库中查询数据，而不是直接抛出异常导致整个程序终止运行
	 * @return
	 */
	@Bean
	@Override
	public CacheErrorHandler errorHandler() {
		CacheErrorHandler cacheErrorHandler = new CacheErrorHandler() {

			@Override
			public void handleCachePutError(RuntimeException exception, Cache cache,
											Object key, Object value) {
				RedisErrorException(exception, key);
			}

			@Override
			public void handleCacheGetError(RuntimeException exception, Cache cache,
											Object key) {
				RedisErrorException(exception, key);
			}

			@Override
			public void handleCacheEvictError(RuntimeException exception, Cache cache,
											  Object key) {
				RedisErrorException(exception, key);
			}

			@Override
			public void handleCacheClearError(RuntimeException exception, Cache cache) {
				RedisErrorException(exception, null);
			}
		};
		return cacheErrorHandler;
	}

	protected void RedisErrorException(Exception exception,Object key){
		log.error("redis异常：key=[{}]", key, exception);
	}

}