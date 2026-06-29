package com.smc.smccloud.model.adapter.order;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Data
public class OrderDetailDTO {

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date from_time;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date to_time;

    private String fromTimeStr;

    private String toTimeStr;

    private int date_type;

    private Integer order_state;

    private List<String> order_nos;

    private List<String> corder_nos;

    private List<String> agentNo;

}
