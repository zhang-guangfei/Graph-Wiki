package com.smc.smccloud.model.shikomi;

import lombok.Data;

import java.io.Serializable;

/**
 * @author C18117
 * @title: ShikomiWarningDTO
 * @date 2023/08/09 11:23
 */
@Data
public class ShikomiWarningDTO implements Serializable {

    private static final long serialVersionUID = -815169388565372363L;

    private String shikomiNo;

    private String modelNo;

    private String supplierCode;

    private String customerNo;

    private String userCustomerNo;

    private String classType;

    private String classCode;

    private Integer qtyNood;

    private Integer remainQty;

    private Integer qtyOrdPre;

    private Integer qtyOnhand;

    private Integer qtyPO;

    private Integer qtyWarning;

    private Integer avgOrdQty;

    private Integer preDate;

    private String deptNo;

    private String applicantNo;

    private String approverNo;
}
