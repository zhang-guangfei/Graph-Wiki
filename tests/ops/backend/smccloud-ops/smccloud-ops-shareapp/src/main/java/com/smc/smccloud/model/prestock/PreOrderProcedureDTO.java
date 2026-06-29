package com.smc.smccloud.model.prestock;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author wuweidong
 * @create 2024/1/23 15:42
 * @description
 */
@Data
public class PreOrderProcedureDTO {

    private Long inventoryId;
    private String orderNo;
    private Integer ItemNo;
    private String modelNo;
    private Date roDate;
    private Integer roQty;
    private Long formId;
    private Date requirementDate ;
    private Integer appQty;
    private BigDecimal ePrice ;
    private String stockType;
    private  Long applyId;
    private  String applyPsnNo;
    private  String deptNo;
}
