package com.smc.smccloud.model.stockassembly;

import java.io.Serializable;

/**
 * @author ：C14717
 * @version: $
 * @description：
 * @date ：Created in 2025/1/6 8:59
 */
public class StockAssemblyDetailProperty implements Serializable {
    private static final long serialVersionUID = -1138178401273840343L;

    private String companyid;

    private Integer quantity;

    private Integer propertyQuantity;

    public String getCompanyid() {
        return companyid;
    }

    public void setCompanyid(String companyid) {
        this.companyid = companyid;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getPropertyQuantity() {
        return propertyQuantity;
    }

    public void setPropertyQuantity(Integer propertyQuantity) {
        this.propertyQuantity = propertyQuantity;
    }
}
