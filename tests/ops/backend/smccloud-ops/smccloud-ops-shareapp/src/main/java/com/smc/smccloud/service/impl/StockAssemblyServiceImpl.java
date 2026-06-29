package com.smc.smccloud.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sales.ops.db.entity.OpsInventoryProperty;
import com.sales.ops.dto.inventory.InventoryForAdjustDto;
import com.sales.ops.dto.inventory.InventoryForProducChangeDto;
import com.sales.ops.dto.util.CommonResult;
import com.sales.ops.dto.util.UserDto;
import com.sales.ops.enums.DoTypeEnum;
import com.sales.ops.feign.OpsPropertyFeignApi;
import com.sales.ops.feign.OpsWmDispatchForOrderFeignApi;
import com.smc.smccloud.core.exception.BusinessException;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.model.constants.GlobalConstant;
import com.smc.smccloud.core.model.dto.LoginUserDTO;
import com.smc.smccloud.core.model.enums.InventoryTypeEnum;
import com.smc.smccloud.core.redis.redisson.RedissonUtil;
import com.smc.smccloud.core.utils.*;
import com.smc.smccloud.mapper.ExpdetailMapper;
import com.smc.smccloud.mapper.stockassembly.StockAssemblyDetailMapper;
import com.smc.smccloud.mapper.stockassembly.StockAssemblyDetailViewMapper;
import com.smc.smccloud.mapper.stockassembly.StockAssemblyMapper;
import com.smc.smccloud.model.cost.ImpdataAdjustDO;
import com.smc.smccloud.model.csstock.WarehouseDO;
import com.smc.smccloud.model.enums.StockAssemblyApplyStatusEnum;
import com.smc.smccloud.model.enums.StockAssemblyAssemblyTypeEnum;
import com.smc.smccloud.model.enums.StockAssemblyDetailStatusEnum;
import com.smc.smccloud.model.inventory.ModelWarehouseStockRequest;
import com.smc.smccloud.model.stockassembly.*;
import com.smc.smccloud.model.trans.TransOrderVO;
import com.smc.smccloud.model.warehouse.WarehouseVO;
import com.smc.smccloud.service.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * Author: B90034
 * Date: 2021-09-27 12:17
 * Description: 组换调库申请服务接口实现类
 */
@Slf4j
@Service
public class StockAssemblyServiceImpl implements StockAssemblyService {

    /**
     * 调库申请号
     */
    private final static String TRANSFER_APPLY_BILLTYPE = "14";
    /**
     * 组换申请号
     */
    private final static String ASSEMBLY_APPLY_BILLTYPE = "18";
    /**
     * 组换票号
     */
    private final static String Assembly_INVOICE_NO = "15";
    /**
     * 调库票号
     */
    private final static String TRANSFER_INVOICE_NO = "16";

    @Resource
    private StockAssemblyMapper stockAssemblyMapper;
    @Resource
    private StockAssemblyDetailMapper stockAssemblyDetailMapper;
    @Resource
    private StockAssemblyDetailViewMapper stockAssemblyDetailViewMapper;
    @Resource
    private ImpdataAdjustService impdataAdjustService;

    @Resource
    private CommonServiceFeignApi commonServiceFeignApi;

    @Resource
    private OpsWmDispatchForOrderFeignApi opsWmDispatchForOrderFeignApi;
    @Resource
    private InventoryServiceFeignApi inventoryServiceFeignApi;
    @Resource
    private TransStockFeignApi transStockFeignApi;
    @Resource
    private OpsPropertyFeignApi opsPropertyFeignApi;
    @Resource
    private ProductServiceFeignApi productServiceFeignApi;
    @Resource
    private RedissonUtil redissonUtil;
    @Resource
    private OpsCommonService opsCommonService;

    @Resource
    private TransactionTemplate transactionTemplate;

    @Resource
    private ExpdetailMapper expdetailMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVo<String> saveStockAssembly(StockAssemblyApplyDTO data) {

        LoginUserDTO userDto = SMCApp.getLoginAuthDto();
        List<StockAssemblyItemDTO> applyItems = new ArrayList<>(data.getOutItems().size() + data.getInItems().size());
        applyItems.addAll(data.getOutItems());
        applyItems.addAll(data.getInItems());

        //    <!--add by WuWeiDong 20230629  bug 11100  判断是否可以调库 -->
        boolean isTransfer = StockAssemblyAssemblyTypeEnum.TRANSFER.getCode().equals(data.getAssembleType());
        if (isTransfer) {
            List<String> warehouseCodeList = applyItems.stream().map(StockAssemblyItemDTO::getWarehouseCode).distinct().collect(Collectors.toList());
            ResultVo<String> canTransferVo = this.canTransferByWarehouseList(warehouseCodeList);
            if (!canTransferVo.isSuccess()) {
                return ResultVo.failure(canTransferVo.getMessage());
            }
        }

        StockAssemblyDO applyInfo = BeanCopyUtil.copy(data, StockAssemblyDO.class);
        List<StockAssemblyDetailDO> detailList = new ArrayList<>(applyItems.size());
        for (StockAssemblyItemDTO item : applyItems) {
            this.spliceInventoryKeys(item);
            detailList.add(BeanCopyUtil.copy(item, StockAssemblyDetailDO.class));
        }
        //    <!--add by WuWeiDong 20231213  bug 12437  前端没有更新，追加数据，不做判断 -->
        List<StockAssemblyDetailDO> newDetailList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(detailList)) {
            newDetailList.addAll(detailList);
        }
        //追加已写入的数据一起验证组换数据
        if (ObjectUtils.isNotEmpty(applyInfo.getId()) && applyInfo.getId() > 0) {
            //去掉前端更新的id
            List<Long> detailIds = newDetailList.stream().filter(i -> ObjectUtils.isNotEmpty(i.getId())).map(StockAssemblyDetailDO::getId).collect(Collectors.toList());
            LambdaQueryWrapper<StockAssemblyDetailDO> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(StockAssemblyDetailDO::getApplyId, applyInfo.getId())
                    .notIn(CollectionUtils.isNotEmpty(detailIds), StockAssemblyDetailDO::getId, detailIds)
                    .ne(StockAssemblyDetailDO::getOptCode, 9);
            List<StockAssemblyDetailDO> detailDOS = stockAssemblyDetailMapper.selectList(queryWrapper);
            if (CollectionUtils.isNotEmpty(detailDOS)) {
                newDetailList.addAll(detailDOS);
            }

        }
        if (CollectionUtils.isNotEmpty(newDetailList)) {
            ResultVo<String> checkResult = this.checkApplyDetail(applyInfo.getApplyType(), newDetailList);
            if (!checkResult.isSuccess()) {
                return ResultVo.failure("保存失败 " + checkResult.getMessage());
            }
        }

        String applyStatus = "1";
        applyInfo.setStatus(applyStatus);
        applyInfo.setUpdateUser(userDto.getUserNo());
        //applyInfo.setWarehouseCode(applyItems.get(0).getWarehouseCode());

        // 保存申请
        if (applyInfo.getId() == null || applyInfo.getId() == 0) {
            String applyNo = this.createApplyNo(applyInfo.getApplyType());
            if (StringUtils.isBlank(applyNo)) {
                return ResultVo.failure("申请号生成失败");
            }
            applyInfo.setApplyNo(applyNo);
            applyInfo.setDeptNo(userDto.getDeptNo());
            applyInfo.setCreateUser(userDto.getUserNo());
            stockAssemblyMapper.insert(applyInfo);
        } else {
            // 申请号不正确，重新生成申请号
            if (StringUtils.isBlank(applyInfo.getApplyNo())
                    || ("1".equals(applyInfo.getApplyType()) && !applyInfo.getApplyNo().startsWith("ZH"))
                    || ("2".equals(applyInfo.getApplyType()) && !applyInfo.getApplyNo().startsWith("ST"))) {
                applyInfo.setApplyNo(this.createApplyNo(applyInfo.getApplyType()));
            }
            stockAssemblyMapper.updateById(applyInfo);
        }

        // 保存申请详情项信息
        int detailStatus = 0; // 编辑中
        for (StockAssemblyDetailDO detail : detailList) {
            detail.setModelNo(detail.getModelNo().trim());
            detail.setUpdateUser(userDto.getUserNo());
            if (detail.getId() == null || detail.getId() == 0) {
                detail.setApplyId(applyInfo.getId());
                detail.setOptCode(detailStatus);
                detail.setCreateUser(userDto.getUserNo());
                stockAssemblyDetailMapper.insert(detail);
            } else {
                stockAssemblyDetailMapper.updateById(detail);
            }
        }
        return ResultVo.success(applyInfo.getId().toString());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVo<String> createStockAssemblyApply(StockAssemblyApplyDTO data) {
        LoginUserDTO userDto = SMCApp.getLoginAuthDto();
        StockAssemblyDO applyInfo = BeanCopyUtil.copy(data, StockAssemblyDO.class);
        List<StockAssemblyItemDTO> applyItems = new ArrayList<>(data.getOutItems().size() + data.getInItems().size());
        applyItems.addAll(data.getOutItems());
        applyItems.addAll(data.getInItems());
        List<StockAssemblyDetailDO> detailList = new ArrayList<>(applyItems.size());
        for (StockAssemblyItemDTO item : applyItems) {
            spliceInventoryKeys(item);
            detailList.add(BeanCopyUtil.copy(item, StockAssemblyDetailDO.class));
        }

        ResultVo<String> checkResult = this.checkApplyDetail(applyInfo.getApplyType(), detailList);
        if (!checkResult.isSuccess()) {
            return ResultVo.failure("保存失败: " + checkResult.getMessage());
        }
        applyInfo.setWarehouseCode(detailList.get(0).getWarehouseCode());

        boolean repeatApply = false; // 重复申请

        if (StringUtils.isBlank(applyInfo.getApplyNo())) {
            String applyNo = this.createApplyNo(applyInfo.getApplyType());
            if (StringUtils.isBlank(applyNo)) {
                return ResultVo.failure("申请号生成失败");
            }
            applyInfo.setApplyNo(applyNo);
        } else {
            // 判断申请是否已存在
            QueryWrapper<StockAssemblyDO> applyQuery = Wrappers.query();
            applyQuery.select("Top(1) id", "ApplyNo", "Status");
            applyQuery.lambda().eq(StockAssemblyDO::getApplyNo, applyInfo.getApplyNo())
                    .ne(StockAssemblyDO::getStatus, 9);
            StockAssemblyDO oldApplyInfo = stockAssemblyMapper.selectOne(applyQuery);
            if (oldApplyInfo != null) {
                if (Integer.parseInt(oldApplyInfo.getStatus()) > 4) {
                    return ResultVo.failure("保存失败: 申请已处理,不可重复申请");
                }
                applyInfo.setId(oldApplyInfo.getId());
                repeatApply = true;
            }
        }

        // 如果是（仅财务）申请类型，跳过组换处理，直接计入成本 Add by Dengdenghui 2022-11-18 for bug-8712
        boolean isFinanceOnly = StockAssemblyAssemblyTypeEnum.ASSEMBLY_FINANCE_ONLY.getCode().equals(applyInfo.getAssembleType());
        // String invoiceNo = ""; Edit by Dengdenghui 2022-11-29 bug-8822
        if (isFinanceOnly) {
            //*invoiceNo = getInvoiceNo(applyInfo.getId(), applyInfo.getApplyType());
            //applyInfo.setBillNo(invoiceNo);
            applyInfo.setStatus(StockAssemblyApplyStatusEnum.finished.getCode());
        }

        // 保存申请信息
        String applyStatus = "3"; // 待审核确认
        applyInfo.setStatus(Optional.ofNullable(applyInfo.getStatus()).orElse(applyStatus));
        applyInfo.setUpdateUser(userDto.getUserNo());
        applyInfo.setCreateUser(userDto.getUserNo());
        if (applyInfo.getId() == null || applyInfo.getId() == 0) {
            stockAssemblyMapper.insert(applyInfo);
        } else {
            stockAssemblyMapper.updateById(applyInfo);
        }

        // 保存申请详情项信息
        LambdaQueryWrapper<StockAssemblyDetailDO> detailQuery = Wrappers.lambdaQuery();
        StockAssemblyDetailDO oldDetail;
        int detailStatus = 2; // 待处理

        for (StockAssemblyDetailDO detail : detailList) {
            detail.setApplyId(applyInfo.getId());
            detail.setModelNo(detail.getModelNo().trim());

            if (repeatApply) {
                detailQuery.clear();
                detailQuery.select(StockAssemblyDetailDO::getId, StockAssemblyDetailDO::getOptCode);
                detailQuery.eq(StockAssemblyDetailDO::getApplyId, detail.getApplyId())
                        .eq(StockAssemblyDetailDO::getModelNo, detail.getModelNo())
                        .eq(StockAssemblyDetailDO::getIsTransOut, detail.getIsTransOut())
                        .eq(StockAssemblyDetailDO::getInventoryKeys, detail.getInventoryKeys());
                oldDetail = stockAssemblyDetailMapper.selectOne(detailQuery);
                if (oldDetail != null) {
                    if (oldDetail.getOptCode() > 4) {
                        continue;
                    } else {
                        detail.setId(oldDetail.getId());
                    }
                }
            }
            if (isFinanceOnly) {
                //detail.setInvoiceNo(invoiceNo);
                detail.setOptCode(6);
            }

            detail.setOptCode(Optional.ofNullable(detail.getOptCode()).orElse(detailStatus));
            detail.setUpdateUser(userDto.getUserNo());
            detail.setCreateUser(userDto.getUserNo());
            if (detail.getId() == null || detail.getId() == 0) {
                stockAssemblyDetailMapper.insert(detail);
            } else {
                stockAssemblyDetailMapper.updateById(detail);
            }
        }
        return ResultVo.success(applyInfo.getId().toString());
    }

    @Override
    public ResultVo<String> removeStockAssemblyApply(List<Long> applyIds) {
        LoginUserDTO userDTO = SMCApp.getLoginAuthDto();
        List<String> canCancelApply = Arrays.asList("1", "2", "3", "7"); // 可取消申请状态
        List<Integer> noCancelDetail = Arrays.asList(5, 6, 8); // 不可取消申请项状态
        StringBuilder success = new StringBuilder();
        StringBuilder error = new StringBuilder();

        for (Long applyId : applyIds) {
            // 1.检查申请状态是否可取消
            LambdaQueryWrapper<StockAssemblyDO> applyQuery = Wrappers.lambdaQuery();
            applyQuery.select(StockAssemblyDO::getApplyNo, StockAssemblyDO::getStatus);
            applyQuery.eq(StockAssemblyDO::getId, applyId);
            StockAssemblyDO applyInfo = stockAssemblyMapper.selectOne(applyQuery);
            if (!canCancelApply.contains(applyInfo.getStatus())) {
                log.info("组换调库 {} 申请状态不可以取消", applyInfo.getApplyNo());
                error.append(applyInfo.getApplyNo())
                        .append(StockAssemblyApplyStatusEnum.getNameByCode(applyInfo.getStatus()))
                        .append(",不可取消; ");
                continue;
            }
            // 2.检查申请项状态是否可取消
            QueryWrapper<StockAssemblyDetailDO> detailQuery = Wrappers.query();
            detailQuery.select("Top(1) Id");
            detailQuery.lambda().eq(StockAssemblyDetailDO::getApplyId, applyId)
                    .in(StockAssemblyDetailDO::getOptCode, noCancelDetail);
            StockAssemblyDetailDO oneDetail = stockAssemblyDetailMapper.selectOne(detailQuery);
            if (oneDetail != null) {
                log.info("组换调库 {} 申请项状态不可以取消", applyInfo.getApplyNo());
                error.append(applyInfo.getApplyNo()).append("处理中,不可取消; ");
                continue;
            }
            // 3.取消申请
            Date updateTime = new Date();

            transactionTemplate.executeWithoutResult(transactionStatus -> {
                try {
                    LambdaUpdateWrapper<StockAssemblyDetailDO> detailUpdate = Wrappers.lambdaUpdate();
                    detailUpdate.set(StockAssemblyDetailDO::getOptCode, 9)
                            .set(StockAssemblyDetailDO::getUpdateUser, userDTO.getUserNo())
                            .set(StockAssemblyDetailDO::getUpdateTime, updateTime);
                    detailUpdate.eq(StockAssemblyDetailDO::getApplyId, applyId)
                            .notIn(StockAssemblyDetailDO::getOptCode, noCancelDetail);
                    stockAssemblyDetailMapper.update(null, detailUpdate);

                    LambdaUpdateWrapper<StockAssemblyDO> applyUpdate = Wrappers.lambdaUpdate();
                    applyUpdate.set(StockAssemblyDO::getStatus, 9)
                            .set(StockAssemblyDO::getUpdateUser, userDTO.getUserNo())
                            .set(StockAssemblyDO::getUpdateTime, updateTime);
                    applyUpdate.eq(StockAssemblyDO::getId, applyId)
                            .in(StockAssemblyDO::getStatus, canCancelApply);
                    stockAssemblyMapper.update(null, applyUpdate);
                    log.info("取消组换调库申请 {}", applyId);
                } catch (Exception e) {
                    log.error("组换调库申请取消异常: {}", e.getMessage(), e);
                    error.append(applyInfo.getApplyNo()).append("取消失败; ");
                    transactionStatus.setRollbackOnly();
                }
            });
            success.append(applyInfo.getApplyNo()).append("取消成功; ");
        }
        return error.length() > 0 ? ResultVo.success("取消失败: " + error.toString() + success.toString()) : ResultVo.success(success.toString());
    }

    @Override
    public void removeStockAssemblyDetail(List<Long> detailIds) {
        LambdaUpdateWrapper<StockAssemblyDetailDO> detailUpdateWrapper = Wrappers.lambdaUpdate();
        detailUpdateWrapper.set(StockAssemblyDetailDO::getOptCode, 9); // 逻辑删除
        detailUpdateWrapper.in(StockAssemblyDetailDO::getId, detailIds)
                .notIn(StockAssemblyDetailDO::getOptCode, 5, 6, 8);
        stockAssemblyDetailMapper.update(new StockAssemblyDetailDO(), detailUpdateWrapper);
    }

    @Override
    public ResultVo<PageInfo<StockAssemblyApplyDTO>> listStockAssembly(StockAssemblyRequest request) {
        if (request.getToDate() != null) {
            request.setToDate(DateUtil.getEndTime(request.getToDate()));
        }
        if (request.getDateType() == null) {
            request.setDateType(0);
        }

        LambdaQueryWrapper<StockAssemblyDO> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(StringUtils.isNotEmpty(request.getApplyNo()), StockAssemblyDO::getApplyNo, request.getApplyNo())
                .eq(StringUtils.isNotEmpty(request.getNeedModelNo()), StockAssemblyDO::getNeedModelNo, request.getNeedModelNo())
                .eq(StringUtils.isNotEmpty(request.getNeedForOrderNo()), StockAssemblyDO::getNeedForOrderNo, request.getNeedForOrderNo())
                .eq(StringUtils.isNotEmpty(request.getDeptNo()), StockAssemblyDO::getDeptNo, request.getDeptNo())
                .eq(StringUtils.isNotEmpty(request.getStatus()), StockAssemblyDO::getStatus, request.getStatus())
                .eq(StringUtils.isNotEmpty(request.getAssembleType()), StockAssemblyDO::getAssembleType, request.getAssembleType())
                .eq(StringUtils.isNotBlank(request.getBillNo()), StockAssemblyDO::getBillNo, request.getBillNo());
        // .ne(StockAssemblyDO::getStatus, 9);
        if (request.getDateType() == 1) {
            queryWrapper.ge((request.getFromDate() != null), StockAssemblyDO::getApplyDate, request.getFromDate())
                    .le((request.getToDate() != null), StockAssemblyDO::getApplyDate, request.getToDate());
        }
        if (request.getDateType() == 2) {
            queryWrapper.ge((request.getFromDate() != null), StockAssemblyDO::getTransTime, request.getFromDate())
                    .le((request.getToDate() != null), StockAssemblyDO::getTransTime, request.getToDate());
        }
        queryWrapper.orderByDesc(StockAssemblyDO::getApplyDate);

        PageHelper.startPage(request.getPageNum(), request.getPageSize());
        List<StockAssemblyDO> list = stockAssemblyMapper.selectList(queryWrapper);
        PageInfo<StockAssemblyDO> pageInfo = PageInfo.of(list);
        PageInfo<StockAssemblyApplyDTO> voPageInfo = BeanCopyUtil.pageDto2Vo(pageInfo, StockAssemblyApplyDTO.class);

        Map<String, String> nameMap = new HashMap<>();
        for (StockAssemblyApplyDTO dto : voPageInfo.getList()) {
            // 部门名称
            if (!nameMap.containsKey(dto.getDeptNo())) {
                nameMap.put(dto.getDeptNo(), opsCommonService.getDeptNameByNo(dto.getDeptNo()));
            }
            dto.setDeptNo(nameMap.getOrDefault(dto.getDeptNo(), dto.getDeptNo()));
        }

        return ResultVo.success(voPageInfo);
    }

    @Override
    public StockAssemblyApplyDTO getStockAssemblyApply(Long applyId) {
        LambdaQueryWrapper<StockAssemblyDO> applyQueryWrapper = Wrappers.lambdaQuery();
        applyQueryWrapper.eq(StockAssemblyDO::getId, applyId)
                .ne(StockAssemblyDO::getStatus, 9);
        StockAssemblyDO applyInfo = stockAssemblyMapper.selectOne(applyQueryWrapper);
        StockAssemblyApplyDTO dto = BeanCopyUtil.copy(applyInfo, StockAssemblyApplyDTO.class);
        dto.setOutItems(Collections.emptyList());
        dto.setInItems(Collections.emptyList());

        /*LambdaQueryWrapper<StockAssemblyDetailDO> detailQueryWrapper = Wrappers.lambdaQuery();
        detailQueryWrapper.eq(StockAssemblyDetailDO::getApplyId, applyId)
                .ne(StockAssemblyDetailDO::getOptCode, 9);
        List<StockAssemblyDetailDO> detailDOList = stockAssemblyDetailMapper.selectList(detailQueryWrapper);

        List<StockAssemblyItemDTO> outItems = new ArrayList<>();
        List<StockAssemblyItemDTO> inItems = new ArrayList<>();
        StockAssemblyItemDTO item;
        for (StockAssemblyDetailDO detailDO : detailDOList) {
            item = BeanCopyUtil.copy(detailDO, StockAssemblyItemDTO.class);
            splitInventoryKeys(item);
            if (item.getIsTransOut()) {
                outItems.add(item);
            } else {
                inItems.add(item);
            }
        }
        dto.setOutItems(outItems);
        dto.setInItems(inItems);*/
        return dto;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public ResultVo<PageInfo<StockAssemblyItemDTO>> listStockAssemblyApplyDetail(StockAssemblyRequest request) {
          //    <!--add by WuWeiDong 20240111  bug 12889  根据申请号查询 -->
        if (StringUtils.isNotBlank(request.getApplyNo()) && ObjectUtils.isEmpty(request.getId())){
            LambdaQueryWrapper<StockAssemblyDO> queryMaster = Wrappers.lambdaQuery();
            queryMaster.eq(StockAssemblyDO::getApplyNo,request.getApplyNo())
                    .select(StockAssemblyDO::getId);
            StockAssemblyDO stockAssemblyDO=stockAssemblyMapper.selectOne(queryMaster);
            if (ObjectUtils.isNotEmpty(stockAssemblyDO)){
                request.setId(stockAssemblyDO.getId());
            }
        }

        LambdaQueryWrapper<StockAssemblyDetailDO> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(StockAssemblyDetailDO::getApplyId, request.getId()) // 申请ID
                .eq(ObjectUtils.isNotEmpty(request.getIsTransOut()), StockAssemblyDetailDO::getIsTransOut, request.getIsTransOut()) // 调出/调入申请项
                .in(CollectionUtils.isNotEmpty(request.getModelNos()) , StockAssemblyDetailDO::getModelNo, request.getModelNos())
                .ne(StockAssemblyDetailDO::getOptCode, 9); // 排除已取消的申请项
        PageHelper.startPage(request.getPageNum(), request.getPageSize());
        List<StockAssemblyDetailDO> detailList = stockAssemblyDetailMapper.selectList(queryWrapper);
        PageInfo<StockAssemblyDetailDO> pageInfo = PageInfo.of(detailList);
        PageInfo<StockAssemblyItemDTO> voPageInfo = BeanCopyUtil.pageDto2Vo(pageInfo, StockAssemblyItemDTO.class);
        for (StockAssemblyItemDTO item : voPageInfo.getList()) {
            this.splitInventoryKeys(item);
        }
        return ResultVo.success(voPageInfo);
    }

    @Override
//     @Transactional(rollbackFor = Exception.class)
    public ResultVo<String> submitStockAssembly(StockAssemblyApplyDTO data) {
        LambdaQueryWrapper<StockAssemblyDO> applyQuery = Wrappers.lambdaQuery();
        applyQuery.select(StockAssemblyDO::getId, StockAssemblyDO::getApplyType, StockAssemblyDO::getAssembleType,
                StockAssemblyDO::getStatus);
        applyQuery.eq(StockAssemblyDO::getId, data.getId());
        StockAssemblyDO applyInfo = stockAssemblyMapper.selectOne(applyQuery);
        if (applyInfo == null) {
            return ResultVo.failure("提交失败: 申请不存在");
        }
        if (!"1".equals(applyInfo.getStatus())) {
            return ResultVo.success("申请已提交");
        }

        LambdaQueryWrapper<StockAssemblyDetailDO> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.select(StockAssemblyDetailDO::getId, StockAssemblyDetailDO::getModelNo, StockAssemblyDetailDO::getIsTransOut,
                        StockAssemblyDetailDO::getQuantity, StockAssemblyDetailDO::getWarehouseCode, StockAssemblyDetailDO::getInventoryKeys)
                .eq(StockAssemblyDetailDO::getApplyId, data.getId())
                .ne(StockAssemblyDetailDO::getOptCode, "9");
        List<StockAssemblyDetailDO> detailList = stockAssemblyDetailMapper.selectList(queryWrapper);
        if (CollectionUtils.isEmpty(detailList)) {
            return ResultVo.failure("提交失败: 申请项不能为空");
        }
        ResultVo<String> checkResult = this.checkApplyDetail(applyInfo.getApplyType(), detailList);
        if (!checkResult.isSuccess()) {
            checkResult.setMessage("提交失败:</br>" + checkResult.getMessage());
            return checkResult;
        }

        // Add by Dengdenghui 2022-11-18 for bug-8712
        // 如果是（仅财务,仅WMS）申请类型，不做在库库存判断
        boolean isFinanceOnly = StockAssemblyAssemblyTypeEnum.ASSEMBLY_FINANCE_ONLY.getCode().equals(applyInfo.getAssembleType());
        boolean isWMSOnly = StockAssemblyAssemblyTypeEnum.ASSEMBLY_WMS_ONLY.getCode().equals(applyInfo.getAssembleType());
        if (!isFinanceOnly && !isWMSOnly) {
            try {
                long startTimer = System.currentTimeMillis();
                //bugid:16354 c14717 20250103 && 16651 先验证cn0 如果失败在验证CNG 原则全部 出单一库存
                //调库
                boolean tkFlag = StockAssemblyAssemblyTypeEnum.TRANSFER.getCode().equals(applyInfo.getAssembleType());
                checkResult = this.checkTransOutStock(detailList, true,tkFlag);
                if (!checkResult.isSuccess() && !tkFlag){
                    checkResult = this.checkTransOutStock(detailList, false,tkFlag);
                }
                System.out.println(" *****  checkTransOutStock  查询结束耗时(s): " + (System.currentTimeMillis() - startTimer) / 1000.0);
                if (!checkResult.isSuccess()) {
                    checkResult.setMessage("提交失败:</br>" + checkResult.getMessage());
                    return checkResult;
                }
            } catch (Exception ex) {
                return ResultVo.failure("提交失败:" + ex);
            }

        } // End
        return this.submitUpdateStatus(applyInfo, detailList.get(0).getWarehouseCode());
//        LoginUserDTO userDto = SMCApp.getLoginAuthDto();
//
//        int status = 2; // 待确认
//        LambdaUpdateWrapper<StockAssemblyDO> updateWrapper = Wrappers.lambdaUpdate();
//        updateWrapper.set(StockAssemblyDO::getStatus, status)
//                .set("1".equals(applyInfo.getApplyType()), StockAssemblyDO::getWarehouseCode, detailList.get(0).getWarehouseCode())
//                .set(StockAssemblyDO::getApplyDate, new Date())
//                .set(StockAssemblyDO::getApplyPsn, userDto.getUserNo())
//                .set(StockAssemblyDO::getUpdateUser, userDto.getUserNo());
//        updateWrapper.eq(StockAssemblyDO::getId, applyInfo.getId())
//                .eq(StockAssemblyDO::getStatus, 1); // 编辑中
//        stockAssemblyMapper.update(new StockAssemblyDO(), updateWrapper);
//
//        int optCode = 1; // 待审核确认
//        LambdaUpdateWrapper<StockAssemblyDetailDO> detailUpdate = Wrappers.lambdaUpdate();
//        detailUpdate.set(StockAssemblyDetailDO::getOptCode, optCode)
//                .set(StockAssemblyDetailDO::getUpdateUser, userDto.getUserNo());
//        detailUpdate.eq(StockAssemblyDetailDO::getApplyId, applyInfo.getId())
//                .eq(StockAssemblyDetailDO::getOptCode, 0); // 编辑中
//        stockAssemblyDetailMapper.update(new StockAssemblyDetailDO(), detailUpdate);
//        log.info("提交组换调库申请 {}", applyInfo.getId());
//        return ResultVo.success(applyInfo.getId().toString(), "申请提交成功");
    }

    //<!--add by WuWeiDong 20230508 bug 10678  读写分离 -->
    private ResultVo<String> submitUpdateStatus(StockAssemblyDO applyInfo, String warehouseCode) {
        final LoginUserDTO userDto = SMCApp.getLoginAuthDto();
        final int status = 2; // 待确认
        final int optCode = 1; // 待审核确认
        return transactionTemplate.execute(action -> {
            try {
                LambdaUpdateWrapper<StockAssemblyDO> updateWrapper = Wrappers.lambdaUpdate();
                updateWrapper.set(StockAssemblyDO::getStatus, status)
                        .set("1".equals(applyInfo.getApplyType()), StockAssemblyDO::getWarehouseCode, warehouseCode)
                        .set(StockAssemblyDO::getApplyDate, new Date())
                        .set(StockAssemblyDO::getApplyPsn, userDto.getUserNo())
                        .set(StockAssemblyDO::getUpdateUser, userDto.getUserNo());
                updateWrapper.eq(StockAssemblyDO::getId, applyInfo.getId())
                        .eq(StockAssemblyDO::getStatus, 1); // 编辑中
                stockAssemblyMapper.update(new StockAssemblyDO(), updateWrapper);


                LambdaUpdateWrapper<StockAssemblyDetailDO> detailUpdate = Wrappers.lambdaUpdate();
                detailUpdate.set(StockAssemblyDetailDO::getOptCode, optCode)
                        .set(StockAssemblyDetailDO::getUpdateUser, userDto.getUserNo());
                detailUpdate.eq(StockAssemblyDetailDO::getApplyId, applyInfo.getId())
                        .eq(StockAssemblyDetailDO::getOptCode, 0); // 编辑中
                stockAssemblyDetailMapper.update(new StockAssemblyDetailDO(), detailUpdate);
                log.info("提交组换调库申请 {}", applyInfo.getId());
                return ResultVo.success(applyInfo.getId().toString(), "申请提交成功");
            } catch (Exception e) {
                action.setRollbackOnly();
                log.error("申请提交异常: {} {}", applyInfo.getApplyNo(), e.getMessage(), e);
                return ResultVo.failure("申请提交失败");
            }
        });
    }

    // Add by Dengdenghui 2022-11-18 for bug-8712
    @Override
    @Transactional
    public ResultVo<String> approveStockAssembly(StockAssemblyHandleDTO dto) {
        if ("4".equals(dto.getHandleType())) {
            dto.setHandleType("1"); // 待确认-退回
            return returnApply(dto);
        }
        LoginUserDTO userDto = SMCApp.getLoginAuthDto();
        QueryWrapper<StockAssemblyDO> applyQuery = Wrappers.query();
        String[] applySelect = new String[]{"Top(1) id", "ApplyType", "AssembleType", "Status"};
        StockAssemblyDO applyInfo;
        LambdaUpdateWrapper<StockAssemblyDetailDO> detailUpdate = Wrappers.lambdaUpdate();
        LambdaUpdateWrapper<StockAssemblyDO> updateWrapper = Wrappers.lambdaUpdate();
        Date now = new Date();
        int result = 0;

        for (Long applyId : dto.getApplyIds()) {
            applyQuery.clear();
            applyQuery.select(applySelect);
            applyQuery.lambda().eq(StockAssemblyDO::getId, applyId);
            applyInfo = stockAssemblyMapper.selectOne(applyQuery);
            if (applyInfo == null || !StockAssemblyApplyStatusEnum.approving.getCode().equals(applyInfo.getStatus())) {
                continue;
            }

            // 如果是（仅财务）申请类型，跳过处理
            boolean isFinanceOnly = StockAssemblyAssemblyTypeEnum.ASSEMBLY_FINANCE_ONLY.getCode().equals(applyInfo.getAssembleType());
            //String invoiceNo = null; Edit by Dengdenghui 2022-11-29 bug-8822
            //if (isFinanceOnly) {
            //    invoiceNo = this.getInvoiceNo(applyInfo.getId(), applyInfo.getApplyType());
            //}

            int optCode = isFinanceOnly ? 6 : 2;
            detailUpdate.clear();
            detailUpdate.set(StockAssemblyDetailDO::getOptCode, optCode);
            //.set(isFinanceOnly, StockAssemblyDetailDO::getInvoiceNo, invoiceNo);
            detailUpdate.eq(StockAssemblyDetailDO::getApplyId, applyId)
                    .eq(StockAssemblyDetailDO::getOptCode, 1); // 待审核确认
            stockAssemblyDetailMapper.update(new StockAssemblyDetailDO(), detailUpdate);

            int status = isFinanceOnly ? 6 : 3;
            updateWrapper.clear();
            updateWrapper.set(StockAssemblyDO::getStatus, status)
                    //.set(isFinanceOnly, StockAssemblyDO::getBillNo, invoiceNo)
                    .set(StringUtils.isNotEmpty(dto.getAnswerText()), StockAssemblyDO::getAnswerText, dto.getAnswerText())
                    .set(StockAssemblyDO::getAnswerDate, now)
                    .set(StockAssemblyDO::getApproveDate, now)
                    .set(StockAssemblyDO::getApprovePsn, userDto.getUserNo())
                    .set(StockAssemblyDO::getUpdateUser, userDto.getUserNo());
            updateWrapper.eq(StockAssemblyDO::getId, applyId)
                    .eq(StockAssemblyDO::getStatus, 2); // 待确认
            result += stockAssemblyMapper.update(new StockAssemblyDO(), updateWrapper);
        }

        return result > 0 ? ResultVo.success("审核确认") : ResultVo.failure("审核确认失败: 不存在待确认申请");
    } // End

    @Override
    public ResultVo<String> handleApply(StockAssemblyHandleDTO dto) {
        if ("4".equals(dto.getHandleType())) {
            dto.setHandleType("3"); // 已确认-退回
            return returnApply(dto);
        }
        // 处理
        StringBuilder msg = new StringBuilder();
        int status = 3; // 审核确认
        LambdaQueryWrapper<StockAssemblyDO> masterQueryWrapper = Wrappers.lambdaQuery();
        StockAssemblyDO applyInfo;
        ResultVo<String> result;
        String keyPrefix = "ops:rediss:stockAssembly:processing:";
        String key;

        for (Long applyId : dto.getApplyIds()) {
            key = keyPrefix + applyId;

            if (redissonUtil.tryLock(key, 0, 0)) {
                try {
                    masterQueryWrapper.clear();
                    masterQueryWrapper.eq(StockAssemblyDO::getId, applyId)
                            .eq(StockAssemblyDO::getStatus, status);
                    applyInfo = stockAssemblyMapper.selectOne(masterQueryWrapper);
                    if (applyInfo == null) {
                        msg.append(applyId).append("申请未审核确认<br/>");
                        continue;
                    }

                    switch (applyInfo.getApplyType()) {
                        case "1":
                            result = handleAssembly(applyInfo, dto);
                            break;
                        case "2":
                            result = handleTransfer(applyInfo, dto);
                            break;
                        default:
                            result = ResultVo.failure("未知申请类型");
                            break;
                    }
                    if (!result.isSuccess()) {
                        msg.append(applyInfo.getApplyNo()).append("  ").append(result.getMessage()).append("<br/>");
                    }
                } finally {
                    redissonUtil.unlock(key);
                }
            } else {
                msg.append("申请").append(applyId).append("正在处理，请耐心等待<br/>");
            }
        }
        return msg.length() > 0 ? ResultVo.failure(msg.toString()) : ResultVo.success("处理成功");
    }

    @Override
    public ResultVo<String> returnApply(StockAssemblyHandleDTO dto) {
        LoginUserDTO userDto = SMCApp.getLoginAuthDto();
        Date now = new Date();
        StringBuilder result = new StringBuilder("执行退回: <br/>");

        for (Long applyId : dto.getApplyIds()) {
            LambdaQueryWrapper<StockAssemblyDO> applyQuery = Wrappers.lambdaQuery();
            applyQuery.select(StockAssemblyDO::getId, StockAssemblyDO::getApplyNo);
            applyQuery.eq(StockAssemblyDO::getId, applyId);
            StockAssemblyDO applyInfo = stockAssemblyMapper.selectOne(applyQuery);
            result.append(applyInfo.getApplyNo());

            // 当申请开始处理后，不可以操作退回申请
            LambdaQueryWrapper<StockAssemblyDetailDO> detailQuery = Wrappers.lambdaQuery();
            detailQuery.eq(StockAssemblyDetailDO::getApplyId, applyId)
                    .in(StockAssemblyDetailDO::getOptCode, 5, 6, 7, 8);
            int count = stockAssemblyDetailMapper.selectCount(detailQuery);
            if (count > 0) {
                result.append("不可退回<br/>");
                continue;
            }

            LambdaUpdateWrapper<StockAssemblyDetailDO> detailUpdate = Wrappers.lambdaUpdate();
            detailUpdate.set(StockAssemblyDetailDO::getOptCode, 0); // 退回编辑
            detailUpdate.eq(StockAssemblyDetailDO::getApplyId, applyId)
                    .in(StockAssemblyDetailDO::getOptCode, 1, 2);
            stockAssemblyDetailMapper.update(new StockAssemblyDetailDO(), detailUpdate);

            LambdaUpdateWrapper<StockAssemblyDO> masterUpdateWrapper = Wrappers.lambdaUpdate();
            masterUpdateWrapper.set(StockAssemblyDO::getStatus, 1) // 编辑中
                    .set(StringUtils.isNotEmpty(dto.getAnswerText()), StockAssemblyDO::getAnswerText, dto.getAnswerText())
                    .set(StringUtils.isNotEmpty(dto.getAnswerText()), StockAssemblyDO::getAnswerDate, now)
                    .set(StockAssemblyDO::getApproveDate, now)
                    .set(StockAssemblyDO::getApprovePsn, userDto.getUserNo())
                    .set(StockAssemblyDO::getUpdateUser, userDto.getUserNo());
            masterUpdateWrapper.eq(StockAssemblyDO::getId, applyId)
                    .in(StockAssemblyDO::getStatus, 2, 3);
            stockAssemblyMapper.update(new StockAssemblyDO(), masterUpdateWrapper);
            result.append("退回成功<br/>");
        }

        return ResultVo.success(result.toString());
    }

    @Override
    public ResultVo<String> handleTransfer(StockAssemblyDO applyInfo, StockAssemblyHandleDTO dto) {
        LambdaQueryWrapper<StockAssemblyDetailDO> detailQueryWrapper = Wrappers.lambdaQuery();
        detailQueryWrapper.eq(StockAssemblyDetailDO::getApplyId, applyInfo.getId())
                .ne(StockAssemblyDetailDO::getOptCode, 9); // 排除已取消的
        List<StockAssemblyDetailDO> detailList = stockAssemblyDetailMapper.selectList(detailQueryWrapper);
        if (CollectionUtils.isEmpty(detailList)) {
            return ResultVo.failure("调库失败: 申请项不能为空");
        }

        Map<Integer, List<StockAssemblyDetailDO>> itemMap = this.handleTransferItem(detailList);
        List<Integer> itemNos = itemMap.keySet().stream().sorted().collect(Collectors.toList());

        StringBuilder msg = new StringBuilder();
        ResultVo<String> resultVo;

        for (Integer itemNo : itemNos) {
            if (itemMap.get(itemNo).get(0).getOptCode() == 2) {
                long startTime = System.currentTimeMillis();
                log.info("执行调拨处理 {}-{} {}", applyInfo.getApplyNo(), itemNo, itemMap.get(itemNo).get(0).getModelNo());
                resultVo = transactionTemplate.execute(action -> {
                    try {
                        return this.createTransferOrder(applyInfo, itemNo, itemMap.get(itemNo));
                    } catch (Exception e) {
                        action.setRollbackOnly();
                        log.error("调库异常: {}-{} {}", applyInfo.getApplyNo(), itemNo, itemMap.get(itemNo).get(0).getModelNo(), e.getMessage(), e);
                        return ResultVo.failure("发生异常-" + e.getMessage());
                    }
                });
                log.info("调拨处理 {}-{} {} 耗时 {}ms, 响应: {}", applyInfo.getApplyNo(), itemNo, itemMap.get(itemNo).get(0).getModelNo(), System.currentTimeMillis() - startTime, JSON.toJSONString(resultVo));
                if (resultVo == null || !resultVo.isSuccess()) {
                    msg.append(itemMap.get(itemNo).get(0).getModelNo());
                    if (resultVo == null) {
                        msg.append("调库失败.").append("<br/>");
                    } else {
                        msg.append("调库失败: ").append(resultVo.getMessage()).append("<br/>");
                    }
                }
            }
        }

        // 判断是否全部处理完成
        QueryWrapper<StockAssemblyDetailDO> detailQuery = Wrappers.query();
        detailQuery.select("Top(1) Id");
        detailQuery.lambda()
                .eq(StockAssemblyDetailDO::getApplyId, applyInfo.getId())
                .eq(StockAssemblyDetailDO::getOptCode, 2);
        StockAssemblyDetailDO detailOne = stockAssemblyDetailMapper.selectOne(detailQuery);
        if (detailOne == null) {
            Date now = new Date();
            String invoiceNo = this.getInvoiceNo(applyInfo.getId());

            LambdaUpdateWrapper<StockAssemblyDO> masterUpdateWrapper = Wrappers.lambdaUpdate();
            masterUpdateWrapper.set(StockAssemblyDO::getStatus, 6)
                    .set(StockAssemblyDO::getBillNo, invoiceNo)
                    .set(StockAssemblyDO::getTransTime, now)
                    .set(StockAssemblyDO::getTransPsn, SMCApp.getLoginAuthDto().getUserNo())
                    .set(StringUtils.isNotEmpty(dto.getAnswerText()), StockAssemblyDO::getAnswerText, dto.getAnswerText())
                    .set(StringUtils.isNotEmpty(dto.getAnswerText()), StockAssemblyDO::getAnswerDate, now)
                    .set(StockAssemblyDO::getUpdateUser, SMCApp.getLoginAuthDto().getUserNo());
            masterUpdateWrapper.eq(StockAssemblyDO::getApplyNo, applyInfo.getApplyNo())
                    .eq(StockAssemblyDO::getStatus, 3);
            stockAssemblyMapper.update(new StockAssemblyDO(), masterUpdateWrapper);
        }

        if (msg.length() == 0) {
            return ResultVo.success("处理成功");
        } else {
            return ResultVo.failure("处理完成. " + msg.toString());
        }
    }

//    /**
//     * @param applyInfo  申请信息
//     * @param detailList 某一型号的调库项
//     * @return 调库结果
//     */
//    private ResultVo<String> createTransferOrder(StockAssemblyDO applyInfo, List<StockAssemblyDetailDO> detailList) {
//        // 按调出/入分组
//        Map<Boolean, List<StockAssemblyDetailDO>> transferTypeMap = detailList.stream()
//                .collect(Collectors.partitioningBy(StockAssemblyDetailDO::getIsTransOut));
//
//        // 调库处理列表
//        Map<String, TransOrderVO> transferMap = new HashMap<>();
//        TransOrderVO transDto;
//        String[] inventoryKeys;
//        OpsInventoryProperty propertyInfo;
//        // 调库项号
//        int itemNo = this.getTransferItemNo(applyInfo.getApplyNo());
//
//        // 循环分配调入库存
//        for (StockAssemblyDetailDO in : transferTypeMap.get(Boolean.FALSE)) {
//            for (StockAssemblyDetailDO out : transferTypeMap.get(Boolean.TRUE)) {
//                if (in.getQuantity() == 0) {
//                    break; // 当前调入项已完成处理,开始下一项
//                }
//                if (out.getQuantity() == 0) {
//                    continue; // 当前出库项数量=0,跳过
//                }
//
//                double quantity = in.getQuantity() + out.getQuantity();
//                int transQty = 0;
//                if (quantity == 0) {
//                    transQty = in.getQuantity().intValue();
//                    in.setQuantity(0d);
//                    out.setQuantity(0d);
//                }
//                if (quantity > 0) {
//                    transQty = Math.abs(out.getQuantity().intValue());
//                    in.setQuantity(quantity);
//                    out.setQuantity(0d);
//                }
//                if (quantity < 0) {
//                    transQty = in.getQuantity().intValue();
//                    in.setQuantity(0d);
//                    out.setQuantity(quantity);
//                }
//
//                itemNo++;
//                transDto = new TransOrderVO();
//                transDto.setTransType(1);
//                transDto.setTransNo(applyInfo.getApplyNo());
//                transDto.setItemNo(itemNo);
//                transDto.setModelNo(out.getModelNo());
//                transDto.setQuantity(transQty);
//                transDto.setStatus(0);
//                transDto.setFromNo(applyInfo.getApplyNo());
//                transDto.setFromId(applyInfo.getId());
//                transDto.setFromType(2);
//                // 调出库存属性
//                inventoryKeys = out.getInventoryKeys().split("~", -1);
//                transDto.setFromWarehouseCode(inventoryKeys[1]);
//                propertyInfo = this.getInventoryProperty(inventoryKeys[0], inventoryKeys[2], inventoryKeys[3], inventoryKeys[4], inventoryKeys[5]);
//                transDto.setFromInventoryPropertyId(propertyInfo.getInventoryPropertyId());
//                transDto.setFromInventoryTypeCode(propertyInfo.getInventoryTypeCode());
//                transDto.setFromCustomerNo(propertyInfo.getCustomerNo());
//                transDto.setFromGroupCustomerNo(propertyInfo.getGroupCustomerNo());
//                transDto.setFromPpl(propertyInfo.getPpl());
//                transDto.setFromProjectCode(propertyInfo.getProjectCode());
//                // 调入库存属性
//                inventoryKeys = in.getInventoryKeys().split("~", -1);
//                transDto.setToWarehouseCode(inventoryKeys[1]);
//                propertyInfo = this.getInventoryProperty(inventoryKeys[0], inventoryKeys[2], inventoryKeys[3], inventoryKeys[4], inventoryKeys[5]);
//                transDto.setToInventoryPropertyId(propertyInfo.getInventoryPropertyId());
//                transDto.setToInventoryTypeCode(propertyInfo.getInventoryTypeCode());
//                transDto.setToCustomerNo(propertyInfo.getCustomerNo());
//                transDto.setToGroupCustomerNo(propertyInfo.getGroupCustomerNo());
//                transDto.setToPpl(propertyInfo.getPpl());
//                transDto.setToProjectCode(propertyInfo.getProjectCode());
//
//                transferMap.put(out.getId() + "-" + in.getId(), transDto);
//            }
//        }
//
//        List<TransOrderVO> transDtoList;
//        ResultVo<String> transResult;
//        for (Map.Entry<String, TransOrderVO> orderMap : transferMap.entrySet()) {
//            transDto = orderMap.getValue();
//            transDtoList = Collections.singletonList(transDto);
//            log.info("组换调库-调拨处理 {} {} {} data = {}", applyInfo.getApplyNo(), orderMap.getKey(), transDto.getModelNo(), JSON.toJSONString(transDtoList));
//            transResult = transStockFeignApi.transStock(transDtoList);
//            log.info("组换调库-调拨处理 {} {} {} 响应 = {}", applyInfo.getApplyNo(), orderMap.getKey(), transDto.getModelNo(), JSON.toJSONString(transResult));
//            if (!transResult.isSuccess()) {
//                return ResultVo.failure(transResult.getMessage());
//            }
//
//            boolean result = true;
//            StringBuilder msg = new StringBuilder();
//            // Add by DengDenghui 2022-11-14 for bug-8650
//            Map<String, String> resultMap = JSON.parseObject(transResult.getData(), new TypeReference<Map<String, String>>() {
//            });
//            for (String value : resultMap.values()) {
//                if (StringUtils.isBlank(value) || "成功".equals(value) || value.contains("trans_order已存在")) {
//                    // result = true;
//                } else {
//                    result = false;
//                    msg.append(value).append(".");
//                }
//            }
//
//            if (result) {
//                String[] detailIds = orderMap.getKey().split("-");
//                String invoiceNo = this.getInvoiceNo(applyInfo.getId(), applyInfo.getApplyType());
//
//                LambdaUpdateWrapper<StockAssemblyDetailDO> detailUpdateWrapper = Wrappers.lambdaUpdate();
//                detailUpdateWrapper.set(StockAssemblyDetailDO::getOptCode, 6) // 已完成
//                        .set(StockAssemblyDetailDO::getInvoiceNo, invoiceNo)
//                        .set(StockAssemblyDetailDO::getTransTime, new Date())
//                        .set(StockAssemblyDetailDO::getUpdateUser, SMCApp.getLoginAuthDto().getUserNo());
//                detailUpdateWrapper.in(StockAssemblyDetailDO::getId, Long.parseLong(detailIds[0]), Long.parseLong(detailIds[1]));
//                stockAssemblyDetailMapper.update(new StockAssemblyDetailDO(), detailUpdateWrapper);
//
//            } else {
//                if (msg.toString().contains("可用数量不足")) {
//                    String[] detailIds = orderMap.getKey().split("-");
//
//                    LambdaUpdateWrapper<StockAssemblyDetailDO> detailUpdateWrapper = Wrappers.lambdaUpdate();
//                    detailUpdateWrapper.set(StockAssemblyDetailDO::getOptCode, 7) // 调库失败
//                            .set(StockAssemblyDetailDO::getTransTime, new Date())
//                            .set(StockAssemblyDetailDO::getUpdateUser, SMCApp.getLoginAuthDto().getUserNo());
//                    detailUpdateWrapper.in(StockAssemblyDetailDO::getId, Long.parseLong(detailIds[0]), Long.parseLong(detailIds[1]));
//                    stockAssemblyDetailMapper.update(new StockAssemblyDetailDO(), detailUpdateWrapper);
//                } else {
//                    throw new BusinessException(msg.toString());
//                }
//            }
//        }
//        return ResultVo.success("调库成功");
//    }

    /**
     * @param applyInfo  申请信息
     * @param itemNo     项号
     * @param detailList 某一型号的调库项
     * @return 调库结果
     */
    private ResultVo<String> createTransferOrder(StockAssemblyDO applyInfo, Integer itemNo, List<StockAssemblyDetailDO> detailList) {
        // 按调出/入分组
        StockAssemblyDetailDO out = detailList.get(0);
        StockAssemblyDetailDO in = detailList.get(1);

        // 调库处理列表
        String[] inventoryKeys;
        OpsInventoryProperty propertyInfo;
        // 分配调入库存
        int transQty = in.getQuantity().intValue();

        TransOrderVO transDto = new TransOrderVO();
        transDto.setTransType(1);
        transDto.setTransNo(applyInfo.getApplyNo());
        transDto.setItemNo(itemNo);
        transDto.setModelNo(out.getModelNo());
        transDto.setQuantity(transQty);
        transDto.setStatus(0);
        transDto.setFromNo(applyInfo.getApplyNo());
        transDto.setFromId(applyInfo.getId());
        transDto.setFromType(2);
        transDto.setWmsDlvDate(applyInfo.getDlvDate());
        // 设置调出库存属性
        inventoryKeys = out.getInventoryKeys().split("~", -1);
        transDto.setFromWarehouseCode(inventoryKeys[1]);
        propertyInfo = this.getInventoryProperty(inventoryKeys[0], inventoryKeys[2], inventoryKeys[3], inventoryKeys[4], inventoryKeys[5]);
        transDto.setFromInventoryPropertyId(propertyInfo.getInventoryPropertyId());
        transDto.setFromInventoryTypeCode(propertyInfo.getInventoryTypeCode());
        transDto.setFromCustomerNo(propertyInfo.getCustomerNo());
        transDto.setFromGroupCustomerNo(propertyInfo.getGroupCustomerNo());
        transDto.setFromPpl(propertyInfo.getPpl());
        transDto.setFromProjectCode(propertyInfo.getProjectCode());
        // 设置调入库存属性
        inventoryKeys = in.getInventoryKeys().split("~", -1);
        transDto.setToWarehouseCode(inventoryKeys[1]);
        propertyInfo = this.getInventoryProperty(inventoryKeys[0], inventoryKeys[2], inventoryKeys[3], inventoryKeys[4], inventoryKeys[5]);
        transDto.setToInventoryPropertyId(propertyInfo.getInventoryPropertyId());
        transDto.setToInventoryTypeCode(propertyInfo.getInventoryTypeCode());
        transDto.setToCustomerNo(propertyInfo.getCustomerNo());
        transDto.setToGroupCustomerNo(propertyInfo.getGroupCustomerNo());
        transDto.setToPpl(propertyInfo.getPpl());
        transDto.setToProjectCode(propertyInfo.getProjectCode());

        List<TransOrderVO> transDtoList = Collections.singletonList(transDto);
        log.info("组换调库-调拨处理 {}-{} {}-{} {} data = {}", applyInfo.getApplyNo(), itemNo, out.getId(), in.getId(), transDto.getModelNo(), JSON.toJSONString(transDtoList));
        ResultVo<String> transResult = transStockFeignApi.transStock(transDtoList);
        log.info("组换调库-调拨处理 {}-{} {}-{} {} 响应 = {}", applyInfo.getApplyNo(), itemNo, out.getId(), in.getId(), transDto.getModelNo(), JSON.toJSONString(transResult));
        if (!transResult.isSuccess()) {
            return ResultVo.failure(transResult.getMessage());
        }

        boolean result = true;
        StringBuilder msg = new StringBuilder();
        // Add by DengDenghui 2022-11-14 for bug-8650
        Map<String, String> resultMap = JSON.parseObject(transResult.getData(), new TypeReference<Map<String, String>>() {
        });
        for (String value : resultMap.values()) {
            if (StringUtils.isBlank(value) || "成功".equals(value) || value.contains("trans_order已存在")) {
                // result = true;
            } else {
                result = false;
                msg.append(value).append(".");
            }
        }

        if (result) {
            String invoiceNo = this.getInvoiceNo(applyInfo.getId(), applyInfo.getApplyType());
            LambdaUpdateWrapper<StockAssemblyDetailDO> detailUpdateWrapper = Wrappers.lambdaUpdate();
            Date now = new Date();
            for (StockAssemblyDetailDO detailInfo : detailList) {
                if (detailInfo.getIsTransOut()) {
                    detailInfo.setInventoryId(this.getInventoryId(transDto.getModelNo(), transDto.getFromWarehouseCode(), transDto.getFromInventoryPropertyId()));
                } else {
                    detailInfo.setInventoryId(this.getInventoryId(transDto.getModelNo(), transDto.getToWarehouseCode(), transDto.getToInventoryPropertyId()));
                }

                detailUpdateWrapper.clear();
                detailUpdateWrapper.set(StockAssemblyDetailDO::getOptCode, 6) // 已完成
                        .set(StockAssemblyDetailDO::getInvoiceNo, invoiceNo)
                        .set(StockAssemblyDetailDO::getAllowOutQty, transDto.getQuantity())
                        .set(StockAssemblyDetailDO::getInventoryId, detailInfo.getInventoryId())
                        .set(StockAssemblyDetailDO::getTransTime, now)
                        .set(StockAssemblyDetailDO::getUpdateUser, SMCApp.getLoginAuthDto().getUserNo())
                        .set(StockAssemblyDetailDO::getUpdateTime, now);
                detailUpdateWrapper.eq(StockAssemblyDetailDO::getId, detailInfo.getId());
                stockAssemblyDetailMapper.update(null, detailUpdateWrapper);
            }
            return ResultVo.success("调库成功");
        } else {
            if (msg.toString().contains("可用数量不足")) {
                Date now = new Date();
                LambdaUpdateWrapper<StockAssemblyDetailDO> detailUpdateWrapper = Wrappers.lambdaUpdate();
                detailUpdateWrapper.set(StockAssemblyDetailDO::getOptCode, 7) // 调库失败
                        .set(StockAssemblyDetailDO::getAllowOutQty, 0)
                        .set(StockAssemblyDetailDO::getTransTime, now)
                        .set(StockAssemblyDetailDO::getRemark, "可用数量不足")
                        .set(StockAssemblyDetailDO::getUpdateUser, SMCApp.getLoginAuthDto().getUserNo())
                        .set(StockAssemblyDetailDO::getUpdateTime, now);
                detailUpdateWrapper.in(StockAssemblyDetailDO::getId, out.getId(), in.getId());
                stockAssemblyDetailMapper.update(null, detailUpdateWrapper);
                return ResultVo.failure(msg.toString());
            } else {
                throw new BusinessException(msg.toString());
            }
        }
    }
    /**
     *     <!--add by WuWeiDong 20240111  bug 12889  返回组换后的数据 -->
     *  返回调入的型号，做生成下发物流指令
     * @param applyNo
     * @return
     */
    @Override
    public  ResultVo<List<InventoryForAdjustDto>> getAssemblyDataForWMS(String applyNo,Boolean isTransOut ){
        if(StringUtils.isBlank(applyNo)){
            return ResultVo.failure("申请号不能为空。");
        }
        log.info("getAssemblyDataForWMS -->传入参考:".concat(applyNo));
        LambdaQueryWrapper<StockAssemblyDO> masterQueryWrapper=new LambdaQueryWrapper<>();
        masterQueryWrapper.eq(StockAssemblyDO::getApplyNo,applyNo);
        StockAssemblyDO applyInfo=stockAssemblyMapper.selectOne(masterQueryWrapper);
        if (ObjectUtils.isEmpty(applyInfo)){
            return ResultVo.failure("无数据，请确认申请号是否正确。申请号：".concat(applyNo));
        }
//        if (applyInfo.getStatus().equalsIgnoreCase(StockAssemblyApplyStatusEnum.cancel.getCode())){
//            return ResultVo.failure("此申请号已取消。申请号：".concat(applyNo));
//        }

        LambdaQueryWrapper<StockAssemblyDetailDO> detailQueryWrapper = Wrappers.lambdaQuery();
        detailQueryWrapper.eq(StockAssemblyDetailDO::getApplyId, applyInfo.getId())
                .eq(StockAssemblyDetailDO::getIsTransOut,isTransOut)
            .eq(StockAssemblyDetailDO::getOptCode,StockAssemblyDetailStatusEnum.confirm.getCode());

        List<StockAssemblyDetailDO> detailList = stockAssemblyDetailMapper.selectList(detailQueryWrapper);
        if  (CollectionUtils.isEmpty(detailList) ) {
            return ResultVo.failure("没有查到组换入的数据");
        }
        List<InventoryForAdjustDto> rkList=new ArrayList<>();
        for (StockAssemblyDetailDO detail : detailList) {
            InventoryForAdjustDto  adjustDto = new InventoryForAdjustDto();
            adjustDto.setOrderId(applyInfo.getApplyNo());
            adjustDto.setQaStatus("0");
            adjustDto.setDeptno(applyInfo.getDeptNo());
            adjustDto.setModelno(detail.getModelNo());
            adjustDto.setQty(Math.abs(detail.getQuantity().intValue()));
            adjustDto.setWarehouseCode(detail.getWarehouseCode());
            adjustDto.setRemark(applyInfo.getRemark());

            // 按 库存类型~仓库代码~客户代码~客户群号~PPL~项目号 拼接

            String[] inventoryKeys = detail.getInventoryKeys().split("~", -1);
            OpsInventoryProperty    propertyInfo=new OpsInventoryProperty();
            if (ObjectUtils.isEmpty(detail.getInventoryId())) {
                propertyInfo = this.getInventoryProperty(inventoryKeys[0], inventoryKeys[2], inventoryKeys[3], inventoryKeys[4], inventoryKeys[5]);
                detail.setInventoryId(this.getInventoryId(adjustDto.getModelno(), detail.getWarehouseCode() ,propertyInfo.getInventoryPropertyId()));
            } else{
                propertyInfo.setInventoryTypeCode(inventoryKeys[0]);
                propertyInfo.setCustomerNo(inventoryKeys[2]);
                propertyInfo.setGroupCustomerNo(inventoryKeys[3]);
                propertyInfo.setPpl(inventoryKeys[4]);
                propertyInfo.setProjectCode(inventoryKeys[5]);
            }
            adjustDto.setInventoryId(detail.getInventoryId());
            adjustDto.setCustomerNo(propertyInfo.getCustomerNo());
            adjustDto.setGroupCustomerNo(propertyInfo.getGroupCustomerNo());
            adjustDto.setPpl(propertyInfo.getPpl());
            adjustDto.setProjectCode(propertyInfo.getProjectCode());
            if (!detail.getIsTransOut()) {
                // 设置组换后型号信息
                rkList.add(adjustDto);
            }
        }
        log.info("getAssemblyDataForWMS -->返回结果:".concat(JSONObject.toJSONString(rkList) ));
        return ResultVo.success(rkList);


    }
    @Override
    public ResultVo<String> handleAssembly(StockAssemblyDO applyInfo, StockAssemblyHandleDTO dto) {
        LoginUserDTO userDto = SMCApp.getLoginAuthDto();

        LambdaQueryWrapper<StockAssemblyDetailDO> detailQueryWrapper = Wrappers.lambdaQuery();
        detailQueryWrapper.eq(StockAssemblyDetailDO::getApplyId, applyInfo.getId())
                .eq(StockAssemblyDetailDO::getOptCode, 2);
        List<StockAssemblyDetailDO> detailList = stockAssemblyDetailMapper.selectList(detailQueryWrapper);
        if (detailList == null || detailList.isEmpty()) {
            return ResultVo.failure("组换失败: 申请项不能为空");
        }
        ResultVo<String> checkResult = this.checkApplyDetail(applyInfo.getApplyType(), detailList);
        if (!checkResult.isSuccess()) {
            checkResult.setMessage("组换失败:</br>" + checkResult.getMessage());
            return checkResult;
        }
        //    <!--add by WuWeiDong 20230629  bug 11100  判断是否可以调库 -->
        boolean isTransfer = StockAssemblyAssemblyTypeEnum.TRANSFER.getCode().equals(applyInfo.getAssembleType());
        if (isTransfer) {
            List<String> warehouseCodeList = detailList.stream().map(StockAssemblyDetailDO::getWarehouseCode).distinct().collect(Collectors.toList());
            ResultVo<String> canTransferVo = this.canTransferByWarehouseList(warehouseCodeList);
            if (!canTransferVo.isSuccess()) {
                return ResultVo.failure(canTransferVo.getMessage());
            }
        }


        //<!--add by WuWeiDong 20230421 bug 10515 -->
        // 如果是（仅财务,仅WMS）申请类型，不做在库库存判断
        boolean isFinanceOnly = StockAssemblyAssemblyTypeEnum.ASSEMBLY_FINANCE_ONLY.getCode().equals(applyInfo.getAssembleType());
        boolean isWMSOnly = StockAssemblyAssemblyTypeEnum.ASSEMBLY_WMS_ONLY.getCode().equals(applyInfo.getAssembleType());

        if (!isFinanceOnly && !isWMSOnly) {
            try {
                //bugid:16354 c14717 20250103 && 16651 先验证cn0 如果失败在验证CNG 原则全部 出单一库存
                boolean tkFlag = StockAssemblyAssemblyTypeEnum.TRANSFER.getCode().equals(applyInfo.getAssembleType());
                checkResult = this.checkTransOutStock(detailList, true ,tkFlag);
                if (!checkResult.isSuccess()){
                    checkResult = this.checkTransOutStock(detailList, false,tkFlag);
                }
                if (!checkResult.isSuccess() && tkFlag) {
                    checkResult.setMessage("组换失败:</br>" + checkResult.getMessage());
                    return checkResult;
                }
            } catch (Exception ex) {
                return ResultVo.failure("组换失败:" + ex);
            }
        }

        InventoryForProducChangeDto inputDto = new InventoryForProducChangeDto();
        UserDto user = new UserDto();
        user.setUserName(userDto.getUserName());
        user.setIp(IpUtil.getIpAddress());
        inputDto.setUserDto(user);
        inputDto.setWarehouseCode(applyInfo.getWarehouseCode());
        List<InventoryForAdjustDto> ckList = new ArrayList<>(detailList.size()); // 变更前的型号
        List<InventoryForAdjustDto> rkList = new ArrayList<>(detailList.size()); // 变更后的产出型号
        InventoryForAdjustDto adjustDto;
        String[] inventoryKeys;
        OpsInventoryProperty propertyInfo;
        Map<String,Long> inventoryMap=new HashMap<>();
        Map<String,OpsInventoryProperty> propertyMap=new HashMap<>();

        for (StockAssemblyDetailDO detail : detailList) {
            adjustDto = new InventoryForAdjustDto();
            adjustDto.setOrderId(applyInfo.getApplyNo());
            adjustDto.setQaStatus("0");
            adjustDto.setDeptno(applyInfo.getDeptNo());
            adjustDto.setModelno(detail.getModelNo());
            adjustDto.setQty(Math.abs(detail.getQuantity().intValue()));
            adjustDto.setWarehouseCode(detail.getWarehouseCode());
            adjustDto.setRemark(applyInfo.getRemark());

            // 按 库存类型~仓库代码~客户代码~客户群号~PPL~项目号 拼接
            inventoryKeys = detail.getInventoryKeys().split("~", -1);
            String inventoryIdKey=String.join("~",detail.getModelNo(),detail.getInventoryKeys());
            propertyInfo=propertyMap.get(detail.getInventoryKeys());
            if (ObjectUtils.isEmpty(propertyInfo)) {
                propertyInfo = this.getInventoryProperty(inventoryKeys[0], inventoryKeys[2], inventoryKeys[3], inventoryKeys[4], inventoryKeys[5]);
               if (ObjectUtils.isEmpty(propertyInfo)){
                   propertyInfo.setInventoryTypeCode(inventoryKeys[0]);
                   propertyInfo.setCustomerNo(inventoryKeys[2]);
                   propertyInfo.setGroupCustomerNo(inventoryKeys[3]);
                   propertyInfo.setPpl(inventoryKeys[4]);
                   propertyInfo.setProjectCode(inventoryKeys[5]);
               }
                propertyMap.put(detail.getInventoryKeys(),propertyInfo);

               if (ObjectUtils.isNotEmpty(propertyInfo.getInventoryPropertyId())) {
                   Long inventoryId = this.getInventoryId(adjustDto.getModelno(), adjustDto.getWarehouseCode(), propertyInfo.getInventoryPropertyId());
                   inventoryMap.put(inventoryIdKey, inventoryId);
               }

            }
            detail.setInventoryId(inventoryMap.getOrDefault(inventoryIdKey,null));
            adjustDto.setInventoryId(detail.getInventoryId());
            adjustDto.setCustomerNo(propertyInfo.getCustomerNo());
            adjustDto.setGroupCustomerNo(propertyInfo.getGroupCustomerNo());
            adjustDto.setPpl(propertyInfo.getPpl());
            adjustDto.setProjectCode(propertyInfo.getProjectCode());
            if (detail.getIsTransOut()) {
                // 设置组换前型号信息
                ckList.add(adjustDto);
            } else {
                // 设置组换后型号信息
                rkList.add(adjustDto);
            }
        }
        inputDto.setCkList(ckList);
        inputDto.setRkList(rkList);
        //    <!--add by WuWeiDong 20230421 bug 10515 增加仅组换WMS库存组换类型-->
        //  6-组换(仅WMS);
        if (isWMSOnly) {
            inputDto.setDoTypeEnum(DoTypeEnum.ZHCKOW);
        } else {
            inputDto.setDoTypeEnum(DoTypeEnum.ZHCK);
        }

        log.info("组换调库-组换处理 data = {}", JSON.toJSONString(inputDto));

        CommonResult<String> producChangeResult = opsWmDispatchForOrderFeignApi.ProducChange(inputDto);
        log.info("组换调库-组换处理 响应 = {}", JSON.toJSONString(producChangeResult));
        if (producChangeResult.getCode() == 500) {
            return ResultVo.failure("组换失败: 调用wm-service组换处理接口失败 " + producChangeResult.getMessage());
        }

        /* Edit by Dengdenghui 2022-11-29 bug-8822
        // Add by Dengdenghui 2022-11-18 for bug-8712
        // 生成组换票号 (一个申请一个票号), (仅业务)类型申请不计入财务成本
        boolean isFinance = !StockAssemblyAssemblyTypeEnum.ASSEMBLY_BUSINESS_ONLY.getCode().equals(applyInfo.getAssembleType());
        String invoiceNo = null;
        if (isFinance) {
            invoiceNo = this.getInvoiceNo(applyInfo.getId(), applyInfo.getApplyType());
            if (StringUtils.isBlank(invoiceNo)) {
                return ResultVo.failure("调库失败: 组换票号生成失败");
            }
        } // End
        */

        // 执行组换处理成功
        // String finalInvoiceNo = invoiceNo;
        return transactionTemplate.execute(action -> {
            try {
                Date now = new Date();
                LambdaUpdateWrapper<StockAssemblyDetailDO> detailUpdateWrapper = Wrappers.lambdaUpdate();
                for (StockAssemblyDetailDO detail : detailList) {
                    detailUpdateWrapper.clear();
                    detailUpdateWrapper.set(StockAssemblyDetailDO::getOptCode, 5) // 组换中
                            .set(StockAssemblyDetailDO::getTransTime, now)
                            .set(StockAssemblyDetailDO::getInventoryId, detail.getInventoryId())
                            .set(StockAssemblyDetailDO::getUpdateUser, userDto.getUserNo())
                            .set(StockAssemblyDetailDO::getUpdateTime, now);
                    detailUpdateWrapper.eq(StockAssemblyDetailDO::getId, detail.getId())
                            .eq(StockAssemblyDetailDO::getOptCode, 2);
                    stockAssemblyDetailMapper.update(null, detailUpdateWrapper);
                }

                LambdaUpdateWrapper<StockAssemblyDO> masterUpdateWrapper = Wrappers.lambdaUpdate();
                masterUpdateWrapper.set(StockAssemblyDO::getStatus, 5) // 组换中
                        .set(StockAssemblyDO::getTransTime, now)
                        .set(StockAssemblyDO::getTransPsn, userDto.getUserNo())
                        .set(StringUtils.isNotEmpty(dto.getAnswerText()), StockAssemblyDO::getAnswerText, dto.getAnswerText())
                        .set(StringUtils.isNotEmpty(dto.getAnswerText()), StockAssemblyDO::getAnswerDate, now)
                        .set(StockAssemblyDO::getUpdateUser, userDto.getUserNo())
                        .set(StockAssemblyDO::getUpdateTime, now);
                masterUpdateWrapper.eq(StockAssemblyDO::getApplyNo, applyInfo.getApplyNo())
                        .eq(StockAssemblyDO::getStatus, 3);
                stockAssemblyMapper.update(null, masterUpdateWrapper);
            } catch (Exception e) {
                action.setRollbackOnly();
                log.error("组换处理异常: {} {}", applyInfo.getApplyNo(), e.getMessage(), e);
                return ResultVo.failure("组换失败");
            }
            return ResultVo.success("组换完成");
        });
    }

    @Override
    public ResultVo<String> updateAssemblyStatus(String applyNo, Boolean result) {
        log.info("更新组换处理状态: {} 处理结果 {}", applyNo, result);
        LoginUserDTO userDto = SMCApp.getLoginAuthDto();
        int assemblyStatus = 5; // 组换中
        LambdaQueryWrapper<StockAssemblyDO> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.select(StockAssemblyDO::getId, StockAssemblyDO::getApplyNo);
        queryWrapper.eq(StockAssemblyDO::getApplyNo, applyNo)
                .eq(StockAssemblyDO::getStatus, assemblyStatus);
        StockAssemblyDO applyInfo = stockAssemblyMapper.selectOne(queryWrapper);
        if (applyInfo == null) {
            return ResultVo.failure("组换中申请的状态更新失败: 组换中申请不存在");
        }

        int updateStatus = result ? 6 : 7; // 6-已完成; 7-不能组换;

        LambdaUpdateWrapper<StockAssemblyDetailDO> detailUpdateWrapper = Wrappers.lambdaUpdate();
        detailUpdateWrapper.set(StockAssemblyDetailDO::getOptCode, updateStatus)
                .set(StockAssemblyDetailDO::getUpdateUser, userDto.getUserNo());
        detailUpdateWrapper.eq(StockAssemblyDetailDO::getApplyId, applyInfo.getId())
                .eq(StockAssemblyDetailDO::getOptCode, assemblyStatus);
        stockAssemblyDetailMapper.update(new StockAssemblyDetailDO(), detailUpdateWrapper);

        applyInfo.setStatus(String.valueOf(updateStatus));
        applyInfo.setUpdateUser(userDto.getUserNo());
        applyInfo.setTransTime(DateUtil.getNow()); // 记录完成组换的时间
        stockAssemblyMapper.updateById(applyInfo);
        return ResultVo.success("组换申请状态更新成功");
    }

    @Override
    public ResultVo<PageInfo<StockAssemblyDetailView>> listStockAssemblyDetail(stockAssemblyDetailRequest request) {
        //    <!--edit by WuWeiDong 20231109  bug 12562  导出数据限制 -->
        if (request.getIsExport()) {
            if (StringUtils.isBlank(request.getApplyNo()) &&
                    (Objects.isNull(request.getFromDate()) || Objects.isNull(request.getToDate()))) {
                throw new BusinessException("请输入查询日期范围。 ");
            }
            if (Objects.nonNull(request.getFromDate()) && Objects.nonNull(request.getToDate())) {
                Long diffDay = DateUtil.getDiffDay(request.getFromDate(), request.getToDate());
                if (diffDay.compareTo(100l) > 0) {
                    log.error("日期范围差不能超过100天。：{} -> {} :{}天", request.getFromDate(), request.getToDate(), diffDay);
                    throw new BusinessException("日期范围差不能超过100天。 当前差:" + diffDay.toString());
                }
            }
        }
        LambdaQueryWrapper<StockAssemblyDetailView> query = Wrappers.lambdaQuery();
        query.eq(StringUtils.isNotEmpty(request.getApplyNo()), StockAssemblyDetailView::getApplyNo, request.getApplyNo())
                .eq(StringUtils.isNotEmpty(request.getDeptNo()), StockAssemblyDetailView::getDeptNo, request.getDeptNo())
                .eq(StringUtils.isNotEmpty(request.getAssembleType()), StockAssemblyDetailView::getAssembleType, request.getAssembleType())
                .eq(StringUtils.isNotBlank(request.getWarehouseCode()), StockAssemblyDetailView::getDetailWarehouseCode, request.getWarehouseCode())
                .eq(StringUtils.isNotBlank(request.getApplyPsn()), StockAssemblyDetailView::getApplyPsn, request.getApplyPsn())
                .eq(StringUtils.isNotEmpty(request.getStatus()), StockAssemblyDetailView::getOptCode, request.getStatus()); // 申请项状态
        // 如果型号参数末尾带%,使用模糊查询
        if (StringUtils.isNotBlank(request.getModelNo())) {
            if (request.getModelNo().endsWith("%")) {
                query.likeRight(StockAssemblyDetailView::getModelNo, request.getModelNo().substring(0, request.getModelNo().lastIndexOf("%")));
            } else {
                query.eq(StockAssemblyDetailView::getModelNo, request.getModelNo());
            }
        }
        if (request.getDateType() == null) {
            request.setDateType(0);
        }
        if (request.getToDate() != null) {
            request.setToDate(DateUtil.getEndTime(request.getToDate()));
        }
        if (request.getDateType() == 1) {
            query.ge((request.getFromDate() != null), StockAssemblyDetailView::getApplyDate, request.getFromDate())
                    .le((request.getToDate() != null), StockAssemblyDetailView::getApplyDate, request.getToDate());
        } else if (request.getDateType() == 2) {
            query.ge((request.getFromDate() != null), StockAssemblyDetailView::getApproveDate, request.getFromDate())
                    .le((request.getToDate() != null), StockAssemblyDetailView::getApproveDate, request.getToDate());
        } else if (request.getDateType() == 3) {
            query.ge((request.getFromDate() != null), StockAssemblyDetailView::getDetailTransTime, request.getFromDate())
                    .le((request.getToDate() != null), StockAssemblyDetailView::getDetailTransTime, request.getToDate());
        } else if (request.getDateType() == 4) {
            query.ge((request.getFromDate() != null), StockAssemblyDetailView::getReceiveTime, request.getFromDate())
                    .le((request.getToDate() != null), StockAssemblyDetailView::getReceiveTime, request.getToDate());
        }
        query.notIn(StockAssemblyDetailView::getStatus, 9);
        query.notIn(StockAssemblyDetailView::getOptCode, 9);
        query.orderByDesc(StockAssemblyDetailView::getApplyDate);
        PageHelper.startPage(request.getPageNum(), request.getPageSize());
        List<StockAssemblyDetailView> list = stockAssemblyDetailViewMapper.selectList(query);
        PageInfo<StockAssemblyDetailView> pageInfo = PageInfo.of(list);

        Map<String, String> nameMap = new HashMap<>();
        for (StockAssemblyDetailView detail : pageInfo.getList()) {
            // 部门名称
            if (!nameMap.containsKey(detail.getDeptNo())) {
                nameMap.put(detail.getDeptNo(), opsCommonService.getDeptNameByNo(detail.getDeptNo()));
            }
            detail.setDeptNo(nameMap.getOrDefault(detail.getDeptNo(), detail.getDeptNo()));
            // 客户名称
            if (StringUtils.isNotBlank(detail.getCustomerNo())) {
                if (!nameMap.containsKey(detail.getCustomerNo())) {
                    nameMap.put(detail.getCustomerNo(), opsCommonService.getCustomerNameByNo(detail.getCustomerNo()));
                }
                detail.setCustomerName(nameMap.getOrDefault(detail.getCustomerNo(), detail.getCustomerNo()));
            }
        }
        return ResultVo.success(pageInfo);
    }

    @Override
    public ExcelUtil exportStockAssemblyDetail(stockAssemblyDetailRequest request) {
        ExcelUtil excel = new ExcelUtil(FileUtil.getTemplate("template/stockAssemblyDetail.xlsx"));

        request.setPageNum(1);
        request.setPageSize(2000);
        PageInfo<StockAssemblyDetailView> pageInfo;
        int row = 1;
        int colIndex;
        Map<Long, Object> detailIds = null; // 动态数据导出去重
        request.setIsExport(true);
        try {
            while (true) {
                pageInfo = this.listStockAssemblyDetail(request).getData();
                //    <!--edit by WuWeiDong 20231109  bug 12562  超过15W提示。 -->
                if (pageInfo.getTotal() > 150000) {
                    throw new BusinessException("数据超过15W，请缩小日期范围。 ");
                }
                if (detailIds == null) {
                    detailIds = new HashMap<>((int) pageInfo.getTotal());
                }

                for (StockAssemblyDetailView detail : pageInfo.getList()) {
                    if (detailIds.containsKey(detail.getDetailId())) {
                        continue;
                    }
                    colIndex = 0;
                    excel.setCellValue(row, colIndex++, detail.getApplyNo());
                    excel.setCellValue(row, colIndex++, detail.getDeptNo());
                    excel.setCellValue(row, colIndex++, StockAssemblyAssemblyTypeEnum.getNameByCode(detail.getAssembleType()));
                    excel.setCellValue(row, colIndex++, detail.getIsTransOut() ? "调出" : "调入");
                    excel.setCellValue(row, colIndex++, detail.getModelNo());
                    excel.setCellValue(row, colIndex++, detail.getQuantity());
                    excel.setCellValue(row, colIndex++, StockAssemblyDetailStatusEnum.getNameByCode(detail.getOptCode()));
                    excel.setCellValue(row, colIndex++, detail.getDetailWarehouseCode());
                    excel.setCellValue(row, colIndex++, detail.getInventoryTypeCode());
                    excel.setCellValue(row, colIndex++, detail.getCustomerNo());
                    excel.setCellValue(row, colIndex++, detail.getCustomerName());
                    excel.setCellValue(row, colIndex++, detail.getGroupCustomerNo());
                    excel.setCellValue(row, colIndex++, detail.getPpl());
                    excel.setCellValue(row, colIndex++, detail.getProjectCode());
                    excel.setCellValue(row, colIndex++, StockAssemblyApplyStatusEnum.getNameByCode(detail.getStatus()));
                    excel.setCellValue(row, colIndex++, detail.getApplyDate());
                    excel.setCellValue(row, colIndex++, detail.getApplyPsn());
                    excel.setCellValue(row, colIndex, detail.getApplyRemark());
                    detailIds.put(detail.getDetailId(), null);
                    row++;
                }
                excel.getSxssfSheet().flushRows();
                if (pageInfo.isIsLastPage()) {
                    break;
                }
                request.setPageNum(request.getPageNum() + 1);
            }
        } catch (IOException e) {
            log.error("导出组换调库明细: params = {}, {}", request, e.getMessage(), e);
            throw new BusinessException("导出失败：" + e.getMessage());
        }
        return excel;
    }

    // Add by Dengdenghui 2022-11-29 bug-8822, Edit by 2022-12-22 for bug-9104
    @Override
    public ResultVo<String> sendAssemblyApplyToCost() {
        String applyType = "1"; // 组换申请
        LambdaQueryWrapper<StockAssemblyDetailView> applyQuery = Wrappers.lambdaQuery();
        List<String> assembleTypeS = new ArrayList<>();
        assembleTypeS.add(StockAssemblyAssemblyTypeEnum.ASSEMBLY_BUSINESS_ONLY.getCode());// 类型-组换（仅业务）
        assembleTypeS.add(StockAssemblyAssemblyTypeEnum.ASSEMBLY_WMS_ONLY.getCode());// 类型-组换（仅WMS）
        applyQuery.select(StockAssemblyDetailView::getApplyId, StockAssemblyDetailView::getApplyNo,
                StockAssemblyDetailView::getBillNo);
        applyQuery.eq(StockAssemblyDetailView::getApplyType, applyType) // 组换申请
                .eq(StockAssemblyDetailView::getOptCode, StockAssemblyDetailStatusEnum.finished.getCode()) // 已完成组换
                .isNotNull(StockAssemblyDetailView::getBranchFlag)//is not null  bugid:16354
                .notIn(StockAssemblyDetailView::getAssembleType, assembleTypeS) // 排除类型-组换（仅业务和WMS）
                .groupBy(StockAssemblyDetailView::getApplyId, StockAssemblyDetailView::getApplyNo,
                        StockAssemblyDetailView::getBillNo);
        List<StockAssemblyDetailView> applyList = stockAssemblyDetailViewMapper.selectList(applyQuery);
        if (applyList.isEmpty()) {
            return ResultVo.success("暂无组换数据需要录入成本");
        }

        boolean error = false;
        LambdaQueryWrapper<StockAssemblyDetailDO> detailQuery = Wrappers.lambdaQuery();
        List<StockAssemblyDetailDO> detailList;
        String invoiceNo;
        LambdaUpdateWrapper<StockAssemblyDetailDO> detailUpdate = Wrappers.lambdaUpdate();
        StockAssemblyDO applyDO;
        Date now;

        for (StockAssemblyDetailView applyInfo : applyList) {
            detailQuery.clear();
            detailQuery.select(StockAssemblyDetailDO::getId, StockAssemblyDetailDO::getModelNo,
                    StockAssemblyDetailDO::getQuantity, StockAssemblyDetailDO::getWarehouseCode,
                    StockAssemblyDetailDO::getInventoryKeys, StockAssemblyDetailDO::getInvoiceNo,StockAssemblyDetailDO::getBranchFlag);
            detailQuery.eq(StockAssemblyDetailDO::getApplyId, applyInfo.getApplyId())
                    .eq(StockAssemblyDetailDO::getOptCode, StockAssemblyDetailStatusEnum.finished.getCode())
                    .isNotNull(StockAssemblyDetailDO::getBranchFlag);// bugid:16354
            detailList = stockAssemblyDetailMapper.selectList(detailQuery);

            // 录入成本
            invoiceNo = impdataAdjustService.getInvoiceNoByOrderNo(applyInfo.getApplyNo());
            if (StringUtils.isBlank(invoiceNo)) {
                // 生成票号
                if (StringUtils.isBlank(applyInfo.getBillNo())) {
                    invoiceNo = this.getInvoiceNo(applyInfo.getApplyId(), applyType);
                } else {
                    invoiceNo = applyInfo.getBillNo();
                }

                List<ImpdataAdjustDO> costDataList = new ArrayList<>(detailList.size());
                ImpdataAdjustDO costData;
                Integer countSubCompany = stockAssemblyDetailMapper.countStockAssemblyDetail(applyInfo.getApplyId());
                for (StockAssemblyDetailDO detail : detailList) {
                    //16354  16651
                    if(detail.getBranchFlag().equals(0)){
                        if(detail.getQuantity().intValue() > 0){//入库 若出库资产中存在任意型号包含 CNG，则入库资产的 owerCompayId 写入 CNG。
                            if(countSubCompany > 0){
                                costData = initCostData(detail, invoiceNo, applyInfo.getApplyNo(), "CNG", detail.getQuantity().intValue());
                            }else {
                                costData = initCostData(detail, invoiceNo, applyInfo.getApplyNo(), "CN0", detail.getQuantity().intValue());
                            }
                            costDataList.add(costData);
                        }else {
                            costData = initCostData(detail, invoiceNo, applyInfo.getApplyNo(), "CN0", detail.getQuantity().intValue());
                            costDataList.add(costData);
                        }
                    }else {
                        List<StockAssemblyDetailProperty> detailProperties = stockAssemblyDetailMapper.queryStockAssemblyDetailProperty(detail.getId());
                        int dpQty = 0;
                        for(StockAssemblyDetailProperty detailProperty : detailProperties){
                            costData = initCostData(detail, invoiceNo, applyInfo.getApplyNo(), detailProperty.getCompanyid(), detailProperty.getPropertyQuantity() * (-1));
                            costDataList.add(costData);
                            dpQty = detailProperty.getPropertyQuantity() + dpQty;
                        }
                        if(detail.getQuantity().intValue() + dpQty < 0) {
                            costData = initCostData(detail, invoiceNo, applyInfo.getApplyNo(), "CN0", detail.getQuantity().intValue() + dpQty);
                            costDataList.add(costData);
                        }
                    }
                }

                try {
                    ResultVo<String> impResult = impdataAdjustService.importAssemblyCostData(costDataList);
                    if (!impResult.isSuccess()) {
                        log.error("组换申请录入成本失败: {} {}", applyInfo.getApplyNo(), impResult.getMessage());
                        error = true;
                        continue;
                    }
                } catch (Exception e) {
                    log.error("组换申请录入成本发生异常: {} {}", applyInfo.getApplyNo(), e.getMessage(), e);
                    error = true;
                    continue;
                }
            }

            now = new Date();
            // 更新组换成本录入成功状态
            detailUpdate.clear();
            detailUpdate.set(StockAssemblyDetailDO::getInvoiceNo, invoiceNo)
                    .set(StockAssemblyDetailDO::getOptCode, StockAssemblyDetailStatusEnum.cost.getCode())
                    .set(StockAssemblyDetailDO::getTransTime, now) // 记录写入成本的时间 bug-9233
                    .set(StockAssemblyDetailDO::getUpdateTime, now);
            detailUpdate.eq(StockAssemblyDetailDO::getApplyId, applyInfo.getApplyId())
                    .eq(StockAssemblyDetailDO::getOptCode, StockAssemblyDetailStatusEnum.finished.getCode());
            stockAssemblyDetailMapper.update(null, detailUpdate);

            applyDO = new StockAssemblyDO();
            applyDO.setId(applyInfo.getApplyId());
            applyDO.setBillNo(invoiceNo);
            applyDO.setTransTime(now); // 记录写入成本的时间 bug-9233
            stockAssemblyMapper.updateById(applyDO);
        }

        return error ? ResultVo.failure("处理时发生异常") : ResultVo.success("处理完成");
    }

    public ImpdataAdjustDO initCostData(StockAssemblyDetailDO detail,String invoiceNo,String applyNo,String companyId,Integer qty){
        String[] inventoryKeys = detail.getInventoryKeys().split("~", -1);
        ImpdataAdjustDO costData = new ImpdataAdjustDO();
        costData.setInvoiceNo(invoiceNo);
        costData.setOrderNo(applyNo);
        costData.setStockCode(detail.getWarehouseCode());
        costData.setInvDesc("组换");
        costData.setCustomerNo(inventoryKeys[2]);
        costData.setModelNo(detail.getModelNo());
        costData.setQuantity(qty);
        costData.setDataSource("SA");
        costData.setOwnerCompanyId(companyId);
        return costData;
    }

    @Override
    @DS("shareapp")
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public ResultVo<List<StockAssemblyDetailView>> listNoImportCostAssemblyData() {
        String applyType = "1"; // 组换申请
        int optCode = 6; // 已执行组换
        List<String> assembleTypeS = new ArrayList<>();
        assembleTypeS.add(StockAssemblyAssemblyTypeEnum.ASSEMBLY_BUSINESS_ONLY.getCode());// 类型-组换（仅业务）
        assembleTypeS.add(StockAssemblyAssemblyTypeEnum.ASSEMBLY_WMS_ONLY.getCode());// 类型-组换（仅WMS）

        LambdaQueryWrapper<StockAssemblyDetailView> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.select(StockAssemblyDetailView::getDetailId, StockAssemblyDetailView::getApplyNo, StockAssemblyDetailView::getInvoiceNo,
                StockAssemblyDetailView::getModelNo, StockAssemblyDetailView::getQuantity, StockAssemblyDetailView::getDetailWarehouseCode,
                StockAssemblyDetailView::getInventoryKeys);
        queryWrapper.eq(StockAssemblyDetailView::getApplyType, applyType)
                // (仅业务)类型，不计入成本 Add by Dengdenghui 2022-11-18 for bug-8712
                //(仅WMD)类型，不计入成本 Add by Wuweidong 2023-0423 bug:10515
                .notIn(StockAssemblyDetailView::getAssembleType, assembleTypeS)
                .eq(StockAssemblyDetailView::getOptCode, optCode)
                .isNotNull(StockAssemblyDetailView::getInvoiceNo); // 限制范围为已生成发票号的数据
        List<StockAssemblyDetailView> detailList = stockAssemblyDetailViewMapper.selectList(queryWrapper);
        return ResultVo.success(detailList);
    }

    @Override
    @DS("shareapp")
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public ResultVo<String> updateImportCostAssemblyApplyStatus(List<StockAssemblyDetailView> detailList) {
        int optCode = 8; // 已计入成本
        List<Long> detailIdList = detailList.stream().map(StockAssemblyDetailView::getDetailId).collect(Collectors.toList());

        LambdaUpdateWrapper<StockAssemblyDetailDO> detailUpdateWrapper = Wrappers.lambdaUpdate();
        detailUpdateWrapper.set(StockAssemblyDetailDO::getOptCode, optCode);
        detailUpdateWrapper.in(StockAssemblyDetailDO::getId, detailIdList)
                .eq(StockAssemblyDetailDO::getOptCode, 6);
        stockAssemblyDetailMapper.update(new StockAssemblyDetailDO(), detailUpdateWrapper);
        log.info("已更新录入成本后的组换申请状态: {}", detailIdList);
        return ResultVo.success("已更新录入成本后的组换申请状态: " + detailIdList);
    }

    // Add by DengDengHui 2022-10-20 for bug-8402, Edit by DengDengHui 2022-10-28 for bug-8402, Edit by 2022-12-21 for 9081
    @Override
    public ResultVo<String> importApplyDetail(InputStream inputStream, Long applyId) {
        long startTime = System.currentTimeMillis();
        ExcelHelper excelHelper = new ExcelHelper(inputStream);
        Sheet sheet = excelHelper.getSheet();
        int lastRowNum = sheet.getLastRowNum();
        log.info("开始批量导入组换调库申请项: applyId={}, lastRowNum={}", applyId, lastRowNum);
        //<!--add by WuWeiDong 20230508 bug 10678   -->

        final int limitItem = 150; //批量写粒度 2100/12
        //<!--edit by WuWeiDong 20230614 bug 11089 取消导入100项限制-->
//        if (lastRowNum>limitItem)
//        {
//            return ResultVo.failure("导入错误：超过"+limitItem+"项。");
//        }

        List<StockAssemblyDetailDO> detailList = new ArrayList<>(limitItem);
        StockAssemblyDetailDO detail;
        Date now = new Date();
        String optUser = SMCApp.getLoginAuthDto().getUserNo();
        String modelNo;
        BigDecimal quantity;
        String warehouse;
        String inventoryTypeCode;
        String customerNo;
        String groupCustomerNo;
        String pplNo;
        String projectCode;
        String remark;
        StringBuilder inventoryKeys = new StringBuilder(30);
        int outItems = 0;
        int inItems = 0;
        String regex = "[^A-Za-z0-9]+";

        for (int rowNum = 1; rowNum <= lastRowNum; rowNum++) {
            modelNo = excelHelper.getCellString(excelHelper.getCell(rowNum, 0));
            // 跳过型号为空的数据行 bug-9301
            if (StringUtils.isNotBlank(modelNo)) {
                modelNo = modelNo.trim();
                quantity = excelHelper.getCellBigDecimal(excelHelper.getCell(rowNum, 1));
                remark = excelHelper.getCellString(excelHelper.getCell(rowNum, 14));

                warehouse = excelHelper.getCellString(excelHelper.getCell(rowNum, 2));
                if (StringUtils.isNotBlank(warehouse)) {
                    inventoryTypeCode = excelHelper.getCellString(excelHelper.getCell(rowNum, 3));
                    customerNo = excelHelper.getCellString(excelHelper.getCell(rowNum, 4));
                    groupCustomerNo = excelHelper.getCellString(excelHelper.getCell(rowNum, 5)).trim();
                    pplNo = excelHelper.getCellString(excelHelper.getCell(rowNum, 6)).trim();
                    projectCode = excelHelper.getCellString(excelHelper.getCell(rowNum, 7)).trim();
                    if (warehouse.contains("~")) {
                        warehouse = warehouse.substring(0, warehouse.indexOf("~"));
                    }
                    if (inventoryTypeCode.contains("~")) {
                        inventoryTypeCode = inventoryTypeCode.substring(0, inventoryTypeCode.indexOf("~"));
                    }
                    customerNo = customerNo.replaceAll(regex, "").toUpperCase(); // 过滤非法字符

                    inventoryKeys.setLength(0);
                    inventoryKeys.append(inventoryTypeCode).append("~").append(warehouse).append("~").append(customerNo)
                            .append("~").append(groupCustomerNo).append("~").append(pplNo).append("~").append(projectCode);

                    detail = new StockAssemblyDetailDO();
                    detail.setApplyId(applyId);
                    detail.setModelNo(modelNo);
                    detail.setQuantity(-Math.abs(quantity.doubleValue()));
                    detail.setIsTransOut(Boolean.TRUE);
                    detail.setWarehouseCode(warehouse);
                    detail.setInventoryKeys(inventoryKeys.toString());
                    detail.setRemark(remark);
                    detail.setOptCode(0);
                    detail.setCreateTime(now);
                    detail.setUpdateTime(now);
                    detail.setCreateUser(optUser);
                    detail.setUpdateUser(optUser);
                    detailList.add(detail);
                    outItems++;
                }

                warehouse = excelHelper.getCellString(excelHelper.getCell(rowNum, 8));
                if (StringUtils.isNotBlank(warehouse)) {
                    inventoryTypeCode = excelHelper.getCellString(excelHelper.getCell(rowNum, 9));
                    customerNo = excelHelper.getCellString(excelHelper.getCell(rowNum, 10));
                    groupCustomerNo = excelHelper.getCellString(excelHelper.getCell(rowNum, 11)).trim();
                    pplNo = excelHelper.getCellString(excelHelper.getCell(rowNum, 12)).trim();
                    projectCode = excelHelper.getCellString(excelHelper.getCell(rowNum, 13)).trim();
                    if (warehouse.contains("~")) {
                        warehouse = warehouse.substring(0, warehouse.indexOf("~"));
                    }
                    if (inventoryTypeCode.contains("~")) {
                        inventoryTypeCode = inventoryTypeCode.substring(0, inventoryTypeCode.indexOf("~"));
                    }
                    customerNo = customerNo.replaceAll(regex, ""); // 过滤非法字符
                    customerNo = customerNo.toUpperCase();

                    inventoryKeys.setLength(0);
                    inventoryKeys.append(inventoryTypeCode).append("~").append(warehouse).append("~").append(customerNo)
                            .append("~").append(groupCustomerNo).append("~").append(pplNo).append("~").append(projectCode);

                    detail = new StockAssemblyDetailDO();
                    detail.setApplyId(applyId);
                    detail.setModelNo(modelNo);
                    detail.setQuantity(Math.abs(quantity.doubleValue()));
                    detail.setIsTransOut(Boolean.FALSE);
                    detail.setWarehouseCode(warehouse);
                    detail.setInventoryKeys(inventoryKeys.toString());
                    detail.setRemark(remark);
                    detail.setOptCode(0);
                    detail.setCreateTime(now);
                    detail.setUpdateTime(now);
                    detail.setCreateUser(optUser);
                    detail.setUpdateUser(optUser);
                    detailList.add(detail);
                    inItems++;
                }
            }
            // 批量插入
            if (detailList.size() == limitItem || (rowNum == lastRowNum && detailList.size() > 0)) {
                stockAssemblyDetailMapper.insertBatch(detailList);
                detailList.clear();
            }
        }
        log.info("完成批量导入组换调库申请项: {}, 已成功导入: 出库项{}个,入库项{}个. 耗时{}s", applyId, outItems, inItems,
                (System.currentTimeMillis() - startTime) / 1000);
        return ResultVo.success("已成功导入: 出库项" + outItems + "个,入库项" + inItems + "个");
    } // end

    // Add by DengDengHui, 2022-10-26 for bug-8395
    @Override
    public ResultVo<List<TransferResult>> handleSMSInStockApply(String applyNo) {
        LambdaQueryWrapper<StockAssemblyDO> applyQuery = Wrappers.lambdaQuery();
        applyQuery.eq(StockAssemblyDO::getApplyNo, applyNo);
        StockAssemblyDO applyInfo = stockAssemblyMapper.selectOne(applyQuery);

        LambdaQueryWrapper<StockAssemblyDetailDO> detailQuery = Wrappers.lambdaQuery();
        detailQuery.eq(StockAssemblyDetailDO::getApplyId, applyInfo.getId())
                .ne(StockAssemblyDetailDO::getOptCode, 9);
        List<StockAssemblyDetailDO> detailList = stockAssemblyDetailMapper.selectList(detailQuery);

        Map<Integer, List<StockAssemblyDetailDO>> itemMap = this.handleTransferItem(detailList);
        List<Integer> itemNos = itemMap.keySet().stream().sorted().collect(Collectors.toList());

        List<TransferResult> resultList = new ArrayList<>(itemMap.size());
        TransferResult result;
        ResultVo<String> resultVo;

        for (Integer itemNo : itemNos) {
            result = new TransferResult();
            result.setNo(applyInfo.getApplyNo());
            result.setItemId(itemNo);
            result.setModelNo(itemMap.get(itemNo).get(0).getModelNo());
            result.setResult(true);
            resultList.add(result);

            if (itemMap.get(itemNo).get(0).getOptCode() == 2) {
                resultVo = transactionTemplate.execute(action -> {
                    try {
                        return this.createTransferOrder(applyInfo, itemNo, itemMap.get(itemNo));
                    } catch (Exception e) {
                        action.setRollbackOnly();
                        log.error("调库异常: {}-{} {} {}", applyInfo.getApplyNo(), itemNo, itemMap.get(itemNo).get(0).getModelNo(), e.getMessage(), e);
                        throw new BusinessException("调库失败," + e.getMessage());
                    }
                });
                log.info("调拨处理 {}-{} {} 响应: {}", applyInfo.getApplyNo(), itemNo, itemMap.get(itemNo).get(0).getModelNo(), JSON.toJSONString(resultVo));
                if (resultVo == null) {
                    result.setMessage("调库失败");
                } else {
                    if (resultVo.isSuccess()) {
                        result.setMessage(resultVo.getData());
                    } else {
                        result.setResult(false);
                        result.setMessage(resultVo.getMessage());
                    }
                }
            } else if (itemMap.get(itemNo).get(0).getOptCode() == 7) {
                result.setResult(false);
                result.setMessage(StringUtils.isNotBlank(itemMap.get(itemNo).get(0).getRemark()) ? itemMap.get(itemNo).get(0).getRemark() : "调库失败,可用数量不足");
            } else {
                result.setMessage("调库成功");
            }
        }
        // 更新申请处理状态
        QueryWrapper<StockAssemblyDetailDO> exitDetailQuery = Wrappers.query();
        exitDetailQuery.select("Top(1) Id");
        exitDetailQuery.lambda()
                .eq(StockAssemblyDetailDO::getApplyId, applyInfo.getId())
                .eq(StockAssemblyDetailDO::getOptCode, 2);
        StockAssemblyDetailDO detailOne = stockAssemblyDetailMapper.selectOne(exitDetailQuery);
        if (detailOne == null) {
            Date now = new Date();
            String invoiceNo = this.getInvoiceNo(applyInfo.getId());

            LambdaUpdateWrapper<StockAssemblyDO> masterUpdateWrapper = Wrappers.lambdaUpdate();
            masterUpdateWrapper.set(StockAssemblyDO::getStatus, 6)
                    .set(StockAssemblyDO::getBillNo, invoiceNo)
                    .set(StockAssemblyDO::getTransTime, now)
                    .set(StockAssemblyDO::getTransPsn, SMCApp.getLoginAuthDto().getUserNo())
                    .set(StockAssemblyDO::getAnswerDate, now)
                    .set(StockAssemblyDO::getUpdateUser, SMCApp.getLoginAuthDto().getUserNo());
            masterUpdateWrapper.eq(StockAssemblyDO::getApplyNo, applyInfo.getApplyNo())
                    .eq(StockAssemblyDO::getStatus, 3);
            stockAssemblyMapper.update(new StockAssemblyDO(), masterUpdateWrapper);
        }

        return ResultVo.success(resultList);
    } // End

    /**
     * <!--add by WuWeiDong 20231206  bug 12437  组换调库校验出入数据 -->
     * 1)出和入的不同型号个数要大于1，每项的数量不能为0
     * 2)组换:非异仓组（暂是没有），基他必须同仓。
     * 3)调库：出入数量一致
     * 4)调入型号验证型号是否正确
     *
     * @param applyType  申请类型 1)组换，2）调拨
     * @param detailList 申请项列表
     * @return result
     */
    public ResultVo<String> checkApplyDetail(String applyType, List<StockAssemblyDetailDO> detailList) {
        if (CollectionUtils.isEmpty(detailList)) {
            return ResultVo.failure("申请项为空,无法校验");
        }
        StringBuilder errorMsg = new StringBuilder();
        // 1)出和入的不同型号个数要大于1，每项的数量不能为0
        List<StockAssemblyDetailDO> doList = detailList.stream().filter(i -> i.getQuantity() == 0).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(doList)) {
            errorMsg.append("</br>").append(" 申请项数量不能为0.");
        }
        doList = detailList.stream().filter(i -> i.getIsTransOut()).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(doList)) {
            errorMsg.append("</br>").append("没有调出型号，请追加。");
        }
        doList = detailList.stream().filter(i -> !i.getIsTransOut()).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(doList)) {
            errorMsg.append("</br>").append("没有调入型号，请追加。");
        }
        // 2)组换:非异仓组（暂是没有），必须同仓,出入不能同型号。
        if ("1".equalsIgnoreCase(applyType)) {
            List<String> collect = detailList.stream().map(StockAssemblyDetailDO::getWarehouseCode).distinct().collect(Collectors.toList());
            if (collect.size() != 1) {
                errorMsg.append("</br>").append("组换申请仓库必须相同。");
            }
            Map<String, Long> collectModel = detailList.stream().collect(
                    Collectors.groupingBy(StockAssemblyDetailDO::getModelNo, Collectors.counting()));
            collect = collectModel.keySet().stream().
                    filter(key -> collectModel.get(key) > 1).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(collect) && collect.size() >= 1) {
                errorMsg.append("</br>").append("组换申请不能存在相同型号。");
            }
        }
        // 3)调库：出入数量一致
        if ("2".equalsIgnoreCase(applyType)) {
            Map<String, List<StockAssemblyDetailDO>> modelGroupMap = detailList.stream().collect(
                    Collectors.groupingBy(StockAssemblyDetailDO::getModelNo));

            for (Map.Entry<String, List<StockAssemblyDetailDO>> entrySet : modelGroupMap.entrySet()) {
                if (entrySet.getValue().stream().mapToDouble(StockAssemblyDetailDO::getQuantity).sum() != 0) {
                    errorMsg.append("</br>").append(entrySet.getKey()).append(" 调库数量不正确.");
                }
            }
        }
        // 4)调入型号验证型号是否正确
        List<String> inModelNos = detailList.stream().filter(item -> !item.getIsTransOut()).map(StockAssemblyDetailDO::getModelNo).collect(Collectors.toList());
        ResultVo<List<String>> checkResult = productServiceFeignApi.checkAndReturnErrorModel(inModelNos);
        if (checkResult.isSuccess()) {
            if (CollectionUtils.isNotEmpty(checkResult.getData())) {
                errorMsg.append("存在错误型号.");
                for (String modelNo : checkResult.getData()) {
                    errorMsg.append("</br>").append(modelNo);
                }
            }
        } else {
            errorMsg.append("入库型号校验失败 ").append(checkResult.getMessage());
        }
        if (errorMsg.length() > 0) {
            return ResultVo.failure(errorMsg.toString());
        }
        // 仓库信息较验
        StringBuilder msg;
        boolean isError;
        String[] inventoryKey;
        Map<String, Boolean> customerMap = new HashMap<>();
        for (StockAssemblyDetailDO detail : detailList) {
            // 按 库存类型~仓库代码~客户代码~客户群号~PPL~项目号 拼接
            inventoryKey = detail.getInventoryKeys().split("~", -1);
            msg = new StringBuilder();
            isError = false;
            msg.append(detail.getModelNo()).append(detail.getIsTransOut() ? "调出" : "调入");
            if (inventoryKey[0].startsWith("GK")) {
                if (StringUtils.isBlank(inventoryKey[2])) {
                    isError = true;
                    msg.append("客户代码不能为空.");
                } else {
                    // 校验客户代码有效性 bug-9138
                    if (!customerMap.containsKey(inventoryKey[2])) {
                        customerMap.put(inventoryKey[2], (opsCommonService.getCustomerByCustomerNo(inventoryKey[2]) != null));
                    }
                    if (!customerMap.get(inventoryKey[2])) {
                        isError = true;
                        msg.append("错误客户代码.");
                    }
                }
            }
            //<!--add by WuWeiDong 20230508 bug 10676   -->
            if (inventoryKey[0].equalsIgnoreCase("TY") &&
                    (StringUtils.isNotBlank(inventoryKey[2]) || StringUtils.isNotBlank(inventoryKey[3]) ||
                            StringUtils.isNotBlank(inventoryKey[4]) || StringUtils.isNotBlank(inventoryKey[5]))) {
                isError = true;
                msg.append("通用在库，不能存客户代码，PPL等信息。");
            }
            if ((inventoryKey[0].endsWith("JT") || inventoryKey[0].endsWith("HY")) && StringUtils.isBlank(inventoryKey[3])) {
                isError = true;
                msg.append("集团编号不能为空.");
            }
            if (inventoryKey[0].endsWith("PPL") && StringUtils.isBlank(inventoryKey[4])) {
                isError = true;
                msg.append("PPL不能为空.");
            }
            if (inventoryKey[0].endsWith("PJ") && StringUtils.isBlank(inventoryKey[5])) {
                isError = true;
                msg.append("项目代码不能为空.");
            }
            if (isError) {
                errorMsg.append("</br>").append(msg);
            }
        }
        return errorMsg.length() > 0 ? ResultVo.failure(errorMsg.toString()) : ResultVo.success("库存属性较验通过");
    }

    /**
     * 检查调出型号是否有库存
     *
     * @return boolean
     */

    //<!--add by WuWeiDong 20230508 bug 10678 拆开几个任务并行判断 -->

    /**
     * outCN 标识CN0
     * @param detailList
     * @param outCNFlag
     * @return
     * @throws Exception
     */
    public ResultVo<String> checkTransOutStock(List<StockAssemblyDetailDO> detailList ,boolean outCNFlag ,boolean tkFlag) throws Exception {
        Map<Long, String> mapError = new Hashtable<>();
        try {
            List<StockAssemblyDetailDO> detailListOut = detailList.stream().filter(i -> i.getIsTransOut()).collect(Collectors.toList());
            final Integer size = detailListOut.size();
            final String modelNo = "modelNo";
            final String message = "message";
            CountDownLatch latch_LongAdder = new CountDownLatch(size);

            final Integer offset = 20;
            final int runSize = (size / offset) + 1;
            ExecutorService executor = Executors.newFixedThreadPool(runSize);
//            System.out.println("===  task数:" + runSize + " 尾数：" + size);
            for (Integer idx = 0; idx < size; idx++) {

                final List<StockAssemblyDetailDO> sublist = (idx + offset) >= size ? detailListOut.subList(idx, size) :
                        detailListOut.subList(idx, idx + offset + 1);
                idx = idx + offset;
                final Future<String> future = executor.submit(new Callable<String>() {
                    @Override
                    public String call() throws Exception {
//                        final long statetime = System.currentTimeMillis();
//                        System.out.println("=== start   task:" + Thread.currentThread().getName());
                        try {

                            ModelWarehouseStockRequest dto = new ModelWarehouseStockRequest();
                            StringBuilder errorMsg = new StringBuilder();
                            String[] inventoryKeys;
                            ResultVo<Integer> resultVo;

                            for (StockAssemblyDetailDO detail : sublist) {
                                errorMsg = new StringBuilder();
                                // 按 库存类型~仓库代码~客户代码~客户群号~PPL~项目号 拼接
                                inventoryKeys = detail.getInventoryKeys().split("~", -1);

                                dto = new ModelWarehouseStockRequest();
                                dto.setModelNo(detail.getModelNo());
                                dto.setInventoryTypeCode(inventoryKeys[0]);
                                dto.setWarehouseCode(inventoryKeys[1]);
                                if (inventoryKeys[0].startsWith("GK")) {
                                    dto.setCustomerNo(inventoryKeys[2]);
                                }
                                if (inventoryKeys[0].endsWith("JT") || inventoryKeys[0].endsWith("HY")) {
                                    dto.setGroupCustomerNo(inventoryKeys[3]);
                                }
                                if (inventoryKeys[0].endsWith("PPL")) {
                                    dto.setPpl(inventoryKeys[4]);
                                }
                                if (inventoryKeys[0].endsWith("PJ")) {
                                    dto.setProjectCode(inventoryKeys[5]);
                                }
                                long startTimer = System.currentTimeMillis();
                                // 查询在库
                                SMCApp.setGrantType(GlobalConstant.GrantType.CLIANT_CREDENTIALS);
                                resultVo = inventoryServiceFeignApi.getModelWarehouseStock(dto);
                                ///    System.out.println(" *****" + dto.getModelNo() + "  " + dto.getWarehouseCode() + "   查询结束耗时(s): " + (System.currentTimeMillis() - startTimer) / 1000.0);
                                if (!resultVo.isSuccess()) {
                                    log.error("库存检查-调出型号库存查询失败: params = {}, {}", dto, resultVo);
//                                    mapError.put(detail.getModelNo()+detail.getInventoryKeys(), "库存检查-调出型号库存查询失败");
                                    JSONObject jsonObj = new JSONObject();
                                    jsonObj.put(modelNo, detail.getModelNo());
                                    jsonObj.put(message, "库存检查-调出型号库存查询失败。");
                                    mapError.put(detail.getId(), JSONObject.toJSONString(jsonObj));
                                    return "库存检查-调出型号库存查询失败";
                                }
                                int quantity = resultVo.getData();

                                // 判断是否有库存
                                if ((quantity + detail.getQuantity()) < 0) {
                                    errorMsg.append("调出型号：").append(detail.getModelNo()).append(" 库存不足,")
                                            .append(inventoryKeys[1]).append("仓库,").append(inventoryKeys[0]).append("库存类型");
                                    if (inventoryKeys[0].startsWith("GK")) {
                                        errorMsg.append(",客户").append(inventoryKeys[2]);
                                    }
                                    if (inventoryKeys[0].endsWith("PPL")) {
                                        errorMsg.append(",PPL号").append(inventoryKeys[4]);
                                    }
                                    if (inventoryKeys[0].endsWith("PJ")) {
                                        errorMsg.append(",项目号").append(inventoryKeys[5]);
                                    }
                                    if (inventoryKeys[0].endsWith("JT") || inventoryKeys[0].endsWith("HY")) {
                                        errorMsg.append(",客户群号").append(inventoryKeys[3]);
                                    }
                                    errorMsg.append(",可用数量").append(quantity).append(".</br>");
                                }else {// 总库存满足 验证分子公司库存
                                    //调库不验证
                                    if(!tkFlag){
                                        //bugid:16354 c14717 20250103
                                        Integer subQty = 0;
                                        if(outCNFlag){//验证CN0库存
                                            //total_Inv
                                            ResultVo<Integer> invByModel = inventoryServiceFeignApi.getInvByModel(dto.getModelNo());
                                            if (!invByModel.isSuccess()) {
                                                log.error("验证CN0库存检查-调出型号库存查询失败: params = {}, {}", dto, resultVo);
                                                JSONObject jsonObj = new JSONObject();
                                                jsonObj.put(modelNo, detail.getModelNo());
                                                jsonObj.put(message, "库存检查-调出型号库存查询失败。");
                                                mapError.put(detail.getId(), JSONObject.toJSONString(jsonObj));
                                                return "验证CN0库存检查-调出型号库存查询失败";
                                            }
                                            //CNG_Inv
                                            subQty = expdetailMapper.queryBranchSumDo(detail.getModelNo());
                                            if(Objects.isNull(subQty)){
                                                subQty = 0;
                                            }
                                            int totalQty = invByModel.getData();
                                            totalQty = totalQty - subQty;
                                            if ((totalQty + detail.getQuantity()) < 0) {
                                                //bugid:16354 c14717 20250103
                                                errorMsg.append("不允许申请，自动化资产数量不足").append(".</br>");
                                            }
                                        }else {//验证CNG库存
                                            //CNG_Inv
                                            subQty = expdetailMapper.queryBranchSumDo(detail.getModelNo());
                                            if(Objects.nonNull(subQty) && subQty > 0){
                                                if ((subQty + detail.getQuantity()) < 0) {
                                                    //bugid:16354 c14717 20250103
                                                    errorMsg.append("不允许申请，自动化和广州制造库存不足无法组换").append(".</br>");
                                                }
                                            }else {
                                                errorMsg.append("不允许申请，自动化和广州制造库存不足无法组换").append(".</br>");
                                            }
                                        }
                                    }
                                }
                                if (errorMsg.length() > 0) {
                                    log.error("checkTransOutStock: {}", errorMsg.toString());
                                    //mapError.put(detail.getModelNo()+detail.getInventoryKeys(), errorMsg.toString());
                                    JSONObject jsonObj = new JSONObject();
                                    jsonObj.put(modelNo, detail.getModelNo());
                                    jsonObj.put(message, errorMsg.toString());
                                    mapError.put(detail.getId(), JSONObject.toJSONString(jsonObj));
                                }
                                latch_LongAdder.countDown();
                            }
                        } catch (NullPointerException ex) {
                            System.out.println(Thread.currentThread().getName() + "->错误1：" + ex);
                            throw new Exception(Thread.currentThread().getName() + "->错误1：" + ex);
                        } catch (Exception ex) {
                            System.out.println(Thread.currentThread().getName() + "->错误2：" + ex);
                            throw new Exception(Thread.currentThread().getName() + "->错误2：" + ex);
                        }
//                        System.out.println("===finish task:" + Thread.currentThread().getName() +
//                                "耗时(s):" + (System.currentTimeMillis() - statetime) / 1000.0);
                        return "完成！";
                    }
                });
                Thread.sleep(500);
            }
            StringBuilder rtnErrorMsg = new StringBuilder();
            latch_LongAdder.await();

            if (mapError != null && mapError.size() > 0) {
                // KEY排序
                //1：把map转换成entryset，再转换成保存Entry对象的list。
                List<Map.Entry<Long, String>> sortMap = new ArrayList<>(mapError.entrySet());
                //2：调用Collections.sort(list,comparator)方法把Entry-list排序
                Collections.sort(sortMap, new Comparator<Map.Entry>() {
                    public int compare(Map.Entry o1, Map.Entry o2) {
                        return Long.compare(Long.valueOf(o1.getKey().toString()), Long.valueOf(o2.getKey().toString()));
                    }
                });

                //<!--edit by WuWeiDong 20230614 bug 11089 返回错误的id,型号-->
                List<JSONObject> listJsonObj = new ArrayList<>();
                for (Map.Entry entry : sortMap) {
                    JSONObject jsonObj = new JSONObject();
                    jsonObj.put("id", entry.getKey().toString());
                    jsonObj.put("modelNo", JSON.parseObject(entry.getValue().toString()).getString(modelNo));
                    String msg = JSON.parseObject(entry.getValue().toString()).getString(message);
                    jsonObj.put("message", msg.replace("</br>", ""));
                    rtnErrorMsg.append(msg);
                    listJsonObj.add(jsonObj);
                }
                String json = JSONObject.toJSONString(listJsonObj);
                return ResultVo.failure(json, "", rtnErrorMsg.toString());
            }

//            for (String v : mapError.values()) {
//                rtnErrorMsg.append(v);
//            }
            return ResultVo.success("出库库存充足");
        } catch (Exception e) {
            log.error("检查调出型号是否有库存: {}", e.getMessage(), e);
            return ResultVo.failure("库存检查失败," + e.getMessage());
        }
    }

//        /**
//         * 检查调出型号是否有库存
//         *
//         * @return boolean
//         */
//        private ResultVo<String> checkTransOutStock (List < StockAssemblyDetailDO > detailList) {
//            StringBuilder errorMsg = new StringBuilder();
//            String[] inventoryKeys;
//            ModelWarehouseStockRequest dto;
//            ResultVo<Integer> resultVo;
//            try {
//                int itemNo = 0;
//                List<StockAssemblyDetailDO> detailListOut = detailList.stream().filter(i -> i.getIsTransOut()).collect(Collectors.toList());
//                for (StockAssemblyDetailDO detail : detailListOut) {
//                    if (detail.getIsTransOut()) {
//                        // 按 库存类型~仓库代码~客户代码~客户群号~PPL~项目号 拼接
//                        inventoryKeys = detail.getInventoryKeys().split("~", -1);
//                        itemNo++;
//                        dto = new ModelWarehouseStockRequest();
//                        dto.setModelNo(detail.getModelNo());
//                        dto.setInventoryTypeCode(inventoryKeys[0]);
//                        dto.setWarehouseCode(inventoryKeys[1]);
//                        if (inventoryKeys[0].startsWith("GK")) {
//                            dto.setCustomerNo(inventoryKeys[2]);
//                        }
//                        if (inventoryKeys[0].endsWith("JT") || inventoryKeys[0].endsWith("HY")) {
//                            dto.setGroupCustomerNo(inventoryKeys[3]);
//                        }
//                        if (inventoryKeys[0].endsWith("PPL")) {
//                            dto.setPpl(inventoryKeys[4]);
//                        }
//                        if (inventoryKeys[0].endsWith("PJ")) {
//                            dto.setProjectCode(inventoryKeys[5]);
//                        }
//                        long startTimer = System.currentTimeMillis();
//                        // 查询在库
//                        resultVo = inventoryServiceFeignApi.getModelWarehouseStock(dto);
//                        System.out.println(" *****" + dto.getModelNo() + "  " + dto.getWarehouseCode() + "   查询结束耗时(s): " + (System.currentTimeMillis() - startTimer) / 1000.0);
//                        if (!resultVo.isSuccess()) {
//                            log.error("库存检查-调出型号库存查询失败: params = {}, {}", dto, resultVo);
//                            return ResultVo.failure("库存检查-调出型号库存查询失败");
//                        }
//                        int quantity = resultVo.getData();
//                        // 判断是否有库存
//                        if ((quantity + detail.getQuantity()) < 0) {
//                            errorMsg.append("第").append(itemNo).append("项调出,").append(detail.getModelNo()).append("库存不足,")
//                                    .append(inventoryKeys[1]).append("仓库,").append(inventoryKeys[0]).append("库存类型");
//                            if (inventoryKeys[0].startsWith("GK")) {
//                                errorMsg.append(",客户").append(inventoryKeys[2]);
//                            }
//                            if (inventoryKeys[0].endsWith("PPL")) {
//                                errorMsg.append(",PPL号").append(inventoryKeys[4]);
//                            }
//                            if (inventoryKeys[0].endsWith("PJ")) {
//                                errorMsg.append(",项目号").append(inventoryKeys[5]);
//                            }
//                            if (inventoryKeys[0].endsWith("JT") || inventoryKeys[0].endsWith("HY")) {
//                                errorMsg.append(",客户群号").append(inventoryKeys[3]);
//                            }
//                            errorMsg.append(",可用数量").append(quantity).append(".</br>");
//                        }
//                    }
//                }
//            } catch (Exception e) {
//                log.error("检查调出型号是否有库存: {}", e.getMessage(), e);
//                return ResultVo.failure("库存检查失败," + e.getMessage());
//            }
//            return errorMsg.length() > 0 ? ResultVo.failure(errorMsg.toString()) : ResultVo.success("出库库存充足");
//        }

    /**
     * 按"库存类型~仓库代码~客户代码~客户群号~PPL~项目号"的格式拼接InventoryKeys
     */
    private void spliceInventoryKeys(StockAssemblyItemDTO item) {
        StringBuilder inventoryKeys = new StringBuilder(30);
        inventoryKeys.append(item.getInventoryType()).append("~").append(item.getWarehouseCode()).append("~");

        if (item.getInventoryType().startsWith("GK")) {
            inventoryKeys.append(item.getCustomerNo().replaceAll("[^A-Za-z0-9]+", "").toUpperCase());
        }
        inventoryKeys.append("~");
        if (item.getInventoryType().endsWith("JT") || item.getInventoryType().endsWith("HY")) {
            inventoryKeys.append(item.getGroupCustomerNo().trim());
        }
        inventoryKeys.append("~");
        if (item.getInventoryType().endsWith("PPL")) {
            inventoryKeys.append(item.getPplNo().trim());
        }
        inventoryKeys.append("~");
        if (item.getInventoryType().endsWith("PJ")) {
            inventoryKeys.append(item.getProjectNo().trim());
        }
        item.setInventoryKeys(inventoryKeys.toString());
    }

    /**
     * 按"库存类型~仓库代码~客户代码~客户群号~PPL~项目号"的格式拆分InventoryKeys
     */
    private void splitInventoryKeys(StockAssemblyItemDTO item) {
        String[] inventoryKeys = item.getInventoryKeys().split("~", -1);
        item.setInventoryType(inventoryKeys[0]);
        item.setCustomerNo(inventoryKeys[2]);
        item.setGroupCustomerNo(inventoryKeys[3]);
        item.setPplNo(inventoryKeys[4]);
        item.setProjectNo(inventoryKeys[5]);
    }

    /**
     * 查询调库项的InventoryPropertyId
     */
    private OpsInventoryProperty getInventoryProperty(String inventoryTypeCode, String customerNo, String
            groupCustomerNo,  String ppl, String projectCode) {
        OpsInventoryProperty propertyVO = new OpsInventoryProperty();
        propertyVO.setInventoryTypeCode(inventoryTypeCode);
        if (InventoryTypeEnum.TY.getCode().equals(inventoryTypeCode)) {
            propertyVO.setInventoryPropertyId(1L);
            return propertyVO;
        }
        propertyVO.setInventoryTypeCode(inventoryTypeCode);
        if (inventoryTypeCode.startsWith("GK") && StringUtils.isNotBlank(customerNo)) {
            propertyVO.setCustomerNo(customerNo);
        }
        if (inventoryTypeCode.endsWith("PPL") && StringUtils.isNotBlank(ppl)) {
            propertyVO.setPpl(ppl);
        }
        if (inventoryTypeCode.endsWith("PJ") && StringUtils.isNotBlank(projectCode)) {
            propertyVO.setProjectCode(projectCode);
        }
        if ((inventoryTypeCode.endsWith("JT") || inventoryTypeCode.endsWith("HY")) && StringUtils.isNotBlank(groupCustomerNo)) {
            propertyVO.setGroupCustomerNo(groupCustomerNo);
        }
        CommonResult checkResult = opsPropertyFeignApi.findProperty(propertyVO);
        if (!checkResult.isSuccess() || checkResult.getData() == null) {
            throw new BusinessException("获取出库项的库存属性失败: " + checkResult.getMessage());
        }
        propertyVO.setInventoryPropertyId(Long.parseLong(checkResult.getData().toString()));
        return propertyVO;
    }

    /**
     * 生成申请单号
     *
     * @param applyType 申请类型
     * @return 申请单号
     */
    private String createApplyNo(String applyType) {
        if ("1".equals(applyType)) { // 组换申请 ZH
            return commonServiceFeignApi.generatorBillNo(ASSEMBLY_APPLY_BILLTYPE).getData();
        } else if ("2".equals(applyType)) { // 调库申请 ST
            return commonServiceFeignApi.generatorBillNo(TRANSFER_APPLY_BILLTYPE).getData();
        } else {
            return null;
        }
    }

    /**
     * 生成发票号
     *
     * @param applyId   申请号
     * @param applyType 申请类型 1-组换; 2-调库
     * @return 发票号  M-组换; T-调库
     */
    private String getInvoiceNo(Long applyId, String applyType) {
        if ("1".equals(applyType)) {
            return commonServiceFeignApi.generatorBillNo(Assembly_INVOICE_NO).getData();
        } else if ("2".equals(applyType)) {
            String invoiceNo = this.getInvoiceNo(applyId);
            if (StringUtils.isBlank(invoiceNo)) {
                return commonServiceFeignApi.generatorBillNo(TRANSFER_INVOICE_NO).getData();
            } else {
                return invoiceNo;
            }
        } else {
            return null;
        }
    }

    /**
     * 匹配项号和调库申请项
     */
    private Map<Integer, List<StockAssemblyDetailDO>> handleTransferItem(List<StockAssemblyDetailDO> detailList) {
        // 区分调出/调入
        Map<Boolean, List<StockAssemblyDetailDO>> transferTypeMap = detailList.stream()
                .collect(Collectors.partitioningBy(StockAssemblyDetailDO::getIsTransOut));

        Map<Integer, List<StockAssemblyDetailDO>> itemMap = new HashMap<>(transferTypeMap.get(Boolean.FALSE).size());
        List<StockAssemblyDetailDO> items;
        int itemNo = 0;
        // 唯一约束
        List<Long> idSet = new ArrayList<>(transferTypeMap.get(Boolean.FALSE).size());

        for (StockAssemblyDetailDO outItem : transferTypeMap.get(Boolean.TRUE)) {
            for (StockAssemblyDetailDO inItem : transferTypeMap.get(Boolean.FALSE)) {
                if (idSet.contains(inItem.getId())) {
                    continue;
                }
                if (outItem.getModelNo().equals(inItem.getModelNo()) && outItem.getQuantity() + inItem.getQuantity() == 0) {
                    itemNo++;
                    items = new ArrayList<>(2);
                    items.add(outItem); // index-0 调出
                    items.add(inItem); // index-1 调入
                    itemMap.put(itemNo, items);
                    idSet.add(inItem.getId());
                    break;
                }
            }
        }
        return itemMap;
    }

    /**
     * 查询申请的发票号
     *
     * @param applyId 申请ID
     * @return 发票号
     */
    private String getInvoiceNo(long applyId) {
        QueryWrapper<StockAssemblyDetailDO> queryWrapper = Wrappers.query();
        queryWrapper.select("Top(1) InvoiceNo");
        queryWrapper.lambda().eq(StockAssemblyDetailDO::getApplyId, applyId)
                .isNotNull(StockAssemblyDetailDO::getInvoiceNo)
                .ne(StockAssemblyDetailDO::getInvoiceNo, "");
        StockAssemblyDetailDO detailDO = stockAssemblyDetailMapper.selectOne(queryWrapper);
        return detailDO == null ? null : detailDO.getInvoiceNo();
    }

    @DS("opsdb")
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public Long getInventoryId(String modelNo, String warehouseCode, Long inventoryPropertyId) {
        return stockAssemblyDetailMapper.getInventoryId(modelNo, warehouseCode, inventoryPropertyId);
    }

    private ResultVo<String> canTransferByWarehouseCode(String warehouseCode) {
        ResultVo<WarehouseVO> warehouseVOResultVo = commonServiceFeignApi.getWarehouseInfoByCode(warehouseCode);
        if (!warehouseVOResultVo.isSuccess()) {
            return ResultVo.failure(warehouseVOResultVo.getMessage());
        }
        if (warehouseVOResultVo.getData().getTransferFlag().compareTo(0) == 0) {
            return ResultVo.failure("此仓库不可调库。");
        }
        return ResultVo.success();

    }

    private ResultVo<String> canTransferByWarehouseList(List<String> warehouseList) {
        Map<String, String> mapError = new HashMap<>();
        for (String warehouseCode : warehouseList) {
            ResultVo<String> canTransferVo = this.canTransferByWarehouseCode(warehouseCode);
            if (!canTransferVo.isSuccess()) {
                String errorMsg = canTransferVo.getMessage().substring(0, canTransferVo.getMessage().length() - 1);
                String val = mapError.get(errorMsg);
                if (val == null) {
                    mapError.put(errorMsg, warehouseCode);
                } else {
                    mapError.put(errorMsg, val + "," + warehouseCode);
                }
            }
        }
        if (mapError.values().size() > 0) {
            StringBuilder checkWarehouseMsg = new StringBuilder();
            for (Map.Entry<String, String> entry : mapError.entrySet()) {
                checkWarehouseMsg.append(entry.getKey()).append(":[").append(entry.getValue()).append("]");
            }
            return ResultVo.failure(checkWarehouseMsg.toString());
        }
        return ResultVo.success();
    }
}
