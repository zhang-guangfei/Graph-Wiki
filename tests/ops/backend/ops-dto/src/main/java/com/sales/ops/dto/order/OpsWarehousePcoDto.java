package com.sales.ops.dto.order;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * @author ：C14717
 * @version: 1.0$
 * @description：加工指令查询条件
 * @date ：Created in 2021/9/28 11:26
 */
public class OpsWarehousePcoDto {
    //加工来源
    private Integer pcoSource;
    //状态 加工标识
    private Integer pcoStatus;
    //加工指令号
    private String pcoId;
    //仓库编码
    private String warehouseCode;
    //10位订单号
    private String subOrderId;
    //开始时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone="GMT+8")
    private Date beginTime;
    //结束时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone="GMT+8")
    private Date endTime;

    public Integer getPcoSource() {
        return pcoSource;
    }

    public void setPcoSource(Integer pcoSource) {
        this.pcoSource = pcoSource;
    }

    public Integer getPcoStatus() {
        return pcoStatus;
    }

    public void setPcoStatus(Integer pcoStatus) {
        this.pcoStatus = pcoStatus;
    }

    public String getPcoId() {
        return pcoId;
    }

    public void setPcoId(String pcoId) {
        this.pcoId = pcoId;
    }

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
    }

    public String getSubOrderId() {
        return subOrderId;
    }

    public void setSubOrderId(String subOrderId) {
        this.subOrderId = subOrderId;
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
}
