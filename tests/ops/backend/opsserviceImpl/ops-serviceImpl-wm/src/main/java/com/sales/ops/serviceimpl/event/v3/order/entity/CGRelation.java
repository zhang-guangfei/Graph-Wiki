package com.sales.ops.serviceimpl.event.v3.order.entity;


import com.sales.ops.db.entity.*;
import com.sales.ops.dto.util.OrderNoInfo;
import com.sales.ops.enums.InventoryStatusEnum;
import com.sales.ops.enums.InventoryTableTypeEnum;
import com.sales.ops.enums.InventoryTypeEnum;
import com.sales.ops.enums.OptStatusEnum;
import lombok.Data;

import java.util.Date;

@Data
public class CGRelation extends Relation {
    // 库存ID
    private Long inventoryId;
    // 请购单号
    private String requestNo;
    private Integer requestItemNo;
    private Integer requestSplitNo;
    // 采购单号
    private String poNo;
    private Integer poItemNo;
    private Integer poSplitNo;
    // 供应商
    private String supplierId;
    // 签收仓
    private String warehouseCode;
    // 库存属性
    private InventoryTypeEnum propertyCode;
    // 操作状态
    private OptStatusEnum optStatus;
    // 操作时间
    private Date optTime;
    // 发票号
    private String invoiceNo;
    // 发票ID
    private Long invoiceId;


    // 无关联关系的do采购（未生成在途库存）
    public CGRelation(OpsDoItem opsDoItem, String requestNo, Integer requestItemNo, Integer requestSplitNo) {
        super.wmOrderId = opsDoItem.getDoId();
        this.requestNo = requestNo;
        this.requestItemNo = requestItemNo;
        this.requestSplitNo = requestSplitNo;

        super.invTable = InventoryTableTypeEnum.TRANS;
        super.invId = null;
        super.useQty = opsDoItem.getQty();
        super.outQty = 0;
    }

    // 无关联关系的pco采购（未生成在途库存）
    public CGRelation(OpsPcoItem opsPcoItem, String requestNo, Integer requestItemNo, Integer requestSplitNo) {
        super.wmOrderId = opsPcoItem.getPcoId();
        this.requestNo = requestNo;
        this.requestItemNo = requestItemNo;
        this.requestSplitNo = requestSplitNo;

        super.invTable = InventoryTableTypeEnum.TRANS;
        super.invId = null;
        super.useQty = opsPcoItem.getSubQty();
        super.outQty = 0;
    }

    // 有关联关系的do采购
    public CGRelation(OpsDoItemInventory doItemInv, String requestNo, Integer requestItemNo, Integer requestSplitNo) {
        super.wmOrderId = doItemInv.getDoId();// 交易出库的DoId或调拨出的DoId
        this.requestNo = requestNo;
        this.requestItemNo = requestItemNo;
        this.requestSplitNo = requestSplitNo;

        super.invTable = InventoryTableTypeEnum.getEnumByType(doItemInv.getInventoryTableType());
        super.invId = doItemInv.getInventoryId();
        super.useQty = doItemInv.getUseQty();
        super.outQty = 0;
    }

    // 有关联关系的pco采购
    public CGRelation(OpsPcoItemInventory pcoInv, String requestNo, Integer requestItemNo, Integer requestSplitNo) {
        super.wmOrderId = pcoInv.getPcoId();// 交易出库的DoId或调拨出的DoId
        this.requestNo = requestNo;
        this.requestItemNo = requestItemNo;
        this.requestSplitNo = requestSplitNo;

        super.invTable = InventoryTableTypeEnum.getEnumByType(pcoInv.getInventoryTableType());
        super.invId = pcoInv.getInventoryId();
        super.useQty = pcoInv.getUseQty();
        super.outQty = 0;
    }

    public void fillInventoryInfo(OpsInventoryMove move, OpsInventoryProperty property) {
        this.inventoryId = null;
        super.invStatus = InventoryStatusEnum.REQUEST;
        if (move != null) {
            this.inventoryId = move.getInventoryId();
            super.invStatus = InventoryStatusEnum.getEnumByType(move.getInventoryStatus());
            this.supplierId = move.getSupplierid();
            this.warehouseCode = move.getSignWarehouseCode();
            this.propertyCode = InventoryTypeEnum.parse(property.getInventoryTypeCode());
            this.poNo = move.getAssociateNo();
            this.poItemNo = move.getAssociateNoItem();
            this.poSplitNo = move.getAssociateNoSplitno();
            this.invoiceNo = move.getInvoiceno();
            this.invoiceId = move.getInvoiceid();
            this.optStatus = OptStatusEnum.getByCode(move.getOptStatus());
            this.optTime = move.getOptTime();
        }
    }

    public String getPoFullNo() {
        OrderNoInfo orderNoInfo = new OrderNoInfo(poNo, poItemNo, poSplitNo);
        return orderNoInfo.getFullNo();
    }

    public String getRePoFullNo() {
        OrderNoInfo orderNoInfo = new OrderNoInfo(requestNo, requestItemNo, requestSplitNo);
        return orderNoInfo.getFullNo();
    }


    @Override
    public String getInventoryTypeCode() {
        return this.propertyCode != null ? propertyCode.getType() : null;
    }

}
