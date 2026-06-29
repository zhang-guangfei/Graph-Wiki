package com.smc.smccloud.core.security.service;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface ResourceCommonService {
	/**
	 * 通过用戶ID获取用户绑定的资源
	 * @return
	 */
	public Set<String> findByUserId(String userId);
	
	/**
	 * 获取角色与资源的对应关系
	 * @return
	 */
	public List<Map<String,String>> findRoleAndResource();
}
