package com.smc.smccloud.core.model.enums;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.annotation.Order;

/**
 * @Author lyc
 * @Date 2022/4/24 9:59
 * @Descripton 订单类型
 */
public enum OrderTypeEnum {
    saleOrder("1","销售订单"),
    gnjtOrder("11","国内集团销售订单"),
    ybmyOrder("12","一般贸易订单"),
    binbkOrder("20","BIN补库订单"),
    xxbkOrder("21","先行补库订单"),
    drcgOrder("24","DR采购订单"),
    crcgOrder("25","CR采购订单"),
    wtzkOrder("3","服务备库订单"),
    dbOrder("6","调拨单"),
    ypOrder("9","样品订单")
    ;


    private String code;
    private String codeName;

    OrderTypeEnum(String code, String codeName) {
        this.code = code;
        this.codeName = codeName;
    }

    public String getCode() {
        return code;
    }

    public String getCodeName() {
        return codeName;
    }

    public static String getCodeName(String code) {
        if (StringUtils.isBlank(code)) {
            return null;
        }
        for (OrderTypeEnum item : OrderTypeEnum.values()) {
            if (item.getCode().equals(code)) {
                return item.codeName;
            }
        }
        return null;
    }

    public static String getCodeByName(String codeName) {
       if ( StringUtils.isBlank(codeName) ) {
            return null;
        }
        for (OrderTypeEnum item : OrderTypeEnum.values()) {
            if (item.getCodeName().equals(codeName)) {
                return item.getCode();
            }
        }
        return null;
    }



}
