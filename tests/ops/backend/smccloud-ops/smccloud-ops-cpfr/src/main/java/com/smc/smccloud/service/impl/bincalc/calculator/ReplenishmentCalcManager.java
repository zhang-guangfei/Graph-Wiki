package com.smc.smccloud.service.impl.bincalc.calculator;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.sales.ops.db.entity.*;
import com.sales.ops.dto.assembly.StockAssemblyDetailView;
import com.sales.ops.enums.SourceTypeEnum;
import com.sales.ops.enums.WarehouseTypeEnum;
import com.smc.smccloud.core.exception.BusinessException;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.model.constants.GlobalConstant;
import com.smc.smccloud.core.utils.DateUtil;
import com.smc.smccloud.core.utils.SMCApp;
import com.smc.smccloud.mapper.BinCalcInventoryBatchMapper;
import com.smc.smccloud.mapper.BinOrderDetailOrdingInfoMapper;
import com.smc.smccloud.mapper.BinOrderDetailPreInfoMapper;
import com.smc.smccloud.mapper.BindataBatchRepository;
import com.smc.smccloud.mapper.binorder.BinOrderDetailSplitMapper;
import com.smc.smccloud.mapper.binorder.BinorderDetailRepository;
import com.smc.smccloud.mapper.binorder.ModelExpFreqMapper;
import com.smc.smccloud.model.DataCodeVO;
import com.smc.smccloud.model.DataTypeVO;
import com.smc.smccloud.model.bindata.BindataDO;
import com.smc.smccloud.model.binorder.*;
import com.smc.smccloud.model.trans.TransOrderDO;
import com.smc.smccloud.model.warehouse.WarehouseQueryDTO;
import com.smc.smccloud.model.warehouse.WarehouseVO;
import com.smc.smccloud.service.CommonServiceFeignApi;
import com.smc.smccloud.service.DictCommonService;
import com.smc.smccloud.service.DictDataServiceFeignApi;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * todo：
 * 解决几个分支的逻辑要条例清晰，纵向逻辑要通畅
 * 考虑流程的统一性，削减分散化的分支逻辑，期望尽量用统一的模板方法设计模式来处理不同分支，尽管有些分支短有些分支长
 * **考虑未来客户bin的修改空间
 * **考虑程序计算效率，考虑缓存的加载和读取，某些只读字段的批量查询，考虑库存的缓存加载和读取机制
 * **考虑异步任务的可能性
 * 考虑横向分层，分为查询模块和决策模块
 * 考虑不同策略的封装和策略选择器的设计与实现
 * 考虑总体模块的分层和解耦
 */
@Slf4j
@Service
public class ReplenishmentCalcManager {
    // IN 查询按 500 个型号分片，避免 SQL Server 参数过多并控制单次结果集大小。
    private static final int INVENTORY_MODEL_BATCH_SIZE = 500;
    // model_exp_freq 结果随主仓数量放大，按 500 个型号一片控制单次结果集和参数数。
    private static final int MODEL_EXP_FREQ_MODEL_BATCH_SIZE = 500;
    // move 预约标识按候选 moveId 分片，控制 SQL Server IN 参数数量。
    private static final int MOVE_PREPARE_QUERY_BATCH_SIZE = 500;
    private static final int MOVE_PREPARE_BATCH_THRESHOLD = 500;
    private static final int MOVE_PREPARE_DETAIL_THRESHOLD = 1000;
    // TKCK 未出库数量按 orderId/orderItem 分片，每个候选 2 个 SQL 参数，1000 个候选低于 SQL Server 2100 参数上限。
    private static final int TKCK_NOT_OUT_QUERY_BATCH_SIZE = 1000;
    private static final int TKCK_NOT_OUT_BATCH_THRESHOLD = 100;

    @Resource
    private BinorderDetailRepository binorderDetailRepository;
    @Resource
    private CommonServiceFeignApi commonServiceFeignApi;
    @Resource
    private BinCalcInventoryBatchMapper binCalcInventoryBatchMapper;
    @Resource
    private ReplenishmentCalculator replenishmentCalculator;
    @Resource
    private BindataBatchRepository bindataBatchRepository;
    @Resource
    private DictCommonService dictCommonService;
    @Resource
    private DictDataServiceFeignApi dictDataServiceFeignApi;
    @Resource
    private BinOrderDetailSplitMapper binOrderDetailSplitMapper;
    @Resource
    private BinOrderDetailOrdingInfoMapper binOrderDetailOrdingInfoMapper;
    @Resource
    private BinOrderDetailPreInfoMapper binOrderDetailPreInfoMapper;
    @Resource
    private ModelExpFreqMapper modelExpFreqMapper;

    //仓库bin，计算库存
    public void calculateWarehouseInventory(Long calcId, Integer calcType) {
        // 仓库 BIN 计算主流程入口，负责缓存加载、逐条策略计算和结果写回。
        //查询要计算的数据
        log.info("开始加载数据到缓存");
        long startTimer = System.currentTimeMillis();
        List<BinOrderDetailDO> details = binorderDetailRepository.findBinOrderDetailByCalcId(calcId);
        long _calcTime = 0;
        int _index = 1;
        HashMap<Long, String> errorResult = new HashMap<>();
        List<BinOrderDetailDTO> list = new ArrayList<>();
        CacheComponent cache = getCacheComponent(details);
        long endTimer = System.currentTimeMillis();
        log.info("缓存加载完成，用时：{}s", (endTimer - startTimer) / 1000.0);//大约40s
        log.info("开始判断补库方案");
        long Timer1;
        long Timer2;
        Timer1 = System.currentTimeMillis();
        for (BinOrderDetailDO detail : details) {
            BinOrderDetailDTO dto;
            _index++;
            try {
                dto = replenishmentCalculator.replenishmentStrategySelector(detail, cache);
                list.add(dto);
            } catch (NullPointerException e) {
                log.error("空指针异常：", e);
                errorResult.put(detail.getId(), detail.getModelNo());
            } catch (Exception e) {
                // 不中断for循环的处理
                errorResult.put(detail.getId(), detail.getModelNo());
                log.error("补库方案计算异常", e);
                log.info("共计算条数：{}，计算时间总计：{}s", _index, _calcTime / 1000);
            }
            //无用，调试用来打印进度，1000条一次
            if (_index % 2000 == 0) {
                Timer2 = System.currentTimeMillis();
                _calcTime = _calcTime + (Timer2 - Timer1);
                log.info("计算进度{}/{},用时{}s,总共用时{}s", _index, details.size(), (Timer2 - Timer1) / 1000.0, _calcTime / 1000.0);
                Timer1 = System.currentTimeMillis();
            }
        }
        //计算完成
        log.info("补库方案计算完成，共计算条数：{}，计算时间总计：{}s", _index, _calcTime / 1000);
        log.error("计算库存异常结果：{}", JSONUtil.toJsonStr(errorResult));
        log.info("开始保存数据");
        Timer1 = System.currentTimeMillis();
        insertTable(calcId, list);
        Timer2 = System.currentTimeMillis();
        _calcTime = _calcTime + (Timer2 - Timer1);
        log.info("数据保存完成，耗时总计：{}s", _calcTime / 1000);
    }

    public CacheComponent getCacheComponent(List<BinOrderDetailDO> details) {
        // 统一加载本次计算需要的只读缓存，为单条 detail 策略计算提供输入。
        long cacheStart = System.currentTimeMillis();
        log.info("BIN缓存加载 | 0 缓存加载开始：本次需要计算 {} 条明细", sizeOf(details));
        //创建缓存对象
        CacheComponent cache = new CacheComponent();
        //加载缓存数据
        // Task 6：缓存仓库主数据并建立只读索引。
        long stepStart = System.currentTimeMillis();
        cacheWarehouseInfo(cache);
        logCacheCost("1.仓库主数据缓存总耗时", stepStart);
        logMemoryUsage("缓存仓库信息后");
        // Task 5：批量缓存非 BIN 月均值，替换 detail * 主仓 的单条查询。
        stepStart = System.currentTimeMillis();
        cacheModelExpFreqAvgQty8Info(cache, details);
        logCacheCost("2.非BIN月均缓存总耗时", stepStart);
        logMemoryUsage("缓存非BIN月均信息后");
        // Task 1、Task 7：批量缓存库存/在途，并重建高频读取索引。
        stepStart = System.currentTimeMillis();
        cacheInventoryInfo(cache, details);
        logCacheCost("3.库存相关缓存总耗时", stepStart);
        logMemoryUsage("缓存总体库存信息后");
        // Task 8：按候选量批量预查 move 预约标识，减少订货中数量计算阶段的循环查询。
        stepStart = System.currentTimeMillis();
        cachePreparedMoveIds(cache, details);
        logCacheCost("4.在途预约标识缓存总耗时", stepStart);
        logMemoryUsage("缓存move预约标识后");
        // Task 9：按候选量批量预查 TKCK 未出库数量，低候选量场景保留 lazy cache。
        stepStart = System.currentTimeMillis();
        cacheTkckNotOutQty(cache);
        logCacheCost("5.TKCK未出库数量缓存总耗时", stepStart);
        logMemoryUsage("缓存TKCK未出库数量后");
        // Task 4：缓存物流中心 bindata，供过剩量计算复用。
        stepStart = System.currentTimeMillis();
        cacheBindataInfo(cache, details);
        logCacheCost("6.物流中心Bindata缓存总耗时", stepStart);
        logMemoryUsage("缓存Bindata信息后");
        // 缓存调拨仓顺序和最大控制月数字典。
        stepStart = System.currentTimeMillis();
        cacheTransWarehouseSequenceByMaster(cache, details);
        logCacheCost("7.调拨仓顺序缓存总耗时", stepStart);
        logMemoryUsage("缓存调拨仓库顺序后");
        stepStart = System.currentTimeMillis();
        cacheMonthFromDict(cache);
        logCacheCost("8.最大控制月数字典缓存总耗时", stepStart);
        logCacheCost("9.缓存加载总耗时", cacheStart);
        return cache;
    }

    private void cacheModelExpFreqAvgQty8Info(CacheComponent cache, List<BinOrderDetailDO> details) {
        // Task 5：按本次计算涉及型号和所有主仓批量加载 AvgQtyOf8。
        long prepareStart = System.currentTimeMillis();
        List<String> modelNos = BinCalcCacheBatchSupport.distinctModelNos(details);
        List<String> masterWarehouses = cache.getMasterWarehouses();
        logCacheCost("2.1非BIN月均-组织查询条件", prepareStart,
                "涉及 " + sizeOf(modelNos) + " 个型号、" + sizeOf(masterWarehouses) + " 个主仓");
        Map<ModelWarehouseKey, Integer> avgQty8Map = new HashMap<>();
        if (!modelNos.isEmpty() && masterWarehouses != null && !masterWarehouses.isEmpty()) {
            int batchIndex = 1;
            for (List<String> modelNoBatch : ListUtil.split(modelNos, MODEL_EXP_FREQ_MODEL_BATCH_SIZE)) {
                long queryStart = System.currentTimeMillis();
                List<ModelExpFreqDO> rows = modelExpFreqMapper.selectAvgQtyOf8ByModelNosAndWarehouseCodes(modelNoBatch, masterWarehouses);
                logCacheCost("2.2查询model_exp_freq批次" + batchIndex, queryStart,
                        "本批 " + sizeOf(modelNoBatch) + " 个型号，返回 " + sizeOf(rows) + " 行");
                long indexStart = System.currentTimeMillis();
                putModelExpFreqAvgQty8(avgQty8Map, rows);
                logCacheCost("2.3组织AvgQtyOf8索引批次" + batchIndex, indexStart,
                        "当前已有 " + sizeOf(avgQty8Map) + " 条索引");
                batchIndex++;
            }
        }
        cache.setModelExpFreqAvgQty8Map(avgQty8Map);
        log.info("BIN缓存加载 | 2.4 非BIN月均索引已就绪：共 {} 条月均索引", sizeOf(avgQty8Map));
    }

    private void putModelExpFreqAvgQty8(Map<ModelWarehouseKey, Integer> avgQty8Map, List<ModelExpFreqDO> rows) {
        if (rows == null || rows.isEmpty()) {
            return;
        }
        for (ModelExpFreqDO row : rows) {
            if (row == null || StringUtils.isBlank(row.getModelNo()) || StringUtils.isBlank(row.getStockCode())
                    || row.getAvgQtyOf8() == null) {
                continue;
            }
            avgQty8Map.put(new ModelWarehouseKey(row.getModelNo(), row.getStockCode()), row.getAvgQtyOf8().intValue());
        }
    }

    private void logMemoryUsage(String stage) {
        Runtime runtime = Runtime.getRuntime();
        long totalMemory = runtime.totalMemory(); // 当前总分配内存
        long freeMemory = runtime.freeMemory();   // 当前空闲内存
        long usedMemory = totalMemory - freeMemory; // 已使用内存

        log.info("BIN缓存内存 | {}：已用 {}MB，总分配 {}MB，空闲 {}MB，最大可用 {}MB",
                stage,
                usedMemory / 1024 / 1024,
                totalMemory / 1024 / 1024,
                freeMemory / 1024 / 1024,
                runtime.maxMemory() / 1024 / 1024);
    }

    private void logCacheCost(String stage, long startMillis) {
        logCacheCost(stage, startMillis, null);
    }

    private void logCacheCost(String stage, long startMillis, String detail) {
        long costMillis = System.currentTimeMillis() - startMillis;
        String title = buildStageTitle(stage);
        if (StringUtils.isBlank(detail)) {
            log.info("BIN缓存耗时 | {}：耗时 {}ms（{}s）", title, costMillis, costMillis / 1000.0);
        } else {
            log.info("BIN缓存耗时 | {}：耗时 {}ms（{}s），{}", title, costMillis, costMillis / 1000.0, detail);
        }
    }

    private String buildStageTitle(String stage) {
        String stageNo = resolveStageNo(stage);
        String stageName = resolveStageName(stage);
        if (StringUtils.isBlank(stageNo)) {
            return stageName;
        }
        if (StringUtils.isBlank(stageName)) {
            return stageNo;
        }
        return stageNo + " " + stageName;
    }

    private String resolveStageNo(String stage) {
        if (StringUtils.isBlank(stage)) {
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
        if (StringUtils.isBlank(stage)) {
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

    //读取字典9002-4，缓存最大控制月数和可用月数
    private void cacheMonthFromDict(CacheComponent cache) {
        SMCApp.setGrantType(GlobalConstant.GrantType.CLIANT_CREDENTIALS);
        long queryStart = System.currentTimeMillis();
        ResultVo<DataTypeVO> resultVo = dictDataServiceFeignApi.getDataTypeCodesInfo("9002", "4");
        logCacheCost("8.1查询字典9002-4最大控制月数", queryStart,
                "请求" + ((resultVo != null && resultVo.isSuccess()) ? "成功" : "失败"));
        long organizeStart = System.currentTimeMillis();
        DataTypeVO data = resultVo.getData();
        cache.setDictMonths(data);
        logCacheCost("8.2组织最大控制月数字典缓存", organizeStart,
                data == null ? "未取到字典数据" : "已取到字典数据");
    }

    //调拨仓库顺序
    private static final String classCode = "4012";

    //缓存调拨仓库顺序配置表信息，用来计算调拨仓库顺序
    private void cacheTransWarehouseSequenceByMaster(CacheComponent cache, List<BinOrderDetailDO> details) {
        Map<String, List<String>> warehouseSequence = new HashMap<>();
        cache.setTransSequenceDict(warehouseSequence);
        long queryStart = System.currentTimeMillis();
        ResultVo<List<DataCodeVO>> resultVo = dictCommonService.getDataCodes(classCode);
        logCacheCost("7.1查询调拨仓顺序字典" + classCode, queryStart,
                "请求" + ((resultVo != null && resultVo.isSuccess()) ? "成功" : "失败")
                        + "，返回 " + (resultVo == null ? 0 : sizeOf(resultVo.getData())) + " 行");
        if (!resultVo.isSuccess()) {
            throw new BusinessException("获取可调库仓库失败," + resultVo.getMessage());
        }
        long organizeStart = System.currentTimeMillis();
        List<DataCodeVO> list = resultVo.getData();
        for (DataCodeVO item : list) {
            List<String> warehouseCodes = new ArrayList<>();
            String data = item.getExtNote1();
            if (StringUtils.isNotBlank(data)) {
                String[] warehouseList = data.split("-");
                warehouseCodes.addAll(Arrays.asList(warehouseList));
            }
            cache.getTransSequenceDict().put(item.getCode(), warehouseCodes);
        }
        logCacheCost("7.2组织调拨仓顺序索引", organizeStart,
                "整理出 " + sizeOf(cache.getTransSequenceDict()) + " 个主仓配置");
    }
    //查询各个型号的所有物流中心的bin信息，用来计算各物流中心的过剩产数量
    private void cacheBindataInfo(CacheComponent cache, List<BinOrderDetailDO> details) {
        // Task 4：缓存所有物流中心的 bindata，用于后续每个主仓的过剩量计算。
        //<modelno,bindataOfMasterWarehouse>
        Map<String, List<BindataDO>> bindataDOMap = new HashMap<>();
        long queryStart = System.currentTimeMillis();
        List<BindataDO> masterWarehouseBindata = bindataBatchRepository.findBindataByMasterWarehouse();
        logCacheCost("6.1查询物流中心Bindata", queryStart,
                "返回 " + sizeOf(masterWarehouseBindata) + " 行");
        long organizeStart = System.currentTimeMillis();
        for (BindataDO bindataDO : masterWarehouseBindata) {
            String modelNo = bindataDO.getModelNo();
            if (!bindataDOMap.containsKey(modelNo)) {
                List<BindataDO> bindataList = new ArrayList<>();
                bindataList.add(bindataDO);
                bindataDOMap.put(modelNo, bindataList);
            } else {
                List<BindataDO> bindataDOList = bindataDOMap.get(modelNo);
                bindataDOList.add(bindataDO);
            }
        }
        cache.setBindataDOMap(bindataDOMap);
        logCacheCost("6.2组织物流中心Bindata索引", organizeStart,
                "整理出 " + sizeOf(bindataDOMap) + " 个型号");
    }

    //批量查询，并存到缓存对象中，，用来计算有效库存和订货中库存，表包括：在库在途库存、请购采购、调库、组换、客户订单、先行在库表
    // ops_inventory、ops_inventory_move、ops_requestPurchase、ops_purchaseOrder、
    // trans_order、stock_assembly_detail_view、order_status_view、prestock_result
    private Map<String, List<OpsInventory>> cacheInventoryInfo(CacheComponent cache, List<BinOrderDetailDO> details) {
        // Task 1、Task 7：批量加载计算所需库存数据，并在加载后重建高频查询索引。
        long queryStart = System.currentTimeMillis();
        List<OpsPurchaseorder> purchaseorders = binCalcInventoryBatchMapper.selectPurchaseOrderAllWarehouse();
        logCacheCost("3.1.1查询采购订单全仓", queryStart, "返回 " + sizeOf(purchaseorders) + " 行");
        // TODO Task 7E：缓存加载阶段 groupingBy 只执行一次，优先级低于 detail 循环内 stream；后续可改 for 循环并预估容量。
        long organizeStart = System.currentTimeMillis();
        Map<String, List<OpsPurchaseorder>> purchaseMap = purchaseorders.stream().collect(Collectors.groupingBy(OpsPurchaseorder::getModelno));
        logCacheCost("3.1.2组织采购订单型号索引", organizeStart, "整理出 " + sizeOf(purchaseMap) + " 个型号");

        queryStart = System.currentTimeMillis();
        List<OpsRequestpurchase> requestpurchases = binCalcInventoryBatchMapper.selectRequestPurchaseAllWarehouse();
        logCacheCost("3.2.1查询请购单全仓", queryStart, "返回 " + sizeOf(requestpurchases) + " 行");
        organizeStart = System.currentTimeMillis();
        Map<String, List<OpsRequestpurchase>> requestMap = requestpurchases.stream().collect(Collectors.groupingBy(OpsRequestpurchase::getModelno));
        logCacheCost("3.2.2组织请购单型号索引", organizeStart, "整理出 " + sizeOf(requestMap) + " 个型号");

        queryStart = System.currentTimeMillis();
        List<TransOrderDO> transOrderDOS = binCalcInventoryBatchMapper.selectTransOrderAllWarehouse();
        logCacheCost("3.3.1查询主动调拨单全仓", queryStart, "返回 " + sizeOf(transOrderDOS) + " 行");
        organizeStart = System.currentTimeMillis();
        Map<String, List<TransOrderDO>> transOrderMap = transOrderDOS.stream().collect(Collectors.groupingBy(TransOrderDO::getModelNo));
        logCacheCost("3.3.2组织主动调拨型号索引", organizeStart, "整理出 " + sizeOf(transOrderMap) + " 个型号");

        queryStart = System.currentTimeMillis();
        List<StockAssemblyDetailView> stockAssemblyDetailViews = binCalcInventoryBatchMapper.selectStockAssemblyDetailAllWarehouse();
        logCacheCost("3.4.1查询组换单全仓", queryStart, "返回 " + sizeOf(stockAssemblyDetailViews) + " 行");
        organizeStart = System.currentTimeMillis();
        Map<String, List<StockAssemblyDetailView>> assemblyMap = stockAssemblyDetailViews.stream().collect(Collectors.groupingBy(StockAssemblyDetailView::getModelNo));
        logCacheCost("3.4.2组织组换单型号索引", organizeStart, "整理出 " + sizeOf(assemblyMap) + " 个型号");

        queryStart = System.currentTimeMillis();
        List<PreStockResultDO> preStockResultDOS = binCalcInventoryBatchMapper.selectPreStockOrderAllWarehouse();
        logCacheCost("3.5.1查询先行在库预约全仓", queryStart, "返回 " + sizeOf(preStockResultDOS) + " 行");
        organizeStart = System.currentTimeMillis();
        Map<String, List<PreStockResultDO>> preStockMap = preStockResultDOS.stream().collect(Collectors.groupingBy(PreStockResultDO::getModelNo));
        logCacheCost("3.5.2组织先行在库型号索引", organizeStart, "整理出 " + sizeOf(preStockMap) + " 个型号");

        queryStart = System.currentTimeMillis();
        List<OrderStatusView> orderStatusViews = binCalcInventoryBatchMapper.selectOrderStatusItemAllWarehouse();
        logCacheCost("3.6.1查询客户订单预约全仓", queryStart, "返回 " + sizeOf(orderStatusViews) + " 行");
        organizeStart = System.currentTimeMillis();
        Map<String, List<OrderStatusView>> statusMap = orderStatusViews.stream().collect(Collectors.groupingBy(OrderStatusView::getModelno));
        logCacheCost("3.6.2组织客户订单预约型号索引", organizeStart, "整理出 " + sizeOf(statusMap) + " 个型号");
        logMemoryUsage("缓存库存信息后");

        List<OpsInventory> inventories = new ArrayList<>();
        List<OpsInventoryMove> inventoryMoves = new ArrayList<>();
        organizeStart = System.currentTimeMillis();
        List<String> modelNos = BinCalcCacheBatchSupport.distinctModelNos(details);
        logCacheCost("3.7组织本次计算型号列表", organizeStart, "本次涉及 " + sizeOf(modelNos) + " 个型号");
        if (!modelNos.isEmpty()) {
            int batchIndex = 1;
            for (List<String> modelNoBatch : ListUtil.split(modelNos, INVENTORY_MODEL_BATCH_SIZE)) {
                queryStart = System.currentTimeMillis();
                List<OpsInventory> batchInventories =
                        binCalcInventoryBatchMapper.selectNormalAvailableInventoryByModelNos(modelNoBatch);
                logCacheCost("3.8.1查询通用在库批次" + batchIndex, queryStart,
                        "本批 " + sizeOf(modelNoBatch) + " 个型号，返回 " + sizeOf(batchInventories) + " 行");
                if (batchInventories != null) {
                    inventories.addAll(batchInventories);
                }
                queryStart = System.currentTimeMillis();
                List<OpsInventoryMove> batchInventoryMoves =
                        binCalcInventoryBatchMapper.selectMoveAvailableInventoryByModelNos(modelNoBatch);
                logCacheCost("3.8.2查询通用在途批次" + batchIndex, queryStart,
                        "本批 " + sizeOf(modelNoBatch) + " 个型号，返回 " + sizeOf(batchInventoryMoves) + " 行");
                if (batchInventoryMoves != null) {
                    inventoryMoves.addAll(batchInventoryMoves);
                }
                batchIndex++;
            }
        }
        organizeStart = System.currentTimeMillis();
        Map<String, List<OpsInventory>> inventoryInfoMap =
                BinCalcCacheBatchSupport.groupInventoryByModel(details, inventories);
        logCacheCost("3.8.3组织通用在库型号索引", organizeStart,
                "用 " + sizeOf(inventories) + " 行库存整理出 " + sizeOf(inventoryInfoMap) + " 个型号");
        organizeStart = System.currentTimeMillis();
        Map<String, List<OpsInventoryMove>> inventoryMoveInfoMap =
                BinCalcCacheBatchSupport.groupInventoryMoveByModel(details, inventoryMoves);
        logCacheCost("3.8.4组织通用在途型号索引", organizeStart,
                "用 " + sizeOf(inventoryMoves) + " 行在途整理出 " + sizeOf(inventoryMoveInfoMap) + " 个型号");
        logMemoryUsage("批量缓存在库在途库存后");

        organizeStart = System.currentTimeMillis();
        for (BinOrderDetailDO detail : details) {
            if (!requestMap.containsKey(detail.getModelNo())) {
                requestMap.put(detail.getModelNo(), new ArrayList<>());
            }
            if (!purchaseMap.containsKey(detail.getModelNo())) {
                purchaseMap.put(detail.getModelNo(), new ArrayList<>());
            }
            if (!transOrderMap.containsKey(detail.getModelNo())) {
                transOrderMap.put(detail.getModelNo(), new ArrayList<>());
            }
            if (!assemblyMap.containsKey(detail.getModelNo())) {
                assemblyMap.put(detail.getModelNo(), new ArrayList<>());
            }
            if (!preStockMap.containsKey(detail.getModelNo())) {
                preStockMap.put(detail.getModelNo(), new ArrayList<>());
            }
            if (!statusMap.containsKey(detail.getModelNo())) {
                statusMap.put(detail.getModelNo(), new ArrayList<>());
            }
        }
        logCacheCost("3.9补齐空缓存列表", organizeStart, "检查 " + sizeOf(details) + " 条明细");
        organizeStart = System.currentTimeMillis();
        cache.setInventoryInfoMap(inventoryInfoMap);
        cache.setInventoryMoveInfoMap(inventoryMoveInfoMap);
        cache.setRequestPurchaseInfoMap(requestMap);
        cache.setPurchaseOrderInfoMap(purchaseMap);
        cache.setTransOrderInfoMap(transOrderMap);
        cache.setAssemblyOrderInfoMap(assemblyMap);
        cache.setCustomerOrderAssociateInfoMap(statusMap);
        cache.setPreStockOrderInfoMap(preStockMap);
        logCacheCost("3.10设置库存基础缓存对象", organizeStart,
                "库存 " + sizeOf(inventoryInfoMap) + " 个型号，在途 " + sizeOf(inventoryMoveInfoMap)
                        + " 个型号，请购 " + sizeOf(requestMap) + " 个型号，采购 " + sizeOf(purchaseMap) + " 个型号");
        organizeStart = System.currentTimeMillis();
        cache.rebuildInventoryIndexes();
        logCacheCost("3.11重建库存派生索引总耗时", organizeStart);
        return inventoryInfoMap;
    }

    private void cachePreparedMoveIds(CacheComponent cache, List<BinOrderDetailDO> details) {
        long organizeStart = System.currentTimeMillis();
        Set<Long> candidateMoveIds = collectPreparedMoveCandidateIds(cache);
        logCacheCost("4.1组织在途预约标识候选ID", organizeStart,
                "找到 " + sizeOf(candidateMoveIds) + " 个候选在途ID");
        int detailSize = details == null ? 0 : details.size();
        boolean shouldBatch = candidateMoveIds.size() >= MOVE_PREPARE_BATCH_THRESHOLD
                || detailSize >= MOVE_PREPARE_DETAIL_THRESHOLD;
        log.info("BIN缓存加载 | 4.1.1 在途预约标识候选统计：找到 {} 个候选在途ID，本次 {} 条明细，{}批量预查",
                candidateMoveIds.size(), detailSize, shouldBatch ? "执行" : "不执行");
        if (!shouldBatch) {
            return;
        }
        long queryStart = System.currentTimeMillis();
        Set<Long> jyckPreparedMoveIds = selectPreparedMoveIds(candidateMoveIds, true, "JYCK");
        logCacheCost("4.2查询JYCK预约在途ID总耗时", queryStart,
                "命中 " + sizeOf(jyckPreparedMoveIds) + " 个在途ID");
        queryStart = System.currentTimeMillis();
        Set<Long> pcoPreparedMoveIds = selectPreparedMoveIds(candidateMoveIds, false, "PCO");
        logCacheCost("4.3查询PCO预约在途ID总耗时", queryStart,
                "命中 " + sizeOf(pcoPreparedMoveIds) + " 个在途ID");
        organizeStart = System.currentTimeMillis();
        cache.setJyckPreparedMoveIds(Collections.unmodifiableSet(jyckPreparedMoveIds));
        cache.setPcoPreparedMoveIds(Collections.unmodifiableSet(pcoPreparedMoveIds));
        logCacheCost("4.4组织在途预约标识集合缓存", organizeStart,
                "JYCK " + sizeOf(jyckPreparedMoveIds) + " 个，PCO " + sizeOf(pcoPreparedMoveIds) + " 个");
    }

    private Set<Long> collectPreparedMoveCandidateIds(CacheComponent cache) {
        Map<String, List<OpsInventoryMove>> inventoryMoveInfoMap = cache.getInventoryMoveInfoMap();
        Set<Long> moveIds = new HashSet<>();
        if (inventoryMoveInfoMap == null) {
            return moveIds;
        }
        for (List<OpsInventoryMove> moves : inventoryMoveInfoMap.values()) {
            if (moves == null) {
                continue;
            }
            for (OpsInventoryMove move : moves) {
                if (move == null || move.getInventoryId() == null || move.getQuantity() == null
                        || move.getQuantity() <= 0) {
                    continue;
                }
                if (SourceTypeEnum.DB.getType().equals(move.getSourceType())
                        || SourceTypeEnum.RETURN.getType().equals(move.getSourceType())) {
                    moveIds.add(move.getInventoryId());
                }
            }
        }
        return moveIds;
    }

    private Set<Long> selectPreparedMoveIds(Set<Long> candidateMoveIds, boolean jyck, String label) {
        if (candidateMoveIds.isEmpty()) {
            return Collections.emptySet();
        }
        Set<Long> preparedMoveIds = new HashSet<>(expectedCapacity(candidateMoveIds.size()));
        List<Long> moveIds = new ArrayList<>(candidateMoveIds);
        String stagePrefix = "JYCK".equals(label) ? "4.2" : "4.3";
        int batchIndex = 1;
        for (List<Long> moveIdBatch : ListUtil.split(moveIds, MOVE_PREPARE_QUERY_BATCH_SIZE)) {
            long queryStart = System.currentTimeMillis();
            List<Long> rows = jyck
                    ? binOrderDetailOrdingInfoMapper.selectDBCKMoveNotPrepareByJYCK(moveIdBatch)
                    : binOrderDetailOrdingInfoMapper.selectDBCKMoveNotPrepareByPCO(moveIdBatch);
            logCacheCost(stagePrefix + ".1查询" + label + "预约在途ID批次" + batchIndex, queryStart,
                    "入参 " + sizeOf(moveIdBatch) + " 个在途ID，返回 " + sizeOf(rows) + " 行");
            long organizeStart = System.currentTimeMillis();
            if (rows != null) {
                preparedMoveIds.addAll(rows);
            }
            logCacheCost(stagePrefix + ".2组织" + label + "预约在途ID集合批次" + batchIndex, organizeStart,
                    "当前集合已有 " + sizeOf(preparedMoveIds) + " 个ID");
            batchIndex++;
        }
        return preparedMoveIds;
    }

    private void cacheTkckNotOutQty(CacheComponent cache) {
        long organizeStart = System.currentTimeMillis();
        Set<String> orderFnos = collectTkckCandidateOrderFnos(cache);
        logCacheCost("5.1组织TKCK候选预约单号", organizeStart,
                "找到 " + sizeOf(orderFnos) + " 个候选预约单号");
        organizeStart = System.currentTimeMillis();
        Map<String, Integer> qtyMap = new ConcurrentHashMap<>(expectedCapacity(orderFnos.size()));
        cache.setTkckNotOutQtyByOrderFno(qtyMap);
        logCacheCost("5.2初始化TKCK未出库数量缓存映射", organizeStart,
                "初始容量按 " + expectedCapacity(orderFnos.size()) + " 预估");
        if (orderFnos.size() < TKCK_NOT_OUT_BATCH_THRESHOLD) {
            cache.setTkckNotOutQtyBatchLoaded(false);
            log.info("BIN缓存加载 | 5.3 TKCK未出库数量候选较少：共 {} 个预约单号，保留懒加载", orderFnos.size());
            return;
        }
        organizeStart = System.currentTimeMillis();
        Set<String> lazyOrderFnos = new HashSet<>();
        List<TkckOrderFnoKey> orderKeyList = toTkckOrderFnoKeys(orderFnos, lazyOrderFnos);
        cache.setTkckNotOutQtyLazyOrderFnos(lazyOrderFnos);
        logCacheCost("5.3组织TKCK查询键", organizeStart,
                "可批量查询 " + sizeOf(orderKeyList) + " 个预约单号，保留懒加载 " + sizeOf(lazyOrderFnos) + " 个");
        if (orderKeyList.isEmpty()) {
            cache.setTkckNotOutQtyBatchLoaded(false);
            log.info("BIN缓存加载 | 5.4 TKCK未出库数量没有可批量查询的预约单号，保留懒加载");
            return;
        }
        int batchIndex = 1;
        for (List<TkckOrderFnoKey> orderKeyBatch : ListUtil.split(orderKeyList, TKCK_NOT_OUT_QUERY_BATCH_SIZE)) {
            long queryStart = System.currentTimeMillis();
            List<TkckNotOutQtyLite> rows = binOrderDetailOrdingInfoMapper.selectTKCKNotOutQtyBatch(orderKeyBatch);
            logCacheCost("5.4查询TKCK未出库数量批次" + batchIndex, queryStart,
                    "入参 " + sizeOf(orderKeyBatch) + " 个预约单号，返回 " + sizeOf(rows) + " 行");
            organizeStart = System.currentTimeMillis();
            putTkckNotOutQty(qtyMap, rows);
            logCacheCost("5.5组织TKCK未出库数量映射批次" + batchIndex, organizeStart,
                    "当前已缓存 " + sizeOf(qtyMap) + " 个预约单号");
            batchIndex++;
        }
        cache.setTkckNotOutQtyBatchLoaded(true);
        log.info("BIN缓存加载 | 5.6 TKCK未出库数量批量预查完成：候选预约单号共 {} 个，可批量查询 {} 个，保留懒加载 {} 个",
                orderFnos.size(), orderKeyList.size(), lazyOrderFnos.size());
    }

    private List<TkckOrderFnoKey> toTkckOrderFnoKeys(Set<String> orderFnos, Set<String> lazyOrderFnos) {
        List<TkckOrderFnoKey> orderKeys = new ArrayList<>(orderFnos.size());
        for (String orderFno : orderFnos) {
            TkckOrderFnoKey orderKey = parseTkckOrderFno(orderFno);
            if (orderKey == null) {
                lazyOrderFnos.add(orderFno);
                continue;
            }
            orderKeys.add(orderKey);
        }
        return orderKeys;
    }

    private TkckOrderFnoKey parseTkckOrderFno(String orderFno) {
        if (StringUtils.isBlank(orderFno)) {
            return null;
        }
        // 用最后一个横线拆分，避免未来 orderId 自身出现横线时被错误截断。
        int separatorIndex = orderFno.lastIndexOf("-");
        if (separatorIndex <= 0 || separatorIndex >= orderFno.length() - 1) {
            return null;
        }
        String orderId = orderFno.substring(0, separatorIndex);
        String orderItem = orderFno.substring(separatorIndex + 1);
        if (StringUtils.isBlank(orderId) || StringUtils.isBlank(orderItem)) {
            return null;
        }
        return new TkckOrderFnoKey(orderFno, orderId, orderItem);
    }

    private Set<String> collectTkckCandidateOrderFnos(CacheComponent cache) {
        Map<String, List<PreStockResultDO>> preStockOrderInfoMap = cache.getPreStockOrderInfoMap();
        Set<String> orderFnos = new HashSet<>();
        if (preStockOrderInfoMap == null) {
            return orderFnos;
        }
        for (List<PreStockResultDO> orders : preStockOrderInfoMap.values()) {
            if (orders == null) {
                continue;
            }
            for (PreStockResultDO order : orders) {
                if (order != null && StringUtils.isNotBlank(order.getOrderNo())) {
                    orderFnos.add(order.getOrderNo());
                }
            }
        }
        return orderFnos;
    }

    private void putTkckNotOutQty(Map<String, Integer> qtyMap, List<TkckNotOutQtyLite> rows) {
        if (rows == null) {
            return;
        }
        for (TkckNotOutQtyLite row : rows) {
            if (row == null || StringUtils.isBlank(row.getOrderFno())) {
                continue;
            }
            Integer quantity = row.getQuantity() == null ? 0 : row.getQuantity();
            qtyMap.put(row.getOrderFno(), quantity);
        }
    }

    private static int expectedCapacity(int expectedSize) {
        return Math.max(16, expectedSize * 2);
    }

    //批量查询仓库数据，然后缓存到对象cache中
    private Map<String, List<WarehouseVO>> cacheWarehouseInfo(CacheComponent cache) {
        // Task 6：加载 RDC/FDC 仓库主数据，并在 CacheComponent 内构建只读索引。
        Map<String, List<WarehouseVO>> warehouseInfoMap = new HashMap<>();
        WarehouseQueryDTO warehouseQueryDTO = new WarehouseQueryDTO();
        warehouseQueryDTO.setWarehouseType(WarehouseTypeEnum.RDC.getHouseTypeCode());
        long queryStart = System.currentTimeMillis();
        ResultVo<List<WarehouseVO>> listResultVo = commonServiceFeignApi.listWarehouse(warehouseQueryDTO);
        logCacheCost("1.1.1查询物流中心仓库", queryStart,
                "请求" + ((listResultVo != null && listResultVo.isSuccess()) ? "成功" : "失败")
                        + "，返回 " + (listResultVo == null ? 0 : sizeOf(listResultVo.getData())) + " 行");
        long organizeStart = System.currentTimeMillis();
        if (listResultVo.isSuccess()) {
            List<WarehouseVO> data = listResultVo.getData();
            warehouseInfoMap.put(WarehouseTypeEnum.RDC.getHouseTypeCode(), data);
        }
        logCacheCost("1.1.2组织物流中心仓库基础缓存", organizeStart,
                "缓存 " + sizeOf(warehouseInfoMap.get(WarehouseTypeEnum.RDC.getHouseTypeCode())) + " 个物流中心");

        warehouseQueryDTO.setWarehouseType(WarehouseTypeEnum.FDC.getHouseTypeCode());
        queryStart = System.currentTimeMillis();
        ResultVo<List<WarehouseVO>> listResultVo2 = commonServiceFeignApi.listWarehouse(warehouseQueryDTO);
        logCacheCost("1.2.1查询分库仓库", queryStart,
                "请求" + ((listResultVo2 != null && listResultVo2.isSuccess()) ? "成功" : "失败")
                        + "，返回 " + (listResultVo2 == null ? 0 : sizeOf(listResultVo2.getData())) + " 行");
        organizeStart = System.currentTimeMillis();
        if (listResultVo2.isSuccess()) {
            List<WarehouseVO> data = listResultVo2.getData();
            warehouseInfoMap.put(WarehouseTypeEnum.FDC.getHouseTypeCode(), data);
        }
        logCacheCost("1.2.2组织分库仓库基础缓存", organizeStart,
                "缓存 " + sizeOf(warehouseInfoMap.get(WarehouseTypeEnum.FDC.getHouseTypeCode())) + " 个分库");

        organizeStart = System.currentTimeMillis();
        cache.setWarehouseInfoMap(warehouseInfoMap);
        logCacheCost("1.3.1设置仓库基础缓存对象", organizeStart,
                "缓存 " + sizeOf(warehouseInfoMap) + " 类仓库数据");

        organizeStart = System.currentTimeMillis();
        cache.rebuildWarehouseIndexes();
        logCacheCost("1.4重建仓库派生索引总耗时", organizeStart);
        return warehouseInfoMap;
    }

    public void insertTable(Long calcId, List<BinOrderDetailDTO> dtos) {
        // Task 11：把补库计算结果回写到 detail、split、ording 和 pre 结果表。
        Date createTime = new Date();
        List<BinOrderDetailDO> detailDOList = new ArrayList<>();
        List<BinOrderDetailSplitDO> detailSplitDOList = new ArrayList<>();
        List<BinOrderDetailOrdingInfoDO> detailOrdingInfoDOList = new ArrayList<>();
        List<BinOrderDetailPreInfoDO> detailPreInfoDOList = new ArrayList<>();
        for (BinOrderDetailDTO dto : dtos) {
            BinOrderDetailDO DO = new BinOrderDetailDO();
            BeanUtil.copyProperties(dto, DO);
            //创建SplitDO
            List<BinOrderDetailSplitDO> list = createSplitDOList(dto);
            //创建完split后，重新设置订单类型
            DO.setOrderType(dto.getOrderType());
            DO.setSupplierCode(dto.getSupplierCode());
            detailDOList.add(DO);
            detailSplitDOList.addAll(list);
            //创建订货中DO
            List<BinOrderDetailOrdingInfoDO> ordingInfoDOList = dto.getOrdingInfoDOList();
            for (BinOrderDetailOrdingInfoDO ording : ordingInfoDOList) {
                ording.setDelFlag(0);
                ording.setCreateTime(createTime);
                ording.setUpdateTime(createTime);
                ording.setCreateUser("binCalc");
                ording.setUpdateUser("binCalc");
                detailOrdingInfoDOList.add(ording);
            }
            for (BinOrderDetailPreInfoDO pre : dto.getPreInfoDOList()) {
                pre.setDelFlag(0);
                pre.setCreateTime(createTime);
                pre.setUpdateTime(createTime);
                pre.setCreateUser("binCalc");
                pre.setUpdateUser("binCalc");
                detailPreInfoDOList.add(pre);
            }
        }
        List<List<BinOrderDetailSplitDO>> group1 = ListUtil.split(detailSplitDOList, 100);
        List<List<BinOrderDetailOrdingInfoDO>> group2 = ListUtil.split(detailOrdingInfoDOList, 100);
        List<List<BinOrderDetailPreInfoDO>> group3 = ListUtil.split(detailPreInfoDOList, 100);

        logMemoryUsage("写入前");
        //更新detail数据
        for (BinOrderDetailDO binOrderDetailDO : detailDOList) {
            binorderDetailRepository.updateById(binOrderDetailDO);
        }
        //重新写入split表数据
        binOrderDetailSplitMapper.delete(new LambdaQueryWrapper<BinOrderDetailSplitDO>().eq(BinOrderDetailSplitDO::getCalcId, calcId));
        for (List<BinOrderDetailSplitDO> split : group1) {
            binorderDetailRepository.insertBatchSplit(split);
        }
        //重新写入订货中数据
        binOrderDetailOrdingInfoMapper.delete(new LambdaQueryWrapper<BinOrderDetailOrdingInfoDO>().eq(BinOrderDetailOrdingInfoDO::getCalcId, calcId));
        for (List<BinOrderDetailOrdingInfoDO> split : group2) {
            binorderDetailRepository.insertBatchOrding(split);
        }
        //重新写入 预占数据
        binOrderDetailPreInfoMapper.delete(new LambdaQueryWrapper<BinOrderDetailPreInfoDO>().eq(BinOrderDetailPreInfoDO::getCalcId, calcId));
        for (List<BinOrderDetailPreInfoDO> split : group3) {
            binorderDetailRepository.insertBatchPre(split);
        }
    }

    private static List<BinOrderDetailSplitDO> createSplitDOList(BinOrderDetailDTO dto) {
        List<BinOrderDetailSplitDO> list = new ArrayList<>();
        // 写入binOrderDetailSplit
        Map<String, Integer> transQtyMap = dto.getTransQtyMap();
        for (Map.Entry<String, Integer> entry : transQtyMap.entrySet()) {
            //调拨发出仓
            String warehouse = entry.getKey();
            Integer quantity = entry.getValue();
            BinOrderDetailSplitDO splitDO = new BinOrderDetailSplitDO();
            splitDO.setFromId(dto.getId());
            splitDO.setCalcId(dto.getCalcId());
            splitDO.setModelNo(dto.getModelNo());
            splitDO.setWarehouseCode(warehouse);
            splitDO.setConfirmQty(quantity);
            //splitDO.setOrderNo();//还没生成，不填
            //splitDO.setItemNo();// 还没生成，不填
            //splitDO.setTransType();//运输方式，不填
            splitDO.setSupplierCode(warehouse);
            splitDO.setOrderType("1");//订单类型   (K/B/U):采购, 1:调拨,9:拆分数量

            splitDO.setStatus(0);
            splitDO.setDelFlag(0);
            splitDO.setCreateTime(DateUtil.getNow());
            splitDO.setCreateUser("BINCALC");
            splitDO.setUpdateTime(DateUtil.getNow());
            splitDO.setUpdateUser("BINCALC");
            list.add(splitDO);
        }
        //采购单
        if (dto.getPoQty() > 0) {
            BinOrderDetailSplitDO splitDO = new BinOrderDetailSplitDO();
            splitDO.setFromId(dto.getId());
            splitDO.setCalcId(dto.getCalcId());
            splitDO.setModelNo(dto.getModelNo());
            splitDO.setWarehouseCode(dto.getWarehouseCode());
            splitDO.setStatus(0);
            splitDO.setConfirmQty(dto.getPoQty());
            splitDO.setOrderType(dto.getOrderType());
            //订单类型 (K/B/U):采购, 1:调拨,9:拆分数量
            //跟detail保持一致且，只能是B/K/U
            if ("9".equalsIgnoreCase(dto.getOrderType()) || "1".equalsIgnoreCase(dto.getOrderType())) {
                splitDO.setOrderType("");
            }
            splitDO.setSupplierCode(dto.getSupplierCode());
            splitDO.setDelFlag(0);
            splitDO.setCreateTime(DateUtil.getNow());
            splitDO.setCreateUser("BINCALC");
            splitDO.setUpdateTime(DateUtil.getNow());
            splitDO.setUpdateUser("BINCALC");
            list.add(splitDO);
        }
        //dto的orderType 如果只有采购则是B/K/U,如果只有一条调拨则是1，如果split表大于等于2，则写9拆分单
        if (dto.getPoQty() > 0 && transQtyMap.isEmpty()) {
            dto.setOrderType(dto.getOrderType());
        } else if (transQtyMap.size() == 1 && dto.getPoQty() == 0) {
            dto.setOrderType("1");
        } else if (dto.getPoQty() > 0 || transQtyMap.size() > 1) {
            dto.setOrderType("9");
        }
        if(!list.isEmpty()){
            dto.setSupplierCode(list.get(0).getSupplierCode());
        }
        return list;
    }

}
