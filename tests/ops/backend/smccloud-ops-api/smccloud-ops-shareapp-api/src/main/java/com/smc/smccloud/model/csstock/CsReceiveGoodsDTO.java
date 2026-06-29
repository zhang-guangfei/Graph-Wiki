package com.smc.smccloud.model.csstock;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class CsReceiveGoodsDTO {

    private String deliveryNo;
    private String receivePsn;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date receiveTime;
    private String warehouseCode;
}
