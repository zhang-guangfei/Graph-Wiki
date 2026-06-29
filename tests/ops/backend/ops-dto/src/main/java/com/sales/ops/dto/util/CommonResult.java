package com.sales.ops.dto.util;

import java.io.Serializable;

public class CommonResult<T> implements Serializable {

    private static final long serialVersionUID = -6671092062293850453L;
    private Integer code;
    private String message;
    private T data;

    public CommonResult() {
    } // openFeign

    public CommonResult(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public CommonResult(ResultCode code, T data) {
        this.code = code.code();
        this.message = code.message();
        this.data = data;
    }

    public CommonResult(ResultCode code) {
        this.code = code.code();
        this.message = code.message();
    }

    public static <T> CommonResult<T> success(String message, T data) {
        return new CommonResult<>(ResultCode.SUCCESS.code(), message, data);
    }

    public static <T> CommonResult<T> success(T data) {
        return new CommonResult<>(ResultCode.SUCCESS.code(), ResultCode.SUCCESS.message(), data);
    }

    public static <T> CommonResult<T> success() {
        return new CommonResult<>(ResultCode.SUCCESS);
    }

    public static <T> CommonResult<T> failure(String message) {
        return new CommonResult<>(ResultCode.FAILURE.code(), message, null);
    }

    public static <T> CommonResult<T> failure(Integer code,String message) {
        return new CommonResult<>(code, message, null);
    }

    public static <T> CommonResult<T> failure(T data, String message) {
        return new CommonResult<>(ResultCode.FAILURE.code(), message, data);
    }



    public static <T> CommonResult<T> failure(T data) {
        return new CommonResult<>(ResultCode.FAILURE, data);
    }

    public static <T> CommonResult<T> failure() {
        return new CommonResult<>(ResultCode.FAILURE);
    }

    public boolean isSuccess() {
        return this.code.equals(200);
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }


}