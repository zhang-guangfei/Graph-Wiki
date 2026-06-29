package com.smc.smccloud.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sales.ops.db.entity.OpsInventoryLog;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.model.enums.CstmTypeEnum;
import com.smc.smccloud.core.model.enums.InventoryTypeEnum;
import com.smc.smccloud.core.model.enums.VoucherTypeEnum;
import com.smc.smccloud.core.redis.RedisManager;
import com.smc.smccloud.core.utils.*;
import com.smc.smccloud.mapper.*;
import com.smc.smccloud.model.DataCodeVO;
import com.smc.smccloud.model.bindata.BindataRequest;
import com.smc.smccloud.model.bindata.BindataVO;
import com.smc.smccloud.model.constants.Constants;
import com.smc.smccloud.model.customer.CustomerVO;
import com.smc.smccloud.model.enums.WareHouseTypeEnum;
import com.smc.smccloud.model.inventory.*;
import com.smc.smccloud.model.order.OrderNoInfo;
import com.smc.smccloud.model.product.CsStockStockTakeParam;
import com.smc.smccloud.service.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.*;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toMap;

@Slf4j
@Service
public class InventoryServiceImpl implements InventoryService {

    @Resource
    private InventoryMapper inventoryMapper;
    @Resource
    private ShikomiTotalMapper shikomiTotalMapper;
    @Resource
    private OpsInventoryLogMapper opsInventoryLogMapper;
    @Resource
    private OpsInventoryLogHistoryMapper opsInventoryLogHistoryMapper;
    @Resource
    private ZeroInventoryMapper zeroInventoryMapper;
    @Resource
    private OpsInventoryPropertyMapper opsInventoryPropertyMapper;
    @Resource
    private WarehouseSalesBranchConfigMapper warehouseConfigMapper;
    @Resource
    private BinServiceFeignApi binServiceFeignApi;
    @Resource
    private CommonService commonService;
    @Resource
    private HttpServletResponse response;
    @Resource
    private RedisManager redisManager;
    @Resource
    private InventorySupplierMapper inventorySupplierMapper;
    @Resource
    private DictDataServiceFeignApi dictDataServiceFeignApi;
    @Resource
    private DictCommonService dictCommonService;

    @Override
    public ResultVo<List<WarehouseStockVO>> listWarehouseStock(String modelNo) {
        List<WarehouseInventoryDO> inventlist = inventoryMapper.listWarehouseInventory(modelNo);
        if (CollectionUtils.isNotEmpty(inventlist)) {
            // 按仓库代码分组
            Map<String, List<WarehouseInventoryDO>> warehouseMap = inventlist.stream()
                    .collect(Collectors.groupingBy(WarehouseInventoryDO::getWarehouseCode));

            List<WarehouseStockVO> list = new ArrayList<>(warehouseMap.size());
            WarehouseStockVO vo;

            for (String keyCode : warehouseMap.keySet()) {
                vo = new WarehouseStockVO(); // 每组新定义
                vo.setWarehouseCode(keyCode);
                vo.setModelNo(modelNo);
                vo.setAvaQty_ty(0);
                vo.setAvaQty_zy(0);
                for (WarehouseInventoryDO info : warehouseMap.get(keyCode)) {
                    if (InventoryTypeEnum.TY.getCode().equalsIgnoreCase(info.getInventoryTypeCode())) {
                        vo.setAvaQty_ty(vo.getAvaQty_ty() + info.getQuantity() - info.getPrepareQty()); // 通用统计
                    } else {
                        vo.setAvaQty_zy(vo.getAvaQty_zy() + info.getQuantity() - info.getPrepareQty()); // 专用统计
                    }
                }
                list.add(vo); // 每组统计出通用和专业数量作为一行数据
            }
            return ResultVo.success(list);
        } else {
            return ResultVo.success(Collections.emptyList());
        }
    }

    @Override
    public ResultVo<List<InventoryVO>> listSpecInventory(InventoryRequestDTO dto) {
        List<InventoryVO> list = inventoryMapper.listSpecInventory(dto);
        if (list.isEmpty()) {
            return ResultVo.success(list, "暂无数据");
        }
        return ResultVo.success(list);
    }
    @Override
    public ResultVo<PageInfo<InventoryVO>> listInventoryByProperty(OpsInventoryPropertyRequestDTO inventoryProperty) {
        PageHelper.startPage(inventoryProperty.getPageNum(), inventoryProperty.getPageSize());
        List<InventoryVO> list = inventoryMapper.listInventoryByProperty(inventoryProperty);
        PageInfo<InventoryVO> pageInfo = PageInfo.of(list);
        return ResultVo.success(pageInfo);
    }

    @Override
    public ResultVo<List<String>> listInventoryModelByProperty(OpsInventoryPropertyRequestDTO inventoryProperty) {
        List<String> list = inventoryMapper.listInventoryModelByProperty(inventoryProperty);
        return ResultVo.success(list);
    }
    @Override
    public ResultVo<List<OpsInventoryVO>> findInventoryListByModelNo(String modelNo) {
        if (StringUtils.isBlank(modelNo)) {
            return ResultVo.failure("型号不可为空.");
        }
        LambdaQueryWrapper<OpsInventoryDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(OpsInventoryDO::getModelNo, modelNo)
                .eq(OpsInventoryDO::getInventoryPropertyId, 1);
        List<OpsInventoryDO> opsInventoryDOS = inventoryMapper.selectList(queryWrapper);
        if (opsInventoryDOS.isEmpty()) {
            return ResultVo.failure("暂无数据.");
        }
        return ResultVo.success(BeanCopyUtil.copyList(opsInventoryDOS, OpsInventoryVO.class));
    }

    @Override
    public ResultVo<PageInfo<OpsInventoryPropertyVO>> listOpsInventoryProperty(OpsInventoryPropertyRequestDTO dto) {
        LambdaQueryWrapper<OpsInventoryPropertyDO> query = this.getOpsInventoryPropertyQuery(dto);
        PageHelper.startPage(dto.getPageNum(), dto.getPageSize());
        List<OpsInventoryPropertyDO> list = opsInventoryPropertyMapper.selectList(query);
        PageInfo<OpsInventoryPropertyDO> pageInfo = PageInfo.of(list);
        PageInfo<OpsInventoryPropertyVO> voPageInfo = BeanCopyUtil.pageDto2Vo(pageInfo, OpsInventoryPropertyVO.class);
        return ResultVo.success(voPageInfo);
    }

    @Override
    public ResultVo<List<Long>>getOpsInventoryPropertyId(OpsInventoryPropertyRequestDTO dto){
        LambdaQueryWrapper<OpsInventoryPropertyDO> query = this.getOpsInventoryPropertyQuery(dto);
        query.eq(OpsInventoryPropertyDO::getDelflag,0)
            .select(OpsInventoryPropertyDO::getInventoryPropertyId);

        List<OpsInventoryPropertyDO> list = opsInventoryPropertyMapper.selectList(query);
        if (PublicUtil.isNotEmpty(list) && list.size() > 0)
        {
            List<Long> ids =  list.stream().map(OpsInventoryPropertyDO::getInventoryPropertyId).distinct().collect(Collectors.toList());
            return ResultVo.success(ids);
        }
        else {
            return ResultVo.failure("没有数据。");
        }

    }

    private LambdaQueryWrapper<OpsInventoryPropertyDO> getOpsInventoryPropertyQuery(OpsInventoryPropertyRequestDTO dto){
        LambdaQueryWrapper<OpsInventoryPropertyDO> query = new LambdaQueryWrapper<>();
        query.eq(PublicUtil.isNotEmpty(dto.getInventoryTypeCode()), OpsInventoryPropertyDO::getInventoryTypeCode, dto.getInventoryTypeCode())
                .eq(PublicUtil.isNotEmpty(dto.getCustomerNo()), OpsInventoryPropertyDO::getCustomerNo, dto.getCustomerNo())
                .eq(PublicUtil.isNotEmpty(dto.getPpl()), OpsInventoryPropertyDO::getPpl, dto.getPpl())
                .eq(PublicUtil.isNotEmpty(dto.getProjectCode()), OpsInventoryPropertyDO::getProjectCode, dto.getProjectCode())
                .eq(PublicUtil.isNotEmpty(dto.getGroupCustomerNo()), OpsInventoryPropertyDO::getGroupCustomerNo, dto.getGroupCustomerNo());
        return query;
    }

    @Override
    public ResultVo<List<Long>> getInventoryIdByPropertyIds(OpsInventoryPropertyRequestDTO inventoryProperty ){
        LambdaQueryWrapper<OpsInventoryDO> query = new LambdaQueryWrapper<>();
        query.in(OpsInventoryDO::getModelNo,inventoryProperty.getModelNos())
                .in(PublicUtil.isNotEmpty(inventoryProperty.getPropertyIds())&&inventoryProperty.getPropertyIds().size()>0,OpsInventoryDO::getInventoryPropertyId,inventoryProperty.getPropertyIds())
                .eq(OpsInventoryDO::getDelFlag,0)
                .select(OpsInventoryDO::getInventoryId);
       List<OpsInventoryDO> list = inventoryMapper.selectList(query) ;
        if (PublicUtil.isNotEmpty(list) && list.size() > 0)
        {
            List<Long> ids =  list.stream().map(OpsInventoryDO::getInventoryId).distinct().collect(Collectors.toList());
            return ResultVo.success(ids);
        }
        else {
            return ResultVo.failure("没有数据。");
        }
    }

    @Override
    public ResultVo<List<OpsInventoryVO>> getOpsInventoryByPropertyIds(OpsInventoryPropertyRequestDTO inventoryProperty) {
        List<OpsInventoryVO> list = inventoryMapper.listInventoryByPropertyIdList( inventoryProperty) ;
       return ResultVo.success(list);
    }
    @Override
    public ResultVo<List<OpsInventoryVO>>  getInventoryMoveByProperty(OpsInventoryPropertyRequestDTO inventoryProperty){
        List<OpsInventoryVO> list = inventoryMapper.getInventoryMoveByProperty(inventoryProperty) ;
        if (PublicUtil.isNotEmpty(list) && list.size()>0) {
            return ResultVo.success(list);
        }else{
            return ResultVo.failure("没有查到在途数据。");
        }
    }

    @Override
    public ResultVo<List<OpsInventoryVO>> getCanUseInventoryByProperty(OpsInventoryPropertyRequestDTO inventoryProperty){
        List<OpsInventoryVO> list = inventoryMapper.getCanUseInventoryByProperty(inventoryProperty) ;
        if (PublicUtil.isNotEmpty(list) && list.size()>0) {
            return ResultVo.success(list);
        }else{
            return ResultVo.failure("没有查到在库数据。");
        }
    }
    @Override
    public ResultVo<Integer> getModelWarehouseStock(ModelWarehouseStockRequest dto) {
        if (StringUtils.isEmpty(dto.getModelNo())) {
            return ResultVo.failure("modelNo不能为空");
        }
        if (StringUtils.isEmpty(dto.getWarehouseCode())) {
            return ResultVo.failure("warehouseCode不能为空");
        }
        if (StringUtils.isEmpty(dto.getInventoryTypeCode())) {
            return ResultVo.failure("inventoryTypeCode不能为空");
        }
       InventoryVO   inventoryVOS = inventoryMapper.getModelWarehouseStock(dto);
        int quantity = 0;
        if (ObjectUtils.isNotEmpty(inventoryVOS) ) {
            quantity =Optional.ofNullable(inventoryVOS .getQuantity()).orElse(0) -Optional.ofNullable(inventoryVOS .getPrepareQuantity()).orElse(0) ;
        }
        return ResultVo.success(quantity);
    }

    @Override
    public ResultVo<Integer> getInvByModel(String modelNo) {
        InventoryVO   inventoryVOS = inventoryMapper.getInvByModel(modelNo);
        int quantity = 0;
        if (ObjectUtils.isNotEmpty(inventoryVOS) ) {
            quantity =Optional.of(inventoryVOS .getQuantity()).orElse(0) -Optional.of(inventoryVOS .getPrepareQuantity()).orElse(0) ;
        }
        return ResultVo.success(quantity);
    }

    @Override
    public ResultVo<OpsInventoryPropertyVO> getOpsInventoryProperty(Long id) {
        OpsInventoryPropertyDO info = opsInventoryPropertyMapper.selectById(id);
        OpsInventoryPropertyVO infoVO = BeanCopyUtil.copy(info, OpsInventoryPropertyVO.class);
        return ResultVo.success(infoVO);
    }

    @Override
    public ResultVo<OpsInventoryPropertyVO> checkInventoryProperty(OpsInventoryPropertyVO vo) {
        if (PublicUtil.isEmpty(vo.getInventoryTypeCode())) {
            return ResultVo.failure("库存分类不能为空");
        }

        LambdaQueryWrapper<OpsInventoryPropertyDO> query = new LambdaQueryWrapper<>();
        query.eq(OpsInventoryPropertyDO::getInventoryTypeCode, vo.getInventoryTypeCode())
                .eq(StringUtils.isNotBlank(vo.getCustomerNo()), OpsInventoryPropertyDO::getCustomerNo, vo.getCustomerNo())
                .eq(StringUtils.isNotBlank(vo.getPpl()), OpsInventoryPropertyDO::getPpl, vo.getPpl())
                .eq(StringUtils.isNotBlank(vo.getProjectCode()), OpsInventoryPropertyDO::getProjectCode, vo.getProjectCode())
                .eq(StringUtils.isNotBlank(vo.getGroupCustomerNo()), OpsInventoryPropertyDO::getGroupCustomerNo, vo.getGroupCustomerNo())
                .eq(StringUtils.isNotBlank(vo.getSalesInfoNo()), OpsInventoryPropertyDO::getSalesInfoNo, vo.getSalesInfoNo())
                .eq(OpsInventoryPropertyDO::getDelflag, 0);
        List<OpsInventoryPropertyDO> doList = opsInventoryPropertyMapper.selectList(query);
        if (doList == null || doList.isEmpty()) {
            return ResultVo.success(null, "不存在该库存");
        }
        OpsInventoryPropertyVO propertyVO = BeanCopyUtil.copy(doList.get(0), OpsInventoryPropertyVO.class);
        return ResultVo.success(propertyVO);
    }

   /* Edit by DengDengHui, 2022-10-20 for bug-8370
   @Override
    public ResultVo<OpsInventoryPropertyVO> checkAndCreateInventoryProperty(OpsInventoryPropertyVO vo) {
        if (PublicUtil.isEmpty(vo.getInventoryTypeCode())) {
            return ResultVo.failure("库存分类不能为空");
        }

        ResultVo<OpsInventoryPropertyVO> checkResult = this.checkInventoryProperty(vo);
        // 如果检查到有数据则直接返回
        if (checkResult.isSuccess() && checkResult.getData() != null) {
            return checkResult;
        }

        // 如果没有,则新增一个
        Date now = new Date();
        LoginUserDTO userDTO = SMCApp.getLoginAuthDto();
        vo.setVersion(1L);
        vo.setDelflag(0);
        vo.setCreTime(now);
        vo.setModifyTime(now);
        vo.setCreator(userDTO.getUserNo());
        vo.setModifier(userDTO.getUserNo());
        OpsInventoryPropertyDO propertyDO = BeanCopyUtil.copy(vo, OpsInventoryPropertyDO.class);
        this.setInventoryPropertyUid(propertyDO);
        int insert = opsInventoryPropertyMapper.insert(propertyDO);
        // 获取新增后的id
        vo.setInventoryPropertyId(propertyDO.getInventoryPropertyId());
        return insert == 1 ? ResultVo.success(vo) : ResultVo.failure("创建失败");
    }

    private void setInventoryPropertyUid(OpsInventoryPropertyDO info) {
        // uid
        String[] array = new String[6];
        array[0] = info.getInventoryTypeCode();
        array[1] = info.getCustomerNo();
        array[2] = info.getGroupCustomerNo();
        array[3] = info.getPpl();
        array[4] = info.getProjectCode();
        array[5] = info.getSalesInfoNo();
        for (int i = 0; i < array.length; i++) {
            if (StringUtils.isBlank(array[i])) {
                array[i] = "NULL";
            }
        }
        info.setUid(StringUtils.join(array, ","));
    }*/

    @Override
    public ResultVo<List<InventorySummaryVO>> listInventorySummaryByPropertyId(InventoryRequestDTO dto) {
        if (CollectionUtils.isEmpty(dto.getModelNos())) {
            return ResultVo.failure("必须输入型号组");
        }
        if (dto.getPropertyId() == null) {
            return ResultVo.failure("必须输入inventoryPropertyId");
        }
        try {
            Map<String, InventorySummaryVO> map = new HashMap<>(dto.getModelNos().size());
            InventorySummaryVO vo;
            for (String modelNo : dto.getModelNos()) {
                vo = new InventorySummaryVO();
                vo.setModelNo(modelNo);
                vo.setPropertyId(dto.getPropertyId());
                map.put(vo.getModelNo(), vo);
            }
            //    <!--add by WuWeiDong 20230516  bug 10730  物流中心BIN -->
            String masterWarehouse = dictCommonService.getMasterWarehouseByCode(dto.getWarehouseCode());//查物流中心代码
            Future<ResultVo<BindataVO[]>> futureBindata = this.futureModelBinData(dto.getModelNos(), 1l, masterWarehouse);//取物流中心的BIN

            Future<List<InventoryVO>> futureInventory = this.futureInventoryByPropertyId(dto.getModelNos(), dto.getPropertyId(), dto.getWarehouseCode());
            Future<List<InventoryOrderingDTO>> futureOrdering = this.futureOrderingInventoryByPropertyId(dto.getModelNos(), dto.getPropertyId(), dto.getWarehouseCode());
            while (!(futureBindata.isDone()&&futureBindata.isDone() && futureOrdering.isDone())) {
                Thread.sleep(100);
            }

            // 查在库数和可用数
            List<InventoryVO> list = futureInventory.get();

            if (PublicUtil.isNotEmpty(list)) {
                for (InventoryVO inventoryVO : list) {
                    vo = map.get(inventoryVO.getModelNo());
                    vo.setQtyOnHand(inventoryVO.getQuantity());
                    vo.setAvaQty(inventoryVO.getQuantity() - inventoryVO.getPrepareQuantity());
                }
            }
            // 查出bin数量和月用量

            ResultVo<BindataVO[]> binDataResult = futureBindata.get();
            if (binDataResult.isSuccess()) {
                if (PublicUtil.isNotEmpty(binDataResult.getData())) {
                    for (BindataVO bindataVo : binDataResult.getData()) {
                        vo = map.get(bindataVo.getModelNo());
                        vo.setBinQty(Optional.ofNullable(bindataVo.getQtyBin()).orElse(0));
                        vo.setMonthAvgQty(Optional.ofNullable(bindataVo.getMean()).orElse(0));
                    }
                }
            }
//        // 查出订货中数量
            List<InventoryOrderingDTO> orderingList = futureOrdering.get();
            if (PublicUtil.isNotEmpty(orderingList)) {
                for (InventoryOrderingDTO orderingDTO : orderingList) {
                    vo = map.get(orderingDTO.getModelNo());
                    vo.setOrdingQty(Optional.ofNullable(orderingDTO.getQuantity()).orElse(0));
                }
            }
            // 计算可用月数: (可用+订货中)/月平均
            List<InventorySummaryVO> summaryVOList = new ArrayList<>(map.values());
            int scale = 2;
            for (InventorySummaryVO summaryVO : summaryVOList) {
                if (summaryVO.getMonthAvgQty() > 0) {
                    summaryVO.setCanUseMonths(new BigDecimal(String.valueOf(summaryVO.getAvaQty() + summaryVO.getOrdingQty()))
                            .divide(new BigDecimal(String.valueOf(summaryVO.getMonthAvgQty())),
                                    scale,
                                    BigDecimal.ROUND_HALF_UP));
                } else {
                    summaryVO.setCanUseMonths(BigDecimal.ZERO);
                }
            }

            return ResultVo.success(summaryVOList);
        } catch (NullPointerException ex) {
            log.error(Thread.currentThread().getName() + "->错误NullPointerException：" + ex);
            return ResultVo.failure(Thread.currentThread().getName() + "->错误：" + ex.getMessage());
        } catch (Exception ex) {
            log.error(Thread.currentThread().getName() + "->错误Exception：" + ex);
            return ResultVo.failure(Thread.currentThread().getName() + "->错误：" + ex.getMessage());
        }
    }

//    @Override
//    public ResultVo<List<InventorySummaryVO>> listInventorySummaryByPropertyId(InventoryRequestDTO dto) {
//        if (CollectionUtils.isEmpty(dto.getModelNos())) {
//            return ResultVo.failure("必须输入型号组");
//        }
//        if (dto.getPropertyId() == null) {
//            return ResultVo.failure("必须输入inventoryPropertyId");
//        }
//        Map<String, InventorySummaryVO> map = new HashMap<>(dto.getModelNos().size());
//        InventorySummaryVO vo;
//        for (String modelNo : dto.getModelNos()) {
//            vo = new InventorySummaryVO();
//            vo.setModelNo(modelNo);
//            vo.setPropertyId(dto.getPropertyId());
//            map.put(vo.getModelNo(), vo);
//        }
//        // 查在库数和可用数
//        List<InventoryVO> list = inventoryMapper.listInventoryByPropertyId(dto.getModelNos(), dto.getPropertyId(), dto.getWarehouseCode());
//        for (InventoryVO inventoryVO : list) {
//            vo = map.get(inventoryVO.getModelNo());
//            vo.setQtyOnHand(inventoryVO.getQuantity());
//            vo.setAvaQty(inventoryVO.getQuantity() - inventoryVO.getPrepareQuantity());
//        }
//        // 查出bin数量和月用量
//        BindataRequest bindataRequest = new BindataRequest();
//        bindataRequest.setPropertyID(dto.getPropertyId());
//        bindataRequest.setModelNos(dto.getModelNos());
//        bindataRequest.setWarehouseCode(dto.getWarehouseCode());
//        ResultVo<BindataVO[]> binDataResult = binServiceFeignApi.listModelBinData(bindataRequest);
//        if (binDataResult.isSuccess()) {
//            for (BindataVO bindataVo : binDataResult.getData()) {
//                vo = map.get(bindataVo.getModelNo());
//                vo.setBinQty(Optional.ofNullable(bindataVo.getQtyBin()).orElse(0));
//                vo.setMonthAvgQty(Optional.ofNullable(bindataVo.getMean()).orElse(0));
//            }
//        }
////        // 查出订货中数量
//        List<InventoryOrderingDTO> orderingList = inventoryMapper.listOrderingInventoryByPropertyId(dto.getModelNos(),
//                dto.getPropertyId(), dto.getWarehouseCode());
//
//        for (InventoryOrderingDTO orderingDTO : orderingList) {
//            vo = map.get(orderingDTO.getModelNo());
//            vo.setOrdingQty(Optional.ofNullable(orderingDTO.getQuantity()).orElse(0));
//        }
//        // 计算可用月数: (可用+订货中)/月平均
//        List<InventorySummaryVO> summaryVOList = new ArrayList<>(map.values());
//        int scale = 2;
//        for (InventorySummaryVO summaryVO : summaryVOList) {
//            if (summaryVO.getMonthAvgQty() > 0) {
//                summaryVO.setCanUseMonths(new BigDecimal(String.valueOf(summaryVO.getAvaQty() + summaryVO.getOrdingQty()))
//                        .divide(new BigDecimal(String.valueOf(summaryVO.getMonthAvgQty())),
//                                scale,
//                                BigDecimal.ROUND_HALF_UP));
//            } else {
//                summaryVO.setCanUseMonths(BigDecimal.ZERO);
//            }
//        }
//
//        return ResultVo.success(summaryVOList);
//    }


    /**
     * 查在库数和可用数
     *
     * @param modelNos
     * @param propertyId
     * @param warehouseCode
     * @return
     */
    private Future<List<InventoryVO>> futureInventoryByPropertyId(List<String> modelNos, long propertyId, String warehouseCode) {
        List<InventoryVO> list = inventoryMapper.listInventoryByPropertyId(modelNos, propertyId, warehouseCode);
        return new AsyncResult<List<InventoryVO>>(list);
    }

    /**
     * 查出bin数量和月用量
     *
     * @param modelNos
     * @param propertyId
     * @param warehouseCode
     * @return
     */
    private Future<ResultVo<BindataVO[]>> futureModelBinData(List<String> modelNos, long propertyId, String warehouseCode) {
        BindataRequest bindataRequest = new BindataRequest();
        bindataRequest.setPropertyID(propertyId);
        bindataRequest.setModelNos(modelNos);
        bindataRequest.setWarehouseCode(warehouseCode);
        ResultVo<BindataVO[]> binDataResult = binServiceFeignApi.listModelBinData(bindataRequest);

        return new AsyncResult<ResultVo<BindataVO[]>>(binDataResult);
    }

    /**
     * 查出订货中数量
     *
     * @param modelNos
     * @param propertyId
     * @param warehouseCode
     * @return
     */
    private Future<List<InventoryOrderingDTO>> futureOrderingInventoryByPropertyId(List<String> modelNos, long propertyId, String warehouseCode) {
        // 查出订货中数量
        List<InventoryOrderingDTO> orderingList = inventoryMapper.listOrderingInventoryByPropertyId(modelNos,
                propertyId, warehouseCode);
        return new AsyncResult<List<InventoryOrderingDTO>>(orderingList);
    }

    @Override
    public ResultVo<String> autoUpdateStock() {
        try {
            inventoryMapper.autoUpdateStock();
        } catch (Exception e) {
            log.info("执行autoUpdateStock存储过程出错,{}", e.getMessage(), e);
        }
        return ResultVo.success();
    }

    @Override
    public ResultVo<List<ModelWarehouseStockVO>> listModelWarehouseStock(ModelWarehouseStockRequest dto) {
        if (CollectionUtils.isEmpty(dto.getModelNos())) {
            return ResultVo.failure("型号不能为空");
        }
        Map<String, ModelWarehouseStockVO> voMap = new HashMap<>(dto.getModelNos().size());
        StringBuilder key = new StringBuilder(70);
        ModelWarehouseStockVO stockVO;

        // 查询在库可用数量(通用在库, 专备在库)
        List<ModelWarehouseStockDTO> voList = inventoryMapper.listAvaQuantityByModelAndWarehouse(dto);
        for (ModelWarehouseStockDTO stockDTO : voList) {
            key.append(stockDTO.getWarehouseCode()).append(stockDTO.getModelNo());
            if (voMap.containsKey(key.toString())) {
                stockVO = voMap.get(key.toString());
            } else {
                stockVO = new ModelWarehouseStockVO();
                stockVO.setModelNo(stockDTO.getModelNo());
                stockVO.setWarehouseCode(stockDTO.getWarehouseCode());
                voMap.put(key.toString(), stockVO);
            }
            if (InventoryTypeEnum.TY.getCode().equals(stockDTO.getInventoryTypeCode())) { // 通用在库
                stockVO.setAvaQty_ty(stockVO.getAvaQty_ty() + stockDTO.getAvaQty());
            } else { // 专备在库
                stockVO.setAvaQty_zy(stockVO.getAvaQty_zy() + stockDTO.getAvaQty());
            }
        }

        // 查询在途数量
        List<InventoryOrderingDTO> orderingDTOList = inventoryMapper.listOrderingInventoryByWarehouse(dto);
        for (InventoryOrderingDTO orderingDTO : orderingDTOList) {
            key.append(orderingDTO.getWarehouseCode()).append(orderingDTO.getModelNo());
            if (voMap.containsKey(key.toString())) {
                stockVO = voMap.get(key.toString());
                stockVO.setOrderingQty(stockVO.getOrderingQty() + orderingDTO.getQuantity());
            } else {
                stockVO = new ModelWarehouseStockVO();
                stockVO.setModelNo(orderingDTO.getModelNo());
                stockVO.setWarehouseCode(orderingDTO.getWarehouseCode());
                stockVO.setOrderingQty(orderingDTO.getQuantity());
                voMap.put(key.toString(), stockVO);
            }
        }
        return ResultVo.success(new ArrayList<>(voMap.values()));
    }

    @Override
    public ResultVo<List<SpecStockVO>> listCustomerSpecStock(ModelWarehouseStockRequest dto) {
        if (CollectionUtils.isEmpty(dto.getModelNos())) {
            return ResultVo.failure("型号不能为空");
        }
        List<SpecStockVO> voList = inventoryMapper.listCustomerSpecStock(dto);
        return ResultVo.success(voList);
    }

    @Override
    public ResultVo<List<InventoryVO>> listCanUseInventory(ModelWarehouseStockRequest dto) {
        if (CollectionUtils.isEmpty(dto.getModelNos())) {
            return ResultVo.failure("型号列表不能为空");
        }
        List<InventoryVO> stockDTOList = inventoryMapper.listCanUseInventory(dto);
        return ResultVo.success(stockDTOList);
    }

    @Override
    public ResultVo<List<WarehouseInventoryVO>> getLogisticWarehouseCanUseStock(List<String> modelNos) {
        List<WarehouseInventoryVO> list = inventoryMapper.getLogisticWarehouseCanUseStock(modelNos);
        return ResultVo.success(list);
    }

    @Override
    public ResultVo<List<WarehouseInventoryVO>> getWarehouseCanUseStock(List<String> warehouseCodes, List<String> modelNos) {
        log.info("getWarehouseCanUseStock warehouseCodes = {}", warehouseCodes);

        try {
            List<WarehouseInventoryVO>  list = inventoryMapper.getWarehouseCanUseStock(warehouseCodes, modelNos);
            return ResultVo.success(list);
        } catch (Exception e) {
            log.error("getWarehouseCanUseStock params: warehouseCodes = {}, modelNos = {}", warehouseCodes, modelNos);
            log.error("getWarehouseCanUseStock error: {}", e.getMessage(), e);
            throw e;
        }
    }


    @Override
    public ResultVo<List<OpsInventoryVO>> findInventQtyByModelNo(String warehouseCode, String modelNo) {
        LambdaQueryWrapper<OpsInventoryDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotBlank(warehouseCode), OpsInventoryDO::getWarehouseCode, warehouseCode)
                .eq(StringUtils.isNotBlank(modelNo), OpsInventoryDO::getModelNo, modelNo);
        List<OpsInventoryDO> opsInventoryDOS = inventoryMapper.selectList(queryWrapper);
        if (opsInventoryDOS.isEmpty()) {
            return ResultVo.failure("暂无数据");
        }
        return ResultVo.success(BeanCopyUtil.copyList(opsInventoryDOS, OpsInventoryVO.class));
    }

    @Override
    public ResultVo<PageInfo<InventoryDetailDTO>> listCsStockStockTake(CsStockStockTakeParam param) {
        if (param == null || StringUtils.isBlank(param.getWarehouseCode()) || StringUtils.isBlank(param.getCustomerNo())) {
            return ResultVo.failure("库房参数/客户代码 不可为空");
        }
//        if (null != param.getDataAuthoritySearchCondition() && null != param.getDataAuthoritySearchCondition().getCustomerCodeListByDataAuthority()
//                && param.getDataAuthoritySearchCondition().getCustomerCodeListByDataAuthority().size() > 0)
//        {
//            List<String> list = new ArrayList<>();
//            for (String item : param.getDataAuthoritySearchCondition().getCustomerCodeListByDataAuthority()) {
//                list.add("W"+item);
//            }
//            param.getDataAuthoritySearchCondition().setCustomerCodeListByDataAuthority(list);
//        }
        log.error("盘点接口参数 : {}", JSONObject.toJSONString(param));
        PageInfo<InventoryDetailDTO> pageInfo = PageHelper.startPage(param.getPage().getPageNumber(), param.getPage().getPageSize()).doSelectPageInfo(
                () -> inventoryMapper.listCsStockStockTake(param)
        );

        List<InventoryDetailDTO> list = pageInfo.getList();
        if (!list.isEmpty()) {
            Map<String, CustomerVO> map = new HashMap<>();
            CustomerVO customer;
            for (InventoryDetailDTO item : list) {
                if (StringUtils.isNotBlank(item.getCustomerNo())) {
                    String custNo = item.getCustomerNo().substring(1);
                    item.setCustomerNo(custNo);
                    // 客户名称
                    if (map.containsKey(item.getCustomerNo())) {
                        item.setCustomerName(map.get(item.getCustomerNo()).getName());
                        item.setCustomerTypeName(CstmTypeEnum.getTypeName(Optional.ofNullable(map.get(item.getCustomerNo()).getCustomerType()).orElse("")));
                    } else {
                        customer = commonService.getCustomerInfoByNo(item.getCustomerNo());
                        if (customer != null) {
                            if (StringUtils.isNotBlank(customer.getName())) {
                                item.setCustomerName(customer.getName());
                                // 客户类型
                                item.setCustomerTypeName(CstmTypeEnum.getTypeName(Optional.ofNullable(customer.getCustomerType()).orElse("")));
                                map.put(item.getCustomerNo(), customer);
                            }
                        }
                    }
                }
            }
        }

        return ResultVo.success(pageInfo);
    }

    @Override
    public ResultVo<List<InventoryForShikomiApplyVO>> listInvnetoryForShikomi(String[] modelNos) {
        if (PublicUtil.isEmpty(modelNos)) {
            return ResultVo.failure("型号不能为空");
        }
        InventoryForShikomiApplyVO applyVO;
        List<InventoryForShikomiApplyVO> list = new ArrayList<>();

        for (String modelNo : modelNos) {
            applyVO = new InventoryForShikomiApplyVO();
            // 查在库
            List<InventoryTypeDTO> stock = inventoryMapper.listInventoryForShikomiOnStock(modelNo);
            // 查在途
            List<InventoryTypeDTO> hand = inventoryMapper.listInventoryForShikomiOnHand(modelNo);
            // 查剩余数量
            Integer remainQty = shikomiTotalMapper.listRemainQty(modelNo);
            // 查顾客户备在库
            Integer stockQty = inventoryMapper.listCustomerOnStockQty(modelNo);
            // 查顾客户备在途
            Integer handQty = inventoryMapper.listCustomerOnHandQty(modelNo);

            for (InventoryTypeDTO dto : stock) {
                if (WareHouseTypeEnum.MASTER.getCode().equals(dto.getWarseType())) {
                    applyVO.setQtyOfKStock(dto.getQty());
                }
                if (WareHouseTypeEnum.SUB.getCode().equals(dto.getWarseType())) {
                    applyVO.setQtyOfSStock(dto.getQty());
                }
                if (WareHouseTypeEnum.WT.getCode().equals(dto.getWarseType())) {
                    applyVO.setQtyOfWStock(dto.getQty());
                }
            }

            for (InventoryTypeDTO dto : hand) {
                if (WareHouseTypeEnum.MASTER.getCode().equals(dto.getWarseType())) {
                    applyVO.setOrdQtyOfKStock(dto.getQty());
                }
                if (WareHouseTypeEnum.SUB.getCode().equals(dto.getWarseType())) {
                    applyVO.setOrdQtyOfSStock(dto.getQty());
                }
                if (WareHouseTypeEnum.WT.getCode().equals(dto.getWarseType())) {
                    applyVO.setOrdQtyOfWStock(dto.getQty());
                }
            }
            applyVO.setQtyOfShikomi(remainQty);
            applyVO.setModelNo(modelNo);
            applyVO.setQtyOfCustomerStock(stockQty);
            applyVO.setOrdQtyOfCustomerStock(handQty);

            list.add(applyVO);
        }

        return ResultVo.success(list);
    }

    @Override
    public ResultVo<List<CustomerModelStockVO>> listCustomerBinModelInventory(InventoryRequestDTO dto) {
        if (CollectionUtils.isEmpty(dto.getModelNos()) || StringUtils.isBlank(dto.getCustomerNo())) {
            return ResultVo.failure("型号列表和客户代码都不能为空");
        }
        CustomerVO customerInfo = commonService.getCustomerInfoByNo(dto.getCustomerNo());
        dto.setWarehouseCodes(this.getWarehouseConfig(customerInfo.getHRUnitId()));
        log.info("listCustomerBinModelInventory params = {}", dto);

        List<InventoryVO> list = inventoryMapper.listCustomerBinModelInventory(dto);
        Map<String, CustomerModelStockVO> map = new HashMap<>(list.size());
        StringBuilder key = new StringBuilder(70);
        CustomerModelStockVO info;

        for (InventoryVO vo : list) {
            key.setLength(0);
            key.append(vo.getInventoryTypeCode()).append(vo.getModelNo());
            if (map.containsKey(key.toString())) {
                info = map.get(key.toString());
            } else {
                info = new CustomerModelStockVO();
                info.setModelNo(vo.getModelNo());
                info.setCustomerNo(vo.getCustomerNo());
                info.setInventoryTypeCode(vo.getInventoryTypeCode());
                info.setTyAvaQty(0);
                info.setZyAvaQty(0);
                map.put(key.toString(), info);
            }
            if (InventoryTypeEnum.TY.getCode().equals(vo.getInventoryTypeCode())) {
                info.setTyAvaQty(info.getTyAvaQty() + (vo.getQuantity() - vo.getPrepareQuantity())); // 计算通用库存
            } else {
                info.setZyAvaQty(info.getZyAvaQty() + (vo.getQuantity() - vo.getPrepareQuantity()));// 计算客户在库库存
            }
            if (InventoryTypeEnum.GK_PPL.getCode().equals(vo.getInventoryTypeCode())) {
                info.setPpl(vo.getPpl());
            }
            if (InventoryTypeEnum.GK_PJ.getCode().equals(vo.getInventoryTypeCode())) {
                info.setProjectCode(vo.getProjectCode());
            }
        }
        // 查询在途数
        List<InventoryOrderingDTO> inOrderingStockList = inventoryMapper.listModelInOrderingInventory(dto);
        for (InventoryOrderingDTO orderingDTO : inOrderingStockList) {
            key.setLength(0);
            key.append(orderingDTO.getInventoryTypeCode()).append(orderingDTO.getModelNo());
            info = map.get(key.toString());
            if (info == null) {
                info = new CustomerModelStockVO();
                info.setModelNo(orderingDTO.getModelNo());
                info.setCustomerNo(orderingDTO.getCustomerNo());
                info.setInventoryTypeCode(orderingDTO.getInventoryTypeCode());
                info.setTyAvaQty(0);
                info.setZyAvaQty(0);
                map.put(key.toString(), info);
            }
            info.setOrderingQty(orderingDTO.getQuantity() - orderingDTO.getPrepareQuantity());
        }

        return ResultVo.success(new ArrayList<>(map.values()));
    }

    @Override
    public ResultVo<PageInfo<OpsInventoryLogVO>> listInventoryLogData(InventoryLogRequstDTO dto) {
        String orderNo = "";
        String itemNo = "";
        if (PublicUtil.isNotEmpty(dto.getOrderNo())) {
            String[] split = dto.getOrderNo().split("-");
            if (dto.getOrderNo().startsWith("YZ")) {
                if (OrderNoInfo.showCountByStr(dto.getOrderNo()) == 2) {
                    orderNo = split[0] + "-" + split[1];
                    itemNo = split[2];
                } else {
                    orderNo = dto.getOrderNo();
                }
            } else {
                if (dto.getOrderNo().contains("-")) {
                    orderNo = split[0];
                    itemNo = split[1];
                } else {
                    orderNo = dto.getOrderNo();
                }
            }
            dto.setOrderNo(orderNo);
            dto.setItemNo(itemNo);
        }

        if (StringUtils.isNotBlank(dto.getCrtTimeStartStr()) && StringUtils.isBlank(dto.getCrtTimeEndStr())) {
            return ResultVo.failure("请选择完整的操作时间区间");
        }

        if (StringUtils.isNotBlank(dto.getCrtTimeEndStr()) && StringUtils.isBlank(dto.getCrtTimeStartStr())) {
            return ResultVo.failure("请选择完整的操作时间区间");
        }

        dto.setProperty(getSortRileFiel(dto.getProperty()));
        PageInfo<OpsInventoryLogVO> pageInfo = null;
        pageInfo = getOpsInventoryLogVOPageInfo(dto);
        return ResultVo.success(pageInfo);
    }

    private PageInfo<OpsInventoryLogVO> getOpsInventoryLogVOPageInfo(InventoryLogRequstDTO dto) {
        PageInfo<OpsInventoryLogVO> pageInfo;
        if(dto.getCheckedHistoryData()==null || !dto.getCheckedHistoryData()){
            //1.联查ops_inventory_log表和property表
            pageInfo = PageHelper.startPage(dto.getPageNum(), dto.getPageSize())
                    .doSelectPageInfo(() -> opsInventoryLogMapper.selectInventoryLogList(dto));
        } else {
            //2.单查已归档的ops_inventory_log表
            pageInfo = PageHelper.startPage(dto.getPageNum(), dto.getPageSize())
                    .doSelectPageInfo(() -> opsInventoryLogHistoryMapper.listInventoryLog(dto));
            //循环查询property表
            for (OpsInventoryLogVO opsInventoryLogVO : pageInfo.getList()) {
                if(opsInventoryLogVO.getPropertyId()!=null){
                    ResultVo<OpsInventoryPropertyVO> propertyResultVO = getOpsInventoryProperty(opsInventoryLogVO.getPropertyId());
                    if (propertyResultVO.isSuccess()) {
                        OpsInventoryPropertyVO data = propertyResultVO.getData();
                        if(data!=null){
                            opsInventoryLogVO.setInventoryTypeCode(data.getInventoryTypeCode());
                            opsInventoryLogVO.setCustomerNo(data.getCustomerNo());
                            opsInventoryLogVO.setPpl(data.getPpl());
                            opsInventoryLogVO.setGroupCustomerNo(data.getGroupCustomerNo());
                            opsInventoryLogVO.setSalesInfoNo(data.getSalesInfoNo());
                            if (data.getVersion() != null) {
                                opsInventoryLogVO.setVersion(data.getVersion().intValue());
                            }
                            if (data.getDelflag() != null) {
                                opsInventoryLogVO.setDelflag(data.getDelflag());
                            }
                            if (StringUtils.isNotBlank(data.getModifier())) {
                                opsInventoryLogVO.setModifier(data.getModifier());
                            }
                            if (data.getModifyTime() != null) {
                                opsInventoryLogVO.setModifyTime(data.getModifyTime());
                            }
                        }
                    }
                }
            }
        }
        return pageInfo;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public ResultVo<PageInfo<OpsInventoryLogVO>> listInventoryLog(InventoryLogRequstDTO dto) {
        PageInfo<OpsInventoryLogVO> pageInfo = null;
        if (dto.getCheckedHistoryData() != null && dto.getCheckedHistoryData()) {
            pageInfo = PageHelper.startPage(dto.getPageNum(), dto.getPageSize())
                    .doSelectPageInfo(() -> opsInventoryLogMapper.listInventoryLog(dto));
        } else {
            pageInfo = PageHelper.startPage(dto.getPageNum(), dto.getPageSize())
                    .doSelectPageInfo(() -> opsInventoryLogHistoryMapper.listInventoryLog(dto));
        }
        return ResultVo.success(pageInfo);
    }
    // inventory_id
    public String getSortRileFiel(String prop) {
        String fiel = "";
        if (StringUtils.isBlank(prop)) {
            fiel = "inventory_id";
            return fiel;
        }
        switch (prop) {
            case "inventoryId":
                fiel = "inventory_id";
                break;
            case "creTime":
                fiel = "cre_time";
                break;
            default:
                fiel = "cre_time";
                break;
        }
        return fiel;
    }

    @Override
    public void exportInventoryLogData(InventoryLogRequstDTO dto) {
        if (PublicUtil.isNotEmpty(dto.getOrderNo())) {
//            String[] split = dto.getOrderNo().split("-");
//            if (dto.getOrderNo().startsWith("YZ")) {
//                if (OrderNoInfo.showCountByStr(dto.getOrderNo()) == 2) {
//                    orderNo = split[0] + "-" + split[1];
//                    itemNo = split[2];
//                } else {
//                    orderNo = dto.getOrderNo();
//                }
//            } else {
//                if (dto.getOrderNo().contains("-")) {
//                    orderNo = split[0];
//                    itemNo = split[1];
//                } else {
//                    orderNo = dto.getOrderNo();
//                }
//            }
            OrderNoInfo info = new OrderNoInfo().convertFullOrderNo(dto.getOrderNo());
            dto.setOrderNo(info.getOrderNo());
            dto.setItemNo(info.getItemNo() == null ? "" : String.valueOf(info.getItemNo()));
        }
//        if (dto.getCrtTimeEnd() != null) {
//            dto.setCrtTimeEnd(DateUtil.getEndTime(dto.getCrtTimeEnd()));
//        }
        if (StringUtils.isNotBlank(dto.getCrtTimeStartStr()) && StringUtils.isBlank(dto.getCrtTimeEndStr())) {
            return;
        }

        if (StringUtils.isNotBlank(dto.getCrtTimeEndStr()) && StringUtils.isBlank(dto.getCrtTimeStartStr())) {
            return;
        }

//        String path = "templates/出入库记录.csv";
        OutputStream os = null;
        try {
//            File file = new File(path);
            StringBuffer buf = new StringBuffer();
            int pageNum = 1;
            int paegSize = 20000;
            Map<Long, Object> detailIds = null; // 动态数据导出去重
            String delimiter = ",";
            String separator = System.lineSeparator();
            List<String> list = Arrays.asList("出入库", "型号", "仓库代码", "变更数量", "指令好", "指令类型", "发票号", "原单号", "原项号", "客户代码", "PPL号", "项目号", "客户群代码", "营业情报号", "库存id", "库存分类", "操作时间", "创建人");
            for (String s : list) {
                buf.append(s).append(delimiter);
            }
            buf.append(separator);

            response.resetBuffer();
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("出入库记录.csv", "UTF-8"));
            os = response.getOutputStream();
            long lstart = System.currentTimeMillis();
            log.info("出入库记录导出开始==> ");
            while (true) {
                dto.setPageNum(pageNum);
                dto.setPageSize(paegSize);
                PageInfo<OpsInventoryLogVO> pageInfo = getOpsInventoryLogVOPageInfo(dto);
                List<OpsInventoryLogVO> voList = pageInfo.getList();
                if (detailIds == null) {
                    detailIds = new HashMap<>((int) pageInfo.getTotal());
                }
                for (OpsInventoryLogVO logVO : voList) {
                    if (detailIds.containsKey(logVO.getInventoryLogId())) {
                        continue;
                    }
                    buf.append(logVO.getType() == 0 ? "出库" : "入库");
                    buf.append(delimiter);
                    buf.append(logVO.getModelno());
                    buf.append(delimiter);
                    buf.append(logVO.getWarehouseCode());
                    buf.append(delimiter);
                    buf.append(logVO.getQuantity());
                    buf.append(delimiter);
                    buf.append(logVO.getVoucherCode());
                    buf.append(delimiter);
                    buf.append(VoucherTypeEnum.getName(logVO.getVoucherType()));
                    buf.append(delimiter);
                    buf.append(Optional.ofNullable(logVO.getInvoiceNo()).orElse(""));
                    buf.append(delimiter);
                    buf.append(Optional.ofNullable(logVO.getOrderNo()).orElse(""));
                    buf.append(delimiter);
                    buf.append(logVO.getItemNo());
                    buf.append(delimiter);
                    buf.append(Optional.ofNullable(logVO.getCustomerNo()).orElse(""));
                    buf.append(delimiter);
                    buf.append(Optional.ofNullable(logVO.getPpl()).orElse(""));
                    buf.append(delimiter);
                    buf.append(Optional.ofNullable(logVO.getProjectCode()).orElse(""));
                    buf.append(delimiter);
                    buf.append(Optional.ofNullable(logVO.getGroupCustomerNo()).orElse(""));
                    buf.append(delimiter);
                    buf.append(Optional.ofNullable(logVO.getSalesInfoNo()).orElse(""));
                    buf.append(delimiter);
                    buf.append(logVO.getInventoryId() == null ? "" : String.valueOf(logVO.getInventoryId()));
                    buf.append(delimiter);
                    buf.append(Optional.ofNullable(logVO.getInventoryTypeCode()).orElse(""));
                    buf.append(delimiter);
                    buf.append(DateUtil.dateToDateString(logVO.getCreTime()));
                    buf.append(delimiter);
                    buf.append(logVO.getCreator());
                    buf.append(separator);
                }
                if (pageNum == 25) {
                    break;
                }
                if (pageInfo.isIsLastPage()) {
                    break;
                }
                pageNum += 1;
            }
            log.info("出入库记录导出结束 耗时:{} s ",(lstart - System.currentTimeMillis()) / 1000);
            //添加bom，不加Excel打开中文会乱码
            os.write(new byte[]{(byte) 0xEF, (byte) 0xBB, (byte) 0xBF});
            os.write(buf.toString().getBytes("UTF-8"));

        } catch (Exception e) {
            log.error("出入库记录导出失败. {}", e.getMessage(), e);
        } finally {
            if (os != null) {
                try {
                    os.flush();
                    os.close();
                } catch (IOException e) {
                    log.error("输出流关闭失败: {}", e.getMessage(), e);
                }
            }
        }


//        ExcelUtil excel = new ExcelUtil(path);
//        excel.openSheet(0);
//
//        // 向模板中写入数据
//        int row = 1;
//        int cel;
//        for (OpsInventoryLogVO logVO : voList) {
//            cel = 0;
//            excel.setCellValue(row, cel++, logVO.getType() == 0 ? "出库" : "入库");
//            excel.setCellValue(row, cel++, logVO.getModelno());
//            excel.setCellValue(row, cel++, logVO.getWarehouseCode());
//            excel.setCellValue(row, cel++, logVO.getQuantity());
//            excel.setCellValue(row, cel++, logVO.getVoucherCode());
//            excel.setCellValue(row, cel++, VoucherTypeEnum.getName(logVO.getVoucherType()));
//            excel.setCellValue(row, cel++, logVO.getInvoiceNo());
//            excel.setCellValue(row, cel++, logVO.getOrderNo());
//            excel.setCellValue(row, cel++, logVO.getItemNo());
//            excel.setCellValue(row, cel++, logVO.getCustomerNo());
//            excel.setCellValue(row, cel++, logVO.getPpl());
//            excel.setCellValue(row, cel++, logVO.getProjectCode());
//            excel.setCellValue(row, cel++, logVO.getGroupCustomerNo());
//            excel.setCellValue(row, cel++, logVO.getSalesInfoNo());
//            excel.setCellValue(row, cel++, logVO.getInventoryId());
//            excel.setCellValue(row, cel++, logVO.getInventoryTypeCode());
//            excel.setCellValue(row, cel++, logVO.getCreTime());
//            excel.setCellValue(row, cel, logVO.getCreator());
//            row++;
//        }
//        excel.writeToResponse(response, "出入库记录.xlsx");
    }

    @Override
    public void updateZeroInventory() {
        zeroInventoryMapper.updateZeroInventory();
        ResultVo<String> resultVo = this.syncZeroInventory(DateUtil.getDate(DateUtil.addDay(new Date(), -1)));
        if (resultVo.isSuccess()) {
            zeroInventoryMapper.updateDays(DateUtil.getDate(new Date()));
        }
    }

    /**
     * 修正数据用
     * bugid: 16792 c14717 2025-02-17
     */
    @Override
    public void updateZeroInventoryVTest() {

        ResultVo<String> resultVo = this.syncZeroInventoryVTest(DateUtil.stringToDate("2025-02-15"));
        if (resultVo.isSuccess()) {
            zeroInventoryMapper.updateDays(DateUtil.stringToDate("2025-02-16"));
        }
    }

    @Override
    public ResultVo<PageInfo<ZeroInventoryVO>> listZeroInventoryData(ZeroInventoryDTO dto) {
        LambdaQueryWrapper<ZeroInventoryDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PublicUtil.isNotEmpty(dto.getModelNo()), ZeroInventoryDO::getModelNo, dto.getModelNo())
                .eq(PublicUtil.isNotEmpty(dto.getWarehouseCode()), ZeroInventoryDO::getWarehouseCode, dto.getWarehouseCode());

        if (dto.getDayType() == 1) {
            queryWrapper.eq(PublicUtil.isNotEmpty(dto.getDays()), ZeroInventoryDO::getDays, dto.getDays());
        }
        if (dto.getDayType() == 2) {
            queryWrapper.gt(PublicUtil.isNotEmpty(dto.getDays()), ZeroInventoryDO::getDays, dto.getDays());
        }
        if (dto.getDayType() == 3) {
            queryWrapper.lt(PublicUtil.isNotEmpty(dto.getDays()), ZeroInventoryDO::getDays, dto.getDays());
        }


        if (PublicUtil.isNotEmpty(dto.getDateType()) && PublicUtil.isNotEmpty(dto.getStratTime())) {
            if (dto.getDateType() == 1) {
                queryWrapper.between(ZeroInventoryDO::getFromDate, dto.getStratTime(), dto.getEndTime());
            }
            if (dto.getDateType() == 2) {
                queryWrapper.between(ZeroInventoryDO::getCalcDate, dto.getStratTime(), dto.getEndTime());
            }
        }
        if (dto.getStockType()) {
            queryWrapper.lt(ZeroInventoryDO::getStockQty, 0);
        }
        queryWrapper.orderByAsc(ZeroInventoryDO::getFromDate);
        PageInfo<ZeroInventoryDO> pageInfo = PageHelper.startPage(dto.getPageNum(), dto.getPageSize())
                .doSelectPageInfo(() -> zeroInventoryMapper.selectList(queryWrapper));
        PageInfo<ZeroInventoryVO> voPageInfo = BeanCopyUtil.pageDto2Vo(pageInfo, ZeroInventoryVO.class);
//        PageInfo<ZeroInventoryVO> voPageInfo;
//        Map<String, Date> map = new HashMap<>();
//        Map<String, Date> dateMap = new HashMap<>();
//        List<ZeroInventoryVO> subList;
//        if (PublicUtil.isNotEmpty(dto.getDateType()) && PublicUtil.isNotEmpty(dto.getStratTime())) {
////            Date lastDate = null;
//            List<ZeroInventoryDO> doList = zeroInventoryMapper.selectList(queryWrapper);
//            for (ZeroInventoryDO inventoryVO : doList) {
//                String key = inventoryVO.getModelNo() + "-" + inventoryVO.getWarehouseCode();
//                if (map.containsKey(key)) {
//                    if (DateUtil.getDiffDay(dateMap.get(key), inventoryVO.getFromDate()) == 1) {
//                        dateMap.put(key, inventoryVO.getFromDate());
//                    } else {
//                        map.put(key, inventoryVO.getFromDate());
//                    }
//                } else {
//                    map.put(key, inventoryVO.getFromDate());
//                }
//                dateMap.put(key, inventoryVO.getFromDate());
//            }
//            doList.removeIf(item -> (map.containsKey(item.getModelNo() + "-" + item.getWarehouseCode()) && item.getFromDate() != map.get(item.getModelNo() + "-" + item.getWarehouseCode())));
//            List<ZeroInventoryVO> voList = BeanCopyUtil.copyList(doList, ZeroInventoryVO.class);
//            int size = dto.getPageNum() * dto.getPageSize();
//            if (size > voList.size()) {
//                subList = voList.subList((dto.getPageNum() - 1) * dto.getPageSize(), voList.size());
//            } else {
//                subList = voList.subList((dto.getPageNum() - 1) * dto.getPageSize(), size);
//            }
//
//            voPageInfo = new PageInfo<>(subList);
//            voPageInfo.setTotal(voList.size());
//
//        } else {
//            PageInfo<ZeroInventoryDO> pageInfo = PageHelper.startPage(dto.getPageNum(), dto.getPageSize())
//                    .doSelectPageInfo(() -> zeroInventoryMapper.selectList(queryWrapper));
//            voPageInfo = BeanCopyUtil.pageDto2Vo(pageInfo, ZeroInventoryVO.class);
//        }

        return ResultVo.success(voPageInfo);
    }

    @Override
    public void exportZeroInventoryData(ZeroInventoryDTO dto) {
        LambdaQueryWrapper<ZeroInventoryDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PublicUtil.isNotEmpty(dto.getModelNo()), ZeroInventoryDO::getModelNo, dto.getModelNo())
                .eq(PublicUtil.isNotEmpty(dto.getWarehouseCode()), ZeroInventoryDO::getWarehouseCode, dto.getWarehouseCode());

        if (dto.getDayType() == 1) {
            queryWrapper.eq(PublicUtil.isNotEmpty(dto.getDays()), ZeroInventoryDO::getDays, dto.getDays());
        }
        if (dto.getDayType() == 2) {
            queryWrapper.gt(PublicUtil.isNotEmpty(dto.getDays()), ZeroInventoryDO::getDays, dto.getDays());
        }
        if (dto.getDayType() == 3) {
            queryWrapper.lt(PublicUtil.isNotEmpty(dto.getDays()), ZeroInventoryDO::getDays, dto.getDays());
        }


        if (PublicUtil.isNotEmpty(dto.getDateType()) && PublicUtil.isNotEmpty(dto.getStratTime())) {
            if (dto.getDateType() == 1) {
                queryWrapper.between(ZeroInventoryDO::getFromDate, dto.getStratTime(), dto.getEndTime());
            }
            if (dto.getDateType() == 2) {
                queryWrapper.between(ZeroInventoryDO::getCalcDate, dto.getStratTime(), dto.getEndTime());
            }
        }
        if (dto.getStockType()) {
            queryWrapper.lt(ZeroInventoryDO::getStockQty, 0);
        }
        queryWrapper.orderByAsc(ZeroInventoryDO::getFromDate, ZeroInventoryDO::getCalcDate);
        List<ZeroInventoryDO> doList = zeroInventoryMapper.selectList(queryWrapper);
//        PageInfo<ZeroInventoryVO> voPageInfo = BeanCopyUtil.pageDto2Vo(pageInfo, ZeroInventoryVO.class);
//        Map<String, Date> map = new HashMap<>();
//        Map<String, Date> dateMap = new HashMap<>();
//        if (PublicUtil.isNotEmpty(dto.getDateType()) && PublicUtil.isNotEmpty(dto.getStratTime())) {
////            Date lastDate = null;
//
//            for (ZeroInventoryDO inventoryVO : doList) {
//                String key = inventoryVO.getModelNo() + "-" + inventoryVO.getWarehouseCode();
//                if (map.containsKey(key)) {
//                    if (DateUtil.getDiffDay(dateMap.get(key), inventoryVO.getFromDate()) == 1) {
//                        dateMap.put(key, inventoryVO.getFromDate());
//                    } else {
//                        map.put(key, inventoryVO.getFromDate());
//                    }
//                } else {
//                    map.put(key, inventoryVO.getFromDate());
//                }
//                dateMap.put(key, inventoryVO.getFromDate());
//            }
//            doList.removeIf(item -> (map.containsKey(item.getModelNo() + "-" + item.getWarehouseCode()) && item.getFromDate() != map.get(item.getModelNo() + "-" + item.getWarehouseCode())));
//        }

        String path = "templates/ZeroInventory.xlsx";
        ExcelUtil excel = new ExcelUtil(path);
        excel.openSheet(0);

        // 向模板中写入数据
        int row = 1;
        int cel;
        for (ZeroInventoryDO zeroDO : doList) {
            cel = 0;
            excel.setCellValue(row, cel++, zeroDO.getWarehouseCode());
            excel.setCellValue(row, cel++, zeroDO.getModelNo());
            excel.setCellValue(row, cel++, zeroDO.getDays());
            excel.setCellValue(row, cel++, Optional.ofNullable(zeroDO.getStockQty()).orElse(0));
            excel.setCellValue(row, cel++, zeroDO.getQtyBin());
            excel.setCellValue(row, cel++, zeroDO.getBinCell());
            excel.setCellValue(row, cel++, zeroDO.getMean());
            excel.setCellValue(row, cel++, zeroDO.getModelClass());
            excel.setCellValue(row, cel++, zeroDO.getSupplier());
            excel.setCellValue(row, cel++, zeroDO.getLoginDate());
            excel.setCellValue(row, cel++, zeroDO.getOrdingQty());
            excel.setCellValue(row, cel++, zeroDO.getTransQty());
            excel.setCellValue(row, cel++, zeroDO.getMinTDlvDate());
            excel.setCellValue(row, cel++, zeroDO.getMinOrdDate());
            excel.setCellValue(row, cel++, zeroDO.getMinDlvDate());
            excel.setCellValue(row, cel++, zeroDO.getFromDate());
            excel.setCellValue(row, cel, zeroDO.getCalcDate());
            row++;
        }
        excel.writeToResponse(response, "ZeroInventory.xlsx");
    }

    @Override
    public ResultVo<String> syncZeroInventory(Date calcDate) {
        log.info("零库存计算开始");
        List<String> list = zeroInventoryMapper.listZeroInventoryModel(calcDate);

        LambdaQueryWrapper<ZeroInventoryDO> queryWrapper = new LambdaQueryWrapper<>();
        for (String modelNo : list) {
            queryWrapper.clear();
            queryWrapper.eq(ZeroInventoryDO::getModelNo, modelNo);
            queryWrapper.eq(ZeroInventoryDO::getWarehouseCode, "KSH");
            queryWrapper.ge(ZeroInventoryDO::getCalcDate, calcDate);
            queryWrapper.orderByAsc(ZeroInventoryDO::getCalcDate);

            Map<String, Date> map = new HashMap<>();
            Date lastDate = null;
            List<ZeroInventoryDO> doList = zeroInventoryMapper.selectList(queryWrapper);
            for (ZeroInventoryDO inventoryDO : doList) {
                if (map.containsKey(inventoryDO.getModelNo())) {
                    if (DateUtil.getDiffDay(lastDate, inventoryDO.getCalcDate()) == 1) {
                        inventoryDO.setFromDate(map.get(inventoryDO.getModelNo()));
                    } else {
                        inventoryDO.setFromDate(inventoryDO.getCalcDate());
                        map.put(inventoryDO.getModelNo(), inventoryDO.getCalcDate());
                    }
                    lastDate = inventoryDO.getCalcDate();
                } else {
//                    inventoryDO.setFromDate(inventoryDO.getCalcDate());
                    lastDate = inventoryDO.getCalcDate();
                    map.put(inventoryDO.getModelNo(), inventoryDO.getFromDate());
                }

                zeroInventoryMapper.updateById(inventoryDO);
            }
        }

        for (String modelNo : list) {
            queryWrapper.clear();
            queryWrapper.eq(ZeroInventoryDO::getModelNo, modelNo);
            queryWrapper.eq(ZeroInventoryDO::getWarehouseCode, "KBJ");
            queryWrapper.ge(ZeroInventoryDO::getCalcDate, calcDate);
            queryWrapper.orderByAsc(ZeroInventoryDO::getCalcDate);

            Map<String, Date> map = new HashMap<>();
            Date lastDate = null;
            List<ZeroInventoryDO> doList = zeroInventoryMapper.selectList(queryWrapper);
            for (ZeroInventoryDO inventoryDO : doList) {
                if (map.containsKey(inventoryDO.getModelNo())) {
                    if (DateUtil.getDiffDay(lastDate, inventoryDO.getCalcDate()) == 1) {
                        inventoryDO.setFromDate(map.get(inventoryDO.getModelNo()));
                    } else {
                        inventoryDO.setFromDate(inventoryDO.getCalcDate());
                        map.put(inventoryDO.getModelNo(), inventoryDO.getCalcDate());
                    }
                    lastDate = inventoryDO.getCalcDate();
                } else {
//                    inventoryDO.setFromDate(inventoryDO.getCalcDate());
                    lastDate = inventoryDO.getCalcDate();
                    map.put(inventoryDO.getModelNo(), inventoryDO.getFromDate());
                }

                zeroInventoryMapper.updateById(inventoryDO);
            }
        }

        for (String modelNo : list) {
            queryWrapper.clear();
            queryWrapper.eq(ZeroInventoryDO::getModelNo, modelNo);
            queryWrapper.eq(ZeroInventoryDO::getWarehouseCode, "KGZ");
            queryWrapper.ge(ZeroInventoryDO::getCalcDate, calcDate);
            queryWrapper.orderByAsc(ZeroInventoryDO::getCalcDate);

            Map<String, Date> map = new HashMap<>();
            Date lastDate = null;
            List<ZeroInventoryDO> doList = zeroInventoryMapper.selectList(queryWrapper);
            for (ZeroInventoryDO inventoryDO : doList) {
                if (map.containsKey(inventoryDO.getModelNo())) {
                    if (DateUtil.getDiffDay(lastDate, inventoryDO.getCalcDate()) == 1) {
                        inventoryDO.setFromDate(map.get(inventoryDO.getModelNo()));
                    } else {
                        inventoryDO.setFromDate(inventoryDO.getCalcDate());
                        map.put(inventoryDO.getModelNo(), inventoryDO.getCalcDate());
                    }
                    lastDate = inventoryDO.getCalcDate();
                } else {
//                    inventoryDO.setFromDate(inventoryDO.getCalcDate());
                    lastDate = inventoryDO.getCalcDate();
                    map.put(inventoryDO.getModelNo(), inventoryDO.getFromDate());
                }

                zeroInventoryMapper.updateById(inventoryDO);
            }
        }

        // bug10924 WuJiaWen 2023/05/29
        Date date = DateUtil.getDate(new Date());
        String lastDate = DateUtil.dateToString(DateUtil.addDay(date, -1));
        //更改常州连续天数
        zeroInventoryMapper.setDays(lastDate, DateUtil.dateToString(date), "SCZ");
        //更改ALL连续天数
        zeroInventoryMapper.setDays(lastDate, DateUtil.dateToString(date), "ALL");

        log.info("零库存计算结束");
        return ResultVo.success("成功");
    }

    @Override
    public ResultVo<String> syncZeroInventoryVTest(Date calcDate) {
        log.info("零库存计算开始");
        List<String> list = zeroInventoryMapper.listZeroInventoryModel(calcDate);

        LambdaQueryWrapper<ZeroInventoryDO> queryWrapper = new LambdaQueryWrapper<>();
        for (String modelNo : list) {
            queryWrapper.clear();
            queryWrapper.eq(ZeroInventoryDO::getModelNo, modelNo);
            queryWrapper.eq(ZeroInventoryDO::getWarehouseCode, "KSH");
            queryWrapper.ge(ZeroInventoryDO::getCalcDate, calcDate);
            queryWrapper.orderByAsc(ZeroInventoryDO::getCalcDate);

            Map<String, Date> map = new HashMap<>();
            Date lastDate = null;
            List<ZeroInventoryDO> doList = zeroInventoryMapper.selectList(queryWrapper);
            for (ZeroInventoryDO inventoryDO : doList) {
                if (map.containsKey(inventoryDO.getModelNo())) {
                    if (DateUtil.getDiffDay(lastDate, inventoryDO.getCalcDate()) == 1) {
                        inventoryDO.setFromDate(map.get(inventoryDO.getModelNo()));
                    } else {
                        inventoryDO.setFromDate(inventoryDO.getCalcDate());
                        map.put(inventoryDO.getModelNo(), inventoryDO.getCalcDate());
                    }
                    lastDate = inventoryDO.getCalcDate();
                } else {
//                    inventoryDO.setFromDate(inventoryDO.getCalcDate());
                    lastDate = inventoryDO.getCalcDate();
                    map.put(inventoryDO.getModelNo(), inventoryDO.getFromDate());
                }

                zeroInventoryMapper.updateById(inventoryDO);
            }
        }

        for (String modelNo : list) {
            queryWrapper.clear();
            queryWrapper.eq(ZeroInventoryDO::getModelNo, modelNo);
            queryWrapper.eq(ZeroInventoryDO::getWarehouseCode, "KBJ");
            queryWrapper.ge(ZeroInventoryDO::getCalcDate, calcDate);
            queryWrapper.orderByAsc(ZeroInventoryDO::getCalcDate);

            Map<String, Date> map = new HashMap<>();
            Date lastDate = null;
            List<ZeroInventoryDO> doList = zeroInventoryMapper.selectList(queryWrapper);
            for (ZeroInventoryDO inventoryDO : doList) {
                if (map.containsKey(inventoryDO.getModelNo())) {
                    if (DateUtil.getDiffDay(lastDate, inventoryDO.getCalcDate()) == 1) {
                        inventoryDO.setFromDate(map.get(inventoryDO.getModelNo()));
                    } else {
                        inventoryDO.setFromDate(inventoryDO.getCalcDate());
                        map.put(inventoryDO.getModelNo(), inventoryDO.getCalcDate());
                    }
                    lastDate = inventoryDO.getCalcDate();
                } else {
//                    inventoryDO.setFromDate(inventoryDO.getCalcDate());
                    lastDate = inventoryDO.getCalcDate();
                    map.put(inventoryDO.getModelNo(), inventoryDO.getFromDate());
                }

                zeroInventoryMapper.updateById(inventoryDO);
            }
        }

        for (String modelNo : list) {
            queryWrapper.clear();
            queryWrapper.eq(ZeroInventoryDO::getModelNo, modelNo);
            queryWrapper.eq(ZeroInventoryDO::getWarehouseCode, "KGZ");
            queryWrapper.ge(ZeroInventoryDO::getCalcDate, calcDate);
            queryWrapper.orderByAsc(ZeroInventoryDO::getCalcDate);

            Map<String, Date> map = new HashMap<>();
            Date lastDate = null;
            List<ZeroInventoryDO> doList = zeroInventoryMapper.selectList(queryWrapper);
            for (ZeroInventoryDO inventoryDO : doList) {
                if (map.containsKey(inventoryDO.getModelNo())) {
                    if (DateUtil.getDiffDay(lastDate, inventoryDO.getCalcDate()) == 1) {
                        inventoryDO.setFromDate(map.get(inventoryDO.getModelNo()));
                    } else {
                        inventoryDO.setFromDate(inventoryDO.getCalcDate());
                        map.put(inventoryDO.getModelNo(), inventoryDO.getCalcDate());
                    }
                    lastDate = inventoryDO.getCalcDate();
                } else {
//                    inventoryDO.setFromDate(inventoryDO.getCalcDate());
                    lastDate = inventoryDO.getCalcDate();
                    map.put(inventoryDO.getModelNo(), inventoryDO.getFromDate());
                }

                zeroInventoryMapper.updateById(inventoryDO);
            }
        }

        // bug10924 WuJiaWen 2023/05/29
        Date date = DateUtil.stringToDate("2025-02-16");
        String lastDate = "2025-02-15";
        //更改常州连续天数
        zeroInventoryMapper.setDays(lastDate, DateUtil.dateToString(date), "SCZ");
        //更改ALL连续天数
        zeroInventoryMapper.setDays(lastDate, DateUtil.dateToString(date), "ALL");

        log.info("零库存计算结束");
        return ResultVo.success("成功");
    }

    @Override
    public ResultVo<String> calcOPSInventoryForManu() {
        inventoryMapper.calcOPSInventoryForManuView1();
        inventoryMapper.calcOPSInventoryForManuView2();
        return ResultVo.success("执行成功");
    }

    @Override
    public ResultVo<List<SMSInventoryVO>> getModelInventoryByWarehouse(String modelNo) {

        if (StringUtils.isBlank(modelNo)) {
            return ResultVo.success();
        }
        long lstart = System.currentTimeMillis();

        Boolean exit = redisManager.hasKey(Constants.CACHE_FINDINVENTORYBYMODELNO + modelNo);
        if (exit) {
            Object obj = redisManager.get(Constants.CACHE_FINDINVENTORYBYMODELNO + modelNo);
            if (obj != null) {
                List<SMSInventoryVO> smsInventoryVOS = JSONObject.parseArray(obj.toString(), SMSInventoryVO.class);
                long lend = System.currentTimeMillis();
                log.info("库存查询(手机端) 走缓存:  参数: " + modelNo + " 耗时(s): " + (lend - lstart) / 1000);
                return ResultVo.success(smsInventoryVOS);
            }
        }
        List<SMSInventoryVO> list = null;
        // 1 根据型号查ops_inventory  通用在库和专用在库
        List<SMSInventoryVO> tyQtyAndSPQtyByModelNo = getTYQtyAndSPQtyByModelNo(modelNo);

        // 2 查询订货中数量
        List<SMSInventoryVO> orderingQty = getOrderingQty(modelNo);

        // 将订货中的数量放进同列中
        if (CollectionUtils.isNotEmpty(tyQtyAndSPQtyByModelNo) && CollectionUtils.isNotEmpty(orderingQty)) {
            list = tyQtyAndSPQtyByModelNo.stream().map(m -> {
                orderingQty.stream().filter(m2 -> Objects.equals(m.getWarehouseCode(), m2.getWarehouseCode())).forEach(m2 -> {
                    m.setQuantityOrder(m2.getQuantityOrder());
                });
                return m;
            }).collect(Collectors.toList());
        } else if (CollectionUtils.isNotEmpty(tyQtyAndSPQtyByModelNo) && CollectionUtils.isEmpty(orderingQty)) {
            list = tyQtyAndSPQtyByModelNo;
        } else if (CollectionUtils.isEmpty(tyQtyAndSPQtyByModelNo) && CollectionUtils.isNotEmpty(orderingQty)) {
            list = orderingQty;
        }

        // 3 根据型号查inventory_supplier 可生产数量,可组装数量
        List<SMSInventoryVO> smsInventoryVOS = getproductQtyAndAssQty(modelNo);
        if (CollectionUtils.isNotEmpty(list)) {
            if (CollectionUtils.isNotEmpty(smsInventoryVOS)) {
                list.addAll(smsInventoryVOS);
            }
        } else {
            list = smsInventoryVOS;
        }

        if (CollectionUtils.isNotEmpty(list)) {
            // 过滤掉数量小于等于0的
            List<SMSInventoryVO> allDataList = list.stream().filter(e -> !(e.getTyAvaQty() <= 0 && e.getSpecQty() <= 0 &&
                    e.getQuantityProduce() <= 0 && e.getQuantityOrder() <= 0 && e.getQuantityAssembly() <= 0)).collect(Collectors.toList());

            List<SMSInventoryVO> collect = allDataList.stream().filter(e -> !(e.getWarehouseCode().startsWith("W") || e.getWarehouseCode().startsWith("w"))).collect(Collectors.toList());
            for (SMSInventoryVO item : collect) {
                if (StringUtils.isNotBlank(item.getWarehouseCode())) {
                    String warehouseNameByCode = commonService.getWarehouseNameByCode(item.getWarehouseCode());
                    if (StringUtils.isBlank(warehouseNameByCode)) {
                        item.setWarehouseCodeName(commonService.getSupplierNameByCode(item.getWarehouseCode()));
                    } else {
                        item.setWarehouseCodeName(warehouseNameByCode);
                    }
                }
            }
            // 合并委托在库的数据集
            SMSInventoryVO smsInventoryVO = mergeWTData(allDataList);
            if (smsInventoryVO != null) {
                collect.add(smsInventoryVO);
            }
            String strList = JSONArray.toJSONString(collect);
            redisManager.set(Constants.CACHE_FINDINVENTORYBYMODELNO + modelNo, strList, 60 * 10);
            long lend = System.currentTimeMillis();
            log.info("库存查询(手机端):  参数: " + modelNo + " 耗时(s): " + (lend - lstart) / 1000);
            return ResultVo.success(collect);
        }
        return ResultVo.success(list);
    }

    @Override
    public ResultVo<PageInfo<SpecStatisticsVO>> findUserStockStatisList(SpecStatisticsRequest request, int pageNumber, int pageSize) {

        if (pageNumber == 0) {
            pageNumber = 1;
        }
        if (pageSize == 0) {
            pageSize = 10;
        }

        if (request == null) {
            return ResultVo.success();
        }

        long lstart = System.currentTimeMillis();

        List<SpecStatisticsVO> list = new ArrayList<>();

        Boolean aBoolean = redisManager.hasKey(Constants.CACHE_FINDUSERSTOCKINVENTORY + JSONObject.toJSONString(request));
        if (aBoolean) {
            Object obj = redisManager.get(Constants.CACHE_FINDUSERSTOCKINVENTORY + JSONObject.toJSONString(request));
            if (obj != null) {
                list = JSONObject.parseArray(obj.toString(), SpecStatisticsVO.class);
                long lend = System.currentTimeMillis();
                log.info("用户专备查询[findUserStockStatisList] 走缓存 参数: " + JSONObject.toJSONString(request) + " 耗时(s): " + (lend - lstart) / 1000);
            }
        } else {

            if (StringUtils.isNotBlank(request.getWarehouseType()) && request.getWarehouseType().equals(WareHouseTypeEnum.SUPPLIER.getCode())) {
                // add by LiYingChao from bug 8758 in 2022-11-23
                list = findSupplierInventoryList(request);
            } else {
                // 统计专备在库数量
                List<SpecStatisticsVO> specQtyList = inventoryMapper.getSpecQty(request);
                long l = System.currentTimeMillis();
                log.info("统计专备在库数量耗时: " + (l - lstart) + "ms" + " 秒:" + (l - lstart) / 1000);
                List<SpecStatisticsVO> canOrderQtyList = inventoryMapper.getCanOrderQty(request);
                long l1 = System.currentTimeMillis();
                log.info("统计查询订货中的数量: " + (l1 - l) + "ms" + " 秒:" + (l1 - l) / 1000);

                if (CollectionUtils.isNotEmpty(canOrderQtyList)) {
                    for (SpecStatisticsVO item : canOrderQtyList) {
                        switch (item.getInventoryStatus()) {
                            case "P": // 生产在途数量
                                item.setProductQty(item.getCanOrderQty());
                                break;
                            case "W": // 待入库数量
                                item.setWaitInQty(item.getCanOrderQty());
                                break;
                            case "T1":
                            case "T3":
                            case "T4": // 运输在途数量
                                item.setTransportQty(item.getCanOrderQty());
                                break;
                        }
                    }
                }

                if (CollectionUtils.isNotEmpty(specQtyList) && CollectionUtils.isNotEmpty(canOrderQtyList)) {
                    list = specQtyList.stream().map(m -> {
                        canOrderQtyList.stream().filter(m2 -> Objects.equals(m.getWarehouseCode(), m2.getWarehouseCode()) &&
                                Objects.equals(m.getCustomerNo(), m2.getCustomerNo()) &&
                                Objects.equals(m.getModelNo(), m2.getModelNo())).forEach(m2 -> {
                            m.setWaitInQty(m2.getWaitInQty()); // 待入库数量
                            m.setTransportQty(m2.getTransportQty()); // 运输在途数量
                            m.setProductQty(m2.getProductQty()); // 生产在途数量
                        });
                        return m;
                    }).collect(Collectors.toList());

                    List<SpecStatisticsVO> noReplateList = canOrderQtyList.stream().parallel().filter(m -> specQtyList.stream()
                            .noneMatch(m2 -> Objects.equals(m.getWarehouseCode(), m2.getWarehouseCode()) &&
                                    Objects.equals(m.getCustomerNo(), m2.getCustomerNo()) &&
                                    Objects.equals(m.getModelNo(), m2.getModelNo()))).collect(Collectors.toList());
                    if (CollectionUtils.isNotEmpty(noReplateList)) {
                        list.addAll(noReplateList);
                    }
                } else if (CollectionUtils.isEmpty(specQtyList) && CollectionUtils.isNotEmpty(canOrderQtyList)) {
                    list = canOrderQtyList;
                } else if (CollectionUtils.isNotEmpty(specQtyList) && CollectionUtils.isEmpty(canOrderQtyList)) {
                    list = specQtyList;
                }

                // 过滤掉<=0的数据
//        List<SpecStatisticsVO> collect = list.stream().filter(e -> !(e.getSpValidQty() <= 0 && e.getProductQty() <= 0 &&
//                e.getTransportQty() <= 0 && e.getWaitInQty() <= 0)).collect(Collectors.toList());
                Map<String, String> map = new HashMap<>();
                if (CollectionUtils.isNotEmpty(list)) {
                    for (SpecStatisticsVO item : list) {
                        if (StringUtils.isBlank(map.get(item.getCustomerNo()))) {
                            map.put(item.getCustomerNo(), commonService.getCustomerNameByNo(item.getCustomerNo()));
                        }
                        item.setCustomerName(map.get(item.getCustomerNo()));

                        if (StringUtils.isBlank(map.get(item.getWarehouseCode()))) {
                            map.put(item.getWarehouseCode(), commonService.getWarehouseNameByCode(item.getWarehouseCode()));
                        }
                        item.setWarehouseCodeName(map.get(item.getWarehouseCode()));

                        if (StringUtils.isBlank(map.get(item.getDeptNo()))) {
                            map.put(item.getDeptNo(), commonService.getDeptNameByNo(item.getDeptNo()));
                        }
                        item.setDeptName(map.get(item.getDeptNo()));
                    }
                }
                long l2 = System.currentTimeMillis();
                log.info("数据处理耗时: " + (l2 - l1) + "ms " + "秒: " + (l2 - l1) / 1000);
                // 存入redis
                redisManager.set(Constants.CACHE_FINDUSERSTOCKINVENTORY + JSONObject.toJSONString(request), JSONArray.toJSONString(list), 60 * 10);

                long lend = System.currentTimeMillis();
                log.info("用户专备查询[findUserStockStatisList]  参数: " + JSONObject.toJSONString(request) + " 耗时(s): " + (lend - lstart) / 1000);
            }
        }

        PageInfo pageInfo = new PageInfo();
        pageInfo.setPageNum(pageNumber);
        pageInfo.setPageSize(pageSize);
        pageInfo.setTotal(list.size());
        pageInfo.setSize(pageSize);
        if (CollectionUtils.isNotEmpty(list)) {
            List<List<SpecStatisticsVO>> partition = ListUtils.partition(list, pageSize);
            pageInfo.setList(partition.get(pageNumber - 1));
            pageInfo.setPages(partition.size());
        } else {
            pageInfo.setList(list);
            pageInfo.setPages(list.size());
        }
        return ResultVo.success(pageInfo);
    }

    /**
     * 合并委托在库数据集
     */
    public SMSInventoryVO mergeWTData(List<SMSInventoryVO> list) {
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        List<SMSInventoryVO> wtInventoryList = list.stream().filter(e -> (e.getWarehouseCode().startsWith("W") || e.getWarehouseCode().startsWith("w"))).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(wtInventoryList)) {
            return null;
        }
        int tyQty = wtInventoryList.stream().mapToInt(SMSInventoryVO::getTyAvaQty).sum();
        int specQty = wtInventoryList.stream().mapToInt(SMSInventoryVO::getSpecQty).sum();
        int productQty = wtInventoryList.stream().mapToInt(SMSInventoryVO::getQuantityProduce).sum();
        int assemblyQty = wtInventoryList.stream().mapToInt(SMSInventoryVO::getQuantityAssembly).sum();
        int orderQty = wtInventoryList.stream().mapToInt(SMSInventoryVO::getQuantityOrder).sum();

        SMSInventoryVO item = new SMSInventoryVO();
        item.setWarehouseCodeName("服务备库");
        item.setTyAvaQty(tyQty);
        item.setSpecQty(specQty);
        item.setQuantityProduce(productQty);
        item.setQuantityAssembly(assemblyQty);
        item.setQuantityOrder(orderQty);
        return item;
    }

    /**
     * 根据型号查ops_inventory  通用在库和专用在库
     *
     * @param modelNo
     * @return
     */
    public List<SMSInventoryVO> getTYQtyAndSPQtyByModelNo(String modelNo) {
        if (StringUtils.isBlank(modelNo)) {
            return null;
        }

        List<SMSInventoryVO> tyQtyAndSpQtyList = inventoryMapper.findTYQtyAndSpQtyByModelNo(modelNo);

        if (CollectionUtils.isNotEmpty(tyQtyAndSpQtyList)) {
            Map<String, List<SMSInventoryVO>> warehouseMap = tyQtyAndSpQtyList.stream()
                    .collect(Collectors.groupingBy(SMSInventoryVO::getWarehouseCode));

            List<SMSInventoryVO> list = new ArrayList<>(warehouseMap.size());
            SMSInventoryVO vo;

            for (String keyCode : warehouseMap.keySet()) {
                vo = new SMSInventoryVO(); // 每组新定义
                vo.setWarehouseCode(keyCode);
                vo.setTyAvaQty(0);
                vo.setSpecQty(0);
                List<SMSInventoryVO> smsInventoryVOS = warehouseMap.get(keyCode);
                if (CollectionUtils.isNotEmpty(smsInventoryVOS)) {
                    for (SMSInventoryVO smsInventoryVO : smsInventoryVOS) {
                        if (InventoryTypeEnum.TY.getCode().equalsIgnoreCase(smsInventoryVO.getInventoryTypeCode())) {
                            vo.setTyAvaQty(vo.getTyAvaQty() + smsInventoryVO.getQuantity() - smsInventoryVO.getPrepareQty());  // 通用数量统计
                        } else {
                            vo.setSpecQty(vo.getSpecQty() + smsInventoryVO.getQuantity() - smsInventoryVO.getPrepareQty()); // 专用统计
                        }
                    }

                }
                list.add(vo); // 每组统计出通用和专业数量作为一行数据
            }
            return list;
        }
        return tyQtyAndSpQtyList;
    }

    /**
     * 根据型号查ops_inventory_move 订货中数量
     */
    public List<SMSInventoryVO> getOrderingQty(String modelNo) {
        return inventoryMapper.findOrderingQty(modelNo);
    }

    /**
     * 根据型号查可生产数量,可组装数量
     */
    public List<SMSInventoryVO> getproductQtyAndAssQty(String modelNo) {
        return inventoryMapper.searchOverSeasInventoryList(modelNo);
    }


    /**
     * // add by LiYingChao from bug 8758 in 2022-11-23
     * 查询供应商库存
     */
    public List<SpecStatisticsVO> findSupplierInventoryList(SpecStatisticsRequest request) {
        List<SpecStatisticsVO> list = new ArrayList<>();
        if (request == null) {
            return list;
        }
        if (!request.getWarehouseType().equals(WareHouseTypeEnum.SUPPLIER.getCode())) {
            return list;
        }
        LambdaQueryWrapper<InventorySupplierDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(StringUtils.isNotBlank(request.getSupplierId()), InventorySupplierDO::getSupplierId, request.getSupplierId())
                .eq(StringUtils.isNotBlank(request.getModelNo()), InventorySupplierDO::getModelNo, request.getModelNo());
        List<InventorySupplierDO> inventorySupplierDOS = inventorySupplierMapper.selectList(queryWrapper);
        SpecStatisticsVO specStatisticsVO;

        // 查询供应商列表
        ResultVo<List<DataCodeVO>> dataCodes = dictDataServiceFeignApi.getDataCodes(Constants.SUPPLIER_LIST_CODE);
        if (!dataCodes.isSuccess() && dataCodes.getData() == null) {
            return list;
        }
        List<DataCodeVO> data = dataCodes.getData();
        Map<String, String> map = data.stream().collect(toMap(DataCodeVO::getCode, DataCodeVO::getCodeName, (oldValue, newValue) -> newValue));

        for (InventorySupplierDO item : inventorySupplierDOS) {
            specStatisticsVO = new SpecStatisticsVO();
            specStatisticsVO.setWarehouseCode(item.getSupplierId());
            specStatisticsVO.setWarehouseCodeName(map.get(item.getSupplierId()));
            specStatisticsVO.setModelNo(item.getModelNo());
            specStatisticsVO.setSpValidQty(item.getQuantity());
            list.add(specStatisticsVO);
        }
        // 存入redis
        redisManager.set(Constants.CACHE_FINDUSERSTOCKINVENTORY + JSONObject.toJSONString(request), JSONArray.toJSONString(list), 60 * 10);
        return list;
    }

    @Override
    public ResultVo<List<OpsInventoryVO>> findInventoryByWareHouseCode(String wareHouseCode) {
        if (StringUtils.isBlank(wareHouseCode)) {
            return ResultVo.failure("库房代码不可为空.");
        }
        // 获取所有型号委托在库数量
        LambdaQueryWrapper<OpsInventoryDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .select(OpsInventoryDO::getWarehouseCode, OpsInventoryDO::getModelNo, OpsInventoryDO::getQuantity)
                .likeRight(OpsInventoryDO::getWarehouseCode, wareHouseCode);
        List<OpsInventoryDO> opsInventoryDOS = inventoryMapper.selectList(queryWrapper);
        if (opsInventoryDOS.isEmpty()) {
            return ResultVo.success(Collections.emptyList(), "仓库" + wareHouseCode + "暂无在库数据");
        }
        return ResultVo.success(BeanCopyUtil.copyList(opsInventoryDOS, OpsInventoryVO.class));
    }

    @Override
    public ResultVo<Integer> getCanUseQty(String modelNo) {
        Integer qty = inventoryMapper.getCanUseQty(modelNo);
        if (qty == null) {
            return ResultVo.success(0, "无库存");
        }
        return ResultVo.success(qty);
    }

    @Override
    public ResultVo<Integer> getCustomerCanUseQty(InventoryRequestDTO dto) {
        if (dto == null) {
            return ResultVo.failure("参数为空.");
        }
        Integer qty = inventoryMapper.getCustomerCanUseQty(dto);
        if (qty == null) {
            return ResultVo.success(0, "无库存");
        }
        return ResultVo.success(qty);
    }
    @Override
    public ResultVo<List<OpsInventoryVO>> getInventoryMoveByModels(OpsInventoryPropertyRequestDTO inventoryProperty){
        List<OpsInventoryDO> doList= inventoryMapper.getInventoryMoveByModels(inventoryProperty);
        return ResultVo.success(BeanCopyUtil.copyList(doList,OpsInventoryVO.class));
    }

    @Override
    public ResultVo<List<ModelOrderExpFreqVO>> getSalesQuantityAndFreq(OpsInventoryPropertyRequestDTO inventoryProperty ){
        List<ModelOrderExpFreqVO> list = opsInventoryLogMapper.getSalesQuantityAndFreq(inventoryProperty);
        return ResultVo.success(list);
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



}

