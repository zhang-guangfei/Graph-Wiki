package com.sales.ops.service.ba;

import com.github.pagehelper.PageInfo;
import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.db.entity.OpsWarehouse;
import com.sales.ops.db.entity.OpsWarehouseAddress;
import com.sales.ops.db.entity.OpsWarehouseAddressConfig;
import com.sales.ops.db.entity.Supplier;
import com.sales.ops.dto.util.PageModel;
import com.sales.ops.dto.warehouse.WarehouseAddressDto;
import com.sales.ops.enums.WarehouseTypeEnum;

import java.util.List;

public interface OpsWarehouseService {

    PageInfo<OpsWarehouseAddress> findMulAddressByPage(PageModel<OpsWarehouseAddress> pageModel);

    Integer addMulAddress(OpsWarehouseAddress record);

    Integer modifyMulAddress(OpsWarehouseAddress record);

    Long getMulAddressCountByAddressId(List<Long> codes);

    Integer removeMulAddressList(List<Long> codes);

    Integer saveMultAaddressConfig(WarehouseAddressDto warehouseAddressDto) throws OpsException;

    List<OpsWarehouseAddress> getMultAdress(OpsWarehouseAddress warehouseAddress);

    OpsWarehouseAddress getMultAdressById(Long id);

    List<String> getMultAdressConfig(OpsWarehouseAddressConfig obj);

    OpsWarehouse getMultAdressSetAddress(OpsWarehouse opsWarehouse, String doType);

    Integer add(OpsWarehouse record);

    Integer modify(OpsWarehouse record);

    Integer removeList(List<String> code);

    List<OpsWarehouse> findAll();

    OpsWarehouse findById(String warehouseCode);

    List<OpsWarehouse> findByIds(List<String> ids);

    List<OpsWarehouse> findByExample(OpsWarehouse condition);

    PageInfo<OpsWarehouse> findByPage(PageModel<OpsWarehouse> pageModel);

    WarehouseTypeEnum[] findTypes();

    List<String> findNamesByType(String type);

    List<OpsWarehouse> findCodeAndNameByType(String type);

    List<String> findCodesByTypeCache(boolean mustwt, String customerNo, String type, boolean disableFlag, String warehouseCode);

    List<String> findCodesByType(boolean mustwt, String customerNo, String type, boolean disableFlag, String warehouseCode);

    String findNameByCode(String code);

    OpsWarehouse findByName(String name);

    Supplier findSupplierByCode(String code);
}
