package com.smc.smccloud.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.smc.smccloud.mapper.GroupMapper;
import com.smc.smccloud.model.group.Group;
import com.smc.smccloud.service.group.GroupService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

@Service
public class GroupServiceImpl implements GroupService {

    @Resource
    private GroupMapper groupMapper;

    @Override
    public List<Group> findAll() {
        QueryWrapper<Group> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("1",1);
        return groupMapper.selectList(queryWrapper);
    }

    @Override
    public Group detail(String id) {
        return null;
    }

    @Override
    public void deleteByCode(String code) {

    }

    @Override
    public Set<String> findByUserId(String userId) {
        return null;
    }
}
