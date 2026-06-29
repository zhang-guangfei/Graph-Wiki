package com.sales.ops.dto.inventory;

import com.sales.ops.db.entity.OpsDo;
import com.sales.ops.db.entity.OpsDoItem;
import com.sales.ops.db.entity.OpsDoItemInventory;

import java.util.Collections;
import java.util.List;

/**
 * @author C02483
 * @version 1.0
 * @description: 创建DO的信息时，返回DO详细信息
 * @date 2021/12/30 11:42
 */
public class OpsDoDto {

    private OpsDo opsDo;
    private OpsDoItem doItem;
    private List<OpsDoItemInventory> doItemInventoryList;
    private InventoryCkByOrderOutDto outDto;


    public OpsDoDto(OpsDo opsDo, OpsDoItem doItem, List<OpsDoItemInventory> doItemInventoryList, InventoryCkByOrderOutDto outDto) {
        this.opsDo = opsDo;
        this.doItem = doItem;
        this.doItemInventoryList = doItemInventoryList;
        this.outDto = outDto;
    }

    public OpsDoDto(OpsDo opsDo, OpsDoItem doItem, List<OpsDoItemInventory> doItemInventoryList) {
        this.opsDo = opsDo;
        this.doItem = doItem;
        this.doItemInventoryList = doItemInventoryList;
    }

    public OpsDoDto(OpsDo opsDo, OpsDoItem doItem, OpsDoItemInventory doItemInventory) {
        this.opsDo = opsDo;
        this.doItem = doItem;
        this.doItemInventoryList = Collections.singletonList(doItemInventory);
    }

    public OpsDoDto(OpsDo opsDo, OpsDoItem opsDoItem) {
        this.opsDo = opsDo;
        this.doItem = opsDoItem;
    }

    public CreDoByInventoryDto toCreDo() {
        return new CreDoByInventoryDto(opsDo, doItem, doItemInventoryList);
    }

    public OpsDo getOpsDo() {
        return opsDo;
    }

    public void setOpsDo(OpsDo opsDo) {
        this.opsDo = opsDo;
    }

    public OpsDoItem getDoItem() {
        return doItem;
    }

    public void setDoItem(OpsDoItem doItem) {
        this.doItem = doItem;
    }

    public List<OpsDoItemInventory> getDoItemInventoryList() {
        return doItemInventoryList;
    }

    public void setDoItemInventoryList(List<OpsDoItemInventory> doItemInventoryList) {
        this.doItemInventoryList = doItemInventoryList;
    }

    public InventoryCkByOrderOutDto getOutDto() {
        return outDto;
    }

    public void setOutDto(InventoryCkByOrderOutDto outDto) {
        this.outDto = outDto;
    }

    public OpsDoItemInventory getDoItemInventory() {
        return doItemInventoryList.get(0);
    }

}
