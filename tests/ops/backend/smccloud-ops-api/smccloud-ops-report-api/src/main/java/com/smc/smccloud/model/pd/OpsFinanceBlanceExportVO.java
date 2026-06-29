package com.smc.smccloud.model.pd;

import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @Author lyc
 * @Date 2023/8/3 10:15
 * @Descripton TODO
 */
@Data
public class OpsFinanceBlanceExportVO {


    @ExcelProperty(value = "id",index = 0)
    private Long id;

    /**
     * 盘点批次号
     */
    @ExcelProperty(value = "盘点批次号",index = 1)
    private String pdBatchNo;

    /**
     * 仓库代码(预留字段)
     */
    @ExcelProperty(value = "仓库代码",index = 2)
    private String warehouseCode;

    /**
     * 型号
     */
    @ExcelProperty(value = "型号",index = 3)
    private String modelNo;

    /**
     * 财务结存数量
     */
    @ExcelProperty(value = "财务结存数量",index = 4)
    private Integer balanceQty;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING, timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ExcelProperty(value = "创建时间",index = 5)
    private Date createTime;

}
