package com.smc.ops.delivery.model.branch;

import java.io.Serializable;

/**
 * @author ：C14717
 * @version: $
 * @description：
 * @date ：Created in 2025/1/3 14:36
 */
public class StockAssemblyDetailViewDo implements Serializable {
    private static final long serialVersionUID = -1305353289139557411L;

    private Long applyId;

    private Long detailId;

    private String applyNo;

    private Integer isTransOut;

    private Integer quantity;

    private String modelNo;

    private String warehouseCode;

    private Integer assembleType;

    public String getModelNo() {
        return modelNo;
    }

    public void setModelNo(String modelNo) {
        this.modelNo = modelNo;
    }

    public Long getApplyId() {
        return applyId;
    }

    public void setApplyId(Long applyId) {
        this.applyId = applyId;
    }

    public Long getDetailId() {
        return detailId;
    }

    public void setDetailId(Long detailId) {
        this.detailId = detailId;
    }

    public String getApplyNo() {
        return applyNo;
    }

    public void setApplyNo(String applyNo) {
        this.applyNo = applyNo;
    }

    public Integer getIsTransOut() {
        return isTransOut;
    }

    public void setIsTransOut(Integer isTransOut) {
        this.isTransOut = isTransOut;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getAssembleType() {
        return assembleType;
    }

    public void setAssembleType(Integer assembleType) {
        this.assembleType = assembleType;
    }

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
    }
}
