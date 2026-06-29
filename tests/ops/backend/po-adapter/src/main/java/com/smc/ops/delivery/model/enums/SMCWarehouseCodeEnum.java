package com.smc.ops.delivery.model.enums;

/**
 * @author ：C14717
 * @version: $
 * @description：
 * @date ：Created in 2024/4/25 12:53
 */
public enum SMCWarehouseCodeEnum {

    CN0KBJ("CN0-KBJ","KBJ","自动化北京仓"),
    CN0KSH("CN0-KSH","KSH","自动化上海仓"),
    CN0SCZ("CN0-SCZ","SCZ","自动化常州仓"),
    CNCMBJ("CNC-MBJ","MBJ","中国仓库"),
    CNGMGZ("CNG-MGZ","MGZ","广州制造仓库"),;

    private String  code;

    private String  code1;
    private String codeName;

    SMCWarehouseCodeEnum(String code,String code1, String codeName) {
        this.code = code;
        this.code1 = code1;
        this.codeName = codeName;
    }

    public String getCode1() {
        return code1;
    }

    public String getCode() {
        return code;
    }

    public String getCodeName() {
        return codeName;
    }
}
