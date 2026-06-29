package com.smc.smccloud.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.smc.smccloud.model.salesinvoice.SalesExpDO;


/**
 * <p>
 *  服务类
 * </p>
 *
 * @author smc
 * @since 2022-07-19
 */
public interface SalesExpService extends IService<SalesExpDO> {

    int insertSalesExp(SalesExpDO salesExpDO);

}
