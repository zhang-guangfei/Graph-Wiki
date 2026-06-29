package com.sales.ops.serviceimpl.dispatch.rodispatch.domain;

import com.sales.ops.db.entity.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author C12961
 * @date 2023/4/27 8:06
 */
public class RoConfirmContextItem {


    /*  废弃字段，因为不能随表中的数据同步更新，所以在context里统一维护，当更新表数据时，刷新context的roDto
      private OpsRo ro;
      private OpsRoItem roItem;
     */
    private OpsRoItemInventory roItemInventory;
    private OpsInventoryMove move;
    private OpsInventory inventory;
    // 此move要转移的库存总数
    private int subQty;
    private int subPreQty;
    private List<PreDoItemInventory> preDoItemInvs;
    private List<PrePcoItemInventory> prePcoItemInvs;


    public RoConfirmContextItem(OpsRoItemInventory roItemInventory, OpsInventoryMove move, OpsInventory inv) {
        this.roItemInventory = roItemInventory;
        this.move = move;
        this.inventory = inv;
        this.preDoItemInvs = new ArrayList<>();
        this.prePcoItemInvs = new ArrayList<>();
    }


    public void addPreDoItemInventory(OpsDo opsDo, OpsDoItem opsDoItem, OpsDoItemInventory fromItemInventory, OpsDoItemInventory targetItemInventory, int preQty) {
        this.preDoItemInvs.add(new PreDoItemInventory(opsDo, opsDoItem, fromItemInventory, targetItemInventory, preQty));
    }

    public void addPrePcoItemInventory(OpsPco opsPco, OpsPcoItem opsPcoItem, OpsPcoItemInventory fromItemInventory, OpsPcoItemInventory targetItemInventory, int preQty) {
        this.prePcoItemInvs.add(new PrePcoItemInventory(opsPco, opsPcoItem, fromItemInventory, targetItemInventory, preQty));
    }


    public OpsRoItemInventory getRoItemInventory() {
        return roItemInventory;
    }

    public void setRoItemInventory(OpsRoItemInventory roItemInventory) {
        this.roItemInventory = roItemInventory;
    }

    public OpsInventoryMove getMove() {
        return move;
    }

    public void setMove(OpsInventoryMove move) {
        this.move = move;
    }

    public OpsInventory getInventory() {
        return inventory;
    }

    public void setInventory(OpsInventory inventory) {
        this.inventory = inventory;
    }

    public int getSubQty() {
        return subQty;
    }

    public void setSubQty(int subQty) {
        this.subQty = subQty;
    }

    public int getSubPreQty() {
        return subPreQty;
    }

    public void setSubPreQty(int subPreQty) {
        this.subPreQty = subPreQty;
    }


    public List<PreDoItemInventory> getPreDoItemInvs() {
        return preDoItemInvs;
    }

    public void setPreDoItemInvs(List<PreDoItemInventory> preDoItemInvs) {
        this.preDoItemInvs = preDoItemInvs;
    }

    public List<PrePcoItemInventory> getPrePcoItemInvs() {
        return prePcoItemInvs;
    }

    public void setPrePcoItemInvs(List<PrePcoItemInventory> prePcoItemInvs) {
        this.prePcoItemInvs = prePcoItemInvs;
    }

    public int getPrepareQty() {
        Integer doPrepareQty = this.preDoItemInvs.stream().map(PreDoItemInventory::getPreQty).reduce(Integer::sum).orElse(0);
        Integer pcoPrepareQty = this.prePcoItemInvs.stream().map(PrePcoItemInventory::getPreQty).reduce(Integer::sum).orElse(0);
        return doPrepareQty + pcoPrepareQty;
    }


}
