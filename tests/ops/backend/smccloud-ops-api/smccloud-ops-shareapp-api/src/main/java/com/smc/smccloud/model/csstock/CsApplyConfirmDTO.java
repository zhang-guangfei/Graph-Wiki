package com.smc.smccloud.model.csstock;

import lombok.Data;

/**
 * 审批申请补仓申请
 *
 * @author wsf
 * @version 1.0
 * @date 2021/11/12 10:36
 */
@Data
public class CsApplyConfirmDTO {


    /**
     * 申请号
     */
    private Integer applyId;

    /**
     * 审批结果
     * 0不通过 1通过
     */
    private boolean approvePass;

    /**
     * 提交内容
     */
    private String approveText;



}
