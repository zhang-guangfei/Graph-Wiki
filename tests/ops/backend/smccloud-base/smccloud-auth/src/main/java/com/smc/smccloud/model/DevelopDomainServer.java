package com.smc.smccloud.model;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author lyc
 * @Date 2022/5/25 13:20
 * @Descripton TODO
 */
@Component
@ConfigurationProperties(
        prefix = "base.domain.server"
)
public class DevelopDomainServer extends BaseDomainServer {
    private String authentication;
    private String factoryInitial;
    private String domainName;

    public DevelopDomainServer() {
    }

    public String getAuthentication() {
        return this.authentication;
    }

    public void setAuthentication(String authentication) {
        this.authentication = authentication;
    }

    public String getFactoryInitial() {
        return this.factoryInitial;
    }

    public void setFactoryInitial(String factoryInitial) {
        this.factoryInitial = factoryInitial;
    }

    public String getDomainName() {
        return this.domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

}
