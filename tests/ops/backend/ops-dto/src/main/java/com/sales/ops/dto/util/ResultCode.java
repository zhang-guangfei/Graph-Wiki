package com.sales.ops.dto.util;

public enum ResultCode {

    SUCCESS(200, "成功"),
    FAILURE(500, "失败");

    private Integer code;

    private String message;

    ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer code() {
        return this.code;
    }

    public String message() {
        return this.message;
    }
}
