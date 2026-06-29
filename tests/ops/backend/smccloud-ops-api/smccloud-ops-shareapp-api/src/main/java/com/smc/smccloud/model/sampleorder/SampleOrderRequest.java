package com.smc.smccloud.model.sampleorder;

import lombok.Data;

import java.util.List;

@Data
public class SampleOrderRequest {

    private String orderNo;
    private String applyNo;
    private Integer status;
    private Integer costStatus;
    private String costType; // 结转类型
    private String applyType; // 申请类型
    private List<String> applyDeptNo; // 申请部门
    private List<String> deptNos; // 费用承担部门
    private String modelNo;
    private String corderNo; // 客户订单号
    private String cmodelNo;
    private String userNo;

    private String applyTimeStart; // 申请时间
    private String applyTimeEnd;

    private String orderDateStart; // 订单生成时间
    private String orderDateEnd;

    private String answerTimeStart; // 处理时间
    private String answerTimeEnd;
}
