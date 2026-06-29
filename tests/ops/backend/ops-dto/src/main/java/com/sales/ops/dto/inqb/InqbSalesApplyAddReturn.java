package com.sales.ops.dto.inqb;

import java.io.Serializable;

/**
 * 门户接入的，inqB催促申请dto
 * @author B91717
 */
public class InqbSalesApplyAddReturn extends OpsInqbInfo implements Serializable {

    private String sourcesApplyNo; // 门户端申请单号
    private String batchNo; //批次号

    // 门户新增时，返回的催促状态
    private String status;
    // 门户新增时,催促状态描述
    private String statusDescription;

    // 申请状态解释
    private String inqbStatusDesc;

    // 回传给门户子项的拆分状态
    private String splitType;
    // INQB整单的有效性状态,中文描述
    private String inqbValidityDesc;

    public String getInqbValidityDesc() {
        return inqbValidityDesc;
    }

    public void setInqbValidityDesc(String inqbValidityDesc) {
        this.inqbValidityDesc = inqbValidityDesc;
    }

    public String getSourcesApplyNo() {
        return sourcesApplyNo;
    }

    public void setSourcesApplyNo(String sourcesApplyNo) {
        this.sourcesApplyNo = sourcesApplyNo;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getSplitType() {
        return splitType;
    }

    public void setSplitType(String splitType) {
        this.splitType = splitType;
    }

    public String getInqbStatusDesc() {
        return inqbStatusDesc;
    }

    public void setInqbStatusDesc(String inqbStatusDesc) {
        this.inqbStatusDesc = inqbStatusDesc;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusDescription() {
        return statusDescription;
    }

    public void setStatusDescription(String statusDescription) {
        this.statusDescription = statusDescription;
    }
}
