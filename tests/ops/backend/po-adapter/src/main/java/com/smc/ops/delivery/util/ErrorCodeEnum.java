
package com.smc.ops.delivery.util;

/**
 * ErrorCodeEnum
 */
public enum ErrorCodeEnum {

    GL_INNER_ERROR(500, "未知异常"),
    GL_PARAM_ERROR(100001, "参数异常"),
    GL_NO_AUTH(100402, "无权访问"),
    GL_AUTH_OVER_TIME(100403, "登录过期,请重新登录"),
    GL_NO_LOGIN(100404, "请登录"),
    BAD_CREDENTIALS(1001, "用户名或密码错误"),
    ACCOUNT_DISABLE(1003, "账号已禁用"),
    ACCOUNT_EXPIRED(1004, "账号已过期"),
    CREDENTIALS_EXPIRED(1006, "凭证已过期"),
    ACCESS_DENIED(1007, "不允许访问"),
    PERMISSION_DENIED(1008, "无权限访问"),
    CREDENTIALS_INVALID(1009, "凭证无效或已过期"),
    REFRESH_CREDENTIALS_INVALID(1010, "刷新凭证无效或已过期"),
    INVALID_REQUEST(1011, "无效请求或请求不接受");

    private int code;
    private String msg;

    /**
     * Msg string.
     *
     * @return the string
     */
    public String msg() {
        return msg;
    }

    /**
     * Code int.
     *
     * @return the int
     */
    public int code() {
        return code;
    }

    ErrorCodeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * Gets enum.
     *
     * @param code the code
     * @return the enum
     */
    public static ErrorCodeEnum getEnum(int code) {
        for (ErrorCodeEnum ele : ErrorCodeEnum.values()) {
            if (ele.code() == code) {
                return ele;
            }
        }
        return null;
    }
}