package com.smc.smccloud.model.shikomi;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author edp04
 * @title: VShikomiTotalDO
 * @date 2022/04/29 14:10
 */
@Data
@TableName("v_shikomi_total")
public class VShikomiTotalDO {

    @TableId(value = "id",type= IdType.AUTO)
    private Long id;
    /**
     * shikomi号
     */
    @TableField(value = "ShikomiNo")
    private String shikomiNo;

    /**
     * 型号
     */
    @TableField(value = "modelno")
    private String modelNo;

    @TableField(value = "subid")
    private Integer subid;

    @TableField(value = "CustomerNo")
    private String customerNo;

    /**
     * 归属子公司代码
     */
    @TableField(value = "SubsidiaryCode")
    private String subsidiaryCode;

    /**
     * 供应商代码
     */
    @TableField(value = "SupplierCode")
    private String supplierCode;

    /**
     * 有效类型A中国特定客户,B海外公司特定客户,C所有客户
     */
    @TableField(value = "ClassType")
    private String classType;

    @TableField(value = "CompanyCode")
    private String companyCode;

    @TableField(value = "BranchCode")
    private String branchCode;

    /**
     * 申请数量
     */
    @TableField(value = "ApplyQty")
    private Integer applyQty;

    /**
     * 剩余数量
     */
    @TableField(value = "RemainQty")
    private Integer remainQty;

    /**
     * 最大数量
     */
    @TableField(value = "MaxQty")
    private Integer maxQty;

    /**
     * 注册时间
     */
    @TableField(value = "RegistDate")
    private Date registDate;

    /**
     * 修正时间
     */
    @TableField(value = "ReviseDate")
    private Date reviseDate;

    /**
     * 状态
     */
    @TableField(value = "Status")
    private Integer status;

    /**
     * 1-完整型号,2-型号系列
     */
    @TableField(value = "ModelType")
    private String modelType;

    @TableField(value = "Remark")
    private String remark;

    /**
     * 修改时间
     */
    @TableField(value = "UpdateTime",fill = FieldFill.UPDATE)
    private Date updateTime;

    /**
     * 创建时间
     */
    @TableField(value = "CreateTime",fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(value = "CreateUser")
    private String createUser;

    @TableField(value = "UpdateUser")
    private String updateUser;

    @TableField(value = "SerialModel")
    private String serialModel;

    /**
     * 1.中国公司共享 2客户专享可协商借用 3 客户专享
     */
    @TableField(value = "ClassCode")
    private String classCode;

    @TableField(value = "ApplyTime")
    private Date applyTime;

    @TableField(value = "DeptNo")
    private String deptNo;

    @TableField(value = "ApplyPsnName")
    private String applyPsnName;

    /**
     * 预计下单时间
     */
    @TableField(value = "DlvDate")
    private Date dlvDate;

    @TableField(value = "AnswerText")
    private String answerText;

    @TableField(value = "EPrice")
    private BigDecimal EPrice;

    @TableField(value = "QtyWarning")
    private Integer qtyWarning;

    @TableField(value = "ApplicantNo")
    private String applicantNo;

    @TableField(value = "ApproverNo")
    private String approverNo;

    @TableField(value = "ApplicantName")
    private String applicantName;

    @TableField(value = "ApplicantEmail")
    private String applicantEmail;

    @TableField(value = "ApproverName")
    private String approverName;

    @TableField(value = "ApproverEmail")
    private String approverEmail;

    @TableField(value = "AsseDays")
    private Integer asseDays;

    @TableField(value = "QtyNoord")
    private Integer qtyNoord;

    @TableField(value = "Price_lot")
    private BigDecimal priceLot;

    @TableField(value = "IndCode")
    private String indCode;

    @TableField(value = "QtyOrdPre")
    private Integer qtyOrdPre;

    @TableField(value = "applyNo")
    private String applyNo;

    @TableField(value = "partPrepareDays")
    private Integer partPrepareDays;

    @TableField(value = "Rohs")
    private String rohs;

    @TableField(value = "qtyPO")
    private Integer qtyPO;

    @TableField(value = "qtyOnhand")
    private Integer qtyOnhand;

    @TableField(value = "inspectStatus")
    private Integer inspectStatus;

    @TableField(value = "inspectSendTime")
    private Date inspectSendTime;

    @TableField(value = "InspectAnswerTime")
    private Date inspectAnswerTime;

    @TableField(value = "InspectAnswerPsnName")
    private String inspectAnswerPsnName;

    @TableField(value = "InspectAnswerPsnNo")
    private String inspectAnswerPsnNo;

    @TableField(value = "inspectDaily")
    private Integer inspectDaily;

    @TableField(value = "inspectType")
    private Integer inspectType;

    @TableField(value = "InspectAnswerText")
    private String inspectAnswerText;

    @TableField(value = "PlanUseDate")
    private Date planUseDate;

    @TableField(value = "pplNo")
    private String pplNo;

    @TableField(value = "projectNo")
    private String projectNo;

    @TableField(value = "warehouseCode")
    private String warehouseCode;

    @TableField(value = "avgOrdQty")
    private Integer avgOrdQty;

    @TableField(value = "LastOrdDate")
    private Date lastOrdDate;

    @TableField(value = "inspectApplyType")
    private Integer inspectApplyType;

    @TableField(value = "InspectApproverNo")
    private String inspectApproverNo;

    @TableField(value = "InspectApproverName")
    private String inspectApproverName;

    @TableField(value = "InspectApproverTime")
    private Date inspectApproverTime;

    @TableField(value = "modelCount")
    private Integer modelCount;

    @TableField(value = "mainModelFlag")
    private Integer mainModelFlag;

    @TableField(value = "LotQty")
    private Integer lotQty;

    @TableField(value = "MainModelNo")
    private String mainModelNo;

    @TableField(value = "inspectQty")
    private Integer inspectQty;

    @TableField(value = "isWarning")
    private Boolean isWarning;

    @TableField(exist = false)
    private List<String> customerList;
}
