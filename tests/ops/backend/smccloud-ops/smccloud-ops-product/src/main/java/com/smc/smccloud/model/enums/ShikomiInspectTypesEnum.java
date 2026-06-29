package com.smc.smccloud.model.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * @Author lyc
 * @Date 2024/3/27 16:22
 * @Descripton TODO 点检类型
 */
public enum ShikomiInspectTypesEnum {  // 优先级按照code大小 数值越大
    jxsqz(1,"继续申请中"),
    qszzz(2,"清算中止中"),
    zjsqz(3,"追加申请中"),
    dqzlx(4,"短期滞留项"),
    cqzlx(5,"长期滞留项"),
    yqx(6,"逾期项"),
    zcxhx(7,"正常消耗项");

    private Integer code;
    private String codeName;

    ShikomiInspectTypesEnum(Integer code, String codeName) {
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
        for (ShikomiInspectTypesEnum item : ShikomiInspectTypesEnum.values()) {
            if (item.getCode().equals(code)) {
                return item.getCodeName();
            }
        }
        return "";
    }
}
