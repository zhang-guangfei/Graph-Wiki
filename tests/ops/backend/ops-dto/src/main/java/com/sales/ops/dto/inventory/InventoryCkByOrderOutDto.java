package com.sales.ops.dto.inventory;

import com.alibaba.fastjson.JSON;
import com.sales.ops.db.entity.OpsInventory;
import com.sales.ops.db.entity.OpsInventoryMove;
import com.sales.ops.db.entity.OpsRequestpurchase;
import com.sales.ops.enums.DoSourceEnum;

import java.io.Serializable;
import java.util.*;

/**
 * @author C02483
 * @version 1.0
 * @description: 库存调用输出
 * @date 2021/9/20 23:28
 */
public class InventoryCkByOrderOutDto implements Serializable {
    private Map<OpsInventory, Integer> inventoryMap;
    private Map<OpsInventoryMove, Integer> inventoryMoveMap;

    private Map<Object, Integer> inventoryMapSorted;

    List<OpsRequestpurchase> opsRequestpurchaseList;

    private Map<Long,Boolean> riskInvMap ;

    DoSourceEnum doSourceEnum;

    private AssBom assBom;

    private Boolean specialBom;

    private Set<String> warehouseCodeSets;

    private String warehouseCode;

    public InventoryCkByOrderOutDto() {
        this.inventoryMap = new HashMap<>();
        this.inventoryMoveMap = new HashMap<>();
        this.warehouseCodeSets = new HashSet<>();
        this.inventoryMapSorted = new HashMap<>();
        this.riskInvMap = new HashMap<>();
    }

    //深拷贝
    public InventoryCkByOrderOutDto deepCopy(){
        if (this.doSourceEnum == null) {
           return null;
        }
        InventoryCkByOrderOutDto copy = new InventoryCkByOrderOutDto();
        if(this.riskInvMap != null){
            copy.riskInvMap = new HashMap<>();
            copy.riskInvMap.putAll(this.riskInvMap);
        }

        if (this.inventoryMap != null) {
            // 深拷贝 key
            copy.inventoryMap = new HashMap<>();
            for (Map.Entry<OpsInventory, Integer> entry : this.inventoryMap.entrySet()) {
                String jsonString = JSON.toJSONString(entry.getKey());
                OpsInventory keyCopy  =  JSON.parseObject(jsonString, OpsInventory.class);
                copy.inventoryMap.put(keyCopy, entry.getValue());
            }
        }
        if (this.inventoryMoveMap != null) {
            copy.inventoryMoveMap = new HashMap<>();
            for (Map.Entry<OpsInventoryMove, Integer> entry : this.inventoryMoveMap.entrySet()) {
                String jsonString = JSON.toJSONString(entry.getKey());
                OpsInventoryMove keyCopy  =  JSON.parseObject(jsonString, OpsInventoryMove.class);
                copy.inventoryMoveMap.put(keyCopy, entry.getValue());
            }
        }
        if (this.inventoryMapSorted != null) {
            copy.inventoryMapSorted = new HashMap<>();
            for (Map.Entry<Object, Integer> entry : this.inventoryMapSorted.entrySet()) {
                Object originalKey = entry.getKey();
                Object keyCopy;

                // 尝试识别类型并深拷贝（根据实际可能的类型）
                if (originalKey instanceof OpsInventory) {
                    String jsonString = JSON.toJSONString(entry.getKey());
                    keyCopy  =  JSON.parseObject(jsonString, OpsInventory.class);
                } else if (originalKey instanceof OpsInventoryMove) {
                    String jsonString = JSON.toJSONString(entry.getKey());
                    keyCopy  =  JSON.parseObject(jsonString, OpsInventoryMove.class);
                } else if (originalKey instanceof String || originalKey instanceof Number) {
                    keyCopy = originalKey; // 不可变，安全
                } else {
                    // 无法处理的类型：只能浅拷贝（有风险）或抛异常
                    keyCopy = originalKey;
                }
                copy.inventoryMapSorted.put(keyCopy, entry.getValue());
            }
        }
        if (this.warehouseCodeSets != null) {
            copy.warehouseCodeSets = new HashSet<>(this.warehouseCodeSets);
        }
        if (this.assBom != null) {
            copy.assBom = this.assBom.deepCopy();
        }
        if (this.doSourceEnum != null) {
            copy.doSourceEnum = this.doSourceEnum;
        }
        if (this.specialBom != null) {
            copy.specialBom = this.specialBom;
        }
        if (this.warehouseCode != null) {
            copy.warehouseCode = this.warehouseCode;
        }
        return copy;
    }

    public List<OpsRequestpurchase> getOpsRequestpurchaseList() {
        return opsRequestpurchaseList;
    }

    public void setOpsRequestpurchaseList(List<OpsRequestpurchase> opsRequestpurchaseList) {
        this.opsRequestpurchaseList = opsRequestpurchaseList;
    }

    public DoSourceEnum getDoSourceEnum() {
        return doSourceEnum;
    }

    public void setDoSourceEnum(DoSourceEnum doSourceEnum) {
        this.doSourceEnum = doSourceEnum;
    }

    public Map<OpsInventory, Integer> getInventoryMap() {
        return inventoryMap;
    }


    public Map<OpsInventoryMove, Integer> getInventoryMoveMap() {
        return inventoryMoveMap;
    }


    public Set<String> getWarehouseCodeSets() {
        return warehouseCodeSets;
    }

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
    }

    public Map<Object, Integer> getInventoryMapSorted() {
        return inventoryMapSorted;
    }

    public void addMapSorted(Object o) {

        this.inventoryMapSorted.put(o, this.inventoryMapSorted.size() + 1);
    }


    public AssBom getAssBom() {
        return assBom;
    }

    public void setAssBom(AssBom assBom) {
        this.assBom = assBom;
    }

    public Boolean getSpecialBom() {
        return specialBom;
    }

    public void setSpecialBom(Boolean specialBom) {
        this.specialBom = specialBom;
    }

    public Map<Long, Boolean> getRiskInvMap() {
        return riskInvMap;
    }

    public void setRiskInvMap(Map<Long, Boolean> riskInvMap) {
        this.riskInvMap = riskInvMap;
    }
}
