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
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
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
@Deprecated
//@Service
@Slf4j
public class BindataServiceImplOld implements BindataService {

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
                    redisUtil.set(rediskey, "excel表第" + (row + 1) + "行，第" + cel + "列数据有问题;" + "存在最小打包数的Bin数量必须时最小打包数的倍数！");
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
    @Async
    @Override
    public void asyupdAndAddBinDataV2(List<BindataExcelVO> voList, String rediskey) {

        String updkey = "ops:binimp:";
        List<BindataDO> list = new ArrayList<>();
        Map<String, BindataDO> map = new HashMap<>();  //查找导入种类
        Map<String, String> modelmap = new HashMap<>();  //查找导入种类
        BindataDO info;
        Set<String> centreModelSet = new HashSet<>();
        // 获取物流中心仓库代码
        List<String> masterWarehouseCodes = getMasterWarehouseCodes();
        if (masterWarehouseCodes == null) {
            redisUtil.set(rediskey, "获取主仓库代码失败");
            redisUtil.del(updkey);
            return;
        }
        //被集约方仓库标准格式
        List<String> warehouseList = new ArrayList<>();
        warehouseList.add("ALL");
        //获取token
        SMCApp.setGrantType(GlobalConstant.GrantType.CLIANT_CREDENTIALS);
        ResultVo<List<com.smc.smccloud.model.warehouse.WarehouseVO>> listResultVo = commonServiceFeignApi.listWarehouseNoWT();
        if (listResultVo != null && listResultVo.isSuccess()) {
            List<com.smc.smccloud.model.warehouse.WarehouseVO> data = listResultVo.getData();
            for (com.smc.smccloud.model.warehouse.WarehouseVO warehouseVO : data) {
                String warehouseCode = warehouseVO.getWarehouseCode();
                warehouseList.add(warehouseCode);
            }
        } else {
            redisUtil.set(rediskey, "获取仓库代码失败");
            redisUtil.del(updkey);
        }
        // 处理传入的voList参数
        for (int rowIndex = 0; rowIndex < voList.size(); rowIndex++) {
            try {
                BindataExcelVO excelVO = voList.get(rowIndex);
                int row = rowIndex + 1; // 用于错误提示的行号（从1开始）

                info = new BindataDO();

                if (PublicUtil.isEmpty(excelVO.getStockType())) {
                    redisUtil.set(rediskey, "第" + (row + 1) + "行，第0列数据有问题;" + "必须输入库存类型");
                    redisUtil.del(updkey);
                    return;
                }
                info.setStockType(excelVO.getStockType());
                info.setWarehouseCode(StringUtils.defaultString(excelVO.getWarehouseCode()).trim());
                info.setModelNo(StringUtils.defaultString(excelVO.getModelNo()).trim());
                info.setCustomerNo(StringUtils.defaultString(excelVO.getCustomerNo()).trim());  // 关键修改点
                info.setPropertyID(excelVO.getPropertyID());

                info.setInventoryTypeCode(StringUtils.defaultString(excelVO.getInventoryTypeCode()).trim());
                info.setGroupCustomerNo(StringUtils.defaultString(excelVO.getGroupCustomerNo()).trim());
                info.setPpl(StringUtils.defaultString(excelVO.getPpl()).trim());
                info.setProjectNo(StringUtils.defaultString(excelVO.getProjectNo()).trim());
                info.setQtyBin(excelVO.getQtyBin());
                info.setBinCell(excelVO.getBinCell());
                if (info.getBinCell() <= 0 || info.getBinCell() > 6) {
                    redisUtil.set(rediskey, "第" + (row + 1) + "行，Bin数必须大于等于1且小于等于6！");
                    redisUtil.del(updkey);
                    return;
                }
                if (info.getQtyBin() <= 0) {
                    redisUtil.set(rediskey, "第" + (row + 1) + "行，Bin数量必须大于0！");
                    redisUtil.del(updkey);
                    return;
                }
                info.setCaseType(StringUtils.defaultString(excelVO.getCaseType()).trim());
                info.setProdType(StringUtils.defaultString(excelVO.getProdType()).trim());
                info.setMeshType(StringUtils.defaultString(excelVO.getMeshType()).trim());
                info.setInCaseQty(excelVO.getInCaseQty());
                info.setAdjustable(StringUtils.defaultString(excelVO.getAdjustable()).trim());
                info.setSafeQty(excelVO.getSafeQty());
                info.setFreq(excelVO.getFreq());
                info.setMean(excelVO.getMean());
                info.setSetLevel(StringUtils.defaultString(excelVO.getSetLevel()).trim());
                info.setPoType(StringUtils.defaultString(excelVO.getPoType()).trim());
                info.setOrderType(StringUtils.defaultString(excelVO.getOrderType()).trim());
                info.setProdSeri(StringUtils.defaultString(excelVO.getProdSeri()).trim());
                info.setStateRange(StringUtils.defaultString(excelVO.getStateRange()).trim());
                info.setMinPackageQty(excelVO.getMinPackageQty());
                if (info.getMinPackageQty() > 0 && info.getQtyBin() % info.getMinPackageQty() > 0) {
                    redisUtil.set(rediskey, "第" + (row + 1) + "行，存在最小打包数的Bin数量必须是最小打包数的倍数！");
                    redisUtil.del(updkey);
                    return;
                }
                info.setSetFreq(excelVO.getSetFreq());
                info.setDirectPurchase(excelVO.getDirectPurchase());
                info.setDirectDelivery(excelVO.getDirectDelivery());
                info.setAutoRepl(excelVO.getAutoRepl());
                info.setBinType(StringUtils.defaultString(excelVO.getBinType()).trim());
                info.setSetSupplierCode(StringUtils.defaultString(excelVO.getSetSupplierCode()).trim());
                info.setCentreFlag(excelVO.getCentreFlag());

                if (info.getCentreFlag() != null && info.getCentreFlag() == 1) {
                    if (!masterWarehouseCodes.contains(info.getWarehouseCode())) {
                        // 当分库设置为中央仓时，强制修改为非中央仓
                        info.setCentreFlag(0);
                    } else if (centreModelSet.contains(info.getModelNo())) {
                        redisUtil.set(rediskey, "第" + (row + 1) + "行，中央仓不能重复写入！");
                        redisUtil.del(updkey);
                        return;
                    }
                    centreModelSet.add(info.getModelNo());
                }
                String client = excelVO.getClient();
                if (StringUtils.isNotEmpty(client)) {
                    //加校验，必须是仓库表的仓库代码
                    List<String> clients = Arrays.asList(client.split(","));
                    for (String clent : clients) {
                        if (!warehouseList.contains(clent)) {
                            redisUtil.set(rediskey, "第" + (row + 1) + "行，被集约方格式错误，"+excelVO.getClient());
                            redisUtil.del(updkey);
                            return;
                        }
                    }
                    info.setClient(clients);
                }
                info.setDelFlag(0);
                if (PublicUtil.isEmpty(info.getStockType())
                        || PublicUtil.isEmpty(info.getModelNo()) || PublicUtil.isEmpty(info.getWarehouseCode())) {
                    redisUtil.set(rediskey, "库存类型、仓库代码、客户不能为空！");
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
                    redisUtil.set(rediskey, "第" + (row + 1) + "行，型号" + info.getModelNo() + "重复使用！");
                    redisUtil.del(updkey);
                    return;
                }
                modelmap.put(key + info.getModelNo(), info.getModelNo());
            } catch (Exception e) {
                int row = list.size(); // 当前正在处理的行
                redisUtil.set(rediskey, "第" + (row + 1) + "行，第0列数据有问题;" + e.getMessage());
                redisUtil.del(updkey);
                return;
            }
        }

        log.info("处理了{}条记录", voList.size());

        try {
            List<BindataDO> dellist = new ArrayList<>();
            int count = 0;
            List<BindataDO> maplist = new ArrayList<>(map.values());
            //按照4字段查询数据库中的数据
            for (BindataDO item : maplist) {
                //库存属性数据更新错误则不导入
                if (item.getStockType() != 1 || !masterWarehouseCodes.contains(item.getWarehouseCode())) {
                    SMCApp.setGrantType(GlobalConstant.GrantType.CLIANT_CREDENTIALS);
                    if (!IsUpdProperty(item)) {
                        redisUtil.set(rediskey, "查属性异常");
                        redisUtil.del(updkey);
                        return;
                    }
                }

                //查出每种类存在的数据，每种类别一个key,排除已删除的 bug8615 begin
                LambdaQueryWrapper<BindataDO> query = new LambdaQueryWrapper<>();
                query.select(BindataDO::getId, BindataDO::getModelNo)
                        .eq(BindataDO::getStockType, item.getStockType())
                        .eq(BindataDO::getWarehouseCode, item.getWarehouseCode())
                        .eq(BindataDO::getCustomerNo, item.getCustomerNo())
                        .eq(BindataDO::getPropertyID, item.getPropertyID())
                        .eq(BindataDO::getDelFlag, 0);
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
            if (!dellist.isEmpty()) {
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
            log.error("导入异常", e);
            redisUtil.set(rediskey, "导入失败！" + e.getMessage());
            redisUtil.del(updkey);
        }
    }

    public boolean updOrAddBinData(BindataDO binDO) {
        BindataDO bindataInfo = getOneBindata(binDO);
        //如果要改中央仓为1，需要先重置该型号的所有中央为1的改为0
        //如果要取消中央仓，则可以直接更新单条为0
        if (binDO.getCentreFlag() != null && binDO.getCentreFlag() == 1) {
            BindataDO update = new BindataDO();
            update.setCentreFlag(0);
            LambdaUpdateWrapper<BindataDO> query = new LambdaUpdateWrapper<>();
            query.eq(BindataDO::getCentreFlag, 1)
                    .eq(BindataDO::getStockType, binDO.getStockType())
                    .eq(BindataDO::getModelNo, binDO.getModelNo())
                    .eq(BindataDO::getCustomerNo, binDO.getCustomerNo())
                    .eq(BindataDO::getPropertyID, binDO.getPropertyID())
            ;
            bindataMapper.update(update, query);  //bug8615 end
            if (bindataInfo != null) {
                deleteClient(bindataInfo.getId());
            }
        }
        //存在则更新
        if (bindataInfo != null) {
            binDO.setId(bindataInfo.getId());
            if (bindataInfo.getDelFlag() != null && bindataInfo.getDelFlag() == 1) {  //已删除的才更新登录日期
                binDO.setLoginDate(DateUtil.getNow());
            }
            binDO.setCreateTime(null);
            if (bindataMapper.updateById(binDO) > 0) {
                updateClientWarehouse(binDO);
                return true;
            }
            log.info(String.valueOf("修改异常"));
        } else {  //否则新增
            binDO.setLoginDate(DateUtil.getNow());
            int addCout = bindataMapper.insert(binDO);
            if (addCout >= 1) {
                updateClientWarehouse(binDO);
                return true;
            }
            log.info(String.valueOf("新增异常"));
        }
        return false;
    }

    @Override
    public ResultVo<String> saveBindata(BindataVO info) {
//        BindataDO bindataDO = bindataMapper.selectById(info.getId());
//        if (PublicUtil.isNotEmpty(bindataDO.getCustomerNo()) && PublicUtil.isEmpty(info.getCustomerNo())) {
//            return ResultVo.failure("必须输入客户！");
//        }
        if (PublicUtil.isEmpty(info.getStockType()) || PublicUtil.isEmpty(info.getModelNo()) || PublicUtil.isEmpty(info.getWarehouseCode())) {
            return ResultVo.failure("必须输入仓库、型号！");
        }
        if (info.getBinCell() < 1 || info.getBinCell() > 6) {
            return ResultVo.failure("Bin数必须大于等于1且小于等于6！");
        }
        if (info.getQtyBin() < 1) {
            return ResultVo.failure("Bin数量必须大于0！");
        }
        if (info.getMinPackageQty() > 0 && info.getQtyBin() % info.getMinPackageQty() > 0) {
            return ResultVo.failure("存在最小打包数的Bin数量必须时最小打包数的倍数！");
        }

        BindataDO infoDO = BeanCopyUtil.copy(info, BindataDO.class);
        infoDO.setUpdateTime(DateUtil.getNow());
        infoDO.setDelFlag(0);
        infoDO.setLastdelFlag(0);
        //库存属性数据更新错误则不写入
        boolean isMasterWarehouse = commonService.isMasterWarehouse(infoDO.getWarehouseCode());
        if (infoDO.getStockType() == 1 && isMasterWarehouse) {
            infoDO.setCustomerNo("");
            infoDO.setPropertyID(1L);
            infoDO.setInventoryTypeCode("TY");
            infoDO.setPpl("");
            infoDO.setProjectNo("");
            infoDO.setGroupCustomerNo("");
        } else {
            if (!IsUpdProperty(infoDO)) {
                return ResultVo.failure("库存属性数据获取失败！");
            }
        }
        //校验中央仓条数
        if (infoDO.getCentreFlag() != null && infoDO.getCentreFlag() == 1) {
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
                return ResultVo.success("更新成功");
            }
        } else {
            infoDO.setCreateTime(DateUtil.getNow());
            infoDO.setLoginDate(DateUtil.getNow());
            int addCount = bindataMapper.insert(infoDO);
            updateClientWarehouse(infoDO);
            if (addCount >= 1) {
                return ResultVo.success("保存成功");
            }
        }
        return ResultVo.failure("保存失败");
    }

    public void updateClientWarehouse(BindataDO bindataDO) {
        if (bindataDO != null && bindataDO.getId() != null) {
            deleteClient(bindataDO.getId());
            if (bindataDO.getCentreFlag() != null && bindataDO.getCentreFlag() == 1 && CollectionUtils.isNotEmpty(bindataDO.getClient())) {
                insertClient(Long.valueOf(bindataDO.getId()), bindataDO.getClient());
            }
        }
    }

    private void insertClient(Long id, List<String> clients) {
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
            bindataClientWarehouseMapper.insert(clientDO);
        }
    }

    /**
     * 更新产品信息
     */
    @Override
    public ResultVo<String> updateProductInfo() {
        bindataMapper.updateProductInfo();
        return ResultVo.success("更新产品信息成功");
    }

    /**
     * 库存属性更新规则
     */
    private boolean IsUpdProperty(BindataDO info) {

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
            CommonResult result = opsPropertyFeignApi.findProperty(vo);
            if (result.isSuccess() && result.getData() != null) {
                info.setPropertyID(Long.parseLong(result.getData().toString()));
                return true;
            } else {
                return false;
            }
        } // end
        return true;
    }

    private BindataDO getOneBindata(BindataDO info) {
        QueryWrapper<BindataDO> query = new QueryWrapper<>();
        query.select("id", "delFlag", "LastdelFlag")
                .eq("StockType", info.getStockType())
                .eq("warehouse_code", info.getWarehouseCode())
                .eq("ModelNo", info.getModelNo())
                .eq("CustomerNo", info.getCustomerNo())
                .eq("Property_ID", info.getPropertyID());
        return bindataMapper.selectOne(query);
    }

    private boolean UpdBindata(BindataDO info) {
        LambdaUpdateWrapper<BindataDO> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(BindataDO::getStockType, info.getStockType())
                .eq(BindataDO::getWarehouseCode, info.getWarehouseCode())
                .eq(BindataDO::getCustomerNo, info.getCustomerNo())
                .eq(BindataDO::getModelNo, info.getModelNo())
                .eq(BindataDO::getPropertyID, info.getPropertyID());
        if (bindataMapper.update(info, updateWrapper) < 1) {
            return false;
        }
        return true;
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
        //更新被集约方
        LambdaUpdateWrapper<BindataClientWarehouseDO> client = new LambdaUpdateWrapper<BindataClientWarehouseDO>()
                .eq(BindataClientWarehouseDO::getBindataId, id)
                .set(BindataClientWarehouseDO::getDelFlag, 1);
        bindataClientWarehouseMapper.update(null,client);
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
            //    <!--add by WuWeiDong 20230213 bug 9615 --导出增加“是否可调”>
            excel.setCellValue(row, cel++, "1".equals(data.getAdjustable()) ? "是" : "否");
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
        /// vo = BeanCopyUtil.copy(data, BindataVO.class);
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
