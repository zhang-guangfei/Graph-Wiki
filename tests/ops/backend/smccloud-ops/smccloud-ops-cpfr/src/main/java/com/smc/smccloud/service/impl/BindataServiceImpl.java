package com.smc.smccloud.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sales.ops.db.entity.OpsInventoryProperty;
import com.sales.ops.dto.util.CommonResult;
import com.sales.ops.enums.WarehouseTypeEnum;
import com.sales.ops.feign.OpsPropertyFeignApi;
import com.smc.smccloud.core.exception.BusinessException;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.model.adapter.DataAuthoritySearchCondition;
import com.smc.smccloud.core.model.constants.GlobalConstant;
import com.smc.smccloud.core.redis.RedisManager;
import com.smc.smccloud.core.utils.*;
import com.smc.smccloud.mapper.BindataClientWarehouseMapper;
import com.smc.smccloud.mapper.BindataMapper;
import com.smc.smccloud.mapper.binorder.ModelExpFreqMapper;
import com.smc.smccloud.model.WarehouseVO;
import com.smc.smccloud.model.adapter.Product;
import com.smc.smccloud.model.bindata.*;
import com.smc.smccloud.model.binorder.*;
import com.smc.smccloud.service.BindataService;
import com.smc.smccloud.service.CommonService;
import com.smc.smccloud.service.CommonServiceFeignApi;
import com.smc.smccloud.service.OpsCommonService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author edp02 @Date 2021/10/6 14:26
 */
@Service
@Slf4j
public class BindataServiceImpl implements BindataService {

    private static final int IMPORT_QUERY_BATCH_SIZE = 1000;

    private final ThreadLocal<BindataImportLogContext> bindataImportLogHolder = new ThreadLocal<>();

    private enum PersistAction {
        NONE,
        UPDATE,
        INSERT
    }

    private static class PersistPlanItem {
        private PersistAction action = PersistAction.NONE;
        private BindataDO target;
        private BindataDO existing;
        private String centreResetKey;
        private boolean needCentreReset;
        private boolean needClientSync;
    }

    private static class PersistPlan {
        private final List<PersistPlanItem> orderedItems = new ArrayList<>();
        private final List<Integer> deleteIds = new ArrayList<>();
        private int ambiguousFallbackCount;
        private int noChangeSkipCount;
    }

    private static class PersistStats {
        private int successCount;
        private int progressUpdateCount;
        private int ambiguousFallbackCount;
        private int noChangeSkipCount;
        private int updateCount;
        private int insertCount;
        private int clientSyncCount;
        private int centreResetCount;
    }

    @Resource
    private BindataMapper bindataMapper;
    @Resource
    private CommonService commonService;
    @Resource
    private ModelExpFreqMapper modelExpFreqMapper;
    @Resource
    private OpsCommonService opsCommonService;
    @Resource
    private OpsPropertyFeignApi opsPropertyFeignApi;

    @Resource
    private HttpServletResponse response;

    @Resource
    private RedisManager redisUtil;
    @Resource
    private BindataClientWarehouseMapper bindataClientWarehouseMapper;
    @Autowired
    private CommonServiceFeignApi commonServiceFeignApi;
    //查询客户BIN
    @Override
    public List<BindataDO> findCustomerBindataExcludeModelnos(List<String> modelnoList) {
        LambdaQueryWrapper<BindataDO> query = new LambdaQueryWrapper<>();
        query.eq(BindataDO::getDelFlag, 0)
                .gt(BindataDO::getQtyBin, 0)
                .eq(BindataDO::getStockType, NewBinOrderCalcServiceImpl.CalcType.CUSTOMER_BIN.getCode())
                .eq(BindataDO::getAdjustable, 0);
        if (CollectionUtils.isNotEmpty(modelnoList)) {
            query.notIn(BindataDO::getModelNo, modelnoList);
        }
        return bindataMapper.selectList(query);
    }

    @Override
    public List<BindataDO> findBindata(String modelno, Long propertyId, String warehouseCode, Integer calcType) {
        LambdaQueryWrapper<BindataDO> query = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(modelno)) {
            query.eq(BindataDO::getModelNo, modelno);
        }
        if (propertyId != null) {
            query.eq(BindataDO::getPropertyID, propertyId);
        }
        if (StringUtils.isNotBlank(warehouseCode)) {
            query.eq(BindataDO::getWarehouseCode, warehouseCode);
        }
        query.eq(BindataDO::getDelFlag, 0);
        query.gt(BindataDO::getQtyBin, 0);
        if (calcType != null) {
            query.eq(BindataDO::getStockType, calcType);
        }

        return bindataMapper.selectList(query);
    }

    @Override
    public List<ModelExpFreqDO> findModeExpFreq(String modelno, Integer stockType, String stockCode) {
        LambdaQueryWrapper<ModelExpFreqDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(StringUtils.isNotBlank(modelno), ModelExpFreqDO::getModelNo, modelno)
                .eq(StringUtils.isNotBlank(stockCode), ModelExpFreqDO::getStockCode, stockCode)
                .eq(Objects.nonNull(stockType), ModelExpFreqDO::getStockType, stockType);
        return modelExpFreqMapper.selectList(queryWrapper);

    }

    //-------------------------------------------------------


    @Override
    public List<BindataVO> listModelBinData(BindataRequest request) {
        LambdaQueryWrapper<BindataDO> query = new LambdaQueryWrapper<>();
        query.eq(PublicUtil.isNotEmpty(request.getWarehouseCode()), BindataDO::getWarehouseCode, request.getWarehouseCode());
        if (request.getPropertyID() != null && request.getPropertyID() > 0) {
            query.eq(BindataDO::getPropertyID, request.getPropertyID());
        }
        if (CollectionUtils.isNotEmpty(request.getModelNos())) {
            query.in(BindataDO::getModelNo, request.getModelNos());
        }
        query.eq(BindataDO::getDelFlag, 0);
        List<BindataDO> list = bindataMapper.selectList(query);
        return BeanCopyUtil.copyList(list, BindataVO.class);
    }

    @Override
    public PageInfo<BindataVO> listBindata(BindataRequest request) {
        LambdaQueryWrapper<BindataDO> query = new LambdaQueryWrapper<>();
        query.eq(request.getStockType() != null && request.getStockType() > 0, BindataDO::getStockType, request.getStockType())
                .eq(PublicUtil.isNotEmpty(request.getWarehouseCode()), BindataDO::getWarehouseCode, request.getWarehouseCode())
                .eq(!Boolean.TRUE.equals(request.getIsdelFlag()), BindataDO::getDelFlag, 0)
                .eq(PublicUtil.isNotEmpty(request.getCustomerNo()), BindataDO::getCustomerNo, request.getCustomerNo())
                .eq(request.getPropertyID() != null && request.getPropertyID() > 0, BindataDO::getPropertyID, request.getPropertyID())
                .eq(PublicUtil.isNotEmpty(request.getOrderType()), BindataDO::getOrderType, request.getOrderType())
                .eq(PublicUtil.isNotEmpty(request.getSupplierCode()), BindataDO::getSupplierCode, request.getSupplierCode());

        if (PublicUtil.isNotEmpty(request.getModelNo())) {
            String modelNo = request.getModelNo();
            modelNo = modelNo.trim();
            if (modelNo.contains("%")) {
                if (modelNo.startsWith("%") && modelNo.endsWith("%")) {
                    query.like(BindataDO::getModelNo, modelNo.substring(1, modelNo.length() - 1));
                } else if (modelNo.startsWith("%")) {
                    query.likeLeft(BindataDO::getModelNo, modelNo.substring(1));
                } else if (modelNo.endsWith("%")) {
                    query.likeRight(BindataDO::getModelNo, modelNo.substring(0, modelNo.length() - 1));
                }
            }else{
                query.eq(BindataDO::getModelNo, modelNo);
            }
        }
        query.orderByAsc(BindataDO::getUpdateTime);
        PageHelper.startPage(request.getPageNum(), request.getPageSize());
        List<BindataDO> list = bindataMapper.selectList(query);
        if (CollectionUtils.isNotEmpty(list)) {
            for (BindataDO item : list) {
                item.setCenterFlagName(item.getCentreFlag() != null && item.getCentreFlag() == 1 ? "中央仓管理" : "非中央仓管理");
                List<String> clients = bindataMapper.getClientsById(Long.valueOf(item.getId()));
                item.setClient(clients);
            }
        }
        PageInfo<BindataDO> pageInfo = PageInfo.of(list);
        return BeanCopyUtil.pageDto2Vo(pageInfo, BindataVO.class);
    }
    @Deprecated
    @Override
    @Async
    public void asyupdAndAddBinData(ExcelHelper excel, String rediskey) {
/*

        String updkey = "ops:binimp:";
        Sheet sheet = excel.getSheet();
        List<BindataDO> list = new ArrayList<>();
        int row = 1;
        int cel = 0;
        Map<String, BindataDO> map = new HashMap<>();  //查找导入种类
        Map<String, String> modelmap = new HashMap<>();  //查找导入种类
        Row rows;
        BindataDO info;
        List<String> masterWarehouseCodes = new ArrayList<>();
        Set<String> centreModelSet = new HashSet<>();
        try {
            ResultVo<List<WarehouseVO>> warehouseByType = opsCommonService.getWarehouseByType(WarehouseTypeEnum.RDC.getHouseTypeCode());
            if (!warehouseByType.isSuccess()) {
                masterWarehouseCodes = warehouseByType.getData().stream()
                        .map(WarehouseVO::getWarehouseCode)
                        .collect(Collectors.toList());
            } else {
                masterWarehouseCodes = commonService.getMasterWarehouseCodes();
            }
        } catch (Exception e) {
            log.error("导入失败：", e);
            redisUtil.set(rediskey, "导入失败！" + e.getMessage());
            redisUtil.del(updkey);
            return;
        }

        while (true) {
            try {
                rows = sheet.getRow(row);
                if (rows == null) {
                    break;
                }
                cel = 0;
                info = new BindataDO();
                if (PublicUtil.isEmpty(excel.getCellString(rows.getCell(2)))) {
                    break;
                }
                if (PublicUtil.isEmpty(excel.getCellString(rows.getCell(cel)))) {
                    redisUtil.set(rediskey, "excel表第" + (row + 1) + "行，第" + cel + "列数据有问题;" + "必须输入库存类型");
                    redisUtil.del(updkey);
                    return;
                }
                info.setStockType(excel.getCellInteger(rows.getCell(cel++)));
                info.setWarehouseCode(excel.getCellString(rows.getCell(cel++)).trim());
                info.setModelNo(excel.getCellString(rows.getCell(cel++)).trim());
                info.setCustomerNo(excel.getCellString(rows.getCell(cel++)).trim());
                info.setPropertyID(excel.getCellLong(rows.getCell(cel++)));

                info.setInventoryTypeCode(excel.getCellString(rows.getCell(cel++)).trim());
                info.setGroupCustomerNo(excel.getCellString(rows.getCell(cel++)).trim());
                info.setPpl(excel.getCellString(rows.getCell(cel++)).trim());
                info.setProjectNo(excel.getCellString(rows.getCell(cel++)).trim());
                info.setQtyBin(excel.getCellInteger(rows.getCell(cel++)));
                info.setBinCell(excel.getCellInteger(rows.getCell(cel++)));
                if (info.getBinCell() <= 0 || info.getBinCell() > 6) {
                    redisUtil.set(rediskey, "excel表第" + (row + 1) + "行，第" + cel + "列数据有问题;" + "Bin数必须大于等于1且小于等于6！");
                    redisUtil.del(updkey);
                    return;
                }
                if (info.getQtyBin() <= 0) {
                    redisUtil.set(rediskey, "excel表第" + (row + 1) + "行，第" + cel + "列数据有问题;" + "Bin数量必须大于0！");
                    redisUtil.del(updkey);
                    return;
                }
                info.setCaseType(excel.getCellString(rows.getCell(cel++)).trim());
                info.setProdType(excel.getCellString(rows.getCell(cel++)).trim());
                info.setMeshType(excel.getCellString(rows.getCell(cel++)).trim());
                info.setInCaseQty(excel.getCellInteger(rows.getCell(cel++)));
                info.setAdjustable(excel.getCellString(rows.getCell(cel++)).trim());
                info.setSafeQty(excel.getCellInteger(rows.getCell(cel++)));
                info.setFreq(excel.getCellInteger(rows.getCell(cel++)));
                info.setMean(excel.getCellInteger(rows.getCell(cel++)));
                info.setSetLevel(excel.getCellString(rows.getCell(cel++)).trim());
                info.setPoType(excel.getCellString(rows.getCell(cel++)).trim());
                info.setOrderType(excel.getCellString(rows.getCell(cel++)).trim());
                info.setProdSeri(excel.getCellString(rows.getCell(cel++)).trim());
                info.setStateRange(excel.getCellString(rows.getCell(cel++)).trim());
                info.setMinPackageQty(excel.getCellInteger(rows.getCell(cel++)));
                if (info.getMinPackageQty() > 0 && info.getQtyBin() % info.getMinPackageQty() > 0) {
                    redisUtil.set(rediskey, "excel表第" + (row + 1) + "行，第" + cel + "列数据有问题;" + "存在最小打包数的Bin数量必须是最小打包数的倍数！");
                    redisUtil.del(updkey);
                    return;
                }
                info.setSetFreq(excel.getCellInteger(rows.getCell(cel++)));
                info.setDirectPurchase(excel.getCellInteger(rows.getCell(cel++)));
                info.setDirectDelivery(excel.getCellInteger(rows.getCell(cel++)));
                info.setAutoRepl(excel.getCellInteger(rows.getCell(cel++)));
                info.setBinType(excel.getCellString(rows.getCell(cel++)).trim());
                info.setSetSupplierCode(excel.getCellString(rows.getCell(cel++)).trim());
                cel++;
                cel++;
                cel++;
                cel++;
                cel++;
                cel++;
                cel++;
                cel++;
                cel++;
                cel++;
                info.setCentreFlag(excel.getCellInteger(rows.getCell(cel++)));
                if (info.getCentreFlag() != null && info.getCentreFlag() == 1) {
                    if (centreModelSet.contains(info.getModelNo())) {
                        redisUtil.set(rediskey, "excel表第" + (row + 1) + "行，第" + cel + "列数据有问题;" + "中央仓不能重复写入！");
                        redisUtil.del(updkey);
                        return;
                    }
                    centreModelSet.add(info.getModelNo());
                }
                String client = excel.getCellString(rows.getCell(cel++));
                if(StringUtils.isNotEmpty(client)){
                    info.setClient(Arrays.asList(client.split(",")));
                }
                info.setDelFlag(0);
//                info.setUpdateTime(DateUtil.getNow());
//                info.setCreateTime(DateUtil.getNow());
                if (PublicUtil.isEmpty(info.getStockType())
                        || PublicUtil.isEmpty(info.getModelNo()) || PublicUtil.isEmpty(info.getWarehouseCode())) {
                    redisUtil.set(rediskey, "前面三列不能为空！");
                    redisUtil.del(updkey);
                    return;
                }
                if (info.getStockType() == 1 && masterWarehouseCodes.contains(info.getWarehouseCode())) {
                    info.setCustomerNo("");
                    info.setPropertyID(1L);
                    info.setInventoryTypeCode("TY");
                    info.setPpl("");
                    info.setProjectNo("");
                    info.setGroupCustomerNo("");
                }
                list.add(info);
                String key = info.getStockType() + info.getWarehouseCode() + info.getCustomerNo() + info.getPropertyID();
                if (!map.containsKey(key)) {
                    map.put(key, info);
                }
                if (modelmap.containsKey(key + info.getModelNo())) {
                    redisUtil.set(rediskey, "excel表第" + (row + 1) + "行，型号" + info.getModelNo() + "重复使用！");
                    redisUtil.del(updkey);
                    return;
                }
                modelmap.put(key + info.getModelNo(), info.getModelNo());
                row++;
            } catch (Exception e) {
                redisUtil.set(rediskey, "excel表第" + (row + 1) + "行，第" + cel + "列数据有问题;" + e.getMessage());
                redisUtil.del(updkey);
                return;
            }
        }
        log.info(String.valueOf(row));

        try {
            //System.out.println(map);
            //System.out.println(map.size());
            List<BindataDO> dellist = new ArrayList<>();
            int count = 0;
            List<BindataDO> maplist = new ArrayList<>(map.values());
            //按照4字段查询数据库中的数据
            for (BindataDO item : maplist) {
                //库存属性数据更新错误则不导入
                if (item.getStockType() != 1 || !masterWarehouseCodes.contains(item.getWarehouseCode())) {
                    SMCApp.setGrantType(GlobalConstant.GrantType.CLIANT_CREDENTIALS);
                    if (!IsUpdProperty(item)) {
                        log.info(String.valueOf("查属性异常"));
                        redisUtil.set(rediskey, "查属性异常");
                        redisUtil.del(updkey);
                        return;
//                    return ResultVo.failure("获取库存项目属性失败");
                    }
                }

                //查出每种类存在的数据，每种类别一个key,排除已删除的 bug8615 begin
                QueryWrapper<BindataDO> query = new QueryWrapper<>();
                query.select("id", "ModelNo")
                        .eq("StockType", item.getStockType())
                        .eq("warehouse_code", item.getWarehouseCode())
                        .eq("CustomerNo", item.getCustomerNo())
                        .eq("Property_ID", item.getPropertyID())
                        .eq("delFlag", 0);
                List<BindataDO> typlist = bindataMapper.selectList(query);  //bug8615 end
                if (typlist != null) {
                    for (BindataDO typinfo : typlist) {
                        typinfo.setModelNo(typinfo.getModelNo() + item.getStockType() + item.getWarehouseCode() + item.getCustomerNo() + item.getPropertyID());
                    }
                    dellist.addAll(typlist);
                }
            }
            //分离出应该删的 和 应该更新写入的
            for (BindataDO binDO : list) {
                //移出excel存在的数据
                String typemodelNo = binDO.getModelNo() + binDO.getStockType() + binDO.getWarehouseCode() + binDO.getCustomerNo() + binDO.getPropertyID();
                dellist.removeIf(i -> i.getModelNo().equalsIgnoreCase(typemodelNo));
                //bin类型默认通用
                binDO.setLastdelFlag(0);
                if (binDO.getStockType() != 1 || !masterWarehouseCodes.contains(binDO.getWarehouseCode())) {
                    String key = binDO.getStockType() + binDO.getWarehouseCode() + binDO.getCustomerNo() + binDO.getPropertyID();
                    binDO.setPropertyID(map.get(key).getPropertyID());
                    binDO.setInventoryTypeCode(map.get(key).getInventoryTypeCode());
                }
                boolean istrue = updOrAddBinData(binDO);
                if (istrue) {
                    count++;
                    redisUtil.set(rediskey, "已导入" + count + "条记录");
                }
            }
            //删除不在excel表的型号
            if (dellist != null && dellist.size() > 0) {
                BindataDO delDO;
                for (BindataDO binDO : dellist) {
                    delDO = new BindataDO();
                    delDO.setId(binDO.getId());
                    // begin bug8932
                    delDO.setQtyBin(0);
                    delDO.setBinCell(0);
                    // end bug8932
                    delDO.setDelFlag(1);
                    bindataMapper.updateById(delDO);
                }
            }

            redisUtil.set(rediskey, "已完全导入成功，共" + count + "条记录", 86400);
            redisUtil.del(updkey);
            updateProductInfo();
        } catch (Exception e) {
            redisUtil.set(rediskey, "导入失败！" + e.getMessage());
            redisUtil.del(updkey);
        }
*/

    }
    private List<String> getMasterWarehouseCodes() {
        try {
            ResultVo<List<WarehouseVO>> warehouseByType = opsCommonService.getWarehouseByType(WarehouseTypeEnum.RDC.getHouseTypeCode());
            if (warehouseByType.isSuccess() && CollectionUtils.isNotEmpty(warehouseByType.getData())) {
                return warehouseByType.getData().stream()
                        .map(WarehouseVO::getWarehouseCode)
                        .collect(Collectors.toList());
            } else {
                return commonService.getMasterWarehouseCodes();
            }
        } catch (Exception e) {
            log.error("获取主仓库代码失败: ", e);
            return null;
        }
    }

    private BindataImportLogContext currentImportLog() {
        return bindataImportLogHolder.get();
    }

    private String buildCategoryKey(BindataDO bindataDO) {
        if (bindataDO == null) {
            return StringUtils.EMPTY;
        }
        return bindataDO.getStockType()
                + "|" + StringUtils.defaultString(bindataDO.getWarehouseCode())
                + "|" + StringUtils.defaultString(bindataDO.getCustomerNo())
                + "|" + bindataDO.getPropertyID();
    }

    private String buildBindataFullKey(BindataDO bindataDO) {
        if (bindataDO == null) {
            return StringUtils.EMPTY;
        }
        return buildCategoryKey(bindataDO) + "|" + StringUtils.defaultString(bindataDO.getModelNo());
    }

    private void logImportSlowCategory(String phase, long costMs) {
        BindataImportLogContext importLog = currentImportLog();
        if (importLog != null) {
            importLog.slowCategory(phase, costMs);
        }
    }

    private void logImportSlowRow(String phase, long costMs) {
        BindataImportLogContext importLog = currentImportLog();
        if (importLog != null) {
            importLog.slowRow(phase, costMs);
        }
    }

    private String buildCentreResetKey(BindataDO bindataDO) {
        if (bindataDO == null) {
            return StringUtils.EMPTY;
        }
        return bindataDO.getStockType()
                + "|" + StringUtils.defaultString(bindataDO.getModelNo())
                + "|" + StringUtils.defaultString(bindataDO.getCustomerNo())
                + "|" + bindataDO.getPropertyID();
    }

    private List<String> normalizeClients(List<String> clients) {
        if (CollectionUtils.isEmpty(clients)) {
            return Collections.emptyList();
        }
        return clients.stream()
                .map(StringUtils::trimToEmpty)
                .filter(StringUtils::isNotBlank)
                .distinct()
                .sorted()
                .collect(Collectors.toList());
    }

    private Map<Integer, List<String>> loadClientWarehouseMap(Collection<BindataDO> bindataList) {
        Map<Integer, List<String>> clientMap = new HashMap<>();
        if (CollectionUtils.isEmpty(bindataList)) {
            return clientMap;
        }
        List<Long> bindataIds = bindataList.stream()
                .map(BindataDO::getId)
                .filter(Objects::nonNull)
                .map(Integer::longValue)
                .collect(Collectors.toList());
        for (int start = 0; start < bindataIds.size(); start += IMPORT_QUERY_BATCH_SIZE) {
            List<Long> idChunk = bindataIds.subList(start, Math.min(start + IMPORT_QUERY_BATCH_SIZE, bindataIds.size()));
            LambdaQueryWrapper<BindataClientWarehouseDO> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.select(BindataClientWarehouseDO::getBindataId, BindataClientWarehouseDO::getWarehouseCode)
                    .eq(BindataClientWarehouseDO::getDelFlag, 0)
                    .in(BindataClientWarehouseDO::getBindataId, idChunk);
            List<BindataClientWarehouseDO> relationList = bindataClientWarehouseMapper.selectList(queryWrapper);
            for (BindataClientWarehouseDO relation : relationList) {
                clientMap.computeIfAbsent(relation.getBindataId().intValue(), key -> new ArrayList<>())
                        .add(relation.getWarehouseCode());
            }
        }
        for (List<String> warehouses : clientMap.values()) {
            Collections.sort(warehouses);
        }
        return clientMap;
    }

    private Map<String, Set<String>> buildCurrentCentreKeyMap(Map<String, BindataDO> activeBindataMap) {
        Map<String, Set<String>> centreKeyMap = new HashMap<>();
        for (Map.Entry<String, BindataDO> entry : activeBindataMap.entrySet()) {
            BindataDO bindataDO = entry.getValue();
            if (Objects.equals(bindataDO.getCentreFlag(), 1)) {
                centreKeyMap.computeIfAbsent(buildCentreResetKey(bindataDO), key -> new HashSet<>()).add(entry.getKey());
            }
        }
        return centreKeyMap;
    }

    private boolean isCentreResetRequired(String fullKey, BindataDO target, Map<String, Set<String>> currentCentreKeyMap) {
        if (!Objects.equals(target.getCentreFlag(), 1)) {
            return false;
        }
        Set<String> currentCentreKeys = currentCentreKeyMap.get(buildCentreResetKey(target));
        if (CollectionUtils.isEmpty(currentCentreKeys)) {
            return false;
        }
        return currentCentreKeys.size() != 1 || !currentCentreKeys.contains(fullKey);
    }

    private boolean isClientSyncRequired(BindataDO target, BindataDO existing, Map<Integer, List<String>> currentClientMap) {
        List<String> incomingClients = normalizeClients(target.getClient());
        target.setClient(incomingClients);
        if (existing == null) {
            return Objects.equals(target.getCentreFlag(), 1) && CollectionUtils.isNotEmpty(incomingClients);
        }
        List<String> currentClients = currentClientMap.getOrDefault(existing.getId(), Collections.emptyList());
        boolean existingCentral = Objects.equals(existing.getCentreFlag(), 1);
        boolean incomingCentral = Objects.equals(target.getCentreFlag(), 1);
        if (!existingCentral && !incomingCentral && CollectionUtils.isEmpty(currentClients) && CollectionUtils.isEmpty(incomingClients)) {
            return false;
        }
        if (existingCentral != incomingCentral) {
            return true;
        }
        return !currentClients.equals(incomingClients);
    }
    //验重
    private boolean isMainDataChanged(BindataDO target, BindataDO existing) {
        if (existing == null) {
            return true;
        }
        if (!Objects.equals(existing.getDelFlag(), 0)) {
            return true;
        }
        return !Objects.equals(target.getStockType(), existing.getStockType())
                || !Objects.equals(target.getWarehouseCode(), existing.getWarehouseCode())
                || !Objects.equals(target.getModelNo(), existing.getModelNo())
                || !Objects.equals(target.getCustomerNo(), existing.getCustomerNo())
                || !Objects.equals(target.getPropertyID(), existing.getPropertyID())
                || !Objects.equals(target.getInventoryTypeCode(), existing.getInventoryTypeCode())
                || !Objects.equals(target.getGroupCustomerNo(), existing.getGroupCustomerNo())
                || !Objects.equals(target.getPpl(), existing.getPpl())
                || !Objects.equals(target.getProjectNo(), existing.getProjectNo())
                || !Objects.equals(target.getQtyBin(), existing.getQtyBin())
                || !Objects.equals(target.getBinCell(), existing.getBinCell())
                || !Objects.equals(target.getCaseType(), existing.getCaseType())
                || !Objects.equals(target.getProdType(), existing.getProdType())
                || !Objects.equals(target.getMeshType(), existing.getMeshType())
                || !Objects.equals(target.getInCaseQty(), existing.getInCaseQty())
                || !Objects.equals(target.getAdjustable(), existing.getAdjustable())
                || !Objects.equals(target.getSafeQty(), existing.getSafeQty())
                || !Objects.equals(target.getFreq(), existing.getFreq())
                || !Objects.equals(target.getMean(), existing.getMean())
                || !Objects.equals(target.getSetLevel(), existing.getSetLevel())
                || !Objects.equals(target.getPoType(), existing.getPoType())
                || !Objects.equals(target.getOrderType(), existing.getOrderType())
                || !Objects.equals(target.getProdSeri(), existing.getProdSeri())
                || !Objects.equals(target.getStateRange(), existing.getStateRange())
                || !Objects.equals(target.getMinPackageQty(), existing.getMinPackageQty())
                || !Objects.equals(target.getSetFreq(), existing.getSetFreq())
                || !Objects.equals(target.getDirectPurchase(), existing.getDirectPurchase())
                || !Objects.equals(target.getDirectDelivery(), existing.getDirectDelivery())
                || !Objects.equals(target.getAutoRepl(), existing.getAutoRepl())
                || !Objects.equals(target.getBinType(), existing.getBinType())
                || !Objects.equals(target.getSetSupplierCode(), existing.getSetSupplierCode())
                || !Objects.equals(target.getCentreFlag(), existing.getCentreFlag())
                || !Objects.equals(target.getDelFlag(), existing.getDelFlag())
                || !Objects.equals(existing.getLastdelFlag(), 0);
    }

    private boolean isDeleteBindataRow(BindataDO bindataDO) {
        return Objects.equals(bindataDO.getDelFlag(), 1);
    }

    private PersistPlan buildPersistPlan(List<BindataDO> list,
                                         Set<String> masterWarehouseCodeSet,
                                         Map<String, BindataDO> categoryMap,
                                         Map<String, BindataDO> existingBindataMap,
                                         Map<String, BindataDO> activeBindataMap,
                                         Set<String> importedFullKeySet,
                                         Set<String> ambiguousExistingKeySet,
                                         Map<Integer, List<String>> currentClientMap) {
        // 初始化计划对象：承载本次导入的插入/更新/跳过/删旧/同步关系等动作。
        PersistPlan plan = new PersistPlan();
        // 先把当前库里“已生效的中央仓”整理成索引，便于后续判断是否需要先重置旧中央仓。
        Map<String, Set<String>> currentCentreKeyMap = buildCurrentCentreKeyMap(activeBindataMap);
        // 同一个中央仓维度只需要重置一次，这里用于去重。
        Set<String> plannedResetKeys = new HashSet<>();
        for (BindataDO bindataDO : list) {
            // 持久化前补齐最终要落库的属性值，例如沿用同大类下已解析出的库存属性信息。
            preparePersistBindata(bindataDO, masterWarehouseCodeSet, categoryMap);
            // 生成“完整唯一键”：大 key + 型号，用于匹配库里现有记录。
            String fullKey = buildBindataFullKey(bindataDO);
            // 记录本次导入覆盖到的 fullKey，后面用来反推哪些旧活跃数据需要删除。
            importedFullKeySet.add(fullKey);
            // 如果同一个 fullKey 在库里存在多条候选记录，则记一次“歧义回退”统计。
            if (ambiguousExistingKeySet.contains(fullKey)) {
                plan.ambiguousFallbackCount++;
            }
            // 找到当前导入行对应的旧数据；找不到则说明后续要新增。
            BindataDO existing = existingBindataMap.get(fullKey);
            PersistPlanItem item = new PersistPlanItem();
            item.target = bindataDO;
            item.existing = existing;
            // 生成“中央仓冲突判断键”，同维度下如果新的记录要设为中央仓，旧的中央仓可能要先清零。
            item.centreResetKey = buildCentreResetKey(bindataDO);
            boolean deleteImportRow = isDeleteBindataRow(bindataDO);
            // 判断是否需要重置旧中央仓；并且同一个 resetKey 只计划一次。
            item.needCentreReset = !deleteImportRow && isCentreResetRequired(fullKey, bindataDO, currentCentreKeyMap) && plannedResetKeys.add(item.centreResetKey);
            // 判断 client 集约关系是否有变化；有变化则后续要同步关系表。
            item.needClientSync = !deleteImportRow && isClientSyncRequired(bindataDO, existing, currentClientMap);
            // 没有旧记录：计划新增。
            if (existing == null) {
                item.action = PersistAction.INSERT;
            // 有旧记录但主数据字段发生变化：计划更新。
            } else if (isMainDataChanged(bindataDO, existing)) {
                item.action = PersistAction.UPDATE;
            // 主数据没变：主表无需动作，但可能仍要执行中央仓重置或 client 同步。
            } else {
                item.action = PersistAction.NONE;
                plan.noChangeSkipCount++;
            }
            // 保留原始顺序，后续按输入顺序逐条执行计划。
            plan.orderedItems.add(item);
        }
        // 反查当前活跃数据：凡是不在本次 importedFullKeySet 中的，都视为“本次未导入的旧活跃数据”，加入删除计划。
        for (Map.Entry<String, BindataDO> entry : activeBindataMap.entrySet()) {
            if (!importedFullKeySet.contains(entry.getKey())) {
                plan.deleteIds.add(entry.getValue().getId());
            }
        }
        return plan;
    }

    private void executeCentreReset(BindataDO bindataDO) {
        BindataDO update = new BindataDO();
        update.setCentreFlag(0);
        LambdaUpdateWrapper<BindataDO> query = new LambdaUpdateWrapper<>();
        query.eq(BindataDO::getCentreFlag, 1)
                .eq(BindataDO::getStockType, bindataDO.getStockType())
                .eq(BindataDO::getModelNo, bindataDO.getModelNo())
                .eq(BindataDO::getCustomerNo, bindataDO.getCustomerNo())
                .eq(BindataDO::getPropertyID, bindataDO.getPropertyID());
        long resetStart = System.currentTimeMillis();
        bindataMapper.update(update, query);
        logImportSlowRow("reset_centre_flag", System.currentTimeMillis() - resetStart);
    }

    private void executeBindataUpdate(PersistPlanItem item) {
        BindataDO bindataDO = item.target;
        BindataDO existing = item.existing;
        bindataDO.setId(existing.getId());
        if (existing.getDelFlag() != null && existing.getDelFlag() == 1) {
            bindataDO.setLoginDate(DateUtil.getNow());
        }
        bindataDO.setCreateTime(null);
        long updateStart = System.currentTimeMillis();
        if (bindataMapper.updateById(bindataDO) < 1) {
            throw new BusinessException("更新Bindata失败: " + bindataDO.getModelNo());
        }
        logImportSlowRow("update_bindata_main", System.currentTimeMillis() - updateStart);
    }

    private void executeBindataInsert(PersistPlanItem item) {
        BindataDO bindataDO = item.target;
        bindataDO.setLoginDate(DateUtil.getNow());
        long insertStart = System.currentTimeMillis();
        if (bindataMapper.insert(bindataDO) < 1) {
            throw new BusinessException("新增Bindata失败: " + bindataDO.getModelNo());
        }
        logImportSlowRow("insert_bindata_main", System.currentTimeMillis() - insertStart);
    }

    private PersistStats executePersistPlan(PersistPlan plan, BindataImportLogContext importLog, long phaseStart) {
        // 初始化执行统计：用于记录成功条数、更新数、新增数、client 同步数等结果。
        PersistStats stats = new PersistStats();
        // 计划阶段已算出的统计项，直接透传到执行结果。
        stats.ambiguousFallbackCount = plan.ambiguousFallbackCount;
        stats.noChangeSkipCount = plan.noChangeSkipCount;
        // 同一个中央仓维度只执行一次 reset，避免重复 update。
        Set<String> executedResetKeys = new HashSet<>();
        List<PersistPlanItem> orderedItems = plan.orderedItems;
        for (int dataIndex = 0; dataIndex < orderedItems.size(); dataIndex++) {
            PersistPlanItem item = orderedItems.get(dataIndex);
            // 先处理中央仓清零：确保当前要设为中央仓的记录落库前，旧中央仓标记已被重置。
            if (item.needCentreReset && executedResetKeys.add(item.centreResetKey)) {
                executeCentreReset(item.target);
                stats.centreResetCount++;
            }
            // 主表动作一：更新已有 bindata。
            if (item.action == PersistAction.UPDATE) {
                executeBindataUpdate(item);
                stats.updateCount++;
            // 主表动作二：插入新 bindata。
            } else if (item.action == PersistAction.INSERT) {
                executeBindataInsert(item);
                stats.insertCount++;
            }
            // 关系表动作：同步被集约仓 client 关系；如果主表没动但关系变了，也会执行这里。
            if (item.needClientSync) {
                // 更新场景下 target 可能还没显式带 id，这里兜底补上，保证关系表能关联到正确 bindata。
                if (item.target.getId() == null && item.existing != null) {
                    item.target.setId(item.existing.getId());
                }
                updateClientWarehouse(item.target);
                stats.clientSyncCount++;
            }
            // 当前计划项没有抛异常，就视为执行成功。
            stats.successCount++;
            // 定期刷新 Redis 进度，供前端/调用方查看导入进展。
            stats.progressUpdateCount += importLog.recordPersistProgress(stats.successCount,
                    dataIndex + 1,
                    orderedItems.size(),
                    stats.ambiguousFallbackCount,
                    stats.noChangeSkipCount,
                    stats.updateCount,
                    stats.insertCount,
                    stats.clientSyncCount,
                    stats.centreResetCount,
                    phaseStart);
        }
        return stats;
    }

    private int batchDeleteStaleBindata(List<Integer> deleteIds) {
        if (CollectionUtils.isEmpty(deleteIds)) {
            return 0;
        }
        int deletedCount = 0;
        for (int start = 0; start < deleteIds.size(); start += IMPORT_QUERY_BATCH_SIZE) {
            List<Integer> idChunk = deleteIds.subList(start, Math.min(start + IMPORT_QUERY_BATCH_SIZE, deleteIds.size()));
            LambdaUpdateWrapper<BindataDO> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.in(BindataDO::getId, idChunk)
                    .set(BindataDO::getQtyBin, 0)
                    .set(BindataDO::getBinCell, 0)
                    .set(BindataDO::getDelFlag, 1);
            bindataMapper.update(null, updateWrapper);
            deletedCount += idChunk.size();
        }
        return deletedCount;
    }

    private void preparePersistBindata(BindataDO binDO, Set<String> masterWarehouseCodeSet, Map<String, BindataDO> map) {
        binDO.setLastdelFlag(0);
        if (binDO.getStockType() != 1 || !masterWarehouseCodeSet.contains(binDO.getWarehouseCode())) {
            String key = buildCategoryKey(binDO);
            BindataDO categoryInfo = map.get(key);
            if (categoryInfo != null) {
                binDO.setPropertyID(categoryInfo.getPropertyID());
                binDO.setInventoryTypeCode(categoryInfo.getInventoryTypeCode());
            }
        }
    }

    private BindataDO buildImportBindata(BindataExcelVO excelVO) {
        BindataDO info = new BindataDO();
        if (excelVO == null) {
            return info;
        }
        // 先只做字段搬运，默认值和必填校验统一放到 applyDefaultAndValidateBindata。
        info.setStockType(excelVO.getStockType());
        info.setWarehouseCode(excelVO.getWarehouseCode());
        info.setModelNo(excelVO.getModelNo());
        info.setCustomerNo(excelVO.getCustomerNo());
        info.setPropertyID(excelVO.getPropertyID());
        info.setInventoryTypeCode(excelVO.getInventoryTypeCode());
        info.setGroupCustomerNo(excelVO.getGroupCustomerNo());
        info.setPpl(excelVO.getPpl());
        info.setProjectNo(excelVO.getProjectNo());
        info.setQtyBin(excelVO.getQtyBin());
        info.setBinCell(excelVO.getBinCell());
        info.setCaseType(excelVO.getCaseType());
        info.setProdType(excelVO.getProdType());
        info.setMeshType(excelVO.getMeshType());
        info.setInCaseQty(excelVO.getInCaseQty());
        info.setAdjustable(excelVO.getAdjustable());
        info.setSafeQty(excelVO.getSafeQty());
        info.setFreq(excelVO.getFreq());
        info.setMean(excelVO.getMean());
        info.setSetLevel(excelVO.getSetLevel());
        info.setPoType(excelVO.getPoType());
        info.setOrderType(excelVO.getOrderType());
        info.setProdSeri(excelVO.getProdSeri());
        info.setStateRange(excelVO.getStateRange());
        info.setMinPackageQty(excelVO.getMinPackageQty());
        info.setSetFreq(excelVO.getSetFreq());
        info.setDirectPurchase(excelVO.getDirectPurchase());
        info.setDirectDelivery(excelVO.getDirectDelivery());
        info.setAutoRepl(excelVO.getAutoRepl());
        info.setBinType(excelVO.getBinType());
        info.setSetSupplierCode(excelVO.getSetSupplierCode());
        info.setCentreFlag(excelVO.getCentreFlag());
        return info;
    }

    // 导入和单条保存共用同一套规则，避免两处入口出现默认值或必填校验不一致。
    private String applyDefaultAndValidateBindata(BindataDO info) {
        if (info == null) {
            return "导入数据不能为空";
        }
        if (info.getStockType() == null) {
            return "必须输入库存类型";
        }
        info.setWarehouseCode(StringUtils.trimToEmpty(info.getWarehouseCode()));
        if (StringUtils.isBlank(info.getWarehouseCode())) {
            return "必须输入库房代码";
        }
        info.setModelNo(StringUtils.trimToEmpty(info.getModelNo()));
        if (StringUtils.isBlank(info.getModelNo())) {
            return "必须输入型号";
        }
        info.setCustomerNo(StringUtils.trimToEmpty(info.getCustomerNo()));
        if (info.getPropertyID() == null) {
            return "必须输入库存属性ID";
        }
        info.setInventoryTypeCode(StringUtils.trimToEmpty(info.getInventoryTypeCode()));
        if (StringUtils.isBlank(info.getInventoryTypeCode())) {
            return "必须输入库存类型代码";
        }
        info.setGroupCustomerNo(StringUtils.trimToEmpty(info.getGroupCustomerNo()));
        info.setPpl(StringUtils.trimToEmpty(info.getPpl()));
        info.setProjectNo(StringUtils.trimToEmpty(info.getProjectNo()));
        if (info.getQtyBin() == null) {
            return "必须输入Bin数量";
        }
        if (info.getQtyBin() < 0) {
            return "Bin数量必须大于等于0";
        }
        if (info.getBinCell() == null) {
            return "必须输入Bin数";
        }
        if (info.getBinCell() < 0 || info.getBinCell() > 6) {
            return "Bin数必须大于等于0且小于等于6";
        }
        // 按新模板规则补默认值：空字符串字段统一 trim，数值型配置项缺省按 0 处理。
        info.setCaseType(StringUtils.trimToEmpty(info.getCaseType()));
        info.setProdType(StringUtils.defaultIfBlank(StringUtils.trimToEmpty(info.getProdType()), "0"));
        info.setMeshType(StringUtils.trimToEmpty(info.getMeshType()));
        if (info.getInCaseQty() == null) {
            info.setInCaseQty(0);
        }
        info.setAdjustable(StringUtils.defaultIfBlank(StringUtils.trimToEmpty(info.getAdjustable()), "0"));
        if (info.getSafeQty() == null) {
            info.setSafeQty(0);
        }
        if (info.getFreq() == null) {
            return "必须输入8频率";
        }
        if (info.getMean() == null) {
            return "必须输入8平均";
        }
        info.setSetLevel(StringUtils.trimToEmpty(info.getSetLevel()));
        if (StringUtils.isBlank(info.getSetLevel())) {
            return "必须输入等级";
        }
        info.setPoType(StringUtils.trimToEmpty(info.getPoType()));
        info.setOrderType(StringUtils.trimToEmpty(info.getOrderType()));
        if (StringUtils.isBlank(info.getOrderType())) {
            return "必须输入订单类别";
        }
        info.setProdSeri(StringUtils.trimToEmpty(info.getProdSeri()));
        info.setStateRange(StringUtils.trimToEmpty(info.getStateRange()));
        if (info.getMinPackageQty() == null) {
            info.setMinPackageQty(0);
        }
        if (info.getSetFreq() == null) {
            return "必须输入设定平均";
        }
        if (info.getDirectPurchase() == null) {
            info.setDirectPurchase(0);
        }
        if (info.getDirectDelivery() == null) {
            info.setDirectDelivery(0);
        }
        if (info.getAutoRepl() == null) {
            info.setAutoRepl(0);
        }
        info.setBinType(StringUtils.trimToEmpty(info.getBinType()));
        info.setSetSupplierCode(StringUtils.trimToEmpty(info.getSetSupplierCode()));
        if (info.getCentreFlag() == null) {
            return "必须输入中央仓标识";
        }
        // Bin 数量或 Bin 数为 0 表示显式删除该 Bin 数据，但仍保留本行导入/保存的其他字段。
        info.setDelFlag(info.getBinCell() == 0 || info.getQtyBin() == 0 ? 1 : 0);
        return null;
    }

    private void abortImportValidation(String rediskey, String updkey, BindataImportLogContext importLog, int row, String message) {
        importLog.abort("prepare_input", message, row + 1);
        redisUtil.set(rediskey, "第" + (row + 1) + "行，" + message);
        redisUtil.del(updkey);
    }

    private String validateMinPackageQty(BindataDO info) {
        if (info.getMinPackageQty() > 0 && info.getQtyBin() % info.getMinPackageQty() > 0) {
            return "存在最小打包数的Bin数量必须是最小打包数的倍数";
        }
        return null;
    }

    private void standardizeMasterWarehouseBindata(BindataDO info) {
        info.setCustomerNo("");
        info.setPropertyID(1L);
        info.setInventoryTypeCode("TY");
        info.setPpl("");
        info.setProjectNo("");
        info.setGroupCustomerNo("");
    }

    @Async
    @Override
    public void asyupdAndAddBinDataV2(List<BindataExcelVO> voList, String rediskey) {

        // 1. 初始化导入上下文：Redis 进度、阶段耗时和统计指标都挂在本次 importId 下。
        String updkey = "ops:binimp:";
        String importId = UUID.randomUUID().toString().replace("-", "");
        BindataImportLogContext importLog = new BindataImportLogContext(log, redisUtil, rediskey, importId, voList == null ? 0 : voList.size());
        bindataImportLogHolder.set(importLog);
        long importStart = System.currentTimeMillis();
        long masterWarehouseCostMs = 0L;
        long warehouseListCostMs = 0L;
        long prepareInputCostMs = 0L;
        long preloadCostMs = 0L;
        long clientPreloadCostMs = 0L;
        long persistCostMs = 0L;
        long deleteCostMs = 0L;
        long updateProductInfoCostMs = 0L;
        int propertyUpdateCount = 0;
        long propertyUpdateCostMs = 0L;
        int existingQueryCount = 0;
        long existingQueryCostMs = 0L;
        int staleCandidateCount = 0;
        int deletedCount;
        int successCount;
        int progressUpdateCount;

        try {
            List<BindataDO> list = new ArrayList<>(voList == null ? 16 : voList.size());
            Map<String, BindataDO> map = new HashMap<>();
            Map<String, String> modelmap = new HashMap<>();
            BindataDO info;
            Set<String> centreModelSet = new HashSet<>();

            // 2. 加载主仓和可用被集约仓清单，后续用于主仓标准化、中央仓校验和 client 校验。
            long phaseStart = System.currentTimeMillis();
            List<String> masterWarehouseCodes = getMasterWarehouseCodes();
            masterWarehouseCostMs = System.currentTimeMillis() - phaseStart;
            importLog.phase("load_master_warehouses", masterWarehouseCostMs, masterWarehouseCodes == null ? 0 : masterWarehouseCodes.size(),
                    "success", masterWarehouseCodes != null,
                    "warehouseCount", masterWarehouseCodes == null ? 0 : masterWarehouseCodes.size());
            if (masterWarehouseCodes == null) {
                importLog.abort("load_master_warehouses", "获取主仓库代码失败", null);
                redisUtil.set(rediskey, "获取主仓库代码失败");
                redisUtil.del(updkey);
                return;
            }

            Set<String> masterWarehouseCodeSet = new HashSet<>(masterWarehouseCodes);

            phaseStart = System.currentTimeMillis();
            Set<String> warehouseCodeSet = new HashSet<>();
            warehouseCodeSet.add("ALL");
            SMCApp.setGrantType(GlobalConstant.GrantType.CLIANT_CREDENTIALS);
            ResultVo<List<com.smc.smccloud.model.warehouse.WarehouseVO>> listResultVo = commonServiceFeignApi.listWarehouseNoWT();
            boolean warehouseFetchSuccess = listResultVo != null && listResultVo.isSuccess();
            if (warehouseFetchSuccess) {
                List<com.smc.smccloud.model.warehouse.WarehouseVO> data = listResultVo.getData();
                if (CollectionUtils.isNotEmpty(data)) {
                    for (com.smc.smccloud.model.warehouse.WarehouseVO warehouseVO : data) {
                        warehouseCodeSet.add(warehouseVO.getWarehouseCode());
                    }
                }
            } else {
                redisUtil.set(rediskey, "获取仓库代码失败");
                redisUtil.del(updkey);
            }
            warehouseListCostMs = System.currentTimeMillis() - phaseStart;
            importLog.phase("load_client_warehouses", warehouseListCostMs, warehouseCodeSet.size(),
                    "success", warehouseFetchSuccess,
                    "warehouseCount", warehouseCodeSet.size());

            // 3. 逐行解析 Excel：统一默认值/必填校验，处理 0 值删除标记、中央仓唯一性和被集约仓格式。
            phaseStart = System.currentTimeMillis();
            for (int rowIndex = 0; rowIndex < voList.size(); rowIndex++) {
                try {
                    BindataExcelVO excelVO = voList.get(rowIndex);
                    int row = rowIndex + 1;

                    info = buildImportBindata(excelVO);
                    // 导入入口复用单条保存的默认值和必填校验，错误信息按导入行号写入 Redis。
                    String validationMessage = applyDefaultAndValidateBindata(info);
                    if (StringUtils.isNotBlank(validationMessage)) {
                        abortImportValidation(rediskey, updkey, importLog, row, validationMessage);
                        return;
                    }
                    validationMessage = validateMinPackageQty(info);
                    if (StringUtils.isNotBlank(validationMessage)) {
                        abortImportValidation(rediskey, updkey, importLog, row, validationMessage);
                        return;
                    }
                    boolean deleteImportRow = isDeleteBindataRow(info);

                    if (!deleteImportRow && info.getCentreFlag() != null && info.getCentreFlag() == 1) {
                        if (!masterWarehouseCodeSet.contains(info.getWarehouseCode())) {
                            info.setCentreFlag(0);
                        } else if (centreModelSet.contains(info.getModelNo())) {
                            importLog.abort("prepare_input", "中央仓不能重复写入", row + 1);
                            redisUtil.set(rediskey, "第" + (row + 1) + "行，中央仓不能重复写入！");
                            redisUtil.del(updkey);
                            return;
                        }
                        centreModelSet.add(info.getModelNo());
                    }
                    String client = excelVO.getClient();
                    if (StringUtils.isNotEmpty(client)) {
                        List<String> clients = Arrays.asList(client.split(","));
                        for (String clent : clients) {
                            if (!warehouseCodeSet.contains(StringUtils.trim(clent))) {
                                importLog.abort("prepare_input", "被集约方格式错误", row + 1);
                                redisUtil.set(rediskey, "第" + (row + 1) + "行，被集约方格式错误，" + excelVO.getClient());
                                redisUtil.del(updkey);
                                return;
                            }
                        }
                        info.setClient(clients);
                    }
                    if (info.getStockType() == 1 && masterWarehouseCodeSet.contains(info.getWarehouseCode())) {
                        standardizeMasterWarehouseBindata(info);
                    }
                    list.add(info);
                    //库存类别，仓库代码，客户代码，库存属性id，型号，相同则视为统一 大类，相同直接覆盖
                    String key = buildCategoryKey(info);
                    if (!map.containsKey(key)) {
                        map.put(key, info);
                    }
                    //型号不能重复，直接报错
                    if (modelmap.containsKey(key + info.getModelNo())) {
                        importLog.abort("prepare_input", "型号重复使用", row + 1);
                        redisUtil.set(rediskey, "第" + (row + 1) + "行，型号" + info.getModelNo() + "重复使用！");
                        redisUtil.del(updkey);
                        return;
                    }
                    modelmap.put(key + info.getModelNo(), info.getModelNo());
                    if (importLog.shouldLogProgress(rowIndex + 1, voList.size())) {
                        importLog.progress("prepare_input", rowIndex + 1, voList.size(),
                                "categoryCount", map.size(),
                                "centreModelCount", centreModelSet.size(),
                                "elapsedMs", System.currentTimeMillis() - phaseStart);
                    }
                } catch (Exception e) {
                    int row = list.size();
                    importLog.abort("prepare_input", e.getMessage(), row + 1);
                    redisUtil.set(rediskey, "第" + (row + 1) + "行，第0列数据有问题;" + e.getMessage());
                    redisUtil.del(updkey);
                    log.error("第" + (row + 1) + "行，第0列数据有问题:" , e);
                    return;
                }
            }
            //分析完毕，开始验重写入
            prepareInputCostMs = System.currentTimeMillis() - phaseStart;
            importLog.phase("prepare_input", prepareInputCostMs, list.size(),
                    "categoryCount", map.size(),
                    "duplicateCheckCount", modelmap.size(),
                    "centreModelCount", centreModelSet.size());

            Map<String, BindataDO> existingBindataMap = new HashMap<>();
            Map<String, BindataDO> activeBindataMap = new HashMap<>();
            Set<String> importedFullKeySet = new HashSet<>(list.size());
            Set<String> ambiguousExistingKeySet = new HashSet<>();
            phaseStart = System.currentTimeMillis();
            List<BindataDO> maplist = new ArrayList<>(map.values());
            // 4. 按导入大类预加载旧数据，建立现有数据索引和活跃数据索引，避免逐行反复查库。
            for (int categoryIndex = 0; categoryIndex < maplist.size(); categoryIndex++) {
                BindataDO item = maplist.get(categoryIndex);
                //1.非物流中心，则查询库存属性id
                if (item.getStockType() != 1 || !masterWarehouseCodeSet.contains(item.getWarehouseCode())) {
                    SMCApp.setGrantType(GlobalConstant.GrantType.CLIANT_CREDENTIALS);
                    long propertyStart = System.currentTimeMillis();
                    //查询或写入库存属性id
                    boolean propertySuccess = IsUpdProperty(item);
                    long propertyCost = System.currentTimeMillis() - propertyStart;
                    propertyUpdateCount++;
                    propertyUpdateCostMs += propertyCost;
                    logImportSlowCategory("property_update", propertyCost);
                    if (!propertySuccess) {
                        importLog.abort("property_update", "查属性异常", null);
                        redisUtil.set(rediskey, "查属性异常");
                        redisUtil.del(updkey);
                        return;
                    }
                }
                //根据大类（不含型号）查询所有型号，查询本仓bindata表，获取所有型号，是否有效，中央仓标识
                LambdaQueryWrapper<BindataDO> query = new LambdaQueryWrapper<>();
                query.eq(BindataDO::getStockType, item.getStockType())
                        .eq(BindataDO::getWarehouseCode, item.getWarehouseCode())
                        .eq(BindataDO::getCustomerNo, item.getCustomerNo())
                        .eq(BindataDO::getPropertyID, item.getPropertyID());
                long existingQueryStart = System.currentTimeMillis();
                List<BindataDO> typlist = bindataMapper.selectList(query);
                long existingQueryCost = System.currentTimeMillis() - existingQueryStart;
                int relatedCount = typlist == null ? 0 : typlist.size();
                existingQueryCount++;
                existingQueryCostMs += existingQueryCost;
                //过时的候选者数量
                staleCandidateCount += relatedCount;
                logImportSlowCategory("query_existing_bindata", existingQueryCost);
                if (typlist != null) {
                    //如果表中存在数据，则验重并记录，存在同一主键但delFlag不同的记录时，以delFlag=0的为准覆盖到existingBindataMap中，后续persist时直接使用，无需再查库
                    for (BindataDO typinfo : typlist) {
                        String fullKey = buildBindataFullKey(typinfo);
                        //
                        BindataDO previous = existingBindataMap.get(fullKey);
                        //如果之前已经有数据了，说明fullKey在库里存在多条记录，记录到歧义列表中，后续这个fullKey的记录不管是更新还是新增，都不使用之前的数据，直接走全量覆盖的逻辑
                        if (previous != null) {
                            ambiguousExistingKeySet.add(fullKey);
                        }
                        //如果之前没有数据，或者之前的数据被删除了但现在的记录没被删除，则覆盖到existingBindataMap中，后续persist时直接使用，无需再查库
                        if (previous == null || (!Objects.equals(previous.getDelFlag(), 0) && Objects.equals(typinfo.getDelFlag(), 0))) {
                            existingBindataMap.put(fullKey, typinfo);
                        }
                        //只有delFlag=0的记录才算活跃数据，才加入activeBindataMap，后续用来反推哪些旧数据本次没有导入需要删除
                        if (Objects.equals(typinfo.getDelFlag(), 0)) {
                            activeBindataMap.put(fullKey, typinfo);
                        }
                    }
                }
                //打印库存属性更新条数，过时候选者数量，查询次数，查询耗时等指标，记录日志
                if (importLog.shouldLogProgress(categoryIndex + 1, maplist.size())) {
                    importLog.progress("preload_existing", categoryIndex + 1, maplist.size(),
                            "propertyUpdateCount", propertyUpdateCount,
                            "staleCandidateCount", staleCandidateCount,
                            "elapsedMs", System.currentTimeMillis() - phaseStart);
                }
            }
            preloadCostMs = System.currentTimeMillis() - phaseStart;
            importLog.phase("preload_existing", preloadCostMs, maplist.size(),
                    "propertyUpdateCount", propertyUpdateCount,
                    "propertyUpdateCostMs", propertyUpdateCostMs,
                    "existingQueryCount", existingQueryCount,
                    "existingQueryCostMs", existingQueryCostMs,
                    "staleCandidateCount", staleCandidateCount,
                    "ambiguousExistingKeyCount", ambiguousExistingKeySet.size());
            // 5. 预加载当前被集约仓关系，后续只同步确实发生变化的关系数据。
            phaseStart = System.currentTimeMillis();
            Map<Integer, List<String>> currentClientMap = loadClientWarehouseMap(activeBindataMap.values());
            clientPreloadCostMs = System.currentTimeMillis() - phaseStart;
            importLog.phase("preload_client_relations", clientPreloadCostMs, currentClientMap.size());
            // 6. 生成并执行持久化计划：统一决定新增、更新、跳过、中央仓重置和 client 同步。
            phaseStart = System.currentTimeMillis();
            PersistPlan persistPlan = buildPersistPlan(list, masterWarehouseCodeSet, map, existingBindataMap,
                    activeBindataMap, importedFullKeySet, ambiguousExistingKeySet, currentClientMap);
            PersistStats persistStats = executePersistPlan(persistPlan, importLog, phaseStart);
            successCount = persistStats.successCount;
            progressUpdateCount = persistStats.progressUpdateCount;
            persistCostMs = System.currentTimeMillis() - phaseStart;
            importLog.phase("persist_bindata", persistCostMs, list.size(),
                    "successCount", successCount,
                    "failedCount", list.size() - successCount,
                    "progressUpdateCount", progressUpdateCount,
                    "ambiguousFallbackCount", persistStats.ambiguousFallbackCount,
                    "noChangeSkipCount", persistStats.noChangeSkipCount,
                    "updateCount", persistStats.updateCount,
                    "insertCount", persistStats.insertCount,
                    "clientSyncCount", persistStats.clientSyncCount,
                    "centreResetCount", persistStats.centreResetCount);
            // 7. 覆盖式收尾：本次导入范围内未出现的旧活跃数据置删，再批量刷新产品信息。
            phaseStart = System.currentTimeMillis();
            deletedCount = batchDeleteStaleBindata(persistPlan.deleteIds);
            deleteCostMs = System.currentTimeMillis() - phaseStart;
            importLog.phase("delete_stale_bindata", deleteCostMs, deletedCount,
                    "deletedCount", deletedCount,
                    "deleteBatchCount", persistPlan.deleteIds.isEmpty() ? 0 : (persistPlan.deleteIds.size() - 1) / IMPORT_QUERY_BATCH_SIZE + 1);

            redisUtil.set(rediskey, "已完全导入成功，共" + successCount + "条记录", 86400);
            redisUtil.del(updkey);
            phaseStart = System.currentTimeMillis();
            updateProductInfo();
            updateProductInfoCostMs = System.currentTimeMillis() - phaseStart;
            importLog.phase("update_product_info", updateProductInfoCostMs, null);
            importLog.finishSuccess(
                    "totalRows", voList.size(),
                    "successCount", successCount,
                    "deletedCount", deletedCount,
                    "masterWarehouseCostMs", masterWarehouseCostMs,
                    "warehouseListCostMs", warehouseListCostMs,
                    "prepareInputCostMs", prepareInputCostMs,
                    "preloadCostMs", preloadCostMs,
                    "clientPreloadCostMs", clientPreloadCostMs,
                    "persistCostMs", persistCostMs,
                    "deleteCostMs", deleteCostMs,
                    "updateProductInfoCostMs", updateProductInfoCostMs,
                    "propertyUpdateCostMs", propertyUpdateCostMs,
                    "existingQueryCostMs", existingQueryCostMs,
                    "totalCostMs", System.currentTimeMillis() - importStart);
        } catch (Exception e) {
            log.error("导入异常", e);
            importLog.finishFailure(e.getMessage(),
                    "masterWarehouseCostMs", masterWarehouseCostMs,
                    "warehouseListCostMs", warehouseListCostMs,
                    "prepareInputCostMs", prepareInputCostMs,
                    "preloadCostMs", preloadCostMs,
                    "clientPreloadCostMs", clientPreloadCostMs,
                    "persistCostMs", persistCostMs,
                    "deleteCostMs", deleteCostMs,
                    "updateProductInfoCostMs", updateProductInfoCostMs,
                    "propertyUpdateCostMs", propertyUpdateCostMs,
                    "existingQueryCostMs", existingQueryCostMs,
                    "totalCostMs", System.currentTimeMillis() - importStart);
            redisUtil.set(rediskey, "导入失败！" + e.getMessage());
            redisUtil.del(updkey);
        } finally {
            importLog.flushIfNecessary();
            bindataImportLogHolder.remove();
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVo<String> saveBindata(BindataVO info) {
//        BindataDO bindataDO = bindataMapper.selectById(info.getId());
//        if (PublicUtil.isNotEmpty(bindataDO.getCustomerNo()) && PublicUtil.isEmpty(info.getCustomerNo())) {
//            return ResultVo.failure("必须输入客户！");
//        }
        if (info == null) {
            return ResultVo.failure("数据不能为空");
        }
        BindataDO infoDO = BeanCopyUtil.copy(info, BindataDO.class);
        // 单条保存先统一补默认值和校验，再进入库存属性、中央仓和持久化逻辑。
        String validationMessage = applyDefaultAndValidateBindata(infoDO);
        if (StringUtils.isNotBlank(validationMessage)) {
            return ResultVo.failure(validationMessage);
        }
        validationMessage = validateMinPackageQty(infoDO);
        if (StringUtils.isNotBlank(validationMessage)) {
            return ResultVo.failure(validationMessage + "！");
        }
        infoDO.setUpdateTime(DateUtil.getNow());
        infoDO.setLastdelFlag(0);
        //库存属性数据更新错误则不写入
        boolean isMasterWarehouse = commonService.isMasterWarehouse(infoDO.getWarehouseCode());
        if (infoDO.getStockType() == 1 && isMasterWarehouse) {
            standardizeMasterWarehouseBindata(infoDO);
        } else {
            if (!IsUpdProperty(infoDO)) {
                return ResultVo.failure("库存属性数据获取失败！");
            }
        }
        //校验中央仓条数
        if (!isDeleteBindataRow(infoDO) && infoDO.getCentreFlag() != null && infoDO.getCentreFlag() == 1) {
            //当分库设置为中央仓时，强制修改为非中央仓
            if (!isMasterWarehouse) {
                infoDO.setCentreFlag(0);
            }
            //把其他的仓改成非中央仓
            else if (StringUtils.isNotBlank(infoDO.getModelNo()) && StringUtils.isNotBlank(infoDO.getWarehouseCode())) {
                LambdaQueryWrapper<BindataDO> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.eq(BindataDO::getCentreFlag, 1)
                        .eq(BindataDO::getModelNo, infoDO.getModelNo())
                        .ne(BindataDO::getWarehouseCode, infoDO.getWarehouseCode());
                BindataDO update = new BindataDO();
                update.setCentreFlag(0);
                bindataMapper.update(update, queryWrapper);
                //是否删除被集约仓字段
            }
        }


        BindataDO bindataInfo = getOneBindata(infoDO);
        if (bindataInfo != null) {
            infoDO.setCreateTime(null);
            if (bindataInfo.getDelFlag() != null && bindataInfo.getDelFlag() == 1) {  //已删除的才更新登录日期
                infoDO.setLoginDate(DateUtil.getNow());
            }
            infoDO.setId(bindataInfo.getId());
            if (bindataMapper.updateById(infoDO) > 0) {
                updateClientWarehouse(infoDO);
                refreshProductInfoByModelNo(infoDO.getModelNo());
                return ResultVo.success("更新成功");
            }
        } else {
            infoDO.setCreateTime(DateUtil.getNow());
            infoDO.setLoginDate(DateUtil.getNow());
            int addCount = bindataMapper.insert(infoDO);
            updateClientWarehouse(infoDO);
            if (addCount >= 1) {
                refreshProductInfoByModelNo(infoDO.getModelNo());
                return ResultVo.success("保存成功");
            }
        }
        return ResultVo.failure("保存失败");
    }

    public void updateClientWarehouse(BindataDO bindataDO) {
        updateClientWarehouse(bindataDO, bindataClientWarehouseMapper);
    }

    private void updateClientWarehouse(BindataDO bindataDO, BindataClientWarehouseMapper currentClientWarehouseMapper) {
        long start = System.currentTimeMillis();
        if (bindataDO != null && bindataDO.getId() != null) {
            deleteClient(bindataDO.getId(), currentClientWarehouseMapper, bindataDO);
            if (bindataDO.getCentreFlag() != null && bindataDO.getCentreFlag() == 1 && CollectionUtils.isNotEmpty(bindataDO.getClient())) {
                insertClient(Long.valueOf(bindataDO.getId()), bindataDO.getClient(), currentClientWarehouseMapper);
            }
        }
        logImportSlowRow("update_client_warehouse", System.currentTimeMillis() - start);
    }

    private void insertClient(Long id,
                              List<String> clients,
                              BindataClientWarehouseMapper currentClientWarehouseMapper) {
        long start = System.currentTimeMillis();
        Date now = DateUtil.getNow();
        for (String client : clients) {
            BindataClientWarehouseDO clientDO = new BindataClientWarehouseDO();
            clientDO.setBindataId(id);
            clientDO.setWarehouseCode(client);
            clientDO.setDelFlag(0);
            clientDO.setCreateTime(now);
            clientDO.setCreateUser("ops");
            clientDO.setUpdateUser("ops");
            clientDO.setUpdateTime(now);
            currentClientWarehouseMapper.insert(clientDO);
        }
        logImportSlowRow("insert_client_relation", System.currentTimeMillis() - start);
    }

    /**
     * 更新产品信息
     */
    @Override
    public ResultVo<String> updateProductInfo() {
        bindataMapper.updateProductInfo();
        return ResultVo.success("更新产品信息成功");
    }

    private void refreshProductInfoByModelNo(String modelNo) {
        if (StringUtils.isNotBlank(modelNo)) {
            bindataMapper.updateProductInfoByModelNo(modelNo);
        }
    }

    /**
     * 库存属性更新规则,查询库存属性id，如果不存在则写入库存属性，并返回库存属性id
     */
    private boolean IsUpdProperty(BindataDO info) {

        long start = System.currentTimeMillis();

        //客户bin,客户存在,库存类型代码不存在，则默认库存代码为GK-TY
        if (info.getStockType() == 4 && PublicUtil.isNotEmpty(info.getCustomerNo()) && PublicUtil.isEmpty(info.getInventoryTypeCode())) {
            info.setInventoryTypeCode("GK-TY");
        }
        // Edit by DengDengHui, 2022-10-20 for bug-8370
        //库存属性编码不存在则调用接口
        if (info.getPropertyID() == null || info.getPropertyID() == 0) {
            if (StringUtils.isBlank(info.getInventoryTypeCode())) {
                info.setInventoryTypeCode("GK-TY");
            }
            OpsInventoryProperty vo = new OpsInventoryProperty();
            vo.setInventoryTypeCode(info.getInventoryTypeCode());
            if (vo.getInventoryTypeCode().startsWith("GK")) {
                vo.setCustomerNo(info.getCustomerNo());
            }
            if (vo.getInventoryTypeCode().endsWith("PPL")) {
                vo.setPpl(info.getPpl());
            }
            if (vo.getInventoryTypeCode().endsWith("PJ")) {
                vo.setProjectCode(info.getProjectNo());
            }
            CommonResult<?> result = opsPropertyFeignApi.findProperty(vo);
            if (result.isSuccess() && result.getData() != null) {
                info.setPropertyID(Long.parseLong(result.getData().toString()));
                logImportSlowCategory("property_lookup", System.currentTimeMillis() - start);
                return true;
            } else {
                logImportSlowCategory("property_lookup", System.currentTimeMillis() - start);
                return false;
            }
        } // end
        logImportSlowCategory("property_lookup_skip", System.currentTimeMillis() - start);
        return true;
    }

    private BindataDO getOneBindata(BindataDO info) {
        long start = System.currentTimeMillis();
        QueryWrapper<BindataDO> query = new QueryWrapper<>();
        query.select("id", "delFlag", "LastdelFlag")
                .eq("StockType", info.getStockType())
                .eq("warehouse_code", info.getWarehouseCode())
                .eq("ModelNo", info.getModelNo())
                .eq("CustomerNo", info.getCustomerNo())
                .eq("Property_ID", info.getPropertyID());
        BindataDO bindataDO = bindataMapper.selectOne(query);
        logImportSlowRow("get_one_bindata", System.currentTimeMillis() - start);
        return bindataDO;
    }


    // update by LiYingChao from bug 8933 in 2022-12-09
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVo<String> deleteBindata(Integer[] ids) {
        Date now = new Date();
        LambdaUpdateWrapper<BindataDO> updateWrapper = new LambdaUpdateWrapper<>();
        try {
            for (int id : ids) {
                updateWrapper.clear();
                updateWrapper
                        .eq(BindataDO::getId, id)
                        .eq(BindataDO::getDelFlag, 0)
                        .set(BindataDO::getDelFlag, 1)
                        .set(BindataDO::getQtyBin, 0)
                        .set(BindataDO::getBinCell, 0)
                        .set(BindataDO::getUpdateTime, now);
                bindataMapper.update(null, updateWrapper);
                deleteClient(id);
            }
        } catch (Exception e) {
            log.error("取消BinData发生异常:", e);
            throw new BusinessException("取消失败");
        }
        return ResultVo.success("取消成功.");
    }

    private void deleteClient(int id) {
        deleteClient(id, bindataClientWarehouseMapper, null);
    }

    private void deleteClient(int id,
                              BindataClientWarehouseMapper currentClientWarehouseMapper,
                              BindataDO bindataDO) {
        long start = System.currentTimeMillis();
        //更新被集约方
        LambdaUpdateWrapper<BindataClientWarehouseDO> client = new LambdaUpdateWrapper<BindataClientWarehouseDO>()
                .eq(BindataClientWarehouseDO::getBindataId, id)
                .set(BindataClientWarehouseDO::getDelFlag, 1);
        currentClientWarehouseMapper.update(null,client);
        logImportSlowRow("delete_client_relation", System.currentTimeMillis() - start);
    }

    @Override
    public ResultVo<BindataVO> getBindataByModelNo(String modelNo, String warehouseCode) {
        LambdaQueryWrapper<BindataDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BindataDO::getModelNo, modelNo);
        queryWrapper.eq(BindataDO::getWarehouseCode, warehouseCode);
        queryWrapper.eq(BindataDO::getStockType, 1);
        queryWrapper.eq(BindataDO::getDelFlag, 0);

        BindataDO bindataDO = bindataMapper.selectOne(queryWrapper);
        if (bindataDO == null) {
            return ResultVo.success(null, "该型号没有登记的BIN信息");
        }
        BindataVO bindataVO = BeanCopyUtil.copy(bindataDO, BindataVO.class);
        return ResultVo.success(bindataVO);
    }

    @Override
    public ResultVo<List<Integer>> getBinCountByModelNo(List<String> modelNos, String warehouseCode) {
        List<Integer> list = new ArrayList<>(modelNos.size());
        LambdaQueryWrapper<BindataDO> queryWrapper = new LambdaQueryWrapper<>();
        for (String modelNo : modelNos) {
            queryWrapper.clear();
            queryWrapper.eq(BindataDO::getModelNo, modelNo);
            queryWrapper.eq(BindataDO::getWarehouseCode, warehouseCode);
            queryWrapper.eq(BindataDO::getStockType, 1);
            queryWrapper.eq(BindataDO::getDelFlag, 0);

            Integer count = bindataMapper.selectCount(queryWrapper);
            list.add(count);
        }
        return ResultVo.success(list);
    }

    @Override
    public ResultVo<Map<String, BindataVO>> getBindataByModelNoAndWarehouse(String modelNo, List<String> warehouseList) {
        LambdaQueryWrapper<BindataDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BindataDO::getModelNo, modelNo)
                .in(BindataDO::getWarehouseCode, warehouseList)
                .eq(BindataDO::getStockType, 1)
                .eq(BindataDO::getDelFlag, 0);
        List<BindataDO> list = bindataMapper.selectList(queryWrapper);
        if (CollectionUtils.isEmpty(list)) {
            return ResultVo.success(Collections.emptyMap());
        }
        Map<String, BindataVO> map = new HashMap<>(list.size());
        BindataVO info;
        for (BindataDO data : list) {
            info = BeanCopyUtil.copy(data, BindataVO.class);
            map.put(info.getWarehouseCode(), info);
        }
        return ResultVo.success(map);
    }

    @Override
    public ResultVo<List<BindataVO>> getBindataByModelNoAnBinType(String modelNo, String binType) {
        LambdaQueryWrapper<BindataDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BindataDO::getModelNo, modelNo)
                .eq(BindataDO::getBinType, binType)
                .eq(BindataDO::getDelFlag, 0);
        List<BindataDO> list = bindataMapper.selectList(queryWrapper);
        if (CollectionUtils.isEmpty(list)) {
            return ResultVo.success(Collections.emptyList());
        }
        return ResultVo.success(BeanCopyUtil.copyList(list, BindataVO.class));
    }

    @Override
    public ResultVo<Map<String, Boolean>> checkModelIsBin(List<String> modelNos, List<String> warehouseList) {

        if (CollectionUtils.isEmpty(warehouseList)) {
            ResultVo<List<WarehouseVO>> resultVo = opsCommonService.getWarehouseByType(WarehouseTypeEnum.RDC.getHouseTypeCode());
            List<String> masterLis = new ArrayList<>();
            if (resultVo.isSuccess() && ObjectUtils.isNotEmpty(resultVo.getData())) {

                masterLis = resultVo.getData().stream().map(WarehouseVO::getWarehouseCode).collect(Collectors.toList());
            }
            warehouseList = masterLis;
        }

        List<Map<String, Integer>> mapList = bindataMapper.checkModelIsBin(modelNos, warehouseList);
        Map<String, Integer> binMap = new HashMap<>();
        for (Map<String, Integer> map : mapList) {
            binMap.putAll(map);
        }
        Map<String, Boolean> isBinMap = new HashMap<>();
        for (String str : modelNos) {
            Integer count = binMap.get(str);
            if (ObjectUtils.isNotEmpty(count) && count.compareTo(0) == 1) {
                isBinMap.put(str, true);
            } else {
                isBinMap.put(str, false);
            }
        }
        return ResultVo.success(isBinMap);
    }

    @Override
    public ResultVo<List<BindataVO>> getBindataByModelNos(BinDataQueryRequest request) {
        if (request == null) {
            return ResultVo.failure("请求参数不可为空");
        }
        if (request.getModelNos() != null && request.getModelNos().length != 0
                && request.getModelno() != null) {
            return ResultVo.failure("只能选择一种型号类型进行查询,要么一个型号,要么一个型号集合.");
        }

        LambdaQueryWrapper<BindataDO> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.eq(StringUtils.isNotBlank(request.getModelno()), BindataDO::getModelNo, request.getModelno());
        queryWrapper.eq(StringUtils.isNotBlank(request.getWarehouseCode()), BindataDO::getWarehouseCode, request.getWarehouseCode());
        if (request.getModelNos() != null && request.getModelNos().length != 0) {
            queryWrapper.in(BindataDO::getModelNo, Arrays.asList(request.getModelNos()));
        }
        queryWrapper.eq(BindataDO::getStockType, 1);
        queryWrapper.eq(BindataDO::getDelFlag, 0);

        List<BindataDO> bindataDOS = bindataMapper.selectList(queryWrapper);

        if (bindataDOS.isEmpty()) {
            return ResultVo.failure("该型号没有登记的BIN信息");
        }
        return ResultVo.success(BeanCopyUtil.copyList(bindataDOS, BindataVO.class));
    }

    @Override
    public ResultVo<Boolean> isBinModel(String modelNo) {
        LambdaQueryWrapper<BindataDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BindataDO::getModelNo, modelNo)
                .eq(BindataDO::getStockType, 1)
                .eq(BindataDO::getDelFlag, 0);
        return bindataMapper.selectCount(queryWrapper) > 0 ? ResultVo.success(true) : ResultVo.success(false);
    }

    @Override
    public ResultVo<List<BinModelNoVO>> isBinModelBatch(List<String> modelNos) {
        if (modelNos.isEmpty()) {
            return ResultVo.failure("参数不可为空.");
        }
        List<BinModelNoVO> list = new ArrayList<>(modelNos.size());
        ResultVo<Boolean> binModel;
        BinModelNoVO binModelNoVO;
        for (String modelNo : modelNos) {
            binModel = isBinModel(modelNo);
            binModelNoVO = new BinModelNoVO();
            if (binModel.isSuccess() && binModel.getData() == true) {
                binModelNoVO.setBin(true);
            } else {
                binModelNoVO.setBin(false);
            }
            binModelNoVO.setModelNo(modelNo);
            list.add(binModelNoVO);
        }
        return ResultVo.success(list);
    }

    @Override
    public void exportBinData(BindataRequest request) {
        LambdaQueryWrapper<BindataDO> query = new LambdaQueryWrapper<>();
        query.eq(request.getStockType() != null && request.getStockType() > 0, BindataDO::getStockType, request.getStockType())
                .eq(PublicUtil.isNotEmpty(request.getWarehouseCode()), BindataDO::getWarehouseCode, request.getWarehouseCode())
                .eq(!Boolean.TRUE.equals(request.getIsdelFlag()), BindataDO::getDelFlag, 0)
                .eq(PublicUtil.isNotEmpty(request.getCustomerNo()), BindataDO::getCustomerNo, request.getCustomerNo())
                .eq(request.getPropertyID() != null && request.getPropertyID() > 0, BindataDO::getPropertyID, request.getPropertyID());
        if (PublicUtil.isNotEmpty(request.getModelNo())) {
            String modelNo = request.getModelNo();
            modelNo = modelNo.trim();
            if (modelNo.contains("%")) {
                if (modelNo.startsWith("%") && modelNo.endsWith("%")) {
                    query.like(BindataDO::getModelNo, modelNo.substring(1, modelNo.length() - 1));
                } else if (modelNo.startsWith("%")) {
                    query.likeLeft(BindataDO::getModelNo, modelNo.substring(1));
                } else if (modelNo.endsWith("%")) {
                    query.likeRight(BindataDO::getModelNo, modelNo.substring(0, modelNo.length() - 1));
                }
            }else{
                query.eq(BindataDO::getModelNo, modelNo);
            }
        }
        List<BindataDO> list = bindataMapper.selectList(query);
        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        for (BindataDO data : list) {
            if(data.getCentreFlag()!=null &&data.getCentreFlag()==1){
                LambdaQueryWrapper<BindataClientWarehouseDO> qw = new LambdaQueryWrapper<>();
                qw.eq(BindataClientWarehouseDO::getBindataId, data.getId());
                qw.eq(BindataClientWarehouseDO::getDelFlag, 0);
                List<BindataClientWarehouseDO> clientWarehouses = bindataClientWarehouseMapper.selectList(qw);
                List<String> client = clientWarehouses.stream().map(BindataClientWarehouseDO::getWarehouseCode).collect(Collectors.toList());
                data.setClient(client);
            }
        }

        String path = "templates/bindata.xlsx";
        InputStream inputStream = FileUtil.getTemplate(path);
        ExcelUtil excel = new ExcelUtil(inputStream);
        excel.openSheet(0);

        // 向模板中写入数据
        int row = 1;
        int cel;
        for (BindataDO data : list) {
            cel = 0;
            excel.setCellValue(row, cel++, data.getStockType());
            excel.setCellValue(row, cel++, data.getWarehouseCode());
            excel.setCellValue(row, cel++, data.getModelNo());
            excel.setCellValue(row, cel++, data.getCustomerNo());
            excel.setCellValue(row, cel++, data.getPropertyID());
            excel.setCellValue(row, cel++, data.getInventoryTypeCode());
            excel.setCellValue(row, cel++, data.getGroupCustomerNo());
            excel.setCellValue(row, cel++, data.getPpl());
            excel.setCellValue(row, cel++, data.getProjectNo());
            excel.setCellValue(row, cel++, data.getQtyBin());
            excel.setCellValue(row, cel++, data.getBinCell());
            excel.setCellValue(row, cel++, data.getCaseType());
            excel.setCellValue(row, cel++, data.getProdType());
//            excel.setCellValue(row, cel++, data.getPositionNo());
            excel.setCellValue(row, cel++, data.getMeshType());
            excel.setCellValue(row, cel++, data.getInCaseQty());
            excel.setCellValue(row, cel++, data.getAdjustable());
            excel.setCellValue(row, cel++, data.getSafeQty());
            excel.setCellValue(row, cel++, data.getFreq());
            excel.setCellValue(row, cel++, data.getMean());
            excel.setCellValue(row, cel++, data.getSetLevel());
            excel.setCellValue(row, cel++, data.getPoType());
            excel.setCellValue(row, cel++, data.getOrderType());
            excel.setCellValue(row, cel++, data.getProdSeri());
            excel.setCellValue(row, cel++, data.getStateRange());
            excel.setCellValue(row, cel++, data.getMinPackageQty());
            excel.setCellValue(row, cel++, data.getSetFreq());
            excel.setCellValue(row, cel++, data.getDirectPurchase());
            excel.setCellValue(row, cel++, data.getDirectDelivery());
            excel.setCellValue(row, cel++, data.getAutoRepl());
            excel.setCellValue(row, cel++, data.getBinType());
            excel.setCellValue(row, cel++, data.getSetSupplierCode());
            excel.setCellValue(row, cel++, data.getSupplierCode());
            excel.setCellValue(row, cel++, data.getOrigin());
            excel.setCellValue(row, cel++, data.getEprice());
            excel.setCellValue(row, cel++, data.getEcode());
            excel.setCellValue(row, cel++, data.getModelSeries());
            excel.setCellValue(row, cel++, data.getDelFlag() == 1 ? "是" : "否");
            excel.setCellValue(row, cel++, data.getUpdateTime());
            excel.setCellValue(row, cel++, data.getLoginDate());
            excel.setCellValue(row, cel++, data.getCreateTime());
            excel.setCellValue(row, cel++, data.getCentreFlag());
            if (CollectionUtils.isNotEmpty(data.getClient())) {
                String warehouses = String.join(",", data.getClient());
                excel.setCellValue(row, cel++, warehouses);
            }
            row++;
        }
        excel.writeToResponse(response, "bindata.xlsx");

    }

    /**
     * 推送给门户补库
     */
    @Override
    public ResultVo<List<BindataVO>> listBinDataForReplQty() {
        LambdaQueryWrapper<BindataDO> query = new LambdaQueryWrapper<>();
        query.gt(BindataDO::getReplQty, 0)
                .eq(BindataDO::getDelFlag, 0);
        List<BindataDO> list = bindataMapper.selectList(query);
        return ResultVo.success(BeanCopyUtil.copyList(list, BindataVO.class));
    }

    @Override
    public ResultVo<List<BindataVO>> getBinDataForAutoPreStock(BindataRequest request) {
        if (StringUtils.isEmpty(request.getWarehouseCode()) || StringUtils.isEmpty(request.getCustomerNo())
                || request.getPropertyID() == null) {
            log.error("获取先行在库自动补库清单失败: params {}", request);
            return ResultVo.failure("获取先行在库自动补库清单失败: 仓库代码、客户代码、PropertyID 都不能为空");
        }
        int stockType = 4;
        int replQty = 0;
        LambdaQueryWrapper<BindataDO> query = new LambdaQueryWrapper<>();
        query.select(BindataDO::getId, BindataDO::getModelNo, BindataDO::getReplQty);
        query.eq(BindataDO::getStockType, stockType)
                .eq(BindataDO::getWarehouseCode, request.getWarehouseCode())
                .eq(BindataDO::getCustomerNo, request.getCustomerNo())
                .eq(BindataDO::getPropertyID, request.getPropertyID())
                .gt(BindataDO::getReplQty, replQty)
                .eq(BindataDO::getDelFlag, 0);
        List<BindataDO> list = bindataMapper.selectList(query);
        List<BindataVO> voList = BeanCopyUtil.copyList(list, BindataVO.class);
        // BindataVO vo;
        // Date nDlvDate = DateUtil.addDay(DateUtil.getToday(), 28);

        //不用计算交货期
        //for (BindataDO data : list) {
        // vo = BeanCopyUtil.copy(data, BindataVO.class);
        // 设置自动补货清单纳期
        //            Date lastDlvDate = binOrderCalcService.getLastPurchaseDlvDate(data.getModelNo(), request.getWarehouseCode());
        //            if (lastDlvDate == null) {
        //                vo.setLoginDate(nDlvDate);
        //            } else {
        //                vo.setLoginDate(DateUtil.addDay(lastDlvDate, 28));
        //            }
        // voList.add(vo);
        // }
        return ResultVo.success(voList);
    }

    @Override
    public List<BindataVO> listCustomerBinModel(String customerNo, List<String> modelNoList) {
        LambdaQueryWrapper<BindataDO> binQuery = new LambdaQueryWrapper<>();
        binQuery.select(BindataDO::getModelNo, BindataDO::getWarehouseCode, BindataDO::getQtyBin, BindataDO::getBinCell,
                BindataDO::getFreq, BindataDO::getMean, BindataDO::getEprice, BindataDO::getReplQty, BindataDO::getInventoryTypeCode,
                BindataDO::getPpl, BindataDO::getProjectNo);
        binQuery.eq(BindataDO::getCustomerNo, customerNo)
                .in(CollectionUtils.isNotEmpty(modelNoList), BindataDO::getModelNo, modelNoList)
                .eq(BindataDO::getStockType, 4)
                .eq(BindataDO::getDelFlag, 0);
        List<BindataDO> list = bindataMapper.selectList(binQuery);
        return BeanCopyUtil.copyList(list, BindataVO.class);
    }

    /*@Override
    public List<BindataVO> listCustomerBinRepleModel(String customerNo) {
        LambdaQueryWrapper<BindataDO> binQuery = new LambdaQueryWrapper<>();
        binQuery.select(BindataDO::getModelNo, BindataDO::getQtyBin, BindataDO::getReplQty);
        binQuery.eq(BindataDO::getCustomerNo, customerNo)
                .eq(BindataDO::getStockType, 4)
                .gt(BindataDO::getReplQty, 0)
                .eq(BindataDO::getDelFlag, 0);
        List<BindataDO> list = bindataMapper.selectList(binQuery);
        return BeanCopyUtil.copyList(list, BindataVO.class);
    }*/

    /*@Override
    public List<BindataVO> listCentralStockBinInfo(List<String> modelNoList) {
        LambdaQueryWrapper<BindataDO> binQuery = new LambdaQueryWrapper<>();
        binQuery.select(BindataDO::getWarehouseCode, BindataDO::getModelNo, BindataDO::getQtyBin);
        binQuery.in(BindataDO::getModelNo, modelNoList)
                .eq(BindataDO::getStockType, 1)
                .likeRight(BindataDO::getWarehouseCode, "K")
                .eq(BindataDO::getDelFlag, 0);
        List<BindataDO> list = bindataMapper.selectList(binQuery);
        return BeanCopyUtil.copyList(list, BindataVO.class);
    }*/

    @Override
    public List<BindataVO> listBinInfoByModelNo(String modelNo) {
        LambdaQueryWrapper<BindataDO> binQuery = new LambdaQueryWrapper<>();
        binQuery.select(BindataDO::getModelNo, BindataDO::getWarehouseCode, BindataDO::getCustomerNo,
                BindataDO::getQtyBin, BindataDO::getBinCell, BindataDO::getSafeQty);
        binQuery.eq(BindataDO::getModelNo, modelNo)
                .eq(BindataDO::getDelFlag, 0);
        List<BindataDO> list = bindataMapper.selectList(binQuery);
        return BeanCopyUtil.copyList(list, BindataVO.class);
    }

    @Override
    public List<BindataVO> listBinCellByModelNo(List<String> modelNoList) {
        QueryWrapper<BindataDO> binQuery = new QueryWrapper<>();
        binQuery.select("ModelNo", "warehouse_code", "Property_ID", "SUM(BinCell) as BinCell");
        binQuery.lambda().in(BindataDO::getModelNo, modelNoList)
                .groupBy(BindataDO::getModelNo, BindataDO::getWarehouseCode, BindataDO::getPropertyID);
        List<BindataDO> list = bindataMapper.selectList(binQuery);
        return BeanCopyUtil.copyList(list, BindataVO.class);
    }

    @Override
    public List<Map<String, Object>> listBinCustomerInfo(DataAuthoritySearchCondition condition) {
        return bindataMapper.listBinCustomerInfo(condition);
    }

    @Override
    public boolean cancelCustomerBinAutoReple(String warehouseCode, String customerNo, List<String> modelNoList) {
        LambdaUpdateWrapper<BindataDO> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(BindataDO::getQtyBin, 0)
                .set(BindataDO::getBinCell, 0)
                .set(BindataDO::getReplQty, 0)
                .set(BindataDO::getAutoRepl, 0);
        updateWrapper.eq(BindataDO::getStockType, 4)
                .eq(BindataDO::getInventoryTypeCode, "GK-TY")
                .eq(BindataDO::getCustomerNo, customerNo)
                .eq(BindataDO::getWarehouseCode, warehouseCode)
                .in(BindataDO::getModelNo, modelNoList)
                .eq(BindataDO::getDelFlag, 0);
        bindataMapper.update(new BindataDO(), updateWrapper);
        return true;
    }

    @Override
    public boolean updateCustomerBinAutoRepleStatus(String warehouseCode, String customerNo, List<String> modelNoList, int status) {

        LambdaUpdateWrapper<BindataDO> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(BindataDO::getAutoRepl, status);
        updateWrapper.eq(BindataDO::getStockType, 4)
                .eq(BindataDO::getInventoryTypeCode, "GK-TY")
                .eq(BindataDO::getCustomerNo, customerNo)
                .eq(BindataDO::getWarehouseCode, warehouseCode)
                .in(BindataDO::getModelNo, modelNoList)
                .eq(BindataDO::getDelFlag, 0);
        bindataMapper.update(new BindataDO(), updateWrapper);
        return true;
    }

    // Edit by dengdenghui 2022-11-22 for bug-8738
    @Override
    public List<Product> listBinAndGSS(List<String> warehouseCodeList, List<String> modelNoList) {
//        LambdaQueryWrapper<BindataDO> binQuery = new LambdaQueryWrapper<>();
//        binQuery.select(BindataDO::getModelNo, BindataDO::getWarehouseCode, BindataDO::getStockType, BindataDO::getSafeQty);
//        binQuery.eq(BindataDO::getWarehouseCode, warehouseCode)
//                .in(BindataDO::getStockType, 1, 2)
//                .gt(BindataDO::getQtyBin, 0)
//                .gt(BindataDO::getBinCell, 0)
//                .in(BindataDO::getModelNo, modelNoList);
//        List<BindataDO> list = bindataMapper.selectList(binQuery);
        return bindataMapper.listBinAndGSS(modelNoList, warehouseCodeList);
    } // End

    /*@Override
    public List<Product> listProductInfo(List<String> modelNoList, String deptNo, String customerNo) {
        String warehouseCode = this.getDeptPriorityStock(deptNo);
        return bindataMapper.listProductInfo(modelNoList, warehouseCode, customerNo);
    }*/

    /*@Override
    public ResultVo<List<BindataVO>> getBindataInfo(CsModelQryRequest csModelQryRequest) {
        if (csModelQryRequest == null) {
            return ResultVo.failure("参数不可为空");
        }
        LambdaQueryWrapper<BindataDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(csModelQryRequest.getModelNos() != null && csModelQryRequest.getModelNos().length != 0,
                                BindataDO::getModelNo,Arrays.asList(csModelQryRequest.getModelNos()))
                .eq(StringUtils.isNotBlank(csModelQryRequest.getCustomerNo()),BindataDO::getCustomerNo,csModelQryRequest.getCustomerNo())
                .eq(StringUtils.isNotBlank(csModelQryRequest.getWarehouseCode()),BindataDO::getWarehouseCode,csModelQryRequest.getWarehouseCode());
        List<BindataDO> bindataDOS = bindataMapper.selectList(queryWrapper);
        if (bindataDOS.isEmpty()) {
            return ResultVo.failure("暂无数据.");
        }
        return ResultVo.success(BeanCopyUtil.copyList(bindataDOS,BindataVO.class));
    }*/

    @Override
    public ResultVo<List<ModelExpFreqVO>> getModelExpFreq(ModelExpFreqRequest request) {
        LambdaQueryWrapper<ModelExpFreqDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(StringUtils.isNotBlank(request.getModelTYpe()), ModelExpFreqDO::getModelType, request.getModelTYpe())
                .eq(StringUtils.isNotBlank(request.getModelNo()), ModelExpFreqDO::getModelNo, request.getModelNo())
                .eq(StringUtils.isNotBlank(request.getStockcode()), ModelExpFreqDO::getStockCode, request.getStockcode())
                .eq(Objects.nonNull(request.getStockType()), ModelExpFreqDO::getStockType, request.getStockType());
        List<ModelExpFreqDO> modelExpFreqDOS = modelExpFreqMapper.selectList(queryWrapper);
        if (modelExpFreqDOS.isEmpty()) {
            return ResultVo.failure("暂无数据");
        }
        return ResultVo.success(BeanCopyUtil.copyList(modelExpFreqDOS, ModelExpFreqVO.class));
    }

    @Override
    public ResultVo<List<ModelExpFreqVO>> getModelExpFreqWithRiskLevel(ModelExpFreqRequest request) {
        LambdaQueryWrapper<ModelExpFreqDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(StringUtils.isNotBlank(request.getModelTYpe()), ModelExpFreqDO::getModelType, request.getModelTYpe())
                .eq(StringUtils.isNotBlank(request.getModelNo()), ModelExpFreqDO::getModelNo, request.getModelNo())
                .eq(StringUtils.isNotBlank(request.getStockcode()), ModelExpFreqDO::getStockCode, request.getStockcode())
                .eq(Objects.nonNull(request.getStockType()), ModelExpFreqDO::getStockType, request.getStockType());
        List<ModelExpFreqDO> modelExpFreqDOS = modelExpFreqMapper.selectList(queryWrapper);
        if (modelExpFreqDOS.isEmpty()) {
            return ResultVo.failure("暂无数据");
        }
        return ResultVo.success(BeanCopyUtil.copyList(modelExpFreqDOS, ModelExpFreqVO.class));
    }

    /**
     * bug11986,订单删除需要增加风险验证，新增获取所有大仓的 AvgQtyOf8 求和字段 B91717
     *
     * @param modelNo
     * @return
     */
    @Override
    public ResultVo<List<ModelExpFreqVO>> getMasterFreq(String modelNo) {
        LambdaQueryWrapper<ModelExpFreqDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(StringUtils.isNotBlank(modelNo), ModelExpFreqDO::getModelNo, modelNo)
                .eq(ModelExpFreqDO::getModelType, '1')
                .eq(ModelExpFreqDO::getStockType, 1) // 0-全公司 1-物流中心,2-分库 3-客户 4-代理
                .isNotNull(ModelExpFreqDO::getAvgQtyOf8);
        List<ModelExpFreqDO> modelExpFreqDOS = modelExpFreqMapper.selectList(queryWrapper);
        if (modelExpFreqDOS.isEmpty()) {
            return ResultVo.failure("暂无数据");
        }
        return ResultVo.success(BeanCopyUtil.copyList(modelExpFreqDOS, ModelExpFreqVO.class));
    }

    @Override
    public ResultVo<List<ModelExpFreqVO>> getCustomerModelFreq(CustomerExpFreqRequest request) {
        LambdaQueryWrapper<ModelExpFreqDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(ModelExpFreqDO::getStockType, 3) // 0-全公司 1-物流中心,2-分库 3-客户 4-代理
                .eq(StringUtils.isNotBlank(request.getCustomerNo()), ModelExpFreqDO::getStockCode, request.getCustomerNo());
        if (request.getModelNos() != null && request.getModelNos().length > 0) {
            queryWrapper.in(ModelExpFreqDO::getModelNo, Arrays.asList(request.getModelNos()));
        }
        List<ModelExpFreqDO> modelExpFreqDOS = modelExpFreqMapper.selectList(queryWrapper);
        return ResultVo.success(BeanCopyUtil.copyList(modelExpFreqDOS, ModelExpFreqVO.class));
    }

    @Override
    public String getDeptPriorityStock(String deptNo) {
        return bindataMapper.getDeptPriorityStock(deptNo);
    }

    @Override
    public List<String> listDeptPriorityStock(String deptNo) {
        return bindataMapper.listDeptPriorityStock(deptNo);
    }

    @Override
    public List<String> getWTWarehouseByCustomerNo(String customerNo) {
        return bindataMapper.getWTWarehouseByCustomerNo(customerNo);
    }

    @Override
    public void dowmBinDataImport() {
        String path = "templates/bindata.xlsx";
        ExcelUtil excel = new ExcelUtil(path);
        excel.writeToResponse(response, "BinData导入模板");
    }

}
