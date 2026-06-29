package com.smc.smccloud.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * Author: lyc
 * Date: 2023-01-13 16:11
 * Description:
 */
@Data
@TableName("hr_organization")
public class HROrganizationDO {

    @TableId(value = "Id", type = IdType.INPUT)
    private String id;

    @TableField("ParentId")
    private String parentId;

    @TableField("Name")
    private String name;

//    @TableField("K3Code")
//    private String k3Code;

    @TableField("FullName")
    private String fullName;

    @TableField("Level")
    private String level;

    @TableField("Status")
    private String status;

    @TableField("SalesCustomized")
    private String salesCustomized;

    @TableField("CompanyCode")
    private String companyCode;

    @TableField("CompanyName")
    private String companyName;

    @TableField("unitCode")
    private String unitCode;

//    @TableField("EmpInvoice")
//    private String empInvoice;

}
