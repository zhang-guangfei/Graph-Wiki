package com.sales.ops.dto.order;

import java.io.Serializable;

public class StartWaveParam implements Serializable {

    private static final long serialVersionUID = 4418939295882505753L;

    private String doId;
    private String waveDeliveryDate;
    private String expectedDeliveryDate;

    private String waveNo;

    public String getDoId() {
        return doId;
    }

    public void setDoId(String doId) {
        this.doId = doId;
    }

    public String getWaveDeliveryDate() {
        return waveDeliveryDate;
    }

    public void setWaveDeliveryDate(String waveDeliveryDate) {
        this.waveDeliveryDate = waveDeliveryDate;
    }

    public String getWaveNo() {
        return waveNo;
    }

    public void setWaveNo(String waveNo) {
        this.waveNo = waveNo;
    }

    public String getExpectedDeliveryDate() {
        return expectedDeliveryDate;
    }

    public void setExpectedDeliveryDate(String expectedDeliveryDate) {
        this.expectedDeliveryDate = expectedDeliveryDate;
    }
}
