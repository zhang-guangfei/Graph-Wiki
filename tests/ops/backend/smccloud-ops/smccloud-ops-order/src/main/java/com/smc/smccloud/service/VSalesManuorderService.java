package com.smc.smccloud.service;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.model.page.Page;
import com.smc.smccloud.model.OrderSales.OrderSalesDO;
import com.smc.smccloud.model.OrderSales.OrderSalesRequest;
import com.smc.smccloud.model.VSalesManuorder.VSalesManuorderDO;
import com.smc.smccloud.model.VSalesManuorder.VSalesManuorderParams;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author smc
 * @since 2022-02-11
 */
public interface VSalesManuorderService extends IService<VSalesManuorderDO> {

    PageInfo<VSalesManuorderDO> findAll(VSalesManuorderParams params, Page page);

    @DS("cmdb")
    int update(VSalesManuorderDO vSalesManuorderDO);

}
