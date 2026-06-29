package com.smc.smccloud.model.csstock;

import lombok.Data;

import java.util.List;

/**
 *  代理收货请求
 */
@Data
public class CsStockImportOrderReceiveRequest {

    private String agentNo;
    private String warehouseCode;
    private Long[] ids;
    private String[] barcodes;
    private String userName;
}
