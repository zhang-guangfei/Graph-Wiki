package com.smc.ops.delivery.model.enums;

/**
 * @Author lyc
 * @Date 2024/2/23 8:33
 * @Descripton TODO
 */

public enum  HandleResultEnum {
    notHand("0","未处理"),
    alreadyHand("1","已处理"),
    notAccepted("2","po不接收,带人工确认"),
    handError("9","异常");

    private String code;
    private String codeName;


    HandleResultEnum(String code, String codeName) {
        this.code = code;
        this.codeName = codeName;
    }

    public String getCode() {
        return code;
    }

    public String getCodeName() {
        return codeName;
    }
}
