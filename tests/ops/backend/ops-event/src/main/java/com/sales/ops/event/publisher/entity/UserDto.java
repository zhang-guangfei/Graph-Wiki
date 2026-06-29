package com.sales.ops.event.publisher.entity;

import java.io.Serializable;

/**
 * @author C02483
 * @version 1.0
 * @description: 用户信息
 * @date 2021/9/10 15:09
 */
public class UserDto implements Serializable {

    public final static  UserDto ADMIN=new UserDto("ADMIN","1.1.1.1");
    public final static  UserDto TEST=new UserDto("TEST","1.1.1.1");
    public final static  UserDto AUTO=new UserDto("AUTO","1.1.1.1");
    public final static  UserDto WMS=new UserDto("WMS","1.1.1.1");
    public final static  UserDto HISTORY=new UserDto("HISTORY","1.1.1.1");
    public final static  UserDto MIGRATE_STATUS=new UserDto("MIGRATE_STATUS","1.1.1.1");
    private static final long serialVersionUID = 4031779531719756731L;
    private String userName;
    private  String ip;

    public UserDto() {
        this.userName = "AUTO";
    }

    public UserDto(String userName, String ip) {
        this.userName = userName;
        this.ip = ip;
    }
    public UserDto(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public UserDto setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "userName='" + userName +
                '}';
    }
}
