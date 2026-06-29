package com.smc.smccloud.model.binorder;

import com.sales.ops.db.entity.*;
import com.sales.ops.dto.assembly.StockAssemblyDetailView;
import com.sales.ops.enums.WarehouseTypeEnum;
import com.smc.smccloud.model.DataTypeVO;
import com.smc.smccloud.model.bindata.BindataDO;
import com.smc.smccloud.model.trans.TransOrderDO;
import com.smc.smccloud.model.warehouse.WarehouseVO;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Data
@Slf4j
public class CacheComponent {
    //<warehouseType,warehouse>
    private Map<String, List<WarehouseVO>> warehouseInfoMap;
    // key=warehouseCode，value=仓库信息，供计算阶段按仓库编码 O(1) 查询。
    private Map<String, WarehouseVO> warehouseByCodeMap;
    // key=warehouseCode，value=warehouseType，用于判断是否物流中心。
    private Map<String, String> warehouseTypeByCodeMap;
    // key=parentWarehouseCode，value=该物流中心下属分库编码列表。
    private Map<String, List<String>> subWarehouseCodesByParentMap;
    private List<String> masterWarehouses;
    //<modelNo,InventoryOfAllWarehouse>
    private Map<String, List<OpsInventory>> inventoryInfoMap;
    private Map<String, List<OpsInventoryMove>> inventoryMoveInfoMap;
    private Map<String, List<OpsRequestpurchase>> requestPurchaseInfoMap;
    private Map<String, List<OpsPurchaseorder>> purchaseOrderInfoMap;
    // key=ModelWarehouseKey(modelNo, warehouseCode)，value=sum(quantity-prepareQuantity)，用于有效库存数量 O(1) 查询。
    private Map<ModelWarehouseKey, Integer> availableInventoryQtyByModelWarehouse;
    // key=ModelWarehouseKey(modelNo, warehouseCode)，value=该型号该仓通用在途列表。
    private Map<ModelWarehouseKey, List<OpsInventoryMove>> inventoryMoveByModelWarehouse;
    // key=ModelWarehouseKey(modelNo, requestWarehouseId)，value=该型号该请购仓列表。
    private Map<ModelWarehouseKey, List<OpsRequestpurchase>> requestPurchaseByModelWarehouse;
    // key=ModelWarehouseKey(modelNo, receiveWarehouseId)，value=该型号该收货仓采购列表。
    private Map<ModelWarehouseKey, List<OpsPurchaseorder>> purchaseOrderByModelWarehouse;
    // key=modelNo，value=order_status_item.associateNo 去重集合。
    private Map<String, Set<String>> customerAssociateNoSetByModel;
    // 外层 key=modelNo，外层 value=采购订单行索引；内层 key=OrderLineKey(orderNo, itemNo, splitNo)，内层 value=采购订单行。
    private Map<String, Map<OrderLineKey, OpsPurchaseorder>> purchaseOrderLineByModel;
    private Map<String, List<TransOrderDO>> transOrderInfoMap;
    private Map<String, List<StockAssemblyDetailView>> assemblyOrderInfoMap;
    private Map<String, List<OrderStatusView>> customerOrderAssociateInfoMap;
    // key=modelNo，value=先行在库预约列表；当前先保留 List 粒度，是否继续按 orderStock/prepareOrder 分桶放到后续数据评估。
    private Map<String, List<PreStockResultDO>> preStockOrderInfoMap;
    // key=ops_inventory_move.inventory_id，value=存在 JYCK 预约标识的 moveId。
    private Set<Long> jyckPreparedMoveIds;
    // key=ops_inventory_move.inventory_id，value=存在 PCO 预约标识的 moveId。
    private Set<Long> pcoPreparedMoveIds;
    // key=prestock_result.order_no，value=TKCK 未出库数量。
    private Map<String, Integer> tkckNotOutQtyByOrderFno;
    // 批量查询无法安全拆分的 orderFno，后续保留单条懒加载兜底。
    private Set<String> tkckNotOutQtyLazyOrderFnos;
    // true 表示 tkckNotOutQtyByOrderFno 已按候选 orderFno 批量加载，缺失 key 可按 0 处理。
    private boolean tkckNotOutQtyBatchLoaded;

    private Map<String, List<BindataDO>> bindataDOMap;
    // key=modelNo + warehouseCode，value=model_exp_freq.AvgQtyOf8，用于非 BIN 物流中心过剩量计算。
    private Map<ModelWarehouseKey, Integer> modelExpFreqAvgQty8Map;
    //<warehouse,warehouseSequence>
    private Map<String, List<String>> transSequenceDict;
    private DataTypeVO dictMonths;

    public void rebuildWarehouseIndexes() {
        // Task 6：把仓库主数据重建为只读索引，供主仓判断、分库查找和调拨顺序计算复用。
        long totalStart = System.currentTimeMillis();
        long organizeStart = System.currentTimeMillis();
        Map<String, WarehouseVO> warehouseByCode = new HashMap<>();
        Map<String, String> warehouseTypeByCode = new HashMap<>();
        Map<String, List<String>> subWarehouseCodesByParent = new HashMap<>();
        List<String> masters = new ArrayList<>();
        if (warehouseInfoMap != null) {
            for (Map.Entry<String, List<WarehouseVO>> entry : warehouseInfoMap.entrySet()) {
                String warehouseType = entry.getKey();
                List<WarehouseVO> warehouses = entry.getValue();
                if (warehouses == null) {
                    continue;
                }
                for (WarehouseVO warehouse : warehouses) {
                    if (warehouse == null || warehouse.getWarehouseCode() == null) {
                        continue;
                    }
                    String warehouseCode = warehouse.getWarehouseCode();
                    warehouseByCode.put(warehouseCode, warehouse);
                    warehouseTypeByCode.put(warehouseCode, warehouseType);
                    if (WarehouseTypeEnum.RDC.getHouseTypeCode().equals(warehouseType)) {
                        masters.add(warehouseCode);
                    } else if (WarehouseTypeEnum.FDC.getHouseTypeCode().equals(warehouseType)
                            && warehouse.getParentCode() != null) {
                        subWarehouseCodesByParent
                                .computeIfAbsent(warehouse.getParentCode(), key -> new ArrayList<>())
                                .add(warehouseCode);
                    }
                }
            }
        }
        logIndexCost("1.4.1组织仓库派生索引临时映射", organizeStart,
                "整理出 " + sizeOf(warehouseByCode) + " 个仓库编码、" + sizeOf(warehouseTypeByCode)
                        + " 个仓库类型、" + sizeOf(masters) + " 个主仓、" + sizeOf(subWarehouseCodesByParent) + " 个父仓");
        organizeStart = System.currentTimeMillis();
        for (Map.Entry<String, List<String>> entry : subWarehouseCodesByParent.entrySet()) {
            entry.setValue(Collections.unmodifiableList(entry.getValue()));
        }
        this.warehouseByCodeMap = Collections.unmodifiableMap(warehouseByCode);
        this.warehouseTypeByCodeMap = Collections.unmodifiableMap(warehouseTypeByCode);
        this.subWarehouseCodesByParentMap = Collections.unmodifiableMap(subWarehouseCodesByParent);
        this.masterWarehouses = Collections.unmodifiableList(masters);
        logIndexCost("1.4.2固化仓库派生索引", organizeStart,
                "固化 " + sizeOf(this.warehouseByCodeMap) + " 个仓库编码、"
                        + sizeOf(this.subWarehouseCodesByParentMap) + " 个父仓");
        logIndexCost("1.4.3重建仓库派生索引总耗时", totalStart);
    }

    public WarehouseVO getWarehouseByCode(String warehouseCode) {
        return warehouseByCodeMap == null ? null : warehouseByCodeMap.get(warehouseCode);
    }

    public boolean isMasterWarehouse(String warehouseCode) {
        return warehouseTypeByCodeMap != null
                && WarehouseTypeEnum.RDC.getHouseTypeCode().equals(warehouseTypeByCodeMap.get(warehouseCode));
    }

    public List<String> getSubWarehouse(String warehouseCode) {
        if (subWarehouseCodesByParentMap == null) {
            return Collections.emptyList();
        }
        return subWarehouseCodesByParentMap.getOrDefault(warehouseCode, Collections.emptyList());
    }


    public List<String> getMasterWarehouses() {
        return masterWarehouses == null ? Collections.emptyList() : masterWarehouses;
    }

    public int getModelExpFreqAvgQty8(String modelNo, String warehouseCode) {
        if (modelExpFreqAvgQty8Map == null) {
            return 1;
        }
        Integer avgQty8 = modelExpFreqAvgQty8Map.get(new ModelWarehouseKey(modelNo, warehouseCode));
        return avgQty8 == null ? 1 : avgQty8;
    }

    public void rebuildInventoryIndexes() {
        // Task 7A-7B：把库存、在途、请购、采购和预约号等高频查询重建为只读索引。
        long totalStart = System.currentTimeMillis();
        Map<ModelWarehouseKey, Integer> availableQtyIndex = new HashMap<>();
        Map<ModelWarehouseKey, List<OpsInventoryMove>> inventoryMoveIndex = new HashMap<>();
        Map<ModelWarehouseKey, List<OpsRequestpurchase>> requestPurchaseIndex = new HashMap<>();
        Map<ModelWarehouseKey, List<OpsPurchaseorder>> purchaseOrderIndex = new HashMap<>();
        Map<String, Set<String>> customerAssociateNoIndex = new HashMap<>();
        Map<String, Map<OrderLineKey, OpsPurchaseorder>> purchaseOrderLineIndex = new HashMap<>();

        long indexStart = System.currentTimeMillis();
        buildAvailableInventoryQtyIndex(availableQtyIndex);
        logIndexCost("3.11.1组织库存有效数量索引", indexStart,
                "整理出 " + sizeOf(availableQtyIndex) + " 条索引");
        indexStart = System.currentTimeMillis();
        buildInventoryMoveIndex(inventoryMoveIndex);
        logIndexCost("3.11.2组织在途索引", indexStart,
                "整理出 " + sizeOf(inventoryMoveIndex) + " 条索引");
        indexStart = System.currentTimeMillis();
        buildRequestPurchaseIndex(requestPurchaseIndex);
        logIndexCost("3.11.3组织请购索引", indexStart,
                "整理出 " + sizeOf(requestPurchaseIndex) + " 条索引");
        indexStart = System.currentTimeMillis();
        buildPurchaseOrderIndexes(purchaseOrderIndex, purchaseOrderLineIndex);
        logIndexCost("3.11.4组织采购索引和采购订单行索引", indexStart,
                "整理出 " + sizeOf(purchaseOrderIndex) + " 条采购仓库索引、"
                        + sizeOf(purchaseOrderLineIndex) + " 个采购订单行型号");
        indexStart = System.currentTimeMillis();
        buildCustomerAssociateNoIndex(customerAssociateNoIndex);
        logIndexCost("3.11.5组织客户订单预约号索引", indexStart,
                "整理出 " + sizeOf(customerAssociateNoIndex) + " 个型号");

        indexStart = System.currentTimeMillis();
        this.availableInventoryQtyByModelWarehouse = availableQtyIndex;
        this.inventoryMoveByModelWarehouse = inventoryMoveIndex;
        this.requestPurchaseByModelWarehouse = requestPurchaseIndex;
        this.purchaseOrderByModelWarehouse = purchaseOrderIndex;
        this.customerAssociateNoSetByModel = customerAssociateNoIndex;
        this.purchaseOrderLineByModel = purchaseOrderLineIndex;
        logIndexCost("3.11.6设置库存派生索引对象", indexStart);
        logIndexCost("3.11.7重建库存派生索引总耗时", totalStart);
    }

    public int getInventoryQuantity(List<String> warehouseRange, String modelno) {
        int total = 0;
        if (warehouseRange == null || availableInventoryQtyByModelWarehouse == null) {
            return total;
        }
        for (String warehouseCode : warehouseRange) {
            Integer qty = availableInventoryQtyByModelWarehouse.get(new ModelWarehouseKey(modelno, warehouseCode));
            if (qty != null) {
                total += qty;
            }
        }
        return total;
    }

    public int getInventoryQuantity(String modelno, String warehouseCode) {
        if (availableInventoryQtyByModelWarehouse == null) {
            return 0;
        }
        Integer qty = availableInventoryQtyByModelWarehouse.get(new ModelWarehouseKey(modelno, warehouseCode));
        return qty == null ? 0 : qty;
    }

    public List<OpsInventoryMove> getInventoryMoveList(List<String> warehouseRange, String modelNo) {
        return collectByWarehouseRange(inventoryMoveByModelWarehouse, warehouseRange, modelNo);
    }

    public List<OpsRequestpurchase> getRequestPurchaseList(List<String> warehouseRange, String modelno) {
        return collectByWarehouseRange(requestPurchaseByModelWarehouse, warehouseRange, modelno);
    }

    public List<OpsPurchaseorder> getPurchaseOrderList(List<String> warehouseRange, String modelno) {
        return collectByWarehouseRange(purchaseOrderByModelWarehouse, warehouseRange, modelno);
    }

    public Set<String> getCustomerAssociateNoSet(String modelNo) {
        if (customerAssociateNoSetByModel == null) {
            return Collections.emptySet();
        }
        Set<String> associateNos = customerAssociateNoSetByModel.get(modelNo);
        return associateNos == null ? Collections.emptySet() : associateNos;
    }

    public OpsPurchaseorder getPurchaseOrderLine(String modelNo, String orderNo, Integer itemNo, Integer splitNo) {
        if (purchaseOrderLineByModel == null) {
            return null;
        }
        Map<OrderLineKey, OpsPurchaseorder> orderLineMap = purchaseOrderLineByModel.get(modelNo);
        return orderLineMap == null ? null : orderLineMap.get(new OrderLineKey(orderNo, itemNo, splitNo));
    }

    public boolean hasPreparedMoveIdCache() {
        return jyckPreparedMoveIds != null && pcoPreparedMoveIds != null;
    }

    public boolean isJyckPreparedMove(Long inventoryId) {
        return inventoryId != null && jyckPreparedMoveIds != null && jyckPreparedMoveIds.contains(inventoryId);
    }

    public boolean isPcoPreparedMove(Long inventoryId) {
        return inventoryId != null && pcoPreparedMoveIds != null && pcoPreparedMoveIds.contains(inventoryId);
    }

    public Integer getTkckNotOutQty(String orderFno) {
        if (orderFno == null || orderFno.trim().isEmpty()) {
            return null;
        }
        if (tkckNotOutQtyByOrderFno == null) {
            return null;
        }
        if (tkckNotOutQtyLazyOrderFnos != null && tkckNotOutQtyLazyOrderFnos.contains(orderFno)) {
            return null;
        }
        Integer quantity = tkckNotOutQtyByOrderFno.get(orderFno);
        if (quantity != null) {
            return quantity;
        }
        return tkckNotOutQtyBatchLoaded ? 0 : null;
    }

    public void putTkckNotOutQty(String orderFno, Integer quantity) {
        if (orderFno == null || orderFno.trim().isEmpty()) {
            return;
        }
        if (tkckNotOutQtyByOrderFno == null) {
            tkckNotOutQtyByOrderFno = new ConcurrentHashMap<>();
        }
        tkckNotOutQtyByOrderFno.put(orderFno, quantity == null ? 0 : quantity);
        if (tkckNotOutQtyLazyOrderFnos != null) {
            tkckNotOutQtyLazyOrderFnos.remove(orderFno);
        }
    }

    public Double getMaxControlMonths() {
        String value = Optional.ofNullable(dictMonths.getExtNote1()).orElse("0");
        return Double.parseDouble(value);
    }

    public Double getMaxStockAllMonths() {
        String value = Optional.ofNullable(dictMonths.getExtNote2()).orElse("0");
        return Double.parseDouble(value);
    }

    private void buildAvailableInventoryQtyIndex(Map<ModelWarehouseKey, Integer> availableQtyIndex) {
        if (inventoryInfoMap == null) {
            return;
        }
        for (List<OpsInventory> inventories : inventoryInfoMap.values()) {
            if (inventories == null) {
                continue;
            }
            for (OpsInventory inventory : inventories) {
                if (inventory == null || inventory.getModelno() == null || inventory.getWarehouseCode() == null) {
                    continue;
                }
                int qty = inventory.getQuantity() - inventory.getPrepareQuantity();
                ModelWarehouseKey key = new ModelWarehouseKey(inventory.getModelno(), inventory.getWarehouseCode());
                Integer oldQty = availableQtyIndex.get(key);
                availableQtyIndex.put(key, oldQty == null ? qty : oldQty + qty);
            }
        }
    }

    private void buildInventoryMoveIndex(Map<ModelWarehouseKey, List<OpsInventoryMove>> inventoryMoveIndex) {
        if (inventoryMoveInfoMap == null) {
            return;
        }
        for (List<OpsInventoryMove> moves : inventoryMoveInfoMap.values()) {
            if (moves == null) {
                continue;
            }
            for (OpsInventoryMove move : moves) {
                if (move == null || move.getModelno() == null || move.getWarehouseCode() == null) {
                    continue;
                }
                addToIndex(inventoryMoveIndex, new ModelWarehouseKey(move.getModelno(), move.getWarehouseCode()), move);
            }
        }
    }

    private void buildRequestPurchaseIndex(Map<ModelWarehouseKey, List<OpsRequestpurchase>> requestPurchaseIndex) {
        if (requestPurchaseInfoMap == null) {
            return;
        }
        for (List<OpsRequestpurchase> requestPurchases : requestPurchaseInfoMap.values()) {
            if (requestPurchases == null) {
                continue;
            }
            for (OpsRequestpurchase requestPurchase : requestPurchases) {
                if (requestPurchase == null || requestPurchase.getModelno() == null || requestPurchase.getRequestwarehouseid() == null) {
                    continue;
                }
                addToIndex(requestPurchaseIndex,
                        new ModelWarehouseKey(requestPurchase.getModelno(), requestPurchase.getRequestwarehouseid()),
                        requestPurchase);
            }
        }
    }

    private void buildPurchaseOrderIndexes(Map<ModelWarehouseKey, List<OpsPurchaseorder>> purchaseOrderIndex,
                                           Map<String, Map<OrderLineKey, OpsPurchaseorder>> purchaseOrderLineIndex) {
        if (purchaseOrderInfoMap == null) {
            return;
        }
        for (Map.Entry<String, List<OpsPurchaseorder>> entry : purchaseOrderInfoMap.entrySet()) {
            List<OpsPurchaseorder> purchaseOrders = entry.getValue();
            if (purchaseOrders == null) {
                continue;
            }
            for (OpsPurchaseorder purchaseOrder : purchaseOrders) {
                if (purchaseOrder == null || purchaseOrder.getModelno() == null) {
                    continue;
                }
                if (purchaseOrder.getReceivewarehouseid() != null) {
                    addToIndex(purchaseOrderIndex,
                            new ModelWarehouseKey(purchaseOrder.getModelno(), purchaseOrder.getReceivewarehouseid()),
                            purchaseOrder);
                }
                Map<OrderLineKey, OpsPurchaseorder> orderLineMap = purchaseOrderLineIndex.computeIfAbsent(
                        purchaseOrder.getModelno(), key -> new HashMap<>());
                OrderLineKey orderLineKey = new OrderLineKey(purchaseOrder.getOrderno(), purchaseOrder.getItemno(),
                        purchaseOrder.getSplititemno());
                // 原逻辑使用 stream().findFirst()，重复订单行时保留列表中的第一条。
                if (!orderLineMap.containsKey(orderLineKey)) {
                    orderLineMap.put(orderLineKey, purchaseOrder);
                }
            }
        }
    }

    private void buildCustomerAssociateNoIndex(Map<String, Set<String>> customerAssociateNoIndex) {
        if (customerOrderAssociateInfoMap == null) {
            return;
        }
        for (Map.Entry<String, List<OrderStatusView>> entry : customerOrderAssociateInfoMap.entrySet()) {
            List<OrderStatusView> orderStatusViews = entry.getValue();
            if (orderStatusViews == null) {
                continue;
            }
            Set<String> associateNos = customerAssociateNoIndex.computeIfAbsent(entry.getKey(), key -> new HashSet<>());
            for (OrderStatusView orderStatusView : orderStatusViews) {
                if (orderStatusView != null && orderStatusView.getAssociateNo() != null) {
                    associateNos.add(orderStatusView.getAssociateNo());
                }
            }
        }
    }

    private static <T> void addToIndex(Map<ModelWarehouseKey, List<T>> index, ModelWarehouseKey key, T value) {
        List<T> values = index.computeIfAbsent(key, item -> new ArrayList<>());
        values.add(value);
    }

    private static <T> List<T> collectByWarehouseRange(Map<ModelWarehouseKey, List<T>> index,
                                                       List<String> warehouseRange,
                                                       String modelNo) {
        if (index == null || warehouseRange == null || warehouseRange.isEmpty()) {
            return new ArrayList<>();
        }
        List<T> result = new ArrayList<>();
        for (String warehouseCode : warehouseRange) {
            List<T> values = index.get(new ModelWarehouseKey(modelNo, warehouseCode));
            if (values != null) {
                result.addAll(values);
            }
        }
        return result;
    }

    private void logIndexCost(String stage, long startMillis) {
        logIndexCost(stage, startMillis, null);
    }

    private void logIndexCost(String stage, long startMillis, String detail) {
        long costMillis = System.currentTimeMillis() - startMillis;
        String title = buildStageTitle(stage);
        if (detail == null || detail.isEmpty()) {
            log.info("BIN缓存索引耗时 | {}：耗时 {}ms（{}s）", title, costMillis, costMillis / 1000.0);
        } else {
            log.info("BIN缓存索引耗时 | {}：耗时 {}ms（{}s），{}", title, costMillis, costMillis / 1000.0, detail);
        }
    }

    private String buildStageTitle(String stage) {
        String stageNo = resolveStageNo(stage);
        String stageName = resolveStageName(stage);
        if (stageNo == null || stageNo.trim().isEmpty()) {
            return stageName;
        }
        if (stageName == null || stageName.trim().isEmpty()) {
            return stageNo;
        }
        return stageNo + " " + stageName;
    }

    private String resolveStageNo(String stage) {
        if (stage == null || stage.trim().isEmpty()) {
            return "";
        }
        int index = 0;
        while (index < stage.length()) {
            char value = stage.charAt(index);
            if (!Character.isDigit(value) && value != '.') {
                break;
            }
            index++;
        }
        String stageNo = stage.substring(0, index);
        while (stageNo.endsWith(".")) {
            stageNo = stageNo.substring(0, stageNo.length() - 1);
        }
        return stageNo;
    }

    private String resolveStageName(String stage) {
        if (stage == null || stage.trim().isEmpty()) {
            return "";
        }
        int index = 0;
        while (index < stage.length()) {
            char value = stage.charAt(index);
            if (!Character.isDigit(value) && value != '.') {
                break;
            }
            index++;
        }
        return stage.substring(index);
    }

    private static int sizeOf(Collection<?> collection) {
        return collection == null ? 0 : collection.size();
    }

    private static int sizeOf(Map<?, ?> map) {
        return map == null ? 0 : map.size();
    }

}
        
        
