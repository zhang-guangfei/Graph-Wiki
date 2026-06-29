package com.sales.ops.dto.order;

import java.io.Serializable;

public class DoWaveParam implements Serializable {

    private static final long serialVersionUID = 4418939295882505754L;

    private String doId;
    private String waveNo;
    private String optTime;

    public String getDoId() {
        return doId;
    }

    public void setDoId(String doId) {
        this.doId = doId;
    }

    public String getWaveNo() {
        return waveNo;
    }

    public void setWaveNo(String waveNo) {
        this.waveNo = waveNo;
    }

    public String getOptTime() {
        return optTime;
    }

    public void setOptTime(String optTime) {
        this.optTime = optTime;
    }
}
