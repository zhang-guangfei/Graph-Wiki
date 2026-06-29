package com.smc.smccloud.model.shikomi;

import lombok.Data;

import java.util.Date;

@Data
public class ShikomiInspectionAnsewrDTO {

    private Integer id;

    private Integer shikomiId;

    private String shikomiNo;

    private Integer warnQtyOptCode;

    private String modelNo;

    private String answerText;

    private Integer qtyWarning;

    private Integer openToWorld;

    private Integer delayToCancel;

    private Integer inspectType;

    private Date createTime;

    private String createUser;

    private Integer demandQtyOptCode;

    private Integer cancelQtyOptCode;

}
