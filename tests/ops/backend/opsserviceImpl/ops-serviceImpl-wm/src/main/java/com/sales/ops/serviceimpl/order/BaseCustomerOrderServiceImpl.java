package com.sales.ops.serviceimpl.order;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sales.ops.common.opsexception.Exceptions;
import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.common.until.AssertUtil;
import com.sales.ops.db.dao.OpsCustomerWldateMapper;
import com.sales.ops.db.dao.RcvViewMapper;
import com.sales.ops.db.dao.RcvdetailMapper;
import com.sales.ops.db.dao.RcvmasterMapper;
import com.sales.ops.db.entity.*;
import com.sales.ops.db.extdao.OpsRcvDetailDao;
import com.sales.ops.dto.order.ReceiveCondition;
import com.sales.ops.dto.util.OrderNoInfo;
import com.sales.ops.dto.util.PageModel;
import com.sales.ops.enums.*;
import com.sales.ops.event.publisher.enums.EventSourceEnum;
import com.sales.ops.service.ba.RcvStatusConfigService;
import com.sales.ops.service.order.BaseCustomerOrderService;
import com.sales.ops.service.order.state.OrderStateService;
import com.sales.ops.service.wmOrder.OpsDoService;
import com.sales.ops.serviceimpl.event.v3.CustomerEventPublisher;
import com.sales.ops.serviceimpl.inventory.BaseInventoryService;
import com.smc.smccloud.model.receiveorder.OrderSpecExpType;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 客户订单基础原子操作类
 *
 * @author C12961
 * @date 2023/2/20 13:46
 */
@Slf4j
@Service
@Transactional(rollbackFor = OpsException.class)
public class BaseCustomerOrderServiceImpl implements BaseCustomerOrderService {

    @Autowired
    private RcvmasterMapper rcvmasterMapper;
    @Autowired
    private RcvdetailMapper rcvdetailMapper;
    @Autowired
    private RcvViewMapper rcvViewMapper;
    @Autowired
    private OrderStateService orderStateService;
    @Autowired
    private OpsRcvDetailDao opsRcvDetailDao;
    @Autowired
    private RcvStatusConfigService rcvStatusConfigService;
    @Autowired
    private BaseInventoryService baseInventoryService;
    @Autowired
    private OpsDoService opsDoService;
    @Autowired
    private OpsCustomerWldateMapper opsCustomerWldateMapper;
    @Autowired
    private CustomerEventPublisher customerEventPublisher;



    /***********************************************************【查询操作】********************************************************/

    @Override
    public PageInfo<RcvView> searchByPage(PageModel<ReceiveCondition> pageModel) throws Exception {
        ReceiveCondition rcv = pageModel.getCondition();
        RcvViewExample rcvExample = new RcvViewExample();
        RcvViewExample.Criteria criteria = rcvExample.createCriteria();
        if (CollectionUtils.isNotEmpty(rcv.getRorderFnoList())) {
            List<String> orderFnoList = rcv.getRorderFnoList().stream().filter(orderFno -> StringUtils.isNotBlank(orderFno)).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(orderFnoList)) {
                criteria.andRorderFnoIn(orderFnoList);
            }
            PageHelper.startPage(pageModel.getPageNumber(), pageModel.getPageSize(), pageModel.getOrderBy());
            List<RcvView> rcvViewList = rcvViewMapper.selectByExample(rcvExample);
            return new PageInfo<>(rcvViewList);
        }
        if (StringUtils.isNotBlank(rcv.getRorderNo())) {
            criteria.andRorderNoEqualTo(rcv.getRorderNo());
        }
        if (StringUtils.isNotBlank(rcv.getRorderFno())) {
            criteria.andRorderFnoLike(rcv.getRorderFno() + "%");
        }
        // 状态筛选（独立于信用拦截）
        if (rcv.getStatus() != null) {
            criteria.andStatusEqualTo(rcv.getStatus());
        }
        // 信用拦截筛选：intercept=null不筛选，intercept=false筛选未拦截，intercept=true筛选已拦截
        if (rcv.getIntercept() != null) {
            criteria.andInterceptEqualTo(rcv.getIntercept());
        }
        if (StringUtils.isNotBlank(rcv.getProdFlag())) {
            criteria.andProdFlagEqualTo(StrUtil.cleanBlank(rcv.getProdFlag()));
        }
        if (StringUtils.isNotBlank(rcv.getModelNo())) {
            criteria.andModelNoEqualTo(StrUtil.trim(rcv.getModelNo()));
        }
        if (StringUtils.isNotBlank(rcv.getCproductNo())) {
            criteria.andCproductNoEqualTo(rcv.getCproductNo());
        }
        if (StringUtils.isNotBlank(rcv.getCustomerNo())) {
            criteria.andCustomerNoEqualTo(rcv.getCustomerNo());
        }
        if (StringUtils.isNotBlank(rcv.getUserNo())) {
            criteria.andUserNoEqualTo(rcv.getUserNo());
        }
        if (StringUtils.isNotBlank(rcv.getCorderNo())) {
            criteria.andCorderNoEqualTo(rcv.getCorderNo());
        }
        if (StringUtils.isNotBlank(rcv.getStockType())) {
            criteria.andStockTypeEqualTo(rcv.getStockType());
        }
        if (StringUtils.isNotBlank(rcv.getStockCode())) {
            criteria.andStockCodeEqualTo(rcv.getStockCode());
        }
        if (CollectionUtils.isNotEmpty(rcv.getDeptNo())) {
            criteria.andDeptNoIn(rcv.getDeptNo());
        }
        if (rcv.getOrderType() != null) {
            criteria.andOrderTypeEqualTo(rcv.getOrderType());
        }
        if (rcv.getRorddate() != null) {
            DateTime end = DateUtil.endOfDay(rcv.getRorddate()[1]);
            DateTime start = DateUtil.beginOfDay(rcv.getRorddate()[0]);
            criteria.andRorddateBetween(start, end);
        }
        if (StringUtils.isNotBlank(rcv.getPurchaseno())) {
            criteria.andPurchasenoEqualTo(rcv.getPurchaseno());
        }
        if (StringUtils.isNotBlank(rcv.getDlvEntire())) {
            criteria.andDlvEntireEqualTo(rcv.getDlvEntire());
        }
        PageHelper.startPage(pageModel.getPageNumber(), pageModel.getPageSize(), pageModel.getOrderBy());
        List<RcvView> rcvViewList = rcvViewMapper.selectByExample(rcvExample);
        return new PageInfo<>(rcvViewList);
    }

    @Override
    public RcvView findRcvViewByFno(String orderFno) {
        RcvViewExample ex = new RcvViewExample();
        ex.createCriteria().andRorderFnoEqualTo(orderFno);
        List<RcvView> views = rcvViewMapper.selectByExample(ex);
        if (CollectionUtils.isNotEmpty(views) && (views.size() == 1)) {
            return views.get(0);
        } else {
            return null;
        }
    }

    @Override
    public RcvView findRcvViewByNo(String orderId, Integer orderItem) {
        RcvViewExample ex = new RcvViewExample();
        ex.createCriteria().andRorderNoEqualTo(orderId).andRorderItemEqualTo(orderItem);
        List<RcvView> views = rcvViewMapper.selectByExample(ex);
        if (CollectionUtils.isNotEmpty(views) && (views.size() == 1)) {
            return views.get(0);
        } else {
            return null;
        }
    }

    @Override
    public Rcvdetail findRcvDetailByNo(String orderId, String orderItem) throws OpsException {
        return findRcvDetailByNo(orderId, Integer.valueOf(orderItem));
    }

    @Override
    public Rcvdetail findRcvDetailByNo(String orderId, Integer orderItem) throws OpsException {
        RcvdetailExample example = new RcvdetailExample();
        example.createCriteria().andDeleteStatusEqualTo((short) 0).andRorderNoEqualTo(orderId).andRorderItemEqualTo(orderItem);
        List<Rcvdetail> rcvDetailList = rcvdetailMapper.selectByExample(example);
        return getOne(rcvDetailList);
    }

    @Override
    public Rcvdetail findRcvDetailByKey(String orderId, Integer orderItem) {
        RcvdetailKey key = new RcvdetailKey();
        key.setRorderNo(orderId);
        key.setRorderItem(orderItem);
        return rcvdetailMapper.selectByPrimaryKey(key);
    }

    @Override
    public List<Rcvdetail> findRcvDetailList(String orderId) {
        RcvdetailExample example = new RcvdetailExample();
        example.createCriteria().andDeleteStatusEqualTo((short) 0).andRorderNoEqualTo(orderId);
        return rcvdetailMapper.selectByExample(example);
    }

    @Override
    public List<String> findCreditInterceptedOrderFno() {
        // 查询订单号
        return opsRcvDetailDao.getRorderFnoByCredit();
    }

    @Override
    public Rcvdetail findRcvDetailByFno(String orderFno) throws OpsException {
        RcvdetailExample example = new RcvdetailExample();
        example.createCriteria().andRorderFnoEqualTo(orderFno);
        List<Rcvdetail> rcvDetailList = rcvdetailMapper.selectByExample(example);
        return getOne(rcvDetailList);
    }

    @Override
    public Rcvmaster findRcvMaster(String orderNo) {
        return rcvmasterMapper.selectByPrimaryKey(orderNo);
    }

    @Override
    public List<Rcvdetail> findRcvDetail(String orderId) throws OpsException {
        RcvdetailExample example = new RcvdetailExample();
        example.createCriteria().andDeleteStatusEqualTo((short) 0)
                .andRorderNoEqualTo(orderId);
        return rcvdetailMapper.selectByExample(example);
    }


    private Rcvdetail getOne(List<Rcvdetail> list) throws OpsException {
        if (list.size() == 1) {
            return list.get(0);
        } else {
            throw Exceptions.OpsException("查询出" + list.size() + "条RcvDetail");
        }
    }

    @Override
    public List<OrderNoInfo> getCustomerOrderNoFromPoNo(String orderNo, Integer itemNo, Integer splitNo) {
        List<OrderNoInfo> orderNoInfoList=new ArrayList<>();
        List<OpsInventoryMove> moves = baseInventoryService.findInventoryMoveByPo(orderNo, itemNo, splitNo);
        for (OpsInventoryMove move : moves) {
            try {
                List<OrderNoInfo> list = opsDoService.findOrderNoInfoListByMove(move.getInventoryId());
                orderNoInfoList.addAll(list);
            } catch (OpsException e) {
                log.info("{}", e);
            }
        }
        return orderNoInfoList;
    }


    /***********************************************************【判断操作】********************************************************/
    @Override
    public boolean isRcvDetailExist(String orderId, Integer orderItem) {
        RcvdetailExample example = new RcvdetailExample();
        example.createCriteria().andDeleteStatusEqualTo((short) 0).andRorderNoEqualTo(orderId).andRorderItemEqualTo(orderItem);
        long count = rcvdetailMapper.countByExample(example);
        return count > 0;
    }

    @Override
    public boolean isAssChildModelExport(Rcvdetail rcvdetail) {
        return OrderSpecExpType.include(rcvdetail.getExpDlvType(), OrderSpecExpType.AssChildToExport);
    }

    @Override
    public Boolean enableUpdateStatus(RcvOrderStatusEnum fromStatus, RcvOrderStatusEnum toStatus) throws OpsException {
        AssertUtil.notNull(fromStatus, "状态变更异常：该状态不在配置表中");
        // 未货齐时，直接放行
        if (fromStatus.getType() <= RcvOrderStatusEnum.WAITCK.getType()) {
            return true;
        }
        return rcvStatusConfigService.findConfigByKey(fromStatus, toStatus);
    }

    @Override
    public boolean isCustomerWldate(String customerNo, Integer type) {
        if (null == customerNo) {
            return false;
        }
        OpsCustomerWldateExample example = new OpsCustomerWldateExample();
        example.createCriteria().andCustomerNoEqualTo(customerNo).andIsWldateEqualTo(type).andDelFlagEqualTo(0);
        Long count = opsCustomerWldateMapper.countByExample(example);
        return count > 0 ? true : false;
    }

    /***********************************************************【更新操作,传值更新，给啥更啥】********************************************************/

    /**
     * @description 自定义要变更的字段，手动更新Rcv信息
     * @author C12961
     * @date 2022/4/16 10:34
     */
    @Override
    public int updateRcvDetail(String orderNo, Integer orderItem, Rcvdetail update) throws OpsException {
        Rcvdetail rcvdetail = findRcvDetailByNo(orderNo, orderItem);
        RcvdetailExample ex = new RcvdetailExample();
        ex.createCriteria().andDeleteStatusEqualTo((short) 0).andVersionEqualTo(rcvdetail.getVersion())
                .andRorderNoEqualTo(orderNo).andRorderItemEqualTo(orderItem);
        update.setRorderNo(null);
        update.setRorderItem(null);
        update.setVersion(rcvdetail.getVersion() + 1);
        update.setUpdateTime(new Date());
        if (update.getUpdateUser() == null) {
            update.setUpdateUser("订单更新接口");
        }
        int i = rcvdetailMapper.updateByExampleSelective(update, ex);
        if (i == 1) {
            if (update.getStatus() != null && update.getStatus() != rcvdetail.getStatus()) {
                log.info("订单{}状态更新了，{}更新为{}", rcvdetail.getRorderFno(), rcvdetail.getStatus(), update.getStatus());
                if (update.getStatus().equals(RcvOrderStatusEnum.INTCP.getType())) {
                    return 1;
                }
                Rcvdetail detail = findRcvDetailByKey(orderNo, orderItem);
                orderStateService.sendOrderStateForUpdateRcvStatus(detail);
            }
            return 1;
        } else {
            log.info("订单并发异常，订单" + rcvdetail.getRorderFno() + "正在被其他线程占用，无法修改状态");
            throw Exceptions.OpsException("订单并发异常，订单" + rcvdetail.getRorderFno() + "正在被其他线程占用，无法修改状态");
        }
    }

    @Override
    public int updateAllotInfo(String orderNo, Integer orderItem, RcvOrderStatusEnum status, String prodFlag, Date allotTime, List<String> stockInfo, Integer readyQuantity, Date readyTime) throws OpsException {
        Rcvdetail update = new Rcvdetail();
        if (status != null) {
            update.setStatus(status.getType());
        }
        if (prodFlag != null) {
            update.setProdFlag(prodFlag);
        }
        if (allotTime != null) {
            update.setAllotTime(allotTime);
        }
        if (stockInfo != null && stockInfo.size() == 3) {
            update.setStockType(stockInfo.get(0));
            update.setStockCode(stockInfo.get(1));
            update.setInventoryTypeCode(stockInfo.get(2));
        }
        if (readyQuantity != null) {
            update.setReadyQty(readyQuantity);
        }
        return updateRcvDetail(orderNo, orderItem, update);
    }

    @Override
    public int updateAllotInfo(String orderNo, Integer orderItem, RcvOrderStatusEnum status, List<String> stockInfo) throws OpsException {
        Rcvdetail update = new Rcvdetail();
        if (status != null) {
            update.setStatus(status.getType());
        }
        if (stockInfo != null && stockInfo.size() == 3) {
            update.setStockType(stockInfo.get(0));
            update.setStockCode(stockInfo.get(1));
            update.setInventoryTypeCode(stockInfo.get(2));
        }
        return updateRcvDetail(orderNo, orderItem, update);
    }

    @Override
    public int updateStockInfo(String orderNo, Integer orderItem, String stockType, String stockCode, String inventoryType) throws OpsException {
        Rcvdetail update = new Rcvdetail();
        update.setStockType(stockType);
        update.setStockCode(stockCode);
        if(StringUtils.isBlank(stockCode)){
            update.setStockCode("");
        }
        update.setInventoryTypeCode(inventoryType);
        if(StringUtils.isBlank(inventoryType)){
            update.setInventoryTypeCode("");
        }
        return updateRcvDetail(orderNo, orderItem, update);
    }

    @Override
    public int updateProdFlag(String orderNo, Integer orderItem, String prodFlag, Date date) throws OpsException {
        Rcvdetail update = new Rcvdetail();
        update.setProdFlag(prodFlag);
        update.setAllotTime(date);
        return updateRcvDetail(orderNo, orderItem, update);
    }


    @Override
    public int updateInterceptInfo(String orderNo, Integer orderItem, boolean intercept, Date date) throws OpsException {
        Rcvdetail detail = findRcvDetailByNo(orderNo, orderItem);
        // 如果状态为已发货，不拦截
        if (detail.getStatus() > RcvOrderStatusEnum.CKING.getType()) {
            return 0;
        }
        if (date == null) {
            date = new Date();
        }
        Rcvdetail update = new Rcvdetail();
        update.setIntercept(intercept);
        update.setInterceptTime(date);
        return updateRcvDetail(orderNo, orderItem, update);
    }

    @Override
    public int updateWaveTime(String orderNo, Integer orderItem, Date expectedDeliveryTime) throws OpsException {
        Rcvdetail update = new Rcvdetail();
        if (expectedDeliveryTime != null) {
            update.setExpectedDeliveryTime(expectedDeliveryTime);
        }
        return updateRcvDetail(orderNo, orderItem, update);
    }

    @Override
    public int updateReadyTime(String orderNo, Integer orderItem, Date readyTime, Date entryTime) throws OpsException {
        if (readyTime == null) {
            opsRcvDetailDao.updateReadyTime(orderNo, orderItem.toString(), null);
        }
        Rcvdetail update = new Rcvdetail();
        update.setReadyTime(readyTime);
        update.setEntryTime(entryTime);
        return updateRcvDetail(orderNo, orderItem, update);
    }

    @Override
    public int updateReadyInfo(String orderNo, Integer orderItem, Integer readyQuantity, RcvOrderStatusEnum rcvStatus) throws OpsException {
        Rcvdetail update = new Rcvdetail();
        if (readyQuantity != null) {
            update.setReadyQty(readyQuantity);
        }
        if (rcvStatus != null) {
            update.setStatus(rcvStatus.getType());
        }
        return updateRcvDetail(orderNo, orderItem, update);
    }

    @Override
    public int updateExpInfo(String orderNo, Integer orderItem, boolean allExp, int expQuantity, Date shipTime, String carrier, String expressno, Date handoverTime) throws OpsException {
        Rcvdetail update = new Rcvdetail();
        update.setCarrierid(carrier);
        update.setExpressno(expressno);
        update.setHandoverTime(handoverTime);
        if (allExp) {
            update.setReadyQty(expQuantity);
            update.setStatus(RcvOrderStatusEnum.CKED.getType());
        }
        update.setExpQty(expQuantity);
        update.setShipTime(shipTime);
        update.setHandoverTime(null);
        update.setExpTime(null);
        return updateRcvDetail(orderNo, orderItem, update);
    }

    @Override
    public int updateInvoiceQty(String orderNo, Integer orderItem, int invoiceQty, RcvOrderStatusEnum statusEnum) throws OpsException {
        Rcvdetail update = new Rcvdetail();
        update.setInvoiceQty(invoiceQty);
        if (statusEnum != null) {
            update.setStatus(statusEnum.getType());
            update.setInvoiceTime(new Date());
        }
        return updateRcvDetail(orderNo, orderItem, update);
    }

    @Override
    public int updateReturnQty(String orderNo, Integer orderItem, int returnQty, RcvOrderStatusEnum statusEnum) throws OpsException {
        Rcvdetail update = new Rcvdetail();
        update.setReturnedQty(returnQty);
        if (statusEnum != null) {
            update.setStatus(statusEnum.getType());
        }
        return updateRcvDetail(orderNo, orderItem, update);
    }

    @Override
    public int updateStatus(String orderNo, Integer orderItem, RcvOrderStatusEnum status) throws OpsException {
        Rcvdetail update = new Rcvdetail();
        update.setStatus(status.getType());
        return updateRcvDetail(orderNo, orderItem, update);
    }

    @Override
    public int updateStatusToInit(String orderId, String orderItem) throws OpsException {
        Rcvdetail update = new Rcvdetail();
        update.setStatus(RcvOrderStatusEnum.INIT.getType());
        update.setStockCode("");
        update.setStockType("");
        update.setInventoryTypeCode("");
        update.setProdFlag("");
        update.setReadyQty(0);
        update.setExpQty(0);
        update.setInvoiceQty(0);
        update.setReturnedQty(0);
        return updateRcvDetail(orderId, Integer.valueOf(orderItem), update);
    }

    @Override
    public int updateStatusToCancel(String orderId, String orderItem) throws OpsException {
        Rcvdetail rcvdetail = findRcvDetailByNo(orderId, orderItem);
        RcvOrderStatusEnum statusEnum = RcvOrderStatusEnum.getEnumByType(rcvdetail.getStatus());
        Boolean enable = enableUpdateStatus(statusEnum, RcvOrderStatusEnum.CANCEL);
        if (!enable) {
            throw Exceptions.OpsException(String.format("状态变更顺序异常：【%s】不允许变更为【%s】", statusEnum.getDesc(), RcvOrderStatusEnum.CANCEL.getDesc()));
        }
        Rcvdetail update = new Rcvdetail();
        update.setStatus(RcvOrderStatusEnum.CANCEL.getType());
        update.setDeleteStatus((short) 1);
        // 删除订单时，如果原订单是信用拦截状态，则清除拦截标记
        if (rcvdetail.getIntercept() != null && rcvdetail.getIntercept()) {
            update.setIntercept(false);
        }
        return updateRcvDetail(orderId, Integer.valueOf(orderItem), update);
    }

    @Override
    public int updateEstimatedDeliveryDay(String orderId, Integer orderItem, Date date) throws OpsException {
        if (date == null) {
            return opsRcvDetailDao.updateEstimatedDeliveryDayToNUll(orderId, orderItem.toString());
        }
        Rcvdetail rcvdetail = findRcvDetailByNo(orderId, orderItem);
        if (rcvdetail.getEstimatedDeliveryDay() != date) {
            Rcvdetail update = new Rcvdetail();
            update.setEstimatedDeliveryDay(date);
            updateRcvDetail(orderId, orderItem, update);
            if (Short.valueOf(OrderTypeEnum.JITUAN).equals(rcvdetail.getOrderType())) {
                customerEventPublisher.customerOrderEvent(EventSourceEnum.CUSTOMER_ORDER_DELIVERY_PLAN_AFTER, orderId, orderItem);
            }
        }
        return 1;
    }
}
