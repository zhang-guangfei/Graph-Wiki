package com.smc.smccloud.core.security.service;

import com.smc.smccloud.core.model.constants.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
@Slf4j
@Component("securityMapService")
public class SecurityMapService {
	
	//public static Map<RequestMatcher, Collection<ConfigAttribute>> requestMap = null;
	@Autowired
	private ReadAuthorityCacheService readAuthorityCacheService;
	
	public SecurityMapService() {
	}
	
	/**
    * loadResourceDefine:加载所有资源与角色的关系
    */
	// @Cacheable(value = Constants.REDIS_ROLE_AUTHORITY_RESOURCE, key="'role_authority_resource_map'", unless = "#result == null || #result.size() == 0")
	public Map<String, Collection<ConfigAttribute>> loadResourceDefine(){
		List<Map<String,String>> list = readAuthorityCacheService.findRoleAndResource();
		Map<String, Collection<ConfigAttribute>> requestMap = new ConcurrentHashMap<String, Collection<ConfigAttribute>>();
        for(Map<String,String> map : list){
        	getMap(requestMap, map.get("NAME"), map.get("PATTERN"));
        }

        return requestMap;
    }

	private synchronized void getMap(Map<String, Collection<ConfigAttribute>> requestMap, String roleName, String resource){
		Collection<ConfigAttribute> configAttributes =  new LinkedHashSet<ConfigAttribute>();  
    	ConfigAttribute configAttribute = new SecurityConfig(roleName);
		
		//RequestMatcher requestMatcher = new AntPathRequestMatcher(resource);
        /**
         * 如果requestMatcher已经含有pattern，
         * 则requestMap的key值requestMatcher保持不变
         * 仅将pattern对应的角色添加至requestMap的value值Collection<ConfigAttribute>
         * 否则requestMap新添加pattern与configAttributes
         */
        if (requestMap.containsKey(resource)){
        	Collection<ConfigAttribute> value = requestMap.get(resource);
        	value.add(configAttribute);
        	//requestMap.put(requestMatcher, value);  
        }else{
        	configAttributes.add(configAttribute); 
        	requestMap.put(resource, configAttributes);  
        }
	}
}
