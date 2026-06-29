package com.smc.smccloud.service.impl;

import com.alibaba.fastjson.JSON;
import com.smc.smccloud.core.redis.RedisManager;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

final class BindataImportLogContext {

    private static final long SLOW_CATEGORY_THRESHOLD_MS = 100L;
    private static final long SLOW_ROW_THRESHOLD_MS = 1000L;
    private static final int PROGRESS_BATCH_SIZE = 5000;
    private static final int PROGRESS_LOG_BATCH_SIZE = 50000;
    private static final String SUMMARY_KEY_SUFFIX = ":summary";

    private final Logger log;
    private final RedisManager redisUtil;
    private final String rediskey;
    private final LinkedHashMap<String, Object> summary = new LinkedHashMap<>();
    private final Map<String, Long> rawMetricMap = new HashMap<>();
    private boolean flushed;

    BindataImportLogContext(Logger log, RedisManager redisUtil, String rediskey, String importId, int totalRows) {
        this.log = log;
        this.redisUtil = redisUtil;
        this.rediskey = rediskey;
        summary.put("导入ID", importId);
        summary.put("Redis键", rediskey);
        summary.put("汇总Redis键", buildSummaryRedisKey());
        summary.put("状态", "进行中");
        summary.put("总行数", totalRows);
        summary.put("阶段汇总", new LinkedHashMap<String, Object>());
        syncToRedis();
    }

    boolean shouldLogProgress(int processed, int total) {
        return processed % PROGRESS_LOG_BATCH_SIZE == 0 || processed == total;
    }

    int recordPersistProgress(int successCount,
                              int processed,
                              int total,
                              int ambiguousFallbackCount,
                              int noChangeSkipCount,
                              int updateCount,
                              int insertCount,
                              int clientSyncCount,
                              int centreResetCount,
                              long phaseStart) {
        int progressUpdateCount = 0;
        if (successCount % PROGRESS_BATCH_SIZE == 0 || successCount == total) {
            progressUpdateCount++;
            redisUtil.set(rediskey, "已导入" + successCount + "条记录");
        }
        if (shouldLogProgress(processed, total)) {
            progress("persist_bindata", processed, total,
                    "successCount", successCount,
                    "failedCount", processed - successCount,
                    "ambiguousFallbackCount", ambiguousFallbackCount,
                    "noChangeSkipCount", noChangeSkipCount,
                    "updateCount", updateCount,
                    "insertCount", insertCount,
                    "clientSyncCount", clientSyncCount,
                    "centreResetCount", centreResetCount,
                    "elapsedMs", System.currentTimeMillis() - phaseStart);
        }
        return progressUpdateCount;
    }

    void phase(String phase, long costMs, Integer count, Object... metrics) {
        LinkedHashMap<String, Object> phaseMetrics = new LinkedHashMap<>();
        phaseMetrics.put("耗时", formatDuration(costMs));
        if (count != null) {
            phaseMetrics.put("数量", count);
        }
        phaseMetrics.putAll(translateMetrics(toMap(metrics)));
        summary.put("当前阶段", translatePhase(phase));
        getPhaseSummary().put(translatePhase(phase), phaseMetrics);
        syncToRedis();
    }

    void progress(String phase, int processed, int total, Object... metrics) {
        LinkedHashMap<String, Object> progress = new LinkedHashMap<>();
        progress.put("阶段", translatePhase(phase));
        progress.put("已处理", processed);
        progress.put("总数", total);
        if (total > 0) {
            progress.put("进度(%)", processed * 100 / total);
        }
        progress.putAll(translateMetrics(toMap(metrics)));
        summary.put("当前阶段", translatePhase(phase));
        summary.put("当前进度", progress);
        syncToRedis();
    }

    void abort(String phase, String message, Integer row) {
        summary.put("状态", "失败");
        summary.put("失败阶段", translatePhase(phase));
        summary.put("失败原因", message);
        if (row != null) {
            summary.put("失败行号", row);
        }
        syncToRedis();
    }

    void slowCategory(String phase, long costMs) {
        if (costMs < SLOW_CATEGORY_THRESHOLD_MS) {
            return;
        }
        recordSlowMetric("慢分类", phase, costMs);
    }

    void slowRow(String phase, long costMs) {
        if (costMs < SLOW_ROW_THRESHOLD_MS) {
            return;
        }
        recordSlowMetric("慢行", phase, costMs);
    }

    void finishSuccess(Object... metrics) {
        finish(true, null, metrics);
    }

    void finishFailure(String message, Object... metrics) {
        finish(false, message, metrics);
    }

    void flushIfNecessary() {
        if (!flushed) {
            writeLog(summary);
            flushed = true;
        }
    }

    private void finish(boolean success, String message, Object... metrics) {
        summary.put("状态", success ? "成功" : "失败");
        if (StringUtils.isNotBlank(message)) {
            summary.put(success ? "说明" : "失败原因", message);
        }
        summary.putAll(translateMetrics(toMap(metrics)));
        LinkedHashMap<String, Object> progress = new LinkedHashMap<>();
        progress.put("阶段", success ? "已完成" : "导入失败");
        progress.put("进度(%)", success ? 100 : 0);
        summary.put("当前阶段", success ? "已完成" : "导入失败");
        summary.put("当前进度", progress);
        summary.put("完成时间", formatTimestamp(System.currentTimeMillis()));
        syncToRedis();
        writeLog(summary);
        flushed = true;
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> getPhaseSummary() {
        return (Map<String, Object>) summary.get("阶段汇总");
    }

    private void recordSlowMetric(String prefix, String phase, long costMs) {
        String countKey = prefix + "次数";
        String maxKey = prefix + "最大耗时";
        int count = summary.get(countKey) instanceof Number ? ((Number) summary.get(countKey)).intValue() : 0;
        long max = rawMetricMap.getOrDefault(maxKey, 0L);
        summary.put(countKey, count + 1);
        if (costMs >= max) {
            rawMetricMap.put(maxKey, costMs);
            summary.put(maxKey, formatDuration(costMs));
            summary.put(prefix + "阶段", translatePhase(phase));
        }
        syncToRedis();
    }

    private LinkedHashMap<String, Object> toMap(Object... metrics) {
        LinkedHashMap<String, Object> metricMap = new LinkedHashMap<>();
        if (metrics == null) {
            return metricMap;
        }
        for (int i = 0; i + 1 < metrics.length; i += 2) {
            metricMap.put(String.valueOf(metrics[i]), metrics[i + 1]);
        }
        return metricMap;
    }

    private LinkedHashMap<String, Object> translateMetrics(Map<String, Object> metrics) {
        LinkedHashMap<String, Object> translated = new LinkedHashMap<>();
        if (metrics == null || metrics.isEmpty()) {
            return translated;
        }
        for (Map.Entry<String, Object> entry : metrics.entrySet()) {
            Object value = entry.getValue();
            if ("phase".equals(entry.getKey()) && value != null) {
                value = translatePhase(String.valueOf(value));
            } else if ("success".equals(entry.getKey()) && value instanceof Boolean) {
                value = (Boolean) value ? "是" : "否";
            } else if (isDurationMetricKey(entry.getKey()) && value instanceof Number) {
                value = formatDuration(((Number) value).longValue());
            }
            translated.put(translateKey(entry.getKey()), value);
        }
        return translated;
    }

    private boolean isDurationMetricKey(String key) {
        return StringUtils.endsWith(key, "CostMs")
                || "costMs".equals(key)
                || "elapsedMs".equals(key)
                || "totalCostMs".equals(key);
    }

    private String translatePhase(String phase) {
        if (StringUtils.isBlank(phase)) {
            return StringUtils.EMPTY;
        }
        switch (phase) {
            case "load_master_warehouses":
                return "读取主仓";
            case "load_client_warehouses":
                return "读取仓库列表";
            case "prepare_input":
                return "解析并校验Excel";
            case "preload_existing":
                return "预加载Bindata";
            case "preload_client_relations":
                return "预加载被集约方关系";
            case "persist_bindata":
                return "执行落库";
            case "delete_stale_bindata":
                return "删除未导入旧数据";
            case "update_product_info":
                return "更新产品信息";
            case "property_update":
                return "刷新库存属性";
            case "query_existing_bindata":
                return "查询已有Bindata";
            case "reset_centre_flag":
                return "重置中央仓";
            case "update_bindata_main":
                return "更新Bindata";
            case "insert_bindata_main":
                return "新增Bindata";
            case "update_client_warehouse":
                return "同步被集约方";
            case "insert_client_relation":
                return "新增被集约方关系";
            case "property_lookup":
                return "查询库存属性";
            case "property_lookup_skip":
                return "跳过库存属性查询";
            case "get_one_bindata":
                return "查询单条Bindata";
            case "delete_client_relation":
                return "删除被集约方关系";
            default:
                return phase;
        }
    }

    private String translateKey(String key) {
        if (StringUtils.isBlank(key)) {
            return StringUtils.EMPTY;
        }
        switch (key) {
            case "redisKey":
                return "Redis键";
            case "totalRows":
                return "总行数";
            case "successCount":
                return "成功数";
            case "failedCount":
                return "失败数";
            case "deletedCount":
                return "删除数";
            case "masterWarehouseCostMs":
                return "读取主仓耗时";
            case "warehouseListCostMs":
                return "读取仓库列表耗时";
            case "prepareInputCostMs":
                return "解析校验耗时";
            case "preloadCostMs":
                return "预加载Bindata耗时";
            case "clientPreloadCostMs":
                return "预加载被集约方耗时";
            case "persistCostMs":
                return "落库耗时";
            case "deleteCostMs":
                return "删除旧数据耗时";
            case "updateProductInfoCostMs":
                return "更新产品信息耗时";
            case "propertyUpdateCostMs":
                return "库存属性处理耗时";
            case "existingQueryCostMs":
                return "旧数据查询耗时";
            case "totalCostMs":
                return "总耗时";
            case "propertyUpdateCount":
                return "库存属性处理次数";
            case "existingQueryCount":
                return "旧数据查询次数";
            case "staleCandidateCount":
                return "旧数据候选数";
            case "ambiguousExistingKeyCount":
                return "歧义旧键数";
            case "progressUpdateCount":
                return "进度刷新次数";
            case "ambiguousFallbackCount":
                return "歧义回退数";
            case "noChangeSkipCount":
                return "无变化跳过数";
            case "updateCount":
                return "更新数";
            case "insertCount":
                return "新增数";
            case "clientSyncCount":
                return "被集约方同步数";
            case "centreResetCount":
                return "中央仓重置数";
            case "deleteBatchCount":
                return "删除批次数";
            case "warehouseCount":
                return "仓库数";
            case "categoryCount":
                return "大类数";
            case "duplicateCheckCount":
                return "验重数";
            case "centreModelCount":
                return "中央仓型号数";
            case "elapsedMs":
                return "阶段已耗时";
            case "processed":
                return "已处理";
            case "total":
                return "总数";
            case "progressPct":
                return "进度(%)";
            case "count":
                return "数量";
            case "costMs":
                return "耗时";
            case "success":
                return "成功";
            case "message":
                return "失败原因";
            case "row":
                return "失败行号";
            default:
                return key;
        }
    }

    private String formatDuration(long durationMs) {
        if (durationMs >= 10000L) {
            return String.format("%.2f秒", durationMs / 1000D);
        }
        return durationMs + "毫秒";
    }

    private String formatTimestamp(long timestamp) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(timestamp));
    }

    private String buildSummaryRedisKey() {
        return StringUtils.isBlank(rediskey) ? null : rediskey + SUMMARY_KEY_SUFFIX;
    }

    private void syncToRedis() {
        String summaryRedisKey = buildSummaryRedisKey();
        if (StringUtils.isBlank(summaryRedisKey)) {
            return;
        }
        redisUtil.set(summaryRedisKey, JSON.toJSONString(summary), 86400);
    }

    private void writeLog(Map<String, Object> logMap) {
        log.info("{}", JSON.toJSONString(logMap));
    }
}

