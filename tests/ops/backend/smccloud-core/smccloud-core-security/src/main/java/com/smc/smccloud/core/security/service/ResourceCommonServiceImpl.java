package com.smc.smccloud.core.security.service;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class ResourceCommonServiceImpl implements ResourceCommonService {

    @Resource
    ResourceCommonMapper resourceCommonMapper;

    @Override
    public Set<String> findByUserId(String userId) {
        Set<String> set = new HashSet<String>();
        resourceCommonMapper.getByUserId(userId).forEach(resource -> set.add(resource.getPattern()));
        return set;
    }

    @Override
    public List<Map<String, String>> findRoleAndResource() {
        return resourceCommonMapper.getRoleAndResource();
    }
}
