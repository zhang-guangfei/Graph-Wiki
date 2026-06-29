package com.smc.smccloud.model.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * @Author lyc
 * @Date 2024/3/28 16:46
 * @Descripton TODO
 */
public enum ShikomiInspectApplyTypeEnum {
    wsq(0,"无申请"),
    sqxj(1,"申请新建"),
    sqzz(2,"申请中止"),
    sqjx(3,"申请继续");

    private Integer code;
    private String codeName;

    ShikomiInspectApplyTypeEnum(Integer code, String codeName) {
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
        for (ShikomiInspectApplyTypeEnum item : ShikomiInspectApplyTypeEnum.values()) {
            if (item.getCode().equals(code)) {
                return item.getCodeName();
            }
        }
        return "";
    }


}
