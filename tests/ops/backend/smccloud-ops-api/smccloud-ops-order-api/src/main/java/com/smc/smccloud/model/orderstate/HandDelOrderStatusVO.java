package com.smc.smccloud.model.orderstate;

import lombok.Data;

import java.util.List;

/**
 * @Author lyc
 * @Date 2023/2/14 13:51
 * @Descripton TODO
 */
@Data
public class HandDelOrderStatusVO {
    private List<String> ids;
    private String optUserNo;
    private String optUserName;
}
