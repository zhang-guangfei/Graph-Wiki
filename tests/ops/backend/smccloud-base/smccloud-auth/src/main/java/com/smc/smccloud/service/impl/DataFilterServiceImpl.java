package com.smc.smccloud.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.smc.smccloud.mapper.DataFilterMapper;
import com.smc.smccloud.model.authority.DataFilter;
import com.smc.smccloud.service.resource.DataFilterService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DataFilterServiceImpl implements DataFilterService {

    @Resource
    private DataFilterMapper dataFilterMapper;

    @Override
    public DataFilter findByRoleId(String roleId) {
        QueryWrapper<DataFilter> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("ROLE_ID",roleId);
        return dataFilterMapper.selectOne(queryWrapper);
    }

    @Override
    public void deleteByRoleId(String roleId) {

    }

    @Override
    public void bindDataFilter(DataFilter dataFilter, List<String> objects) {

    }

    @Override
    public List<DataFilter> findByGroupName(List<String> list) {
        return null;
    }
}
