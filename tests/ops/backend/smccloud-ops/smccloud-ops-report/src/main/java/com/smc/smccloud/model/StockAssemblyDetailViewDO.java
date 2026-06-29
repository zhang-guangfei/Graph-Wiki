package com.smc.smccloud.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * [0E232FF0-B466-11cf-A24F-00AA00A3EFFF, 1.00]	Begin DesignProperties = 	   Begin PaneConfigurations = 	      Begin PaneConfiguration = 0	         NumPanes = 4	         Configuration = "(H (1[48] 4[29] 2[12] 3) )"	      End	      Begin PaneConfiguration = 1	         NumPanes = 3	         Configuration = "(H (1 [50] 4 [25] 3))"	      End	      Begin PaneConfiguration = 2	         NumPanes = 3	         Configuration = "(H (1 [50] 2 [25] 3))"	      End	      Begin PaneConfiguration = 3
 * </p>
 *
 * @author smc
 * @since 2024-11-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("stock_assembly_detail_view")
public class StockAssemblyDetailViewDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableField("apply_remark")
    private String applyRemark;

    @TableField("detail_id")
    private Long detailId;

    @TableField("DeptNo")
    private String DeptNo;

    @TableField("detail_transTime")
    private Date detailTranstime;

    @TableField("ApplyPsn")
    private String ApplyPsn;

    @TableField("Inventory_Id")
    private Long inventoryId;

    @TableField("create_user")
    private String createUser;

    @TableField("ReceivePsn")
    private String ReceivePsn;

    @TableField("Unit")
    private String Unit;

    @TableField("TransPsn")
    private String TransPsn;

    @TableField("apply_transTime")
    private Date applyTranstime;

    @TableField("ApplyNo")
    private String ApplyNo;

    @TableField("update_user")
    private String updateUser;

    @TableField("create_time")
    private Date createTime;

    @TableField("apply_warehouseCode")
    private String applyWarehousecode;

    @TableField("IsTransOut")
    private Boolean IsTransOut;

    @TableField("AnswerText")
    private String AnswerText;

    @TableField("detail_remark")
    private String detailRemark;

    @TableField("NeedQuantity")
    private Integer NeedQuantity;

    @TableField("OptCode")
    private Integer OptCode;

    @TableField("ApprovePsn")
    private String ApprovePsn;

    @TableField("LenDM")
    private Integer LenDM;

    @TableField("DlvDate")
    private Date DlvDate;

    @TableField("ApplyDate")
    private Date ApplyDate;

    @TableField("AllowOutQty")
    private Integer AllowOutQty;

    @TableField("ReceiveTime")
    private Date ReceiveTime;

    @TableField("ApplyId")
    private Long ApplyId;

    @TableField("BillNo")
    private String BillNo;

    @TableField("AssembleType")
    private Integer AssembleType;

    @TableField("inventory_keys")
    private String inventoryKeys;

    @TableField("Quantity")
    private Double Quantity;

    @TableField("NeedForOrderNo")
    private String NeedForOrderNo;

    @TableField("NeedModelNo")
    private String NeedModelNo;

    @TableField("IsPrepareInv")
    private Boolean IsPrepareInv;

    @TableField("AnswerDate")
    private Date AnswerDate;

    @TableField("ModelNo")
    private String ModelNo;

    @TableField("detail_warehouseCode")
    private String detailWarehousecode;

    @TableField("Status")
    private Integer Status;

    @TableField("InvoiceNo")
    private String InvoiceNo;

    @TableField("ApplyType")
    private Integer ApplyType;

    @TableField("update_time")
    private Date updateTime;

    @TableField("ApproveDate")
    private Date ApproveDate;


}
