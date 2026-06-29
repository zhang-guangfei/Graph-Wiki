package com.smc.smccloud.controller;

import com.google.gson.Gson;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.model.constants.GlobalConstant;
import com.smc.smccloud.core.model.dto.ClientDetails;
import com.smc.smccloud.core.utils.HttpRequest;
import com.smc.smccloud.core.utils.PublicUtil;
import com.smc.smccloud.model.LoginSsoBody;
import com.smc.smccloud.service.OAthuApi.SsoLoginTokenVerifyService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.jasypt.encryption.StringEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author C14717 bugid：14930  2024/8/14
 * @date 2024/8/14 14:14
 */
@Slf4j
@RestController
@RequestMapping("/sso")
public class SSOAuthController {

    @Autowired
    private StringEncryptor encryptor;

    @Resource
    private SsoLoginTokenVerifyService ssoTokenVerifyService;

    @Value("${smccloud.oauth2.client.clientId}")
    private String clientId;

    @Value("${smccloud.oauth2.client.clientSecret}")
    private String clientSecret;

    @Value("${smccloud.oauth2.client.access-token-uri}")
    private String accessTokeUri;

    private final static  String TOKEN_HEADER="Authorization";
    private final static  String GRANT_TYPE_HEADER="grant_type";

    @PostMapping("/login")
    public ResultVo<HashMap> login(@RequestBody LoginSsoBody loginSsoBody) {
        ResultVo<HashMap> resultVo = null;
        try {
            Map<String, String> map = new HashMap<>();
            map.put("auth_code",loginSsoBody.getUsernumber());
            map.put("token",loginSsoBody.getToken());
            Boolean verify = ssoTokenVerifyService.verify(map);
            if(verify){
                String param = "grant_type=" + GlobalConstant.GrantType.CLIANT_CREDENTIALS + "&client_id=" + clientId + "&client_secret=" + clientSecret;
                String s = HttpRequest.sendPost(accessTokeUri, param);
                ClientDetails clientDetails = new Gson().fromJson(s, ClientDetails.class);
                String tokeny = clientDetails.getAccess_token();
                String username = obtainUsername(loginSsoBody.getUsernumber());
                String enUsername =  encryptor.encrypt(username);
                resultVo = ResultVo.success();
                HashMap mapResult = new HashMap();
                mapResult.put("token",tokeny);
                mapResult.put("userCode",enUsername);
                resultVo.setData(mapResult);
            }else {
                resultVo = ResultVo.failure("登录验证失败");
            }
        } catch (Exception e) {
            log.info("单点登录失败");
            resultVo = ResultVo.failure("登录验证失败");
        }
        return resultVo;
    }
    private String obtainRedirectTo(String redirectTo) {
        if (PublicUtil.isEmpty(redirectTo)) {
            return null;
        }
        byte[] bytess = Base64.decodeBase64(redirectTo);
        String userAndDate = new String(bytess);
        return org.apache.commons.lang.StringUtils.trim(userAndDate);
    }
    private String obtainUsername(String usernumber) {
        if (PublicUtil.isEmpty(usernumber)) {
            return null;
        }

        byte[] bytess = Base64.decodeBase64(usernumber);
        String userAndDate = new String(bytess);
        int pos = userAndDate.indexOf('&');
        if (pos == -1 ) {
            return null;
        }

        String username = userAndDate.substring(0, pos);

        return org.apache.commons.lang.StringUtils.trim(username);
    }
    private String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (StringUtils.hasText(ip)) {
            return ip.indexOf(",") > 0 ? ip.substring(0, ip.indexOf(",")) : ip;
        } else {
            ip = request.getHeader("X-Real-IP");
            return StringUtils.hasText(ip) ? ip : request.getRemoteAddr();
        }
    }
}
