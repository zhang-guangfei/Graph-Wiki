package com.sales.ops.service.purchase;

public interface SpecialOrderNoService {

    Boolean poNoRepeatCheck(String type, String orderNo);

    String generateOrderNo(String type);
}
