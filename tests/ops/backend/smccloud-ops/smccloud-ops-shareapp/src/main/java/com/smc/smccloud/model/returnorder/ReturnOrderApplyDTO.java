package com.smc.smccloud.model.returnorder;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author C18117
 * @title: ReturnOrderApplyDTO
 * @date 2022/11/02 14:40
 */
@Data
public class ReturnOrderApplyDTO {

    private String applyNo;

    private Integer itemNo;

    private String orderNo;

    private Integer applyQty;

    private String modelNo;

    private Integer orderQty;

    private String expStockCode;

    private BigDecimal price;

    private String warehouseCode;

    private Integer toUserStock;

    private BigDecimal feeRate;

    private String customerNo;

    private String userNo;

    private String deptNo;

    private String invoiceNo;

    private Date invoiceDate;

    private Integer dutyType;

    private String returnType;

    private String remark;

    private String employeeNo;

    private String reason;

    private String applicant;
}
