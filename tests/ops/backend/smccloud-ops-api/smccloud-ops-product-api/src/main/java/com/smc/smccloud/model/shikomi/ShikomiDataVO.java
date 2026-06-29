package com.smc.smccloud.model.shikomi;

import lombok.Data;

import java.util.Date;

@Data
public class ShikomiDataVO {

    private String modelNo;

    private String customerNo;

    private String shikomiNo;

    private Integer status;

    private String supplierCode;

    private Integer pageNumber;

    private String deptNo;

    private Integer pageSize;

    private Integer inspectStatus;

    private Integer inspectType;

    private String applicantNo;

    private String classType;

    private Integer classCode;

    private String applyNo;

    private Integer isManageProduct;

    private String[] modelNos;

    private Boolean checked;

    private String[] deptNos;

    private Integer subsidiaryType;

    private String mainCustomerNo;

    private String applicantName;

    private Integer qtyNoord1;
    private Integer qtyNoord2;

    private Integer dateType;

    private Date startTime;
    private Date endTime;

    private Boolean isWarning;

    private String[] supplierCodeList;

    private String indCode;

    private Integer rohs;

    private Integer mainModelFlag;
}
