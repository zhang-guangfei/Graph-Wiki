package com.smc.smccloud.core.model.enums;

/**
 * @Author lyc
 * @Date 2023/11/6 16:22
 * @Descripton TODO
 */
public enum  SampleBalApplyHandStatusEnum {
    notHand("0","未受理"),
    alreadyHand("1","已受理"),
    alreadyBal("2","已结转"),
    returnApply("3","退回申请"),
    balerror("4","结转失败");


    private String code;
    private String codeName;

    SampleBalApplyHandStatusEnum(String code, String codeName) {
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

    public static String getCodeNameByCode(String code) {
        if (code == null) {
            return "";
        }
        for (SampleBalApplyHandStatusEnum item : SampleBalApplyHandStatusEnum.values()) {
            if (item.getCode().equals(code)) {
                return item.getCodeName();
            }
        }
        return "";
    }
}
