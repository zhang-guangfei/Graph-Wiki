package com.smc.smccloud.core.security.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;


@Service("rbacAuthorityService")
@Slf4j
public class RbacAuthorityServiceImpl implements RbacAuthorityService {

    @Autowired
    private SecurityMapService securityMapService;

    @Override
    public boolean hasPermission(HttpServletRequest request, Authentication authentication) {
        String requestURI = request.getRequestURI();
        // log.info("requestURI = {}", requestURI);
        // C14717 bugid：14930  2024/8/14
        if(requestURI.contains("/sso/login")){
            return true;
        }
        Object principal = authentication.getPrincipal();
        // log.info("principal = {} , authentication = {}" ,principal,authentication.getAuthorities().isEmpty() ? "" : authentication.getAuthorities().toString());
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        boolean hasPermission = false;
        if (CollectionUtils.isEmpty(authorities)) {
            return hasPermission;
        }

        Set<String> set = AuthorityUtils.authorityListToSet(authorities);
        Set<String> set2 = new HashSet<>();

        boolean flag = false;
        for (String str : set) {
            if (str.contains("{") && str.contains("=" ) && str.contains("}") ) {
                String str2 = str.split("=")[1];
                String str3 = str2.split("}")[0];
                set2.add(str3);
                flag = true;
            } else {
                break;
            }
        }
        String[] strings;
        if (flag) {
            strings = new String[set2.size()];
            set2.toArray(strings);
        } else {
            strings = new String[set.size()];
            set.toArray(strings);
        }

        /**
         * 个人拥有的角色集合
         */
        Collection<ConfigAttribute> configAttribute = SecurityConfig.createList(strings);

        /**
         * 访问的url对应的角色集合
         */
        Collection<ConfigAttribute> configAttributes = getAttributes(request);
        if (CollectionUtils.isEmpty(configAttribute) || CollectionUtils.isEmpty(configAttributes))
            return false;
        configAttributes.retainAll(configAttribute);  // 判断 configAttribute 与 configAttributes 是否有交集的数据
        hasPermission = !CollectionUtils.isEmpty(configAttributes);
        return hasPermission;
    }

    public Collection<ConfigAttribute> getAttributes(HttpServletRequest request) {
        Map<String, Collection<ConfigAttribute>> requestMap = securityMapService.loadResourceDefine();
        for (Map.Entry<String, Collection<ConfigAttribute>> entry : requestMap.entrySet()) {
            if (new AntPathRequestMatcher(entry.getKey()).matches(request)) {
                return new ArrayList<ConfigAttribute>(entry.getValue());
            }
        }
        return null;
    }
}
