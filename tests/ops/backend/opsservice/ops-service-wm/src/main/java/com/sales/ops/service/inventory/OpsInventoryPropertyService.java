package com.sales.ops.service.inventory;

import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.db.entity.InvPropView;
import com.sales.ops.db.entity.OpsInventoryProperty;
import com.sales.ops.dto.util.UserDto;

import java.util.List;

public interface OpsInventoryPropertyService {

    List<OpsInventoryProperty> findByExample(OpsInventoryProperty condition);

    List<OpsInventoryProperty> matchByExample(OpsInventoryProperty condition);

    OpsInventoryProperty findById(Long id);

    Long findPropertyWithInsertByExample2(OpsInventoryProperty property, UserDto userDto) throws OpsException;

    String getPropertyUid(OpsInventoryProperty property)throws OpsException;

    OpsInventoryProperty formatProperty(OpsInventoryProperty property) throws OpsException;

    Long findPropertyWithInsertByExample(OpsInventoryProperty property, UserDto userDto)throws OpsException;

    Long findPropertyIdBySalesInfoNo(String salesInfoNo)  throws OpsException;

    List<String> findExpireSalesInfo()throws OpsException;

    List<String> findExpireSalesInfoDeleted(Integer days) throws OpsException;

    void deletePropertyById(Long id);

    //查询库存批属性

    InvPropView getPropertyViewByInventoryId(Long inventoryId, String tableType);
}
