package com.smc.ops.delivery.model.inqb;

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
 * @since 2024-06-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ops_inqb_adapter_config")
public class OpsInqbAdapterConfigDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 供应商代码
     */
    @TableField("supplier_id")
    private String supplierId;

    /**
     * 供应商名称
     */
    @TableField("supplier_name")
    private String supplierName;

    /**
     * 针对不同供应商，问询发送的类别（0:表对接，1：接口对接）
     */
    @TableField("connect_type")
    private String connectType;

    /**
     * 问询发送类名
     */
    @TableField("send_class_name")
    private String sendClassName;

    /**
     * 问询发送方法名
     */
    @TableField("send_method_name")
    private String sendMethodName;

    /**
     * 问询接受回复类名
     */
    @TableField("callback_class_name")
    private String callbackClassName;

    /**
     * 问询接受回复方法名
     */
    @TableField("callback_method_name")
    private String callbackMethodName;

    /**
     * 创建人
     */
    @TableField("create_user")
    private String createUser;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private String createTime;

    /**
     * 更新人
     */
    @TableField("update_user")
    private String updateUser;

    /**
     * 更新时间
     */
    @TableField("update_time")
    private String updateTime;


    @TableField("is_deleted")
    private Boolean isDeleted;




}
