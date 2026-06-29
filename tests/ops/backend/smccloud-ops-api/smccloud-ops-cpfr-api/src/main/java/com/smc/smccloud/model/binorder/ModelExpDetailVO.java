package com.smc.smccloud.model.binorder;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author edp02 @Date 2021/10/25 14:07
 */
@Data
public class ModelExpDetailVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String warehouseCode;

    private String deptNo;

    private String modelNo;

    private String customerNo;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date monthDate;

    /**
     * 数量
     */
    private Integer qty;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date updateTime;

    /**
     * 订单数量
     */
    private Integer orderNumber;

    /**
     * 0-非拆分型号,1-组装父型号,2-组装子型号
     */
    private Integer assType;

    private String agentNo;

    private String subWarehouseCode;

    private Integer cstmQty;
}
