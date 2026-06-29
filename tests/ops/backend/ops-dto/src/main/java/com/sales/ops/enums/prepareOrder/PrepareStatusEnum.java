package com.sales.ops.enums.prepareOrder;


public enum PrepareStatusEnum {
    dcl(1, "待处理"),
    dfs(2, "待发送"),
    yfs(3, "已发送"),
    yjd(4, "已接单"),
    bkz(5, "备库中"),
    refuse(6, "已拒单"),
    djs(7, "待决算"),
    qsz(8, "清算中"),
    yqs(9, "已清算"),
    sendFail(10, "发送失败"),
    prepareFail(11, "预处理失败")
    ;

    private int code;
    private String codeName;

    PrepareStatusEnum(int code, String codeName) {
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


    public static String getCodeNameByCode(int code) {
        for (PrepareStatusEnum value : PrepareStatusEnum.values()) {
            if (value.getCode() == code) {
                return value.getCodeName();
            }
        }
        return String.valueOf(code);
    }

}
