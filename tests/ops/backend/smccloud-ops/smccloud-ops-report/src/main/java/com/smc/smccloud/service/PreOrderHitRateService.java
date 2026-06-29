package com.smc.smccloud.service;

import com.smc.smccloud.core.model.ResultVo.ResultVo;

/**
 * @Author lyc
 * @Date 2024/12/23 15:36
 * @Descripton TODO
 */
public interface PreOrderHitRateService {


    /**
     * 先行在库命中率
     */
    ResultVo<String> preOrderHitRate(String calDate);


}
