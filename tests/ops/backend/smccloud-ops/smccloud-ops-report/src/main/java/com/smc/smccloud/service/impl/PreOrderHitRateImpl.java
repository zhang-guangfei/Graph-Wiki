package com.smc.smccloud.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.sales.ops.common.constants.Constants;
import com.sales.ops.db.entity.OpsInventoryLog;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.utils.DateUtil;
import com.smc.smccloud.mapper.OpsInventoryWithHitrateMapper;
import com.smc.smccloud.mapper.PreorderHitrateReportMapper;
import com.smc.smccloud.mapper.pd.OpsInventoryOpeningMapper;
import com.smc.smccloud.model.DataCodeVO;
import com.smc.smccloud.model.OpsInventoryWithHitrateDO;
import com.smc.smccloud.model.PreorderHitrateReportDO;
import com.smc.smccloud.service.DictCommonService;
import com.smc.smccloud.service.PreOrderHitRateService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author lyc
 * @Date 2024/12/23 15:37
 * @Descripton TODO
 */
@Slf4j
@Service
public class PreOrderHitRateImpl implements PreOrderHitRateService {

    @Resource
    private PreorderHitrateReportMapper preorderHitrateReportMapper;

    @Resource
    private DictCommonService dictCommonService;

    @Resource
    private OpsInventoryWithHitrateMapper opsInventoryWithHitrateMapper;

    @Resource
    private OpsInventoryOpeningMapper opsInventoryOpeningMapper;

    @Override
    public ResultVo<String> preOrderHitRate(String calDate) {
        log.info("开始先行在库命中率计算preOrderHitRate calDate = {}", calDate);
        log.info("开始先行在库命中率计算 开始时间: {}", DateUtil.dateToDateTimeString(new Date()));
        long startTime = System.currentTimeMillis();
        String yearMonthCode = null;
        if(StringUtils.isNotBlank(calDate)) {
            yearMonthCode = DateUtil.getYearMonthCode(DateUtil.stringToDate(calDate));
        } else {
            // 获取当前月
            yearMonthCode = DateUtil.getYearMonthCode(DateUtil.addMonth(new Date(),-1));
        }

        // 清空当前月次的数据
        LambdaQueryWrapper<PreorderHitrateReportDO> hitrateReportDOLambdaQueryWrapper = new LambdaQueryWrapper<>();
        hitrateReportDOLambdaQueryWrapper.eq(PreorderHitrateReportDO::getMonth, yearMonthCode);
        preorderHitrateReportMapper.delete(hitrateReportDOLambdaQueryWrapper);

        // 拿到需要统计的数据清单 库存不为0
        List<PreorderHitrateReportDO> preorderHitrateReport = new ArrayList<>();
        preorderHitrateReport = preorderHitrateReportMapper.getPreorderHitrateReport(yearMonthCode,null);

        // 获取需要统计的类型 入库&出库 指令
        ResultVo<List<DataCodeVO>> dataCodesNotCache = dictCommonService.getDataCodesNotCache("4009");
        List<String> allType = new ArrayList<>();
        List<String> ruku = new ArrayList<>();
        List<String> chuku = new ArrayList<>();
        if(dataCodesNotCache != null && dataCodesNotCache.isSuccess()) {
            List<DataCodeVO> data = dataCodesNotCache.getData();
            for (DataCodeVO dataCodeVO : data) {
                if (StringUtils.isNotBlank(dataCodeVO.getExtNote3())) {
                    if ("1".equals(dataCodeVO.getExtNote3())) {
                        ruku.add(dataCodeVO.getCode());
                    }
                    if ("2".equals(dataCodeVO.getExtNote3())) {
                        chuku.add(dataCodeVO.getCode());
                    }
                }
            }
        }

        if (CollectionUtils.isEmpty(chuku) || CollectionUtils.isEmpty(ruku)) {
            return ResultVo.failure("请检查4009编码节点3的配置是否正常,未能获取出入库的指令");
        }

        allType.addAll(ruku);
        allType.addAll(chuku);

        // 查出一个月内有出入库的库存id
        String s = conventDateStr(calDate);
        String[] split = s.split(",");
        List<PreorderHitrateReportDO> list = new ArrayList<>();

        List<Long> inventoryIdByTimeAndvoucherType = preorderHitrateReportMapper.getInventoryIdByTimeAndvoucherType(allType, split[0], split[1]);

        if (CollectionUtils.isNotEmpty(inventoryIdByTimeAndvoucherType)) {
            /**
             * 提取preorderHitrateReport库存id
             *  和 inventoryIdByTimeAndvoucherType 比较 提取不重复集合
             *  根据新库存id集合 获取库存信息
             */
            // 提取preorderHitrateReport库存id
            List<Long> inventoryId = preorderHitrateReport.stream().map(PreorderHitrateReportDO::getInventoryId).distinct().collect(Collectors.toList());

            List<Long> uniqueElements = getUniqueElements(inventoryId, inventoryIdByTimeAndvoucherType);

            if(uniqueElements.size() > 2000) {
                List<List<Long>> partition = ListUtils.partition(uniqueElements, 2000);
                for(List<Long> longs : partition) {
                    List<PreorderHitrateReportDO> preorderHitrateReport1 = preorderHitrateReportMapper.getInventoryInfo(yearMonthCode, longs);
                    list.addAll(preorderHitrateReport1);
                }
            } else {
                list = preorderHitrateReportMapper.getInventoryInfo(yearMonthCode, uniqueElements);
            }
        } else {
            list = preorderHitrateReport;
        }

        Date date = new Date();

        // 批量获取期初数量
        List<OpsInventoryWithHitrateDO> allInventoryWithHitrate = new ArrayList<>();
        List<Long> inventoryIds = list.stream().map(PreorderHitrateReportDO::getInventoryId).distinct().collect(Collectors.toList());
        if(CollectionUtils.isNotEmpty(inventoryIds)) {
            if (inventoryIds.size() > 2000) {
                List<List<Long>> partition = ListUtils.partition(inventoryIds, 2000);
                for(List<Long> longs : partition) {
                    LambdaQueryWrapper<OpsInventoryWithHitrateDO> queryWrapper = new LambdaQueryWrapper<>();
                    queryWrapper.select(OpsInventoryWithHitrateDO::getQuantity, OpsInventoryWithHitrateDO::getInventoryId);
                    queryWrapper
                            .eq(OpsInventoryWithHitrateDO::getBatchNo, yearMonthCode)
                            .in(OpsInventoryWithHitrateDO::getInventoryId,longs);
                    List<OpsInventoryWithHitrateDO> opsInventoryWithHitrateDOList = opsInventoryWithHitrateMapper.selectList(queryWrapper);
                    allInventoryWithHitrate.addAll(opsInventoryWithHitrateDOList);
                }
            } else {
                LambdaQueryWrapper<OpsInventoryWithHitrateDO> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.select(OpsInventoryWithHitrateDO::getQuantity, OpsInventoryWithHitrateDO::getInventoryId);
                queryWrapper
                        .eq(OpsInventoryWithHitrateDO::getBatchNo, yearMonthCode)
                        .in(OpsInventoryWithHitrateDO::getInventoryId,inventoryIds);
                List<OpsInventoryWithHitrateDO> opsInventoryWithHitrateDOList = opsInventoryWithHitrateMapper.selectList(queryWrapper);
                allInventoryWithHitrate.addAll(opsInventoryWithHitrateDOList);
            }

        }
        Map<Long, List<OpsInventoryWithHitrateDO>> inventoryByInventoryIdMap = allInventoryWithHitrate.stream()
                .collect(Collectors.groupingBy(OpsInventoryWithHitrateDO::getInventoryId));

        log.info("批量获取期初数量耗时: {} s " , (System.currentTimeMillis() - startTime) /1000);

        long startTime2 = System.currentTimeMillis();
        // 批量获取E价
        List<PreorderHitrateReportDO> priceModelNos = new ArrayList<>();
        List<String> modelNos = list.stream().map(PreorderHitrateReportDO::getModelNo).distinct().collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(modelNos)) {
            if(modelNos.size() > 2000) {
                List<List<String>> partition = ListUtils.partition(modelNos, 2000);
                for(List<String> strings : partition) {
                    List<PreorderHitrateReportDO> pricesByModelNos1 = preorderHitrateReportMapper.getPricesByModelNos(strings);
                    priceModelNos.addAll(pricesByModelNos1);
                }
            } else {
                priceModelNos = preorderHitrateReportMapper.getPricesByModelNos(modelNos);
            }
        }
        Map<String, BigDecimal> pricesByModelNos = priceModelNos.stream().collect(Collectors.toMap(PreorderHitrateReportDO::getModelNo, PreorderHitrateReportDO::getEprice));

        log.info("批量获取E价耗时: {} s " , (System.currentTimeMillis() - startTime2) /1000);

        long startTime5 = System.currentTimeMillis();
        /**
         * 批量获取本月库存的入库数量
         */
        Map<Long, Integer> rukuMap = new HashMap<>();
        Map<Long, Integer> chukuMap = new HashMap<>();
        List<OpsInventoryLog> allRukuInventoryLog = new ArrayList<>();
        List<OpsInventoryLog> allChukuInventoryLog = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(inventoryIds)) {
            if (inventoryIds.size() > 2000) {
                List<List<Long>> partition = ListUtils.partition(inventoryIds, 2000);
                for(List<Long> longs : partition) {
                    // 本月入库
                    List<OpsInventoryLog> rukuSum = preorderHitrateReportMapper.getInventoryLogList2(longs, "1", ruku, split[0], split[1]);
                    allRukuInventoryLog.addAll(rukuSum);
                    // 本月出库
                    List<OpsInventoryLog> chukuSum = preorderHitrateReportMapper.getInventoryLogList2(longs, "0", chuku, split[0], split[1]);
                    allChukuInventoryLog.addAll(chukuSum);
                }
            } else {
                List<OpsInventoryLog> rukuSum2 = preorderHitrateReportMapper.getInventoryLogList2(inventoryIds, "1", ruku, split[0], split[1]);
                allRukuInventoryLog.addAll(rukuSum2);
                List<OpsInventoryLog> chukuSum2 = preorderHitrateReportMapper.getInventoryLogList2(inventoryIds, "0", chuku, split[0], split[1]);
                allChukuInventoryLog.addAll(chukuSum2);
            }
            if(CollectionUtils.isNotEmpty(allRukuInventoryLog)) {
                rukuMap = allRukuInventoryLog.stream().collect(Collectors.toMap(OpsInventoryLog::getInventoryId, OpsInventoryLog::getQuantity));
            }
            if(CollectionUtils.isNotEmpty(allChukuInventoryLog)) {
                chukuMap = allChukuInventoryLog.stream().collect(Collectors.toMap(OpsInventoryLog::getInventoryId, OpsInventoryLog::getQuantity));
            }
        }
        log.info("批量获取出入库数量耗时: {} s " , (System.currentTimeMillis() - startTime5) /1000);


        long startTime3 = System.currentTimeMillis();
        for (PreorderHitrateReportDO item : list) {
            List<OpsInventoryWithHitrateDO> opsInventoryWithHitrateDOS = inventoryByInventoryIdMap.get(item.getInventoryId());
            if (CollectionUtils.isEmpty(opsInventoryWithHitrateDOS)) {
                item.setInitialQuantity(0);
            } else {
                // 获取本月期初数量
                item.setInitialQuantity(opsInventoryWithHitrateDOS.get(0).getQuantity());
            }
            // 本月入库数量之和   type 1入 0出
//            int rukuSum = getInventoryLogSumQty(item.getInventoryId(), "1", ruku,calDate);
//            int chukuSum = getInventoryLogSumQty(item.getInventoryId(), "0", chuku,calDate);
            int rukuSum = rukuMap.getOrDefault(item.getInventoryId(), 0);
            int chukuSum = chukuMap.getOrDefault(item.getInventoryId(), 0);
            item.setInQtyTotal(rukuSum);
            item.setOutQtyTotal(chukuSum);

            // E单价
            BigDecimal priceByModelNo = pricesByModelNos.get(item.getModelNo());
            item.setEprice(priceByModelNo);
            if (priceByModelNo != null) {
                // 期初E金额
                item.setInitalEamount(priceByModelNo.multiply(new BigDecimal(Optional.ofNullable(item.getInitialQuantity()).orElse(0))));
                // 本月出库E金额
                item.setOutAmountTotal(priceByModelNo.multiply(new BigDecimal(Optional.ofNullable(item.getOutQtyTotal()).orElse(0))));
                // 本月入库E金额
                item.setInAmountTotal(priceByModelNo.multiply(new BigDecimal(Optional.ofNullable(item.getInQtyTotal()).orElse(0))));
            }
            // 是否命中
            if (item.getOutQtyTotal() > 0) {
                item.setHit(1);
            } else {
                item.setHit(0);
            }
            item.setMonth(yearMonthCode);
            item.setCreateTime(date);
            item.setUpdateTime(date);
            item.setCreateUser(Constants.SystemName);
            item.setUpdateUser(Constants.SystemName);
            preorderHitrateReportMapper.insert(item);
        }
        log.info("写入计算数据耗时: {} s " , (System.currentTimeMillis() - startTime3) /1000);
        log.info("整个流程结束耗时: {} s " , (System.currentTimeMillis() - startTime) /1000);
        return ResultVo.success("先行在库命中率基础数据计算完毕");
    }

    private int getInventoryLogSumQty(Long invId,String type, List<String> voucherType,String dateStr ) {

        String s = conventDateStr(dateStr);
        String[] split = s.split(",");
        return preorderHitrateReportMapper.getInventoryLogList(invId, type, voucherType,split[0],split[1]);
    }

    public String conventDateStr(String dateStr) {
        String start = "";
        String end = "";

        if (StringUtils.isNotBlank(dateStr)) {
            Date monthFirstDate = DateUtil.getMonthFirstDate(DateUtil.stringToDate(dateStr));
//            Date monthEndDate = DateUtil.getMonthEndDate(DateUtil.stringToDate(dateStr));
            start = DateUtil.dateToString(monthFirstDate)+" 00:00:00";
            end = dateStr+" 23:59:59";
        } else {
            Date lastMonthFirstDate = DateUtil.getLastMonthFirstDate(new Date());
            Date lastMonthEndDate = DateUtil.getLastMonthEndDate(new Date());
            start = DateUtil.dateToString(lastMonthFirstDate)+" 00:00:00";
            end = DateUtil.dateToString(lastMonthEndDate)+" 23:59:59";
        }
        return start+","+end;
    }

    public List<Long> getUniqueElements(List<Long> list1, List<Long> list2) {
        // 使用Set来存储不重复的元素
        Set<Long> set = new HashSet<>(list1);
        set.addAll(list2);
        // 将Set转换为List并返回
        return new ArrayList<>(set);
    }
}
