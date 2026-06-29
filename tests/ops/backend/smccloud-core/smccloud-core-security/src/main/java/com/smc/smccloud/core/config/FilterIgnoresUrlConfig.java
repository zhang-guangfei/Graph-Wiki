package com.smc.smccloud.core.config;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class FilterIgnoresUrlConfig {

    /**
     * 不被安全拦截的URL
     */
    private List<String> urls = new ArrayList<>();

    public List<String> getUrls() {
        return urls;
    }

    public void setUrls(List<String> urls) {
        this.urls = urls;
    }
}

