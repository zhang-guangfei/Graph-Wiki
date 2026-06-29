package com.sales.ops.enums.invoice;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ：B91717
 * @version: $
 * @description： imp_invoice同步所需的常量
 * @date ：Created in 2024/3/21 10:00
 */
public enum ImpInvoiceCommonEnum {

    JPSHIP("jpship","ship文件导入写入imp_invoicemaster创建人"),

    SHPINF("SHPINF","ship文件导入写入imp_invoice_detail_pack创建人"),

    CNIMP("CNIMP","BJ制造发货数据"),

    GZIMP("GZIMP","GZ制造发货数据"),

    GWIMP("GWIMP","关务发票导入imp_invoice时，默认创建人"),
    CNY("CNY","北京制造导入imp_invoice时，默认货币代码"),
    JPSHIPERROR1("已经存在和入库不可以再变更","ship文件导入错误1"),

    JPSHIPERROR2("生成invoice_master-id异常","ship文件导入错误2"),

    JPSHIPERROR3("[add ImpInvoiceMaster]改变行数小于 1 ,操作失败","ship文件导入错误3"),

    CNIMPERROR1("当前master表状态不可再重新导入！","北京制造导入错误1"),

    NOTCOPYDETAILTOPACK("JP;CN;CT;CM;","不复制到pack表的供应商"),
    IMPSYSN_DICTID("6002","impinvoice同步时，存储最大值的字典id"),
    IMPSYSN_DICTNAME("impInvoiceId","impinvoice同步时，字典名称");


    private String code;
    private String codeName;

    ImpInvoiceCommonEnum(String code, String codeName) {
        this.code = code;
        this.codeName = codeName;
    }

    public static String  getCodeByCodeName(String codeName) {

        for (ImpInvoiceCommonEnum obj : ImpInvoiceCommonEnum.values()) {
            if (obj.codeName.equals(codeName)) {
                return obj.getCode();
            }
        }
        return ImpInvoiceCommonEnum.JPSHIP.getCode();
    }

    public String getCode() {
        return code;
    }

    public String getCodeName() {
        return codeName;
    }

    public static List<String> impInvoiceCodeList() {
        List<String> list = new ArrayList<String>();
        list.add(DeliveryInvoiceCodeEnum.L.getCode());
        list.add(DeliveryInvoiceCodeEnum.P0.getCode());
        list.add(DeliveryInvoiceCodeEnum.P1.getCode());
        list.add(String.format("%s,%s", DeliveryInvoiceCodeEnum.L.getCode(),DeliveryInvoiceCodeEnum.P1.getCode()));
        list.add(String.format("%s,%s", DeliveryInvoiceCodeEnum.L.getCode(),DeliveryInvoiceCodeEnum.P0.getCode()));
        return list;
    }


}
