package com.smc.smccloud.service.impl.bincalc.creator;

import com.smc.smccloud.mapper.BindataClientWarehouseMapper;
import com.smc.smccloud.model.bindata.BindataClientWarehouseDO;
import com.smc.smccloud.model.bindata.BindataDO;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Task 2：
 * 批量加载 bindata 对应的被集约方仓库，避免首次生成大量 detail 时按 bindata 逐条查询。
 */
final class BindataClientBatchLoader {

    // 单字段 IN 查询，按 1000 分片，避开 SQL Server 2100 参数上限。
    private static final int BATCH_SIZE = 1000;

    private BindataClientBatchLoader() {
    }

    // 返回索引结构：key=bindataId，value=该 bindata 对应的被集约方仓库编码列表。
    static Map<Long, List<String>> load(BindataClientWarehouseMapper mapper, List<BindataDO> bindatas) {
        List<Long> bindataIds = collectBindataIds(bindatas);
        Map<Long, List<String>> index = new HashMap<>(hashMapCapacity(bindataIds.size()));
        for (int from = 0; from < bindataIds.size(); from += BATCH_SIZE) {
            int to = Math.min(from + BATCH_SIZE, bindataIds.size());
            List<BindataClientWarehouseDO> rows = mapper.selectClientsByBindataIds(bindataIds.subList(from, to));
            addToIndex(index, rows);
        }
        return index;
    }

    // index 结构：key=bindataId，value=该 bindata 对应的被集约方仓库编码列表。
    static List<String> getClients(Map<Long, List<String>> index, Integer bindataId) {
        if (bindataId == null) {
            return Collections.emptyList();
        }
        List<String> clients = index.get(bindataId.longValue());
        return clients == null ? Collections.emptyList() : clients;
    }

    private static List<Long> collectBindataIds(List<BindataDO> bindatas) {
        if (CollectionUtils.isEmpty(bindatas)) {
            return Collections.emptyList();
        }
        // 保留原 bindata 顺序，同时去重，避免重复 id 放大 IN 参数数量。
        Set<Long> bindataIds = new LinkedHashSet<>();
        for (BindataDO bindata : bindatas) {
            if (bindata != null && bindata.getId() != null) {
                bindataIds.add(bindata.getId().longValue());
            }
        }
        return new ArrayList<>(bindataIds);
    }

    private static void addToIndex(Map<Long, List<String>> index, List<BindataClientWarehouseDO> rows) {
        if (CollectionUtils.isEmpty(rows)) {
            return;
        }
        for (BindataClientWarehouseDO row : rows) {
            if (row == null || row.getBindataId() == null || StringUtils.isBlank(row.getWarehouseCode())) {
                continue;
            }
            List<String> clients = index.computeIfAbsent(row.getBindataId(), key -> new ArrayList<>());
            clients.add(row.getWarehouseCode());
        }
    }

    private static int hashMapCapacity(int expectedSize) {
        if (expectedSize < 3) {
            return 4;
        }
        return (int) (expectedSize / 0.75f) + 1;
    }
}
