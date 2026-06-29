package com.sales.ops.serviceimpl.event.v3.status.entity;


import com.sales.ops.serviceimpl.event.v3.order.entity.JYCK;
import com.sales.ops.serviceimpl.event.v3.order.entity.OKRelation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.Objects;

@Data
public class OKRelationStatus extends RelationStatus {

    private OKRelation okRelation;
    // 物流指令统一状态码
    private StateCode stateCode;// 0未下发，1已下发，2已发货，3已签收
    // // 发票号（即运单号）
    // private String invoiceNo;


    public OKRelationStatus(OKRelation okRelation) {
        this.okRelation = okRelation;
        setStateCode();
    }

    public void setStateCode() {
        if (!Objects.equals(getRelation().getJyckDo().getIsWms(), 1)) {
            stateCode = StateCode.READY;
        } else if (Objects.equals(getRelation().getJyckDo().getIsWms(), 1) && getRelation().getOutQty() == 0) {
            stateCode = StateCode.SEND;
        } else if (getRelation().getOutQty() > 0) {
            stateCode = StateCode.DELIVERY;
        }
    }


    @AllArgsConstructor
    @Getter
    public enum StateCode {
        READY("0", "已货齐"),
        SEND("1", "已下发"),
        DELIVERY("2", "已发货"),
        SIGNED("3", "已签收"),
        ;

        private String code;
        private String desc;
    }

    @AllArgsConstructor
    @Getter
    public enum RemarkCode implements RemarkBuilder.RemarkEnum {

        DELIVERY_WAREHOUSE("发货仓"),
        SEND_TIME("下发时间"),
        WAVE_TIME("生成发货统计时间"),
        PACK_TIME("集箱时间"),
        EXPRESS_COMPANY("承运商"),
        EXPRESS_NO("运单号"),
        COLLECTION_TIME("揽收时间"),
        EXPRESS_TIME("运输时间"),
        ES_ARRIVAL_TIME("预计到达日"),
        SIGN_TIME("送达时间"),
        TMS_CALLBACK("TMS返回"),
        WM_ORDER_ID("物流指令"),
        DLV_DATE("指定物流出荷日")
        ;

        private final String desc;
    }


    @Override
    public OKRelation getRelation() {
        return okRelation;
    }

    @Override
    public void createOrderStatusItem(JYCK jyck) {
        super.createOrderStatusItem(jyck);
        orderStatusItem.setQtyIn(getRelation().getUseQty());
        orderStatusItem.setInventoryType(getRelation().getPropertyCode().getType());
        orderStatusItem.setInvoiceNo(null);
        orderStatusItem.setAssociateNo(getRelation().getAssociateNo());
    }
    public boolean afterSign() {
        return true;
    }

}
