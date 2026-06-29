package com.smc.smccloud.model.delivery;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @Author lyc
 * @Date 2023/8/11 10:24
 * @Descripton TODO
 */
@Data
public class WarehouseSendDateVO {

    private String orderNo;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @JSONField(format = "yyyy-MM-dd")
    private Date dlvDate;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @JSONField(format = "yyyy-MM-dd")
    private Date warehouseSendDate;
}
