package com.smc.smccloud.model.shikomi;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class ShikomiInspectionVO {

    private Integer id;

    private Integer shikomiId;

    private String shikomiNo;

    private String modelNo;

    private Integer inspectType;

    private Integer qtyWarning;

    private Date createTime;

    private String createUser;

    private Integer warnQtyOptCode;

    private Integer demandQtyOptCode;

    private String answerText;

    private Integer cancelQtyOptCode;

    private Integer openToWorld;

    private Integer delayToCancel;

    private String ApplyNo;

    private Integer ApplyType;

    private Integer ApplyQty;

    private String Reason;

    private Date applyDate;

    private String applicantNo;

    private Integer remainQty;

    private Integer cancelQty;

    private Integer customerQty;

    private Date retentionDurationDate;

    private String expirationHandle;

    private Integer repairQty;

    private Integer inspectStatus;

    private String indCode;

    private String customerNo;

    private String deptNo;

    private Integer qtyNoord;

    private String deptName;

    private String applicantName;
}
