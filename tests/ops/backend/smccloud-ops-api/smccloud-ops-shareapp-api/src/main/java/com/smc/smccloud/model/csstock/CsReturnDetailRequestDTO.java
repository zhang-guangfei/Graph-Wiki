package com.smc.smccloud.model.csstock;

import com.smc.smccloud.core.model.page.BaseQuery;
import lombok.Data;

/**
 * 退货清单查询
 *
 * @author wsf
 * @version 1.0
 * @date 2022/1/7 11:53
 */
@Data
public class CsReturnDetailRequestDTO extends BaseQuery {
    private  Integer applyId;
}
