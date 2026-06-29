package com.smc.smccloud.service.impl.bincalc.creator;

import com.smc.smccloud.mapper.BindataRepository;
import com.smc.smccloud.model.bindata.BindataDO;
import com.smc.smccloud.model.binorder.BinOrderDetailDO;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Task 4：
 * 批量加载 IncludeNotBIN 查询结果，保持原单条查询“未删除优先，软删除兜底”的业务语义。
 */
final class BindataBatchLoader {

    // 批量条件包含 stockType、warehouseCode 和 modelNo，500 个型号一片可稳定避开参数上限。
    private static final int BATCH_SIZE = 500;

    private BindataBatchLoader() {
    }

    // 返回索引结构：外层 key=warehouseCode，内层 key=modelNo，value=该仓库该型号优先级最高的 BindataDO。
    static Map<String, Map<String, BindataDO>> load(BindataRepository repository,
                                                     Integer stockType,
                                                     List<BinOrderDetailDO> details) {
        Map<String, Set<String>> warehouseModelNos = collectModelNosByWarehouse(details);
        Map<String, Map<String, BindataDO>> index = new HashMap<>(hashMapCapacity(warehouseModelNos.size()));
        for (Map.Entry<String, Set<String>> entry : warehouseModelNos.entrySet()) {
            List<String> modelNos = new ArrayList<>(entry.getValue());
            for (int from = 0; from < modelNos.size(); from += BATCH_SIZE) {
                int to = Math.min(from + BATCH_SIZE, modelNos.size());
                List<BindataDO> bindatas = repository.findBindataIncludeNotBINBatch(
                        stockType, entry.getKey(), modelNos.subList(from, to));
                addToIndex(index, bindatas);
            }
        }
        return index;
    }

    // index 结构：外层 key=warehouseCode，内层 key=modelNo，value=BindataDO。
    static BindataDO get(Map<String, Map<String, BindataDO>> index, BinOrderDetailDO detail) {
        if (detail == null || StringUtils.isBlank(detail.getWarehouseCode()) || StringUtils.isBlank(detail.getModelNo())) {
            return null;
        }
        Map<String, BindataDO> modelIndex = index.get(detail.getWarehouseCode());
        return modelIndex == null ? null : modelIndex.get(detail.getModelNo());
    }

    // 返回待查询条件：key=warehouseCode，value=该仓库下需要查询的去重 modelNo 集合。
    private static Map<String, Set<String>> collectModelNosByWarehouse(List<BinOrderDetailDO> details) {
        Map<String, Set<String>> warehouseModelNos = new HashMap<>();
        if (CollectionUtils.isEmpty(details)) {
            return warehouseModelNos;
        }
        // 按 detail 自身仓库拆批，保留原单条查询使用 detail.warehouseCode 的语义。
        for (BinOrderDetailDO detail : details) {
            if (detail == null || StringUtils.isBlank(detail.getWarehouseCode()) || StringUtils.isBlank(detail.getModelNo())) {
                continue;
            }
            Set<String> modelNos = warehouseModelNos.computeIfAbsent(detail.getWarehouseCode(), key -> new LinkedHashSet<>());
            modelNos.add(detail.getModelNo());
        }
        return warehouseModelNos;
    }

    // index 结构：外层 key=warehouseCode，内层 key=modelNo，value=BindataDO。
    private static void addToIndex(Map<String, Map<String, BindataDO>> index, List<BindataDO> bindatas) {
        if (CollectionUtils.isEmpty(bindatas)) {
            return;
        }
        for (BindataDO bindata : bindatas) {
            if (bindata == null || StringUtils.isBlank(bindata.getWarehouseCode()) || StringUtils.isBlank(bindata.getModelNo())) {
                continue;
            }
            // SQL 已按 delFlag 排序取 rn=1，这里只负责建立 warehouse -> model 的快速索引。
            Map<String, BindataDO> modelIndex = index.computeIfAbsent(
                    bindata.getWarehouseCode(), key -> new HashMap<>());
            modelIndex.put(bindata.getModelNo(), bindata);
        }
    }

    private static int hashMapCapacity(int expectedSize) {
        if (expectedSize < 3) {
            return 4;
        }
        return (int) (expectedSize / 0.75f) + 1;
    }
}
