package com.smc.smccloud.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.smc.smccloud.core.exception.BusinessException;
import com.smc.smccloud.mapper.salesInvoice.SalesExpMapper;
import com.smc.smccloud.model.salesinvoice.SalesExpDO;
import com.smc.smccloud.service.SalesExpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author smc
 * @since 2022-07-19
 */
@Slf4j
@Service
public class SalesExpServiceImpl extends ServiceImpl<SalesExpMapper, SalesExpDO> implements SalesExpService {

    @Resource
    private SalesExpMapper salesExpMapper;

    @Override
    @DS("costdb")
    @Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRES_NEW)
    public int insertSalesExp(SalesExpDO salesExpDO) {
        try {
            return salesExpMapper.insert(salesExpDO);
        } catch (Exception e) {
            log.error("insertSalesExp发生异常:",e);
            log.error("insertSalesExp errorData : {}", salesExpDO);
            throw new BusinessException("insert SalesExp error", e);
        }
    }
}
