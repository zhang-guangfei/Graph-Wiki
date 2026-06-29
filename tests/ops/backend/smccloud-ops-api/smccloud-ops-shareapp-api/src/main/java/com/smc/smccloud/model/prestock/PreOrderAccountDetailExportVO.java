package com.smc.smccloud.model.prestock;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author lyc
 * @Date 2024/11/26 13:39
 * @Descripton TODO
 */
@Data
public class PreOrderAccountDetailExportVO {

    @ExcelIgnore
    private String companyNo ;
    @ExcelProperty("分公司")
    private String companyName;
    @ExcelIgnore
    private String parentNo ;
    @ExcelProperty("营业部/行业")
    private String parentName ;
    @ExcelIgnore
    private String salesNo ;
    @ExcelProperty("营业所/子行业")
    private String salesName ;
    @ExcelIgnore
    private String deptNo ;
    @ExcelIgnore
    private String deptName ;

    @ExcelProperty("库房")
    private String warehouseCode;
    @ExcelProperty("库存类型")
    private String  inventoryTypeCode;


    @ExcelProperty("型号")
    private String modelNo ;

    @ExcelProperty("客户")
    private String customerNo;

    @ExcelProperty("客户群")
    private String groupCustomerNo ;

    @ExcelProperty("PPL")
    private String ppl ;

    @ExcelProperty("PJ")
    private String projectCode ;

    @ExcelProperty("决算申请号")
    private String accountApplyNo;

    @ExcelProperty("申请号")
    private String applyNo;

    // 申请项号
    @ExcelProperty("项号")
    private String applyItemNo;

    @ExcelProperty("订单号")
    private String orderNo ;

    @ExcelProperty("入库数量")
    private Integer roQty ;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ExcelProperty("入库日期")
    private Date roDate ;

    @ExcelProperty("有效在库数")
    private Integer avaQty ;


    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING, timezone = "GMT+8")
    @ExcelProperty("需求日期")
    private Date requirementDate ;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ExcelProperty("决算日期")
    private Date countDate ;

    @ExcelIgnore
    private int status ;
    @ExcelProperty("决算状态")
    private String statusName;

    @ExcelProperty("推送决算数")
    private Integer pushQty ;

    @ExcelProperty("审批中数")
    private Integer approveCountQty ;

    // 确认决算数
    @ExcelProperty("确认决算数")
    private Integer sureAccountQty ;
    // 决算说明
    @ExcelProperty("决算说明")
    private String accountDesc;

    @ExcelProperty("确认延期数")
    private Integer delayQty ;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @ExcelProperty("延期日期")
    private Date delayDate ;

    // 延期说明
    @ExcelProperty("延期说明")
    private String delayDesc;

    // 实际延期数量
    @ExcelProperty("延期中数")
    private int factDelayQty;


    @ExcelProperty("清算数")
    private Integer transQty ;


    @ExcelProperty("调库号")
    private String transNo ;

    @ExcelProperty("E价格")
    private BigDecimal ePrice ;


    @ExcelProperty("E金额")
    private BigDecimal eAmount ;

    @ExcelProperty("BIN")
    private String binStr ;

    @ExcelProperty("担当")
    private String charger ;


}
