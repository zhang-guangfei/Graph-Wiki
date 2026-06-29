package com.smc.smccloud.model;

/**
 * 用户登录对象
 * 
 * C14717 bugid：14930  2024/8/14
 */
public class LoginSsoBody
{
    /**
     * 用户名&时间戳 base64
     */
    private String usernumber;

    /**
     * 转发地址 base64
     */
    private String redirectTo;

    /**
     * 验证token
     */
    private String token;

    public String getUsernumber() {
        return usernumber;
    }

    public void setUsernumber(String usernumber) {
        this.usernumber = usernumber;
    }

    public String getRedirectTo() {
        return redirectTo;
    }

    public void setRedirectTo(String redirectTo) {
        this.redirectTo = redirectTo;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
