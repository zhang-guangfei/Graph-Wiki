package com.smc.smccloud.service;

import com.smc.smccloud.model.bindata.BindataDO;
import com.smc.smccloud.model.binorder.ModelExpFreqDO;
import com.smc.smccloud.model.customer.CustomerVO;
import com.smc.smccloud.model.inventory.WarehouseInventoryVO;

import java.util.List;
import java.util.concurrent.Future;

/**
 * Author: B90034
 * Date: 2023-01-09 13:54
 * Description:
 */
public interface CommonService {

    CustomerVO getCustomerInfoByNo(String customerNo);

    String getWarehouseNameByCode(String warehouseCode);

    boolean isMasterWarehouse(String warehouseCode);

    String getWarehouseType(String warehouseCode);

    List<String> getMasterWarehouseCodes();
    ModelExpFreqDO getModelExpFreq(String modelno,String warehouseCode);


    List<ModelExpFreqDO> getModelExpFreqForAvgQty(List<ModelExpFreqDO> doList);
    List<ModelExpFreqDO> getModelExpFreqForAvgQty12 (List<String> modelNos);
    List<ModelExpFreqDO> getModelExpFreqForAvgQty (List<String> modelNos,String warehouseCode,Integer month);

    Future<List<WarehouseInventoryVO>> asyncGetWarehouseInventoryByModels(List<String> modelNoList, List<String> warehouseCodeList);
    Future<List<BindataDO>> asyncGetBinDataByModels(List<String> modelNoList, List<String> warehouseCodeList);


}
