package com.sales.ops.serviceimpl.event.v3.status.entity;


import com.sales.ops.serviceimpl.event.v3.order.entity.EXRelation;
import com.sales.ops.serviceimpl.event.v3.order.entity.JYCK;

public class EXRelationStatus extends RelationStatus {

    private EXRelation exRelation;

    public EXRelationStatus(EXRelation exRelation) {
        this.exRelation = exRelation;
    }

    @Override
    public EXRelation getRelation() {
        return exRelation;
    }


    @Override
    public void createOrderStatusItem(JYCK jyck) {
        super.createOrderStatusItem(jyck);
        orderStatusItem.setQtyIn(0);
        orderStatusItem.setInventoryType(null);
        orderStatusItem.setInvoiceNo(null);
        orderStatusItem.setAssociateNo(null);
    }

    public boolean afterSign() {
        return true;
    }

}
