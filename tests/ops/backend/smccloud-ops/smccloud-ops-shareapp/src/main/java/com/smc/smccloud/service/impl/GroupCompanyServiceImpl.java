package com.smc.smccloud.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.smc.smccloud.mapper.GroupCompanyMapper;
import com.smc.smccloud.model.GroupCompanyDO;
import com.smc.smccloud.service.GroupCompanyService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @Author lyc
 * @Date 2023/8/2 10:15
 * @Descripton TODO
 */
@Service
public class GroupCompanyServiceImpl implements GroupCompanyService {

    @Resource
    private GroupCompanyMapper groupCompanyMapper;

    @Override
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public boolean isExistGroupCompany(String id) {
        if(StringUtils.isBlank(id)) {
            return false;
        }
        LambdaQueryWrapper<GroupCompanyDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(GroupCompanyDO::getId,id);
        queryWrapper.eq(GroupCompanyDO::getEnableSale,1);
        GroupCompanyDO groupCompanyDO = null;
        try {
            groupCompanyDO = groupCompanyMapper.selectOne(queryWrapper);
        } catch (Exception e) {
            return false;
        }
        if (Objects.isNull(groupCompanyDO)) {
            return false;
        }
        return true;
    }
}
