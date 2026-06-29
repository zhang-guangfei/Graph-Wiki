package com.smc.smccloud.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author smc
 * @since 2024-10-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ops_kd_info")
public class OpsKdInfoDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * kd编号
     */
    @TableField("kd_no")
    private String kdNo;

    /**
     * 型号
     */
    @TableField("model_no")
    private String modelNo;

    /**
     * 机种
     */
    @TableField("model_sort")
    private String modelSort;

    /**
     * 预计评价日(中国)
     */
    @TableField("expect_eval_time_cn")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date expectEvalTimeCn;

    /**
     * 是否日本评价;是否日本评价：0-否，1-沟通中，2-是
     */
    @TableField("is_jp_eval")
    private String isJpEval;

    /**
     * 预计量产日
     */
    @TableField("expect_batch_prod_time")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date expectBatchProdTime;

    /**
     * 变更次数
     */
    @TableField("change_num")
    private Integer changeNum;

    /**
     * 状态：1-中国评价合格，2-日本评价中，3-开始量产，4-终止
     */
    @TableField("state")
    private String state;

    /**
     * 生产工厂
     */
    @TableField("prod_factory")
    private String prodFactory;

    /**
     * 生产部门
     */
    @TableField("prod_dept")
    private String prodDept;

    /**
     * 负责人
     */
    @TableField("manager")
    private String manager;

    /**
     * 量产开始日
     */
    @TableField("batch_prod_start_time")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date batchProdStartTime;

    /**
     * 是否有效：1-有效，0-无效
     */
    @TableField("is_activity")
    private String isActivity;

    @TableField("create_user")
    private String createUser;

    @TableField("update_time")
    private Date updateTime;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("is_jp_eval_desc")
    private String isJpEvalDesc;

    @TableField("create_time")
    private Date createTime;

    @TableField("remark")
    private String remark;

    @TableField("state_desc")
    private String stateDesc;

    @TableField("is_activity_desc")
    private String isActivityDesc;

    @TableField("update_user")
    private String updateUser;

    /**
     * 源创建人
     */
    @TableField("created_by")
    private String createdBy;

    /**
     * 源更新人
     */
    @TableField("updated_by")
    private String updatedBy;

    /**
     * 源更新时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField("source_updated_time")
    private String sourceUpdatedTime;

    /**
     * 源创建时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField("source_created_time")
    private String sourceCreatedTime;


}
