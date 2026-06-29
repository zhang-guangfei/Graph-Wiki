package com.smc.smccloud.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author smc
 * @since 2022-03-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("OrganizationRelation")
public class OrganizationRelationDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 开票担当
     */
    @TableField("EmpInvoice")
    private String EmpInvoice;

    @TableField("Remark")
    private String Remark;

    @TableField("ParentId")
    private String ParentId;

    @TableField("HRUnitId")
    private String HRUnitId;

    @TableField("CNUnitId")
    private String CNUnitId;

    @TableField("FormalId")
    private String FormalId;

    @TableField("OrganizationType")
    private Integer OrganizationType;

    @TableId(value = "Id", type = IdType.INPUT)
    private String Id;

    @TableField("UpdateTime")
    private String UpdateTime;


}
