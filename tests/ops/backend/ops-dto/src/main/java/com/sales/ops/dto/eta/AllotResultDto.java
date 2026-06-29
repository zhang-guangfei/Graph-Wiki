package com.sales.ops.dto.eta;

import com.sales.ops.db.entity.OpsInventory;
import com.sales.ops.db.entity.OpsInventoryMove;
import com.sales.ops.db.entity.OpsInventoryProperty;
import com.sales.ops.enums.InventoryStatusEnum;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import java.util.TimeZone;

/**
 * @author ：C14717
 * @version: $
 * @description：
 * @date ：Created in 2023/11/9 16:11
 */
public class AllotResultDto implements Serializable {
    private static final long serialVersionUID = -2879303891169207795L;

    private Integer seqno;

    private String modelno;

    private Integer quantity;

    private String stockType;

    private String stockCode;

    private String inventoryTypeCode;

    private String associateNo;

    private Integer associateNoItem;

    private Integer associateNoSplitNo;

    private String supplierid;

    private Long invId;

    private String gatherWarehouseCode;

    private Integer deliveryDay = 0;

    private Integer DBDeliveryDay = 0;

    private Integer moveTransportDay = 0;

    private List<CGTransportDayDto> CGDay;

    private String remark;


    public AllotResultDto(){};

    public AllotResultDto(String gatherWarehouseCode, Integer splitNo, String modelNo, Integer qty){
        this.stockCode = gatherWarehouseCode;
        this.seqno = splitNo;
        this.modelno = modelNo;
        this.stockType = "CG";
        this.quantity = qty;
        this.gatherWarehouseCode = gatherWarehouseCode;
    };

    public AllotResultDto(OpsInventory opsInventory , OpsInventoryMove opsInvMove, Integer useqty, Integer splitNo, String gatherWarehouseCode, OpsInventoryProperty property){
        if(Objects.nonNull(opsInventory)){
            this.supplierid = gatherWarehouseCode;
            this.seqno = splitNo;
            this.stockType = opsInventory.getInventoryStatus();
            this.stockCode = opsInventory.getWarehouseCode();
            this.setInvId(opsInventory.getInventoryId());
            this.inventoryTypeCode = "TY";
            if(Objects.nonNull(property)){
                this.inventoryTypeCode = property.getInventoryTypeCode();
            }
            this.modelno = opsInventory.getModelno();
            this.quantity = useqty;
            this.gatherWarehouseCode = gatherWarehouseCode;
        }
        if(Objects.nonNull(opsInvMove)){
            if (Objects.nonNull(opsInvMove.getAssociateNo())) {
                this.associateNo = opsInvMove.getAssociateNo();
            }
            if (Objects.nonNull(opsInvMove.getAssociateNoItem())) {
                this.associateNoItem = opsInvMove.getAssociateNoItem();
            }
            if (Objects.nonNull(opsInvMove.getAssociateNoSplitno())) {
                this.associateNoSplitNo = opsInvMove.getAssociateNoSplitno();
            } else {
                this.associateNoSplitNo = 0;
            }
            this.supplierid = gatherWarehouseCode;
            this.seqno = splitNo;
            this.stockType = opsInvMove.getInventoryStatus();
            this.stockCode = opsInvMove.getWarehouseCode();
            this.setInvId(opsInvMove.getInventoryId());
            this.inventoryTypeCode = "TY";
            if(Objects.nonNull(property)){
                this.inventoryTypeCode = property.getInventoryTypeCode();
            }
            this.modelno = opsInvMove.getModelno();
            this.quantity = useqty;
            this.gatherWarehouseCode = gatherWarehouseCode;
            //todo 计算move表天数 p t1 w
            if(InventoryStatusEnum.PRODUCE.getCode().equals(opsInvMove.getInventoryStatus())){
                this.moveTransportDay = 15;
            }
            //todo 如何计算T1 默认值 大于预到货日期 小于预到货日期
            if(InventoryStatusEnum.CGTRANS.getCode().equals(opsInvMove.getInventoryStatus())){
                this.moveTransportDay = 10;
                if(Objects.nonNull(opsInvMove.getPrereceivedate())){
                    Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
                    Long day = ((opsInvMove.getPrereceivedate().getTime() - calendar.getTime().getTime())/1000*60*60*24);
                    if(day>0){
                        this.moveTransportDay = day.intValue();
                    }else{
                        this.moveTransportDay = 1;
                    }
                }
            }
            if(InventoryStatusEnum.W.getCode().equals(opsInvMove.getInventoryStatus())){
                this.moveTransportDay = 1;
            }

        }
    }


    public Integer getSeqno() {
        return seqno;
    }

    public void setSeqno(Integer seqno) {
        this.seqno = seqno;
    }

    public String getModelno() {
        return modelno;
    }

    public void setModelno(String modelno) {
        this.modelno = modelno;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getStockType() {
        return stockType;
    }

    public void setStockType(String stockType) {
        this.stockType = stockType;
    }

    public String getStockCode() {
        return stockCode;
    }

    public void setStockCode(String stockCode) {
        this.stockCode = stockCode;
    }

    public String getInventoryTypeCode() {
        return inventoryTypeCode;
    }

    public void setInventoryTypeCode(String inventoryTypeCode) {
        this.inventoryTypeCode = inventoryTypeCode;
    }

    public String getAssociateNo() {
        return associateNo;
    }

    public void setAssociateNo(String associateNo) {
        this.associateNo = associateNo;
    }

    public Integer getAssociateNoItem() {
        return associateNoItem;
    }

    public void setAssociateNoItem(Integer associateNoItem) {
        this.associateNoItem = associateNoItem;
    }

    public Integer getAssociateNoSplitNo() {
        return associateNoSplitNo;
    }

    public void setAssociateNoSplitNo(Integer associateNoSplitNo) {
        this.associateNoSplitNo = associateNoSplitNo;
    }

    public String getSupplierid() {
        return supplierid;
    }

    public void setSupplierid(String supplierid) {
        this.supplierid = supplierid;
    }

    public Long getInvId() {
        return invId;
    }

    public void setInvId(Long invId) {
        this.invId = invId;
    }

    public String getGatherWarehouseCode() {
        return gatherWarehouseCode;
    }

    public void setGatherWarehouseCode(String gatherWarehouseCode) {
        this.gatherWarehouseCode = gatherWarehouseCode;
    }

    public Integer getDeliveryDay() {
        return deliveryDay;
    }

    public void setDeliveryDay(Integer deliveryDay) {
        this.deliveryDay = deliveryDay;
    }

    public Integer getDBDeliveryDay() {
        return DBDeliveryDay;
    }

    public void setDBDeliveryDay(Integer DBDeliveryDay) {
        this.DBDeliveryDay = DBDeliveryDay;
    }

    public List<CGTransportDayDto> getCGDay() {
        return CGDay;
    }

    public void setCGDay(List<CGTransportDayDto> CGDay) {
        this.CGDay = CGDay;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getMoveTransportDay() {
        return moveTransportDay;
    }

    public void setMoveTransportDay(Integer moveTransportDay) {
        this.moveTransportDay = moveTransportDay;
    }
}
