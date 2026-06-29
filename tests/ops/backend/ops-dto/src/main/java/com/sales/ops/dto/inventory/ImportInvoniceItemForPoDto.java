package com.sales.ops.dto.inventory;

/**
 * @author C02483
 * @version 1.0
 * @description: 发票导入DTO明细
 * @date 2021/11/12 13:20
 */
public class ImportInvoniceItemForPoDto {

    private Integer itemno;
    private String modelno;
    private Integer quantity;
    private String caseno;
    private String barcode;

    public Integer getItemno() {
        return itemno;
    }

    public void setItemno(Integer itemno) {
        this.itemno = itemno;
    }

    public String getModelno() {
        return modelno;
    }

    public void setModelno(String modelno) {
        this.modelno = modelno;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getCaseno() {
        return caseno;
    }

    public void setCaseno(String caseno) {
        this.caseno = caseno;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }
}
