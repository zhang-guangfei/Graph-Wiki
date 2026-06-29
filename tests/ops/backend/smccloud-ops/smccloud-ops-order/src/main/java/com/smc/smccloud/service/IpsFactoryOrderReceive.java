package com.smc.smccloud.service;

import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.model.VSalesManuorder.OpsVManuorderToSales;

import java.util.List;

/**
 * lyc
 *
 * @date 2024/12/30
 * /
 * /**
 * @Author lyc
 * @Date 2024/12/30 10:51
 * @Descripton TODO
 */
public interface IpsFactoryOrderReceive {

    /**
     * 从ips获取工厂订单
     * @return
     */
    ResultVo<List<OpsVManuorderToSales>> getManuOrderFromIpsSendOrdertoOps();

}
