package com.smc.smccloud.model.shikomi;

import lombok.Data;

import java.util.Date;

@Data
public class ShikomiInspectionDTO {

    private String shikomiNo;
    private String supplierCode;
    private String modelNo;
    private Date inspectAnswerTime;
    private String inspectAnswerPsnName;
    private String inspectAnswerPsnNo;
    private String inspectAnswerText;
    /**
     * 申请类型 1-终止，2继续
     */
    private Integer inspectApplyType;
    private Integer inspectQty;
    private String applyNo;
    private Integer remainQty;
    private Integer cancelQty;
    private Integer customerQty;
    private Date retentionDurationDate;
    private String expirationHandle;
    private Integer repairQty;

    // shikomi中止和继续用上面的
    private Integer handleType; //处理类型
    private Integer id;
}
