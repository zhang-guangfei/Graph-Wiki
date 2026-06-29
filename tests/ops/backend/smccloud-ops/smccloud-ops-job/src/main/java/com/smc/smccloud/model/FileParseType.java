package com.smc.smccloud.model;

import lombok.AllArgsConstructor;

/**
 * Author: B90034
 * Date: 2021-12-24 10:52
 * Description:
 */
@AllArgsConstructor
public enum FileParseType {

    DELIVERY_FILE("DELIVERY.DAT"),
    ORDER_RECEIVE_FILE("ORDER RECEIVE REPORT"),
    SHIPPING_FILE("SHPINF"),
    JPSTOCK_FILE("製品在庫"),
    OVERSEASORdERREPLAY("海外订单返信"),
    JPSHIKOMI_REPLY_FILE("N45355F.TXT"),
    CNPRODUCTSHIKOMI_FILE("SHIKOMI剩余"),
    SUPPLIER_IMP_REPLY_FILE("供应商返信"),
    SHIKOMINOORD_FILE("仕込月報");

    private final String keyword;

    public String keyword() {
        return keyword;
    }

    /**
     * 判断是否keyword
     *
     * @param keyword keyword
     * @return boolean
     */
    public static boolean isKeyword(String keyword) {
        for (FileParseType fileParseType : FileParseType.values()) {
            if (fileParseType.keyword.equals(keyword)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 更具文件名模糊匹配keyword
     *
     * @param filename 文件名
     * @return keyword or null
     */
    public static String getKeyword(String filename) {
        for (FileParseType fileParseType : FileParseType.values()) {
            if (filename.contains(fileParseType.keyword)) {
                return fileParseType.keyword;
            }
        }
        return null;
    }

//    public static void main(String[] args) {
//    }
}
