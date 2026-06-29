package com.sales.ops.dto.inventory;

import java.io.Serializable;

/**
 * @author C02483
 * @version 1.0
 * @description: 采购导入发票号
 * @date 2021/10/22 15:33
 */
public class WmRoImportBarcodeDto implements Serializable {

    private String invoiceno;

    private String poid;

    private String warehouseCode;

    private String barcode;

    private String packageCode;

    private String modelno;

    private Integer qty;

    public String getInvoiceno() {
        return invoiceno;
    }

    public void setInvoiceno(String invoiceno) {
        this.invoiceno = invoiceno;
    }

    public String getPoid() {
        return poid;
    }

    public void setPoid(String poid) {
        this.poid = poid;
    }

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getPackageCode() {
        return packageCode;
    }

    public void setPackageCode(String packageCode) {
        this.packageCode = packageCode;
    }

    public String getModelno() {
        return modelno;
    }

    public void setModelno(String modelno) {
        this.modelno = modelno;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }
}
