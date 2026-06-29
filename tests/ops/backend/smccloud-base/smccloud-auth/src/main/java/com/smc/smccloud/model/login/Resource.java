package com.smc.smccloud.model.login;

import lombok.Data;

@Data
public class Resource extends TreeImpl<Resource> {

    /**
     * 模式
     */
    private String pattern;
}
