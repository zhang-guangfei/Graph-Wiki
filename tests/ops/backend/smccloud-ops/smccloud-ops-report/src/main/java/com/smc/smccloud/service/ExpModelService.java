package com.smc.smccloud.service;

import com.smc.smccloud.core.model.ResultVo.ResultVo;

import java.util.Date;

/**
 * @author edp04
 * @title: ExpModelService
 * @date 2022/05/11 12:12
 */
public interface ExpModelService {

    ResultVo<String> onSendAgentOrderFreqReport(String agentNo);

    ResultVo<String> sendCustomerData();
}
