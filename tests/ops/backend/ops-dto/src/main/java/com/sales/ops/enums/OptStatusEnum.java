package com.sales.ops.enums;

import java.util.Arrays;

/**
 * @author ：c02483
 * @date ：Created in 2021/12/31 11:49
 * @description：在途操作枚举
 */
public enum OptStatusEnum {

    PURCHASE_ACCEPT(1, "采购接单"),
    INVOICE_INIT(2, "预到货发票导入"),
    INVOICE_CONFIRM(3, "发票确认"),
    INVOICE_SIGN(4, "发票签收"),
    GOODS_CONFIRM(5, "物流到货确认"),
    ;

    private Integer code;
    private String desc;

    OptStatusEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public static OptStatusEnum getByCode(Integer code) {
        return Arrays.stream(OptStatusEnum.values()).filter(item -> item.getCode().equals(code)).findFirst().orElse(null);
    }


}
