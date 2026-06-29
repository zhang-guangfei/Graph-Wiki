package com.sales.ops.dto.order;

import com.sales.ops.db.entity.OpsPco;
import com.sales.ops.db.entity.OpsPcoItem;
import com.sales.ops.db.entity.OpsPcoItemInventory;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * @author ：C14717
 * @version: 1.0$
 * @description：加工指令和明细 关联库存
 * @date ：Created in 2021/10/28 8:21
 */
public class OpsPcoAndItemAndItemInventoryDto implements Serializable {

    private static final long serialVersionUID = 8658582502442402467L;

    private OpsPco opsPco;

    private List<OpsPcoItem> itemList;

    private List<OpsPcoItemInventory> itemInventoryList;

    public OpsPco getOpsPco() {
        return opsPco;
    }

    public void setOpsPco(OpsPco opsPco) {
        this.opsPco = opsPco;
    }

    public List<OpsPcoItem> getItemList() {
        return itemList;
    }

    public void setItemList(List<OpsPcoItem> itemList) {
        this.itemList = itemList;
    }

    public List<OpsPcoItemInventory> getItemInventoryList() {
        return itemInventoryList;
    }

    public void setItemInventoryList(List<OpsPcoItemInventory> itemInventoryList) {
        if(CollectionUtils.isEmpty(itemInventoryList)){
            this.itemInventoryList = Collections.emptyList();
        }else {
            this.itemInventoryList = itemInventoryList;
        }
    }
}
