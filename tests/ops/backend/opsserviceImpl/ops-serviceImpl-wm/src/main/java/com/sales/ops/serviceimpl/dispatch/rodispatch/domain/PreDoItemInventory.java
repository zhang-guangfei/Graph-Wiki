package com.sales.ops.serviceimpl.dispatch.rodispatch.domain;

import com.sales.ops.db.entity.OpsDo;
import com.sales.ops.db.entity.OpsDoItem;
import com.sales.ops.db.entity.OpsDoItemInventory;

public class PreDoItemInventory {
    private OpsDo opsDo;
    private OpsDoItem opsDoItem;
    private OpsDoItemInventory fromItemInventory;
    private OpsDoItemInventory targetItemInventory;
    private int preQty;

    public PreDoItemInventory(OpsDo opsDo, OpsDoItem opsDoItem, OpsDoItemInventory fromItemInventory, OpsDoItemInventory targetItemInventory, int preQty) {
        this.opsDo = opsDo;
        this.opsDoItem = opsDoItem;
        this.fromItemInventory = fromItemInventory;
        this.targetItemInventory = targetItemInventory;
        this.preQty = preQty;
    }

    public OpsDo getOpsDo() {
        return opsDo;
    }

    public void setOpsDo(OpsDo opsDo) {
        this.opsDo = opsDo;
    }

    public OpsDoItem getOpsDoItem() {
        return opsDoItem;
    }

    public void setOpsDoItem(OpsDoItem opsDoItem) {
        this.opsDoItem = opsDoItem;
    }

    public OpsDoItemInventory getFromItemInventory() {
        return fromItemInventory;
    }

    public void setFromItemInventory(OpsDoItemInventory fromItemInventory) {
        this.fromItemInventory = fromItemInventory;
    }


    public OpsDoItemInventory getTargetItemInventory() {
        return targetItemInventory;
    }

    public void setTargetItemInventory(OpsDoItemInventory targetItemInventory) {
        this.targetItemInventory = targetItemInventory;
    }

    public int getPreQty() {
        return preQty;
    }

    public void setPreQty(int preQty) {
        this.preQty = preQty;
    }
}