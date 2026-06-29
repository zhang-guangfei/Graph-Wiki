package com.smc.ops.delivery.model.branch;

import java.io.Serializable;

/**
 * @author ：C14717
 * @version: $
 * @description：
 * @date ：Created in 2024/12/29 10:27
 */
public class BranchSumDo implements Serializable {
    private static final long serialVersionUID = -4050759135887860600L;

    private String modelNo;

    private String companyId;

    private Integer overQty;

    public String getModelNo() {
        return modelNo;
    }

    public void setModelNo(String modelNo) {
        this.modelNo = modelNo;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public Integer getOverQty() {
        return overQty;
    }

    public void setOverQty(Integer overQty) {
        this.overQty = overQty;
    }
}
