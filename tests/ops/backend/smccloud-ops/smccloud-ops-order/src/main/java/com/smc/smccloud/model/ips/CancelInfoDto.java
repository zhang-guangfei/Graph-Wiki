package com.smc.smccloud.model.ips;

import lombok.Data;

/**
 * @description:
 * "cancelMark": "",//取消标识
 * "cancelReasonCode": ""//取消原因
 * @author: B91717
 * @time: 2024/12/17 16:42
 */
@Data
public class CancelInfoDto {

    private String cancelMark; // 取消标识
    private String cancelReasonCode; // 取消原因

}
