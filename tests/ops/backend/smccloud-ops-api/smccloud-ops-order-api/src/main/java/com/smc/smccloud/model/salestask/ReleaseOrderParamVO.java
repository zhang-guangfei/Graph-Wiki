package com.smc.smccloud.model.salestask;

import lombok.Data;

import java.util.List;

/**
 * @Author lyc
 * @Date 2023/11/18 13:55
 * @Descripton TODO
 */
@Data
public class ReleaseOrderParamVO {

    private List<String> orderNos;
    // 申请放行原因
    private String reason;

}
