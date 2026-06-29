package com.smc.ops.delivery.model.enums;

/**
 * @author ：C14717
 * @version: $
 * @description：
 * @date ：Created in 2024/4/25 11:48
 */
public enum SMCCompanyCodeEnum {

    JP0("JP0","SMC日本"),
    CNC("CNC","SMC中国公司"),
    CNM("CNM","SMC北京制造公司"),
    CNT("CNT","SMC天津制造公司"),
    CNG("CNG","SMC广州自动化"),
    CN0("CN0","SMC自动化公司"),
    CNSH("CNSH","SMC上海制造公司"),
    CNCZ("CNCZ","SMC常州制造公司"),
    CNYZ("CNG","SMC中国公司-制造六课"),
    US0("CNG","SMC美国"),
    US1("US1","AP Tec"),;
    private String  code;
    private String codeName;

    SMCCompanyCodeEnum(String code, String codeName) {
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
