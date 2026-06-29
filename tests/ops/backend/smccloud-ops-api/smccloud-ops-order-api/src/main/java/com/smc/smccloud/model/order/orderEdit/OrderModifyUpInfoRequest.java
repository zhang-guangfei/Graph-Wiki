package com.smc.smccloud.model.order.orderEdit;

import lombok.Data;

import java.util.List;

/**
 * @Author lyc
 * @Date 2023/8/8 13:08
 * @Descripton TODO
 */
@Data
public class OrderModifyUpInfoRequest {
    private String modifyType;
    private List<String> orderNoList;
}
