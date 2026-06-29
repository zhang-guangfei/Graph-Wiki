package com.smc.smccloud.model.sampleorder;

import lombok.Data;

import java.util.Date;

/**
 * @Author lyc
 * @Date 2024/1/25 11:38
 * @Descripton TODO
 */
@Data
public class SampleBalApplySpecialVO {
    /**
     * 快递单号
     */
    private String trackingNo;

    /**
     * 邮寄时间
     */
    private Date mailingDate;


    // 1 强制结转 0 非强制结转
    private Boolean forceBalFlag = false;
}
