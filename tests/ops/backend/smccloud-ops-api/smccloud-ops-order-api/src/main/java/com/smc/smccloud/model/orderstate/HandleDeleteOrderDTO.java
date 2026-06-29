package com.smc.smccloud.model.orderstate;

import lombok.Data;

@Data
public class HandleDeleteOrderDTO {
    private  String orderNo;
    private  String corderNo;
    private  String customerNo;
    private  String optUser;
    private  String remark;
}
