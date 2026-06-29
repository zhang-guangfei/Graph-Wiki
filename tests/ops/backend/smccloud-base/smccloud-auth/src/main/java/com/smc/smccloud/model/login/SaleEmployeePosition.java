package com.smc.smccloud.model.login;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "Hr_employee_position")
public class SaleEmployeePosition
{
    /**
     * 员工id
     */
    @TableField(value = "EmployeeId")
    private String employeeId;
    /**
     * 组织id
     */
    @TableField(value = "OrgId")
    private String orgId;
    /**
     * 岗位id
     */
    @TableField(value = "PositionId")
    private String positionId;
    /**
     * 是否主要岗位
     */
    @TableField(value = "IsPrimaryPosition")
    private Boolean isPrimaryPosition;

    /**
     * 1:在职|2:离职
     */
    @TableField(value = "status")
    private String status;

}
