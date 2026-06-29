package com.smc.smccloud.model.pd;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @Author lyc
 * @Date 2023/12/15 12:15
 * @Descripton TODO
 */
@Data
public class ExportShelvesVO {

    @ExcelProperty(value = "仓库",index = 0)
    private String warehouseCode;

    @ExcelProperty(value = "型号",index = 1)
    private String modelNo1;

    @ExcelProperty(value = "账簿数",index = 2)
    private int billQty;

    @ExcelProperty(value = "货架号",index = 3)
    private String shelvesNo;
}
