package com.sales.ops.dto.inventory;

import java.util.List;

/**
 * @author C02483
 * @version 1.0
 * @description: 发票导入DTO
 * @date 2021/11/12 13:14
 */
public class ImportInvoniceForPoDto {

    private String invoiceno;
    private String orderno;
    private Integer itemno;
    private Integer splititemno;
    private String warehouseCode;
    private String supplierId;
    private String transType;

    private List<ImportInvoniceItemForPoDto> itemForPoDtoList;

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
    }

    public String getInvoiceno() {
        return invoiceno;
    }

    public void setInvoiceno(String invoiceno) {
        this.invoiceno = invoiceno;
    }

    public String getOrderno() {
        return orderno;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno;
    }

    public Integer getItemno() {
        return itemno;
    }

    public void setItemno(Integer itemno) {
        this.itemno = itemno;
    }

    public Integer getSplititemno() {
        return splititemno;
    }

    public void setSplititemno(Integer splititemno) {
        this.splititemno = splititemno;
    }

    public List<ImportInvoniceItemForPoDto> getItemForPoDtoList() {
        return itemForPoDtoList;
    }

    public void setItemForPoDtoList(List<ImportInvoniceItemForPoDto> itemForPoDtoList) {
        this.itemForPoDtoList = itemForPoDtoList;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public String getTransType() {
        return transType;
    }

    public void setTransType(String transType) {
        this.transType = transType;
    }
}
