package com.smc.smccloud.core.model.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * @author edp04
 * @title: VoucherTypeEnum
 * @date 2022/08/29 16:56
 */
public enum VoucherTypeEnum {
    THRK("THRK","退货入库收货"),
    CGRKGK("CGRKGK","顾客采购入库收货"),
    CGRKBK("CGRKBK","补库采购入库收货"),
    DBRK("DBRK","调拨入库收货-仓库间调拨"),

    TZRK("TZRK","调账入库"),//调账不下发wms
    TKRK("TKRK","调库入库"),
    ZHRK("ZHRK","组换入库"),

    PYRK("PYRK","盘盈入库收货"),
    QBRK("QBRK","情报占用入库"),//情报不下发wms
    QBQX("QBQX","情报占用取消"),//情报不下发wms

    JYCK("JYCK","客户交易出库单"),

    DBCK("DBCK","调拨出库"),
    CGDBCK("CGDBCK","采购调拨出库"),

    TZCK("TZCK","调账出库"),
    TKCK("TKCK","调库出库"),
    ZHCK("ZHCK","组换出库"),
    PKCK("PKCK","盘亏出库收货"),

    QBCK("QBCK","情报占用出库"),
//    QBQX("QBQX","情报占用撤销"),
    ;

    private String code;

    private String name;

    VoucherTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }

    public static String getName(String code) {
        if (StringUtils.isBlank(code)) {
            return "";
        }
        for (VoucherTypeEnum e : VoucherTypeEnum.values()) {
            if (e.getCode().equals(code)) {
                return e.getName();
            }
        }
        return null;
    }
}
