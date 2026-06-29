package com.sales.ops.dto.po.core;

import com.sales.ops.db.entity.OpsRequestpurchase;

import java.util.Date;

/**
 * @author ：C14717
 * @version: $
 * @description：
 * @date ：Created in 2025/12/16 9:12
 */
public class TransAndExportDateParamDto {

   private String supplierId; // 供应商 必传
   private String modelNo; // 型号 必传
   private Integer orderQty; // 订单数量 必传
   private String ordType; // 订单类型 必传

   private String preTransId; // 先行在库必传 先行在库时必传
   private Boolean recoredAir = false; // 备案空运标识

   private Date hopeDeliveryDate; // 客户希望货期 必传
   private Date TestDate; // 订货日期

   private String purchasetype; // 采购类型 A/B/K
   private String shikomianswerno; // shikomianswerNo 非必填
   private String deptNo; // 营业所代码必填
   private String pWarehouse; // 采购仓库 必填
   private String rWarehouse; // 请购仓库 必填

    public TransAndExportDateParamDto(){};

    public TransAndExportDateParamDto(OpsRequestpurchase item){
        this.supplierId = item.getSupplierid();
        this.modelNo = item.getModelno();
        this.orderQty = item.getQuantity();
        this.ordType = item.getOrdtype();
        this.hopeDeliveryDate = item.getHopedeliverydate();
        this.purchasetype = item.getPurchasetype();
        this.shikomianswerno = item.getShikomianswerno();
        this.deptNo = item.getDeptno();
        this.pWarehouse = item.getPurchasewarehouseid();
        this.rWarehouse = item.getRequestwarehouseid();
    };

    private String calTransId; // 非必填
    private Date calDate;// 非必填

    public Date getTestDate() {
        return TestDate;
    }

    public void setTestDate(Date testDate) {
        TestDate = testDate;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }


    public String getModelNo() {
        return modelNo;
    }

    public void setModelNo(String modelNo) {
        this.modelNo = modelNo;
    }

    public Integer getOrderQty() {
        return orderQty;
    }

    public void setOrderQty(Integer orderQty) {
        this.orderQty = orderQty;
    }

    public String getOrdType() {
        return ordType;
    }

    public void setOrdType(String ordType) {
        this.ordType = ordType;
    }

    public String getPreTransId() {
        return preTransId;
    }

    public void setPreTransId(String preTransId) {
        this.preTransId = preTransId;
    }

    public Boolean getRecoredAir() {
        return recoredAir;
    }

    public void setRecoredAir(Boolean recoredAir) {
        this.recoredAir = recoredAir;
    }

    public Date getHopeDeliveryDate() {
        return hopeDeliveryDate;
    }

    public void setHopeDeliveryDate(Date hopeDeliveryDate) {
        this.hopeDeliveryDate = hopeDeliveryDate;
    }

    public String getPurchasetype() {
        return purchasetype;
    }

    public void setPurchasetype(String purchasetype) {
        this.purchasetype = purchasetype;
    }

    public String getShikomianswerno() {
        return shikomianswerno;
    }

    public void setShikomianswerno(String shikomianswerno) {
        this.shikomianswerno = shikomianswerno;
    }

    public String getDeptNo() {
        return deptNo;
    }

    public void setDeptNo(String deptNo) {
        this.deptNo = deptNo;
    }

    public String getpWarehouse() {
        return pWarehouse;
    }

    public void setpWarehouse(String pWarehouse) {
        this.pWarehouse = pWarehouse;
    }

    public String getrWarehouse() {
        return rWarehouse;
    }

    public void setrWarehouse(String rWarehouse) {
        this.rWarehouse = rWarehouse;
    }

    public String getCalTransId() {
        return calTransId;
    }

    public void setCalTransId(String calTransId) {
        this.calTransId = calTransId;
    }

    public Date getCalDate() {
        return calDate;
    }

    public void setCalDate(Date calDate) {
        this.calDate = calDate;
    }
}
