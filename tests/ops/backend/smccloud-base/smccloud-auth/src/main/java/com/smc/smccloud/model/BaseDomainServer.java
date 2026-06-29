package com.smc.smccloud.model;

import lombok.Data;

/**
 * @Author lyc
 * @Date 2022/5/25 13:21
 * @Descripton TODO
 */
public class BaseDomainServer {
    private String url;
    private String base;
    private String username;
    private String password;

    public BaseDomainServer() {
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getBase() {
        return this.base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
