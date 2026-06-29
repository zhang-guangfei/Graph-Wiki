package com.smc.smccloud.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.model.Customer.TblGroupcustomerDO;


/**
 * <p>
 *  服务类
 * </p>
 *
 * @author smc
 * @since 2022-04-15
 */
public interface TblGroupcustomerService extends IService<TblGroupcustomerDO> {

    ResultVo<String> getDlvDeptNoByNo(String customerNo);

}
