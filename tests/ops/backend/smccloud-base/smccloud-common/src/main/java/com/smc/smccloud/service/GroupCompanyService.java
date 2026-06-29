package com.smc.smccloud.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.model.Customer.GroupCompanyDO;


/**
 * <p>
 *  服务类
 * </p>
 *
 * @author smc
 * @since 2022-04-14
 */
public interface GroupCompanyService extends IService<GroupCompanyDO> {

    /***
     * 根据客户名称获取客户集团ID
     */
    ResultVo<String> findcustGroupIdByName(String custName);

}
