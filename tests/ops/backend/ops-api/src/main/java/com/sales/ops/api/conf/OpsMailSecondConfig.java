package com.sales.ops.api.conf;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author ：C14717
 * @version: $
 * @description：
 * @date ：Created in 2023/8/28 16:03
 */
@Component
@ConfigurationProperties(prefix = "secondary")
public class OpsMailSecondConfig {

    private String host;
    private String username;
    private String password;
    private int port;
    private String protocol;

    private Map<String, String> properties = new HashMap<>();


    @Bean(name = "javaMailSender2")
    public JavaMailSenderImpl javaMailSender2() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(host);
        mailSender.setPort(port);
        mailSender.setUsername(username);
        mailSender.setPassword(password);
        mailSender.setProtocol(protocol);
        mailSender.setDefaultEncoding("UTF-8");
        mailSender.setJavaMailProperties(asProperties(properties));
        return mailSender;
    }
    private Properties asProperties(Map<String, String> source) {
        Properties properties = new Properties();
        properties.putAll(source);
        return properties;
    }


    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public Map<String, String> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }
}
