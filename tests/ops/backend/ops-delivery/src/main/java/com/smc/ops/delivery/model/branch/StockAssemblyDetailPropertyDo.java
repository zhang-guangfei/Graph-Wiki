package com.smc.ops.delivery.model.branch;

import java.io.Serializable;

/**
 * @author ：C14717
 * @version: $
 * @description：
 * @date ：Created in 2024/12/29 10:48
 */
public class StockAssemblyDetailPropertyDo implements Serializable {

    private static final long serialVersionUID = 7679713177445697004L;
    private Long detailId;

    private String modelNo;

    private String companyId;

    private Integer quantity;

    private Integer propertyQuantity;

    public StockAssemblyDetailPropertyDo(){}

    public StockAssemblyDetailPropertyDo(Long detailId, String modelNo, String companyId, Integer quantity, Integer propertyQuantity){
        this.detailId = detailId;
        this.modelNo = modelNo;
        this.quantity = quantity;
        this.propertyQuantity = propertyQuantity;
        this.companyId = companyId;
    }

    public Long getDetailId() {
        return detailId;
    }

    public void setDetailId(Long detailId) {
        this.detailId = detailId;
    }

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
