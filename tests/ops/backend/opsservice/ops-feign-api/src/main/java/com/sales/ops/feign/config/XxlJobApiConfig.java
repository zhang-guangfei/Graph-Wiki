package com.sales.ops.feign.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;

/**
 * @author ：C14717
 * @version: 1.0注入header$
 * @description：
 * @date ：Created in 2021/11/29 8:22
 */
public class XxlJobApiConfig  implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        requestTemplate.header("needLoginApi", "0");//不需要登录
    }
}
