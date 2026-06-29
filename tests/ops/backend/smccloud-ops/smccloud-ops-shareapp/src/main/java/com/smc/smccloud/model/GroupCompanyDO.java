package com.smc.smccloud.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author smc
 * @since 2022-04-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("GroupCompany")
public class GroupCompanyDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableField("EnableSale")
    private Boolean EnableSale;

    @TableField("ID")
    private String id;

    @TableField("Name")
    private String Name;

    @TableField("create_time")
    private Date createTime;

    @TableField("create_user")
    private String createUser;

    @TableField("update_time")
    private Date updateTime;

    @TableField("update_user")
    private String updatedUser;


}
