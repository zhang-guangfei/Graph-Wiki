package com.smc.smccloud.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.smc.smccloud.core.exception.BusinessException;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.model.adapter.DataAuthoritySearchCondition;
import com.smc.smccloud.core.model.enums.InventoryTypeEnum;
import com.smc.smccloud.core.model.enums.ShikomiClassCodeEnum;
import com.smc.smccloud.core.model.enums.ShikomiClassTypeEnum;
import com.smc.smccloud.core.model.page.Page;
import com.smc.smccloud.core.redis.RedisManager;
import com.smc.smccloud.core.utils.DateUtil;
import com.smc.smccloud.core.utils.PublicUtil;
import com.smc.smccloud.mapper.*;
import com.smc.smccloud.model.DataTypeVO;
import com.smc.smccloud.model.adapter.*;
import com.smc.smccloud.model.constants.Constants;
import com.smc.smccloud.model.delivery.DeliveryInfo;
import com.smc.smccloud.model.delivery.DeliveryModelInfo;
import com.smc.smccloud.model.enums.WareHouseTypeEnum;
import com.smc.smccloud.model.inventory.InventoryOrderingDTO;
import com.smc.smccloud.model.inventory.InventorySupplierDO;
import com.smc.smccloud.model.inventory.InventoryVO;
import com.smc.smccloud.model.inventory.OpsInventoryDO;
import com.smc.smccloud.model.product.ProductDeliveryDO;
import com.smc.smccloud.model.shikomi.ShikomiCustomerVO;
import com.smc.smccloud.model.shikomi.ShikomiModelDTO;
import com.smc.smccloud.model.shikomi.VShikomiTotalDO;
import com.smc.smccloud.service.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Author: B90034
 * Date: 2022-03-14 11:22
 * Description:
 */
@Slf4j
@Service
public class SMSAdapterServiceImpl implements SMSAdapterService {

    @Resource
    private WarehouseSalesBranchConfigMapper warehouseConfigMapper;
    @Resource
    private InventoryMapper inventoryMapper;
    @Resource
    private WarehouseMapper warehouseMapper;
    @Resource
    private ShikomiService shikomiService;
    @Resource
    private VShikomiTotalMapper vShikomiTotalMapper;
    @Resource
    private ProductBomMapper productBomMapper;
    @Resource
    private InventorySupplierService inventorySupplierService;
    @Resource
    private DeliveryService deliveryService;
    @Resource
    private ProductDeliveryService productDeliveryService;
    @Resource
    private OpsCommonService opsCommonService;
    @Resource
    private DictCommonService dictCommonService;
    @Resource
    private CommonServiceFeignApi commonServiceFeignApi;
    @Resource
    private RedisManager redisManager;
    @Resource
    private CommonService commonService;

    @Override
    public List<StockCode> findSubTreasury(String deptNo) {
        log.info("findSubTreasury deptNo = {}", deptNo);
        if (StringUtils.isBlank(deptNo)) {
            return null;
        }
        deptNo = this.converDeptCode(deptNo);
        List<StockCode> stockCodeList = warehouseMapper.findSubTreasury(deptNo);
        this.setStockTypeName(stockCodeList);
        return stockCodeList;
    }

    @Override
    public List<StockCode> findGoodsLocation(String deptNo) {
        // 查询营业所可出库仓库代码列表
        List<String> warehouseList = this.getCentralWarehouseConfig(this.converDeptCode(deptNo));
        if (warehouseList.isEmpty()) {
            return Collections.emptyList();
        }
        // 根据仓库列表查询仓库信息
        List<StockCode> stockCodeList = warehouseMapper.findGoodsLocation(warehouseList);
        this.setStockTypeName(stockCodeList);
        stockCodeList = stockCodeList.stream()
                .sorted(Comparator.comparingInt(o -> warehouseList.indexOf(o.getStockCode()))).collect(Collectors.toList());
        return stockCodeList;
    }

    /*@Override
    public List<InventoryInfo> getInventoryDetailByDeptNo(String modelNo, String deptNo) {
        // 查询部门的可出库仓库
        List<String> warehouseConfig = null;
        List<Long> propertyIdList = null;

        if (StringUtils.isNotBlank(deptNo)) {
            deptNo = this.converDeptCode(deptNo);
            warehouseConfig = this.getWarehouseConfig(deptNo);
            propertyIdList = inventoryPropertyMapper.listDeptCustomerPropertyId(deptNo);
        }
        if (CollectionUtils.isEmpty(warehouseConfig)) {
            log.info("getInventoryDetailByDeptNo deptNo = {} warehouseConfig is NULL.", deptNo);
            warehouseConfig = this.getCentralWarehouseCode();
        }
        // 查询营业所专备 inventory_property_id
        if (propertyIdList == null) {
            propertyIdList = Collections.emptyList();
        }

        List<InventoryInfo> resultList = new ArrayList<>();
        Inventory temp;
        // 可用在库
        InventoryInfo canUseStock = new InventoryInfo();
        canUseStock.setInventoryName("可用在库");
        canUseStock.setInventoryQuantity(0);
        canUseStock.setList(new ArrayList<>());
        resultList.add(canUseStock);
        List<Inventory> canUseTY = new ArrayList<>();
        List<Inventory> canUseGK = new ArrayList<>();
        List<Inventory> canUseQB = new ArrayList<>();
        //List<Inventory> canUseZL = new ArrayList<>();
        // 分库在库
        InventoryInfo subStock = new InventoryInfo();
        subStock.setInventoryName("分库在库");
        subStock.setInventoryQuantity(0);
        subStock.setList(new ArrayList<>());
        resultList.add(subStock);
        // 专备在库
        InventoryInfo GKStock = new InventoryInfo();
        GKStock.setInventoryName("专备在库");
        GKStock.setInventoryQuantity(0);
        GKStock.setList(new ArrayList<>());
        resultList.add(GKStock);
        // 预占库存
        InventoryInfo QBStock = new InventoryInfo();
        QBStock.setInventoryName("预占库存");
        QBStock.setInventoryQuantity(0);
        QBStock.setList(new ArrayList<>());
        resultList.add(QBStock);
        // 战略在库
        InventoryInfo ZLStock = new InventoryInfo();
        ZLStock.setInventoryName("战略在库");
        ZLStock.setInventoryQuantity(0);
        ZLStock.setList(new ArrayList<>());
        resultList.add(ZLStock);
        // 供应商库存
        InventoryInfo supplierStock = new InventoryInfo();
        supplierStock.setInventoryName("供应商库存");
        supplierStock.setInventoryQuantity(0);
        supplierStock.setList(new ArrayList<>());
        resultList.add(supplierStock);
        // 在途库存
        InventoryInfo onOrderStock = new InventoryInfo();
        onOrderStock.setInventoryName("在途库存");
        onOrderStock.setInventoryQuantity(0);
        onOrderStock.setList(new ArrayList<>());
        resultList.add(onOrderStock);

        // 查询型号的所有库存信息
        List<InventoryVO> stockList = inventoryMapper.listModelInventory(modelNo, null);
        Map<String, String> warehouseNameMap = new HashMap<>();
        ResultVo<String> warehouseName;

        for (InventoryVO info : stockList) {
            temp = new Inventory();
            temp.setModelNo(info.getModelNo());
            temp.setCustomerNo(info.getCustomerNo());
            temp.setQtyOnHand(info.getQuantity());
            temp.setQtyPrePare(info.getPrepareQuantity());
            temp.setQuantity(info.getQuantity() - info.getPrepareQuantity());
            temp.setStockCode(info.getWarehouseCode());
            temp.setBomNo(info.getPpl());
            temp.setProjectCode(info.getProjectCode());
            temp.setGroupCustomerNo(info.getGroupCustomerNo());
            temp.setInventoryTypeCode(info.getInventoryTypeCode());
            temp.setInventoryTypeName(InventoryTypeEnum.getName(info.getInventoryTypeCode()));
            if (warehouseNameMap.containsKey(info.getWarehouseCode())) {
                temp.setStock(warehouseNameMap.get(info.getWarehouseCode()));
            } else {
                warehouseName = commonServiceFeignApi.getWarehouseName(info.getWarehouseCode());
                if (warehouseName.isSuccess() && warehouseName.getData() != null) {
                    temp.setStock(warehouseName.getData());
                    warehouseNameMap.put(info.getWarehouseCode(), warehouseName.getData());
                }
            }
            if (info.getInventoryTypeCode().equals(InventoryTypeEnum.TY.getCode())) {
                temp.setCustomerNo(this.getWarehouseCustomerNo(info.getWarehouseCode()));
            }

            // 可用在库
            if (warehouseConfig.contains(info.getWarehouseCode())) {
                // 通用在库
                if (StringUtils.equals(info.getInventoryTypeCode(), InventoryTypeEnum.TY.getCode())) {
                    canUseStock.setInventoryQuantity(canUseStock.getInventoryQuantity() + temp.getQuantity());
                    canUseTY.add(temp);
                }
                // 客户专备
                if (info.getInventoryTypeCode().startsWith("GK")) {
                    if (propertyIdList.contains(info.getPropertyId())) {
                        canUseStock.setInventoryQuantity(canUseStock.getInventoryQuantity() + temp.getQuantity());
                        canUseGK.add(temp);
                    } else {
                        GKStock.setInventoryQuantity(GKStock.getInventoryQuantity() + temp.getQuantity());
                        GKStock.getList().add(temp);
                    }
                }
                // 预占库存
                if (StringUtils.equals(info.getInventoryTypeCode(), InventoryTypeEnum.QB_NO.getCode())) {
                    if (propertyIdList.contains(info.getPropertyId())) {
                        canUseStock.setInventoryQuantity(canUseStock.getInventoryQuantity() + temp.getQuantity());
                        canUseQB.add(temp);
                    } else {
                        QBStock.setInventoryQuantity(QBStock.getInventoryQuantity() + temp.getQuantity());
                        QBStock.getList().add(temp);
                    }
                }
                // 战略在库
                if (info.getInventoryTypeCode().startsWith("ZL")) {
                    ZLStock.setInventoryQuantity(ZLStock.getInventoryQuantity() + temp.getQuantity());
                    ZLStock.getList().add(temp);
                }
            } else {
                // 分库在库
                if (info.getInventoryTypeCode().equals(InventoryTypeEnum.TY.getCode())
                        && info.getWarehouseCode().startsWith("S")) {
                    subStock.setInventoryQuantity(subStock.getInventoryQuantity() + temp.getQuantity());
                    subStock.getList().add(temp);
                }
                // 非部门客户的专备
                if (info.getInventoryTypeCode().startsWith("GK")) {
                    GKStock.setInventoryQuantity(GKStock.getInventoryQuantity() + temp.getQuantity());
                    GKStock.getList().add(temp);
                }
                // 预占库存
                if (StringUtils.equals(info.getInventoryTypeCode(), InventoryTypeEnum.QB_NO.getCode())) {
                    QBStock.setInventoryQuantity(QBStock.getInventoryQuantity() + temp.getQuantity());
                    QBStock.getList().add(temp);
                }
                // 战略在库
                if (info.getInventoryTypeCode().startsWith("ZL")) {
                    ZLStock.setInventoryQuantity(ZLStock.getInventoryQuantity() + temp.getQuantity());
                    ZLStock.getList().add(temp);
                }
            }
        }
        canUseStock.getList().addAll(canUseTY);
        canUseStock.getList().addAll(canUseGK);
        canUseStock.getList().addAll(canUseQB);
        //canUseStock.getList().addAll(canUseZL);
        // 查询供应商库存
        List<InventorySupplierDO> supplierStockList = inventorySupplierService.listModelSupplierInventory(modelNo);
        Map<String, String> supplierNameMap = new HashMap<>();
        ResultVo<String> supplierName;
        for (InventorySupplierDO info : supplierStockList) {
            temp = new Inventory();
            temp.setModelNo(info.getModelNo());
            temp.setQtyOnHand(info.getQuantity());
            temp.setQuantity(info.getQuantity());
            temp.setStockCode(info.getSupplierId());
            temp.setInventoryTypeCode(InventoryTypeEnum.TY.getCode());
            temp.setInventoryTypeName(InventoryTypeEnum.TY.getName());
            if (supplierNameMap.containsKey(info.getSupplierId())) {
                temp.setStock(supplierNameMap.get(info.getSupplierId()));
            } else {
                supplierName = commonServiceFeignApi.getSupplierName(info.getSupplierId());
                if (supplierName.isSuccess() && supplierName.getData() != null) {
                    temp.setStock(supplierName.getData());
                    supplierNameMap.put(info.getSupplierId(), temp.getStock());
                }
            }
            supplierStock.setInventoryQuantity(supplierStock.getInventoryQuantity() + temp.getQuantity());
            supplierStock.getList().add(temp);
        }
        // 查询型号的在途库存
        ModelWarehouseStockRequest dto = new ModelWarehouseStockRequest();
        dto.setModelNos(Collections.singletonList(modelNo));
        List<InventoryVO> orderingStockList = inventoryMapper.listModelOnWayInventory(modelNo);
        for (InventoryVO info : orderingStockList) {
            temp = new Inventory();
            temp.setModelNo(info.getModelNo());
            temp.setQtyOnHand(info.getQuantity());
            temp.setQuantity(info.getQuantity());
            temp.setStockCode(info.getWarehouseCode());
            temp.setInventoryTypeCode(info.getInventoryTypeCode());
            temp.setInventoryTypeName(InventoryTypeEnum.getName(info.getInventoryTypeCode()));
            if (warehouseNameMap.containsKey(info.getWarehouseCode())) {
                temp.setStock(warehouseNameMap.get(info.getWarehouseCode()));
            } else {
                warehouseName = commonServiceFeignApi.getWarehouseName(info.getWarehouseCode());
                if (warehouseName.isSuccess() && warehouseName.getData() != null) {
                    temp.setStock(warehouseName.getData());
                    warehouseNameMap.put(info.getWarehouseCode(), warehouseName.getData());
                }
            }
            if (StringUtils.isNotBlank(info.getInventoryTypeCode())
                    && info.getInventoryTypeCode().equals(InventoryTypeEnum.TY.getCode())) {
                temp.setCustomerNo(this.getWarehouseCustomerNo(info.getWarehouseCode()));
            }
            onOrderStock.setInventoryQuantity(onOrderStock.getInventoryQuantity() + temp.getQuantity());
            onOrderStock.getList().add(temp);
        }

        return resultList;
    }*/

    @Override
    public List<InventoryInfo> getInventoryDetailByDeptNo(String modelNo, String deptNo) {
        if (StringUtils.isBlank(modelNo)) {
            return Collections.emptyList();
        }
        Date startTime = new Date();
        List<String> warehouseConfig = Collections.emptyList();
        if (StringUtils.isNotBlank(deptNo)) {
            deptNo = this.converDeptCode(deptNo);
            warehouseConfig = this.getWarehouseConfig(deptNo);
        }
        if (CollectionUtils.isEmpty(warehouseConfig)) {
            warehouseConfig = this.getCentralWarehouseCode();
        }

        List<InventoryInfo> resultList = new ArrayList<>();
        Inventory temp;
        // 可用在库
        InventoryInfo canUseStock = new InventoryInfo();
        canUseStock.setInventoryName("可用在库");
        canUseStock.setInventoryQuantity(0);
        canUseStock.setList(new ArrayList<>());
        resultList.add(canUseStock);
        List<Inventory> canUseTY = new ArrayList<>();
        List<Inventory> canUseGK = new ArrayList<>();
        //List<Inventory> canUseQB = new ArrayList<>();
        // 分库在库
        InventoryInfo subStock = new InventoryInfo();
        subStock.setInventoryName("分库在库");
        subStock.setInventoryQuantity(0);
        subStock.setList(new ArrayList<>());
        resultList.add(subStock);
        // 专备在库
        InventoryInfo GKStock = new InventoryInfo();
        GKStock.setInventoryName("专备在库");
        GKStock.setInventoryQuantity(0);
        GKStock.setList(new ArrayList<>());
        resultList.add(GKStock);
        // 预占库存
        InventoryInfo QBStock = new InventoryInfo();
        QBStock.setInventoryName("预占库存");
        QBStock.setInventoryQuantity(0);
        QBStock.setList(new ArrayList<>());
        resultList.add(QBStock);
        // 战略在库
        InventoryInfo ZLStock = new InventoryInfo();
        ZLStock.setInventoryName("战略在库");
        ZLStock.setInventoryQuantity(0);
        ZLStock.setList(new ArrayList<>());
        resultList.add(ZLStock);
        // 供应商库存
        InventoryInfo supplierStock = new InventoryInfo();
        supplierStock.setInventoryName("供应商库存");
        supplierStock.setInventoryQuantity(0);
        supplierStock.setList(new ArrayList<>());
        resultList.add(supplierStock);
        // 在途库存
        InventoryInfo onOrderStock = new InventoryInfo();
        onOrderStock.setInventoryName("在途库存");
        onOrderStock.setInventoryQuantity(0);
        onOrderStock.setList(new ArrayList<>());
        resultList.add(onOrderStock);

        // 查询型号库存信息
        List<String> modelNos = Collections.singletonList(modelNo);
        // 查询在库库存信息
        List<InventoryVO> inventoryInfoList = inventoryMapper.listModelInventory(modelNos);
        // 查询供应商库存信息
        List<InventorySupplierDO> supplierStockList = inventorySupplierService.listModelSupplierInventory(modelNos);
        // 查询在途库存信息
        List<InventoryOrderingDTO> orderingStockList = inventoryMapper.listModelOnWayInventory(modelNos);
        // 获取仓库信息
        Map<String, String> nameMap;
        List<String> warehouseCodeList = inventoryInfoList.stream().map(InventoryVO::getWarehouseCode).collect(Collectors.toList());
        warehouseCodeList.addAll(orderingStockList.stream().map(InventoryOrderingDTO::getWarehouseCode).collect(Collectors.toList()));
        warehouseCodeList = warehouseCodeList.stream().distinct().collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(warehouseCodeList)) {
            List<StockCode> stockCodeList = warehouseMapper.findGoodsLocation(warehouseCodeList);
            nameMap = new HashMap<>((stockCodeList.size() + 8));
            for (StockCode stockCode : stockCodeList) {
                nameMap.put(stockCode.getStockCode(), stockCode.getStockName());
            }
        } else {
            nameMap = new HashMap<>();
        }
        Map<String, String> customerNoMap = new HashMap<>();
        List<String> masterWarehouses = this.getCentralWarehouseCode();

        for (InventoryVO info : inventoryInfoList) {
            temp = new Inventory();
            temp.setModelNo(info.getModelNo());
            temp.setDeptNo(info.getDeptNo());
            if (StringUtils.isNotBlank(info.getDeptNo())) {
                if (!nameMap.containsKey(info.getDeptNo())) {
                    nameMap.put(info.getDeptNo(), opsCommonService.getDeptNameByNo(info.getDeptNo()));
                }
                temp.setDeptName(nameMap.get(info.getDeptNo()));
            }
            temp.setCustomerNo(info.getCustomerNo());
            temp.setQtyOnHand(info.getQuantity());
            temp.setQtyPrePare(info.getPrepareQuantity());
            temp.setQuantity(info.getQuantity() - info.getPrepareQuantity());
            temp.setBomNo(info.getPpl());
            temp.setProjectCode(info.getProjectCode());
            temp.setGroupCustomerNo(info.getGroupCustomerNo());
            temp.setInventoryTypeCode(info.getInventoryTypeCode());
            temp.setInventoryTypeName(InventoryTypeEnum.getName(info.getInventoryTypeCode()));
            temp.setStockCode(info.getWarehouseCode());
//            if (!nameMap.containsKey(info.getWarehouseCode())) {
//                nameMap.put(info.getWarehouseCode(), opsCommonService.getWarehouseNameByCode(info.getWarehouseCode()));
//            }
            temp.setStock(nameMap.get(info.getWarehouseCode()));

            // 通用在库
            if (InventoryTypeEnum.TY.getCode().equals(info.getInventoryTypeCode())) {
                if (!customerNoMap.containsKey(info.getWarehouseCode())) {
                    customerNoMap.put(info.getWarehouseCode(), this.getWarehouseCustomerNo(info.getWarehouseCode(), masterWarehouses));
                }
                temp.setCustomerNo(customerNoMap.get(info.getWarehouseCode()));
                // 可用在库-通用在库
                if (warehouseConfig.contains(info.getWarehouseCode())) {
                    canUseStock.setInventoryQuantity(canUseStock.getInventoryQuantity() + temp.getQuantity());
                    canUseTY.add(temp);
                } else {
                    // 分库在库
                    if (info.getWarehouseCode().startsWith("S") && !masterWarehouses.contains(info.getWarehouseCode())) {
                        subStock.setInventoryQuantity(subStock.getInventoryQuantity() + temp.getQuantity());
                        subStock.getList().add(temp);
                    }
                    // 客户专备(TY委托在库)
                    if (info.getWarehouseCode().startsWith("W")) {
                        GKStock.setInventoryQuantity(GKStock.getInventoryQuantity() + temp.getQuantity());
                        GKStock.getList().add(temp);
                    }
                }
                continue;
            }
            // 客户专备
            if (info.getInventoryTypeCode().startsWith("GK")) {
                // 可用在库-客户专备
                if (StringUtils.isNotBlank(deptNo) && deptNo.equals(info.getDeptNo())) {
                    canUseStock.setInventoryQuantity(canUseStock.getInventoryQuantity() + temp.getQuantity());
                    canUseGK.add(temp);
                } else {
                    GKStock.setInventoryQuantity(GKStock.getInventoryQuantity() + temp.getQuantity());
                    GKStock.getList().add(temp);
                }
                continue;
            }
            // 预占库存
            if (InventoryTypeEnum.QB_NO.getCode().equals(info.getInventoryTypeCode())) {
                /*if (StringUtils.isNotBlank(deptNo) && deptNo.equals(info.getDeptNo())) {
                    canUseStock.setInventoryQuantity(canUseStock.getInventoryQuantity() + temp.getQuantity());
                    canUseQB.add(temp);
                } else {
                    QBStock.setInventoryQuantity(QBStock.getInventoryQuantity() + temp.getQuantity());
                    QBStock.getList().add(temp);
                }*/
                QBStock.setInventoryQuantity(QBStock.getInventoryQuantity() + temp.getQuantity());
                QBStock.getList().add(temp);
                continue;
            }
            // 战略在库
            if (info.getInventoryTypeCode().startsWith("ZL")) {
                ZLStock.setInventoryQuantity(ZLStock.getInventoryQuantity() + temp.getQuantity());
                ZLStock.getList().add(temp);
            }
        }
        canUseStock.getList().addAll(canUseTY);
        canUseStock.getList().addAll(canUseGK);
        //canUseStock.getList().addAll(canUseQB);
        // 排序
        GKStock.setList(GKStock.getList().stream().sorted(Comparator.comparing(Inventory::getStockCode)).collect(Collectors.toList()));
        // 供应商库存
        for (InventorySupplierDO info : supplierStockList) {
            temp = new Inventory();
            temp.setModelNo(info.getModelNo());

            if(info.getQuantity() == null) {
                info.setQuantity(0);
            }
            if (info.getQuantityPrepare() == null) {
                info.setQuantityPrepare(0);
            }
            if(info.getBinflag() != null) {
                temp.setBinFlagStr(info.getBinflag() == 1 ? "是" : "否");
            }
            temp.setQtyOnHand(info.getQuantity());
            temp.setQuantity(info.getQuantity() - info.getQuantityPrepare());
            temp.setInventoryTypeCode(InventoryTypeEnum.TY.getCode());
            temp.setInventoryTypeName(InventoryTypeEnum.TY.getName());
            temp.setStockCode(info.getSupplierId());
            if (!nameMap.containsKey(info.getSupplierId())) {
                nameMap.put(info.getSupplierId(), opsCommonService.getSupplierNameByCode(info.getSupplierId()));
            }
            temp.setStock(nameMap.get(info.getSupplierId()));
            supplierStock.setInventoryQuantity(supplierStock.getInventoryQuantity() + temp.getQuantity());
            supplierStock.getList().add(temp);
        }
        // 在途库存
        for (InventoryOrderingDTO info : orderingStockList) {
            info.setQuantity(info.getQtyW() + info.getQtyC());
            if (info.getQuantity() == 0) {
                continue;
            }
            info.setPrepareQuantity(info.getPqtyW() + info.getPqtyC());

            temp = new Inventory();
            temp.setModelNo(info.getModelNo());
            temp.setCustomerNo(info.getCustomerNo());
            temp.setQtyOnHand(info.getQuantity());
            temp.setQtyPrePare(info.getPrepareQuantity());
            temp.setQuantity(info.getQuantity() - info.getPrepareQuantity());
            temp.setBomNo(info.getPpl());
            temp.setProjectCode(info.getProjectCode());
            temp.setGroupCustomerNo(info.getGroupCustomerNo());
            temp.setInventoryTypeCode(info.getInventoryTypeCode());
            temp.setInventoryTypeName(InventoryTypeEnum.getName(info.getInventoryTypeCode()));
            temp.setStockCode(info.getWarehouseCode());
//            if (!nameMap.containsKey(info.getWarehouseCode())) {
//                nameMap.put(info.getWarehouseCode(), opsCommonService.getWarehouseNameByCode(info.getWarehouseCode()));
//            }
            temp.setStock(nameMap.get(info.getWarehouseCode()));
            if (StringUtils.isNotBlank(info.getInventoryTypeCode()) && InventoryTypeEnum.TY.getCode().equals(info.getInventoryTypeCode())) {
                if (!customerNoMap.containsKey(info.getWarehouseCode())) {
                    customerNoMap.put(info.getWarehouseCode(), this.getWarehouseCustomerNo(info.getWarehouseCode(), masterWarehouses));
                }
                temp.setCustomerNo(customerNoMap.get(info.getWarehouseCode()));
            }
            onOrderStock.setInventoryQuantity(onOrderStock.getInventoryQuantity() + temp.getQuantity());
            onOrderStock.getList().add(temp);
        }
        log.info("查询产品库存信息: 开始时间-{}, 耗时: {}(s)", DateUtil.dateToDateTimeString(startTime), DateUtil.getDiffSecond(startTime, new Date()));
        return resultList;
    }

    /*@Override
    public List<Inventory> findInventoryList(List<String> modelNoList, String deptNo) {
        // 查询部门的可出库仓库
        deptNo = this.converDeptCode(deptNo);
        List<String> warehouseConfig = this.getWarehouseConfig(deptNo);
        if (CollectionUtils.isEmpty(warehouseConfig)) {
            log.info("findInventoryList deptNo = {} warehouseConfig is NULL.", deptNo);
            return Collections.emptyList();
        }
        // 查询营业所专备 inventory_property_id
        List<Long> propertyIdList = inventoryPropertyMapper.listDeptCustomerPropertyId(deptNo);
        if (propertyIdList == null) {
            propertyIdList = Lists.newArrayList();
        }
        // 查询型号的所有库存信息
        List<Inventory> inventoryList = new ArrayList<>();
        Inventory temp;
        List<InventoryVO> stockList;
        Map<String, String> warehouseNameMap = new HashMap<>();
        ResultVo<String> warehouseName;
        List<Inventory> canUseTY;
        List<Inventory> canUseGK;
        List<Inventory> canUseQB;
        List<Inventory> canUseZL;
        List<Inventory> subStock;
        List<Inventory> GKStock;
        List<Inventory> QBStock;
        List<Inventory> ZLStock;

        for (String modelNo : modelNoList) {
            stockList = inventoryMapper.listModelInventory(modelNo, null);
            canUseTY = new ArrayList<>();
            canUseGK = new ArrayList<>();
            canUseQB = new ArrayList<>();
            canUseZL = new ArrayList<>();
            subStock = new ArrayList<>();
            GKStock = new ArrayList<>();
            QBStock = new ArrayList<>();
            ZLStock = new ArrayList<>();
            for (InventoryVO info : stockList) {
                temp = new Inventory();
                temp.setModelNo(info.getModelNo());
                temp.setCustomerNo(info.getCustomerNo());
                temp.setQtyOnHand(info.getQuantity());
                temp.setQtyPrePare(info.getPrepareQuantity());
                temp.setQuantity(info.getQuantity() - info.getPrepareQuantity());
                temp.setStockCode(info.getWarehouseCode());
                temp.setBomNo(info.getPpl());
                temp.setProjectCode(info.getProjectCode());
                temp.setGroupCustomerNo(info.getGroupCustomerNo());
                temp.setInventoryTypeCode(info.getInventoryTypeCode());
                temp.setInventoryTypeName(InventoryTypeEnum.getName(info.getInventoryTypeCode()));
                if (warehouseNameMap.containsKey(info.getWarehouseCode())) {
                    temp.setStock(warehouseNameMap.get(info.getWarehouseCode()));
                } else {
                    warehouseName = commonServiceFeignApi.getWarehouseName(info.getWarehouseCode());
                    if (warehouseName.isSuccess() && warehouseName.getData() != null) {
                        temp.setStock(warehouseName.getData());
                        warehouseNameMap.put(info.getWarehouseCode(), warehouseName.getData());
                    }
                }
                temp.setStockCode(temp.getStock() + "(" + temp.getStockCode() + ")");
                if (info.getInventoryTypeCode().equals(InventoryTypeEnum.TY.getCode())) {
                    temp.setCustomerNo(this.getWarehouseCustomerNo(info.getWarehouseCode()));
                }

                if (warehouseConfig.contains(info.getWarehouseCode())) {
                    // 通用在库
                    if (StringUtils.equals(info.getInventoryTypeCode(), InventoryTypeEnum.TY.getCode())) {
                        temp.setStock("可用在库");
                        canUseTY.add(temp);
                    }
                    // 客户专备
                    if (info.getInventoryTypeCode().startsWith("GK")) {
                        if (propertyIdList.contains(info.getPropertyId())) {
                            temp.setStock("可用在库");
                            canUseGK.add(temp);
                        } else {
                            temp.setStock("专备在库");
                            GKStock.add(temp);
                        }
                    }
                    // 营业情报
                    if (StringUtils.equals(info.getInventoryTypeCode(), InventoryTypeEnum.QB_NO.getCode())) {
                        if (propertyIdList.contains(info.getPropertyId())) {
                            temp.setStock("可用在库");
                            canUseQB.add(temp);
                        } else {
                            temp.setStock("预占库存");
                            QBStock.add(temp);
                        }
                    }
                    // 战略在库
                    if (info.getInventoryTypeCode().startsWith("ZL")) {
                        temp.setStock("战略在库");
                        canUseZL.add(temp);
                    }
                } else {
                    if (info.getInventoryTypeCode().equals(InventoryTypeEnum.TY.getCode())
                            && info.getWarehouseCode().startsWith("S")) {
                        temp.setStock("分库在库");
                        subStock.add(temp);
                    }
                    if (info.getInventoryTypeCode().startsWith("GK")) {
                        temp.setStock("专备在库");
                        GKStock.add(temp);
                    }
                    if (StringUtils.equals(info.getInventoryTypeCode(), InventoryTypeEnum.QB_NO.getCode())) {
                        temp.setStock("营业情报");
                        QBStock.add(temp);
                    }
                    if (info.getInventoryTypeCode().startsWith("ZL")) {
                        temp.setStock("战略在库");
                        ZLStock.add(temp);
                    }
                }
            }
            inventoryList.addAll(canUseTY);
            inventoryList.addAll(canUseGK);
            inventoryList.addAll(canUseQB);
            inventoryList.addAll(canUseZL);
            inventoryList.addAll(subStock);
            inventoryList.addAll(GKStock);
            inventoryList.addAll(QBStock);
            inventoryList.addAll(ZLStock);
            // 查询供应商库存
            List<InventorySupplierDO> supplierStockList = inventorySupplierService.listModelSupplierInventory(modelNo);
            Map<String, String> supplierNameMap = new HashMap<>();
            ResultVo<String> supplierName;
            for (InventorySupplierDO info : supplierStockList) {
                temp = new Inventory();
                temp.setModelNo(info.getModelNo());
                temp.setQtyOnHand(info.getQuantity());
                temp.setQuantity(info.getQuantity());
                temp.setStockCode(info.getSupplierId());
                temp.setInventoryTypeCode(InventoryTypeEnum.TY.getCode());
                temp.setInventoryTypeName(InventoryTypeEnum.TY.getName());
                if (supplierNameMap.containsKey(info.getSupplierId())) {
                    temp.setStock(supplierNameMap.get(info.getSupplierId()));
                } else {
                    supplierName = commonServiceFeignApi.getSupplierName(info.getSupplierId());
                    if (supplierName.isSuccess() && supplierName.getData() != null) {
                        temp.setStock(supplierName.getData());
                        supplierNameMap.put(info.getSupplierId(), temp.getStock());
                    }
                }
                temp.setStockCode(temp.getStock() + "(" + temp.getStockCode() + ")");
                temp.setStock("供应商库存");
                inventoryList.add(temp);
            }
            // 查询型号的在途库存
            ModelWarehouseStockRequest dto = new ModelWarehouseStockRequest();
            dto.setModelNos(Arrays.asList(modelNo));
            List<InventoryVO> orderingStockList = inventoryMapper.listModelOnWayInventory(modelNo);
            for (InventoryVO info : orderingStockList) {
                temp = new Inventory();
                temp.setModelNo(info.getModelNo());
                temp.setQtyOnHand(info.getQuantity());
                temp.setQuantity(info.getQuantity());
                temp.setStockCode(info.getWarehouseCode());
                temp.setInventoryTypeCode(info.getInventoryTypeCode());
                temp.setInventoryTypeName(InventoryTypeEnum.getName(info.getInventoryTypeCode()));
                if (warehouseNameMap.containsKey(info.getWarehouseCode())) {
                    temp.setStock(warehouseNameMap.get(info.getWarehouseCode()));
                } else {
                    warehouseName = commonServiceFeignApi.getWarehouseName(info.getWarehouseCode());
                    if (warehouseName.isSuccess() && warehouseName.getData() != null) {
                        temp.setStock(warehouseName.getData());
                        warehouseNameMap.put(info.getWarehouseCode(), warehouseName.getData());
                    }
                }
                if (StringUtils.isNotBlank(info.getInventoryTypeCode())
                        && info.getInventoryTypeCode().equals(InventoryTypeEnum.TY.getCode())) {
                    temp.setCustomerNo(this.getWarehouseCustomerNo(info.getWarehouseCode()));
                }
                temp.setStockCode(temp.getStock() + "(" + temp.getStockCode() + ")");
                temp.setStock("在途库存");
                inventoryList.add(temp);
            }
        }
        return inventoryList;
    } */

//    @Override
//    public List<Inventory> findInventoryList(List<String> modelNoList, String deptNo) {
//        Date startTime = new Date();
//        deptNo = this.converDeptCode(deptNo);
//        List<String> warehouseConfig = this.getWarehouseConfig(deptNo);
//        if (CollectionUtils.isEmpty(warehouseConfig)) {
//            warehouseConfig = this.getCentralWarehouseCode();
//        }
//
//        List<Inventory> inventoryList = new ArrayList<>();
//        Inventory temp;
//        Map<String, String> nameMap = new HashMap<>();
//        List<Inventory> canUseTY;
//        List<Inventory> canUseGK;
//        //List<Inventory> canUseQB;
//        List<Inventory> subStock;
//        List<Inventory> GKStock;
//        List<Inventory> QBStock;
//        List<Inventory> ZLStock;
//        List<InventorySupplierDO> supplierStockList;
//        List<InventoryOrderingDTO> orderingStockList;
//
//        if (CollectionUtil.isEmpty(modelNoList)) {
//            return inventoryList;
//        }
//        List<InventoryVO> inventoryInfoList = inventoryMapper.listModelInventory(modelNoList);
//        Map<String, List<InventoryVO>> inventoryInfoMap = inventoryInfoList.stream().collect(Collectors.groupingBy(InventoryVO::getModelNo));
//        Map<String, String> customerNoMap = new HashMap<>();
//        List<String> masterWarehouses = commonService.getMasterWarehouseCodes();
//
//        for (String modelNo : modelNoList) {
//            if (CollectionUtils.isNotEmpty(inventoryInfoMap.get(modelNo))) {
//                canUseTY = new ArrayList<>();
//                canUseGK = new ArrayList<>();
//                //canUseQB = new ArrayList<>();
//                subStock = new ArrayList<>();
//                GKStock = new ArrayList<>();
//                QBStock = new ArrayList<>();
//                ZLStock = new ArrayList<>();
//                for (InventoryVO info : inventoryInfoMap.get(modelNo)) {
//                    temp = new Inventory();
//                    temp.setModelNo(info.getModelNo());
//                    temp.setCustomerNo(info.getCustomerNo());
//                    temp.setQtyOnHand(info.getQuantity());
//                    temp.setQtyPrePare(info.getPrepareQuantity());
//                    temp.setQuantity(info.getQuantity() - info.getPrepareQuantity());
//                    temp.setStockCode(info.getWarehouseCode());
//                    temp.setBomNo(info.getPpl());
//                    temp.setProjectCode(info.getProjectCode());
//                    temp.setGroupCustomerNo(info.getGroupCustomerNo());
//                    temp.setInventoryTypeCode(info.getInventoryTypeCode());
//                    temp.setInventoryTypeName(InventoryTypeEnum.getName(info.getInventoryTypeCode()));
//                    if (!nameMap.containsKey(info.getWarehouseCode())) {
//                        nameMap.put(info.getWarehouseCode(), commonService.getWarehouseNameByCode(info.getWarehouseCode()));
//                    }
//                    temp.setStock(nameMap.get(info.getWarehouseCode()));
//                    temp.setStockCode(temp.getStock() + "(" + temp.getStockCode() + ")");
//
//                    // 通用在库
//                    if (InventoryTypeEnum.TY.getCode().equals(info.getInventoryTypeCode())) {
//                        if (!customerNoMap.containsKey(info.getWarehouseCode())) {
//                            customerNoMap.put(info.getWarehouseCode(), this.getWarehouseCustomerNo(info.getWarehouseCode(), masterWarehouses));
//                        }
//                        temp.setCustomerNo(customerNoMap.get(info.getWarehouseCode()));
//                        if (warehouseConfig.contains(info.getWarehouseCode())) {
//                            temp.setStock("可用在库");
//                            canUseTY.add(temp);
//                        } else {
//                            if (info.getWarehouseCode().startsWith("S") && !masterWarehouses.contains(info.getWarehouseCode())) {
//                                temp.setStock("分库在库");
//                                subStock.add(temp);
//                            }
//                        }
//                        continue;
//                    }
//                    // 客户专备
//                    if (info.getInventoryTypeCode().startsWith("GK")) {
//                        if (deptNo.equals(info.getDeptNo())) {
//                            temp.setStock("可用在库");
//                            canUseGK.add(temp);
//                        } else {
//                            temp.setStock("专备在库");
//                            GKStock.add(temp);
//                        }
//                        continue;
//                    }
//                    // 预占库存
//                    if (InventoryTypeEnum.QB_NO.getCode().equals(info.getInventoryTypeCode())) {
//                        /*if (deptNo.equals(info.getDeptNo())) {
//                            temp.setStock("可用在库");
//                            canUseQB.add(temp);
//                        } else {
//                            temp.setStock("预占库存");
//                            QBStock.add(temp);
//                        }*/
//                        temp.setStock("预占库存");
//                        QBStock.add(temp);
//                        continue;
//                    }
//                    // 战略在库
//                    if (info.getInventoryTypeCode().startsWith("ZL")) {
//                        temp.setStock("战略在库");
//                        ZLStock.add(temp);
//                    }
//                }
//                inventoryList.addAll(canUseTY);
//                inventoryList.addAll(canUseGK);
//                //inventoryList.addAll(canUseQB);
//                inventoryList.addAll(subStock);
//                inventoryList.addAll(GKStock);
//                inventoryList.addAll(QBStock);
//                inventoryList.addAll(ZLStock);
//            }
//
//            // 查询供应商库存
//            supplierStockList = inventorySupplierService.listModelSupplierInventory(modelNo);
//            for (InventorySupplierDO info : supplierStockList) {
//                temp = new Inventory();
//                temp.setModelNo(info.getModelNo());
//                temp.setQtyOnHand(info.getQuantity());
//                temp.setQuantity(info.getQuantity());
//                temp.setStockCode(info.getSupplierId());
//                temp.setInventoryTypeCode(InventoryTypeEnum.TY.getCode());
//                temp.setInventoryTypeName(InventoryTypeEnum.TY.getName());
//                if (!nameMap.containsKey(info.getSupplierId())) {
//                    nameMap.put(info.getSupplierId(), commonService.getSupplierNameByCode(info.getSupplierId()));
//
//                }
//                temp.setStock(nameMap.get(info.getSupplierId()));
//                temp.setStockCode(temp.getStock() + "(" + temp.getStockCode() + ")");
//                temp.setStock("供应商库存");
//                inventoryList.add(temp);
//            }
//            // 查询型号的在途库存
//            orderingStockList = inventoryMapper.listModelOnWayInventory(modelNo);
//            for (InventoryOrderingDTO info : orderingStockList) {
//                info.setQuantity(info.getQtyW() + info.getQtyC());
//                info.setPrepareQuantity(info.getPqtyW() + info.getPqtyC());
//
//                temp = new Inventory();
//                temp.setModelNo(info.getModelNo());
//                temp.setCustomerNo(info.getCustomerNo());
//                temp.setQtyOnHand(info.getQuantity());
//                temp.setQtyPrePare(info.getPrepareQuantity());
//                temp.setQuantity(info.getQuantity() - info.getPrepareQuantity());
//                temp.setStockCode(info.getWarehouseCode());
//                temp.setInventoryTypeCode(info.getInventoryTypeCode());
//                temp.setInventoryTypeName(InventoryTypeEnum.getName(info.getInventoryTypeCode()));
//                if (!nameMap.containsKey(info.getWarehouseCode())) {
//                    nameMap.put(info.getWarehouseCode(), commonService.getWarehouseNameByCode(info.getWarehouseCode()));
//                }
//                temp.setStock(nameMap.get(info.getWarehouseCode()));
//                if (StringUtils.isNotBlank(info.getInventoryTypeCode()) && info.getInventoryTypeCode().equals(InventoryTypeEnum.TY.getCode())) {
//                    if (!customerNoMap.containsKey(info.getWarehouseCode())) {
//                        customerNoMap.put(info.getWarehouseCode(), this.getWarehouseCustomerNo(info.getWarehouseCode(), masterWarehouses));
//                    }
//                    temp.setCustomerNo(customerNoMap.get(info.getWarehouseCode()));
//                }
//                temp.setStockCode(temp.getStock() + "(" + temp.getStockCode() + ")");
//                temp.setStock("在途库存");
//                inventoryList.add(temp);
//            }
//        }
//        log.info("查询拆分型号库存信息 startTime-{}, 耗时: {}(s)", DateUtil.dateToDateTimeString(startTime), DateUtil.getDiffSecond(startTime, new Date()));
//        return inventoryList;
//    }

    @Override
    public List<Inventory> findInventoryList(List<String> modelNoList, String deptNo) {
        if (CollectionUtil.isEmpty(modelNoList)) {
            return Collections.emptyList();
        }
        Date startTime = new Date();
        deptNo = this.converDeptCode(deptNo);
        List<String> warehouseConfig = this.getWarehouseConfig(deptNo);
        if (CollectionUtils.isEmpty(warehouseConfig)) {
            warehouseConfig = this.getCentralWarehouseCode();
        }

        // 在库库存
        List<InventoryVO> inventoryInfoList = inventoryMapper.listModelInventory(modelNoList);
        Map<String, List<InventoryVO>> inventoryInfoMap = inventoryInfoList.stream().collect(Collectors.groupingBy(InventoryVO::getModelNo));
        // 查询供应商库存
        List<InventorySupplierDO> supplierStockList = inventorySupplierService.listModelSupplierInventory(modelNoList);
        Map<String, List<InventorySupplierDO>> supplierStockMap = supplierStockList.stream().collect(Collectors.groupingBy(InventorySupplierDO::getModelNo));
        // 查询在途库存
        List<InventoryOrderingDTO> orderingStockList = inventoryMapper.listModelOnWayInventory(modelNoList);
        Map<String, List<InventoryOrderingDTO>> orderingStockMap = orderingStockList.stream().collect(Collectors.groupingBy(InventoryOrderingDTO::getModelNo));
        // 获取物流中心
        List<String> masterWarehouses = this.getCentralWarehouseCode();
        // 获取仓库信息
        StringBuilder nameBuilder = new StringBuilder(32);
        Map<String, String> nameMap;
        List<String> warehouseCodeList = inventoryInfoList.stream().map(InventoryVO::getWarehouseCode).collect(Collectors.toList());
        warehouseCodeList.addAll(orderingStockList.stream().map(InventoryOrderingDTO::getWarehouseCode).collect(Collectors.toList()));
        warehouseCodeList = warehouseCodeList.stream().distinct().collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(warehouseCodeList)) {
            List<StockCode> stockCodeList = warehouseMapper.findGoodsLocation(warehouseCodeList);
            nameMap = new HashMap<>((stockCodeList.size() + 8));
            for (StockCode stockCode : stockCodeList) {
                nameBuilder.setLength(0);
                nameBuilder.append(stockCode.getStockName()).append("(").append(stockCode.getStockCode()).append(")");
                nameMap.put(stockCode.getStockCode(), nameBuilder.toString());
            }
        } else {
            nameMap = new HashMap<>();
        }
        log.info("查询拆分型号库存信息 startTime-{}, 耗时: {}(s)", DateUtil.dateToDateTimeString(startTime), DateUtil.getDiffSecond(startTime, new Date()));

        Inventory temp;
        List<Inventory> canUseTY = new ArrayList<>();
        List<Inventory> canUseGK = new ArrayList<>();
        //List<Inventory> canUseQB = canUseQB = new ArrayList<>();
        List<Inventory> subStock = new ArrayList<>();
        List<Inventory> GKStock = new ArrayList<>(inventoryInfoList.size());
        List<Inventory> QBStock = new ArrayList<>();
        List<Inventory> ZLStock = new ArrayList<>();
        List<Inventory> supplierStock = new ArrayList<>(supplierStockList.size());
        List<Inventory> orderingStock = new ArrayList<>(orderingStockList.size());
        Map<String, String> customerNoMap = new HashMap<>();
        String[] stockName = new String[]{"可用在库", "专备在库", "分库在库", "预占库存", "战略在库", "供应商库存", "在途库存"};

        for (String modelNo : modelNoList) {
            if (inventoryInfoMap.containsKey(modelNo)) {
                for (InventoryVO info : inventoryInfoMap.get(modelNo)) {
                    temp = new Inventory();
                    temp.setModelNo(info.getModelNo());
                    temp.setCustomerNo(info.getCustomerNo());
                    temp.setQtyOnHand(info.getQuantity());
                    temp.setQtyPrePare(info.getPrepareQuantity());
                    temp.setQuantity(info.getQuantity() - info.getPrepareQuantity());
                    temp.setStockCode(info.getWarehouseCode());
                    temp.setBomNo(info.getPpl());
                    temp.setProjectCode(info.getProjectCode());
                    temp.setGroupCustomerNo(info.getGroupCustomerNo());
                    temp.setInventoryTypeCode(info.getInventoryTypeCode());
                    temp.setInventoryTypeName(InventoryTypeEnum.getName(info.getInventoryTypeCode()));
//                    if (!nameMap.containsKey(info.getWarehouseCode())) {
//                        nameBuilder.setLength(0);
//                        nameBuilder.append(opsCommonService.getWarehouseNameByCode(info.getWarehouseCode())).append("(").append(temp.getStockCode()).append(")");
//                        nameMap.put(info.getWarehouseCode(), nameBuilder.toString());
//                    }
                    temp.setStockCode(nameMap.get(info.getWarehouseCode()));

                    // 通用在库
                    if (InventoryTypeEnum.TY.getCode().equals(info.getInventoryTypeCode())) {
                        if (!customerNoMap.containsKey(info.getWarehouseCode())) {
                            customerNoMap.put(info.getWarehouseCode(), this.getWarehouseCustomerNo(info.getWarehouseCode(), masterWarehouses));
                        }
                        temp.setCustomerNo(customerNoMap.get(info.getWarehouseCode()));
                        if (warehouseConfig.contains(info.getWarehouseCode())) {
                            temp.setStock(stockName[0]);
                            canUseTY.add(temp);
                        } else {
                            if (info.getWarehouseCode().startsWith("S") && !masterWarehouses.contains(info.getWarehouseCode())) {
                                temp.setStock(stockName[2]);
                                subStock.add(temp);
                            }
                            // 客户专备(TY委托在库)
                            if (info.getWarehouseCode().startsWith("W")) {
                                temp.setStock(stockName[1]);
                                GKStock.add(temp);
                            }
                        }
                        continue;
                    }
                    // 客户专备
                    if (info.getInventoryTypeCode().startsWith("GK")) {
                        if (deptNo.equals(info.getDeptNo())) {
                            temp.setStock(stockName[0]);
                            canUseGK.add(temp);
                        } else {
                            temp.setStock(stockName[1]);
                            GKStock.add(temp);
                        }
                        continue;
                    }
                    // 预占库存
                    if (InventoryTypeEnum.QB_NO.getCode().equals(info.getInventoryTypeCode())) {
                        /*if (deptNo.equals(info.getDeptNo())) {
                            temp.setStock("可用在库");
                            canUseQB.add(temp);
                        } else {
                            temp.setStock("预占库存");
                            QBStock.add(temp);
                        }*/
                        temp.setStock(stockName[3]);
                        QBStock.add(temp);
                        continue;
                    }
                    // 战略在库
                    if (info.getInventoryTypeCode().startsWith("ZL")) {
                        temp.setStock(stockName[4]);
                        ZLStock.add(temp);
                    }
                }
            }

            // 供应商库存
            if (supplierStockMap.containsKey(modelNo)) {
                for (InventorySupplierDO info : supplierStockMap.get(modelNo)) {
                    temp = new Inventory();
                    if (info.getQuantity() == null) {
                        info.setQuantity(0);
                    }
                    if (info.getQuantityPrepare() == null) {
                        info.setQuantityPrepare(0);
                    }
                    temp.setModelNo(info.getModelNo());
                    temp.setQtyOnHand(info.getQuantity());
                    temp.setQuantity(info.getQuantity() - info.getQuantityPrepare());
                    temp.setStockCode(info.getSupplierId());
                    temp.setInventoryTypeCode(InventoryTypeEnum.TY.getCode());
                    temp.setInventoryTypeName(InventoryTypeEnum.TY.getName());
                    if (!nameMap.containsKey(info.getSupplierId())) {
                        nameBuilder.setLength(0);
                        nameBuilder.append(opsCommonService.getSupplierNameByCode(info.getSupplierId())).append("(").append(temp.getStockCode()).append(")");
                        nameMap.put(info.getSupplierId(), nameBuilder.toString());
                    }
                    temp.setStockCode(nameMap.get(info.getSupplierId()));
                    temp.setStock(stockName[5]);
                    supplierStock.add(temp);
                }
            }
            // 查询型号的在途库存
            if (orderingStockMap.containsKey(modelNo)) {
                for (InventoryOrderingDTO info : orderingStockMap.get(modelNo)) {
                    info.setQuantity(info.getQtyW() + info.getQtyC());
                    if (info.getQuantity() == 0) {
                        continue;
                    }
                    info.setPrepareQuantity(info.getPqtyW() + info.getPqtyC());

                    temp = new Inventory();
                    temp.setModelNo(info.getModelNo());
                    temp.setCustomerNo(info.getCustomerNo());
                    temp.setQtyOnHand(info.getQuantity());
                    temp.setQtyPrePare(info.getPrepareQuantity());
                    temp.setQuantity(info.getQuantity() - info.getPrepareQuantity());
                    temp.setStockCode(info.getWarehouseCode());
                    temp.setInventoryTypeCode(info.getInventoryTypeCode());
                    temp.setInventoryTypeName(InventoryTypeEnum.getName(info.getInventoryTypeCode()));
//                    if (!nameMap.containsKey(info.getWarehouseCode())) {
//                        nameBuilder.setLength(0);
//                        nameBuilder.append(opsCommonService.getWarehouseNameByCode(info.getWarehouseCode())).append("(").append(temp.getStockCode()).append(")");
//                        nameMap.put(info.getWarehouseCode(), nameBuilder.toString());
//                    }
                    temp.setStockCode(nameMap.get(info.getWarehouseCode()));
                    if (StringUtils.isNotBlank(info.getInventoryTypeCode()) && info.getInventoryTypeCode().equals(InventoryTypeEnum.TY.getCode())) {
                        if (!customerNoMap.containsKey(info.getWarehouseCode())) {
                            customerNoMap.put(info.getWarehouseCode(), this.getWarehouseCustomerNo(info.getWarehouseCode(), masterWarehouses));
                        }
                        temp.setCustomerNo(customerNoMap.get(info.getWarehouseCode()));
                    }
                    temp.setStock(stockName[6]);
                    orderingStock.add(temp);
                }
            }
        }
        int dataSize = canUseTY.size() + canUseGK.size() + subStock.size() + GKStock.size() + QBStock.size() + ZLStock.size() + supplierStock.size() + orderingStock.size();
        List<Inventory> inventoryList = new ArrayList<>(dataSize);
        inventoryList.addAll(canUseTY);
        inventoryList.addAll(canUseGK);
        //inventoryList.addAll(canUseQB);
        inventoryList.addAll(subStock);
        inventoryList.addAll(GKStock);
        inventoryList.addAll(QBStock);
        inventoryList.addAll(ZLStock);
        inventoryList.addAll(supplierStock);
        inventoryList.addAll(orderingStock);
        log.info("查询拆分型号库存信息 startTime-{}, 总耗时: {}(s)", DateUtil.dateToDateTimeString(startTime), DateUtil.getDiffSecond(startTime, new Date()));
        return inventoryList;
    }

    @Override
    public ResultVo<Map<String, Integer>> getQuantityCanUseBatch(List<String> modelNos, String deptNo, String customerNo) {
        if (CollectionUtils.isEmpty(modelNos)) {
            return ResultVo.success(Collections.emptyMap());
        }

        // 查询营业所可出库仓库列表
        List<String> warehouseList = null;
        if (StringUtils.isNotBlank(deptNo)) {
            warehouseList = this.getWarehouseConfig(this.converDeptCode(deptNo));
        }
        if (CollectionUtils.isEmpty(warehouseList)) {
            warehouseList = this.getCentralWarehouseCode();
        }
        // 获取客户的委托在库仓库
        if (StringUtils.isNotBlank(customerNo)) {
            List<String> wtWarehouseCode = warehouseMapper.getWTWarehouseByCustomerNo(customerNo);
            if (CollectionUtils.isNotEmpty(wtWarehouseCode)) {
                warehouseList.addAll(wtWarehouseCode);
            }
        }
        Map<String, Integer> result = new HashMap<>(modelNos.size());
        List<String> temp;
        List<InventoryVO> avaQtyList;
        List<InventoryVO> supplierCanOrderQtyList;

        // 查询型号有效库存
        int i = 0;
        while (i < modelNos.size()) {
            if (modelNos.size() < 2001) {
                temp = modelNos;
            } else {
                if (modelNos.size() > (i + 2000)) {
                    temp = modelNos.subList(i, i + 2000);
                } else {
                    temp = modelNos.subList(i, modelNos.size());
                }
            }
            i += temp.size();
            // 查询OPS库存
            avaQtyList = inventoryMapper.listModelCanUseQuantity(temp, warehouseList, customerNo);
            for (InventoryVO info : avaQtyList) {
                result.put(info.getModelNo(), info.getAvaQty());
            }
            // 查询供应商库存 bug-11913
            supplierCanOrderQtyList = inventorySupplierService.listModelSupplierCanOrderQty(temp);
            for (InventoryVO info : supplierCanOrderQtyList) {
                result.put(info.getModelNo(), info.getAvaQty() + result.getOrDefault(info.getModelNo(), 0));
            }
        }

        for (String modelNo : modelNos) {
            if (!result.containsKey(modelNo)) {
                result.put(modelNo, 0);
            }
        }
        return ResultVo.success(result);
    }

    // Add by DengDenghui 2022-11-08 for bug-8597
    @Override
    public ResultVo<List<MaterialModelInfo>> getMaterialModelCanUseQtyBatch(List<MaterialModelInfo> infoList, String deptNo) {
        if (CollectionUtils.isEmpty(infoList)) {
            return ResultVo.success(Collections.emptyList());
        }

        // 查询营业所可出库仓库列表
        List<String> warehouseList = null;
        if (StringUtils.isNotBlank(deptNo)) {
            warehouseList = this.getWarehouseConfig(this.converDeptCode(deptNo));
        }
        if (CollectionUtils.isEmpty(warehouseList)) {
            warehouseList = this.getCentralWarehouseCode();
        }

        // 按客户代码分组查询
        Map<String, List<MaterialModelInfo>> customerMap = infoList.stream().collect(Collectors.groupingBy(MaterialModelInfo::getCustomerNo));
        List<String> modelNoList;
        List<String> temp;


        for (Map.Entry<String, List<MaterialModelInfo>> entry : customerMap.entrySet()) {
            // 提取型号
            modelNoList = entry.getValue().stream().map(MaterialModelInfo::getModelNo).distinct().collect(Collectors.toList());
            Map<String, Integer> avaQtyMap = new HashMap<>(modelNoList.size());
            int i = 0;

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

                List<InventoryVO> avaQtyList = inventoryMapper.listModelCanUseQuantity(temp, warehouseList, entry.getKey());
                for (InventoryVO info : avaQtyList) {
                    avaQtyMap.put(info.getModelNo(), info.getAvaQty());
                }
            }
            for (MaterialModelInfo info : entry.getValue()) {
                info.setAvaQty(avaQtyMap.getOrDefault(info.getModelNo(), 0));
            }
        }

        return ResultVo.success(infoList);
    } // End

    @Override
    public ResultVo<List<StockCode>> findSubTreasuryByOperator(DataAuthoritySearchCondition condition, String deptNo) {
        List<StockCode> list = new ArrayList<>();
        List<String> deptNos = new ArrayList<>();

        if (CollectionUtils.isNotEmpty(condition.getDeptCodeListByDataAuthority())) {
            deptNos = condition.getDeptCodeListByDataAuthority();
        }
        if (CollectionUtils.isEmpty(deptNos)) {
            deptNos = new ArrayList<>();
            deptNos.add(deptNo);
        }

        List<StockCode> stockCodeList;
        for (String no : deptNos) {
            // 查询营业所可补库分库代码列表
            List<String> warehouseList = this.getSubWarehouseConfig(this.converDeptCode(no));
            if (CollectionUtils.isEmpty(warehouseList)) {
                continue;
            }
            // 根据仓库列表查询仓库信息
            stockCodeList = warehouseMapper.findGoodsLocation(warehouseList);
            this.setStockTypeName(stockCodeList);
            stockCodeList = stockCodeList.stream()
                    .sorted(Comparator.comparingInt(o -> warehouseList.indexOf(o.getStockCode())))
                    .collect(Collectors.toList());
            list.addAll(stockCodeList);
        }

        return ResultVo.success(list);
    }

    @Override
    public ResultVo<List<ShikomiInfo>> getByNoBatch(List<String> answerNo) {
        if (PublicUtil.isEmpty(answerNo)) {
            return ResultVo.failure("未查到到shikomi");
        }

        List<VShikomiTotalDO> totalDOList = vShikomiTotalMapper.selectListByNo(answerNo);
        List<ShikomiInfo> infos = this.getShikomiInfos(totalDOList);

        return ResultVo.success(infos);
    }

    @Override
    public ResultVo<List<ShikomiInfo>> findByModelNoList(List<ShikomiCondition> conditionList) {
        log.info("门户shikomi查询: {}", conditionList.toString());
        if (CollectionUtil.isEmpty(conditionList)) {
            return ResultVo.failure("传入参数为空，请检查");
        }
        try {
            // 获取当前查询使用的客户
            String customerNo = conditionList.get(0).getCustomerNo();
            // 获取所有型号的集合
            List<String> modelList = conditionList.stream().map(ShikomiCondition::getModelNo).filter(StringUtils::isNotBlank).collect(Collectors.toList());
            // 过滤掉已知不是shikomi的组合
            modelList.removeIf(item -> redisManager.hHasKey("ops:shikomi:nomodelno", item + "-" + customerNo));
            if (CollectionUtil.isEmpty(modelList)) {
                return ResultVo.success(Collections.emptyList());
            }
            // 根据型号集合查询出所有的shikomi
            List<VShikomiTotalDO> list = vShikomiTotalMapper.findByModelNoList2(modelList);
            // 进行shikomi是否可用的判断
            ShikomiModelDTO modelDTO = this.getModelNoList(list, customerNo);
            Map<String, String> map = modelDTO.getMap(); // 可用的型号map集合
            List<ShikomiInfo> infoList = modelDTO.getInfoList(); // 可用的list集合
            // noList 获取不可用型号集合
            List<String> noList = modelList.parallelStream().filter(str -> !map.containsKey(str)).collect(Collectors.toList());
            Map<String, String> modelMap = new HashMap<>(noList.size());
            // 模糊匹配到的型号集合
            List<String> twList = new ArrayList<>(noList.size());

            if (CollectionUtil.isNotEmpty(noList)) {
                // 没有查询到信息的型号进行 完整匹配模糊型号 再查询
                Object o = redisManager.getHashKeyValue("ops:shikomi:serialmodel");
                Map<String, Object> hashMap = JSON.parseObject(JSON.toJSONString(o), HashMap.class);//模糊集合
                String substring;

                for (String model : noList) { //使用正则表达式匹配是否为模糊型号
                    for (Map.Entry<String, Object> entry : hashMap.entrySet()) {
                        substring = entry.getValue().toString().substring(0, 1);
                        if (!model.substring(0, 1).equalsIgnoreCase(substring) && !substring.equals("*")) {
                            continue;
                        }
                        if (Pattern.matches(entry.getKey(), model)) {
                            twList.add(entry.getValue().toString()); // 匹配到的型号集合
//                            modelMap.put(entry.getValue().toString(), model); // 匹配到的型号map集合 用于返回时返回查询型号
                            modelMap.put(model, entry.getValue().toString());
                            break;
                        }
                    }
                }
            }

            // 有匹配到模糊型号则进行二次查询
            // 2023/07/26 bug11572 WuJiaWen
            if (CollectionUtil.isNotEmpty(twList)) {
                List<VShikomiTotalDO> totalDOList = new ArrayList<>();
                for (Map.Entry<String, String> entry : modelMap.entrySet()) {
                    List<VShikomiTotalDO> doList = vShikomiTotalMapper.findByModelNoList3(entry.getValue());
                    totalDOList.addAll(doList);
                }

                ShikomiModelDTO dto = this.getModelNoList(totalDOList, customerNo);
                // 把模糊型号替换成查询时的型号
                for (ShikomiInfo shikomiInfo : dto.getInfoList()) {
                    String modelNo = shikomiInfo.getModelNo().get(0);
                    for (Map.Entry<String, String> entry : modelMap.entrySet()) {
                        String rmodelNo = entry.getKey(); //查询的型号
                        if (entry.getValue().equals(modelNo)) {
                            // 模糊型号如果是一样的，就替换成查询型号
                            shikomiInfo.setModelNo(Collections.singletonList(rmodelNo));
                            break;
                        }
                    }
                }
                infoList.addAll(dto.getInfoList());
            }

            // 获取不是shikomi的查询型号条件,缓存进redis
            List<String> caList = noList.parallelStream().filter(str -> !modelMap.containsKey(str)).collect(Collectors.toList());
            if (CollectionUtil.isNotEmpty(caList)) {
                Map<String, List<String>> listMap = caList.parallelStream().collect(Collectors.groupingBy(a -> a + "-" + customerNo));
                for (Map.Entry<String, List<String>> entry : listMap.entrySet()) {
                    redisManager.hPut("ops:shikomi:nomodelno", entry.getKey(), customerNo);
                }
            }
            return ResultVo.success(infoList);
        } catch (Exception e) {
            log.error("门户shikomi查询程序错误:" + e.getMessage());
            return ResultVo.success(Collections.emptyList());
        }
    }

    private ShikomiModelDTO getModelNoList(List<VShikomiTotalDO> list, String customerNo) {
        List<ShikomiInfo> doList = new ArrayList<>();
        ShikomiModelDTO dto = new ShikomiModelDTO();
        ShikomiInfo info;
        // 国内共享 1.中国公司共享 2客户专享可协商借用 3 客户专享
        // 不传客户
        if (PublicUtil.isEmpty(customerNo)) {
            List<String> cusList;
            for (VShikomiTotalDO totalDO : list) {
                cusList = new ArrayList<>();
                if ("3".equals(totalDO.getClassCode())) {
                    Object o = redisManager.hGet("ops:shikomi:customer", totalDO.getShikomiNo());
                    if (o != null) {
                        cusList = JSON.parseArray(o.toString(), String.class);
                    }
                }
                if (PublicUtil.isNotEmpty(totalDO.getCustomerNo())) {
                    cusList.add(totalDO.getCustomerNo());
                }
                dto.getMap().put(totalDO.getModelNo(), totalDO.getShikomiNo());
                info = new ShikomiInfo();
                info.setApplyId(totalDO.getApplyNo());
                info.setCustomerNo(cusList);
                info.setDeptNo(totalDO.getDeptNo());
                info.setOnHandQuantity(totalDO.getRemainQty());
                info.setShikomiId(totalDO.getShikomiNo());
                info.setQuantity(totalDO.getLotQty());
                info.setLockQuantity(Optional.ofNullable(totalDO.getQtyOrdPre()).orElse(0));
                info.setCountryName(totalDO.getCompanyCode());
                info.setModelNo(Collections.singletonList(totalDO.getModelNo()));
                info.setWarnQuantity(totalDO.getQtyWarning());
//                info.setLockQuantity(totalDO.);
                if (PublicUtil.isNotEmpty(totalDO.getClassCode())) {
                    info.setSharedType(ShikomiShareType.getEnum(Integer.parseInt(totalDO.getClassCode())));
                }
                doList.add(info);
            }
        } else { //有客户
            VShikomiTotalDO shikomiTotalDO;
            for (VShikomiTotalDO totalDO : list) {
                shikomiTotalDO = this.getCanUse2(totalDO, customerNo);
                if (shikomiTotalDO != null) {
                    info = new ShikomiInfo();
                    dto.getMap().put(totalDO.getModelNo(), totalDO.getShikomiNo());
                    info.setApplyId(totalDO.getApplyNo());
                    info.setCustomerNo(shikomiTotalDO.getCustomerList());
                    info.setDeptNo(totalDO.getDeptNo());
                    info.setOnHandQuantity(totalDO.getRemainQty());
                    info.setShikomiId(totalDO.getShikomiNo());
                    info.setQuantity(totalDO.getLotQty());
                    info.setLockQuantity(Optional.ofNullable(totalDO.getQtyOrdPre()).orElse(0));
                    info.setCountryName(totalDO.getCompanyCode());
                    info.setModelNo(Collections.singletonList(totalDO.getModelNo()));
                    info.setWarnQuantity(totalDO.getQtyWarning());
                    if (PublicUtil.isNotEmpty(totalDO.getClassCode())) {
                        info.setSharedType(ShikomiShareType.getEnum(Integer.parseInt(totalDO.getClassCode())));
                    }
                    doList.add(info);
                }
            }
        }
        dto.setInfoList(doList);
        return dto;
    }

    private VShikomiTotalDO getCanUse2(VShikomiTotalDO totalDO, String customerNo) {
        //add by WuJiaWen 2022/10/21 bug 8416
        //add by WuJiaWen 2022/10/27 bug 8496
//        int remainQty = Optional.ofNullable(totalDO.getRemainQty()).orElse(0);
//        int lotQty = Optional.ofNullable(totalDO.getLotQty()).orElse(0);
//        if (lotQty < remainQty) {
//            return null;
//        }
        List<String> doList = new ArrayList<>();
        if (PublicUtil.isNotEmpty(totalDO.getCustomerNo())) {
            doList.add(totalDO.getCustomerNo());
            totalDO.setCustomerList(doList);
        }
        /**
         * 1	中国公司共享
         * 2	专享可借用
         * 3	客户专享
         * 0	全球共享
         * 4	国内不可用
         */
        if (ShikomiClassCodeEnum.Global.getCode().equals(totalDO.getClassCode()) || ShikomiClassCodeEnum.China.getCode().equals(totalDO.getClassCode())
                || totalDO.getClassCode() == null) {
            return totalDO;
        }
        if (ShikomiClassCodeEnum.ChinaUNAVAIL.getCode().equals(totalDO.getClassCode())) {
            return null;
        }
        // 2022/12/01 WuJiaWen Bug8828
        if (ShikomiClassCodeEnum.CustomerPrivate.getCode().equals(totalDO.getClassCode()) || ShikomiClassCodeEnum.CustomerShare.getCode().equals(totalDO.getClassCode())) {
            if (customerNo.equalsIgnoreCase(totalDO.getCustomerNo())) {
                return totalDO;
            }
            Object o = redisManager.hGet(Constants.CACHE_SHIKOMI_CUSTOMER, totalDO.getShikomiNo());
            if (o == null) {
                return null;
            }
            List<String> list = JSON.parseArray(o.toString(), String.class);
            doList.addAll(list);
            if (doList.contains(customerNo)) {
                totalDO.setCustomerList(doList);
                return totalDO;
            } else {
                return null;
            }
        }
        return null;
    }

    private VShikomiTotalDO getCanUse(VShikomiTotalDO totalDO, String customerNo) {
        List<String> doList = new ArrayList<>();
        if (PublicUtil.isNotEmpty(totalDO.getCustomerNo())) {
            doList.add(totalDO.getCustomerNo());
        }
        //A中国特定客户,B海外公司特定客户,C所有客户
        ShikomiClassTypeEnum shikomiClassTypeEnum = ShikomiClassTypeEnum.getEnum(totalDO.getClassType());
        if (ShikomiClassTypeEnum.C.equals(shikomiClassTypeEnum)) {
            return totalDO;
        }
        if (ShikomiClassTypeEnum.B.equals(shikomiClassTypeEnum)) {
            if (Constants.SUBSIDIARY_CODE.equalsIgnoreCase(totalDO.getSubsidiaryCode())) {
                return totalDO;
            } else {
                return null;
            }
        } else if (ShikomiClassTypeEnum.A.equals(shikomiClassTypeEnum)) {

            //1.中国公司共享 2客户专享可协商借用 3 客户专享 0.全球共享
            if (totalDO.getClassCode() == null
                    || ShikomiClassCodeEnum.China.getCode().equals(totalDO.getClassCode())
                    || ShikomiClassCodeEnum.Global.getCode().equals(totalDO.getClassCode())) {
                return totalDO;
            }

            if (ShikomiClassCodeEnum.CustomerShare.getCode().equals(totalDO.getClassCode())) {
                return totalDO;
            }

            if (ShikomiClassCodeEnum.CustomerPrivate.getCode().equals(totalDO.getClassCode())) {

                Object o = redisManager.hGet("ops:shikomi:customer", totalDO.getShikomiNo());
                if (o != null) {
                    List<String> list = JSON.parseArray(o.toString(), String.class);
                    doList.addAll(list);
                }
                if (doList.contains(customerNo) || customerNo.equalsIgnoreCase(totalDO.getCustomerNo())) {
                    totalDO.setCustomerList(doList);
                    return totalDO;
                } else {
                    return null;
                }
            }
        }
        return null;
    }

    @Override
    public Map<String, List<Inventory>> getCanPreInventory(InventoryCondition condition) {
        if (StringUtils.isBlank(condition.getDeptNo())) {
            return Collections.emptyMap();
        }
        // 查询营业所可出库仓库优先级列表(仅中心库)
        String deptNo = this.converDeptCode(condition.getDeptNo()); // 营业所代码
        List<String> warehouseList = this.getCentralWarehouseConfig(deptNo);
        if (warehouseList.isEmpty()) {
            return Collections.emptyMap();
        }

        // 查询中心库的可预约库存
        Map<String, List<Inventory>> resultMap = new HashMap<>(condition.getModelNo().size());
        List<Inventory> valueList;
        Map<String, String> nameMap = new HashMap<>();
        List<Inventory> canUseList;
        List<Inventory> preList;
        Map<String, Integer> preMap;
        List<String> temp;
        int i = 0;

        while (i < condition.getModelNo().size()) {
            if (condition.getModelNo().size() < 2001) {
                temp = condition.getModelNo();
            } else {
                if (condition.getModelNo().size() > (i + 2000)) {
                    temp = condition.getModelNo().subList(i, i + 2000);
                } else {
                    temp = condition.getModelNo().subList(i, condition.getModelNo().size());
                }
            }
            i += temp.size();
            // 查询可预占用库存信息
            canUseList = inventoryMapper.listTYCanUseInventory(warehouseList, temp);
            // 查询预占用库存信息
            preList = inventoryMapper.listPreInventory(warehouseList, temp);
            // list --> Map
            preMap = new HashMap<>(preList.size());
            for (Inventory pre : preList) {
                preMap.put(pre.getStockCode() + "_" + pre.getModelNo(), pre.getQtyForecast());
            }

            for (Inventory info : canUseList) {
                if (resultMap.containsKey(info.getModelNo())) {
                    valueList = resultMap.get(info.getModelNo());
                } else {
                    valueList = new ArrayList<>();
                    resultMap.put(info.getModelNo(), valueList);
                }
//                if (info.getStockCode().startsWith("K")) {
//                    info.setCustomerNo(this.getCentralWarehouseCustomerNo(info.getStockCode()));
//                }
                // 计算可用数量
                info.setQuantity(info.getQtyOnHand() - info.getQtyPrePare());
                // 设置预占用库存数
                if (preMap.containsKey(info.getStockCode() + "_" + info.getModelNo())) {
                    info.setQtyForecast(preMap.get(info.getStockCode() + "_" + info.getModelNo()));
                }
                // 设置仓库名称
                if (!nameMap.containsKey(info.getStockCode())) {
                    nameMap.put(info.getStockCode(), opsCommonService.getWarehouseNameByCode(info.getStockCode()));
                }
                info.setStock(nameMap.getOrDefault(info.getStockCode(), ""));
                valueList.add(info);
            }






        }

        // 按出库优先级排序
        for (Map.Entry<String, List<Inventory>> map : resultMap.entrySet()) {
            map.setValue(map.getValue().stream()
                    .sorted(Comparator.comparingInt(o -> warehouseList.indexOf(o.getStockCode())))
                    .collect(Collectors.toList()));
        }
        return resultMap;
    }

    @Override
    public CanTransInventory getCanUseByDeptCustomer(InventoryCondition condition) {
        log.info("门户-在库调库申请-查询可用库存 params = {}", condition);
        // 查询营业所可出库仓库代码列表
        String deptNo = this.converDeptCode(condition.getDeptNo());
        List<String> warehouseList = this.getWarehouseConfig(deptNo);
        log.info("门户-在库调库申请-查询可用库存 {}可出在库仓库 {}", deptNo, warehouseList);
        if (warehouseList.isEmpty()) {
            CanTransInventory result = new CanTransInventory();
            result.setMsg("营业所无可出库仓库");
            return result;
        }

        Map<String, Integer> modelQtyMap = new HashMap<>(condition.getList().size());
        int quantity;
        for (ModelNoQuantity modelQty : condition.getList()) {
            modelQty.setModelno(modelQty.getModelno().toUpperCase().trim());
            quantity = Optional.ofNullable(modelQty.getQuantity()).orElse(0);
            if (modelQtyMap.containsKey(modelQty.getModelno())) {
                quantity += modelQtyMap.get(modelQty.getModelno());
            }
            modelQtyMap.put(modelQty.getModelno(), quantity);
        }
        List<String> modelNoList = new ArrayList<>(modelQtyMap.keySet());

        // 查询客户专备可用库存信息
        List<Inventory> canUseList = inventoryMapper.listGKZYCanUseInventory(warehouseList, modelNoList);
        Map<String, String> nameMap = new HashMap<>();

        // 设置返回值
        Map<String, List<Inventory>> transInventory = new HashMap<>(modelNoList.size());
        List<Inventory> valueList;
        Map<String, StockInfo> stockInfoMap = new HashMap<>();
        StockInfo stockInfo;

        // 找出满足所有查询型号库存的营业所
        Map<String, Set<String>> deptModelMap = new HashMap<>();
        Set<String> modelSet;
        for (Inventory info : canUseList) {
            info.setModelNo(info.getModelNo().toUpperCase().trim());
            // 计算有效数量
            info.setQuantity(info.getQtyOnHand() - info.getQtyPrePare());
            if (modelQtyMap.get(info.getModelNo()) <= info.getQuantity()) {
                if (deptModelMap.containsKey(info.getStockCode())) {
                    modelSet = deptModelMap.get(info.getStockCode());
                } else {
                    modelSet = new HashSet<>();
                    deptModelMap.put(info.getStockCode(), modelSet);
                }
                modelSet.add(info.getModelNo());
            }
        }

        for (Inventory info : canUseList) {
            if (info.getQuantity() == 0) {
                continue;
            }
            modelSet = deptModelMap.get(info.getStockCode());
            if (CollectionUtils.isEmpty(modelSet) || modelSet.size() < modelNoList.size()) {
                // 需要满足所有查询型号库存充足的条件
                continue;
            }

            // 设置营业所名称
            if (!nameMap.containsKey(info.getStockCode())) {
                nameMap.put(info.getStockCode(), opsCommonService.getDeptNameByNo(info.getStockCode()));
            }
            info.setStock(nameMap.getOrDefault(info.getStockCode(), ""));
            // 设置仓库名称
            if (!nameMap.containsKey(info.getWarehouseCode())) {
                nameMap.put(info.getWarehouseCode(), opsCommonService.getWarehouseNameByCode(info.getWarehouseCode()));
            }
            info.setWarehouseName(nameMap.getOrDefault(info.getWarehouseCode(), ""));
            info.setStockCode(info.getStockCode() + "-" + info.getWarehouseCode());
            info.setStock(info.getStock() + "-" + info.getWarehouseName());
            // 设置库存类型名称
            info.setInventoryTypeName(InventoryTypeEnum.getName(info.getInventoryTypeCode()));

            if (!stockInfoMap.containsKey(info.getStockCode())) {
                stockInfo = new StockInfo();
                stockInfo.setStockCode(info.getStockCode());
                stockInfo.setStockName(info.getStock());
                stockInfoMap.put(info.getStockCode(), stockInfo);
            }
            if (transInventory.containsKey(info.getModelNo())) {
                valueList = transInventory.get(info.getModelNo());
            } else {
                valueList = new ArrayList<>();
                transInventory.put(info.getModelNo(), valueList);
            }
            valueList.add(info);
        }
        List<StockInfo> stockInfoList = new ArrayList<>(stockInfoMap.values());

        CanTransInventory canTrans = new CanTransInventory();
        canTrans.setStockList(stockInfoList);
        canTrans.setTransInventory(transInventory);

        return canTrans;
    }

    @Override
    public ResultVo<DeliveryInfo> getDeliveryDay(DeliveryInfo info) {
        // log.info("获取交货期参数: " + JSONObject.toJSONString(info));
        // 根据部门获取可出库列表
        if (StringUtils.isBlank(info.getDeptNo())) {
            return ResultVo.failure("部门代码不可为空！");
        }
        if (StringUtils.isBlank(info.getCustomerNo())) {
            return ResultVo.failure("客户代码不可为空！");
        }
        // 数量控制：每次不可超过500个型号进行获取货期
        if (CollectionUtils.isEmpty(info.getModelList()) || info.getModelList().size() > 500) {
            return ResultVo.failure("型号数量需大于零且小于五百!");
        }
        info.setDeptNo(this.converDeptCode(info.getDeptNo()));
        info = deliveryService.getDeliveryDay(info);
        return ResultVo.success(info);
    }

    @Override
    public ResultVo<DeliveryInfo> getWarehouseSendDate(DeliveryInfo info) {

        log.info("获取物流交货期参数接口 : {}", JSONObject.toJSONString(info));
        if (info == null) {
            return ResultVo.failure("参数不可为空");
        }
        if (StringUtils.isBlank(info.getDeptNo())) {
            return ResultVo.failure("参数中未传输部门代码，请检查参数！");
        }
        if (info.getModelList().isEmpty()) {
            return ResultVo.failure("物流发货期明细不可为空.");
        }

        int dlvDay;
        // 根据部门编号获取运输天数
        Object o = redisManager.get(com.smc.smccloud.core.model.constants.Constants.REDIS_TRANSPORTDAY_DEPTNO + info.getDeptNo());

        if (null == o) {
            ResultVo<Integer> resultVo = commonServiceFeignApi.getTransDayByDeptNo(info.getDeptNo());
            if (resultVo.isSuccess()) {
                dlvDay = resultVo.getData();
            } else {
                dlvDay = 0;
            }
        } else {
            dlvDay = Integer.parseInt(o.toString());
        }

        Date today = DateUtil.getToday();
        Date warehouseSendDate;

        for (DeliveryModelInfo item : info.getModelList()) {
            if (item.getDlvDate() == null) {
                return ResultVo.failure("客户货期不可为空.");
            }
            // 物流货期 - 运输天数 (暂时先这样计算货期)
            warehouseSendDate = DateUtil.addDay(item.getDlvDate(), -dlvDay);
            // 物流货期小于当天 则设置为当天
            if (warehouseSendDate.before(today)) {
                warehouseSendDate = today;
            }
            item.setWarehouseSendDate(warehouseSendDate);
        }
        return ResultVo.success(info);
    }

//     @Override
//    public ResultVo<List<WarehouseSendDateVO>> getWarehouseSendDateByOrderNo(List<WarehouseSendDateVO> info) {
//        if(CollectionUtils.isEmpty(info)) {
//            return ResultVo.failure("入参不可为空");
//        }
//        log.info("反算物流货期参数 {}", JSONArray.toJSONString(info));
//        StringBuilder errMsg = new StringBuilder();
//        for (WarehouseSendDateVO item : info ) {
//            if (StringUtils.isBlank(item.getOrderNo())) {
//                continue;
//            }
//            if(Objects.isNull(item.getDlvDate())) {
//                errMsg.append(item.getOrderNo()).append("客户货期不可为空;");
//                continue;
//            }
//            OrderNoInfo orderNoInfo = new OrderNoInfo().convertFullOrderNo(item.getOrderNo());
//            CommonResult<String> orderMaxWlday = opsWmDispatchForOrderFeignApi.getOrderMaxWlday(orderNoInfo.getOrderNo(),
//                                                                                String.valueOf(orderNoInfo.getItemNo()), item.getDlvDate());
//            log.info("反算物流货期接口返回结果 {}", JSONUtil.toJsonStr(orderMaxWlday));
//            if (!Objects.isNull(orderMaxWlday) && orderMaxWlday.isSuccess()) {
//                String data = orderMaxWlday.getData();
//                item.setWarehouseSendDate(DateUtil.stringToDate(data));
//            } else {
//                errMsg.append(item.getOrderNo()).append(orderMaxWlday.getMessage()).append(";");
//            }
//
//        }
//        if (StringUtils.isNotBlank(errMsg.toString())) {
//            return ResultVo.failure(errMsg.toString());
//        }
//        return ResultVo.success(info);
//    }

    private List<ShikomiInfo> getShikomiInfos(List<VShikomiTotalDO> list) {

        List<ShikomiInfo> infos = new ArrayList<>(list.size());
        ShikomiInfo info;
        ResultVo<List<ShikomiCustomerVO>> resultVo;
        List<String> customerNo;

        for (VShikomiTotalDO totalDO : list) {
            info = new ShikomiInfo();
            info.setApplyId(totalDO.getApplyNo());
            resultVo = shikomiService.findCustomerDataByNo(totalDO.getShikomiNo());
            customerNo = resultVo.getData().stream().map(ShikomiCustomerVO::getCustomerNo).collect(Collectors.toList());
            if (StringUtils.isNotBlank(totalDO.getCustomerNo())) {
                customerNo.add(totalDO.getCustomerNo());
            }
            info.setCustomerNo(customerNo);
            info.setDeptNo(totalDO.getDeptNo());
            info.setOnHandQuantity(totalDO.getRemainQty());
            info.setShikomiId(totalDO.getShikomiNo());
            info.setQuantity(Optional.ofNullable(totalDO.getLotQty()).orElse(0));
            info.setLockQuantity(Optional.ofNullable(totalDO.getQtyOrdPre()).orElse(0));
            info.setCountryName(totalDO.getCompanyCode());
            info.setModelNo(Collections.singletonList(totalDO.getModelNo()));
            info.setWarnQuantity(totalDO.getQtyWarning());
            if (StringUtils.isNotBlank(totalDO.getClassCode())) {
                switch (totalDO.getClassCode()) {
                    case "0":
                        info.setSharedType(ShikomiShareType.Global);
                        break;
                    case "1":
                        info.setSharedType(ShikomiShareType.China);
                        break;
                    case "2":
                        info.setSharedType(ShikomiShareType.CustomerShare);
                        break;
                    case "3":
                        info.setSharedType(ShikomiShareType.CustomerPrivate);
                        break;
                    case "4":
                        info.setSharedType(ShikomiShareType.ChinaNoUse);
                        break;
                    default:
                        break;
                }
            }
            infos.add(info);
        }
        return infos;
    }

    @Override
    public PageInfo<InventoryFuzzySearch> getFuzzyInventory(FuzzySearchCondition condition, Page page) {
        // condition.setModelNo(condition.getModelNo() + "%");
        PageInfo<InventoryFuzzySearch> pageInfo = PageHelper.startPage(page.getPageNumber(), page.getPageSize())
                .doSelectPageInfo(() -> inventoryMapper.listFuzzyInventory(condition));

        if (CollectionUtils.isNotEmpty(pageInfo.getList())) {
            Map<String, String> nameMap = new HashMap<>();
            // nameMap.put("KJP", "日本库存");
            for (InventoryFuzzySearch info : pageInfo.getList()) {
                if (!nameMap.containsKey(info.getStockCode())) {
                    if (StringUtils.isNotBlank(info.getStockCode()) && info.getStockCode().length() < 3) {
                        nameMap.put(info.getStockCode(), opsCommonService.getSupplierNameByCode(info.getStockCode()));
                    } else {
                        nameMap.put(info.getStockCode(), opsCommonService.getWarehouseNameByCode(info.getStockCode()));
                    }
                }
                info.setStockName(nameMap.get(info.getStockCode()));
                info.setStockTypeName(InventoryTypeEnum.getName(info.getStockType()));
            }
        }
        return pageInfo;
    }

    @Override
    public ResultVo<List<ProductBomInventory>> getProductBomByModelNo(String modelNo) {
        List<ProductBomInventory> list = productBomMapper.listProductBomInfo(modelNo);
        if (CollectionUtils.isEmpty(list)) {
            return ResultVo.success(Collections.emptyList(), "无可拆分型号");
        }
        Map<String, List<ProductBomInventory>> bomMap = list.stream().collect(Collectors.groupingBy(ProductBomInventory::getModelNo));

        List<ProductDeliveryDO> modelOrgCountryList = productDeliveryService.listOrgCountryByModelNo(new ArrayList<>(bomMap.keySet()));
        Map<String, List<ProductDeliveryDO>> orgCountryMap = modelOrgCountryList.stream().collect(Collectors.groupingBy(ProductDeliveryDO::getModelNo));
        for (ProductBomInventory info : list) {
            if (orgCountryMap.containsKey(info.getModelNo())) {
                info.setOrgCountry(orgCountryMap.get(info.getModelNo()).get(0).getOrgCountryId());
            }
        }
        return ResultVo.success(list);
    }

//    @Override
//    public ResultVo<List<ProductBomInventory>> getProductBomByModelNo(String modelNo) {
//        List<ProductBomInfo> list = productBomMapper.listProductBomInfo(modelNo);
//        if (CollectionUtils.isEmpty(list)) {
//            return ResultVo.success(Collections.emptyList(), "非拆分型号");
//        }
//
//        List<ProductBomInventory> infoList = BeanCopyUtil.copyList(list, ProductBomInventory.class);
//        Map<String, List<ProductBomInventory>> modelMap = new HashMap<>();
//        List<ProductBomInventory> bomModelList;
//        for (ProductBomInventory bomInfo : infoList) {
//            if (modelMap.containsKey(bomInfo.getModelNo())) {
//                bomModelList = modelMap.get(bomInfo.getModelNo());
//            } else {
//                bomModelList = new ArrayList<>();
//                modelMap.put(bomInfo.getModelNo(), bomModelList);
//            }
//            bomModelList.add(bomInfo);
//        }
//        List<String> modelNoList = new ArrayList<>(modelMap.keySet());
//
//        // 设置拆分型号产地
//        List<ProductDeliveryDO> productDeliveryList = productDeliveryService.listOrgCountryByModelNo(modelNoList);
//        for (ProductDeliveryDO deliveryInfo : productDeliveryList) {
//            if (modelMap.containsKey(deliveryInfo.getModelNo())) {
//                for (ProductBomInventory bomInventory : modelMap.get(deliveryInfo.getModelNo())) {
//                    bomInventory.setOrgCountry(deliveryInfo.getOrgCountryId());
//                }
//            }
//        }
//        // 设置拆分型号可用库存
//        /*QueryWrapper<OpsInventoryDO> inventoryQuery = new QueryWrapper<>();
//        inventoryQuery.select("modelNo", "SUM(quantity - prepare_quantity) as quantity");
//        inventoryQuery.lambda().in(OpsInventoryDO::getModelNo, modelNoList)
//                .eq(OpsInventoryDO::getInventoryStatus, "N")
//                .eq(OpsInventoryDO::getQaStatus, 0)
//                .eq(OpsInventoryDO::getDelFlag, 0)
//                .groupBy(OpsInventoryDO::getModelNo);
//        List<OpsInventoryDO> inventoryList = inventoryMapper.selectList(inventoryQuery);
//        for (OpsInventoryDO inventoryInfo : inventoryList) {
//            if (modelMap.containsKey(inventoryInfo.getModelNo())) {
//                for (ProductBomInventory bomInventory : modelMap.get(inventoryInfo.getModelNo())) {
//                    bomInventory.setCanUseQuantity(inventoryInfo.getQuantity());
//                }
//            }
//        }*/
//
//        return ResultVo.success(infoList);
//    }

    @Override
    public ResultVo<String> testMybatisTimeOut() {
        // LambdaQueryWrapper<OpsInventoryDO> queryWrapper = new LambdaQueryWrapper<>();
        // inventoryMapper.selectList(queryWrapper);
        List<OpsInventoryDO> opsInventoryDOS = inventoryMapper.testMybatisTimeOut();
        log.info("opsInventoryDOS.size() : {}", opsInventoryDOS.size());
        return ResultVo.success("查询成功");
    }

    private void setStockTypeName(List<StockCode> stockCodeList) {
        for (StockCode info : stockCodeList) {
            if (WareHouseTypeEnum.MASTER.getCode().equals(info.getStockType())) {
                info.setStockTypeName(WareHouseTypeEnum.MASTER.getCodeName());
            } else if (WareHouseTypeEnum.SUB.getCode().equals(info.getStockType())) {
                info.setStockTypeName(WareHouseTypeEnum.SUB.getCodeName());
            } else if (WareHouseTypeEnum.WT.getCode().equals(info.getStockType())) {
                info.setStockTypeName(WareHouseTypeEnum.WT.getCodeName());
            }
        }
    }

    /**
     * 获取仓库客户代码
     *
     * @param warehouseCode 仓库代码
     * @return 客户代码
     */
    private String getWarehouseCustomerNo(String warehouseCode, List<String> masterWarehouses) {
        if (warehouseCode.startsWith("W")) {
            return "";
        }
        String classCode = "4013";
        ResultVo<DataTypeVO> resultVo = dictCommonService.getDataTypeCodesInfo(classCode, warehouseCode);
        if (resultVo.isSuccess()) {
            return resultVo.getData().getExtNote1();
        } else {
            if ("KBJ".equals(warehouseCode)) {
                return "C1E7H";
            } else if ("KGZ".equals(warehouseCode)) {
                return "C1E7F";
            } else if ("KSH".equals(warehouseCode)) {
                return "C1E7G";
            } else if ("SCZ".equals(warehouseCode) && masterWarehouses.contains(warehouseCode)) {
                return "C1E7G";
            } else if (warehouseCode.startsWith("S") && !masterWarehouses.contains(warehouseCode)) {
                return "S0000";
            } else {
                return "";
            }
        }
    }

    /**
     * 获取部门可出库仓库
     */
    private List<String> getWarehouseConfig(String deptNo) {
        List<String> warehouseList = warehouseConfigMapper.getWarehouseConfig(deptNo);
        if (CollectionUtils.isEmpty(warehouseList)) {
            return Collections.emptyList();
        }
        return warehouseList;
    }

    private List<String> getCentralWarehouseConfig(String deptNo) {
        List<String> warehouseList = warehouseConfigMapper.getCentralWarehouseConfig(deptNo);
        if (CollectionUtils.isEmpty(warehouseList)) {
            return Collections.emptyList();
        }
        return warehouseList;
    }

    private List<String> getSubWarehouseConfig(String deptNo) {
        List<String> warehouseList = warehouseConfigMapper.getSubWarehouseConfig(deptNo);
        if (CollectionUtils.isEmpty(warehouseList)) {
            return Collections.emptyList();
        }
        return warehouseList;
    }

    /**
     * 转换部门代码
     */
    private String converDeptCode(String deptNo) {
        ResultVo<String> resultVo = opsCommonService.getDeptNoByHRSalesDeptNo(deptNo);
        if (!resultVo.isSuccess()) {
            throw new BusinessException("获取营业所代码失败");
        }
        return resultVo.getData();
    }

    /**
     * @return 物流中心代码
     */
    private List<String> getCentralWarehouseCode() {
        List<String> masterWarehouseCodes = commonService.getMasterWarehouseCodes();
        if (CollectionUtils.isEmpty(masterWarehouseCodes)) {
            throw new BusinessException("获取物流中心仓库代码失败");
        }
        return masterWarehouseCodes;
    }
}
