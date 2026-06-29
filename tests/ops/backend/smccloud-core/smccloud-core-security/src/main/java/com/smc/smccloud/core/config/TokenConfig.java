package com.smc.smccloud.core.config;

import com.smc.smccloud.core.model.constants.Constants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import javax.annotation.Resource;


@Configuration
public class TokenConfig
{

    /**
     * REDIS缓存:SESSION前缀
     */
//    public final static String REDIS_CACHE_SYSTEM_PREFIX = "sale:";
//    public final static String REDIS_CACHE_SESSION_PREFIX = REDIS_CACHE_SYSTEM_PREFIX + "session:cache:";

    /**
     * The Redis key prefix for caches
     */
//    public static final String DEFAULT_CACHE_KEY_PREFIX = Constants.REDIS_CACHE_SESSION_PREFIX;
//    private String keyPrefix = DEFAULT_CACHE_KEY_PREFIX;
//
//    public String getKeyPrefix() {
//        return keyPrefix;
//    }
//
//    public void setKeyPrefix(String keyPrefix) {
//        this.keyPrefix = keyPrefix;
//    }
//
//    @Bean
//    public TokenStore tokenStore() {
//        RedisTokenStore redis = new RedisTokenStore(connectionFactory);
//        redis.setPrefix(getKeyPrefix());
//
//        return redis;
//    }

    /**
     *    JWT
     * @return
     */
    @Bean
    public TokenStore jwtTokenStore() {
        return new JwtTokenStore(accessTokenConverter());
    }

    /**
     * token生成处理：指定签名
     */
    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey(Constants.JWT_TOKEN_KEY); //对称秘钥，资源服务器使用该秘钥来验证
        return converter;
    }


}
