package com.sales.ops.serviceimpl.event.v3.order.entity;


import com.sales.ops.enums.InventoryStatusEnum;
import com.sales.ops.enums.InventoryTableTypeEnum;

public class EXRelation extends Relation {


    public EXRelation(JYCK jyck, InventoryStatusEnum status) {
        super.wmOrderId = jyck.getJyDoId();
        super.invTable = InventoryTableTypeEnum.EMPTY;
        super.invId = null;
        super.useQty = jyck.getQty();

        super.invStatus = status;
        super.outQty = 0;
    }




}
