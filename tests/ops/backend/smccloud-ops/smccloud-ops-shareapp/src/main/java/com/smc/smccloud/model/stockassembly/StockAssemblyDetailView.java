package com.smc.smccloud.model.stockassembly;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * Author: B90034
 * Date: 2022-01-18 13:22
 * Description:
 */
@Data
@TableName("stock_assembly_detail_view")
public class StockAssemblyDetailView {

    /**
     * stock_assembly_detail.inventory_keys
     * 按"库存类型~仓库代码~客户代码~客户群号~PPL~项目号"拼接
     */
    @TableField("inventory_keys")
    private String inventoryKeys;

    /**
     * stock_assembly_detail.warehouseCode
     */
    @TableField("detail_warehouseCode")
    private String detailWarehouseCode;

    /**
     * stock_assembly_detail.id
     */
    @TableId("detail_id")
    private Long detailId;

    /**
     * stock_assembly_detail.ApplyId
     */
    @TableField("ApplyId")
    private Long applyId;

    /**
     * stock_assembly_detail.ModelNo
     */
    @TableField("ModelNo")
    private String modelNo;

    /**
     * stock_assembly_detail.IsTransOut
     */
    @TableField("IsTransOut")
    private Boolean isTransOut;

    /**
     * stock_assembly_detail.Quantity
     */
    @TableField("Quantity")
    private Double quantity;

    /**
     * stock_assembly_detail.IsPrepareInv
     */
    @TableField("IsPrepareInv")
    private Boolean isPrepareInv;

    /**
     * stock_assembly_detail.OptCode
     * 处理状态 0-编辑中; 1-审核中; 2-待处理; 4-退回; 5-组换中; 6-已完成; 7-不能组换; 8-已计入成本; 9-已取消;
     */
    @TableField("OptCode")
    private String optCode;

    /**
     * stock_assembly_detail.LenDM
     */
    @TableField("LenDM")
    private String lenDM;

    /**
     * stock_assembly_detail.Unit
     */
    @TableField("Unit")
    private String unit;

    /**
     * stock_assembly_detail.Remark
     */
    @TableField("detail_remark")
    private String detailRemark;

    /**
     * stock_assembly_detail.AllowOutQty
     */
    @TableField("AllowOutQty")
    private String allowOutQty;

    /**
     * stock_assembly_detail.Inventory_Id
     */
    @TableField("Inventory_Id")
    private String inventoryId;

    /**
     * stock_assembly_detail.create_time
     */
    @TableField("create_time")
    private Date createTime;

    /**
     * stock_assembly_detail.update_time
     */
    @TableField("update_time")
    private Date updateTime;

    /**
     * stock_assembly_detail.create_user
     */
    @TableField("create_user")
    private String createUser;

    /**
     * stock_assembly_detail.update_user
     */
    @TableField("update_user")
    private String updateUser;

    /**
     * stock_assembly_detail.InvoiceNo
     * 票号
     */
    @TableField("InvoiceNo")
    private String InvoiceNo;

    /**
     * stock_assembly_detail.TransTime
     * 执行时间
     */
    @TableField("detail_transTime")
    private Date detailTransTime;

    /**
     * stock_assembly.Status
     */
    @TableField("Status")
    private String status;

    /**
     * stock_assembly.DeptNo
     */
    @TableField("DeptNo")
    private String deptNo;

    /**
     * stock_assembly.AssembleType
     */
    @TableField("AssembleType")
    private String assembleType;

    /**
     * 申请类型: 1-组换; 2-调库;
     * stock_assembly.ApplyType
     */
    @TableField("ApplyType")
    private String applyType;

    /**
     * stock_assembly.remark
     */
    @TableField("apply_remark")
    private String applyRemark;

    /**
     * stock_assembly.NeedForOrderNo
     */
    @TableField("NeedForOrderNo")
    private String needForOrderNo;

    /**
     * stock_assembly.NeedModelNo
     */
    @TableField("NeedModelNo")
    private String needModelNo;

    /**
     * stock_assembly.NeedQuantity
     */
    @TableField("NeedQuantity")
    private Integer needQuantity;

    /**
     * stock_assembly.BillNo
     */
    @TableField("BillNo")
    private String billNo;

    /**
     * stock_assembly.AnswerDate
     */
    @TableField("AnswerDate")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date answerDate;

    /**
     * stock_assembly.AnswerText
     */
    @TableField("AnswerText")
    private String answerText;

    /**
     * stock_assembly.ApplyDate
     */
    @TableField("ApplyDate")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date applyDate;

    /**
     * stock_assembly.ApplyPsn
     */
    @TableField("ApplyPsn")
    private String applyPsn;

    /**
     * stock_assembly.ApprovePsn
     */
    @TableField("ApprovePsn")
    private String approvePsn;

    /**
     * stock_assembly.DlvDate
     */
    @TableField("DlvDate")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date dlvDate;

    /**
     * stock_assembly.TransPsn
     */
    @TableField("TransPsn")
    private String transPsn;

    /**
     * stock_assembly.TransTime
     */
    @TableField("apply_transTime")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date applyTransTime;

    /**
     * stock_assembly.ReceiveTime
     */
    @TableField("ReceiveTime")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date receiveTime;

    /**
     * stock_assembly.ReceivePsn
     */
    @TableField("ReceivePsn")
    private String receivePsn;

    /**
     * stock_assembly.warehouseCode
     */
    @TableField("apply_warehouseCode")
    private String applyWarehouseCode;

    /**
     * stock_assembly.ApplyNo
     */
    @TableField("ApplyNo")
    private String applyNo;

    /**
     * stock_assembly.ApproveDate
     */
    @TableField("ApproveDate")
    private Date approveDate;

    // 标识
    @TableField("branch_flag")
    private Integer branchFlag;

    @TableField(select = false)
    private String inventoryTypeCode;

    @TableField(select = false)
    private String customerNo;

    @TableField(select = false)
    private String groupCustomerNo;

    @TableField(select = false)
    private String ppl;

    @TableField(select = false)
    private String projectCode;

    @TableField(select = false)
    private String customerName;

    public void setInventoryKeys(String inventoryKeys) {
        this.inventoryKeys = inventoryKeys;
        if (inventoryKeys != null && inventoryKeys.length() > 0) {
            String[] inventoryKey = inventoryKeys.split("~", -1);
            this.inventoryTypeCode = inventoryKey[0];
            this.detailWarehouseCode = inventoryKey[1];
            this.customerNo = inventoryKey[2];
            this.groupCustomerNo = inventoryKey[3];
            this.ppl = inventoryKey[4];
            this.projectCode = inventoryKey[5];
        }
    }
}
