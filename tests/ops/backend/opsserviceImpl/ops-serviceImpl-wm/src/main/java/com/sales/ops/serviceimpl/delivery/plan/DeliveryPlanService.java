package com.sales.ops.serviceimpl.delivery.plan;

import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.common.until.DateUtil;
import com.sales.ops.common.until.SplitBatchUtils;
import com.sales.ops.db.batchdao.OpsDeliveryPlanDetailBatchDao;
import com.sales.ops.db.batchdao.OpsDeliveryPlanResultBatchDao;
import com.sales.ops.db.entity.*;
import com.sales.ops.db.extdao.OpsDeliveryPlanDao;
import com.sales.ops.db.extdao.TblWorkDayYearDao;
import com.sales.ops.dto.purchase.PurchaseDayDto;
import com.sales.ops.dto.purchase.PurchaseInvoiceDetailInfo;
import com.sales.ops.dto.purchase.PurchaseOrderDetailInfo;
import com.sales.ops.dto.util.CommonResult;
import com.sales.ops.enums.InventoryStatusEnum;
import com.sales.ops.service.po.OrderPurchaseService;
import com.sales.ops.service.wm.WmCommonService;
import com.sales.ops.service.wmOrder.BaseRoService;
import com.sales.ops.serviceimpl.event.v3.order.entity.*;
import com.sales.ops.serviceimpl.event.v3.order.service.OrderRelationService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * bugid: 13685
 * @author ：C14717
 * @version: $
 * @description：
 * @date ：Created in 2024/3/26 9:53
 */
@Service
public class DeliveryPlanService {

    @Resource
    private OrderPurchaseService orderPurchaseService;
    @Resource
    private WmCommonService wmCommonService;
    @Resource
    private TblWorkDayYearDao tblWorkDayYearDao;
    @Resource
    private OpsDeliveryPlanDetailBatchDao detailBatchDao;
    @Resource
    private OpsDeliveryPlanResultBatchDao resultBatchDao;
    @Resource
    private OpsDeliveryPlanDao opsDeliveryPlanDao;
    @Resource
    private BaseRoService baseRoService;
    @Resource
    private OrderRelationService orderRelationService;

    /**
     * 计算交付计划 对外调用
     * @param pointStatus 状态节点
     * @param orderNo 订单号
     * @param orderItem 订单项号
     */
    public Boolean calculateDeliveryPlanDate(String pointStatus, String orderNo, Integer orderItem) throws OpsException {
        Order order = orderRelationService.getOrder(orderNo, orderItem);
        List<OpsDeliveryPlanResult> deliveryPlanResults = new ArrayList<>();
        // 1.获取详情
        List<OpsDeliveryPlanDetail> deliveryPlanDetails = getDetails(order);
        // 2.根据详情汇总计算结果 doid分组 计算最晚关联关系的交货期 或 记录异常
        getResult(deliveryPlanResults, deliveryPlanDetails, pointStatus, order.getOrderId(), order.getOrderItem().toString());
        // 3.持久化
        persistenceDeliveryPlan(deliveryPlanResults, deliveryPlanDetails, order.getOrderId(), order.getOrderItem().toString());
        //4.返回是否更新
        if(CollectionUtils.isEmpty(deliveryPlanResults)){
            return false;
        }
        return true;
    }

    // 根据详情汇总计算结果 doid分组 计算最晚关联关系的交货期
    void getResult(List<OpsDeliveryPlanResult> deliveryPlanResults, List<OpsDeliveryPlanDetail> deliveryPlanDetails, String pointStatus, String orderId, String orderItem){
        // 1.获取版本号;
        Long version = opsDeliveryPlanDao.getVersion(orderId, orderItem);
        Map<String, OpsDeliveryPlanResult> resultMaxMap = new HashMap<String, OpsDeliveryPlanResult>();
        if(Objects.isNull(version)){
            version = 1L;
        } else {
            version = version +1;
        }
        // 获取可信度配置表
        List<OpsDeliveryPlanReliabilityConfig> reliablilityList = opsDeliveryPlanDao.getReliablilityList();
        HashMap<String,Integer> reliablilityMap = new HashMap<>();
        for(OpsDeliveryPlanReliabilityConfig obj : reliablilityList){
            String key = String.format("%s-%s", obj.getCurrentCycle(),obj.getCurrentCyclePoint());
            reliablilityMap.put(key, obj.getReliability());
        }
        // 计算结果表 获取doid 分组 开始周期最小可信度，异常时可信度为0
        Map<String, OpsDeliveryPlanResult> getResultReliabilityMap = new HashMap<String, OpsDeliveryPlanResult>();
        for(OpsDeliveryPlanDetail detail : deliveryPlanDetails ){
            // 非异常，且 sort字段 = 1
            if(detail.getSort().equals(1)){
                if(getResultReliabilityMap.containsKey(detail.getDoId())){
                    //获取可信度
                    int currentDetailReliability = 0;
                    if(!detail.getExRelation().equals(1)) {
                        String key = String.format("%s-%s", detail.getCurrentCycle(), detail.getBeginName());
                        if(Objects.nonNull(reliablilityMap.get(key))){
                            currentDetailReliability = reliablilityMap.get(key);
                        }
                    }
                    //获取上一次result
                    OpsDeliveryPlanResult lastResult = getResultReliabilityMap.get(detail.getDoId());
                    // 写入最小 上一次可信度大于本次 则替换
                    if(lastResult.getReliability() > currentDetailReliability){
                        lastResult.setReliability(currentDetailReliability);
                        lastResult.setCurrentCycle(detail.getCurrentCycle());
                        lastResult.setCurrentCyclePoint(detail.getBeginName());
                        getResultReliabilityMap.put(detail.getDoId(),lastResult);
                    }
                }else {
                    // 初始化
                    OpsDeliveryPlanResult result = new OpsDeliveryPlanResult();
                    result.setCurrentCycle(detail.getCurrentCycle());
                    result.setCurrentCyclePoint(detail.getBeginName());
                    //异常可信度0 非异常 从结果表获取可信度
                    int currentDetailReliability = 0;
                    if(!detail.getExRelation().equals(1)){
                        String key = String.format("%s-%s", result.getCurrentCycle(),result.getCurrentCyclePoint());
                        if(Objects.nonNull(reliablilityMap.get(key))){
                            currentDetailReliability = reliablilityMap.get(key);
                        }
                    }
                    result.setReliability(currentDetailReliability);
                    getResultReliabilityMap.put(detail.getDoId(),result);
                }
            }
        }

        // 2.计算每条doid明细中最大交付期，如果有一条异常则result标识为异常单子
        for(OpsDeliveryPlanDetail detail : deliveryPlanDetails ){
            detail.setVersion(version);
            detail.setCurrentValid(1);
            detail.setEventSource(pointStatus);
            if(resultMaxMap.containsKey(detail.getDoId())){
                // 异常单子不重复写入
                if(resultMaxMap.get(detail.getDoId()).getExRelation().equals(1)){
                    continue;
                }
                if(resultMaxMap.get(detail.getDoId()).getExpectDeliveryDay().getTime() < detail.getEndDate().getTime()){
                    resultMaxMap.get(detail.getDoId()).setExpectDeliveryDay(detail.getEndDate());
                }
            }else {
                OpsDeliveryPlanResult result = initResult(detail, version);
                // 记录异常
                if(detail.getExRelation().equals(1)){
                    result.setExRelation(1);
                }
                resultMaxMap.put(detail.getDoId(),result);
            }
        }
        for (Map.Entry<String, OpsDeliveryPlanResult> entry : resultMaxMap.entrySet()){
            deliveryPlanResults.add(entry.getValue());
        }
        //获取打分配置 bugid:14289 c14717 20240528 result表增加打分字段
        if(CollectionUtils.isNotEmpty(deliveryPlanResults)){
            for(OpsDeliveryPlanResult obj : deliveryPlanResults){
                obj.setReliability(getResultReliabilityMap.get(obj.getDoId()).getReliability());
                obj.setCurrentCycle(getResultReliabilityMap.get(obj.getDoId()).getCurrentCycle());
                obj.setCurrentCyclePoint(getResultReliabilityMap.get(obj.getDoId()).getCurrentCyclePoint());
            }
        }
    }

    // 初始化result
    OpsDeliveryPlanResult initResult(OpsDeliveryPlanDetail detail,Long version){
        OpsDeliveryPlanResult result = new OpsDeliveryPlanResult();
        result.setOrderId(detail.getOrderId());
        result.setOrderItem(detail.getOrderItem());
        result.setDoId(detail.getDoId());
        result.setExpectDeliveryDay(detail.getEndDate());
        result.setEventSource(detail.getEventSource());
        result.setDelflag(0);
        result.setCreTime(DateUtil.getNow());
        result.setVersion(version);
        result.setCurrentValid(1);
        result.setExRelation(0);
        return result;

    }

    // 持久化数据
    @Transactional(rollbackFor = Exception.class)
    public void persistenceDeliveryPlan(List<OpsDeliveryPlanResult> resultList, List<OpsDeliveryPlanDetail> detailList, String orderId, String orderItem) {
        //1.更新有效状态 result detail bugid:17143 不报错返回
        if(CollectionUtils.isNotEmpty(resultList)){
            //opsDeliveryPlanDao.updateDetailsCurrentValid(orderId,orderItem);
            //opsDeliveryPlanDao.updateResultsCurrentValid(orderId,orderItem);
            updateCurrentValid(orderId,orderItem);
        }
        //2.写入新数据 result detail
        Map<Integer, List<OpsDeliveryPlanResult>> resultMap = SplitBatchUtils.getInsertBatchBySqlserver(resultList, OpsDeliveryPlanResult.class);
        Map<Integer, List<OpsDeliveryPlanDetail>> detailMap = SplitBatchUtils.getInsertBatchBySqlserver(detailList, OpsDeliveryPlanDetail.class);
        for (Map.Entry<Integer, List<OpsDeliveryPlanResult>> entry : resultMap.entrySet()) {
            resultBatchDao.batchInsert(entry.getValue());
        }
        for (Map.Entry<Integer, List<OpsDeliveryPlanDetail>> entry : detailMap.entrySet()) {
            detailBatchDao.batchInsert(entry.getValue());
        }
    }
    // 按照id更新 当前状态 bugid:17143 c14717 20250319
    public void updateCurrentValid( String orderId, String orderItem){
        List<Long> resultsCurrentValid = opsDeliveryPlanDao.getResultsCurrentValid(orderId, orderItem);
        List<Long> detailsCurrentValid = opsDeliveryPlanDao.getDetailsCurrentValid(orderId, orderItem);
        for(Long id : resultsCurrentValid){
            opsDeliveryPlanDao.updateResultsCurrentValidById(id);
        }
        for(Long id : detailsCurrentValid){
            opsDeliveryPlanDao.updateDetailsCurrentValidById(id);
        }
    }

    /**
     *
     * @param order 订单关联关系
     */
    List<OpsDeliveryPlanDetail> getDetails(Order order) throws OpsException{
        List<OpsDeliveryPlanDetail> deliveryPlanDetails = new ArrayList<>();
        for (JYCK jyck : order.getJycks()) {
            // 解析关联关系
            for (Relation relation : jyck.getRelations()) {
                List<OpsDeliveryPlanDetail> relationDetails = new ArrayList<>();
                if (relation instanceof CGRelation) {
                    // 供应商生产周期 P1 供应商运输周期P2 采购收货周期T1
                    getCGRelation(jyck, (CGRelation)relation, relationDetails);
                } else if (relation instanceof DBRelation) {
                    // 调拨周期T2 调拨收货周期T4
                    getDBRelation(jyck, (DBRelation)relation, relationDetails);
                } else if (relation instanceof OKRelation) {
                    // 发货仓发货周期T5 送达客户运输周期T6
                    getOKRelation(jyck, (OKRelation)relation, relationDetails);
                } else if (relation instanceof EXRelation) {
                    // 异常
                    getExRelation(jyck, (EXRelation)relation, relationDetails);
                }
                // 收集数据
                deliveryPlanDetails.addAll(relationDetails);
            }
        }
        return deliveryPlanDetails;
    }

    //关联关系对应周期： 供应商生产周期 P1 供应商运输周期P2 采购收货周期T1
    void getCGRelation(JYCK jyck , CGRelation relation, List<OpsDeliveryPlanDetail> relationDetails) throws OpsException{
        //  invStatus.code = "P" 生产在途 || 0.虚拟库存 invStatus.code = "T0" 虚拟请购
        if(relation.getInvStatus().getCode().equals(InventoryStatusEnum.PRODUCE.getCode())
                || relation.getInvStatus().getCode().equals(InventoryStatusEnum.REQUEST.getCode())){
            //1.供应商生产周期 P1
            getP1(jyck, relation, relationDetails);
            //采购在途
        } else if (relation.getInvStatus().getCode().equals(InventoryStatusEnum.CGTRANS.getCode())){
            //2.供应商运输周期P2
            getP2(jyck, relation, relationDetails);
        } else if(relation.getInvStatus().getCode().equals(InventoryStatusEnum.W.getCode())){
            //3.采购收货周期T1
            getT1(jyck, relation, relationDetails);
        }
    }

    //关联关系对应周期： 调拨周期T2 调拨收货周期T4
    void getDBRelation(JYCK jyck, DBRelation relation, List<OpsDeliveryPlanDetail> relationDetails){
        if(!relation.getInvStatus().getCode().equals(InventoryStatusEnum.W.getCode())){
            //1.调拨周期T2
            getT2(jyck, relation, relationDetails);
        } else {
            //2. 调拨收货周期T4
            getT4(jyck, relation, relationDetails);
        }
    }

    //关联关系对应周期： 发货仓发货周期T5 送达客户运输周期T6
    void getOKRelation(JYCK jyck, OKRelation relation, List<OpsDeliveryPlanDetail> relationDetails){
        if(relation.getOutQty() == 0){
            //1.发货仓发货周期T5
            getT5(jyck, relation, relationDetails);
        }else {
            //2.送达客户运输周期T6
            getT6(jyck, relation, relationDetails);
        }
    }

    OpsDeliveryPlanDetail initDetail(JYCK jyck, Relation relation){
        //初始化detail
        OpsDeliveryPlanDetail detail = new OpsDeliveryPlanDetail();
        detail.setOrderId(jyck.getOrderId());
        detail.setOrderItem(jyck.getOrderItem().toString());
        detail.setDoSource(jyck.getDoSource().getType());
        detail.setDoId(jyck.getJyDoId());
        if(Objects.nonNull(jyck.getPcoItem())){
            detail.setPcoId(jyck.getPcoItem().getPcoId());
        }
        detail.setModelNo(jyck.getModelno());
        detail.setSort(1);
        detail.setCurrentCycle("P1");
        detail.setCycleName("供应商生产周期");
        detail.setBeginName("开始日期名");
        detail.setBeginDate(null);
        detail.setEndDate(null);
        detail.setCycleDays(0);
        detail.setWorkDay(0);//查表
        detail.setDelflag(0);
        detail.setCreator("");
        detail.setCreTime(DateUtil.getNow());
        detail.setExRelation(0);
        if (relation instanceof CGRelation) {
            CGRelation cgRelation = (CGRelation)relation;
            detail.setPoNo(cgRelation.getRequestNo());
            detail.setPoNoItem(cgRelation.getRequestItemNo());
            detail.setPoNoSplitNo(cgRelation.getRequestSplitNo());
            detail.setInventoryId(cgRelation.getInventoryId());
            detail.setInventoryStatus(cgRelation.getInvStatus().getCode());
            detail.setQuantity(cgRelation.getUseQty());
            detail.setDelivery(cgRelation.getSupplierId());
            detail.setReceive(cgRelation.getSupplierId());
        } else if (relation instanceof DBRelation) {
            DBRelation dbRelation = (DBRelation)relation;
            detail.setInventoryId(dbRelation.getInventoryId());
            detail.setInventoryStatus(dbRelation.getInvStatus().getCode());
            detail.setQuantity(dbRelation.getUseQty());
            detail.setDelivery(dbRelation.getFromWarehouseCode());
            detail.setReceive(dbRelation.getToWarehouseCode());
        } else if (relation instanceof OKRelation) {
            OKRelation okRelation = (OKRelation)relation;
            detail.setInventoryId(okRelation.getInventoryId());
            detail.setInventoryStatus(okRelation.getInvStatus().getCode());
            detail.setQuantity(okRelation.getUseQty());
            detail.setDelivery(okRelation.getWarehouseCode());
        } else if (relation instanceof EXRelation) {
            detail.setExRelation(1);
            detail.setRemark("异常单据");
            detail.setCurrentCycle("E");
            detail.setCycleName("异常");
            detail.setBeginName("异常");
        }
        return detail;
    }

    //周期计算： 供应商生产周期 P1
    void getP1(JYCK jyck, CGRelation relation, List<OpsDeliveryPlanDetail> relationDetails) throws OpsException{
        //1. 初始化detail
        OpsDeliveryPlanDetail detail = initDetail(jyck,relation);
        detail.setCurrentCycle("P1");
        detail.setCycleName("供应商生产周期");

        //2. 获取供应商生产(P1天数)天数及是否是工作日计算
        PurchaseDayDto purchaseDay = orderPurchaseService.getPurchaseDay(relation.getRequestNo(), relation.getRequestItemNo(), relation.getRequestSplitNo());
        //
        if(Objects.nonNull(purchaseDay) && !purchaseDay.getExistSupplier()){
            return;
        }
        // BUGID:19298 C12961 2025-11-03
        if(Objects.isNull(purchaseDay) || Objects.isNull(purchaseDay.getProduceDay()) || Objects.isNull(purchaseDay.getProduceWorkDay())){
            return;
            //throw Exceptions.OpsException("获取供应商生产周期失败");
        }
        detail.setCycleDays(purchaseDay.getProduceDay());//供应商生产周期天数 P1
        if(purchaseDay.getProduceWorkDay()){
            detail.setWorkDay(1);
        }
        //3. 获取当前周期开始时间
        Date beginDate =  DateUtil.getNow();
        detail.setBeginName("计算时系统时间");
        PurchaseOrderDetailInfo purchaseInfo = orderPurchaseService.getPurchaseInfo(relation.getRequestNo(), relation.getRequestItemNo(), relation.getRequestSplitNo());
        if(Objects.nonNull(purchaseInfo)){
            // 返信 != null? 返信 ： （生产 != null ? 生产：计算时系统时间）
            //1.生产日期
            if(Objects.nonNull(purchaseInfo.getFacProdDate())){
                detail.setBeginName("供应商生产日期");
                beginDate = purchaseInfo.getFacProdDate();
            }
            //2.返信日期
            if(Objects.nonNull(purchaseInfo.getReplyExportDate())){
                detail.setBeginName("供应商返信日期");
                beginDate = purchaseInfo.getReplyExportDate();
            }
        }
        if(Objects.isNull(beginDate)){
            beginDate =  DateUtil.getNow();
            detail.setBeginName("计算时系统时间");
        }
        detail.setBeginDate(beginDate);
        //4.获取当前周期结束日期
        Date endDate = beginDate;
        if(!detail.getBeginName().equals("供应商返信日期")){
            endDate = DateUtil.addDay(beginDate,detail.getCycleDays());
            if(detail.getWorkDay().equals(1)){
                if(detail.getCycleDays()>0){
                    TblWorkdayYear workDay = tblWorkDayYearDao.getAddDaysWorkDay(DateUtil.dateToString(detail.getBeginDate()),detail.getCycleDays()+1)
                            .get(detail.getCycleDays());
                    endDate = workDay.getWorkdayDate();
                }
            }
        }
        detail.setEndDate(endDate);
        relationDetails.add(detail);
        getP2(jyck, relation, relationDetails);
    }

    //周期计算 供应商运输周期P2
    void getP2(JYCK jyck, CGRelation relation, List<OpsDeliveryPlanDetail> relationDetails) throws OpsException{
        //1.初始化detail
        OpsDeliveryPlanDetail detail = initDetail(jyck,relation);
        detail.setCurrentCycle("P2");
        detail.setCycleName("供应商运输周期");
        detail.setReceive(relation.getWarehouseCode());
        //2. 获取供应商运输周期(P2)天数及是否是工作日计算
        PurchaseDayDto purchaseDay = orderPurchaseService.getPurchaseDay(relation.getRequestNo(), relation.getRequestItemNo(), relation.getRequestSplitNo());
        // BUGID:15349 C14717 20240929
        if(Objects.isNull(purchaseDay) || Objects.isNull(purchaseDay.getTransDay()) || Objects.isNull(purchaseDay.getTransWorkDay())){
            //bugid：17143 异常跳过，不抛出
            //throw Exceptions.OpsException("获取供应商运输周期失败");
            relationDetails.clear();
            return;

        }
        detail.setCycleDays(purchaseDay.getTransDay());//供应商运输周期天数 P2
        if(purchaseDay.getTransWorkDay()){
            detail.setWorkDay(1);
        }
        // 3. 获取当前周期开始时间
        Date beginDate =  DateUtil.getNow();
        detail.setBeginName("计算时系统时间");
        int detailsSize = relationDetails.size();
        detail.setSort(detailsSize+1);
        // 3.1预计节点
        if(detailsSize > 0){
            detail.setBeginName("上一节点结束日");//上一节点结束日期
            beginDate = relationDetails.get(detailsSize-1).getEndDate();
        }
        // 3.2开始节点
        if(detailsSize == 0){
            //3.1.1 获取实际离场日
            PurchaseOrderDetailInfo purchaseInfo = orderPurchaseService.getPurchaseInfo(relation.getRequestNo(), relation.getRequestItemNo(), relation.getRequestSplitNo());
            if(Objects.nonNull(purchaseInfo)){
                detail.setBeginName("供应商实际离场日");
                beginDate =  purchaseInfo.getFacExpDate();
            }
            //3.1.2 获取实际发运日期 和 预计到货日期
            if(StringUtils.isNotBlank(relation.getInvoiceNo())){
                PurchaseInvoiceDetailInfo invoiceInfo = orderPurchaseService.getInvoiceInfo(relation.getInvoiceNo(), relation.getInvoiceId());
                if(Objects.nonNull(invoiceInfo)){
                    //发运日期
                    detail.setBeginName("供应商实际发运日");
                    beginDate =  invoiceInfo.getShipDate();
                    if(Objects.nonNull(invoiceInfo.getEsArrivalDate())){
                        // 预计到货期
                        detail.setBeginName("供应商预计到货日");
                        beginDate = invoiceInfo.getEsArrivalDate();
                    }
                }
            }
        }
        if(Objects.isNull(beginDate)){
            beginDate =  DateUtil.getNow();
            detail.setBeginName("计算时系统时间");
        }
        detail.setBeginDate(beginDate);

        //4.获取当前周期结束日期
        Date endDate = beginDate;
        if(!detail.getBeginName().equals("供应商预计到货日")){
            endDate = DateUtil.addDay(beginDate,detail.getCycleDays());
            if(detail.getWorkDay().equals(1)){
                if(detail.getCycleDays()>0){
                    TblWorkdayYear workDay = tblWorkDayYearDao.getAddDaysWorkDay(DateUtil.dateToString(detail.getBeginDate()),detail.getCycleDays()+1)
                            .get(detail.getCycleDays());
                    endDate = workDay.getWorkdayDate();
                }
            }
        }
        detail.setEndDate(endDate);
        relationDetails.add(detail);
        getT1(jyck, relation,relationDetails);
    }

    //周期计算 采购收货周期T1 cg
    void getT1(JYCK jyck, CGRelation relation, List<OpsDeliveryPlanDetail> relationDetails){
        //1. 初始化detail
        OpsDeliveryPlanDetail detail = initDetail(jyck,relation);
        detail.setCurrentCycle("T1");
        detail.setCycleName("收货仓处理周期");
        detail.setReceive(relation.getWarehouseCode());
        //2. 获取采购收货周期(T1)天数及是否是工作日计算
        OpsDeliveryPlanConfig configT1 = opsDeliveryPlanDao.getOpsDeliveryPlanConfig("T1");
        if(Objects.nonNull(configT1)){
            detail.setCycleDays(configT1.getDays());
            detail.setWorkDay(configT1.getWorkday());
        }
        // 3. 获取当前周期开始时间
        Date beginDate =  DateUtil.getNow();
        detail.setBeginName("计算时系统时间");
        int detailsSize = relationDetails.size();
        detail.setSort(detailsSize+1);
        // 3.1预计节点
        if(detailsSize > 0){
            detail.setBeginName("上一节点结束日");//上一节点结束日期
            beginDate = relationDetails.get(detailsSize-1).getEndDate();
        }
        // 3.2开始节点
        if(detailsSize == 0){
            // RO 签收时间
            if(Objects.nonNull(relation.getInvoiceId())){
                beginDate = opsDeliveryPlanDao.getOpsRoSignTime(relation.getInvoiceId());
                detail.setBeginName("签收时间");//
            }
        }
        if(Objects.isNull(beginDate)){
            beginDate =  DateUtil.getNow();
            detail.setBeginName("计算时系统时间");
        }
        detail.setBeginDate(beginDate);

        //4.获取当前周期结束日期
        Date endDate = DateUtil.addDay(beginDate,detail.getCycleDays());
        if(detail.getWorkDay().equals(1)){
            if(detail.getCycleDays()>0){
                TblWorkdayYear workDay = tblWorkDayYearDao.getAddDaysWorkDay(DateUtil.dateToString(detail.getBeginDate()),detail.getCycleDays()+1)
                        .get(detail.getCycleDays());
                endDate = workDay.getWorkdayDate();
            }
        }
        detail.setEndDate(endDate);
        relationDetails.add(detail);
        //1.实际入库日 ro表签收时间 发票id查询 ；2.采购虚拟关联关系 T0状态，无调拨周期
        if(!jyck.getWarehouseCode().equals(relation.getWarehouseCode()) && !InventoryStatusEnum.REQUEST.getCode().equals(relation.getInvStatus().getCode())){
            // 调拨周期T2
            getT2(jyck, relation, relationDetails);
        } else {
            // 发货仓发货周期T5
            getT5(jyck, relation, relationDetails);
        }
    }
    //周期计算 调拨周期T2
    void getT2(JYCK jyck, Relation relation, List<OpsDeliveryPlanDetail> relationDetails){
        //1.初始化detail
        OpsDeliveryPlanDetail detail = initDetail(jyck,relation);
        detail.setPoNo(null);
        detail.setPoNoItem(null);
        detail.setPoNoSplitNo(null);
        detail.setCurrentCycle("T2");
        detail.setCycleName("调拨周期");
        //2. 获取调拨周期(T2)天数及是否是工作日计算
        OpsDeliveryPlanConfig configT2 = opsDeliveryPlanDao.getOpsDeliveryPlanConfig("T2");
        if(Objects.nonNull(configT2)){
            detail.setWorkDay(configT2.getWorkday());
        }
        //doPost时间
        Date transferDate = null;
        // 预计出库时间
        Date expectedDeliveryDate = null;
        //Date wlDate = null;
        if (relation instanceof CGRelation) {
            CGRelation cgRelation = (CGRelation)relation;
            //获取仓库到仓库配置  运输时间
            detail.setReceive(cgRelation.getWarehouseCode());
            detail.setDelivery(jyck.getWarehouseCode());
        } else if (relation instanceof DBRelation) {
            DBRelation dbRelation = (DBRelation)relation;
            if(Objects.nonNull(dbRelation.getDbDo())){
                // ops_do_post 创建时间
                transferDate = opsDeliveryPlanDao.getOpsDoPostCreateTime(dbRelation.getDbDo().getDoId());
                // bugid:14335 c14717 20240605
                expectedDeliveryDate = dbRelation.getDbDo().getWmsExpectedDeliveryDate();
            }
            /*if(Objects.nonNull(dbRelation.getDbDo())){
                wlDate = dbRelation.getDbDo().getWlDate();
            }*/
            detail.setReceive(dbRelation.getToWarehouseCode());
            detail.setDelivery(dbRelation.getFromWarehouseCode());
        }
        CommonResult<Integer> findDBCKDDResult =  wmCommonService.getWarehouseDeliveryDayByCode(detail.getDelivery(),detail.getReceive());
        if(findDBCKDDResult.isSuccess()){
            detail.setCycleDays(findDBCKDDResult.getData());
        }
        // 3. 获取当前周期开始时间
        Date beginDate =  DateUtil.getNow();
        detail.setBeginName("计算时系统时间");

        //bugid:14307 c14717 20240529
        // 如果不存在调拨单 根据客单反推调拨单
        /*if(Objects.isNull(wlDate)){
            wlDate = DateUtil.addDay(jyck.getWlDate(),-detail.getCycleDays());
        }*/
        // 物流指定发货日和当前时间比较
        /*if(wlDate.getTime() > beginDate.getTime()){
            detail.setBeginName("物流指定交货期");
            beginDate = wlDate;
        }*/
        int detailsSize = relationDetails.size();
        detail.setSort(detailsSize+1);
        // 3.1预计节点
        if(detailsSize > 0){
            Date lastCDate  = relationDetails.get(detailsSize-1).getEndDate();
            // 采购到货时间 和开始时间比较
            if(lastCDate.getTime() > beginDate.getTime()){
                detail.setBeginName("上一节点结束日");//上一节点结束日期
                beginDate = lastCDate;
            }
        }
        // 3.2开始节点
        if(detailsSize == 0){
            if(Objects.nonNull(expectedDeliveryDate)){
                detail.setBeginName("预计出库时间");
                beginDate = expectedDeliveryDate;
            }
            //3.实际调拨发出日 T3
            if(Objects.nonNull(transferDate)){
                beginDate = transferDate;
                detail.setBeginName("实际调拨发出日");//
            }
        }
        detail.setBeginDate(beginDate);
        //4.获取当前周期结束日期
        Date endDate  = DateUtil.addDay(beginDate,detail.getCycleDays());
        if(detail.getWorkDay().equals(1)){
            if(detail.getCycleDays()>0){
                TblWorkdayYear workDay = tblWorkDayYearDao.getAddDaysWorkDay(DateUtil.dateToString(detail.getBeginDate()),detail.getCycleDays()+1)
                        .get(detail.getCycleDays());
                endDate = workDay.getWorkdayDate();
            }
        }

        detail.setEndDate(endDate);
        relationDetails.add(detail);
        getT4(jyck, relation, relationDetails);
    }

    // 周期计算 调拨收货周期T4（调拨入库）
    void getT4(JYCK jyck, Relation relation, List<OpsDeliveryPlanDetail> relationDetails){
        //1.初始化detail
        OpsDeliveryPlanDetail detail = initDetail(jyck,relation);
        detail.setPoNo(null);
        detail.setPoNoItem(null);
        detail.setPoNoSplitNo(null);
        detail.setCurrentCycle("T4");
        detail.setCycleName("调拨入库周期");// 获取开始时间
        //2. 获取采购收货周期(T4)天数及是否是工作日计算
        OpsDeliveryPlanConfig configT4 = opsDeliveryPlanDao.getOpsDeliveryPlanConfig("T4");
        if(Objects.nonNull(configT4)){
            detail.setCycleDays(configT4.getDays());
            detail.setWorkDay(configT4.getWorkday());
        }
        Long invoiceId = null;
        if (relation instanceof CGRelation) {
            // CGRelation cgRelation = (CGRelation)relation;
            detail.setReceive(jyck.getWarehouseCode());
            detail.setDelivery(jyck.getWarehouseCode());
        } else if (relation instanceof DBRelation) {
            DBRelation dbRelation = (DBRelation)relation;
            detail.setReceive(dbRelation.getToWarehouseCode());
            detail.setDelivery(dbRelation.getToWarehouseCode());
            invoiceId = dbRelation.getInvoiceId();
        }
        // 3. 获取当前周期开始时间
        Date beginDate =  DateUtil.getNow();
        detail.setBeginName("计算时系统时间");
        int detailsSize = relationDetails.size();
        detail.setSort(detailsSize+1);
        // 3.1预计节点
        if(detailsSize > 0){
            detail.setBeginName("上一节点结束日");//上一节点结束日期
            beginDate = relationDetails.get(detailsSize-1).getEndDate();
        }
        // 3.2开始节点
        if(detailsSize == 0){
            // RO 签收时间 调拨入 ro表签收时间 发票id查询 ro类型
            if(Objects.nonNull(invoiceId)){
                beginDate = opsDeliveryPlanDao.getOpsRoSignTime(invoiceId);
                detail.setBeginName("签收时间");//
            }
        }
        if(Objects.isNull(beginDate)){
            beginDate =  DateUtil.getNow();
            detail.setBeginName("计算时系统时间");
        }
        detail.setBeginDate(beginDate);

        //4.获取当前周期结束日期
        Date endDate = DateUtil.addDay(beginDate,detail.getCycleDays());
        if(detail.getWorkDay().equals(1)){
            if(detail.getCycleDays()>0){
                TblWorkdayYear workDay = tblWorkDayYearDao.getAddDaysWorkDay(DateUtil.dateToString(detail.getBeginDate()),detail.getCycleDays()+1)
                        .get(detail.getCycleDays());
                endDate = workDay.getWorkdayDate();
            }
        }
        detail.setEndDate(endDate);
        relationDetails.add(detail);
        // 发货仓发货周期T5
        getT5(jyck, relation, relationDetails);
    }

    //周期计算 发货仓发货周期T5
    void getT5(JYCK jyck, Relation relation, List<OpsDeliveryPlanDetail> relationDetails){
        //1.初始化detail
        OpsDeliveryPlanDetail detail = initDetail(jyck,relation);
        detail.setPoNo(null);
        detail.setPoNoItem(null);
        detail.setPoNoSplitNo(null);
        detail.setCurrentCycle("T5");
        detail.setCycleName("发货仓发货周期");// 获取开始时间
        detail.setReceive(jyck.getWarehouseCode());
        detail.setDelivery(jyck.getWarehouseCode());

        //2. 获取采购收货周期(T4)天数及是否是工作日计算
        OpsDeliveryPlanConfig configT5 = opsDeliveryPlanDao.getOpsDeliveryPlanConfig("T5");
        if(Objects.nonNull(configT5)){
            detail.setCycleDays(configT5.getDays());
            detail.setWorkDay(configT5.getWorkday());
        }

        // 3. 获取当前周期开始时间
        Date beginDate =  DateUtil.getNow();
        detail.setBeginName("计算时系统时间");
        //bugid:14307 c14717 20240529
        /*Date wlDate = jyck.getWlDate();
        if(wlDate.getTime() > beginDate.getTime()){
            detail.setBeginName("物流指定交货期");
            beginDate = wlDate;
        }*/

        int detailsSize = relationDetails.size();
        detail.setSort(detailsSize+1);
        // 3.1预计节点
        if(detailsSize > 0){
            Date lastCDate = relationDetails.get(detailsSize-1).getEndDate();
            if(lastCDate.getTime() > beginDate.getTime()){
                detail.setBeginName("上一节点结束日");//上一节点结束日期
                beginDate = lastCDate;
            }
        }
        // 3.2开始节点
        if(detailsSize == 0){
            if(Objects.nonNull(jyck.getWmsExpectedDeliveryDate())){
                detail.setBeginName("预计出库时间");//上一节点结束日期
                beginDate = jyck.getWmsExpectedDeliveryDate();
            }
        }
        detail.setBeginDate(beginDate);

        //4.获取当前周期结束日期
        Date endDate = DateUtil.addDay(beginDate,detail.getCycleDays());
        if(detail.getWorkDay().equals(1)){
            if(detail.getCycleDays()>0){
                TblWorkdayYear workDay = tblWorkDayYearDao.getAddDaysWorkDay(DateUtil.dateToString(detail.getBeginDate()),detail.getCycleDays()+1)
                        .get(detail.getCycleDays());
                endDate = workDay.getWorkdayDate();
            }
        }
        detail.setEndDate(endDate);
        relationDetails.add(detail);
        //送达客户运输周期T6
        getT6(jyck, relation, relationDetails);
    }

    //周期计算 送达客户运输周期T6
    void getT6(JYCK jyck, Relation relation, List<OpsDeliveryPlanDetail> relationDetails){
        //1.初始化detail
        OpsDeliveryPlanDetail detail = initDetail(jyck,relation);
        detail.setPoNo(null);
        detail.setPoNoItem(null);
        detail.setPoNoSplitNo(null);
        detail.setCurrentCycle("T6");
        detail.setCycleName("送达客户运输周期");// 获取开始时间
        detail.setReceive(jyck.getJyckDo().getDeptNo());
        detail.setDelivery(jyck.getWarehouseCode());

        //2. 获取采购收货周期(T4)天数及是否是工作日计算
        OpsDeliveryPlanConfig configT6 = opsDeliveryPlanDao.getOpsDeliveryPlanConfig("T6");
        if(Objects.nonNull(configT6)){
            detail.setWorkDay(configT6.getWorkday());
        }
        CommonResult<Integer> warehouseToDeptConfig = wmCommonService.getWarehouseSalesbranchConfigByCode(detail.getReceive(),detail.getDelivery());
        if(warehouseToDeptConfig.isSuccess()){
            detail.setCycleDays(warehouseToDeptConfig.getData());
        }

        // 3. 获取当前周期开始时间
        Date beginDate =  DateUtil.getNow();
        detail.setBeginName("计算时系统时间");
        int detailsSize = relationDetails.size();
        detail.setSort(detailsSize+1);
        // 3.1预计节点
        if(detailsSize > 0){
            detail.setBeginName("上一节点结束日");//上一节点结束日期
            beginDate = relationDetails.get(detailsSize-1).getEndDate();
        }
        // 3.2开始节点
        if(detailsSize == 0){
            // 1.发运日期
            // 2.承运商预计送达日期
            beginDate = opsDeliveryPlanDao.getExpdetailShipDate(jyck.getJyDoId());
            detail.setBeginName("发运日期");//

        }
        if(Objects.isNull(beginDate)){
            beginDate =  DateUtil.getNow();
            detail.setBeginName("计算时系统时间");
        }
        detail.setBeginDate(beginDate);
        //4.获取当前周期结束日期
        Date endDate = null;
        //bugid:14264 20240529 c14717 tms回传预计送达日 为detail表的结束日期
        if(Objects.nonNull(jyck.getTmsExpectedDeliveryDate())){
            endDate = jyck.getTmsExpectedDeliveryDate();
        }else {
            endDate = DateUtil.addDay(beginDate,detail.getCycleDays());
            if(detail.getWorkDay().equals(1)){
                if(detail.getCycleDays()>0){
                    TblWorkdayYear workDay = tblWorkDayYearDao.getAddDaysWorkDay(DateUtil.dateToString(detail.getBeginDate()),detail.getCycleDays()+1)
                            .get(detail.getCycleDays());
                    endDate = workDay.getWorkdayDate();
                }
            }
        }
        detail.setEndDate(endDate);
        relationDetails.add(detail);
    }

    // 异常处理
    void getExRelation(JYCK jyck, EXRelation relation, List<OpsDeliveryPlanDetail> relationDetails){
        OpsDeliveryPlanDetail detail = initDetail(jyck,relation);
        relationDetails.add(detail);
    }

}
