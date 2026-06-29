package com.smc.smccloud.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.smc.smccloud.core.exception.BusinessException;
import com.smc.smccloud.core.utils.BeanCopyUtil;
import com.smc.smccloud.mapper.OrderDlvDataMapper;
import com.smc.smccloud.mapper.OrderDlvDataMapperReadOnlyMapper;
import com.smc.smccloud.model.OrderSales.OrderDlvDataDO;
import com.smc.smccloud.model.OrderSales.OrderDlvDataVO;
import com.smc.smccloud.service.OrderDlvDataService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Author: B90034
 * Date: 2022-01-12 09:03
 * Description:
 */
@Slf4j
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
                orderDlvDataMapper.insert(data);
                return true;
            } else {
                return true;
            }
        } catch (Exception e) {
            throw new BusinessException("保存订单收货信息失败: 异常信息=>" + e.getMessage(), e);
        }
    }

    @Override
    public OrderDlvDataVO findOrderDlvInfo(OrderDlvDataVO orderDlvDataVO) {
        if (orderDlvDataVO == null) {
            return null;
        }
        QueryWrapper<OrderDlvDataDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotBlank(orderDlvDataVO.getOrderNo()), "OrderNo", orderDlvDataVO.getOrderNo());
        queryWrapper.eq(orderDlvDataVO.getId() != null, "id", orderDlvDataVO.getId());
        queryWrapper.eq(orderDlvDataVO.getItemNo() != null, "ItemNo", orderDlvDataVO.getItemNo());
        OrderDlvDataDO orderDlvDataDO = orderDlvDataMapperReadOnlyMapper.selectOne(queryWrapper);
        if (orderDlvDataDO == null) {
            return null;
        }
        return BeanCopyUtil.copy(orderDlvDataDO, OrderDlvDataVO.class);
    }
}
