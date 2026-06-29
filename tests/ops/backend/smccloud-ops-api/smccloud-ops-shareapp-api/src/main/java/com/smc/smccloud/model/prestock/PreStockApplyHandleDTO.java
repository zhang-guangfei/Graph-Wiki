package com.smc.smccloud.model.prestock;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * Author: B90034
 * Date: 2022-01-14 15:08
 * Description:
 */
@Data
public class PreStockApplyHandleDTO {

    /**
     * 申请号
     */
    @Valid
    @NotEmpty(message = "申请号不能为空")
    private List<Long> applyIds;

    /**
     * 型号 (选填)
     */
    private List<String> modelNos;

    /**
     * 申请项id
     */
    private List<Long> detailIds;
    /**
     * 是否检查拦截条件
     */
    private Boolean isIntercept=true;
}
