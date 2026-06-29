package com.smc.smccloud.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.sales.ops.enums.WarehouseTypeEnum;
import com.smc.smccloud.core.exception.BusinessException;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.model.constants.Constants;
import com.smc.smccloud.core.model.constants.GlobalConstant;
import com.smc.smccloud.core.redis.RedisManager;
import com.smc.smccloud.core.utils.PublicUtil;
import com.smc.smccloud.core.utils.SMCApp;
import com.smc.smccloud.mapper.BindataMapper;
import com.smc.smccloud.mapper.binorder.ModelExpFreqMapper;
import com.smc.smccloud.model.bindata.BindataDO;
import com.smc.smccloud.model.binorder.ModelExpFreqDO;
import com.smc.smccloud.model.customer.CustomerVO;
import com.smc.smccloud.model.inventory.InventoryRequestDTO;
import com.smc.smccloud.model.inventory.WarehouseInventoryVO;
import com.smc.smccloud.service.CommonService;
import com.smc.smccloud.service.CommonServiceFeignApi;
import com.smc.smccloud.service.InventoryServiceFeignApi;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Author: B90034
 * Date: 2023-01-09 13:54
 * Description:
 */
@Slf4j
@Service
public class CommonServiceImpl implements CommonService {

    @Resource
    private CommonServiceFeignApi commonServiceFeignApi;
    @Resource
    private InventoryServiceFeignApi inventoryServiceFeignApi;
    @Resource
    private BindataMapper bindataMapper;
    @Resource
    private RedisManager redisManager;
    @Resource
    private ModelExpFreqMapper modelExpFreqMapper;

    @Override
    public CustomerVO getCustomerInfoByNo(String customerNo) {
        if (StringUtils.isBlank(customerNo)) {
            return null;
        }
        Object customer = redisManager.hGet(Constants.REDIS_ALL_CUSTOMER_INFO, customerNo);
        if (customer == null) {
            ResultVo<CustomerVO> resultVo = commonServiceFeignApi.findCustomerByCustomerNo(customerNo);
            if (resultVo.isSuccess() && resultVo.getData() != null) {
                return resultVo.getData();
            }
            return null;
        }
        return JSONObject.parseObject(customer.toString(), CustomerVO.class);
    }

    @Override
    public String getWarehouseNameByCode(String warehouseCode) {
        if (StringUtils.isBlank(warehouseCode)) {
            return null;
        }
        Object houseName = redisManager.hGet(Constants.REDIS_WAREHOUSE_NAME_BY_CODE, warehouseCode);
        if (houseName == null) {
            ResultVo<String> warehouseName = commonServiceFeignApi.getWarehouseName(warehouseCode);
            if (warehouseName.isSuccess() && warehouseName.getData() != null) {
                return warehouseName.getData();
            }
            return null;
        } else {
            return houseName.toString();
        }
    }

    @Override
    public boolean isMasterWarehouse(String warehouseCode) {
        String warehouseType = this.getWarehouseType(warehouseCode);
        return WarehouseTypeEnum.RDC.getHouseTypeCode().equals(warehouseType);
    }

    @Override
    public String getWarehouseType(String warehouseCode) {
        Object o = redisManager.hGet(Constants.REDIS_WAREHOUSE_TYPE_BY_CODE, warehouseCode);
        if (o != null) {
            return o.toString();
        }
        ResultVo<String> resultVo = commonServiceFeignApi.getWarehouseType(warehouseCode);
        if (!resultVo.isSuccess()) {
            return "";
        }
        return resultVo.getData();
    }

    @Override
    public List<String> getMasterWarehouseCodes() {
        Object o = redisManager.get(Constants.REDIS_WAREHOUSE_CODES_BY_TYPE + WarehouseTypeEnum.RDC.getHouseTypeCode());
        if (o != null) {
            return JSON.parseArray(o.toString(), String.class);
        }
        SMCApp.setGrantType(GlobalConstant.GrantType.CLIANT_CREDENTIALS);
        ResultVo<List<String>> resultVo = commonServiceFeignApi.getWarehouseCodeByWarehouseType(WarehouseTypeEnum.RDC.getHouseTypeCode());
        if (!resultVo.isSuccess()) {
            return Collections.emptyList();
        }
        return resultVo.getData();
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public ModelExpFreqDO getModelExpFreq(String modelno, String warehouseCode) {
        LambdaQueryWrapper<ModelExpFreqDO> query = new LambdaQueryWrapper<>();
        query.select(ModelExpFreqDO::getModelNo, ModelExpFreqDO::getStockCode, ModelExpFreqDO::getAvgQtyOf8, ModelExpFreqDO::getStockType);
        query.eq(ModelExpFreqDO::getModelNo, modelno)
                .eq(ModelExpFreqDO::getStockCode, warehouseCode)
                .eq(ModelExpFreqDO::getModelType, 1);//查基础型号的
        return modelExpFreqMapper.selectOne(query);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public List<ModelExpFreqDO> getModelExpFreqForAvgQty(List<ModelExpFreqDO> doList) {
        if (CollectionUtil.isEmpty(doList)) {
            return null;
        }
        long startTimer = System.currentTimeMillis();
        Integer count = doList.size();
        Integer offset = 1000 - 1;
        List<ModelExpFreqDO> rtnData = new ArrayList<>(doList.size());
        List<ModelExpFreqDO> queryParm = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            if ((i + offset) > count) {
                queryParm = doList.subList(i, count);
            } else {
                queryParm = doList.subList(i, i + offset + 1);
            }
            i = i + offset;
            List<ModelExpFreqDO> resultData = modelExpFreqMapper.getModelExpFreqForAvgQty(queryParm);
            if (CollectionUtil.isNotEmpty(resultData)) {
                rtnData.addAll(resultData);
            }
        }

        System.out.println(" ***** " + Thread.currentThread().getStackTrace()[1].getMethodName() + " 查询结束耗时(s): " + (System.currentTimeMillis() - startTimer) / 1000.0);
        return rtnData;

    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public List<ModelExpFreqDO> getModelExpFreqForAvgQty12(List<String> modelNos) {

        if (CollectionUtils.isEmpty(modelNos) || modelNos.size() == 0) {
            return null;
        }

        try {
            Integer size = modelNos.size();
            Integer offset = 2000;//最小粒度
            Integer runSize = (size / offset) + 1;//最大线程
            if (runSize > 200) {
                runSize = 200; //最大只允许200个
            }
            ExecutorService executorService = Executors.newFixedThreadPool(runSize);
            List<ModelExpFreqDO> rtnData = Collections.synchronizedList(new ArrayList<>());//为了解决线程安全问题
            for (int idx = 0; idx < size; idx++) {
                final List<String> params = (idx + offset) >= size ? modelNos.subList(idx, size) : modelNos.subList(idx, idx + offset + 1);
                idx = idx + offset;
                final Future<Integer> future = executorService.submit(new Callable<Integer>() {
                    @Override
                    public Integer call() throws Exception {
                        try {
                            List<ModelExpFreqDO> resultData = modelExpFreqMapper.getModelExpFreqForAvgQty12(params);
                            if (CollectionUtil.isNotEmpty(resultData) && resultData.size() > 0) {
                                rtnData.addAll(resultData);
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
            }
            executorService.shutdown();
            while (!executorService.isTerminated()) {
                Thread.sleep(100);
            }
            return rtnData;
        } catch (Exception ex) {
            throw new BusinessException(Thread.currentThread().getName() + "->错误：" + ex);
        }

    }

    /**
     * 分库别，36，24，12，8取平均
     * @param modelNos
     * @param warehouseCode
     * @param month
     * @return
     */
    @Override
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
   public List<ModelExpFreqDO> getModelExpFreqForAvgQty (List<String> modelNos,String warehouseCode,Integer month){
        if (CollectionUtils.isEmpty(modelNos) || StringUtils.isEmpty(warehouseCode)  ) {
            throw new BusinessException("请输入型号和仓库代码");
        }
        if(!Arrays.asList(36,24,12,8).contains(month)){
            throw new BusinessException("只有36，24，12，8 这个几个月的平均，请确认。");
        }

        try {
            Integer size = modelNos.size();
            Integer offset = 2000;//最小粒度
            Integer runSize = (size / offset) + 1;//最大线程
            if (runSize > 200) {
                runSize = 200; //最大只允许200个
            }
            ExecutorService executorService = Executors.newFixedThreadPool(runSize);
            List<ModelExpFreqDO> rtnData = Collections.synchronizedList(new ArrayList<>());//为了解决线程安全问题
            for (int idx = 0; idx < size; idx++) {
                final List<String> params = (idx + offset) > size ? modelNos.subList(idx, size) : modelNos.subList(idx, idx + offset + 1);
                final String parmWarehouse=warehouseCode;
                final Integer parmMonth=month;
                idx = idx + offset;
                final Future<Integer> future = executorService.submit(new Callable<Integer>() {
                    @Override
                    public Integer call() throws Exception {
                        try {
                            List<ModelExpFreqDO> resultData = modelExpFreqMapper.getModelExpFreqForAvgQty(params,parmWarehouse,parmMonth);
                            if (CollectionUtil.isNotEmpty(resultData) && resultData.size() > 0) {
                                rtnData.addAll(resultData);
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
            }
            executorService.shutdown();
            while (!executorService.isTerminated()) {
                Thread.sleep(100);
            }
            return rtnData;
        } catch (Exception ex) {
            throw new BusinessException(Thread.currentThread().getName() + "->错误：" + ex);
        }
   }
    /**
     * 拆成多个批量，异步查询库存
     * add by WuWeiDong 20230327
     *
     * @param modelNoList
     * @param warehouseCodeList  可以为空值
     * @return
     */
    @Async
    @Override
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public Future<List<WarehouseInventoryVO>> asyncGetWarehouseInventoryByModels(List<String> modelNoList, List<String> warehouseCodeList) {
        if (CollectionUtil.isEmpty(modelNoList) || CollectionUtil.isEmpty(warehouseCodeList)) {
            return new AsyncResult<List<WarehouseInventoryVO>>(null);
        }
        try {
            long startTimer = System.currentTimeMillis();
            Integer count = modelNoList.size();
            Integer offset = 1000 - 1;
            List<WarehouseInventoryVO> rtnData = new ArrayList<>(modelNoList.size());
            List<String> queryParm = new ArrayList<>();
            InventoryRequestDTO dto = new InventoryRequestDTO();
            if (PublicUtil.isNotEmpty(warehouseCodeList)) {
                dto.setWarehouseCodes(warehouseCodeList);
            }

            List<List<String>> partition = ListUtils.partition(modelNoList, 900);
            for (List<String> item: partition) {
                dto.setModelNos(item);
                SMCApp.setGrantType(GlobalConstant.GrantType.CLIANT_CREDENTIALS);
                ResultVo<List<WarehouseInventoryVO>> resultData = inventoryServiceFeignApi.getWarehouseCanUseStock(dto);
                if (!resultData.isSuccess()) {
                    log.error("获取BIN型号库存信息失败" + resultData.getMessage());
                    //return ResultVo.failure("获取BIN型号库存信息失败: " + resultData.getMessage());
                    return new AsyncResult<List<WarehouseInventoryVO>>(null);
                }
                if (CollectionUtil.isNotEmpty(resultData.getData())) {
                    rtnData.addAll(resultData.getData());
                }
            }
//            for (int i = 0; i < count; i++) {
//                if ((i + offset) > count) {
//                    queryParm = modelNoList.subList(i, count);
//                } else {
//                    queryParm = modelNoList.subList(i, i + offset + 1);
//                }
//                i = i + offset;
//
//                dto.setModelNos(queryParm);
//                SMCApp.setGrantType(GlobalConstant.GrantType.CLIANT_CREDENTIALS);
//                ResultVo<List<WarehouseInventoryVO>> resultData = inventoryServiceFeignApi.getWarehouseCanUseStock(dto);
//                if (!resultData.isSuccess()) {
//                    log.error("获取BIN型号库存信息失败" + resultData.getMessage());
//                    //return ResultVo.failure("获取BIN型号库存信息失败: " + resultData.getMessage());
//                    return new AsyncResult<List<WarehouseInventoryVO>>(null);
//                }
//                if (CollectionUtil.isNotEmpty(resultData.getData())) {
//                    rtnData.addAll(resultData.getData());
//                }
//            }
            System.out.println(" ***** " + Thread.currentThread().getStackTrace()[1].getMethodName() + " 查询结束耗时(s): " + (System.currentTimeMillis() - startTimer) / 1000.0);
            return new AsyncResult<List<WarehouseInventoryVO>>(rtnData);
        } catch (Exception ex) {
            log.error(Thread.currentThread().getStackTrace()[1].getMethodName() + "->错误：" + ex);
            System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName() + "->错误：" + ex);
            return new AsyncResult<List<WarehouseInventoryVO>>(null);
        }
    }

    /**
     * 拆成多个批量，异步查询Bin数据
     * add by WuWeiDong 20230327
     *
     * @param modelNoList
     * @param warehouseCodeList
     * @return
     */

    @Async
    @Override
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public Future<List<BindataDO>> asyncGetBinDataByModels(List<String> modelNoList, List<String> warehouseCodeList) {
        if (CollectionUtil.isEmpty(modelNoList) || CollectionUtil.isEmpty(warehouseCodeList)) {
            return new AsyncResult<List<BindataDO>>(null);
        }
        try {
            Integer count = modelNoList.size();
            Integer offset = 1000 - 1;
            List<BindataDO> rtnData = new ArrayList<>(modelNoList.size());
            List<String> queryParm = new ArrayList<>();
            LambdaQueryWrapper<BindataDO> query = new LambdaQueryWrapper<>();

            List<List<String>> partition = ListUtils.partition(modelNoList, 900);
            for (List<String> item: partition) {
                query.clear();
                query.select(BindataDO::getWarehouseCode, BindataDO::getModelNo, BindataDO::getQtyBin, BindataDO::getBinCell, BindataDO::getFreq,
                        BindataDO::getNewMean, BindataDO::getStockType);
                query.in(BindataDO::getModelNo, item)
                        .in(BindataDO::getWarehouseCode, warehouseCodeList)
                        .eq(BindataDO::getStockType, 1)
                        .eq(BindataDO::getDelFlag, 0);
                List<BindataDO> resultData = bindataMapper.selectList(query);
                if (CollectionUtil.isNotEmpty(resultData)) {
                    rtnData.addAll(resultData);
                }
            }
//            for (int i = 0; i < count; i++) {
//                if ((i + offset) > count) {
//                    queryParm = modelNoList.subList(i, count);
//                } else {
//                    queryParm = modelNoList.subList(i, i + offset + 1);
//                }
//                i = i + offset;
//                query.clear();
//                query.select(BindataDO::getWarehouseCode, BindataDO::getModelNo, BindataDO::getQtyBin, BindataDO::getBinCell, BindataDO::getFreq,
//                        BindataDO::getNewMean, BindataDO::getStockType);
//                query.in(BindataDO::getModelNo, queryParm)
//                        .in(BindataDO::getWarehouseCode, warehouseCodeList)
//                        .eq(BindataDO::getStockType, 1)
//                        .eq(BindataDO::getDelFlag, 0);
//                List<BindataDO> resultData = bindataMapper.selectList(query);
//                if (CollectionUtil.isNotEmpty(resultData)) {
//                    rtnData.addAll(resultData);
//                }
//            }
            return new AsyncResult<List<BindataDO>>(rtnData);
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error(Thread.currentThread().getStackTrace()[1].getMethodName() + "->错误：" + ex);
            return new AsyncResult<List<BindataDO>>(null);
        }
    }

}
