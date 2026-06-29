package com.smc.smccloud.model;

import lombok.Data;

import java.util.Date;

/**
 * @Author lyc
 * @Date 2025/6/26 13:17
 * @Descripton TODO
 */
@Data
public class OpsSyncDeliveryManageDO {
    private Long id;
    private String jobName;
    private Date execTime;
    private int delFlag;
}
