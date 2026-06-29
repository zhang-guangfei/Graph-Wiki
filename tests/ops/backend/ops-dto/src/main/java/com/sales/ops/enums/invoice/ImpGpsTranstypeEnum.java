package com.sales.ops.enums.invoice;

/**
 * @author ：B91717
 * @version: $
 * @description： PO适配器与集团内采购的运输方式转换
 *  code
 *
 */
public enum ImpGpsTranstypeEnum {

    SEA("0","0"), // 海运
    AIR("1","1"), // 空运
    LAND("2","3"), // 陆运
    SEAAIR("4","4"); // 快船


    private String gpsCode;
    private String opsCode;

    ImpGpsTranstypeEnum(String gpsCode, String opsCode) {
        this.gpsCode = gpsCode;
        this.opsCode = opsCode;
    }

    /**
     * 根据gpsCode获取对应的opsCode
     * @param gpsCode
     * @return
     */
    public static String  getOpsCodeByGps(String gpsCode) {

        for (ImpGpsTranstypeEnum obj : ImpGpsTranstypeEnum.values()) {
            if (obj.gpsCode.equals(gpsCode)) {
                return obj.getOpsCode();
            }
        }
        return gpsCode;
    }

    public String getGpsCode() {
        return gpsCode;
    }

    public String getOpsCode() {
        return opsCode;
    }

}
