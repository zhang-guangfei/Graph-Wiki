package com.sales.ops.dto.po.core;

/**
 * @author ：C14717
 * @version: $
 * @description：
 * @date ：Created in 2025/12/15 13:24
 */
public class PoTransTypeCoreDto {
    private String modelNo;           // model_no
    private Boolean matchType;         // match_type
    private String transId;           // trans_id
    private Boolean compareGreater;   // compare_greater
    private Integer compareQuantity;  // compare_quantity
    private Integer usageType;         // usage_type
    private String remark;            // remark

    public String getModelNo() {
        return modelNo;
    }

    public void setModelNo(String modelNo) {
        this.modelNo = modelNo;
    }


    public String getTransId() {
        return transId;
    }

    public void setTransId(String transId) {
        this.transId = transId;
    }

    public Boolean getCompareGreater() {
        return compareGreater;
    }

    public void setCompareGreater(Boolean compareGreater) {
        this.compareGreater = compareGreater;
    }

    public Integer getCompareQuantity() {
        return compareQuantity;
    }

    public void setCompareQuantity(Integer compareQuantity) {
        this.compareQuantity = compareQuantity;
    }

    public Boolean getMatchType() {
        return matchType;
    }

    public void setMatchType(Boolean matchType) {
        this.matchType = matchType;
    }

    public Integer getUsageType() {
        return usageType;
    }

    public void setUsageType(Integer usageType) {
        this.usageType = usageType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
