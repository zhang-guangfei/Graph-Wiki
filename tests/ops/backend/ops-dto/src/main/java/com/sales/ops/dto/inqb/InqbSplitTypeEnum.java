package com.sales.ops.dto.inqb;

import java.util.Arrays;

/**
 * @author B91717
 */
public enum InqbSplitTypeEnum {
    NOASS("0", "不拆分"),
    ASSQTY("1", "数量拆分"),
    ASSModelNo("2", "型号拆分"),
    ;


    private String splitType;
    private String splitDesc;


    InqbSplitTypeEnum(String splitType, String splitDesc) {
        this.splitType = splitType;
        this.splitDesc = splitDesc;
    }

    public static InqbSplitTypeEnum parse(String splitType) {
        return Arrays.stream(InqbSplitTypeEnum.values()).filter(e -> e.getSplitType().equals(splitType)).findFirst().orElse(null);
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


}
