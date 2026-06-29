package com.smc.smccloud.model.preaccount;

/**
 * @author lyc
 * @create 2024/06/18 16:04
 * @description  决算表状态枚举
 */
public enum PreAccountStatusEnum {

    djs(1, "待决算"),
    dsp(2, "待审批"),
    dqs(3, "待清算"),
    yqz(4, "延期中"),
    yqs(5, "已清算"),
    dcl(6, "待处理"),
    zbjs(7, "暂不决算"),
    yqdjs(8,"延期待决算")
    ;

    private int code;
    private String codeName;

    PreAccountStatusEnum(int code, String codeName) {
        this.code = code;
        this.codeName = codeName;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getCodeName() {
        return codeName;
    }

    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }

    public static String getNameByCode(int code) {
        for (PreAccountStatusEnum item : PreAccountStatusEnum.values()) {
            if (item.getCode() == code) {
                return item.getCodeName();
            }
        }
        return String.valueOf(code);
    }
}
