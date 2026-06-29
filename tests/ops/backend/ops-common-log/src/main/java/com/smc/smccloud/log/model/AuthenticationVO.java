package com.smc.smccloud.log.model;

import lombok.Data;

/**
 * @Author lyc
 * @Date 2023/5/30 13:39
 * @Descripton TODO
 */
@Data
public class AuthenticationVO {
    private String tokenType;
    private String tokenValue;
    private String remoteAddress;
}
