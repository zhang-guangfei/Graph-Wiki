package com.smc.smccloud.service.impl;

import com.github.pagehelper.PageInfo;

import com.smc.smccloud.core.redis.RedisManager;
import com.smc.smccloud.core.utils.PublicUtil;
import com.smc.smccloud.core.model.constants.Constants;
import com.smc.smccloud.core.model.page.Page;
import com.smc.smccloud.model.tokenManage.TokenManageCondition;
import com.smc.smccloud.service.tokenManage.TokenManageService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class TokenManageServiceImpl implements TokenManageService {

    @Autowired
    private TokenStore tokenStore;

    @Autowired
    private RedisManager redisUtil;

    @Override
    public PageInfo<OAuth2AccessToken> query(TokenManageCondition condition, Page page) {
        PageInfo<OAuth2AccessToken> pageInfo = new PageInfo<OAuth2AccessToken>();
        // 查询某些前缀的key
        Set<String> set = redisUtil.scan(Constants.REDIS_CACHE_SESSION_PREFIX + "auth:" + "*");
        if(PublicUtil.isEmpty(set)) {
            return null;
        }
        //转换为可识别的OAuth2AccessToken
        List<OAuth2AccessToken> list = new ArrayList<OAuth2AccessToken>();
        for(String prefixToken : set) {
            String token = StringUtils.substringAfter(prefixToken,Constants.REDIS_CACHE_SESSION_PREFIX + "auth:");
            OAuth2AccessToken accessToken = tokenStore.readAccessToken(token);
            list.add(accessToken);
        }
        //根据条件过滤
        List<OAuth2AccessToken> conditionList = new ArrayList<OAuth2AccessToken>();
        if(PublicUtil.isNotEmpty(condition.getUsername())) {
            for(OAuth2AccessToken accessToken : list) {
                Map<String, Object> map = accessToken.getAdditionalInformation();
                if(map == null || map.size() == 0)
                    continue;

                if(map.containsValue(condition.getUsername())) {
                    conditionList.add(accessToken);
                }
            }
        }
        if(CollectionUtils.isEmpty(conditionList)) {
            conditionList = list;
        }

        int totalPages = conditionList.size() % page.getPageSize() == 0 ? conditionList.size() / page.getPageSize() : conditionList.size() / page.getPageSize() + 1;
        int start = page.getPageSize() * (page.getPageNumber() - 1) + 1;
        int end = page.getPageSize() * (page.getPageNumber() - 1) + page.getPageSize();
        int count = 0;

        List<OAuth2AccessToken> newList = new ArrayList<OAuth2AccessToken>();
        for(OAuth2AccessToken accessToken : conditionList) {
            count ++ ;
            if(count >= start && count <= end) {
                newList.add(accessToken);
                pageInfo.setList(newList);
            }
        }

        pageInfo.setPageNum(page.getPageNumber());
        pageInfo.setPageSize(page.getPageSize());
        pageInfo.setTotal(totalPages);
        return pageInfo;
    }

    @Override
    public void delete(String token) {
        OAuth2AccessToken oAuth2AccessToken = tokenStore.readAccessToken(token);
        if(oAuth2AccessToken != null) {
            tokenStore.removeAccessToken(oAuth2AccessToken);
            OAuth2RefreshToken oAuth2RefreshToken = oAuth2AccessToken.getRefreshToken();
            tokenStore.removeRefreshToken(oAuth2RefreshToken);
            tokenStore.removeAccessTokenUsingRefreshToken(oAuth2RefreshToken);
        }
    }
}
