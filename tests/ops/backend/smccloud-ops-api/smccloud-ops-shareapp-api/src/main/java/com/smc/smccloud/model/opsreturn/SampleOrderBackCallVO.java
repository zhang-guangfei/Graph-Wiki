package com.smc.smccloud.model.opsreturn;

import lombok.Data;

/**
 * @Author lyc
 * @Date 2022/1/6 15:51
 * @Descripton TODO
 */
@Data
public class SampleOrderBackCallVO {
    private Integer status;
    private String  reason;
    private BcakCallOrderData data;
}
