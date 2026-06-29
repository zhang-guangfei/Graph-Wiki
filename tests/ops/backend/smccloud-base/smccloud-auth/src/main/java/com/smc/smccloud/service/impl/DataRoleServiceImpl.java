package com.smc.smccloud.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.smc.smccloud.core.utils.PublicUtil;
import com.smc.smccloud.mapper.DataRoleMapper;
import com.smc.smccloud.model.authority.DataRole;
import com.smc.smccloud.service.resource.DataRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DataRoleServiceImpl implements DataRoleService {
    @Resource
    private DataRoleMapper dataRoleMapper;

    @Override
    public void deleteByRoleId(String roleId) {
        dataRoleMapper.deleteByRoleId(roleId);
    }

    @Override
    public List<DataRole> findByRoleId(String roleId) {
        QueryWrapper<DataRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("ROLE_ID",roleId);
        return dataRoleMapper.selectList(queryWrapper);
    }

    @Override
    public void add(String roleId, List<DataRole> list) {
        deleteByRoleId(roleId);
        if (PublicUtil.isEmpty(list)) {
            return;
        }
        for (DataRole dataRole : list ) {
            dataRoleMapper.insert(dataRole);
        }
    }
}
