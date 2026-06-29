package com.smc.smccloud.model.invoice;

import lombok.Data;

import java.util.List;

@Data
public class OrderInvoiceDTO {

    String orderNo;
    Integer itemNo;
    String customerNo;
    String userNo;
    List<String> orderNos;
}
