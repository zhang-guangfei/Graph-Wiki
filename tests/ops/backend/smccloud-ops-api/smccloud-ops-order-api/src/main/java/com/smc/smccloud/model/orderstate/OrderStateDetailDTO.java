package com.smc.smccloud.model.orderstate;

import lombok.Data;

import java.util.List;

/**
 * @author edp04
 * @title: OrderStateDetailDTO
 * @date 2022/05/11 10:40
 */
@Data
public class OrderStateDetailDTO {

    private String status;

    private String status_desc;

    private String model_no;

    private Integer quantity;

    private List<OrderSubStateDTO> sub_statuses;
}
