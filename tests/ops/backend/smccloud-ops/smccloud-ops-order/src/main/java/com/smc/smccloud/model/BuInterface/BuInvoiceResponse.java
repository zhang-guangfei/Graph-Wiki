package com.smc.smccloud.model.BuInterface;

import lombok.Data;

@Data
public class BuInvoiceResponse {

    private Integer code;

    private String message;

    private BuInvoiceVO data;
}
