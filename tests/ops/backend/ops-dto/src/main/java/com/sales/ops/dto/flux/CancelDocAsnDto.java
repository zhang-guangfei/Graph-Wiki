package com.sales.ops.dto.flux;

import java.io.Serializable;

/**
 * @author ：C14717
 * @version: 1.0$
 * @description：4.5.	预期到货通知（采购入库/仓间调拨/退货入库/盘点差异调账）取消(整单)
 * @date ：Created in 2021/12/17 14:49
 */
public class CancelDocAsnDto implements Serializable {
    private static final long serialVersionUID = 8143407728155259695L;

    private String warehouseId;
    private String customerId;
    private String docNo;
    private String asnType;
    private String erpCancelReason; //取消原因
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

    public String getAsnType() {
        return asnType;
    }

    public void setAsnType(String asnType) {
        this.asnType = asnType;
    }

    public String getDocNo() {
        return docNo;
    }

    public void setDocNo(String docNo) {
        this.docNo = docNo;
    }

    public String getErpCancelReason() {
        return erpCancelReason;
    }

    public void setErpCancelReason(String erpCancelReason) {
        this.erpCancelReason = erpCancelReason;
    }
}
