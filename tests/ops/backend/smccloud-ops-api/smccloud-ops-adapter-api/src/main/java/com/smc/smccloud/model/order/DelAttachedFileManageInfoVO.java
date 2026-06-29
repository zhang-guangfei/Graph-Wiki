package com.smc.smccloud.model.order;

import lombok.Data;

import java.util.List;

/**
 * @Author lyc
 * @Date 2023/11/15 9:13
 * @Descripton TODO
 */
@Data
public class DelAttachedFileManageInfoVO {
    private String optUser;
    private List<String> ids;
}
