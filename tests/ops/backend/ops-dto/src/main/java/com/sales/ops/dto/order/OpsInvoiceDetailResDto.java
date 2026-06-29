package com.sales.ops.dto.order;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
public class OpsInvoiceDetailResDto {
    // 订单号
    private String rorderno;
    // 单条订单数量
    private Integer quantity;
    // 型号
    private String modelno;
    // 开票日期
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date invdate;
    // 发票号
    private String invoiceno;

    public String getRorderno() {
        return rorderno;
    }

    public void setRorderno(String rorderno) {
        this.rorderno = rorderno;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getModelno() {
        return modelno;
    }

    public void setModelno(String modelno) {
        this.modelno = modelno;
    }

    public Date getInvdate() {
        return invdate;
    }

    public void setInvdate(Date invdate) {
        this.invdate = invdate;
    }

    public String getInvoiceno() {
        return invoiceno;
    }

    public void setInvoiceno(String invoiceno) {
        this.invoiceno = invoiceno;
    }
}
