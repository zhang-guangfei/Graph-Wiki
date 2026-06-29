package com.smc.smccloud.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.smc.smccloud.core.utils.PublicUtil;
import com.smc.smccloud.core.utils.UUIDGenerator;
import com.smc.smccloud.mapper.ResourceMapper;

import com.smc.smccloud.model.login.ResourceDO;
import com.smc.smccloud.service.resource.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResourceServiceImpl implements ResourceService {

    @Autowired
    private ResourceMapper resourceMapper;

    @Override
    public List<ResourceDO> findAll() {
        QueryWrapper<ResourceDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("1",1);
        return resourceMapper.selectList(queryWrapper);
    }

    @Override
    public ResourceDO saveResource(ResourceDO resource) {
        if (PublicUtil.isEmpty(resource.getId())) {
            resource.setId(UUIDGenerator.getUUID());
            resource.setCode(UUIDGenerator.getRandomNum());
            resource.setStatus("有效");
            resourceMapper.insert(resource);
            return resource;
        }else {
            resourceMapper.updateById(resource);
            return resource;
        }
    }

    @Override
    public void deleteByCode(String code) {
        resourceMapper.deleteByCode(code);
    }
}
