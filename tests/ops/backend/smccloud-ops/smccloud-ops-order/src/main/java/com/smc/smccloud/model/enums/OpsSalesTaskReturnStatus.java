package com.smc.smccloud.model.enums;

/**
 * @Author lyc
 * @Date 2023/7/20 9:32
 * @Descripton TODO
 */
public enum  OpsSalesTaskReturnStatus {
    notReturn("0","待返回"),
    return_success("1","已返回"),
    return_error("9","返回失败");

    private String code;

    private String codeName;

    OpsSalesTaskReturnStatus(String code, String codeName) {
        this.code = code;
        this.codeName = codeName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCodeName() {
        return codeName;
    }

    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }
}
