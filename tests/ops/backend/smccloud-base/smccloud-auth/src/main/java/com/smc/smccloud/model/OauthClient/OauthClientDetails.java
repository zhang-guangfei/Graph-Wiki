package com.smc.smccloud.model.OauthClient;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "oauth_client_details")
public class OauthClientDetails {

    /**
     * 编号
     */
    @TableId(value = "client_id")
    private String clientId;

    /**
     * 密钥：Encrypted
     */
    @TableField(value = "client_secret")
    private String clientSecret;

    @TableField(value = "resource_ids")
    private String resourceIds;

    /**
     * 域
     */
    @TableField(value = "scope")
    private String scope;

    /**
     * 授权模式
     * "授权码模式"：authorization_code, "密码模式": password, "刷新模式"：refresh_token, "简化模式"：implicit, "客户端模式"：client_credentials
     * 默认值 "authorization_code,refresh_token".
     */
    @TableField(value = "authorized_grant_types")
    private String authorizedGrantTypes = "authorization_code,refresh_token,password";

    /**
     * 令牌时效
     */
    @TableField(value = "access_token_validity")
    private Integer accessTokenValidity;

    /**
     * 刷新时效
     */
    @TableField(value = "refresh_token_validity")
    private Integer refreshTokenValidity;

    /**
     * 回调地址
     */
    @TableField(value = "web_server_redirect_uri")
    private String webServerRedirectUri;

    /**
     * 权限
     */
    @TableField(value = "authorities")
    private String authorities;

    // 附加信息
    @TableField(value = "additional_information")
    private String additionalInformation;

    /**
     * 客户端是否受信任。如果是信任，将跳过批准步骤
     * default false.
     */
//    @TableField(value = "client_secret")
//    private boolean trusted = false;

    /**
     * 自动放行：Value is 'true' or 'false',  default 'true'
     */
    @TableField(value = "autoapprove")
    private String autoApprove;
}
