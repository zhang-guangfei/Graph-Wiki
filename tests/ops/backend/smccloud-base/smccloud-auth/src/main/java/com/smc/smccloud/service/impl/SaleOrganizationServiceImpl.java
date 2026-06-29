package com.smc.smccloud.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.smc.smccloud.core.redis.RedisManager;
import com.smc.smccloud.core.utils.PublicUtil;
import com.smc.smccloud.core.model.constants.Constants;
import com.smc.smccloud.mapper.SaleOrganizationMapper;
import com.smc.smccloud.model.authority.SaleOrgPositionBean;
import com.smc.smccloud.model.authority.SaleOrganization;
import com.smc.smccloud.service.employeeAndOrgan.SaleOrganizationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
@Slf4j
public class SaleOrganizationServiceImpl implements SaleOrganizationService {

    @Resource
    private SaleOrganizationMapper saleOrganizationMapper;

    @Resource
    private RedisManager redisUtil;

    @Override
    public List<SaleOrganization> findAllOrganizations() {

        List<SaleOrganization> list = null;
        try {
            list = (List<SaleOrganization>) redisUtil.get(Constants.REDIS_KEY_ORGAN);
        } catch (Exception e) {
            log.error("{} 键不存在 或者 为 该值null",Constants.REDIS_KEY_ORGAN);
            e.printStackTrace();
        }

        if (PublicUtil.isEmpty(list)) {
            QueryWrapper<SaleOrganization> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("Status",0);
            list = saleOrganizationMapper.selectList(queryWrapper);
            redisUtil.set(Constants.REDIS_KEY_ORGAN,list,Constants.REDIS_LIMIT_TIME_ORGAN);
        }
        return list;
    }

    @Override
    public List<SaleOrgPositionBean> findByUnitIdAndPositionName(String id, String positionName) {
        return saleOrganizationMapper.getByUnitIdAndPositionName(id, positionName);
    }

    @Override
    public List<SaleOrgPositionBean> findByUnitIdAndPositionNameLike(String id, String positionName) {
        return saleOrganizationMapper.getByUnitIdAndPositionNameLike(id, positionName);
    }
}
