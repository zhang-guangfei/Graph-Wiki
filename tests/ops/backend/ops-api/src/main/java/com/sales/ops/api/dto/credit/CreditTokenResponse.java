package com.sales.ops.api.dto.credit;

/**
 * @author C02483
 * @version 1.0
 * @description: 信用拦截响应
 * @date 2022/1/25 11:02
 */
public class CreditTokenResponse {
    private String access_token;
    private String token_type;
    private String refresh_token;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getToken_type() {
        return token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }
}
