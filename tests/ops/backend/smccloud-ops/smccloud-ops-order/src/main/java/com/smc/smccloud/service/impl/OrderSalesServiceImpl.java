package com.smc.smccloud.service.impl;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sales.ops.dto.ips.IpsReceiveDeliveryInfoFromAllVO;
import com.smc.smccloud.core.exception.BusinessException;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.model.constants.Constants;
import com.smc.smccloud.core.model.dto.LoginUserDTO;
import com.smc.smccloud.core.model.enums.CarrierEnum;
import com.smc.smccloud.core.model.enums.OrderTypeEnum;
import com.smc.smccloud.core.model.page.Page;
import com.smc.smccloud.core.rabbitmq.RabbitMqMessage;
import com.smc.smccloud.core.redis.RedisManager;
import com.smc.smccloud.core.redis.redisson.RedissonUtil;
import com.smc.smccloud.core.utils.*;
import com.smc.smccloud.mapper.*;
import com.smc.smccloud.model.DataCodeVO;
import com.smc.smccloud.model.DataTypeVO;
import com.smc.smccloud.model.OpsEventBusDO;
import com.smc.smccloud.model.OrderSales.*;
import com.smc.smccloud.model.ProductPriceDO;
import com.smc.smccloud.model.Purchase.OpsPurchaseInvoiceDO;
import com.smc.smccloud.model.VSalesManuorder.OpsVManuorderToSales;
import com.smc.smccloud.model.VSalesManuorder.VSalesManuorderDO;
import com.smc.smccloud.model.VSalesManuorder.VSalesManuorderParams;
import com.smc.smccloud.model.VSalesManuorder.VSalesManuorderVO;
import com.smc.smccloud.model.adapter.order.AdapterOrderResult;
import com.smc.smccloud.model.customer.CustomerVO;
import com.smc.smccloud.model.customer.TblGroupcustomerVO;
import com.smc.smccloud.model.inventory.InventoryRequestDTO;
import com.smc.smccloud.model.ips.AssembleContentDto;
import com.smc.smccloud.model.ips.AssembleOrderDto;
import com.smc.smccloud.model.ips.ReceiveContentInfoDto;
import com.smc.smccloud.model.order.IpsSendOrderToOpsDO;
import com.smc.smccloud.model.order.OrderNoInfo;
import com.smc.smccloud.model.order.RcvDetailDO;
import com.smc.smccloud.model.order.RcvMasterDO;
import com.smc.smccloud.model.orderlog.OrderLogVO;
import com.smc.smccloud.model.orderstate.OrderStateEnum;
import com.smc.smccloud.model.orderstate.OrderStateVO;
import com.smc.smccloud.model.product.ProductRemark;
import com.smc.smccloud.model.receiveorder.OrderCount;
import com.smc.smccloud.model.receiveorder.OrderSpecExpType;
import com.smc.smccloud.rabbitmq.SendMessage;
import com.smc.smccloud.service.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;


/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author smc
 * @since 2021-10-23
 */
@Slf4j
@Service
public class OrderSalesServiceImpl implements OrderSalesService {

    @Resource
    private OrderSalesMapper orderSalesMapper;
    @Resource
    private RcvdetailMapper rcvdetailMapper;
    @Resource
    private RcvmasterMapper rcvmasterMapper;
    @Resource
    private VSalesManuorderMapper vSalesManuorderMapper;
    @Resource
    private PlatformTransactionManager transactionManager;
    @Resource
    private OpsVManuorderToSalesMapper opsVManuorderToSalesMapper;
    @Resource
    private OrderDlvDataService orderDlvDataService;
    @Resource
    private VSalesManuorderService vSalesManuorderService;
    @Resource
    private RedisManager redisManager;
    @Resource
    private SendMessage sendMessage;
    @Resource
    private RedissonUtil redissonUtil;
    @Resource
    private OpsCommonService opsCommonService;
    @Resource
    private DictCommonService dictCommonService;
    @Resource
    private CommonServiceFeignApi commonServiceFeignApi;
    @Resource
    private ProductServiceFeignApi productServiceFeignApi;
    @Resource
    private InventoryServiceFeignApi inventoryServiceFeignApi;
    @Resource
    private OpsPurchaseInvoiceMapper opsPurchaseInvoiceMapper;
    @Resource
    private ProductPriceMapper productPriceMapper;
    @Resource
    private IpsSendOrderToOpsMapper ipsSendOrderToOpsMapper;

    @Resource
    private EventService eventService;

    @Resource
    private CommonMapper commonMapper;

    @Override
    public PageInfo<OrderSalesDO> findAll(OrderSalesRequest orderSalesRequest, Page page) {

        LambdaQueryWrapper<OrderSalesDO> query = Wrappers.lambdaQuery();
        query.eq(PublicUtil.isNotEmpty(orderSalesRequest.getStatus()), OrderSalesDO::getStatus, orderSalesRequest.getStatus());
        query.eq(PublicUtil.isNotEmpty(orderSalesRequest.getTradecompanyId()), OrderSalesDO::getTradeCompanyId, orderSalesRequest.getTradecompanyId());
        query.eq(PublicUtil.isNotEmpty(orderSalesRequest.getTypeCode()), OrderSalesDO::getTypeCode, orderSalesRequest.getTypeCode());
        query.like(PublicUtil.isNotEmpty(orderSalesRequest.getFullOrderNo()), OrderSalesDO::getFullOrderNo, orderSalesRequest.getFullOrderNo());
        query.eq(PublicUtil.isNotEmpty(orderSalesRequest.getAreaDeptNo()), OrderSalesDO::getDeptNo, orderSalesRequest.getAreaDeptNo());

        PageInfo<OrderSalesDO> orderSalesList = PageHelper.startPage(page.getPageNumber(), page.getPageSize())
                .doSelectPageInfo(() -> orderSalesMapper.selectList(query));

        if (CollectionUtils.isNotEmpty(orderSalesList.getList())) {
            for (OrderSalesDO item : orderSalesList.getList()) {
                item.setExpDlvTypeString(OrderSpecExpType.getSpecExpTypeDes(item.getExpDlvType()));

            }
        }

        return orderSalesList;

    }

    @Override
    public ResultVo<OrderCount> getOrderCount(OrderSalesRequest dto) {

        // 统计待接订单数量
        LambdaQueryWrapper<OrderSalesDO> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(OrderSalesDO::getStatus, 0);
        queryWrapper.eq(PublicUtil.isNotEmpty(dto.getTradecompanyId()), OrderSalesDO::getTradeCompanyId, dto.getTradecompanyId());
        queryWrapper.eq(PublicUtil.isNotEmpty(dto.getAreaDeptNo()), OrderSalesDO::getDeptNo, dto.getAreaDeptNo());
        Integer orderSalesCount = orderSalesMapper.selectCount(queryWrapper);
        // 统计处理中订单数量
        Integer processingOrderCount = rcvdetailMapper.getProcessingOrderCount(dto.getTradecompanyId(), dto.getAreaDeptNo());
        // 统计暂不处理订单数量
        Integer notHandleOrderCount = rcvdetailMapper.getNotHandleOrderCount(dto.getTradecompanyId(), dto.getAreaDeptNo());
        // 统计异常订单数量
        Integer exceptionHandCount = rcvdetailMapper.getExceptionHandCount(dto.getTradecompanyId(), dto.getAreaDeptNo());

        OrderCount orderCount = new OrderCount();
        orderCount.setOrderSalesCount(Optional.ofNullable(orderSalesCount).orElse(0));
        orderCount.setProcessingOrderCount(Optional.ofNullable(processingOrderCount).orElse(0));
        orderCount.setNotHandleOrderCount(Optional.ofNullable(notHandleOrderCount).orElse(0));
        orderCount.setExceptionHandCount(Optional.ofNullable(exceptionHandCount).orElse(0));

        return ResultVo.success(orderCount);
    }

    @DS("sharedb")
    @Override
    public ResultVo<String> addOrderSales(OrderSalesDTO orderSalesDTO) {

        ResultVo<DataTypeVO> dataTypeCodesInfo1 = dictCommonService.getDataTypeCodesInfo("6012", "2");
        if (dataTypeCodesInfo1.isSuccess() && dataTypeCodesInfo1.getData() != null) {
            DataTypeVO data = dataTypeCodesInfo1.getData();
            if ("0".equals(data.getExtNote1())) {
                return ResultVo.failure("此接口现在停用");
            }
        }

        if (orderSalesDTO == null) {
            return ResultVo.failure("新增失败");
        }
        LoginUserDTO userDTO = SMCApp.getLoginAuthDto();
        // 暂时只接入typeCode[1,11] 的订单
        OrderTypeEnum orderTypeEnum = null;
        switch (orderSalesDTO.getFromType()) {
            case 1: // 门户订单
                orderTypeEnum = OrderTypeEnum.saleOrder;
                break;
            case 2: // 中国制造
            case 3: // 广州制造
                orderTypeEnum = OrderTypeEnum.gnjtOrder;
                break;
            default:
                log.error("未知来源订单{}", orderSalesDTO.getFromType());
                break;
        }
        if (orderTypeEnum == null) {
            return ResultVo.failure("订单类型" + orderSalesDTO.getTypeCode() + "不在接入范围内");
        }

//        // 客户销售订单
//        if (orderTypeEnum.equals(OrderTypeEnum.saleOrder)) {
//            Object o = redisManager.lLeftPop(Constants.SALES_ORDER_NO); // bug-8874
//            orderSalesDTO.setRorderNo(o.toString());
//        }
//        // 国内集团销售订单
//        if (orderTypeEnum.equals(OrderTypeEnum.gnjtOrder)) {
//            ResultVo<String> stringResultVo = commonServiceFeignApi.generatorBillNo("6");
//            if (!stringResultVo.isSuccess() || stringResultVo.getData() == null) {
//                return ResultVo.failure("生成订单号失败");
//            }
//            orderSalesDTO.setRorderNo(stringResultVo.getData());
//        }

        List<OrderSalesDetailVO> orderSalesDetailVOList = orderSalesDTO.getItems();
        List<OrderSalesDO> orderSalesDOList = BeanCopyUtil.copyList(orderSalesDetailVOList, OrderSalesDO.class);
        OrderSalesDO orderSalesDO = BeanCopyUtil.copy(orderSalesDTO, OrderSalesDO.class);

        OrderStateVO orderStateVO;
        RabbitMqMessage orderStateMqMsg;
        Date now = new Date();
        int itemNo = 0; // 订单项号
        String status = "0";

        // 查税率
        if (null == orderSalesDTO.getTaxRate() || orderSalesDTO.getTaxRate().equals(BigDecimal.ZERO)) {
            ResultVo<DataTypeVO> dataTypeCodesInfo = dictCommonService.getDataTypeCodesInfo("9002", "1");
            if (dataTypeCodesInfo.isSuccess() && dataTypeCodesInfo.getData() != null) {
                orderSalesDTO.setTaxRate(new BigDecimal(dataTypeCodesInfo.getData().getExtNote1()));
            } else {
                orderSalesDTO.setTaxRate(new BigDecimal("0.13"));
            }
        }

        LambdaQueryWrapper<OrderSalesDO> queryWrapper = Wrappers.lambdaQuery();
        OrderSalesDO exitOrderInfo;
        TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
        transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        ResultVo<String> addResult;
        ResultVo<String> resultVo;
        OrderNoInfo orderNoInfo;
        RcvMasterDO rcvMasterInfo = null;
        OpsPurchaseInvoiceDO opsPurchaseInvoiceDO;
        for (OrderSalesDO item : orderSalesDOList) {
            String userNo = orderSalesDO.getUserNo();
            if (StringUtils.isNotBlank(item.getOpsOrderNo())) {
                // 根据opsOrder带出用户
                // 1结尾的单号 去除掉1 否则保留原值
                if (item.getOpsOrderNo().endsWith("-1")) {
                    String beforeLastSepaStr = PublicUtil.getBeforeLastSepaStr(item.getOpsOrderNo(), "-");
                    orderNoInfo = new OrderNoInfo().convertFullOrderNo(beforeLastSepaStr);
                } else {
                    orderNoInfo = new OrderNoInfo().convertFullOrderNo(item.getOpsOrderNo());
                }
                rcvMasterInfo = getRcvMasterInfo(orderNoInfo.getOrderNo());
                if (rcvMasterInfo != null) {
                    userNo = rcvMasterInfo.getEndUser();
                }

                // 88/99开头的关联单号 查ops_purchaseInvoice带出关联用户代码
                if (item.getOpsOrderNo().startsWith("88") || item.getOpsOrderNo().startsWith("99")) {
                    opsPurchaseInvoiceDO = getOpsPurchaseInvoiceInfo(orderNoInfo.getOrderNo(), String.valueOf(orderNoInfo.getItemNo()));
                    if(opsPurchaseInvoiceDO != null) {
                        if (StringUtils.isBlank(opsPurchaseInvoiceDO.getUserNo())) {
                            opsPurchaseInvoiceDO.setUserNo(opsPurchaseInvoiceDO.getCustomerNo());
                        }
                        userNo = opsPurchaseInvoiceDO.getUserNo();
                    }
                }
            }
            // 根据型号进行校验
            resultVo = checkByModel(item.getModelNo(), item.getQuantity(), orderSalesDO.getCustomerNo(), userNo);
            if (!resultVo.isSuccess()) {
                return ResultVo.failure(resultVo.getMessage());
            }
            if (orderSalesDO.getTypeCode() == 1 && PublicUtil.isEmpty(item.getPrice())) {
                return ResultVo.failure("客户销售单价不可为空！");
            }
            if (PublicUtil.isEmpty(item.getQuantity()) || item.getQuantity() < 1) {
                return ResultVo.failure("数量不可为空");
            }
            // 国内工厂订单
            if (orderTypeEnum.equals(OrderTypeEnum.gnjtOrder)) {
                item.setPreSalesOrderNo(item.getCorderNo());
            }
            queryWrapper.clear();
            queryWrapper.select(OrderSalesDO::getRorderNo, OrderSalesDO::getRorderItem, OrderSalesDO::getFullOrderNo);
            queryWrapper.eq(OrderSalesDO::getPreSalesOrderNo, item.getPreSalesOrderNo());
            exitOrderInfo = orderSalesMapper.selectOne(queryWrapper);
            if (exitOrderInfo != null) {
                    orderSalesDTO.setRorderNo(exitOrderInfo.getRorderNo());
                itemNo = exitOrderInfo.getRorderItem();
                log.info("广州制造订单{}重复发单", item.getPreSalesOrderNo());
                continue;
            }

            BeanCopyUtil.mergeObject(orderSalesDO, item);
            itemNo++;
            if (itemNo == 1 || itemNo > 999) {
                itemNo = 1;
                // 客户销售订单
                if (orderTypeEnum.equals(OrderTypeEnum.saleOrder)) {
                    Object o = redisManager.lLeftPop(Constants.SALES_ORDER_NO);
                    orderSalesDTO.setRorderNo(o.toString());
                }
                // 国内集团销售订单
                if (orderTypeEnum.equals(OrderTypeEnum.gnjtOrder)) {
                    resultVo = commonServiceFeignApi.generatorBillNo("6");
                    if (!resultVo.isSuccess() || resultVo.getData() == null) {
                        return ResultVo.failure("生成订单号失败");
                    }
                    orderSalesDTO.setRorderNo(resultVo.getData());
                }
            }

            item.setRorderNo(orderSalesDTO.getRorderNo());
            item.setRorderItem(itemNo);
            item.setFullOrderNo(item.getRorderNo() + "-" + item.getRorderItem());
            item.setWorkday(Optional.ofNullable(item.getWorkday()).orElse(now));
            item.setStatus(status);

            // 订单信息保存事务
            addResult = transactionTemplate.execute(transactionStatus -> {
                try {
                    orderSalesMapper.insert(item);
                } catch (Exception e) {
                    log.error("订单接入失败: data = {}, {}", item, e.getMessage(), e);
                    return ResultVo.failure("订单接入失败: " + e.getMessage());
                }
                // 保存订单收货地址等信息
                if (item.getRorderItem() == 1) {
                    OrderDlvDataDO orderDlvDataDO = BeanCopyUtil.copy(orderSalesDTO.getAddressInfo(), OrderDlvDataDO.class);
                    orderDlvDataDO.setOrderNo(orderSalesDTO.getRorderNo());
                    try {
                        orderDlvDataService.saveOrderDlvData(orderDlvDataDO);
                    } catch (Exception e) {
                        log.error("保存订单收货地址失败: data = {},  {}", orderDlvDataDO, e.getMessage(), e);
                        transactionStatus.setRollbackOnly();  // 手动回滚
                        return ResultVo.failure("保存订单收货地址失败: " + e.getMessage());
                    }
                }
                return ResultVo.success("订单接入成功: " + orderSalesDO.getFullOrderNo());
            });
            if (addResult == null || !addResult.isSuccess()) {
                return ResultVo.failure(addResult == null ? "订单接入失败" : addResult.getMessage());
            }

//            // 推送订单操作日志
//            orderLogVO = new OrderLogVO();
//            orderLogVO.setOrderNo(item.getFullOrderNo());
//            orderLogVO.setOptType(11);
//            orderLogVO.setDescription("接入门户订单");
//            orderLogVO.setOptTime(now);
//            orderLogVO.setIp(ipAddress);
//            orderLogVO.setOptUserName(userDTO.getUserName());
//            orderLogVO.setOptUserId(userDTO.getUserNo());
//            orderLogMQMag = new RabbitMqMessage();
//            orderLogMQMag.setContent(JSON.toJSONString(orderLogVO));
//            orderLogMQMag.setFlag(com.smc.smccloud.core.rabbitmq.constants.Constants.OPS_ORDER_LOG);
//            orderLogMQMag.setDataType(com.smc.smccloud.core.rabbitmq.constants.Constants.OPS_ORDER);
//            orderLogMQMag.setSystem(com.smc.smccloud.core.rabbitmq.constants.Constants.OPS);
//            if (!sendMessage.sendOrderLogMsg(orderLogMQMag)) {
//                log.error("推送订单日志至MQ失败");
//            }

            // 推送货期状态到MQ
            orderStateVO = BeanCopyUtil.copy(item, OrderStateVO.class);
            orderStateVO.setOrderNo(item.getFullOrderNo());
            orderStateVO.setModelNo(item.getModelNo());
            orderStateVO.setQuantity(item.getQuantity());
            orderStateVO.setOrderType(item.getTypeCode());
            orderStateVO.setStateCode(OrderStateEnum.ReceiveFromOtherSys.getCode());
            orderStateVO.setStateDes(OrderStateEnum.ReceiveFromOtherSys.getStateName());
            orderStateVO.setStateType(1);
            orderStateVO.setStateDate(now);
            orderStateVO.setCustDlvDate(item.getDlvDate());
            orderStateVO.setCustShipDate(item.getWarehouseSendDate());
            orderStateVO.setOrderPsnNo(item.getEmpOffice());
            orderStateVO.setCustomerNo(item.getCustomerNo());
            orderStateVO.setPoTransType(item.getTransChannel());
            orderStateVO.setTransType(item.getTransChannel());
            orderStateVO.setUserNo(item.getUserNo());
            orderStateVO.setDeptNo(item.getDeptNo());
            orderStateVO.setShikomiNo(item.getShikomiNo());
            orderStateVO.setOptUserNo(userDTO.getUserName());
            orderStateVO.setOptUserName(userDTO.getRealName());
            orderStateVO.setProvider("GZOrder");
            orderStateVO.setIsAddLog(true);
            orderStateMqMsg = new RabbitMqMessage();
            orderStateMqMsg.setContent(JSON.toJSONString(orderStateVO));
            orderStateMqMsg.setFlag(com.smc.smccloud.core.rabbitmq.constants.Constants.OPS_ORDER_STATE);
            orderStateMqMsg.setDataType(com.smc.smccloud.core.rabbitmq.constants.Constants.OPS_ORDER);
            orderStateMqMsg.setSystem(com.smc.smccloud.core.rabbitmq.constants.Constants.OPS);
            if (!sendMessage.sendOrderStateMsg(orderStateMqMsg)) {
                log.error("推送货期状态至MQ失败");
            }
        }
        return ResultVo.success(orderSalesDTO.getRorderNo());
    }

    @DS("sharedb")
    @Override
    public ResultVo<List<AdapterOrderResult>> addOrderSalesFromSMS(OrderSalesDTO dto) {
        log.info("开始保存门户订单: {} {}项.", dto.getItems().get(0).getOrOrderNo(), dto.getItems().size());
        List<AdapterOrderResult> resultList = new ArrayList<>(dto.getItems().size());
        AdapterOrderResult result;

        OrderSalesDO commonInfo = BeanCopyUtil.copy(dto, OrderSalesDO.class);
        OrderSalesDO orderSalesInfo;
        String orderNo = null;
        int itemNo = 0;
        String delimiter = "-";
        String status = "0";

        ResultVo<ProductRemark> productRemarkInfo;
        TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
        transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        ResultVo<String> addResult;

        /// deptNo和HLCode bug-10665 回滚
        // if (StringUtils.isBlank(commonInfo.getHLCode())) {
            ResultVo<String> deptNoResult = opsCommonService.getDeptNoByHRSalesDeptNo(commonInfo.getDeptNo());
            if (!deptNoResult.isSuccess() || deptNoResult.getData() == null) {
                log.error("门户订单接入,校验部门代码失败: {}", deptNoResult);
                return ResultVo.failure("校验部门代码失败," + deptNoResult.getMessage());
            } else {
                if (!commonInfo.getDeptNo().equals(deptNoResult.getData())) {
                    commonInfo.setHLCode(commonInfo.getDeptNo());
                    commonInfo.setDeptNo(deptNoResult.getData());
                }
            }
        // }
        // 客户信息
        ResultVo<CustomerVO> customerInfo = commonServiceFeignApi.findCustomerByCustomerNo(commonInfo.getCustomerNo());
        // 税率
        if (commonInfo.getTaxRate() == null || commonInfo.getTaxRate().compareTo(BigDecimal.ZERO) == 0) {
            ResultVo<DataTypeVO> dataTypeCodesInfo = dictCommonService.getDataTypeCodesInfo("9002", "1");
            if (dataTypeCodesInfo.isSuccess() && dataTypeCodesInfo.getData() != null) {
                commonInfo.setTaxRate(new BigDecimal(dataTypeCodesInfo.getData().getExtNote1()));
            } else {
                commonInfo.setTaxRate(new BigDecimal("0.13"));
            }
        }

        // 发注单排序
        dto.setItems(dto.getItems().stream().sorted(Comparator.comparing(OrderSalesDetailVO::getPreSalesOrderNo)).collect(Collectors.toList()));
        String keyPrefix = "ops:rediss:orderSales:inserting:";
        LambdaQueryWrapper<OrderSalesDO> orderQuery = Wrappers.lambdaQuery();
        OrderSalesDO existOrderInfo;
        Date today = DateUtil.getToday();
        //String savedKeyFormat = "ops:orderSales:saved:%s:%s";
        //String savedKey;
        //Object savedOrder;

        for (OrderSalesDetailVO item : dto.getItems()) {
            log.info("门户订单保存: {} --- {}", item.getOrOrderNo(), item.getPreSalesOrderNo());
            result = new AdapterOrderResult();
            result.setResult(false);
            result.setNo(item.getPreSalesOrderNo());
            resultList.add(result);

            if (StringUtils.isEmpty(commonInfo.getDeptNo())) {
                result.setMessage("所属营业所不能为空");
                continue;
            }
            if (!customerInfo.isSuccess() || customerInfo.getData() == null) {
                result.setMessage("客户代码不存在");
                continue;
            }
            if (commonInfo.getWorkday() == null) {
                result.setMessage("订货日期不能为空");
                continue;
            }
            if (!validDlvDate(today, item.getDlvDate())) { // bug-10261
                result.setMessage("预交货期不能早于接单当日");
                continue;
            }
//            if (!validDlvDate(today, item.getWarehouseSendDate())) { // bug-10261
//                result.setMessage("物流发货日期不能早于接单当日"); // bug-10460 物流发货日在订单分配的时候计算，不再从门户取
//                continue;
//            }
            if (StringUtils.isBlank(commonInfo.getTradeCompanyId())) {
                result.setMessage("交易主体不能为空");
                continue;
            }
            if (item.getQuantity() < 1) {
                result.setMessage("数量必须大于零");
                continue;
            }
            if (item.getPrice().compareTo(BigDecimal.ZERO) < 1) {
                result.setMessage("单价不能为零");
                continue;
            }
            if (item.getDiscount() == null) {
                result.setMessage("E率格式错误");
                continue;
            }
            if (!CarrierEnum.ZT.getCode().equals(dto.getAddressInfo().getCarrierId())) {
                if (StringUtils.isBlank(dto.getAddressInfo().getDlvAddress())) {
                    result.setMessage("发货地址不能为空");
                    continue;
                }
                if (StringUtils.isBlank(dto.getAddressInfo().getProvince())) {
                    result.setMessage("发货地址-省编码不能为空");
                    continue;
                }
                if (StringUtils.isBlank(dto.getAddressInfo().getCity())) {
                    result.setMessage("发货地址-市编码不能为空");
                    continue;
                }
                if (StringUtils.isBlank(dto.getAddressInfo().getDistrict())) {
                    result.setMessage("发货地址-区编码不能为空");
                    continue;
                }
            }
            if (StringUtils.isBlank(dto.getAddressInfo().getTelNo())) {
                result.setMessage("发货地址-联系人电话不能为空");
                continue;
            }
            productRemarkInfo = productServiceFeignApi.getProductRemarkByModelNo(item.getModelNo());
            if (!productRemarkInfo.isSuccess()) {
                result.setMessage("型号信息获取失败");
                continue;
            }
            if (productRemarkInfo.getData() == null) {
                result.setMessage("型号不存在");
                continue;
            }
            if ("1".equals(productRemarkInfo.getData().getIsError())) {
                result.setMessage("错误型号");
                continue;
            }
            if ("1".equals(productRemarkInfo.getData().getIsEven()) && this.checkParity(item.getQuantity()) == 1) {
                result.setMessage("该型号必须按偶数订货");
                continue;
            }

            // 检查门户订单是否已接入
            orderQuery.clear();
            orderQuery.select(OrderSalesDO::getRorderNo, OrderSalesDO::getRorderItem, OrderSalesDO::getFullOrderNo);
            orderQuery.eq(OrderSalesDO::getPreSalesOrderNo, item.getPreSalesOrderNo());
            existOrderInfo = orderSalesMapper.selectOne(orderQuery);
            if (existOrderInfo != null) {
                result.setResult(true);
                result.setERPno(existOrderInfo.getFullOrderNo());
                orderNo = existOrderInfo.getRorderNo();
                itemNo = existOrderInfo.getRorderItem();
                log.info("门户订单{}重复发单", item.getPreSalesOrderNo());
                continue;
            }
//            // 从redis中查询保存历史记录,检查是否已接入
//            savedKey = String.format(savedKeyFormat, item.getOrOrderNo(), item.getPreSalesOrderNo());
//            savedOrder = redisManager.get(savedKey);
//            if (savedOrder != null) {
//                String fullOrderNo = savedOrder.toString();
//                String[] orderNos = fullOrderNo.split(delimiter);
//                result.setResult(true);
//                result.setERPno(fullOrderNo);
//                orderNo = orderNos[0];
//                itemNo = Integer.parseInt(orderNos[1]);
//                continue;
//            }

            orderSalesInfo = BeanCopyUtil.copy(item, OrderSalesDO.class);
            BeanCopyUtil.mergeObject(commonInfo, orderSalesInfo);

            itemNo++;
            if (itemNo == 1 || itemNo > 999) {
                if (itemNo > 999) {
                    // 发送订单接入消息
                    orderSalesInfo.setRorderNo(orderNo);
                    this.addOrderReceiveMsg(orderSalesInfo);
                    redissonUtil.unlock(keyPrefix + orderNo);
                }
                Object o = redisManager.lLeftPop(Constants.SALES_ORDER_NO); // bug-8874
                if (o == null || StringUtils.isBlank(o.toString())) {
                    return ResultVo.failure("订单号生成失败");
                }
                orderNo = o.toString();
                itemNo = 1;
                redissonUtil.lock(keyPrefix + orderNo, 30, TimeUnit.SECONDS);
            }

            orderSalesInfo.setRorderNo(orderNo);
            orderSalesInfo.setRorderItem(itemNo);
            orderSalesInfo.setFullOrderNo(orderNo + delimiter + itemNo);
            orderSalesInfo.setStatus(status);
            // 订单信息保存事务
            OrderSalesDO orderSalesDO = orderSalesInfo;
            //String finalSavedKey = savedKey;
            addResult = transactionTemplate.execute(transactionStatus -> {
                // 保存订单收货地址等信息
                if (orderSalesDO.getRorderItem() == 1) {
                    OrderDlvDataDO orderDlvDataDO = BeanCopyUtil.copy(dto.getAddressInfo(), OrderDlvDataDO.class);
                    orderDlvDataDO.setOrderNo(orderSalesDO.getRorderNo());
                    try {
                        orderDlvDataService.saveOrderDlvData(orderDlvDataDO);
                        log.info("保存地址:" + orderDlvDataDO.getOrderNo());
                    } catch (Exception e) {
                        log.error("保存门户订单收货地址失败: data = {}, {}", orderDlvDataDO, e.getMessage(), e);
                        transactionStatus.setRollbackOnly(); // 手动回滚
                        return ResultVo.failure("保存门户订单收货地址失败: " + e.getMessage());
                    }
                }

                try {
                    orderSalesMapper.insert(orderSalesDO);
                    log.info("增加ordersales:" + orderSalesDO.getFullOrderNo());
                } catch (Exception e) {
                    if (!e.getMessage().contains("违反了 PRIMARY KEY 约束")) {
                        log.error("门户订单接入失败: data = {}, {}", orderSalesDO, e.getMessage(), e);
                        transactionStatus.setRollbackOnly();  // 手动回滚
                        return ResultVo.failure("门户订单接入失败: " + e.getMessage());
                    }
                }
                //// redis缓存历史订单7天
                //redisManager.set(finalSavedKey, orderSalesDO.getFullOrderNo(), 604800L);

                return ResultVo.success("门户订单接入成功: " + orderSalesDO.getFullOrderNo());
            });
            if (addResult == null || !addResult.isSuccess()) {
                return ResultVo.failure(addResult == null ? "门户订单接入失败" : addResult.getMessage());
            }
            // 门户订单接入成功
            result.setResult(true);
            result.setERPno(orderSalesInfo.getFullOrderNo());
            log.info("门户订单接入成功 {} {} {}", orderSalesDO.getFullOrderNo(), orderSalesDO.getOrOrderNo(), orderSalesDO.getPreSalesOrderNo());
            // 3.记录日志
            this.addRcvOrderLogAndState(orderSalesInfo);
            log.info("记录门户订单日志 {} {} {}", orderSalesDO.getFullOrderNo(), orderSalesDO.getOrOrderNo(), orderSalesDO.getPreSalesOrderNo());
        }

        // 发送订单接入消息
        if (StringUtils.isNotBlank(orderNo)) {
            redissonUtil.unlock(keyPrefix + orderNo);
            orderSalesInfo = new OrderSalesDO();
            orderSalesInfo.setRorderNo(orderNo);
            try {
                this.addOrderReceiveMsg(orderSalesInfo);
            } catch (Exception e) {
                log.error("发送订单接入消息到mq异常 {}",e.getMessage(),e);
            }
        }

        return ResultVo.success(resultList);
    }

    private void addRcvOrderLogAndState(OrderSalesDO orderSalesInfo) {
        Date now = new Date();
        // 推送订单接入操作日志
        try {
            OrderLogVO orderLogVO = new OrderLogVO();
            orderLogVO.setOrderNo(orderSalesInfo.getFullOrderNo());
            orderLogVO.setOptType(12);
            orderLogVO.setDescription("接入门户订单");
            orderLogVO.setOptTime(now);
            //orderLogVO.setIp(ipAddress);
            orderLogVO.setOptUserName("ops");
            orderLogVO.setOptUserId("ops");
            RabbitMqMessage orderLogMQMag = new RabbitMqMessage();
            orderLogMQMag.setContent(JSON.toJSONString(orderLogVO));
            orderLogMQMag.setFlag(com.smc.smccloud.core.rabbitmq.constants.Constants.OPS_ORDER_LOG);
            orderLogMQMag.setDataType(com.smc.smccloud.core.rabbitmq.constants.Constants.OPS_ORDER);
            orderLogMQMag.setSystem(com.smc.smccloud.core.rabbitmq.constants.Constants.OPS);
            if (!sendMessage.sendOrderLogMsg(orderLogMQMag)) {
                log.error("推送订单日志至MQ失败");
            }
        } catch (Exception e) {
            log.error("推送订单日志至MQ失败: {}", e.getMessage(), e);
        }

        // 推送货期状态到MQ
        try {
            OrderStateVO orderStateVO = BeanCopyUtil.copy(orderSalesInfo, OrderStateVO.class);
            orderStateVO.setOrderNo(orderSalesInfo.getFullOrderNo());
            orderStateVO.setRorderNo(orderSalesInfo.getRorderNo());
            orderStateVO.setItemNo(orderSalesInfo.getRorderItem());
            orderStateVO.setModelNo(orderSalesInfo.getModelNo());
            orderStateVO.setQuantity(orderSalesInfo.getQuantity());
            orderStateVO.setOrderType(orderSalesInfo.getTypeCode());
            orderStateVO.setStateCode(12);
            orderStateVO.setStateDes("接入门户订单");
            orderStateVO.setStateType(1);
            orderStateVO.setOrderType(orderSalesInfo.getTypeCode());
            orderStateVO.setStateDate(now);
            orderStateVO.setOrderDate(orderSalesInfo.getWorkday());
            orderStateVO.setCustDlvDate(orderSalesInfo.getDlvDate());
            orderStateVO.setCustShipDate(orderSalesInfo.getWarehouseSendDate());
            orderStateVO.setOrderPsnNo(orderSalesInfo.getEmpOffice());
            orderStateVO.setOrderPsnDept(orderSalesInfo.getDeptNo());
            orderStateVO.setCustomerNo(orderSalesInfo.getCustomerNo());
            orderStateVO.setPoTransType(orderSalesInfo.getTransChannel());
            orderStateVO.setTransType(orderSalesInfo.getTransChannel());
            orderStateVO.setUserNo(orderSalesInfo.getUserNo());
            orderStateVO.setDeptNo(orderSalesInfo.getDeptNo());
            orderStateVO.setShikomiNo(orderSalesInfo.getShikomiNo());
            orderStateVO.setOrderDate(orderSalesInfo.getWorkday() == null ? new Date() : orderSalesInfo.getWorkday());
            orderStateVO.setOptUserNo("ops");
            orderStateVO.setOptUserName("ops");
            //orderStateVO.setFirstDate(now);
            //orderStateVO.setMinDate(now);
            //orderStateVO.setMaxDate(now);
            orderStateVO.setProvider("OPS");
            RabbitMqMessage orderStateMqMsg = new RabbitMqMessage();
            orderStateMqMsg.setContent(JSON.toJSONString(orderStateVO));
            orderStateMqMsg.setFlag(com.smc.smccloud.core.rabbitmq.constants.Constants.OPS_ORDER_STATE);
            orderStateMqMsg.setDataType(com.smc.smccloud.core.rabbitmq.constants.Constants.OPS_ORDER);
            orderStateMqMsg.setSystem(com.smc.smccloud.core.rabbitmq.constants.Constants.OPS);
            if (!sendMessage.sendOrderStateMsg(orderStateMqMsg)) {
                log.error("推送订单状态至MQ失败");
            }
        } catch (Exception e) {
            log.error("推送订单状态至MQ失败: {}", e.getMessage(), e);
        }
    }

    private boolean addOrderReceiveMsg(OrderSalesDO orderSalesInfo) {
        ResultVo<DataTypeVO> dataTypeCodesInfo = dictCommonService.getDataTypeCodesInfo("9002", "2");
        if (dataTypeCodesInfo.getData() == null || !"1".equals(dataTypeCodesInfo.getData().getExtNote1())) {
            log.info("【自动接入订单-已关闭】: 将不会自动接入订单{}", orderSalesInfo.getRorderNo());
            return true;
        }
        RabbitMqMessage rabbitMqMessage = new RabbitMqMessage(JSON.toJSONString(orderSalesInfo),
                com.smc.smccloud.core.rabbitmq.constants.Constants.OPS_ORDER_RECEIVE,
                com.smc.smccloud.core.rabbitmq.constants.Constants.OPS_ORDER,
                com.smc.smccloud.core.rabbitmq.constants.Constants.OPS);
        //log.info("发送接入订单消息: {} {}", orderSalesInfo.getRorderNo(), result);
        return sendMessage.sendOrderReceiveMsg(rabbitMqMessage);
    }


    @Override
    //@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    @DS("sharedb")
    public int updateOrderSalesToProcessedStatus(String rorderNo, int rorderItemNo) {
        String unProcessedStatus = "0";
        String processedStatus = "1";
        try {
            LambdaUpdateWrapper<OrderSalesDO> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.set(OrderSalesDO::getStatus, processedStatus)
                    .set(OrderSalesDO::getUpdateTime, new Date());
            updateWrapper.eq(OrderSalesDO::getRorderNo, rorderNo)
                    .eq(OrderSalesDO::getRorderItem, rorderItemNo)
                    .eq(OrderSalesDO::getStatus, unProcessedStatus);
            return orderSalesMapper.update(null, updateWrapper);
        } catch (Exception e) {
            throw new BusinessException("更新ordersales失败", e);
        }
    }

    /**
     * 将ops的接单状态返回给中国工厂
     *
     * @return
     */
    @Override
    public ResultVo<String> updateCNMadeOrderStatus() {

        VSalesManuorderParams params = new VSalesManuorderParams();
        params.setOptStatus(1);
        Page page = new Page();
        page.setPageSize(1);
        int pageNum = 1;
        PageInfo<VSalesManuorderDO> pageInfo;
        List<VSalesManuorderDO> vSalesManuorderDOList;
        RcvDetailDO rcvDetailDO;
        LambdaQueryWrapper<RcvDetailDO> findRcvDetailQuery = Wrappers.lambdaQuery();

        int count = 0;
        while (true) {
            page.setPageNumber(pageNum);
            pageInfo = vSalesManuorderService.findAll(params, page);

            if (pageInfo.getList().isEmpty()) {
                break;
            }

            vSalesManuorderDOList = pageInfo.getList();
            VSalesManuorderDO vSalesManuorderDO = vSalesManuorderDOList.get(0);

            findRcvDetailQuery.clear();
            findRcvDetailQuery.eq(RcvDetailDO::getRorderFno, vSalesManuorderDO.getSalesOrderNo());
            rcvDetailDO = rcvdetailMapper.selectOne(findRcvDetailQuery);
            if (rcvDetailDO == null) {
                pageNum++;
                continue;
            }
            // 返回订货区分
            if (PublicUtil.isEmpty(vSalesManuorderDO.getExpInvCode())) {
                vSalesManuorderDO.setExpInvCode(PublicUtil.isEmpty(rcvDetailDO.getStockCode()) ? "" : rcvDetailDO.getStockCode());
            }
            // 设置物流货期
            if (null == vSalesManuorderDO.getSalesDeliveryTime()) {
                vSalesManuorderDO.setSalesDeliveryTime(rcvDetailDO.getWmsDlvDate());
            }
            // 税率
            if (null == vSalesManuorderDO.getTaxRate()) {
                ResultVo<DataTypeVO> dataTypeCodesInfo = dictCommonService.getDataTypeCodesInfo("9002", "1");
                if (dataTypeCodesInfo.isSuccess() && dataTypeCodesInfo.getData() != null) {
                    vSalesManuorderDO.setTaxRate(new BigDecimal(dataTypeCodesInfo.getData().getExtNote1()));
                } else {
                    vSalesManuorderDO.setTaxRate(new BigDecimal("0.13"));
                }
            }

            vSalesManuorderDO.setSalesUpdateTime(new Date());

            // 判断rcvDetail 的接收状态
            switch (rcvDetailDO.getStatus()) {
                case 1: // 订单受理中
                    vSalesManuorderDO.setSalesStatus(1);
                    vSalesManuorderService.update(vSalesManuorderDO);
                    break;
                case 9: // 订单受理失败
                    vSalesManuorderDO.setSalesStatus(3);
                    vSalesManuorderService.update(vSalesManuorderDO);
                    break;
                default: // 订单受理成功
                    vSalesManuorderDO.setSalesStatus(2);
                    vSalesManuorderService.update(vSalesManuorderDO);
                    break;
            }
            count++;
            if (pageInfo.isIsLastPage()) {
                break;
            }
            pageNum++;
        }
        return ResultVo.success(Integer.toString(count), "更新中国工厂状态成功" + count);
    }


    public void updateReceiveCNOrderFault(Integer id, String fullOrderNo, String remark) {
        // 错误型号
        LambdaUpdateWrapper<VSalesManuorderDO> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper
                .eq(VSalesManuorderDO::getId, id)
                .set(VSalesManuorderDO::getSalesStatus, 3)
                .set(VSalesManuorderDO::getOptStatus, "1")
                .set(VSalesManuorderDO::getSalesCancelStatus, 1)
                .set(VSalesManuorderDO::getSalesCancelReason, remark)
                .set(VSalesManuorderDO::getSalesCancelTime, DateUtil.dateToDateTimeString(new Date()))
                .set(VSalesManuorderDO::getSalesOrderNo, fullOrderNo)
                .set(VSalesManuorderDO::getSalesRemark, "订单受理失败,原因:" + remark)
                .set(VSalesManuorderDO::getSalesUpdateTime, new Date());
        vSalesManuorderMapper.update(null, lambdaUpdateWrapper);
    }

    public BigDecimal getEpriceFromProductPrice(String modelNo,int qty) {
        if (StringUtils.isBlank(modelNo)) {
            return BigDecimal.ZERO;
        }
        ProductPriceDO productPriceDO = productPriceMapper.getEpriceByModelNoAndMinQuantity(modelNo, qty);
        if (Objects.isNull(productPriceDO)) {
            return BigDecimal.ZERO;
        }
        return productPriceDO.getEprice();
    }

    /**
     * true 通过
     * false 不通过
     *
     * @param modelNo
     * @return
     */
    public ResultVo<String> checkByModel(String modelNo, int quantity, String customerNo, String userNo) {
        if (StringUtils.isBlank(modelNo)) {
            return ResultVo.failure("型号不可为空");
        }

        // 错误数量
        if (quantity <= 0) {
            return ResultVo.failure("错误数量");
        }

        if (StringUtils.isBlank(userNo)) {
            userNo = "";
        }

        // bug 18050 lyc 客户代码为非广州C1D72的时，才进行型号产地校验。
        /**
         * 客户代码C1D72, 型号产地是中国，有在库时则正常接单，库存不足：则拒单
         * 客户代码不是C1D72，则维持现状，型号产地是中国则拒单，非中国时正常接单。
         */
        ResultVo<Boolean> modelOfCN = productServiceFeignApi.isModelOfCN(modelNo);
        if("C1D72".equals(customerNo)) {
            if (modelOfCN.isSuccess() && modelOfCN.getData() != null && modelOfCN.getData()) {
                // 判断是否有库存
                InventoryRequestDTO dto = new InventoryRequestDTO();
                dto.setCustomerNo(customerNo);
                dto.setModelNo(modelNo);
                dto.setUserNo(userNo);
                ResultVo<Integer> resultInv = inventoryServiceFeignApi.getCustomerCanUseQty(dto);
                Integer canUseQty = 0;
                if (resultInv.isSuccess() && resultInv.getData() != null) {
                    canUseQty = resultInv.getData();
                }
                // 判断是否有库存
                if (canUseQty < quantity) {
                    return ResultVo.failure("中国产型号,且库存不满足,可用数:" + canUseQty);
                }
            }
        } else {
            // 中国产型号不可下单
            if (modelOfCN.isSuccess() && modelOfCN.getData() != null && modelOfCN.getData()) {
                return ResultVo.failure("中国产型号不可下单");
            }
        }

        // 错误型号直接跳过
        ResultVo<Boolean> errorModelNo = productServiceFeignApi.isErrorModelNo(modelNo);
        if (errorModelNo.isSuccess() && errorModelNo.getData() != null && errorModelNo.getData()) {
            return ResultVo.failure("错误型号");
        }
        // 不是偶数订货跳过
        ResultVo<ProductRemark> productRemarkByModelNo = productServiceFeignApi.getProductRemarkByModelNo(modelNo);
        if (productRemarkByModelNo.isSuccess() && productRemarkByModelNo.getData() != null) {
            if ("1".equals(productRemarkByModelNo.getData().getIsEven()) && this.checkParity(quantity) == 1) {
                return ResultVo.failure("该型号必须按偶数订货");
            }
        }
        //  查询型号是否  (贩卖限制品)
        // 返回true 是限制品会拦截
        ResultVo<Boolean> resultVo = productServiceFeignApi.getIsProductRestrict(modelNo, customerNo, userNo);
        if (!Objects.isNull(resultVo) && !resultVo.isSuccess()) {
           throw new RuntimeException("程序异常"+resultVo.toString());
        }
        if (resultVo.getData()) {
            return ResultVo.failure("贩卖限制品未登录" + customerNo + "权限");
        }
        // 是否收敛品
        ResultVo<Boolean> eosModelNoResult = productServiceFeignApi.isEosModelNo(modelNo);
        log.info("检查是否收敛品 {}", eosModelNoResult.toString());
        if (eosModelNoResult.isSuccess() && eosModelNoResult.getData() != null && eosModelNoResult.getData()) {
            InventoryRequestDTO dto = new InventoryRequestDTO();
            dto.setCustomerNo(customerNo);
            dto.setModelNo(modelNo);
            dto.setUserNo(userNo);
            ResultVo<Integer> resultInv = inventoryServiceFeignApi.getCustomerCanUseQty(dto);
            Integer canUseQty = 0;
            if (resultInv.isSuccess() && resultInv.getData() != null) {
                canUseQty = resultInv.getData();
            }
            // 判断是否有库存
            if (canUseQty < quantity) {
                return ResultVo.failure("收敛品型号,且库存不满足,可用数:" + canUseQty);
            }
        }
        return ResultVo.successMsg("ok");
    }


//    @DS("cmdb")
//    public ResultVo<String> receiveOrderSalesFromVSManuOrder(List<VSalesManuorderVO> list) {
//        if (list.isEmpty()) {
//            return ResultVo.failure("暂无所需接入数据");
//        }
//        TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
//        transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
//        transactionTemplate.execute(status -> {
//
//            OrderSalesDO orderSalesDO = new OrderSalesDO();
//
//            Map<String, List<VSalesManuorderVO>> map = new HashMap<>();
//
//            for (VSalesManuorderVO item : list) {
//                List<VSalesManuorderVO> mapList = new ArrayList<>();
//                if (!map.containsKey(item.getCustomerNo().trim())) {
//                    mapList.add(item);
//                    map.put(item.getCustomerNo(), mapList);
//                } else {
//                    map.get(item.getCustomerNo()).add(item);
//                }
//            }
//            LambdaQueryWrapper<OrderSalesDO> queryWrapper;
//            for (String key : map.keySet()) {
//                List<VSalesManuorderVO> vSalesManuorderVOS = map.get(key);
//
//                ResultVo<TblGroupcustomerVO> tblGroupCustInfo = commonServiceFeignApi.getTblGroupCustInfo(key);
//                if (!tblGroupCustInfo.isSuccess() && null == tblGroupCustInfo.getData()) {
//                    continue;
//                }
//                TblGroupcustomerVO tblGroupcustomerVO = tblGroupCustInfo.getData();
//                // 生成订单号
//                ResultVo<String> stringResultVo = commonServiceFeignApi.generatorBillNo("6");
//                if (!stringResultVo.isSuccess() && stringResultVo.getData() == null) {
//                    return ResultVo.failure("生成订单号失败.");
//                }
//                String orderNo = stringResultVo.getData();
//                int itemNo = 0;
//                for (VSalesManuorderVO item : vSalesManuorderVOS) {
//                    queryWrapper = new LambdaQueryWrapper<>();
//                    queryWrapper
//                            .eq(OrderSalesDO::getCorderNo, item.getOrderno())
//                            .eq(OrderSalesDO::getModelNo, item.getModelNo())
//                            .eq(OrderSalesDO::getQuantity, item.getQuantity())
//                            .eq(StringUtils.isNotBlank(item.getCustomerNo()), OrderSalesDO::getCustomerNo, item.getCustomerNo());
//                    Integer count = orderSalesMapper.selectCount(queryWrapper);
//                    if (count != 0) {
//                        continue;
//                    }
//                    try {
//                        if (PublicUtil.isEmpty(item.getCustomerNo())) {
//                            return ResultVo.failure(item.getOrderno() + "客户编码为空");
//                        }
//                        orderSalesDO.setRorderNo(orderNo);
//                        itemNo = itemNo + 1;
//                        orderSalesDO.setRorderItem(itemNo);
//                        orderSalesDO.setCustomerNo(item.getCustomerNo());
//                        orderSalesDO.setCorderNo(item.getOrderno());
//                        orderSalesDO.setModelNo(item.getModelNo());
//                        orderSalesDO.setQuantity(item.getQuantity());
//                        orderSalesDO.setDlvDate(item.getDlvydate());
//                        orderSalesDO.setTransChannel(PublicUtil.isEmpty(item.getTransType()) ? "" : item.getTransType()); // 运输方式
//                        orderSalesDO.setStatus(item.getOptStatus());  // 处理状态
//                        orderSalesDO.setDeliveryDeptNo(tblGroupcustomerVO.getDlvDeptNo()); // 收货地所在营业所代码
//                        orderSalesDO.setRcvClassify(StringUtils.isBlank(item.getExpInvCode()) ? "" : item.getExpInvCode()); // 订货区分
//                        orderSalesDO.setSpecMark(String.valueOf(item.getIsGroup()));
//                        orderSalesDO.setCproductName(item.getProductName());
//                        orderSalesDO.setTaxRate(item.getTaxRate() == null ? new BigDecimal("0.13") : item.getTaxRate()); // 税率
//                        orderSalesDO.setPrice(item.getUnitPricewithTax() == null ? new BigDecimal("0.00") : item.getUnitPricewithTax());
//                        orderSalesDO.setTradeCompanyId("CNO");
//                        orderSalesDO.setFullOrderNo(orderSalesDO.getRorderNo() + "-" + orderSalesDO.getRorderItem());
//                        orderSalesDO.setCreateTime(new Date());
//                        orderSalesDO.setTypeCode(11);
//                        orderSalesDO.setUpdateTime(new Date());
//                        int insert = orderSalesMapper.insert(orderSalesDO);
//                        if (insert <= 0) {
//                            return ResultVo.failure(item.getOrderno() + "=> insert error");
//                        }
//
//                        VSalesManuorderDO vSalesManuorderDO = BeanCopyUtil.copy(item, VSalesManuorderDO.class);
//                        vSalesManuorderDO.setSalesStatus(1);
//                        vSalesManuorderService.update(vSalesManuorderDO);
//
//                    } catch (NumberFormatException e) {
//                        throw new BusinessException(e);
//                    }
//                }
//                // 将地址信息写入OrderDlvData
//                OrderDlvDataDO dlvDataDO = new OrderDlvDataDO();
//                dlvDataDO.setOrderNo(orderNo);
//                dlvDataDO.setItemNo(0);
//                dlvDataDO.setCustomerNo(key);
//                dlvDataDO.setCstmName(vSalesManuorderVOS.get(0).getCstmName());
//                dlvDataDO.setDlvAddress(vSalesManuorderVOS.get(0).getDlvAddress());
//                // 获取客户联系人(在此为收货人)
//                dlvDataDO.setContactPsn(vSalesManuorderVOS.get(0).getContactPsn());
////                ResultVo<CustomerVO> customerByCustomerNo = commonServiceFeignApi.findCustomerByCustomerNo(key);
////                if (customerByCustomerNo.isSuccess()) {
////                    dlvDataDO.setContactPsn(customerByCustomerNo.getData().getName());
////                } else {
////                    dlvDataDO.setContactPsn("未设置");
////                }
//                dlvDataDO.setTelNo(vSalesManuorderVOS.get(0).getTeleNo());
//                dlvDataDO.setCreateTime(new Date());
//                dlvDataDO.setUpdateTime(new Date());
//                orderDlvDataService.saveOrderDlvData(dlvDataDO);
//
//            }
//            return true;
//        });
//        return ResultVo.success("", "订单接入中国工厂订单完毕");
//    }

    @Override
    public ResultVo<String> receiveIPSOrder(List<String> id) {

        String[] split = Constants.carrier_9002_25.split(";");
        // 从字典里获取上次读取到的id
        ResultVo<DataTypeVO> dataTypeCodesInfo = dictCommonService.getDataTypeCodesInfo(split[0], split[1]);
        if(dataTypeCodesInfo == null || !dataTypeCodesInfo.isSuccess()) {
            return ResultVo.failure("从字典里获取上次读取到的id失败");
        }
        LambdaQueryWrapper<IpsSendOrderToOpsDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.gt(CollectionUtils.isEmpty(id),IpsSendOrderToOpsDO::getId, dataTypeCodesInfo.getData().getExtNote1());
        queryWrapper.in(CollectionUtils.isNotEmpty(id), IpsSendOrderToOpsDO::getId, id);
        queryWrapper.orderByDesc(IpsSendOrderToOpsDO::getId);
        List<IpsSendOrderToOpsDO> ipsSendOrderToOpsDOList = ipsSendOrderToOpsMapper.selectList(queryWrapper);

        if(CollectionUtils.isEmpty(ipsSendOrderToOpsDOList)) {
            return ResultVo.success("暂无数据可接入");
        }
        Long maxId = ipsSendOrderToOpsDOList.get(0).getId();
        // 获取采购方与客户的对应关系
        ResultVo<List<DataCodeVO>> dataCodesNotCache = dictCommonService.getDataCodesNotCache("4601");
        if (!dataCodesNotCache.isSuccess()) {
            return ResultVo.failure("获取采购方与客户的对应关系失败");
        }
        List<DataCodeVO> dataCodeVOS = dataCodesNotCache.getData();
        Map<String, String> codeMap = new HashMap<>();
        for (DataCodeVO dataCodeVO : dataCodeVOS) {
            codeMap.put(dataCodeVO.getCode(), dataCodeVO.getExtNote1());
        }
        List<VSalesManuorderVO> list = new ArrayList<>();
        for (IpsSendOrderToOpsDO item : ipsSendOrderToOpsDOList) {
            VSalesManuorderVO vSalesManuorderVO = conventData(item, codeMap);
            if (vSalesManuorderVO != null) {
                list.add(vSalesManuorderVO);
            }
        }
        ResultVo<String> resultVo = rcvIpsOrdertoOps(list);
        if (!resultVo.isSuccess()) {
            return ResultVo.failure(resultVo.getMessage());
        }

        /**
         * 记录最大ID
         */
        if (CollectionUtils.isEmpty(id)) {
            commonMapper.updateUiTblDateType(String.valueOf(maxId));
        }

        return ResultVo.success("接入订单完毕,共接入:" + list.size() + "条数据");
    }

    public ResultVo<String> rcvIpsOrdertoOps(List<VSalesManuorderVO> list) {

        if (CollectionUtils.isEmpty(list)) {
            return ResultVo.success("暂无所需接入数据");
        }

        Map<String, List<VSalesManuorderVO>> map = new HashMap<>(list.size());
        List<VSalesManuorderVO> mapList;
        for (VSalesManuorderVO item : list) {
            if (StringUtils.isBlank(item.getCustomerNo())) {
                pushEvent(item, "采购方代码翻译不出来");
                continue;
            }
            if (!map.containsKey(item.getCustomerNo().trim())) {
                mapList = new ArrayList<>();
                mapList.add(item);
                map.put(item.getCustomerNo(), mapList);
            } else {
                map.get(item.getCustomerNo()).add(item);
            }
        }

        Set<String> orderNos = new HashSet<>();
        List<VSalesManuorderVO> vSalesManuorderVOS;
        ResultVo<TblGroupcustomerVO> tblGroupCustInfo;
        TblGroupcustomerVO tblGroupcustomerVO;
        List<VSalesManuorderVO> manuOrderByAddress;
        OrderSalesDO orderSalesDO;

        for (String customerNo : map.keySet()) {

            vSalesManuorderVOS = map.get(customerNo);
            tblGroupCustInfo = commonServiceFeignApi.getTblGroupCustInfo(customerNo);
            if (!tblGroupCustInfo.isSuccess() && null == tblGroupCustInfo.getData()) {
                continue;
            }

            tblGroupcustomerVO = tblGroupCustInfo.getData();

            //按收货地址,联系人,订单号,项号排序
            manuOrderByAddress = vSalesManuorderVOS.stream()
                    .sorted(Comparator
                            .comparing(VSalesManuorderVO::getDlvAddress)
                            .thenComparing(VSalesManuorderVO::getContactPsn)
                            .thenComparing(VSalesManuorderVO::getOrderno)
                            .thenComparing(VSalesManuorderVO::getItemno))
                    .collect(Collectors.toList());

            String orderNo = "";
            AtomicInteger itemNo = new AtomicInteger();
            LambdaQueryWrapper<OrderSalesDO> queryWrapper = new LambdaQueryWrapper<>();
            String lastUserNo = "";
            String lastDlvAddress = "";
            String lastContactPsn = "";
            String currUserNo;
            String currDlvAddress;
            String currContactPsn;
            OrderNoInfo orderNoInfo;
            RcvDetailDO rcvDetailInfo;
            RcvMasterDO rcvMasterInfo;
            OpsPurchaseInvoiceDO opsPurchaseInvoiceDO;
            ResultVo<String> orderNoResult;
            OrderDlvDataDO dlvDataDO;

            for (VSalesManuorderVO item : manuOrderByAddress) {

                // 收货地址为空
                if (StringUtils.isBlank(item.getDlvAddress()) || StringUtils.isBlank(item.getContactPsn())) {
                    // updateReceiveCNOrderFault(item.getId(), "", "收货信息不可为空");
                    /**
                     * 推送异常订单事件
                     */
                    pushEvent(item, "收货信息不可为空");
                    continue;
                }
                // 电话为空
                if (StringUtils.isBlank(item.getTeleNo())) {
                    // updateReceiveCNOrderFault(item.getId(), "", "电话不可为空");
                    /**
                     * 推送异常订单事件
                     */
                    pushEvent(item, "电话不可为空");
                    continue;
                }

                //客户名称为空
                if(StringUtils.isBlank(item.getCstmName())) {
                    pushEvent(item, "客户名称不可为空");
                    continue;
                }

                orderSalesDO = new OrderSalesDO();
                orderSalesDO.setPurchaseUnitCode(item.getPurchaseUnitCode());
                orderSalesDO.setExtOrderNo(item.getExtOrderNo());
                orderSalesDO.setExtOrderItem(item.getExtOrderItem());
                orderSalesDO.setCustomerNo(item.getCustomerNo());
                orderSalesDO.setEndUser(item.getCustomerNo());
                orderSalesDO.setRemark(item.getRemark());

                if (StringUtils.isNotBlank(item.getUserNo())) {
                    if (item.getUserNo().length() == 5) {
                        orderSalesDO.setUserNo(item.getUserNo());
                        orderSalesDO.setEndUser(item.getEndUser());
                    } else {
                        if (item.getUserNo().startsWith("YY")) {
                            orderSalesDO.setExpLinkNo(item.getUserNo().substring(2));
                        } else {
                            orderSalesDO.setExpLinkNo(item.getUserNo());
                        }
                        // 根据UserNo带出rcvDetail相关信息
                        orderNoInfo = new OrderNoInfo().convertFullOrderNo(orderSalesDO.getExpLinkNo());
                        rcvDetailInfo = getRcvDetailInfo(orderNoInfo.getOrderNo(), String.valueOf(orderNoInfo.getItemNo()));
                        if (null != rcvDetailInfo) {
                            orderSalesDO.setPplNo(rcvDetailInfo.getPplNo());
                            orderSalesDO.setProjectNo(rcvDetailInfo.getProjectNo());
                            orderSalesDO.setShikomiNo(rcvDetailInfo.getShikomiNo());
                            orderSalesDO.setGroupCustomerNo(rcvDetailInfo.getGroupCustomerNo());

                            rcvMasterInfo = getRcvMasterInfo(rcvDetailInfo.getRorderNo());
                            if (rcvMasterInfo != null) {
                                orderSalesDO.setEndUser(rcvMasterInfo.getEndUser());
                                orderSalesDO.setUserNo(rcvMasterInfo.getEndUser());
                            }
                        } else {
                            opsPurchaseInvoiceDO = getOpsPurchaseInvoiceInfo(orderNoInfo.getOrderNo(), String.valueOf(orderNoInfo.getItemNo()));
                            if(opsPurchaseInvoiceDO != null) {
                                if (StringUtils.isBlank(opsPurchaseInvoiceDO.getUserNo())) {
                                    opsPurchaseInvoiceDO.setUserNo(opsPurchaseInvoiceDO.getCustomerNo());
                                }
                                orderSalesDO.setUserNo(opsPurchaseInvoiceDO.getUserNo());
                                orderSalesDO.setEndUser(opsPurchaseInvoiceDO.getUserNo());
                            }
                        }
                    }
                }

                // 1-门户订单 2-中国制造 3-广州制造
                ResultVo<String> resultVo = checkByModel(item.getModelNo(), item.getQuantity(), item.getCustomerNo(), orderSalesDO.getUserNo());
                if (!resultVo.isSuccess()) {
                    // updateReceiveCNOrderFault(item.getId(), "", resultVo.getMessage());
                    /**
                     * 推送异常订单事件
                     */
                    pushEvent(item, resultVo.getMessage());
                    continue;
                }

                /**
                 * 型号校验
                 */
                List<ModelNoCheckBean> modelNoCheckBeans = opsCommonService.modelNoListCheck(Arrays.asList(item.getModelNo()));
                if (CollectionUtils.isEmpty(modelNoCheckBeans) || (modelNoCheckBeans.get(0).getResult() != null && !modelNoCheckBeans.get(0).getResult()) ) {
                    // updateReceiveCNOrderFault(item.getId(), "", "型号校验失败");
                    /**
                     * 推送异常订单事件
                     */
                    pushEvent(item, "型号校验失败");
                    continue;
                }

                // 查询是否存在
                queryWrapper.clear();
                queryWrapper.eq(OrderSalesDO::getPreSalesOrderNo, item.getOrderno() + "-" + item.getItemno());
                if (orderSalesMapper.selectCount(queryWrapper) > 0) {
//                    lambdaUpdateWrapper.clear();
//                    lambdaUpdateWrapper
//                            .eq(VSalesManuorderDO::getId, item.getId())
//                            .set(VSalesManuorderDO::getOptStatus, "1")
//                            .set(VSalesManuorderDO::getSalesRemark, "重复，已存在")
//                            .set(VSalesManuorderDO::getSalesUpdateTime, new Date());
//                    vSalesManuorderMapper.update(null, lambdaUpdateWrapper);
                    /**
                     * 推送异常订单事件
                     */
                    pushEvent(item, "重复，已存在");
                    log.info("中国制造订单{}重复发单", item.getOrderno() + "-" + item.getItemno());
                    continue;
                }

                currUserNo = orderSalesDO.getEndUser().trim();
                currDlvAddress = item.getDlvAddress().trim();
                currContactPsn = item.getContactPsn().trim();
                if (!lastUserNo.equalsIgnoreCase(currUserNo)
                        || !lastDlvAddress.equalsIgnoreCase(currDlvAddress)
                        || !lastContactPsn.equalsIgnoreCase(currContactPsn)) {
                    // 接入中国制造的订单是，如果userNo里面有订单号，并且如果从rcvmaster查出的endUser与上一条的不同时，另外生成一个订单号
                    orderNoResult = commonServiceFeignApi.generatorBillNo("6"); // 生成订单号
                    if (!orderNoResult.isSuccess() && orderNoResult.getData() == null) {
                        return ResultVo.failure(item.getOrderno() + "-" + item.getItemno() + "制造订单接入,生成订单号失败.");
                    }
                    orderNo = orderNoResult.getData();
                    orderSalesDO.setRorderNo(orderNo);
                    itemNo.set(1);
                    lastUserNo = currUserNo;
                    lastDlvAddress = currDlvAddress;
                    lastContactPsn = currContactPsn;
                } else {
                    orderSalesDO.setRorderNo(orderNo);
                    itemNo.set(itemNo.get() + 1);
                }
                orderSalesDO.setRorderItem(itemNo.get());
                orderSalesDO.setCorderNo(item.getOrderno() + "-" + item.getItemno());
                orderSalesDO.setOrOrderNo(item.getOrderno());
                orderSalesDO.setModelNo(item.getModelNo());
                orderSalesDO.setQuantity(item.getQuantity());
                orderSalesDO.setDlvDate(item.getDlvydate());
                orderSalesDO.setWarehouseSendDate(item.getDlvydate());
                orderSalesDO.setCdlvDate(item.getDlvydate());
                orderSalesDO.setTransChannel(item.getTransType()); // 运输方式
                orderSalesDO.setStatus("0");  // 处理状态
                orderSalesDO.setDeliveryDeptNo(tblGroupcustomerVO.getDlvDeptNo()); // 收货地所在营业所代码
                orderSalesDO.setDeptNo(tblGroupcustomerVO.getDeptNo());
                if (item.getIsGroup() != null) {
                    orderSalesDO.setSpecMark(String.valueOf(item.getIsGroup()));
                }
                orderSalesDO.setCproductName(item.getProductName());
                orderSalesDO.setTradeCompanyId("CN0");
                orderSalesDO.setFullOrderNo(orderSalesDO.getRorderNo() + "-" + orderSalesDO.getRorderItem());
                orderSalesDO.setPreSalesOrderNo(orderSalesDO.getCorderNo());
                orderSalesDO.setCreateTime(new Date());
                orderSalesDO.setTypeCode(11);
                // 客户类型 直销
                com.smc.smccloud.model.CustomerVO info = opsCommonService.getCustomerByCustomerNo(orderSalesDO.getCustomerNo());
                if (info != null) {
                    orderSalesDO.setCustomerType(info.getCustomerType());
                }

                orderSalesDO.setUpdateTime(new Date());
                if (StringUtils.isNotBlank(item.getContactPsn())) {
                    orderSalesDO.setEmpOffice(item.getContactPsn().trim());
                }
                // bug 11509  2023-08-14 update by lyc
                if (StringUtils.isNotBlank(item.getModelNo()) && item.getQuantity() != null) {
                    BigDecimal epriceFromProductPrice = getEpriceFromProductPrice(item.getModelNo(), item.getQuantity());
                    orderSalesDO.setEPrice(epriceFromProductPrice);
                    // 计算e率  放到定时作业计算价格里
//                    BigDecimal bigDecimal = CommonFormulaUtil.calcDiscount(item.getUnitprice(), epriceFromProductPrice);
//                    orderSalesDO.setDiscount(bigDecimal);
                }

                try {
                    int insert = orderSalesMapper.insert(orderSalesDO);
                    if (insert <= 0) {
                        return ResultVo.failure(item.getOrderno() + "=> insert error");
                    }

                    // 将地址信息写入OrderDlvData
                    if (orderSalesDO.getRorderItem() == 1) {
                        orderNos.add(orderSalesDO.getRorderNo());
                        dlvDataDO = new OrderDlvDataDO();
                        dlvDataDO.setOrderNo(orderSalesDO.getRorderNo());
                        dlvDataDO.setItemNo(0);
                        dlvDataDO.setCustomerNo(orderSalesDO.getCustomerNo());
                        dlvDataDO.setCstmName(item.getCstmName());
                        if (StringUtils.isNotBlank(item.getContactPsn())) {
                            dlvDataDO.setContactPsn(item.getContactPsn().trim());
                            dlvDataDO.setDlvAddress(item.getDlvAddress() + "（" + item.getContactPsn().trim() + "）");
                        }
                        dlvDataDO.setTelNo(item.getTeleNo());
                        dlvDataDO.setCreateTime(new Date());
                        dlvDataDO.setUpdateTime(new Date());
                        dlvDataDO.setProvince(tblGroupcustomerVO.getProvince());
                        dlvDataDO.setCity(tblGroupcustomerVO.getCity());
                        dlvDataDO.setDistrict(tblGroupcustomerVO.getDistrict());
                        orderDlvDataService.saveOrderDlvData(dlvDataDO);
                    }
                } catch (Exception e) {
                    log.error("接入中国制造订单失败: 异常信息: {},  异常数据 {}", e, JSON.toJSONString(orderSalesDO));
                    /**
                     * 推送异常订单事件
                     */
                    pushEvent(item, e.getMessage());
                    // throw new BusinessException();
                }
            }
        }
        //发送接单消息
        for (String orderNo : orderNos) {
            // 推送到 订单接单-消息接收器
            OrderSalesDO salesDO = new OrderSalesDO();
            salesDO.setRorderNo(orderNo);
            addOrderReceiveMsg(salesDO);
        }
        return ResultVo.success("订单接入中国工厂订单" + String.join(";", orderNos));
    }


    public void pushEvent(VSalesManuorderVO item,String remark) {
        log.info("推送异常订单事件: {}", JSONUtil.toJsonStr(item));
        try {
            IpsReceiveDeliveryInfoFromAllVO params = new IpsReceiveDeliveryInfoFromAllVO();
            params.setFactSupplier(item.getCustomerNo());
            params.setOrderNo(item.getOrderno());
            params.setOrderItem(item.getItemno());
            params.setModel(item.getModelNo());
            params.setQuantity(item.getQuantity().doubleValue());
            params.setRemark("订单受理失败,原因:"+remark);
            params.setPurchaseUnitCode(item.getPurchaseUnitCode());
            params.setSupplierOperateTime(new Date());

            OpsEventBusDO eventBusDO = new OpsEventBusDO();
            eventBusDO.setOrderId(item.getOrderno());
            eventBusDO.setOrderItem(item.getItemno());
            eventBusDO.setDealFlag(0);
            eventBusDO.setCreator("receiveIPSOrder");
            eventBusDO.setCreTime(new Date());
            eventBusDO.setEventCode("CUSTOMER_ORDER_ALLOT_FAILURE");
            eventBusDO.setVersion(0);
            eventBusDO.setParams(JSONUtil.toJsonStr(params));
            eventService.insert(eventBusDO);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("事件写入失败：{}",e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public VSalesManuorderVO conventData(IpsSendOrderToOpsDO item,Map<String, String> map) {
           VSalesManuorderVO dto = new VSalesManuorderVO();

           boolean isOK = true;
           String errMsg = "";

        try {
            ReceiveContentInfoDto receiveContentInfoDto = JSONObject.parseObject(item.getContentInfo(), ReceiveContentInfoDto.class);

            dto.setModelNo(item.getModelNo());
            dto.setQuantity(item.getQty());
            dto.setDlvydate(item.getSpecifiedDeliveryDate());
            dto.setTransType(item.getSpecifiedDeliveryWay());
            dto.setOrderno(item.getOrderNo());
            dto.setItemno(item.getItemNo());
            dto.setExtOrderNo(item.getOrderNo());
            dto.setExtOrderItem(item.getItemNo());
            dto.setProductName(item.getModelName());
            AssembleOrderDto assembleOrderDto = receiveContentInfoDto.getAssembleOrder();
            /**
             *   isAssembleOrder = 1  是组装  isAssembleOrder = 0  不是组装
             *    当为1时   List<AssembleContentDto> assembleContent 里的
             *        assemblyType = 0 对应OPS 的 isGroup 为1
             *        assemblyType = 1  isGroup 为 2
             */
            if(assembleOrderDto != null && StringUtils.isNotBlank(assembleOrderDto.getIsAssembleOrder()) && assembleOrderDto.getIsAssembleOrder().equals("1")) {
                if (assembleOrderDto.getAssembleContent() != null) {
                    if (CollectionUtils.isEmpty(assembleOrderDto.getAssembleContent())) {
                        dto.setIsGroup(0);
                    } else {
                        for (AssembleContentDto assembleContentDto : assembleOrderDto.getAssembleContent()) {
                            if (item.getItemNo().equals(assembleContentDto.getItemNo()) && item.getOrderNo().equals(assembleContentDto.getOrderNo())) {
                                if ("0".equals(assembleContentDto.getAssemblyType())) {
                                    dto.setIsGroup(1);
                                } else if ("1".equals(assembleContentDto.getAssemblyType())){
                                    dto.setIsGroup(2);
                                }
                            }
                        }
                    }
                } else {
                    dto.setIsGroup(0);
                }
            } else {
                dto.setIsGroup(0);
            }
            dto.setCstmName(item.getRequestDepartmentName());
            // 19553bug 工厂订单接入地址
            if(receiveContentInfoDto.getDeliveryInfo() == null || StringUtils.isBlank(receiveContentInfoDto.getDeliveryInfo().getExpectedReceiveAddress())) {
                if (StringUtils.isBlank(item.getReceivingAddress())) {
                    errMsg = "拒单:地址为空";
                    isOK = false;
                }
                if(StringUtils.isNotBlank(item.getPlantmarkAddress())) {
                    dto.setDlvAddress(item.getReceivingAddress()+item.getPlantmarkAddress());
                } else {
                    dto.setDlvAddress(item.getReceivingAddress());
                }
            } else {
                dto.setDlvAddress(receiveContentInfoDto.getDeliveryInfo().getExpectedReceiveAddress());
            }
            if (StringUtils.isBlank(dto.getDlvAddress())) {
                errMsg = "拒单:地址为空";
                isOK = false;
            }
            if (dto.getDlvAddress().length() > 100) {
                errMsg = "拒单: 地址超过100长度";
                isOK = false;
            }
            dto.setContactPsn(item.getReceivingConnector());
            dto.setTeleNo(item.getReceivingDepartmentTeleno());
            if (StringUtils.isNotBlank(item.getPurchaseUnitCode())) {
                String s = map.get(item.getPurchaseUnitCode());
                dto.setCustomerNo(s);
                dto.setPurchaseUnitCode(item.getPurchaseUnitCode());
            }

            if (receiveContentInfoDto.getEnduserInfo() != null) {
                dto.setRemark(receiveContentInfoDto.getEnduserInfo().getEnduserRemarks());
                if (StringUtils.isNotBlank(receiveContentInfoDto.getEnduserInfo().getPurchaseEnduserNo()) && receiveContentInfoDto.getEnduserInfo().getPurchaseEnduserNo().length() == 5 ) {
                    dto.setUserNo(receiveContentInfoDto.getEnduserInfo().getPurchaseEnduserNo());
                    dto.setEndUser(receiveContentInfoDto.getEnduserInfo().getPurchaseEnduserNo());
                } else {
                    if(StringUtils.isNotBlank(receiveContentInfoDto.getEnduserInfo().getEnduserOrderNo())) {
                        dto.setUserNo(receiveContentInfoDto.getEnduserInfo().getEnduserOrderNo());
                    }
                }
            }

            if(!isOK) {
                pushEvent(dto,errMsg);
                return null;
            }
            return dto;
        } catch (Exception e) {
            dto.setPurchaseUnitCode(item.getPurchaseUnitCode());
            // 追加接单异常的事件,写给IPS推得状态表里
            pushEvent(dto,"数据转换异常");
        }
        return null;
    }


    public ResultVo<List<OpsVManuorderToSales>> findPendingCNMOrder() {
        LambdaQueryWrapper<OpsVManuorderToSales> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OpsVManuorderToSales::getOptStatus, 0);
        List<OpsVManuorderToSales> opsVManuorderToSales = opsVManuorderToSalesMapper.selectList(queryWrapper);
        if (opsVManuorderToSales.isEmpty()) {
            return ResultVo.success(Collections.emptyList(), "暂无数据可接入");
        }
        return ResultVo.success(opsVManuorderToSales);
    }

    /**
     * 交货日期、发货日期较验
     *
     * @param workDay 订货日期
     * @param dlvDate 交货日期、发货日期
     * @return boolean
     */
    /*private boolean validDlvDate(Date workDay, Date dlvDate) {
        // 判断是否在最晚发货日之前，最晚发货日为六个月后
        Date last = DateUtil.addMonth(workDay, 6);
        // 物流发货日不能小于订货日期
        if (dlvDate.before(workDay) || dlvDate.after(last)) {
            return false;
        }
        return true;
    }*/

    /**
     * 交货日期、发货日期较验
     *
     * @param workDay 订货日期
     * @param dlvDate 交货日期、发货日期
     * @return boolean
     */
    private boolean validDlvDate(Date workDay, Date dlvDate) {
        // 发货日不能早于订货日期
        return workDay.compareTo(dlvDate) <= 0;
    }

    /**
     * 判断奇偶性
     *
     * @param number 数字
     * @return 偶数-0; 奇数-1
     */
    private int checkParity(int number) {
        return number & 0x1;
    }

    /**
     * 获取交易主体ID
     *
     * @param tradeCompanyName 交易主体名称
     * @return tradeCompanyId
     */
    private String getTradeCompanyId(String tradeCompanyName) {
        if (tradeCompanyName.contains("北京分")) {
            return "CN0-B";
        }
        if (tradeCompanyName.contains("广州分")) {
            return "CN0-G";
        }
        if (tradeCompanyName.contains("上海分")) {
            return "CN0-S";
        }
        if (tradeCompanyName.contains("自动化")) {
            return "CN0";
        }
        return null;
    }


    public RcvDetailDO getRcvDetailInfo(String rorderNo, String itemNo) {
        if (StringUtils.isBlank(rorderNo) || StringUtils.isBlank(itemNo)) {
            return null;
        }
        LambdaQueryWrapper<RcvDetailDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(RcvDetailDO::getRorderNo, rorderNo)
                .eq(RcvDetailDO::getRorderItem, itemNo);
        return rcvdetailMapper.selectOne(queryWrapper);
    }

    // 88/99开头的关联单号 去ops_purchaseInvoice查用户代码
    public OpsPurchaseInvoiceDO getOpsPurchaseInvoiceInfo(String rorderNo,String itemNo) {
        LambdaQueryWrapper<OpsPurchaseInvoiceDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OpsPurchaseInvoiceDO::getOrderNo,rorderNo).eq(OpsPurchaseInvoiceDO::getItemNo,itemNo);
        List<OpsPurchaseInvoiceDO> opsPurchaseInvoiceDOS = opsPurchaseInvoiceMapper.selectList(queryWrapper);
        OpsPurchaseInvoiceDO opsPurchaseInvoiceDO = null;
        if (CollectionUtils.isNotEmpty(opsPurchaseInvoiceDOS)) {
            opsPurchaseInvoiceDO = opsPurchaseInvoiceDOS.get(0);
        }
        return opsPurchaseInvoiceDO;
    }

    public RcvMasterDO getRcvMasterInfo(String rorderno) {
        if (StringUtils.isBlank(rorderno)) {
            return null;
        }

        LambdaQueryWrapper<RcvMasterDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(RcvMasterDO::getRorderNo, rorderno);
        return rcvmasterMapper.selectOne(queryWrapper);
    }

}
