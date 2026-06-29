package com.smc.smccloud.model.pd;

import lombok.Data;

import java.util.List;

/**
 * @Author lyc
 * @Date 2023/6/22 10:23
 * @Descripton TODO
 */
@Data
public class PdBorrowDto {
    private List<OpsAsPdBorrowDataDO> dto;
}
