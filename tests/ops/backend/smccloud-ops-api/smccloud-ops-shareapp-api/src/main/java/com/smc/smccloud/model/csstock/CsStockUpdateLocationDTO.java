package com.smc.smccloud.model.csstock;

import lombok.Data;

/**
 * 修改委托在库货架位
 *
 * @author wsf
 * @version 1.0
 * @date 2022/1/7 11:25
 */
@Data
public class CsStockUpdateLocationDTO {
    /**
     * 代理代码
     */
    private  String agentNo;
    /**
     * 库房代码
     */
    private  String warehouseCode;
    /**
     * 型号
     */
    private String modelNo;

    /**
     * 货架位
     */
    private String locationNo;
}
