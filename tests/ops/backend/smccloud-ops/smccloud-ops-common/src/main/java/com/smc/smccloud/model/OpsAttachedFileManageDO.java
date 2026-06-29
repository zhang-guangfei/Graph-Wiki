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
 * @since 2023-10-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ops_attached_file_manage")
public class OpsAttachedFileManageDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 业务类型
     */
    @TableField("business_type")
    private String businessType;

    /**
     * 业务单号 业务表主键
     */
    @TableField("business_key_value")
    private String businessKeyValue;

    /**
     * 文件类型(minPrice最低售价)
     */
    @TableField("file_type")
    private String fileType;

    /**
     * 文件路径
     */
    @TableField("file_path")
    private String filePath;

    /**
     * 实际文件名
     */
    @TableField("random_file_name")
    private String randomFileName;

    /**
     * 真实文件名
     */
    @TableField("real_file_name")
    private String realFileName;

    @TableField("update_time")
    private Date updateTime;

    @TableField("create_user")
    private String createUser;

    @TableField("create_time")
    private Date createTime;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("del_flag")
    private Boolean delFlag;

    @TableField("update_user")
    private String updateUser;

//    @TableField("batch_no") (废弃)
//    private String batchNo;


}
