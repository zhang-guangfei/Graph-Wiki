package com.smc.smccloud.model.authority;

import lombok.Data;

@Data
public class SaleOrgPositionBean {
    private static final long serialVersionUID = 4224166491826829749L;

    /**
     * 岗位ID
     */
    private String id;
    /**
     * 岗位名称
     */
    private String name;
    /**
     * 部门ID
     */
    private String unitId;

    /**
     * 部门名称
     */
    private String unitName;

    /**
     * 部门全称
     */
    private String fullName;

    /**
     * 父节点ID
     */
    private String parentId;
}
