package com.smc.smccloud.service.authority;

import com.smc.smccloud.model.authority.AuthorityResource;

import java.util.List;

public interface AuthorityResourceService {
    /**
     * 通过权限ID获取该权限绑定的资源
     * @param authorityId
     * @return
     */
    List<AuthorityResource> findByAuthorityId(String authorityId);

    void add(String authorityId,List<String> resourceIds);

    /**
     * 通过权限ID删除权限绑定的资源
     * @param authorityId
     * @return
     */
    void deleteByAuthorityId(String authorityId);
}
