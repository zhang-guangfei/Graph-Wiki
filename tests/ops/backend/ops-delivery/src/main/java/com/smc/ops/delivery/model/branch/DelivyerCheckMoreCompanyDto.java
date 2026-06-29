package com.smc.ops.delivery.model.branch;

import java.io.Serializable;

/**
 * @author ：C14717
 * @version: $
 * @description：
 * @date ：Created in 2025/2/5 10:52
 */
public class DelivyerCheckMoreCompanyDto implements Serializable {
    private static final long serialVersionUID = -3628158143666335999L;

    private Integer detailQty;

    private Integer detailBranchFlag;

    private Integer propertyQty;

    private Integer propertyDelFlag;

    private String propertyCompanyId;

    public Integer getDetailQty() {
        return detailQty;
    }

    public void setDetailQty(Integer detailQty) {
        this.detailQty = detailQty;
    }

    public Integer getDetailBranchFlag() {
        return detailBranchFlag;
    }

    public void setDetailBranchFlag(Integer detailBranchFlag) {
        this.detailBranchFlag = detailBranchFlag;
    }

    public Integer getPropertyQty() {
        return propertyQty;
    }

    public void setPropertyQty(Integer propertyQty) {
        this.propertyQty = propertyQty;
    }

    public Integer getPropertyDelFlag() {
        return propertyDelFlag;
    }

    public void setPropertyDelFlag(Integer propertyDelFlag) {
        this.propertyDelFlag = propertyDelFlag;
    }

    public String getPropertyCompanyId() {
        return propertyCompanyId;
    }

    public void setPropertyCompanyId(String propertyCompanyId) {
        this.propertyCompanyId = propertyCompanyId;
    }
}
