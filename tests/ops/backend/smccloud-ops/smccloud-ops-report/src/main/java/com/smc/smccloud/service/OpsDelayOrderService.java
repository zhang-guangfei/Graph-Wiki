package com.smc.smccloud.service;

import com.sales.ops.common.opsexception.OpsException;

public interface OpsDelayOrderService {

    String exportDelayOrderInfo();

    void sendEmail(String fileName) throws OpsException;
}
