package com.sales.ops.dto.inventory;

import java.io.Serializable;
import java.util.List;

/**
 * @author ：C14717
 * @version: 1.0$
 * @description：出库发货Item
 * @date ：Created in 2021/10/21 14:44
 */
public class WmDoItemConfirmDto implements Serializable {

    private static final long serialVersionUID = -5728849023071471453L;

    private String logisticsCode; //承运商代码

    private String expressCode; //运单号

    private String doItem;//行项目

    private Integer qty;  //数量 整数量

    private String barCode; //出库箱号

    private String packageCode; //出库托号

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

    public String getDoItem() {
        return doItem;
    }

    public void setDoItem(String doItem) {
        this.doItem = doItem;
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

    @Override
    public String toString() {
        return "WmDoItemConfirmDto{" +
                "logisticsCode='" + logisticsCode + '\'' +
                ", expressCode='" + expressCode + '\'' +
                ", qty=" + qty +
                '}';
    }
}
