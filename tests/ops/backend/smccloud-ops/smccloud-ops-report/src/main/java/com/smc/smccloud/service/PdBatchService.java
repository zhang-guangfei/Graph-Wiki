package com.smc.smccloud.service;

import com.smc.smccloud.core.model.ResultVo.ResultVo;

/**
 * @Author lyc
 * @Date 2023/6/15 16:03
 * @Descripton TODO
 */
public interface PdBatchService {
    ResultVo<String> updatePdStateByPdbatchNo(String pdBatchNo,String pdState, String optUser);
}
