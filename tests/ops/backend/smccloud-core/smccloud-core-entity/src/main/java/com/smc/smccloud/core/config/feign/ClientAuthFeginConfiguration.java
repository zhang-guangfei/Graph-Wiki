//package com.smc.smccloud.core.config.feign;
//
//import com.google.gson.Gson;
//import com.smc.smccloud.core.exception.BusinessException;
//import com.smc.smccloud.core.model.ErrorCodeEnum;
//import com.smc.smccloud.core.model.constants.Constants;
//import com.smc.smccloud.core.model.constants.GlobalConstant;
//import com.smc.smccloud.core.model.dto.ClientDetails;
//import com.smc.smccloud.core.utils.HttpRequest;
//import com.smc.smccloud.core.utils.JwtUtil;
//import com.smc.smccloud.core.utils.ThreadLocalMapUtil;
//import feign.RequestInterceptor;
//import feign.RequestTemplate;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.cloud.openfeign.EnableFeignClients;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.stereotype.Component;
//
///**
// * 客户端Fegin访问设置认证身份
// */
//@Slf4j
//@EnableFeignClients(basePackages = "com.smc.smccloud.service.*")
//// @Configuration
//@Component
//public class ClientAuthFeginConfiguration implements RequestInterceptor {
//
//    @Value("${smccloud.oauth2.client.clientId}")
//    private String clientId;
//
//    @Value("${smccloud.oauth2.client.clientSecret}")
//    private String clientSecret;
//
//    @Value("${smccloud.oauth2.client.access-token-uri}")
//    private String accessTokeUri;
//
//    private final static  String TOKEN_HEADER="Authorization";
//    private final static  String GRANT_TYPE_HEADER="grant_type";
//
//    public ClientAuthFeginConfiguration() {
//    }
//
//    @Override
//    public void apply(RequestTemplate requestTemplate) {
//        Object tokenObj = ThreadLocalMapUtil.get(TOKEN_HEADER);
//        Object grantType = ThreadLocalMapUtil.get(GlobalConstant.GRANT_TYPE);
//        if(tokenObj == null) {
//            String token = createToken();
//            requestTemplate.header(TOKEN_HEADER, token);
//            requestTemplate.header(GRANT_TYPE_HEADER, GlobalConstant.GrantType.CLIANT_CREDENTIALS);
//
//            ThreadLocalMapUtil.put(TOKEN_HEADER, token);
//        }
//        else
//        {
//            String jwtToken = StringUtils.substringAfter(tokenObj.toString(), "Bearer ");
//
////            if (grantType.toString().equals("password")) {
////                if (JwtUtil.verifyToken(jwtToken)) {
////                    throw new BusinessException(ErrorCodeEnum.GL_AUTH_OVER_TIME);
////                }
////            }
//
//            if (JwtUtil.isExpired(jwtToken)) {
//                String token = createToken();
//                requestTemplate.header(TOKEN_HEADER, token);
//            } else {
//                requestTemplate.header(TOKEN_HEADER, tokenObj.toString());
//            }
//
//            requestTemplate.header(GRANT_TYPE_HEADER, GlobalConstant.GrantType.CLIANT_CREDENTIALS);
//        }
//    }
//
//    public String createToken() {
//        String param = "grant_type=" + GlobalConstant.GrantType.CLIANT_CREDENTIALS + "&client_id=" + clientId + "&client_secret=" + clientSecret;
//        String s = HttpRequest.sendPost(accessTokeUri, param);
//
//        ClientDetails clientDetails = new Gson().fromJson(s, ClientDetails.class);
//        // System.out.println("clientDetails = " + clientDetails.getAccess_token());
//        // 设置token
//        return "Bearer " + clientDetails.getAccess_token();
//    }
//}
