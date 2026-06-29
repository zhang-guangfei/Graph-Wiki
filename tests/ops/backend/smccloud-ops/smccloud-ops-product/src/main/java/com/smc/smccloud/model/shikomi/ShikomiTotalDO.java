package com.smc.smccloud.model.shikomi;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author wjw
 * @since 2021-09-28
 */
@Data
@TableName("shikomi_total")
public class ShikomiTotalDO {

    @TableId(value = "id",type=IdType.AUTO)
    private Long id;
    /**
     * shikomi号
     */
    @TableField(value = "ShikomiNo")
    private String shikomiNo;

    /**
     * 型号
     */
    @TableField(value = "ModelNo")
    private String modelNo;

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

    // 系列型号
    @TableField(value = "SerialModel")
    private String serialModel;

    /**
     * 1.中国公司共享 2客户专享可协商借用 3 客户专享
     */
    @TableField(value = "ClassCode")
    private String classCode;

    // 申请时间
    @TableField(value = "ApplyTime")
    private Date applyTime;

    // 申请部门
    @TableField(value = "DeptNo")
    private String deptNo;

    // 申请人名称
    @TableField(value = "ApplyPsnName")
    private String applyPsnName;

    /**
     * 交货期(预计下单时间)
     */
    @TableField(value = "DlvDate")
    private Date dlvDate;

    // 回复说明
    @TableField(value = "AnswerText")
    private String answerText;

    @TableField(value = "EPrice")
    private BigDecimal EPrice;

    // 警告数量
    @TableField(value = "QtyWarning")
    private Integer qtyWarning;

    // 申请人
    @TableField(value = "ApplicantNo")
    private String applicantNo;

    // 审批人
    @TableField(value = "ApproverNo")
    private String approverNo;

    // 申请人名称
    @TableField(value = "ApplicantName")
    private String applicantName;

    // 申请人邮箱
    @TableField(value = "ApplicantEmail")
    private String applicantEmail;

    // 审批人名称
    @TableField(value = "ApproverName")
    private String approverName;

    // 审批人邮箱
    @TableField(value = "ApproverEmail")
    private String approverEmail;

    // 组装工作天数
    @TableField(value = "AsseDays")
    private Integer asseDays;
    // 未订货月数
    @TableField(value = "QtyNoord")
    private Integer qtyNoord;
    // 多段价格
    @TableField(value = "Price_lot")
    private BigDecimal priceLot;
    // 行业代码
    @TableField(value = "IndCode")
    private String indCode;
    // 订单预约数量
    @TableField(value = "QtyOrdPre")
    private Integer qtyOrdPre;
    // 申请号
    @TableField(value = "applyNo")
    private String applyNo;
    // 部品准备工作日
    @TableField(value = "partPrepareDays")
    private Integer partPrepareDays;

    @TableField(value = "Rohs")
    private String rohs;

    // 采购中数量
    @TableField(value = "qtyPO")
    private Integer qtyPO;

    // 在库数量
    @TableField(value = "qtyOnhand")
    private Integer qtyOnhand;

    // 检点状态
    @TableField(value = "inspectStatus")
    private Integer inspectStatus;
    // 检点发送时间
    @TableField(value = "inspectSendTime",fill = FieldFill.UPDATE)
    private Date inspectSendTime;
    // 检点回复
    @TableField(value = "InspectAnswerTime")
    private Date inspectAnswerTime;

    @TableField(value = "InspectAnswerPsnName")
    private String inspectAnswerPsnName;

    @TableField(value = "InspectAnswerPsnNo")
    private String inspectAnswerPsnNo;
    // 是否每天检点
    @TableField(value = "inspectDaily")
    private Integer inspectDaily;
    // 点检类型
    @TableField(value = "inspectType")
    private Integer inspectType;
    // 点检回复内容
    @TableField(value = "InspectAnswerText")
    private String inspectAnswerText;
    // 预计晚使用月份
    @TableField(value = "PlanUseDate")
    private Date planUseDate;

    @TableField(value = "pplNo")
    private String pplNo;
    // 项目号
    @TableField(value = "projectNo")
    private String projectNo;
    // 仓库代码
    @TableField(value = "warehouseCode")
    private String warehouseCode;
    // 最近6个月的月平均数量
    @TableField(value = "avgOrdQty")
    private Integer avgOrdQty;
    // 最近下单日期
    @TableField(value = "LastOrdDate")
    private Date lastOrdDate;
    // 点检申请类型：1-申请终止，2申请继续
    @TableField(value = "inspectApplyType")
    private Integer inspectApplyType;
    //
    @TableField(value = "InspectApproverNo")
    private String inspectApproverNo;

    @TableField(value = "InspectApproverName")
    private String inspectApproverName;
    // 检点审批时间
    @TableField(value = "InspectApproverTime")
    private Date inspectApproverTime;
    // shikomi号对应型号数
    @TableField(value = "modelCount")
    private Integer modelCount;
    // 批量数量
    @TableField(value = "LotQty")
    private Integer lotQty;

    @TableField(exist = false)
    private Integer mainModelFlag;

    @TableField(exist = false)
    private String mainModelNo;
    // 点检数量 终止或申请继续数量
    @TableField(value = "inspectQty")
    private Integer inspectQty;
    // 点检申请号
    @TableField(value = "inspectApplyNo")
    private String inspectApplyNo;
    // 是否警告
    @TableField(value = "isWarning")
    private Boolean isWarning;

    @TableField(exist = false)
    private Integer reserveMonth;
}
