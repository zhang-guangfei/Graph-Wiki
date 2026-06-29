package com.smc.smccloud.model.bindata;

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
 * @since 2025-12-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("bindata_client_warehouse")
public class BindataClientWarehouseDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableField("update_time")
    private Date updateTime;

    @TableField("del_flag")
    private Integer delFlag;

    @TableField("update_user")
    private String updateUser;

    @TableField("create_time")
    private Date createTime;

    @TableField("bindata_id")
    private Long bindataId;

    @TableField("warehouse_code")
    private String warehouseCode;

    @TableField("create_user")
    private String createUser;

    @TableId(value = "id" , type = IdType.AUTO)
    private Long id;


}
