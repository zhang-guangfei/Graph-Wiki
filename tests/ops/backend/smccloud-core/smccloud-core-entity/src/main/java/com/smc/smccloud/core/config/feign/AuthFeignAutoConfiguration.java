
package com.smc.smccloud.core.config.feign;


import com.google.gson.Gson;
import com.smc.smccloud.core.model.constants.GlobalConstant;
import com.smc.smccloud.core.model.dto.ClientDetails;
import com.smc.smccloud.core.model.dto.LoginUserDTO;
import com.smc.smccloud.core.utils.HttpRequest;
import com.smc.smccloud.core.utils.ThreadLocalMapUtil;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * 请求别的服务时 拦截注入当前访问者的token
 */
@Slf4j
@Configuration
public class AuthFeignAutoConfiguration implements RequestInterceptor {


    @Value("${smccloud.oauth2.client.clientId}")
    private String clientId;

    @Value("${smccloud.oauth2.client.clientSecret}")
    private String clientSecret;

    @Value("${smccloud.oauth2.client.access-token-uri}")
    private String accessTokenUri;

    @Value("${spring.application.name}")
    private String name;

    @Override
    public void apply(RequestTemplate template) {

        String grantType = getGrantType();
        if (grantType != null) {
            if (GlobalConstant.GrantType.CLIANT_CREDENTIALS.equals(grantType)) {
                String serviceName = name.replaceAll("service", "ms");

                template.header(GlobalConstant.GRANT_TYPE, GlobalConstant.GrantType.CLIANT_CREDENTIALS);
                template.header(GlobalConstant.CLIENT_ID, clientId);
                template.header(GlobalConstant.CLIENT_SECRET, clientSecret);
                template.header(GlobalConstant.TOKEN, createToken());
                template.header(GlobalConstant.SERVICE_NAME, serviceName);
                LoginUserDTO loginUser = new LoginUserDTO(serviceName, serviceName, serviceName);
                ThreadLocalMapUtil.put(GlobalConstant.Sys.LOGIN_USER_DTO, loginUser);
            }
        }

        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            return;
        }

        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();

        Enumeration<String> headerNames = request.getHeaderNames();
        if (headerNames != null) {
            while (headerNames.hasMoreElements()) {
                String name = headerNames.nextElement();
                Enumeration<String> values = request.getHeaders(name);
                while (values.hasMoreElements()) {
                    String value = values.nextElement();
                    template.header(name, value);
                }
            }
        }
    }


    public static String getGrantType() {
        Object obj = null;
        if (RequestContextHolder.getRequestAttributes() != null) {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            obj = request.getAttribute(GlobalConstant.GRANT_TYPE);
        }
        if (obj != null) {
            return obj.toString();
        }
        obj = ThreadLocalMapUtil.get(GlobalConstant.GRANT_TYPE);
        if (obj != null) {
            return obj.toString();
        }
        return null;
    }

    public String createToken() {
        //String param = "grant_type=" + GlobalConstant.GrantType.CLIANT_CREDENTIALS + "&client_id=" + clientId + "&client_secret=" + clientSecret;
        Map<String, String> param = new HashMap<>();
        param.put("grant_type", GlobalConstant.GrantType.CLIANT_CREDENTIALS);
        param.put("client_id", clientId);
        param.put("client_secret", clientSecret);
        String s = HttpRequest.doPost(accessTokenUri, param, null);
        ClientDetails clientDetails = new Gson().fromJson(s, ClientDetails.class);
        // System.out.println("clientDetails = " + clientDetails.getAccess_token());
        // 设置token
        return "Bearer " + clientDetails.getAccess_token();
    }


//    @Override
//    public void apply(RequestTemplate requestTemplate) {
//        Object tokenObj = ThreadLocalMapUtil.get(GlobalConstant.TOKEN);
//        if (tokenObj != null) {
//            requestTemplate.header(GlobalConstant.TOKEN, tokenObj.toString());
//            return;
//        }
//        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
//        if (Objects.nonNull(requestAttributes)) {
//            RequestContextHolder.setRequestAttributes(requestAttributes, true);
//            try {
//                ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
//                HttpServletRequest request = attributes.getRequest();
//                String requestURI = request.getRequestURI();
//                System.out.println("requestURI = " + requestURI);
//                if (!request.getRequestURI().equals("")) {
//                    //System.out.println("[] -> request = " + request.getRequestURI());
//                    //  传递header
//                    Enumeration<String> headerNames = request.getHeaderNames();
//                    if (headerNames != null) {
//                        while (headerNames.hasMoreElements()) {
//                            String name = headerNames.nextElement();
//                            Enumeration<String> values = request.getHeaders(name);
//                            while (values.hasMoreElements()) {
//                                String value = values.nextElement();
//                                requestTemplate.header(name, value);
//                            }
//                        }
//                    }
//                }
//            } catch (Exception e) {
//                log.info("服务之间的调用");
//            } finally {
//                RequestContextHolder.resetRequestAttributes();
//            }
//        } else {
//            RequestContextHolder.setRequestAttributes(new NonWebRequestAttributes(), Boolean.TRUE);
//            HttpServletRequest httpRequest = this.getHttpServletRequestSafely();
//            if (null != httpRequest && null != httpRequest.getAttribute("X-Request-No")) {
//                requestTemplate.header("X-Request-No", httpRequest.getAttribute("X-Request-No").toString());
//            }
//        }
//
//    }
//
//    public HttpServletRequest getHttpServletRequestSafely() {
//        try {
//            RequestAttributes requestAttributesSafely = this.getRequestAttributesSafely();
//            return requestAttributesSafely instanceof NonWebRequestAttributes ? null : ((ServletRequestAttributes) requestAttributesSafely).getRequest();
//        } catch (Exception var2) {
//            return null;
//        }
//    }
//
//    public RequestAttributes getRequestAttributesSafely() {
//        RequestAttributes requestAttributes;
//        try {
//            requestAttributes = RequestContextHolder.currentRequestAttributes();
//        } catch (IllegalStateException var3) {
//            requestAttributes = new NonWebRequestAttributes();
//        }
//
//        return requestAttributes;
//    }

}
