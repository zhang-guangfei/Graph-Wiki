package com.sales.ops.dto.inventory;

import com.sales.ops.db.entity.InventorySupplierKey;
import lombok.Data;

import java.util.Date;

/**
 * @Author lyc
 * @Date 2024/7/12 10:37
 * @Descripton TODO
 */
public class InventorySupplierDto extends InventorySupplierKey {

    private Integer quantity;

    private Integer quantityassembly;

    private Integer quantityproduce;

    private Date updatetime;

    private Integer quantityprepare;

    private Integer binflag;

    private String binflagStr;



    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getQuantityassembly() {
        return quantityassembly;
    }

    public void setQuantityassembly(Integer quantityassembly) {
        this.quantityassembly = quantityassembly;
    }

    public Integer getQuantityproduce() {
        return quantityproduce;
    }

    public void setQuantityproduce(Integer quantityproduce) {
        this.quantityproduce = quantityproduce;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public Integer getQuantityprepare() {
        return quantityprepare;
    }

    public void setQuantityprepare(Integer quantityprepare) {
        this.quantityprepare = quantityprepare;
    }

    public Integer getBinflag() {
        return binflag;
    }

    public void setBinflag(Integer binflag) {
        this.binflag = binflag;
    }

    public String getBinflagStr() {
        return binflagStr;
    }

    public void setBinflagStr(String binflagStr) {
        this.binflagStr = binflagStr;
    }
}
