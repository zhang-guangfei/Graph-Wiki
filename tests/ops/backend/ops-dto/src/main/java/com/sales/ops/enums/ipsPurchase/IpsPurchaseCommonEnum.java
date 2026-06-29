package com.sales.ops.enums.ipsPurchase;

/**
 * @author ：B91717
 * ips采购单发送，公共的枚举类
 */
public enum IpsPurchaseCommonEnum {

    IPS_PURCHASE_SEND_DICT("6010","IPS采购发单开关的字典id"),

    IPS_WAREHOUSE_CONVERT_DICT("6011","向IPS采购发单时收货仓转换的字典id"),

    IPS_CUSTOMERTYPE_DICT("6013","向IPS采购发单时南北方客户获取"),

    IPS_REMARK_CONVERT_DICT("6014","向IPS采购发单时南北方客户获取"),
    GZ_SWITCH("GZ","OPS采购发单,广州制造的开关配置"),
    MANU_SWITCH("manu","OPS采购发单，中国制造的开关配置"),
    IPS_SEND_SOURCE_SYSTEM("OPS","ops发单给IPS的来源系统配置"),

    IPS_SEND_UNIT("17","ops发单给IPS的默认发单单位"),

    IPS_SEND_3C_FLAG("3C","ops发单给IPS的默认发单单位"),

    REDIS_KEY_SUPPLIER_ADAPTER("ops:purchase:ipssupplier:","OPS与IPS供应商之间的转换"),

    REDIS_KEY_TRANSTYPE_CONVERT("ops:purchase:ipstranstype:","OPS与IPS运输方式之间的转换"),

    REDIS_KEY_WAREHOUSE_INFO("ops:purchase:ipsaddressinfo:","OPS与IPS运输方式之间的转换"),

    REDIS_KEY_IPS_BATCHNO("ops:purchase:ipsbatchno:","OPS发送IPS时批次号的生成"),

    REDIS_KEY_IPS_MANU_ACCEPTER("ops:purchase:manuaccepter:","OPS发送IPS时ACCEPTER赋值"),

    REDIS_KEY_IPS_MANU_CUSTOMERTYPE("ops:purchase:manucustomertype:","OPS发送IPS时南北方客户赋值"),

    JP_SWITCH("JP","OPS采购发单,日本的开关配置"),

    OVERSEA_SWITCH("OVERSEA","OPS采购发单,海外发单的开关配置");


    private String code;
    private String codeName;

    IpsPurchaseCommonEnum(String code, String codeName) {
        this.code = code;
        this.codeName = codeName;
    }

    public static String  getCodeByCodeName(String codeName) {

        for (IpsPurchaseCommonEnum obj : IpsPurchaseCommonEnum.values()) {
            if (obj.codeName.equals(codeName)) {
                return obj.getCode();
            }
        }
        return null;
    }

    public String getCode() {
        return code;
    }

    public String getCodeName() {
        return codeName;
    }



}
