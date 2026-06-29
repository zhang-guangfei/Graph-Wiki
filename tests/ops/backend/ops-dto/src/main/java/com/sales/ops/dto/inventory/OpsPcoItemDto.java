package com.sales.ops.dto.inventory;

import com.sales.ops.db.entity.OpsDo;
import com.sales.ops.db.entity.OpsPco;
import com.sales.ops.db.entity.OpsPcoItem;
import com.sales.ops.db.entity.OpsPcoItemInventory;

import java.util.Collections;
import java.util.List;

/**
 * @author C02483
 * @version 1.0
 * @description: 创建DO的信息时，返回DO详细信息
 * @date 2021/12/30 11:42
 */
public class OpsPcoItemDto {

    private OpsDo jyck;
    private OpsPco opsPco;
    private OpsPcoItem pcoItem;
    private List<OpsPcoItemInventory> pcoItemInventoryList;

    public OpsPcoItemDto(OpsPco opsPco, OpsPcoItem pcoItem) {
        this.opsPco = opsPco;
        this.pcoItem = pcoItem;
    }

    public OpsPcoItemDto(OpsPco opsPco, OpsPcoItem pcoItem, OpsPcoItemInventory pcoItemInventory) {
        this.opsPco = opsPco;
        this.pcoItem = pcoItem;
        this.pcoItemInventoryList = Collections.singletonList(pcoItemInventory);
    }

    public OpsPcoItemDto(OpsPco opsPco, OpsPcoItem pcoItem, List<OpsPcoItemInventory> pcoItemInventoryList) {
        this.opsPco = opsPco;
        this.pcoItem = pcoItem;
        this.pcoItemInventoryList = pcoItemInventoryList;
    }

    public OpsPcoItemDto(OpsDo jyck, OpsPco opsPco, OpsPcoItem pcoItem, OpsPcoItemInventory pcoItemInventory) {
        this.jyck = jyck;
        this.opsPco = opsPco;
        this.pcoItem = pcoItem;
        this.pcoItemInventoryList = Collections.singletonList(pcoItemInventory);
    }

    public OpsDo getJyck() {
        return jyck;
    }

    public void setJyck(OpsDo jyck) {
        this.jyck = jyck;
    }

    public OpsPco getOpsPco() {
        return opsPco;
    }

    public void setOpsPco(OpsPco opsPco) {
        this.opsPco = opsPco;
    }

    public OpsPcoItem getPcoItem() {
        return pcoItem;
    }

    public int getSplitNo() {
        return pcoItem.getPcoItem();
    }

    public void setPcoItem(OpsPcoItem pcoItem) {
        this.pcoItem = pcoItem;
    }

    public List<OpsPcoItemInventory> getPcoItemInventoryList() {
        return pcoItemInventoryList;
    }

    public OpsPcoItemInventory getPcoItemInventory() {
        return pcoItemInventoryList.get(0);
    }

    public void setPcoItemInventoryList(List<OpsPcoItemInventory> pcoItemInventoryList) {
        this.pcoItemInventoryList = pcoItemInventoryList;
    }
}
