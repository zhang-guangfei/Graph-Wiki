package com.smc.smccloud.model.csstock;

import com.smc.smccloud.core.model.page.BaseQuery;
import lombok.Data;

/**
 * @author edp04
 * @title: CsTmpReturnDTO
 * @date 2022/05/30 13:49
 */
@Data
public class CsTmpReturnDTO extends BaseQuery {

    private String deptNo;

    private String agentNo;

    private String warehouseCode;

    private Integer calcType;
}
