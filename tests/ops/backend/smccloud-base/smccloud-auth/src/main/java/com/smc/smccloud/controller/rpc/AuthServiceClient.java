package com.smc.smccloud.controller.rpc;

import com.smc.smccloud.core.security.service.ResourceCommonService;
import com.smc.smccloud.core.utils.PublicUtil;
import com.smc.smccloud.service.AuthService;
import com.smc.smccloud.service.AuthServiceApi;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@RestController
public class AuthServiceClient implements AuthServiceApi {

    @Autowired
    private ResourceCommonService resourceCommonService;

    @Resource
    private AuthService authService;

    @Override
    public Boolean hasPermission(String url) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // 获取当前访问者的权限
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Set<String> set = AuthorityUtils.authorityListToSet(authorities);
        String[] strings = new String[set.size()];
        set.toArray(strings);
        /**
         * 当前访问者拥有的角色集合
         */
        Collection<ConfigAttribute> configAttribute = SecurityConfig.createList(strings);

        if (PublicUtil.isEmpty(url) || PublicUtil.isEmpty(configAttribute)) {
            return false;
        }
        // 获取所有的角色资源
        List<Map<String, String>> roleAndResource = resourceCommonService.findRoleAndResource();

        Map<String, Collection<ConfigAttribute>> requestMap = new ConcurrentHashMap<String, Collection<ConfigAttribute>>();
        for (Map<String, String> map : roleAndResource) {
            getMap(requestMap, map.get("NAME"), map.get("PATTERN"));
        }
        // 获取当前访问者所访问的资源在所有资源中所属的角色权限
        Collection<ConfigAttribute> value = null;
        for (Map.Entry<String, Collection<ConfigAttribute>> entry : requestMap.entrySet()) {
            if (new AntPathMatcher().match(entry.getKey(), url)) {  // 匹配路径 若路径相同 则返回其所属value（所属权限）
                value = entry.getValue();
            }
        }

        // 判断当前访问者所访问的资源在所有资源中所属的角色权限
        //  Collection<ConfigAttribute> configAttributes = requestMap.get(url);
        if (PublicUtil.isEmpty(value)) {
            return false;
        }

        /**
         * A.retainAll(B)
         * 通过取交集的方式 判断这两个集合是否包含相同的对象或元素
         * 如果存在相同元素，A中仅保留相同的元素。A集合只保留B中存在的元素
         * 如果不存在相同元素，A会变为空。
         */
        if (value.retainAll(configAttribute)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Boolean hasFuncPermission(String authName,String loginUserNo) {
        return authService.hasFuncPermission(authName,loginUserNo);
    }

    private synchronized void getMap(Map<String, Collection<ConfigAttribute>> requestMap, String roleName, String resource) {
        Collection<ConfigAttribute> configAttributes = new LinkedHashSet<ConfigAttribute>();
        ConfigAttribute configAttribute = new SecurityConfig(roleName);

        //RequestMatcher requestMatcher = new AntPathRequestMatcher(resource);
        /**
         * 如果requestMatcher已经含有pattern，
         * 则requestMap的key值requestMatcher保持不变
         * 仅将pattern对应的角色添加至requestMap的value值Collection<ConfigAttribute>
         * 否则requestMap新添加pattern与configAttributes
         */
        if (requestMap.containsKey(resource)) {
            Collection<ConfigAttribute> value = requestMap.get(resource);
            value.add(configAttribute);
            //requestMap.put(requestMatcher, value);
        } else {
            configAttributes.add(configAttribute);
            requestMap.put(resource, configAttributes);
        }
    }

    /**
     * 判断指定url地址是否匹配指定url集合中的任意一个
     *
     * @param urlPath 指定url地址
     * @param urls    需要检查的url集合
     * @return 是否匹配  匹配返回true，不匹配返回false
     */
    public static boolean matches(String urlPath, List<String> urls) {
        if (StringUtils.isEmpty(urlPath) || CollectionUtils.isEmpty(urls)) {
            return false;
        }
        for (String url : urls) {
            if (isMatch(url, urlPath)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断url是否与规则配置:
     * ? 表示单个字符
     * * 表示一层路径内的任意字符串，不可跨层级
     * ** 表示任意层路径
     *
     * @param url     匹配规则
     * @param urlPath 需要匹配的url
     */
    public static boolean isMatch(String url, String urlPath) {
        AntPathMatcher matcher = new AntPathMatcher();
        return matcher.match(url, urlPath);
    }


}
