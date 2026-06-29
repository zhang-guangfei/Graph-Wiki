package com.smc.smccloud.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.smc.smccloud.mapper.OrderStateLogMapper;;
import com.smc.smccloud.model.OrderLog.OrderStateLogDO;
import com.smc.smccloud.model.orderstate.OrderStateVO;
import com.smc.smccloud.service.OrderStateLogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author smc
 * @since 2022-10-08
 */
@Service
public class OrderStateLogServiceImpl extends ServiceImpl<OrderStateLogMapper, OrderStateLogDO> implements OrderStateLogService {

    @Resource
    private OrderStateLogMapper orderStateLogMapper;


    @Override
    public int insertOrderStateLog(OrderStateVO orderStateVO,String provider) {
        if (orderStateVO == null) {
            return 0;
        }
        OrderStateLogDO orderStateLogDO = new OrderStateLogDO();
        orderStateLogDO.setUserNo(orderStateVO.getUserNo());
        orderStateLogDO.setDescription(orderStateVO.getStateDes());
        orderStateLogDO.setData(JSONObject.toJSONString(orderStateVO));
        orderStateLogDO.setCreateUser(orderStateVO.getOptUserNo());
        orderStateLogDO.setOrderItem(orderStateVO.getItemNo());
        orderStateLogDO.setOrderId(orderStateVO.getRorderNo());
        orderStateLogDO.setOrderDate(orderStateVO.getOrderDate());
        orderStateLogDO.setCustomerNo(orderStateVO.getCustomerNo());
        orderStateLogDO.setSplitNo(orderStateVO.getSplitNo());
        orderStateLogDO.setOrderNo(orderStateVO.getOrderNo());
        orderStateLogDO.setCreateTime(new Date());
        orderStateLogDO.setOrderType(provider);
        return orderStateLogMapper.insert(orderStateLogDO);
    }
}
