package com.smc.smccloud.model.shikomi;

import lombok.Data;

import java.util.Date;

/**
 * @Author lyc
 * @Date 2022/4/14 16:49
 * @Descripton TODO
 */

@Data
public class ShikomiCallbackDetail {

    /**
     * 项号
     */
    private String itemNo;

    private String applyNoitemNo;

    /**
     * 型号
     */
    private String modelNo;

    /**
     * shikomi号
     */
    private String shikomiNo;

    private Date registDate;

    private Integer asseDays;

    private Date approvalDate;

    /**
     * 子表主键
     */
    private Long id;
}
