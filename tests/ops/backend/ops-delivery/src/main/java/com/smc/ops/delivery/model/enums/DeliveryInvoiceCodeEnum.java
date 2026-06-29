package com.smc.ops.delivery.model.enums;

/**
 * @author ：B91717
 * @version: $
 * @description： PO适配器ops_po_delivery_invoice_log_from_supplier表，code
 * @date ：Created in 2024/3/15 10:00
 */
public enum DeliveryInvoiceCodeEnum {

    L("L","箱单状态"),
    P0("P0","发票生成"),

    P1("P1","价格确认"),
    L0("L0","箱单生成"),
    L1("L1","准备通关"),
    L2("L2","开始通关"),
    L3("L3","货物通关完毕"),

    L4("L4","送达仓库");


    private String code;
    private String codeName;

    DeliveryInvoiceCodeEnum(String code, String codeName) {
        this.code = code;
        this.codeName = codeName;
    }

    public static String  getCodeByCodeName(String codeName) {

        for (DeliveryInvoiceCodeEnum obj : DeliveryInvoiceCodeEnum.values()) {
            if (obj.codeName.equals(codeName)) {
                return obj.getCode();
            }
        }
        return DeliveryInvoiceCodeEnum.L0.getCode();
    }

    public String getCode() {
        return code;
    }

    public String getCodeName() {
        return codeName;
    }
}
