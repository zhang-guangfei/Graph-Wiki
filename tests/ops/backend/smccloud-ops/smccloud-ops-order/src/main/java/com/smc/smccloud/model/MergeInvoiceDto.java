package com.smc.smccloud.model;

import java.io.Serializable;

/**
 * @author ：C14717
 * @version: $
 * @description：
 * @date ：Created in 2025/12/1 8:45
 */
public class MergeInvoiceDto implements Serializable {

    private String originalInvoiceNo;

    private Long mergeInvoiceId;

    private Long splitInvoiceId;

    private String splitInvoiceNo;

    private Integer totalQty;

    public String getOriginalInvoiceNo() {
        return originalInvoiceNo;
    }

    public void setOriginalInvoiceNo(String originalInvoiceNo) {
        this.originalInvoiceNo = originalInvoiceNo;
    }

    public Long getMergeInvoiceId() {
        return mergeInvoiceId;
    }

    public void setMergeInvoiceId(Long mergeInvoiceId) {
        this.mergeInvoiceId = mergeInvoiceId;
    }

    public Long getSplitInvoiceId() {
        return splitInvoiceId;
    }

    public void setSplitInvoiceId(Long splitInvoiceId) {
        this.splitInvoiceId = splitInvoiceId;
    }

    public String getSplitInvoiceNo() {
        return splitInvoiceNo;
    }

    public void setSplitInvoiceNo(String splitInvoiceNo) {
        this.splitInvoiceNo = splitInvoiceNo;
    }

    public Integer getTotalQty() {
        return totalQty;
    }

    public void setTotalQty(Integer totalQty) {
        this.totalQty = totalQty;
    }
}
