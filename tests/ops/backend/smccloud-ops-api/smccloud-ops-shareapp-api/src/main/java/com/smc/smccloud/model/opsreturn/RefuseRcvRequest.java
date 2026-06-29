package com.smc.smccloud.model.opsreturn;

import lombok.Data;

import java.util.List;

/**
 * @Author lyc
 * @Date 2022/6/14 11:26
 * @Descripton TODO
 */
@Data
public class RefuseRcvRequest {
    private List<String> ids;

    private int allRefuseRcv; // 0 不  1 是
}
