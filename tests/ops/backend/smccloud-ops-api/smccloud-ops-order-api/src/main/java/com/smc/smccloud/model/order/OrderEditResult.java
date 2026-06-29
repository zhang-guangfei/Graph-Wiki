package com.smc.smccloud.model.order;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author lyc
 * @Date 2022/9/29 8:55
 * @Descripton TODO
 */

@Data
public class OrderEditResult<T>  implements Serializable  {


    private String code;

    private T data;

    private Boolean success;

    private String message;


    public OrderEditResult() {
    }

    public OrderEditResult(String code, T data, Boolean success, String message) {
        this.code = code;
        this.data = data;
        this.success = success;
        this.message = message;
    }
}
