package com.smc.smccloud.model.authority;

import com.smc.smccloud.service.Condition;

import lombok.Data;

@Data
public class OrgPositionCondition implements Condition
{
    private static final long serialVersionUID = -409253736142222386L;

    /**
     * 6位部门编码
     */
    private String unitId;

    /**
     * 岗位名称
     */
    private String positionName;

    /**
     * 精确查询，true精确，false模糊
     */
    private boolean accurate;
}
