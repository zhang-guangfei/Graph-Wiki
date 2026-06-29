package com.sales.ops.enums;

import java.util.Arrays;

/**
 * @author C12961
 * @date 2022/3/14 12:46
 */
public enum TransferOrderEventEnum implements EventTypeEnum {

    TRANS_WAITING("TRANS_WAIT", "调拨待出库"),
    TRANS_OUTING("TRANS_OUTING", "调拨出库中"),
    TRANS_OUTED("TRANS_OUTED", "调拨已出库"),
    TRANS_SIGN_IN("TRANS_SIGN_IN", "调拨已签收"),
    TRANS_CONFIRM_GOODS("TRANS_CONFIRM_GOODS", "到货确认"),
    TRANS_FINISHED("TRANS_FINISHED", "调拨完成")
    ;

    private final String code;
    private final String desc;


    TransferOrderEventEnum(String event, String desc) {
        this.code = event;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }


    public static TransferOrderEventEnum getEventEnum(String event) {
        return Arrays.stream(TransferOrderEventEnum.values()).filter(Enum -> Enum.getCode().equals(event)).findFirst().orElse(null);
    }
}
