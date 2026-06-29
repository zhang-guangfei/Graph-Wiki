package com.smc.smccloud.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.smc.smccloud.mapper.OrderStateDetailMapper;
import com.smc.smccloud.model.orderstate.OrderStateDetailDO;
import com.smc.smccloud.service.OrderStateDetailService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class OrderStateDetailServiceImpl extends ServiceImpl<OrderStateDetailMapper, OrderStateDetailDO> implements OrderStateDetailService {

    @Resource
    private OrderStateDetailMapper orderStateDetailMapper;

    @Override
    public List<OrderStateDetailDO> getOrderStateDetail(String orderNo) {
        LambdaQueryWrapper<OrderStateDetailDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OrderStateDetailDO::getOrderNo, orderNo);
        return orderStateDetailMapper.selectList(queryWrapper);
    }
}
