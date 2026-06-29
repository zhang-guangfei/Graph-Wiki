package com.smc.smccloud.model;

import lombok.Data;

import java.util.Date;

/**
 * @Author lyc
 * @Date 2022/4/7 9:02
 * @Descripton TODO
 */
@Data
public class CarrierVO {

    private String carrierId;

    private String name;

    private Date createTime;

    private String createUser;

    private Date updateTime;

    private String updateUser;
}
