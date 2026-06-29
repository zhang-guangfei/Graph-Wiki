package com.smc.smccloud.model.inventory;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author edp04
 * @title: ZeroInventoryVO
 * @date 2022/05/07 15:40
 */
@Data
public class ZeroInventoryVO {

    private Integer id;

    private String modelNo;

    private Date createTime;

    private String warehouseCode;

    private Integer days;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date fromDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date calcDate;

    private Integer qtyBin;

    private Integer binCell;

    private Integer mean;

    private String modelClass;

    private String supplier;

    private Integer stockQty;

    private Integer ordingQty;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date minOrdDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date minDlvDate;

    private Integer transQty;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date minTDlvDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date loginDate;
}
