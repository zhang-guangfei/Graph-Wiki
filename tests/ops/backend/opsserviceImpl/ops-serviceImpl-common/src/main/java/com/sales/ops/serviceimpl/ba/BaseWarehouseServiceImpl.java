package com.sales.ops.serviceimpl.ba;

import com.sales.ops.db.dao.OpsWarehouseMapper;
import com.sales.ops.db.entity.OpsWarehouse;
import com.sales.ops.enums.WarehouseTypeEnum;
import com.sales.ops.service.ba.BaseWarehouseService;
import com.sales.ops.service.wm.WmCommonService;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author C12961
 * @date 2023/2/16 19:31
 */
@Service
public class BaseWarehouseServiceImpl implements BaseWarehouseService {


    @Autowired
    private WmCommonService wmCommonService;
    @Autowired
    private OpsWarehouseMapper opsWarehouseMapper;

    @Override
    public OpsWarehouse getWarehouseByCode(String warehouseCode) {
        ResultVo<OpsWarehouse> resultVo = wmCommonService.getWarehouseByCode(warehouseCode);
        if (resultVo.isSuccess() && resultVo.getData() != null) {
            return resultVo.getData();
        } else {
            return opsWarehouseMapper.selectByPrimaryKey(warehouseCode);
        }
    }

    @Override
    public boolean isMaster(String warehouseType) {
        return StringUtils.equals(WarehouseTypeEnum.RDC.getHouseTypeCode(), warehouseType);
    }

    @Override
    public boolean isWT(String warehouseType) {
        return StringUtils.equals(WarehouseTypeEnum.WT.getHouseTypeCode(), warehouseType);
    }

}
