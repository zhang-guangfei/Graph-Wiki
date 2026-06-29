package com.smc.smccloud.model.binorder;

import com.smc.smccloud.core.model.page.BaseQuery;
import lombok.Data;

/**
 * @Author edp02 @Date 2022/5/30 16:44
 */
@Data
public class BinOrderDetailSplitRequestDTO extends BaseQuery {
    private Long appId;
    private String modelNo;
    private String orderNo;
    private String orderType;
    private String supplierCode;
}
