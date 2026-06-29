package com.sales.ops.api.dto.wms;

import java.io.Serializable;

/**
 * @author ：C14717
 * @version: 1.0$
 * @description：WMS - > OPS  5.3 出库确认回传
 * @date ：Created in 2021/11/2 9:44
 */
public class OpsDoItemConfirmDto implements Serializable {

    private static final long serialVersionUID = -2703961421237772368L;

    private String logisticsCode; //承运商代码 TRUE
    private String expressCode; //运单号 TRUE
    private Integer qty; //数量 TRUE
    private String barCode; //出库箱号 TRUE
    private String packageCode; //出库托号 TRUE

    public String getLogisticsCode() {
        return logisticsCode;
    }

    public void setLogisticsCode(String logisticsCode) {
        this.logisticsCode = logisticsCode;
    }

    public String getExpressCode() {
        return expressCode;
    }

    public void setExpressCode(String expressCode) {
        this.expressCode = expressCode;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Double qty) {
        this.qty = qty.intValue();
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public String getPackageCode() {
        return packageCode;
    }

    public void setPackageCode(String packageCode) {
        this.packageCode = packageCode;
    }
}
