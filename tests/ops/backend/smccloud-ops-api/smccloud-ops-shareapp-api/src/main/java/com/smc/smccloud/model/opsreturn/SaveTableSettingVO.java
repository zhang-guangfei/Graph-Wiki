package com.smc.smccloud.model.opsreturn;

import lombok.Data;

/**
 * @Author lyc
 * @Date 2024/7/26 11:25
 * @Descripton TODO
 */
@Data
public class SaveTableSettingVO {
    private String optUser;
    private String uiViewId;
    private Object jsonTableStr;
}
