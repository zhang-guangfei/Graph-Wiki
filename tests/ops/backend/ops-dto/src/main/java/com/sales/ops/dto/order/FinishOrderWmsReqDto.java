package com.sales.ops.dto.order;

import java.io.Serializable;

/**
 * @author ：C14717
 * @version: $
 * @description：
 * @date ：Created in 2023/8/14 10:22
 */
public class FinishOrderWmsReqDto implements Serializable {

    private static final long serialVersionUID = 8166394936338465593L;
    private String customerId;
    private String docNo;

    private String orderType;

    private String warehouseId;

    private Integer finishQty;

    private String erpCancelReason;

    private String organizationId;

    public FinishOrderWmsReqDto(String docNo,String warehouseId,Integer finishQty,String erpCancelReason){
        this.customerId = "SMC";
        this.organizationId = "SMC";
        this.docNo = docNo;
        this.orderType = "CM";
        this.warehouseId = warehouseId;
        this.finishQty = finishQty;
        this.erpCancelReason = erpCancelReason;

    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getDocNo() {
        return docNo;
    }

    public void setDocNo(String docNo) {
        this.docNo = docNo;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(String warehouseId) {
        this.warehouseId = warehouseId;
    }

    public Integer getFinishQty() {
        return finishQty;
    }

    public void setFinishQty(Integer finishQty) {
        this.finishQty = finishQty;
    }

    public String getErpCancelReason() {
        return erpCancelReason;
    }

    public void setErpCancelReason(String erpCancelReason) {
        this.erpCancelReason = erpCancelReason;
    }

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    @Override
    public String toString() {
        return "FinishOrderWmsReqDto{" +
                "docNo='" + docNo + '\'' +
                ", finishQty=" + finishQty +
                '}';
    }
}
