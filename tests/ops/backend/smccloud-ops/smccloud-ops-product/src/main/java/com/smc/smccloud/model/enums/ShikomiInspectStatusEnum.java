package com.smc.smccloud.model.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * @Author lyc
 * @Date 2024/3/26 16:12
 * @Descripton 点检状态
 */
public enum ShikomiInspectStatusEnum {
    ddj(1,"待点检"),
    dsp(2,"待审批"),
    djwc(3,"点检完成");

    private Integer code;
    private String codeName;

    ShikomiInspectStatusEnum(Integer code, String codeName) {
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
        for (ShikomiInspectStatusEnum item : ShikomiInspectStatusEnum.values()) {
            if (item.getCode().equals(code)) {
                return item.getCodeName();
            }
        }
        return "";
    }


}
