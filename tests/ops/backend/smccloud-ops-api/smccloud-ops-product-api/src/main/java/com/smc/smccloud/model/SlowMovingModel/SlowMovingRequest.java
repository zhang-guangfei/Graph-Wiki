package com.smc.smccloud.model.SlowMovingModel;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.smc.smccloud.core.model.page.BaseQuery;
import lombok.Data;

import java.util.Date;

@Data
public class SlowMovingRequest extends BaseQuery {

    private String modelNo;

    private Integer status;

    private Integer dateType;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date stratTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date endTime;

}
