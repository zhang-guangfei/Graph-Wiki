package com.smc.smccloud.model.order;

import lombok.Data;

/**
 * @author ：C14717
 * @version: $
 * @description：
 * @date ：Created in 2025/7/11 13:05
 */
@Data
public class OrderLimitDto {

    private String rorderNo;

    private Integer rorderItem;

    private String modelNo;

    private String endUser;

    private String remindType;

    private String remindNode;

    private String createUser;

}
