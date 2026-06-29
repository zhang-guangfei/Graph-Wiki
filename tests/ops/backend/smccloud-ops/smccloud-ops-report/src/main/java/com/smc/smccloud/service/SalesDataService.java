package com.smc.smccloud.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.model.SalesDataDO;


/**
 * <p>
 *  服务类
 * </p>
 *
 * @author smc
 * @since 2022-10-28
 */
public interface SalesDataService extends IService<SalesDataDO> {

    /**
     * 发送E差益统计邮件
     * 财务、运营、价格课
     * @return
     */
    ResultVo<String> sendSalesEDiscountReport(String date);

}
