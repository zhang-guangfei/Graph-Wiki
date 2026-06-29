package com.smc.smccloud.model.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * @Author lyc
 * @Date 2023/8/10 14:52
 * @Descripton TODO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PreStockApplyDetailDtoTemp {
    private Long id;

    /**
     * 申请号
     */
    private String applyNo;

    /**
     * 申请类型: 1-专备; 2-SHIKOMI; 3-委托在库
     */
    private String applyType;

    /**
     * 备库类型 （inventoryType）
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
    private String applyTimeTemp;

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

    /**
     * 申请担当
     */
    private String applyPsn;

    private String applyPsnNo;

    /**
     * 申请负责人
     */
    private String approverNo;

    private String approverName;

    /**
     * shikomi类型
     */
    private String shikomiClass;

    /**
     * 行业
     */
    private String indCode;

    /**
     * 1-SMC提案; 2-客户提案; 3-自动周转;
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

    /**
     * shikomi申请客户英文名称
     */
    private String enname;

    /**
     * 最终审批时间
     */
    private Date approveTime;

    /**
     * SHIKOMI申请共享客户
     */
    private List<String> shareCustomerNos;

    /**
     * 申请项
     */
    private List<PreStockDetailDtoTemp> details;
}
