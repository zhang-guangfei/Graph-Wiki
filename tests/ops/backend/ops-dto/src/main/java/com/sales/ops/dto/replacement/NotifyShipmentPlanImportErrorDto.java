package com.sales.ops.dto.replacement;

import org.apache.commons.lang.StringUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * @author ：C14717
 * @version: $
 * @description：
 * @date ：Created in 2024/11/29 9:09
 */
public class NotifyShipmentPlanImportErrorDto implements Serializable {


    private String orderFno;
    private String modelNo;
    private String remark;
    public NotifyShipmentPlanImportErrorDto(){}

    public NotifyShipmentPlanImportErrorDto(String orderFno , String modelNo, String remark){
        this.orderFno = orderFno;
        this.modelNo = modelNo;
        this.remark = remark;

    }

    public String getOrderFno() {
        return orderFno;
    }

    public void setOrderFno(String orderFno) {
        this.orderFno = orderFno;
    }

    public String getModelNo() {
        return modelNo;
    }

    public void setModelNo(String modelNo) {
        this.modelNo = modelNo;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
