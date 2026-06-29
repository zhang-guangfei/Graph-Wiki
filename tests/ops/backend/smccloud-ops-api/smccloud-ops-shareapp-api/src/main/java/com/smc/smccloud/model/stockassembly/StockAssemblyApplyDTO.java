package com.smc.smccloud.model.stockassembly;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;

/**
 * Author: Denghui
 * Date: 2021-10-09 10:21
 * Description: 申请主体信息
 */
@Data
public class StockAssemblyApplyDTO {

    /**
     * stock_assembly.id
     */
    private Long id;

    /**
     * 申请号
     */
    private String applyNo;

    /**
     * 状态 (必需)
     * 1-编辑中; 2-审批中; 3-审批通过; 4-组装中; 6-已完成; 9-取消
     */
    @NotBlank(message = "申请状态不能为空")
    private String status;

    /**
     * 部门代码
     */
    private String deptNo;

    /**
     * 组装类型 (必需)
     * 1-组装; 2-拆分; 3-调库; 4-组换(仅业务); 5-组换(仅财务);
     */
    @NotBlank(message = "申请目的不能为空")
    private String assembleType;

    /**
     * 申请类型 (必需)
     * 1-组换; 2-调库
     */
    @NotBlank(message = "申请目的不能为空")
    private String applyType;

    /**
     * 用于订单号
     */
    private String needForOrderNo;

    /**
     * 所需型号
     */
    private String needModelNo;

    /**
     * 所需数量
     */
    private Integer needQuantity;

    /**
     * 希望交货期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date dlvDate;

    /**
     * 备注
     */
    @Length(max = 250)
    private String remark;

    /**
     * 调库票号
     */
    private String billNo;

    /**
     * 回复说明日期
     */
    private Date answerDate;

    /**
     * 回复说明
     */
    private String answerText;

    /**
     * 申请时间 (申请必需)
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date applyDate;

    /**
     * 申请人 (申请必需)
     */
    private String applyPsn;

    /**
     * 审批时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date approveDate;

    /**
     * 审批人
     */
    private String approvePsn;

    private String transPsn;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date transTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date receiveTime;

    private String receivePsn;

    /**
     * 仓库代码
     */
    private String warehouseCode;

    /**
     * 申请调出项 (必需)
     */
//    @Valid
//    @NotEmpty(message = "调出申请项不能为空")
    private List<StockAssemblyItemDTO> outItems;

    /**
     * 申请调入项 (必需)
     */
//    @Valid
//    @NotEmpty(message = "调入申请项不能为空")
    private List<StockAssemblyItemDTO> inItems;
}
