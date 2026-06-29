package com.smc.smccloud.model;

import lombok.Data;

import java.util.Date;

@Data
public class OpsDelayOrderDO {
    private String rorderFno;
    private String rorderNo;
    private String rorderItem;
    private String dlvEntire;
    private Date maxEntryTime;
}
