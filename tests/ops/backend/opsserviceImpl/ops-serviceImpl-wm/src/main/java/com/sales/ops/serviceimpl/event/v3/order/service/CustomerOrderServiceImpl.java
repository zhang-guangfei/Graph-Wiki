package com.sales.ops.serviceimpl.event.v3.order.service;

import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.db.dao.OrderStatusItemMapper;
import com.sales.ops.db.dao.OrderStatusMapper;
import com.sales.ops.db.entity.*;
import com.sales.ops.dto.order.OpsInvoiceResDto;
import com.sales.ops.enums.OrderSplitTypeEnum;
import com.sales.ops.enums.RcvOrderStatusEnum;
import com.sales.ops.service.order.BaseCustomerOrderService;
import com.sales.ops.service.order.OpsCustomerOrderService;
import com.sales.ops.service.wmOrder.ExpdetailService;
import com.sales.ops.serviceimpl.dispatch.dodispatch.service.TMSRouteHandler;
import com.sales.ops.serviceimpl.event.v3.order.entity.JYCK;
import com.sales.ops.serviceimpl.event.v3.order.entity.Order;
import com.sales.ops.serviceimpl.event.v3.status.enums.OrderStatusEnum;
import com.sales.ops.serviceimpl.event.v3.status.enums.OrderStatusItemEnum;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class CustomerOrderServiceImpl implements CustomerOrderService {

    private BaseCustomerOrderService baseCustomerOrderService;
    private TMSRouteHandler tmsRouteHandler;
    private ExpdetailService expdetailService;
    private OrderStatusItemMapper orderStatusItemMapper;
    private OrderStatusMapper orderStatusMapper;
    private OpsCustomerOrderService opsCustomerOrderService;
    private RcvdetailService rcvdetailService;

    @Override
    public void refreshRcvdetail(Order order, String userDto) throws OpsException {
        List<OrderStatusItem> orderStatusItemList = findOrderStatusItemList(order.getOrderId(), order.getOrderItem());
        List<OrderStatus> orderStatusList = findOrderStatusList(order.getOrderId(), order.getOrderItem());
        // 订单状态 出库区分
        getRcvStatus(order, orderStatusItemList);
        // 货齐数 货齐时间 组波次时间 （status后，总是更新，有则不更新，总是更新）
        getReadyInfo(order, orderStatusList);
        // 信用拦截 拦截时间
        getInterceptInfo(order, orderStatusItemList);
        // 已发数 发货时间
        // 承运商 运单号 承运商交接时间
        getExpInfo(order, orderStatusList);
        // 退货数 开票数 开票时间
        getInvoiceInfo(order, orderStatusItemList);
        // 更新
        Rcvdetail rcvdetail = baseCustomerOrderService.findRcvDetailByNo(order.getOrderId(), order.getOrderItem());
        Rcvdetail update = createUpdater(order, rcvdetail, userDto);
        rcvdetailService.updateRcvdetail(order.getOrderId(), order.getOrderItem(), update);
    }


    public List<OrderStatusItem> findOrderStatusItemList(String orderNo, Integer orderItem) {
        OrderStatusItemExample statusEx = new OrderStatusItemExample();
        statusEx.createCriteria().andOrderIdEqualTo(orderNo).andOrderItemEqualTo(orderItem);
        List<OrderStatusItem> orderStatuses = orderStatusItemMapper.selectByExample(statusEx);
        return orderStatuses;
    }

    public List<OrderStatus> findOrderStatusList(String orderNo, Integer orderItem) {
        OrderStatusExample statusEx = new OrderStatusExample();
        statusEx.createCriteria().andOrderIdEqualTo(orderNo).andOrderItemEqualTo(orderItem);
        List<OrderStatus> orderStatuses = orderStatusMapper.selectByExample(statusEx);
        return orderStatuses;
    }

    // 参数为source和target，类型相同且为泛型T，如果source为空，则返回空，如果不为空，则和target对比，如果不相同，则返回source,如果相同，则返回空
    public <T> T needUpdate(T source, T target) {
        // 检查source是否为空
        if (source == null) {
            return null;
        }

        // 比较source和target是否相等
        if (!source.equals(target)) {
            return source;
        }

        // 如果source不为空且等于target，则返回null
        return null;
    }

    private Rcvdetail createUpdater(Order order, Rcvdetail rcvdetail, String userDto) {
        Rcvdetail update = new Rcvdetail();
        update.setStatus(needUpdate(order.getStatus(), rcvdetail.getStatus()));
        //update.setStockType(needUpdate(order.getStockType(),rcvdetail.getStockType()));
        //update.setStockCode(needUpdate(order.getStockCode(),rcvdetail.getStockCode()));
        //update.setInventoryTypeCode(needUpdate(order.getInventoryType(),rcvdetail.getInventoryTypeCode()));
        //update.setAllotTime(needUpdate(order.getAllotTime(),rcvdetail.getAllotTime()));
        //update.setProdFlag(needUpdate(order.getProdFlag(),rcvdetail.getProdFlag()));
        update.setReadyQty(needUpdate(order.getReadyQty(), rcvdetail.getReadyQty()));
        //update.setReadyTime(order.getReadyTime());
        update.setExpectedDeliveryTime(needUpdate(order.getEsDeliveryTime(), rcvdetail.getExpectedDeliveryTime()));
        update.setIntercept(needUpdate(order.getIntercept(), rcvdetail.getIntercept()));
        update.setInterceptTime(needUpdate(order.getInterceptTime(), rcvdetail.getInterceptTime()));
        update.setExpQty(needUpdate(order.getExpQty(), rcvdetail.getExpQty()));
        update.setExpTime(needUpdate(order.getExpTime(), rcvdetail.getExpTime()));
        update.setCarrierid(needUpdate(order.getCarrierId(), rcvdetail.getCarrierid()));
        update.setExpressno(needUpdate(order.getExpressNo(), rcvdetail.getExpressno()));
        update.setShipTime(needUpdate(order.getShipTime(), rcvdetail.getShipTime()));
        update.setReturnedQty(needUpdate(order.getReturnedQty(), rcvdetail.getReturnedQty()));
        update.setInvoiceQty(needUpdate(order.getInvoicedQty(), rcvdetail.getInvoiceQty()));
        update.setInvoiceTime(needUpdate(order.getInvoiceTime(), rcvdetail.getInvoiceTime()));
        update.setUpdateUser(userDto);
        return update;
    }


    //子项状态中存在信用拦截状态
    public boolean existIntercept(List<OrderStatusItem> list) throws Exception {
        //    WAREHOUSE_INTERCEPTION
        boolean exist = list.stream().anyMatch(item -> StringUtils.equals(item.getStatusDesc(), OrderStatusItemEnum.WAREHOUSE_INTERCEPTION.getCode()));
        return exist;
    }

    private void getInterceptInfo(Order order, List<OrderStatusItem> list) {
        // 1拦截 0不拦截
        Boolean intercept = null;
        try {
            intercept = existIntercept(list);
        } catch (Exception e) {
            log.error("信用拦截接口查询异常:{}", e.getMessage());
        }
        if (intercept != null) {
            // 0->0,不更新时间，其他都更新时间0->1,1->0,1->1
            if (order.getIntercept() || intercept) {
                order.setInterceptTime(new Date());
            }
            // 更新标识
            order.setIntercept(intercept);
        }
    }

    private void getReadyInfo(Order order, List<OrderStatus> orderStatusList) {
        if (CollectionUtils.isEmpty(orderStatusList)) {
            return;
        }
        //分析拆分方式
        String splitType = null;
        if (orderStatusList.size() == 1) {
            splitType = OrderSplitTypeEnum.ALL.getSplitType();
        } else {
            boolean assModelNo = orderStatusList.stream().map(item -> item.getModelno()).distinct().count() > 1;
            splitType = assModelNo ? OrderSplitTypeEnum.ASSModelNo.getSplitType() : OrderSplitTypeEnum.ASSQTY.getSplitType();
        }

        //不是型号拆分
        if (OrderSplitTypeEnum.notAssModel(splitType)) {
            Integer readyQty = orderStatusList.stream().map(item -> item.getQtyIn()).reduce(Integer::sum).orElse(0);
            order.setReadyQty(readyQty);
        }
        //是型号拆分
        else {
            boolean ready = orderStatusList.stream().allMatch(item -> item.getQtyIn() >= item.getQty());
            order.setReadyQty(ready ? order.getQuantity() : 0);
            if(!ready){
                int readyQty = getReadyQty(order.getQuantity(), orderStatusList);
                order.setReadyQty(readyQty);
            }

        }
        // 组波次时间
        Date maxWaveTime = order.getJycks().stream().filter(item -> item.getWaveTime() != null).map(JYCK::getWaveTime).max(Date::compareTo).orElse(null);
        order.setEsDeliveryTime(maxWaveTime);
    }

    private int getReadyQty(int allQty, List<OrderStatus> orderStatusList) {
        //<Modelno,[Qty,QtyIn,QtyInInterger]>
        Map<String, List<Integer>> map = new HashMap<>();
        for (OrderStatus orderStatus : orderStatusList) {
            if (!map.containsKey(orderStatus.getModelno())) {
                List<Integer> qtyList = new ArrayList<>();
                qtyList.add(orderStatus.getQty());
                qtyList.add(orderStatus.getQtyIn());
                map.put(orderStatus.getModelno(), qtyList);
            } else {
                List<Integer> qtyList = map.get(orderStatus.getModelno());
                qtyList.set(0, orderStatus.getQty() + qtyList.get(0));
                qtyList.set(1, orderStatus.getQtyIn() + qtyList.get(1));
                map.put(orderStatus.getModelno(), qtyList);
            }
        }
        for (List<Integer> list : map.values()) {
            //求货齐套数
            int i = allQty * list.get(1) / list.get(0);
            list.add(2, i);
        }
        int readyQty = map.values().stream().mapToInt(item -> item.get(2)).min().orElse(0);
        return readyQty;
    }

    private int getExpQty(int allQty, List<OrderStatus> orderStatusList) {
        //<Modelno,[Qty,QtyOut,QtyOutInteger]>
        Map<String, List<Integer>> map = new HashMap<>();
        for (OrderStatus orderStatus : orderStatusList) {
            if (!map.containsKey(orderStatus.getModelno())) {
                List<Integer> qtyList = new ArrayList<>();
                qtyList.add(orderStatus.getQty());
                qtyList.add(orderStatus.getQtyOut());
                map.put(orderStatus.getModelno(), qtyList);
            } else {
                List<Integer> qtyList = map.get(orderStatus.getModelno());
                qtyList.set(0, orderStatus.getQty() + qtyList.get(0));
                qtyList.set(1, orderStatus.getQtyOut() + qtyList.get(1));
                map.put(orderStatus.getModelno(), qtyList);
            }
        }
        for (List<Integer> list : map.values()) {
            int i = allQty * list.get(1) / list.get(0);
            list.add(2, i);
        }
        int expQty = map.values().stream().mapToInt(item -> item.get(2)).min().orElse(0);
        return expQty;
    }


    private void getRcvStatus(Order order, List<OrderStatusItem> list) {
        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        List<OrderStatusItemEnum> statusEnums = list.stream().map(item -> OrderStatusItemEnum.getEnumByCode(item.getStatusDesc())).collect(Collectors.toList());
        OrderStatusItemEnum minIndexEnum = Collections.min(statusEnums, Comparator.comparing(OrderStatusItemEnum::getIndex));
        OrderStatusEnum statusEnum = OrderStatusItemEnum.getOrderStatus(minIndexEnum);
        RcvOrderStatusEnum rcvOrderStatusEnum = RcvOrderStatusEnum.INIT;
        switch (statusEnum) {
            case Request:
                rcvOrderStatusEnum = RcvOrderStatusEnum.CGING;
                break;
            case RequestIntercept:
                rcvOrderStatusEnum = RcvOrderStatusEnum.INTCP;
                break;
            case Purchase:
                rcvOrderStatusEnum = RcvOrderStatusEnum.CGING;
                break;
            case Transfer:
                rcvOrderStatusEnum = RcvOrderStatusEnum.DBING;
                break;
            case WMReceive:
                rcvOrderStatusEnum = RcvOrderStatusEnum.WAITCK;
                break;
            case WMTask:// 等待出库
                rcvOrderStatusEnum = RcvOrderStatusEnum.WAITCK;
                break;
            case WMDelivery:// 出库处理
                rcvOrderStatusEnum = RcvOrderStatusEnum.CKING;
                break;
            case Transition:// 出库处理
                rcvOrderStatusEnum = RcvOrderStatusEnum.CKING;
                break;
            case Finish:// 出库处理
                rcvOrderStatusEnum = RcvOrderStatusEnum.CKING;
                break;
            case Exception:
                rcvOrderStatusEnum = RcvOrderStatusEnum.RESOLVE;
                break;
            case CANCEL:
                rcvOrderStatusEnum = RcvOrderStatusEnum.CANCEL;
                break;
        }
        order.setOrderStatusEnum(rcvOrderStatusEnum);
        order.setStatus(rcvOrderStatusEnum.getType());
    }


    private void getExpInfo(Order order, List<OrderStatus> orderStatusList) {
        if (CollectionUtils.isEmpty(orderStatusList)) {
            return;
        }
        //只要子项中有发货数
        boolean anyItemOut = orderStatusList.stream().anyMatch(item -> item.getQtyOut() > 0);
        if (anyItemOut) {
            // 1.计算rcv的已发数
            Integer expQty = 0;
            if (OrderSplitTypeEnum.notAssModel(order.getProdFlag())) {
                expQty = orderStatusList.stream().map(OrderStatus::getQtyOut).reduce(Integer::sum).orElse(0);
            } else {
                boolean exp = orderStatusList.stream().allMatch(item -> item.getQtyOut() >= item.getQty());
                expQty = exp ? order.getQuantity() : 0;
                if (!exp) {
                    expQty = getExpQty(order.getQuantity(), orderStatusList);
                }
            }
            // 如果已发数变了，则更新发货时间
            if (!Objects.equals(order.getExpQty(), expQty)) {
                order.setExpQty(expQty);
                order.setExpTime(new Date());
            }
            // 2.查询承运商和运单号
            Expdetail expdetail = expdetailService.getSihpTimeByOrderFno(order.getOrderFno());
            if (expdetail != null) {
                order.setCarrierId(expdetail.getExpressCompany());
                order.setExpressNo(expdetail.getExpressNo());
            }
            //当已发数满了，rcv状态为已发货，
            if (order.getQuantity().equals(expQty)) {
                order.setOrderStatusEnum(RcvOrderStatusEnum.CKED);
                order.setStatus(RcvOrderStatusEnum.CKED.getType());
                //更新shipTime为expdetail的shipTime最晚值，只有shipTime为空时才更新，一但有值则不更新 15998
                if (order.getShipTime() == null && expdetail != null) {
                    order.setShipTime(expdetail.getShipDate());
                }
            }
            //此if不能有else指定为CKING，因为订单子项可能存在采购的情况，此时状态应为最小值
        }
    }

    private void getInvoiceInfo(Order order, List<OrderStatusItem> orderStatusItemList) {
        //do有已发数或者expdetail存在invoice_flag=1时，需要查询发票接口更新开票数 15809
        //有已发数后，就需要查询开票信息
        boolean enableSearchInvoiceInfo = orderStatusItemList.stream().anyMatch(item -> item.getQtyOut() > 0);
        //或者有invoice_flag标识，就需要查询开票信息
        if (!enableSearchInvoiceInfo) {
            int i = expdetailService.countExpdetailByOrderFnoAndInvoiceFlag(order.getOrderFno());
            enableSearchInvoiceInfo = i > 0;
        }
        if (enableSearchInvoiceInfo) {
            // 退货数量查询
            int returnedQty = getReturnedQty(order.getOrderFno());
            order.setReturnedQty(returnedQty);
            // 开票数量查询
            int invoicedQty = getInvoicedQty(order.getOrderFno());
            order.setInvoicedQty(invoicedQty);
            if (invoicedQty > 0) {
                order.setInvoiceTime(new Date());
            }
            if (order.getOrderStatusEnum() == RcvOrderStatusEnum.CKED && order.getExpQty() - order.getQuantity() >= 0) {
                RcvOrderStatusEnum rcvOrderStatusEnum = null;
                //判断退货状态
                if (returnedQty > 0 && returnedQty >= order.getQuantity()) {
                    rcvOrderStatusEnum = RcvOrderStatusEnum.RETURN;
                }
                //判断开票状态
                else if (invoicedQty > 0) {
                    if (invoicedQty >= order.getQuantity() || invoicedQty >= order.getQuantity() - returnedQty) {
                        rcvOrderStatusEnum = RcvOrderStatusEnum.INVOICE;
                    }
                }
                if (rcvOrderStatusEnum != null) {
                    order.setOrderStatusEnum(rcvOrderStatusEnum);
                    order.setStatus(rcvOrderStatusEnum.getType());
                }
            }
        }
    }

    @Override
    public void updateInvoiceInfo(Rcvdetail rcvdetail) throws OpsException {
        Rcvdetail update = new Rcvdetail();
        int returnedQty = getReturnedQty(rcvdetail.getRorderFno());
        int invoicedQty = getInvoicedQty(rcvdetail.getRorderFno());
        update.setReturnedQty(returnedQty);
        update.setInvoiceQty(invoicedQty);
        if (invoicedQty > 0) {
            update.setInvoiceTime(new Date());
        }
        //如果是已发货之后的状态且发货数量已满（非必要，防止异常数据），则考虑更新为已开票或者已退货
        List<Short> list = Arrays.asList(RcvOrderStatusEnum.CKED.getType(), RcvOrderStatusEnum.RETURN.getType(), RcvOrderStatusEnum.INVOICE.getType());
        if (list.contains(rcvdetail.getStatus()) && rcvdetail.getQuantity().equals(rcvdetail.getExpQty())) {
            RcvOrderStatusEnum rcvOrderStatusEnum = null;
            //判断退货状态
            if (returnedQty > 0 && returnedQty >= rcvdetail.getQuantity()) {
                rcvOrderStatusEnum = RcvOrderStatusEnum.RETURN;
            }
            //判断开票状态
            else if (invoicedQty > 0) {
                if (invoicedQty >= rcvdetail.getQuantity() || invoicedQty >= rcvdetail.getQuantity() - returnedQty) {
                    rcvOrderStatusEnum = RcvOrderStatusEnum.INVOICE;
                }
            }
            if (rcvOrderStatusEnum != null) {
                update.setStatus(rcvOrderStatusEnum.getType());
            }
        }
        rcvdetailService.updateRcvdetail(rcvdetail.getRorderNo(), rcvdetail.getRorderItem(), update);
    }

    @Override
    public int getInvoicedQty(String orderFno) {
        List<OpsInvoiceResDto> invoiceInfos = null;
        try {
            invoiceInfos = opsCustomerOrderService.getInvoiceInfo(Collections.singletonList(orderFno));
        } catch (OpsException e) {
            log.error("查询发票数据失败：{}，{}", orderFno, e.getMessage());
            return 0;
        }
        for (OpsInvoiceResDto invoiceInfo : invoiceInfos) {
            if (StringUtils.equals(invoiceInfo.getRorderno(), orderFno)) {
                Integer invoiceSumQty = invoiceInfo.getInvoiceSumQty();
                if (invoiceSumQty != null) {
                    return invoiceSumQty;
                }
            }
        }
        return 0;
    }

    @Override
    public int getReturnedQty(String orderFno) {
        Integer qty = opsCustomerOrderService.getReturnedQty(orderFno);
        return Optional.ofNullable(qty).orElse(0);
    }


}
