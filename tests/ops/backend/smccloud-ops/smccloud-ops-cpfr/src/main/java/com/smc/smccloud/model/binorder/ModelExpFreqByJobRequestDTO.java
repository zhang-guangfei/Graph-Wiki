package com.smc.smccloud.model.binorder;

import com.smc.smccloud.core.model.page.BaseQuery;
import lombok.Data;

/**
 * @author wuweidong
 * @create 2023/6/8 14:03
 * @description
 */
@Data
public class ModelExpFreqByJobRequestDTO extends BaseQuery {
    public Long jobId;
    public String jobNo;
    public String warehouseCode;
    public String[] salesBranchIds;
    public String[] modelNos;
    public String modelType;
}
