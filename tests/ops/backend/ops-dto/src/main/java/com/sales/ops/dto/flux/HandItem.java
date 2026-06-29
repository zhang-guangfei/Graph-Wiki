package com.sales.ops.dto.flux;

/**
 * @author C02483
 * @version 1.0
 * @description: 物流交接信息
 * @date 2022/1/26 15:36
 */
public class HandItem {

    private String doid;

    private String modelno;

    private int qty;

    private String caseno;
    /**
     * 目的仓,wms传null
     */
    private String gatherWarehouseCode;


    private String barcode;

    public HandItem() {

    }

    public String getDoid() {
        return doid;
    }

    public void setDoid(String doid) {
        this.doid = doid;
    }

    public String getModelno() {
        return modelno;
    }

    public void setModelno(String modelno) {
        this.modelno = modelno;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
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
    public String getGatherWarehouseCode() {
        return gatherWarehouseCode;
    }

    public void setGatherWarehouseCode(String gatherWarehouseCode) {
        this.gatherWarehouseCode = gatherWarehouseCode;
    }

}
