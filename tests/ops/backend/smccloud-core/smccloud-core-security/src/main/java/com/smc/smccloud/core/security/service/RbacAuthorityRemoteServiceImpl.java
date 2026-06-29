package com.smc.smccloud.core.security.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import com.smc.smccloud.core.security.service.ResourceCommonService;
import com.smc.smccloud.service.AuthServiceApi;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;


//@Service("rbacAuthorityService")
@Slf4j
public class RbacAuthorityRemoteServiceImpl implements RbacAuthorityService{
    @Resource
   private AuthServiceApi authServiceApi;

    @Override
    public boolean hasPermission(HttpServletRequest request, Authentication authentication) {
        String requestURI = request.getRequestURI();

         Boolean result =  authServiceApi.hasPermission(request.getRequestURI());
         if(result!=null&& result)
         {
             return true;
         }
         return false;

    }
}
