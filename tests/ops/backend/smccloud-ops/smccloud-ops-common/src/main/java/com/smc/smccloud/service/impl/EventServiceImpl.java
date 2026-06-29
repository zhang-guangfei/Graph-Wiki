package com.smc.smccloud.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.smc.smccloud.mapper.OpsEventBusMapper;
import com.smc.smccloud.model.OpsEventBusDO;
import com.smc.smccloud.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EventServiceImpl implements EventService {

    @Autowired
    private OpsEventBusMapper opsEventBusMapper;

    @DS("opsdb")
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    @Override
    public int insert(OpsEventBusDO orderDO) {
        return opsEventBusMapper.insert(orderDO);
    }
}
