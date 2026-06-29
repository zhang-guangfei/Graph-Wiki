package com.sales.ops.serviceimpl.dispatch.dodispatch.domain.goodsconfirm;

import com.sales.ops.db.entity.*;
import com.sales.ops.dto.inventory.OpsDoDto;
import com.sales.ops.dto.inventory.OpsPcoItemDto;
import com.sales.ops.enums.WarehouseTypeEnum;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author C12961
 * @date 2023/3/14 13:36
 */
@Data
public class GoodsConfirmContextItem {


    // 当前在途库存
    private OpsInventoryMove move;
    // 签收仓库
    private OpsWarehouse warehouse;
    // move的发票号
    private String invoiceNo;
    // move所属roId
    private String roId;
    // roId的该发票的所有的barcode总数
    private int barcodeQty;

    private GoodsConfirmReason reason = GoodsConfirmReason.SAME_RO_WITH_OTHER_MOVE;

    // 此move的关联关系（预约信息）：
    // DOID，DbItemInventory     调拨单的关联关系
    Map<String, List<OpsDoDto>> dbMap = new HashMap<>();
    // DOID，DoItemInventory     交易出库-不加工的关联关系
    Map<String, List<OpsDoDto>> doMap = new HashMap<>();
    // DOID，PcoItemInventory    交易出库-加工的关联关系
    Map<String, List<OpsPcoItemDto>> pcoMap = new HashMap<>();

    public GoodsConfirmContextItem(String invoiceNo, OpsInventoryMove move, OpsWarehouse warehouse, String roId, Integer barcodeQty) {
        this.invoiceNo = invoiceNo;
        this.move = move;
        this.warehouse = warehouse;
        this.roId = roId;
        this.barcodeQty = barcodeQty;
    }

    public void putDB(OpsDo opsdo, OpsDoItem opsDoItem, OpsDoItemInventory item) {
        String doId = item.getDoId();
        if (!this.dbMap.containsKey(doId)) {
            this.dbMap.put(doId, new ArrayList<>());
        }
        this.dbMap.get(doId).add(new OpsDoDto(opsdo, opsDoItem, item));
    }

    public void putDo(OpsDo opsdo, OpsDoItem opsDoItem, OpsDoItemInventory item) {
        String doId = item.getDoId();
        if (!this.doMap.containsKey(doId)) {
            this.doMap.put(doId, new ArrayList<>());
        }
        this.doMap.get(doId).add(new OpsDoDto(opsdo, opsDoItem, item));
    }

    public void putPco(OpsDo jyck, OpsPco pco, OpsPcoItem pcoItem, OpsPcoItemInventory item) {
        String doId = jyck.getDoId();
        if (!this.pcoMap.containsKey(doId)) {
            this.pcoMap.put(doId, new ArrayList<>());
        }
        this.pcoMap.get(doId).add(new OpsPcoItemDto(jyck, pco, pcoItem, item));
    }

    public boolean notMasterWarehouse() {
        boolean yes = !StringUtils.equals(WarehouseTypeEnum.RDC.getHouseTypeCode(), warehouse.getWarehouseType());
        if (yes) this.reason = GoodsConfirmReason.NOT_MASTER;
        return yes;
    }

    public boolean isMergePurchase() {
        boolean yes = move.getQuantity() < this.barcodeQty;
        if (yes) this.reason = GoodsConfirmReason.MERGE_PURCHASE;
        return yes;
    }

    public boolean notFullPrepareForMove() {
        boolean yes = (move.getPrepareQuantity() > 0 && move.getPrepareQuantity() < this.barcodeQty);
        this.reason = GoodsConfirmReason.NOT_FULL_PRE_QTY;
        return yes;
    }

    public boolean noPrepareByDoOrPco() {
        boolean yes = (this.doMap.size() + this.dbMap.size() + this.pcoMap.size() == 0);
        if (yes) this.reason = GoodsConfirmReason.NOT_FULL_PRE_QTY;
        return yes;
    }

    public boolean prepareByDoAndPco() {
        boolean yes = this.doMap.size() + this.dbMap.size() + this.pcoMap.size() > 1;
        if (yes) this.reason = GoodsConfirmReason.BOTH_DO_AND_PCO_REPATION;
        return yes;
    }

    public boolean prepareByOneDB() {
        boolean yes = (this.dbMap.size() == 1);
        if (yes) this.reason = GoodsConfirmReason.DBCK_NOT_ENOUGH;
        return yes;
    }

    public boolean prepareByOneDo() {
        boolean yes = (this.doMap.size() == 1);
        if (yes) this.reason = GoodsConfirmReason.JYCK_NOT_ENOUGH;
        return yes;
    }

    public boolean prepareByOnePco() {
        boolean yes = (this.pcoMap.size() == 1);
        if (yes) this.reason = GoodsConfirmReason.PCO_NOT_ENOUGH;
        return yes;
    }


    public String getDBKey() {
        for (String doId : this.dbMap.keySet()) {
            return doId;
        }
        return null;
    }

    public OpsDo getDB() {
        return this.dbMap.get(this.getDBKey()).get(0).getOpsDo();
    }

    public String getDoKey() {
        for (String doId : this.doMap.keySet()) {
            return doId;
        }
        return null;
    }

    public OpsDo getDo() {
        return this.doMap.get(this.getDoKey()).get(0).getOpsDo();
    }

    public String getPcoKey() {
        for (String doId : this.pcoMap.keySet()) {
            return doId;
        }
        return null;
    }

    public OpsPcoItemDto getPcoDto() {
        return this.pcoMap.get(this.getPcoKey()).get(0);
    }

    public OpsPcoItem getPco() {
        return this.pcoMap.get(this.getPcoKey()).get(0).getPcoItem();
    }

}
