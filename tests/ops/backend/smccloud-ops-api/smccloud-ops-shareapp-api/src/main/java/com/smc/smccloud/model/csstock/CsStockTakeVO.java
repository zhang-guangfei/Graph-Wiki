package com.smc.smccloud.model.csstock;

import lombok.Data;

/**
 * 盘点清单信息
 *
 * @author wsf
 * @version 1.0
 * @date 2022/1/7 12:09
 */
@Data
public class CsStockTakeVO {

    private  String modelNo;
    private  Integer qtyOnhand;
    private  String locationNo;


}
