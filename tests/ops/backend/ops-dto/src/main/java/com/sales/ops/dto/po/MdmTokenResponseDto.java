package com.sales.ops.dto.po;/**
 * @author     ：C14717
 * @version:     $
 * @description：
 * @date       ：Created in 2025/7/30 13:35
 */
public class MdmTokenResponseDto {

    private String access_token;
    private String token_type;
    private Long expires_in;
    private String scope;
    private String username;

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

    public Long getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(Long expires_in) {
        this.expires_in = expires_in;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
