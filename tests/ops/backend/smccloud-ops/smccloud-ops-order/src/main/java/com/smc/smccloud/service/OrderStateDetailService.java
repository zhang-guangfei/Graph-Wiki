package com.smc.smccloud.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.smc.smccloud.model.orderstate.OrderStateDetailDO;

import java.util.List;

public interface OrderStateDetailService extends IService<OrderStateDetailDO> {

    List<OrderStateDetailDO> getOrderStateDetail(String orderNo);
}
