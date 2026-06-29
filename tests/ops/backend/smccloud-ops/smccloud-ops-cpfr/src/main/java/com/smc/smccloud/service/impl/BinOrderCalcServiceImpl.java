package com.smc.smccloud.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.smc.smccloud.core.exception.BusinessException;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.model.constants.GlobalConstant;
import com.smc.smccloud.core.redis.RedisManager;
import com.smc.smccloud.core.redis.redisson.RedissonUtil;
import com.smc.smccloud.core.utils.*;
import com.smc.smccloud.mapper.BinOrderCalcMapper;
import com.smc.smccloud.mapper.BindataMapper;
import com.smc.smccloud.mapper.binorder.BinOrderDetailMapper;
import com.smc.smccloud.mapper.binorder.BinOrderDetailSplitMapper;
import com.smc.smccloud.mapper.binorder.ModelExpFreqMapper;
import com.smc.smccloud.model.Constants;
import com.smc.smccloud.model.DataTypeVO;
import com.smc.smccloud.model.bindata.BindataDO;
import com.smc.smccloud.model.binorder.*;
import com.smc.smccloud.model.inventory.InventoryRequestDTO;
import com.smc.smccloud.model.inventory.WarehouseInventoryVO;
import com.smc.smccloud.model.warehouse.WarehouseVO;
import com.smc.smccloud.service.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author smc
 * @since 2021-10-19
 */
@Slf4j
@Service
public class BinOrderCalcServiceImpl extends ServiceImpl<BinOrderCalcMapper, BinOrderCalcDO> implements BinOrderCalcService {

    @Value("${file.base}")
    private String serverPath;
    @Resource
    private BinOrderCalcMapper binOrderCalcMapper;
    @Resource
    private BinOrderDetailMapper binOrderDetailMapper;
    @Resource
    private BinOrderDetailSplitMapper binOrderDetailSplitMapper;
    @Resource
    private ModelExpFreqMapper modelExpFreqMapper;
    @Resource
    private BindataMapper bindataMapper;
    @Resource
    private RedissonUtil redissonUtil;
    @Resource
    private RedisManager redisUtil;
    @Resource
    private CommonService commonService;
    @Resource
    private InventoryServiceFeignApi inventoryServiceFeignApi;
    @Resource
    private DictDataServiceFeignApi dictDataServiceFeignApi;

    @Resource
    private PlatformTransactionManager transactionManager;

    @Resource
    private DictCommonService dictCommonService;


    @Resource
    private CommonServiceFeignApi commonServiceFeignApi;

    @Override
    public ResultVo<PageInfo<BinOrderDetailVO>> listBinOrderDetail(BinOrderCalcQueryDTO dto) {
        LambdaQueryWrapper<BinOrderDetailDO> query = new LambdaQueryWrapper<>();

        PageHelper.startPage(dto.getPageNum(), dto.getPageSize());

        query.eq(BinOrderDetailDO::getCalcId, dto.getCalcId())
                .gt(dto.getOnlyHasOrderQty(), BinOrderDetailDO::getConfirmQty, 0)
                .like(PublicUtil.isNotEmpty(dto.getModelNo()), BinOrderDetailDO::getModelNo, dto.getModelNo())
                .eq(dto.getAppId() != null && dto.getAppId() > 0, BinOrderDetailDO::getAppId, dto.getAppId())
                .like(PublicUtil.isNotEmpty(dto.getOrderNo()), BinOrderDetailDO::getOrderNo, dto.getOrderNo())
                .eq(PublicUtil.isNotEmpty(dto.getWarehouseCode()), BinOrderDetailDO::getWarehouseCode, dto.getWarehouseCode())
                .eq(PublicUtil.isNotEmpty(dto.getCalcType()), BinOrderDetailDO::getCalcType, dto.getCalcType());

        if (dto.getStatus() != null && dto.getStatus() == 1) {
            query.le(BinOrderDetailDO::getStatus, dto.getStatus());
        } else if (dto.getStatus() != null && dto.getStatus() == 2) {
            query.ge(BinOrderDetailDO::getStatus, dto.getStatus());
        }

        if (PublicUtil.isNotEmpty(dto.getMoreContent())) {
            query.apply(dto.getMoreContent());
        }

        List<BinOrderDetailDO> list = binOrderDetailMapper.selectList(query);
        PageInfo<BinOrderDetailDO> pageInfo = PageInfo.of(list);
        PageInfo<BinOrderDetailVO> page = BeanCopyUtil.pageDto2Vo(pageInfo, BinOrderDetailVO.class);
        return ResultVo.success(page);
    }

    /**
     * 查询型号的每个库房的在库
     *
     * @param modelNo 型号
     * @return 在库情况
     */
    @Override
    public ResultVo<List<BinOrderInventoryInfoVO>> listBinWarehouseStock(String modelNo) {
        try {
            Map<String, BinOrderInventoryInfoVO> map = new HashMap<>();
            BinOrderInventoryInfoVO info = new BinOrderInventoryInfoVO();
            SMCApp.setGrantType(GlobalConstant.GrantType.CLIANT_CREDENTIALS);
            List<BindataDO> binNotInventory = new ArrayList<>();

            //查询物流中心的可用库存
            InventoryRequestDTO dto = new InventoryRequestDTO();
            dto.setModelNos(Collections.singletonList(modelNo));
            ResultVo<List<WarehouseInventoryVO>> inventListResult = inventoryServiceFeignApi.getLogisticWarehouseCanUseStock(dto);
            if (!inventListResult.isSuccess()) {
                log.error("获取型号库存信息失败" + inventListResult.getMessage());
                return ResultVo.failure("获取型号库存信息失败: " + inventListResult.getMessage());
            }
            //查询bindata数据
            LambdaQueryWrapper<BindataDO> query = new LambdaQueryWrapper<>();
            query.select(BindataDO::getModelNo, BindataDO::getWarehouseCode, BindataDO::getQtyBin, BindataDO::getBinCell, BindataDO::getFreq,
                    BindataDO::getNewMean, BindataDO::getStockType,BindataDO::getCentreFlag,BindataDO::getSafeQty, BindataDO::getSetFreq);
            query.eq(BindataDO::getModelNo, modelNo)
                    .eq(BindataDO::getStockType, 1)
                    .eq(BindataDO::getDelFlag, 0);
            List<BindataDO> binList = bindataMapper.selectList(query);

            List<BindataDO> binDataQuery = new ArrayList<>();

            if (inventListResult.getData() != null && CollectionUtils.isNotEmpty(inventListResult.getData())) {
                List<WarehouseInventoryVO> inventoryVOList = inventListResult.getData();

                final List<String> warehouseCodes = inventoryVOList.stream().map(WarehouseInventoryVO::getWarehouseCode)
                        .distinct().collect(Collectors.toList());
                //不在物流中心中的bindata
                binNotInventory = binList.stream().filter(f -> !warehouseCodes.contains(f.getWarehouseCode())).collect(Collectors.toList());

                //   bug 10113  用于计算 bin数量是0的型号把通用库存都当过剩了，改成：过剩数量=通用在库数-2*物流中心8月平用量

                for (WarehouseInventoryVO vo : inventoryVOList) {
                    if (CollectionUtil.isNotEmpty(binList)) {
                        //同型号同库房的bindata数据
                        binDataQuery = binList.stream().filter(f -> f.getModelNo().equalsIgnoreCase(vo.getModelNo()) && f.getWarehouseCode().equalsIgnoreCase(vo.getWarehouseCode()))
                                .collect(Collectors.toList());
                    }

                    // 是否BIN
                    boolean isBin = CollectionUtil.isNotEmpty(binDataQuery);

                    // 是否中央仓
                    boolean isCenterWareHouse = false;
                    if(CollectionUtil.isNotEmpty(binDataQuery) && binDataQuery.get(0).getCentreFlag() == 1){
                        isCenterWareHouse = true;
                    }

                    BigDecimal binCell = BigDecimal.ZERO;
                    //如果无bindata数据或bin数量为0
                    if (CollectionUtil.isEmpty(binDataQuery)  ||
                            binDataQuery.get(0).getQtyBin() == null || binDataQuery.get(0).getQtyBin() <= 0) {
                        //物流中心8月平用量
                        ModelExpFreqDO expFreqDO = commonService.getModelExpFreq(vo.getModelNo(), vo.getWarehouseCode());
                        binCell = expFreqDO == null ? BigDecimal.ONE : expFreqDO.getAvgQtyOf8();
                    }

                    if (map.containsKey(vo.getWarehouseCode())) {
                        info = map.get(vo.getWarehouseCode());
                        info.setAvaQty_ty(info.getAvaQty_ty() + vo.getTyAvaQty());
                        info.setAvaQty_zy(info.getAvaQty_zy() + vo.getZyAvaQty());
                    } else {
                        //    <!--add by WuWeiDong 20230327    -->
                        //   bug 10113  用于计算 bin数量是0的型号把通用库存都当过剩了，改成：过剩数量=通用在库数-2*物流中心8月平用量
                        info = new BinOrderInventoryInfoVO();
                        info.setIsBin(isBin);
                        info.setIsCenterWareHouse(isCenterWareHouse);
                        info.setWarehouseCode(vo.getWarehouseCode());
                        info.setModelNo(modelNo);
                        info.setAvaQty_zy(vo.getZyAvaQty());
                        info.setAvaQty_ty(vo.getTyAvaQty());
                        if (CollectionUtil.isNotEmpty(binDataQuery)) {
                            info.setSafeQty(binDataQuery.get(0).getSafeQty() == null ? 0 : binDataQuery.get(0).getSafeQty());
                        }
                        //如果8月均大于0，即基础型号表查询到了值
                        if (binCell.compareTo(BigDecimal.ZERO) == 1) {
                            info.setQtyBin(0);
                            info.setBinCell(0);
                            info.setFreq(0);
                            info.setMean(Integer.parseInt(binCell.toString()));
                            info.setMonths(0);
                            info.setExcessQty(0);
                        } else {
                            if (CollectionUtil.isNotEmpty(binDataQuery) && binDataQuery.size() >= 1) {
                                info.setStockType(binDataQuery.get(0).getStockType());
                                info.setQtyBin(Optional.ofNullable(binDataQuery.get(0).getQtyBin()).orElse(0));
                                info.setBinCell(Optional.ofNullable(binDataQuery.get(0).getBinCell()).orElse(0));
                                info.setFreq(Optional.ofNullable(binDataQuery.get(0).getFreq()).orElse(0));
                                info.setMean(Optional.ofNullable(binDataQuery.get(0).getSetFreq()).orElse(0));
                            } else {
                                info.setQtyBin(0);
                                info.setBinCell(0);
                                info.setFreq(0);
                                info.setMean(0);
                            }
                            info.setMonths(0);
                            info.setExcessQty(0);

                        }
                        map.put(vo.getWarehouseCode(), info);
                    }
                }
            } else {
                binNotInventory.addAll(binList);
            }
            //没有在库数据的其他Bin数据
            if (PublicUtil.isNotEmpty(binNotInventory) && binNotInventory.size() >= 1) {
                for (BindataDO bin : binNotInventory) {
                    info = new BinOrderInventoryInfoVO();
                    info.setWarehouseCode(bin.getWarehouseCode());
                    info.setModelNo(modelNo);
                    info.setAvaQty_zy(0);
                    info.setAvaQty_ty(0);
                    info.setStockType(bin.getStockType());
                    info.setQtyBin(Optional.ofNullable(bin.getQtyBin()).orElse(0));
                    info.setBinCell(Optional.ofNullable(bin.getBinCell()).orElse(0));
                    info.setFreq(Optional.ofNullable(bin.getFreq()).orElse(0));
                    info.setMean(Optional.ofNullable(bin.getSetFreq()).orElse(0));
                    info.setMonths(0);
                    info.setExcessQty(0);
                    map.put(bin.getWarehouseCode(), info);
                }
            }
            List<BinOrderInventoryInfoVO> voList = new ArrayList<>(map.values());

            for (BinOrderInventoryInfoVO vo : voList) {
                // 计算可用月数
                vo.setMonths(vo.getMean() == 0 ? 0 : (vo.getAvaQty_ty() + vo.getAvaQty_zy()) / vo.getMean());
                //vo.setExcessQty(Math.max(excessQty, 0));
                //过剩数量改成前端解析bin_order_detail中的excessQty
                vo.setExcessQty(0);
            }
            return ResultVo.success(voList);
        } catch (NullPointerException ex) {
            ex.printStackTrace();
            log.error(Thread.currentThread().getStackTrace()[1].getMethodName() + "->错误NullPointerException：" + ex);
            return ResultVo.failure("错误：" + ex);
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error(Thread.currentThread().getStackTrace()[1].getMethodName() + "->错误Exception：" + ex);
            return ResultVo.failure("错误：" + ex);
        }

    }

    /**
     * 批量型号查询每个物流中心的在库
     *
     * @param modelNoList 型号列表
     * @return modelNo-warehouseCode-binInventory
     */
    public ResultVo<Map<String, Map<String, BinOrderInventoryInfoVO>>> listBinWarehouseStock(List<String> modelNoList, List<String> warehouseCodeList) {
        // Add by Dengdenghui 2022-11-25 for bug-8788
        if (CollectionUtils.isEmpty(warehouseCodeList)) {
            return ResultVo.success(Collections.emptyMap());
        }
        try {
            long startTimer = System.currentTimeMillis();
            Map<String, Map<String, BinOrderInventoryInfoVO>> modelMap = new HashMap<>(modelNoList.size());
            Map<String, BinOrderInventoryInfoVO> warehouseMap = new HashMap<>();
            List<String> modelNos = new ArrayList<>();
            modelNos = modelNoList;
            List<String> warehouseCodes = new ArrayList<>();
            warehouseCodes = warehouseCodeList;

            BinOrderInventoryInfoVO binStockInfo = new BinOrderInventoryInfoVO();
            List<WarehouseInventoryVO> inventoryList = new ArrayList<>(modelNoList.size());
            List<BindataDO> binList = new ArrayList<>();

            //    <!--add by WuWeiDong 20230327  优化查询效率，异步查询   -->
            // 型号在各配置仓的可用库存
            Future<List<WarehouseInventoryVO>> inventoryfuture = commonService.asyncGetWarehouseInventoryByModels(modelNos, warehouseCodes);
            Future<List<BindataDO>> binDatafuture = commonService.asyncGetBinDataByModels(modelNos, warehouseCodes);

            while (true) {
                if (binDatafuture.isDone() && inventoryfuture.isDone()) {

                    inventoryList = inventoryfuture.get();
                    binList = binDatafuture.get();
                    //先把没有qtybin的数据数据批理查询8月平用量 。
                    List<ModelExpFreqDO> freqDOList = this.getModelExpFreqForAvgQty(binList);

                    List<BindataDO> bindataquery = new ArrayList<>();
                    List<ModelExpFreqDO> expfreqquery = new ArrayList<>();

                    for (WarehouseInventoryVO inventoryInfo : inventoryList) {
                        if (CollectionUtil.isNotEmpty(binList)) {
                            bindataquery = binList.stream().filter(f -> f.getModelNo().equalsIgnoreCase(inventoryInfo.getModelNo()) && f.getWarehouseCode().equalsIgnoreCase(inventoryInfo.getWarehouseCode()))
                                    .collect(Collectors.toList());
                        }
                        BindataDO binDataInfo = bindataMapper.getBinDataInfo(inventoryInfo.getModelNo(), inventoryInfo.getWarehouseCode());
                        /**
                         * 是否bin
                         */
                        boolean isBin = binDataInfo != null;
                        /**
                         * 是否中央仓
                         */
                        boolean isCenterWareHouse = false;
                        if(binDataInfo != null && binDataInfo.getCentreFlag() == 1){
                            isCenterWareHouse = true;
                        }

                        BigDecimal binCell = BigDecimal.ZERO;
                        if (CollectionUtil.isEmpty(bindataquery) || bindataquery.size() == 0 ||
                                bindataquery.get(0).getQtyBin() == null || bindataquery.get(0).getQtyBin() <= 0) {

                            if (CollectionUtil.isNotEmpty(freqDOList)) {
                                expfreqquery = freqDOList.stream().filter(f -> f.getModelNo().equalsIgnoreCase(inventoryInfo.getModelNo()) && f.getStockCode().equalsIgnoreCase(inventoryInfo.getWarehouseCode()))
                                        .collect(Collectors.toList());
                            }
                            if (CollectionUtil.isNotEmpty(expfreqquery) && expfreqquery.size() >= 1) {
                                binCell = expfreqquery.get(0).getAvgQtyOf8();
                            }
                            if (binCell.compareTo(BigDecimal.ZERO) == 0) {
                                //物流中心8月平用量
                                // log.error("modelNO:" + inventoryInfo.getModelNo() + " WarehouseCode:" + inventoryInfo.getWarehouseCode());
                                bindataquery = binList.stream().filter(f -> f.getModelNo().equalsIgnoreCase(inventoryInfo.getModelNo()) && f.getWarehouseCode().equalsIgnoreCase(inventoryInfo.getWarehouseCode()))
                                        .collect(Collectors.toList());
                                ModelExpFreqDO expFreqDO = commonService.getModelExpFreq(inventoryInfo.getModelNo(), inventoryInfo.getWarehouseCode());
                                binCell = expFreqDO == null ? BigDecimal.ONE : expFreqDO.getAvgQtyOf8();
                            }
                        }

                        if (modelMap.containsKey(inventoryInfo.getModelNo())) {
                            warehouseMap = modelMap.get(inventoryInfo.getModelNo());
                        } else {
                            warehouseMap = new HashMap<>();
                            modelMap.put(inventoryInfo.getModelNo(), warehouseMap);
                        }
                        if (warehouseMap.containsKey(inventoryInfo.getWarehouseCode())) {
                            binStockInfo = warehouseMap.get(inventoryInfo.getWarehouseCode());
                            binStockInfo.setAvaQty_ty(binStockInfo.getAvaQty_ty() + inventoryInfo.getTyAvaQty());
                            binStockInfo.setAvaQty_zy(binStockInfo.getAvaQty_zy() + inventoryInfo.getZyAvaQty());

                        } else {
                            binStockInfo = new BinOrderInventoryInfoVO();
                            binStockInfo.setIsBin(isBin);
                            binStockInfo.setIsCenterWareHouse(isCenterWareHouse);
                            if (binDataInfo != null) {
                                binStockInfo.setSafeQty(binDataInfo.getSafeQty());
                            }
                            binStockInfo.setWarehouseCode(inventoryInfo.getWarehouseCode());
                            binStockInfo.setModelNo(inventoryInfo.getModelNo());
                            binStockInfo.setAvaQty_zy(inventoryInfo.getZyAvaQty());
                            binStockInfo.setAvaQty_ty(inventoryInfo.getTyAvaQty());
                            //    <!--add by WuWeiDong 20230327    -->
                            //   bug 10113  用于计算 bin数量是0的型号把通用库存都当过剩了，改成：过剩数量=通用在库数-2*物流中心8月平用量
                            if (binCell.compareTo(BigDecimal.ZERO) == 1) {
                                binStockInfo.setQtyBin(2);
                                binStockInfo.setBinCell(Integer.parseInt(binCell.subtract(BigDecimal.ONE).toString()));//后面计算时有加1，所以这里先减1
                                binStockInfo.setFreq(0);
                                binStockInfo.setMean(0);
                                binStockInfo.setMonths(0);
                                binStockInfo.setExcessQty(0);
                            } else {
                                if (CollectionUtil.isNotEmpty(bindataquery) || bindataquery.size() >= 1) {
                                    binStockInfo.setStockType(bindataquery.get(0).getStockType());
                                    binStockInfo.setQtyBin(Optional.ofNullable(bindataquery.get(0).getQtyBin()).orElse(0));
                                    binStockInfo.setBinCell(Optional.ofNullable(bindataquery.get(0).getBinCell()).orElse(0));
                                    binStockInfo.setFreq(Optional.ofNullable(bindataquery.get(0).getFreq()).orElse(0));
                                    binStockInfo.setMean(Optional.ofNullable(bindataquery.get(0).getNewMean()).orElse(0));
                                } else {
                                    // binStockInfo.setStockType(1);
                                    binStockInfo.setQtyBin(0);
                                    binStockInfo.setBinCell(0);
                                    binStockInfo.setFreq(0);
                                    binStockInfo.setMean(0);
                                }
                                binStockInfo.setMonths(0);
                                binStockInfo.setExcessQty(0);
                            }
                            warehouseMap.put(inventoryInfo.getWarehouseCode(), binStockInfo);
                        }
                    }
                    break;
                }
            }

            /**
             * 过剩数量： 中央仓bin：BIN品过剩=有效在库-安全在库量
             * 		      非中央仓bin：BIN品过剩=有效在库-(BIN数+1)*BIN数量
             * 			  中央仓非bin：NON-BIN过剩数量=有效在库-2*月均
             * 			  非中央仓非bin：NON-BIN过剩数量=有效在库-2*月均
             */
            for (Map<String, BinOrderInventoryInfoVO> map : modelMap.values()) {
                for (BinOrderInventoryInfoVO vo : map.values()) {
                    // 计算可用月数
                    vo.setMonths(vo.getMean() == 0 ? 0 : (vo.getAvaQty_ty() + vo.getAvaQty_zy()) / vo.getMean());
                    // 计算剩余数量
                    Integer excessQty = 0;
                    if (vo.getIsBin() && vo.getIsCenterWareHouse()) {
                        excessQty = vo.getAvaQty_ty() - vo.getSafeQty();
                    } else {
                        excessQty = vo.getAvaQty_ty() - (vo.getQtyBin() * (1 + vo.getBinCell()));
                    }
                    vo.setExcessQty(excessQty < 0 ? 0 : excessQty);
                }
            }
            log.info(Thread.currentThread().getStackTrace()[1].getMethodName() + " 处理结束，耗时(s): " + (System.currentTimeMillis() - startTimer) / 1000.0);

            return ResultVo.success(modelMap);
        } catch (NullPointerException ex) {
            ex.printStackTrace();
            log.error(Thread.currentThread().getStackTrace()[1].getMethodName() + "->错误NullPointerException：" + ex);
            return ResultVo.failure("计算bin在库数错误：" + ex);
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error(Thread.currentThread().getStackTrace()[1].getMethodName() + "->错误Exception：" + ex);
            return ResultVo.failure("计算bin在库数错误：" + ex);
        }
    }

    @Override
    @Async
    // @Transactional(rollbackFor = Exception.class)
    public void asycalcBinOrder(BinOrderCalcRequestDTO dto) {
        String lockKey = Constants.REDIS_KEY_BIN_CALC_LOCK + dto.getCalcId();

        if (!redissonUtil.tryLock(lockKey, 1, 60 * 20, TimeUnit.SECONDS)) {
            log.info("bin补库计算中 asycalcBinOrder " + lockKey);
            return;
        }

        RedisMessageVO messageVO = new RedisMessageVO();
        messageVO.setNo(dto.getCalcId().toString());
        messageVO.setStatus(1);

        try {
            long startTimer = System.currentTimeMillis();
            SMCApp.setGrantType(GlobalConstant.GrantType.CLIANT_CREDENTIALS);
            //1.调用存储过程计算补货数量
            Map<String, Object> map = new HashMap<>();
            map.put("calcId", dto.getCalcId());
            map.put("calcType", dto.getCalcType());
            map.put("warehouseCode", dto.getWarehouseCode());
            map.put("result", 0);
            binOrderCalcMapper.calcOrder(map); // *****测试暂不计算
            log.info("bin_order_calc 计算结束，耗时(s): " + (System.currentTimeMillis() - startTimer) / 1000.0);

            messageVO.setContent(messageVO.getNo() + "已计算补货数，待分配是否可调库");
            saveCalcState(messageVO);
            log.info("开始执行计算2");

            boolean isCalcSuccess = this.calcBinOrderTransQty(dto, messageVO);
            if (!isCalcSuccess) {
                log.error("计算失败-{} calcId-{}", messageVO.getContent(), dto.getCalcId());
                return;
            }


            messageVO.setContent(messageVO.getNo() + "已计算完成");
            messageVO.setStatus(2);
            saveCalcState(messageVO);
            log.info(Thread.currentThread().getStackTrace()[1].getMethodName() + " 处理结束，耗时(s): " + (System.currentTimeMillis() - startTimer) / 1000.0);

        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            messageVO.setStatus(4);
            messageVO.setContent(messageVO.getNo() + "计算失败;" + e.getMessage());
            log.error("BIN补库计算: {}", messageVO.getContent(), e);
            saveCalcState(messageVO);
        } finally {
            redissonUtil.unlock(lockKey);
            ThreadLocalMapUtil.remove(GlobalConstant.GrantType.CLIANT_CREDENTIALS);
        }
    }

    /**
     * add by WuWeiDong 20230327
     * 分开更新明细数，写入拆分表，取消拆分表
     *
     * @param updDOList
     * @param insertSplitList
     * @param updateSplitCancelList
     * @return
     */
    private boolean insertUpdateBinOrderDetail
    (List<BinOrderDetailDO> updDOList, List<BinOrderDetailSplitDO> insertSplitList, List<Long> updateSplitCancelList) {
        long startTimer = System.currentTimeMillis();
        boolean rtnval = false;
        try {
            //    <!--add by WuWeiDong 20230703  bug 11267    -->
            List<BinOrderDetailSplitDO> SplitList = new ArrayList<>();
            if(CollectionUtils.isNotEmpty(insertSplitList)){
                SplitList = insertSplitList.stream().filter(item -> item.getConfirmQty() > 0).collect(Collectors.toList());
            }
            TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);//纺
            transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
            List<BinOrderDetailSplitDO> finalSplitList = SplitList;
            rtnval = transactionTemplate.execute(transactionStatus -> {
                try {
                    //    <!--add by WuWeiDong 20230508 bug[10690]  先取消之前数据，再写入子表   -->
                    //取消之前数据
                    ExecutorService executorServiceCancelSplit = this.updateBinOrderDetailSplitCancel(updateSplitCancelList);
                    executorServiceCancelSplit.shutdown();
                    while (true) {
                        if (executorServiceCancelSplit.isTerminated()) {
                            break;
                        }
                    }
                    //写入子表
                    ExecutorService executorServiceInsertSplit = this.insertBinOrderDetailSplit(finalSplitList);
                    executorServiceInsertSplit.shutdown();
                    //更新
                    ExecutorService executorServiceUpdateDetail = this.updateBinOrderDetailByIds(updDOList);
                    executorServiceUpdateDetail.shutdown();
                    while (true) {
                        if (executorServiceInsertSplit.isTerminated() && executorServiceUpdateDetail.isTerminated()) {
                            log.info(" 数据写入更新结束耗时(s): " + (System.currentTimeMillis() - startTimer) / 1000.0);
                            break;
                        }
                    }
                    // 保存完成计算时间
                    if(CollectionUtils.isNotEmpty(updDOList)){
                        this.updateCalcFinishTime(updDOList.get(0).getCalcId());
                    }
                    return true;
                } catch (Exception ex) {
                    log.error(Thread.currentThread().getName() + "->错误TransactionTemplate：" + ex);
                    return false;
                }
            });
        } catch (NullPointerException ex) {
            log.error(Thread.currentThread().getName() + "->错误NullPointerException：" + ex);
            return false;
        } catch (Exception ex) {
            log.error(Thread.currentThread().getName() + "->错误Exception：" + ex);
            return false;
        }
        return rtnval;
    }

    private void updateCalcFinishTime(long calcId) {
        // 保存完成计算时间
        BinOrderCalcDO calcDO = new BinOrderCalcDO();
        calcDO.setId(calcId);
        calcDO.setUpdateTime(DateUtil.getNow());
        calcDO.setUpdateUser(SMCApp.getLoginAuthDtoForSysUser().getUserNo());
        calcDO.setCalcFinishTime(DateUtil.getNow());
        binOrderCalcMapper.updateById(calcDO);
    }

    @Override
    public void saveCalcState(RedisMessageVO messageVO) {
        messageVO.setOptDate(new Date());
        messageVO.setContent(messageVO.getContent() + " " + DateUtil.getFormatDate(new Date(), "hh:mm"));
        String json = JSON.toJSONString(messageVO);

        redisUtil.set(Constants.REDIS_KEY_BIN_CALC_ING, json);
        redisUtil.expire(Constants.REDIS_KEY_BIN_CALC_ING, 1800);
        if (StringUtils.isNotBlank(messageVO.getNo())) {
            String keyCalcById = Constants.REDIS_KEY_BIN_CALC_STATUS + messageVO.getNo();

            redisUtil.set(keyCalcById, json);
            redisUtil.expire(keyCalcById, 1800);
        }
    }

    @Override
    public RedisMessageVO getCalcState(Long calcId) {
        RedisMessageVO messageVo;
        Object obj = redisUtil.get(Constants.REDIS_KEY_BIN_CALC_STATUS + calcId);
        if (obj != null) {
            messageVo = JSON.parseObject(obj.toString(), RedisMessageVO.class);
            /*if (messageVo.getStatus() == 1) {
                return ResultVo.failure("正在计算中：" + messageVo.getContent());
            }
            if (messageVo.getStatus() == 2) {
                return ResultVo.failure("已计算完成不用重复计算：" + messageVo.getContent());
            }*/
            return messageVo;
        } else {
            messageVo = new RedisMessageVO();
            messageVo.setStatus(0);
            messageVo.setContent("未计算状态");
        }
        return messageVo;

    }


    public boolean calcBinOrderTransQty(BinOrderCalcRequestDTO dto, RedisMessageVO messageVo) {
        //1.1查询detail数据，按照calcId查询
        // Long id = 15753453L;
        LambdaQueryWrapper<BinOrderDetailDO> detailQuery = Wrappers.lambdaQuery();
        detailQuery
                .eq(BinOrderDetailDO::getCalcId, dto.getCalcId())
                .gt(BinOrderDetailDO::getConfirmQty, 0)  //补货数量大于0
                .eq(BinOrderDetailDO::getStatus, 1)
                .ne(BinOrderDetailDO::getCalcType, 4);  //客户BIN只算补库数量，不参与采购或调库
        List<BinOrderDetailDO> list = binOrderDetailMapper.selectList(detailQuery);
        if (CollectionUtils.isEmpty(list)) {
            messageVo.setContent("没有需要补货的数量");
            saveCalcState(messageVo);
            this.updateCalcFinishTime(dto.getCalcId());
            return true;
        }
        //1.2查询仓库信息
        ResultVo<WarehouseVO> warehouseVOResultVo = commonServiceFeignApi.getWarehouseInfoByCode(dto.getWarehouseCode());

        if (!warehouseVOResultVo.isSuccess()) {
            log.error(dto.getWarehouseCode() + warehouseVOResultVo.getMessage());
            messageVo.setContent(dto.getWarehouseCode() + warehouseVOResultVo.getMessage());
            saveCalcState(messageVo);
            return false;
        }
        WarehouseVO warehouseVO = warehouseVOResultVo.getData();

        if (warehouseVO.getTransferFlag() == null) {
            warehouseVO.setTransferFlag(0);
        }
        if (warehouseVO.getPrestockFlag() == null) {
            warehouseVO.setPrestockFlag(0);
        }
        if (warehouseVO.getTransferFlag() == 0 && warehouseVO.getPrestockFlag() == 0) {
            log.error(warehouseVO.getWarehouseCode() + "此仓库不可补库和调库。");
            messageVo.setContent(warehouseVO.getWarehouseCode() + "此仓库不可补库和调库。");
            saveCalcState(messageVo);
            return false;
        }
        String warehouseType = warehouseVO.getWarehouseType();
        // 2.把detail数据按照型号分组
        List<String> modelNoList = list.stream().map(BinOrderDetailDO::getModelNo).distinct().collect(Collectors.toList());
        // 3.根据计算时所选的仓库  获取可出在库仓库的顺序，从字典
        List<String> warehouseCodeList = this.getBinOrderTransWarehouseCode(dto.getWarehouseCode(), warehouseType);
        // 根据型号查询每个物流中心的在库
        //4.计算过剩数量 Map<modelno,Map<warehouse,InventoryInfo>>
        ResultVo<Map<String, Map<String, BinOrderInventoryInfoVO>>> stockResult = this.listBinWarehouseStock(modelNoList, warehouseCodeList);
        if (!stockResult.isSuccess()) {
            log.error("获取BIN补货型号在库信息失败: {}", stockResult.getMessage());
            messageVo.setContent("BIN补库数量分配调拨和采购失败-" + stockResult.getMessage());
            saveCalcState(messageVo);
            return false;
        }
        //Map<modelno,Map<warehouse,InventoryInfo>>
        Map<String, Map<String, BinOrderInventoryInfoVO>> modelWarehouseStockMap = stockResult.getData();
        Map<String, BinOrderInventoryInfoVO> warehouseMap;
        //start 初始化变量，准备更新detail和split
        long startTimer = System.currentTimeMillis();
        List<BinOrderDetailDO> updDOList = new ArrayList<>();
        List<BinOrderDetailSplitDO> insertSplitList = new ArrayList<>();
        List<Long> updateSplitCancelList = new ArrayList<>();

        List<BinOrderDetailSplitDO> splitList = new ArrayList<>();
        int count = 0;
        int totalCount = list.size();
        // 5.获取公司库存总量控制最大月数
        BigDecimal maxControlMonths = this.getMaxControlMonths();
        if (maxControlMonths == null) {
            messageVo.setContent("BIN补库数量分配调拨和采购失败-库存总量控制月数为空");
            saveCalcState(messageVo);
            return false;
        }
        //6.分库计算
        BinOrderDetailDO updDO;
        if ("SUB".equalsIgnoreCase(warehouseType)) {
            // 查找该分库的物流中心
            String warehouseCode = warehouseCodeList.get(0);
            //    <!--add by WuWeiDong 20240223  bug 13554  分库自动补库改善 -->
            //该分库对应物流中心是否为BIN
            Map<String, String> binMap = new HashMap<>();
            try {
                Future<List<BindataDO>> binDatafuture = commonService.asyncGetBinDataByModels(modelNoList, Arrays.asList(warehouseCode));
                while (true) {
                    if (binDatafuture.isDone()) {
                        binMap = binDatafuture.get().stream().filter(i -> i.getQtyBin().compareTo(0) == 1).collect(Collectors.toMap(BindataDO::getModelNo, BindataDO::getWarehouseCode, (n1, n2) -> n1));
                        break;
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                log.error("获取BIN数据失败: {}", stockResult.getMessage());
                messageVo.setContent("获取BIN数据失败-" + stockResult.getMessage());
                saveCalcState(messageVo);
                return false;
            }

            for (BinOrderDetailDO info : list) {
                messageVo.setContent(dto.getCalcId() + "计算是否有过剩调拨" + count++ + "/" + totalCount);
                log.info(messageVo.getContent());
                saveCalcState(messageVo);
                try {
                    Boolean isBin = binMap.containsKey(info.getModelNo());
                    warehouseMap = modelWarehouseStockMap.get(info.getModelNo());
                    if (warehouseMap == null) {
                        continue;
                    }
                    updDO = new BinOrderDetailDO();
                    splitList = new ArrayList<>(warehouseCodeList.size());
                    List<Long> SplitCancelList = new ArrayList<>();
                    //分库计算
                    this.handelSubWarehouse(warehouseCodeList, info, warehouseVO, isBin, warehouseMap, maxControlMonths, updDO, splitList, SplitCancelList);
                    updDO.setCalcId(dto.getCalcId());
                    updDOList.add(updDO);
                    if (CollectionUtils.isNotEmpty(splitList)) {
                        insertSplitList.addAll(splitList);
                    }
                    if (CollectionUtils.isNotEmpty(SplitCancelList)) {
                        updateSplitCancelList.addAll(SplitCancelList);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    messageVo.setContent("计算异常" + e.getMessage() + dto.getCalcId() + "计算是否有过剩调拨" + count++ + "/" + totalCount);
                    log.error("BIN补库计算: {}", messageVo.getContent(), e);
                    messageVo.setStatus(4);
                    saveCalcState(messageVo);
                }
            }
        }
        else {
            //7.非分库补库
            for (BinOrderDetailDO info : list) {

                //先默认成可采购，存在调拨条件再改成调拨
                messageVo.setContent(dto.getCalcId() + "计算是否有过剩调拨" + count++ + "/" + totalCount);
                log.info(messageVo.getContent());
                saveCalcState(messageVo);
                try {
                    warehouseMap = modelWarehouseStockMap.get(info.getModelNo());
                    updDO = new BinOrderDetailDO();
                    splitList = new ArrayList<>(warehouseCodeList.size());
                    List<Long> SplitCancelList = new ArrayList<>();
                    //主要逻辑：非分库计算
                    this.handelNotSubWarehouse(warehouseCodeList, info, warehouseVO, warehouseMap, maxControlMonths, updDO, splitList, SplitCancelList);
                    updDO.setCalcId(dto.getCalcId());
                    updDOList.add(updDO);
                    if (CollectionUtils.isNotEmpty(splitList)) {
                        insertSplitList.addAll(splitList);
                    }
                    if (CollectionUtils.isNotEmpty(SplitCancelList)) {
                        updateSplitCancelList.addAll(SplitCancelList);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    messageVo.setContent("计算异常" + e.getMessage() + dto.getCalcId() + "计算是否有过剩调拨" + count++ + "/" + totalCount);
                    log.error("BIN补库计算: {}", messageVo.getContent(), e);
                    messageVo.setStatus(4);
                    saveCalcState(messageVo);
                }
            }


        }
        ///8写入，更新 end
        boolean result = this.insertUpdateBinOrderDetail(updDOList, insertSplitList, updateSplitCancelList);
        log.info(Thread.currentThread().getStackTrace()[1].getMethodName() + " 处理结束，耗时(s): " + (System.currentTimeMillis() - startTimer) / 1000.0);
        return result;
    }

    /**
     * 分库补库计算
     *
     * @param warehouseCodeList                 ：补库仓库
     * @param detailDO                          :明细数据
     * @param warehouseVO                       ：当前备库库房信息
     * @param isBin
     * @param warehouseMap                      ：在库数等型号库存信息
     * @param maxControlMonths                  ：库存最大保有月数
     * @param updDO                             ：返回更新明细数据
     * @param insertSplitList                   ：返回拆分数据
     * @param updateSplitCancelList：返回之前拆分ID，取消
     */
    private void handelSubWarehouse(List<String> warehouseCodeList, BinOrderDetailDO detailDO, WarehouseVO warehouseVO,
                                    Boolean isBin, Map<String, BinOrderInventoryInfoVO> warehouseMap, BigDecimal maxControlMonths,
                                    BinOrderDetailDO updDO, List<BinOrderDetailSplitDO> insertSplitList, List<Long> updateSplitCancelList) {

        List<BinOrderDetailSplitDO> splitList = new ArrayList<>();
        String[] stocks;
        int[] stockQtys;
        BinOrderInventoryInfoVO binOrderInventoryInfo = new BinOrderInventoryInfoVO();

        String warehouseCode = warehouseCodeList.get(0);
        updDO.setId(detailDO.getId());
        //    <!--add by WuWeiDong 20240320  bug 13764  增加默认值，避免空值 -->
        if (ObjectUtils.isEmpty(detailDO.getConfirmQty())) {
            detailDO.setConfirmQty(0);
        }
        if (ObjectUtils.isEmpty(detailDO.getOrderQty())) {
            detailDO.setOrderQty(0);
        }
        if (ObjectUtils.isEmpty(detailDO.getStockQty())) {
            detailDO.setStockQty(0);
        }
        if (ObjectUtils.isEmpty(detailDO.getOrdingQty())) {
            detailDO.setOrdingQty(0);
        }
        if (ObjectUtils.isEmpty(detailDO.getPreQty())) {
            detailDO.setPreQty(0);
        }
        if (ObjectUtils.isEmpty(detailDO.getMean())) {
            detailDO.setMean(0d);
        }
        if (ObjectUtils.isEmpty(detailDO.getStockMonthsAll())) {
            detailDO.setStockMonthsAll(BigDecimal.ZERO);
        }

        updDO.setConfirmQty(detailDO.getConfirmQty());
        updDO.setPoQty(0);
        updDO.setTransQty(0);
        splitList = new ArrayList<>(warehouseCodeList.size());
        boolean maxControlMonthIntercept = detailDO.getStockMonthsAll().compareTo(maxControlMonths) >= 0 && detailDO.getOrderQty() == 0;
        if (StringUtils.isNotBlank(detailDO.getOrderType())) {
            updDO.setOrderType(detailDO.getOrderType());
        } else if (StringUtils.isNotBlank(detailDO.getSetOrderType())) {
            updDO.setOrderType(detailDO.getSetOrderType());
        } else {
            updDO.setOrderType("K");
        }
        updDO.setSupplierCode(detailDO.getMainSupplierCode());
        //直采，或仓库属性不能调拨，采购
        if (detailDO.getQtybin()!= null && (detailDO.getDirectpurchase().compareTo(0) != 0 || warehouseVO.getTransferFlag().compareTo(1) != 0)) {
            updDO.setPoQty(detailDO.getConfirmQty());
            updDO.setTransQty(0);
            if (isBin) {
                updDO.setDirectpurchase(2);
            }
        } else {
            //未标记直采的型号，如果分库型号是大库的BIN品，进入库存调整模块
            //查找物流中心的过剩数量
            if (ObjectUtils.isNotEmpty(warehouseMap)) {
                if (isBin) {
                    stocks = new String[warehouseCodeList.size()];
                    stockQtys = new int[warehouseCodeList.size()];
                    //    <!--add by WuWeiDong 20240327  bug 13800  异地物流中心处理-->
                    for (int index = 0; index < warehouseCodeList.size(); index++) {
                        stocks[index] = warehouseCodeList.get(index);
                        BinOrderInventoryInfoVO inventoryInfoVO = warehouseMap.getOrDefault(warehouseCodeList.get(index), null);
                        Integer excessQty = 0;
                        if (ObjectUtils.isNotEmpty(inventoryInfoVO) && Optional.ofNullable(inventoryInfoVO.getExcessQty()).orElse(0).compareTo(0) == 1) {
                            excessQty = inventoryInfoVO.getExcessQty();

                        }
                        //1）剩余数>=补库数，全部调拨。
                        if (excessQty.compareTo(detailDO.getConfirmQty()) < 0) {
                            excessQty = 0;
                        }
                        stocks[index] = warehouseCodeList.get(index);
                        stockQtys[index] = excessQty;

                    }
                    Integer transQty = Arrays.stream(stockQtys).sum();
                    if (transQty.compareTo(0) == 1) {
                        splitList = this.getBinOrderDetailSplits(stocks, stockQtys, updDO, detailDO, maxControlMonthIntercept);

                    } // End
                    updateSplitCancelList.add(detailDO.getId());
                    if (CollectionUtils.isNotEmpty(splitList) && splitList.size() > 0) {
                        //写入拆分表
                        insertSplitList.addAll(splitList);
                    //    transQty = splitList.stream().mapToInt(BinOrderDetailSplitDO::getConfirmQty).sum();

                    }
                 //   updDO.setTransQty(transQty);
                    if (updDO.getTransQty().compareTo(0) == 1 && updDO.getPoQty().compareTo(0) <= 0) {
                        //只调拨
                        updDO.setOrderType("1");
                        updDO.setSupplierCode(warehouseCode);
                        updDO.setOrderStockCode(warehouseCode);

                    } else if (updDO.getTransQty().compareTo(0) == 1 && updDO.getPoQty().compareTo(0) == 1) {
                        //调拨和采购
                        updDO.setOrderType("9");
                        updDO.setOrderStockCode(warehouseCode);
                        if (isBin) {
                            updDO.setDirectpurchase(2);
                        }
                    } else {
                        //只采购
                        updDO.setOrderType("K");
                        if (isBin) {
                            updDO.setDirectpurchase(2);
                        }
                    }

                }
                else {
                    //所有物流中心补库
                    //查找各个库房过剩数量
                    if (MapUtils.isNotEmpty(warehouseMap) && warehouseVO.getTransferFlag().compareTo(0) != 0) {
                        //其它物流中心过剩数量>=补库数量则调拨
                        // KBJ --> KSH > KGZ
                        // KSH --> KBJ > KGZ
                        // KGZ --> KBJ > KSH
                        // 根据可出在库仓库，分配调拨数量 bug-8788
                        stocks = new String[warehouseCodeList.size()];
                        stockQtys = new int[warehouseCodeList.size()];
                        for (int index = 0; index < warehouseCodeList.size(); index++) {

                            BinOrderInventoryInfoVO inventoryInfoVO = warehouseMap.getOrDefault(warehouseCodeList.get(index), null);
                            Integer excessQty = 0;
                            if (ObjectUtils.isNotEmpty(inventoryInfoVO) && Optional.ofNullable(inventoryInfoVO.getExcessQty()).orElse(0).compareTo(0) == 1) {
                                excessQty = inventoryInfoVO.getExcessQty();
                            }
                            stocks[index] = warehouseCodeList.get(index);
                            stockQtys[index] = excessQty;
                        }
                        if (Arrays.stream(stockQtys).sum() > 0) {
                            splitList = this.getBinOrderDetailSplits(stocks, stockQtys, updDO, detailDO, maxControlMonthIntercept);
                        } // End
                        updateSplitCancelList.add(detailDO.getId());

                    }
                    Integer transQty = 0;
                    if (CollectionUtils.isNotEmpty(splitList) && splitList.size() > 0) {
                        //写入拆分表
                        insertSplitList.addAll(splitList);
                    }
                    // 当超过全公司的总的备库月数总量控制的月数，不做采购
                    if (maxControlMonthIntercept) {
                        // order_qty > 0 时, confirm_qty = order_qty
//                        if (detailDO.getOrderQty().compareTo(0) == 0) {
//                            updDO.setConfirmQty(updDO.getConfirmQty() - updDO.getPoQty());
//                        }
                        if (detailDO.getPoQty().compareTo(0) == 1) {
                            updDO.setConfirmQty(updDO.getConfirmQty() - updDO.getPoQty());
                        }
                        updDO.setPoQty(0);
                        if (detailDO.getMean() > 0) {
                            updDO.setMonths(BigDecimalUtil.div((updDO.getConfirmQty() + detailDO.getStockQty() + detailDO.getOrdingQty() - detailDO.getPreQty()), detailDO.getMean(), 2).doubleValue());
                        }
                    }

                }
            } else {
                updDO.setPoQty(detailDO.getConfirmQty());
                updDO.setTransQty(0);
                if (isBin) {
                    updDO.setDirectpurchase(2);
                }
            }
        }
        updDO.setUpdateTime(DateUtil.getNow());
        updDO.setUpdateUser(SMCApp.getLoginAuthDtoForSysUser().getUserNo());
    }

    /**
     * 非分库补库计算
     *
     * @param warehouseCodeList                 ：补库仓库
     * @param detailDO                          :明细数据
     * @param warehouseVO                       ：当前备库库房信息
     * @param warehouseMap                      ：在库数等型号库存信息
     * @param maxControlMonths                  ：库存最大保有月数
     * @param updDO                             ：返回更新明细数据
     * @param insertSplitList                   ：返回拆分数据
     * @param updateSplitCancelList：返回之前拆分ID，取消
     */
    private void handelNotSubWarehouse(List<String> warehouseCodeList, BinOrderDetailDO detailDO, WarehouseVO warehouseVO,
                                       Map<String, BinOrderInventoryInfoVO> warehouseMap, BigDecimal maxControlMonths,
                                       BinOrderDetailDO updDO, List<BinOrderDetailSplitDO> insertSplitList, List<Long> updateSplitCancelList) {

        List<BinOrderDetailSplitDO> splitList = new ArrayList<>();
        String[] stocks;
        int[] stockQtys;
        updDO.setId(detailDO.getId());
        //    <!--add by WuWeiDong 20240320  bug 13764  增加默认值，避免空值 -->
        if (ObjectUtils.isEmpty(detailDO.getConfirmQty())) {
            detailDO.setConfirmQty(0);
        }
        if (ObjectUtils.isEmpty(detailDO.getOrderQty())) {
            detailDO.setOrderQty(0);
        }
        if (ObjectUtils.isEmpty(detailDO.getStockQty())) {
            detailDO.setStockQty(0);
        }
        if (ObjectUtils.isEmpty(detailDO.getOrdingQty())) {
            detailDO.setOrdingQty(0);
        }
        if (ObjectUtils.isEmpty(detailDO.getPreQty())) {
            detailDO.setPreQty(0);
        }
        if (ObjectUtils.isEmpty(detailDO.getMean())) {
            detailDO.setMean(0d);
        }
        if (ObjectUtils.isEmpty(detailDO.getStockMonthsAll())) {
            detailDO.setStockMonthsAll(BigDecimal.ZERO);
        }

        if (detailDO.getDirectpurchase() == null) {
            detailDO.setDirectpurchase(0);
        }
        if (MapUtils.isEmpty(warehouseMap)) {
            warehouseMap = new HashMap<>();
        }
        //初始化.设置采购数量
        updDO.setPoQty(0);
        //设置调拨数量
        updDO.setTransQty(0);
        updDO.setConfirmQty(detailDO.getConfirmQty());
        /**
         * 1.计算采购数量
         * 采购数量 = （(补库数量-所有仓的过剩数量）/qtyBin)*qtyBin
         */
        //过剩数量
        int sumExcessQty = warehouseMap.values().stream().filter(i -> i.getExcessQty() != null).mapToInt(BinOrderInventoryInfoVO::getExcessQty).sum();
        int poQty = 0;
        if (detailDO.getQtybin() != null) {
            int i = (detailDO.getBinDiffQty() - sumExcessQty) / detailDO.getQtybin();
            if (i < 0) {
                i = 0;
            }
            // (BinDiffQty - sumExcessQty) - (0.5 × Qtybin) >0
            if (i < 1 && detailDO.getBincell() == 1
                    && new BigDecimal(detailDO.getBinDiffQty() - sumExcessQty).compareTo(new BigDecimal("0.5").multiply(new BigDecimal(detailDO.getQtybin().toString()))) > 0) {
                i = 1;
            }
            //计算采购数据
            poQty = i * detailDO.getQtybin();
        }
        //2.设置采购数量,判断是否有补库能力
        if (warehouseVO.getPrestockFlag().compareTo(0) != 0) {
            //如果有补库能力，设置采购数量
            updDO.setPoQty(poQty);
            //设置OrderType
            if (StringUtils.isNotBlank(detailDO.getOrderType())) {
                updDO.setOrderType(detailDO.getOrderType());
            } else if (StringUtils.isNotBlank(detailDO.getSetOrderType())) {
                updDO.setOrderType(detailDO.getSetOrderType());
            } else {
                updDO.setOrderType("K");
            }
            updDO.setSupplierCode(detailDO.getMainSupplierCode());
            //3.判断最大控制月数： StockMonthsAll 大于等于 maxControlMonths，则为 true
            boolean maxControlMonthIntercept = detailDO.getStockMonthsAll().compareTo(maxControlMonths) >= 0 && detailDO.getOrderQty() == 0;
            //判断有调库能力
            if (warehouseVO.getTransferFlag() != 0) {
                List<String> list = warehouseCodeList;
                //bin则算list
                if(detailDO.getQtybin() != null){
                    //判断不为空
                    // 总可用月数<=1.5,则采购数量等于poQty
                    if (detailDO.getStockMonthsAll().compareTo(new BigDecimal(1.5)) <= 0) {
                        updDO.setPoQty(poQty);
                        // 重新计算confirmQty
                        updDO.setConfirmQty(updDO.getPoQty() + updDO.getTransQty());
                        // 计算补货可用月数
                        if (detailDO.getMean() != null && detailDO.getMean() > 0) {
                            // 补货可用月数=（确认补货数量+在库数量+订货中数量-预约中数量）/月用量,保留两位小数，四舍五入
                            updDO.setMonths(BigDecimalUtil.div((updDO.getConfirmQty() + detailDO.getStockQty() + detailDO.getOrdingQty() - detailDO.getPreQty()), detailDO.getMean(), 2).doubleValue());
                        }
                        return;
                    }
                    //总可用月数>1.5,则重新计算分配仓
                     list = calWarehouseCodeConfig(detailDO, updDO, warehouseMap, warehouseCodeList);
                }
                //7.如果有过剩数量，则创建split数据
                /**
                 * ALL 完整顺序 KBJ-SCZ-KSH-KGZ
                 * KBJ 北京仓  SCZ-KSH-KGZ
                 * KGZ 广州仓 KBJ-SCZ-KSH
                 * KSH 上海仓  SCZ-KBJ-KGZ
                 * SCZ 常州仓 KSH-KBJ-KGZ
                 */
                // 计算非本仓过剩总数量
                stocks = new String[list.size()];
                stockQtys = new int[list.size()];
                for (int index = 0; index < list.size(); index++) {

                    BinOrderInventoryInfoVO inventoryInfoVO = warehouseMap.getOrDefault(list.get(index), null);
                    Integer excessQty = 0;
                    if (inventoryInfoVO != null && Optional.ofNullable(inventoryInfoVO.getExcessQty()).orElse(0) != 0) {
                        excessQty = inventoryInfoVO.getExcessQty();
                    }
                    stocks[index] = list.get(index);
                    stockQtys[index] = excessQty;
                }
                //如果大于0，则创建拆分表数据

                    //根据可出在库仓库，分配调拨数量 bug-8788
                splitList = this.getBinOrderDetailSplitsByNotSub(stocks, stockQtys, updDO, detailDO, maxControlMonthIntercept);

                updateSplitCancelList.add(detailDO.getId());
            }
            if (CollectionUtils.isNotEmpty(splitList) && splitList.size() > 0) {
                //写入拆分表
                insertSplitList.addAll(splitList);
            }
        } else {
            //没有补库能力
            updDO.setPoQty(0);
            updDO.setTransQty(0);
        }
        // 重新计算confirmQty
        updDO.setConfirmQty(updDO.getPoQty() + updDO.getTransQty());
        // 计算补货可用月数
        if (detailDO.getMean() != null && detailDO.getMean() > 0) {
            // 补货可用月数=（确认补货数量+在库数量+订货中数量-预约中数量）/月用量,保留两位小数，四舍五入
            updDO.setMonths(BigDecimalUtil.div((updDO.getConfirmQty() + detailDO.getStockQty() + detailDO.getOrdingQty() - detailDO.getPreQty()), detailDO.getMean(), 2).doubleValue());
        }
    }

    /**
     * 总可用月数 <= 1.5
     *     更新采购数量 = 补库数量; 更新调拨数量 = 0
     * 不小于1.5
     *   是否直采
     *      是直采: 更新调拨数为0
     *      不是直采:
     *          判断中央仓标识:
     *               是中央仓: 按照调拨数量逐一比较过剩数量
     *               不是中央仓: 先找到该型号的中央仓 比较过剩数量,再按调拨顺序比较
     *          其他仓的过剩数量/补库仓月用量 < 0.5?
     *               是:  减去该仓调拨数量,不生成该藏调拨
     *               不是: 等待确认后生成调拨指令调拨
     */
    public List<String> calWarehouseCodeConfig(BinOrderDetailDO detailDO,BinOrderDetailDO updDO,Map<String, BinOrderInventoryInfoVO> warehouseMap, List<String> warehouseCodeList) {
        List<String> calwarehouseCodeList = new ArrayList<>();
        //if(detailDO.getStockMonthsAll().compareTo(new BigDecimal("1.5")) <= 0) {   // 小于等于1.5
        //    updDO.setPoQty(detailDO.getConfirmQty());
        //    updDO.setTransQty(0);
        //    return calwarehouseCodeList;//a
        //} else {
        //    // 是否直采
        //    if (detailDO.getDirectpurchase() == 1) {
        //        updDO.setTransQty(0);
        //        return calwarehouseCodeList;//b1
        //    }
        //    if(detailDO.getDirectpurchase() == 2){
        //        updDO.setPoQty(0);
        //    }
        // 判断中央仓
        BinOrderInventoryInfoVO binOrderInventoryInfoVO = warehouseMap.get(detailDO.getWarehouseCode());
        // 如果是中央仓，则返回warehouseCodeList
        if (binOrderInventoryInfoVO != null && binOrderInventoryInfoVO.getIsCenterWareHouse()) {
            return warehouseCodeList;
        }
        //如果不是中央仓，则去查询bindata表，找到中央仓
        LambdaQueryWrapper<BindataDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BindataDO::getModelNo, detailDO.getModelNo()).eq(BindataDO::getCentreFlag, 1).eq(BindataDO::getDelFlag, 0);
        List<BindataDO> bindataDOS = bindataMapper.selectList(queryWrapper);
        String centerWarehosue = "";
        if (CollectionUtil.isNotEmpty(bindataDOS)) {
            centerWarehosue = bindataDOS.get(0).getWarehouseCode();
            //把中央仓放在最前边,warehouseCodeList放在后边
            calwarehouseCodeList.add(centerWarehosue);
            for (String item : warehouseCodeList) {
                if (!item.equals(centerWarehosue)) {
                    calwarehouseCodeList.add(item);
                }
            }
            return calwarehouseCodeList;
        } else {
            //如果查询bindata中央仓为空，则返回原warehouseList
            return warehouseCodeList;
        }
    }

    //分库 BIN\非BIN 原始
    public List<BinOrderDetailSplitDO> getBinOrderDetailSplits(String[] stocks, int[] stockQtys,
                                                               BinOrderDetailDO updDO, BinOrderDetailDO info,
                                                               boolean maxControlMonthIntercept) {
        List<BinOrderDetailSplitDO> list = new ArrayList<>(stocks.length);
        int confirmQty = info.getBinDiffQty();
        int totalTranQty = 0;
        int poQty = 0;
        BinOrderDetailSplitDO splitDO;

        // Add by Dengdenghui 2022-11-25 for bug-8788
        // Edit by DengDengHui 2022-11-01 for bug-8395
        for (int index = 0; index < stocks.length; index++) {
            if (stockQtys[index] > 0 && confirmQty > 0) {
                splitDO = BeanCopyUtil.copy(info, BinOrderDetailSplitDO.class);
                splitDO.setCreateTime(DateUtil.getNow());
                splitDO.setCreateUser("BINCALC");
                splitDO.setUpdateTime(DateUtil.getNow());
                splitDO.setUpdateUser("BINCALC");
                splitDO.setDelFlag(0);
                splitDO.setStatus(0);
                splitDO.setFromId(info.getId());
                splitDO.setSupplierCode(stocks[index]);
                if (stockQtys[index] >= confirmQty) {
                    splitDO.setConfirmQty(confirmQty);
                } else {
                    splitDO.setConfirmQty(stockQtys[index]);
                }
                splitDO.setOrderType("1");
                list.add(splitDO);

                stockQtys[index] = stockQtys[index] - splitDO.getConfirmQty();
                confirmQty = confirmQty - splitDO.getConfirmQty();
                totalTranQty += splitDO.getConfirmQty();
            }
        } // End

        if (confirmQty > 0 && Optional.ofNullable(info.getQtybin()).orElse(0).compareTo(0) > 0) {
            //重新算剩余数量是否有一个bin
            int leftQty = info.getBinDiffQty() - totalTranQty;
            if (leftQty > 0) {
                int leftPoQty = leftQty - leftQty % info.getQtybin();
                confirmQty = leftPoQty;
                if (leftPoQty == 0 && info.getBincell() == 1
                        && new BigDecimal(leftQty).compareTo(
                        new BigDecimal("0.5").multiply(new BigDecimal(info.getQtybin().toString()))) > 0) {
                    //解决bincell=1 ,在库不够0.5时下次还需要补1个bin
                    confirmQty = info.getQtybin();
                }
            } else {
                confirmQty = 0;
            }
        }
        //未调拨数量>0,并且全公司的总的备库月数不超过总量控制的月数，则转成采购数量
        if (confirmQty > 0 && !maxControlMonthIntercept) {
            splitDO = BeanCopyUtil.copy(info, BinOrderDetailSplitDO.class);
            splitDO.setCreateTime(DateUtil.getNow());
            splitDO.setCreateUser("BINCALC");
            splitDO.setUpdateTime(DateUtil.getNow());
            splitDO.setUpdateUser("BINCALC");
            splitDO.setDelFlag(0);
            splitDO.setStatus(0);
            if ("9".equalsIgnoreCase(splitDO.getOrderType()) || "1".equalsIgnoreCase(splitDO.getOrderType())) {
                splitDO.setOrderType("");
            }
            poQty = confirmQty;
            splitDO.setConfirmQty(confirmQty);
            splitDO.setFromId(info.getId());
            splitDO.setSupplierCode(info.getSupplierCode());
            list.add(splitDO);
        }
        updDO.setTransQty(totalTranQty);
        updDO.setPoQty(poQty);
        // order_qty > 0 时, confirm_qty = order_qty
        if (Optional.ofNullable(info.getOrderQty()).orElse(0) == 0) {
            updDO.setConfirmQty(poQty + totalTranQty);
        }
        if (list.size() >= 1) {
            splitDO = list.get(0);
            updDO.setSupplierCode(splitDO.getSupplierCode());
            updDO.setOrderStockCode(splitDO.getSupplierCode());
            updDO.setOrderType(splitDO.getOrderType());
        }
        if(totalTranQty>0 && poQty>0)
        {
            updDO.setOrderType("9");
        }
//        updDO.setCreateTime(DateUtil.getNow());
//        updDO.setCreateUser("BINCALC");
//        updDO.setStatus(1);
        return list;
    }

    //非分库 BIN
    public List<BinOrderDetailSplitDO> getBinOrderDetailSplitsByNotSub(String[] stocks, int[] stockQtys,
                                                                       BinOrderDetailDO updDO, BinOrderDetailDO info,
                                                                       boolean maxControlMonthIntercept) {

        //是否走调拨splitDO
        boolean transFlag = true;
        if (Arrays.stream(stockQtys).sum() <= 0) {
            transFlag = false;
        }
        List<BinOrderDetailSplitDO> list = new ArrayList<>(stocks.length);
        int confirmQty = info.getBinDiffQty();
        int totalTranQty = 0;
        int poQty = updDO.getPoQty();
        BinOrderDetailSplitDO splitDO;
        //先判断直采标识是否=2，是则将采购数量设置为0
        if (info.getDirectpurchase() == 2) {
            poQty = 0;
        } else {
            //直采标识=0/1时，判断总月用量不超过全公司总月用量，继续判断直采标识
            if (maxControlMonthIntercept) {
                poQty = 0;
            }
            //不超过，则判断直采
            if (info.getDirectpurchase() == 1) {
                transFlag = false;
            }
        }
        if (transFlag) {
            for (int index = 0; index < stocks.length; index++) {
                if (stockQtys[index] > 0 && confirmQty > 0) {
                    splitDO = BeanCopyUtil.copy(info, BinOrderDetailSplitDO.class);
                    splitDO.setCreateTime(DateUtil.getNow());
                    splitDO.setCreateUser("BINCALC");
                    splitDO.setUpdateTime(DateUtil.getNow());
                    splitDO.setUpdateUser("BINCALC");
                    splitDO.setDelFlag(0);
                    splitDO.setStatus(0);
                    splitDO.setFromId(info.getId());
                    splitDO.setSupplierCode(stocks[index]);
                    if (stockQtys[index] >= confirmQty) {
                        splitDO.setConfirmQty(confirmQty);
                    } else {
                        splitDO.setConfirmQty(stockQtys[index]);
                    }
                    //计算（他仓过剩数量÷补库仓月用量）是否＜0.5？是，则跳过该过剩仓的调拨
                    if (stockQtys[index] / info.getMean() < 0.5) {
                        confirmQty = confirmQty - stockQtys[index];
                        continue;
                    }
                    splitDO.setOrderType("1");
                    list.add(splitDO);
                    //stockQtys[index] = stockQtys[index] - splitDO.getConfirmQty(); 没用
                    confirmQty = confirmQty - splitDO.getConfirmQty();
                    totalTranQty += splitDO.getConfirmQty();
                }
            }
        }
        if (info.getQtybin() == null) {
            poQty = confirmQty;
        }
        //采购数量>0
        if (poQty > 0) {
            //如果允许采购，则创建采购的split数据
            splitDO = BeanCopyUtil.copy(info, BinOrderDetailSplitDO.class);
            splitDO.setCreateTime(DateUtil.getNow());
            splitDO.setCreateUser("BINCALC");
            splitDO.setUpdateTime(DateUtil.getNow());
            splitDO.setUpdateUser("BINCALC");
            splitDO.setDelFlag(0);
            splitDO.setStatus(0);
            if ("9".equalsIgnoreCase(splitDO.getOrderType()) || "1".equalsIgnoreCase(splitDO.getOrderType())) {
                splitDO.setOrderType("");
            }
            splitDO.setConfirmQty(poQty);
            splitDO.setFromId(info.getId());
            splitDO.setSupplierCode(info.getSupplierCode());
            if (info.getQtybin() == null) {
                splitDO.setSupplierCode(info.getMainSupplierCode());
            }
            list.add(splitDO);
        }
        updDO.setTransQty(totalTranQty);
        updDO.setPoQty(poQty);
        // order_qty > 0 时, confirm_qty = order_qty  todo??
        if (Optional.ofNullable(info.getOrderQty()).orElse(0) == 0) {
            updDO.setConfirmQty(poQty + totalTranQty);
        }
        if (list.size() >= 1) {
            splitDO = list.get(0);
            updDO.setSupplierCode(splitDO.getSupplierCode());
            updDO.setOrderStockCode(splitDO.getSupplierCode());
            updDO.setOrderType(splitDO.getOrderType());
        }
        if (list.size() > 1) {
            updDO.setOrderType("9");
        }
        if (totalTranQty > 0 && poQty > 0) {
            updDO.setOrderType("9");
        }
        return list;
    }

//    /**
//     * 生成订单
//     *
//     * @param dto 订单数据
//     */
//    @Override
//    public ResultVo<String> createOrder(BinOrderApproveRequestDTO dto) {
//        binorderService.createOrderByAppId(dto.getAppIds()[0]);
//        return ResultVo.success("后台正在生成订单，请稍后再查询，生成完成会更新状态");
//    }


    /**
     * 按库房存储
     *
     * @param modelNo
     * @return
     */
    @Override
    public Map<String, BinOrderInventoryInfoVO> getInventoryInfomap(String modelNo) {
        List<BinOrderInventoryInfoVO> stocklist = listBinWarehouseStock(modelNo).getData();
        if (stocklist != null && stocklist.size() > 0) {
            //按库房存储起来
            Map<String, BinOrderInventoryInfoVO> listmap = stocklist.stream()
                    .collect(Collectors.toMap(BinOrderInventoryInfoVO::getWarehouseCode, (p) -> p));
            return listmap;
        }
        return null;
    }

    /**
     * 更新交货期
     */
    private void updateDlvDate(Long calcId) {
        LambdaQueryWrapper<BinOrderDetailDO> query = new LambdaQueryWrapper<>();
        query.eq(BinOrderDetailDO::getCalcId, calcId).isNull(BinOrderDetailDO::getDlvDate).gt(BinOrderDetailDO::getPoQty, 0);
        List<BinOrderDetailDO> list = binOrderDetailMapper.selectList(query);

        for (BinOrderDetailDO info : list) {
//            String key = "calcdlv:" + info.getWarehouseCode() + info.getModelNo();
//            String dlvdateinfo = (String) redisUtil.get(key);
            Date lastDlvDate = getLastPurchaseDlvDate(info.getModelNo(), info.getWarehouseCode());
            if (lastDlvDate != null) {

                Date dlvDate = null;
                if ("VN".equalsIgnoreCase(info.getMainSupplierCode())) {
                    lastDlvDate = DateUtil.addDay(DateUtil.getLastMonthFirstDate(lastDlvDate), 9);
                } else {
                    dlvDate = DateUtil.addDay(lastDlvDate, 28);
                }

                LambdaUpdateWrapper<BinOrderDetailDO> updateWrapper = new LambdaUpdateWrapper<>();

                updateWrapper.eq(BinOrderDetailDO::getCalcId, calcId)
                        .eq(BinOrderDetailDO::getWarehouseCode, info.getWarehouseCode())
                        .eq(BinOrderDetailDO::getModelNo, info.getModelNo())
                        .set(BinOrderDetailDO::getDlvDate, dlvDate)
                        .set(BinOrderDetailDO::getUpdateTime, DateUtil.getNow())
                        .set(BinOrderDetailDO::getUpdateUser, SMCApp.getLoginAuthDtoForSysUser().getUserNo());
                binOrderDetailMapper.update(new BinOrderDetailDO(), updateWrapper);  //更新交货期
            }
        }
    }

    @Override
    @Async
    public void addModelExpFreqCsv() {

        SMCApp.setGrantType(GlobalConstant.GrantType.CLIANT_CREDENTIALS);
        
        long startTime = System.currentTimeMillis();
        log.info("========== 开始优化导出 ModelExpFreq ==========");
        
        // 1. 获取库存类型列表
        List<ModelExpFreqDO> typelist = modelExpFreqMapper.getExpFreqStockType();

        // 2. 补充默认配置项
        addDefaultStockTypes(typelist);
        
        // 3. 获取每个类型的数量并分类
        Map<String, Long> typeCountMap = getTypeCountMap(typelist);
        
        // 4. 按数据量降序排序
        typelist.sort((a, b) -> {
            long countA = typeCountMap.getOrDefault(buildTypeKey(a), 0L);
            long countB = typeCountMap.getOrDefault(buildTypeKey(b), 0L);
            return Long.compare(countB, countA);
        });
        
        // 5. 分类:大类型 vs 小类型(阈值100万)
        final long LARGE_THRESHOLD = 1000000L;
        List<ModelExpFreqDO> largeTypes = new ArrayList<>();
        List<ModelExpFreqDO> smallTypes = new ArrayList<>();
        
        for (ModelExpFreqDO expDO : typelist) {
            long count = typeCountMap.getOrDefault(buildTypeKey(expDO), 0L);
            if (count >= LARGE_THRESHOLD) {
                largeTypes.add(expDO);
                log.info("大类型: {} - {} 条", buildFileName(expDO), count);
            } else {
                smallTypes.add(expDO);
                log.info("小类型: {} - {} 条", buildFileName(expDO), count);
            }
        }
        
        // 6. 先处理大类型(串行,使用TOP分页)
        log.info("========== 开始处理 {} 个大类型 ==========", largeTypes.size());
        for (ModelExpFreqDO expDO : largeTypes) {
            try {
                exportLargeTypeByTopPaging(expDO);
            } catch (Exception e) {
                log.error("大类型导出失败: {}", buildFileName(expDO), e);
            }
        }
        
        // 7. 并行处理小类型
        log.info("========== 开始处理 {} 个小类型 ==========", smallTypes.size());
        if (!smallTypes.isEmpty()) {
            exportSmallTypesInParallel(smallTypes);
        }
        
        long cost = System.currentTimeMillis() - startTime;
        log.info("========== 优化导出完成,总耗时 {} 分 {} 秒 ==========", cost / 60000, (cost % 60000) / 1000);
    }

    /**
     * 获取每个类型的数量
     */
    private Map<String, Long> getTypeCountMap(List<ModelExpFreqDO> types) {
        Map<String, Long> countMap = new HashMap<>();
        for (ModelExpFreqDO type : types) {
            long count = modelExpFreqMapper.countByType(
                type.getStockType(), 
                type.getStockCode(), 
                type.getModelType()
            );
            countMap.put(buildTypeKey(type), count);
        }
        return countMap;
    }

    /**
     * 构建类型唯一标识
     */
    private String buildTypeKey(ModelExpFreqDO type) {
        if (type.getStockType() != null && type.getStockType() > 2) {
            return type.getStockType() + "_" + type.getModelType();
        } else {
            return type.getStockCode() + "_" + type.getModelType();
        }
    }

    /**
     * 大类型导出(使用TOP分页,高效)
     */
    private String exportLargeTypeByTopPaging(ModelExpFreqDO expDO) {
        String fileName = buildFileName(expDO);
        //todo test
        String zipPath = serverPath + "Bin" + File.separator + fileName + ".zip";
        //String zipPath = "D:"+serverPath + "Bin" + File.separator + fileName + ".zip";

        log.info("========== 开始导出大类型: {} ==========", fileName);
        long startTime = System.currentTimeMillis();
        
        // 1. 获取ID范围
        Map<String, Object> rangeInfo = modelExpFreqMapper.getIdRange(
            expDO.getStockType(), expDO.getStockCode(), expDO.getModelType()
        );
        
        if (rangeInfo == null || rangeInfo.get("totalCount") == null) {
            log.warn("{} 无数据,跳过导出", fileName);
            return null;
        }
        
        long minId = rangeInfo.get("minId") != null ? ((Number) rangeInfo.get("minId")).longValue() : 0L;
        long maxId = rangeInfo.get("maxId") != null ? ((Number) rangeInfo.get("maxId")).longValue() : 0L;
        long totalCount = ((Number) rangeInfo.get("totalCount")).longValue();
        
        if (totalCount == 0) {
            log.warn("{} 无数据,跳过导出", fileName);
            return null;
        }
        
        log.info("{} 数据范围: ID {} ~ {},共 {} 条", fileName, minId, maxId, totalCount);
        
        // 2. 确保目录存在
        File zipFile = new File(zipPath);
        File parentDir = zipFile.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs();
        }
                
        // 3. 创建ZIP输出流(直接写入ZIP,边写边压缩)
        // 针对300-500万条数据优化:三层缓冲区统一为1MB,匹配每次写入量
        try (
            FileOutputStream fos = new FileOutputStream(zipPath);
            BufferedOutputStream bos = new BufferedOutputStream(fos, 1048576); // 1MB缓冲,匹配batchWriteSize
            ZipOutputStream zos = new ZipOutputStream(bos);
            BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(zos, "GB2312"), 1048576) // 1MB缓冲,避免多次刷新
        ) {
            ZipEntry entry = new ZipEntry(fileName + ".csv");
            zos.putNextEntry(entry);
            
            // 写入表头
            writeCsvHeaderDirect(writer, expDO);
            
            // 4. 分批导出(简化为单层循环 + 批量写入优化)
            long exportedCount = 0;
            long currentId = minId;
            int batchSize = 100000;
            // 针对300-500万条大数据量优化:每1000行批量写入一次,减少IO次数
            int batchWriteSize = 1000;
            // 单行约900字节,1000行约900KB,初始容量设为1MB避免扩容
            StringBuilder batchBuffer = new StringBuilder(1048576);
            
            while (currentId <= maxId) {
                long endId = Math.min(currentId + batchSize - 1, maxId);
                
                // TOP分页查询
                List<ModelExpFreqDO> batch = modelExpFreqMapper.selectByIdRange(
                    expDO.getStockType(),
                    expDO.getStockCode(),
                    expDO.getModelType(),
                    currentId,
                    endId,
                    batchSize
                );
                
                if (batch.isEmpty()) {
                    currentId = endId + 1;
                    continue;
                }
                
                // 批量构建CSV行
                for (ModelExpFreqDO info : batch) {
                    convertToCsvRow(batchBuffer, info, expDO);
                    exportedCount++;
                    
                    // 达到批量写入阈值时,一次性写入
                    if (exportedCount % batchWriteSize == 0) {
                        writer.write(batchBuffer.toString());
                        batchBuffer.setLength(0); // 清空缓冲区
                    }
                }
                
                // 写入剩余数据
                if (batchBuffer.length() > 0) {
                    writer.write(batchBuffer.toString());
                    batchBuffer.setLength(0);
                }
                
                currentId = batch.get(batch.size() - 1).getId() + 1;
                
                // 进度日志(每10万条打印一次)
                if (exportedCount % 100000 == 0) {
                    long percent = 100 * exportedCount / totalCount;
                    log.info("{} 进度: {}/{} 条 ({}%)", fileName, exportedCount, totalCount, percent);
                }
            }
            
            writer.flush();
            zos.closeEntry();
            zos.close();
            long cost = System.currentTimeMillis() - startTime;
            log.info("========== {} 导出完成,共 {} 条,耗时 {} 分 {} 秒 ==========", 
                fileName, exportedCount, cost / 60000, (cost % 60000) / 1000);
            
        } catch (Exception e) {
            log.error("导出失败: {}", fileName, e);
            throw new RuntimeException("导出失败: " + e.getMessage(), e);
        }
        return zipPath;
    }

    /**
     * 小类型并行导出
     */
    private void exportSmallTypesInParallel(List<ModelExpFreqDO> smallTypes) {
        // 降低并行度,避免占用过多数据库连接(每个线程持有1个连接)
        int parallelism = Math.min(2, smallTypes.size());
        ForkJoinPool pool = new ForkJoinPool(parallelism);
        try {
            pool.submit(() -> 
                smallTypes.parallelStream().forEach(this::exportSmallType)
            ).get();
        } catch (Exception e) {
            log.error("并行导出小类型失败", e);
        } finally {
            pool.shutdown();
        }
    }

    /**
     * 小类型导出(统一使用TOP分页+批量写入,与大类型保持一致)
     */
    private void exportSmallType(ModelExpFreqDO expDO) {
        String fileName = buildFileName(expDO);
        //todo test
        String zipPath = serverPath + "Bin" + File.separator + fileName + ".zip";
        //String zipPath = "D:"+serverPath + "Bin" + File.separator + fileName + ".zip";
        log.info("开始小类型导出: {}", fileName);
        long startTime = System.currentTimeMillis();
        
        // 1. 获取ID范围
        Map<String, Object> rangeInfo = modelExpFreqMapper.getIdRange(
            expDO.getStockType(), expDO.getStockCode(), expDO.getModelType()
        );
        
        if (rangeInfo == null || rangeInfo.get("totalCount") == null) {
            log.warn("{} 无数据,跳过导出", fileName);
            return;
        }
        
        long minId = rangeInfo.get("minId") != null ? ((Number) rangeInfo.get("minId")).longValue() : 0L;
        long maxId = rangeInfo.get("maxId") != null ? ((Number) rangeInfo.get("maxId")).longValue() : 0L;
        long totalCount = ((Number) rangeInfo.get("totalCount")).longValue();
        
        if (totalCount == 0) {
            log.warn("{} 无数据,跳过导出", fileName);
            return;
        }
        
        // 2. 确保目录存在
        File zipFile = new File(zipPath);
        File parentDir = zipFile.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs();
        }
        
        // 3. 创建ZIP输出流(统一使用1MB缓冲区)
        try (
            FileOutputStream fos = new FileOutputStream(zipPath);
            BufferedOutputStream bos = new BufferedOutputStream(fos, 1048576); // 1MB缓冲
            ZipOutputStream zos = new ZipOutputStream(bos);
            BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(zos, "GB2312"), 1048576) // 1MB缓冲
        ) {
            ZipEntry entry = new ZipEntry(fileName + ".csv");
            zos.putNextEntry(entry);
            writeCsvHeaderDirect(writer, expDO);
            
            // 4. 分批导出(使用TOP分页+批量写入,与大类型一致)
            long exportedCount = 0;
            long currentId = minId;
            int batchSize = 10000;
            // 小类型数据量小,使用500行/批即可
            int batchWriteSize = totalCount > 100000 ? 1000 : 500;
            StringBuilder batchBuffer = new StringBuilder(1048576); // 1MB缓冲区
            
            while (currentId <= maxId) {
                long endId = Math.min(currentId + batchSize - 1, maxId);
                
                // TOP分页查询
                List<ModelExpFreqDO> batch = modelExpFreqMapper.selectByIdRange(
                    expDO.getStockType(),
                    expDO.getStockCode(),
                    expDO.getModelType(),
                    currentId,
                    endId,
                    batchSize
                );
                
                if (batch.isEmpty()) {
                    currentId = endId + 1;
                    continue;
                }
                
                // 批量构建CSV行
                for (ModelExpFreqDO info : batch) {
                    convertToCsvRow(batchBuffer, info, expDO);
                    exportedCount++;
                    
                    // 达到批量写入阈值时,一次性写入
                    if (exportedCount % batchWriteSize == 0) {
                        writer.write(batchBuffer.toString());
                        batchBuffer.setLength(0);
                    }
                }
                
                // 写入剩余数据
                if (batchBuffer.length() > 0) {
                    writer.write(batchBuffer.toString());
                    batchBuffer.setLength(0);
                }
                
                currentId = batch.get(batch.size() - 1).getId() + 1;
            }
            
            // 显式刷新并关闭资源,确保数据完整写入
            writer.flush();
            zos.closeEntry();
            zos.close();
            
            long cost = System.currentTimeMillis() - startTime;
            log.info("{} 导出完成,共 {} 条,耗时 {} 秒", fileName, exportedCount, cost / 1000);
            
        } catch (Exception e) {
            log.error("导出失败: {}", fileName, e);
        }
    }

    /**
     * 添加默认库存类型配置
     */
    private void addDefaultStockTypes(List<ModelExpFreqDO> typelist) {
        int[][] defaultConfigs = {{3, 1}, {3, 2}, {4, 1}, {4, 2}};
        for (int[] config : defaultConfigs) {
            ModelExpFreqDO info = new ModelExpFreqDO();
            info.setStockType(config[0]);
            info.setModelType(String.valueOf(config[1]));
            typelist.add(info);
        }
    }


    /**
     * 构建文件名
     */
    private String buildFileName(ModelExpFreqDO expDO) {
        String stockPart = StringUtils.isBlank(expDO.getStockCode()) 
            ? expDO.getStockType().toString() 
            : expDO.getStockCode().toUpperCase();
        return stockPart + expDO.getModelType() + DateUtil.getFormatDate(DateUtil.getNow(), "yyyy-MM");
    }


    /**
     * 写入CSV表头(直接写入ZIP流)
     */
    private void writeCsvHeaderDirect(Writer writer, ModelExpFreqDO expDO) throws IOException {
        String delimiter = ",";
        
        String[] baseHeaders = {
            "型号", "仓库", "统计类型", "型号类别", "等级", "型号分类",
            "产品类别(1-标准品 2-简易特注品 3-特注品 4-集成型号 5-维修品 6-阀岛型号)",
            "产品区分(1-收敛品2-贩卖限制品4-Shikomi8-可拆分",
            "最后下单月份", "产品系列", "供应商", "原产地", "E价", "ECode",
            "设定平均", "设定级别", "变动系数",
            "移动平均变量1", "移动平均变量2", "移动平均变量3",
            "最新月均"
        };
        
        for (String header : baseHeaders) {
            writer.write(header);
            writer.write(delimiter);
        }
        
        // 条件字段
        if (PublicUtil.isNotEmpty(expDO.getStockType()) && expDO.getStockType() > 2) {
            writer.write("部门");
            writer.write(delimiter);
        }
        
        // 8/12/24/36月统计数据
        int[] periods = {8, 12, 24, 36};
        String[] periodHeaders = {
            "下单月数", "客户数", "下单数量", "月平均数量", "最多下单客户",
            "营业所代码", "最多客户的比例", "最多下单客户数量", "订单项数"
        };
        
        for (int period : periods) {
            for (String header : periodHeaders) {
                writer.write(header + period);
                writer.write(delimiter);
            }
        }
        
        // M1-M36
        for (int i = 1; i <= 36; i++) {
            writer.write("M" + i);
            //本行的最后一个逗号不写入
            if (i < 36) {
                writer.write(delimiter);
            }
        }
        
        writer.write("\n");
    }

    /**
     * 写入CSV数据行(直接写入ZIP流)
     */
    /**
     * 将单条数据转换为CSV行(使用StringBuilder批量构建)
     * @param buffer 缓冲区,用于累积多行数据
     * @param info 数据对象
     * @param expDO 配置对象
     */
    private void convertToCsvRow(StringBuilder buffer, ModelExpFreqDO info, ModelExpFreqDO expDO) {
        String delimiter = ",";
        
        buffer.append('"').append(info.getModelNo() == null ? "" : info.getModelNo()).append('"');
        buffer.append(delimiter);
        buffer.append(info.getStockCode() == null ? "" : info.getStockCode());
        buffer.append(delimiter);
        buffer.append(info.getStockType() == null ? "0" : info.getStockType().toString());
        buffer.append(delimiter);
        buffer.append("1".equals(info.getModelType()) ? "基础型号" : "订货型号");
        buffer.append(delimiter);
        buffer.append(info.getClassCode() == null ? "" : info.getClassCode());
        buffer.append(delimiter);
        buffer.append(info.getModelClass() == null ? "" : info.getModelClass());
        buffer.append(delimiter);
        buffer.append(info.getDesignType() == null ? "" : info.getDesignType());
        buffer.append(delimiter);
        buffer.append(info.getProductType() == null ? "" : info.getProductType().toString());
        buffer.append(delimiter);
        buffer.append(info.getLastOrdMonth() == null ? "" : DateUtil.getFormatDate(info.getLastOrdMonth(), "yyyy-MM-dd"));
        buffer.append(delimiter);
        buffer.append(info.getProductSeries() == null ? "" : info.getProductSeries());
        buffer.append(delimiter);
        buffer.append(info.getMainOrigin() == null ? "" : info.getMainOrigin());
        buffer.append(delimiter);
        buffer.append(info.getSecondOrigin() == null ? "" : info.getSecondOrigin());
        buffer.append(delimiter);
        buffer.append(info.getEprice() == null ? "" : info.getEprice().toString());
        buffer.append(delimiter);
        buffer.append(info.getEcode() == null ? "" : info.getEcode());
        buffer.append(delimiter);
        buffer.append(info.getSetMean() == null ? "" : info.getSetMean().toString());
        buffer.append(delimiter);
        buffer.append(info.getSetClassCode() == null ? "" : info.getSetClassCode());
        buffer.append(delimiter);
        buffer.append(info.getVariation() == null ? "" : info.getVariation().toString());
        buffer.append(delimiter);
        buffer.append(info.getMoveRate1() == null ? "" : info.getMoveRate1().toString());
        buffer.append(delimiter);
        buffer.append(info.getMoveRate2() == null ? "" : info.getMoveRate2().toString());
        buffer.append(delimiter);
        buffer.append(info.getMoveRate3() == null ? "" : info.getMoveRate3().toString());
        buffer.append(delimiter);
        buffer.append(info.getSetAvgQty() == null ? "" : info.getSetAvgQty().toString());
        buffer.append(delimiter);
        
        // 条件字段
        if (PublicUtil.isNotEmpty(expDO.getStockType()) && expDO.getStockType() > 2) {
            buffer.append(info.getDeptNo() == null ? "" : info.getDeptNo().toString());
            buffer.append(delimiter);
        }
        
        // 8/12/24/36月统计数据
        appendPeriodData(buffer, delimiter, info, 8);
        appendPeriodData(buffer, delimiter, info, 12);
        appendPeriodData(buffer, delimiter, info, 24);
        appendPeriodData(buffer, delimiter, info, 36);
        
        // M1-M36
        Integer[] monthData = {
            info.getM1(), info.getM2(), info.getM3(), info.getM4(), info.getM5(), info.getM6(),
            info.getM7(), info.getM8(), info.getM9(), info.getM10(), info.getM11(), info.getM12(),
            info.getM13(), info.getM14(), info.getM15(), info.getM16(), info.getM17(), info.getM18(),
            info.getM19(), info.getM20(), info.getM21(), info.getM22(), info.getM23(), info.getM24(),
            info.getM25(), info.getM26(), info.getM27(), info.getM28(), info.getM29(), info.getM30(),
            info.getM31(), info.getM32(), info.getM33(), info.getM34(), info.getM35(), info.getM36()
        };
        
        for (Integer m : monthData) {
            buffer.append(m == null ? "" : m.toString());
            buffer.append(delimiter);
        }
        //去掉每行行尾的分隔字符
        buffer.deleteCharAt(buffer.length() - 1);
        buffer.append("\n");
    }

    /**
     * 追加周期统计数据到StringBuilder
     */
    private void appendPeriodData(StringBuilder buffer, String delimiter, ModelExpFreqDO info, int period) {
        switch (period) {
            case 8:
                buffer.append(info.getMonthsOf8() == null ? "" : info.getMonthsOf8().toString());
                buffer.append(delimiter);
                buffer.append(info.getCustomersOf8() == null ? "" : info.getCustomersOf8().toString());
                buffer.append(delimiter);
                buffer.append(info.getQtyOf8() == null ? "" : info.getQtyOf8().toString());
                buffer.append(delimiter);
                buffer.append(info.getAvgQtyOf8() == null ? "" : info.getAvgQtyOf8().toString());
                buffer.append(delimiter);
                buffer.append(info.getMaxCustomerOf8() == null ? "" : info.getMaxCustomerOf8());
                buffer.append(delimiter);
                buffer.append(info.getMaxCustomerDeptOf8() == null ? "" : info.getMaxCustomerDeptOf8());
                buffer.append(delimiter);
                buffer.append(info.getMaxCustomerRateOf8() == null ? "" : info.getMaxCustomerRateOf8().toString());
                buffer.append(delimiter);
                buffer.append(info.getMaxCustomerQtyOf8() == null ? "" : info.getMaxCustomerQtyOf8().toString());
                buffer.append(delimiter);
                buffer.append(info.getOrdersOf8() == null ? "" : info.getOrdersOf8().toString());
                break;
            case 12:
                buffer.append(info.getMonthsOf12() == null ? "" : info.getMonthsOf12().toString());
                buffer.append(delimiter);
                buffer.append(info.getCustomersOf12() == null ? "" : info.getCustomersOf12().toString());
                buffer.append(delimiter);
                buffer.append(info.getQtyOf12() == null ? "" : info.getQtyOf12().toString());
                buffer.append(delimiter);
                buffer.append(info.getAvgQtyOf12() == null ? "" : info.getAvgQtyOf12().toString());
                buffer.append(delimiter);
                buffer.append(info.getMaxCustomerOf12() == null ? "" : info.getMaxCustomerOf12());
                buffer.append(delimiter);
                buffer.append(info.getMaxCustomerDeptOf12() == null ? "" : info.getMaxCustomerDeptOf12());
                buffer.append(delimiter);
                buffer.append(info.getMaxCustomerRateOf12() == null ? "" : info.getMaxCustomerRateOf12().toString());
                buffer.append(delimiter);
                buffer.append(info.getMaxCustomerQtyOf12() == null ? "" : info.getMaxCustomerQtyOf12().toString());
                buffer.append(delimiter);
                buffer.append(info.getOrdersOf12() == null ? "" : info.getOrdersOf12().toString());
                break;
            case 24:
                buffer.append(info.getMonthsOf24() == null ? "" : info.getMonthsOf24().toString());
                buffer.append(delimiter);
                buffer.append(info.getCustomersOf24() == null ? "" : info.getCustomersOf24().toString());
                buffer.append(delimiter);
                buffer.append(info.getQtyOf24() == null ? "" : info.getQtyOf24().toString());
                buffer.append(delimiter);
                buffer.append(info.getAvgQtyOf24() == null ? "" : info.getAvgQtyOf24().toString());
                buffer.append(delimiter);
                buffer.append(info.getMaxCustomerOf24() == null ? "" : info.getMaxCustomerOf24());
                buffer.append(delimiter);
                buffer.append(info.getMaxCustomerDeptOf24() == null ? "" : info.getMaxCustomerDeptOf24());
                buffer.append(delimiter);
                buffer.append(info.getMaxCustomerRateOf24() == null ? "" : info.getMaxCustomerRateOf24().toString());
                buffer.append(delimiter);
                buffer.append(info.getMaxCustomerQtyOf24() == null ? "" : info.getMaxCustomerQtyOf24().toString());
                buffer.append(delimiter);
                buffer.append(info.getOrdersOf24() == null ? "" : info.getOrdersOf24().toString());
                break;
            case 36:
                buffer.append(info.getMonthsOf36() == null ? "" : info.getMonthsOf36().toString());
                buffer.append(delimiter);
                buffer.append(info.getCustomersOf36() == null ? "" : info.getCustomersOf36().toString());
                buffer.append(delimiter);
                buffer.append(info.getQtyOf36() == null ? "" : info.getQtyOf36().toString());
                buffer.append(delimiter);
                buffer.append(info.getAvgQtyOf36() == null ? "" : info.getAvgQtyOf36().toString());
                buffer.append(delimiter);
                buffer.append(info.getMaxCustomerOf36() == null ? "" : info.getMaxCustomerOf36());
                buffer.append(delimiter);
                buffer.append(info.getMaxCustomerDeptOf36() == null ? "" : info.getMaxCustomerDeptOf36());
                buffer.append(delimiter);
                buffer.append(info.getMaxCustomerRateOf36() == null ? "" : info.getMaxCustomerRateOf36().toString());
                buffer.append(delimiter);
                buffer.append(info.getMaxCustomerQtyOf36() == null ? "" : info.getMaxCustomerQtyOf36().toString());
                buffer.append(delimiter);
                buffer.append(info.getOrdersOf36() == null ? "" : info.getOrdersOf36().toString());
                break;
        }
        buffer.append(delimiter);
    }

    /**
     * 写入CSV行(直接写入ZIP流,兼容旧代码)
     * @deprecated 推荐使用 convertToCsvRow + 批量写入方式
     */
    @Deprecated
    private void writeCsvRowDirect(Writer writer, ModelExpFreqDO info, ModelExpFreqDO expDO) throws IOException {
        String delimiter = ",";
        
        writer.write('"' + (info.getModelNo() == null ? "" : info.getModelNo()) + '"');
        writer.write(delimiter);
        writer.write(info.getStockCode() == null ? "" : info.getStockCode());
        writer.write(delimiter);
        writer.write(info.getStockType() == null ? "0" : info.getStockType().toString());
        writer.write(delimiter);
        writer.write(info.getModelType().equals("1") ? "基础型号" : "订货型号");
        writer.write(delimiter);
        writer.write(info.getClassCode() == null ? "" : info.getClassCode());
        writer.write(delimiter);
        writer.write(info.getModelClass() == null ? "" : info.getModelClass());
        writer.write(delimiter);
        writer.write(info.getDesignType() == null ? "" : info.getDesignType());
        writer.write(delimiter);
        writer.write(info.getProductType() == null ? "" : info.getProductType().toString());
        writer.write(delimiter);
        writer.write(info.getLastOrdMonth() == null ? "" : DateUtil.getFormatDate(info.getLastOrdMonth(), "yyyy-MM-dd"));
        writer.write(delimiter);
        writer.write(info.getProductSeries() == null ? "" : info.getProductSeries());
        writer.write(delimiter);
        writer.write(info.getMainOrigin() == null ? "" : info.getMainOrigin());
        writer.write(delimiter);
        writer.write(info.getSecondOrigin() == null ? "" : info.getSecondOrigin());
        writer.write(delimiter);
        writer.write(info.getEprice() == null ? "" : info.getEprice().toString());
        writer.write(delimiter);
        writer.write(info.getEcode() == null ? "" : info.getEcode());
        writer.write(delimiter);
        writer.write(info.getSetMean() == null ? "" : info.getSetMean().toString());
        writer.write(delimiter);
        writer.write(info.getSetClassCode() == null ? "" : info.getSetClassCode());
        writer.write(delimiter);
        writer.write(info.getVariation() == null ? "" : info.getVariation().toString());
        writer.write(delimiter);
        writer.write(info.getMoveRate1() == null ? "" : info.getMoveRate1().toString());
        writer.write(delimiter);
        writer.write(info.getMoveRate2() == null ? "" : info.getMoveRate2().toString());
        writer.write(delimiter);
        writer.write(info.getMoveRate3() == null ? "" : info.getMoveRate3().toString());
        writer.write(delimiter);
        writer.write(info.getSetAvgQty() == null ? "" : info.getSetAvgQty().toString());
        writer.write(delimiter);
        
        // 条件字段
        if (PublicUtil.isNotEmpty(expDO.getStockType()) && expDO.getStockType() > 2) {
            writer.write(info.getDeptNo() == null ? "" : info.getDeptNo().toString());
            writer.write(delimiter);
        }
        
        // 8/12/24/36月统计数据
        writePeriodDataDirect(writer, delimiter, info, 8);
        writePeriodDataDirect(writer, delimiter, info, 12);
        writePeriodDataDirect(writer, delimiter, info, 24);
        writePeriodDataDirect(writer, delimiter, info, 36);
        
        // M1-M36
        Integer[] monthData = {
            info.getM1(), info.getM2(), info.getM3(), info.getM4(), info.getM5(), info.getM6(),
            info.getM7(), info.getM8(), info.getM9(), info.getM10(), info.getM11(), info.getM12(),
            info.getM13(), info.getM14(), info.getM15(), info.getM16(), info.getM17(), info.getM18(),
            info.getM19(), info.getM20(), info.getM21(), info.getM22(), info.getM23(), info.getM24(),
            info.getM25(), info.getM26(), info.getM27(), info.getM28(), info.getM29(), info.getM30(),
            info.getM31(), info.getM32(), info.getM33(), info.getM34(), info.getM35(), info.getM36()
        };

        for (int i = 0; i < monthData.length; i++) {
            Integer m = monthData[i];
            writer.write(m == null ? "" : m.toString());
            //本行的最后一个逗号不写入
            if (i < monthData.length - 1) {
                writer.write(delimiter);
            }

        }
        
        writer.write("\n");
    }

    /**
     * 写入周期统计数据(直接写入ZIP流)
     */
    private void writePeriodDataDirect(Writer writer, String delimiter, ModelExpFreqDO info, int period) throws IOException {
        switch (period) {
            case 8:
                writer.write(info.getMonthsOf8() == null ? "" : info.getMonthsOf8().toString());
                writer.write(delimiter);
                writer.write(info.getCustomersOf8() == null ? "" : info.getCustomersOf8().toString());
                writer.write(delimiter);
                writer.write(info.getQtyOf8() == null ? "" : info.getQtyOf8().toString());
                writer.write(delimiter);
                writer.write(info.getAvgQtyOf8() == null ? "" : info.getAvgQtyOf8().toString());
                writer.write(delimiter);
                writer.write(info.getMaxCustomerOf8() == null ? "" : info.getMaxCustomerOf8());
                writer.write(delimiter);
                writer.write(info.getMaxCustomerDeptOf8() == null ? "" : info.getMaxCustomerDeptOf8());
                writer.write(delimiter);
                writer.write(info.getMaxCustomerRateOf8() == null ? "" : info.getMaxCustomerRateOf8().toString());
                writer.write(delimiter);
                writer.write(info.getMaxCustomerQtyOf8() == null ? "" : info.getMaxCustomerQtyOf8().toString());
                writer.write(delimiter);
                writer.write(info.getOrdersOf8() == null ? "" : info.getOrdersOf8().toString());
                break;
            case 12:
                writer.write(info.getMonthsOf12() == null ? "" : info.getMonthsOf12().toString());
                writer.write(delimiter);
                writer.write(info.getCustomersOf12() == null ? "" : info.getCustomersOf12().toString());
                writer.write(delimiter);
                writer.write(info.getQtyOf12() == null ? "" : info.getQtyOf12().toString());
                writer.write(delimiter);
                writer.write(info.getAvgQtyOf12() == null ? "" : info.getAvgQtyOf12().toString());
                writer.write(delimiter);
                writer.write(info.getMaxCustomerOf12() == null ? "" : info.getMaxCustomerOf12());
                writer.write(delimiter);
                writer.write(info.getMaxCustomerDeptOf12() == null ? "" : info.getMaxCustomerDeptOf12());
                writer.write(delimiter);
                writer.write(info.getMaxCustomerRateOf12() == null ? "" : info.getMaxCustomerRateOf12().toString());
                writer.write(delimiter);
                writer.write(info.getMaxCustomerQtyOf12() == null ? "" : info.getMaxCustomerQtyOf12().toString());
                writer.write(delimiter);
                writer.write(info.getOrdersOf12() == null ? "" : info.getOrdersOf12().toString());
                break;
            case 24:
                writer.write(info.getMonthsOf24() == null ? "" : info.getMonthsOf24().toString());
                writer.write(delimiter);
                writer.write(info.getCustomersOf24() == null ? "" : info.getCustomersOf24().toString());
                writer.write(delimiter);
                writer.write(info.getQtyOf24() == null ? "" : info.getQtyOf24().toString());
                writer.write(delimiter);
                writer.write(info.getAvgQtyOf24() == null ? "" : info.getAvgQtyOf24().toString());
                writer.write(delimiter);
                writer.write(info.getMaxCustomerOf24() == null ? "" : info.getMaxCustomerOf24());
                writer.write(delimiter);
                writer.write(info.getMaxCustomerDeptOf24() == null ? "" : info.getMaxCustomerDeptOf24());
                writer.write(delimiter);
                writer.write(info.getMaxCustomerRateOf24() == null ? "" : info.getMaxCustomerRateOf24().toString());
                writer.write(delimiter);
                writer.write(info.getMaxCustomerQtyOf24() == null ? "" : info.getMaxCustomerQtyOf24().toString());
                writer.write(delimiter);
                writer.write(info.getOrdersOf24() == null ? "" : info.getOrdersOf24().toString());
                break;
            case 36:
                writer.write(info.getMonthsOf36() == null ? "" : info.getMonthsOf36().toString());
                writer.write(delimiter);
                writer.write(info.getCustomersOf36() == null ? "" : info.getCustomersOf36().toString());
                writer.write(delimiter);
                writer.write(info.getQtyOf36() == null ? "" : info.getQtyOf36().toString());
                writer.write(delimiter);
                writer.write(info.getAvgQtyOf36() == null ? "" : info.getAvgQtyOf36().toString());
                writer.write(delimiter);
                writer.write(info.getMaxCustomerOf36() == null ? "" : info.getMaxCustomerOf36());
                writer.write(delimiter);
                writer.write(info.getMaxCustomerDeptOf36() == null ? "" : info.getMaxCustomerDeptOf36());
                writer.write(delimiter);
                writer.write(info.getMaxCustomerRateOf36() == null ? "" : info.getMaxCustomerRateOf36().toString());
                writer.write(delimiter);
                writer.write(info.getMaxCustomerQtyOf36() == null ? "" : info.getMaxCustomerQtyOf36().toString());
                writer.write(delimiter);
                writer.write(info.getOrdersOf36() == null ? "" : info.getOrdersOf36().toString());
                break;
        }
        writer.write(delimiter);
    }

    /**
     * 写入CSV表头
     */
    private void writeCsvHeader(BufferedWriter bw, String delimiter, ModelExpFreqDO expDO) throws IOException {
        String[] baseHeaders = {
            "型号", "仓库", "统计类型", "型号类别", "等级", "型号分类",
            "产品类别(1-标准品 2-简易特注品 3-特注品 4-集成型号 5-维修品 6-阀岛型号)",
            "产品区分(1-收敛品2-贩卖限制品4-Shikomi8-可拆分",
            "最后下单月份", "产品系列", "供应商", "原产地", "E价", "ECode",
            "设定平均", "设定级别", "变动系数",
            "移动平均变量1", "移动平均变量2", "移动平均变量3",
            "最新月均"
        };
        
        writeHeaders(bw, delimiter, baseHeaders);
        
        // 条件字段
        if (PublicUtil.isNotEmpty(expDO.getStockType()) && expDO.getStockType() > 2) {
            bw.write("部门");
            bw.write(delimiter);
        }
        
        // 8/12/24/36月统计数据
        int[] periods = {8, 12, 24, 36};
        String[] periodHeaders = {
            "下单月数", "客户数", "下单数量", "月平均数量", "最多下单客户",
            "营业所代码", "最多客户的比例", "最多下单客户数量", "订单项数"
        };
        
        for (int period : periods) {
            for (String header : periodHeaders) {
                bw.write(header + period);
                bw.write(delimiter);
            }
        }
        
        // M1-M36
        for (int i = 1; i <= 36; i++) {
            bw.write("M" + i);
            bw.write(delimiter);
        }
        
        bw.newLine();
    }

    /**
     * 批量写入表头数组
     */
    private void writeHeaders(BufferedWriter bw, String delimiter, String[] headers) throws IOException {
        for (int i = 0; i < headers.length; i++) {
            bw.write(headers[i]);
            bw.write(delimiter);
        }
    }

    /**
     * 写入CSV数据行
     */
    private void writeCsvRow(BufferedWriter bw, String delimiter, ModelExpFreqDO info, ModelExpFreqDO expDO) throws IOException {
        bw.write('\"' + info.getModelNo() + '\"');
        bw.write(delimiter);
        bw.write(info.getStockCode() == null ? "" : info.getStockCode());
        bw.write(delimiter);
        bw.write(info.getStockType() == null ? "0" : info.getStockType().toString());
        bw.write(delimiter);
        bw.write(info.getModelType().equals("1") ? "基础型号" : "订货型号");
        bw.write(delimiter);
        bw.write(info.getClassCode() == null ? "" : info.getClassCode());
        bw.write(delimiter);
        bw.write(info.getModelClass() == null ? "" : info.getModelClass());
        bw.write(delimiter);
        bw.write(info.getDesignType() == null ? "" : info.getDesignType());
        bw.write(delimiter);
        bw.write(info.getProductType() == null ? "" : info.getProductType().toString());
        bw.write(delimiter);
        bw.write(info.getLastOrdMonth() == null ? "" : DateUtil.getFormatDate(info.getLastOrdMonth(), "yyyy-MM-dd"));
        bw.write(delimiter);
        bw.write(info.getProductSeries() == null ? "" : info.getProductSeries());
        bw.write(delimiter);
        bw.write(info.getMainOrigin() == null ? "" : info.getMainOrigin());
        bw.write(delimiter);
        bw.write(info.getSecondOrigin() == null ? "" : info.getSecondOrigin());
        bw.write(delimiter);
        bw.write(info.getEprice() == null ? "" : info.getEprice().toString());
        bw.write(delimiter);
        bw.write(info.getEcode() == null ? "" : info.getEcode());
        bw.write(delimiter);
        bw.write(info.getSetMean() == null ? "" : info.getSetMean().toString());
        bw.write(delimiter);
        bw.write(info.getSetClassCode() == null ? "" : info.getSetClassCode());
        bw.write(delimiter);
        bw.write(info.getVariation() == null ? "" : info.getVariation().toString());
        bw.write(delimiter);
        bw.write(info.getMoveRate1() == null ? "" : info.getMoveRate1().toString());
        bw.write(delimiter);
        bw.write(info.getMoveRate2() == null ? "" : info.getMoveRate2().toString());
        bw.write(delimiter);
        bw.write(info.getMoveRate3() == null ? "" : info.getMoveRate3().toString());
        bw.write(delimiter);
        bw.write(info.getSetAvgQty() == null ? "" : info.getSetAvgQty().toString());
        bw.write(delimiter);
        
        // 条件字段
        if (PublicUtil.isNotEmpty(expDO.getStockType()) && expDO.getStockType() > 2) {
            bw.write(info.getDeptNo() == null ? "" : info.getDeptNo().toString());
            bw.write(delimiter);
        }
        
        // 8/12/24/36月统计数据
        writePeriodData(bw, delimiter, info, 8);
        writePeriodData(bw, delimiter, info, 12);
        writePeriodData(bw, delimiter, info, 24);
        writePeriodData(bw, delimiter, info, 36);
        
        // M1-M36
        Integer[] monthData = {
            info.getM1(), info.getM2(), info.getM3(), info.getM4(), info.getM5(), info.getM6(),
            info.getM7(), info.getM8(), info.getM9(), info.getM10(), info.getM11(), info.getM12(),
            info.getM13(), info.getM14(), info.getM15(), info.getM16(), info.getM17(), info.getM18(),
            info.getM19(), info.getM20(), info.getM21(), info.getM22(), info.getM23(), info.getM24(),
            info.getM25(), info.getM26(), info.getM27(), info.getM28(), info.getM29(), info.getM30(),
            info.getM31(), info.getM32(), info.getM33(), info.getM34(), info.getM35(), info.getM36()
        };
        
        for (Integer m : monthData) {
            bw.write(m == null ? "" : m.toString());
            bw.write(delimiter);
        }
        
        bw.newLine();
    }

    /**
     * 写入周期统计数据(8/12/24/36月)
     */
    private void writePeriodData(BufferedWriter bw, String delimiter, ModelExpFreqDO info, int period) throws IOException {
        switch (period) {
            case 8:
                bw.write(info.getMonthsOf8() == null ? "" : info.getMonthsOf8().toString());
                bw.write(delimiter);
                bw.write(info.getCustomersOf8() == null ? "" : info.getCustomersOf8().toString());
                bw.write(delimiter);
                bw.write(info.getQtyOf8() == null ? "" : info.getQtyOf8().toString());
                bw.write(delimiter);
                bw.write(info.getAvgQtyOf8() == null ? "" : info.getAvgQtyOf8().toString());
                bw.write(delimiter);
                bw.write(info.getMaxCustomerOf8() == null ? "" : info.getMaxCustomerOf8());
                bw.write(delimiter);
                bw.write(info.getMaxCustomerDeptOf8() == null ? "" : info.getMaxCustomerDeptOf8());
                bw.write(delimiter);
                bw.write(info.getMaxCustomerRateOf8() == null ? "" : info.getMaxCustomerRateOf8().toString());
                bw.write(delimiter);
                bw.write(info.getMaxCustomerQtyOf8() == null ? "" : info.getMaxCustomerQtyOf8().toString());
                bw.write(delimiter);
                bw.write(info.getOrdersOf8() == null ? "" : info.getOrdersOf8().toString());
                break;
            case 12:
                bw.write(info.getMonthsOf12() == null ? "" : info.getMonthsOf12().toString());
                bw.write(delimiter);
                bw.write(info.getCustomersOf12() == null ? "" : info.getCustomersOf12().toString());
                bw.write(delimiter);
                bw.write(info.getQtyOf12() == null ? "" : info.getQtyOf12().toString());
                bw.write(delimiter);
                bw.write(info.getAvgQtyOf12() == null ? "" : info.getAvgQtyOf12().toString());
                bw.write(delimiter);
                bw.write(info.getMaxCustomerOf12() == null ? "" : info.getMaxCustomerOf12());
                bw.write(delimiter);
                bw.write(info.getMaxCustomerDeptOf12() == null ? "" : info.getMaxCustomerDeptOf12());
                bw.write(delimiter);
                bw.write(info.getMaxCustomerRateOf12() == null ? "" : info.getMaxCustomerRateOf12().toString());
                bw.write(delimiter);
                bw.write(info.getMaxCustomerQtyOf12() == null ? "" : info.getMaxCustomerQtyOf12().toString());
                bw.write(delimiter);
                bw.write(info.getOrdersOf12() == null ? "" : info.getOrdersOf12().toString());
                break;
            case 24:
                bw.write(info.getMonthsOf24() == null ? "" : info.getMonthsOf24().toString());
                bw.write(delimiter);
                bw.write(info.getCustomersOf24() == null ? "" : info.getCustomersOf24().toString());
                bw.write(delimiter);
                bw.write(info.getQtyOf24() == null ? "" : info.getQtyOf24().toString());
                bw.write(delimiter);
                bw.write(info.getAvgQtyOf24() == null ? "" : info.getAvgQtyOf24().toString());
                bw.write(delimiter);
                bw.write(info.getMaxCustomerOf24() == null ? "" : info.getMaxCustomerOf24());
                bw.write(delimiter);
                bw.write(info.getMaxCustomerDeptOf24() == null ? "" : info.getMaxCustomerDeptOf24());
                bw.write(delimiter);
                bw.write(info.getMaxCustomerRateOf24() == null ? "" : info.getMaxCustomerRateOf24().toString());
                bw.write(delimiter);
                bw.write(info.getMaxCustomerQtyOf24() == null ? "" : info.getMaxCustomerQtyOf24().toString());
                bw.write(delimiter);
                bw.write(info.getOrdersOf24() == null ? "" : info.getOrdersOf24().toString());
                break;
            case 36:
                bw.write(info.getMonthsOf36() == null ? "" : info.getMonthsOf36().toString());
                bw.write(delimiter);
                bw.write(info.getCustomersOf36() == null ? "" : info.getCustomersOf36().toString());
                bw.write(delimiter);
                bw.write(info.getQtyOf36() == null ? "" : info.getQtyOf36().toString());
                bw.write(delimiter);
                bw.write(info.getAvgQtyOf36() == null ? "" : info.getAvgQtyOf36().toString());
                bw.write(delimiter);
                bw.write(info.getMaxCustomerOf36() == null ? "" : info.getMaxCustomerOf36());
                bw.write(delimiter);
                bw.write(info.getMaxCustomerDeptOf36() == null ? "" : info.getMaxCustomerDeptOf36());
                bw.write(delimiter);
                bw.write(info.getMaxCustomerRateOf36() == null ? "" : info.getMaxCustomerRateOf36().toString());
                bw.write(delimiter);
                bw.write(info.getMaxCustomerQtyOf36() == null ? "" : info.getMaxCustomerQtyOf36().toString());
                bw.write(delimiter);
                bw.write(info.getOrdersOf36() == null ? "" : info.getOrdersOf36().toString());
                break;
        }
        bw.write(delimiter);
    }

    @Override
    public void exportModelExpFreq(ModelExpFreqRequest request, HttpServletResponse response) {
        //导入模板
        ModelExpFreqDO expDO = new ModelExpFreqDO();
        expDO.setStockCode(request.getStockcode());
        expDO.setModelType(request.getModelTYpe());
        expDO.setStockType(request.getStockType());
        String path = exportLargeTypeByTopPaging(expDO);
        if(path==null){
            return;
        }
        //导出文件
        try {
            File zipFile = new File(path);
            InputStream is = Files.newInputStream(zipFile.toPath());
            byte[] b = new byte[100];
            int len;
            try {
                while ((len = is.read(b)) > 0) {
                    response.getOutputStream().write(b, 0, len);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                is.close();
            }
        } catch (IOException e) {
            log.error("exportModelExpFreq failure, reason = {}", e.getMessage(), e);
        }
    }


    /**
     * 获取上次采购交货期
     */
    @Override
    public Date getLastPurchaseDlvDate(String modelNo, String warehouseCode) {

        Date dlvDate = null;  //采购单交货期
//
//        String key = "po:dlvdate:" + warehouseCode + modelNo;
//        String dlvdateinfo = (String) redisUtil.get(key);
//
//        if (PublicUtil.isNotEmpty(dlvdateinfo)) {
//            Date redisdlvDate = DateUtil.stringToDate(dlvdateinfo);
//            if (dlvDate == null) {
//                dlvDate = redisdlvDate;
//            } else if (redisdlvDate.compareTo(dlvDate) > 0) {
//                dlvDate = redisdlvDate;
//            }
//        }else {
        dlvDate = binOrderDetailMapper.getLastPurchaseDlvDate(modelNo, warehouseCode);  //采购单交货期
        //}
        return dlvDate;
    }

    /**
     * Add by Dengdenghui 2022-11-25 for bug-8788
     * 获取可出在库仓库的顺序
     * <!--addby WuWeiDong 20240229 bug 13554  分库可以补其他物流中心-->
     * 分库：所属中心->所属中心补库顺序（字典4012）
     * 中心：所属中心补库顺序（字典4012）
     *
     * @param warehouseCode BIN备库仓库
     * @param warehouseType 仓库类型，分库，物流中心
     * @return 可出在库仓库顺序
     */
    private List<String> getBinOrderTransWarehouseCode(String warehouseCode, String warehouseType) {
        String masterCode = warehouseCode;
        List<String> warehouseCodes = new ArrayList<>();
        if ("SUB".equalsIgnoreCase(warehouseType)) {
            // 分库 ---> 所属物流中心
            masterCode = binOrderCalcMapper.getParentCode(warehouseCode);
            warehouseCodes.add(masterCode);

        }
        SMCApp.setGrantType(GlobalConstant.GrantType.CLIANT_CREDENTIALS);
        String classCode = "4012";
        ResultVo<DataTypeVO> resultVo = dictCommonService.getDataTypeCodesInfo(classCode, masterCode);

        if (!resultVo.isSuccess()) {
            if ("暂无数据".equals(resultVo.getMessage())) {
                return Collections.emptyList();
            } else {
                throw new BusinessException("获取可调库仓库失败," + resultVo.getMessage());
            }
        }
        if (StringUtils.isBlank(resultVo.getData().getExtNote1())) {
            return Collections.emptyList();
        }
        String[] warehouseList = resultVo.getData().getExtNote1().split("-");
        warehouseCodes.addAll(Arrays.asList(warehouseList));
        return warehouseCodes;

    }

    /**
     * @return 全公司库存总量控制最大月数
     */
    private BigDecimal getMaxControlMonths() {
        SMCApp.setGrantType(GlobalConstant.GrantType.CLIANT_CREDENTIALS);
        ResultVo<DataTypeVO> resultVo = dictDataServiceFeignApi.getDataTypeCodesInfo("9002", "4");
        if (!resultVo.isSuccess() || resultVo.getData() == null) {
            log.error("getMaxControlMonths error : {}", resultVo.getMessage());
            return null;
        }
        String maxControlMonths = Optional.ofNullable(resultVo.getData().getExtNote1()).orElse("0");
        return new BigDecimal(maxControlMonths);
    }

    private ExecutorService updateBinOrderDetailByIds(List<BinOrderDetailDO> list) throws Exception {
        final long statetime = System.currentTimeMillis();

        Integer count = 0;
        Integer size = list.size();
        Integer offset = 2000;
        int runSize = (size / offset) + 1;
        ExecutorService executor = Executors.newFixedThreadPool(runSize);
        if(CollectionUtils.isNotEmpty(list)){
            for (Integer idx = 0; idx < size; idx++) {
            final List<BinOrderDetailDO> listSub = (idx + offset) >= size ? list.subList(idx, size) : list.subList(idx, idx + offset + 1);
            final Future<Integer> future = executor.submit(new Callable<Integer>() {
                @Override
                public Integer call() throws Exception {
//                    final List<BinOrderDetailDO> listsub = updateBinOrderDetailListSub;
                    Thread.sleep(100);
                    try {
                        for (BinOrderDetailDO updDO : listSub) {
                            updDO.setUpdateTime(DateUtil.getNow());
                            if (PublicUtil.isEmpty(updDO.getUpdateUser())) {
                                updDO.setUpdateUser(SMCApp.getLoginAuthDtoForSysUser().getUserNo());
                            }
                            binOrderDetailMapper.updateById(updDO);
                        }
                    } catch (NullPointerException ex) {
                        log.error(Thread.currentThread().getName() + "->错误NullPointerException：" + ex);
                        throw new Exception(Thread.currentThread().getName() + "->错误：" + ex);
                    } catch (Exception ex) {
                        log.error(Thread.currentThread().getName() + "->错误Exception：" + ex);
                        throw new Exception(Thread.currentThread().getName() + "->错误：" + ex);
                    }
                    return 1;
                }
            });
            Thread.sleep(100);
            idx = idx + offset;
        }
        }
        log.info(Thread.currentThread().getName() +
                "耗时(s):" + (System.currentTimeMillis() - statetime) / 1000.0);
        return executor;
    }

    private ExecutorService insertBinOrderDetailSplit(List<BinOrderDetailSplitDO> list) throws Exception {
        final long statetime = System.currentTimeMillis();
        Integer count = 0;
        Integer size = list.size();
        Integer offset = 150;////最多 2100 个参数 2100/13
        int runSize = (size / offset) + 1;
        if (runSize > 200) {
            runSize = 200;
        }
        ExecutorService executor = Executors.newFixedThreadPool(runSize);
        if (CollectionUtils.isNotEmpty(list)) {
            for (Integer idx = 0; idx < size; idx++) {
                final List<BinOrderDetailSplitDO> listsSub = (idx + offset) >= size ? list.subList(idx, size) : list.subList(idx, idx + offset + 1);
                idx = idx + offset;
                final Future<Integer> future = executor.submit(new Callable<Integer>() {
                    @Override
                    public Integer call() throws Exception {
                        Thread.sleep(100);
                        try {
                            binOrderDetailSplitMapper.InsertByBatch(listsSub);
                        } catch (NullPointerException ex) {
                            log.error(Thread.currentThread().getName() + "->错误NullPointerException：" + ex);
                            throw new Exception(Thread.currentThread().getName() + "->错误：" + ex);
                        } catch (Exception ex) {
                            log.error(Thread.currentThread().getName() + "->错误Exception：" + ex);
                            throw new Exception(Thread.currentThread().getName() + "->错误：" + ex);
                        }
                        return 1;
                    }
                });
                Thread.sleep(100);

            }
        }
        log.info(Thread.currentThread().getName() +
                "耗时(s):" + (System.currentTimeMillis() - statetime) / 1000.0);
        return executor;
    }

    private ExecutorService updateBinOrderDetailSplitCancel(List<Long> list) throws Exception {
        final long statetime = System.currentTimeMillis();
        Integer count = 0;
        Integer size = list.size();
        Integer offset = 2000;
        int runSize = (size / offset) + 1;
        if (runSize > 200) {
            runSize = 200;
        }
        ExecutorService executor = Executors.newFixedThreadPool(runSize);
        if(CollectionUtils.isNotEmpty(list)) {
            for (Integer idx = 0; idx < size; idx++) {
                final List<Long> updateCancelSub = (idx + offset) >= size ? list.subList(idx, size) : list.subList(idx, idx + offset + 1);
                idx = idx + offset;
                final Future<Integer> future = executor.submit(new Callable<Integer>() {
                    @Override
                    public Integer call() throws Exception {
                        Thread.sleep(100);
                        try {
                            LambdaUpdateWrapper<BinOrderDetailSplitDO> updateSplitDetailWrapper = Wrappers.lambdaUpdate();
                            updateSplitDetailWrapper.in(BinOrderDetailSplitDO::getFromId, updateCancelSub)
                                    .set(BinOrderDetailSplitDO::getDelFlag, 1)
                                    .set(BinOrderDetailSplitDO::getUpdateTime, DateUtil.getNow())
                                    .set(BinOrderDetailSplitDO::getUpdateUser, SMCApp.getLoginAuthDtoForSysUser().getUserNo());
                            binOrderDetailSplitMapper.update(null, updateSplitDetailWrapper);
                        } catch (NullPointerException ex) {
                            log.error(Thread.currentThread().getName() + "->错误NullPointerException：" + ex);
                            throw new Exception(Thread.currentThread().getName() + "->错误：" + ex);
                        } catch (Exception ex) {
                            log.error(Thread.currentThread().getName() + "->错误Exception：" + ex);
                            throw new Exception(Thread.currentThread().getName() + "->错误：" + ex);
                        }
                        return 1;
                    }
                });
                Thread.sleep(100);

            }
        }
        log.info(Thread.currentThread().getName() +
                "耗时(s):" + (System.currentTimeMillis() - statetime) / 1000.0);
        return executor;
    }

    private List<ModelExpFreqDO> getModelExpFreqForAvgQty(List<BindataDO> bindataDOList) throws Exception {
        if (CollectionUtil.isEmpty(bindataDOList)) {
            return null;
        }
        try {
            final long statetime = System.currentTimeMillis();
            List<BindataDO> list = bindataDOList.stream().filter(f -> f.getQtyBin().compareTo(0) == 0)
                    .collect(Collectors.toList());

            if (CollectionUtil.isEmpty(list)) {
                return null;
            }

            List<ModelExpFreqDO> freqDOList = new ArrayList<>(list.size());
            ModelExpFreqDO freqDO;
            for (BindataDO bindata : list) {
                freqDO = new ModelExpFreqDO();
                freqDO.setModelNo(bindata.getModelNo());
                freqDO.setStockCode(bindata.getWarehouseCode());
                freqDOList.add(freqDO);
            }
            log.info(Thread.currentThread().getName() + "耗时(s):" + (System.currentTimeMillis() - statetime) / 1000.0);
            return commonService.getModelExpFreqForAvgQty(freqDOList);
        } catch (Exception ex) {
            log.error(Thread.currentThread().getName() + "->错误Exception：" + ex);
            throw new Exception(Thread.currentThread().getName() + "->错误：" + ex);

        }
    }

//    public boolean calcBinOrderTransQty(BinOrderCalcRequestDTO dto, RedisMessageVO messageVo) {
//        LambdaQueryWrapper<BinOrderDetailDO> detailQuery = Wrappers.lambdaQuery();
//        detailQuery.eq(BinOrderDetailDO::getCalcId, dto.getCalcId())
//                .gt(BinOrderDetailDO::getConfirmQty, 0)  //补货数量大于0
//                .eq(BinOrderDetailDO::getStatus, 1)
//                .ne(BinOrderDetailDO::getCalcType, 4);  //客户BIN只算补库数量，不参与采购或调库
//        List<BinOrderDetailDO> list = binOrderDetailMapper.selectList(detailQuery);
//        if (CollectionUtils.isEmpty(list)) {
//            messageVo.setContent("没有需要补货的数量");
//            saveCalcState(messageVo);
//            this.updateCalcFinishTime(dto.getCalcId());
//            return true;
//        }
//
//        //  String warehouseType = commonService.getWarehouseType(dto.getWarehouseCode());
//
//        //    <!--add by WuWeiDong 202306020  bug 11100  补库，调库申请校验追加 -->
//
//        ResultVo<WarehouseVO> warehouseVOResultVo = commonServiceFeignApi.getWarehouseInfoByCode(dto.getWarehouseCode());
//
//        if (!warehouseVOResultVo.isSuccess()) {
//            log.error(dto.getWarehouseCode() + warehouseVOResultVo.getMessage());
//            messageVo.setContent(dto.getWarehouseCode() + warehouseVOResultVo.getMessage());
//            saveCalcState(messageVo);
//            return false;
//        }
//        WarehouseVO warehouseVO = warehouseVOResultVo.getData();
//        if (warehouseVO.getTransferFlag().compareTo(0) == 0 && warehouseVO.getPrestockFlag().compareTo(0) == 0) {
//            log.error(warehouseVO.getWarehouseCode() + "此仓库不可补库和调库。");
//            messageVo.setContent(warehouseVO.getWarehouseCode() + "此仓库不可补库和调库。");
//            saveCalcState(messageVo);
//            return false;
//        }
//
//        String warehouseType = warehouseVO.getWarehouseType();
//
//        List<String> modelNoList = list.stream().map(BinOrderDetailDO::getModelNo).distinct().collect(Collectors.toList());
//
//        List<String> warehouseCodeList = this.getBinOrderTransWarehouseCode(dto.getWarehouseCode(), warehouseType);
//
//        ResultVo<Map<String, Map<String, BinOrderInventoryInfoVO>>> stockResult = this.listBinWarehouseStock(modelNoList, warehouseCodeList);
//        if (!stockResult.isSuccess()) {
//            log.error("获取BIN补货型号在库信息失败: {}", stockResult.getMessage());
//            messageVo.setContent("BIN补库数量分配调拨和采购失败-" + stockResult.getMessage());
//            saveCalcState(messageVo);
//            return false;
//        }
//        Map<String, Map<String, BinOrderInventoryInfoVO>> modelWarehouseStockMap = stockResult.getData();
//        Map<String, BinOrderInventoryInfoVO> warehouseMap;
//        LambdaUpdateWrapper<BinOrderDetailSplitDO> updateSplitDetailWrapper = Wrappers.lambdaUpdate();
//        BinOrderDetailSplitDO entity = new BinOrderDetailSplitDO();
//
//        long startTimer = System.currentTimeMillis();
//        List<BinOrderDetailDO> updDOList = new ArrayList<>();
//        List<BinOrderDetailSplitDO> insertSplitList = new ArrayList<>();
//        List<Long> updateSplitCancelList = new ArrayList<>();
//        String[] stocks;
//        int[] stockQtys;
//        List<BinOrderDetailSplitDO> splitList = new ArrayList<>();
//        BinOrderInventoryInfoVO binOrderInventoryInfo = new BinOrderInventoryInfoVO();
//        // 获取公司库存总量控制最大月数
//        BigDecimal maxControlMonths = this.getMaxControlMonths();
//        if (maxControlMonths == null) {
//            messageVo.setContent("BIN补库数量分配调拨和采购失败-库存总量控制月数为空");
//            saveCalcState(messageVo);
//            return false;
//        }
//        //分库计算
//        BinOrderDetailDO updDO;
//        if ("SUB".equalsIgnoreCase(warehouseType)) {
//            // 查找该分库的物流中心
//            String warehouseCode = warehouseCodeList.get(0);
//            //    <!--add by WuWeiDong 20240223  bug 13554  分库自动补库改善 -->
//            //该分库对应物流中心是否为BIN
//            Map<String, String> binMap = new HashMap<>();
//            try {
//                Future<List<BindataDO>> binDatafuture = commonService.asyncGetBinDataByModels(modelNoList, Arrays.asList(warehouseCode));
//                while (true) {
//                    if (binDatafuture.isDone()) {
//                        binMap = binDatafuture.get().stream().filter(i -> i.getQtyBin().compareTo(0) == 1).collect(Collectors.toMap(BindataDO::getModelNo, BindataDO::getWarehouseCode, (n1, n2) -> n1));
//                        break;
//                    }
//                }
//            } catch (Exception ex) {
//                log.error("获取BIN数据失败: {}", stockResult.getMessage());
//                messageVo.setContent("获取BIN数据失败-" + stockResult.getMessage());
//                saveCalcState(messageVo);
//                return false;
//            }
//            for (BinOrderDetailDO info : list) {
//
//                Boolean isBin = binMap.containsKey(info.getModelNo());
//                updDO = new BinOrderDetailDO();
//                updDO.setId(info.getId());
//                updDO.setCalcId(dto.getCalcId());
//                //    <!--add by WuWeiDong 20240320  bug 13764  增加默认值，避免空值 -->
//                updDO.setConfirmQty(0);
//                updDO.setPoQty(0);
//                updDO.setTransQty(0);
//                splitList = new ArrayList<>(warehouseCodeList.size());
//                boolean maxControlMonthIntercept = Optional.ofNullable(info.getStockMonthsAll()).orElse(BigDecimal.ZERO).compareTo(maxControlMonths) >= 0
//                        && Optional.ofNullable(info.getOrderQty()).orElse(0) == 0;
//                if (StringUtils.isNotBlank(info.getOrderType())) {
//                    updDO.setOrderType(info.getOrderType());
//                } else if (StringUtils.isNotBlank(info.getSetOrderType())) {
//                    updDO.setOrderType(info.getSetOrderType());
//                } else {
//                    updDO.setOrderType("K");
//                }
//                updDO.setSupplierCode(info.getMainSupplierCode());
//                //直采，或仓库属性不能调拨，采购
//                if (info.getDirectpurchase().compareTo(0) != 0
//                        || warehouseVO.getTransferFlag().compareTo(1) != 0) {
//                    updDO.setPoQty(info.getConfirmQty());
//                    updDO.setTransQty(0);
//                    if (isBin) {
//                        updDO.setDirectpurchase(2);
//                    }
//                } else {
//                    //未标记直采的型号，如果分库型号是大库的BIN品，进入库存调整模块
//                    //查找物流中心的过剩数量
//                    warehouseMap = modelWarehouseStockMap.get(info.getModelNo());
//                    if (ObjectUtils.isNotEmpty(warehouseMap)) {
//                        if (isBin) {
//                            stocks = new String[warehouseCodeList.size()];
//                            stockQtys = new int[warehouseCodeList.size()];
//                            for (int index = 0; index < warehouseCodeList.size(); index++) {
//                                BinOrderInventoryInfoVO inventoryInfoVO = warehouseMap.getOrDefault(warehouseCodeList.get(index), null);
//                                //1）剩余数>=补库数，全部调拨。
//                                if (ObjectUtils.isNotEmpty(inventoryInfoVO) && inventoryInfoVO.getAvaQty_ty().compareTo(info.getConfirmQty()) >= 0) {
//                                    stocks[index] = warehouseCodeList.get(index);
//                                    binOrderInventoryInfo = warehouseMap.get(stocks[index]);
//                                    if (binOrderInventoryInfo != null) {
//                                        stockQtys[index] = Optional.ofNullable(inventoryInfoVO.getExcessQty()).orElse(0);
//                                    }
//                                }
//                            }
//                            Integer transQty = Arrays.stream(stockQtys).sum();
//                            if (transQty.compareTo(0) == 1) {
//                                splitList = this.getBinOrderDetailSplits(stocks, stockQtys, updDO, info, maxControlMonthIntercept);
//                            } // End
//                            updateSplitCancelList.add(info.getId());
//                            if (CollectionUtils.isNotEmpty(splitList) && splitList.size() > 0) {
//                                //写入拆分表
//                                insertSplitList.addAll(splitList);
//
//                            }
//                            updDO.setTransQty(transQty);
//                            updDO.setPoQty(info.getConfirmQty() - transQty);
//                            if (updDO.getTransQty().compareTo(0) == 1 && updDO.getPoQty().compareTo(0) == 0) {
//                                //只调拨
//                                updDO.setOrderType("1");
//                                updDO.setSupplierCode(warehouseCode);
//                                updDO.setOrderStockCode(warehouseCode);
//
//                            } else if (updDO.getTransQty().compareTo(0) == 1 && updDO.getPoQty().compareTo(0) == 1) {
//                                //调拨和采购
//                                updDO.setOrderType("9");
//                                updDO.setOrderStockCode(warehouseCode);
//                                if (isBin) {
//                                    updDO.setDirectpurchase(2);
//                                }
//                            } else {
//                                //只采购
//                                updDO.setOrderType("K");
//                                if (isBin) {
//                                    updDO.setDirectpurchase(2);
//                                }
//                            }
//
//                        } else {
//                            //所有物流中心补库
//                            //查找各个库房过剩数量
//                            if (MapUtils.isNotEmpty(warehouseMap) && warehouseVO.getTransferFlag().compareTo(0) != 0) {
//                                //其它物流中心过剩数量>=补库数量则调拨
//                                // KBJ --> KSH > KGZ
//                                // KSH --> KBJ > KGZ
//                                // KGZ --> KBJ > KSH
//                                // 根据可出在库仓库，分配调拨数量 bug-8788
//                                stocks = new String[warehouseCodeList.size()];
//                                stockQtys = new int[warehouseCodeList.size()];
//                                for (int index = 0; index < warehouseCodeList.size(); index++) {
//                                    stocks[index] = warehouseCodeList.get(index);
//                                    binOrderInventoryInfo = warehouseMap.get(stocks[index]);
//                                    if (binOrderInventoryInfo != null) {
//                                        stockQtys[index] = Optional.ofNullable(binOrderInventoryInfo.getExcessQty()).orElse(0);
//                                    }
//                                }
//                                if (Arrays.stream(stockQtys).sum() > 0) {
//                                    splitList = this.getBinOrderDetailSplits(stocks, stockQtys, updDO, info, maxControlMonthIntercept);
//                                } // End
//                                updateSplitCancelList.add(info.getId());
//
//                            }
//                            if (CollectionUtils.isNotEmpty(splitList) && splitList.size() > 0) {
//                                //写入拆分表
//                                insertSplitList.addAll(splitList);
//
//                            }
//                            // 当超过全公司的总的备库月数总量控制的月数，不做采购
//                            if (maxControlMonthIntercept) {
//                                // order_qty > 0 时, confirm_qty = order_qty
//                                if (Optional.ofNullable(info.getOrderQty()).orElse(0).compareTo(0) == 0) {
//                                    updDO.setConfirmQty(Optional.ofNullable(updDO.getConfirmQty()).orElse(0) - Optional.ofNullable(updDO.getPoQty()).orElse(0));
//                                }
//                                if (info.getMean() != null && info.getMean() > 0) {
//                                    updDO.setMonths(BigDecimalUtil.div((updDO.getConfirmQty() + info.getStockQty() + info.getOrdingQty() - info.getPreQty()), info.getMean(), 2).doubleValue());
//                                }
//                                updDO.setPoQty(0);
//                            }
//
//
//                        }
//                    } else {
//                        updDO.setPoQty(info.getConfirmQty());
//                        updDO.setTransQty(0);
//                        if (isBin) {
//                            updDO.setDirectpurchase(2);
//                        }
//                    }
//                }
//                updDO.setUpdateTime(DateUtil.getNow());
//                updDO.setUpdateUser(SMCApp.getLoginAuthDtoForSysUser().getUserNo());
//                //判断完更新采购或调拨
//                //  binOrderDetailMapper.updateById(updDO);
//                updDOList.add(updDO);
//
//            }
//        } else {
//            //非分库补库
//            int count = 0;
//            int totalCount = list.size();
//            for (BinOrderDetailDO info : list) {
//                //先默认成可采购，存在调拨条件再改成调拨
//                messageVo.setContent(dto.getCalcId() + "计算是否有过剩调拨" + count++ + "/" + totalCount);
//                log.info(messageVo.getContent());
//                saveCalcState(messageVo);
//                try {
//                    updDO = new BinOrderDetailDO();
//                    updDO.setCalcId(dto.getCalcId());
//                    updDO.setId(info.getId());
//                    updDO.setConfirmQty(info.getConfirmQty());
//                    //    <!--add by WuWeiDong 20240320  bug 13764  增加默认值，避免空值 -->
//                    updDO.setConfirmQty(0);
//                    updDO.setPoQty(0);
//                    updDO.setTransQty(0);
//                    if (warehouseVO.getPrestockFlag().compareTo(0) != 0) {
//                        updDO.setPoQty(info.getConfirmQty());
//                    } else {
//                        updDO.setPoQty(0);
//                    }
//                    if (StringUtils.isNotBlank(info.getOrderType())) {
//                        updDO.setOrderType(info.getOrderType());
//                    } else if (StringUtils.isNotBlank(info.getSetOrderType())) {
//                        updDO.setOrderType(info.getSetOrderType());
//                    } else {
//                        updDO.setOrderType("K");
//                    }
//                    updDO.setSupplierCode(info.getMainSupplierCode());
//
//                    //查找各个库房过剩数量
//                    warehouseMap = modelWarehouseStockMap.get(info.getModelNo());
//                    splitList = new ArrayList<>(warehouseCodeList.size());
//                    boolean maxControlMonthIntercept = Optional.ofNullable(info.getStockMonthsAll()).orElse(BigDecimal.ZERO).compareTo(maxControlMonths) >= 0
//                            && Optional.ofNullable(info.getOrderQty()).orElse(0) == 0;
//                    if (MapUtils.isNotEmpty(warehouseMap) && warehouseVO.getTransferFlag().compareTo(0) != 0) {
//                        //其它物流中心过剩数量>=补库数量则调拨
//                        // KBJ --> KSH > KGZ
//                        // KSH --> KBJ > KGZ
//                        // KGZ --> KBJ > KSH
//                        // 根据可出在库仓库，分配调拨数量 bug-8788
//                        stocks = new String[warehouseCodeList.size()];
//                        stockQtys = new int[warehouseCodeList.size()];
//                        for (int index = 0; index < warehouseCodeList.size(); index++) {
//                            stocks[index] = warehouseCodeList.get(index);
//                            binOrderInventoryInfo = warehouseMap.get(stocks[index]);
//                            if (binOrderInventoryInfo != null) {
//                                stockQtys[index] = Optional.ofNullable(binOrderInventoryInfo.getExcessQty()).orElse(0);
//                            }
//                        }
//                        if (Arrays.stream(stockQtys).sum() > 0) {
//                            splitList = this.getBinOrderDetailSplits(stocks, stockQtys, updDO, info, maxControlMonthIntercept);
//                        } // End
//                        updateSplitCancelList.add(info.getId());
//
//                    }
//                    if (CollectionUtils.isNotEmpty(splitList) && splitList.size() > 0) {
//                        //写入拆分表
//                        insertSplitList.addAll(splitList);
//
//                    }
//                    // 当超过全公司的总的备库月数总量控制的月数，不做采购
//                    if (maxControlMonthIntercept) {
//                        // order_qty > 0 时, confirm_qty = order_qty
//                        if (Optional.ofNullable(info.getOrderQty()).orElse(0).compareTo(0) == 0) {
//                            updDO.setConfirmQty(Optional.ofNullable(updDO.getConfirmQty()).orElse(0) - Optional.ofNullable(updDO.getPoQty()).orElse(0));
//                        }
//                        if (info.getMean() != null && info.getMean() > 0) {
//                            updDO.setMonths(BigDecimalUtil.div((updDO.getConfirmQty() + info.getStockQty() + info.getOrdingQty() - info.getPreQty()), info.getMean(), 2).doubleValue());
//                        }
//                        updDO.setPoQty(0);
//                    }
//                    //判断完更新采购或调拨
//                    //binOrderDetailMapper.updateById(updDO);
//                    updDOList.add(updDO);
//
//                } catch (Exception e) {
//                    messageVo.setContent("计算异常" + e.getMessage() + dto.getCalcId() + "计算是否有过剩调拨" + count++ + "/" + totalCount);
//                    log.error("BIN补库计算: {}", messageVo.getContent(), e);
//                    messageVo.setStatus(4);
//                    saveCalcState(messageVo);
//                }
//            }
//
//
//        }
//        ///写入，更新
//        boolean result = this.insertUpdateBinOrderDetail(updDOList, insertSplitList, updateSplitCancelList);
//        log.info(Thread.currentThread().getStackTrace()[1].getMethodName() + " 处理结束，耗时(s): " + (System.currentTimeMillis() - startTimer) / 1000.0);
//        return result;
//    }
}
