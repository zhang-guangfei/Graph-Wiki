package com.smc.smccloud.model.specorder;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class SpecOrderExportVO {

    @ExcelProperty(value = "批次号",index = 0)
    private String groupNo; //批次号
    @ExcelProperty(value = "订单号",index = 1)
    private String orderNo; //订单号
    @ExcelProperty(value = "项号",index = 2)
    private Integer orderItem; //项号

    @ExcelProperty(value = "客户代码",index = 3)
    private String customerNo; //客户代码
    @ExcelProperty(value = "用户代码",index = 4)
    private String userNo; //用户代码

    @ExcelIgnore
    private Integer status;
    @ExcelProperty(value = "订单状态",index = 5)
    private String statusName;

    @ExcelIgnore
    private Integer orderType; //申请类型
    @ExcelProperty(value = "申请类型",index = 6)
    private String orderTypeName;

    @ExcelProperty(value = "型号",index = 7)
    private String modelNo; //产品型号

    @ExcelProperty(value = "数量",index = 8)
    private Integer quantity; //数量

    @ExcelProperty(value = "原币",index = 9)
    private String orgCurrency; //原币

    @ExcelProperty(value = "原币单价",index = 10)
    private BigDecimal orgPrice; //原币单价

    @ExcelProperty(value = "单价",index = 11)
    private BigDecimal price; //单价

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @ExcelProperty(value = "交货期",index = 12)
    private Date dlvDate; //交货期

    @ExcelProperty(value = "客户型号",index = 13)
    private String cproductNo; //客户型号

    @ExcelProperty(value = "客户订单号",index = 14)
    private String corderNo; //客户订单号

    // 投诉号
    @ExcelProperty(value = "投诉号",index = 15)
    private String complaintNo;

    @ExcelProperty(value = "备注",index = 16)
    private String remark; //备注

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ExcelProperty(value = "创建时间",index = 17)
    private Date createTime;

    @ExcelProperty(value = "创建人",index = 18)
    private String createUser; //创建人


}
