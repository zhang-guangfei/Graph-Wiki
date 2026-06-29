package com.sales.ops.dto.prepareOrder;

public class PrepareOrderTransferDto {

    private String orderFno;

    private String poNo;

    private String dlvDate;

    private int qty;

    private String fromId;

    private String applyId;

    public String getFromId() {
        return fromId;
    }

    public String getPoNo() {
        return poNo;
    }

    public void setPoNo(String poNo) {
        this.poNo = poNo;
    }

    public void setFromId(String fromId) {
        this.fromId = fromId;
    }

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public String getOrderFno() {
        return orderFno;
    }

    public void setOrderFno(String orderFno) {
        this.orderFno = orderFno;
    }

    public String getDlvDate() {
        return dlvDate;
    }

    public void setDlvDate(String dlvDate) {
        this.dlvDate = dlvDate;
    }
}
