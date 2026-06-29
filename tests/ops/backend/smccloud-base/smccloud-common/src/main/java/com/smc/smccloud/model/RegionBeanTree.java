package com.smc.smccloud.model;

import lombok.Data;

import java.util.List;

@Data
public class RegionBeanTree {

    private Long id;
    private String code;
    private String codeName;
    private String classCode;
    private List<RegionBeanTree> children;
}
