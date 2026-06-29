package com.sales.ops.dto.purchase;

import com.sales.ops.db.entity.OpsRequestpurchase;

import java.io.Serializable;

public class PurchaseModifyApplyInfoDto extends OpsRequestpurchase implements Serializable {

//  需要提供的查询信息：  型号，数量，营业所，日本手拍号，采购单状态，供应商，重量，运输方式，指定工厂出荷日，客户，用户，

    private String orderFno;

    private String replyorderno;

    public String getReplyorderno() {
        return replyorderno;
    }

    public void setReplyorderno(String replyorderno) {
        this.replyorderno = replyorderno;
    }

    public String getOrderFno() {
        return orderFno;
    }

    public void setOrderFno(String orderFno) {
        this.orderFno = orderFno;
    }
}
