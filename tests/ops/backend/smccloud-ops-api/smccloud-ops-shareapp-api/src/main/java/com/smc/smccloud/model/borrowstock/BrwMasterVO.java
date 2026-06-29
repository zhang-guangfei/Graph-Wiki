package com.smc.smccloud.model.borrowstock;


import lombok.Data;

import java.util.Date;

@Data
public class BrwMasterVO {

    /**
     * 部门代码
     */
    private String deptNo;

    /**
     * 借货单号
     */
    private String brwNo;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 客户代码
     */
    private String customerNo;

    /**
     * 客户名称
     */
    private String customerName;

    /**
     * 用户代码
     */
    private String userNo;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 借货人
     */
    private String brwPsn;

    /**
     * 借货目的
     */
    private String purpose;

    /**
     * 备注
     */
    private String remark;

    private Date createTime;

    /**
     * 预计归还时间
     */
    private Date esReturnDate;

    private String timeStart;

    private String timeEnd;
}
