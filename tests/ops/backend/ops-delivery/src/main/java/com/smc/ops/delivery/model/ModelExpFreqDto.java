package com.smc.ops.delivery.model;

import java.io.Serializable;

/**
 * @author ：C14717
 * @version: $
 * @description：
 * @date ：Created in 2024/7/9 11:40
 */
public class ModelExpFreqDto implements Serializable {

    private static final long serialVersionUID = -7067949025764113501L;
    private String modeno;
    private String stockcode;

    private Integer avgqtyof8;

    private Integer modeltype;

    public String getModeno() {
        return modeno;
    }

    public void setModeno(String modeno) {
        this.modeno = modeno;
    }

    public String getStockcode() {
        return stockcode;
    }

    public void setStockcode(String stockcode) {
        this.stockcode = stockcode;
    }

    public Integer getAvgqtyof8() {
        return avgqtyof8;
    }

    public void setAvgqtyof8(Integer avgqtyof8) {
        this.avgqtyof8 = avgqtyof8;
    }

    public Integer getModeltype() {
        return modeltype;
    }

    public void setModeltype(Integer modeltype) {
        this.modeltype = modeltype;
    }
}
