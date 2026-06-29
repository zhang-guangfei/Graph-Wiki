package com.smc.smccloud.model;

import lombok.Data;

import java.util.Map;

/**
 * @Author lyc
 * @Date 2025/6/26 13:03
 * @Descripton TODO
 */
@Data
public class KettleParamDto {

    private String transPath;

    private Map<String, String> parameters;

}
