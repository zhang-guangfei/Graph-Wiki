package com.smc.smccloud.model.prestock;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * @author B90034
 * @since 2021-10-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("prestock_apply")
public class PreStockApplyDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 申请号
     */
    @TableField("apply_no")
    private String applyNo;

    /**
     * 申请类型 1-专备; 2-SHIKOMI; 3-委托在库补库
     */
    @TableField("apply_type")
    private String applyType;

    /**
     * 库存类型 - inventoryTypeCode
     */
    @TableField("stock_type")
    private String stockType;

    /**
     * 部门代码
     */
    @TableField("dept_no")
    private String deptNo;

    /**
     * 客户代码
     */
    @TableField("customer_no")
    private String customerNo;

    /**
     * 用户代码
     */
    @TableField("user_no")
    private String userNo;

    /**
     * 状态 1-编辑中; 2-待审核; 3-待处理; 4-不通过; 5-已确认(SHIKOMI); 6-已备库; 9-取消
     */
    @TableField("status")
    private String status;

    /**
     * 申请时间
     */
    @TableField("apply_time")
    private Date applyTime;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;

    /**
     * 项目代码
     */
    @TableField("project_no")
    private String projectNo;

    /**
     * 项目名称
     */
    @TableField("project_name")
    private String projectName;

    /**
     * 应用设备
     */
    @TableField("equipment")
    private String equipment;

    /**
     * 项目状态
     */
    @TableField("project_status")
    private String projectStatus;

    /**
     * 用途
     */
    @TableField("purpose")
    private String purpose;

    /**
     * 原因
     */
    @TableField("reason")
    private String reason;

    @TableField("reason_en")
    private String reasonEn;

    /**
     * 订单大小
     */
    @TableField("order_size")
    private String orderSize;

    /**
     * 担当
     */
    @TableField("sales_psn")
    private String salesPsn;

    /**
     * 用户部门
     */
    @TableField("user_dept")
    private String userDept;

    /**
     * 用户联系人
     */
    @TableField("user_psnName")
    private String userPsnName;

    /**
     * 用户联系人职位
     */
    @TableField("user_psnJob")
    private String userPsnJob;

    /**
     * 申请人
     */
    @TableField("apply_psn")
    private String applyPsn;

    @TableField(value = "create_Time", fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(value = "update_Time", fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @TableField("create_user")
    private String createUser;

    @TableField("update_user")
    private String updateUser;

    /**
     * 随机key
     */
    @TableField("passkey")
    private String passkey;

    /**
     * ppl_no项目号
     */
    @TableField("ppl_no")
    private String pplNo;

    /**
     * 客户组号
     */
    @TableField("group_customer_no")
    private String groupCustomerNo;

    /**
     * 行业代码
     */
    @TableField("ind_Code")
    private String indCode;

    /**
     * 仓库
     */
    @TableField("warehouse_code")
    private String warehouseCode;

    @TableField("apply_psn_no")
    private String applyPsnNo;

    /**
     * 负责人
     */
    @TableField("approver_no")
    private String approverNo;

    @TableField("approver_name")
    private String approverName;

    /**
     * 0-全球共享; 1-中国公司共享; 2-专享可借用; 3-客户专享; 4-国内不可用;
     */
    @TableField("shikomi_class")
    private String shikomiClass;

    /**
     * 1-SMC提案; 2-客户提案; 3-自动周转;
     */
    @TableField("repl_type")
    private String replType;

    @TableField("apply_psn_mail")
    private String applyPsnMail;

    @TableField("approver_mail")
    private String approverMail;

    @TableField("enname")
    private String enname;

    /**
     * 审批时间
     */
    @TableField("approve_time")
    private Date approveTime;

    /**
     * 共享客户（SHIKOMI）
     */
    @TableField("customer_nos")
    private String customerNos;

    /**
     * 是否异仓库调拨
     */
    @TableField("trans_flag")
    private Boolean transFlag;

    @TableField("vip")
    private Boolean vip;

    // po号
    @TableField("corder_no")
    private String corderNo;
}
