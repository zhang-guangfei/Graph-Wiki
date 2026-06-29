package com.smc.smccloud.model.enums;

import com.sales.ops.enums.RequestPurchaseStatusEnum;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ：B91717
 * 采购批量修改，公共枚举
 */
public enum PurchaseModifyCommonEnum {
    ORDERNONULL("0", "订单号不能为空，请补充后重试!");

    private String code;
    private String message;

    PurchaseModifyCommonEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getType() {
        return code;
    }
    public String getDesc() {
        return message;
    }
    public static PurchaseModifyCommonEnum getType(String type) {
        for (PurchaseModifyCommonEnum typeEnum : values()) {
            if (typeEnum.code.equals(type)) {
                return typeEnum;
            }
        }
        return null;
    }

    /**
     * 可以进行采购删单的 请购单状态
     * @return
     */
    public static List<String> purchaseStatusList() {
        List<String> list = new ArrayList<String>();
        list.add(RequestPurchaseStatusEnum.CHULIZHONG);
        list.add(RequestPurchaseStatusEnum.LANJIE);
        list.add(RequestPurchaseStatusEnum.SHIKOMILANJIE);
        return list;
    }

}
