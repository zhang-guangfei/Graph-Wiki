package com.sales.ops.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 在途状态排序
 *
 * @auther C14717
 * @date 2021/11/18 8:10
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum InvIndexStatusEnum {

    PRODUCE("P", 1,0,"生产在途"),
    CGTRANS("T1", 2,0,"采购在途"),
    DBTRANS("T3", 3,0,"调拨在途"),
    W4("W", 4,4,"到货未入库"),
    W5("W", 5,5,"到货未入库"),// W5不拆 sort=0 W5拆 sort = 5 且 需要修改获取可拆数量代码 OptStatusEnum.GOODS_CONFIRM.getCode().equals(inventoryMove.getOptStatus())
    ;

    InvIndexStatusEnum(String code,Integer sort,Integer optStatus, String desc) {
        this.code = code;
        this.desc = desc;
        this.sort = sort;
        this.optStatus = optStatus;
    }

    public static InvIndexStatusEnum getEnumByType(String type, Integer optStatus) {
        if(InvIndexStatusEnum.W5.code.equals(type)){
            if(InvIndexStatusEnum.W5.optStatus.equals(optStatus)){
                return InvIndexStatusEnum.W5;
            }
        }
        return Arrays.stream(InvIndexStatusEnum.values()).filter(Enum -> Enum.getCode().equals(type)).findFirst().orElse(null);
    }

    private String code;

    private Integer sort;
    private String desc;

    private Integer optStatus;

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public Integer getSort() {
        return sort;
    }

    public Integer getOptStatus() {
        return optStatus;
    }
}