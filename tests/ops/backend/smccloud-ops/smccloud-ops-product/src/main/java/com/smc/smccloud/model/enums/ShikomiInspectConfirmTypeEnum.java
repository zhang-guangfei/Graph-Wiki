package com.smc.smccloud.model.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * @Author lyc
 * @Date 2024/3/27 16:19
 * @Descripton 营业所点检确认类型
 */
public enum ShikomiInspectConfirmTypeEnum {
    zj(1,"追加"),
    zz(2,"中止"),
    jx(3,"保留");

    private Integer code;

    private String codeName;

    ShikomiInspectConfirmTypeEnum(Integer code, String codeName) {
        this.code = code;
        this.codeName = codeName;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getCodeName() {
        return codeName;
    }

    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }

    public static String getCodeNameByCode(Integer code){
        if (code == null) {
            return "";
        }
        for (ShikomiInspectConfirmTypeEnum item : ShikomiInspectConfirmTypeEnum.values()) {
            if (item.getCode().equals(code)) {
                return item.getCodeName();
            }
        }
        return "";
    }
}
