package com.smc.smccloud.model.orderstate;

public enum OrderStateEnum {

    None(0,"未知"),
    ReceiveFromOtherSys(11,"接入外部系统单"),
    ReceiveToRcvDetail(12,"接入订单"),
    ProcessOrder(13,"接单处理"),
    ProblemOrder(14,"接单异常"),
    PurchaseFault(17,"采购拦截"),
    Purchareing(20,"采购中"),
    SupplierReplyProblemOrder(21,"供应商接单异常"),
    SupplierRcvOrder(22,"供应商已接单"),
    SupplierInProd(25,"生产中"),
    SupplierCanceled(28,"供应商已取消订单待确认"),
    SupplierCancelAndConfirm(29,"供应商已取消订单"),
    SupplierFinishProd(30,"完工出厂"),
    SupplierShipped(31,"发出"),
    InvoiceImpStock(36,"发票数据入库"),

    InvoiceDeclaredCustoms(37,"发票已报关"),
    ReceivedPOOrder(41,"到货"),
    InStock(42,"已入库"),
    Exporting(51,"出库处理中"),
    GoodsReady(50,"货齐"),
    Shipped(61,"已发货"),
    Sign(63,"已签收"),
    Returned(64,"已退货"),
    // CustomerSigned(66,"客户已签收"),
    CreatedInvoiceData(70,"生成开票数据"),
    Invoiced(71,"已开发票"),
    CanceledNotConfirm(90,"已删单待确认"),
    Canceled(91,"已删单已确认");



    OrderStateEnum(int code ,String stateName) {
        this.code=code;
        this.stateName=stateName;
    }

    private  int code;
    private  String stateName;

    public  int code()
    {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public int getCode() {
        return code;
    }

    public String getStateName() {
        return stateName;
    }

    public String stateName()
    {
        return stateName;
    }


    public static String getStateNameByCode(Integer code) {
        if (code == null) {
            return null;
        }
        for (OrderStateEnum item : OrderStateEnum.values()) {
            if (code == item.getCode()) {
                return item.getStateName();
            }
        }
        return "";
    }

    public  static OrderStateEnum getFromRcvState(int rcvStatus) {
        switch (rcvStatus)
        {
            case 1:
                return OrderStateEnum.ProcessOrder;
            case 2:
                return OrderStateEnum.Purchareing;
            case 3:
                return OrderStateEnum.PurchaseFault;
            case 4:
                return OrderStateEnum.ProcessOrder;
            case 5:
                return OrderStateEnum.GoodsReady;
            case 6:
                return  OrderStateEnum.Exporting;
            case 7:
                return OrderStateEnum.Shipped;
            case 8:
                return OrderStateEnum.Returned;
            case 9:
                return OrderStateEnum.CanceledNotConfirm;
            case 10:
                return OrderStateEnum.ProblemOrder;
            case 11:
                return OrderStateEnum.ProcessOrder;
            case 12:
                return OrderStateEnum.GoodsReady;
            case 13:
                return OrderStateEnum.Invoiced;
            case 14:
                return OrderStateEnum.ProcessOrder;
            default:
                return OrderStateEnum.None;

        }
    }


    public static void main(String[] args) {
        OrderStateEnum fromRcvState = getFromRcvState(1);
        System.out.println("fromRcvState = " + fromRcvState.code);
    }

}
