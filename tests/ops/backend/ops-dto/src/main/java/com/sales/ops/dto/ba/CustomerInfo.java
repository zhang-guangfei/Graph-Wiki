package com.sales.ops.dto.ba;

import com.sales.ops.db.entity.OpsCustomer;
import com.sales.ops.db.entity.OpsCustomerAddress;

import java.io.Serializable;
import java.util.List;

/**
 * @description
 * @date 2021/10/29 15:24
 * @auther C12961
 */
public class CustomerInfo implements Serializable {

    private OpsCustomer opsCustomer;
    private List<OpsCustomerAddress> addrList;

    public CustomerInfo(OpsCustomer opsCustomer, List<OpsCustomerAddress> list) {
        this.opsCustomer = opsCustomer;
        this.addrList = list;
    }

    public CustomerInfo() {
    }


    public OpsCustomer getOpsCustomer() {
        return opsCustomer;
    }

    public void setOpsCustomer(OpsCustomer opsCustomer) {
        this.opsCustomer = opsCustomer;
    }

    public List getAddrList() {
        return addrList;
    }

    public void setAddrList(List addrList) {
        this.addrList = addrList;
    }
}
