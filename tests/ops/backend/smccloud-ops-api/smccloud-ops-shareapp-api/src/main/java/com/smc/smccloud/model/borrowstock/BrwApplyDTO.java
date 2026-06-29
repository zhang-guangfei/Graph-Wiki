package com.smc.smccloud.model.borrowstock;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class BrwApplyDTO {

    /**
     * 借货单号
     */
    private String brwNo;

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
     * 用途
     */
    private String purpose;

    /**
     * 备注
     */
    private String remark;

    /**
     * 预计归还时间
     */
    private Date esReturnDate;

    private String brwType;

    private String dlvType;

    private String salesPsn;

    private String salesPsnTel;

    private String receiverName;

    private String receiverPhone;

    private Integer transType;

    private Integer transFee;

    private String receiverAddress;

    private String addressNo;

    private String deptNo;

    private Integer status;

    private Integer id;

    /**
     * 借货类别名称
     */
    private String brwTypeName;

    /**
     * 借货部门
     */
    private String brwPsnDept;

    /**
     * 借货人电话
     */
    private String brwPsnTel;

    /**
     * 借货内勤
     */
    private String employee;

    /**
     * 通过时间
     */
    private Date passDate;

    /**
     * 收货公司
     */
    private String receiverCompany;

    private List<BrwDetailDTO> details;
}
