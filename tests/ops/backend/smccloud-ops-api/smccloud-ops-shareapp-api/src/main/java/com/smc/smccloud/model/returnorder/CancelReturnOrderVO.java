package com.smc.smccloud.model.returnorder;

import lombok.Data;

/**
 * @Author lyc
 * @Date 2022/9/16 10:37
 * @Descripton TODO
 */
@Data
public class CancelReturnOrderVO {

    private String[] ids;

    private String reason;
}
