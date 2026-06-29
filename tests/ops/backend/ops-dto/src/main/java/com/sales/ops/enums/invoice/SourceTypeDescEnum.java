package com.sales.ops.enums.invoice;


/**
 * @Author lyc
 * @Date 2024/2/22 8:24
 * @Descripton TODO
 */
public enum SourceTypeDescEnum {

    OPS_V_RequisitionStausToSales("[10.116.0.30].[Manufacture].[dbo].OPS_V_RequisitionStausToSales"),
    OPS_T_ExportRequestToSales("[10.116.0.30].[Manufacture].[dbo].[OPS_T_ExportRequestToSales]"),
    supplierReplyOrderState("http://10.116.1.15:9000/order/state/supplierReplyOrderState"),
    ImpData_OPS("[10.116.192.19].[DBGZ].[dbo].[ImpData_OPS]"),
    delivery("日本DELIVERY.DAT文件"),
    shipf("SHIPF文件"),
    queryImportInvoiceInfo("中国制造/日本/海外3国[http://10.116.1.236:10100/service/sales/queryImportInvoiceInfo]"),
    manuJPShippedInfo("[10.116.0.68].[PRODUCT_JP].[dbo].[ManuJPShippedInfo]"),
    inqueryDataSending("[10.116.0.68].[PRODUCT_JP].[dbo].[InqueryDataSending]"),
    ORDER_95012("ORDER-95012.TXT文件"),

    CMInvoice("CM_InvoiceBarcodePrice"),
    GZInvoice("GZ_getInvoiceBarcodePrice"),

    PSI_CMInvoice("PSI_CM_getInvoiceBarcodePrice"),
    PSI_GZInvoice("PSI_GZ_getInvoiceBarcodePrice"),

    PSI_JPInvoice("PSI_JP_getInvoiceBarcodePrice"),

    PSI_GWInvoice("PSI_GW_getInvoiceBarcodePrice");

    private String desc;

    SourceTypeDescEnum(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
