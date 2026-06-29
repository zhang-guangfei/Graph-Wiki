package com.smc.smccloud.core.model.wrapper;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonSerialize
public class Wrapper<T> implements Serializable {

    /**
     * 序列化标识
     */
    private static final long serialVersionUID = 4893280118017319089L;

    /**
     * 成功码.
     */
    public static final int SUCCESS_CODE = 200;

    /**
     * 成功信息.
     */
    public static final String SUCCESS_MESSAGE = "操作成功";

    /**
     * 错误码.
     */
    public static final int ERROR_CODE = 500;

    /**
     * 错误信息.
     */
    public static final String ERROR_MESSAGE = "内部异常";

    /**
     * 错误码：参数非法
     */
    public static final int ILLEGAL_ARGUMENT_CODE_ = 100;

    /**
     * 错误信息：参数非法
     */
    public static final String ILLEGAL_ARGUMENT_MESSAGE = "参数非法";

    /**
     * 编号.
     */
    private int status;

    /**
     * 信息.
     */
    private String message;

    /**
     * 结果数据
     */
    private T result;

    /**
     * 是否成功
     *
     */
    private Boolean success;

    /**
     * Instantiates a new wrapper. default status=200
     */
    Wrapper() {
        this(SUCCESS_CODE, SUCCESS_MESSAGE);
    }

    /**
     * Instantiates a new wrapper.
     *
     * @param status    the status
     * @param message the message
     */
    Wrapper(int status, String message) {
        this(status, message, null);
    }

    /**
     * Instantiates a new wrapper.
     *
     * @param status    the status
     * @param message the message
     * @param result  the result
     */
    Wrapper(int status, String message, T result) {
        super();
        this.status(status).message(message).result(result);
    }

    /**
     * Sets the 编号 , 返回自身的引用.
     *
     * @param status the new 编号
     *
     * @return the wrapper
     */
    private Wrapper<T> status(int status) {
        this.setStatus(status);
        return this;
    }

    /**
     * Sets the 信息 , 返回自身的引用.
     *
     * @param message the new 信息
     *
     * @return the wrapper
     */
    private Wrapper<T> message(String message) {
        this.setMessage(message);
        return this;
    }

    /**
     * Sets the 结果数据 , 返回自身的引用.
     *
     * @param result the new 结果数据
     *
     * @return the wrapper
     */
    public Wrapper<T> result(T result) {
        this.setResult(result);
        return this;
    }

    /**
     * 判断是否成功： 依据 Wrapper.SUCCESS_CODE == this.status
     *
     * @return status =200,true;否则 false.
     */
    @JsonIgnore
    public boolean success() {
        return Wrapper.SUCCESS_CODE == this.status;
    }

    /**
     * 判断是否成功： 依据 Wrapper.SUCCESS_CODE != this.status
     *
     * @return status !=200,true;否则 false.
     */
    @JsonIgnore
    public boolean error() {
        return !success();
    }

    /*public static <T> Wrapper<T> failure() {
        return new Wrapper(false,500,"" );
    }

    private Wrapper(boolean success, int status, String message) {
        this.success = success;
        this.status = status;
        this.message = message;
    }*/

}
