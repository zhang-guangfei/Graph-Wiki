package com.smc.smccloud.model.order.orderEdit;

import lombok.Data;

import java.util.List;

/**
 * @Author lyc
 * @Date 2023/8/9 14:42
 * @Descripton TODO
 */
@Data
public class UpApproveReplayVO {
    private List<String > batchNo;
    private String answerText;
    private String optUserNo;
    private String newOrderNo;
}
