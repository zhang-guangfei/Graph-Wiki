package com.smc.smccloud.model.prestock;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.annotation.Tainted;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author lyc
 * @Date 2024/11/26 12:50
 * @Descripton TODO
 */
@Data
public class PreOrderAccountExportVO {


    @ExcelProperty("分公司")
    private String companyName;


    @ExcelProperty("营业部/行业")
    private String parentName;

    @ExcelProperty("营业所/子行业")
    private String salesName;

    // 库房
    @ExcelProperty("库房")
    private String warehouseCode ;

    // 库存分类：顾客在库，战略在库、通用在库
    @ExcelProperty("库存类型")
    private String inventoryTypeCode ;

    @ExcelProperty("型号")
    private String modelNo ;

    // 客户号
    @ExcelProperty("客户")
    private String customerNo ;

    // 客户群代码
    @ExcelProperty("客户群")
    private String groupCustomerNo ;


    // PPL代码
    @ExcelProperty("PPL")
    private String ppl ;

    // 项目号
    @ExcelProperty("PJ")
    private String projectCode ;

    // 入库数量（2年内)
    @ExcelProperty("入库数量")
    private Integer roQty ;

    // 有效在库数
    @ExcelProperty("有效在库")
    private Integer avaQty ;

    // 推送决算数
    @ExcelProperty("推送决算数")
    private Integer pushQty ;

    // 审批中数
    @ExcelProperty("审批中数")
    private Integer approveQty ;

    // 延期数量 实际延期数量
    @ExcelProperty("延期中数")
    private Integer delayQty ;


    // 最终调出数量 (清算数)
    @ExcelProperty("清算数")
    private Integer transQty ;

    // 订货频率12个月
    @ExcelProperty("订货频率12")
    private Integer frequency12 ;
    // 月均12个月
    @ExcelProperty("订货月均12")
    private Integer averageof12 ;


    // 保有月=有效在库数/月均
    @ExcelProperty("保留月")
    private Integer retentionMonth ;

    // push_qty*eprice
    @ExcelProperty("E金额")
    private BigDecimal eAmount ;

    @ExcelProperty("BIN")
    private String binStr;

    // 担当
    @ExcelProperty("担当")
    private String charger ;

    // BIN(物流中心或ALL存在1个BIN设置，就显示“是”)
    @ExcelIgnore
    private Boolean isBin ;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ExcelIgnore
    private Date createTime ;

    @ExcelIgnore
    private String creator ;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ExcelIgnore
    private Date modifyTime ;

    @ExcelIgnore
    private String modifier ;

    // 营所/子行业
    @ExcelIgnore
    private String deptNo ;
}
