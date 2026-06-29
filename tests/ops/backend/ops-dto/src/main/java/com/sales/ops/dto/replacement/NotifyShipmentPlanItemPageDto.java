package com.sales.ops.dto.replacement;

import com.sales.ops.dto.util.DateUtil;

import java.io.Serializable;
import java.util.Date;

/**
 * @author ：C14717
 * @version: $
 * @description：
 * @date ：Created in 2024/11/29 9:10
 */
public class NotifyShipmentPlanItemPageDto implements Serializable {

    private static final long serialVersionUID = 4965458975640807835L;
    private Long Id;
    private String planNo;
    private String doId;
    private Integer qty;
    private Integer isWms;

    private Integer doStatus;
    private String warehouseCode;
    private String status = "未下发";

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getPlanNo() {
        return planNo;
    }

    public void setPlanNo(String planNo) {
        this.planNo = planNo;
    }

    public String getDoId() {
        return doId;
    }

    public void setDoId(String doId) {
        this.doId = doId;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public Integer getIsWms() {
        return isWms;
    }

    public void setIsWms(Integer isWms) {
        this.isWms = isWms;
    }

    public Integer getDoStatus() {
        return doStatus;
    }

    public void setDoStatus(Integer doStatus) {
        this.doStatus = doStatus;
    }

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
    }

    public String getStatus() {
        if(this.isWms == 1){
            this.status = "已下发";
        }
        if(this.doStatus == 2){
            this.status= "部分发货";
        }
        if(this.doStatus == 3){
            this.status = "已发货";
        }
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
