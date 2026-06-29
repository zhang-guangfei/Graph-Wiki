package com.sales.ops.serviceimpl.event.v3.order.entity;

import com.sales.ops.enums.InventoryStatusEnum;
import com.sales.ops.enums.InventoryTableTypeEnum;
import lombok.Data;

/**
 * 指令-库存关联关系
 */
@Data
public abstract class Relation {
    // 库存关联的物流指令
    protected String wmOrderId;
    // 库存类型
    protected InventoryTableTypeEnum invTable;
    // 库存ID
    protected Long invId;
    // 关联数量
    protected Integer useQty;
    // 如果wmOrderId是交易单，则为useQty,如果是调拨单，则为itemInv表中useQty-outQty
    protected Integer outQty;// 此outQty为交易单的出库数量，只有交易单的类型起作用
    // 库存状态
    protected InventoryStatusEnum invStatus;


    public String getInventoryTypeCode(){
        return null;
    }



}
