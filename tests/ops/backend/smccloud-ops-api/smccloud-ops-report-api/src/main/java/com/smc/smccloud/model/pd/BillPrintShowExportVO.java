package com.smc.smccloud.model.pd;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class BillPrintShowExportVO {

    // 盘点批次、仓库代码、盘点票号、货架号、货位号、订单号、型号、账簿数量

    @ExcelProperty(value = "盘点批次")
    private String pdBatchNo;

    @ExcelProperty(value = "仓库代码")
    private String warehouseCode;

    @ExcelProperty(value = "盘点票号")
    private String pdBillNo;

    @ExcelProperty(value = "货架号")
    private String shelvesNo;

    @ExcelProperty(value = "货位号")
    private String locationNo;

    @ExcelProperty(value = "订单号")
    private String orderNo;

    @ExcelProperty(value = "型号")
    private String modelNo1;

    @ExcelProperty(value = "账簿数量")
    private Integer billQty;


}
