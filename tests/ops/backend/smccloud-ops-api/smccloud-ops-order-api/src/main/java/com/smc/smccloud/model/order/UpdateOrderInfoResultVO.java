package com.smc.smccloud.model.order;

import lombok.Data;

/**
 * @Author lyc
 * @Date 2022/9/22 12:48
 * @Descripton TODO
 */
@Data
public class UpdateOrderInfoResultVO<T> {

    private String code;

    private T data;

    private Boolean success;

    private String message;

}
