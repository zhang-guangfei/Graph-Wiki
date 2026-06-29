package com.sales.ops.common.errorEnum;


import com.sales.ops.common.opsexception.ErrorCode;

/**
 * @author C12961
 * @date 2022/10/21 8:47
 */
public enum SalesInfoErrorCodeEnum implements ErrorCode {
    SUCCESS_CANCEL(201,"情报预约取消成功"),
    NOT_FOUND_INVENTORY(202,"该情报号没有库存占用"),
    NOT_FOUND_PROPERTY(203,"该情报号不存在"),
    NON_UNIQUE_PROPERTY(503,"批属性不唯一"),
    ;

    SalesInfoErrorCodeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private Integer code;
    private String desc;

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getDesc() {
        return desc;
    }


    public static SalesInfoErrorCodeEnum getByCode(Integer code) {
        for (SalesInfoErrorCodeEnum codeEnum : SalesInfoErrorCodeEnum.values()) {
            if (codeEnum.getCode().equals(code)) {
                return codeEnum;
            }
        }
        return null;
    }

    public static String getDescByCode(Integer code) {
        for (SalesInfoErrorCodeEnum codeEnum : SalesInfoErrorCodeEnum.values()) {
            if (codeEnum.getCode().equals(code)) {
                return codeEnum.getDesc();
            }
        }
        return null;
    }

}
