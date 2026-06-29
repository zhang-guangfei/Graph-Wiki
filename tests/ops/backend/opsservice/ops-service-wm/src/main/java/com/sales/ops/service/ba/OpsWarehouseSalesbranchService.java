package com.sales.ops.service.ba;

import com.github.pagehelper.PageInfo;
import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.db.entity.OpsWarehouseSalesbranchConfig;
import com.sales.ops.dto.query.WarehouseSalesbranchQO;
import com.sales.ops.dto.util.PageModel;
import com.sales.ops.enums.CKTYPEEnum;
import com.sales.ops.enums.DoSourceEnum;

import java.util.List;

public interface OpsWarehouseSalesbranchService {

    Integer add(OpsWarehouseSalesbranchConfig record);

    Integer modify(OpsWarehouseSalesbranchConfig record);

    Integer removeList(List<Integer> code);

    List<OpsWarehouseSalesbranchConfig> findAll();

    PageInfo<OpsWarehouseSalesbranchConfig> findByPage(PageModel<WarehouseSalesbranchQO> pageModel);

    List<OpsWarehouseSalesbranchConfig> getBranchListBysalesBranchId(String orderType, String customerNo, String salesBranchId, String warehouseTypeCode, Integer expDlvType, String expLinkNo) throws OpsException;

    String getGatherWarehousesWithHouse(String salesBranchId, List<String> houseList) throws OpsException;

    String getGatherWarehousesWithBranchId(Boolean ckTypeSingleWH, String salesBranchId, DoSourceEnum doSourceEnum) throws OpsException;

    List<OpsWarehouseSalesbranchConfig> getBranchListBysalesBranchId(String salesBranchId);

    List<OpsWarehouseSalesbranchConfig> getGatherWarehouses(String salesBranchId, List<String> houseList) throws OpsException;

    String getWarehouseCodeWithMaxDeliveryDay(String deptNo, List<String> warehouses) throws OpsException;

    List<OpsWarehouseSalesbranchConfig> getConfigBySalesBranchIdAndWarehouse(String salesBranchId, String warehouse) ;
}
