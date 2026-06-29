package com.sales.ops.dto.product;

import java.io.Serializable;

/**
 * @author ：C14717
 * @version: $
 * @description：
 * @date ：Created in 2026/4/28 11:41
 */
public class BomDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long bomid;

    private String modelno;

    private Boolean priorityComplete;

    private Integer priorityLevel;

    private Boolean isvalid;

    private String subModelNo;

    private Integer subQty;

    private Boolean subClassify;

    public Long getBomid() {
        return bomid;
    }

    public void setBomid(Long bomid) {
        this.bomid = bomid;
    }

    public String getModelno() {
        return modelno;
    }

    public void setModelno(String modelno) {
        this.modelno = modelno;
    }

    public Boolean getPriorityComplete() {
        return priorityComplete;
    }

    public void setPriorityComplete(Boolean priorityComplete) {
        this.priorityComplete = priorityComplete;
    }

    public Integer getPriorityLevel() {
        return priorityLevel;
    }

    public void setPriorityLevel(Integer priorityLevel) {
        this.priorityLevel = priorityLevel;
    }

    public Boolean getIsvalid() {
        return isvalid;
    }

    public void setIsvalid(Boolean isvalid) {
        this.isvalid = isvalid;
    }

    public String getSubModelNo() {
        return subModelNo;
    }

    public void setSubModelNo(String subModelNo) {
        this.subModelNo = subModelNo;
    }

    public Integer getSubQty() {
        return subQty;
    }

    public void setSubQty(Integer subQty) {
        this.subQty = subQty;
    }

    public Boolean getSubClassify() {
        return subClassify;
    }

    public void setSubClassify(Boolean subClassify) {
        this.subClassify = subClassify;
    }
}
