package com.smc.smccloud.core.security.service;

import com.smc.smccloud.core.model.constants.Constants;
import com.smc.smccloud.core.redis.RedisManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;
@Slf4j
@Service
public class ReadAuthorityCacheServiceImpl implements ReadAuthorityCacheService{

	@Autowired
	private ResourceCommonService resourceCommonService;

    @Resource
    private RedisManager redisManager;

    @Autowired
    private ReadAuthorityCacheService readAuthorityCacheService;

	@Override
	@Cacheable(value = Constants.REDIS_ROLE_AUTHORITY_RESOURCE, key="'role_authority_resource'", unless = "#result == null || #result.size() == 0")
	public List<Map<String, String>> findRoleAndResource() {
		return resourceCommonService.findRoleAndResource();
	}
	
	@Override
	@Cacheable(value = Constants.REDIS_ROLE_AUTHORITY_RESOURCE, key = "'user_all_role_' + #userId", unless = "#result == null || #result.size() == 0")
	public Set<String> findRoleByUserId(String userId){
		return resourceCommonService.findByUserId(userId);
	}

    @Override
    public void reloadRoleResourse() {
        // 重新加载缓存
        String role_authority_key = "ops:authorityCache:::role_authority_resource";
        Boolean aBoolean = redisManager.hasKey(role_authority_key);
        if (aBoolean) {
            redisManager.del(role_authority_key);
            readAuthorityCacheService.findRoleAndResource();
        }
    }
}
