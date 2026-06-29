package com.sales.ops.enums;

import java.util.Objects;

/**
 * @author ：c02483
 * @date ：Created in 2021/9/21 17:45
 * @description：出库类型：整出，单项出，随发
 */
public enum CKTYPEEnum {
    /* ALL("ALL", "整单出库"),
     ITEM("ITEM", "单项出库"),
     UNLIMIT("UNLIMIT", "无限制"),*/
    ORDER_GETHER_SIGNLE_HOUSE(1, "1", "ORDER", "货齐单仓"),//
    ORDER_GETHER_SIGNLE_HOUSE_ITEM_WMS(2, "4", "ORDER", "随发单仓"),//
    ORDER_GETHER_MULTI_HOUSE(2, "3", "ORDER", "货齐多仓"),//
    ITEM_GETHER_SIGNLE_HOUSE(3, "0", "ITEM", "随到随发"),
    ITEM_UNLIMIT(4, "2", "UNLIMIT", "随发分批"),//随发分批
    NOTIFY_UNLIMIT(5, "5", "NOTIFY", "通知发货"),//通知发货
    ;
    private Integer index;
    private String code;
    private String type;
    private String desc;

    CKTYPEEnum(Integer index, String code, String type, String desc) {
        this.index = index;
        this.code = code;
        this.type = type;
        this.desc = desc;
    }


    public static CKTYPEEnum getCode(String code) {

        for (CKTYPEEnum cktypeEnum : CKTYPEEnum.values()) {
            if (cktypeEnum.code.equals(code)) {
                return cktypeEnum;
            }
        }
        return CKTYPEEnum.ORDER_GETHER_SIGNLE_HOUSE;
    }


    public static boolean enableModify(String source, String target) {
        CKTYPEEnum sourceEnum = getCode(source);
        CKTYPEEnum targetEnum = getCode(target);
        if (Objects.equals(sourceEnum, targetEnum)) {
            // 防止A变A报错
            return true;
        }
        return sourceEnum.getIndex() < targetEnum.getIndex();
    }


    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }


    public String getType() {
        return type;
    }


}
