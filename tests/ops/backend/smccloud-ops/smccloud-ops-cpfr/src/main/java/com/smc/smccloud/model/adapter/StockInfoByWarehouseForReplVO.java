package com.smc.smccloud.model.adapter;


import lombok.Data;

/*
  仓库在库在途数量
* */
@Data
public class StockInfoByWarehouseForReplVO {

    /**
     * 仓库代码
     */
    private  String warehouseCode;

    /**
     * 通用在库总可用数量
     */
    private Integer tyAvaQty;

    /**
     * 通用总在途数量
     */
    private Integer tyTransQty;

    private Integer binQty;

    /**
     * 是分仓
     */
    private  Boolean isSubWarehouse;





}
