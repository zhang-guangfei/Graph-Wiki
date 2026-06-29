package com.smc.smccloud.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.smc.smccloud.model.OrderLog.OrderStateLogDO;
import com.smc.smccloud.model.orderstate.OrderStateVO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author smc
 * @since 2022-10-08
 */
public interface OrderStateLogService extends IService<OrderStateLogDO> {

    int insertOrderStateLog(OrderStateVO orderStateVO,String provider);

}
