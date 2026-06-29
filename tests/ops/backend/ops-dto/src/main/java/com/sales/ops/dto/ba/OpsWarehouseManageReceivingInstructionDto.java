package com.sales.ops.dto.ba;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * @author ：C14717
 * @version: 1.0$
 * @description：收货指令查询条件 入库类型、仓库、状态、供应商--入库指令号、发票号、开始时间、结束时间
 * @date ：Created in 2021/9/26 11:10
 */
public class OpsWarehouseManageReceivingInstructionDto {
    //入库类型
    private Integer roType;
    //仓库编码
    private String warehouseCode;
    //状态 收货状态
    private Integer roStatus;
    // 承运商
    private String carried;

    //供应商
    private String supplierId;

    //入库指令号
    private String roId;
    //发票号
    private String invoiceNo;
    //开始时间

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone="GMT+8")
    private Date beginTime;
    //结束时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone="GMT+8")
    private Date endTime;

    public Integer getRoType() {
        return roType;
    }

    public void setRoType(Integer roType) {
        this.roType = roType;
    }

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
    }

    public Integer getRoStatus() {
        return roStatus;
    }

    public void setRoStatus(Integer roStatus) {
        this.roStatus = roStatus;
    }

    public String getCarried() {
        return carried;
    }

    public void setCarried(String carried) {
        this.carried = carried;
    }

    public String getRoId() {
        return roId;
    }

    public void setRoId(String roId) {
        this.roId = roId;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }
}
