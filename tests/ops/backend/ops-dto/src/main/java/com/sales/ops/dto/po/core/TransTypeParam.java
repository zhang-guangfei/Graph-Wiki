package com.sales.ops.dto.po.core;

/**
 * @author ：C14717
 * @version: $
 * @description：
 * @date ：Created in 2025/12/18 12:57
 */
public class TransTypeParam {

    /**
     * 1.都可为空
     * 2. 如果传型号和供应商会判断型号选择器
     */
    private String supplierId; // 供应商
    private String modelNo; // 型号
    private Integer orderQty; // 订单数量
    private String ordType; // 订单类型
    private String warehouse; // 仓库
    private Boolean translation = false; //是否用于翻译

    public Boolean getTranslation() {
        return translation;
    }

    public void setTranslation(Boolean translation) {
        this.translation = translation;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public String getModelNo() {
        return modelNo;
    }

    public void setModelNo(String modelNo) {
        this.modelNo = modelNo;
    }

    public Integer getOrderQty() {
        return orderQty;
    }

    public void setOrderQty(Integer orderQty) {
        this.orderQty = orderQty;
    }

    public String getOrdType() {
        return ordType;
    }

    public void setOrdType(String ordType) {
        this.ordType = ordType;
    }

    public String getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(String warehouse) {
        this.warehouse = warehouse;
    }
}
