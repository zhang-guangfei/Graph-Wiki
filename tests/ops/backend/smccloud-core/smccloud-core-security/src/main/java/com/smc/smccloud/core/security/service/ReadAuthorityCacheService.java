package com.smc.smccloud.core.security.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface ReadAuthorityCacheService {
	
	public List<Map<String,String>> findRoleAndResource();
	
	public Set<String> findRoleByUserId(String userId);

	// 重新加载角色资源
    void reloadRoleResourse();

}
