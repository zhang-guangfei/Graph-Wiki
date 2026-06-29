package com.smc.smccloud.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.smc.smccloud.core.utils.UUIDGenerator;
import com.smc.smccloud.mapper.OauthClientDetailsMapper;
import com.smc.smccloud.model.OauthClient.OauthClientDetails;
import com.smc.smccloud.core.model.page.Page;
import com.smc.smccloud.service.Condition;
import com.smc.smccloud.service.oauthClient.OauthClientDetailsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Service
public class OauthClientDetailsServiceImpl implements OauthClientDetailsService {

    @Resource
    private OauthClientDetailsMapper oauthClientDetailsMapper;


    @Override
    public PageInfo<OauthClientDetails> page(Condition condition, Page page) {
        QueryWrapper<OauthClientDetails> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("1",1);
        return PageHelper.startPage(page.getPageNumber(), page.getPageSize()).doSelectPageInfo(() -> {
            oauthClientDetailsMapper.selectList(queryWrapper);
        });
    }

    @Override
    public Map<String, String> findClient() {
        String clientId = UUIDGenerator.getUUID();
        String clientSecret = UUIDGenerator.getUUID();
        Map<String,String> map = new HashMap<String,String>();
        map.put("clientId", clientId);
        map.put("clientSecret", clientSecret);
        return map;
    }

    @Override
    public OauthClientDetails saveOauthClient(OauthClientDetails oauthClientDetails) {
       /* QueryWrapper<OauthClientDetails> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(PublicUtil.isNotEmpty(oauthClientDetails.getClientId()),"client_id",oauthClientDetails.getClientId());
        queryWrapper.eq(PublicUtil.isNotEmpty(oauthClientDetails.getScope()),"client_id",oauthClientDetails.getScope());
        OauthClientDetails oau = oauthClientDetailsMapper.selectOne(queryWrapper);
        if (PublicUtil.isEmpty(oau)) {
        }else {
            oauthClientDetailsMapper.updateById(oauthClientDetails);
            return oauthClientDetails;
        }*/
        oauthClientDetailsMapper.insert(oauthClientDetails);
        return oauthClientDetails;
    }

    @Override
    public OauthClientDetails updateOauthClient(OauthClientDetails oauthClientDetails) {
        oauthClientDetailsMapper.updateById(oauthClientDetails);
        return oauthClientDetails;
    }

    @Override
    public void delete(String clientId) {
        oauthClientDetailsMapper.delete(clientId);
    }
}
