package com.smc.smccloud.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author wuweidong
 * @create 2023/12/12 08:45
 * @description
 */
@Data
@TableName("hr_employee_position")

public class EmployeePositionDO
{

    @TableField("EmployeeId")
    public String employeeId ;

    @TableField("OrgId")
    public String orgId ;

    @TableField("PositionId")
    public String positionId ;

    @TableField("IsPrimaryPosition")
    public String isPrimaryPosition ;

    @TableField("status")
    public String status ;

    @TableField("isFuZeRen")
    public String isFuZeRen ;

    @TableField("is_deleted")
    public Boolean isDeleted ;

}
