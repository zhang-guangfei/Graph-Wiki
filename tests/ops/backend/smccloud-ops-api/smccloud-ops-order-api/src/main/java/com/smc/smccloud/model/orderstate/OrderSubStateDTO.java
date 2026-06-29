package com.smc.smccloud.model.orderstate;

import lombok.Data;

/**
 * @author edp04
 * @title: OrderSubStateDTO
 * @date 2022/05/11 10:45
 */
@Data
public class OrderSubStateDTO {

    private String status;

    private String status_desc;

    private String model_no;

    private Integer quantity;

    private String sub_statuses;
}
