package com.sales.ops.dto.inventory;

import com.sales.ops.db.entity.Rcvdetail;
import com.sales.ops.db.entity.Rcvmaster;

import java.io.Serializable;

/**
 * @author C02483
 * @version 1.0
 * @description: TODO
 * @date 2021/10/26 19:00
 */
public class DispatchForOrderItemInputDto implements Serializable{
    private static final long serialVersionUID = -2654688932968765614L;
    private Rcvmaster rcvmaster;
    private Rcvdetail rcvdetail;

    public DispatchForOrderItemInputDto(Rcvmaster rcvmaster, Rcvdetail rcvdetail) {
        this.rcvmaster = rcvmaster;
        this.rcvdetail = rcvdetail;
    }

    public Rcvmaster getRcvmaster() {
        return rcvmaster;
    }

    public void setRcvmaster(Rcvmaster rcvmaster) {
        this.rcvmaster = rcvmaster;
    }

    public Rcvdetail getRcvdetail() {
        return rcvdetail;
    }

    public void setRcvdetail(Rcvdetail rcvdetail) {
        this.rcvdetail = rcvdetail;
    }
}
