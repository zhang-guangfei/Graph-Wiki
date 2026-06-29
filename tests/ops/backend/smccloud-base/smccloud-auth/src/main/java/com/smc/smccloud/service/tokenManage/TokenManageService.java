package com.smc.smccloud.service.tokenManage;

import com.github.pagehelper.PageInfo;
import com.smc.smccloud.core.model.page.Page;
import com.smc.smccloud.model.tokenManage.TokenManageCondition;
import org.springframework.security.oauth2.common.OAuth2AccessToken;

public interface TokenManageService {
    /**
     * 查询
     * @param condition
     * @param page
     * @return
     */
    public PageInfo<OAuth2AccessToken> query(TokenManageCondition condition, Page page);

    /**
     * 删除token
     */
    public void delete(String token);
}
