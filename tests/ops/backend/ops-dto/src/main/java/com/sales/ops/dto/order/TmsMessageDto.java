package com.sales.ops.dto.order;

import java.util.Date;
import java.util.Map;

/**
 * @author C12961
 * @date 2022/7/13 9:35
 */
public class TmsMessageDto {

    private String carrierid;

    private String expressno;

    private Date handoverTime;

    private Integer quantity;

    public String getCarrierid() {
        return carrierid;
    }

    public void setCarrierid(String carrierid) {
        this.carrierid = carrierid;
    }

    public String getExpressno() {
        return expressno;
    }

    public void setExpressno(String expressno) {
        this.expressno = expressno;
    }

    public Date getHandoverTime() {
        return handoverTime;
    }

    public void setHandoverTime(Date handoverTime) {
        this.handoverTime = handoverTime;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

}
