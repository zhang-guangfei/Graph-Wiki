package com.smc.smccloud.model.binorder;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author edp02 @Date 2022/7/16 9:27
 */
@Data
public class PrepareOrderVO implements Serializable {
    private Long id;

    private Long calcId;

    private Long binDetailId;

    private String orderType;

    private String poOrderNo;

    private String orderNo;

    private String modelno;

    private Integer quantity;
}
