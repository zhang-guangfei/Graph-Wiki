package com.smc.smccloud.core.model.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * @Author lyc
 * @Date 2022/5/9 13:22
 * @Descripton TODO
 */
public enum  OrderStateStatusEnum {
    JRWBORDER("11","接入外部系统单"),
    RCVORDER("12","接入订单"),
    RCVHand("13","接单处理"),
    PO_INTERCEPT("17", "采购拦截"),
    PURCHASE("20","采购中"),
    GYSRCVORDERERROR("21","供应商接单异常"),
    GYSRCVORDER("22","供应商已接单"),
    PRODUCTING("25","生产中"),
    GYSQXQR("28","供应商取消订单待确认"),
    GYSYQXORDER("29","供应商已取消订单"),
    FINISH("31","完工/发出"),
    INVOICE_IMP("36", "发票数据入库"),
    DH("41","到货"),
    YRK("42","已入库"),
    CKCLZ("51","出库处理中"),
    HQ("50","货齐"),
    YFH("61","已发货"),
    YQS("63","已签收"),
    RETURNED("64","已退货"),
    SCKPSJ("70","生成开票数据"),
    YKFP("71","已开发票"),
    YSDDQR("90","已删单待确认"),
    YSDYQR("91","已删单已确认");

    private String code;
    private String codeName;

    OrderStateStatusEnum(String code, String codeName) {
        this.code = code;
        this.codeName = codeName;
    }

    public String getCode() {
        return code;
    }

    public String getCodeName() {
        return codeName;
    }

    public static String getName(String code)
    {
        if (StringUtils.isBlank(code)) {
            return null;
        }

        for (OrderStateStatusEnum item : OrderStateStatusEnum.values())
        {
            if (item.getCode().equals(code)) {
                return item.getCodeName();
            }
        }
        return null;
    }

    public static String getCodeByName (String name)
    {
        if (StringUtils.isBlank(name)) {
            return null;
        }

        for (OrderStateStatusEnum item : OrderStateStatusEnum.values())
        {
            if (item.getCodeName().equals(name)) {
                return item.getCode();
            }
        }
        return null;
    }

}
