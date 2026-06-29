package com.sales.ops.enums;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author C12961
 * @description
 * @date 2021/11/18 10:49
 */
public enum InventoryTypeEnum {

    GKPPL("GK-PPL", "顾客在库PPL"),
    GKPJ("GK-PJ", "顾客在库项目"),
    GKTY("GK-TY", "顾客在库通用"),
    TY("TY", "通用在库"),
    QB("QB_NO", "情报在库"),
    ZLCP("ZL-CP", "战略在库（产品）"),
    ZLHY("ZL-HY", "战略在库（行业）"),
    ZLJT("ZL-JT", "战略在库（集团）"),
    ZLPJ("ZL-PJ", "战略在库（PJ）");


    private String type;
    private String desc;

    InventoryTypeEnum(String type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public String getType() {
        return type;
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


    public static InventoryTypeEnum parse(String type) {
        return Arrays.stream(InventoryTypeEnum.values()).filter(item -> StringUtils.equalsIgnoreCase(item.type, type)).findFirst().orElse(null);
    }

    /**
     * 获取所有的type
     */
    public static List<String> getAllType() {
        List<String> list = new ArrayList<>();
        for (InventoryTypeEnum item : InventoryTypeEnum.values()) {
            list.add(item.getType());
        }
        return list;
    }
}
