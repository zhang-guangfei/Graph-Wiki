package com.smc.smccloud.model.opsreturn;

import lombok.Data;

import java.util.List;

/**
 * @Author lyc
 * @Date 2022/1/6 15:52
 * @Descripton TODO
 */
@Data
public class BcakCallOrderData {
    private String applyNo;
    private String applyType;
    private String orderNo;
    private List<DetailItem> detailItem;

}
