package com.smc.smccloud.core.model.adapter;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data
public class DataAuthoritySearchCondition implements Serializable {

    private static final long serialVersionUID = -8489787479172483096L;

    private boolean isFullDataAuthority = false;
    
    private boolean isDesignated = false;

    /**
     * 根据数据权限获得的客户担当集合
     */
    private List<String> userIdListByDataAuthority = new ArrayList<>();
    /**
     * 根据数据权限获得的部门代码集合
     */
    private List<String> deptCodeListByDataAuthority = new ArrayList<>();
    /**
     * 根据数据权限获得的客户代码集合
     */
    private List<String> customerCodeListByDataAuthority = new ArrayList<>();
    /**
     * 根据数据权限获得的行业代码集合
     */
    private List<String> indCodeListByDataAuthority = new ArrayList<>();

    /**
     * 根据数据权限获得的用户编码集合
     */
    private List<String> userNoListByDataAuthority = new ArrayList<>();


}