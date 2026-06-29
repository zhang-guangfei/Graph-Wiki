package com.smc.ops.delivery.util;

public class BaseException extends RuntimeException {
    private static final long serialVersionUID = 3776449414775909590L;
    private String code;
    private Object[] params;

    public BaseException(String code) {
        super(SpringContextUtil.getMessage(code));
        this.code = code;
    }

    public BaseException(String code, Object... params) {
        super(SpringContextUtil.getMessage(code, params));
        this.code = code;
        this.params = params;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Object[] getParams() {
        return this.params;
    }

    public void setParams(Object[] params) {
        this.params = params;
    }
}
