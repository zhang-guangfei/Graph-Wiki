package com.smc.smccloud.model.prestock;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * Author: B90034
 * Date: 2021-10-26 08:42
 * Description: 每月备库明细
 */
@Data
public class PreStockApplyMonthlyDetail {

    /**
     * detail.id
     */
    private Long id;

    private Long applyId;

    /**
     * 型号
     */
    private String modelNo;

    /**
     * 需求月份
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date dlvDate;

    /**
     * 备库数量
     */
    private Integer quantity;

    /**
     * 处理状态 1-提交中; 2-处理中; 5-已确认; 6-已处理;
     */
    private String status;
}
