package com.smc.smccloud.model.purchase;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author smc
 * @since 2023-10-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("purchase_modify")
public class PurchaseModifyDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 变更类型(变更采购运输方式、变更指定工厂出荷日、变更供应商、删除采购单)
     */
    @TableField("modify_type")
    private String modifyType;

    /**
     * 门户任务批次号
     */
    @TableField("batch_no")
    private String batchNo;
    /**
     * 处理状态(待处理、暂不处理、不可变更、完成)
     */
    @TableField("status")
    private Integer status;

    /**
     * 申请号
     */
    @TableField("apply_no")
    private String applyNo;

    /**
     * 申请表更内容
     */
    @TableField("apply_content")
    private String applyContent;

    /**
     * 原因说明,申请理由
     */
    @TableField("apply_reason")
    private String applyReason;

    /**
     * 申请人
     */
    @TableField("apply_person_no")
    private String applyPersonNo;

    @TableField("apply_dept_no")
    private String applyDeptNo;

    /**
     * 申请时间
     */
    @TableField("apply_time")
    private Date applyTime;


    /**
     * 完整的采购单号（包含拆分单号）
     */
    @TableField("orderFno")
    private String orderFno;

    /**
     * 主单号
     */
    @TableField("orderNo")
    private String orderNo;

    /**
     * 子项号
     */
    @TableField("itemNo")
    private Integer itemNo;

    /**
     * 拆分单号
     */
    @TableField("splitItemNo")
    private Integer splitItemNo;

    /**
     * 型号
     */
    @TableField("modelno")
    private String modelno;

    /**
     * 数量
     */
    @TableField("quantity")
    private Integer quantity;

    /**
     * 营业所
     */
    @TableField("dept_no")
    private String deptNo;

    /**
     * 客户代码
     */
    @TableField("customer_no")
    private String customerNo;

    /**
     * 采购单状态
     */
    @TableField("purchase_statecode")
    private String purchaseStatecode;

    /**
     * 采购供应商
     */
    @TableField("supplierId")
    private String supplierId;

    /**
     * 重量
     */
    //todo 查 ops_requestPurchase
    @TableField("netweight")
    private BigDecimal netweight;

    /**
     * 运输方式
     */
    @TableField("transType")
    private String transType;

    /**
     * 指定工厂出荷日
     */
    @TableField("hopeExportDate")
    private Date hopeExportDate;

    /**
     * 业务处理人
     */
    @TableField("handler")
    private String handler;

    /**
     * 业务处理结果
     */
    @TableField("handle_result")
    private String handleResult;

    /**
     * 业务处理时间
     */
    @TableField("handle_time")
    private Date handleTime;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;

    /**
     * 创建人
     */
    @TableField("create_user")
    private String createUser;

    /**
     * 更新时间
     */
    @TableField("update_time")
    private Date updateTime;

    /**
     * 更新人
     */
    @TableField("update_user")
    private String updateUser;

    /**
     * 日本回复手配号
     */
    @TableField("supplier_orderno")
    private String supplierOrderno;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("user_no")
    private String userNo;

    @TableField("end_user")
    private String endUser;

    @TableField("remark")
    private String remark;

    @TableField("source_type")
    private String sourceType;

}
