package com.smc.smccloud.model.pd;

import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @Author lyc
 * @Date 2023/8/2 15:45
 * @Descripton TODO
 */
@Data
public class OpsAsPdCompensateDataExportVO {
    private static final long serialVersionUID = 1L;

    @ExcelProperty(value = "ID",index = 0)
    private Long id;

    /**
     * 盘点批次号
     */
    @ExcelProperty(value = "盘点批次号",index = 1)
    private String pdBatchNo;

    /**
     * 仓库代码
     */
    @ExcelProperty(value = "仓库代码",index = 2)
    private String warehouseCode;

    /**
     * 盘点票号(预留字段)
     */
    @ExcelProperty(value = "盘点票号",index = 3)
    private String pdBillNo;

    /**
     * 数据类型1 样品未结转，2已发货未推财务，3财务补偿数据，4调拨在途，5制造在途
     */
    @ExcelProperty(value = "数据类型",index = 4)
    private String pdDataType;

    /**
     * 数据来源1WMS，2OPS ，3财务系统，调拨在途和制造在途WMS和OPS均有自己的数据
     */
    @ExcelProperty(value = "数据来源",index = 5)
    private String pdDataSource;

    /**
     * 完整单号
     */
    @ExcelProperty(value = "完整单号",index = 6)
    private String orderNo;

    /**
     * 主单号
     */
    @ExcelProperty(value = "主单号",index = 7)
    private String rorderNo;

    /**
     * 项号
     */
    @ExcelProperty(value = "项号",index = 8)
    private Integer itemNo;

    /**
     * 拆分项号
     */
    @ExcelProperty(value = "拆分项号",index = 9)
    private Integer splitItemNo;

    /**
     * doid
     */
    @ExcelProperty(value = "doid",index = 10)
    private String doId;

    /**
     * 型号
     */
    @ExcelProperty(value = "型号",index = 11)
    private String modelNo;

    /**
     * 数量
     */
    @ExcelProperty(value = "数量",index = 12)
    private Integer qty;


    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING, timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ExcelProperty(value = "创建时间",index = 13)
    private Date createTime;


}
