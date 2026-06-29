package com.sales.ops.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 在途在库状态
 *
 * @auther C12961
 * @date 2021/11/18 8:10
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum InventoryStatusEnum {

    NORMAL("N", "正常在库"),
    PRODUCE("P", "生产在途"),
    T("T", "在途"),
    W("W", "到货未入库"),
    X("X", "限定"),
    REQUEST("T0", "虚拟请购"),
    CGTRANS("T1", "采购在途"),
    ZYTRANS("T2", "转运在途"),
    DBTRANS("T3", "调拨在途"),
    THTRANS("T4", "退货在途"),
    ;

    InventoryStatusEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    //判断 采购在途和调拨在途
    public static boolean containT(String value) {
        if (StringUtils.isEmpty(value)) {
            return false;
        } else {
            if (InventoryStatusEnum.CGTRANS.getCode().equals(value) || InventoryStatusEnum.DBTRANS.getCode().equals(value)) {
                return true;
            } else {
                return false;
            }
        }
    }

    public static List<String> getNormalCode() {
        List list = new ArrayList<String>();
        list.add(InventoryStatusEnum.NORMAL.getCode());
        list.add(InventoryStatusEnum.PRODUCE.getCode());
        list.add(InventoryStatusEnum.W.getCode());
        list.add(InventoryStatusEnum.X.getCode());
        return list;
    }

    public static List<String> getTransCode() {
        List list = new ArrayList<String>();
        list.add(InventoryStatusEnum.CGTRANS.getCode());
        list.add(InventoryStatusEnum.ZYTRANS.getCode());
        list.add(InventoryStatusEnum.DBTRANS.getCode());
        return list;
    }

    public static InventoryStatusEnum getEnumByType(String type) {
        return Arrays.stream(InventoryStatusEnum.values()).filter(Enum -> Enum.getCode().equals(type)).findFirst().orElse(null);
    }

    private String code;
    private String desc;

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}