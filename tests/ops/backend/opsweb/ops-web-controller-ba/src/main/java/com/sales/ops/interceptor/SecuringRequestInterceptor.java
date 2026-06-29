package com.sales.ops.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * @author ：C14717
 * @version: 1.0$
 * @description：拦截器
 * @date ：Created in 2021/12/8 15:52
 */

public class SecuringRequestInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {
        if("ba-service".equals(requestTemplate.feignTarget().name())
                || "wm-service".equals(requestTemplate.feignTarget().name())
                || "po-service".equals(requestTemplate.feignTarget().name())
                || "opsApi".equals(requestTemplate.feignTarget().name())){//opsApi
            return;
        }
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        Enumeration<String> headerNames = request.getHeaderNames();
        if (headerNames != null) {
            while (headerNames.hasMoreElements()) {
                String name = headerNames.nextElement();
                String values = request.getHeader(name);
                if(name.equals("authorization")){
                    requestTemplate.header(name, values);
                }
            }
        }
    }
}
