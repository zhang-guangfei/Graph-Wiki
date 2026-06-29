package com.sales.ops.dto.inquiry;

import com.sales.ops.db.entity.OpsPurchaseorder;

import java.io.Serializable;
import java.util.Date;

public class InquiryQueryPurchaseDto extends OpsPurchaseorder implements Serializable {

//  需要查询另外表中的字段： qtyTrans,inventorytypecode

    private String inventorytypecode;

    private Integer qtytrans;

    // bug14435,对应日本INQA修改,新增pono，；lineitem字段
    private String pono;

    private Integer lineitem;

    private String srcDeliveryTime;

    private String producefactory; // 采购工厂

    private Date replyorderdate;

    public Date getReplyorderdate() {
        return replyorderdate;
    }

    public void setReplyorderdate(Date replyorderdate) {
        this.replyorderdate = replyorderdate;
    }

    public String getProducefactory() {
        return producefactory;
    }

    public void setProducefactory(String producefactory) {
        this.producefactory = producefactory == null ? null : producefactory.trim();
    }

    public String getSrcDeliveryTime() {
        return srcDeliveryTime;
    }

    public void setSrcDeliveryTime(String srcDeliveryTime) {
        this.srcDeliveryTime = srcDeliveryTime;
    }

    public String getPono() {
        return pono;
    }

    public void setPono(String pono) {
        this.pono = pono;
    }

    public Integer getLineitem() {
        return lineitem;
    }

    public void setLineitem(Integer lineitem) {
        this.lineitem = lineitem;
    }

    public String getInventorytypecode() {
        return inventorytypecode;
    }

    public void setInventorytypecode(String inventorytypecode) {
        this.inventorytypecode = inventorytypecode;
    }

    public Integer getQtytrans() {
        return qtytrans;
    }

    public void setQtytrans(Integer qtytrans) {
        this.qtytrans = qtytrans;
    }
}
