package com.smc.smccloud.model.binorder;

import lombok.Data;

@Data
public class CustomerExpFreqRequest {
    private  String[] modelNos;
    private String customerNo;
}
