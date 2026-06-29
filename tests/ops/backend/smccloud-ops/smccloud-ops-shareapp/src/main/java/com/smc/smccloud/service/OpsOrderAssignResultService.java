package com.smc.smccloud.service;

import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.model.OpsOrderAssignResultDO;

import java.util.List;

/**
 * @Author lyc
 * @Date 2023/6/25 12:06
 * @Descripton TODO
 */
public interface OpsOrderAssignResultService {

    ResultVo<List<OpsOrderAssignResultDO>> findOrderAssignResult(String orderNo,String itemNo);
}
