package com.sales.ops.dto.order;

import java.util.List;

public class OpsInvoiceResDto {
    // 订单号
    private String rorderno;
    // 总开票数
    private Integer invoiceSumQty;
    // 发票详情
    private List<OpsInvoiceDetailResDto> invoiceDetail;

    public String getRorderno() {
        return rorderno;
    }

    public void setRorderno(String rorderno) {
        this.rorderno = rorderno;
    }

    public Integer getInvoiceSumQty() {
        return invoiceSumQty;
    }

    public void setInvoiceSumQty(Integer invoiceSumQty) {
        this.invoiceSumQty = invoiceSumQty;
    }

    public List<OpsInvoiceDetailResDto> getInvoiceDetail() {
        return invoiceDetail;
    }

    public void setInvoiceDetail(List<OpsInvoiceDetailResDto> invoiceDetail) {
        this.invoiceDetail = invoiceDetail;
    }
}
