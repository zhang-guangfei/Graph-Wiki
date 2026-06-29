package com.smc.ops.delivery.service.poImps.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sales.ops.dto.invoice.*;
import com.sales.ops.enums.invoice.ImpInvoiceConstants;
import com.smc.ops.delivery.mapper.*;
import com.smc.ops.delivery.model.poImps.*;
import com.smc.ops.delivery.service.poImps.InvoiceGroupPurchaseService;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.redis.RedisManager;
import com.smc.smccloud.core.utils.BeanCopyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @description: 交付子系统发票信息导入
 * @author: B91717
 * @time: 2024/8/7 10:58
 */
@Service
@Slf4j
public class InvoiceGroupPurchaseServiceImpl implements InvoiceGroupPurchaseService {

    @Resource
    private OpsPoDeliveryInvoiceLogFromSupplierMapper opsPoDeliveryInvoiceLogFromSupplierMapper;

    @Resource
    private OpsPoDeliveryInvoiceBarcodeFromSupplierMapper opsPoDeliveryInvoiceBarcodeFromSupplierMapper;

    @Resource
    private OpsPoInvoicePriceLogFromSupplierMapper opsPoInvoicePriceLogFromSupplierMapper;

    @Resource
    private OpsPoInvoicePriceDetailLogFromSupplierMapper opsPoInvoicePriceDetailLogFromSupplierMapper;

    @Resource
    private PoImpFailLogMapper poImpFailLogMapper;

    @Resource
    private RedisManager redisManager;


    /**
     * 查询集团采购中间库的发票主表数据
     *
     * @param maxId
     * @param pageSize
     * @return
     */
    @Override
    public ResultVo<List<OpsPoInvoiceDataDto>> getDeliveryInvoiceList(Long maxId, Integer pageSize) throws Exception {
        String jobName = ImpInvoiceConstants.IMP_INVOICE_SYSN;
        // 根据传入参数，查询delivery_invoice_log_from_supplier总表数据
        LambdaQueryWrapper<OpsPoDeliveryInvoiceLogFromSupplierDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .gt(OpsPoDeliveryInvoiceLogFromSupplierDO::getId, maxId)
                .eq(OpsPoDeliveryInvoiceLogFromSupplierDO::getDelFlag, 0) //未删除的
                // bug 18335,不需要限制code码
//                .in(OpsPoDeliveryInvoiceLogFromSupplierDO::getCode, ImpInvoiceCommonEnum.impInvoiceCodeList())  // 筛选 L0和P1和L的数据
                .orderByAsc(OpsPoDeliveryInvoiceLogFromSupplierDO::getId); // 根据id升序排序
        // 执行分页查询,采购pageInfo的方式,selectPage方式无效
        PageInfo<OpsPoDeliveryInvoiceLogFromSupplierDO> opsPoDeliveryInvoiceLogPage = PageHelper.startPage(1, pageSize)
                .doSelectPageInfo(() -> opsPoDeliveryInvoiceLogFromSupplierMapper.selectList(queryWrapper));
        List<OpsPoDeliveryInvoiceLogFromSupplierDO> opsPoDeliveryInvoiceLogFromSupplierDOS = opsPoDeliveryInvoiceLogPage.getList();
        // 查询上次处理失败的数据
        ResultVo<List<OpsImpinvoiceFailLogDO>> failedResult = getUnHandleFailLog(jobName);
        List<OpsImpinvoiceFailLogDO> opsImpinvoiceFailLogDOS = null;
        if (failedResult.isSuccess()) {
            opsImpinvoiceFailLogDOS = failedResult.getData();
            LambdaQueryWrapper<OpsPoDeliveryInvoiceLogFromSupplierDO> queryWrapperFaile = new LambdaQueryWrapper<>();
            queryWrapperFaile
                    .eq(OpsPoDeliveryInvoiceLogFromSupplierDO::getDelFlag, 0) //未删除的
                    .in(OpsPoDeliveryInvoiceLogFromSupplierDO::getId, opsImpinvoiceFailLogDOS.stream().map(OpsImpinvoiceFailLogDO::getSourceId).collect(Collectors.toList())); // 筛选异常ID的订单，补充到处理清单中
            List<OpsPoDeliveryInvoiceLogFromSupplierDO> lastFailedList = opsPoDeliveryInvoiceLogFromSupplierMapper.selectList(queryWrapperFaile);
            if (!CollectionUtils.isEmpty(lastFailedList)) {
                // bug18428 循环增加判断，当REDIS中该 sourrid的数据量达到10次以上时，不再进行推送
                lastFailedList = this.getUnHandleFailCounts(lastFailedList);
                opsPoDeliveryInvoiceLogFromSupplierDOS.addAll(lastFailedList);
            }
        }
        if (CollectionUtils.isEmpty(opsPoDeliveryInvoiceLogFromSupplierDOS)) {
            return ResultVo.successMsg("暂无待处理数据");
        }
        // 更新异常表的状态，需要放到采购中更新吗？
        List<OpsPoInvoiceDataDto> opsPoInvoiceDataDtos = getInvoiceData(opsPoDeliveryInvoiceLogFromSupplierDOS); // 返回所有子表的明细集合
        return ResultVo.success(opsPoInvoiceDataDtos);
    }

    /**
     * 根据主表明细，去匹配pack表、发票表、发票子表数据，返回给OPS大集合的数据
     * 根据code码进行区分，L的查barcode信息，包含P(p0\p1)的查price信息
     *
     * @param deliveryInvoiceLogDOS
     * @return
     */
    public List<OpsPoInvoiceDataDto> getInvoiceData(List<OpsPoDeliveryInvoiceLogFromSupplierDO> deliveryInvoiceLogDOS) {
        List<OpsPoInvoiceDataDto> opsPoInvoiceDataDtos = new ArrayList<>();
        OpsPoInvoiceDataDto opsPoInvoiceDataDto;
        for (OpsPoDeliveryInvoiceLogFromSupplierDO deliveryInvoiceLogDO : deliveryInvoiceLogDOS) {
            try {
                String poCodeType = deliveryInvoiceLogDO.getCode();
                opsPoInvoiceDataDto = new OpsPoInvoiceDataDto();
                opsPoInvoiceDataDto.setSourceType(deliveryInvoiceLogDO.getSourceType());
                opsPoInvoiceDataDto.setDeliveryInvoiceId(deliveryInvoiceLogDO.getId()); // 赋值总表的id,方便后期筛选使用
                // 判断poCodeType中是否包含L，去查询barcode表
                if (poCodeType.contains("L")) {
                    // 查询barcode表
                    LambdaQueryWrapper<OpsPoDeliveryInvoiceBarcodeFromSupplierDO> barcodeWrapper = new LambdaQueryWrapper<>();
                    barcodeWrapper
                            .eq(OpsPoDeliveryInvoiceBarcodeFromSupplierDO::getPid, deliveryInvoiceLogDO.getId());
                    List<OpsPoDeliveryInvoiceBarcodeFromSupplierDO> barcodeList = opsPoDeliveryInvoiceBarcodeFromSupplierMapper.selectList(barcodeWrapper);
                    if (CollectionUtils.isEmpty(barcodeList)) {
                        log.error("未找到对应的barcode子项数据，无法写入" + ",deliveryInvoiceLog表id:" + deliveryInvoiceLogDO.getId());
                        continue;
                    }
                    // 对barcodeList中的pono进行校验，存在特殊异常单据时，则不取
//                    if (barcodeList.stream().anyMatch(barcode -> !isValidPONO(barcode.getPono()))) {
//                        // 从barcodeList中移除这些订单
//                        barcodeList.removeIf(barcode -> !isValidPONO(barcode.getPono()));
//                        log.error("当前子项有异常单号异常，无法写入:" + ",deliveryInvoiceLog表id:" + deliveryInvoiceLogDO.getId());
//                    }
                    if (CollectionUtils.isEmpty(barcodeList)) {
                        log.error("当前子项有异常单号异常，无法写入:" + ",deliveryInvoiceLog表id:" + deliveryInvoiceLogDO.getId());
                        continue;
                    }
                    opsPoInvoiceDataDto.setOpsPoDeliveryInvoiceBarcodeFromSupplierDtos(BeanCopyUtil.copyList(barcodeList,
                            OpsPoDeliveryInvoiceBarcodeFromSupplierDto.class));
                }
                // 判断poCodeType中是否包含P，去查询invoice主子表
                if (poCodeType.contains("P")) {
                    LambdaQueryWrapper<OpsPoInvoicePriceLogFromSupplierDO> poInvoiceWrapper = new LambdaQueryWrapper<>();
                    poInvoiceWrapper.eq(OpsPoInvoicePriceLogFromSupplierDO::getPid, deliveryInvoiceLogDO.getId()); //根据PID检索
                    List<OpsPoInvoicePriceLogFromSupplierDO> poInvoiceList = opsPoInvoicePriceLogFromSupplierMapper.selectList(poInvoiceWrapper); // 查询价格主表
                    if (CollectionUtils.isEmpty(poInvoiceList)) {
                        log.error("发票号:" + deliveryInvoiceLogDO.getInvoiceNo() + ",id:" + deliveryInvoiceLogDO.getId() + "在表ops_po_invoice_price_log_from_supplier中数据为空"); // todo 是否考虑加一个暂不处理记录表，记录未补齐的数据,下次执行时接着执行
                        continue;
                    }
                    OpsPoInvoicePriceLogFromSupplierDO poInvoicePriceLogFromSupplierDO = poInvoiceList.get(0);
                    opsPoInvoiceDataDto.setOpsPoInvoicePriceLogFromSupplierDto(BeanCopyUtil.copy(poInvoicePriceLogFromSupplierDO, OpsPoInvoicePriceLogFromSupplierDto.class));
                    //检索价格子项
                    LambdaQueryWrapper<OpsPoInvoicePriceDetailLogFromSupplierDO> poInvoiceDetaillogWrapper = new LambdaQueryWrapper<>();
                    poInvoiceDetaillogWrapper
                            .eq(OpsPoInvoicePriceDetailLogFromSupplierDO::getPid, poInvoicePriceLogFromSupplierDO.getId());
                    List<OpsPoInvoicePriceDetailLogFromSupplierDO> poInvoicePriceDetailList = opsPoInvoicePriceDetailLogFromSupplierMapper.selectList(poInvoiceDetaillogWrapper); // 查询价格子表明细
                    if (CollectionUtils.isEmpty(poInvoicePriceDetailList)) {
                        log.error("未找到对应价格子项数据，无法写入" + ",deliveryInvoiceLog表id:" + poInvoicePriceLogFromSupplierDO.getId());
                        continue;
                    }
                    // 对barcodeList中的pono进行校验，存在特殊异常单据时，则不取
//                    if (poInvoicePriceDetailList.stream().anyMatch(barcode -> !isValidPONO(barcode.getPono()))) {
//                        // 从barcodeList中移除这些订单
//                        poInvoicePriceDetailList.removeIf(barcode -> !isValidPONO(barcode.getPono()));
//                        log.error("存在异常订单号:" + ",deliveryInvoiceLog表id:" + deliveryInvoiceLogDO.getId());
//                    }
                    if (CollectionUtils.isEmpty(poInvoicePriceDetailList)) {
                        log.error("存在异常订单号:" + ",deliveryInvoiceLog表id:" + deliveryInvoiceLogDO.getId());
                        continue;
                    }
                    opsPoInvoiceDataDto.setOpsPoInvoicePriceDetailLogFromSupplierDtos(
                            BeanCopyUtil.copyList(poInvoicePriceDetailList, OpsPoInvoicePriceDetailLogFromSupplierDto.class)
                    );

                }
                opsPoInvoiceDataDto.setOpsPoDeliveryInvoiceLogFromSupplierDto(BeanCopyUtil.copy(deliveryInvoiceLogDO, OpsPoDeliveryInvoiceLogFromSupplierDto.class)); // 复制总表数据
                opsPoInvoiceDataDtos.add(opsPoInvoiceDataDto);
            } catch (Exception e) {
                log.error("查询集团采购适配器失败，异常原因", e);
                // 是否记录到异常表中，下次接着处理
                insertHandleFailLog(ImpInvoiceConstants.INVOICE_SYSN_ERROR + deliveryInvoiceLogDO.getSourceType(), String.valueOf(deliveryInvoiceLogDO.getId()), "ops_po_delivery_invoice_log_from_supplier", "查询集团采购适配器失败,请稍后重试", deliveryInvoiceLogDO.getSourceType() + ":导入ImpInvoice表报错");
            }
        }
        return opsPoInvoiceDataDtos;
    }


    /**
     * 查询上次处理失败的数据
     * todo 这块是否需要放在po项目中查询
     *
     * @param sourceType
     * @return
     */
    public ResultVo<List<OpsImpinvoiceFailLogDO>> getUnHandleFailLog(String sourceType) {
        LambdaQueryWrapper<OpsImpinvoiceFailLogDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .like(OpsImpinvoiceFailLogDO::getSourceType, sourceType) //未删除的
                .isNotNull(OpsImpinvoiceFailLogDO::getSourceId)
                .eq(OpsImpinvoiceFailLogDO::getHandleStatus, "0");
        List<OpsImpinvoiceFailLogDO> opsImpinvoiceFailLogDOList = poImpFailLogMapper.selectList(queryWrapper);
        if (CollectionUtils.isEmpty(opsImpinvoiceFailLogDOList)) {
            return ResultVo.failure("暂无待处理的异常数据");
        }
        this.updateHandleFailLog(opsImpinvoiceFailLogDOList); // 更新上次未处理异常表的处理状态
        return ResultVo.success(opsImpinvoiceFailLogDOList);
    }

    public ResultVo<String> updateHandleFailLog(List<OpsImpinvoiceFailLogDO> list) {
        if (CollectionUtils.isEmpty(list)) {
            return ResultVo.failure("未找到对应异常数据！");
        }
        list.forEach(poImpFailLogDO -> {
            poImpFailLogDO.setHandleStatus(1);
            poImpFailLogDO.setHandleTime(new Date());
            poImpFailLogMapper.updateById(poImpFailLogDO);
        });
        return ResultVo.success("更新成功");
    }

    public ResultVo<String> insertHandleFailLog(String sourceType, String sourceId, String sourceTable, String errorDataStr, String remark) {
        OpsImpinvoiceFailLogDO opsImpinvoiceFailLogDO = new OpsImpinvoiceFailLogDO();
        opsImpinvoiceFailLogDO.setContent(errorDataStr);
        opsImpinvoiceFailLogDO.setSourceId(sourceId);
        opsImpinvoiceFailLogDO.setSourceTable(sourceTable);
        opsImpinvoiceFailLogDO.setSourceType(sourceType);
        opsImpinvoiceFailLogDO.setCreateTime(new Date());
        opsImpinvoiceFailLogDO.setCreateUser(ImpInvoiceConstants.IMP_INVOICE_SYSN);
        opsImpinvoiceFailLogDO.setRemark(remark);
        poImpFailLogMapper.insert(opsImpinvoiceFailLogDO);
        return ResultVo.success("写入成功");
    }

    /**
     * bug18428
     * 循环判断，Redis中存储的，该sourceId的重试次数是否超过10次，超过10次时，不再处理
     * @param lastFailedList
     * @return
     */
    public List<OpsPoDeliveryInvoiceLogFromSupplierDO> getUnHandleFailCounts(List<OpsPoDeliveryInvoiceLogFromSupplierDO> lastFailedList) {
        List<OpsPoDeliveryInvoiceLogFromSupplierDO> failedDealList = new ArrayList<>();
        try {
            for (OpsPoDeliveryInvoiceLogFromSupplierDO deliveryInvoiceLogDO : lastFailedList) {
                String key = ImpInvoiceConstants.REDIS_KEY_IMP_ERROR_INVOICE_COUNT + deliveryInvoiceLogDO.getSourceId();
                Boolean hasKey = redisManager.hasKey(key);
                Integer count = 0;
                if (hasKey != null && hasKey) {
                    Object o = redisManager.get(key);
                    if (o != null) {
                        // 将Object转为Integer
                        count = Integer.parseInt(o.toString());
                        if (count >= ImpInvoiceConstants.IMP_ERROR_INVOICE_ReTry_COUNT) {
                            continue; //跳过,不再执行
                        } else {
                            redisManager.set(key, String.valueOf(count + 1));
                        }
                    }
                } else {
                    // redis中不存在时，新建key值,有效期48小时
                    redisManager.set(key, String.valueOf(count + 1), 60 * 60 * 48);
                }
                failedDealList.add(deliveryInvoiceLogDO);
            }
        } catch (Exception e) {
            log.error("Redis中存储的，该sourceId的重试次数是否超过10次，超过10次时，不再处理异常数据，异常原因:", e.getMessage());
        }
        return failedDealList;
    }

//    public Boolean isValidPONO(String poNo) {
//        // 如果订单中含有特殊字符，则认为订单号无效,订单号只会由字母和数字和-组成
//        return poNo.matches("^[A-Za-z0-9-]+$");
//    }
}
