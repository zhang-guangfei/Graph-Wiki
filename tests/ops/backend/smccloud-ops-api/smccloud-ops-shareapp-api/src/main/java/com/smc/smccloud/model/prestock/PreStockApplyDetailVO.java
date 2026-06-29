package com.smc.smccloud.model.prestock;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Author: B90034
 * Date: 2022-01-21 09:40
 * Description:
 */
@Data
public class PreStockApplyDetailVO {

    private Long id;

    /**
     * 申请号
     */
    private String applyNo;

    /**
     * 申请类型
     */
    private String applyType;

    /**
     * 备库类型
     */
    private String stockType;

    /**
     * 仓库
     */
    private String warehouseCode;

    /**
     * 部门代码
     */
    private String deptNo;

    /**
     * 客户代码
     */
    private String customerNo;

    /**
     * 用户代码
     */
    private String userNo;

    /**
     * 状态 1-编辑中; 2-待审核; 3-待处理; 4-不通过; 5-已确认(SHIKOMI); 6-已备库; 9-取消
     */
    private String status;

    /**
     * 申请时间
     */
    private Date applyTime;

    /**
     * 备注
     */
    private String remark;

    /**
     * 项目代码
     */
    private String projectNo;

    /**
     * 项目名称
     */
    private String projectName;

    /**
     * 应用设备
     */
    private String equipment;

    /**
     * 项目状态
     */
    private String projectStatus;

    /**
     * 用途
     */
    private String purpose;

    /**
     * 原因
     */
    private String reason;

    private String reasonEn;

    /**
     * 订单大小
     */
    private String orderSize;

    /**
     * 销售担当
     */
    private String salesPsn;

    /**
     * 用户部门
     */
    private String userDept;

    /**
     * 用户联系人
     */
    private String userPsnName;

    /**
     * 用户联系人职位
     */
    private String userPsnJob;

    /**
     * 申请人
     */
    private String applyPsn;

    /**
     * 随机key
     */
    private String passkey;

    /**
     * ppl_no项目号
     */
    private String pplNo;

    /**
     * 客户组号
     */
    private String groupCustomerNo;

    private String applyPsnNo;

    private String approverNo;

    private String approverName;

    private String shikomiClass;

    /**
     * 行业代码
     */
    private String indCode;

    /**
     * 1-普通申请 2-申请自动周转 3-自动补库
     */
    private String replType;

    /**
     * 申请担当邮箱
     */
    private String applyPsnMail;

    /**
     * 负责人邮箱
     */
    private String approverMail;

    private String enname;

    /**
     * 审批时间
     */
    private Date approveTime;
    /**
     * 是否异仓库调拨
     */
    private Boolean transFlag;
    private Boolean vip;

    private String corderNo;
    /**
     * 申请项
     */
    private List<PreStockDetailDTO> details;

    /**
     * 处理结果
     */
    private List<PreStockResultDTO> resultDetails;

}
