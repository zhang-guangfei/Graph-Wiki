package com.smc.smccloud.model.notice;

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
 * @since 2023-07-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ops_sales_notice_config")
public class OpsSalesNoticeConfigDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 业务编码
     */
    @TableField("business_code")
    private String businessCode;

    /**
     * 系统名
     */
    @TableField("system_name")
    private String systemName;

    /**
     * 业务类型名称
     */
    @TableField("business_name")
    private String businessName;

    /**
     * 处理类名
     */
    @TableField("class_name")
    private String className;

    /**
     * 处理方法名
     */
    @TableField("method_name")
    private String methodName;

    /**
     * 回调处理方法名
     */
    @TableField("callback_method_name")
    private String callbackMethodName;

    /**
     * 回调处理方法类名
     */
    @TableField("callback_class_name")
    private String callbackClassName;

    /**
     * 删除标识
     */
    @TableField("is_deleted")
    private Boolean isDeleted;

    @TableField("create_time")
    private Date createTime;

    @TableField("update_user")
    private String updateUser;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("create_user")
    private String createUser;

    @TableField("update_time")
    private Date updateTime;

    @TableField("is_insert_order_modify")
    private Boolean isInsertOrderModify;

    @TableField("group_name")
    private String groupName;

}
