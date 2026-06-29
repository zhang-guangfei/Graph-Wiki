package com.smc.smccloud.model.csstock;

import lombok.Data;

/**
 * 盘点清单查查询
 *
 * @author wsf
 * @version 1.0
 * @date 2022/1/7 12:08
 */
@Data
public class CsStockTakeRequestDTO {
    private  String agentNo;
    private  String warehouseCode;
    private  String modelNo;
}
