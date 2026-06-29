package com.smc.smccloud.service.impl;

import cn.hutool.json.JSONNull;
import cn.hutool.json.JSONUtil;
import com.alibaba.cloud.commons.lang.StringUtils;
import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sales.ops.db.entity.OpsImpdata;
import com.sales.ops.db.entity.StockTransferPlan;
import com.sales.ops.dto.order.PreorderAccountTransOrderDto;
import com.sales.ops.dto.util.CommonResult;
import com.sales.ops.feign.OpsWmDispatchForOrderFeignApi;
import com.smc.smccloud.constants.CommonConstants;
import com.smc.smccloud.core.exception.BusinessException;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.model.enums.InventoryTypeEnum;
import com.smc.smccloud.core.redis.RedisManager;
import com.smc.smccloud.core.utils.*;
import com.smc.smccloud.mapper.prestock.*;
import com.smc.smccloud.mapper.returnorder.ReturnOrderMapper;
import com.smc.smccloud.mapper.stockassembly.StockAssemblyMapper;
import com.smc.smccloud.model.CustomerVO;
import com.smc.smccloud.model.OrganizationVO;
import com.smc.smccloud.model.RcvDetailDO;
import com.smc.smccloud.model.binorder.ModelExpFreqVO;
import com.smc.smccloud.model.constants.Constants;
import com.smc.smccloud.model.inventory.InventoryVO;
import com.smc.smccloud.model.inventory.OpsInventoryLogVO;
import com.smc.smccloud.model.inventory.OpsInventoryPropertyRequestDTO;
import com.smc.smccloud.model.inventory.OpsInventoryVO;
import com.smc.smccloud.model.preaccount.HandlePreOrderAccountParam;
import com.smc.smccloud.model.preaccount.PreAccountStatusEnum;
import com.smc.smccloud.model.prestock.*;
import com.smc.smccloud.model.product.ProductPriceVO;
import com.smc.smccloud.model.returnorder.ReturnOrderDO;
import com.smc.smccloud.model.salestask.OpsSalesCommonParamVO;
import com.smc.smccloud.model.stock.DealReturnOpsParam;
import com.smc.smccloud.model.stock.DealReturnOpsParamVO;
import com.smc.smccloud.model.stockassembly.StockAssemblyDO;
import com.smc.smccloud.model.trans.TransOrderVO;
import com.smc.smccloud.service.CommonServiceFeignApi;
import com.smc.smccloud.service.OpsCommonService;
import com.smc.smccloud.service.PreOrderAccountHandService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

/**
 * @Author lyc
 * @Date 2024/6/17 13:42
 * @Descripton TODO
 */
@Service
@Slf4j
public class PreOrderAccountHandServiceImpl implements PreOrderAccountHandService {
    @Resource
    private PreOrderAccountCommonMapper preOrderAccountCommonMapper;
    @Resource
    private PreOrderAccountDetailMapper preOrderAccountDetailMapper;

    @Resource
    private StockAssemblyMapper stockAssemblyMapper;

    @Resource
    private PreStockDetailMapper preStockDetailMapper;

    @Resource
    private PreOrderAccountMapper preOrderAccountMapper;

    @Resource
    private OpsCommonService opsCommonService;

    @Resource
    private PreStockApplyMapper preStockApplyMapper;

    @Resource
    private PreStockDetailViewMapper preStockDetailViewMapper;

    @Resource
    private PreorderAccountConfigMapper preorderAccountConfigMapper;

    @Resource
    private CommonServiceFeignApi commonServiceFeignApi;

    @Resource
    private ReturnOrderMapper returnOrderMapper;

    @Resource
    private PreorderAccountRecordsMapper preorderAccountRecordsMapper;


    @Value("${file.base}")
    private String serverPath;

    @Resource
    private PreorderAccountApplyDetailMapper preorderAccountApplyDetailMapper;

    @Resource
    private OpsWmDispatchForOrderFeignApi opsWmDispatchForOrderFeignApi;


    // ALL BIN 型号
    private List<String> binListModelNos = new ArrayList<>();

    @Resource
    private RedisManager redisManager;
    @Override
    public ResultVo<String> updatePreAccountDetail(PreOrderAccountDetailVO info) {
        if (info == null) {
            return ResultVo.failure("入参不可为空");
        }
        if (info.getInventoryId() == null || StringUtils.isBlank(info.getBatchNo()) || info.getInventoryIdItem() == null) {
            return ResultVo.failure("唯一标识不完整.");
        }
        LambdaUpdateWrapper<PreOrderAccountDetailDO> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper
                .eq(PreOrderAccountDetailDO::getInventoryId,info.getInventoryId())
                .eq(PreOrderAccountDetailDO::getBatchNo,info.getBatchNo())
                .eq(PreOrderAccountDetailDO::getInventoryIdItem,info.getInventoryIdItem())
                .set(info.getSureAccountQty() != null,PreOrderAccountDetailDO::getSureAccountQty,info.getSureAccountQty())
                .set(info.getDelayQty() != null,PreOrderAccountDetailDO::getDelayQty,info.getDelayQty())
                .set(PreOrderAccountDetailDO::getDelayDesc,info.getDelayDesc())
                .set(PreOrderAccountDetailDO::getDelayDate,info.getDelayDate())
                .set(StringUtils.isNotBlank(info.getAccountDesc()),PreOrderAccountDetailDO::getAccountDesc,info.getAccountDesc())
                .set(info.getFactDelayQty()!=0,PreOrderAccountDetailDO::getFactDelayQty,info.getFactDelayQty())
                .set(info.getStatus() != null,PreOrderAccountDetailDO::getStatus,info.getStatus())
                .set(PreOrderAccountDetailDO::getApproveQty,info.getApproveQty())
                .set(StringUtils.isNotBlank(info.getAccountApplyNo()),PreOrderAccountDetailDO::getAccountApplyNo,info.getAccountApplyNo())
                .set(PreOrderAccountDetailDO::getModifyTime,new Date());
        preOrderAccountDetailMapper.update(null,updateWrapper);
        return ResultVo.success("更新成功");
    }

    @Override
    public ResultVo<String> handlePreOrderAccountByIds(HandlePreOrderAccountParam param) {
        if (param == null || CollectionUtils.isEmpty(param.getIds())) {
            return ResultVo.failure("入参不可为空");
        }
        LambdaQueryWrapper<PreOrderAccountDetailDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(PreOrderAccountDetailDO::getId,param.getIds());
        List<PreOrderAccountDetailDO> accountDetailDOS = preOrderAccountDetailMapper.selectList(queryWrapper);
        if (CollectionUtils.isEmpty(accountDetailDOS)) {
            return ResultVo.failure("选中数据不存在");
        }

        StringBuilder errormsg = new StringBuilder();
        int itemNo = 1;
        for (PreOrderAccountDetailDO item : accountDetailDOS) {
            Boolean dclFlag = false;
            try {
                if(item.getTransQty() == null) {
                    item.setTransQty(0);
                }
                if(item.getDelayQty() == null) {
                   item.setDelayQty(0);
                }
                OpsInventoryVO inventoryVO = preOrderAccountCommonMapper.getOpsInventoryByInventoryId(item.getInventoryId());
                // 现有效在库数
                int avgQty = 0;

                // 交集部分的logid 和 avgQty
                List<FindAvgQtyRecordsVO> findAvgQtyRecordsVOList = new CopyOnWriteArrayList<>();

                if (PreAccountStatusEnum.dcl.getCode() == item.getStatus()) {
                    item.setSureAccountQty(param.getSureAccountQty());
                    item.setAccountDesc(param.getAccountDesc());
                    LambdaUpdateWrapper<PreOrderAccountDetailDO> updateWrapper = new LambdaUpdateWrapper<>();
                    updateWrapper
                            .eq(PreOrderAccountDetailDO::getId,item.getId())
                            .set(PreOrderAccountDetailDO::getSureAccountQty,param.getSureAccountQty())
                            .set(PreOrderAccountDetailDO::getAccountDesc,param.getAccountDesc())
                            .set(PreOrderAccountDetailDO::getModifier,param.getOptUser())
                            .set(PreOrderAccountDetailDO::getModifyTime,new Date());
                    preOrderAccountDetailMapper.update(null,updateWrapper);
                    dclFlag = true;
                    avgQty = inventoryVO.getQuantity() - inventoryVO.getPrepareQuantity();

                } else {
                    // 根据库存id查询入库日志
                    List<OpsInventoryLogVO> opsInventoryLogByInventoryId = preOrderAccountCommonMapper.getOpsInventoryLogByInventoryId(item.getInventoryId());
                    if (CollectionUtils.isEmpty(opsInventoryLogByInventoryId)) {
                        avgQty = 0;
                    }
                    /**
                     *  重新根据inventory_id反凑入库记录
                     */
                    RecordResultVO resultVO = getInvenrotyLog(opsInventoryLogByInventoryId, inventoryVO.getQuantity() - inventoryVO.getPrepareQuantity());
                    // 合并后的反凑的记录
                    List<OpsInventoryLogVO> invenrotyLog = resultVO.getLogVOList();
                    // 合并前的反凑记录
                    List<FindAvgQtyRecordsVO> findAvgQtyRecordsVOS = resultVO.getFindAvgQtyRecordsVOS();

                    for (OpsInventoryLogVO logVO : invenrotyLog) {
                        if (StringUtils.isNotBlank(logVO.getMerInventoryLogId()) && StringUtils.isNotBlank(item.getInventotyLogId()) ) {
                            /**
                             * 4 3 2 1
                             * 4 3 2
                             * 只取交集 4 3 2 的入库数
                             */
                            // 分割合并后的logid
                            List<String> list = Arrays.asList(logVO.getMerInventoryLogId().split(";"));
                            // 分割account_detail表的logid
                            List<String> list1 = Arrays.asList(item.getInventotyLogId().split(";"));
                            // 取出合并后的logid和account_detail表的logid的交集
                            List<String> intersection = PublicUtil.getIntersection(list, list1);
                            log.info("取出合并后的logid和account_detail表的logid的交集 {}", JSONUtil.toJsonStr(intersection));
                            if(CollectionUtils.isNotEmpty(intersection)) {
                                // 累加交集部分的logid的有效库存
                                for(FindAvgQtyRecordsVO recordsVO: findAvgQtyRecordsVOS) {
                                    for (String logId : intersection) {
                                        if (logId.equals(String.valueOf(recordsVO.getInventoryLogId()))) {
                                            findAvgQtyRecordsVOList.add(recordsVO);
                                            avgQty += recordsVO.getAvgQty();
                                        }
                                    }
                                }
                           }
                        }
                    }
                }
                if(CollectionUtils.isNotEmpty(findAvgQtyRecordsVOList)) {
                    findAvgQtyRecordsVOList =  findAvgQtyRecordsVOList.stream()
                            .sorted(Comparator.comparing(FindAvgQtyRecordsVO::getInventoryLogId).reversed())
                            .collect(Collectors.toList());
                }


                LambdaQueryWrapper<PreOrderAccountDO> query = new LambdaQueryWrapper<>();
                query.eq(PreOrderAccountDO::getInventoryId,item.getInventoryId());
                PreOrderAccountDO preOrderAccountDO = preOrderAccountMapper.selectOne(query);

                if (avgQty <= 0) {
                    // 状态变为已清算,实际延期数量和清算数均修改为0   bug17828修改为 实际延期数量修改为0 清算数保持不变
                    LambdaUpdateWrapper<PreOrderAccountDetailDO> updateWrapper = new LambdaUpdateWrapper<>();
                    updateWrapper
                            .eq(PreOrderAccountDetailDO::getId,item.getId())
                            .set(PreOrderAccountDetailDO::getStatus,PreAccountStatusEnum.yqs.getCode())
                            .set(PreOrderAccountDetailDO::getFactDelayQty,0)
                             // .set(PreOrderAccountDetailDO::getTransQty,0)
                            .set(PreOrderAccountDetailDO::getModifyTime,new Date())
                            .set(PreOrderAccountDetailDO::getModifier,param.getOptUser());
                    preOrderAccountDetailMapper.update(null,updateWrapper);

                    // 修改主表对应数量(不修改调库数,因为调库数只做累加)
                    calPreOrderAccountQty(item.getInventoryId());

                    LambdaUpdateWrapper<PreorderAccountApplyDetailDO> up = new LambdaUpdateWrapper<>();
                    up.eq(PreorderAccountApplyDetailDO::getInventoryId,item.getInventoryId());
                    up.eq(PreorderAccountApplyDetailDO::getInventoryIdItem,item.getInventoryIdItem());
                    up.eq(PreorderAccountApplyDetailDO::getBatchNo,item.getBatchNo());
                    up.set(PreorderAccountApplyDetailDO::getStatus,PreAccountStatusEnum.yqs.getCode())
                            .set(PreorderAccountApplyDetailDO::getFactDelayQty,0)
                            .set(PreorderAccountApplyDetailDO::getTransQty,0)
                            .set(PreorderAccountApplyDetailDO::getModifyTime,new Date())
                            .set(PreorderAccountApplyDetailDO::getModifier,param.getOptUser());
                    preorderAccountApplyDetailMapper.update(null,up);

                    if(!dclFlag) {
                        /**
                         *  回调门户
                         */
                        PreOrderAccountDetailVO preOrderAccountDetailVO= new PreOrderAccountDetailVO();
                        preOrderAccountDetailVO.setInventoryId(item.getInventoryId());
                        preOrderAccountDetailVO.setInventoryIdItem(item.getInventoryIdItem());
                        preOrderAccountDetailVO.setBatchNo(item.getBatchNo());
                        preOrderAccountDetailVO.setTransQty(0);
                        preOrderAccountDetailVO.setFactDelayQty(0);
                        preOrderAccountDetailVO.setTransNo(null);
                        preOrderAccountDetailVO.setWareHouseCode(inventoryVO.getWarehouseCode());
                        if (StringUtils.isNotBlank(inventoryVO.getWarehouseCode())) {
                            String warehouseNameByCode = opsCommonService.getWarehouseNameByCode(inventoryVO.getWarehouseCode());
                            preOrderAccountDetailVO.setWarehouseName(warehouseNameByCode);
                        }
                        preOrderAccountDetailVO.setStatus(PreAccountStatusEnum.yqs.getCode());
                        updateTaskCallBackParam(preOrderAccountDetailVO);
                        continue;
                    }

                }
                if (item.getSureAccountQty() == null) {
                    item.setSureAccountQty(0);
                }
                // 确认延期数 > = 现有效在库数
                if (item.getDelayQty() >= avgQty) {
                    // 更新 实际延期数 = 现在有效库存数
                    LambdaUpdateWrapper<PreOrderAccountDetailDO> updateWrapper = new LambdaUpdateWrapper<>();
                    updateWrapper.eq(PreOrderAccountDetailDO::getId,item.getId())
                            .set(PreOrderAccountDetailDO::getFactDelayQty,avgQty)
                            .set(PreOrderAccountDetailDO::getAvaQty,avgQty)
                            .set(PreOrderAccountDetailDO::getStatus,PreAccountStatusEnum.yqz.getCode())
                            .set(PreOrderAccountDetailDO::getModifyTime,new Date())
                            .set(PreOrderAccountDetailDO::getModifier,param.getOptUser());
                    preOrderAccountDetailMapper.update(null,updateWrapper);

                    findAvgQtyRecordsVOList.remove(0);

                    LambdaUpdateWrapper<PreorderAccountApplyDetailDO> up = new LambdaUpdateWrapper<>();
                    up.eq(PreorderAccountApplyDetailDO::getInventoryId,item.getInventoryId());
                    up.eq(PreorderAccountApplyDetailDO::getInventoryIdItem,item.getInventoryIdItem());
                    up.eq(PreorderAccountApplyDetailDO::getBatchNo,item.getBatchNo());
                    up.set(PreorderAccountApplyDetailDO::getStatus,PreAccountStatusEnum.yqz.getCode())
                            .set(PreorderAccountApplyDetailDO::getFactDelayQty,avgQty)
                            .set(PreorderAccountApplyDetailDO::getTransQty,0)
                            .set(PreorderAccountApplyDetailDO::getAvaQty,avgQty)
                            .set(PreorderAccountApplyDetailDO::getModifyTime,new Date())
                            .set(PreorderAccountApplyDetailDO::getModifier,param.getOptUser());
                    preorderAccountApplyDetailMapper.update(null,up);

                    // 累加主表的延期中数
                    calPreOrderAccountQty(item.getInventoryId());
                    /**
                     * 回调门户
                     */
                    PreOrderAccountDetailVO preOrderAccountDetailVO= new PreOrderAccountDetailVO();
                    preOrderAccountDetailVO.setInventoryId(item.getInventoryId());
                    preOrderAccountDetailVO.setInventoryIdItem(item.getInventoryIdItem());
                    preOrderAccountDetailVO.setBatchNo(item.getBatchNo());
                    preOrderAccountDetailVO.setTransQty(0);
                    preOrderAccountDetailVO.setFactDelayQty(avgQty);
                    preOrderAccountDetailVO.setTransNo(null);
                    preOrderAccountDetailVO.setWareHouseCode(inventoryVO.getWarehouseCode());
                    if (StringUtils.isNotBlank(inventoryVO.getWarehouseCode())) {
                        String warehouseNameByCode = opsCommonService.getWarehouseNameByCode(inventoryVO.getWarehouseCode());
                        preOrderAccountDetailVO.setWarehouseName(warehouseNameByCode);
                    }
                    preOrderAccountDetailVO.setStatus(PreAccountStatusEnum.yqz.getCode());
                    updateTaskCallBackParam(preOrderAccountDetailVO);
                    continue;
                } else {
                    // 更新 实际延期数 = 确认延期数
                    LambdaUpdateWrapper<PreOrderAccountDetailDO> updateWrapper = new LambdaUpdateWrapper<>();
                    updateWrapper.eq(PreOrderAccountDetailDO::getId,item.getId())
                            .set(PreOrderAccountDetailDO::getFactDelayQty,item.getDelayQty())
                            .set(PreOrderAccountDetailDO::getModifyTime,new Date())
                            .set(PreOrderAccountDetailDO::getModifier,param.getOptUser());
                    preOrderAccountDetailMapper.update(null,updateWrapper);
                    if(item.getDelayQty() > 0 ) {
                        int delaynum = item.getDelayQty();
                        Iterator<FindAvgQtyRecordsVO> iterator = findAvgQtyRecordsVOList.iterator();
                        while (iterator.hasNext()) {
                            FindAvgQtyRecordsVO obj = iterator.next();
                            if (obj.getAvgQty() < delaynum) {
                                iterator.remove();
                                delaynum = delaynum - obj.getAvgQty();
                            } else {
                                findAvgQtyRecordsVOList.get(0).setAvgQty(findAvgQtyRecordsVOList.get(0).getAvgQty() - delaynum);
                                break;
                            }
                        }
                    }

                    LambdaUpdateWrapper<PreorderAccountApplyDetailDO> up = new LambdaUpdateWrapper<>();
                    up.eq(PreorderAccountApplyDetailDO::getInventoryId,item.getInventoryId());
                    up.eq(PreorderAccountApplyDetailDO::getInventoryIdItem,item.getInventoryIdItem());
                    up.eq(PreorderAccountApplyDetailDO::getBatchNo,item.getBatchNo());
                    up.set(PreorderAccountApplyDetailDO::getFactDelayQty,item.getDelayQty())
                            .set(PreorderAccountApplyDetailDO::getModifyTime,new Date())
                            .set(PreorderAccountApplyDetailDO::getModifier,param.getOptUser());
                    preorderAccountApplyDetailMapper.update(null,up);


                    // 确认决算数 >= 现有效在库数 - 实际延期数   多的那部分进行清算
                    String trunkNo = commonServiceFeignApi.generatorBillNo("34").getData();
                    int facTrunQty = 0;
                    PreorderAccountTransOrderDto transOrderVO;
                    if(item.getSureAccountQty() >= avgQty -item.getDelayQty() ) {
                        // 生成调库单 数量为现有效在库数 - 实际延期数
                        transOrderVO = conventerTransOrderVO(item, preOrderAccountDO, trunkNo, itemNo, avgQty - item.getDelayQty());
                    } else {
                        // 生成调库单 数量为确认决算数
                        transOrderVO = conventerTransOrderVO(item, preOrderAccountDO, trunkNo, itemNo, item.getSureAccountQty());
                    }
                    trunkNo = trunkNo+"-1";
                    log.info("生成调拨单入参: {}",JSONUtil.toJsonStr(transOrderVO));
                    /**
                     * 生成调拨单
                     */
                    CommonResult<Map> transOrderForPreorderAccount = opsWmDispatchForOrderFeignApi.createTransOrderForPreorderAccount(transOrderVO);
                    log.info("生成调拨单结果: {}",JSONUtil.toJsonStr(transOrderForPreorderAccount));
                    if (transOrderForPreorderAccount!=null && transOrderForPreorderAccount.getData() == null) {
                        throw new BusinessException(transOrderForPreorderAccount.getMessage());
                    }

                    Map data = transOrderForPreorderAccount.getData();
                    Object quantity = data.get("quantity");
                    if (quantity != null) {
                        facTrunQty = Integer.parseInt(quantity.toString());
                        /**
                         * 记录决算数量
                         */
                        if(CollectionUtils.isEmpty(findAvgQtyRecordsVOList)) {
                            log.error("记录"+item.getInventoryId()+" inventoryLogId:"+item.getInventotyLogId()+"异常,未找到交集logid");
                            throw new BusinessException("记录"+item.getInventoryId()+" inventoryLogId:"+item.getInventotyLogId()+"异常,未找到交集logid");
                        }
                        for (FindAvgQtyRecordsVO vo : findAvgQtyRecordsVOList) {
                            insertPreOrderAccountRecords(vo.getInventoryLogId(),vo.getAvgQty(),vo.getOrderNo());
                        }
                    }
                    // 更新决算明细表里的清算数 = 上次清算数+实际调库数
                    LambdaUpdateWrapper<PreOrderAccountDetailDO> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
                    lambdaUpdateWrapper
                            .eq(PreOrderAccountDetailDO::getId,item.getId())
                            .set(PreOrderAccountDetailDO::getTransQty,item.getTransQty()+facTrunQty)
                            .set(PreOrderAccountDetailDO::getTransTime,new Date())
                            .set(PreOrderAccountDetailDO::getModifyTime,new Date())
                            .set(PreOrderAccountDetailDO::getModifier,param.getOptUser());
                    if (StringUtils.isNotBlank(item.getTransNo())) {
                        lambdaUpdateWrapper.set(PreOrderAccountDetailDO::getTransNo,item.getTransNo()+";"+trunkNo);
                    } else {
                        lambdaUpdateWrapper.set(PreOrderAccountDetailDO::getTransNo,trunkNo);
                    }
                    // 实际清算数 < 确认决算数   有效在库 = 0
                    // 实际清算数 = 确认决算数 有效在库 = 有效在库 - 清算数
                    if (item.getSureAccountQty() == facTrunQty) {
                        lambdaUpdateWrapper.set(PreOrderAccountDetailDO::getAvaQty,item.getAvaQty() - facTrunQty);
                    } else if (facTrunQty < item.getSureAccountQty() ) {
                        lambdaUpdateWrapper.set(PreOrderAccountDetailDO::getAvaQty,0);
                    }
                    if (item.getDelayQty() > 0) {
                        lambdaUpdateWrapper.set(PreOrderAccountDetailDO::getStatus,PreAccountStatusEnum.yqz.getCode());
                    } else {
                        lambdaUpdateWrapper.set(PreOrderAccountDetailDO::getStatus,PreAccountStatusEnum.yqs.getCode());
                    }
                    preOrderAccountDetailMapper.update(null,lambdaUpdateWrapper);


                    LambdaUpdateWrapper<PreorderAccountApplyDetailDO> doLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
                    doLambdaUpdateWrapper
                            .eq(PreorderAccountApplyDetailDO::getInventoryId,item.getInventoryId())
                            .eq(PreorderAccountApplyDetailDO::getInventoryIdItem,item.getInventoryIdItem())
                            .eq(PreorderAccountApplyDetailDO::getBatchNo,item.getBatchNo())
                            .set(PreorderAccountApplyDetailDO::getTransQty,facTrunQty)
                            .set(PreorderAccountApplyDetailDO::getTransNo,trunkNo)
                            .set(PreorderAccountApplyDetailDO::getTransTime,new Date())
                            .set(PreorderAccountApplyDetailDO::getModifyTime,new Date())
                            .set(PreorderAccountApplyDetailDO::getModifier,param.getOptUser());
                    if (item.getDelayQty() > 0) {
                        doLambdaUpdateWrapper.set(PreorderAccountApplyDetailDO::getStatus,PreAccountStatusEnum.yqz.getCode());
                    } else {
                        doLambdaUpdateWrapper.set(PreorderAccountApplyDetailDO::getStatus,PreAccountStatusEnum.yqs.getCode());
                    }
                    preorderAccountApplyDetailMapper.update(null,doLambdaUpdateWrapper);


                    if (preOrderAccountDO.getTransQty() == null) {
                        preOrderAccountDO.setTransQty(0);
                    }

                    // 更新主表的最终调库数量
                    LambdaUpdateWrapper<PreOrderAccountDO> accountDOLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
                    accountDOLambdaUpdateWrapper
                            .eq(PreOrderAccountDO::getId,preOrderAccountDO.getId())
                            .set(PreOrderAccountDO::getTransQty,preOrderAccountDO.getTransQty()+facTrunQty)
                            .set(PreOrderAccountDO::getAvaQty,preOrderAccountDO.getAvaQty() - facTrunQty)
                            .set(PreOrderAccountDO::getModifyTime,new Date());
                    preOrderAccountMapper.update(null,accountDOLambdaUpdateWrapper);

                    calPreOrderAccountQty(item.getInventoryId());

                    if (!dclFlag) {
                        /**
                         * 回调门户
                         */
                        PreOrderAccountDetailVO preOrderAccountDetailVO= new PreOrderAccountDetailVO();
                        preOrderAccountDetailVO.setInventoryId(item.getInventoryId());
                        preOrderAccountDetailVO.setInventoryIdItem(item.getInventoryIdItem());
                        preOrderAccountDetailVO.setBatchNo(item.getBatchNo());
                        preOrderAccountDetailVO.setTransQty(facTrunQty);
                        preOrderAccountDetailVO.setFactDelayQty(item.getDelayQty());
                        preOrderAccountDetailVO.setTransNo(trunkNo);
                        preOrderAccountDetailVO.setWareHouseCode(inventoryVO.getWarehouseCode());
                        if (StringUtils.isNotBlank(inventoryVO.getWarehouseCode())) {
                            String warehouseNameByCode = opsCommonService.getWarehouseNameByCode(inventoryVO.getWarehouseCode());
                            preOrderAccountDetailVO.setWarehouseName(warehouseNameByCode);
                        }
                        if (item.getDelayQty() > 0) {
                            preOrderAccountDetailVO.setStatus(PreAccountStatusEnum.yqz.getCode());
                        } else {
                            preOrderAccountDetailVO.setStatus(PreAccountStatusEnum.yqs.getCode());
                        }
                        preOrderAccountDetailVO.setAccountApplyNo(item.getAccountApplyNo());
                        updateTaskCallBackParam(preOrderAccountDetailVO);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                errormsg.append("订单号:").append(item.getOrderNo()).append("型号:").append(item.getModelNo()).append(":").append(e.getMessage()).append(";");
                continue;
            }
        }
        if (StringUtils.isNotBlank(errormsg)) {
            return ResultVo.failure(errormsg.toString());
        }
        return ResultVo.success("处理成功");
    }

    public ResultVo<String> calPreOrderAccountQty(Long inventoryId) {

        LambdaQueryWrapper<PreOrderAccountDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PreOrderAccountDO::getInventoryId,inventoryId);
        List<PreOrderAccountDO> preOrderAccountDOS = preOrderAccountMapper.selectList(queryWrapper);
        if (CollectionUtils.isEmpty(preOrderAccountDOS)) {
            return ResultVo.failure(inventoryId+"没有决算总表数据");
        }

        // 查询明细数据
        LambdaQueryWrapper<PreOrderAccountDetailDO> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(PreOrderAccountDetailDO::getInventoryId,inventoryId);
        lambdaQueryWrapper.eq(PreOrderAccountDetailDO::getDelFlag,0);
        List<PreOrderAccountDetailDO> accountDetailDOList = preOrderAccountDetailMapper.selectList(lambdaQueryWrapper);
        if (CollectionUtils.isEmpty(accountDetailDOList)) {
            return ResultVo.failure(inventoryId+"没有决算明细表数据");
        }
        int avgQty = accountDetailDOList.stream()
                .mapToInt(detail -> detail.getAvaQty() == null ? 0 : detail.getAvaQty())
                .sum();
        int pushQty = accountDetailDOList.stream()
                .mapToInt(detail -> detail.getPushQty() == null ? 0 : detail.getPushQty())
                .sum();

        int factDelayQty = accountDetailDOList.stream()
                .mapToInt(detail -> detail.getFactDelayQty() == 0 ? 0 : detail.getFactDelayQty())
                .sum();
        // 审批中数
        int approveQty = accountDetailDOList.stream()
                .mapToInt(detail -> detail.getApproveQty() == 0 ? 0 : detail.getApproveQty())
                .sum();

        Integer avgMonth = null;
        if(preOrderAccountDOS.get(0).getAverageof12() != null && preOrderAccountDOS.get(0).getAverageof12() != 0 && avgQty != 0) {
            // 保有月
            avgMonth = avgQty/preOrderAccountDOS.get(0).getAverageof12();
        }
        // emount
        BigDecimal sumEmount = accountDetailDOList.stream()
                .filter(detail -> detail.getStatus() != PreAccountStatusEnum.zbjs.getCode())
                .map(detail -> Optional.ofNullable(detail.getEAmount()).orElse(BigDecimal.ZERO))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // 更新主表数据
        LambdaUpdateWrapper<PreOrderAccountDO> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper
                .eq(PreOrderAccountDO::getInventoryId,inventoryId)
                .set(PreOrderAccountDO::getAvaQty,avgQty)
                .set(PreOrderAccountDO::getPushQty,pushQty)
                .set(PreOrderAccountDO::getDelayQty,factDelayQty)
                .set(PreOrderAccountDO::getApproveQty,approveQty)
                .set(PreOrderAccountDO::getEAmount,sumEmount)
                .set(PreOrderAccountDO::getModifyTime,new Date())
                .set(avgMonth != null,PreOrderAccountDO::getRetentionMonth,avgMonth)
        ;
        preOrderAccountMapper.update(null,lambdaUpdateWrapper);
        return ResultVo.success("处理成功");
    }

    @Override
    public void downYQPZTemplate(HttpServletResponse response) {
        String path = "template/决算延期设定.xlsx";
        ExcelUtil excel = new ExcelUtil(path);
        excel.writeToResponse(response, "决算延期设定.xlsx");
    }

    @Override
    public void insertPreOrderAccountRecords(Long logId, int accountId,String orderNo) {
        PreorderAccountRecordsDO preorderAccountRecordsDO = new PreorderAccountRecordsDO();
        preorderAccountRecordsDO.setInventoryLogId(logId);
        preorderAccountRecordsDO.setAccountQty(accountId);
        preorderAccountRecordsDO.setCreateTime(new Date());
        preorderAccountRecordsDO.setOrderNo(orderNo);
        preorderAccountRecordsMapper.insert(preorderAccountRecordsDO);
    }

    public void updateTaskCallBackParam(PreOrderAccountDetailVO info) {
        OpsSalesCommonParamVO vo = new OpsSalesCommonParamVO();

        DealReturnOpsParamVO dealReturnOpsParamVO = new DealReturnOpsParamVO();
        dealReturnOpsParamVO.setApplyType(12); // 先行在库预决算回调

        DealReturnOpsParam dealReturnOpsParam = new DealReturnOpsParam();
        dealReturnOpsParam.setReturnParam(info);
        dealReturnOpsParamVO.setDealReturnOpsParam(dealReturnOpsParam);
        vo.setData(dealReturnOpsParamVO);
        preOrderAccountCommonMapper.updateTaskByApplyNo(info.getAccountApplyNo(),JSONUtil.toJsonStr(vo));

    }

    public RecordResultVO getInvenrotyLog(List<OpsInventoryLogVO> opsInventoryLogByInventoryId,int quantity) {
        List<OpsInventoryLogVO> newInventoryList = new ArrayList<>();
        int num = quantity;
        for (OpsInventoryLogVO item: opsInventoryLogByInventoryId) {
            /**
             * 1.当反凑数量的订单号是以88开头时
             * 2.拿到订单到prostock_detail表中找到apply_id 再根据apply_id到prestock_apply表中找到trans_flag和 warehouse_code
             * 3.根据订单号到ops_impdata表找到receiveWarehouseId  (作废)
             * 4. 入库记录里的warehouse_code = 申请仓 prestock_apply  计算
             *    入库记录里的warehouse_code<>申请仓 prestock_apply  判断transflag标识，transflag标识为1时不计算此条入库记录，transflag为空时默认为0
             */
            try {
                if (num <= 0) {
                    break;
                }
                // 1.当反凑数量的订单号是以88开头时
                if (!item.getOrderNo().startsWith("88")) {
                    // 匹配记录的inventory_log_id 看是否已经决算过
                    int alreadyAdjuQty = getRecordNum(item.getInventoryLogId());
                    if (item.getQuantity() - alreadyAdjuQty <= 0) {
                        continue;
                    }
                    item.setQuantity(item.getQuantity()-alreadyAdjuQty);
                    int lastQty = num;
                    num -= item.getQuantity();
                    if(num<=0) {
                        item.setAvaQty(lastQty);
                    } else {
                        item.setAvaQty(item.getQuantity());
                    }
                    newInventoryList.add(item);
                    continue;
                }
                // 2.拿到订单到prostock_detail表中找到apply_id 再根据apply_id到prestock_apply表中找到trans_flag和 warehouse_code
                LambdaQueryWrapper<PreStockDetailDO> lambdaQueryWrapper = new LambdaQueryWrapper<>();
                lambdaQueryWrapper.likeRight(PreStockDetailDO::getOrderNos,item.getOrderNo()+"-"+item.getItemNo());
                List<PreStockDetailDO> preStockDetailDOS = preStockDetailMapper.selectList(lambdaQueryWrapper);
                if (CollectionUtils.isEmpty(preStockDetailDOS)) {

                    // 匹配记录的inventory_log_id 看是否已经决算过
                    int alreadyAdjuQty = getRecordNum(item.getInventoryLogId());

                    if (item.getQuantity() - alreadyAdjuQty <= 0) {
                        continue;
                    }
                    item.setQuantity(item.getQuantity()-alreadyAdjuQty);

                    int lastQty = num;
                    num -= item.getQuantity();
                    if(num<=0) {
                        item.setAvaQty(lastQty);
                    } else {
                        item.setAvaQty(item.getQuantity());
                    }
                    newInventoryList.add(item);
                    continue;
                }
                LambdaQueryWrapper<PreStockApplyDO> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.eq(PreStockApplyDO::getId,preStockDetailDOS.get(0).getApplyId());
                PreStockApplyDO preStockApplyDO = preStockApplyMapper.selectOne(queryWrapper);
                if (preStockApplyDO == null) {

                    // 匹配记录的inventory_log_id 看是否已经决算过
                    int alreadyAdjuQty = getRecordNum(item.getInventoryLogId());
                    if (item.getQuantity() - alreadyAdjuQty <= 0) {
                        continue;
                    }
                    item.setQuantity(item.getQuantity()-alreadyAdjuQty);

                    int lastQty = num;
                    num -= item.getQuantity();
                    if(num<=0) {
                        item.setAvaQty(lastQty);
                    } else {
                        item.setAvaQty(item.getQuantity());
                    }
                    newInventoryList.add(item);
                    continue;
                }
                // 3.根据订单号到ops_impdata表找到receiveWarehouseId 作废
//                List<OpsImpdata> opsImpdataByOrderNo = preOrderAccountCommonMapper.getOpsImpdataByOrderNo(item.getOrderNo(), item.getItemNo());
//                if (CollectionUtils.isEmpty(opsImpdataByOrderNo)) {
//                    // 匹配记录的inventory_log_id 看是否已经决算过
//                    int alreadyAdjuQty = getRecordNum(item.getInventoryLogId());
//                    if (item.getQuantity() - alreadyAdjuQty <= 0) {
//                        continue;
//                    }
//                    item.setQuantity(item.getQuantity()-alreadyAdjuQty);
//                    int lastQty = num;
//                    num -= item.getQuantity();
//                    if(num<=0) {
//                        item.setAvaQty(lastQty);
//                    } else {
//                        item.setAvaQty(item.getQuantity());
//                    }
//                    newInventoryList.add(item);
//                    continue;
//                }
                // 4.入库记录里的warehouse_code<>申请仓 prestock_apply  判断transflag标识，transflag标识为1时不计算此条入库记录，transflag为空时默认为0  不计算此条入库记录
                String warehouseCode = preStockApplyDO.getWarehouseCode();
                Boolean transFlag = preStockApplyDO.getTransFlag();
                if (transFlag == null) {
                    transFlag = false;
                }
                String receiveWarehouseCode = item.getWarehouseCode();
                if (StringUtils.isNotBlank(warehouseCode) && StringUtils.isNotBlank(receiveWarehouseCode)) {
                    if (!warehouseCode.equals(receiveWarehouseCode)) {
                        if (transFlag) {
                            continue;
                        }
                    }
                }
                // 匹配记录的inventory_log_id 看是否已经决算过
                int alreadyAdjuQty = getRecordNum(item.getInventoryLogId());
                if (item.getQuantity() - alreadyAdjuQty <= 0) {
                    continue;
                }
                item.setQuantity(item.getQuantity()-alreadyAdjuQty);
                int lastQty = num;
                num -= item.getQuantity();
                if(num<=0) {
                    item.setAvaQty(lastQty);
                } else {
                    item.setAvaQty(item.getQuantity());
                }
                newInventoryList.add(item);
            } catch (Exception e) {
                log.error("异常数据: {}",JSONUtil.toJsonStr(item));
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }

        // 根据相同ro_id,相同订单号,相同入库日期 合并入库记录
        Map<String,OpsInventoryLogVO> map = null;

        List<FindAvgQtyRecordsVO> recordResultVOList = new ArrayList<>();

        try {
            map = new HashMap<>();
            for(OpsInventoryLogVO item : newInventoryList) {
                FindAvgQtyRecordsVO findAvgQtyRecordsVO = new FindAvgQtyRecordsVO();
                findAvgQtyRecordsVO.setInventoryLogId(item.getInventoryLogId());
                findAvgQtyRecordsVO.setAvgQty(item.getAvaQty());
                findAvgQtyRecordsVO.setOrderNo(item.getOrderNo()+"-"+item.getItemNo());
                recordResultVOList.add(findAvgQtyRecordsVO);

                String key = item.getVoucherCode()+item.getOrderNo()+item.getItemNo()+DateUtil.dateToDateString(item.getCreTime());
                if (map.containsKey(key)) {
                    OpsInventoryLogVO opsInventoryLog = map.get(key);
                    opsInventoryLog.setQuantity(opsInventoryLog.getQuantity()+item.getQuantity());
                    opsInventoryLog.setAvaQty(opsInventoryLog.getAvaQty()+item.getAvaQty());
                    opsInventoryLog.setMerInventoryLogId(opsInventoryLog.getMerInventoryLogId()+";"+item.getInventoryLogId());
                    map.put(key,opsInventoryLog);
                } else {
                    if(item.getInventoryLogId() != null) {
                        item.setMerInventoryLogId(String.valueOf(item.getInventoryLogId()));
                    }
                    map.put(key,item);
                }
            }
        } catch (Exception e) {
            log.error("根据相同ro_id,相同订单号,相同入库日期 合并入库记录 => 异常数据: {}",JSONUtil.toJsonStr(newInventoryList));
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
        RecordResultVO vo = new RecordResultVO();
        vo.setLogVOList(new ArrayList<>(map.values().stream()
                .sorted(Comparator.comparing(OpsInventoryLogVO::getCreTime).reversed())
                .collect(Collectors.toList())));
        vo.setFindAvgQtyRecordsVOS(recordResultVOList);
        return vo;
    }

    private int getRecordNum(Long inventoryLogId) {
        LambdaQueryWrapper<PreorderAccountRecordsDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PreorderAccountRecordsDO::getInventoryLogId,inventoryLogId);
        List<PreorderAccountRecordsDO> preorderAccountRecordsDOS = preorderAccountRecordsMapper.selectList(queryWrapper);
        if(CollectionUtils.isEmpty(preorderAccountRecordsDOS)) {
            return 0;
        }
        return preorderAccountRecordsDOS.stream().mapToInt(PreorderAccountRecordsDO::getAccountQty).sum();
    }

    @Override
    public ResultVo<String> handNotPushSalePreAccount(HandlePreOrderAccountParam param) {

        if (param == null || CollectionUtils.isEmpty(param.getIds())) {
            return ResultVo.failure("入参不可为空");
        }

        LambdaQueryWrapper<PreOrderAccountDetailDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(PreOrderAccountDetailDO::getId,param.getIds());
        List<PreOrderAccountDetailDO> accountDetailDOS = preOrderAccountDetailMapper.selectList(queryWrapper);
        if (CollectionUtils.isEmpty(accountDetailDOS)) {
            return ResultVo.failure("选中数据不存在");
        }
        StringBuilder errormsg = new StringBuilder();
        int itemNo = 0;
        for (PreOrderAccountDetailDO item : accountDetailDOS) {
            try {
                itemNo++;
                OpsInventoryVO inventoryVO = preOrderAccountCommonMapper.getOpsInventoryByInventoryId(item.getInventoryId());

                LambdaQueryWrapper<PreOrderAccountDO> query = new LambdaQueryWrapper<>();
                query.eq(PreOrderAccountDO::getInventoryId,item.getInventoryId());
                PreOrderAccountDO preOrderAccountDO = preOrderAccountMapper.selectOne(query);

                String trunkNo = commonServiceFeignApi.generatorBillNo("34").getData();

                // 现有效在库数
                int avgQty= inventoryVO.getQuantity()-inventoryVO.getPrepareQuantity();
                if (avgQty <= 0) {
                    LambdaUpdateWrapper<PreOrderAccountDetailDO> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
                    lambdaUpdateWrapper
                            .eq(PreOrderAccountDetailDO::getId,item.getId())
                            .set(PreOrderAccountDetailDO::getStatus,PreAccountStatusEnum.yqs.getCode())
                            .set(PreOrderAccountDetailDO::getFactDelayQty,0)
                            .set(PreOrderAccountDetailDO::getTransQty,0)
                            .set(PreOrderAccountDetailDO::getAccountDesc,param.getAccountDesc())
                            .set(PreOrderAccountDetailDO::getSureAccountQty,param.getSureAccountQty())
                            .set(PreOrderAccountDetailDO::getModifyTime,new Date())
                            .set(PreOrderAccountDetailDO::getModifier,param.getOptUser());
                    preOrderAccountDetailMapper.update(null,lambdaUpdateWrapper);
                } else {
                    int facTrunQty = 0;
                    CommonResult<Map> transOrderForPreorderAccount;
                    if(item.getSureAccountQty() >= avgQty) {
                        PreorderAccountTransOrderDto transOrderVO = conventerTransOrderVO(item, preOrderAccountDO, trunkNo, itemNo, avgQty);
                        /**
                         * 调用调库接口
                         */
                        transOrderForPreorderAccount = opsWmDispatchForOrderFeignApi.createTransOrderForPreorderAccount(transOrderVO);
                    } else {
                        PreorderAccountTransOrderDto transOrderVO = conventerTransOrderVO(item, preOrderAccountDO, trunkNo, itemNo, item.getSureAccountQty());
                        /**
                         * 调用调库接口
                         */
                        transOrderForPreorderAccount = opsWmDispatchForOrderFeignApi.createTransOrderForPreorderAccount(transOrderVO);
                    }
                    Map data = transOrderForPreorderAccount.getData();
                    Object quantity = data.get("quantity");
                    if (quantity != null) {
                        facTrunQty = Integer.parseInt(quantity.toString());
                    }
                    // 更新决算明细想关指标
                    LambdaUpdateWrapper<PreOrderAccountDetailDO> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
                    lambdaUpdateWrapper
                            .eq(PreOrderAccountDetailDO::getId,item.getId())
                            .set(PreOrderAccountDetailDO::getStatus,PreAccountStatusEnum.yqs.getCode())
                            .set(PreOrderAccountDetailDO::getFactDelayQty,0)
                            .set(PreOrderAccountDetailDO::getTransQty,facTrunQty)
                            .set(PreOrderAccountDetailDO::getAccountDesc,param.getAccountDesc())
                            .set(PreOrderAccountDetailDO::getSureAccountQty,param.getSureAccountQty())
                            .set(PreOrderAccountDetailDO::getModifyTime,new Date())
                            .set(PreOrderAccountDetailDO::getModifier,param.getOptUser());
                    preOrderAccountDetailMapper.update(null,lambdaUpdateWrapper);

                    // 更新主表的最终调库数量
                    LambdaUpdateWrapper<PreOrderAccountDO> accountDOLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
                    accountDOLambdaUpdateWrapper
                            .eq(PreOrderAccountDO::getId,preOrderAccountDO.getId())
                            .set(PreOrderAccountDO::getTransQty,preOrderAccountDO.getTransQty()+facTrunQty)
                            .set(PreOrderAccountDO::getModifyTime,new Date());
                    preOrderAccountMapper.update(null,accountDOLambdaUpdateWrapper);
                }
            } catch (Exception e) {
                errormsg.append("订单号:").append(item.getOrderNo()).append("型号:").append(item.getModelNo()).append(":").append(e.getMessage()).append(";");
            }
        }

        if (StringUtils.isNotBlank(errormsg)) {
            return ResultVo.failure(errormsg.toString());
        }
        return ResultVo.success("处理成功");
    }

    @Override
    public ResultVo<String> crePreOrderAccountDataNew() {

        // 1.增量查询inventory表，找到inventory表quantity-pre_quantity>0的顾客在库&战略在库的inventory_id
        // (顾客在库&战略在库是ops_inventory_property.invenotry_type_code为GK-PJ，GK-PPL,GK-TY,ZL-CP,ZL-HY,ZL-JT,ZL-PJ,或者除了TY和QB_NO)
        OpsInventoryPropertyRequestDTO inventoryProperty = new OpsInventoryPropertyRequestDTO();
        inventoryProperty.setIsPres(true);
        inventoryProperty.setDateType(2);
        List<InventoryVO> inventoryVOS = preOrderAccountCommonMapper.listInventoryByProperty(inventoryProperty);
        if (CollectionUtils.isEmpty(inventoryVOS)) {
            return ResultVo.failure("暂无所需计算的有效在库库存");
        }

        // 将preorder_count_detail表中状态为暂不决算 或者 待决算且没有提前决算的标识的数据 置为无效delflag=1
        preOrderAccountCommonMapper.upPreorderAccountDetailDelFlag();
        // 其他数据的有效在库数量为0 =>  delFlag != 1
        preOrderAccountCommonMapper.upPreorderAccountDetailAvgQty();

        try {
            List<String> modelNosList = inventoryVOS.stream().map(InventoryVO::getModelNo).distinct().collect(Collectors.toList());

            List<List<String>> subList = ListUtils.partition(modelNosList, 1800);
            for (List<String> item : subList) {
                List<String> bin = preOrderAccountCommonMapper.isBin(item);
                if(CollectionUtils.isNotEmpty(bin)) {
                    // ALL BIN型号
                    binListModelNos.addAll(bin);
                }
            }

            ExecutorService fixedThreadPoolInsert = Executors.newFixedThreadPool(10);

            try {
                for (InventoryVO item: inventoryVOS) {
                    fixedThreadPoolInsert.execute(() -> {
                        // 根据库存id查询入库日志
                        List<OpsInventoryLogVO> opsInventoryLogByInventoryId = preOrderAccountCommonMapper.getOpsInventoryLogByInventoryId(item.getId());
                        if (CollectionUtils.isEmpty(opsInventoryLogByInventoryId)) {
                            addPreOrderAccountWithDetail(item);
                        } else {
                            try {
                                conventInventoryLogVO(opsInventoryLogByInventoryId,item);
                            } catch (Exception e) {
                                log.error("conventInventoryLogVO方法异常 {},{}",JSONUtil.toJsonStr(item),e.getMessage());
                                e.printStackTrace();
                                throw new RuntimeException("conventInventoryLogVO方法异常 =>"+e.getMessage(),e);
                            }
                        }
                    });
                }
            } finally {
                fixedThreadPoolInsert.shutdown();
                while (true) {
                    if (fixedThreadPoolInsert.isTerminated()) {
                        break;
                    }
                }
            }

            Thread.sleep(3000*60);

            /**
             *  preorder_account_detail表有效在库数为0且状态不是已清算的有效数据  更新状态为已清算 实际延期数量为0
             */
            LambdaUpdateWrapper<PreOrderAccountDetailDO> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper
                    .eq(PreOrderAccountDetailDO::getDelFlag,0)
                    .eq(PreOrderAccountDetailDO::getAvaQty,0)
                    .ne(PreOrderAccountDetailDO::getStatus,PreAccountStatusEnum.yqs.getCode())
                    .set(PreOrderAccountDetailDO::getStatus,PreAccountStatusEnum.yqs.getCode())
                    .set(PreOrderAccountDetailDO::getFactDelayQty,0)
                    .set(PreOrderAccountDetailDO::getModifyTime,new Date());
            preOrderAccountDetailMapper.update(null,updateWrapper);


        } catch (Exception e) {
            throw new RuntimeException("生成先行在库预决算数据异常: "+e.getMessage(),e);
        } finally {
            binListModelNos.clear();
        }
        return ResultVo.success("计算完毕");
    }

    @Override
    public ResultVo<String> insertPreOrderAccountApplyDetailData(PreOrderAccountDetailVO info) {
        LambdaQueryWrapper<PreOrderAccountDetailDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PreOrderAccountDetailDO::getInventoryId,info.getInventoryId())
                .eq(PreOrderAccountDetailDO::getBatchNo,info.getBatchNo())
                .eq(PreOrderAccountDetailDO::getInventoryIdItem,info.getInventoryIdItem());
        PreOrderAccountDetailDO preOrderAccountDetailDO = preOrderAccountDetailMapper.selectOne(queryWrapper);
        if (preOrderAccountDetailDO == null) {
            return ResultVo.failure(info.getInventoryId()+" "+info.getBatchNo()+" "+info.getInventoryIdItem()+" 数据不存在");
        }
        PreorderAccountApplyDetailDO detailDO = BeanCopyUtil.copy(preOrderAccountDetailDO, PreorderAccountApplyDetailDO.class);
        detailDO.setId(null);
        detailDO.setSureAccountQty(info.getSureAccountQty());
        detailDO.setDelayQty(info.getDelayQty());
        detailDO.setDelayDesc(info.getDelayDesc());
        detailDO.setDelayDate(info.getDelayDate());
        detailDO.setStatus(info.getStatus());
        detailDO.setAccountApplyNo(info.getAccountApplyNo());
        detailDO.setDelFlag(0);
        detailDO.setTransQty(0);
        detailDO.setTransNo(null);
        detailDO.setCreateTime(new Date());
        detailDO.setModifyTime(new Date());
        preorderAccountApplyDetailMapper.insert(detailDO);
        return ResultVo.success("新增成功");
    }

    @Override
    public ResultVo<PageInfo<PreOrderAccountDetailDTO>> listPreOrderApplyDetail(PreOrderAccountRequest request) {

        if (request.getTransQtyGtZero()) {
            request.setTemp(1);
        }

        PageHelper.startPage(request.getPageNum(), request.getPageSize());
        List<PreOrderAccountDetailDTO> list = preOrderAccountDetailMapper.listPreOrderApplyDetail(request);
        if (org.apache.commons.collections4.CollectionUtils.isNotEmpty(list)) {
            Map<String, OrganizationVO> nameMap = new HashMap<>();
            for (PreOrderAccountDetailDTO item : list) {
                if (StringUtils.isNotBlank(item.getDeptNo())) {
                    ResultVo<String> deptNoByHRSalesDeptNo = opsCommonService.getDeptNoByHRSalesDeptNo(item.getDeptNo());
                    if(deptNoByHRSalesDeptNo.isSuccess()) {
                        item.setDeptNo(deptNoByHRSalesDeptNo.getData());
                    }
                    OrganizationVO organizationVO = nameMap.getOrDefault(item.getDeptNo(), new OrganizationVO());
                    if (ObjectUtils.isEmpty(organizationVO.getDeptNo())) {
                        organizationVO = this.getDeptName(item.getDeptNo());
                        nameMap.put(item.getDeptNo(), organizationVO);
                    }
                    item.setCompanyNo(organizationVO.getCompanyNo());
                    item.setCompanyName(organizationVO.getCompanyName());
                    item.setParentNo(organizationVO.getParentNo());
                    item.setParentName(organizationVO.getParentName());
                    item.setSalesNo(organizationVO.getSalesNo());
                    item.setSalesName(organizationVO.getSalesName());
                    item.setDeptName(organizationVO.getDeptName());
                }
                item.setStatusName(PreAccountStatusEnum.getNameByCode(item.getStatus()));
                item.setInventoryTypeCode(InventoryTypeEnum.getName(item.getInventoryTypeCode()));
                if (item.getIsBin() == null) {
                    item.setBinStr("是");
                } else {
                    item.setBinStr(item.getIsBin() ? "是" : "否");
                }
                /**
                 * 审批中数
                 * 待审批: 确认决算数 + 确认延期数
                 * 反之 0
                 */
                item.setApproveCountQty(item.getApproveQty());
            }
        }
        return ResultVo.success(PageInfo.of(list));
    }

    @Override
    public void exportPreOrderApplyDetail(PreOrderAccountRequest request, HttpServletResponse response) {
        if (request == null) {
            return;
        }
        request.setPageSize(9999999);
        ResultVo<PageInfo<PreOrderAccountDetailDTO>> pageInfoResultVo = this.listPreOrderApplyDetail(request);
        if (pageInfoResultVo == null) {
            return;
        }
        if (org.apache.commons.collections4.CollectionUtils.isEmpty(pageInfoResultVo.getData().getList())) {
            return;
        }
        List<PreOrderAccountDetailExportVO> preOrderAccountExportVOS = BeanCopyUtil.copyList(pageInfoResultVo.getData().getList(), PreOrderAccountDetailExportVO.class);
        try {
            String fileName = URLEncoder.encode("opsInventoryData", "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition","attachment;utf-8;filename="+fileName+".xlxs");
            response.setHeader("Access-Control-Expose-Headres","Content-Disposition");
            InputStream inputStream = new ClassPathResource(CommonConstants.others_data_export_excel).getInputStream();

            EasyExcel.write(response.getOutputStream(), PreOrderAccountDetailExportVO.class)
                    .withTemplate(inputStream)
                    .sheet("Sheet1").doWrite(preOrderAccountExportVOS);
        } catch (IOException e) {
            response.reset();
            throw new RuntimeException("导出数据发生异常: "+e.getMessage());
        }
    }

    @Override
    public ResultVo<String> advancedHand(HandlePreOrderAccountParam param) {
        if (param == null || CollectionUtils.isEmpty(param.getIds())) {
            return ResultVo.failure("入参不可为空");
        }
        LambdaQueryWrapper<PreOrderAccountDetailDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(PreOrderAccountDetailDO::getId,param.getIds());
        List<PreOrderAccountDetailDO> accountDetailDOS = preOrderAccountDetailMapper.selectList(queryWrapper);
        if (CollectionUtils.isEmpty(accountDetailDOS)) {
            return ResultVo.failure("选中数据不存在");
        }
        /**
         * 只有暂不决算和延期中状态的可以点击，可单条或批量选择。
         * A:暂不决算状态的点击后，状态变为待决算，推送决算数=有效在库数，计算对应E金额，带上提前决算标识
         * B:延期中状态的点击后，状态变为延期待决算，推送决算数=有效在库数，计算对应E金额，需求日期=延期日期，带上提前决算标识
         */
        LambdaUpdateWrapper<PreOrderAccountDetailDO> updateWrapper = new LambdaUpdateWrapper<>();

        for (PreOrderAccountDetailDO item : accountDetailDOS) {
            updateWrapper.clear();
            updateWrapper.eq(PreOrderAccountDetailDO::getId,item.getId());
            item.setPreFlag(1);
            item.setPushQty(item.getAvaQty());
            if(item.getStatus() == PreAccountStatusEnum.zbjs.getCode()) {
                item.setStatus(PreAccountStatusEnum.djs.getCode());
            } else if(item.getStatus() == PreAccountStatusEnum.yqz.getCode())  {
                item.setStatus(PreAccountStatusEnum.yqdjs.getCode());
                item.setRequirementDate(item.getDelayDate());
                updateWrapper
                        .set(PreOrderAccountDetailDO::getSureAccountQty,0)
                        .set(PreOrderAccountDetailDO::getAccountDesc,null)
                        .set(PreOrderAccountDetailDO::getDelayQty,0)
                        .set(PreOrderAccountDetailDO::getDelayDate,null)
                        .set(PreOrderAccountDetailDO::getDelayDesc,null);
            }
            if(item.getPushQty() != null && item.getPushQty() != 0 && item.getEPrice() != null){
                updateWrapper.set(PreOrderAccountDetailDO::getEAmount,item.getEPrice().multiply(new BigDecimal(item.getPushQty())));
            }
            updateWrapper
                    .set(PreOrderAccountDetailDO::getPreFlag,item.getPreFlag())
                    .set(PreOrderAccountDetailDO::getPushQty,item.getAvaQty())
                    .set(PreOrderAccountDetailDO::getRequirementDate,item.getRequirementDate())
                    .set(PreOrderAccountDetailDO::getStatus,item.getStatus())
                    .set(PreOrderAccountDetailDO::getModifier,param.getOptUser())
                    .set(PreOrderAccountDetailDO::getBatchNo,DateUtil.getYearMonthDay(DateUtil.addDay(new Date(),1)))
                    .set(PreOrderAccountDetailDO::getModifyTime,new Date());
            preOrderAccountDetailMapper.update(null,updateWrapper);

            calPreOrderAccountQty(item.getInventoryId());

        }
        return ResultVo.success("提前决算完毕");
    }

    /**
     * 决算明细表，状态为延期中的，当天日期>=延期日期，在原id数据上修改，生成新的批次号覆盖，
     * 状态变为“延期待决算”，推送决算数修改为有效在库数，决算日期=当天日期，需求日期=延期日期，最大延期日期计算利用“最大延期日期计算逻辑”模块
     * @return
     */
    @Override
    public ResultVo<String> autoHandleYqzPre() {
        LambdaQueryWrapper<PreOrderAccountDetailDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(PreOrderAccountDetailDO::getStatus,PreAccountStatusEnum.yqz.getCode())
                .eq(PreOrderAccountDetailDO::getPreFlag,0)
                .lt(PreOrderAccountDetailDO::getDelayDate,new Date());
        List<PreOrderAccountDetailDO> accountDetailDOList = preOrderAccountDetailMapper.selectList(queryWrapper);
        if (CollectionUtils.isEmpty(accountDetailDOList)) {
            return ResultVo.success("暂无待决算的数据");
        }
        LambdaUpdateWrapper<PreOrderAccountDetailDO> updateWrapper = new LambdaUpdateWrapper<>();
        for(PreOrderAccountDetailDO item : accountDetailDOList) {
            item.setPushQty(item.getAvaQty());
            updateWrapper.clear();
            updateWrapper
                    .eq(PreOrderAccountDetailDO::getId,item.getId())
                    .set(PreOrderAccountDetailDO::getBatchNo,DateUtil.getYearMonthDay(new Date()))
                    .set(PreOrderAccountDetailDO::getStatus,PreAccountStatusEnum.yqdjs.getCode())
                    .set(PreOrderAccountDetailDO::getPushQty,item.getAvaQty())
                    .set(PreOrderAccountDetailDO::getPreFlag,1)
                    .set(PreOrderAccountDetailDO::getSureAccountQty,0)
                    .set(PreOrderAccountDetailDO::getAccountDesc,null)
                    .set(PreOrderAccountDetailDO::getDelayQty,0)
                    .set(PreOrderAccountDetailDO::getDelayDate,null)
                    .set(PreOrderAccountDetailDO::getCountDate,new Date())
                    .set(PreOrderAccountDetailDO::getRequirementDate,item.getDelayDate());
            if(item.getPushQty() != null && item.getPushQty() != 0 && item.getEPrice() != null){
                updateWrapper.set(PreOrderAccountDetailDO::getEAmount,item.getEPrice().multiply(new BigDecimal(item.getPushQty())));
            }

            /**
             * 设置最大延期日期
             */
            LambdaQueryWrapper<PreOrderAccountDO> accountDOLambdaQueryWrapper = new LambdaQueryWrapper<>();
            accountDOLambdaQueryWrapper.eq(PreOrderAccountDO::getInventoryId,item.getInventoryId());
            List<PreOrderAccountDO> preOrderAccountDOS = preOrderAccountMapper.selectList(accountDOLambdaQueryWrapper);
            if (CollectionUtils.isEmpty(preOrderAccountDOS)) {
                continue;
            }
            OpsInventoryLogVO opsInventoryLogVO = new OpsInventoryLogVO();
            opsInventoryLogVO.setCreTime(item.getRoDate());
            InventoryVO inventoryVO = new InventoryVO();
            inventoryVO.setInventoryTypeCode(preOrderAccountDOS.get(0).getInventoryTypeCode());
            item.setRequirementDate(item.getDelayDate());
            setMaxDelayDate(item,opsInventoryLogVO,inventoryVO);
            updateWrapper.set(PreOrderAccountDetailDO::getMaxDelayDate,item.getMaxDelayDate())
                         .set(PreOrderAccountDetailDO::getModifyTime,new Date())
                         .set(PreOrderAccountDetailDO::getModifier,"job");
            preOrderAccountDetailMapper.update(null,updateWrapper);
        }

        return ResultVo.success("处理成功");
    }

    private OrganizationVO getDeptName(String code) {
        OrganizationVO organizationVO = opsCommonService.getHrOrganMasterInfoByCode(code).getData();
        return organizationVO;
    }

    public void conventInventoryLogVO(List<OpsInventoryLogVO> opsInventoryLogByInventoryId, InventoryVO inventoryVO) {
        /**
         * 得到最终处理过的入库信息
         */
        RecordResultVO invenrotyLog1 = getInvenrotyLog(opsInventoryLogByInventoryId, inventoryVO.getQuantity() - inventoryVO.getPrepareQuantity());
        List<OpsInventoryLogVO> invenrotyLog = invenrotyLog1.getLogVOList();
        if (CollectionUtils.isEmpty(invenrotyLog)) {
            return;
        }

        LambdaQueryWrapper<PreOrderAccountDetailDO> queryWrapper = new LambdaQueryWrapper<>();
        LambdaUpdateWrapper<PreOrderAccountDetailDO> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        int itemNo = 0;
        // preorder_account_detail 是否存在此inventory_id
        queryWrapper.clear();
        queryWrapper
                .eq(PreOrderAccountDetailDO::getInventoryId,invenrotyLog.get(0).getInventoryId())
                .eq(PreOrderAccountDetailDO::getDelFlag,0);
        List<PreOrderAccountDetailDO> accountDetailDOS = preOrderAccountDetailMapper.selectList(queryWrapper);
        for (OpsInventoryLogVO item : invenrotyLog) {
            try {
                itemNo++;
                // 不存在 => 查找订单相关信息,包含申请号,申请人需求日期等
                if (CollectionUtils.isEmpty(accountDetailDOS)) {
                    addPreAccountDetail(item,inventoryVO,itemNo);
                } else {
                    // 存在 =>  拿到反凑的inventory_log_id 看preorder_account_detail表是否存在有效数据
                    queryWrapper.clear();
                    queryWrapper
                            .eq(PreOrderAccountDetailDO::getInventoryId,item.getInventoryId())
                            .like(PreOrderAccountDetailDO::getInventotyLogId,item.getMerInventoryLogId())
                            .eq(PreOrderAccountDetailDO::getDelFlag,0);
                    List<PreOrderAccountDetailDO> accountDetailDOList = preOrderAccountDetailMapper.selectList(queryWrapper);
                    // 不存在 => 查找订单相关信息,包含申请号,申请人需求日期等
                    if (CollectionUtils.isEmpty(accountDetailDOList)) {
                        addPreAccountDetail(item,inventoryVO,itemNo);
                    } else {
                        String charger = "";

                        /**
                         * 获取顾客在库担当
                         * 顾客在库仓库担当、营业所寻找方法：顾客在库仓库找到对应的客户代码，根据客户代码找到对应的客户担当和营业所
                         */
                        if (StringUtils.isNotBlank(inventoryVO.getInventoryTypeCode()) && inventoryVO.getInventoryTypeCode().startsWith("GK")) {
                            CustomerVO customerVO = opsCommonService.getCustomerByCustomerNo(inventoryVO.getCustomerNo());
                            if (customerVO != null && StringUtils.isNotBlank(customerVO.getPSNSMCID())) {
                                charger = customerVO.getPSNSMCID();
                            }
                        }

                        /**
                         * 战略在库仓库担当、营业所寻找方法：
                         * 战略在库（行业） ZL-HY 和战略在库（集团）ZL-JT 取门户提供客户群表的 deptCode 担当取得是 customer_take_id
                         */
                        if (StringUtils.isNotBlank(inventoryVO.getInventoryTypeCode()) &&
                                (inventoryVO.getInventoryTypeCode().equals("ZL-HY") || inventoryVO.getInventoryTypeCode().equals("ZL-JT")))
                        {
                            if (StringUtils.isNotBlank(inventoryVO.getGroupCustomerNo())) {
                                com.smc.smccloud.model.SalesCustomerClusterDO customerClusterInfo = opsCommonService.getCustomerClusterInfo(inventoryVO.getGroupCustomerNo());
                                if (customerClusterInfo != null) {
                                    charger = customerClusterInfo.getCustomerTakeId();
                                }
                            }
                        }

                        /**
                         * 1. 状态待清算,待审批,延期中,待处理 =>  更新有效在库数为现有效在库数  担当
                         * 2. 状态延期待决算,待决算 => 更新有效在库数和推送决算数为现有效在库数 担当
                         */
                        PreOrderAccountDetailDO preOrderAccountDetailDO = accountDetailDOList.get(0);
                        if (preOrderAccountDetailDO.getStatus() == PreAccountStatusEnum.dqs.getCode()
                                || preOrderAccountDetailDO.getStatus() == PreAccountStatusEnum.dsp.getCode()
                                || preOrderAccountDetailDO.getStatus() == PreAccountStatusEnum.yqz.getCode()
                                || preOrderAccountDetailDO.getStatus() == PreAccountStatusEnum.dcl.getCode())
                        {
                            lambdaUpdateWrapper.clear();
                            lambdaUpdateWrapper.eq(PreOrderAccountDetailDO::getId,preOrderAccountDetailDO.getId())
                                    .set(PreOrderAccountDetailDO::getAvaQty,item.getAvaQty())
                                    .set(StringUtils.isNotBlank(charger),PreOrderAccountDetailDO::getCharger,charger)
                                    .set(PreOrderAccountDetailDO::getModifyTime,new Date());
                            // 1. 状态待清算,待审批,延期中,待处理 =>  更新有效在库数为现有效在库数
                            preOrderAccountDetailMapper.update(null,lambdaUpdateWrapper);
                        }
                        if (preOrderAccountDetailDO.getStatus() == PreAccountStatusEnum.yqdjs.getCode() ||
                                 (preOrderAccountDetailDO.getStatus() == PreAccountStatusEnum.djs.getCode() && preOrderAccountDetailDO.getPreFlag() == 1 ))
                        {
                            lambdaUpdateWrapper.clear();
                            lambdaUpdateWrapper.eq(PreOrderAccountDetailDO::getId,preOrderAccountDetailDO.getId())
                                    .set(PreOrderAccountDetailDO::getAvaQty,item.getAvaQty())
                                    .set(PreOrderAccountDetailDO::getPushQty,item.getAvaQty())
                                    .set(StringUtils.isNotBlank(charger),PreOrderAccountDetailDO::getCharger,charger)
                                    .set(PreOrderAccountDetailDO::getModifyTime,new Date());
                            if(preOrderAccountDetailDO.getEPrice() != null) {
                                if (item.getAvaQty() == null) {
                                    item.setAvaQty(0);
                                }
                                lambdaUpdateWrapper.set(PreOrderAccountDetailDO::getEAmount,preOrderAccountDetailDO.getEPrice().multiply(new BigDecimal(item.getAvaQty())));
                            }
                            preOrderAccountDetailMapper.update(null,lambdaUpdateWrapper);
                        }
                    }
                }
            } catch (Exception e) {
                log.error("数据写入决算异常: {} ", JSONUtil.toJsonStr(item));
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }

        // 写入决算总表
        addPreAccount(invenrotyLog.get(0),inventoryVO);
    }

    public void addPreAccount(OpsInventoryLogVO opsInventoryLogVO,InventoryVO inventoryData) {

        // 查询决算明细
        LambdaQueryWrapper<PreOrderAccountDetailDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(PreOrderAccountDetailDO::getInventoryId,inventoryData.getId())
                .eq(PreOrderAccountDetailDO::getDelFlag,0);
        List<PreOrderAccountDetailDO> preOrderAccountDetailDOS = preOrderAccountDetailMapper.selectList(queryWrapper);
        // 查询决算主表
        LambdaQueryWrapper<PreOrderAccountDO> accountQueryWrapper = new LambdaQueryWrapper<>();
        accountQueryWrapper.eq(PreOrderAccountDO::getInventoryId,inventoryData.getId());
        List<PreOrderAccountDO> preOrderAccountDOS = preOrderAccountMapper.selectList(accountQueryWrapper);

        if (CollectionUtils.isEmpty(preOrderAccountDetailDOS) && CollectionUtils.isNotEmpty(preOrderAccountDOS)) {
            // preOrderAccountMapper.deleteById(preOrderAccountDOS.get(0).getId());
            LambdaQueryWrapper<PreOrderAccountDO> queryWrapper1 = new LambdaQueryWrapper<>();
            queryWrapper1.eq(PreOrderAccountDO::getInventoryId,preOrderAccountDOS.get(0).getInventoryId());
            preOrderAccountMapper.delete(queryWrapper1);
            return;
        }

        int sumRoQty = preOrderAccountDetailDOS.stream()
                .mapToInt(detail -> detail.getRoQty() == null ? 0 : detail.getRoQty())
                .sum();
        int sumPushQty = preOrderAccountDetailDOS.stream()
                .mapToInt(detail -> detail.getPushQty() == null ? 0 : detail.getPushQty())
                .sum();

        int sumAvaQty = preOrderAccountDetailDOS.stream()
                .mapToInt(detail -> detail.getAvaQty() == null ? 0 : detail.getAvaQty())
                .sum();

        BigDecimal sumEmount = preOrderAccountDetailDOS.stream()
                .filter(detail -> detail.getStatus() != PreAccountStatusEnum.zbjs.getCode())
                .map(detail -> Optional.ofNullable(detail.getEAmount()).orElse(BigDecimal.ZERO))
                .reduce(BigDecimal.ZERO, BigDecimal::add);



        List<ModelExpFreqVO> modelExpFreqVOList = new ArrayList<>();
        if (StringUtils.isNotBlank(inventoryData.getInventoryTypeCode())) {
            if (inventoryData.getInventoryTypeCode().startsWith("GK")) {
                if(StringUtils.isNotBlank(inventoryData.getCustomerNo())) {
                    modelExpFreqVOList = preOrderAccountCommonMapper.getModelExpFreqForAvgQty12(inventoryData.getModelNo(), inventoryData.getCustomerNo());
                }
            } else if (inventoryData.getInventoryTypeCode().startsWith("ZL")) {
                if(StringUtils.isNotBlank(inventoryData.getWarehouseCode())) {
                    modelExpFreqVOList = preOrderAccountCommonMapper.getModelExpFreqForAvgQty12(inventoryData.getModelNo(), inventoryData.getWarehouseCode());
                }
            }
        }
        PreOrderAccountDO  preOrderAccountDO = new PreOrderAccountDO();
        preOrderAccountDO.setInventoryId(opsInventoryLogVO.getInventoryId());
        // preOrderAccountDO.setStatus(PreAccountStatusEnum.yjs.getCode());
        preOrderAccountDO.setModelNo(opsInventoryLogVO.getModelno());
        preOrderAccountDO.setWarehouseCode(inventoryData.getWarehouseCode());
        preOrderAccountDO.setInventoryPropertyId(inventoryData.getPropertyId());
        preOrderAccountDO.setInventoryTypeCode(inventoryData.getInventoryTypeCode());
        preOrderAccountDO.setCustomerNo(inventoryData.getCustomerNo());
        preOrderAccountDO.setPpl(inventoryData.getPpl());
        preOrderAccountDO.setProjectCode(inventoryData.getProjectCode());
        preOrderAccountDO.setGroupCustomerNo(inventoryData.getGroupCustomerNo());

        if (CollectionUtils.isNotEmpty(modelExpFreqVOList)) {
            preOrderAccountDO.setFrequency12(modelExpFreqVOList.get(0).getMonthsOf12());
            if (modelExpFreqVOList.get(0).getAvgQtyOf12() != null) {
                preOrderAccountDO.setAverageof12(modelExpFreqVOList.get(0).getAvgQtyOf12().intValue());
            }
        }

        if (preOrderAccountDO.getAverageof12() == null) {
            preOrderAccountDO.setAverageof12(0);
        }

        if (preOrderAccountDO.getFrequency12() == null) {
            preOrderAccountDO.setFrequency12(0);
        }

        preOrderAccountDO.setIsBin(binListModelNos.contains(opsInventoryLogVO.getModelno()));
        if(StringUtils.isNotBlank(inventoryData.getInventoryTypeCode()) && inventoryData.getInventoryTypeCode().startsWith("GK")) {
            CustomerVO customerVO = opsCommonService.getCustomerByCustomerNo(inventoryData.getCustomerNo());
            if (customerVO != null){
                preOrderAccountDO.setDeptNo(customerVO.getHRUnitId());
                preOrderAccountDO.setCharger(customerVO.getPSNSMCID());
            }
        } else if (StringUtils.isNotBlank(inventoryData.getInventoryTypeCode()) &&
                (inventoryData.getInventoryTypeCode().equals("ZL-HY") || inventoryData.getInventoryTypeCode().equals("ZL-JT")))
        {
            if (StringUtils.isNotBlank(inventoryData.getGroupCustomerNo())) {
                com.smc.smccloud.model.SalesCustomerClusterDO customerClusterInfo = opsCommonService.getCustomerClusterInfo(inventoryData.getGroupCustomerNo());
                if (customerClusterInfo != null) {
                    preOrderAccountDO.setDeptNo(customerClusterInfo.getDeptCode());
                    preOrderAccountDO.setCharger(customerClusterInfo.getCustomerTakeId());
                }
            }
        }
        preOrderAccountDO.setCreateTime(new Date());
        preOrderAccountDO.setModifyTime(new Date());
        preOrderAccountDO.setCreator(CommonConstants.COMMON_USER_OPS_JOB);

        preOrderAccountDO.setRoQty(sumRoQty);
        preOrderAccountDO.setAvaQty(sumAvaQty);
        preOrderAccountDO.setPushQty(sumPushQty);
        preOrderAccountDO.setEAmount(sumEmount);

        preOrderAccountDO.setRetentionMonth(0);
        if(preOrderAccountDO.getAvaQty() != 0 && preOrderAccountDO.getAverageof12() != 0) {
            preOrderAccountDO.setRetentionMonth(preOrderAccountDO.getAvaQty() / preOrderAccountDO.getAverageof12());
        }

        if(preOrderAccountDO.getRetentionMonth() == null) {
            preOrderAccountDO.setRetentionMonth(0);
        }

        if(CollectionUtils.isEmpty(preOrderAccountDOS)) {
            preOrderAccountMapper.insert(preOrderAccountDO);
        } else {
            LambdaUpdateWrapper<PreOrderAccountDO> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
            lambdaUpdateWrapper
                    .eq(PreOrderAccountDO::getInventoryId,preOrderAccountDO.getInventoryId())
                    .set(PreOrderAccountDO::getRoQty,preOrderAccountDO.getRoQty())
                    .set(PreOrderAccountDO::getAvaQty,preOrderAccountDO.getAvaQty())
                    .set(PreOrderAccountDO::getPushQty,preOrderAccountDO.getPushQty())
                    .set(PreOrderAccountDO::getEAmount,preOrderAccountDO.getEAmount())
                    .set(PreOrderAccountDO::getRetentionMonth,preOrderAccountDO.getRetentionMonth())
                    .set(PreOrderAccountDO::getModifyTime,new Date());
            preOrderAccountMapper.update(null,lambdaUpdateWrapper);
        }
    }

    public void addPreAccountDetail(OpsInventoryLogVO item,InventoryVO inventoryData,int itemNo) {
        /**
         *  1. 调库单:  入库指令类型为 TKRK(调库入库)  ZHRK(组换入库)
         *          有需求日期按需求日期(stock_assembly.dlv_date)没有需求日期按入库日期+14天当做需求日期
         *   2. 补库单 入库指令类型为 CGRKBK(补库采购入库收货) CGRKGK(顾客采购入库收货)
         *           补库单取客户要求货期为需求日期(prestock_detail.dlv_date），若为空 按入库日期+14天当做需求日期
         *   3. 其他单:  入库日期+14天
         *  注: 决算日期未到达需求日期的写决算表,状态为暂不决算(不显示界面上,也不推门户)
         *  若入库日期>需求日期，则变更需求日期为入库日期+ABS(入库日期-原需求日期)
         */
        // 写入决算明细的数据
        PreOrderAccountDetailDO preOrderAccountDetailDO = new PreOrderAccountDetailDO();
        String yearMonthDay = DateUtil.getYearMonthDay(new Date());

        String charger = "";
        String deptNo = "";
        Date requirementDate = null;

        /**
         * 获取顾客在库担当
         * 顾客在库仓库担当、营业所寻找方法：顾客在库仓库找到对应的客户代码，根据客户代码找到对应的客户担当和营业所
         */
        if (StringUtils.isNotBlank(inventoryData.getInventoryTypeCode()) && inventoryData.getInventoryTypeCode().startsWith("GK")) {
            CustomerVO customerVO = opsCommonService.getCustomerByCustomerNo(inventoryData.getCustomerNo());
            if (customerVO != null && StringUtils.isNotBlank(customerVO.getPSNSMCID())) {
                charger = customerVO.getPSNSMCID();
                deptNo = customerVO.getHRUnitId();
            }
        }

        if (StringUtils.isBlank(charger) && StringUtils.isBlank(deptNo)) {
            /**
             * 战略在库仓库担当、营业所寻找方法：
             * 战略在库（行业） ZL-HY 和战略在库（集团）ZL-JT 取门户提供客户群表的 deptCode 担当取得是 customer_take_id
             */
            if (StringUtils.isNotBlank(inventoryData.getInventoryTypeCode()) &&
                    (inventoryData.getInventoryTypeCode().equals("ZL-HY") || inventoryData.getInventoryTypeCode().equals("ZL-JT")))
            {
                if (StringUtils.isNotBlank(inventoryData.getGroupCustomerNo())) {
                    com.smc.smccloud.model.SalesCustomerClusterDO customerClusterInfo = opsCommonService.getCustomerClusterInfo(inventoryData.getGroupCustomerNo());
                    if (customerClusterInfo != null) {
                        deptNo = customerClusterInfo.getDeptCode();
                        charger = customerClusterInfo.getCustomerTakeId();
                    }
                }
            }
        }


        if(StringUtils.isNotBlank(item.getOrderNo())) {
            /**
             * 88或ZK,ZL,PT开头的订单，为先行在库补库申请单，
             * 根据单号到prestock_detail中匹配order_nos,拿到对应apply_id,再根据apply_id找到到prestock_apply中的apply_person_no为担当，deptno为营业所
             * dlv_date为需求日期，为空则用入库日期+14天
             */
            if (StringUtils.isBlank(charger) && StringUtils.isBlank(deptNo)) {
                if (item.getOrderNo().startsWith("88") || item.getOrderNo().startsWith("ZK") || item.getOrderNo().startsWith("ZL") || item.getOrderNo().startsWith("PT") || item.getOrderNo().startsWith("FK")) {
                    LambdaQueryWrapper<PreStockDetailView> queryWrapper = new LambdaQueryWrapper<>();
                    queryWrapper
                            .like(PreStockDetailView::getOrderNos,item.getOrderNo()+"-"+item.getItemNo())
                            .orderByDesc(PreStockDetailView::getApplyId);
                    List<PreStockDetailView> preStockDetailViews = preStockDetailViewMapper.selectList(queryWrapper);
                    if (CollectionUtils.isNotEmpty(preStockDetailViews)) {
                        if (StringUtils.isNotBlank(preStockDetailViews.get(0).getApplyPsnNo())) {
                            charger = preStockDetailViews.get(0).getApplyPsnNo();
                        }
                        if (StringUtils.isNotBlank(preStockDetailViews.get(0).getDeptNo())) {
                            deptNo = preStockDetailViews.get(0).getDeptNo();
                        }
                    }
                }
            }

            if (item.getOrderNo().startsWith("88") || item.getOrderNo().startsWith("ZK") || item.getOrderNo().startsWith("ZL") || item.getOrderNo().startsWith("PT") || item.getOrderNo().startsWith("FK")) {
                LambdaQueryWrapper<PreStockDetailView> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper
                        .like(PreStockDetailView::getOrderNos,item.getOrderNo()+"-"+item.getItemNo())
                        .orderByDesc(PreStockDetailView::getApplyId);
                List<PreStockDetailView> preStockDetailViews = preStockDetailViewMapper.selectList(queryWrapper);
                if (CollectionUtils.isNotEmpty(preStockDetailViews)) {
                    if (preStockDetailViews.get(0).getDlvDate() != null) {
                        requirementDate = preStockDetailViews.get(0).getDlvDate();
                    } else {
                        requirementDate = DateUtil.addDay(item.getCreTime(),14);
                    }
                    LambdaQueryWrapper<PreStockDetailDO> preQueryWrapper = new LambdaQueryWrapper<>();
                    preQueryWrapper.eq(PreStockDetailDO::getId,preStockDetailViews.get(0).getDetailId());
                    PreStockDetailDO preStockDetailDO = preStockDetailMapper.selectOne(preQueryWrapper);
                    if (preStockDetailDO != null) {
                        preOrderAccountDetailDO.setEPrice(preStockDetailDO.getEprice());
                    }
                }
            }

            if (StringUtils.isBlank(charger) && StringUtils.isBlank(deptNo)) {
                /**
                 * ST、GTD、ZH开头的订单，为调库申请单，根据单号到stock_assembly找到对应ApplyPsn为担当，deptno为营业所
                 */
                if (item.getOrderNo().startsWith("ST") || item.getOrderNo().startsWith("GTD") || item.getOrderNo().startsWith("ZH")) {
                    LambdaQueryWrapper<StockAssemblyDO> queryWrapper = new LambdaQueryWrapper<>();
                    queryWrapper
                            .eq(StockAssemblyDO::getApplyNo,item.getOrderNo())
                            .orderByDesc(StockAssemblyDO::getId);
                    List<StockAssemblyDO> stockAssemblyDOS = stockAssemblyMapper.selectList(queryWrapper);
                    if (CollectionUtils.isNotEmpty(stockAssemblyDOS)) {
                        if (StringUtils.isNotBlank(stockAssemblyDOS.get(0).getApplyPsn())) {
                            charger = stockAssemblyDOS.get(0).getApplyPsn();
                        }
                        if (StringUtils.isNotBlank(stockAssemblyDOS.get(0).getDeptNo())) {
                            deptNo = stockAssemblyDOS.get(0).getDeptNo();
                        }
                    }
                }
            }
            if (item.getOrderNo().startsWith("ST") || item.getOrderNo().startsWith("GTD") || item.getOrderNo().startsWith("ZH")) {
                LambdaQueryWrapper<StockAssemblyDO> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper
                        .eq(StockAssemblyDO::getApplyNo,item.getOrderNo())
                        .orderByDesc(StockAssemblyDO::getId);
                List<StockAssemblyDO> stockAssemblyDOS = stockAssemblyMapper.selectList(queryWrapper);
                if (CollectionUtils.isNotEmpty(stockAssemblyDOS)) {
                    if (stockAssemblyDOS.get(0).getDlvDate() != null) {
                        requirementDate = stockAssemblyDOS.get(0).getDlvDate();
                    } else {
                        requirementDate = DateUtil.addDay(item.getCreTime(),14);
                    }
                }
            }

            if (StringUtils.isBlank(charger) && StringUtils.isBlank(deptNo)) {
                /**
                 * voucher_type为THRK的，用order_no和item_no匹配rcvdetail表的rorder_no和rorder_item，拿到rorder_fno，
                 * 用rorder_fno匹配return_order表中的order_no，拿到apply_no为申请号，item_no为申请项号，applicant为担当，deptno为营业所
                 */
                if ("THRK".equals(item.getVoucherType())) {
                    RcvDetailDO rcvDetailDO = preOrderAccountCommonMapper.getRcvDetailByOrderNoAndItemNo(item.getOrderNo(), item.getItemNo());
                    if (rcvDetailDO != null) {
                        LambdaQueryWrapper<ReturnOrderDO> queryWrapper1 = new LambdaQueryWrapper<>();
                        queryWrapper1
                                .eq(ReturnOrderDO::getOrderNo,rcvDetailDO.getRorderFno())
                                .orderByDesc(ReturnOrderDO::getId);
                        List<ReturnOrderDO> returnOrderDOS = returnOrderMapper.selectList(queryWrapper1);
                        if (CollectionUtils.isNotEmpty(returnOrderDOS)) {
                            if (StringUtils.isNotBlank(returnOrderDOS.get(0).getApplicant())) {
                                charger = returnOrderDOS.get(0).getApplicant();
                            }
                            if (StringUtils.isNotBlank(returnOrderDOS.get(0).getDeptNo())) {
                                deptNo = returnOrderDOS.get(0).getDeptNo();
                            }
                        }
                    }
                }
            }
            if ("THRK".equals(item.getVoucherType())) {
                RcvDetailDO rcvDetailDO = preOrderAccountCommonMapper.getRcvDetailByOrderNoAndItemNo(item.getOrderNo(), item.getItemNo());
                if (rcvDetailDO != null) {
                    LambdaQueryWrapper<ReturnOrderDO> queryWrapper1 = new LambdaQueryWrapper<>();
                    queryWrapper1
                            .eq(ReturnOrderDO::getOrderNo,rcvDetailDO.getRorderFno())
                            .orderByDesc(ReturnOrderDO::getId);
                    List<ReturnOrderDO> returnOrderDOS = returnOrderMapper.selectList(queryWrapper1);
                    if (CollectionUtils.isNotEmpty(returnOrderDOS)) {
                        requirementDate = DateUtil.addDay(item.getCreTime(),14);
                    }
                }
            }
            if (StringUtils.isBlank(charger) && StringUtils.isBlank(deptNo)) {
                /**
                 * voucher_type为TZRK的，担当默认为A30500，营业所默认为在库企划课239810，不推送门户，决算状态为待处理
                 */
                if ("TZRK".equals(item.getVoucherType())) {
                    charger = "A30500";
                    deptNo = "239810";
                }
            }
            if ("TZRK".equals(item.getVoucherType())) {
                requirementDate = DateUtil.addDay(item.getCreTime(),14);
            }
        }

        if (requirementDate == null && item.getCreTime() != null) {
            requirementDate = DateUtil.addDay(item.getCreTime(),14);
        }

        item.setRequirementDate(requirementDate);

        if (item.getCreTime().after(item.getRequirementDate())) {
            item.setRequirementDate(DateUtil.addDay(item.getCreTime(), Math.toIntExact(DateUtil.getDiffDay(item.getCreTime(), item.getRequirementDate()))));
        }

        preOrderAccountDetailDO.setInventoryId(item.getInventoryId());
        preOrderAccountDetailDO.setOrderNo(item.getOrderNo()+"-"+item.getItemNo());
        preOrderAccountDetailDO.setRoDate(item.getCreTime());
        preOrderAccountDetailDO.setModelNo(item.getModelno());
        preOrderAccountDetailDO.setRoQty(item.getQuantity());
        preOrderAccountDetailDO.setAvaQty(item.getAvaQty());
        preOrderAccountDetailDO.setRequirementDate(item.getRequirementDate());
        preOrderAccountDetailDO.setCountDate(new Date());
        preOrderAccountDetailDO.setPushQty(item.getAvaQty());
        if(item.getRequirementDate().after(new Date())) {
            preOrderAccountDetailDO.setStatus(PreAccountStatusEnum.zbjs.getCode());
            preOrderAccountDetailDO.setPushQty(0);
        } else {
            preOrderAccountDetailDO.setStatus(PreAccountStatusEnum.djs.getCode());
        }
         /*
          申请号&项号设置
         （1）88或ZK,ZL,PT开头的订单，为先行在库补库申请单，根据单号到prestock_detail中匹配order_nos记录下item_no为项号,拿到对应apply_id,再根据apply_id找到到prestock_apply中的apply_no为申请号
         （2）ST、GTD、ZH开头的订单，为调库申请单，根据单号到trans_order中匹配trans_no和item_no,from_no为申请号，item_no为项号；当from_no是plan开头时，拿着from_no到stock_transfer_plan中拿到associate_no为申请号，associate_no_item为项号
         （3）自己补充的YJS订单，无申请号
         （4）其他订单没有申请号
         */
        getApplyNoByOrderType(preOrderAccountDetailDO, item);
        // 延期日期计算
        setMaxDelayDate(preOrderAccountDetailDO, item,inventoryData);

        // 调库单和其他单 按照入库数量+型号获取E价 补库单如果有e价 直接取 , 如果没 按照入库数量+型号获取E价
        if (preOrderAccountDetailDO.getEPrice() == null || preOrderAccountDetailDO.getEPrice().compareTo(BigDecimal.ZERO) == 0 ) {
            ProductPriceVO epriceByModelNoAndMinQuantity = preOrderAccountCommonMapper.getEpriceByModelNoAndMinQuantity(preOrderAccountDetailDO.getModelNo(), preOrderAccountDetailDO.getRoQty());
            if (epriceByModelNoAndMinQuantity != null) {
                preOrderAccountDetailDO.setEPrice(epriceByModelNoAndMinQuantity.getEprice());
            }
        }
        if (preOrderAccountDetailDO.getEPrice() != null && preOrderAccountDetailDO.getEPrice().compareTo(BigDecimal.ZERO) != 0 ) {
            preOrderAccountDetailDO.setEAmount(new BigDecimal(preOrderAccountDetailDO.getPushQty()).multiply(preOrderAccountDetailDO.getEPrice()));
        }
        if(preOrderAccountDetailDO.getEAmount() == null) {
            preOrderAccountDetailDO.setEAmount(BigDecimal.ZERO);
        }
        // 是否BIN
        preOrderAccountDetailDO.setIsBin(binListModelNos.contains(preOrderAccountDetailDO.getModelNo()));

        preOrderAccountDetailDO.setCreator(CommonConstants.COMMON_USER_OPS_JOB);
        preOrderAccountDetailDO.setCreateTime(new Date());
        preOrderAccountDetailDO.setModifyTime(new Date());
        preOrderAccountDetailDO.setInventotyLogId(item.getMerInventoryLogId());
        preOrderAccountDetailDO.setInventoryIdItem(itemNo);
        preOrderAccountDetailDO.setBatchNo(yearMonthDay);

        if (StringUtils.isBlank(charger) && StringUtils.isBlank(deptNo)) {
            charger = "A30500";
            deptNo = "239810";
        }

        if (Objects.equals(charger, "A30500") && Objects.equals(deptNo, "239810")) {
            preOrderAccountDetailDO.setStatus(PreAccountStatusEnum.dcl.getCode());
            preOrderAccountDetailDO.setPushQty(0);
        }

        preOrderAccountDetailDO.setCharger(charger);
        ResultVo<String> deptNoByHRSalesDeptNo = opsCommonService.getDeptNoByHRSalesDeptNo(deptNo);
        if(deptNoByHRSalesDeptNo.isSuccess()) {
            preOrderAccountDetailDO.setDeptNo(deptNoByHRSalesDeptNo.getData());
        } else {
            preOrderAccountDetailDO.setDeptNo(deptNo);
        }
        preOrderAccountDetailMapper.insert(preOrderAccountDetailDO);

    }

    public PreorderAccountTransOrderDto conventerTransOrderVO(PreOrderAccountDetailDO preOrderAccountDetailDO, PreOrderAccountDO accountDO, String transNo, int itemNo, int qty) {

        PreorderAccountTransOrderDto preorderAccountTransOrderDto = new PreorderAccountTransOrderDto();
        preorderAccountTransOrderDto.setTransNo(transNo);
        preorderAccountTransOrderDto.setItemNo(itemNo);
        preorderAccountTransOrderDto.setModelNo(preOrderAccountDetailDO.getModelNo());
        preorderAccountTransOrderDto.setQuantity(qty);
        preorderAccountTransOrderDto.setStatus(0);
        preorderAccountTransOrderDto.setFromNo(preOrderAccountDetailDO.getAccountApplyNo());
        preorderAccountTransOrderDto.setFromType(5);
        preorderAccountTransOrderDto.setFromInventoryPropertyId(accountDO.getInventoryPropertyId());
        preorderAccountTransOrderDto.setFromWarehouseCode(accountDO.getWarehouseCode());
        preorderAccountTransOrderDto.setFromInventoryTypeCode(accountDO.getInventoryTypeCode());
        preorderAccountTransOrderDto.setFromPpl(accountDO.getPpl());
        preorderAccountTransOrderDto.setFromProjectCode(accountDO.getProjectCode());
        preorderAccountTransOrderDto.setFromGroupCustomerNo(accountDO.getGroupCustomerNo());
        preorderAccountTransOrderDto.setFromCustomerNo(accountDO.getCustomerNo());
        preorderAccountTransOrderDto.setFromInventoryId(preOrderAccountDetailDO.getInventoryId());
        preorderAccountTransOrderDto.setToInventoryPropertyId(1L);
        preorderAccountTransOrderDto.setToWarehouseCode(accountDO.getWarehouseCode());
        preorderAccountTransOrderDto.setToInventoryTypeCode(InventoryTypeEnum.TY.getCode());

        return preorderAccountTransOrderDto;
    }


    /**
     *  (1）若入库日期>需求日期 ，则最大延期日期 = 入库日期 + 最大延期天数
     * （2）若入库日期<=需求日期，则最大延期日期 = 需求日期 + 最大延期天数
     */
    public void setMaxDelayDate(PreOrderAccountDetailDO detailDO,OpsInventoryLogVO inventoryLogVO,InventoryVO inventoryData) {

        PreOrderAccountConfigDO preOrderAccountConfig = new PreOrderAccountConfigDO();

        preOrderAccountConfig.setInventoryTypeCode(inventoryData.getInventoryTypeCode());
        /**
         * 1.顾客通用：
         * （1）根据库存类型GK-TY+客户代码匹配preorder_accpunt_config看是否允许延期，若允许则取delay_max_day；若不允许则默认为0。
         * （2）若匹配不到，则取库存类型为GK-TY的优先级为1的数据看是否允许延期，若允许则取delay_max_day；若不允许则默认为0。
         */
        if (inventoryData.getInventoryTypeCode().equalsIgnoreCase("GK-TY")) {

            preOrderAccountConfig.setCustomerNo(inventoryData.getCustomerNo());
            setMaxDelayDateByConfig(preOrderAccountConfig, detailDO, inventoryData,inventoryLogVO);
        }
        /**
         * 2.顾客PPL：
         * （1）根据库存类型GK-PPL+客户代码+PPL匹配preorder_accpunt_config看是否允许延期，若允许则取delay_max_day；若不允许则默认为0。
         * （2）若匹配不到，则取库存类型为GK-PPL的优先级为1的数据看是否允许延期，若允许则取delay_max_day；若不允许则默认为0
         */
        if (inventoryData.getInventoryTypeCode().equalsIgnoreCase("GK-PPL")) {
            preOrderAccountConfig.setCustomerNo(inventoryData.getCustomerNo());
            preOrderAccountConfig.setPpl(inventoryData.getPpl());
            setMaxDelayDateByConfig(preOrderAccountConfig, detailDO, inventoryData,inventoryLogVO);
        }
        /**
         * 3.顾客PJ：
         * （1）根据库存类型GK-PJ+客户代码+PJ匹配preorder_accpunt_config看是否允许延期，若允许则取delay_max_day；若不允许则默认为0。
         * （2）若匹配不到，则取库存类型为GK-PPL的优先级为1的数据看是否允许延期，若允许则取delay_max_day；若不允许则默认为0。
         */
        if (inventoryData.getInventoryTypeCode().equalsIgnoreCase("GK-PJ")) {
            preOrderAccountConfig.setCustomerNo(inventoryData.getCustomerNo());
            preOrderAccountConfig.setProjectCode(inventoryData.getProjectCode());
            setMaxDelayDateByConfig(preOrderAccountConfig, detailDO, inventoryData,inventoryLogVO);
        }
        /**
         * 4.战略在库（产品）
         * （1）根据库存类型ZL-CP匹配preorder_accpunt_config看是否允许延期，若允许则取delay_max_day；若不允许则默认为0。
         * （2）若匹配不到，则取库存类型为ZL-CP的优先级为1的数据看是否允许延期，若允许则取delay_max_day；若不允许则默认为0。
         */
        if (inventoryData.getInventoryTypeCode().equalsIgnoreCase("ZL-CP")) {
            setMaxDelayDateByConfig(preOrderAccountConfig, detailDO, inventoryData,inventoryLogVO);
        }
        /**
         * 5.战略在库（行业）
         * （1）根据库存类型ZL-HY+群代码匹配preorder_accpunt_config看是否允许延期，若允许则取delay_max_day；若不允许则默认为0。
         * （2）若匹配不到，则取库存类型为ZL-HY的优先级为1的数据看是否允许延期，若允许则取delay_max_day；若不允许则默认为0。
         */
        if (inventoryData.getInventoryTypeCode().equalsIgnoreCase("ZL-HY")) {
            preOrderAccountConfig.setGroupCustomerNo(inventoryData.getGroupCustomerNo());
            setMaxDelayDateByConfig(preOrderAccountConfig, detailDO, inventoryData,inventoryLogVO);
        }
        /**
         * 6. 战略在库（PJ）
         * （1）根据库存类型ZL-PJ+PJ匹配preorder_accpunt_config看是否允许延期，若允许则取delay_max_day；若不允许则默认为0。
         * （2）若匹配不到，则取库存类型为ZL-PJ的优先级为1的数据看是否允许延期，若允许则取delay_max_day；若不允许则默认为0。
         */
        if (inventoryData.getInventoryTypeCode().equalsIgnoreCase("ZL-PJ")) {
            preOrderAccountConfig.setProjectCode(inventoryData.getProjectCode());
            setMaxDelayDateByConfig(preOrderAccountConfig, detailDO, inventoryData,inventoryLogVO);
        }
        /**
         * 7. 战略在库（集团）
         * （1）根据库存类型ZL-JT+群代码匹配preorder_accpunt_config看是否允许延期，若允许则取delay_max_day；若不允许则默认为0。
         * （2）若匹配不到，则取库存类型为ZL-JT的优先级为1的数据看是否允许延期，若允许则取delay_max_day；若不允许则默认为0。
         */
        if (inventoryData.getInventoryTypeCode().equalsIgnoreCase("ZL-JT")) {
            preOrderAccountConfig.setGroupCustomerNo(inventoryData.getGroupCustomerNo());
            setMaxDelayDateByConfig(preOrderAccountConfig, detailDO, inventoryData,inventoryLogVO);
        }

    }

    private void setMaxDelayDateByConfig(PreOrderAccountConfigDO configDO,PreOrderAccountDetailDO detailDO,InventoryVO inventoryData, OpsInventoryLogVO inventoryLogVO) {

        Date calDate = null;
        if(detailDO.getRequirementDate().after(inventoryLogVO.getCreTime())) {
            calDate =  detailDO.getRequirementDate();
        } else {
            calDate = inventoryLogVO.getCreTime();
        }

        int defaultDelayDay = 60;
        PreOrderAccountConfigDO preOrderAccountConfig = getPreOrderAccountConfig(configDO);
        if (preOrderAccountConfig == null) {
            PreOrderAccountConfigDO configDO2 = new PreOrderAccountConfigDO();
            configDO2.setInventoryTypeCode(inventoryData.getInventoryTypeCode());
            configDO2.setPriority(1);
            preOrderAccountConfig = getPreOrderAccountConfig(configDO2);
            if (preOrderAccountConfig == null) {
                detailDO.setMaxDelayDate(DateUtil.addDay(calDate, defaultDelayDay));
            } else {
                if (preOrderAccountConfig.getIsDelay()) {
                    detailDO.setMaxDelayDate(DateUtil.addDay(calDate, preOrderAccountConfig.getDelayMaxDay()));
                } else {
                    detailDO.setMaxDelayDate(calDate);
                }
            }
        } else {
            if (preOrderAccountConfig.getIsDelay()) {
                detailDO.setMaxDelayDate(DateUtil.addDay(calDate, preOrderAccountConfig.getDelayMaxDay()));
            } else {
                detailDO.setMaxDelayDate(calDate);
            }
        }
     }

    public PreOrderAccountConfigDO getPreOrderAccountConfig(PreOrderAccountConfigDO configDO) {
        LambdaQueryWrapper<PreOrderAccountConfigDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(PreOrderAccountConfigDO::getInventoryTypeCode,configDO.getInventoryTypeCode())
                .eq(StringUtils.isNotBlank(configDO.getCustomerNo()),PreOrderAccountConfigDO::getCustomerNo,configDO.getCustomerNo())
                .eq(StringUtils.isNotBlank(configDO.getGroupCustomerNo()),PreOrderAccountConfigDO::getGroupCustomerNo,configDO.getGroupCustomerNo())
                .eq(StringUtils.isNotBlank(configDO.getProjectCode()),PreOrderAccountConfigDO::getProjectCode,configDO.getProjectCode())
                .eq(PreOrderAccountConfigDO::getDelFlag,false)
                .eq(configDO.getPriority() != null,PreOrderAccountConfigDO::getPriority,configDO.getPriority())
                .orderByDesc(PreOrderAccountConfigDO::getPriority);
        List<PreOrderAccountConfigDO> preOrderAccountConfigDOS = preorderAccountConfigMapper.selectList(queryWrapper);
        if (CollectionUtils.isEmpty(preOrderAccountConfigDOS)) {
            return null;
        }
        return preOrderAccountConfigDOS.get(0);
    }


    public void getApplyNoByOrderType(PreOrderAccountDetailDO detailDO,OpsInventoryLogVO inventoryLogVO) {
        if (StringUtils.isBlank(inventoryLogVO.getOrderNo())) {
            return;
        }
        // 先行在库补库申请单
        if (inventoryLogVO.getOrderNo().startsWith("88") || inventoryLogVO.getOrderNo().startsWith("ZK") ||
                inventoryLogVO.getOrderNo().startsWith("ZL") || inventoryLogVO.getOrderNo().startsWith("PT") || inventoryLogVO.getOrderNo().startsWith("FK")) {
            LambdaQueryWrapper<PreStockDetailView> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper
                    .like(PreStockDetailView::getOrderNos,inventoryLogVO.getOrderNo()+"-"+inventoryLogVO.getItemNo())
                    .orderByDesc(PreStockDetailView::getApplyId);
            List<PreStockDetailView> preStockDetailViews = preStockDetailViewMapper.selectList(queryWrapper);
            if (CollectionUtils.isNotEmpty(preStockDetailViews)) {
                detailDO.setApplyNo(preStockDetailViews.get(0).getApplyNo());
                if(preStockDetailViews.get(0).getItemNo() != null) {
                    detailDO.setApplyItemNo(String.valueOf(preStockDetailViews.get(0).getItemNo()));
                }
            }
        } else if (inventoryLogVO.getOrderNo().startsWith("ST") || inventoryLogVO.getOrderNo().startsWith("GTD") ||
                inventoryLogVO.getOrderNo().startsWith("ZH")) {  // 调库申请单
            if (inventoryLogVO.getItemNo() == null) {
                inventoryLogVO.setItemNo(0);
            }
            List<TransOrderVO> transOrderVOList = preOrderAccountCommonMapper.getTransOrderByTransOrderNoAndItemNo(inventoryLogVO.getOrderNo(), String.valueOf(inventoryLogVO.getItemNo()));
            if (CollectionUtils.isEmpty(transOrderVOList)) {
                return;
            }
            detailDO.setApplyNo(transOrderVOList.get(0).getFromNo());
            detailDO.setApplyItemNo(String.valueOf(transOrderVOList.get(0).getItemNo()));
            if (StringUtils.isNotBlank(transOrderVOList.get(0).getFromNo()) && transOrderVOList.get(0).getFromNo().toLowerCase().startsWith("plan")) {
                List<StockTransferPlan> stockTransferPlanByFromNo = preOrderAccountCommonMapper.getStockTransferPlanByFromNo(transOrderVOList.get(0).getFromNo());
                if(CollectionUtils.isNotEmpty(stockTransferPlanByFromNo)) {
                    detailDO.setApplyNo(stockTransferPlanByFromNo.get(0).getAssociateNo());
                    detailDO.setApplyItemNo(String.valueOf(stockTransferPlanByFromNo.get(0).getAssociateNoItem()));
                }
            }
        }

        if ("THRK".equals(inventoryLogVO.getVoucherType())) {
            RcvDetailDO rcvDetailDO = preOrderAccountCommonMapper.getRcvDetailByOrderNoAndItemNo(inventoryLogVO.getOrderNo(), inventoryLogVO.getItemNo());
            if (rcvDetailDO != null) {
                LambdaQueryWrapper<ReturnOrderDO> queryWrapper1 = new LambdaQueryWrapper<>();
                queryWrapper1
                        .eq(ReturnOrderDO::getOrderNo,rcvDetailDO.getRorderFno())
                        .orderByDesc(ReturnOrderDO::getId);
                List<ReturnOrderDO> returnOrderDOS = returnOrderMapper.selectList(queryWrapper1);
                if (CollectionUtils.isNotEmpty(returnOrderDOS)) {
                    detailDO.setApplyNo(returnOrderDOS.get(0).getApplyNo());
                    detailDO.setApplyItemNo(String.valueOf(returnOrderDOS.get(0).getItemNo()));
                }
            }
        }

        if("TZRK".equals(inventoryLogVO.getVoucherType())) {
            detailDO.setApplyNo(inventoryLogVO.getOrderNo());
            detailDO.setApplyItemNo(String.valueOf(inventoryLogVO.getItemNo()));
        }

    }

    public void addPreOrderAccountWithDetail(InventoryVO item) {

        // 判断是否已经新增过
        LambdaQueryWrapper<PreOrderAccountDO> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(PreOrderAccountDO::getInventoryId,item.getId());
        List<PreOrderAccountDO> preOrderAccountDOS = preOrderAccountMapper.selectList(lambdaQueryWrapper);
        if (CollectionUtils.isNotEmpty(preOrderAccountDOS)) {
            return;
        }
        preOrderAccountCommonMapper.upPreorderAccountDetailAvgQtyYJS(item.getId(),item.getModelNo());

        PreOrderAccountDetailDO detailDO = new PreOrderAccountDetailDO();
        detailDO.setInventoryId(item.getId());
        // YJSYYMMDD+6位随机+-1
        detailDO.setOrderNo(UIDGenerator.generateUIDLen6("YJS"+ DateUtil.getYearMonthDay(new Date())));
        detailDO.setRoDate(DateUtil.stringToDate("2022-08-22"));
        detailDO.setRequirementDate(detailDO.getRoDate());
        detailDO.setCountDate(new Date());
        detailDO.setModelNo(item.getModelNo());
        detailDO.setRoQty(item.getAvaQty());
        detailDO.setAvaQty(item.getAvaQty());
        detailDO.setStatus(PreAccountStatusEnum.dcl.getCode());
        detailDO.setPushQty(0);
        ProductPriceVO epriceByModelNoAndMinQuantity = preOrderAccountCommonMapper.getEpriceByModelNoAndMinQuantity(item.getModelNo(), item.getAvaQty());
        if (epriceByModelNoAndMinQuantity != null) {
            detailDO.setEPrice(epriceByModelNoAndMinQuantity.getEprice());
        }
        if (detailDO.getEPrice() != null && detailDO.getEPrice().compareTo(BigDecimal.ZERO) != 0) {
            detailDO.setEAmount(detailDO.getEPrice().multiply(new BigDecimal(detailDO.getRoQty())));
        }

        // 是否BIN
        detailDO.setIsBin(binListModelNos.contains(item.getModelNo()));
        detailDO.setCreateTime(new Date());
        detailDO.setModifyTime(new Date());
        detailDO.setInventotyLogId(null);
        detailDO.setCreator(CommonConstants.COMMON_USER_OPS_JOB);

        PreOrderAccountDO accountDO = new PreOrderAccountDO();
        accountDO.setInventoryId(item.getId());
        accountDO.setModelNo(item.getModelNo());
        accountDO.setWarehouseCode(item.getWarehouseCode());
        accountDO.setInventoryPropertyId(item.getPropertyId());
        accountDO.setInventoryTypeCode(item.getInventoryTypeCode());
        accountDO.setCustomerNo(item.getCustomerNo());
        accountDO.setPpl(item.getPpl());
        accountDO.setProjectCode(item.getProjectCode());
        accountDO.setGroupCustomerNo(item.getGroupCustomerNo());
        accountDO.setRoQty(item.getAvaQty());
        accountDO.setAvaQty(item.getAvaQty());
        accountDO.setPushQty(0);

        List<ModelExpFreqVO> modelExpFreqForAvgQty12 = new ArrayList<>();
        if (StringUtils.isNotBlank(item.getInventoryTypeCode())) {
            if (item.getInventoryTypeCode().startsWith("GK")) {
                if(StringUtils.isNotBlank(item.getCustomerNo())) {
                    modelExpFreqForAvgQty12 = preOrderAccountCommonMapper.getModelExpFreqForAvgQty12(item.getModelNo(), item.getCustomerNo());
                }
            } else if (item.getInventoryTypeCode().startsWith("ZL")) {
                if(StringUtils.isNotBlank(item.getWarehouseCode())) {
                    modelExpFreqForAvgQty12 = preOrderAccountCommonMapper.getModelExpFreqForAvgQty12(item.getModelNo(), item.getWarehouseCode());
                }
            }
        }
        if (CollectionUtils.isNotEmpty(modelExpFreqForAvgQty12)) {
            accountDO.setFrequency12(modelExpFreqForAvgQty12.get(0).getMonthsOf12());
            if (modelExpFreqForAvgQty12.get(0).getAvgQtyOf12() != null) {
                accountDO.setAverageof12(modelExpFreqForAvgQty12.get(0).getAvgQtyOf12().intValue());
            }
        }

        if (accountDO.getAverageof12() == null) {
            accountDO.setAverageof12(0);
        }

        if(item.getAvaQty() != 0 &&  accountDO.getAverageof12() != 0) {
            accountDO.setRetentionMonth(item.getAvaQty() / accountDO.getAverageof12());
        }

        if (accountDO.getAverageof12() == null) {
            accountDO.setAverageof12(0);
        }
        if (accountDO.getFrequency12() == null) {
            accountDO.setFrequency12(0);
        }

        if(accountDO.getRetentionMonth() == null) {
            accountDO.setRetentionMonth(0);
        }

        accountDO.setEAmount(detailDO.getEAmount() == null ? BigDecimal.ZERO:detailDO.getEAmount());
        accountDO.setIsBin(detailDO.getIsBin());
        accountDO.setCreateTime(new Date());
        accountDO.setModifyTime(new Date());
        accountDO.setCreator(CommonConstants.COMMON_USER_OPS_JOB);

        accountDO.setCharger("A30500");
        detailDO.setCharger("A30500");
        accountDO.setDeptNo("239810");
        detailDO.setDeptNo("239810");

        preOrderAccountMapper.insert(accountDO);
        preOrderAccountDetailMapper.insert(detailDO);

    }

}
