package com.smc.smccloud.model.shikomi;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class ShikomiVO {

    private Long id;
    private String shikomiNo;
    private String customerNo;
    private String customerName;
    private String modelNo;
    private Integer status;
    private Integer remainQty;
    private Integer applyQty;
    private Date updateTime;
    private Date applyTime;
    private String remark;
    private String companyCode;
    private String deptNo;
    private String deptName;
    private String applyPsnName;
    private String answerText;
    private Date dlvDate;
    private String updateUser;
    private String branchCode;
    /**
     * 有效类型 A中国特定客户,B海外公司特定客户,C所有客户
     */
    private String classType;

    /**
     * 1.中国公司共享 2客户专享可协商借用 3 客户专享
     */
    private String classCode;
    /**
     * 归属子公司代码 中国公司95012
     */
    private String subsidiaryCode;

    private String supplierCode;

    private String supplierName;

    private BigDecimal EPrice;

    private Integer qtyWarning;

    private String applicantName;

    private String applicantEmail;

    private String applicantNo;

    private String approverNo;

    private String approverName;

    private String approverEmail;

    private Integer asseDays;

    private Integer qtyNoord;

    private BigDecimal priceLot;

    private String indCode;

    private String CreateUser;

    private String serialModel;

    private String modelType;

    private Integer qtyOrdPre;

    private Date registDate;

    private String applyNo;

    private Integer partPrepareDays;

    private Integer inspectStatus;

    private Integer inspectType;

    private Date inspectSendTime;

    private Date inspectAnswerTime;

    private Date planUseDate;

    private String pplNo;

    private String projectNo;

    private Integer qtyPO;

    private Integer qtyOnhand;

    private Integer inspectDaily;

    private Integer maxQty;

    private Date reviseDate;

    private Date createTime;

    private String inspectAnswerPsnName;

    private String inspectAnswerPsnNo;

    private String inspectAnswerText;

    private String warehouseCode;

    private Integer inspectApplyType;

    private Integer avgOrdQty;

    private String classCodeName;

    private Date lastOrdDate;

    private Integer mainModelFlag;

    private List<String> customerNos;

    private Integer lotQty;

    private String mainModelNo;

    private String rohs;

    private Integer inspectQty;

    private Boolean isWarning;

    private String classTypeName;

    private Boolean isCNManage;
}
