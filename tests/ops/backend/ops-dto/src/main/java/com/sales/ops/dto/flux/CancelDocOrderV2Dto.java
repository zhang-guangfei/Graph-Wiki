package com.sales.ops.dto.flux;

import java.io.Serializable;

/**
 * @author ：C14717
 * @version: 2.0$ bugid:12714
 * @description：4.7 销售/发运订单/加工单/盘点差异调账取消 拣货后取消
 * @date ：Created in 2023/11/27 14:49
 */
public class CancelDocOrderV2Dto implements Serializable {

    private static final long serialVersionUID = -1027191748485325895L;
    private String organizationId;
    private String customerId;
    private String warehouseId;
    private String docNo;
    private String relatedPcoId;

    private String erpCancelReason;

    public CancelDocOrderV2Dto(){};

    public CancelDocOrderV2Dto(String warehouseId,String docNo,String erpCancelReason){
        this.organizationId = "SMC";
        this.customerId = "SMC";
        this.warehouseId = warehouseId;
        this.docNo = docNo;
        this.erpCancelReason = erpCancelReason;
    };

    public String getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(String warehouseId) {
        this.warehouseId = warehouseId;
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

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    public String getRelatedPcoId() {
        return relatedPcoId;
    }

    public void setRelatedPcoId(String relatedPcoId) {
        this.relatedPcoId = relatedPcoId;
    }

    public String getErpCancelReason() {
        return erpCancelReason;
    }

    public void setErpCancelReason(String erpCancelReason) {
        this.erpCancelReason = erpCancelReason;
    }

    @Override
    public String toString() {
        return "CancelDocOrderDto{" +
                ", warehouseId='" + warehouseId + '\'' +
                ", customerId='" + customerId + '\'' +
                ", docNo='" + docNo + '\'' +
                ", relatedPcoId='" + relatedPcoId + '\'' +
                '}';
    }
}
