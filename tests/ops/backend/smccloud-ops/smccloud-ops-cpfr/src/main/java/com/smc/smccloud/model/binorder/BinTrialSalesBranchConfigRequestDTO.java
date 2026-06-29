package com.smc.smccloud.model.binorder;

import com.smc.smccloud.core.model.page.BaseQuery;
import lombok.Data;

/**
 * @author wuweidong
 * @create 2023/6/2 10:48
 * @description
 */
@Data
public class BinTrialSalesBranchConfigRequestDTO extends BaseQuery {
    public  Long jobId;
    public String jobNo;
    public String[] salesBranchIds;
    public String[]  warehouseCode;
    public String warehouseMaster;

}
