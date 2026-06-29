package com.smc.smccloud.service.authority;

import com.smc.smccloud.model.authority.AuthorityMenu;
import com.smc.smccloud.model.login.Authority;

import java.util.List;

public interface AuthorityMenuService {
    /**
     * 通过权限ID获取该权限绑定的菜单
     * @param authorityId
     * @return
     */
    public List<AuthorityMenu> findByAuthorityId(String authorityId);

    /**
     * 通过权限ID删除权限绑定的菜单
     * @param authorityId
     * @return
     */
    public void deleteByAuthorityId(String authorityId);

    AuthorityMenu saveAuthMenu(AuthorityMenu authorityMenu);
}
