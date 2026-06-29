package com.sales.ops.interceptor;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.smc.smccloud.core.model.constants.GlobalConstant;
import com.smc.smccloud.core.model.dto.ClientDetails;
import com.smc.smccloud.core.utils.HttpRequest;
import com.smc.smccloud.core.utils.JwtUtil;
import com.smc.smccloud.core.utils.ThreadLocalMapUtil;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author ：C14717
 * @version: 1.0$
 * @description：拦截器
 * @date ：Created in 2021/12/8 15:52
 */
@Component
@Slf4j
public class SecuringRequestInterceptor implements RequestInterceptor {

    @Value("${smccloud.oauth2.client.clientId}")
    private String clientId;

    @Value("${smccloud.oauth2.client.clientSecret}")
    private String clientSecret;

    @Value("${smccloud.oauth2.client.access-token-uri}")
    private String accessTokeUri;

    private final static  String TOKEN_HEADER="Authorization";
    private final static  String GRANT_TYPE_HEADER="grant_type";

    @Override
    public void apply(RequestTemplate requestTemplate) {
        try {
            if("ba-service".equals(requestTemplate.feignTarget().name())
                    || "mdmFeign".equals(requestTemplate.feignTarget().name())
                    || "wm-service".equals(requestTemplate.feignTarget().name())
                    || "po-service".equals(requestTemplate.feignTarget().name())
                    || "opsApi".equals(requestTemplate.feignTarget().name())){//opsApi
                return;
            }
            Object tokenObj = ThreadLocalMapUtil.get(TOKEN_HEADER);
            if(tokenObj!=null)
            {
                String token = StringUtils.substringAfter(tokenObj.toString(), "Bearer ");
                if(StringUtils.isNotEmpty(token)){
                    boolean valid = JwtUtil.verifyToken(token);
                    if (valid) {
                        requestTemplate.header(TOKEN_HEADER, tokenObj.toString());
                        requestTemplate.header(GRANT_TYPE_HEADER, GlobalConstant.GrantType.CLIANT_CREDENTIALS);
                        return ;
                    }
                }

            }

            String param = "grant_type=" + GlobalConstant.GrantType.CLIANT_CREDENTIALS + "&client_id=" + clientId + "&client_secret=" + clientSecret;
            String s = HttpRequest.sendPost(accessTokeUri, param);

            ClientDetails clientDetails = new Gson().fromJson(s, ClientDetails.class);
            // System.out.println("clientDetails = " + clientDetails.getAccess_token());
            // 设置token bugid:18983
            if(Objects.nonNull(clientDetails)){
                String token = "Bearer " + clientDetails.getAccess_token();
                requestTemplate.header(TOKEN_HEADER, token);
                requestTemplate.header(GRANT_TYPE_HEADER, GlobalConstant.GrantType.CLIANT_CREDENTIALS);
                ThreadLocalMapUtil.put(TOKEN_HEADER, token);
            }
        } catch (JsonSyntaxException e) {
            log.error("{}",e);
        }
    }


}
