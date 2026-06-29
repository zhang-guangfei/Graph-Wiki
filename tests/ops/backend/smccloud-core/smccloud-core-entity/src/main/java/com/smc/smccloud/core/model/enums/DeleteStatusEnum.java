package com.smc.smccloud.core.model.enums;

/**
 * @Author lyc
 * @Date 2022/6/30 10:07
 * @Descripton TODO
 */
public enum  DeleteStatusEnum {
    noDel(0,"未删除"),
    alreadyDel(1,"已删除"),
    deling(2,"删除中"),
    returnOrderIng(3,"退货中");

    private int code;
    private String codeName;

    DeleteStatusEnum(int code, String codeName) {
        this.code = code;
        this.codeName = codeName;
    }

    public int getCode() {
        return code;
    }

    public String getCodeName() {
        return codeName;
    }

    public static String getNameByCode(Integer delStatus) {
        if (delStatus == null) {
            return null;
        }
        for (DeleteStatusEnum item : DeleteStatusEnum.values()) {
            if (item.getCode() == delStatus) {
                return item.getCodeName();
            }
        }
        return null;
    }

}
