package com.acme.invoice.service.impl;

import com.acme.invoice.dto.InvoiceRequest;
import com.acme.invoice.mapper.InvoiceMapper;

public class InvoiceServiceImpl {
    private final InvoiceMapper invoiceMapper = new InvoiceMapper();

    public void createInvoice(InvoiceRequest request) {
        invoiceMapper.saveInvoice(request.invoiceNo);
    }

    public void submitInvoice(String invoiceNo) {
        invoiceMapper.updateInvoiceStatus(invoiceNo, "SUBMITTED");
    }

    public void queryInvoices() {
        invoiceMapper.findInvoices();
    }
}
