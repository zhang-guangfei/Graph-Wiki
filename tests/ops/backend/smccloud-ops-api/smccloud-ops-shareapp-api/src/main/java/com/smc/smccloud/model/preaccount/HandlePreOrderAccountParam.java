package com.smc.smccloud.model.preaccount;

import lombok.Data;

import java.util.List;

/**
 * @Author lyc
 * @Date 2024/7/18 15:10
 * @Descripton TODO
 */
@Data
public class HandlePreOrderAccountParam {

    private List<Long> ids;

    private String optUser;

    private String accountDesc;

    private int sureAccountQty;

}
