package com.sales.ops.serviceimpl.dispatch.rodispatch.domain;

import com.sales.ops.db.entity.OpsPco;
import com.sales.ops.db.entity.OpsPcoItem;
import com.sales.ops.db.entity.OpsPcoItemInventory;

public class PrePcoItemInventory {
    private OpsPco opsPco;
    private OpsPcoItem opsPcoItem;
    private OpsPcoItemInventory fromItemInventory;
    private OpsPcoItemInventory targetItemInventory;
    private int preQty;

    public PrePcoItemInventory(OpsPco opsPco, OpsPcoItem opsPcoItem, OpsPcoItemInventory fromItemInventory, OpsPcoItemInventory targetItemInventory, int preQty) {
        this.opsPco = opsPco;
        this.opsPcoItem = opsPcoItem;
        this.fromItemInventory = fromItemInventory;
        this.targetItemInventory = targetItemInventory;
        this.preQty = preQty;
    }

    public OpsPco getOpsPco() {
        return opsPco;
    }

    public void setOpsPco(OpsPco opsPco) {
        this.opsPco = opsPco;
    }

    public OpsPcoItem getOpsPcoItem() {
        return opsPcoItem;
    }

    public void setOpsPcoItem(OpsPcoItem opsPcoItem) {
        this.opsPcoItem = opsPcoItem;
    }

    public OpsPcoItemInventory getFromItemInventory() {
        return fromItemInventory;
    }

    public void setFromItemInventory(OpsPcoItemInventory fromItemInventory) {
        this.fromItemInventory = fromItemInventory;
    }

    public int getPreQty() {
        return preQty;
    }

    public void setPreQty(int preQty) {
        this.preQty = preQty;
    }

    public OpsPcoItemInventory getTargetItemInventory() {
        return targetItemInventory;
    }

    public void setTargetItemInventory(OpsPcoItemInventory targetItemInventory) {
        this.targetItemInventory = targetItemInventory;
    }
}
