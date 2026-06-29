package com.sales.ops.enums;
/**
 * @author ：c02483
 * @date ：Created in 2021/11/17 18:06
 * @description：这个不用定义字典
 */
public enum OrderIDFormatEnum {
    DO_FORMAT("DO%s%s%s%s", "销售出库命名规则"),
    DBC_FORMAT("DBC%s%s%s%s", "调拨出库命名规则"),
    DBR_FORMAT("DBR%s%s%s%s", "调拨入库命名规则"),
    DBR_RANDOM_FORMAT("DBR%s%s%s%s%s", "调拨入库命名规则_随机数后缀"),
    PCO_FORMAT("PCO%s%s%s%s", "加工单命名规则"),
    RO_FORMAT("RO%s%s%s%s", "收货单命名规则"),
    DO_PCO_CHANGE_DO_FORMAT("DO%s%s%s%sPCO", "用于订单修改拆分子项不组装发货"),


    DBC_YC_FORMAT("DBC%s%s%s%sYC","异常数据调拨出库命名规则"),
    DBR_YC_FORMAT("DBR%s%s%s%sYC", "异常数据调拨入库命名规则"),

    DBC_CG_FORMAT("DBC%s%s%s%sCG","采购调拨出库命名规则"),
    DBR_CG_FORMAT("DBR%s%s%s%sCG", "采购调拨入库命名规则"),

    DO_ZD_FORMAT("DO%s%s%s%sZD", "转定销售出库命名规则"),
    DBC_ZD_FORMAT("DBC%s%s%s%sZD", "转定调拨出库命名规则"),
    DBR_ZD_FORMAT("DBR%s%s%s%sZD", "转定调拨入库命名规则"),
    PCO_ZD_FORMAT("PCO%s%s%s%sZD", "转定加工单命名规则"),
    RO_ZD_FORMAT("RO%s%s%s%sZD", "转定收货单命名规则"),
    DO_FN_FORMAT("DO%s%s%s%sFN", "分纳到货出库命名规则"),

    DBC_FN_FORMAT("DBC%s%s%s%sFN", "分纳调拨出库命名规则"),
    DBR_FN_FORMAT("DBR%s%s%s%sFN", "分纳调拨入库命名规则"),

    RO_TH_FORMAT("RO%s%s%s%s", "退货入库命名规则"),
    DBC_FN1_FORMAT("DBC%s%s%s%s%s", "分纳调拨出库命名规则v1"),
    DBR_FN1_FORMAT("DBR%s%s%s%s%s", "分纳调拨入库命名规则V1"),

    ;

    private String format;
    private String desc;

    OrderIDFormatEnum(String format, String desc) {
        this.format = format;
        this.desc = desc;
    }

    public String getFormat() {
        return format;
    }

    public String getDesc() {
        return desc;
    }
}
