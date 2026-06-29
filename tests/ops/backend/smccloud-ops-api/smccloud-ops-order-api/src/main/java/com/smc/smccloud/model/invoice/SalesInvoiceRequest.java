package com.smc.smccloud.model.invoice;

import lombok.Data;

import java.util.List;

@Data
public class SalesInvoiceRequest {

    private String customerNo;

    private String userNo;

    private List<String> orderNos;
}
