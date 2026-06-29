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
@TableName("ops_inqb_code_config")
public class OpsInqbCodeConfigDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 问询分类码类别（0：发送分类码，1：回复分类码）
     */
    @TableField("code_type")
    private String codeType;
    /**
     * ops催促问询分类码
     */
    @TableField("ops_reason_code")
    private String opsReasonCode;

    /**
     * ops催促问询分类码含义
     */
    @TableField("ops_reason_desc")
    private String opsReasonDesc;

    /**
     * 供应商代码
     */
    @TableField("supplie_code")
    private String supplieCode;

    /**
     * 对应供应商编码
     */
    @TableField("supplie_reason_code")
    private String supplieReasonCode;

    /**
     * 对应供应商编码解释
     */
    @TableField("supplie_reason_desc")
    private String supplieReasonDesc;

    /**
     * 是否删除
     */
    @TableField("is_deleted")
    private Boolean isDeleted;

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

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;


}
