package com.smc.smccloud.core.security.service;

import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;

public interface RbacAuthorityService {
    boolean hasPermission(HttpServletRequest request, Authentication authentication);
}
