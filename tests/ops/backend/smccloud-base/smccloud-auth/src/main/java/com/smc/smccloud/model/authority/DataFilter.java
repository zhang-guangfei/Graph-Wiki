package com.smc.smccloud.model.authority;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.smc.smccloud.model.login.BaseEntity;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("sales_sys_data_filter")
public class DataFilter extends BaseEntity implements Serializable
{
    private static final long serialVersionUID = -3281543465694592786L;

   @TableId(value = "ID",type = IdType.AUTO)
    private String id;


    /**
     * 角色ID
     */
    @TableField(value = "ROLE_ID")
    private String roleId;

    /**
     * 权限类型，1：按部门授权，2：按员工授权，3：按客户授权
     */
    @TableField(value = "FILTER_TYPE")
    private String filterType;

    /**
     * 读权限
     */
    @TableField(value = "READ_PERMISSION")
    private boolean readPermission;

    /**
     * 编辑权限
     */
    @TableField(value = "EDIT_PERMISSION")
    private boolean editPermission;

    /**
     * 删除权限
     */
    @TableField(value = "DELETE_PERMISSION")
    private boolean deletePermission;

    /**
     * 创建日期
     */
    @TableField(value = "CREATETIME")
    private Date createTime = new Date();

    @TableField(value = "CREATEID")
    private String createId;

    @TableField(value = "CREATENAME")
    private String createName;

    @TableField(value = "DESCRIPTION")
    private String description;
}
