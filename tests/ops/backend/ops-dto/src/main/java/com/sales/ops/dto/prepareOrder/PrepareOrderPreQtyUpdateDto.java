package com.sales.ops.dto.prepareOrder;

public class PrepareOrderPreQtyUpdateDto {

    private String orderFno;

    private int preQty;

    private int oldPreQty;

    public String getOrderFno() {
        return orderFno;
    }

    public void setOrderFno(String orderFno) {
        this.orderFno = orderFno;
    }

    public int getPreQty() {
        return preQty;
    }

    public void setPreQty(int preQty) {
        this.preQty = preQty;
    }

    public int getOldPreQty() {
        return oldPreQty;
    }

    public void setOldPreQty(int oldPreQty) {
        this.oldPreQty = oldPreQty;
    }
}
