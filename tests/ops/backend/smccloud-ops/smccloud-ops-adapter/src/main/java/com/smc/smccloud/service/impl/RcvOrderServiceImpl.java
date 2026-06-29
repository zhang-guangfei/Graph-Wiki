package com.smc.smccloud.service.impl;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.sales.ops.enums.FileUploadTypeEnum;
import com.smc.smccloud.constants.CommonConstants;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.model.constants.Constants;
import com.smc.smccloud.core.model.enums.CarrierEnum;
import com.smc.smccloud.core.rabbitmq.RabbitMqMessage;
import com.smc.smccloud.core.redis.RedisManager;
import com.smc.smccloud.core.redis.redisson.RedissonUtil;
import com.smc.smccloud.core.utils.BeanCopyUtil;
import com.smc.smccloud.core.utils.DateUtil;
import com.smc.smccloud.mapper.OrderSalesMapper;
import com.smc.smccloud.model.CustomerVO;
import com.smc.smccloud.model.DataTypeVO;
import com.smc.smccloud.model.OrderSales.OrderSalesDTO;
import com.smc.smccloud.model.OrderSales.OrderSalesDetailVO;
import com.smc.smccloud.model.adapter.order.AdapterOrderResult;
import com.smc.smccloud.model.fileupload.FileUpload;
import com.smc.smccloud.model.orderlog.OrderLogVO;
import com.smc.smccloud.model.ordersales.OrderDlvDataDO;
import com.smc.smccloud.model.ordersales.OrderSalesDO;
import com.smc.smccloud.model.orderstate.OrderStateVO;
import com.smc.smccloud.model.product.ProductRemark;
import com.smc.smccloud.rabbitmq.SendRcvOrderMqMsg;
import com.smc.smccloud.service.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @Author lyc
 * @Date 2023/9/12 9:05
 * @Descripton TODO
 */
@Service
@Slf4j
public class RcvOrderServiceImpl implements RcvOrderService {
    @Resource
    private PlatformTransactionManager transactionManager;
    @Resource
    private OpsCommonService opsCommonService;
    @Resource
    private DictCommonService dictCommonService;
    @Resource
    private ProductServiceFeignApi productServiceFeignApi;
    @Resource
    private OrderSalesMapper orderSalesMapper;
    @Resource
    private RedisManager redisManager;
    @Resource
    private RedissonUtil redissonUtil;
    @Resource
    private SendRcvOrderMqMsg sendRcvOrderMqMsg;
    @Resource
    private OrderDlvDataService orderDlvDataService;
    @Resource
    private OpsAttachedFileManageService opsAttachedFileManageService;

    @Value("${sales-file-upload-path.url}")
    private String salesFileUploadPath;

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
        // 客户信息
        CustomerVO customerInfo = opsCommonService.getCustomerByCustomerNo(commonInfo.getCustomerNo());
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

        for (OrderSalesDetailVO item : dto.getItems()) {
            log.info("门户订单保存: {} --- {}", item.getOrOrderNo(), item.getPreSalesOrderNo());
            result = new AdapterOrderResult();
            result.setResult(false);
            result.setNo(item.getPreSalesOrderNo());
            resultList.add(result);
            // 判断是否特殊型号
            boolean specialModel = opsCommonService.isSpecialModel(item.getModelNo());
            if (item.isSpecialModelFlag()) {
                if (!specialModel) {
                    result.setMessage("specialModelFlag是true,型号不在特殊型号清单里");
                    continue;
                }
            } else {
                if(specialModel) {
                    result.setMessage("specialModelFlag是false,型号不能在特殊型号清单里");
                    continue;
                }
            }

            if (StringUtils.isEmpty(commonInfo.getDeptNo())) {
                result.setMessage("所属营业所不能为空");
                continue;
            }
            if (customerInfo == null) {
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
            // 最低售价判断
            if(item.getHasLowestPrice()) {
                if (StringUtils.isBlank(item.getFileType())) {
                    result.setMessage("如果有最低售价,必须有附件和文件类型");
                    continue;
                }
                if (CollectionUtils.isEmpty(item.getFileList())) {
                    result.setMessage("如果有最低售价,必须有附件");
                    continue;
                }
                boolean flag = false;
                for (FileUpload f : item.getFileList()) {
                    if(f == null || StringUtils.isBlank(f.getFilePath())) {
                        flag = true;
                        break;
                    }
                }
                if (flag) {
                    result.setMessage("如果有最低售价,必须有附件且附件路径不可为空");
                    continue;
                }
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

                if (item.getHasLowestPrice()) {
                    // 保存最低售价附件信息
                    try {
                        ResultVo<String> resultVo = opsAttachedFileManageService.insertFileInfo(orderSalesDO.getFullOrderNo(), item.getFileList(),
                                CommonConstants.COMMON_USER_OPS, item.getFileType(), FileUploadTypeEnum.MINPRICE.getBusinessType());
                        log.info("保存最低售价附件信息 {}", JSONUtil.toJsonStr(resultVo));
                        if (!resultVo.isSuccess()) {
                            return resultVo;
                        }
                    } catch (Exception e) {
                        log.error("保存接单最低售价附件失败: {}", e.getMessage(), e);
                        transactionStatus.setRollbackOnly(); // 手动回滚
                        return ResultVo.failure("保存接单最低售价附件失败: " + e.getMessage());
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
            log.info("进入发送订单接入消息(开始) : {}", orderNo);
            redissonUtil.unlock(keyPrefix + orderNo);
            // bug 19585  写入rcv表的处理废弃mq的使用 只通过job进行写入，频率需要调节2分钟一次
//            orderSalesInfo = new OrderSalesDO();
//            orderSalesInfo.setRorderNo(orderNo);
//            try {
//                this.addOrderReceiveMsg(orderSalesInfo);
//                log.info("进入发送订单接入消息(发送完毕) : {}", orderNo);
//            } catch (Exception e) {
//                log.error("发送订单接入消息到mq异常 {}",e.getMessage(),e);
//            }
        }

        return ResultVo.success(resultList);
    }

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
        return sendRcvOrderMqMsg.sendOrderReceiveMsg(rabbitMqMessage);
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
            if (!sendRcvOrderMqMsg.sendOrderLogMsg(orderLogMQMag)) {
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
            if (!sendRcvOrderMqMsg.sendOrderStateMsg(orderStateMqMsg)) {
                log.error("推送订单状态至MQ失败");
            }
        } catch (Exception e) {
            log.error("推送订单状态至MQ失败: {}", e.getMessage(), e);
        }
    }



}
