package com.sales.ops.feign.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * @author ：C14717
 * @version: 1.0$
 * @description：
 * @date ：Created in 2021/10/13 14:47
 */
@Component
@RefreshScope
@ConfigurationProperties(
        prefix = "feign.ignores"
)
public class FeignIgnoreUrls {
    private List<String> urls = Collections.emptyList();
    private String test = "";

    public FeignIgnoreUrls() {
    }

    public List<String> getUrls() {
        return this.urls;
    }

    public void setUrls(List<String> urls) {
        this.urls = urls;
    }

    public String getTest() {
        return this.test;
    }

    public void setTest(String test) {
        this.test = test;
    }
}
