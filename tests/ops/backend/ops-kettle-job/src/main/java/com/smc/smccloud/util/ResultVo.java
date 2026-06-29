package com.smc.smccloud.util;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class ResultVo<T> implements Serializable {
    private static final long serialVersionUID = -7846546181778843081L;
    public static final String DEFAULT_SUCCESS_RESULT_CODE = "200";
    public static final String DEFAULT_ERROR_RESULT_CODE = "500";
    private boolean success = true;
    private String code;
    private String message;

    @JsonProperty("content")
    private T data;

    private ResultVo() {
    }

    public ResultVo(boolean success, String code, String message, T data) {
        this.success = success;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    private ResultVo(boolean success) {
        this.success = success;
    }

    private ResultVo(boolean success, String code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }

    private ResultVo(T data, boolean success, String code, String message) {
        this.data = data;
        this.success = success;
        this.code = code;
        this.message = message;
    }

    public static <T> ResultVo<T> newInstance() {
        return new ResultVo<>();
    }

    public static <T> ResultVo<T> success() {
        return new ResultVo<>(true);
    }

    public static <T> ResultVo<T> success(T data) {
        return new ResultVo<>(data, true, DEFAULT_SUCCESS_RESULT_CODE, "操作成功");
    }

    public static <T> ResultVo<T> success(T data, String message) {
        return new ResultVo<>(data, true, DEFAULT_SUCCESS_RESULT_CODE, message);
    }
    public static <T> ResultVo<T> successMsg(String message) {
        return new ResultVo<>(true, DEFAULT_SUCCESS_RESULT_CODE, message);
    }
    public static <T> ResultVo<T> failure() {
        return new ResultVo<>(false, DEFAULT_ERROR_RESULT_CODE, "系统错误");
    }

    public static <T> ResultVo<T> failure(String message) {
        return new ResultVo<>(false, DEFAULT_ERROR_RESULT_CODE, message);
    }

    public static <T> ResultVo<T> successByCode(String code, String message) {
        return new ResultVo<>(true, code, message);
    }

    public static <T> ResultVo<T> successDataByCode(String code, String message,T data) {
        return new ResultVo<>(data,true, code, message);
    }

    public static <T> ResultVo<T> failure(String code, String message) {
        return new ResultVo<>(false, code, message);
    }

    public static <T> ResultVo<T> failure(T data, String code, String message) {
        return new ResultVo<>(data, false, code, message);
    }

    public T getData() {
        return this.data;
    }

    public ResultVo<T> data(T data) {
        this.data = data;
        return this;
    }

    public boolean isSuccess() {
        return this.success;
    }

    public ResultVo<T> sucess(boolean success) {
        this.success = success;
        return this;
    }

    public String getCode() {
        return this.code;
    }

    public ResultVo<T> code(String code) {
        this.code = code;
        return this;
    }

    public String getMessage() {
        return this.message;
    }

    public ResultVo<T> message(String message) {
        this.message = message;
        return this;
    }
}
