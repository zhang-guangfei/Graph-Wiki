package com.smc.smccloud.model.inventory;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.smc.smccloud.core.model.page.BaseQuery;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author edp04
 * @title: ZeroInventoryDTO
 * @date 2022/05/07 15:41
 */
@Data
public class ZeroInventoryDTO extends BaseQuery {

    private String modelNo;

    private String warehouseCode;

    private Integer days;

    private int dayType;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date stratTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date endTime;

    private Integer dateType;

    private Boolean stockType;
}
