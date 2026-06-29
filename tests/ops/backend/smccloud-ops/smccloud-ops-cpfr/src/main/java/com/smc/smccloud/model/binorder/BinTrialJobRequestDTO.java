package com.smc.smccloud.model.binorder;

import com.smc.smccloud.core.model.page.BaseQuery;
import lombok.Data;

import java.util.List;
import java.util.prefs.BackingStoreException;

/**
 * @author wuweidong
 * @create 2023/6/2 10:42
 * @description
 */
@Data
public class BinTrialJobRequestDTO extends BaseQuery {
    public Long id;
    public String jobNo;
    public String jobName;
    public List<String> warehouseCode;
    public Integer status ;
    public Integer isDeleted ;

}
