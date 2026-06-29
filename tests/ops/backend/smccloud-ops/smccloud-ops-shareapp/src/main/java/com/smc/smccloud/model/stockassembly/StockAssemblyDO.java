package com.smc.smccloud.model.stockassembly;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * Author: B90034
 * Date: 2021-09-27 12:09
 * Description:
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("stock_assembly")
public class StockAssemblyDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 申请ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("ApplyNo")
    private String applyNo;

    /**
     * 状态
     * 1-编辑中; 2-审核中; 3-待处理; 4-退回; 5-组换中; 6-已完成; 7-不能组装; 9-已取消
     */
    @TableField("Status")
    private String status;

    /**
     * 部门代码
     */
    @TableField("DeptNo")
    private String deptNo;

    /**
     * 组装类型
     * 1-组装; 2-拆分; 3-调库; 4-组换(仅业务); 5-组换(仅财务); 6-组换(仅WMS);
     */
    @TableField("AssembleType")
    private String assembleType;

    /**
     * 申请类型
     * 1-组换; 2-调库
     */
    @TableField("ApplyType")
    private String applyType;

    @TableField("Remark")
    private String remark;

    /**
     * 用于订单号
     */
    @TableField("NeedForOrderNo")
    private String needForOrderNo;

    /**
     * 所需型号
     */
    @TableField("NeedModelNo")
    private String needModelNo;

    /**
     * 所需数量
     */
    @TableField("NeedQuantity")
    private Integer needQuantity;

    /**
     * 调库票号
     */
    @TableField("BillNo")
    private String billNo;

    /**
     * 回复时间
     */
    @TableField("AnswerDate")
    private Date answerDate;

    /**
     * 回复说明
     */
    @TableField("AnswerText")
    private String answerText;

    /**
     * 申请时间
     */
    @TableField("ApplyDate")
    private Date applyDate;

    /**
     * 申请人
     */
    @TableField("ApplyPsn")
    private String applyPsn;

    /**
     * 审批时间
     */
    @TableField("ApproveDate")
    private Date approveDate;

    /**
     * 审批人
     */
    @TableField("ApprovePsn")
    private String approvePsn;

    /**
     * 希望交货期
     */
    @TableField("DlvDate")
    private Date dlvDate;

    /**
     * 调库人
     */
    @TableField("TransPsn")
    private String transPsn;

    /**
     * 调库时间
     */
    @TableField("TransTime")
    private Date transTime;

    @TableField("ReceiveTime")
    private Date receiveTime;

    @TableField("ReceivePsn")
    private String receivePsn;

    @TableField("WarehouseCode")
    private String warehouseCode;

    @TableField(value = "CreateTime", fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(value = "UpdateTime", fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @TableField("CreateUser")
    private String createUser;

    @TableField("UpdateUser")
    private String updateUser;
}
