package com.smc.smccloud.model.invoice;

public enum POInvoiceState {

    Imported(1,"发票入库"),
    Arrived(2,"物流已签收"),
    CostBalanced(3,"成本结算");

    private  int code;
    private  String stateName;

    POInvoiceState(int code,String stateName)
    {
        this.code=code;
        this.stateName=stateName;
    }

    public int code()
    {
        return this.code;
    }
    public String stateName()
    {
        return this.name();
    }
}
