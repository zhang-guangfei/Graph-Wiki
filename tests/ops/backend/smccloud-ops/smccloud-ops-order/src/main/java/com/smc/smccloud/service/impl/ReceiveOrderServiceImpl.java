package com.smc.smccloud.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.google.gson.Gson;
import com.sales.ops.dto.util.CommonResult;
import com.smc.smccloud.core.exception.BusinessException;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.model.dto.ClientDetails;
import com.smc.smccloud.core.model.enums.RCVOrderStatusEnum;
import com.smc.smccloud.core.model.page.Page;
import com.smc.smccloud.core.redis.RedisManager;
import com.smc.smccloud.core.redis.redisson.RedissonUtil;
import com.smc.smccloud.core.utils.*;
import com.smc.smccloud.mapper.*;
import com.smc.smccloud.model.DataCodeVO;
import com.smc.smccloud.model.DataTypeVO;
import com.smc.smccloud.model.OpsEventBusDO;
import com.smc.smccloud.model.OrderSales.OrderDlvDataDO;
import com.smc.smccloud.model.OrderSales.OrderDlvDataVO;
import com.smc.smccloud.model.OrderSales.OrderSalesDO;
import com.smc.smccloud.model.OrderSales.OrderSalesVO;
import com.smc.smccloud.model.VSalesManuorder.OpsVManuorderToSales;
import com.smc.smccloud.model.binorder.ModelExpFreqRequest;
import com.smc.smccloud.model.binorder.ModelExpFreqVO;
import com.smc.smccloud.model.customer.TblGroupcustomerVO;
import com.smc.smccloud.model.enums.OrderStockTypeEnum;
import com.smc.smccloud.model.invoice.OrderInvoiceDTO;
import com.smc.smccloud.model.invoice.SalesInvoiceDO;
import com.smc.smccloud.model.invoice.SalesInvoiceDTO;
import com.smc.smccloud.model.invoice.SalesInvoiceRequest;
import com.smc.smccloud.model.order.*;
import com.smc.smccloud.model.orderlog.OrderLogVO;
import com.smc.smccloud.model.orderstate.OrderStateDO;
import com.smc.smccloud.model.orderstate.OrderStateVO;
import com.smc.smccloud.model.receiveorder.*;
import com.smc.smccloud.service.*;
import com.smc.smccloud.util.PriceCompute;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ReceiveOrderServiceImpl implements ReceiveOrderService {

    @Resource
    private RcvdetailMapper rcvdetailMapper;
    @Resource
    private RcvmasterMapper rcvmasterMapper;
    @Resource
    private OrderSalesService orderSalesService;
    @Resource
    private PlatformTransactionManager transactionManager;
    @Resource
    private OrderStateMapper orderStateMapper;
    @Resource
    private OrderLogService orderLogService;
    @Resource
    private OrderSalesMapper orderSalesMapper;
    @Resource
    private OrderDlvDataService orderDlvDataService;
    @Resource
    private OrderDlvDataMapper orderDlvDataMapper;
    @Resource
    private RedissonUtil redissonUtil;
    @Resource
    private SalesInvoiceMapper salesInvoiceMapper;
    @Resource
    private OrderStateService orderStateService;
    @Resource
    private CommonServiceFeignApi commonServiceFeignApi;
    @Resource
    private BinServiceFeignApi binServiceFeignApi;
    @Resource
    private OpsVManuorderToSalesMapper opsVManuorderToSalesMapper;
    @Resource
    private RcvorderViewMapper rcvorderViewMapper;
    @Resource
    private OpsCommonService opsCommonService;
    @Resource
    private DictCommonService dictCommonService;
    @Resource
    private AuthServiceApi authServiceApi;
    @Resource
    private ExpdetailMapper expdetailMapper;
    @Resource
    private RedisManager redisManager;
    @Resource
    private EventService eventService;

    @Value("${outsidesys.yingye-system-auth-url}")
    private String yingyeSysAuthUrl;

    @Resource
    private CommonOutSideInterfaceAuthService commonOutSideInterfaceAuthService;


    //@Override
    /*public ResultVo<String> receiveOrderALLFromOrderSalesOld() {
        List<OrderSalesDO> orderSalesDOList = orderSalesService.listNotProcessOrderSales();
        if (orderSalesDOList.isEmpty()) {
            return ResultVo.success("暂无需要接入的订单");
        }
        return this.receiveOrderFromOrderSales(orderSalesDOList);
    }*/

    @Override
    public ResultVo<String> receiveOrderALLFromOrderSales() {
        List<String> orderNos = orderSalesMapper.listNoProcessOrderNos();
        if (orderNos.isEmpty()) {
            return ResultVo.success("暂无需要接入的订单");
        }

        StringBuilder sbMsg = new StringBuilder();
        ResultVo<String> resultVo;
        for (String orderNo : orderNos) {
            resultVo = this.receiveOrderByOrderNo(orderNo);
            sbMsg.append(resultVo.getMessage()).append(", ");
        }

        return ResultVo.success(sbMsg.toString());
    }

    @Override
    public ResultVo<PageInfo<ReceiveOrderVO>> listReceiveOrder(ReceiveOrderRequest request, Page page) {
        PageInfo<ReceiveOrderVO> pageInfo = PageHelper.startPage(page.getPageNumber(), page.getPageSize())
                .doSelectPageInfo(() -> rcvdetailMapper.selectJoinList(ReceiveOrderVO.class,
                        new MPJLambdaWrapper<RcvDetailDO>()
                                .select(RcvDetailDO.class, tableFieldInfo -> !tableFieldInfo.getColumn().equals("id"))
                                .select(RcvMasterDO.class, tableFieldInfo -> !tableFieldInfo.getColumn().equals("rorder_no")
                                        && !tableFieldInfo.getColumn().equals("create_time") && !tableFieldInfo.getColumn().equals("create_user")
                                        && !tableFieldInfo.getColumn().equals("update_time") && !tableFieldInfo.getColumn().equals("update_user"))
                                .selectAs(RcvMasterDO::getId, ReceiveOrderVO::getRmId)
                                .selectAs(RcvDetailDO::getId, ReceiveOrderVO::getRid)
                                .selectAs(RcvMasterDO::getDlvEntire, ReceiveOrderVO::getDlvEntire)
                                .leftJoin(RcvMasterDO.class, RcvMasterDO::getRorderNo, RcvDetailDO::getRorderNo)
                                .like(PublicUtil.isNotEmpty(request.getRorderFno()), RcvDetailDO::getRorderFno, request.getRorderFno())
                                .eq(PublicUtil.isNotEmpty(request.getModelNo()), RcvDetailDO::getModelNo, request.getModelNo())
                                .eq(PublicUtil.isNotEmpty(request.getCustomerNo()), RcvMasterDO::getCustomerNo, request.getCustomerNo())
                                .eq(PublicUtil.isNotEmpty(request.getStatus()), RcvDetailDO::getStatus, request.getStatus())
                                .eq(PublicUtil.isNotEmpty(request.getTradecompanyId()), RcvMasterDO::getTradeCompanyId, request.getTradecompanyId())
                                .eq(PublicUtil.isNotEmpty(request.getAreaDeptNo()), RcvMasterDO::getDeptNo, request.getAreaDeptNo())
                                .eq(PublicUtil.isNotEmpty(request.getUserNo()), RcvMasterDO::getUserNo, request.getUserNo())
                                .eq(PublicUtil.isNotEmpty(request.getCorderNo()), RcvDetailDO::getCorderNo, request.getCorderNo())
                                .eq(PublicUtil.isNotEmpty(request.getCproductNo()), RcvDetailDO::getCproductNo, request.getCproductNo())
                                .eq(PublicUtil.isNotEmpty(request.getDeptNo()), RcvMasterDO::getDeptNo, request.getDeptNo())
                                .ge(PublicUtil.isNotEmpty(request.getROrdDateStart()), RcvDetailDO::getShipTime, request.getROrdDateStart())
                                .le(PublicUtil.isNotEmpty(request.getROrdDateEnd()), RcvDetailDO::getShipTime, request.getROrdDateEnd())
                ));
        if (!pageInfo.getList().isEmpty()) {
            for (ReceiveOrderVO item : pageInfo.getList()) {
                item.setProdFlag("2".equals(item.getProdFlag()) ? "是" : "否");
                if (item.getExpDlvType() == null || item.getExpDlvType() == 0) {
                    item.setExpDlvTypeName("");
                } else {
                    item.setExpDlvTypeName(OrderSpecExpType.getSpecExpTypeDes(item.getExpDlvType()));
                }
            }
        }
        return ResultVo.success(pageInfo);
    }

//    @Override
//    public PageInfo<RcvDetailDO> listRcvDetail(ReceiveOrderRequest request, Page page) {
//        QueryWrapper<RcvDetailDO> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("1", 1);
//        return PageHelper.startPage(page.getPageNumber(), page.getPageSize())
//                .doSelectPageInfo(() -> rcvdetailMapper.selectList(queryWrapper));
//    }

    @Override
    public ResultVo<String> receiveOrderByOrderNos(String[] rorderNos) {
        if (ArrayUtils.isEmpty(rorderNos)) {
            return ResultVo.failure("请传入订单号");
        }

        ResultVo<String> stringResultVo;
        for (String rorderno : rorderNos) {
            stringResultVo = receiveOrderByOrderNo(rorderno);
            if (!stringResultVo.isSuccess()) {
                return ResultVo.failure("接入订单失败: {}", stringResultVo.getMessage());
            }
        }
        return ResultVo.success("操作成功");
    }

    private Boolean exitRcvMaster(String rorderNo) {
        LambdaQueryWrapper<RcvMasterDO> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(RcvMasterDO::getRorderNo, rorderNo);
        int count = rcvmasterMapper.selectCount(queryWrapper);
        return count > 0;
    }

    private Boolean exitRcvDetail(String rorderno, Integer itemNo) {
        LambdaQueryWrapper<RcvDetailDO> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(RcvDetailDO::getRorderNo, rorderno);
        queryWrapper.eq(RcvDetailDO::getRorderItem, itemNo);
        int count = rcvdetailMapper.selectCount(queryWrapper);
        return count > 0;
    }

    @Override
    public ResultVo<String> receiveOrderByOrderNo(String orderNo) {
        // 判断订单是否正在保存
        String keyPrefix = "ops:rediss:orderSales:inserting:";
        if (redissonUtil.isLock(keyPrefix + orderNo)) {
            return ResultVo.success(orderNo, "订单保存中" + orderNo);
        }
        // 1-查询 orderSales 未接入订单
        LambdaQueryWrapper<OrderSalesDO> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(OrderSalesDO::getRorderNo, orderNo);
        queryWrapper.eq(OrderSalesDO::getStatus, "0"); // 未接入
        List<OrderSalesDO> orderSalesDOList = orderSalesMapper.selectList(queryWrapper);
        if (orderSalesDOList.isEmpty()) {
            return ResultVo.success("", "没有未处理项" + orderNo);
        }

        //1.新增到rcvmaster
        RcvMasterDO rcvMasterDO = convertToRcvMasterDO(orderSalesDOList.get(0));
        try {
            addRcvMaster(rcvMasterDO);
        } catch (Exception e) {
            log.error("增加rcvMaster发生异常,异常信息:", e);
            if (!exitRcvMaster(rcvMasterDO.getRorderNo())) {
                return ResultVo.failure("增加rcvMaster失败" + rcvMasterDO.getRorderNo());
            }
        }
        RcvDetailDO rcvDetailDO;
        for (OrderSalesDO orderSalesDO : orderSalesDOList) {
            //2.新增到rcvdetail
            rcvDetailDO = convertToRcvDetailDO(orderSalesDO);
            try {
                addRcvDetail(rcvDetailDO);
                insertEvent(rcvDetailDO, "CUSTOMER_ORDER_ALLOT_BEFORE",null);
            } catch (Exception e) {
                if (!exitRcvDetail(rcvDetailDO.getRorderNo(), rcvDetailDO.getRorderItem())) {
                    return ResultVo.failure("增加到rcvDetail失败" + rcvDetailDO.getRorderFno());
                }
            }

            //3.更新ordersales状态
            try {
                orderSalesService.updateOrderSalesToProcessedStatus(orderSalesDO.getRorderNo(), orderSalesDO.getRorderItem());
            } catch (Exception e) {
                log.error("变更OrderSales接入处理状态失败,{}", e.getMessage(), e);
                return ResultVo.failure("变更OrderSales接入处理状态失败" + rcvDetailDO.getRorderFno());
            }

            //4.写入日志
            addOrderLogAndState(rcvDetailDO, "接入订单");

        }

        return ResultVo.success(orderNo, "接单成功" + orderNo);
    }

    private void insertEvent(RcvDetailDO rcvDetailDO,String eventCode,String remark) {
        try {
            OpsEventBusDO eventBusDO = new OpsEventBusDO();
            eventBusDO.setOrderId(rcvDetailDO.getRorderNo());
            eventBusDO.setOrderItem(rcvDetailDO.getRorderItem().toString());
            eventBusDO.setDealFlag(0);
            eventBusDO.setCreator("receiveOrderByOrderNo");
            eventBusDO.setCreTime(new Date());
            eventBusDO.setEventCode(eventCode);
            eventBusDO.setVersion(0);
            eventBusDO.setParams(remark);
            eventService.insert(eventBusDO);
        } catch (Exception e) {
            log.error("事件写入失败：{}", rcvDetailDO.getRorderFno());
        }
    }

    @Override
    public ResultVo<PageInfo<RcvMasterDO>> listRcvMaster(RcvMasterRequest request, Page page) {
        if (request == null) {
            return ResultVo.failure("参数为空.");
        }

        if (StringUtils.isNotBlank(request.getCustomerNo())) {
            request.setCustomerNo(request.getCustomerNo() + "%");
        }
        if (StringUtils.isNotBlank(request.getRorderNo())) {
            request.setRorderNo(request.getRorderNo() + "%");
        }
        if (StringUtils.isNotBlank(request.getPurchaseNo())) {
            request.setPurchaseNo(request.getPurchaseNo() + "%");
        }
        if (StringUtils.isNotBlank(request.getModelNo())) {
            request.setModelNo(request.getModelNo() + "%");
        }
        if (StringUtils.isNotBlank(request.getCproductNo())) {
            request.setCproductNo(request.getCproductNo() + "%");
        }
        String strStartDate = " 00:00:00";
        String strEndDate = " 23:59:59";
        if (request.getEndDate() != null && request.getStartDate() != null) {
            request.setStrStartDate(DateUtil.dateToDateString(request.getStartDate()) + strStartDate);
            request.setStrEndDate(DateUtil.dateToDateString(request.getEndDate()) + strEndDate);
        }

        PageInfo<RcvMasterDO> pageInfo = PageHelper.startPage(page.getPageNumber(), page.getPageSize())
                .doSelectPageInfo(() -> rcvmasterMapper.listData(request));

        if (CollectionUtils.isNotEmpty(pageInfo.getList())) {
            Map<String, String> nameMap = new HashMap<>(pageInfo.getList().size());
            for (RcvMasterDO item : pageInfo.getList()) {
                // 客户担当名称
                if (!nameMap.containsKey(item.getEmployeeNo())) {
                    nameMap.put(item.getEmployeeNo(), opsCommonService.getEmpSaleNameByNo(item.getEmployeeNo()));
                }
                item.setEmployee(nameMap.get(item.getEmployee()));
            }
        }

        return ResultVo.success(pageInfo);
    }

    @Override
    public ResultVo<List<RcvDetailDO>> listRcvDetail(String rorderNo) {
        if (StringUtils.isBlank(rorderNo)) {
            return ResultVo.failure("参数不可为空.");
        }
        LambdaQueryWrapper<RcvDetailDO> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(RcvDetailDO::getRorderNo, rorderNo);
        List<RcvDetailDO> rcvDetailDOList = rcvdetailMapper.selectList(queryWrapper);
        if (rcvDetailDOList.isEmpty()) {
            return ResultVo.failure("暂无数据");
        }
        for (RcvDetailDO item : rcvDetailDOList) {
            if (item.getStatus() != null) {
                item.setStatusName(RCVOrderStatusEnum.getName(item.getStatus()));
            }
        }

        return ResultVo.success(rcvDetailDOList);
    }

    @Override
    public ResultVo<RcvOrderViewVO> getRcvOrderDataByNo(String orderNo) {
        LambdaQueryWrapper<RcvOrderViewDO> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(RcvOrderViewDO::getRorderFno, orderNo);
        queryWrapper.ne(RcvOrderViewDO::getStatus, 9);

        RcvOrderViewDO viewDO = rcvorderViewMapper.selectOne(queryWrapper);
        if (viewDO == null) {
            return ResultVo.failure("未查询到订单");
        }
        RcvOrderViewVO viewVO = BeanCopyUtil.copy(viewDO, RcvOrderViewVO.class);
        List<SalesInvoiceDO> invoice = salesInvoiceMapper.selectInvoiceForReturn(orderNo, viewDO.getCustomerNo());
        if (CollectionUtil.isNotEmpty(invoice)) {
            viewVO.setInvoiceNo(invoice.get(0).getInvoiceNo());
            viewVO.setInvoiceDate(invoice.get(0).getInvDate());
        }
        return ResultVo.success(viewVO);
    }

    @Override
    public ResultVo<List<OpsOrderAssignResultVO>> getOrderAssItemsByModelNo(String orderNo, Integer itemNo) {
        List<com.smc.smccloud.model.OpsOrderAssignResultDO> list = rcvdetailMapper.getOrderAssignResultModelQty(orderNo, itemNo);
        if(list.isEmpty())
        {
            return  ResultVo.failure();
        }
        return ResultVo.success(BeanCopyUtil.copyList(list, OpsOrderAssignResultVO.class));
    }

    @Async
    public void addOrderLogAndState(RcvDetailDO rcvDetailDO, String optName) {
        //4.记录日志和写入货期
        OrderLogVO orderLogVO = new OrderLogVO();
        orderLogVO.setOrderNo(rcvDetailDO.getRorderFno());
        orderLogVO.setOptType(12);
        orderLogVO.setDescription("接入订单");
        orderLogVO.setOptTime(rcvDetailDO.getUpdateTime());
        orderLogVO.setIp(IpUtil.getIpAddress());
        orderLogVO.setOptUserName(rcvDetailDO.getUpdateUser());
        orderLogService.sendOrderLog(orderLogVO);

        // 推送货期状态到MQ
        // OrderStateVO orderStateVO = new OrderStateVO();
        OrderStateVO orderStateVO = new OrderStateVO();
        orderStateVO.setOrderNo(rcvDetailDO.getRorderFno());
        orderStateVO.setRorderNo(rcvDetailDO.getRorderNo());
        orderStateVO.setItemNo(rcvDetailDO.getRorderItem());
        orderStateVO.setModelNo(rcvDetailDO.getModelNo());
        orderStateVO.setQuantity(rcvDetailDO.getQuantity());
        orderStateVO.setOrderType(rcvDetailDO.getOrderType());
        orderStateVO.setStateCode(12);
        orderStateVO.setCorderNo(rcvDetailDO.getCorderNo());
        orderStateVO.setCustDlvDate(rcvDetailDO.getCdlvDate());
        orderStateVO.setStateDes("接入订单");
        orderStateVO.setStateType(1);
        orderStateVO.setOrderType(rcvDetailDO.getOrderType());
        orderStateVO.setStateDate(new Date());
        orderStateVO.setProvider("OPS");
        orderStateVO.setReceiveDate(rcvDetailDO.getUpdateTime());
        orderStateService.addOrderState(orderStateVO);
    }

    @Override
    @Transactional
    public ResultVo<String> convertToProcessing(List<ReceiveOrderVO> receiveOrderVOList) {
        if (PublicUtil.isEmpty(receiveOrderVOList)) {
            return ResultVo.failure("转为处理订单失败");
        }

        try {
            LambdaUpdateWrapper<RcvDetailDO> updateWrapper = Wrappers.lambdaUpdate();
            for (ReceiveOrderVO receiveOrderVO : receiveOrderVOList) {
                if (PublicUtil.isEmpty(receiveOrderVO.getRid())) {
                    return ResultVo.failure("转为处理订单失败");
                }
                updateWrapper.clear();
                updateWrapper.set(RcvDetailDO::getStatus, 1);
                updateWrapper.eq(RcvDetailDO::getId, receiveOrderVO.getRid());
                int i = rcvdetailMapper.update(null, updateWrapper);
                if (i <= 0) {
                    throw new BusinessException();
                }
            }
            return ResultVo.success("成功转为处理订单");
        } catch (Exception e) {
            throw new BusinessException(e);
        }
    }

    @Override
    public ResultVo<RcvOrderInfoVO> getRcvOrderInfo(String orderNo, Integer itemNo) {
//        //1.去revdetail查信息
//        QueryWrapper<RcvDetailDO> query = new QueryWrapper<>();
//        query.eq("rorder_no", orderNo);
//        query.eq("rorder_item", itemNo);
//
//        RcvDetailDO detailDO = rcvdetailMapper.selectOne(query);
//        if (detailDO == null) {
//            return ResultVo.failure("未查询到信息");
//        }
//        RcvOrderInfoVO infoVO = BeanCopyUtil.copy(detailDO, RcvOrderInfoVO.class);
//        infoVO.setEprice(detailDO.getEPrice());
//        infoVO.setRorderItemNo(detailDO.getRorderItem());
//
//        //2.去revMaster表查信息
//        QueryWrapper<RcvMasterDO> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("rorder_no", orderNo);
//
//        RcvMasterDO masterDO = rcvmasterMapper.selectOne(queryWrapper);
//        infoVO.setUserNo(masterDO.getUserNo());
//        infoVO.setCustomerNo(masterDO.getCustomerNo());
//
        // 查询rcvorder_view视图
        RcvOrderInfoVO orderInfo = rcvdetailMapper.getRcvOrderInfo(orderNo, itemNo);

        if (orderInfo == null) {
            return ResultVo.failure("暂无数据");
        }

        orderInfo.setUserName(opsCommonService.getCustomerNameByNo(orderInfo.getUserNo()));
        orderInfo.setCustomerName(opsCommonService.getCustomerNameByNo(orderInfo.getCustomerNo()));

        //2.根据状态获取状态名称
        // ResultVo<DataTypeVO> codesInfo = dictDataServiceFeignApi.getDataTypeCodesInfo("1001", String.valueOf(orderInfo.getStatus()));
        // orderInfo.setStatusName(codesInfo.getData().getCodeName());
        orderInfo.setStatusName(RCVOrderStatusEnum.getName(orderInfo.getStatus()));

//        //3.根据订单全号去orderstate表里查货期状态
//        QueryWrapper<OrderStateDO> stateWrapper = new QueryWrapper<>();
//        stateWrapper.eq("order_no", orderInfo.getRorderFno());
//
//        OrderStateDO stateDO = orderStateMapper.selectOne(stateWrapper);
//        if (stateDO != null) {
//            orderInfo.setStatusText(stateDO.getStateDes());
//        }

        return ResultVo.success(orderInfo);
    }

    @Override
    public ResultVo<List<OrderInvoiceVO>> getOrderInvoiceInfo(OrderInvoiceDTO dto) {
        List<OrderInvoiceVO> voList = new ArrayList<>();
        LambdaQueryWrapper<SalesInvoiceDO> query = Wrappers.lambdaQuery();
        List<SalesInvoiceDO> doList;
        OrderInvoiceVO invoiceVO;
        if (CollectionUtils.isNotEmpty(dto.getOrderNos())) {
            for (String orderNo : dto.getOrderNos()) {
                query.clear();
                query.eq(SalesInvoiceDO::getROrderNo, orderNo);
                //BUG11021 WuJiaWen 2023/06/07
//                query.eq(SalesInvoiceDO::getCustomerNo, dto.getCustomerNo());
//                query.eq(PublicUtil.isNotEmpty(dto.getUserNo()), SalesInvoiceDO::getUserNo, dto.getUserNo());
                if(StringUtils.isNotBlank(dto.getUserNo()) || StringUtils.isNotBlank(dto.getCustomerNo())){
                    query.and(i ->
                            i.eq(StringUtils.isNotBlank(dto.getUserNo()),SalesInvoiceDO::getUserNo, dto.getUserNo())
                                    .or(StringUtils.isNotBlank(dto.getUserNo()))
                                    .eq(StringUtils.isNotBlank(dto.getUserNo()),SalesInvoiceDO::getCustomerNo, dto.getUserNo())
                                    .or(StringUtils.isNotBlank(dto.getUserNo()))
                                    .eq(StringUtils.isNotBlank(dto.getUserNo()),SalesInvoiceDO::getEndUser, dto.getUserNo())
                                    .or(StringUtils.isNotBlank(dto.getUserNo()))
                                    .eq(StringUtils.isNotBlank(dto.getCustomerNo()),SalesInvoiceDO::getUserNo, dto.getCustomerNo())
                                    .or(StringUtils.isNotBlank(dto.getCustomerNo()))
                                    .eq(StringUtils.isNotBlank(dto.getCustomerNo()),SalesInvoiceDO::getCustomerNo, dto.getCustomerNo())
                                    .or(StringUtils.isNotBlank(dto.getCustomerNo()))
                                    .eq(StringUtils.isNotBlank(dto.getCustomerNo()),SalesInvoiceDO::getEndUser, dto.getCustomerNo()));
                }
                doList = salesInvoiceMapper.selectList(query);
                if (CollectionUtils.isEmpty(doList)) {
                    continue;
                }

                for (SalesInvoiceDO salesInvoiceDO : doList) {
                    invoiceVO = new OrderInvoiceVO();
                    invoiceVO.setRorderNo(salesInvoiceDO.getOrderno());
                    invoiceVO.setModelNo(StringUtils.isBlank(salesInvoiceDO.getModelNo()) ? "" : salesInvoiceDO.getModelNo());
                    if (salesInvoiceDO.getQuantity() != null) {
                        invoiceVO.setQuantity(salesInvoiceDO.getQuantity());
                    }
                    invoiceVO.setRorderItem(Integer.valueOf(salesInvoiceDO.getItemno()));
                    invoiceVO.setInvoiceNo(StringUtils.isBlank(salesInvoiceDO.getInvoiceNo()) ? "" : salesInvoiceDO.getInvoiceNo());
                    if (salesInvoiceDO.getInvDate() != null) {
                        invoiceVO.setInvoiceDate(salesInvoiceDO.getInvDate());
                    }
                    invoiceVO.setRorderFno(salesInvoiceDO.getROrderNo().trim());

                    voList.add(invoiceVO);
                }
            }
        } else {
            query.clear();
            query.eq(SalesInvoiceDO::getOrderno, dto.getOrderNo());
            query.eq(SalesInvoiceDO::getItemno, dto.getItemNo());
//            query.eq(SalesInvoiceDO::getCustomerNo, dto.getCustomerNo());
            if(StringUtils.isNotBlank(dto.getUserNo()) || StringUtils.isNotBlank(dto.getCustomerNo())){
                query.and(i ->
                        i.eq(StringUtils.isNotBlank(dto.getUserNo()),SalesInvoiceDO::getUserNo, dto.getUserNo())
                                .or(StringUtils.isNotBlank(dto.getUserNo()))
                                .eq(StringUtils.isNotBlank(dto.getUserNo()),SalesInvoiceDO::getCustomerNo, dto.getUserNo())
                                .or(StringUtils.isNotBlank(dto.getUserNo()))
                                .eq(StringUtils.isNotBlank(dto.getUserNo()),SalesInvoiceDO::getEndUser, dto.getUserNo())
                                .or(StringUtils.isNotBlank(dto.getUserNo()))
                                .eq(StringUtils.isNotBlank(dto.getCustomerNo()),SalesInvoiceDO::getUserNo, dto.getCustomerNo())
                                .or(StringUtils.isNotBlank(dto.getCustomerNo()))
                                .eq(StringUtils.isNotBlank(dto.getCustomerNo()),SalesInvoiceDO::getCustomerNo, dto.getCustomerNo())
                                .or(StringUtils.isNotBlank(dto.getCustomerNo()))
                                .eq(StringUtils.isNotBlank(dto.getCustomerNo()),SalesInvoiceDO::getEndUser, dto.getCustomerNo()));
            }
            doList = salesInvoiceMapper.selectList(query);
            if (CollectionUtils.isEmpty(doList)) {
                return ResultVo.success(voList);
            }
            for (SalesInvoiceDO salesInvoiceDO : doList) {
                invoiceVO = new OrderInvoiceVO();
                invoiceVO.setRorderNo(dto.getOrderNo());
                invoiceVO.setModelNo(StringUtils.isBlank(salesInvoiceDO.getModelNo()) ? "" : salesInvoiceDO.getModelNo());
                if (salesInvoiceDO.getQuantity() != null) {
                    invoiceVO.setQuantity(salesInvoiceDO.getQuantity());
                }
                invoiceVO.setRorderItem(dto.getItemNo());
                invoiceVO.setInvoiceNo(StringUtils.isBlank(salesInvoiceDO.getInvoiceNo()) ? "" : salesInvoiceDO.getInvoiceNo());
                if (salesInvoiceDO.getInvDate() != null) {
                    invoiceVO.setInvoiceDate(salesInvoiceDO.getInvDate());
                }
                invoiceVO.setRorderFno(salesInvoiceDO.getROrderNo().trim());
                voList.add(invoiceVO);
            }
        }
        return ResultVo.success(voList);
    }

    @Override
    public ResultVo<String> addRcvOrder(RcvOrderDTO dto) {
        if (dto == null || dto.getAddressInfo() == null ||
                dto.getItems().isEmpty()) {
            return ResultVo.failure("订单信息，地址信息不能为空");
        }
        TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
        transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        transactionTemplate.execute(status -> {
            RcvMasterDO rcvMasterDO = BeanCopyUtil.copy(dto, RcvMasterDO.class);
            // 写入rcvMaster
            try {
                addRcvMaster(rcvMasterDO);
            } catch (Exception e) {
                throw new BusinessException(e);
            }
            // 写入rcvDetail
            RcvDetailDO rcvDetailDO;
            for (RcvDetailVO rcvDetailVO : dto.getItems()) {
                try {
                    rcvDetailDO = BeanCopyUtil.copy(rcvDetailVO, RcvDetailDO.class);
                    addRcvDetail(rcvDetailDO);
                    insertEvent(rcvDetailDO, "CUSTOMER_ORDER_ALLOT_BEFORE", null);
                } catch (Exception e) {
                    throw new BusinessException(e);
                }
            }
            // 写入 orderDlvData 订单收货
            OrderDlvDataDO orderDlvDataDO = BeanCopyUtil.copy(dto.getAddressInfo(), OrderDlvDataDO.class);
            try {
                orderDlvDataService.saveOrderDlvData(orderDlvDataDO);
            } catch (Exception e) {
                throw new BusinessException(e);
            }
            return true;
        });
        return ResultVo.success("操作成功!");
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    @DS("opsdb")
    public ResultVo<Integer> addRcvDetail(RcvDetailDO rcvDetailDO) {
        LambdaQueryWrapper<RcvDetailDO> rcvdetailQuery = Wrappers.lambdaQuery();
        rcvdetailQuery.eq(RcvDetailDO::getRorderNo, rcvDetailDO.getRorderNo());
        rcvdetailQuery.eq(RcvDetailDO::getRorderItem, rcvDetailDO.getRorderItem());
        int count = rcvdetailMapper.selectCount(rcvdetailQuery);
        if (count > 0) {
            return ResultVo.success(0);
        }

        int i;
        try {
            i = rcvdetailMapper.insert(rcvDetailDO);

        } catch (Exception e) {
            log.error("写入rcvdetail发生异常:", e);
            throw new BusinessException("写入rcvDetail失败", e);
        }
        return ResultVo.success(i);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    @DS("opsdb")
    public ResultVo<Integer> addRcvMaster(RcvMasterDO rcvMasterDO) {
        int result;
        LambdaQueryWrapper<RcvMasterDO> rcvMasterQuery = Wrappers.lambdaQuery();
        rcvMasterQuery.eq(RcvMasterDO::getRorderNo, rcvMasterDO.getRorderNo());
        result = rcvmasterMapper.selectCount(rcvMasterQuery);
        if (result > 0) {
            return ResultVo.success(result);
        }
        try {
            result = rcvmasterMapper.insert(rcvMasterDO);
            return ResultVo.success(result);
        } catch (Exception e) {
            log.error("写入rcvMaster发生异常,", e);
            throw new BusinessException("写入rcvMaster失败", e);
        }

    }

    @Override
    @DS("opsdb")
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public ResultVo<RcvDetailVO> findOrderDetail(String fullOrderNo) {
        LambdaQueryWrapper<RcvDetailDO> orderQuery = Wrappers.lambdaQuery();
        orderQuery.eq(RcvDetailDO::getRorderFno, fullOrderNo);
        RcvDetailDO rcvDetailDO = rcvdetailMapper.selectOne(orderQuery);
        if (PublicUtil.isEmpty(rcvDetailDO)) {
            return ResultVo.failure("暂无数据");
        }
        return ResultVo.success(BeanCopyUtil.copy(rcvDetailDO, RcvDetailVO.class));
    }

    @Override
    @DS("opsdb")
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public ResultVo<RcvMasterVO> findOrderMaster(String rorderNo) {
        LambdaQueryWrapper<RcvMasterDO> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(RcvMasterDO::getRorderNo, rorderNo);
        RcvMasterDO rcvMasterDO = rcvmasterMapper.selectOne(queryWrapper);
        if (PublicUtil.isEmpty(rcvMasterDO)) {
            return ResultVo.failure("暂无数据");
        }
        return ResultVo.success(BeanCopyUtil.copy(rcvMasterDO, RcvMasterVO.class));
    }

    @Override
    @DS("opsdb")
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public Integer getRcvDetailStatus(String fullOrderNo) {
        LambdaQueryWrapper<RcvDetailDO> query = Wrappers.lambdaQuery();
        query.select(RcvDetailDO::getRorderFno, RcvDetailDO::getStatus);
        query.eq(RcvDetailDO::getRorderFno, fullOrderNo)
                .select(RcvDetailDO::getStatus);
        RcvDetailDO info = rcvdetailMapper.selectOne(query);
        return info.getStatus();
    }

    @Override
    @DS("opsdb")
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public ResultVo<String> cancelRevDetail(String fullOrderNo) {
        LambdaUpdateWrapper<RcvDetailDO> updateWrapper = Wrappers.lambdaUpdate();
        updateWrapper.eq(RcvDetailDO::getRorderFno, fullOrderNo)
                .set(RcvDetailDO::getStatus, 9);
        int updcount = rcvdetailMapper.update(null, updateWrapper);
        return updcount > 0 ? ResultVo.success("订单取消成功") : ResultVo.failure("订单取消失败");
    }

    public RcvMasterDO convertToRcvMasterDO(OrderSalesDO orderSalesDO) {

        RcvMasterDO rcvMasterDO = BeanCopyUtil.copy(orderSalesDO, RcvMasterDO.class);
        rcvMasterDO.setPurchaseUnitCode(orderSalesDO.getPurchaseUnitCode());
        rcvMasterDO.setEmployee(Optional.ofNullable(orderSalesDO.getEmpSale()).orElse("")); // 外勤
        rcvMasterDO.setEmployeeNo(Optional.ofNullable(orderSalesDO.getEmpOffice()).orElse("")); // 内勤
        rcvMasterDO.setOptTime(new Date());
        rcvMasterDO.setRordDate(orderSalesDO.getCreateTime()); // 接单分配接单号时间
        rcvMasterDO.setPurchaseNo(orderSalesDO.getCorderNo());
        rcvMasterDO.setHlCode(Optional.ofNullable(orderSalesDO.getHLCode()).orElse(""));
        if (StringUtils.isBlank(rcvMasterDO.getEndUser())) {
            if (PublicUtil.isEmpty(rcvMasterDO.getUserNo())) {
                rcvMasterDO.setEndUser(rcvMasterDO.getCustomerNo());
            } else {
                rcvMasterDO.setEndUser(rcvMasterDO.getUserNo());
            }
        }
        return rcvMasterDO;
    }

    public RcvDetailDO convertToRcvDetailDO(OrderSalesDO orderSalesDO) {
        RcvDetailDO rcvDetailDO = BeanCopyUtil.copy(orderSalesDO, RcvDetailDO.class);
        rcvDetailDO.setStatus(1);
        if (orderSalesDO.getTypeCode() == 11) {
            // 国内集团的订单 因为未计算价格 所以设置为暂不处理 成功计算完价格 改为待处理 反之 异常订单
            rcvDetailDO.setStatus(11);
        }
        rcvDetailDO.setAmount(Optional.ofNullable(orderSalesDO.getAccount()).orElse(BigDecimal.ZERO));
        if (rcvDetailDO.getPrice() != null && rcvDetailDO.getQuantity() != null) {
            if (rcvDetailDO.getAmount() == null || rcvDetailDO.getAmount().compareTo(BigDecimal.ZERO) == 0) {
                rcvDetailDO.setAmount(rcvDetailDO.getPrice().multiply(new BigDecimal(rcvDetailDO.getQuantity())));
            }
        }
        rcvDetailDO.setOpponent(orderSalesDO.getRcvClassify());
        rcvDetailDO.setSpecOfferNo(Optional.ofNullable(orderSalesDO.getSpcPrice()).orElse(""));
        rcvDetailDO.setProductName(Optional.ofNullable(orderSalesDO.getCproductName()).orElse(""));
        rcvDetailDO.setWmsDlvDate(orderSalesDO.getWarehouseSendDate());
        rcvDetailDO.setCdlvDate(orderSalesDO.getCdlvDate());
        rcvDetailDO.setDlvDate(orderSalesDO.getDlvDate());
        rcvDetailDO.setDiscount(orderSalesDO.getDiscount());
        rcvDetailDO.setPriceUser(orderSalesDO.getUserPrice()); // 用户价格

        if (orderSalesDO.getTypeCode() != null) {
            rcvDetailDO.setOrderType(orderSalesDO.getTypeCode());
        }
        if (PublicUtil.isEmpty(orderSalesDO.getFullOrderNo())) {
            rcvDetailDO.setRorderFno(orderSalesDO.getRorderNo() + "-" + orderSalesDO.getRorderItem());
        } else {
            rcvDetailDO.setRorderFno(orderSalesDO.getFullOrderNo());
        }
        if (rcvDetailDO.getTaxRate() == null) {
            ResultVo<DataTypeVO> dataTypeCodesInfo = dictCommonService.getDataTypeCodesInfo("9002", "1");
            if (dataTypeCodesInfo.isSuccess() && dataTypeCodesInfo.getData() != null) {
                rcvDetailDO.setTaxRate(new BigDecimal(dataTypeCodesInfo.getData().getExtNote1()));
            } else {
                rcvDetailDO.setTaxRate(new BigDecimal("0.13"));
            }
        }
        if (rcvDetailDO.getPrice() != null && rcvDetailDO.getQuantity() != null) {
            // 不含税单价 保留4位小数 四舍五入
            rcvDetailDO.setNtaxPice(PriceCompute.unitPriceExcludingTax(rcvDetailDO.getPrice(), rcvDetailDO.getTaxRate()));
            if (rcvDetailDO.getAmount() != null) {
                // 不含税金额
                rcvDetailDO.setNtaxAmount(PriceCompute.ntaxAmount(rcvDetailDO.getAmount(), rcvDetailDO.getTaxRate()));
                // 税额 保留4位小数 四舍五入
                rcvDetailDO.setTaxAmount(PriceCompute.taxAmount(rcvDetailDO.getAmount(), rcvDetailDO.getNtaxAmount()));
            }
        }
        return rcvDetailDO;
    }

    public RcvOrderDTO dataConversion(OrderSalesVO orderSalesVO) {
        if (PublicUtil.isEmpty(orderSalesVO)) {
            return null;
        }
        // rcvMaster 值传递
        RcvOrderDTO rcvOrderDTO = BeanCopyUtil.copy(orderSalesVO, RcvOrderDTO.class);
        rcvOrderDTO.setEmployee(orderSalesVO.getEmpSale()); // 外勤
        rcvOrderDTO.setEmployeeNo(orderSalesVO.getEmpOffice()); // 内勤
        if (PublicUtil.isEmpty(orderSalesVO.getOptDate())) { // 操作时间
            rcvOrderDTO.setOptTime(new Date());
        } else {
            rcvOrderDTO.setOptTime(orderSalesVO.getOptDate());
        }

        if (PublicUtil.isEmpty(orderSalesVO.getFullOrderNo())) {
            rcvOrderDTO.setOrOrderNo(orderSalesVO.getRorderNo() + "-" + orderSalesVO.getRorderItem());
        } else {
            rcvOrderDTO.setOrOrderNo(orderSalesVO.getFullOrderNo());
        }
        List<RcvDetailVO> detailVOList = new ArrayList<>();

        // rcvDetail 值传递
        RcvDetailVO rcvDetailVO = BeanCopyUtil.copy(orderSalesVO, RcvDetailVO.class);
        rcvDetailVO.setAmount(orderSalesVO.getAccount());
        rcvDetailVO.setSpecOfferNo(orderSalesVO.getSpcPrice());
        rcvDetailVO.setProductName(orderSalesVO.getCproductName());
        rcvDetailVO.setOrderType(orderSalesVO.getTypeCode());
        if (PublicUtil.isEmpty(orderSalesVO.getFullOrderNo())) {
            rcvDetailVO.setRorderFno(orderSalesVO.getRorderNo() + "-" + orderSalesVO.getRorderItem());
        } else {
            rcvDetailVO.setRorderFno(orderSalesVO.getFullOrderNo());
        }

        detailVOList.add(rcvDetailVO);
        rcvOrderDTO.setItems(detailVOList);

        return rcvOrderDTO;
    }

    /*@Override
    public String tetsTransaction(Boolean flag) {
        OrderSalesRequest orderSalesRequest = new OrderSalesRequest();
        orderSalesRequest.setStatus(0);
        Page page = new Page();
        page.setPageSize(1);
        int pageNum = 1;
        while (true) {
            page.setPageNumber(pageNum);
            PageInfo<OrderSalesDO> queryData = orderSalesService.findAll(orderSalesRequest, page);
            List<OrderSalesVO> orderSalesVOList = BeanCopyUtil.copyList(queryData.getList(), OrderSalesVO.class);
            String conss = conss(orderSalesVOList);
            if (queryData.isIsLastPage()) {
                break;
            }
            pageNum++;
        }
        return "ok";
    }*/

    /*@Override
    public ResultVo<String> receiveOrderFromOrderSales(List<OrderSalesDO> orderSalesDOList) {
        if (orderSalesDOList.isEmpty()) {
            return ResultVo.success();
        }
        StringBuilder result = new StringBuilder("接入成功订单: ");
        LoginUserDTO userDTO = SMCApp.getLoginAuthDto();
        // 按订单号分组接入
        Map<String, List<OrderSalesDO>> orderMap = orderSalesDOList.stream().collect(Collectors.groupingBy(OrderSalesDO::getRorderNo));

        AtomicInteger count = new AtomicInteger();
        String keyPrefix = "rcvorder:";
        String key;
        OrderLogVO orderLogVO;
        OrderStateVO orderStateVO;

        TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
        transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        Boolean addResult;

        for (Map.Entry<String, List<OrderSalesDO>> entry : orderMap.entrySet()) {
            log.info("开始从OrderSales接入订单: {}", entry.getKey());
            result.append(entry.getKey()).append(":");
            count.set(0);
            for (OrderSalesDO orderSalesDO : entry.getValue()) {
                key = keyPrefix + orderSalesDO.getFullOrderNo();
                if (redissonUtil.tryLock(key, 60, 50)) {
                    try {
                        addResult = transactionTemplate.execute(status -> {
                            if (orderSalesDO.getRorderItem() == 1) {
                                //1.写入到rcvmaster
                                RcvMasterDO rcvMasterDO = convertToRcvMasterDO(orderSalesDO);
                                addRcvMaster(rcvMasterDO);
                            }
                            //2.写入到rcvdetail
                            try {
                                RcvDetailDO rcvDetailDO = convertToRcvDetailDO(orderSalesDO);
                                ResultVo<Integer> addItemResult = addRcvDetail(rcvDetailDO);
                                if (!addItemResult.isSuccess()) {
                                    return Boolean.FALSE;
                                }
                            } catch (Exception e) {
                                status.setRollbackOnly();  // 手动回滚
                                log.error("写入recDetail失败: {}", e.getMessage(), e);
                                return Boolean.FALSE;
                            }

                            //3.更新ordersales状态
                            try {
                                orderSalesService.updateOrderSalesToProcessedStatus(orderSalesDO.getRorderNo(), orderSalesDO.getRorderItem());
                            } catch (Exception e) {
                                status.setRollbackOnly();  // 手动回滚
                                log.error("更新ordersales处理状态失败: {}", e.getMessage(), e);
                                return Boolean.FALSE;
                            }

                            count.getAndIncrement();
                            return Boolean.TRUE;
                        });
                        if (!Optional.ofNullable(addResult).orElse(Boolean.FALSE)) {
                            return ResultVo.failure("从OrderSales接入订单失败");
                        }

                        //4.记录日志和写入货期
                        orderLogVO = new OrderLogVO();
                        orderLogVO.setOrderNo(orderSalesDO.getFullOrderNo());
                        orderLogVO.setOptType(12);
                        orderLogVO.setDescription("接入订单");
                        orderLogVO.setOptTime(new Date());
                        orderLogVO.setIp(IpUtil.getIpAddress());
                        if (userDTO != null) {
                            orderLogVO.setOptUserName(userDTO.getUserName());
                        }
                        orderLogService.sendOrderLog(orderLogVO);

                        // 推送货期状态到MQ
                        // OrderStateVO orderStateVO = new OrderStateVO();
                        orderStateVO = BeanCopyUtil.copy(orderSalesDO, OrderStateVO.class);
                        orderStateVO.setOrderNo(orderSalesDO.getFullOrderNo());
                        orderStateVO.setRorderNo(orderSalesDO.getRorderNo());
                        orderStateVO.setItemNo(orderSalesDO.getRorderItem());
                        orderStateVO.setModelNo(orderSalesDO.getModelNo());
                        orderStateVO.setQuantity(orderSalesDO.getQuantity());
                        orderStateVO.setOrderType(orderSalesDO.getTypeCode());
                        orderStateVO.setStateCode(12);
                        if (orderSalesDO.getCdlvDate() != null) {
                            orderStateVO.setCustDlvDate(orderSalesDO.getCdlvDate());
                        }
                        orderStateVO.setStateDes("接入订单");
                        orderStateVO.setCustShipDate(orderSalesDO.getWarehouseSendDate());
                        orderStateVO.setStateType(1);
                        orderStateVO.setStateDate(new Date());
//                        orderStateVO.setFirstDate(new Date());
//                        orderStateVO.setMinDate(new Date());
//                        orderStateVO.setMaxDate(new Date());
                        orderStateVO.setProvider("OPS");
                        orderStateVO.setCustomerNo(orderSalesDO.getCustomerNo());
                        orderStateVO.setUserNo(orderSalesDO.getUserNo());
                        orderStateVO.setDeptNo(orderSalesDO.getDeptNo());
                        orderStateVO.setPoTransType(orderSalesDO.getTransChannel());
                        orderStateVO.setTransType(orderSalesDO.getTransChannel());
                        orderStateVO.setShikomiNo(orderSalesDO.getShikomiNo());
                        if (userDTO != null) {
                            orderStateVO.setOptUserNo(StringUtils.isBlank(userDTO.getUserNo()) ? "" : userDTO.getUserNo());
                            orderStateVO.setOptUserName(StringUtils.isBlank(userDTO.getRealName()) ? "" : userDTO.getRealName());
                        }
                        if (orderSalesDO.getOptDate() != null) {
                            orderStateVO.setReceiveDate(orderSalesDO.getOptDate());
                        }
                        orderStateService.addOrderState(orderStateVO);
                    } catch (Exception e) {
                        result.append(count.get()).append("项. ");
                        log.error("从OrderSales接入订单失败: {}, 异常信息:{}", orderSalesDO, e.getMessage(), e);
                        return ResultVo.failure(result.toString() + " 接入失败订单: " + orderSalesDO.getFullOrderNo());
                    } finally {
                        redissonUtil.unlock(key);
                    }

                }
            }
            result.append(count.get()).append("项. ");
        }
        return ResultVo.success(result.toString());
    }*/

    @Override
    public ResultVo<RcvOrderDTO> getfullOrderInfo(String orderNo) {
        if (PublicUtil.isEmpty(orderNo)) {
            return ResultVo.failure("订单号不可为空");
        }
        // 1.获取rcvMaster信息
        LambdaQueryWrapper<RcvMasterDO> queryMaster = Wrappers.lambdaQuery();
        queryMaster.eq(RcvMasterDO::getRorderNo, orderNo);
        RcvMasterDO rcvMasterDO = rcvmasterMapper.selectOne(queryMaster);
        if (rcvMasterDO == null) {
            return ResultVo.failure("获取订单主体信息失败");
        }
        RcvOrderDTO rcvOrderDTO = BeanCopyUtil.copy(rcvMasterDO, RcvOrderDTO.class);

        // 2.获取订单明细信息
        LambdaQueryWrapper<RcvDetailDO> queryDetail = Wrappers.lambdaQuery();
        queryDetail.eq(RcvDetailDO::getRorderNo, orderNo);
        List<RcvDetailDO> rcvDetailDOList = rcvdetailMapper.selectList(queryDetail);
        List<RcvDetailVO> rcvDetailVOList = BeanCopyUtil.copyList(rcvDetailDOList, RcvDetailVO.class);
        rcvOrderDTO.setItems(rcvDetailVOList);

        // 3. 获取收货地址信息
        LambdaQueryWrapper<OrderDlvDataDO> queryOrderDlv = Wrappers.lambdaQuery();
        queryOrderDlv.eq(OrderDlvDataDO::getOrderNo, orderNo);
        OrderDlvDataDO orderDlvDataDO = orderDlvDataMapper.selectOne(queryOrderDlv);
        OrderDlvDataVO orderDlvDataVO = BeanCopyUtil.copy(orderDlvDataDO, OrderDlvDataVO.class);
        rcvOrderDTO.setAddressInfo(orderDlvDataVO);

        return ResultVo.success(rcvOrderDTO);
    }

    @Override
    public ResultVo<RcvDetailDTO> getOrderDetailInfo(String rorderFno) {
        LambdaQueryWrapper<RcvDetailDO> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(RcvDetailDO::getRorderFno, rorderFno);
        RcvDetailDO rcvDetailDO = rcvdetailMapper.selectOne(queryWrapper);
        if (rcvDetailDO == null) {
            return ResultVo.success(null);
        }
        RcvDetailDTO rcvDetailDTO = new RcvDetailDTO();
        rcvDetailDTO.setRorderFno(rcvDetailDO.getRorderFno());
        rcvDetailDTO.setStatus(rcvDetailDO.getStatus());
        return ResultVo.success(rcvDetailDTO);
    }

    @Override
    public ResultVo<List<RcvOrderInfoVO>> getRcvOrderInfos(RcvOrderInfosDTO dto) {
        if (PublicUtil.isEmpty(dto.getOrderNos())) {
            return ResultVo.success(null, "请输入订单全号");
        }
        if (PublicUtil.isEmpty(dto.getOrderType())) {
            return ResultVo.success(null, "请输入订单类型");
        }

        // 判断权限
        List<String> userAuth = new ArrayList<>();
        List<String> deptAuth = new ArrayList<>();
        List<String> customerAuth = new ArrayList<>();
        if (dto.getDataAuthoritySearchCondition() != null) {
            if (null != dto.getDataAuthoritySearchCondition().getUserIdListByDataAuthority()
                    && !dto.getDataAuthoritySearchCondition().getUserIdListByDataAuthority().isEmpty()) {
                userAuth = dto.getDataAuthoritySearchCondition().getUserIdListByDataAuthority();
            }
            if (null != dto.getDataAuthoritySearchCondition().getDeptCodeListByDataAuthority()
                    && !dto.getDataAuthoritySearchCondition().getDeptCodeListByDataAuthority().isEmpty()) {
                deptAuth = dto.getDataAuthoritySearchCondition().getDeptCodeListByDataAuthority();
            }
            if (null != dto.getDataAuthoritySearchCondition().getCustomerCodeListByDataAuthority()
                    && !dto.getDataAuthoritySearchCondition().getCustomerCodeListByDataAuthority().isEmpty()) {
                customerAuth = dto.getDataAuthoritySearchCondition().getCustomerCodeListByDataAuthority();
            }
        }
        List<String> finalUserAuth = userAuth;
        List<String> finalDeptAuth = deptAuth;
        List<String> finalCustomerAuth = customerAuth;

        List<RcvOrderInfoVO> list = new ArrayList<>(dto.getOrderNos().size());

        log.info("批量查询订单的基本信息和状态 参数: " + JSONObject.toJSONString(dto));
        LambdaQueryWrapper<RcvDetailDO> queryDetail = Wrappers.lambdaQuery();
        LambdaQueryWrapper<RcvMasterDO> queryMaster = Wrappers.lambdaQuery();
        RcvDetailDO detailDO;
        RcvMasterDO masterDO;
        RcvOrderInfoVO infoVO;
        //ResultVo<String> customerName;
        LambdaQueryWrapper<OrderStateDO> stateWrapper = Wrappers.lambdaQuery();
        OrderStateDO stateDO;

        for (String orderNo : dto.getOrderNos()) {
            //1.去revdetail查信息
            queryDetail.clear();
            queryDetail.eq(RcvDetailDO::getRorderFno, orderNo);
            queryDetail.eq(RcvDetailDO::getOrderType, dto.getOrderType());
            detailDO = rcvdetailMapper.selectOne(queryDetail);
            if (detailDO == null) {
                continue;
            }
            infoVO = BeanCopyUtil.copy(detailDO, RcvOrderInfoVO.class);
            infoVO.setEprice(detailDO.getEPrice());
            infoVO.setRorderItemNo(detailDO.getRorderItem());

            //2.去revMaster表查信息
//            QueryWrapper<RcvMasterDO> queryWrapper = new QueryWrapper<>();
//            queryWrapper.eq("rorder_no", detailDO.getRorderNo());
            queryMaster.clear();
            queryMaster.select(RcvMasterDO::getRorderNo, RcvMasterDO::getCustomerNo, RcvMasterDO::getUserNo);
//            queryMaster
//                    .eq(RcvMasterDO::getRorderNo, detailDO.getRorderNo())
//                    // 判断部门权限
//                    .or().in(PublicUtil.isNotEmpty(finalDeptAuth), RcvMasterDO::getDeptNo, finalDeptAuth)
//                    // 判断客户权限
//                    .or().in(PublicUtil.isNotEmpty(finalCustomerAuth), RcvMasterDO::getCustomerNo, finalCustomerAuth)
//                    // 判断用户权限
//                    .or().in(PublicUtil.isNotEmpty(finalUserAuth), RcvMasterDO::getUserNo, finalUserAuth);

            queryMaster.eq(RcvMasterDO::getRorderNo, detailDO.getRorderNo());
            if (PublicUtil.isNotEmpty(finalDeptAuth) || PublicUtil.isNotEmpty(finalCustomerAuth) || PublicUtil.isNotEmpty(finalUserAuth)) {
                queryMaster.and(wrapper -> wrapper.in(PublicUtil.isNotEmpty(finalDeptAuth), RcvMasterDO::getDeptNo, finalDeptAuth)
                        .or().in(PublicUtil.isNotEmpty(finalDeptAuth), RcvMasterDO::getHlCode, finalDeptAuth)
                        .or().in(PublicUtil.isNotEmpty(finalCustomerAuth), RcvMasterDO::getCustomerNo, finalCustomerAuth)
                        .or().in(PublicUtil.isNotEmpty(finalUserAuth), RcvMasterDO::getUserNo, finalUserAuth));
            }
//

            masterDO = rcvmasterMapper.selectOne(queryMaster);
            if (masterDO == null) {
                continue;
            }
            infoVO.setUserNo(masterDO.getUserNo());
            infoVO.setCustomerNo(masterDO.getCustomerNo());

            infoVO.setUserName(opsCommonService.getCustomerNameByNo(infoVO.getUserNo()));
            infoVO.setCustomerName(opsCommonService.getCustomerNameByNo(infoVO.getCustomerNo()));

            //3.根据状态获取状态名称
            if (detailDO.getStatus() != null) {
                infoVO.setStatusName(RCVOrderStatusEnum.getName(detailDO.getStatus()));
            }

            //4.根据订单全号去orderstate表里查货期状态
            stateWrapper.clear();
            stateWrapper.select(OrderStateDO::getOrderNo, OrderStateDO::getStateDes);
            stateWrapper.eq(OrderStateDO::getOrderNo, infoVO.getRorderFno());
            stateDO = orderStateMapper.selectOne(stateWrapper);
            if (stateDO != null) {
                infoVO.setStatusText(stateDO.getStateDes());
            }
            list.add(infoVO);
        }

        if (PublicUtil.isEmpty(list)) {
            return ResultVo.success(null, "未查询到订单信息");
        }

        return ResultVo.success(list);
    }

    @Override
    public ResultVo<List<SalesInvoiceDTO>> getInvoiceForReturn(SalesInvoiceRequest request) {
        List<SalesInvoiceDTO> dtoList = new ArrayList<>();
        SalesInvoiceDTO dto;
        //QueryWrapper<SalesInvoiceDO> queryWrapper = new QueryWrapper<>();
        List<SalesInvoiceDO> list;
        ResultVo<List<ModelExpFreqVO>> all;
        SalesInvoiceDO invoiceDO;

        for (String orderNo : request.getOrderNos()) {
            dto = new SalesInvoiceDTO();
            // 样品订单
            //add by WuJiaWen 2022/10/21 bug 8414
            if (orderNo.startsWith("S") || orderNo.startsWith("9")) {
//                invoiceDO = rcvdetailMapper.getSampOrderData(orderNo,request.getCustomerNo());
//                if (invoiceDO == null) {
//                    continue;
//                }
//                dto.setQuantity(invoiceDO.getQuantity());
                continue;
            } else {
                // 非样品订单
                list = salesInvoiceMapper.selectInvoiceForReturn(orderNo, request.getCustomerNo());
                if (CollectionUtils.isEmpty(list)) {
                    continue;
                }
                invoiceDO = list.get(0);
                dto.setQuantity(list.stream().mapToInt(SalesInvoiceDO::getQuantity).sum());
                if (dto.getQuantity() == 0) {
                    continue;
                }
                List<String> invoiceNoList = list.stream().map(SalesInvoiceDO::getInvoiceNo).filter(StringUtils::isNotBlank).distinct().collect(Collectors.toList());
                dto.setInvoiceNoList(invoiceNoList);
            }
            if (invoiceDO == null) {
                continue;
            }
            Date shipTime = null;
            //add by WuJiaWen 2022/10/25 bug 8450
            RcvDetailVO detailVO = rcvdetailMapper.getRcvdetailQtyByNo(orderNo);
            if (detailVO != null) {
                // 14456bug lyc [退货]退货模块那新增一个出库区分字段
                String desc = "";
                if(StringUtils.isNotBlank(detailVO.getStockCode()) && StringUtils.isNotBlank(detailVO.getStockType())) {
                    String typeName = OrderStockTypeEnum.getTypeName(detailVO.getStockType());
                    desc = typeName+"["+detailVO.getStockCode()+"]";
                } else if(StringUtils.isNotBlank(detailVO.getStockCode()) && StringUtils.isBlank(detailVO.getStockType())) {
                    desc = detailVO.getStockCode();
                } else if(StringUtils.isBlank(detailVO.getStockCode()) && StringUtils.isNotBlank(detailVO.getStockType())) {
                    desc = OrderStockTypeEnum.getTypeName(detailVO.getStockType());
                }
                dto.setStockCodeDescription(desc);

                // WuJiaWen 2023/07/24 bug11569
                shipTime = detailVO.getShipTime(); //rcvdetail表里的发货时间
                if (shipTime == null) { //如果rcvdetail表的发货时间为空，则取expdetail表的
                    shipTime = expdetailMapper.seleShipDateByNo(orderNo);
                }
                dto.setShipTime(shipTime);

            }
            if (detailVO!= null && detailVO.getQuantity() != null && detailVO.getQuantity() != 0) {
                dto.setQuantity(detailVO.getQuantity());
            }
            dto.setOrderNo(invoiceDO.getROrderNo());
            dto.setModelNo(invoiceDO.getModelNo());
            dto.setPrice(invoiceDO.getPrice());
            dto.setInvoiceNo(invoiceDO.getInvoiceNo());
            dto.setInvoiceDate(invoiceDO.getInvDate());
            dto.setReturnFeeRate(invoiceDO.getTaxRate());
            // 通过型号获取频率
            ModelExpFreqRequest freqRequest=new ModelExpFreqRequest();
            freqRequest.setModelTYpe("2");
            freqRequest.setModelNo(invoiceDO.getModelNo());
            freqRequest.setStockcode( "ALL");
            freqRequest.setStockType(0);

            all = binServiceFeignApi.getModelExpFreq(freqRequest);
            if (all.isSuccess() && all.getData() != null) {
                dto.setFreq(all.getData().get(0).getMonthsOf12() != null ? all.getData().get(0).getMonthsOf12() : 0);
                dto.setCustomersOf12(all.getData().get(0).getCustomersOf12() != null ? all.getData().get(0).getCustomersOf12() : 0);

                if (dto.getCustomersOf12() <= 1 || (dto.getFreq() >= 0 && dto.getFreq() <= 3)) {
                    dto.setProductRiskLevel("高风险");
                }

                if (dto.getFreq() >= 4 && dto.getFreq() <= 8) {
                    dto.setProductRiskLevel("中风险");
                }

                if (dto.getFreq() >= 9 && dto.getFreq() <= 13) {
                    dto.setProductRiskLevel("低风险");
                }
            } else {
                dto.setProductRiskLevel("高风险");
            }
            dtoList.add(dto);
        }

        return ResultVo.success(dtoList);
    }


//    private static String Authorization = "";
//    private final static String tokenParams = "username=testsystem&password=123456";
//    private final static String url = "http://10.116.1.233/api/auth/login";
//    private final static String calSMCGuidingPrice_url = "http://10.116.1.233/api/sps/price/calSMCGuidingPrice";

    enum PriceErrorEnum{
        NOPRICE_MODEL("NOMODEL","P_M","无集团价格，请先登记:产品型号 {{modelno}} 未登录价格，请等待处理"),
        NOPRICE_PRICE("NOPRICE","P_P","无集团价格，请先登记:产品型号  {{modelno}}  暂无价格信息"),
        NOPRICE_SHIKOMI("ERRORSHIKOMI","P_S","无集团价格，请先登记:shikomi号不可用"),
        NOPRICE_EPRICE("ERROREPRICE","P_EP","无集团价格，请先登记:产品型号  {{modelno}}  E价格设置不正确"),
        NOPRICE_SOURCE("ERRORSOURCETYPE","P_S","无集团价格，请先登记:该型号{}无产品来源，请确认！"),
        NOPRICE_RATE("ERRORERATE","P_R","无集团价格，请先登记:开票双方未设置对应E率，请确认！"),
        NOPRICE_APIRESULT("","P_A","无集团价格，请先登记:{{modelno}} 计算价格失败:{{errorMsg}}"),
        ;


        private String srcCode;
        private String opsCode;
        private String msg;

        PriceErrorEnum(String src, String code, String msg) {
            this.srcCode = src;
            this.opsCode = code;
            this.msg = msg;
        }

        public String getSrcCode() {
            return srcCode;
        }

        public String getOpsCode() {
            return opsCode;
        }

        public String getMsg() {
            return msg;
        }

        public static PriceErrorEnum getByCode(String sourceCode) {
            for (PriceErrorEnum errorEnum : PriceErrorEnum.values()) {
                if (errorEnum.getSrcCode().equals(sourceCode)) {
                    return errorEnum;
                }
            }
            return NOPRICE_APIRESULT;
        }

    }


    @Override
    public ResultVo<CalSMCGuidingPriceEntity> calSMCGuidingPrice(String sellerGroupNo, String buyerGroupNo,
                                                                 String modelNo, int quantity, String shikomiId) {

        String token = commonOutSideInterfaceAuthService.getyingyeSysToken();
        HttpResponse response;
        String accessurl = yingyeSysAuthUrl+"/api/sps/price/calSMCGuidingPrice" + "?sellerGroupNo=" + sellerGroupNo + "&buyerGroupNo=" + buyerGroupNo +
                "&modelNo=" + modelNo + "&quantity=" + quantity + "&shikomiId=" + shikomiId;
        response = HttpUtil.createGet(accessurl)
                .header("Authorization", token)
                .header("Content-Type", "application/json;charset=UTF-8")
                .charset(StandardCharsets.UTF_8)
                .execute();

        String strResponse = response.body();
        log.info(strResponse);
        if (StringUtils.isBlank(strResponse)) {
            return ResultVo.failure(modelNo + "计算价格失败[接口响应空]");
        }

        CalSMCGuidingPriceEntity calSMCGuidingPriceEntity = null;
        try {
            if (!strResponse.startsWith("<oauth>")) {
                calSMCGuidingPriceEntity = JSONObject.parseObject(strResponse, CalSMCGuidingPriceEntity.class);
            }
        } catch (Exception e) {
            log.error("集团内客户交易价格计算失败: {}", e.getMessage(), e);
            return ResultVo.failure(modelNo + "计算价格失败:" + e.getMessage());
        }

        if (null == calSMCGuidingPriceEntity || calSMCGuidingPriceEntity.getSuccess() == null) {
            return ResultVo.failure(modelNo + "计算价格失败.接口返回:" + strResponse);
        }

        if (calSMCGuidingPriceEntity.getSuccess()) {
            return ResultVo.success(calSMCGuidingPriceEntity);
        } else {
            return ResultVo.failure(calSMCGuidingPriceEntity, ResultVo.DEFAULT_ERROR_RESULT_CODE, calSMCGuidingPriceEntity.getMessage());
        }
    }

    @Override
    public ResultVo<String> calcOrderPriceSMCGroupCustomer() {
        // redis记录缓存key前缀
        // String redisKey = "ops:calcOrderPrice:";
        // 获取价格为空.为0的订单
        List<RcvDetailDO> detailDOList = getRcvDetailForPriceIsZero();
        if (null == detailDOList || detailDOList.isEmpty()) {
            return ResultVo.success("暂无数据需要获取价格");
        }
        Map<String, List<RcvDetailDO>> map = new HashMap<>();

        for (RcvDetailDO item : detailDOList) {
            List<RcvDetailDO> list = new ArrayList<>();
            if (!map.containsKey(item.getRorderNo())) {
                list.add(item);
                map.put(item.getRorderNo(), list);
            } else {
                map.get(item.getRorderNo()).add(item);
            }

        }
        int count = 1;
        for (String key : map.keySet()) {
            // 根据子项订单号查出主项订单-客户代码
            LambdaQueryWrapper<RcvMasterDO> queryWrapper = Wrappers.lambdaQuery();
            queryWrapper.eq(RcvMasterDO::getRorderNo, key);
            RcvMasterDO rcvMasterDO = rcvmasterMapper.selectOne(queryWrapper);
            // 根据客户代码获取客户集团Id
            ResultVo<TblGroupcustomerVO> tblGroupCustInfo = commonServiceFeignApi.getTblGroupCustInfo(rcvMasterDO.getCustomerNo());
            if (!tblGroupCustInfo.isSuccess() && tblGroupCustInfo.getData() == null) {
                continue;
            }
            TblGroupcustomerVO data1 = tblGroupCustInfo.getData();
            for (RcvDetailDO item : map.get(key)) {
                // 获取失败次数, 当失败3次之后 不再计算
//                Object o = redisManager.get(redisKey+item.getRorderFno());
//                int errorCount = 1;
//                if (o != null) {
//                    errorCount = Integer.parseInt(o.toString());
//                     if (errorCount > 3) {
//                         continue;
//                     }
//                }

                ResultVo<CalSMCGuidingPriceEntity> priceResultVo = calSMCGuidingPrice("CN0", data1.getCompanyId(), item.getModelNo(), item.getQuantity(), "");
                if (!priceResultVo.isSuccess()) {
                    // redisManager.set(redisKey+item.getRorderFno(),errorCount+1);
                    if (StringUtils.isBlank(item.getExpMsg())) {
                        item.setExpMsg(priceResultVo.getMessage());
                    } else {
                        String expMsg = item.getExpMsg();
                        if (expMsg.contains(priceResultVo.getMessage())) {
                            item.setExpMsg(expMsg);
                        } else {
                            item.setExpMsg(expMsg + ";" + priceResultVo.getMessage());
                        }
                    }
                    // update 14501bug lyc 20240628
//                    // 只有状态为暂不处理的订单 计算不出价格 修改为异常订单
//                    if (item.getStatus() == 11) {
//                        item.setStatus(10);
//                    }
                    if (!"C1D72".equals(tblGroupCustInfo.getData().getCustomerNo())) {

                        ResultVo<DataTypeVO> dataTypeCodesInfoWithDS = dictCommonService.getDataTypeCodesInfoWithDS("9002", "30");
                        if (dataTypeCodesInfoWithDS.isSuccess() && dataTypeCodesInfoWithDS.getData() != null) {
                            String extNote1 = dataTypeCodesInfoWithDS.getData().getExtNote1();
                            if (extNote1 != null) {
                                if (extNote1.equals("1")) {
                                    // 19090 写入事件，暂不处理
                                    PriceErrorEnum errorEnum ;
                                    //将远程接口定义的错误代码解析为OPS定义的错误代码
                                    if (priceResultVo.getData() != null) {
                                        CalSMCGuidingPriceEntity errorResult = priceResultVo.getData();
                                        errorEnum = PriceErrorEnum.getByCode(errorResult.getErrorCode());
                                    } else {
                                        errorEnum = PriceErrorEnum.NOPRICE_APIRESULT;
                                    }
                                    //创建事件报文:暂不处理异常代码和描述,msg为ops定义的异常编码编码，data为异常描述
                                    CommonResult<String> eventParams = CommonResult.failure("无集团价格，请先登记:" + priceResultVo.getMessage(), errorEnum.getOpsCode());
                                    insertEvent(item, "CUSTOMER_ORDER_NOT_ALLOT",JSONUtil.toJsonStr(eventParams));
                                }

                                if (extNote1.equals("2")) {
                                    // 计算价格失败.回改中国制造订单
                                    if (StringUtils.isNotBlank(item.getCorderNo())) {
                                        OrderNoInfo orderNoInfo = new OrderNoInfo().convertFullOrderNo(item.getCorderNo());
                                        LambdaUpdateWrapper<OpsVManuorderToSales> updateWrapper = Wrappers.lambdaUpdate();
                                        updateWrapper
                                                .eq(OpsVManuorderToSales::getOrderno, orderNoInfo.getOrderNo())
                                                .eq(OpsVManuorderToSales::getItemno, orderNoInfo.getItemNo())
                                                .set(OpsVManuorderToSales::getSalesStatus, 1)
                                                .set(OpsVManuorderToSales::getTaxRate, item.getTaxRate())
                                                .set(OpsVManuorderToSales::getSalesRemark, "无集团价格，请先登记:" + priceResultVo.getMessage());
                                        try {
                                            opsVManuorderToSalesMapper.update(null, updateWrapper);

                                        } catch (Exception e) {
                                            log.error("回改 OPS_V_ManuOrderToSales 视图未税价格 异常信息: {}, 异常订单号 {}", e, item.getCorderNo());
                                        }
                                    }
                                }

                                if (extNote1.equals("3")) {
                                    // 计算价格失败.回改中国制造订单
                                    if (StringUtils.isNotBlank(item.getCorderNo())) {
                                        OrderNoInfo orderNoInfo = new OrderNoInfo().convertFullOrderNo(item.getCorderNo());
                                        LambdaUpdateWrapper<OpsVManuorderToSales> updateWrapper = Wrappers.lambdaUpdate();
                                        updateWrapper
                                                .eq(OpsVManuorderToSales::getOrderno, orderNoInfo.getOrderNo())
                                                .eq(OpsVManuorderToSales::getItemno, orderNoInfo.getItemNo())
                                                .set(OpsVManuorderToSales::getSalesStatus, 1)
                                                .set(OpsVManuorderToSales::getTaxRate, item.getTaxRate())
                                                .set(OpsVManuorderToSales::getSalesRemark, "无集团价格，请先登记:" + priceResultVo.getMessage());
                                        try {
                                            opsVManuorderToSalesMapper.update(null, updateWrapper);

                                        } catch (Exception e) {
                                            log.error("回改 OPS_V_ManuOrderToSales 视图未税价格 异常信息: {}, 异常订单号 {}", e, item.getCorderNo());
                                        }
                                    }
                                    // 19090 写入事件，暂不处理
                                    PriceErrorEnum errorEnum = PriceErrorEnum.NOPRICE_APIRESULT;
                                    //将远程接口定义的错误代码解析为OPS定义的错误代码
                                    if (priceResultVo.getData() != null) {
                                        CalSMCGuidingPriceEntity errorResult = priceResultVo.getData();
                                        errorEnum = PriceErrorEnum.getByCode(errorResult.getErrorCode());
                                    }
                                    //创建事件报文:暂不处理异常代码和描述
                                    CommonResult<String> eventParams = CommonResult.failure("无集团价格，请先登记:" + priceResultVo.getMessage(), errorEnum.getOpsCode());
                                    insertEvent(item, "CUSTOMER_ORDER_NOT_ALLOT",JSONUtil.toJsonStr(eventParams));
                                }
                            } else {
                                // 计算价格失败.回改中国制造订单
                                if (StringUtils.isNotBlank(item.getCorderNo())) {
                                    OrderNoInfo orderNoInfo = new OrderNoInfo().convertFullOrderNo(item.getCorderNo());
                                    LambdaUpdateWrapper<OpsVManuorderToSales> updateWrapper = Wrappers.lambdaUpdate();
                                    updateWrapper
                                            .eq(OpsVManuorderToSales::getOrderno, orderNoInfo.getOrderNo())
                                            .eq(OpsVManuorderToSales::getItemno, orderNoInfo.getItemNo())
                                            .set(OpsVManuorderToSales::getSalesStatus, 1)
                                            .set(OpsVManuorderToSales::getTaxRate, item.getTaxRate())
                                            .set(OpsVManuorderToSales::getSalesRemark, "无集团价格，请先登记:" + priceResultVo.getMessage());
                                    try {
                                        opsVManuorderToSalesMapper.update(null, updateWrapper);

                                    } catch (Exception e) {
                                        log.error("回改 OPS_V_ManuOrderToSales 视图未税价格 异常信息: {}, 异常订单号 {}", e, item.getCorderNo());
                                    }
                                }
                            }
                        }
                    }
                } else {
                    ReferencePrice data = priceResultVo.getData().getPrice();
                    item.setPrice(data.getPriceIncludingTax());

                    if (item.getTaxRate() == null) {
                        ResultVo<DataTypeVO> dataTypeCodesInfo = dictCommonService.getDataTypeCodesInfo("9002", "1");
                        if (dataTypeCodesInfo.isSuccess() && dataTypeCodesInfo.getData() != null) {
                            item.setTaxRate(new BigDecimal(dataTypeCodesInfo.getData().getExtNote1()));
                        } else {
                            item.setTaxRate(new BigDecimal("0.13"));
                        }
                    }

                    if (item.getAmount() == null || item.getAmount().compareTo(BigDecimal.ZERO) == 0) {
                        item.setAmount(item.getPrice().multiply(new BigDecimal(item.getQuantity())));
                    }
                    // 不含税单价 保留4位小数 四舍五入
                    item.setNtaxPice(PriceCompute.unitPriceExcludingTax(item.getPrice(), item.getTaxRate()));
                    // 不含税金额
                    item.setNtaxAmount(PriceCompute.ntaxAmount(item.getAmount(), item.getTaxRate()));
                    // 税额 保留4位小数 四舍五入
                    item.setTaxAmount(PriceCompute.taxAmount(item.getAmount(), item.getNtaxAmount()));

                    item.setPriceEndUser(item.getPrice());

                    BigDecimal discount = CommonFormulaUtil.calcDiscount(item.getNtaxPice(), item.getEPrice());
                    item.setDiscount(discount);

                    // 只有状态为暂不处理的订单 计算出价格 修改状态为待处理 其他状态只计算价格 不修改状态
                    if (item.getStatus() == 11 || item.getStatus() == 10) {
                        item.setStatus(1);
                        item.setExpMsg("");
                    }
                    // 广州制造订单不回改
                    if (!"C1D72".equals(tblGroupCustInfo.getData().getCustomerNo())) {
                        // 回改 OPS_V_ManuOrderToSales 视图未税价格
                        if (data.getPriceExcludingTax() != null) {
                            if (StringUtils.isNotBlank(item.getCorderNo())) {
                                String[] corderNo = item.getCorderNo().split("-");
                                LambdaUpdateWrapper<OpsVManuorderToSales> updateWrapper = Wrappers.lambdaUpdate();
                                updateWrapper
                                        .eq(OpsVManuorderToSales::getOrderno, corderNo[0])
                                        .eq(OpsVManuorderToSales::getItemno, corderNo[1])
                                        .set(OpsVManuorderToSales::getSalesStatus, 2)
                                        .set(OpsVManuorderToSales::getSalesRemark, "订单受理成功")
                                        .set(OpsVManuorderToSales::getTaxRate, item.getTaxRate())
                                        .set(OpsVManuorderToSales::getUnitprice, data.getPriceExcludingTax());
                                try {
                                    opsVManuorderToSalesMapper.update(null, updateWrapper);
                                } catch (Exception e) {
                                    log.error("回改 OPS_V_ManuOrderToSales 视图未税价格 异常信息: {}, 异常订单号 {}", e, item.getCorderNo());
                                }
                            }
                        }
                    }
                }
                rcvdetailMapper.updateById(item);
                count++;
            }
        }
        return ResultVo.success("执行完毕.共计操作" + count + "条数据");
    }

    @Override
    public ResultVo<RcvDetailVO> findRcvDetailWithIsAssOrder(String fullOrderNo) {

        LambdaQueryWrapper<RcvDetailDO> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper
                .eq(RcvDetailDO::getRorderFno, fullOrderNo)
                .eq(RcvDetailDO::getProdFlag, 2);
        RcvDetailDO rcvDetailDO = rcvdetailMapper.selectOne(queryWrapper);
        if (rcvDetailDO == null) {
            return ResultVo.success(null, "暂无数据");
        }
        return ResultVo.success(BeanCopyUtil.copy(rcvDetailDO, RcvDetailVO.class));
    }

    @Override
    public ResultVo<RcvSaleDTO> getSalesDateByModelNo(String modelNo, String applyDate) {
        Date date = DateUtil.stringToDate(applyDate);
        List<String> list = rcvdetailMapper.getShikomiModels(modelNo);
        RcvSaleDTO saleDate = rcvdetailMapper.getSaleDate(list, date, DateUtil.getNow());
//        if (saleDate != null) {
//            saleDate.setSaleStartTime(DateUtil.addMonth(date, 1));
//            saleDate.setSaleEndTime(DateUtil.addMonth(saleDate.getSaleEndTime(), -1));
//        }

        return ResultVo.success(saleDate);
    }

    public List<RcvDetailDO> getRcvDetailForPriceIsZero() {

        ResultVo<List<DataCodeVO>> dataCodes = dictCommonService.getDataCodesNotCache("1001");
        if (!dataCodes.isSuccess() || CollectionUtils.isEmpty(dataCodes.getData())) {
            throw new BusinessException("未找到1001数据字典");
        }

        List<String> status = new ArrayList<>();
        for (DataCodeVO dataCodeVO : dataCodes.getData()) {
            if ("1".equals(dataCodeVO.getExtNote1())) {
                status.add(dataCodeVO.getCode());
            }
        }

        LambdaQueryWrapper<RcvDetailDO> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper
                .eq(RcvDetailDO::getPrice, 0)
                .in(CollectionUtils.isNotEmpty(status),RcvDetailDO::getStatus, status)
                .eq(RcvDetailDO::getOrderType, 11);
        List<RcvDetailDO> rcvDetailDOList = rcvdetailMapper.selectList(queryWrapper);
        if (rcvDetailDOList.isEmpty()) {
            return null;
        }
        return rcvDetailDOList;
    }

    public String getToken(String url, String tokenParams) {
        String auth = "";
        String s = HttpRequest.sendPost(url, tokenParams);
        if (StringUtils.isNotBlank(s)) {
            ClientDetails clientDetails = new Gson().fromJson(s, ClientDetails.class);
            if (clientDetails.getAccess_token() != null) {
                auth = clientDetails.getAccess_token();
            }
        }
        if (auth == null || "".equals(auth)) {
            throw new BusinessException("Authorization is null.");
        }
        return auth;
    }

    // @Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRED)
    /*public String conss(List<OrderSalesVO> orderSalesVOList) {
        TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
        transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        transactionTemplate.execute(status -> {
            RcvDetailDO rcvDetailDO = new RcvDetailDO();
            OrderLogDO orderLogDO = new OrderLogDO();
            for (OrderSalesVO orderSalesVO : orderSalesVOList) {
                rcvDetailDO.setRorderNo(orderSalesVO.getRorderNo());
                rcvDetailDO.setRorderItem(orderSalesVO.getRorderItem());
                rcvDetailDO.setOrderType(1);
                rcvDetailDO.setModelNo("adwada");
                rcvDetailDO.setQuantity(1);
                try {
                    rcvdetailMapper.insert(rcvDetailDO);
                } catch (Exception e) {
                    throw new BusinessException("rcvdetailMapper insert error");
                }
                orderLogDO.setOrderNo(orderSalesVO.getRorderNo() + "-" + orderSalesVO.getRorderItem());
                try {
                    orderLogService.addLog(orderLogDO);
                } catch (Exception e) {
                    throw new BusinessException("orderLogService insert error");
                }
                orderSalesVO.setStatus("1");
                OrderSalesDO copy = BeanCopyUtil.copy(orderSalesVO, OrderSalesDO.class);
                try {
                    orderSalesService.updateOrderSales(copy);
                } catch (Exception e) {
                    throw new BusinessException("orderSalesService insert error");
                }
            }
            return true;
        });
        return "insert ok";
    }*/
}
