package com.sales.ops.dto.prepareOrder;


public class EditPrepareOrderAvailableUserDto {

    private String orderFno;

    private String availableCustomers;

    private String optUser;

    public String getOrderFno() {
        return orderFno;
    }

    public void setOrderFno(String orderFno) {
        this.orderFno = orderFno;
    }

    public String getAvailableCustomers() {
        return availableCustomers;
    }

    public void setAvailableCustomers(String availableCustomers) {
        this.availableCustomers = availableCustomers;
    }

    public String getOptUser() {
        return optUser;
    }

    public void setOptUser(String optUser) {
        this.optUser = optUser;
    }
}
