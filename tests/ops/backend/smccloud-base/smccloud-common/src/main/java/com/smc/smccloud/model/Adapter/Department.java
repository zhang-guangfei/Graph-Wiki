package com.smc.smccloud.model.Adapter;

import lombok.Data;

import java.io.Serializable;

/**
 * 部门实体
 */
@Data
public class Department implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = -6478825576611926756L;

    /**
     * 部门编码：两位
     */
    private String deptNo;

    /**
     * 部门编码 : 六位
     */
    private String hrUnitId;

    /**
     * 部门名称
     */
    private String deptDesc;

    /**
     * 地址
     */
    private String address;

    /**
     * 电话
     */
    private String teleNo;

    /**
     * 邮箱
     */
    private String email_Sample;

    /**
     * 负责人
     */
    private String superIntendent;

    /**
     * 运输天数
     */
    private String dlvFlag;

}
