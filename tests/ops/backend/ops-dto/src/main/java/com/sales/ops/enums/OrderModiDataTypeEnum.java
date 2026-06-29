package com.sales.ops.enums;

/**
 * 订单修改表的类别 枚举
 * @author C12961
 * @date 2022/4/13 8:52
 */
public enum OrderModiDataTypeEnum {


    CANCEL("C","取消"),
    RETURN("R","退货"),
    UPDATE("U","变更"),
    CANCELPO("CP","取消采购"),
    REORDER("REORDER","转订"),
    REDISPATCH("H","重新处理"),
    ;

    private String type;
    private String desc;

    OrderModiDataTypeEnum(String type, String desc) {
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
}
