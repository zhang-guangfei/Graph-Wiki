package com.smc.smccloud.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "hr_employee")
public class EmployeeDO {

    @TableField(value = "ID")
    private String id;

    @TableField(value = "Name")
    private String name;

    @TableField(value = "Sex")
    private String sex;

    @TableField(value = "CellPhone")
    private String cellPhone;

    @TableField(value = "Email")
    private String email;

    @TableField(value = "Status")
    private String status;
}
