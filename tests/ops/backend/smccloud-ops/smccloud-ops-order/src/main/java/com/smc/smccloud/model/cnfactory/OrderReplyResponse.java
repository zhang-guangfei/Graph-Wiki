package com.smc.smccloud.model.cnfactory;

import lombok.Data;

import java.util.List;

@Data
public class OrderReplyResponse {
    private  int code;
    private String message;
    private List<OrderReplyInfo> data;

}
