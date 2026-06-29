package com.sales.ops.dto.expdetail;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @author ：C14717
 * @version: 转运发货实体$
 * @description：
 * @date ：Created in 2025/11/27 10:08
 */
public class TransferVO implements Serializable {

    // 原始发票号
    private String originalInvoiceNo;

    // 最终收货仓（目的仓）
    private String endReceiveWarehouse;

    // 操作时间
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date operationDate;

    // 签收状态 1 签收 2 发货
    private Integer status;

    // 签收仓
    private String signWarehouse;

    // 操作人
    private String updator;

    // 承运商
    private String carried;

    // 运单号 母单
    private String expressCode;

    public String getOriginalInvoiceNo() {
        return originalInvoiceNo;
    }

    public void setOriginalInvoiceNo(String originalInvoiceNo) {
        this.originalInvoiceNo = originalInvoiceNo;
    }

    public String getEndReceiveWarehouse() {
        return endReceiveWarehouse;
    }

    public void setEndReceiveWarehouse(String endReceiveWarehouse) {
        this.endReceiveWarehouse = endReceiveWarehouse;
    }

    public Date getOperationDate() {
        return operationDate;
    }

    public void setOperationDate(Date operationDate) {
        this.operationDate = operationDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getSignWarehouse() {
        return signWarehouse;
    }

    public void setSignWarehouse(String signWarehouse) {
        this.signWarehouse = signWarehouse;
    }

    public String getUpdator() {
        return updator;
    }

    public void setUpdator(String updator) {
        this.updator = updator;
    }

    public String getCarried() {
        return carried;
    }

    public void setCarried(String carried) {
        this.carried = carried;
    }

    public String getExpressCode() {
        return expressCode;
    }

    public void setExpressCode(String expressCode) {
        this.expressCode = expressCode;
    }
}
