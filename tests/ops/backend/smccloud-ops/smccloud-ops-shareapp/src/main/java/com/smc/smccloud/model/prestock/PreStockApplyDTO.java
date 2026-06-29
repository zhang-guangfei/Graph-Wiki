package com.smc.smccloud.model.prestock;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * Author: B90034
 * Date: 2021-10-25 15:35
 * Description:
 */
@Data
public class PreStockApplyDTO {

    private Long id;

    /**
     * 申请号
     */
    private String applyNo;

    /**
     * 申请类型
     */
    private String applyType;

    /**
     * 备库类型
     */
    private String stockType;

    /**
     * 部门代码
     */
    private String deptNo;

    /**
     * 客户代码
     */
    private String customerNo;

    /**
     * 用户代码
     */private String userNo;

    /**
     * 状态
     */
    private String status;

    /**
     * 申请时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date applyTime;

    /**
     * 备注
     */
    private String remark;

    /**
     * 原因
     */
    private String reason;

    private String reasonEn;


    /**
     * 申请人
     */
    private String applyPsn;

    private String passkey;

    private String modelNo;

    private Integer quantity;

    /**
     * 1-普通申请 2-申请自动周转 3-自动补库
     */
    private String replType;

    /**
     * 备库仓库
     */
    private String warehouseCode;
}
