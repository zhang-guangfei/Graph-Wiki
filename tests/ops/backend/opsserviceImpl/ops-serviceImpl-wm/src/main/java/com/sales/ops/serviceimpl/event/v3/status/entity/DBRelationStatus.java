package com.sales.ops.serviceimpl.event.v3.status.entity;


import com.sales.ops.enums.InventoryTableTypeEnum;
import com.sales.ops.enums.OptStatusEnum;
import com.sales.ops.serviceimpl.event.v3.order.entity.DBRelation;
import com.sales.ops.serviceimpl.event.v3.order.entity.JYCK;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
public class DBRelationStatus extends RelationStatus {

    private DBRelation dbRelation;
    private StateCode stateCode;// 0未下发，1已下发，2已发货


    public DBRelationStatus(DBRelation dbRelation) {
        this.dbRelation = dbRelation;
        setStateCode();
    }

    private void setStateCode() {
        // 未下发
        if (!isWms()) {
            stateCode = StateCode.READY;
        }
        // 已下发
        else if (getRelation().getInvTable() == InventoryTableTypeEnum.NORMAL) {
            stateCode = StateCode.SEND;
        }
        // 已发货
        else if (getRelation().getInvTable() == InventoryTableTypeEnum.TRANS && !isSign() && !isConfirm()) {
            stateCode = StateCode.DELIVERY;
        }
        // 已签收
        else if (isSign() || isConfirm()) {
            stateCode = StateCode.SIGNED;
        }
    }


    @Getter
    @AllArgsConstructor
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

        DELIVERY_DATE("指定物流出库日"),
        WM_ORDER_ID("调拨单号"),
        DELIVERY_WAREHOUSE("调拨发货仓"),

        ES_DELIVERY_DATE("调拨预计出库日"),
        INVOICE_NO("调拨批次号"),// 发票号,
        EXPRESS_COMPANY("承运商"),
        EXPRESS_NO("运单号"),
        RECEIVE_WAREHOUSE("调拨收货仓"),
        SIGN_TIME("签收时间"),
        RECEIVE_TIME("收货处理时间"),

        ;
        private final String desc;

    }


    @Override
    public DBRelation getRelation() {
        return dbRelation;
    }

    @Override
    public void createOrderStatusItem(JYCK jyck) {
        super.createOrderStatusItem(jyck);
        orderStatusItem.setQtyIn(0);
        orderStatusItem.setInventoryType(getRelation().getInventoryTypeCode());
        orderStatusItem.setInvoiceNo(getRelation().getInvoiceNo()); // 调拨DoId
        orderStatusItem.setAssociateNo(getRelation().getAssociateNo()); // move.invoiceNo
    }

    public boolean isWms() {
        return getRelation().getDbDo().getIsWms() == 1;
    }

    // wmsStatus: 0已下发，1已组波次，2已拣货
    public boolean isTaskSend() {
        return isWms() && (getRelation().getDbDo().getWmsStatus() == null || getRelation().getDbDo().getWmsStatus() == 0);
    }

    public boolean isJoinWave() {
        return getRelation().getDbDo().getWmsStatus() == 1;
    }

    public boolean isPackage() {
        return getRelation().getDbDo().getWmsStatus() > 1;
    }


    public boolean isSign() {
        return getRelation().getOptStatus() == OptStatusEnum.INVOICE_SIGN;
    }

    public boolean isConfirm() {
        return getRelation().getOptStatus() == OptStatusEnum.GOODS_CONFIRM;
    }

    public boolean afterSign() {
        // 此处的已签收代表调拨已签收和采购已签收，都算作已签收，因为要计算合并状态
        return stateCode == StateCode.SIGNED || stateCode == StateCode.READY;
    }


}
