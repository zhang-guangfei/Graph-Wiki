package com.smc.smccloud.service.oauthClient;

import com.github.pagehelper.PageInfo;
import com.smc.smccloud.model.OauthClient.OauthClientDetails;
import com.smc.smccloud.core.model.page.Page;
import com.smc.smccloud.service.Condition;

import java.util.Map;

public interface OauthClientDetailsService {

    PageInfo<OauthClientDetails> page(Condition condition, Page page);

    public Map<String, String> findClient();

    OauthClientDetails saveOauthClient(OauthClientDetails oauthClientDetails);

    OauthClientDetails updateOauthClient(OauthClientDetails oauthClientDetails);

    void delete(String clientId);
}
