package com.sales.ops.dto.order;

/**
 * @author C12961
 * @date 2022/4/18 9:31
 */
public class OrderInventoryQueryDto {

    private String orderId;
    private String orderItem;
    private Integer splitNo;
    private Integer sortNo;
    private Boolean delDo;
    private String orderFullNo;
    private String modelno;

    private String poNo;
    private Integer poItemNo;
    private Integer poSplitNo;

    public OrderInventoryQueryDto() {
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderItem() {
        return orderItem;
    }

    public void setOrderItem(String orderItem) {
        this.orderItem = orderItem;
    }

    public Integer getSplitNo() {
        return splitNo;
    }

    public void setSplitNo(Integer splitNo) {
        this.splitNo = splitNo;
    }

    public Integer getSortNo() {
        return sortNo;
    }

    public void setSortNo(Integer sortNo) {
        this.sortNo = sortNo;
    }

    public Boolean getDelDo() {
        return delDo;
    }

    public void setDelDo(Boolean delDo) {
        this.delDo = delDo;
    }

    public String getOrderFullNo() {
        return orderFullNo;
    }

    public void setOrderFullNo(String orderFullNo) {
        this.orderFullNo = orderFullNo;
    }

    public String getModelno() {
        return modelno;
    }

    public void setModelno(String modelno) {
        this.modelno = modelno;
    }

    public String getPoNo() {
        return poNo;
    }

    public void setPoNo(String poNo) {
        this.poNo = poNo;
    }

    public Integer getPoItemNo() {
        return poItemNo;
    }

    public void setPoItemNo(Integer poItemNo) {
        this.poItemNo = poItemNo;
    }

    public Integer getPoSplitNo() {
        return poSplitNo;
    }

    public void setPoSplitNo(Integer poSplitNo) {
        this.poSplitNo = poSplitNo;
    }
}
