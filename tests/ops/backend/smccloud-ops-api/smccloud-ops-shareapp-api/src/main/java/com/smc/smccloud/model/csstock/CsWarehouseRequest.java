package com.smc.smccloud.model.csstock;

import com.smc.smccloud.core.model.page.BaseQuery;
import com.smc.smccloud.core.model.page.Page;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 寄售库房查询
 *
 * @author wsf
 * @version 1.0
 * @date 2021/11/8 18:20
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CsWarehouseRequest extends BaseQuery {

    private  String agentNo;

    private  String warehouseCode;
 
    private String warehouseType;

}
