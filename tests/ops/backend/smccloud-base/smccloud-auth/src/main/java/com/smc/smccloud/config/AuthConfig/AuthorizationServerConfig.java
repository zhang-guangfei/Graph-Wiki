package com.smc.smccloud.config.AuthConfig;

import com.smc.smccloud.model.login.UserSecurity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.client.ClientCredentialsTokenEndpointFilter;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import javax.sql.DataSource;
import java.util.*;

/**
 * 授权服务配置
 */
@Configuration
@EnableAuthorizationServer
@Slf4j
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter
{
    @Autowired
    private AuthenticationManager authenticationManager; // 认证管理器 密码模式 在WebSecurityConfig中 需要配置这个

    @Autowired
    private TokenStore jwtTokenStore;

    @Autowired
    private JwtAccessTokenConverter accessTokenConverter;

    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private WebResponseExceptionTranslator<OAuth2Exception> webResponseExceptionTranslator;
    @Autowired
    private DataSource dataSource;


    /*
     * 客户端详情服务配置
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(clientDetails());
    }


//    @Bean
//    public JwtAccessTokenConverter accessTokenConverter() {
//        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
//        converter.setSigningKey(Constants.JWT_TOKEN_KEY); //对称秘钥，资源服务器使用该秘钥来验证
//        // converter.setVerifier(new RsaVerifier(Constants.JWT_TOKEN_KEY));
//        return converter;
//    }

    /***
     * 令牌访问端点与令牌服务配置   需要设置以下属性以支持授权类型
     *  authenticationManager 认证管理器 当选择资源所有者密码授权类型时 需要给该属性注入一个 authenticationManager对象
     *  userDetailsService 用于密码模式 实现自己的用户名密码认证
     *  authenticationCodeService 授权码模式（该项目不采用）
     *  TokenGranters 自定义授权 会忽略掉以上属性
     */
    // 令牌访问端点配置
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(Arrays.asList(tokenEnhancer(),accessTokenConverter));
        endpoints.tokenEnhancer(tokenEnhancerChain);
        endpoints.reuseRefreshTokens(false); // 不刷新令牌
        endpoints.tokenStore(jwtTokenStore) // 绑定 tokenstore
                .accessTokenConverter(accessTokenConverter)//将值转为jwt格式
                .userDetailsService(userDetailsService)
                .tokenEnhancer(tokenEnhancerChain) //内容增强器
                .authenticationManager(authenticationManager); // 认证管理器 密码模式
       // endpoints.tokenGranter(new CompositeTokenGranter(getTokenGranters(endpoints))); // 令牌管理服务
        endpoints.exceptionTranslator(webResponseExceptionTranslator);
    }

    /**
     * 令牌访问端点安全策略（令牌访问端点约束）
     *
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        // 对应oauth默认的url /oauth/token_key 公开
        security.tokenKeyAccess("permitAll()");
        // 检测令牌 对应oauth默认的url /oauth/check_token 完全公开 用于资源服务访问的令牌解析端点
        security.checkTokenAccess("permitAll()");
        security.allowFormAuthenticationForClients();  // 允许表单认证
    }

    @Bean
    public ClientCredentialsTokenEndpointFilter checkTokenEndpointFilter() {
        ClientCredentialsTokenEndpointFilter filter = new ClientCredentialsTokenEndpointFilter("/oauth/check_token");
        filter.setAuthenticationManager(authenticationManager);
        filter.setAllowOnlyPost(true);
        return filter;
    }

    @Bean
    public ClientDetailsService clientDetails() {
        return new JdbcClientDetailsService(dataSource);
    }

    @Bean
    public TokenEnhancer tokenEnhancer(){
        return new TokenEnhancer() {
            @Override
            public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
                if (accessToken instanceof DefaultOAuth2AccessToken){
                    DefaultOAuth2AccessToken token= (DefaultOAuth2AccessToken) accessToken;
                    token.setExpiration(new Date(System.currentTimeMillis() + 20*3600000));
                    Map<String, Object> additionalInformation = new LinkedHashMap<String, Object>();
                    if (authentication.getUserAuthentication() != null) {
                        UserSecurity userSecurity = (UserSecurity)authentication.getUserAuthentication().getPrincipal();
                        additionalInformation.put("deptNo",userSecurity.getDeptNo());
                        additionalInformation.put("psnName",userSecurity.getPsnName());
                    }
                    additionalInformation.put("username",authentication.getName());
                    token.setAdditionalInformation(additionalInformation);
                }
                return accessToken;
            }
        };
    }

    //    //  令牌管理服务配置
//    //  令牌访问端点的授权类型 tokenGranter
//    private List<TokenGranter> getTokenGranters(AuthorizationServerEndpointsConfigurer endpoints){
//        ClientDetailsService clientDetails = endpoints.getClientDetailsService();
//        AuthorizationServerTokenServices tokenServices = endpoints.getTokenServices();
//        AuthorizationCodeServices authorizationCodeServices = endpoints.getAuthorizationCodeServices();
//        OAuth2RequestFactory requestFactory = endpoints.getOAuth2RequestFactory();
//        List<TokenGranter> tokenGranters = new ArrayList<TokenGranter>();
//        tokenGranters.add(new AuthorizationCodeTokenGranter(tokenServices, authorizationCodeServices, clientDetails,
//                requestFactory));
//        tokenGranters.add(new RefreshTokenGranter(tokenServices, clientDetails, requestFactory));
//        ImplicitTokenGranter implicit = new ImplicitTokenGranter(tokenServices, clientDetails, requestFactory);
//        tokenGranters.add(implicit);
//        tokenGranters.add(new ClientCredentialsTokenGranter(tokenServices, clientDetails, requestFactory));
//        if (authenticationManager != null) {
//            tokenGranters.add(new ResourceOwnerPasswordTokenGranter(authenticationManager, tokenServices,
//                    clientDetails, requestFactory));
//        }
//        //添加自定义granter
//        tokenGranters.add(new SsoTokenGranter(
//                authenticationManager,
//                endpoints.getTokenServices(),
//                endpoints.getClientDetailsService(),
//                endpoints.getOAuth2RequestFactory()));
//        return tokenGranters;
//    }

}
