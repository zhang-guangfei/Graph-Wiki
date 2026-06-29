package com.smc.smccloud.service.impl;

import com.google.gson.Gson;
import com.smc.smccloud.core.exception.BusinessException;
import com.smc.smccloud.core.model.dto.ClientDetails;
import com.smc.smccloud.core.utils.HttpRequest;
import com.smc.smccloud.service.CommonOutSideInterfaceAuthService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @Author lyc
 * @Date 2023/8/30 16:05
 * @Descripton TODO
 */
@Service
public class CommonOutSideInterfaceAuthServiceImpl implements CommonOutSideInterfaceAuthService {

    @Value("${outsidesys.yingye-system-auth-url}")
    private String yingyeSysAuthUrl;

    private final static String tokenParams = "username=OPS&password=123456";

    @Override
    public String getyingyeSysToken() {
        return getToken(yingyeSysAuthUrl+"/api/auth/login", tokenParams);
    }

    public String getToken(String url, String tokenParams) {
        String auth = "";
        String s = HttpRequest.sendPost(url, tokenParams);
        if (StringUtils.isNotBlank(s)) {
            ClientDetails clientDetails = new Gson().fromJson(s, ClientDetails.class);
            if (clientDetails.getAccess_token() != null) {
                auth = clientDetails.getAccess_token();
            }
        }
        if (auth == null || "".equals(auth)) {
            throw new BusinessException("Authorization is null.");
        }
        return "Bearer " + auth;
    }
}
