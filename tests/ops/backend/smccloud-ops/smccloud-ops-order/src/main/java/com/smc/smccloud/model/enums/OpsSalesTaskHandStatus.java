package com.smc.smccloud.model.enums;

/**
 * @Author lyc
 * @Date 2023/7/20 9:32
 * @Descripton TODO
 */
public enum  OpsSalesTaskHandStatus {
    notHand("0","未处理"),
    hand_success("1","已处理"),

    system_error("4","系统异常"),
    hand_error("9","处理失败");

    private String code;

    private String codeName;

    OpsSalesTaskHandStatus(String code, String codeName) {
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
