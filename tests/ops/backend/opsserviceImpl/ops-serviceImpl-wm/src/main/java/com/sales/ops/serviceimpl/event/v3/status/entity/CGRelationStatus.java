package com.sales.ops.serviceimpl.event.v3.status.entity;


import com.sales.ops.enums.InventoryStatusEnum;
import com.sales.ops.serviceimpl.event.v3.order.entity.CGRelation;
import com.sales.ops.serviceimpl.event.v3.order.entity.JYCK;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
public class CGRelationStatus extends RelationStatus {

    private CGRelation cgRelation;
    private StateCode stateCode;

    public CGRelationStatus(CGRelation cgRelation) {
        this.cgRelation = cgRelation;
        setStateCode();
    }

    public void setStateCode() {
        stateCode = StateCode.REQUEST;
        if (InventoryStatusEnum.PRODUCE == getRelation().getInvStatus()) {
            stateCode = StateCode.ACCEPT;
        } else if (InventoryStatusEnum.CGTRANS == getRelation().getInvStatus()) {
            stateCode = StateCode.DELIVERY;
        } else if (InventoryStatusEnum.W == getRelation().getInvStatus()) {
            stateCode = StateCode.SIGNED;
        }
    }

    @Getter
    @AllArgsConstructor
    public enum StateCode {
        REQUEST("0", "已请购"),
        ACCEPT("1", "已接单"),
        DELIVERY("2", "已发货"),
        SIGNED("3", "已签收"),
        ;

        private String code;
        private String desc;

    }

    @Getter
    @AllArgsConstructor
    public enum RemarkCode implements RemarkBuilder.RemarkEnum {

        REPO_ORDER_FNO("请购单号"),
        PO_ORDER_FNO("采购单号"),
        SUPPLIER_ID("供应商"),
        SUPPLIER_ORDERNO("供应商接单号"),

        TRANS_TYPE("指定运输方式"),
        EXPORT_DATE("指定交货期"),
        INTERCEPTION_REASON("拦截原因"),
        SEND_DATE("采购日期"),
        STOCK_TYPE("出库区分"),
        REPLY_EXPORT_DATE("返信纳期"),
        SRC_REPLY_EXPORT_DATE("返信纳期"),
        FAC_TRANS_TYPE("实际运输方式"),
        HOLON("生产Holon"),
        FAC_PROD_DATE("实际生产日"),
        FAC_EXP_DATE("实际离厂日"),

        INVOICE_NO("发票号"),
        INVOICE_ID("发票ID"),
        ES_ARRIVAL_DATE("预计到物流日"),
        RECEIVE_DATE("到场日期"),
        FAC_EXP_PROT_DATE("实际到港日"),
        CUSTOMS_DATE("报关开始日"),


        ES_ARRIVAL_WAREHOUSE("预计到货仓库"),
        SIGN_WAREHOUSE("签收仓"),
        RECEIVE_WAREHOUSE("采购收货仓"),
        SIGN_TIME("签收时间"),
        GOODS_CONFIRM_TIME("收货处理时间"),
        TRANSFER_LOGISTICS_STATUS("转运物流状态"),
        ;

        private final String desc;

    }

    @Override
    public CGRelation getRelation() {
        return cgRelation;
    }


    @Override
    public void createOrderStatusItem(JYCK jyck) {
        super.createOrderStatusItem(jyck);
        orderStatusItem.setQtyIn(0);
        orderStatusItem.setInventoryType(getRelation().getInventoryTypeCode());
        orderStatusItem.setInvoiceNo(getRelation().getInvoiceNo());
        orderStatusItem.setAssociateNo(getRelation().getRePoFullNo());
    }
    public boolean afterSign() {
        return stateCode == StateCode.SIGNED;
    }

}
