package com.sales.ops.enums.invoice;

/**
 * @author ：B91717
 * @version: $
 * @description： PO适配器ops_po_delivery_invoice_log_from_supplier表，code
 * @date ：Created in 2024/3/15 10:00
 */
public enum DeliveryInvoiceCodeEnum {

    L("L","物的移动"),
    P0("P0","发票生成"),

    P1("P1","价格已确认");


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
        return codeName;
    }

    public String getCode() {
        return code;
    }

    public String getCodeName() {
        return codeName;
    }
}
