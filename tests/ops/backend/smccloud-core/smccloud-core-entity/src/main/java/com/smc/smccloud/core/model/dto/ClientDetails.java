package com.smc.smccloud.core.model.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ClientDetails implements Serializable {
    private String access_token;
    private String token_type;
    private String expires_in;
    private String scope;
    private String user_name;
}
