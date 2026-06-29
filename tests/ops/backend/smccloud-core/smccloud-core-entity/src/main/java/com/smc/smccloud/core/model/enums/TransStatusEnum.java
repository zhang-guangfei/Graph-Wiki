package com.smc.smccloud.core.model.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * @author edp04
 * @title: TransStatusEnum
 * @date 2022/08/18 10:42
 */
public enum TransStatusEnum {

    init(0, "初始化"),
    daichuku(1, "待出库"),
    chukuzhong(2, "出库中"),
    yichuku(3, "已出库"),
    daohuodaichuli(4, "到货待处理"),
    daohuoqueren(5, "到货确认"),
    yiruku(6, "已入库"),
    cancel(9, "取消");


    private Integer code;
    private String codeName;

    TransStatusEnum(Integer code, String codeName) {
        this.code = code;
        this.codeName = codeName;
    }

    public Integer getCode() {
        return code;
    }

    public String getCodeName() {
        return codeName;
    }

    public static String getName (Integer code) {
        if(code == null) {
            return null;
        }
        for (TransStatusEnum item : TransStatusEnum.values()) {
            if (item.getCode() == code) {
                return item.codeName;
            }
        }
        return null;
    }

    public static Integer getCodeByName (String name) {
        for (TransStatusEnum item : TransStatusEnum.values()) {
            if (item.getCodeName().equals(name)) {
                return item.code;
            }
        }
        return null;
    }
}
