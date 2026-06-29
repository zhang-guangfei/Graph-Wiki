package com.sales.ops.dto.order;

/**
 * @author C12961
 * @date 2022/7/3 11:05
 */
public class OrderStatusParamsDto {

    private String orderno;
    private String itemno;
    private Integer Quantity;
    private String OptDate;
    private Integer invoiceQty;

    public String getOrderno() {
        return orderno;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno;
    }

    public String getItemno() {
        return itemno;
    }

    public void setItemno(String itemno) {
        this.itemno = itemno;
    }

    public Integer getQuantity() {
        return Quantity;
    }

    public void setQuantity(Integer quantity) {
        Quantity = quantity;
    }

    public String getOptDate() {
        return OptDate;
    }

    public void setOptDate(String optDate) {
        OptDate = optDate;
    }

    public Integer getInvoiceQty() {
        return invoiceQty;
    }

    public void setInvoiceQty(Integer invoiceQty) {
        this.invoiceQty = invoiceQty;
    }
}
