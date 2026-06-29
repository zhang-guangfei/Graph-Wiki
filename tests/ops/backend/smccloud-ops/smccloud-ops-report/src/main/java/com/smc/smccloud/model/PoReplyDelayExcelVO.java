package com.smc.smccloud.model;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
public class PoReplyDelayExcelVO implements Serializable {



    @ExcelProperty("订单号")
    private String poOrderNo;
    @ExcelProperty("项号")
    private Integer poItemNo;
    @ExcelProperty("拆分单号")
    private Integer poSplitItemNo;
    @ExcelProperty("型号")
    private String modelNo;
    @ExcelProperty("数量")
    private Integer quantity;

    @ExcelProperty("营业所代码")
    private String deptNoCustomer;

    @ExcelProperty("客户代码")
    private String customerNo;

    @ExcelProperty("用户代码")
    private String userNo;

    @ExcelProperty("最新返信")
    private String replyDateMod;

    @ExcelProperty("供应商")
    private String supplierId;


    @ExcelProperty("订单类型")
    private String orderType;

    @ExcelProperty("采购类型")
    private String purchaseType;

    @ExcelProperty("采购状态")
    private String purchaseStateCode;

    @ExcelProperty("手配号")
    private String supplierOrderNo;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ExcelProperty("采购日期")
    private Date purchaseDate;

    //完整单号
    @ExcelIgnore
    private String poOrderFNo;
    //最终用户
    @ExcelIgnore
    private String endUser;
    //供应商实际出荷日
    @ExcelIgnore
    private Date facExpdate;
    // 客户货期
    @ExcelIgnore
    private Date hopeDeliveryDate;
    // 订单类型名称
    @ExcelIgnore
    private String orderTypeName;

}
