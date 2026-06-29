package com.smc.smccloud.model;


import lombok.Data;

import java.util.Date;

@Data
public class EmailInfo {
    private Date sendTime;
    private String strSendTime;
    private Date receiveTime;
    private String strReceiveTime;
    private String subject;
    private String sender;
    private Date parseTime;
    private String strParseTime;
    private String uid;
    private boolean hasAttachment;
    private boolean hasParse;
    private String parseResult;
    private int parseNum;
}
