package com.acme.invoice.controller;

import com.acme.invoice.dto.InvoiceRequest;
import com.acme.invoice.service.impl.InvoiceServiceImpl;

public class InvoiceController {
    private final InvoiceServiceImpl invoiceService = new InvoiceServiceImpl();

    public void createInvoice(InvoiceRequest request) {
        invoiceService.createInvoice(request);
    }

    public void submitInvoice(String invoiceNo) {
        invoiceService.submitInvoice(invoiceNo);
    }

    public void queryInvoices() {
        invoiceService.queryInvoices();
    }
}
