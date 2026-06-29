package com.smc.smccloud.config.threadpool;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.sales.ops.dto.assembly.InventoryRelationOrderExcel;
import com.sales.ops.enums.*;
import com.smc.smccloud.mapper.*;
import com.smc.smccloud.model.*;
import com.smc.smccloud.service.DictCommonService;
import com.smc.smccloud.service.OpsCommonService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

@Slf4j
@Service
public class ThreadPoolTaskConfig {

    @Autowired
    private InventoryRelationOrderViewMapper inventoryRelationOrderViewMapper;
    @Autowired
    private OrderStatusItemMapper orderStatusItemMapper;
    @Autowired
    private RcvmasterMapper rcvmasterMapper;
    @Autowired
    TransOrderMapper transOrderMapper;
    @Autowired
    private StockAssemblyDetailViewMapper stockAssemblyDetailViewMapper;
    @Autowired
    private OpsCommonService opsCommonService;
    @Autowired
    private DictCommonService dictCommonService;

    @Bean(name = "InventoryRelationReportTaskExecutor")
    public ThreadPoolTaskExecutor InventoryRelationReportTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5); // 核心线程数
        executor.setMaxPoolSize(20); // 最大线程数
        executor.setQueueCapacity(20); // 队列容量
        executor.setThreadNamePrefix("Inventory-Relation-Report-Task-Executor"); // 线程名前缀
        executor.initialize();
        return executor;
    }

    @Async("InventoryRelationReportTaskExecutor")
    public Future<String> handle(List<InventoryRelationOrderViewDO> list, List<InventoryRelationOrderExcel> excelList) {
        for (InventoryRelationOrderViewDO invRelation : list) {
            //插入队列
            InventoryRelationOrderExcel excelData = BeanUtil.copyProperties(invRelation, InventoryRelationOrderExcel.class);
            excelList.add(excelData);
            String doType = invRelation.getDoType();
            DoTypeEnum typeEnum = DoTypeEnum.getType(doType);

            String doSource = invRelation.getDoSource();
            boolean isAssModel = DoSourceEnum.isAssModel(doSource);
            Integer splitNo = isAssModel ? invRelation.getAssNum() : invRelation.getNum();
            excelData.setOrderFno(invRelation.getOrderId() + "-" + invRelation.getOrderItem() + "-" + splitNo);
            excelData.setSplitModelno(invRelation.getModelno());
            if (!StringUtils.isEmpty(invRelation.getInventoryTypeCode())) {
                InventoryTypeEnum invTypeEnum = InventoryTypeEnum.parse(invRelation.getInventoryTypeCode());
                String invTypeDesc = invTypeEnum == null ? invRelation.getInventoryTypeCode() : invTypeEnum.getDesc();
                excelData.setInventoryTypeCode(invTypeDesc);
            }
            if (!StringUtils.isEmpty(invRelation.getInventoryStatus())) {
                InventoryStatusEnum invStatusEnum = InventoryStatusEnum.getEnumByType(invRelation.getInventoryStatus());
                String invStatusDesc = invStatusEnum == null ? invRelation.getInventoryStatus() : invStatusEnum.getDesc();
                excelData.setInventoryStatus(invStatusDesc);
            }
            if (!StringUtils.isEmpty(invRelation.getDlvEntire())) {
                CKTYPEEnum dlvEntireEnum = CKTYPEEnum.getCode(invRelation.getDlvEntire());
                excelData.setDlvEntire(dlvEntireEnum.getDesc());
            }
            //property 上的营业所
            if (!StringUtils.isEmpty(invRelation.getDeptCode())) {
                HROrganizationDO organization = opsCommonService.getHrOrganInfoByCode(invRelation.getDeptCode()).getData();
                String deptName = organization == null ? invRelation.getDeptCode() : organization.getName();
                excelData.setDeptCode(deptName);
            }
            //do上的营业所
            if (!StringUtils.isEmpty(invRelation.getDeptNo())) {
                HROrganizationDO organization = opsCommonService.getHrOrganInfoByCode(invRelation.getDeptNo()).getData();
                String deptName = organization == null ? invRelation.getDeptNo() : organization.getName();
                excelData.setDeptNo(deptName);
                HROrganizationDO salesDepartment = opsCommonService.findSalesDepartment(invRelation.getDeptNo());
                if (salesDepartment != null) {
                    excelData.setSalesDeptNo(salesDepartment.getName());
                }
            }

            String stockType = invRelation.getInventoryStatus();
            String stockCode = invRelation.getWarehouseCode();
            String stockProp = invRelation.getInventoryTypeCode();
            if (StringUtils.isNotBlank(stockType)) {
                InventoryStatusEnum inventoryStatusEnum = InventoryStatusEnum.getEnumByType(stockType);
                if (inventoryStatusEnum != null) {
                    stockType = inventoryStatusEnum.getDesc();
                }
            }
            if (StringUtils.isNotBlank(stockProp)) {
                InventoryTypeEnum inventoryTypeEnum = InventoryTypeEnum.parse(stockProp);
                if (inventoryTypeEnum != null) {
                    stockProp = inventoryTypeEnum.getDesc();
                }
            }
            if (StringUtils.isNotBlank(stockCode)) {
                String warehouseName = opsCommonService.getWarehouseNameByCode(stockCode);
                if (warehouseName != null) {
                    stockCode = warehouseName;
                }
            }
            String stockInfo = stockType + "-" + stockCode + "-" + stockProp;
            //判断订单类型，查询订单状态和订货日期
            if (DoTypeEnum.JYCK == typeEnum || DoTypeEnum.DBCK == typeEnum) {
                Map<String, String> statusItemMap = dictCommonService.getDataCodesMap("4501").getData();
                String inventoryTable = "N".equals(invRelation.getInventoryStatus()) ? "N" : "T";

                LambdaQueryWrapper<OrderStatusItemDO> qw = new LambdaQueryWrapper<>();
                qw.eq(OrderStatusItemDO::getOrderId, invRelation.getOrderId())
                        .eq(OrderStatusItemDO::getOrderItem, invRelation.getOrderItem())
                        .eq(OrderStatusItemDO::getSplitNo, invRelation.getNum())
                        .eq(OrderStatusItemDO::getPcoItem, invRelation.getAssNum())
                ;
                if (StringUtils.isNotBlank(inventoryTable)) {
                    qw.eq(OrderStatusItemDO::getInventoryTable, inventoryTable)
                            .eq(OrderStatusItemDO::getInventoryId, invRelation.getInventoryId())
                            .le(OrderStatusItemDO::getQtyOut, invRelation.getDoUseQty());
                }
                List<OrderStatusItemDO> statusItems = orderStatusItemMapper.selectList(qw);
                if (!CollectionUtils.isEmpty(statusItems)) {
                    String statusDesc = statusItems.get(0).getStatusDesc();
                    if (statusItemMap != null) {
                        String desc = statusItemMap.get(statusDesc);
                        if (desc != null) {
                            excelData.setStatus(desc);
                        } else {
                            excelData.setStatus(statusDesc);
                        }
                    }
                } else {
                    log.error("客户订单无法查询到状态：{} {}{}", excelData.getOrderFno(), excelData.getInventoryId(), inventoryTable);
                }
                RcvmasterDO rcvmaster = rcvmasterMapper.selectById(invRelation.getOrderId());
                if (rcvmaster != null) {
                    excelData.setOrderDate(rcvmaster.getROrdDate());
                }
                excelData.setStockInfo(stockInfo);
            }
            //调库
            else if (DoTypeEnum.TKCK == typeEnum || DoTypeEnum.CGDBCK == typeEnum) {
                LambdaQueryWrapper<TransOrderDO> qw = new LambdaQueryWrapper<>();
                qw.eq(TransOrderDO::getTransNo, invRelation.getOrderId())
                        .eq(TransOrderDO::getItemNo, invRelation.getOrderItem());
                TransOrderDO transOrder = transOrderMapper.selectOne(qw);
                if (transOrder != null) {
                    OrderTransStatusEnum statusEnum = OrderTransStatusEnum.getByStatus(transOrder.getStatus());
                    if (statusEnum != null) {
                        excelData.setStatus(statusEnum.getDesc());
                    }
                    excelData.setStockInfo(stockInfo);

                    excelData.setOrderDate(transOrder.getCreateTime());
                } else {
                    log.error("调库单无法查询到状态：{}-{}", excelData.getOrderId(), excelData.getOrderItem());
                }
            }
            //组换
            else if (DoTypeEnum.ZHCK == typeEnum) {
                LambdaQueryWrapper<StockAssemblyDetailViewDO> qw = new LambdaQueryWrapper<>();
                qw.eq(StockAssemblyDetailViewDO::getApplyNo, invRelation.getOrderId()).eq(StockAssemblyDetailViewDO::getInventoryId, invRelation.getInventoryId());
                List<StockAssemblyDetailViewDO> assemblyList = stockAssemblyDetailViewMapper.selectList(qw);
                if (!assemblyList.isEmpty()) {
                    StockAssemblyDetailViewDO assemblyDetailView = assemblyList.get(0);
                    Integer optCode = assemblyDetailView.getOptCode();
                    String opeCodeName = StockAssemblyDetailStatusEnum.getNameByCode(optCode.toString());
                    excelData.setStatus(opeCodeName);
                    excelData.setOrderDate(assemblyDetailView.getApplyTranstime());
                    excelData.setStockInfo(stockInfo);
                } else {
                    log.error("组换单无法查询到状态：{}-{} {}", excelData.getOrderId(), excelData.getOrderItem(), excelData.getInventoryId());
                }
            }
        }
        return new AsyncResult<>("成功");
    }


}

