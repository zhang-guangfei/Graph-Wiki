package com.smc.smccloud.service;

import com.smc.smccloud.model.pd.OpsAsWmsInventoryDataDO;

import java.util.List;

/**
 * @Author lyc
 * @Date 2023/7/8 12:01
 * @Descripton TODO
 */
public interface OpsAsWmsInventoryService {
    List<OpsAsWmsInventoryDataDO> selectByDataType(String pdNo,List<String> dataTypeList);
}
