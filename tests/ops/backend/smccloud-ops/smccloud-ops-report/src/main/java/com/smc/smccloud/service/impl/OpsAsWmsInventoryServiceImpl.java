package com.smc.smccloud.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.smc.smccloud.mapper.pd.OpsAsWmsInventoryDataMapper;
import com.smc.smccloud.model.pd.OpsAsWmsInventoryDataDO;
import com.smc.smccloud.service.OpsAsWmsInventoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author lyc
 * @Date 2023/7/8 12:02
 * @Descripton TODO
 */
@Service
public class OpsAsWmsInventoryServiceImpl implements OpsAsWmsInventoryService {
    @Resource
    private OpsAsWmsInventoryDataMapper opsAsWmsInventoryDataMapper;

    @Override
    // @Transactional(readOnly = true,propagation = Propagation.NOT_SUPPORTED)
    public List<OpsAsWmsInventoryDataDO> selectByDataType(String pdNo, List<String> dataTypeList) {
        LambdaQueryWrapper<OpsAsWmsInventoryDataDO> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper
                .eq(OpsAsWmsInventoryDataDO::getPdBatchNo,pdNo)
                .in(OpsAsWmsInventoryDataDO::getPdDataType,dataTypeList).orderByAsc(OpsAsWmsInventoryDataDO::getId);
        return opsAsWmsInventoryDataMapper.selectList(lambdaQueryWrapper);
    }
}
