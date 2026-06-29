package com.smc.smccloud.model.shikomi;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;

@Data
@TableName("shikomi_inspection")
public class ShikomiInspectionDO {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField(value = "ShikomiId")
    private Integer shikomiId;

    @TableField(value = "ShikomiNo")
    private String shikomiNo;

    @TableField(value = "modelNo")
    private String modelNo;

    @TableField(value = "inspectType")
    private Integer inspectType;

    @TableField(value = "QtyWarning")
    private Integer qtyWarning;

    @TableField(value = "createTime",fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(value = "createUser")
    private String createUser;

    @TableField(value = "WarnQtyOptCode")
    private Integer warnQtyOptCode;

    @TableField(value = "DemandQtyOptCode")
    private Integer demandQtyOptCode;

    @TableField(value = "AnswerText")
    private String answerText;

    @TableField(value = "CancelQtyOptCode")
    private Integer cancelQtyOptCode;

    @TableField(value = "OpenToWorld")
    private Integer openToWorld;

    @TableField(value = "DelayToCancel")
    private Integer delayToCancel;

    @TableField(value = "ApplyNo")
    private String ApplyNo;

    @TableField(value = "ApplyType")
    private Integer ApplyType;

    @TableField(value = "ApplyQty")
    private Integer ApplyQty;

    @TableField(value = "Reason")
    private String Reason;

    @TableField(value = "ApplyDate")
    private Date applyDate;

    @TableField(value = "ApplicantNo")
    private String applicantNo;

    @TableField(value = "RemainQty")
    private Integer remainQty;

    @TableField(value = "CancelQty")
    private Integer cancelQty;

    @TableField(value = "CustomerQty")
    private Integer customerQty;

    @TableField(value = "RetentionDurationDate")
    private Date retentionDurationDate;

    @TableField(value = "ExpirationHandle")
    private String expirationHandle;

    @TableField(value = "RepairQty")
    private Integer repairQty;

    @TableField(value = "inspectStatus")
    private Integer inspectStatus;
}
