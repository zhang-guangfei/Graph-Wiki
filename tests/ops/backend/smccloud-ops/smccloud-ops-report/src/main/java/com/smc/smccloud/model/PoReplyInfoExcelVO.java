package com.smc.smccloud.model;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
@Data
public class PoReplyInfoExcelVO implements Serializable {

    @ExcelProperty("采购单")
    private String poOrderFNo;
    @ExcelIgnore
    private String poOrderNo;
    @ExcelIgnore
    private Integer poItemNo;
    @ExcelIgnore
    private Integer poSplitItemNo;
    @ExcelProperty("型号")
    private String modelNo;
    @ExcelProperty("数量")
    private Integer quantity;
    // 手配号，即供应商接单号
    @ExcelProperty("手配号")
    private String supplierOrderNo;
    // 供应商代码
    @ExcelProperty("供应商")
    private String supplierId;
    // 指定出荷日
    @ExcelProperty("指定出荷日")
    private Date hopeExportDate;

    // 变更前返信
    @ExcelProperty("变更前返信")
    private String replyDateModBefore;
    // 变更后返信
    @ExcelProperty("变更后返信")
    private String replyDateMod;

    // 出库区分
    @ExcelIgnore
    private String produceFactory;

    // 客户代码
    @ExcelProperty("客户")
    private String customerNo;

    // 用户代码
    @ExcelProperty("用户")
    private String userNo;

    // 最终用户
    @ExcelProperty("最终用户")
    private String endUser;

    // 客户所属部门
    @ExcelProperty("部门")
    private String deptNoCustomer;


    // 担当
    @ExcelProperty("担当")
    private String leader;

    // 供应商实际出荷日
    @ExcelIgnore
    private Date facExpdate;

    // 客户货期
    @ExcelIgnore
    private Date hopeDeliveryDate;

    // 订单类型
    @ExcelIgnore
    private String orderType;

    // 订单类型名称
    @ExcelProperty("订单类型")
    private String orderTypeName;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @ExcelProperty("变更日期")
    private Date modifyTime;



}
