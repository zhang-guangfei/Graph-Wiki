package com.smc.smccloud.core.utils;

import com.google.gson.Gson;
import com.smc.smccloud.core.model.constants.GlobalConstant;
import com.smc.smccloud.core.model.dto.ClientDetails;
//import jdk.nashorn.internal.ir.annotations.Reference;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author lyc
 * @Date 2022/4/15 15:29
 * @Descripton TODO
 */


@Component
//@Reference
public class CreateTokenForOtherServer {

    @Value("${menhu.url}")
    private String menHuUrl;

    /**
     * 门户 退货订单回调接口所需token认证
     */
    public String clientId = "f4dcb3e91cf54913b170d31893a10151";
    public String clientSecret = "bcc6c35c713941a3886eb59ce5bf8067";
    public String scope = "saleManageSystem";

    public String getMHToken() {
        String token_url = menHuUrl +  "/manage/oauth/token";
        Map<String, String> paramMap = new HashMap<>();
        System.out.println("url:  == > " + menHuUrl);
        paramMap.put("grant_type", GlobalConstant.GrantType.CLIANT_CREDENTIALS);
        paramMap.put("scope", scope);
        paramMap.put("client_id", clientId);
        paramMap.put("client_secret", clientSecret);
        String response = HttpRequest.doPost(token_url, paramMap, null);
        System.out.println("response = " + response);
        ClientDetails clientDetails = new Gson().fromJson(response, ClientDetails.class);
        if (null == clientDetails || StringUtils.isBlank(clientDetails.getAccess_token())) {
            return null;
        }
        return "Bearer " + clientDetails.getAccess_token();
    }

    public String getMHToken(String url) {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("grant_type", GlobalConstant.GrantType.CLIANT_CREDENTIALS);
        paramMap.put("scope", scope);
        paramMap.put("client_id", clientId);
        paramMap.put("client_secret", clientSecret);
        String response = null;
        response = HttpRequest.doPost(url, paramMap, null);
        System.out.println("response = " + response);
        ClientDetails clientDetails = new Gson().fromJson(response, ClientDetails.class);
        if (null == clientDetails || StringUtils.isBlank(clientDetails.getAccess_token())) {
            return null;
        }
        return "Bearer " + clientDetails.getAccess_token();
    }




}
