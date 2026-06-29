package com.smc.smccloud.model.orderstate;

import java.util.Arrays;

public enum OrderSateDateType {
    OrderDate(1,"下单日期"),
    ReceiveDate(2,"接单日期"),
    CustDlvDate(3,"客户交货期"),
    CustShipDate(4,"客户要求发货日期"),
    PoDate(5,"采购日期"),
    SupplierRcvtime(6,"供应商接单日期"),
    PoReplyDate(7,"工厂纳期"),
    PoShipDate(8,"实际发出日"),
    EsArriveDate(9,"采购预计到达日期"),
    PoFacExpDate(10,"实际工厂出荷日");

    private  int code;
    private  String dateName;

    OrderSateDateType(int code,String dateName)
    {
        this.code=code;
        this.dateName=dateName;
    }


    public  int code()
    {
        return this.code;
    }
    public  String dateName()
    {
        return this.dateName;
    }


    public  static OrderSateDateType getByName(String name)
    {
               OrderSateDateType dateType = Arrays.stream(OrderSateDateType.values())
                .filter(o->o.toString().equalsIgnoreCase(name)).findAny().orElse(null);
               return  dateType;
    }


}
