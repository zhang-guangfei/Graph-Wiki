package com.smc.smccloud.model.trans;

import lombok.Data;

import java.util.List;

/**
 * @author edp04
 * @title: TransOrderCancelDTO
 * @date 2022/07/24 14:40
 */
@Data
public class TransOrderCancelDTO {

    private List<Long> ids;
    private String transNo;

    /**
     * 1-调库 2组换
     */
    private Integer transType;

    private Integer itemNo;

    private String reason;
}
