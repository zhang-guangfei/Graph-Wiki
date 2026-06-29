package com.smc.smccloud.core.model.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author lyc
 * @Date 2022/1/18 9:00
 * @Descripton TODO
 */
@Data
public class JwtEntity implements Serializable
{

    private static final long serialVersionUID = -1242493306307174690L;

    private String[] aud;
    private String user_name;
    private String[] scope;
    private Long exp;
    private String[] authorities;
    private String jti;
    private String deptNo;
    private String client_id;
    private String username;
    private String psnName;

}
