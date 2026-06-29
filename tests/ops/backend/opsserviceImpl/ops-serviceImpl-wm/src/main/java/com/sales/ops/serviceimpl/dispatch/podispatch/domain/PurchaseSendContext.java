package com.sales.ops.serviceimpl.dispatch.podispatch.domain;

import com.sales.ops.db.entity.OpsPurchaseorder;
import com.sales.ops.db.entity.OpsRequestpurchase;
import com.sales.ops.db.entity.OpsWarehouse;
import com.sales.ops.dto.purchase.OpsPurchaseStatusToWMDto;
import com.sales.ops.dto.util.UserDto;
import com.sales.ops.utils.PoNoUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author C12961
 * @date 2023/5/16 8:46
 */
public class PurchaseSendContext {


    private List<OpsRequestpurchase> requestList;
    private List<OpsPurchaseorder> purchaseList;
    private Map<OpsRequestpurchase, OpsPurchaseorder> purchaseMap;
    private Map<String, OpsWarehouse> requestWarehouseMap;
    private Map<String, String> warehouseCodeType;
    private List<OpsRequestpurchase> requestListForDB;
    private UserDto creator;

    public PurchaseSendContext(List<OpsPurchaseStatusToWMDto> list) {
        this.creator = new UserDto("采购发单", "0.0.0.0");
        this.purchaseMap = list.stream().collect(
                Collectors.toMap(OpsPurchaseStatusToWMDto::getOpsRequestpurchase, OpsPurchaseStatusToWMDto::getOpsPurchaseorder));
        this.requestList = new ArrayList<>(purchaseMap.keySet());
        Map<String, OpsPurchaseorder> poMap = new HashMap<>();
        for (OpsPurchaseorder po : purchaseMap.values()) {
            poMap.put(PoNoUtil.getFullPoNo(po), po);
        }
        this.purchaseList = new ArrayList<>(poMap.values());
    }


    public List<OpsRequestpurchase> getRequests() {
        return requestList;
    }


    public List<OpsPurchaseorder> getPurchases() {
        return purchaseList;
    }


    public Map<OpsRequestpurchase, OpsPurchaseorder> getPurchaseMap() {
        return purchaseMap;
    }

    public Map<String, OpsWarehouse> getRequestWarehouseMap() {
        return requestWarehouseMap;
    }

    public void setRequestWarehouseMap(Map<String, OpsWarehouse> requestWarehouseMap) {
        this.requestWarehouseMap = requestWarehouseMap;
    }

    public Map<String, String> getWarehouseCodeType() {
        return warehouseCodeType;
    }

    public void setWareHouseTypeMap(Map<String, String> map) {
        this.warehouseCodeType = map;
    }

    public List<OpsRequestpurchase> getRequestListForDB() {
        return requestListForDB;
    }

    public void setRequestListForDB(List<OpsRequestpurchase> requestsForDB) {
        this.requestListForDB = requestsForDB;
    }

    public UserDto getCreator() {
        return creator;
    }

    public void setCreator(UserDto creator) {
        this.creator = creator;
    }
}
