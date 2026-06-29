package com.sales.ops.serviceimpl.event.v3.status.service;

import cn.hutool.json.JSONUtil;
import com.sales.ops.common.opsexception.Exceptions;
import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.db.dao.OrderStatusItemMapper;
import com.sales.ops.db.dao.OrderStatusMapper;
import com.sales.ops.db.dao.OrderStatusViewMapper;
import com.sales.ops.db.entity.*;
import com.sales.ops.db.extdao.OrderDao;
import com.sales.ops.db.extdao.OrderStatusViewDao;
import com.sales.ops.dto.order.OrderStatusViewVO;
import com.sales.ops.dto.purchase.PurchaseInvoiceDetailInfo;
import com.sales.ops.dto.purchase.PurchaseOrderDetailInfo;
import com.sales.ops.dto.replacement.NotifyShipmentPlanDto;
import com.sales.ops.dto.replacement.NotifyShipmentPlanPageDto;
import com.sales.ops.dto.tms.TmsTrackingResult;
import com.sales.ops.dto.util.DateUtil;
import com.sales.ops.enums.*;
import com.sales.ops.service.log.OpsHandOverService;
import com.sales.ops.service.log.RoConfirmLogService;
import com.sales.ops.service.order.OpsCustomerOrderService;
import com.sales.ops.service.po.OrderPurchaseService;
import com.sales.ops.service.wmOrder.BaseRoService;
import com.sales.ops.service.wmOrder.DoPcoLogicCenterService;
import com.sales.ops.service.wmOrder.ExpdetailService;
import com.sales.ops.serviceimpl.dispatch.dodispatch.service.TMSRouteHandler;
import com.sales.ops.serviceimpl.event.v3.order.entity.*;
import com.sales.ops.serviceimpl.event.v3.order.service.CustomerOrderService;
import com.sales.ops.serviceimpl.event.v3.plan.OrderDeliveryPlanService;
import com.sales.ops.serviceimpl.event.v3.status.entity.*;
import com.sales.ops.serviceimpl.event.v3.status.enums.OrderStatusEnum;
import com.sales.ops.serviceimpl.event.v3.status.enums.OrderStatusItemEnum;
import com.smc.smccloud.core.model.enums.CarrierEnum;
import com.smc.smccloud.core.model.enums.OPSTransTypeEnum;
import com.smc.smccloud.model.order.OrderNoInfo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import static com.sales.ops.serviceimpl.event.v3.status.enums.OrderStatusItemEnum.*;

@Slf4j
@Service
@AllArgsConstructor
public class OrderStatusServiceImpl implements OrderStatusService {

    private OrderStatusMapper orderStatusMapper;
    private OrderStatusItemMapper orderStatusItemMapper;
    private OrderStatusViewMapper orderStatusViewMapper;
    private OpsHandOverService opsHandOverService;
    private TMSRouteHandler tmsRouteHandler;
    private OrderPurchaseService orderPurchaseService;
    private ExpdetailService expdetailService;
    private CustomerOrderService customerOrderService;
    private BaseRoService baseRoService;
    private RoConfirmLogService roConfirmLogService;
    private OpsCustomerOrderService opsCustomerOrderService;
    private DoPcoLogicCenterService doPcoLogicCenterService;

    private OrderDao orderDao;
    private OrderStatusViewDao orderStatusViewDao;
    private OrderDeliveryPlanService orderDeliveryPlanService;

    @Override
    public void updateStatusToCancel(String orderNo, Integer orderItem) {
        OrderStatusItemExample e = new OrderStatusItemExample();
        e.createCriteria().andOrderIdEqualTo(orderNo).andOrderItemEqualTo(orderItem);
        OrderStatusItem update = new OrderStatusItem();
        update.setStatus(OrderStatusEnum.CANCEL.getCode());
        update.setStatusDesc(OrderStatusItemEnum.CANCEL.getCode());
        HashMap<String, String> map = new HashMap<>();
        map.put("删除时间", DateUtil.dateToDateTimeString(new Date()));
        update.setStatusInfo(JSONUtil.toJsonStr(map));
        update.setModifier("订单取消");
        update.setModifyTime(new Date());
        orderStatusItemMapper.updateByExampleSelective(update, e);
    }

    @Override
    public List<OrderStatusViewVO> findOrderStatusViewList(String orderNo, Integer orderItem) {
        //查询订单状态详情
        List<OrderStatusViewVO> orderStatuses = orderStatusViewDao.selectOrderStatusViewList(orderNo, orderItem);
        //查询交付计划表
        for (OrderStatusViewVO orderStatus : orderStatuses) {
            //为空不查询
            if (StringUtils.isNotBlank(orderStatus.getWmOrderId())) {
                List<OpsDeliveryPlanResult> planResultList = orderDeliveryPlanService.findPlanResultListByDoId(orderStatus.getWmOrderId());
                if (CollectionUtils.isNotEmpty(planResultList)) {
                    //只获取第一条数据，理论上只有一条生效数据
                    OpsDeliveryPlanResult planResult = planResultList.get(0);
                    if (planResult != null && planResult.getReliability() != null) {
                        //可信度60才展示
                        if (planResult.getReliability() >= 60) {
                            orderStatus.setReliability(planResult.getReliability());
                            orderStatus.setEstimatedDeliveryDay(planResult.getExpectDeliveryDay());
                        }
                    }
                }
            }
        }
        //去掉合并发货没发完的子项状态 19187

        List<OrderStatusViewVO> result = new ArrayList<>();

        for (OrderStatusViewVO vo : orderStatuses) {
            List<NotifyShipmentPlanPageDto> notifyShipments = doPcoLogicCenterService.findNotifyShipment(vo.getWmOrderId());
            if (!CollectionUtils.isEmpty(notifyShipments)) {
                NotifyShipmentPlanPageDto notify = notifyShipments.get(0);
                vo.setPlanNo(notify.getPlanNo());
                vo.setPlanQty(notify.getPlanQty());
                vo.setMatchQty(notify.getMatchQty());
                vo.setOutQty(notify.getOutQty());
                vo.setHopeDate(notify.getHopeDate());
            } else {
                vo.setPlanNo("");
                vo.setPlanQty(0);
                vo.setMatchQty(0);
                vo.setOutQty(0);
            }
            result.add(vo);
        }
        // 排序 doId,invId
        result = result.stream().sorted(
                Comparator.comparing(OrderStatusViewVO::getPlanNo).thenComparing(OrderStatusViewVO::getSplitNo).thenComparing(OrderStatusViewVO::getPcoItem)
                        .thenComparing(item -> item.getInventoryId() == null ? 0 : item.getInventoryId())
        ).collect(Collectors.toList());
        //加通知发货空计划
        List<String> planNos = result.stream().map(OrderStatusViewVO::getPlanNo).distinct().collect(Collectors.toList());
        List<NotifyShipmentPlanDto> notifyShipmentPlanList = doPcoLogicCenterService.findNotifyShipmentPlanList(orderNo, orderItem.toString());
        for (NotifyShipmentPlanDto planDto : notifyShipmentPlanList) {
            if (!planNos.contains(planDto.getPlanNo())) {
                OrderStatusViewVO vo = new OrderStatusViewVO();
                vo.setPlanNo(planDto.getPlanNo());
                vo.setPlanQty(planDto.getPlanQty());
                vo.setMatchQty(0);
                vo.setOutQty(0);
                vo.setHopeDate(planDto.getHopeDate());
                result.add(0, vo);
            }
        }
        return result;
    }

    //合并相同inventoryId的状态
    public List<OrderStatusViewVO> filterOrderStatusViewList(List<OrderStatusViewVO> orderStatuses) {
        if (CollectionUtils.isEmpty(orderStatuses) || orderStatuses.size() == 1) {
            return orderStatuses;
        }
        HashMap<String, OrderStatusViewVO> map = new HashMap<>();
        for (OrderStatusViewVO orderStatus : orderStatuses) {
            String uid = String.format("%s+%s+%s+%s+%d", orderStatus.getWmOrderId(), orderStatus.getSplitNo(), orderStatus.getPcoItem(), orderStatus.getInventoryTable(), orderStatus.getInventoryId());
            if (map.containsKey(uid)) {
                OrderStatusViewVO target = map.get(uid);
                target.setQty(target.getQty() + orderStatus.getQty());
                target.setQtyIn(target.getQtyIn() + orderStatus.getQtyIn());
                target.setQtyOut(target.getQtyOut() + orderStatus.getQtyOut());
            } else {
                map.put(uid, orderStatus);
            }
        }
        return new ArrayList<>(map.values());
    }

    @Override
    public List<OrderStatusItem> findOrderStatusItemList(String orderNo, Integer orderItem) {
        OrderStatusItemExample statusEx = new OrderStatusItemExample();
        statusEx.createCriteria().andOrderIdEqualTo(orderNo).andOrderItemEqualTo(orderItem);
        List<OrderStatusItem> orderStatuses = orderStatusItemMapper.selectByExample(statusEx);
        return orderStatuses;
    }

    @Override
    public OrderStatus getOrderStatusInfoByOrderFno(String orderFno) throws Exception {
        if (StringUtils.isBlank(orderFno)) {
            throw Exceptions.OpsException("入参不可为空.");
        }
        OrderNoInfo orderNoInfo = new OrderNoInfo().convertFullOrderNo(orderFno);
        if (orderNoInfo.getSplitItem() == null) {
            orderNoInfo.setSplitItem(0);
        }
        String orderDlvEntry = orderDao.getOrderDlvEntry(orderNoInfo.getOrderNo());
        if (StringUtils.isEmpty(orderDlvEntry)) {
            throw Exceptions.OpsException("无订单数据");
        }
        if (CKTYPEEnum.NOTIFY_UNLIMIT.getCode().equals(orderDlvEntry)) {
            throw Exceptions.OpsException("通知发货请到接单查询页面转定");
        }
        String orderProdFlag = orderDao.getOrderProdFlag(orderNoInfo.getOrderNo() + "-" + orderNoInfo.getItemNo());
        if (StringUtils.isEmpty(orderProdFlag)) {
            throw Exceptions.OpsException("数据未完成，请重试");
        }
        //型号拆分
        if ("2".equals(orderProdFlag)) { // 此判断不包含子型号发货
            String doSource = orderDao.getDoSource(orderNoInfo.getOrderNo(), orderNoInfo.getItemNo());
            if (StringUtils.isNotBlank(doSource) && DoSourceEnum.ASSModelNo.getType().equals(doSource)) {
                return findOrderAssModelStatus(orderNoInfo.getOrderNo(), orderNoInfo.getItemNo(), orderNoInfo.getSplitItem());
            }
        }
        // 非拆分型号
        return findOrderNotAssModelStatus(orderNoInfo.getOrderNo(), orderNoInfo.getItemNo(), orderNoInfo.getSplitItem());
    }


    /**
     * 不要通用
     *
     * @param orderNo
     * @param orderItem
     * @param splitNo
     * @return
     */
    @Override
    public OrderStatus findOrderNotAssModelStatus(String orderNo, Integer orderItem, Integer splitNo) {
        OrderStatusExample statusEx = new OrderStatusExample();
        statusEx.createCriteria().andOrderIdEqualTo(orderNo).andOrderItemEqualTo(orderItem).andSplitNoEqualTo(splitNo);
        List<OrderStatus> orderStatuses = orderStatusMapper.selectByExample(statusEx);
        if (CollectionUtils.isNotEmpty(orderStatuses)) {
            return orderStatuses.get(0);
        }
        return null;
    }

    /**
     * 不要通用
     *
     * @param orderNo
     * @param orderItem
     * @param splitNo
     * @return
     */
    @Override
    public OrderStatus findOrderAssModelStatus(String orderNo, Integer orderItem, Integer splitNo) {
        OrderStatusExample statusEx = new OrderStatusExample();
        statusEx.createCriteria().andOrderIdEqualTo(orderNo).andOrderItemEqualTo(orderItem).andPcoItemEqualTo(splitNo);
        List<OrderStatus> orderStatuses = orderStatusMapper.selectByExample(statusEx);
        if (CollectionUtils.isNotEmpty(orderStatuses)) {
            return orderStatuses.get(0);
        }
        return null;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateOrderStatusTable(Order order, String userDto) throws OpsException {
        // 删除旧状态
        deleteOrderStatus(order.getOrderId(), order.getOrderItem());
        // 存储新状态表
        insertOrderStatus(order.getJyckStatusList(), userDto);
        // 刷新接单表数据
        customerOrderService.refreshRcvdetail(order, userDto);
    }

    @Override
    public void handleOrderStatus(Order order, String userDto) throws OpsException {
        List<JYCKStatus> JYCKStatusList = new ArrayList<>();
        for (JYCK jyck : order.getJycks()) {
            // 创建状态表数据
            JYCKStatus jyckStatus = new JYCKStatus(jyck);
            JYCKStatusList.add(jyckStatus);
            // 解析关联关系
            for (Relation relation : jyck.getRelations()) {
                RelationStatus relationStatus = null;
                if (relation instanceof OKRelation) {
                    relationStatus = new OKRelationStatus((OKRelation) relation);
                } else if (relation instanceof DBRelation) {
                    relationStatus = new DBRelationStatus((DBRelation) relation);
                } else if (relation instanceof CGRelation) {
                    relationStatus = new CGRelationStatus((CGRelation) relation);
                } else if (relation instanceof EXRelation) {
                    relationStatus = new EXRelationStatus((EXRelation) relation);
                }
                // 创建状态详情表数据
                relationStatus.createOrderStatusItem(jyck);
                // 填充状态信息
                fillStatusDescForStatusItem(jyck, relationStatus);
                jyckStatus.addRelationStatus(relationStatus);
            }
            // 计算合并状态
            // 已签收等待处理:等待类型是调拨或采购，且全都已签收，并是采购已签收等待处理或调拨已签收等待处理
            // 收货处理中:  等待类型是调拨或采购，且全都已签收,并是调拨收货处理或是采购收货处理
            // 等待货齐出库:等待类型是调拨或采购，且全都已签收,并是调拨收货处理或是采购收货处理
            if (jyck.getWaitType() == DoWaitTypeEnum.WaitCG || jyck.getWaitType() == DoWaitTypeEnum.WaitDB) {
                boolean allAfterSign = jyckStatus.getRelationStatusList().stream().allMatch(item -> item.afterSign());
                if (allAfterSign) {
                    for (RelationStatus relationStatus : jyckStatus.getRelationStatusList()) {
                        boolean signConfirm = relationStatus.getStatusItemEnum() == PURCHASE_SIGN_CONFIRM || relationStatus.getStatusItemEnum() == TRANSFER_SIGN_CONFIRM;
                        if (signConfirm) {
                            relationStatus.setStatusItemEnum(WAREHOUSE_SIGN_CONFIRM);
                        }
                        boolean goodsConfirm = relationStatus.getStatusItemEnum() == PURCHASE_GOODS_CONFIRM || relationStatus.getStatusItemEnum() == TRANSFER_GOODS_CONFIRM;
                        if (goodsConfirm) {
                            relationStatus.setStatusItemEnum(WAREHOUSE_GOODS_CONFIRM);
                        }
                        boolean receiveConfirm = relationStatus.getStatusItemEnum() == PURCHASE_RECEIVE_CONFIRM || relationStatus.getStatusItemEnum() == TRANSFER_RECEIVE_CONFIRM;
                        if (receiveConfirm) {
                            relationStatus.setStatusItemEnum(WAREHOUSE_RECEIVE_CONFIRM);
                        }
                    }
                }
            }
            for (RelationStatus relationStatus : jyckStatus.getRelationStatusList()) {
                OrderStatusItemEnum statusItemEnum = relationStatus.getStatusItemEnum();
                OrderStatusItem orderStatusItem = relationStatus.getOrderStatusItem();
                orderStatusItem.setStatus(OrderStatusItemEnum.getOrderStatus(statusItemEnum).getCode());
                orderStatusItem.setStatusDesc(statusItemEnum.getCode());
                orderStatusItem.setStatusInfo(relationStatus.getRemarkBuilder().buildAsJson());
            }
        }
        order.setJyckStatusList(JYCKStatusList);
    }

    @Override
    public void deleteOrderStatus(String orderId, Integer orderItem) {
        OrderStatusExample statusEx = new OrderStatusExample();
        statusEx.createCriteria().andOrderIdEqualTo(orderId).andOrderItemEqualTo(orderItem);
        List<OrderStatus> orderStatusList = orderStatusMapper.selectByExample(statusEx);
        if (CollectionUtils.isNotEmpty(orderStatusList)) {
            for (OrderStatus orderStatus : orderStatusList) {
                orderStatusMapper.deleteByPrimaryKey(orderStatus.getId());
            }
        }
        OrderStatusItemExample itemEx = new OrderStatusItemExample();
        itemEx.createCriteria().andOrderIdEqualTo(orderId).andOrderItemEqualTo(orderItem);
        List<OrderStatusItem> orderStatusItemList = orderStatusItemMapper.selectByExample(itemEx);
        if (CollectionUtils.isNotEmpty(orderStatusItemList)) {
            for (OrderStatusItem orderStatusItem : orderStatusItemList) {
                orderStatusItemMapper.deleteByPrimaryKey(orderStatusItem.getId());
            }
        }
    }

    private void insertOrderStatus(List<JYCKStatus> jycks, String creator) {
        for (JYCKStatus jyck : jycks) {
            jyck.getOrderStatus().setCreator(creator);
            orderStatusMapper.insertSelective(jyck.getOrderStatus());
            //bug 19187 2025-12-29 C12961
            List<OrderStatusItem> orderStatusItemList = jyck.getOrderStatusItem();
            //当整个DO发完货后，同inventoryId的OrderStatusItem合并为一条
            boolean allOut = orderStatusItemList.stream().allMatch(item -> OrderStatusEnum.Transition.getCode().equals(item.getStatus()) ||
                    OrderStatusEnum.Finish.getCode().equals(item.getStatus()) || item.getQty().equals(item.getQtyOut())
            );
            //只要jyck内有没发完的，就都不合并
            if (allOut) {
                List<OrderStatusItem> list = new ArrayList<>();
                // 按照inventoryId分组
                Map<Long, List<OrderStatusItem>> inventoryIdGroup = orderStatusItemList.stream().collect(Collectors.groupingBy(OrderStatusItem::getInventoryId));
                for (Map.Entry<Long, List<OrderStatusItem>> entry : inventoryIdGroup.entrySet()) {
                    List<OrderStatusItem> value = entry.getValue();
                    //每个inventoryId只取第一条，并合并数量
                    OrderStatusItem first = value.get(0);
                    if (value.size() > 1) {
                        for (int i = 1; i < value.size(); i++) {
                            first.setQty(first.getQty() + value.get(i).getQty());
                            first.setQtyIn(first.getQtyIn() + value.get(i).getQtyIn());
                            first.setQtyOut(first.getQtyOut() + value.get(i).getQtyOut());
                        }
                    }
                    list.add(first);
                }
                orderStatusItemList = list;
            }
            for (OrderStatusItem orderStatusItem : orderStatusItemList) {
                orderStatusItem.setCreator(creator);
                orderStatusItemMapper.insertSelective(orderStatusItem);
            }
        }
    }


    public void fillStatusDescForStatusItem(JYCK jyck, RelationStatus relationStatus) throws OpsException {
        OrderStatusItem orderStatusItem = relationStatus.getOrderStatusItem();
        OrderStatusItemEnum statusEnum = null;
        RelationStatus.RemarkBuilder remarkBuilder = relationStatus.getRemarkBuilder();
        // 采购状态
        if (relationStatus instanceof CGRelationStatus) {
            CGRelationStatus cgRelationStatus = (CGRelationStatus) relationStatus;
            CGRelation relation = cgRelationStatus.getRelation();
            PurchaseOrderDetailInfo purchaseInfo = orderPurchaseService.getPurchaseInfo(relation.getRequestNo(), relation.getRequestItemNo(), relation.getRequestSplitNo());
            remarkBuilder.set(CGRelationStatus.RemarkCode.PO_ORDER_FNO, purchaseInfo.getPoOrderFNo());
            if (StringUtils.isNotBlank(purchaseInfo.getPoOrderFNo())) {
                cgRelationStatus.getOrderStatusItem().setAssociateNo(purchaseInfo.getPoOrderFNo());
            }
            if (cgRelationStatus.getStateCode() == CGRelationStatus.StateCode.REQUEST || cgRelationStatus.getStateCode() == CGRelationStatus.StateCode.ACCEPT) {
                switch (purchaseInfo.getStateCode()) {
                    case REQUEST:// 1请购处理
                        statusEnum = OrderStatusItemEnum.REQUEST_PREPROCESS;
                        remarkBuilder.set(CGRelationStatus.RemarkCode.REPO_ORDER_FNO, relation.getRePoFullNo());
                        break;
                    case INTERCEPT:// 2请购拦截
                        statusEnum = OrderStatusItemEnum.REQUEST_INTERCEPT;
                        remarkBuilder.set(CGRelationStatus.RemarkCode.REPO_ORDER_FNO, relation.getRePoFullNo());
                        break;
                    case SEND:// 3采购已发单
                        statusEnum = OrderStatusItemEnum.PURCHASE_SEND;
                        break;
                    case RECEIVE_ERROR:// 5供应商接单异常
                        statusEnum = OrderStatusItemEnum.PURCHASE_RECEIVE_ERROR;
                        break;
                    case RECEIVE:// 4供应商已接单
                        statusEnum = OrderStatusItemEnum.PURCHASE_RECEIVED;
                        break;
                    case PRODUCT:// 6供应商生产中
                        statusEnum = OrderStatusItemEnum.PURCHASE_PRODUCTION;
                        break;
                    // 如果move为生产中，但是查询采购单状态为运输中或其他，则可能该采购单已分纳，且此库存为未分纳出的生产中库存
                    case TRANSIT:// 7供应商运输中
                        statusEnum = OrderStatusItemEnum.PURCHASE_PRODUCTION;
                        break;
                    case FINISH:// 8已完成
                        statusEnum = OrderStatusItemEnum.PURCHASE_PRODUCTION;
                        break;
                    default:
                        log.info(JSONUtil.toJsonStr(purchaseInfo));
                        throw Exceptions.OpsException("查询采购状态异常:" + purchaseInfo.getStateCode().getDesc());
                }
                remarkBuilder.set(CGRelationStatus.RemarkCode.SUPPLIER_ID, purchaseInfo.getSupplierId());
                remarkBuilder.set(CGRelationStatus.RemarkCode.SUPPLIER_ORDERNO, purchaseInfo.getSupplierOrderNo());
                String transTypeName = OPSTransTypeEnum.getNameByCode(purchaseInfo.getTransType());
                remarkBuilder.set(CGRelationStatus.RemarkCode.TRANS_TYPE, transTypeName);
                remarkBuilder.set(CGRelationStatus.RemarkCode.EXPORT_DATE, purchaseInfo.getExportDate());
                remarkBuilder.set(CGRelationStatus.RemarkCode.INTERCEPTION_REASON, purchaseInfo.getInterceptionReason());
                remarkBuilder.set(CGRelationStatus.RemarkCode.SEND_DATE, purchaseInfo.getSendDate());
                remarkBuilder.set(CGRelationStatus.RemarkCode.STOCK_TYPE, purchaseInfo.getStockType());
                remarkBuilder.set(CGRelationStatus.RemarkCode.REPLY_EXPORT_DATE, purchaseInfo.getReplyExportDate());
                if (purchaseInfo.getReplyExportDate() == null) {
                    if (StringUtils.isNotBlank(purchaseInfo.getSrcdeliverytime())) {
                        String remark = purchaseInfo.getSrcdeliverytime();
                        if (StringUtils.isNotBlank(purchaseInfo.getSrcdeliverytimedesc())) {
                            remark = String.format("%s(%s)", purchaseInfo.getSrcdeliverytime(), purchaseInfo.getSrcdeliverytimedesc());
                        }
                        remarkBuilder.set(CGRelationStatus.RemarkCode.SRC_REPLY_EXPORT_DATE, remark);
                    }
                }
                String facTransTypeName = OPSTransTypeEnum.getNameByCode(purchaseInfo.getFacTransType());
                remarkBuilder.set(CGRelationStatus.RemarkCode.FAC_TRANS_TYPE, facTransTypeName);
                remarkBuilder.set(CGRelationStatus.RemarkCode.HOLON, purchaseInfo.getHolon());
                remarkBuilder.set(CGRelationStatus.RemarkCode.FAC_PROD_DATE, purchaseInfo.getFacProdDate());
            }
            if (cgRelationStatus.getStateCode() == CGRelationStatus.StateCode.DELIVERY) {
                PurchaseInvoiceDetailInfo invoiceInfo = orderPurchaseService.getInvoiceInfo(relation.getInvoiceNo(), relation.getInvoiceId());
                switch (invoiceInfo.getStateCode()) {
                    case CUSTOMS:// 2已到港报关中
                        statusEnum = OrderStatusItemEnum.PURCHASE_CUSTOMS;
                        break;
                    case DELIVERY:// 1已发运
                        statusEnum = OrderStatusItemEnum.PURCHASE_TRANSIT;
                        remarkBuilder.set(CGRelationStatus.RemarkCode.FAC_EXP_DATE, invoiceInfo.getShipDate());
                        break;
                    case ARRIVE: // 3已到货 此已到货为已发票入库（发票确认）
                        statusEnum = OrderStatusItemEnum.PURCHASE_INVOICE_CONFIRM;
                        break;
                    case OTHER:// 0
                    default:
                        log.info(JSONUtil.toJsonStr(invoiceInfo));
                        throw Exceptions.OpsException("查询采购状态异常:" + invoiceInfo.getStateCode().getDesc());
                }
                remarkBuilder.set(CGRelationStatus.RemarkCode.SUPPLIER_ID, invoiceInfo.getSupplierId());
                remarkBuilder.set(CGRelationStatus.RemarkCode.INVOICE_NO, invoiceInfo.getInvoiceNo());
                if (invoiceInfo.getInvoiceId() != null) {
                    remarkBuilder.set(CGRelationStatus.RemarkCode.INVOICE_ID, invoiceInfo.getInvoiceId().toString());
                }
                //值设置为空，前端不显示 bugid:15666 c14717 20250325 转成上下午
                if (Objects.nonNull(invoiceInfo.getEsArrivalDate())) {
                    // yyyy-MM-dd 上午、下午
                    String s = DateUtil.dateToDateStringAndMsg(invoiceInfo.getEsArrivalDate(), 12, " 下午", " 上午");
                    remarkBuilder.set(CGRelationStatus.RemarkCode.ES_ARRIVAL_DATE, s);
                }
                if (Objects.nonNull(invoiceInfo.getReceiveDate())) {//到场日期 加*
                    // yyyy-MM-dd 上午、下午
                    String s = "*" + DateUtil.dateToDateStringAndMsg(invoiceInfo.getReceiveDate(), 12, " 下午", " 上午");
                    remarkBuilder.set(CGRelationStatus.RemarkCode.ES_ARRIVAL_DATE, s);
                }
                remarkBuilder.set(CGRelationStatus.RemarkCode.FAC_EXP_PROT_DATE, invoiceInfo.getFacProtDate());
                remarkBuilder.set(CGRelationStatus.RemarkCode.CUSTOMS_DATE, invoiceInfo.getCustomsDate());
                remarkBuilder.set(CGRelationStatus.RemarkCode.ES_ARRIVAL_WAREHOUSE, invoiceInfo.getWarehouseCode());
                remarkBuilder.set(CGRelationStatus.RemarkCode.TRANSFER_LOGISTICS_STATUS, invoiceInfo.getTransferLogisticsStatus());
            }
            if (cgRelationStatus.getStateCode() == CGRelationStatus.StateCode.SIGNED) {
                OptStatusEnum optStatus = relation.getOptStatus();
                remarkBuilder.set(CGRelationStatus.RemarkCode.INVOICE_NO, relation.getInvoiceNo());
                remarkBuilder.set(CGRelationStatus.RemarkCode.SIGN_WAREHOUSE, relation.getWarehouseCode());
                switch (optStatus) {
                    case INVOICE_SIGN:
                        statusEnum = PURCHASE_SIGN_CONFIRM;
                        Date signTime = getSignTime(relation.getInventoryId(), relation.getInvoiceNo(), relation.getInvoiceId());
                        remarkBuilder.set(CGRelationStatus.RemarkCode.SIGN_TIME, signTime);
                        // remarkBuilder.set(CGRelationStatus.RemarkCode.SIGN_TIME, relation.getOptTime());
                        break;
                    case GOODS_CONFIRM:
                        statusEnum = OrderStatusItemEnum.PURCHASE_GOODS_CONFIRM;
                        remarkBuilder.set(CGRelationStatus.RemarkCode.GOODS_CONFIRM_TIME, relation.getOptTime());
                        // remarkBuilder.set(CGRelationStatus.RemarkCode.GOODS_CONFIRM_TIME, relation.getOptTime());
                        break;
                }
            }
            if (statusEnum == null) {
                throw Exceptions.OpsException("没有查询到采购单状态");
            }
            remarkBuilder.set(OKRelationStatus.RemarkCode.DLV_DATE, jyck.getJyckDo().getWlDate());
        }
        // 调拨状态
        else if (relationStatus instanceof DBRelationStatus) {
            DBRelationStatus dbRelationStatus = (DBRelationStatus) relationStatus;
            // 未下发
            DBRelation dbRelation = dbRelationStatus.getRelation();
            if (dbRelationStatus.getStateCode() == DBRelationStatus.StateCode.READY) {
                if (jyck.getWaitType() == DoWaitTypeEnum.WaitCG) {
                    // 采购等待货齐出库
                    statusEnum = OrderStatusItemEnum.PURCHASE_RECEIVE_CONFIRM;
                    remarkBuilder.set(CGRelationStatus.RemarkCode.RECEIVE_WAREHOUSE, dbRelation.getFromWarehouseCode());
                } else {
                    // 就绪待下发仓库
                    statusEnum = OrderStatusItemEnum.TRANSFER_TASK_WAITING;
                    remarkBuilder.set(DBRelationStatus.RemarkCode.DELIVERY_WAREHOUSE, dbRelation.getFromWarehouseCode());
                }
                remarkBuilder.set(DBRelationStatus.RemarkCode.DELIVERY_DATE, dbRelation.getDbDo().getWlDate());
            }
            // 已下发
            else if (dbRelationStatus.getStateCode() == DBRelationStatus.StateCode.SEND) {
                remarkBuilder.set(DBRelationStatus.RemarkCode.DELIVERY_WAREHOUSE, dbRelation.getFromWarehouseCode());
                // 已下发
                if (dbRelationStatus.isTaskSend()) {
                    statusEnum = OrderStatusItemEnum.TRANSFER_TASK_SEND;
                }
                // 已组波次
                else if (dbRelationStatus.isJoinWave()) {
                    statusEnum = OrderStatusItemEnum.TRANSFER_WAVE_CONFIRM;
                    // 如果wms预计出库时间没有，则取组波次时间
                    Date date = dbRelation.getDbDo().getWmsExpectedDeliveryDate() == null ? dbRelation.getDbDo().getWaveTime() : dbRelation.getDbDo().getWmsExpectedDeliveryDate();
                    remarkBuilder.set(DBRelationStatus.RemarkCode.ES_DELIVERY_DATE, date);
                }
                // 已拣货
                else if (dbRelationStatus.isPackage()) {
                    statusEnum = OrderStatusItemEnum.TRANSFER_PICKING;
                }
                remarkBuilder.set(DBRelationStatus.RemarkCode.DELIVERY_DATE, dbRelation.getDbDo().getWlDate());
            }
            // 已发货
            else if (dbRelationStatus.getStateCode() == DBRelationStatus.StateCode.DELIVERY) {
                statusEnum = OrderStatusItemEnum.TRANSFER_DELIVERY;
                remarkBuilder.set(DBRelationStatus.RemarkCode.DELIVERY_WAREHOUSE, dbRelation.getFromWarehouseCode());
                remarkBuilder.set(DBRelationStatus.RemarkCode.RECEIVE_WAREHOUSE, dbRelation.getToWarehouseCode());
                // 查询handover,获取承运商，运单号，和发票号
                String invoiceNo = dbRelation.getInvoiceNo();
                String dbId = dbRelation.getDbDo().getDoId();
                if (invoiceNo != null) {
                    List<OpsHandover> handOvers = opsHandOverService.findHandOvers(dbId, invoiceNo);
                    if (CollectionUtils.isNotEmpty(handOvers)) {
                        OpsHandover opsHandover = handOvers.get(0);
                        statusEnum = OrderStatusItemEnum.TRANSFER_TRANSIT;
                        remarkBuilder.set(DBRelationStatus.RemarkCode.INVOICE_NO, invoiceNo);
                        remarkBuilder.set(DBRelationStatus.RemarkCode.EXPRESS_COMPANY, opsHandover.getLogisticscode());
                        remarkBuilder.set(DBRelationStatus.RemarkCode.EXPRESS_NO, opsHandover.getExpresscode());
                    } else {
                        throw Exceptions.OpsException("缺少OpsHandOver数据");
                    }
                }
            }
            // 已签收
            else if (dbRelationStatus.getStateCode() == DBRelationStatus.StateCode.SIGNED) {
                remarkBuilder.set(DBRelationStatus.RemarkCode.RECEIVE_WAREHOUSE, dbRelation.getToWarehouseCode());
                DBRelation relation = dbRelationStatus.getRelation();
                if (dbRelation.getOptStatus() == OptStatusEnum.INVOICE_SIGN) {
                    statusEnum = OrderStatusItemEnum.TRANSFER_SIGN_CONFIRM;
                    // 要求不用optTime
                    Date signTime = getSignTime(relation.getInventoryId(), relation.getInvoiceNo(), relation.getInvoiceId());
                    remarkBuilder.set(DBRelationStatus.RemarkCode.SIGN_TIME, signTime);
                    // remarkBuilder.set(DBRelationStatus.RemarkCode.SIGN_TIME, dbRelation.getOptTime());
                } else if (dbRelation.getOptStatus() == OptStatusEnum.GOODS_CONFIRM) {
                    statusEnum = OrderStatusItemEnum.TRANSFER_GOODS_CONFIRM;
                    // 要求不用optTime
                    Date goodsConfirmTime = getGoodsConfirmTime(relation.getInventoryId(), relation.getInvoiceNo(), relation.getInvoiceId());
                    remarkBuilder.set(DBRelationStatus.RemarkCode.RECEIVE_TIME, goodsConfirmTime);
                    // remarkBuilder.setDateTime(DBRelationStatus.RemarkCode.RECEIVE_TIME, dbRelation.getOptTime());
                }
                remarkBuilder.set(DBRelationStatus.RemarkCode.INVOICE_NO, dbRelation.getInvoiceNo());
            }
        }
        // 货齐状态
        else if (relationStatus instanceof OKRelationStatus) {
            OKRelationStatus okRelationStatus = (OKRelationStatus) relationStatus;
            // 已货齐
            OKRelation okRelation = okRelationStatus.getRelation();
            if (okRelationStatus.getStateCode() == OKRelationStatus.StateCode.READY) {
                // 采购等待货齐出库
                if (jyck.getWaitType() == DoWaitTypeEnum.WaitCG) {
                    statusEnum = OrderStatusItemEnum.PURCHASE_RECEIVE_CONFIRM;
                    remarkBuilder.set(CGRelationStatus.RemarkCode.RECEIVE_WAREHOUSE, okRelation.getWarehouseCode());
                }
                // 调拨等待货齐出库
                else if (jyck.getWaitType() == DoWaitTypeEnum.WaitDB) {
                    statusEnum = OrderStatusItemEnum.TRANSFER_RECEIVE_CONFIRM;
                    remarkBuilder.set(DBRelationStatus.RemarkCode.RECEIVE_WAREHOUSE, okRelation.getWarehouseCode());
                }
                // 就绪待下发仓库
                else {
                    statusEnum = OrderStatusItemEnum.WAREHOUSE_TASK_WAITING;
                    remarkBuilder.set(OKRelationStatus.RemarkCode.DELIVERY_WAREHOUSE, okRelation.getWarehouseCode());
                }
                remarkBuilder.set(OKRelationStatus.RemarkCode.DLV_DATE, jyck.getJyckDo().getWlDate());
            }
            // 已下发
            else if (okRelationStatus.getStateCode() == OKRelationStatus.StateCode.SEND) {
                remarkBuilder.set(OKRelationStatus.RemarkCode.DELIVERY_WAREHOUSE, okRelation.getWarehouseCode());
                // 指令已下发
                if (jyck.getWmsStatus() == null || WmStatusEnum.SENT_TASK.getCode().equals(jyck.getWmsStatus())) {
                    // 信用拦截检测
                    boolean intercept = opsCustomerOrderService.checkCredit(jyck.getOrderId(), jyck.getOrderItem().toString());
                    statusEnum = !intercept ? OrderStatusItemEnum.WAREHOUSE_TASK_SEND : OrderStatusItemEnum.WAREHOUSE_INTERCEPTION;
                    if (!intercept) {
                        remarkBuilder.set(OKRelationStatus.RemarkCode.DELIVERY_WAREHOUSE, okRelation.getWarehouseCode());
                        remarkBuilder.set(OKRelationStatus.RemarkCode.SEND_TIME, jyck.getWmsTime());
                    }
                }
                // 已组波次
                else if (WmStatusEnum.GROUP_BATCH.getCode().equals(jyck.getWmsStatus())) {
                    statusEnum = OrderStatusItemEnum.WAREHOUSE_WAVE_CONFIRM;
                    remarkBuilder.set(OKRelationStatus.RemarkCode.DELIVERY_WAREHOUSE, okRelation.getWarehouseCode());
                    remarkBuilder.set(OKRelationStatus.RemarkCode.WAVE_TIME, jyck.getWaveTime());
                }
                // 拣货中
                else if (WmStatusEnum.PICK.getCode().equals(jyck.getWmsStatus())
                        || WmStatusEnum.PACKAGE.getCode().equals(jyck.getWmsStatus())
                        || WmStatusEnum.DELIVERED.getCode().equals(jyck.getWmsStatus())) {
                    statusEnum = OrderStatusItemEnum.WAREHOUSE_PICKING;
                    remarkBuilder.set(OKRelationStatus.RemarkCode.DELIVERY_WAREHOUSE, okRelation.getWarehouseCode());
                    // remarkBuilder.set(OKRelationStatus.RemarkCode.PACK_TIME, null);
                }
                remarkBuilder.set(OKRelationStatus.RemarkCode.DLV_DATE, jyck.getJyckDo().getWlDate());
            }
            // 已发货
            else if (okRelationStatus.getStateCode() == OKRelationStatus.StateCode.DELIVERY || okRelationStatus.getStateCode() == OKRelationStatus.StateCode.SIGNED) {
                TmsTrackingResult route = getJYCKTransportStatus(okRelation.getAssociateNo());
                if (route != null) {
                    remarkBuilder.set(OKRelationStatus.RemarkCode.EXPRESS_COMPANY, route.getCarrierName());
                    remarkBuilder.set(OKRelationStatus.RemarkCode.EXPRESS_NO, route.getExpressNo());
                    // 0无,1已揽收,2运输中,3派件中,4已签收
                    switch (route.getStateCode()) {
                        case 0:
                            statusEnum = OrderStatusItemEnum.WAREHOUSE_DELIVERY;
                            remarkBuilder.set(OKRelationStatus.RemarkCode.DELIVERY_WAREHOUSE, okRelation.getWarehouseCode());
                            break;
                        case 1:
                            statusEnum = OrderStatusItemEnum.TRANSPORT_COLLECTION;
                            remarkBuilder.set(OKRelationStatus.RemarkCode.COLLECTION_TIME, route.getActionTime());
                            break;
                        case 2:
                            statusEnum = OrderStatusItemEnum.TRANSPORT_TRANSIT;
                            remarkBuilder.set(OKRelationStatus.RemarkCode.EXPRESS_TIME, route.getActionTime());
                            break;
                        case 3:
                            statusEnum = OrderStatusItemEnum.TRANSPORT_SENDING;
                            remarkBuilder.set(OKRelationStatus.RemarkCode.EXPRESS_TIME, route.getActionTime());
                            break;
                        case 4:
                            statusEnum = OrderStatusItemEnum.TRANSPORT_DELIVERED;
                            remarkBuilder.set(OKRelationStatus.RemarkCode.SIGN_TIME, route.getActionTime());
                            break;
                        default:
                            statusEnum = OrderStatusItemEnum.WAREHOUSE_DELIVERY;
                            remarkBuilder.set(OKRelationStatus.RemarkCode.DELIVERY_WAREHOUSE, okRelation.getWarehouseCode());
                            break;
                    }
                } else {
                    statusEnum = OrderStatusItemEnum.WAREHOUSE_DELIVERY;
                    remarkBuilder.set(OKRelationStatus.RemarkCode.DELIVERY_WAREHOUSE, okRelation.getWarehouseCode());
                    remarkBuilder.set(OKRelationStatus.RemarkCode.WM_ORDER_ID, okRelation.getAssociateNo());
                }
                //16340 判断do的承运商是否为空，并翻译
                if (!remarkBuilder.containsKey(OKRelationStatus.RemarkCode.EXPRESS_COMPANY.getDesc())) {
                    String carried = jyck.getJyckDo().getCarried();
                    if (StringUtils.isNotBlank(carried)) {
                        String carriedName = CarrierEnum.getNameByCode(carried);
                        remarkBuilder.set(OKRelationStatus.RemarkCode.EXPRESS_COMPANY, carriedName);
                    }
                }
            }
        }
        // 异常状态
        else if (relationStatus instanceof EXRelationStatus) {
            EXRelationStatus eXRelation = (EXRelationStatus) relationStatus;
            statusEnum = OrderStatusItemEnum.WAREHOUSE_TASK_WAITING;
            if (eXRelation.getRelation().getInvStatus() == InventoryStatusEnum.X) {
                statusEnum = OrderStatusItemEnum.EXCEPTION;
            } else if (eXRelation.getRelation().getInvStatus() == null) {
                statusEnum = OrderStatusItemEnum.CANCEL;
            }
        }
        relationStatus.setStatusItemEnum(statusEnum);
    }

    private Date getSignTime(Long inventoryId, String invoiceNo, Long invoiceId) throws OpsException {
        List<OpsRoItemInventory> roItemInventoryList = baseRoService.findRoItemInventoryByInventoryId(inventoryId);
        for (OpsRoItemInventory opsRoItemInventory : roItemInventoryList) {
            OpsRo ro = baseRoService.getRoByRoId(opsRoItemInventory.getRoId());
            if (ro != null) {
                if (ro.getIsSign() && StringUtils.equals(invoiceNo, ro.getInvoiceNo()) && !ObjectUtils.notEqual(invoiceId, ro.getInvoiceId())) {
                    return ro.getSignTime();
                }
            }
        }
        return null;
    }


    private Date getGoodsConfirmTime(Long inventoryId, String invoiceNo, Long invoiceId) throws OpsException {
        List<OpsRoItemInventory> roItemInventoryList = baseRoService.findRoItemInventoryByInventoryId(inventoryId);
        for (OpsRoItemInventory opsRoItemInventory : roItemInventoryList) {
            OpsRo ro = baseRoService.getRoByRoId(opsRoItemInventory.getRoId());
            if (ro != null) {
                List<OpsRoConfirmLog> roConfirmLogList = roConfirmLogService.getRoConfirmLogsByInvoiceId(invoiceId, invoiceNo);
                if (CollectionUtils.isNotEmpty(roConfirmLogList)) {
                    OpsRoConfirmLog opsRoConfirmLog = roConfirmLogList.stream().filter(log -> ro.getRoId().equals(log.getRoId())).findFirst().orElse(null);
                    if (opsRoConfirmLog != null) {
                        return opsRoConfirmLog.getCreTime();
                    }
                }
            }
        }
        return null;
    }

    private TmsTrackingResult getJYCKTransportStatus(String doId) {
        //1.先判断expdetail是否签收
        List<Expdetail> expdetails = expdetailService.getExpdetails(doId);
        if (CollectionUtils.isEmpty(expdetails)) {
            return null;
        }
        Expdetail expdetail = expdetails.get(0);
        if (expdetail.getSignStatus().equals(Short.valueOf("2"))) {//如果2已签收，则返回4已签收
            TmsTrackingResult trackingResult = new TmsTrackingResult();
            trackingResult.setStateCode(4);
            trackingResult.setCarrierId(expdetail.getExpressCompany());
            trackingResult.setActionTime(expdetail.getSignTime());
            trackingResult.setExpressNo(expdetail.getExpressNo());
            return trackingResult;
        }
        // 2.再通过运单号查询TMS路由信息
        String expressNo = expdetail.getExpressNo();
        if (StringUtils.isBlank(expressNo)) {
            return null;
        }
        TmsTrackingResult orderRoute = tmsRouteHandler.getOrderRoute(expressNo);
        if (orderRoute == null) {
            return null;
        }
        return orderRoute;
    }

}
