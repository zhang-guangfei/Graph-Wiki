package com.smc.smccloud.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.smc.smccloud.core.exception.BusinessException;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.model.adapter.DataAuthoritySearchCondition;
import com.smc.smccloud.core.model.constants.GlobalConstant;
import com.smc.smccloud.core.utils.*;
import com.smc.smccloud.model.adapter.*;
import com.smc.smccloud.model.bindata.BindataDO;
import com.smc.smccloud.model.bindata.BindataVO;
import com.smc.smccloud.model.binorder.ModelExpFreqDO;
import com.smc.smccloud.model.customer.CustomerVO;
import com.smc.smccloud.model.inventory.*;
import com.smc.smccloud.model.shikomi.ShikomiVO;
import com.smc.smccloud.service.*;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

/**
 * Author: B90034
 * Date: 2022-03-17 13:55
 * Description:
 */
@Slf4j
@Service
public class SMSAdapterServiceImpl implements SMSAdapterService {

    @Resource
    private BindataService bindataService;
    @Resource
    private CommonService commonService;
    @Resource
    private OpsCommonService opsCommonService;
    @Resource
    private InventoryServiceFeignApi inventoryServiceFeignApi;

    @Resource
    private ProductServiceFeignApi productServiceFeignApi;


    @Override
    public Map<String, BinModelInfo> findBinModelInfo(BinModelCondition condition) {
        log.info("#findBinModelInfo condition = {}", condition);
        if (CollectionUtils.isEmpty(condition.getModelNoList())) {
            return Collections.emptyMap();
        }

        // 查询大库BIN数
        List<BindataVO> binDataList = bindataService.listCustomerBinModel(condition.getCustomerNo(), condition.getModelNoList());
        Map<String, List<BinModelInfo>> infoMap = new HashMap<>(condition.getModelNoList().size());
        List<BinModelInfo> binModelList;
        BinModelInfo info;

        for (BindataVO bindataInfo : binDataList) {
            if (bindataInfo.getMean() == null) {
                bindataInfo.setMean(0);
            }
            if (bindataInfo.getFreq() == null) {
                bindataInfo.setFreq(0);
            }
            if (bindataInfo.getEprice() == null) {
                bindataInfo.setEprice(BigDecimal.ZERO);
            }

            binModelList = infoMap.getOrDefault(bindataInfo.getModelNo(), new ArrayList<>());
            info = new BinModelInfo();
            info.setModelNo(bindataInfo.getModelNo());
            info.setCustomerNo(condition.getCustomerNo());
            info.setSaleRate(BigDecimal.ZERO);
            info.setSaleQuantity(0);
            info.setSaleAmount(BigDecimal.ZERO);
            if ("KBJ".equals(bindataInfo.getWarehouseCode())) {
                info.setBinQuantityBJ(bindataInfo.getBinCell()); // 北京BIN数
            }
            if ("SCZ".equals(bindataInfo.getWarehouseCode())) {
                info.setBinQuantityCZ(bindataInfo.getBinCell()); // 常州BIN数
            }
            if ("KSH".equals(bindataInfo.getWarehouseCode())) {
                info.setBinQuantitySH(bindataInfo.getBinCell()); // 上海BIN数
            }
            if ("KGZ".equals(bindataInfo.getWarehouseCode())) {
                info.setBinQuantityGZ(bindataInfo.getBinCell()); // 广州BIN数
            }
            // 销售频率 = 频率（在12/36个月内有销售的月数）
            info.setSaleRate(info.getSaleRate().add(new BigDecimal(String.valueOf(bindataInfo.getFreq()))));
            // 历史销售数量 = 频率 * 月用量
            info.setSaleQuantity(info.getSaleQuantity() + (bindataInfo.getFreq() * bindataInfo.getMean()));
            // 历史销售额 = 频率 * 月用量 * E价
            info.setSaleAmount(info.getSaleAmount().add(new BigDecimal(String.valueOf(info.getSaleQuantity())).multiply(bindataInfo.getEprice())));
            info.setApplyHairNoteQuantity(bindataInfo.getQtyBin()); // 业务建议发注点
            info.setQtyOrd(bindataInfo.getReplQty()); // 业务建议补库数量
            info.setBomNo(bindataInfo.getPpl());
            info.setProjectCode(bindataInfo.getProjectNo());
            binModelList.add(info);
            infoMap.put(bindataInfo.getModelNo(), binModelList);
        }

        // 查询客户BIN型号在库信息
        InventoryRequestDTO dto = new InventoryRequestDTO();
        dto.setCustomerNo(condition.getCustomerNo());
        dto.setModelNos(condition.getModelNoList());
        ResultVo<List<CustomerModelStockVO>> stockResult = inventoryServiceFeignApi.listCustomerBinModelInventory(dto);
        if (!stockResult.isSuccess()) {
            log.error("查询客户Bin型号通用在库和客户专备失败: {}", stockResult);
            throw new BusinessException("查询通用在库和客户专备失败: " + stockResult.getMessage());
        }
        Map<String, List<CustomerModelStockVO>> stockInfoMap = stockResult.getData().stream().collect(Collectors.groupingBy(CustomerModelStockVO::getModelNo));

        Map<String, BinModelInfo> result = new HashMap<>(condition.getModelNoList().size());
        for (String modelNo : condition.getModelNoList()) {
            if (infoMap.containsKey(modelNo)) {
                int index = 0;
                info = infoMap.get(modelNo).get(0);
                for (BinModelInfo temp : infoMap.get(modelNo)) {
                    index++;
                    if (index == 1) {
                        continue;
                    }
                    info.setBomNo(temp.getBomNo());
                    info.setProjectCode(temp.getProjectCode());
                    info.setBinQuantityBJ(info.getBinQuantityBJ() + temp.getBinQuantityBJ()); // 北京BIN数
                    info.setBinQuantityCZ(info.getBinQuantityCZ() + temp.getBinQuantityCZ()); // 常州BIN数
                    info.setBinQuantitySH(info.getBinQuantitySH() + temp.getBinQuantitySH()); // 上海BIN数
                    info.setBinQuantityGZ(info.getBinQuantityGZ() + temp.getBinQuantityGZ()); // 广州BIN数
                }
            } else {
                info = new BinModelInfo();
                info.setModelNo(modelNo);
                info.setCustomerNo(condition.getCustomerNo());
                info.setSaleRate(BigDecimal.ZERO);
                info.setSaleQuantity(0);
                info.setSaleAmount(BigDecimal.ZERO);
                info.setApplyHairNoteQuantity(0);
                info.setQtyOrd(0);
            }

            if (stockInfoMap.containsKey(modelNo)) {
                for (CustomerModelStockVO stockInfo : stockInfoMap.get(modelNo)) {
                    if (StringUtils.isNotBlank(stockInfo.getPpl())) {
                        info.setBomNo(stockInfo.getPpl()); // 设备bom号
                    }
                    if (StringUtils.isNotBlank(stockInfo.getProjectCode())) {
                        info.setProjectCode(stockInfo.getProjectCode()); // 项目号
                    }
                    info.setInventoryQuantity(info.getInventoryQuantity() + stockInfo.getTyAvaQty()); // 通用在库数
                    info.setCustomerQuantity(info.getCustomerQuantity() + stockInfo.getZyAvaQty()); // 客户在库数
                    info.setCustomerInOrderingQuantity(info.getCustomerInOrderingQuantity() + stockInfo.getOrderingQty()); // 客户在库在途数
                }
            }

            result.put(modelNo, info);
        }

        return result;
    }

//    @Override
//    public Map<String, BinModelInfo> findBinModelInfo(BinModelCondition condition) {
//        log.info("findBinModelInfo condition = {}", condition);
//        if (CollectionUtils.isEmpty(condition.getModelNoList())) {
//            return Collections.emptyMap();
//        }
//
//        // 查询大库BIN数
//        List<BindataVO> binDataList = bindataService.listCustomerBinModel(condition.getCustomerNo(), condition.getModelNoList());
//        Map<String, BinModelInfo> infoMap = new HashMap<>(condition.getModelNoList().size());
//        String binModel;
//        BinModelInfo info;
//        Map<Long, List<String>> propertyIdMap = new HashMap<>();
//        Map<String, Integer> meanMap = new HashMap<>(binDataList.size());
//        List<String> modelNoList;
//        int mean; // 月用量
//
//
//        for (BindataVO bindataInfo : binDataList) {
//            /*if (Optional.ofNullable(bindataInfo.getReplQty()).orElse(0) == 0) {
//                continue;
//            }*/
//            if (bindataInfo.getMean() == null) {
//                bindataInfo.setMean(0);
//            }
//            if (bindataInfo.getFreq() == null) {
//                bindataInfo.setFreq(0);
//            }
//            if (bindataInfo.getEprice() == null) {
//                bindataInfo.setEprice(BigDecimal.ZERO);
//            }
//            mean = 0;
//            if (meanMap.containsKey(bindataInfo.getModelNo())) {
//                mean = meanMap.get(bindataInfo.getModelNo());
//            }
//            mean += Optional.ofNullable(bindataInfo.getMean()).orElse(0);
//            meanMap.put(bindataInfo.getModelNo(), mean);
//            if (propertyIdMap.containsKey(bindataInfo.getPropertyID())) {
//                modelNoList = propertyIdMap.get(bindataInfo.getPropertyID());
//            } else {
//                modelNoList = new ArrayList<>();
//                propertyIdMap.put(bindataInfo.getPropertyID(), modelNoList);
//            }
//            modelNoList.add(bindataInfo.getModelNo());
//
//            binModel = bindataInfo.getPropertyID() + ":" + bindataInfo.getModelNo();
//            info = new BinModelInfo();
//            info.setModelNo(bindataInfo.getModelNo());
//            info.setCustomerNo(condition.getCustomerNo());
//            info.setSaleRate(BigDecimal.ZERO);
//            info.setSaleQuantity(0);
//            info.setSaleAmount(BigDecimal.ZERO);
//            if ("KBJ".equals(bindataInfo.getWarehouseCode())) {
//                info.setBinQuantityBJ(bindataInfo.getBinCell()); // 北京BIN数
//            }
//            if ("KSH".equals(bindataInfo.getWarehouseCode())) {
//                info.setBinQuantitySH(bindataInfo.getBinCell()); // 上海BIN数
//            }
//            if ("KGZ".equals(bindataInfo.getWarehouseCode())) {
//                info.setBinQuantityGZ(bindataInfo.getBinCell()); // 广州BIN数
//            }
//            // 销售频率 = 频率（在12/36个月内有销售的月数）
//            info.setSaleRate(info.getSaleRate().add(new BigDecimal(String.valueOf(bindataInfo.getFreq()))));
//            // 历史销售数量 = 频率 * 月用量
//            info.setSaleQuantity(info.getSaleQuantity() + (bindataInfo.getFreq() * bindataInfo.getMean()));
//            // 历史销售额 = 频率 * 月用量 * E价
//            info.setSaleAmount(info.getSaleAmount().add(new BigDecimal(String.valueOf(info.getSaleQuantity())).multiply(bindataInfo.getEprice())));
//            info.setApplyHairNoteQuantity(bindataInfo.getQtyBin()); // 业务建议发注点
//            info.setQtyOrd(bindataInfo.getReplQty()); // 业务建议补库数量
//            info.setBomNo(bindataInfo.getPpl());
//            info.setProjectCode(bindataInfo.getProjectNo());
//            infoMap.put(binModel, info);
//        }
//
//        this.handleBinModelInfo(condition.getCustomerNo(), infoMap, propertyIdMap, meanMap);
//
//        Map<String, BinModelInfo> result = new HashMap<>(condition.getModelNoList().size());
//        Map<String, List<BinModelInfo>> map = infoMap.values().stream().collect(Collectors.groupingBy(BinModelInfo::getModelNo));
//        for (String modelNo : condition.getModelNoList()) {
//            info = new BinModelInfo();
//            info.setModelNo(modelNo);
//            info.setCustomerNo(condition.getCustomerNo());
//            info.setSaleRate(BigDecimal.ZERO);
//            info.setSaleQuantity(0);
//            info.setSaleAmount(BigDecimal.ZERO);
//            info.setApplyHairNoteQuantity(0);
//            info.setQtyOrd(0);
//            if (map.containsKey(modelNo)) {
//                for (BinModelInfo temp : map.get(modelNo)) {
//                    info.setBomNo(temp.getBomNo());
//                    info.setProjectCode(temp.getProjectCode());
//                    info.setInventoryQuantity(info.getInventoryQuantity() + temp.getInventoryQuantity()); // 通用在库数
//                    info.setCustomerQuantity(info.getCustomerQuantity() + temp.getCustomerQuantity()); // 客户在库数
//                    info.setCustomerInOrderingQuantity(info.getCustomerInOrderingQuantity() + temp.getCustomerInOrderingQuantity()); // 客户在库在途数
//                    info.setBinQuantityBJ(info.getBinQuantityBJ() + temp.getBinQuantityBJ()); // 北京BIN数
//                    info.setBinQuantitySH(info.getBinQuantitySH() + temp.getBinQuantitySH()); // 上海BIN数
//                    info.setBinQuantityGZ(info.getBinQuantityGZ() + temp.getBinQuantityGZ()); // 广州BIN数
//                }
//            }
//            result.put(modelNo, info);
//        }
//
//        return result;
//    }

    @Override
    public List<AutoBinInfo> findAutoBinCustomerInfo(DataAuthoritySearchCondition condition) {
        log.info("#findAutoBinCustomerInfo condition = {}", condition);
        List<Map<String, Object>> listMap = bindataService.listBinCustomerInfo(condition);
        if (CollectionUtils.isEmpty(listMap)) {
            return Collections.emptyList();
        }
        JSONArray jsonArray = new JSONArray();
        jsonArray.addAll(listMap);
        return jsonArray.toJavaList(AutoBinInfo.class);
    }

    @Override
    public List<BinModelInfo> findAutoBinInfoByCustomer(DataAuthoritySearchCondition condition, String customerNo) {
        log.info("#findAutoBinInfoByCustomer condition = {}, customerNo = {}", condition, customerNo);
        CustomerVO customerVo = commonService.getCustomerInfoByNo(customerNo);
        if (customerVo == null) {
            throw new BusinessException("查询客户信息失败: null");
        }
        if (CollectionUtils.isNotEmpty(condition.getUserIdListByDataAuthority())
                && !condition.getUserIdListByDataAuthority().contains(customerVo.getPSNSMCID())) {
            return Collections.emptyList();
        }
        if (CollectionUtils.isNotEmpty(condition.getDeptCodeListByDataAuthority())
                && !condition.getDeptCodeListByDataAuthority().contains(customerVo.getHRUnitId())) {
            return Collections.emptyList();
        }
        if (CollectionUtils.isNotEmpty(condition.getCustomerCodeListByDataAuthority())
                && !condition.getCustomerCodeListByDataAuthority().contains(customerVo.getCustomerNo())) {
            return Collections.emptyList();
        }

        List<BindataVO> binDataList = bindataService.listCustomerBinModel(customerNo, null);
        if (CollectionUtils.isEmpty(binDataList)) {
            return Collections.emptyList();
        }

        Map<String, BinModelInfo> binModelMap = new HashMap<>();
        Map<String, Integer> meanMap = new HashMap<>();
        StringBuilder binModel = new StringBuilder(70);
        BinModelInfo info;
        Set<String> modelNoSet = new HashSet<>(binDataList.size());
        int mean; // 月用量

        for (BindataVO bindataInfo : binDataList) {
            if (Optional.ofNullable(bindataInfo.getReplQty()).orElse(0) == 0) {
                continue;
            }
            if (bindataInfo.getMean() == null) {
                bindataInfo.setMean(0);
            }
            if (bindataInfo.getFreq() == null) {
                bindataInfo.setFreq(0);
            }
            if (bindataInfo.getEprice() == null) {
                bindataInfo.setEprice(BigDecimal.ZERO);
            }

            mean = 0;
            if (meanMap.containsKey(bindataInfo.getModelNo())) {
                mean = meanMap.get(bindataInfo.getModelNo());
            }
            mean += Optional.ofNullable(bindataInfo.getMean()).orElse(0);
            meanMap.put(bindataInfo.getModelNo(), mean);

            binModel.setLength(0);
            binModel.append(bindataInfo.getInventoryTypeCode()).append(":").append(bindataInfo.getModelNo());
            info = new BinModelInfo();
            info.setModelNo(bindataInfo.getModelNo());
            info.setCustomerNo(customerNo);
            info.setCustomerName(customerVo.getName());
            info.setSaleRate(BigDecimal.ZERO);
            info.setSaleQuantity(0);
            info.setSaleAmount(BigDecimal.ZERO);
            if ("KBJ".equals(bindataInfo.getWarehouseCode())) {
                info.setBinQuantityBJ(bindataInfo.getBinCell()); // 北京BIN数
            }
            if ("SCZ".equals(bindataInfo.getWarehouseCode())) {
                info.setBinQuantityCZ(bindataInfo.getBinCell()); // 常州BIN数
            }
            if ("KSH".equals(bindataInfo.getWarehouseCode())) {
                info.setBinQuantitySH(bindataInfo.getBinCell()); // 上海BIN数
            }
            if ("KGZ".equals(bindataInfo.getWarehouseCode())) {
                info.setBinQuantityGZ(bindataInfo.getBinCell()); // 广州BIN数
            }
            // 销售频率 = 频率（在12/36个月内有销售的月数）
            info.setSaleRate(info.getSaleRate().add(new BigDecimal(String.valueOf(bindataInfo.getFreq()))));
            // 历史销售数量 = 频率 * 月用量
            info.setSaleQuantity(info.getSaleQuantity() + (bindataInfo.getFreq() * bindataInfo.getMean()));
            // 历史销售额 = 频率 * 月用量 * E价
            info.setSaleAmount(info.getSaleAmount().add(new BigDecimal(String.valueOf(info.getSaleQuantity())).multiply(bindataInfo.getEprice())));
            info.setApplyHairNoteQuantity(bindataInfo.getQtyBin()); // 业务建议发注点
            info.setQtyOrd(bindataInfo.getReplQty()); // 业务建议补库数量
            info.setInventoryTypeCode(bindataInfo.getInventoryTypeCode());
            info.setBomNo(bindataInfo.getPpl());
            info.setProjectCode(bindataInfo.getProjectNo());
            info.setGroupCustomerNo(bindataInfo.getGroupCustomerNo());
            binModelMap.put(binModel.toString(), info);
            modelNoSet.add(bindataInfo.getModelNo());
        }

        // 设置BinInfo
        List<String> modelList = new ArrayList<>(modelNoSet);
        this.handleBinModelInfo(customerNo, binModelMap, modelList, meanMap);

        return new ArrayList<>(binModelMap.values());
    }

    @Override
    public boolean cancelCustomerBinAutoReple(UpdateAutoStatusCondition condition) {
        log.info("#cancelCustomerBinAutoReple condition = {}", condition);
        if (StringUtils.isBlank(condition.getCustomerNo()) || CollectionUtils.isEmpty(condition.getModelNoList())) {
            return false;
        }
        CustomerVO customerVo = commonService.getCustomerInfoByNo(condition.getCustomerNo());
        if (customerVo == null) {
            throw new BusinessException("查询客户信息失败: null");
        }
        String warehouseCode = bindataService.getDeptPriorityStock(customerVo.getHRUnitId());

        return bindataService.cancelCustomerBinAutoReple(warehouseCode, condition.getCustomerNo(), condition.getModelNoList());
    }

    @Override
    public boolean updateCustomerBinAutoRepleStatus(UpdateAutoStatusCondition condition) {
        log.info("#updateCustomerBinAutoRepleStatus condition = {}", condition);
        if (StringUtils.isBlank(condition.getCustomerNo()) || CollectionUtils.isEmpty(condition.getModelNoList())) {
            return false;
        }
        CustomerVO customerVo = commonService.getCustomerInfoByNo(condition.getCustomerNo());
        if (customerVo == null) {
            throw new BusinessException("查询客户信息失败: null");
        }
        String warehouseCode = bindataService.getDeptPriorityStock(customerVo.getHRUnitId());

        // 启动自动补库时传值为1, 取消自动补库时不传值为NULL
        int status = StringUtils.isBlank(condition.getOptCode()) ? 0 : 1;

        return bindataService.updateCustomerBinAutoRepleStatus(warehouseCode, condition.getCustomerNo(), condition.getModelNoList(),
                status);
    }

    @Override
    public List<BinInfo> getBinInfoByModelNo(String modelNo) {
        log.info("#getBinInfoByModelNo modelNo = {}", modelNo);
        List<BindataVO> list = bindataService.listBinInfoByModelNo(modelNo);
        if (CollectionUtils.isEmpty(list)) {
            return Collections.emptyList();
        }
        List<BinInfo> infoList = new ArrayList<>(list.size());
        BinInfo info;
        Map<String, String> nameMap = new HashMap<>();
        String warehouseName;
        for (BindataVO bindataVO : list) {
            info = new BinInfo();
            info.setModelNo(bindataVO.getModelNo());
            info.setStockCode(bindataVO.getWarehouseCode());
            info.setCustomerNo(bindataVO.getCustomerNo());
            info.setQtyBin(bindataVO.getQtyBin().toString());
            info.setSafeQty(bindataVO.getSafeQty());
            info.setBinCell(bindataVO.getBinCell().toString());
            if (nameMap.containsKey(info.getStockCode())) {
                warehouseName = nameMap.get(info.getStockCode());
            } else {
                warehouseName = opsCommonService.getWarehouseNameByCode(info.getStockCode());
                nameMap.put(info.getStockCode(), warehouseName);
            }
            info.setStockName(warehouseName);
            infoList.add(info);
        }
        return infoList;
    }

    // Add by dengdenghui 2022-11-22 for bug-8738
    @Override
    public ResultVo<Map<String, List<Product>>> listModelBinOrGSSInfo(ProductCondition condition) {
        List<String> warehouseCodeList;
        if (StringUtils.isBlank(condition.getWarehouseCode())) {
            warehouseCodeList = commonService.getMasterWarehouseCodes();
        } else {
            warehouseCodeList = Collections.singletonList(condition.getWarehouseCode());
        }

        List<Product> resultList = new ArrayList<>(condition.getModelList().size() * warehouseCodeList.size());
        List<Product> bindataList;
        List<String> temp;
        int i = 0;

        while (i < condition.getModelList().size()) {
            if (i == 0 && condition.getModelList().size() < 2001) {
                temp = condition.getModelList();
            } else {
                if (condition.getModelList().size() > (i + 2000)) {
                    temp = condition.getModelList().subList(i, i + 2000);
                } else {
                    temp = condition.getModelList().subList(i, condition.getModelList().size());
                }
            }
            i += temp.size();

            // 查询BIN品和GSS品
            bindataList = bindataService.listBinAndGSS(warehouseCodeList, temp);
            resultList.addAll(bindataList);
        }

        Map<String, String> nameMap = new HashMap<>();
        String warehouseName;

        for (Product info : resultList) {
            if ("1".equals(info.getModelType())) {
                info.setModelType("K");
                info.setModelTypeDesc("BIN品");
            }
            if ("2".equals(info.getModelType())) {
                info.setModelType("G");
                info.setModelTypeDesc("GSS品");
            }
            if (!nameMap.containsKey(info.getStockCode())) {
                warehouseName = opsCommonService.getWarehouseNameByCode(info.getStockCode());
                if (StringUtils.isNotBlank(warehouseName)) {
                    nameMap.put(info.getStockCode(), warehouseName);
                }
            }
            info.setStock(nameMap.getOrDefault(info.getStockCode(), ""));
        }
        // 按型号分组
        Map<String, List<Product>> resultMap = resultList.stream().collect(Collectors.groupingBy(Product::getModelNo));

        return ResultVo.success(resultMap);
    } // End

    @Override
    public ResultVo<List<Product>> listBinOrGSS(ProductCondition condition) {
        if (StringUtils.isBlank(condition.getDeptNo())) {
            return ResultVo.failure("部门代码不能为空");
        }
        String warehouseCode = bindataService.getDeptPriorityStock(this.converDeptCode(condition.getDeptNo()));
        List<String> warehouseCodeList = Collections.singletonList(warehouseCode);

        List<Product> resultList = new ArrayList<>(condition.getModelList().size());
        List<Product> bindataList;
        List<String> temp;
        int i = 0;

        while (i < condition.getModelList().size()) {
            if (i == 0 && condition.getModelList().size() < 2001) {
                temp = condition.getModelList();
            } else {
                if (condition.getModelList().size() > (i + 2000)) {
                    temp = condition.getModelList().subList(i, i + 2000);
                } else {
                    temp = condition.getModelList().subList(i, condition.getModelList().size());
                }
            }
            i += temp.size();

            // 查询BIN品和GSS品
            bindataList = bindataService.listBinAndGSS(warehouseCodeList, temp);
            resultList.addAll(bindataList);
        }

        for (Product info : resultList) {
            if ("1".equals(info.getModelType())) {
                info.setModelType("K");
                info.setModelTypeDesc("BIN品");
            }
            if ("2".equals(info.getModelType())) {
                info.setModelType("G");
                info.setModelTypeDesc("GSS品");
            }
        }
        return ResultVo.success(resultList);
    }


    @Override
    public ResultVo<List<Product>> productInfo(ProductCondition condition) {
        if (StringUtils.isBlank(condition.getDeptNo())) {
            return ResultVo.failure("部门代码不能为空");
        }
        if (CollectionUtils.isEmpty(condition.getModelCalList())) {
            return ResultVo.success(Collections.emptyList());
        }
        Date startTime = new Date();

        List<String> modelNoList = new ArrayList<>(condition.getModelCalList().size());
        List<String> stockModelList = new ArrayList<>(condition.getModelCalList().size());
        for (ModelCalCondition item : condition.getModelCalList()) {
            modelNoList.add(item.getModelNo());
            if (item.isCal()) {
                stockModelList.add(item.getModelNo());
            }
        }
        List<String> warehouseCodeList = bindataService.listDeptPriorityStock(this.converDeptCode(condition.getDeptNo()));
        if (CollectionUtils.isEmpty(warehouseCodeList)) {
            log.info("#productInfo 未能找到此部门的出库规则 deptNo = {}", condition.getDeptNo());
            return ResultVo.failure("未能找到该部门的出库规则");
        }
        // 获取客户的委托在库仓库
        if (StringUtils.isNotBlank(condition.getCustomerNo())) {
            List<String> wtWarehouseCode = bindataService.getWTWarehouseByCustomerNo(condition.getCustomerNo());
            if (CollectionUtils.isNotEmpty(wtWarehouseCode)) {
                warehouseCodeList.addAll(wtWarehouseCode);
            }
        }
        List<String> binWarehouseList = Collections.singletonList(warehouseCodeList.get(0));

        List<String> temp;
        int i = 0;
        List<Product> resultList = new ArrayList<>(modelNoList.size());
        List<Product> binDataTempList;
        while (i < modelNoList.size()) {
            if (modelNoList.size() < 2001) {
                temp = modelNoList;
            } else {
                if (modelNoList.size() > (i + 2000)) {
                    temp = modelNoList.subList(i, i + 2000);
                } else {
                    temp = modelNoList.subList(i, modelNoList.size());
                }
            }
            i += temp.size();

            // 查询BIN品和GSS品
            binDataTempList = bindataService.listBinAndGSS(binWarehouseList, temp);
            resultList.addAll(binDataTempList);
        }

        // 查询仓库型号的有效库存
        Map<String, Integer> stockMap = new HashMap<>(stockModelList.size());
        if (CollectionUtils.isNotEmpty(stockModelList)) {
            i = 0;
            ResultVo<List<InventoryVO>> stockResult;
            ModelWarehouseStockRequest dto = new ModelWarehouseStockRequest();
            dto.setCustomerNo(condition.getCustomerNo());
            dto.setWarehouseCodeList(warehouseCodeList);

            while (i < stockModelList.size()) {
                if (stockModelList.size() < 1001) {
                    temp = stockModelList;
                } else {
                    if (stockModelList.size() > (i + 1000)) {
                        temp = stockModelList.subList(i, i + 1000);
                    } else {
                        temp = stockModelList.subList(i, stockModelList.size());
                    }
                }
                i += temp.size();

                dto.setModelNos(temp);
                stockResult = inventoryServiceFeignApi.listCanUseInventory(dto);
                if (!stockResult.isSuccess()) {
                    return ResultVo.failure("查询BIN型号有效库存失败");
                }
                for (InventoryVO stockDTO : stockResult.getData()) {
                    if(stockMap.containsKey(stockDTO.getModelNo())) {
                        stockMap.put(stockDTO.getModelNo(), stockMap.get(stockDTO.getModelNo()) + stockDTO.getAvaQty());
                    } else {
                        stockMap.put(stockDTO.getModelNo(), stockDTO.getAvaQty());
                    }
                }
            }
        }

        temp = new ArrayList<>(resultList.size());
        for (Product info : resultList) {
            if ("1".equals(info.getModelType())) {
                info.setModelType("K");
                info.setModelTypeDesc("BIN品");
            }
            if ("2".equals(info.getModelType())) {
                info.setModelType("G");
                info.setModelTypeDesc("GSS品");
            }
            info.setCanUseQuantity(stockMap.getOrDefault(info.getModelNo(), 0));
            temp.add(info.getModelNo());
        }

        // Edit by B98075 2022-12-21 for bug-9088
        List<String> finalTemp = temp;
        List<String> subtract = modelNoList.stream().filter(a -> !finalTemp.contains(a)).collect(Collectors.toList());
        Product result;
        for (String modelNo : subtract) {
            result = new Product();
            result.setModelNo(modelNo);
            result.setModelType("");
            if (stockModelList.contains(modelNo)) {
                result.setCanUseQuantity(stockMap.getOrDefault(modelNo, 0));
            } else {
                result.setCanUseQuantity(0);
            }
            resultList.add(result);
        } // End
        log.info("查询bin品和库存 startTime-{}, 耗时: {}(s)", DateUtil.dateToDateTimeString(startTime), DateUtil.getDiffSecond(startTime, new Date()));
        return ResultVo.success(resultList);
    }

    //    <!--add by WuWeiDong 20230714  bug 11274  专备补库当前库存查询统一接口 -->
    //    <!--add by WuWeiDong 20231008  bug 12285  完善优先在库的门户接口。 -->
    @Override
    public ResultVo<List<StockInfoForReplVO>> listStockForRepl(StockQueryForReplDTO dto) {
        log.info("listStockForRepl ==>传入参数:{}", JSONObject.toJSONString(dto));

        ResultVo<String> resultVo = this.checkInventoryProperty(dto);
        if (!resultVo.isSuccess()) {
            return ResultVo.failure(resultVo.getMessage());
        }
        long startTimer = System.currentTimeMillis();
        List<StockInfoForReplVO> list = new ArrayList<>();
        StockInfoForReplVO stockInfoForReplVO = new StockInfoForReplVO();
        List<StockInfoByWarehouseForReplVO> warehouseForReplVOS = new ArrayList<>();
        Map<String, List<StockInfoByWarehouseForReplVO>> mapReplVO = new HashMap<>();
        List<BindataDO> bindataDOList = new ArrayList<>();
        List<BindataDO> checkBindataList = new ArrayList<>();
        List<String> masterWarehouseList = commonService.getMasterWarehouseCodes();
        ;
        OpsInventoryPropertyRequestDTO propertyRequestDTO = new OpsInventoryPropertyRequestDTO();
        OpsInventoryPropertyRequestDTO requestDTO = new OpsInventoryPropertyRequestDTO();

        requestDTO.setModelNos(dto.getModelNos());
        requestDTO.setInventoryTypeCode(dto.getInventoryTypeCode());
        requestDTO.setCustomerNo(dto.getCustomerNo());
        requestDTO.setPpl(dto.getPpl());
        requestDTO.setProjectCode(dto.getProjectCode());
        requestDTO.setGroupCustomerNo(dto.getGroupCustomerNo());
        requestDTO.setSalesInfoNo(dto.getSalesInfoNo());

        try {

            propertyRequestDTO = new OpsInventoryPropertyRequestDTO();
            propertyRequestDTO.setModelNos(dto.getModelNos());
            ExecutorService executorService = Executors.newFixedThreadPool(3);
            OpsInventoryPropertyRequestDTO requestAll = propertyRequestDTO;
            //  查在库数
            Future<List<OpsInventoryVO>> futureInventoryQuantityAll = executorService.submit(new Callable<List<OpsInventoryVO>>() {
                @Override
                public List<OpsInventoryVO> call() throws Exception {
                    return getCanUseInventoryByProperty(requestAll);
                }
            });

            //  查在途数
            Future<List<OpsInventoryVO>> futureInventoryMoveAll = executorService.submit(new Callable<List<OpsInventoryVO>>() {
                @Override
                public List<OpsInventoryVO> call() throws Exception {
                    return getInventoryMoveByProperty(requestAll);
                }
            });

            //取物流中心bin数据，用于判断是否为bin
            Future<List<BindataDO>> futureCheckBinData = executorService.submit(new Callable<List<BindataDO>>() {
                @Override
                public List<BindataDO> call() throws Exception {
                    return commonService.asyncGetBinDataByModels(dto.getModelNos(), masterWarehouseList).get();
                }
            });
            executorService.shutdown();
            while (true) {
                if (executorService.isTerminated()) {
                    break;
                }
                Thread.sleep(100);
            }
            List<OpsInventoryVO> inventoryQuantityAll = futureInventoryQuantityAll.get();
            List<OpsInventoryVO> inventoryMoveListAll = futureInventoryMoveAll.get();
            checkBindataList = futureCheckBinData.get(); //用于判断是否为bin

            // **先行在库数据
            List<OpsInventoryVO> inventoryQuantitySPEC = new ArrayList<>();
            List<OpsInventoryVO> inventoryMoveListSPEC = new ArrayList<>();

            if ("TY".equalsIgnoreCase(dto.getInventoryTypeCode())) {
                if (PublicUtil.isNotEmpty(inventoryQuantityAll) && inventoryQuantityAll.size() > 0) {
                    inventoryQuantitySPEC = inventoryQuantityAll.stream().filter(i -> "SPEC".equalsIgnoreCase(i.getClassCode())).collect(Collectors.toList());
                }
                if (PublicUtil.isNotEmpty(inventoryMoveListAll) && inventoryMoveListAll.size() > 0) {
                    inventoryMoveListSPEC = inventoryMoveListAll.stream().filter(i -> "SPEC".equalsIgnoreCase(i.getClassCode())).collect(Collectors.toList());
                }
            } else {
                OpsInventoryPropertyRequestDTO requestSPEC = requestDTO;
                executorService = Executors.newFixedThreadPool(2);
                //  查在库数
                Future<List<OpsInventoryVO>> futureInventoryQuantitySPEC = executorService.submit(new Callable<List<OpsInventoryVO>>() {
                    @Override
                    public List<OpsInventoryVO> call() throws Exception {
                        return getCanUseInventoryByProperty(requestSPEC);
                    }
                });

                //  查在途数
                Future<List<OpsInventoryVO>> futureInventoryMoveSPEC = executorService.submit(new Callable<List<OpsInventoryVO>>() {
                    @Override
                    public List<OpsInventoryVO> call() throws Exception {
                        return getInventoryMoveByProperty(requestSPEC);
                    }
                });

                executorService.shutdown();
                while (true) {
                    if (executorService.isTerminated()) {
                        break;
                    }
                    Thread.sleep(100);
                }
                inventoryQuantitySPEC = futureInventoryQuantitySPEC.get();
                inventoryMoveListSPEC = futureInventoryMoveSPEC.get();

            }

            //**各个仓库信息
            if (PublicUtil.isNotEmpty(inventoryQuantityAll) && inventoryQuantityAll.size() > 0) {

                List<String> modelNos = inventoryQuantityAll.stream().filter(i -> "TY".equalsIgnoreCase(i.getClassCode())).map(OpsInventoryVO::getModelNo).distinct().collect(Collectors.toList());
                List<String> warehouses = inventoryQuantityAll.stream().filter(i -> "TY".equalsIgnoreCase(i.getClassCode())).map(OpsInventoryVO::getWarehouseCode).distinct().collect(Collectors.toList());
                //bin数量
                if (dto.getIsQryBindata()) {
                    if (dto.getModelNos().equals(modelNos) && masterWarehouseList.equals(warehouses)) {
                        bindataDOList = checkBindataList;
                    } else {
                        bindataDOList = commonService.asyncGetBinDataByModels(modelNos, warehouses).get();

                    }
                }

                for (String modelNo : modelNos) {
                    for (String warehouse : warehouses) {
                        StockInfoByWarehouseForReplVO stockInfo = new StockInfoByWarehouseForReplVO();
                        Integer tyQuantity = inventoryQuantityAll.stream()
                                .filter(i -> "TY".equalsIgnoreCase(i.getClassCode()) && i.getModelNo().equalsIgnoreCase(modelNo) && i.getWarehouseCode().equalsIgnoreCase(warehouse))
                                .mapToInt(OpsInventoryVO::getQuantity).sum();

                        stockInfo.setWarehouseCode(warehouse);
                        stockInfo.setTyAvaQty(tyQuantity);
                        stockInfo.setIsSubWarehouse(!commonService.isMasterWarehouse(warehouse));

                        if (PublicUtil.isNotEmpty(inventoryMoveListAll) && inventoryMoveListAll.size() > 0) {
                            Integer tyTransQty = inventoryMoveListAll.stream()
                                    .filter(i -> "TY".equalsIgnoreCase(i.getClassCode()) && i.getModelNo().equalsIgnoreCase(modelNo) && i.getWarehouseCode().equalsIgnoreCase(warehouse))
                                    .mapToInt(OpsInventoryVO::getQuantity).sum();
                            stockInfo.setTyTransQty(tyTransQty);
                        }
                        if (dto.getIsQryBindata() && PublicUtil.isNotEmpty(bindataDOList) && bindataDOList.size() > 0) {
                            List<BindataDO> bindataList = bindataDOList.stream()
                                    .filter(i -> i.getModelNo().equalsIgnoreCase(modelNo) && i.getWarehouseCode().equalsIgnoreCase(warehouse))
                                    .collect(Collectors.toList());
                            if (PublicUtil.isNotEmpty(bindataList) && bindataList.size() > 0) {
                                stockInfo.setBinQty(bindataList.get(0).getQtyBin());
                            }
                        }
                        warehouseForReplVOS = mapReplVO.get(modelNo);
                        if (PublicUtil.isEmpty(warehouseForReplVOS)) {
                            warehouseForReplVOS = new ArrayList<>();
                        }
                        warehouseForReplVOS.add(stockInfo);
                        mapReplVO.put(modelNo, warehouseForReplVOS);
                    }
                }
            }


            // **处理出库信息

            propertyRequestDTO = new OpsInventoryPropertyRequestDTO();
            propertyRequestDTO.setModelNos(dto.getModelNos());
            propertyRequestDTO.setMonths(12);
            executorService = Executors.newFixedThreadPool(2);
            final List<String> modelAll = dto.getModelNos();
            Future<List<ModelOrderExpFreqVO>> futureSalesAll = executorService.submit(new Callable<List<ModelOrderExpFreqVO>>() {
                     @Override
                     public List<ModelOrderExpFreqVO> call() throws Exception {
                         return getModelExpFreqForAvgQty12(modelAll);
                     }
                 }
            );


            if ("TY".equalsIgnoreCase(dto.getInventoryTypeCode())) {
                propertyRequestDTO.setInventoryTypeCode(null);
                propertyRequestDTO.setIsPres(true);
            } else {
                propertyRequestDTO = requestDTO;
                propertyRequestDTO.setMonths(12);
            }
            final OpsInventoryPropertyRequestDTO RequestSPEC = propertyRequestDTO;
            Future<List<ModelOrderExpFreqVO>> futureSalesSPEC = executorService.submit(new Callable<List<ModelOrderExpFreqVO>>() {
                  @Override
                  public List<ModelOrderExpFreqVO> call() throws Exception {
                      return getSalesQuantityAndFreq(RequestSPEC);
                  }
              }
            );
            executorService.shutdown();
            while (true) {
                if (executorService.isTerminated()) {
                    break;
                }
                Thread.sleep(100);
            }
            List<ModelOrderExpFreqVO> salesInfoAll = futureSalesAll.get();
            List<ModelOrderExpFreqVO> salesInfoSPEC = futureSalesSPEC.get();

            Map<String, Integer> mapShikomiQuantity = new HashMap<>();
            if (dto.getIsQryShikomi()) {
                mapShikomiQuantity = this.getCanUseShikomiQuantity(dto.getModelNos());

            }
            // **整理返回信息
            for (String modelNo : dto.getModelNos()) {
                stockInfoForReplVO = new StockInfoForReplVO();
                stockInfoForReplVO.setModelNo(modelNo);
                if (PublicUtil.isNotEmpty(mapReplVO) && mapReplVO.size() > 0) {
                    List<StockInfoByWarehouseForReplVO> replVOS = mapReplVO.get(modelNo);
                    if (dto.getIsQrySubWarehouseStock()) {
                        stockInfoForReplVO.setWarehouseStocks(replVOS);
                    } else {
                        List<StockInfoByWarehouseForReplVO> master = replVOS.stream().filter(i -> !i.getIsSubWarehouse()).collect(Collectors.toList());
                        stockInfoForReplVO.setWarehouseStocks(master);
                    }
                }
                Integer allAvaQty = 0;
                Integer allTransQty = 0;
                Integer allSalesQty = 0;
                Integer allAvgQty = 0;
                Integer allFreq = 0;

                Integer tyAvaQty = 0;
                Integer tyTransQty = 0;

                Integer specAvaQty = 0;
                Integer specTransQty = 0;
                Integer specSalesQty = 0;
                Integer specAvgQty = 0;
                Integer specCanUseMonths = 0;
                Integer specFreq = 0;


                if (PublicUtil.isNotEmpty(inventoryQuantityAll) && inventoryQuantityAll.size() > 0) {
                    allAvaQty = inventoryQuantityAll.stream().filter(i -> i.getModelNo().equalsIgnoreCase(modelNo) && Objects.nonNull(i.getQuantity())).mapToInt(OpsInventoryVO::getQuantity).sum();
                    tyAvaQty = inventoryQuantityAll.stream().filter(i -> i.getModelNo().equalsIgnoreCase(modelNo) && Objects.nonNull(i.getQuantity()) && "TY".equalsIgnoreCase(i.getClassCode()))
                            .mapToInt(OpsInventoryVO::getQuantity).sum();
                }
                if (PublicUtil.isNotEmpty(inventoryMoveListAll) && inventoryMoveListAll.size() > 0) {
                    allTransQty = inventoryMoveListAll.stream().filter(i -> i.getModelNo().equalsIgnoreCase(modelNo) && Objects.nonNull(i.getQuantity())).mapToInt(OpsInventoryVO::getQuantity).sum();
                    tyTransQty = inventoryMoveListAll.stream().filter(i -> i.getModelNo().equalsIgnoreCase(modelNo) && Objects.nonNull(i.getQuantity()) && "TY".equalsIgnoreCase(i.getClassCode()))
                            .mapToInt(OpsInventoryVO::getQuantity).sum();
                }
                if (PublicUtil.isNotEmpty(inventoryQuantitySPEC) && inventoryQuantitySPEC.size() > 0) {
                    specAvaQty = inventoryQuantitySPEC.stream().filter(i -> i.getModelNo().equalsIgnoreCase(modelNo) && Objects.nonNull(i.getQuantity())).mapToInt(OpsInventoryVO::getQuantity).sum();
                }
                if (PublicUtil.isNotEmpty(inventoryMoveListSPEC) && inventoryMoveListSPEC.size() > 0) {
                    specTransQty = inventoryMoveListSPEC.stream().filter(i -> i.getModelNo().equalsIgnoreCase(modelNo) && Objects.nonNull(i.getQuantity())).mapToInt(OpsInventoryVO::getQuantity).sum();
                }

                if (PublicUtil.isNotEmpty(salesInfoAll) && salesInfoAll.size() > 0) {
                    allSalesQty = salesInfoAll.stream().filter(i -> i.getModelNo().equalsIgnoreCase(modelNo) && Objects.nonNull(i.getQuantity())).mapToInt(ModelOrderExpFreqVO::getQuantity).sum();
                    allFreq = salesInfoAll.stream().filter(i -> i.getModelNo().equalsIgnoreCase(modelNo) && Objects.nonNull(i.getFreq())).mapToInt(ModelOrderExpFreqVO::getFreq).sum();
                }
                if (PublicUtil.isNotEmpty(salesInfoSPEC) && salesInfoSPEC.size() > 0) {
                    specSalesQty = salesInfoSPEC.stream().filter(i -> i.getModelNo().equalsIgnoreCase(modelNo) && Objects.nonNull(i.getQuantity())).mapToInt(ModelOrderExpFreqVO::getQuantity).sum();
                    specFreq = salesInfoSPEC.stream().filter(i -> i.getModelNo().equalsIgnoreCase(modelNo) && Objects.nonNull(i.getFreq())).mapToInt(ModelOrderExpFreqVO::getFreq).sum();
                }
                //    <!--add by WuWeiDong 20231026  bug 12458  先行在库门户接口全国订货月均数不对 -->
                allAvgQty = Optional.ofNullable(allSalesQty).orElse(0) ;//已是取model_exp_freq 12平均数据
                if(Optional.ofNullable(specSalesQty).orElse(0).compareTo(0)>1) {
                    BigDecimal bigSpecAvgQty = BigDecimalUtil.div(Optional.ofNullable(specSalesQty).orElse(0), 12, 1);
                    specAvgQty = bigSpecAvgQty.add(new BigDecimal(0.5)).setScale(0, BigDecimal.ROUND_HALF_UP).toBigInteger().intValue();
                }
                if (specAvgQty.compareTo(0) >0) {
                    specCanUseMonths = (Optional.ofNullable(specAvaQty).orElse(0) + Optional.ofNullable(specTransQty).orElse(0)) / specAvgQty;
                }

                stockInfoForReplVO.setAllAvaQty(allAvaQty);
                stockInfoForReplVO.setAllTransQty(allTransQty);
                stockInfoForReplVO.setTyAvaQty(tyAvaQty);
                stockInfoForReplVO.setTyTransQty(tyTransQty);
                stockInfoForReplVO.setAllAvgQty(allAvgQty);
                stockInfoForReplVO.setAllFreq(allFreq);

                stockInfoForReplVO.setSpecAvaQty(specAvaQty);
                stockInfoForReplVO.setSpecTransQty(specTransQty);
                stockInfoForReplVO.setSpecAvgQty(specAvgQty);
                stockInfoForReplVO.setSpecFreq(specFreq);
                stockInfoForReplVO.setSpecCanUseMonths(specCanUseMonths);

                if (dto.getIsQryShikomi()) {
                    stockInfoForReplVO.setShikomiQty(mapShikomiQuantity.get(modelNo));
                }
                //是否BIN品：KBJ/KSH/KGZ/SCZ 物流中心有一个设置BIN则  字段显示“是”   （后续如果追加自动化BIN，则只要是自动化BIN 字段就显示“是”）
                stockInfoForReplVO.setIsBinModel(false);
                if (PublicUtil.isNotEmpty(checkBindataList) && checkBindataList.size() > 0) {
                    List<BindataDO> checkBindata = checkBindataList.stream()
                            .filter(i -> i.getModelNo().equalsIgnoreCase(modelNo) && i.getQtyBin() != null && i.getQtyBin() > 0).collect(Collectors.toList());
                    if (PublicUtil.isNotEmpty(checkBindata) && checkBindata.size() > 0) {
                        stockInfoForReplVO.setIsBinModel(true);
                    }
                }

                list.add(stockInfoForReplVO);
            }
            log.info("listStockForRepl ==>返回:{}", JSONObject.toJSONString(list));
            log.info("listStockForRepl ==>处理完成，耗时:{}s", (System.currentTimeMillis() - startTimer) / 1000.0);

            return ResultVo.success(list);

        } catch (
                Exception ex) {
            log.error(Thread.currentThread().getName() + "->错误：" + ex);
            return ResultVo.failure("错误：" + ex);
        }

    }


    /**
     * handleBinModelInfo
     *
     * @param binModelInfoMap Map<String, BinModelInfo>
     */
    private void handleBinModelInfo(String customerNo, Map<String, BinModelInfo> binModelInfoMap, List<String> modelNoList,
                                    Map<String, Integer> meanMap) {

        // 查询客户BIN型号在库信息
        StringBuilder binModel = new StringBuilder(70);
        BinModelInfo info;
        Map<String, CustomerModelStockVO> stockInfoMap = new HashMap<>(binModelInfoMap.size());

        if (CollectionUtils.isNotEmpty(modelNoList)) {
            InventoryRequestDTO dto = new InventoryRequestDTO();
            dto.setCustomerNo(customerNo);
            dto.setModelNos(modelNoList);
            ResultVo<List<CustomerModelStockVO>> stockResult = inventoryServiceFeignApi.listCustomerBinModelInventory(dto);
            if (!stockResult.isSuccess()) {
                log.error("查询客户Bin型号通用在库和客户专备失败: {}", stockResult);
                throw new BusinessException("查询通用在库和客户专备失败: " + stockResult.getMessage());
            }
            for (CustomerModelStockVO stockVO : stockResult.getData()) {
                binModel.setLength(0);
                binModel.append(stockVO.getInventoryTypeCode()).append(":").append(stockVO.getModelNo());
                stockInfoMap.put(binModel.toString(), stockVO);
            }
        }

        // 设置库存信息
        for (Map.Entry<String, CustomerModelStockVO> stockInfo : stockInfoMap.entrySet()) {
            info = binModelInfoMap.get(stockInfo.getKey());
            if (StringUtils.isNotBlank(stockInfo.getValue().getPpl())) {
                info.setBomNo(stockInfo.getValue().getPpl()); // 设备bom号
            }
            if (StringUtils.isNotBlank(stockInfo.getValue().getProjectCode())) {
                info.setProjectCode(stockInfo.getValue().getProjectCode()); // 项目号
            }
            info.setInventoryQuantity(info.getInventoryQuantity() + stockInfo.getValue().getTyAvaQty()); // 通用在库数
            info.setCustomerQuantity(info.getCustomerQuantity() + stockInfo.getValue().getZyAvaQty()); // 客户在库数
            info.setCustomerInOrderingQuantity(info.getCustomerInOrderingQuantity() + stockInfo.getValue().getOrderingQty()); // 客户在库在途数
            //info.setHopeDeliveryDate(0); // 期望交货期
        }

        /*List<BindataVO> binCellList = bindataService.listBinCellByModelNo(new ArrayList<>(binModelInfoMap.keySet()));
        log.info("binCellList = {}", JSON.toJSONString(binCellList));
        for (BindataVO binCellInfo : binCellList) {
            binModel = binCellInfo.getPropertyID() + ":" + binCellInfo.getModelNo();
            info = binModelInfoMap.get(binModel);
            if ("KBJ".equals(binCellInfo.getWarehouseCode())) {
                info.setBinQuantityBJ(binCellInfo.getBinCell()); // 北京BIN数
            }
            if ("KSH".equals(binCellInfo.getWarehouseCode())) {
                info.setBinQuantitySH(binCellInfo.getBinCell()); // 上海BIN数
            }
            if ("KGZ".equals(binCellInfo.getWarehouseCode())) {
                info.setBinQuantityGZ(binCellInfo.getBinCell()); // 广州BIN数
            }
        }*/

        // 计算在库可用月数、可用月数
        for (BinModelInfo value : binModelInfoMap.values()) {
            if (Optional.ofNullable(meanMap.get(value.getModelNo())).orElse(0) > 0) {
                int mean = meanMap.get(value.getModelNo());
                // 在库可用月数 = (客户在库数 + 在途数) / 月用量
                value.setStockCanUseMonthQuantity((value.getCustomerQuantity() + value.getCustomerInOrderingQuantity()) / mean);
                // 可用月数 = （生成补库数量 + 客户在库数 + 在途数） / 月用量
                value.setCanUseMonthQuantity((value.getApplyHairNoteQuantity() + value.getCustomerQuantity() + value.getCustomerInOrderingQuantity()) / mean);
            }
        }
    }

//    /**
//     * handleBinModelInfo
//     *
//     * @param binModelInfoMap Map<String, BinModelInfo>
//     */
//    private void handleBinModelInfo(String customerNo, Map<String, BinModelInfo> binModelInfoMap,
//                                    Map<Long, List<String>> propertyIdMap,
//                                    Map<String, Integer> meanMap) {
//        String binModel;
//        BinModelInfo info;
//        // 查询客户BIN型号在库信息
//        Map<String, CustomerModelStockVO> stockInfoMap = new HashMap<>(binModelInfoMap.size());
//        //List<CustomerModelStockVO> stockInfoList = new ArrayList<>(binModelInfoMap.size());
//        InventoryRequestDTO dto;
//        for (Map.Entry<Long, List<String>> entry : propertyIdMap.entrySet()) {
//            dto = new InventoryRequestDTO();
//            dto.setCustomerNo(customerNo);
//            dto.setPropertyId(entry.getKey());
//            dto.setModelNos(entry.getValue());
//            ResultVo<List<CustomerModelStockVO>> stockResult = inventoryServiceFeignApi.listCustomerBinModelInventory(dto);
//            if (!stockResult.isSuccess()) {
//                log.error("查询客户Bin型号通用在库和客户专备失败: {}", stockResult);
//                throw new BusinessException("查询通用在库和客户专备失败: " + stockResult.getMessage());
//            }
//            //stockInfoList.addAll(stockResult.getData());
//            for (CustomerModelStockVO stockVO : stockResult.getData()) {
//                binModel = entry.getKey() + ":" + stockVO.getModelNo();
//                stockInfoMap.put(binModel, stockVO);
//            }
//        }
//
//        // 设置库存信息
//        for (Map.Entry<String, CustomerModelStockVO> stockInfo : stockInfoMap.entrySet()) {
//            info = binModelInfoMap.get(stockInfo.getKey());
//            if (StringUtils.isNotBlank(stockInfo.getValue().getPpl())) {
//                info.setBomNo(stockInfo.getValue().getPpl()); // 设备bom号
//            }
//            if (StringUtils.isNotBlank(stockInfo.getValue().getProjectCode())) {
//                info.setProjectCode(stockInfo.getValue().getProjectCode()); // 项目号
//            }
//            info.setInventoryQuantity(info.getInventoryQuantity() + stockInfo.getValue().getTyAvaQty()); // 通用在库数
//            info.setCustomerQuantity(info.getCustomerQuantity() + stockInfo.getValue().getZyAvaQty()); // 客户在库数
//            info.setCustomerInOrderingQuantity(info.getCustomerInOrderingQuantity() + stockInfo.getValue().getOrderingQty()); // 客户在库在途数
//            //info.setHopeDeliveryDate(0); // 期望交货期
//        }
//
//        /*List<BindataVO> binCellList = bindataService.listBinCellByModelNo(new ArrayList<>(binModelInfoMap.keySet()));
//        log.info("binCellList = {}", JSON.toJSONString(binCellList));
//        for (BindataVO binCellInfo : binCellList) {
//            binModel = binCellInfo.getPropertyID() + ":" + binCellInfo.getModelNo();
//            info = binModelInfoMap.get(binModel);
//            if ("KBJ".equals(binCellInfo.getWarehouseCode())) {
//                info.setBinQuantityBJ(binCellInfo.getBinCell()); // 北京BIN数
//            }
//            if ("KSH".equals(binCellInfo.getWarehouseCode())) {
//                info.setBinQuantitySH(binCellInfo.getBinCell()); // 上海BIN数
//            }
//            if ("KGZ".equals(binCellInfo.getWarehouseCode())) {
//                info.setBinQuantityGZ(binCellInfo.getBinCell()); // 广州BIN数
//            }
//        }*/
//
//        // 计算在库可用月数、可用月数
//        for (BinModelInfo value : binModelInfoMap.values()) {
//            if (Optional.ofNullable(meanMap.get(value.getModelNo())).orElse(0) > 0) {
//                int mean = meanMap.get(value.getModelNo());
//                // 在库可用月数 = (客户在库数 + 在途数) / 月用量
//                value.setStockCanUseMonthQuantity((value.getCustomerQuantity() + value.getCustomerInOrderingQuantity()) / mean);
//                // 可用月数 = （生成补库数量 + 客户在库数 + 在途数） / 月用量
//                value.setCanUseMonthQuantity((value.getApplyHairNoteQuantity() + value.getCustomerQuantity() + value.getCustomerInOrderingQuantity()) / mean);
//            }
//        }
//    }

    /**
     * 转换部门代码
     */
    private String converDeptCode(String deptNo) {
        ResultVo<String> resultVo = opsCommonService.getDeptNoByHRSalesDeptNo(deptNo);
        log.info("#converDeptCode {} ==> {}", deptNo, resultVo);
        if (!resultVo.isSuccess()) {
            throw new BusinessException("获取营业所代码失败");
        }
        return resultVo.getData();
    }


    private ResultVo<List<OpsInventoryVO>> getOpsInventoryForReplDTO(StockQueryForReplDTO dto) {
        OpsInventoryPropertyRequestDTO requestDTO = new OpsInventoryPropertyRequestDTO();
        List<Long> PropertyIds = new ArrayList<>();
        Boolean isTY = false;
        if (dto.getInventoryTypeCode().equalsIgnoreCase("TY")) {
            PropertyIds.add(1l);
            isTY = true;
        } else {
            requestDTO.setInventoryTypeCode(dto.getInventoryTypeCode());
            requestDTO.setCustomerNo(dto.getCustomerNo());
            requestDTO.setPpl(dto.getPpl());
            requestDTO.setProjectCode(dto.getProjectCode());
            requestDTO.setGroupCustomerNo(dto.getGroupCustomerNo());

            ResultVo<List<Long>> resultPropertyIds = inventoryServiceFeignApi.getOpsInventoryPropertyId(requestDTO);
            PropertyIds = resultPropertyIds.getData();
        }

        if (PublicUtil.isEmpty(PropertyIds)) {
            return ResultVo.failure("没有此类型仓库信息，请确认。");
        }
        requestDTO = new OpsInventoryPropertyRequestDTO();
        requestDTO.setModelNos(dto.getModelNos());
        requestDTO.setPropertyIds(PropertyIds);

        ResultVo<List<OpsInventoryVO>> resultInventory = inventoryServiceFeignApi.getOpsInventoryByPropertyIds(requestDTO);
        return resultInventory;

    }

//    /**
//     * 出库数据统计，目前到时用专备
//     *
//     * @param modelNos
//     * @param propertyIds
//     * @return
//     */
//    private Future<List<ModelExpFreqVO>> getSalesQuantityByInventoryId(List<String> modelNos, List<Long> propertyIds) {
//
//        List<ModelExpFreqVO> list = new ArrayList<>();
//        OpsInventoryPropertyRequestDTO requestDTO=new OpsInventoryPropertyRequestDTO();
//        if(PublicUtil.isNotEmpty(propertyIds)&&propertyIds.size()>0) {
//            for (String modelNo : modelNos) {
//                requestDTO.setModelNos( Arrays.asList(modelNo));
//                requestDTO.setPropertyIds(propertyIds);
//                ResultVo<List<Long>> resultList = inventoryServiceFeignApi.getInventoryIdByPropertyIds(requestDTO);
//                if (PublicUtil.isNotEmpty(resultList.getData()) && resultList.getData().size() > 0) {
//                    requestDTO=new OpsInventoryPropertyRequestDTO();
//                    requestDTO.setPropertyIds(resultList.getData());
//                    requestDTO.setMonths(12);
//                    ResultVo<List<ModelExpFreqVO>> logList = inventoryServiceFeignApi.getSalesQuantityByInventoryId(requestDTO);
//                    if (!logList.isSuccess()) {
//                        log.error("getSalesQuantityByInventoryId 错误：" + logList.getMessage());
//                          throw new BusinessException("错误：" + logList.getMessage());
//                    } else if (PublicUtil.isNotEmpty(logList.getData()) && logList.getData().size() > 0) {
//                        list.addAll(logList.getData());
//                    }
//                }
//            }
//        } else {
//            requestDTO=new OpsInventoryPropertyRequestDTO();
//            requestDTO.setModelNos(modelNos);
//            requestDTO.setMonths(12);
//            ResultVo<List<ModelExpFreqVO>> logList = inventoryServiceFeignApi.getSalesQuantityByModelNo(requestDTO);
//            if (!logList.isSuccess()) {
//                log.error("getSalesQuantityByModelNo 错误：" + logList.getMessage());
//                throw new BusinessException("错误：" + logList.getMessage());
//            } else if (PublicUtil.isNotEmpty(logList.getData()) && logList.getData().size() > 0) {
//                list.addAll(logList.getData());
//            }
//        }
//        return new AsyncResult<List<ModelExpFreqVO>>(list);
//    }

    private List<OpsInventoryVO> getInventoryMoveByProperty(OpsInventoryPropertyRequestDTO propertyRequestDTO) {

        SMCApp.setGrantType(GlobalConstant.GrantType.CLIANT_CREDENTIALS);
        ResultVo<List<OpsInventoryVO>> resultVo = inventoryServiceFeignApi.getInventoryMoveByProperty(propertyRequestDTO);
        ThreadLocalMapUtil.remove(GlobalConstant.GrantType.CLIANT_CREDENTIALS);
        if (resultVo.isSuccess()) {
            return resultVo.getData();
        } else {
            log.info("getInventoryMoveByProperty->错误Exception：" + resultVo.getMessage());
            return null;
        }


    }

    private List<OpsInventoryVO> getCanUseInventoryByProperty(OpsInventoryPropertyRequestDTO inventoryProperty) {

        SMCApp.setGrantType(GlobalConstant.GrantType.CLIANT_CREDENTIALS);
        ResultVo<List<OpsInventoryVO>> resultVo = inventoryServiceFeignApi.getCanUseInventoryByProperty(inventoryProperty);
        ThreadLocalMapUtil.remove(GlobalConstant.GrantType.CLIANT_CREDENTIALS);
        if (resultVo.isSuccess()) {
            return resultVo.getData();
        } else {
            log.info("getCanUseInventoryByProperty->错误Exception：" + resultVo.getMessage());
            return null;
        }


    }

    private List<ModelOrderExpFreqVO> getModelExpFreqForAvgQty12(List<String> modelNos)   {
        try {
            SMCApp.setGrantType(GlobalConstant.GrantType.CLIANT_CREDENTIALS);
            List<ModelExpFreqDO> freqDOS = commonService.getModelExpFreqForAvgQty12(modelNos);
            ThreadLocalMapUtil.remove(GlobalConstant.GrantType.CLIANT_CREDENTIALS);
            List<ModelOrderExpFreqVO> rtnData = new ArrayList<>();
            if (CollectionUtils.isNotEmpty(freqDOS) && freqDOS.size() > 0) {
                for (ModelExpFreqDO freqDO : freqDOS) {
                    ModelOrderExpFreqVO freqVO = new ModelOrderExpFreqVO();
                    freqVO.setModelNo(freqDO.getModelNo());
                    freqVO.setQuantity(freqDO.getAvgQtyOf12().intValue());
                    freqVO.setFreq(freqDO.getMonthsOf12());
                    rtnData.add(freqVO);
                }
            }
            return rtnData;
        } catch (Exception ex) {
            log.info("getModelExpFreqForAvgQty12->错误Exception：" + ex);
            throw new BusinessException("getModelExpFreqForAvgQty12->错误Exception：" + ex);
        }

    }

    private List<ModelOrderExpFreqVO> getSalesQuantityAndFreq(OpsInventoryPropertyRequestDTO inventoryProperty) throws Exception {
        SMCApp.setGrantType(GlobalConstant.GrantType.CLIANT_CREDENTIALS);
        ResultVo<List<ModelOrderExpFreqVO>> resultVo = inventoryServiceFeignApi.getSalesQuantityAndFreq(inventoryProperty);
        ThreadLocalMapUtil.remove(GlobalConstant.GrantType.CLIANT_CREDENTIALS);
        if (resultVo.isSuccess()) {
            return resultVo.getData();
        } else {
            log.info("getSalesQuantityAndFreq->错误Exception：" + resultVo.getMessage());
            throw new Exception("getSalesQuantityAndFreq->错误Exception：" + resultVo.getMessage());
        }
    }


    private Map<String, Integer> getCanUseShikomiQuantity(List<String> modelNos) {
        Map<String, Integer> map = new HashMap<>(modelNos.size());
        SMCApp.setGrantType(GlobalConstant.GrantType.CLIANT_CREDENTIALS);
        for (String modelno : modelNos) {
            ResultVo<List<ShikomiVO>> resultVo = productServiceFeignApi.getCanUseShikomi(modelno);
            if (resultVo.isSuccess()) {
                if (PublicUtil.isNotEmpty(resultVo.getData()) && resultVo.getData().size() > 0) {
                    List<ShikomiVO> shikomiVOS = resultVo.getData();
                    Integer shikomiQty = shikomiVOS.stream().mapToInt(ShikomiVO::getRemainQty).sum();
                    map.put(modelno, shikomiQty);
                }
            } else {
                log.info("getCanUseShikomiQuantity 错误：{}：{}", modelno, resultVo.getMessage());
                map.put(modelno, null);
            }
        }
        ThreadLocalMapUtil.remove(GlobalConstant.GrantType.CLIANT_CREDENTIALS);
        return map;
    }

    /**
     * 验证仓库属性是否正确
     *
     * @param dto
     * @return
     */
    private ResultVo<String> checkInventoryProperty(StockQueryForReplDTO dto) {

        if ("TY".equalsIgnoreCase(dto.getInventoryTypeCode()) &&
                (StringUtils.isNotBlank(dto.getCustomerNo()) || StringUtils.isNotBlank(dto.getPpl()) ||
                        StringUtils.isNotBlank(dto.getProjectCode()) || StringUtils.isNotBlank(dto.getGroupCustomerNo())
                        || StringUtils.isNotBlank(dto.getSalesInfoNo()))) {
            return ResultVo.failure("通用在库，不能存客户代码，PPL等信息。");
        }
//        if ((dto.getInventoryTypeCode().startsWith("GK")) && StringUtils.isBlank(dto.getCustomerNo())) {
//            return ResultVo.failure("客户代码不能为空.");
//        }
//        if ((dto.getInventoryTypeCode().endsWith("JT") || dto.getInventoryTypeCode().endsWith("HY"))
//                && StringUtils.isBlank(dto.getGroupCustomerNo())) {
//            return ResultVo.failure("集团编号不能为空.");
//        }
//        if (dto.getInventoryTypeCode().endsWith("PPL") && StringUtils.isBlank(dto.getPpl())) {
//            return ResultVo.failure("PPL不能为空.");
//        }
//        if (dto.getInventoryTypeCode().endsWith("PJ") && StringUtils.isBlank(dto.getProjectCode())) {
//            return ResultVo.failure("项目代码不能为空.");
//        }
//        if (dto.getInventoryTypeCode().startsWith("QB") && StringUtils.isBlank(dto.getSalesInfoNo())) {
//            return ResultVo.failure("营业情报不能为空.");
//        }
        return ResultVo.success("验证通过");
    }


}
