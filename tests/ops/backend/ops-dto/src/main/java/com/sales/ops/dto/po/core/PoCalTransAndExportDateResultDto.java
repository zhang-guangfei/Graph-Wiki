package com.sales.ops.dto.po.core;

import java.util.Date;
import java.util.Objects;

/**
 * @author ：C14717
 * @version: $
 * @description：
 * @date ：Created in 2025/12/15 16:05
 */
public class PoCalTransAndExportDateResultDto {

    private String calTransId; //计算运输方式

    private Date calDate; // 计算制造指定出荷日

    private Long configId;// 计算配置表

    private Boolean outInv;

    private Integer supplierInventory;// 供应商库存 qty-preQty




    private String msg;

    public PoCalTransAndExportDateResultDto(){}

    public PoCalTransAndExportDateResultDto(String calTransId, Date calDate, Long configId,Boolean outStock, Integer supplierInventory){
        this.calTransId = calTransId;
        this.calDate = calDate;
        this.configId = configId;
        if(Objects.nonNull(outStock)){
            this.outInv = outStock;
        }
        if(Objects.nonNull(supplierInventory)){
            this.supplierInventory = supplierInventory;
        }
    }

    public String getCalTransId() {
        return calTransId;
    }

    public void setCalTransId(String calTransId) {
        this.calTransId = calTransId;
    }

    public Date getCalDate() {
        return calDate;
    }

    public void setCalDate(Date calDate) {
        this.calDate = calDate;
    }

    public Long getConfigId() {
        return configId;
    }

    public void setConfigId(Long configId) {
        this.configId = configId;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Boolean getOutInv() {
        return outInv;
    }

    public void setOutInv(Boolean outInv) {
        this.outInv = outInv;
    }

    public Integer getSupplierInventory() {
        return supplierInventory;
    }

    public void setSupplierInventory(Integer supplierInventory) {
        this.supplierInventory = supplierInventory;
    }
}
