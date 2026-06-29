package com.smc.smccloud.model.pd;

import lombok.Data;

import java.util.List;

/**
 * @Author lyc
 * @Date 2023/8/18 13:32
 * @Descripton TODO
 */
@Data
public class RemotePdBillNoVo {
    private String pdBillNo;
    private List<String> warehouseCodes;
}
