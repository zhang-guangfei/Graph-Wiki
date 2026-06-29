package com.smc.smccloud.model.salestask;

import lombok.Data;

/**
 * @Author lyc
 * @Date 2023/8/2 12:09
 * @Descripton TODO
 */
@Data
public class UpTaskInfoVO {
    // 批次号 非空
    private String batchNo;
    // 处理状态 非空(OpsSalesTaskReturnStatus枚举里有状态说明)
    private String handleStatus;
    // 操作人 非空
    private String optUserNo;
    // 回调接口参数 (JSON格式)
    private String callBackParameter;
    // 接口返回结果 (JSON格式)
    private String returnResult;

    private String errorMsg;

    private String returnStatus;

    private int tryCount;

    private int errorHandCount;
}
