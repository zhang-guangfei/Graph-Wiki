package com.sales.ops.serviceimpl.order.result;

import com.sales.ops.db.entity.OpsInventory;
import com.sales.ops.db.entity.OpsInventoryMove;
import com.sales.ops.db.entity.OpsOrderAssignResult;
import com.sales.ops.dto.util.OrderNoInfo;
import com.sales.ops.enums.OrderStockTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.lang.StringUtils;

import java.util.Date;

@Data
@AllArgsConstructor
public class NewResult {

    private String orderId;
    private Integer orderItem;
    private String stockType;//N,T,P,T1,T3,W
    private OrderNoInfo associateNo;
    private String supplierId;//采购、补库填供应商，调拨不填

    private String warehouseCode;
    private String modelno;
    private String inventoryTypeCode;
    private Integer qty;


    public static NewResult create(String orderId, String orderItem, int qty, OpsInventoryMove inventory, String inventoryTypeCode) {
        String stockType = inventory.getInventoryId() == null ? OrderStockTypeEnum.CG.getType() : inventory.getInventoryStatus();
        return new NewResult(orderId, Integer.valueOf(orderItem), stockType, OrderNoInfo.getFromMove(inventory), inventory.getSupplierid(), inventory.getWarehouseCode(),
                inventory.getModelno(), inventoryTypeCode, qty);
    }


    public static NewResult create(String orderId, String orderItem, int qty, OpsInventory inventory, String inventoryTypeCode) {
        return new NewResult(orderId, Integer.valueOf(orderItem), OrderStockTypeEnum.ZK.getType(), new OrderNoInfo(), null, inventory.getWarehouseCode(),
                inventory.getModelno(), inventoryTypeCode, qty);
    }

    public OpsOrderAssignResult toOps() {
        OpsOrderAssignResult result = new OpsOrderAssignResult();
        result.setOrderNo(this.orderId);
        result.setOrderItem(this.orderItem);
        result.setSeqno(0);
        result.setModelno(this.modelno);
        result.setQuantity(this.qty);
        result.setStockType(this.stockType);
        if (StringUtils.equals(this.stockType, OrderStockTypeEnum.CG.getType())) {
            result.setStockCode(this.supplierId);
        } else {
            result.setStockCode(this.warehouseCode);
        }
        result.setInventoryTypeCode(this.inventoryTypeCode);
        result.setAssociateNo(associateNo.getOrderNo());
        result.setAssociateNoItem(associateNo.getItemNo());
        result.setAssociateNoSplitNo(associateNo.getSplitNoNotNull());
        result.setSupplierid(supplierId);
        result.setDelflag(0);
        result.setSourceType(2);
        result.setCreateTime(new Date());
        result.setCreateUser("转订");
        result.setUpdateTime(new Date());
        result.setUpdateUser("转订");
        return result;
    }
}
