package com.smc.smccloud.model.pd;

import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @Author lyc
 * @Date 2023/8/3 9:32
 * @Descripton TODO
 */
@Data
public class OpsInventoryExportVO {

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
     * 型号
     */
    @ExcelProperty(value = "型号",index = 3)
    private String modelNo;

    /**
     * 数量
     */
    @ExcelProperty(value = "数量",index = 4)
    private Integer qty;

    /**
     * 数据类型1 库存数据，2 OPS采购到货未入，3 OPS调拨到货未入，4OPS退货到货未入
     */
    @ExcelProperty(value = "数据类型",index = 5)
    private String dataType;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING, timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ExcelProperty(value = "创建时间",index = 6)
    private Date createTime;

}
