package com.sales.ops.common.until;

import com.google.gson.Gson;
import com.smc.smccloud.core.model.constants.GlobalConstant;
import com.smc.smccloud.core.model.dto.ClientDetails;
import com.smc.smccloud.core.utils.HttpRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class MenhuTokenUtil {

    @Value("${menhu.url}")
    private String menHuUrl;
    private String clientId = "f4dcb3e91cf54913b170d31893a10151";
    private String clientSecret = "bcc6c35c713941a3886eb59ce5bf8067";
    private String scope = "saleManageSystem";

    public String getMHToken() {
        String token_url = menHuUrl + "/manage/oauth/token";
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("grant_type", GlobalConstant.GrantType.CLIANT_CREDENTIALS);
        paramMap.put("scope", scope);
        paramMap.put("client_id", clientId);
        paramMap.put("client_secret", clientSecret);
        String response = HttpRequest.doPost(token_url, paramMap, null);
        ClientDetails clientDetails = new Gson().fromJson(response, ClientDetails.class);
        if (null == clientDetails || StringUtils.isBlank(clientDetails.getAccess_token())) {
            return null;
        }
        return "Bearer " + clientDetails.getAccess_token();
    }

    public String getMenHuUrl(){
        return this.menHuUrl;
    }


}