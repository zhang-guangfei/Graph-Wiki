package com.smc.smccloud.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
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
 * @since 2025-02-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("product_special_model")
public class ProductSpecialModelDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 描述
     */
    @TableField("description")
    private String description;

    /**
     * 逻辑删除0未删除1删除
     */
    @TableField("is_deleted")
    private Boolean isDeleted;

    @TableField("create_user")
    private String createUser;

    @TableField("update_time")
    private Date updateTime;

    @TableId(value = "ModelNo", type = IdType.INPUT)
    private String ModelNo;

    @TableField("create_time")
    private Date createTime;

    @TableField("update_user")
    private String updateUser;


}
