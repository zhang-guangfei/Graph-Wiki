package com.smc.ops.delivery.model.inqb;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class OpsInquiryApplyExcelVO {
    @ExcelProperty("催促申请号")
    private String inquiryApplyNo;
    @ExcelProperty("订单号")
    private String orderNo;
    @ExcelProperty("型号")
    private String modelNo;
    @ExcelProperty("数量")
    private Integer quantity;
    @ExcelProperty("客户代码")
    private String customerNo;
    @ExcelProperty("最终用户")
    private String endUser;
    @ExcelProperty("客户PO")
    private String purchaseNo;
    @ExcelProperty("订单状态")
    private String orderStatus;
    @ExcelProperty("订单类型")
    private String orderType;
    @ExcelProperty("催促状态")
    private String inquiryStatus;

    @ExcelProperty("指定出荷日")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date hopeExportDate;

    @ExcelProperty("返信纳期")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dlvModdate;

    @ExcelProperty("期望出库日")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date hopeDeliveryDate;

    @ExcelProperty("催促原因")
    private String inquiryReason;
    @ExcelProperty("催促原因参数")
    private String inquiryReasonParam;
    @ExcelProperty("催促备注")
    private String inquiryRemark;
    @ExcelProperty("催促时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date inquiryTime;
    @ExcelProperty("申请部门")
    private String inquiryDept;
    @ExcelProperty("申请人员工编号")
    private String inquiryUser;
    @ExcelProperty("申请人")
    private String inquiryUserName;
    @ExcelProperty("供应商")
    private String replyDept;
    @ExcelProperty("供应商接单号")
    private String supplierOrderNo;
    @ExcelProperty("回复号")
    private String replyNo;
    @ExcelProperty("回复货期")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String replyDeliveryDateSrc;
    @ExcelProperty("回复人")
    private String replyUser;
    @ExcelProperty("回复时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date replyTime;
    @ExcelProperty("回复延迟原因")
    private String replyDelayReason;
    @ExcelProperty("回复备注")
    private String replyRemark;
    @ExcelProperty("催促级别")
    private String inquiryLevel;
    @ExcelProperty("回复部门")
    private String replySupplierDept;
    @ExcelProperty("催促结果描述")
    private String replyResultDesc;
}