package com.smc.smccloud.model.csstock;

import com.smc.smccloud.core.model.page.BaseQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 寄售库房查询
 *
 * @author wsf
 * @version 1.0
 * @date 2021/11/8 18:20
 */
@Data
public class WarehouseStockCodeVO {

    private String warehouseCode;

    private String warehouseType;

    private String warehouseName;

    private String customerNo;




}
