package com.smc.smccloud.service.impl;

import com.sales.ops.common.opsexception.Exceptions;
import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.db.entity.OpsInventoryProperty;
import com.sales.ops.enums.InventoryTypeEnum;
import com.smc.smccloud.mapper.OpsInventoryReadOnlyMapper;
import com.smc.smccloud.model.adapter.stock.InventoryScopeEnum;
import com.smc.smccloud.model.stock.InventoryCondition;
import com.smc.smccloud.model.stock.InventoryDto;
import com.smc.smccloud.model.stock.InventoryListDto;
import com.smc.smccloud.service.InventoryPropertyService;
import com.smc.smccloud.service.InventoryService;
import com.smc.smccloud.service.OpsCommonService;
import jodd.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class InventoryServiceImpl implements InventoryService {


    @Autowired
    private OpsInventoryReadOnlyMapper opsInventoryReadOnlyMapper;
    @Autowired
    private OpsCommonService opsCommonService;
    @Autowired
    private InventoryPropertyService inventoryPropertyService;


    @Override
    public List<String> randomModelno(Integer num) {
        return opsInventoryReadOnlyMapper.randomModelno(num);
    }


    @Override
    public List<InventoryListDto> searchInventory(InventoryCondition condition) throws OpsException {
        List<InventoryDto> inventoryList = new ArrayList<>();
        List<Long> propertyIds = null;
        if (CollectionUtils.isEmpty(condition.getModelNoList())) {
            List<Long> list = new ArrayList<>();
            for (String customerNo : condition.getAllCustomerNo()) {
                List<Long> ids = inventoryPropertyService.getinventoryPropertyListByCustomerNo(customerNo, condition.getInventoryType());
                list.addAll(ids);
            }
            //去重
            propertyIds=list.stream().distinct().collect(Collectors.toList());
        }

        //按照库存范围分类
        if (StringUtils.isNotBlank(condition.getInventoryScope())) {
            InventoryScopeEnum scopeEnum = InventoryScopeEnum.getEnumByType(condition.getInventoryScope());
            switch (scopeEnum) {
                case NORMAL:
                    inventoryList = getInventoryNormal(condition.getModelNoList(), condition.getWarehouseCode(), propertyIds);
                    break;
                case MOVE:
                    inventoryList = getInventoryMove(condition.getModelNoList(), condition.getWarehouseCode(), propertyIds);
                    break;
                case SUPPLIER:
                    inventoryList = getInventorySupplier(condition.getModelNoList(), condition.getWarehouseCode(), propertyIds);
                    break;
            }
        } else {
            List<InventoryDto> invNormal = getInventoryNormal(condition.getModelNoList(), condition.getWarehouseCode(), propertyIds);
            inventoryList.addAll(invNormal);
            List<InventoryDto> invMove = getInventoryMove(condition.getModelNoList(), condition.getWarehouseCode(), propertyIds);
            inventoryList.addAll(invMove);
            List<InventoryDto> invSupplier = getInventorySupplier(condition.getModelNoList(), condition.getWarehouseCode(), propertyIds);
            inventoryList.addAll(invSupplier);
        }
        //筛选条件
        inventoryList = filterInventory(condition, inventoryList);
        List<InventoryListDto> dtoList = fillInventoryDto(condition, inventoryList);
        return dtoList;
    }

    public List<InventoryDto> getInventoryNormal(List<String> modelnoList, String warehouseCode, List<Long> propertyIds) {
        //bugid:19127 风险在库
        return opsInventoryReadOnlyMapper.selectInventoryNormalV1(modelnoList, warehouseCode, propertyIds);
    }


    public List<InventoryDto> getInventoryMove(List<String> modelnoList, String warehouseCode, List<Long> propertyIds) {
        return opsInventoryReadOnlyMapper.selectInventoryMove(modelnoList, warehouseCode, propertyIds);
    }

    //null代表是有型号，如果有客户代码，则propertyId为List，如果有客户代码但没有查到porperty，则list.size==0
    //该库存只有TY才需要查询，其他不需要查询，所以有型号时可查询，
    public List<InventoryDto> getInventorySupplier(List<String> modelnoList, String warehouseCode, List<Long> propertyIds) {
        if (propertyIds == null || (!propertyIds.isEmpty() && "1".equals(String.valueOf(propertyIds.get(0))))) {
            return opsInventoryReadOnlyMapper.selectInventorySupplier(modelnoList, warehouseCode);
        }
        return new ArrayList<>();
    }


    public List<InventoryDto> filterInventory(InventoryCondition condition, List<InventoryDto> list) throws OpsException {
        List<InventoryDto> result = new ArrayList<>();
        for (InventoryDto dto : list) {
            if (InventoryScopeEnum.NORMAL.getCode().equals(dto.getInventoryScope()) ||
                    InventoryScopeEnum.MOVE.getCode().equals(dto.getInventoryScope())) {
                Long propertyId = dto.getInventoryPropertyId();
                OpsInventoryProperty property = inventoryPropertyService.getInventoryProperty(propertyId);
                if (property == null) {
                    continue;
                }
                dto.setInventoryTypeCode(property.getInventoryTypeCode());
                dto.setCustomerNo(property.getCustomerNo());
                dto.setPpl(property.getPpl());
                dto.setGroupCustomerNo(property.getGroupCustomerNo());
                dto.setProjectCode(property.getProjectCode());
                dto.setSalesInfoNo(property.getSalesInfoNo());
                boolean needCustomerNo = condition.hasCustomerNo();
                boolean needInventoryType = StringUtil.isNotBlank(condition.getInventoryType());
                boolean customerNoAvailable = condition.getAllCustomerNo().contains(property.getCustomerNo());
                if (needInventoryType) {
                    if (StringUtils.equals(property.getInventoryTypeCode(), condition.getInventoryType())) {
                        if (needCustomerNo) {
                            if (customerNoAvailable) {
                                result.add(dto);
                            }
                        } else {
                            result.add(dto);
                        }
                    }
                } else {
                    if (needCustomerNo) {
                        if (customerNoAvailable) {
                            result.add(dto);
                        }
                        //通用的该客户也能用
                        if (StringUtils.equals(property.getInventoryTypeCode(), InventoryTypeEnum.TY.getType())) {
                            result.add(dto);
                        }
                    } else {
                        result.add(dto);
                    }
                }
            } else {
                if (InventoryScopeEnum.SUPPLIER.getCode().equals(dto.getInventoryScope())) {
                    if (StringUtils.isBlank(condition.getInventoryType()) || StringUtils.equals(condition.getInventoryType(), InventoryTypeEnum.TY.getType())) {
                        result.add(dto);
                    }
                }
            }
        }
        return result;
    }


    public List<InventoryListDto> fillInventoryDto(InventoryCondition condition, List<InventoryDto> inventoryList) {
        // 填充字段
        Map<String, List<InventoryDto>> map = new HashMap<>();
        HashMap<String, String> warehouseMap = new HashMap<>();
        for (InventoryDto inventoryDto : inventoryList) {
            //翻译库存类型
            if (StringUtils.isNotBlank(inventoryDto.getInventoryTypeCode())) {
                InventoryTypeEnum typeEnum = InventoryTypeEnum.parse(inventoryDto.getInventoryTypeCode());
                if (typeEnum != null) {
                    inventoryDto.setInventoryTypeName(typeEnum.getDesc());
                }
            }
            //翻译仓库和供应商
            if (StringUtils.isNotBlank(inventoryDto.getWarehouseCode())) {
                String name = null;
                if (warehouseMap.containsKey(inventoryDto.getWarehouseCode())) {
                    name = warehouseMap.get(inventoryDto.getWarehouseCode());
                } else {
                    if (StringUtils.equals(InventoryScopeEnum.NORMAL.getCode(), inventoryDto.getInventoryScope())
                            || StringUtils.equals(InventoryScopeEnum.MOVE.getCode(), inventoryDto.getInventoryScope())) {
                        name = opsCommonService.getWarehouseNameByCode(inventoryDto.getWarehouseCode());
                    } else if (StringUtils.equals(InventoryScopeEnum.SUPPLIER.getCode(), inventoryDto.getInventoryScope())) {
                        name = opsCommonService.getSupplierNameByCode(inventoryDto.getWarehouseCode());
                    }
                    if (StringUtils.isBlank(name)) {
                        name = inventoryDto.getWarehouseCode();
                    }
                    warehouseMap.put(inventoryDto.getWarehouseCode(), name);
                }
                inventoryDto.setWarehouseName(name);
            }
            //按照型号分类
            if (!map.containsKey(inventoryDto.getModelNo())) {
                List<InventoryDto> dtoList = new ArrayList<>();
                dtoList.add(inventoryDto);
                map.put(inventoryDto.getModelNo(), dtoList);
            } else {
                List<InventoryDto> dtoList = map.get(inventoryDto.getModelNo());
                dtoList.add(inventoryDto);
            }
        }
        //构造返回实体
        List<InventoryListDto> dtoList = new ArrayList<>();
        for (Map.Entry<String, List<InventoryDto>> entry : map.entrySet()) {
            Boolean binModelno = isBinModelno(entry.getKey(), condition.getAllCustomerNo());
            InventoryListDto listDto = new InventoryListDto(entry.getKey(), entry.getValue(), binModelno);
            dtoList.add(listDto);
        }
        return dtoList;
    }


    public Boolean isBinModelno(String modelNo, List<String> customerNos) {
        List<String> customerNoList = inventoryPropertyService.getBinData(modelNo);
        if (CollectionUtils.isNotEmpty(customerNoList)) {
            if (customerNoList.contains("all")) {
                return true;
            }
            if (CollectionUtils.isNotEmpty(customerNos)) {
                //有交集
                return !CollectionUtils.intersection(customerNos,customerNoList).isEmpty();
            }
        }
        return false;
    }


}
