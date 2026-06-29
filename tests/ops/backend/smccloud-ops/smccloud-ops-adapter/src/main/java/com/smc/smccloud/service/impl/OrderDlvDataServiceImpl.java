package com.smc.smccloud.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.smc.smccloud.core.exception.BusinessException;
import com.smc.smccloud.mapper.OrderDlvDataMapper;
import com.smc.smccloud.mapper.OrderDlvDataMapperReadOnlyMapper;
import com.smc.smccloud.model.ordersales.OrderDlvDataDO;
import com.smc.smccloud.service.OrderDlvDataService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @Author lyc
 * @Date 2023/9/12 9:53
 * @Descripton TODO
 */
@Service
public class OrderDlvDataServiceImpl implements OrderDlvDataService {
    @Resource
    private OrderDlvDataMapper orderDlvDataMapper;

    @Resource
    private OrderDlvDataMapperReadOnlyMapper orderDlvDataMapperReadOnlyMapper;

    @DS("opsdb")
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public boolean saveOrderDlvData(OrderDlvDataDO data) {
        if (data == null) {
            return false;
        }
        QueryWrapper<OrderDlvDataDO> queryWrapper = Wrappers.query();
        queryWrapper.select("Top(1) OrderNo", "ItemNo");
        queryWrapper.lambda().eq(OrderDlvDataDO::getOrderNo, data.getOrderNo())
                .eq(OrderDlvDataDO::getItemNo, data.getItemNo());
        try {
            if (orderDlvDataMapperReadOnlyMapper.selectOne(queryWrapper) == null) {
                data.setCreateTime(new Date());
                orderDlvDataMapper.insert(data);
                return true;
            } else {
                return true;
            }
        } catch (Exception e) {
            throw new BusinessException("保存订单收货信息失败: 异常信息=>" + e.getMessage(), e);
        }
    }
}
