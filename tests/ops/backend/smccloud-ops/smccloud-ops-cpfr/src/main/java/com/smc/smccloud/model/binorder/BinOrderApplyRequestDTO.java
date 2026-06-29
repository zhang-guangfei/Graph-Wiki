package com.smc.smccloud.model.binorder;

import lombok.Data;

import java.util.List;

/**
 * @Author edp02 @Date 2021/11/13 16:50
 */
@Data
public class BinOrderApplyRequestDTO {
    private Long appId;
    private Long calcId;
    private Boolean allHasOrdQtyItem;
    private String applyText;
    private List<String> modelNos;
    private List<Long> ids;
    private Integer selectType;
    private BinOrderCalcQueryDTO queryDTO;
}
