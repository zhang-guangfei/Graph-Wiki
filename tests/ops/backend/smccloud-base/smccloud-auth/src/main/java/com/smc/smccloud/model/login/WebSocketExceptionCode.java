package com.smc.smccloud.model.login;

import com.smc.smccloud.core.model.constants.BaseExceptionCode;

public class WebSocketExceptionCode  extends BaseExceptionCode {
    /**
     * 身份验证未通过
     */
    public final static String UN_AUTH = "600001";

    /**
     * 暂无即时通讯权限
     */
    public final static String NOT_HAVE_AUTH = "600002";


}
