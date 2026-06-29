package com.smc.smccloud.model.pd;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @Author lyc
 * @Date 2023/7/31 13:28
 * @Descripton TODO
 */
@Data
public class PdDiffBillDataExportVO {

    @ExcelProperty(value = "ID",index = 0)
    private Long id;

    /**
     * 盘点批次号
     */
    @ExcelProperty(value = "盘点批次号",index = 1)
    private String pdBatchNo;


    /**
     * 盘点票号
     */
    @ExcelProperty(value = "盘点票号",index = 2)
    private String pdBillNo;

    /**
     * 票据类型1：现品票，2数据票，3.清单票 4.现品空白票，5到货未入空白票
     */
    @ExcelProperty(value = "票据类型",index = 3)
    private String pdBillType;

    /**
     * 货架号
     */
    @ExcelProperty(value = "货架号",index = 4)
    private String shelvesNo;

    /**
     * 货位号
     */
    @ExcelProperty(value = "货位号",index = 5)
    private String locationNo;


    /**
     * 发票号(仅到货未入数据填写)
     */
    @ExcelProperty(value = "发票号",index = 6)
    private String invoiceNo;

    /**
     * 拍号(仅到货未入数据填写)
     */
    @ExcelProperty(value = "拍号",index = 7)
    private String caseNo;

    /**
     * 箱号(仅到货未入数据填写)
     */
    @ExcelProperty(value = "BarCode",index = 8)
    private String barcode;

    /**
     * 账簿数
     */
    @ExcelProperty(value = "账簿数量",index = 9)
    private Integer billQty;

    /**
     * 第一次盘点型号
     */
    @ExcelProperty(value = "第一次盘点型号",index = 10)
    private String modelNo1;

    /**
     * 第二次盘点型号
     */
    @ExcelProperty(value = "第二次盘点型号",index = 11)
    private String modelNo2;



    /**
     * 第一次盘点数量
     */
    @ExcelProperty(value = "第一次盘点数量",index = 12)
    private Integer pdQty1;

    /**
     * 第二次盘点数量
     */
    @ExcelProperty(value = "第二次盘点数量",index = 13)
    private Integer pdQty2;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING, timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ExcelProperty(value = "第一次录入时间",index = 14)
    private Date pdInputTime1UI;

    /**
     * 第二次录入时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING, timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ExcelProperty(value = "第二次录入时间",index = 15)
    private Date pdInputTime2UI;

    /**
     * 第一次录入担当
     */
    @ExcelProperty(value = "第一次录入人",index = 16)
    private String pdInputort1;

    /**
     * 第二次录入担当
     */
    @ExcelProperty(value = "第二次录入人",index = 17)
    private String pdInputort2;

    @ExcelIgnore
    private Date pdInputTime1;

    @ExcelIgnore
    private Date pdInputTime2;


}
