package com.smc.smccloud.model.order;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author edp04
 * @title: RcvSaleDTO
 * @date 2022/04/27 17:04
 */
@Data
public class RcvSaleDTO {

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM")
    @DateTimeFormat(pattern = "yyyy-MM")
    private Date saleStartTime;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM")
    @DateTimeFormat(pattern = "yyyy-MM")
    private Date saleEndTime;
}
