package com.sales.ops.enums;

import java.util.Arrays;
import java.util.List;

/**
 * @author C12961
 * @date 2022/4/2 16:27
 */
public enum OrderSplitTypeEnum {
    CG("0", "整单采购"),
    ALL("0", "整单出库"),
    ASSQTY("1", "数量拆分"),
    ASSModelNo("2", "型号拆分"),
    ;


    private String splitType;
    private String splitDesc;


    OrderSplitTypeEnum(String splitType, String splitDesc) {
        this.splitType = splitType;
        this.splitDesc = splitDesc;
    }

    public static OrderSplitTypeEnum parse(String splitType) {
        return Arrays.stream(OrderSplitTypeEnum.values()).filter(e -> e.getSplitType().equals(splitType)).findFirst().orElse(null);
    }

    public String getSplitType() {
        return splitType;
    }

    public void setSplitType(String splitType) {
        this.splitType = splitType;
    }

    public String getSplitDesc() {
        return splitDesc;
    }

    public void setSplitDesc(String splitDesc) {
        this.splitDesc = splitDesc;
    }

    public static boolean notAssModel(String splitType) {
        List<String> list = Arrays.asList(ALL.getSplitType(), ASSQTY.getSplitType());
        return list.contains(splitType);
    }


}
