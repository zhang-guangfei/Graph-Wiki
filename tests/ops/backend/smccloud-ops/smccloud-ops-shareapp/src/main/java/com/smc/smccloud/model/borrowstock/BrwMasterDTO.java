package com.smc.smccloud.model.borrowstock;

import lombok.Data;

import java.util.Date;

@Data
public class BrwMasterDTO {

    private Integer optStatus;

    private String modelNo;

    private String brwNo;

    private Integer quantity;

    private Integer returnQty;

    private String brwPsnTel;

    private String salesPsn;

    private String salesPsnTel;

    private String purpose;

    /**
     * 借货人
     */
    private String brwPsn;

    /**
     * 客户名称
     */
    private String customerName;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 部门代码
     */
    private String deptNo;

    /**
     * 预计归还时间
     */
    private Date esReturnDate;
}
