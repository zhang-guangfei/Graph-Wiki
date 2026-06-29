package com.smc.smccloud.model.pd;

import lombok.Data;

import java.util.List;

/**
 * @Author lyc
 * @Date 2023/6/21 15:20
 * @Descripton TODO
 */
@Data
public class PdInputDto {
    private List<OpsAsPdBillDataDO> dto;
}
