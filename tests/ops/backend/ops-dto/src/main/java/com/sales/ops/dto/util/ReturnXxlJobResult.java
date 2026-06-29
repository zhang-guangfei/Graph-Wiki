package com.sales.ops.dto.util;

import java.io.Serializable;

/**
 * @author ：C14717
 * @version: 1.0$
 * @description：xxl返回结果
 * @date ：Created in 2021/11/26 17:00
 */
public class ReturnXxlJobResult<T> implements Serializable {
    public static final long serialVersionUID = 42L;

    public static final int SUCCESS_CODE = 200;
    public static final int FAIL_CODE = 500;

    public static final ReturnXxlJobResult<String> SUCCESS = new ReturnXxlJobResult<String>(null);
    public static final ReturnXxlJobResult<String> FAIL = new ReturnXxlJobResult<String>(FAIL_CODE, null);

    private int code;
    private String msg;
    private T content;

    public ReturnXxlJobResult(){}
    public ReturnXxlJobResult(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
    public ReturnXxlJobResult(T content) {
        this.code = SUCCESS_CODE;
        this.content = content;
    }

    public int getCode() {
        return code;
    }
    public void setCode(int code) {
        this.code = code;
    }
    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }
    public T getContent() {
        return content;
    }
    public void setContent(T content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "ReturnT [code=" + code + ", msg=" + msg + ", content=" + content + "]";
    }
}
