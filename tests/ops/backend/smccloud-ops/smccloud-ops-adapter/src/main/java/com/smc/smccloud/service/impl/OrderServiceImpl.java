package com.smc.smccloud.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.sales.ops.db.entity.Orderdlvdata;
import com.sales.ops.dto.order.OpsOrderModifyDto;
import com.sales.ops.dto.util.CommonResult;
import com.sales.ops.dto.util.UserDto;
import com.sales.ops.enums.RcvOrderStatusEnum;
import com.sales.ops.feign.OpsOrderFeignApi;
import com.smc.smccloud.config.AdapterRedisManager;
import com.smc.smccloud.constants.CommonConstants;
import com.smc.smccloud.core.exception.BusinessException;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.model.constants.OrderExceptionCode;
import com.smc.smccloud.core.model.enums.CancelOrderToSalesStatus;
import com.smc.smccloud.core.model.enums.CarrierEnum;
import com.smc.smccloud.core.model.enums.OrderModifyEnum;
import com.smc.smccloud.core.model.enums.OrderModifyTypeEnum;
import com.smc.smccloud.core.rabbitmq.RabbitMqMessage;
import com.smc.smccloud.core.rabbitmq.constants.Constants;
import com.smc.smccloud.core.utils.BeanCopyUtil;
import com.smc.smccloud.core.utils.DateUtil;
import com.smc.smccloud.core.utils.SpringContextUtil;
import com.smc.smccloud.mapper.OrderSalesMapper;
import com.smc.smccloud.model.OrderSales.OrderDlvDataVO;
import com.smc.smccloud.model.OrderSales.OrderSalesDTO;
import com.smc.smccloud.model.OrderSales.OrderSalesDetailVO;
import com.smc.smccloud.model.adapter.order.*;
import com.smc.smccloud.model.customer.CustomerVO;
import com.smc.smccloud.model.order.OrderCancelResult;
import com.smc.smccloud.model.order.OrderNoInfo;
import com.smc.smccloud.model.ordermodify.OrderDeliveryModifyInfo;
import com.smc.smccloud.model.ordermodify.OrderModifyVO;
import com.smc.smccloud.model.ordersales.OrderSalesDO;
import com.smc.smccloud.model.receiveorder.OrderSpecExpType;
import com.smc.smccloud.model.receiveorder.RcvDetailVO;
import com.smc.smccloud.model.receiveorder.RcvMasterVO;
import com.smc.smccloud.rabbitmq.SendMessage;
import com.smc.smccloud.service.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Author: B90034
 * Date: 2022-02-14 16:58
 * Description: 订单服务
 */
@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    @Resource
    private OrderSalesMapper orderSalesMapper;
    @Resource
    private SendMessage sendMessage;
    @Resource
    private ReceiveOrderServiceFeignApi receiveOrderServiceFeignApi;
    @Resource
    private OrderModifyServiceFeignApi orderModifyServiceFeignApi;
    @Resource
    private CommonServiceFeignApi commonServiceFeignApi;
    @Resource
    private OpsOrderFeignApi opsOrderFeignApi;
    @Resource(name = "adapterRedisManager")
    private AdapterRedisManager redisManager;
    @Resource
    private OpsCommonService opsCommonService;
    @Resource
    private RcvOrderService rcvOrderService;

    @Override
    public List<OrderCancelResult> handleOrderCancelMQ(OrderCancelDTO dto) {
        OrderModifyVO info;
        List<OrderModifyVO> applyItemList = new ArrayList<>(dto.getItems().size());
        ResultVo<RcvMasterVO> resultVo;
        Map<String, RcvMasterVO> map = new HashMap<>();
        RcvMasterVO rcvMasterVO;

        for (OrderCancelItem item : dto.getItems()) {
            info = new OrderModifyVO();
            info.setApplyNo(item.getNo());
            info.setOrderNo(item.getOrderNo());
            info.setStatus(Integer.parseInt(OrderModifyEnum.waitHand.getCode())); // 待处理
            info.setModifyType(OrderModifyTypeEnum.cancel_order.getCode());
            info.setReason(dto.getApplyReason()); // 申请原因
            info.setApplyPersonNo(dto.getApplyPersonNo());
            info.setApplyTime(dto.getApplyTime());
            info.setCreateTime(new Date());
            info.setCreateUser(CommonConstants.COMMON_USER_SALES);
            String mainOrderNo = conventOrderNo(item.getOrderNo());

            if (mainOrderNo != null) {
                if (map.containsKey(mainOrderNo)) {
                    rcvMasterVO = map.get(mainOrderNo);
                    info.setDeptNo(rcvMasterVO.getDeptNo());
                    info.setCustomerNo(rcvMasterVO.getCustomerNo());
//                    info.setUserNo(rcvMasterVO.getUserNo());
//                    info.setEndUser(rcvMasterVO.getEndUser());
                } else {
                    // 查询主单信息
                    resultVo = receiveOrderServiceFeignApi.findOrderMaster(mainOrderNo);
                    if (resultVo.isSuccess() & resultVo.getData() != null) {
                        rcvMasterVO = resultVo.getData();
                        info.setDeptNo(rcvMasterVO.getDeptNo());
                        info.setCustomerNo(rcvMasterVO.getCustomerNo());
//                        info.setUserNo(rcvMasterVO.getUserNo());
//                        info.setEndUser(rcvMasterVO.getEndUser());
                        map.put(mainOrderNo, rcvMasterVO);
                    }
                }
            }
            applyItemList.add(info);
        }

        ResultVo<List<OrderDeleteReturn>> applyResult = orderModifyServiceFeignApi.applyOrderCancel(applyItemList);
        List<OrderDeleteReturn> resultList;
        OrderDeleteReturn result;
        if (!applyResult.isSuccess()) {
            resultList = new ArrayList<>(applyItemList.size());
            for (OrderModifyVO vo : applyItemList) {
                result = new OrderDeleteReturn();
                result.setNo(vo.getApplyNo());
                result.setOrderNo(vo.getOrderNo());
                // 处理结果：0申请成功；1申请失败；2删除成功；3删除失败
                result.setResult(CancelOrderToSalesStatus.apply_fail.getCode());
                result.setMessage("申请失败，稍后再申请！");
                resultList.add(result);
            }
        } else {
            resultList = applyResult.getData();
        }
        log.info("取消订单推送门户参数: {}", JSONObject.toJSONString(applyResult));
        return BeanCopyUtil.copyList(resultList, OrderCancelResult.class);
    }

    public static String conventOrderNo(String orderNo) {
        if (orderNo.contains("-")) {
            return orderNo.split("-")[0];
        } else {
            if (orderNo.length() < 7) {
                return null;
            }
            return orderNo.substring(0, orderNo.length() - 3);
        }
    }

    @Override
    public ResultVo<Boolean> sendOrderCancelReturnMessage(List<OrderCancelResult> resultList) {
        if (CollectionUtils.isEmpty(resultList)) {
            return ResultVo.failure("取消订单返回结果不能为空");
        }
        log.info("回调门户删单处理 参数: {}", resultList);

        RabbitMqMessage rabbitMqMessageOrder = new RabbitMqMessage(JSON.toJSONString(resultList),
                Constants.ERP_ORDER_CANCEL_RETURN,
                Constants.ORDER,
                Constants.SMS);
        sendMessage.opsToSmsOrderCancel(rabbitMqMessageOrder);
        return ResultVo.success(true);
    }

    @Override
    public List<AdapterOrderResult> handleOrderCreateMQ(List<AdapterOrder> orderList, Date workTime) {
        if (CollectionUtils.isEmpty(orderList)) {
            return null;
        }
        // 校验门户合同单号 bug-10027
        List<String> hddnos = orderList.stream().map(AdapterOrder::getHddno).distinct().collect(Collectors.toList());
        if (hddnos.size() > 1 || StringUtils.isBlank(hddnos.get(0))) {
            List<AdapterOrderResult> resultList = new ArrayList<>(orderList.size());
            AdapterOrderResult result;
            String errorMsg = hddnos.size() > 1 ? "存在多个合同单号" : "合同单号不能为空";
            for (AdapterOrder item : orderList) {
                result = new AdapterOrderResult();
                result.setResult(false);
                result.setNo(item.getNo());
                result.setMessage(errorMsg);
                resultList.add(result);
            }
            return resultList;
        }

        Date startTime = new Date();
        OrderSalesDTO orderSalesDTO = null;
        OrderDlvDataVO orderDlvDataVO;
        OrderSalesDetailVO orderSalesDetail;
        int typeCode = 1;
        String status = "0";

        for (AdapterOrder order : orderList) {
            if (orderSalesDTO == null) {
                orderSalesDTO = new OrderSalesDTO();
                /**
                 * 赊销标识 20772bug  对应委托：WTSR2026000407，需要从门户接入订单赊销标识信息，后续开票中使用
                 */
                if(order.getCreditSaleClientFlag() != null) {
                    if (order.getCreditSaleClientFlag()) {
                        orderSalesDTO.setCreditSaleClientFlag("1");
                    } else {
                        orderSalesDTO.setCreditSaleClientFlag("0");
                    }
                }
                orderSalesDTO.setItems(new ArrayList<>(orderList.size()));
                orderSalesDTO.setCustomerNo(order.getCustomerno()); // 客户代码
                orderSalesDTO.setUserNo(order.getUserno());
                orderSalesDTO.setEndUser(order.getEnduser()); // 最终用户代码

                if (StringUtils.isBlank(orderSalesDTO.getEndUser())) {
                    orderSalesDTO.setEndUser(orderSalesDTO.getUserNo());
                }
                if (StringUtils.isBlank(orderSalesDTO.getEndUser())) {
                    orderSalesDTO.setEndUser(orderSalesDTO.getCustomerNo());
                }
                if (StringUtils.isNotBlank(order.getPrjcode())) {
                    order.setPrjcode(order.getPrjcode().trim());
                }

                // 门户订单
                orderSalesDTO.setFromType(1);
                orderSalesDTO.setDlvEntire(order.getDlventire()); // 货齐出货
                orderSalesDTO.setTransFee(order.getTransfee()); // 运费负担
                orderSalesDTO.setTransChannel(order.getTranschannel());  // 承运方
                orderSalesDTO.setDlvSite(order.getDlvsite()); // 发货方式--直发营业所
                orderSalesDTO.setContractNo(order.getContractno()); // 合同代码
                orderSalesDTO.setQuotationNo(order.getQuotationno()); // 报价单号
                orderSalesDTO.setWorkday(workTime); // 订货日期(发单时间)
                //orderSalesDTO.setPrjCode(order.getPrjcode()); // 项目代码
                orderSalesDTO.setEmpSale(order.getEmpsale()); // 外勤代码--申请人
                orderSalesDTO.setEmpOffice(order.getEmpoffice()); // 申请人代码
                orderSalesDTO.setDeptNo(order.getDeptno()); // 订货部门
                // orderSalesDTO.setHLCode(order.getHlCode()); // HLCode bug-10665 回滚
                orderSalesDTO.setPriceChecked(Boolean.FALSE); // 价格验证标志
                orderSalesDTO.setTradeCompanyId(order.getTradecompanyid()); // 销售主体
                orderSalesDTO.setTypeCode(typeCode); // 订单类别
                orderSalesDTO.setGroupCustomerNo(this.getGroupCustomerNo(orderSalesDTO, order.getCustomerClusterBinds())); // 设置客户群号
                // 订单收货信息
                orderDlvDataVO = new OrderDlvDataVO();
                orderDlvDataVO.setCustomerNo(order.getAddressInfo().getCustomerNo());
                orderDlvDataVO.setCstmName(order.getAddressInfo().getCustomerName());
                orderDlvDataVO.setDlvAddress(order.getAddressInfo().getDlvAddress());
                orderDlvDataVO.setContactPsn(order.getAddressInfo().getContactPerson());
                orderDlvDataVO.setIDCard(order.getAddressInfo().getPersonId());
                orderDlvDataVO.setTelNo(order.getAddressInfo().getContactPhone());
                orderDlvDataVO.setPostCode(order.getAddressInfo().getPostCode()); // 邮政编号
                orderDlvDataVO.setProvince(order.getAddressInfo().getProvince()); // 省编码
                orderDlvDataVO.setCity(order.getAddressInfo().getCity()); // 市编码
                orderDlvDataVO.setDistrict(order.getAddressInfo().getRegion()); // 区编码
                orderDlvDataVO.setDlvFlag(order.getAddressInfo().getDlvFlag());

                if (StringUtils.isNotBlank(order.getAddressInfo().getEmail())) {
                    orderDlvDataVO.setEmail(order.getAddressInfo().getEmail().replaceAll("/",";"));
                }

                orderDlvDataVO.setCarrierId(opsCommonService.getCarrierCodeByName(order.getAddressInfo().getCarrier()));
                if (orderDlvDataVO.getDlvAddress().contains("自提")) {
                    orderDlvDataVO.setCarrierId(CarrierEnum.ZT.getCode());
                }
                orderSalesDTO.setAddressInfo(orderDlvDataVO);
            }

            orderSalesDetail = new OrderSalesDetailVO();
            orderSalesDetail.setDlvType(order.getDlvtype()); // 出货方式
            orderSalesDetail.setRemark(order.getRemark());
            orderSalesDetail.setStatus(status); // 状态
            orderSalesDetail.setModelNo(order.getModelno()); // 型号
            orderSalesDetail.setQuantity(Optional.ofNullable(order.getQuantity()).orElse(0)); // 数量
            orderSalesDetail.setPrice(Optional.ofNullable(order.getPrice()).orElse(BigDecimal.ZERO)); // 单价
            orderSalesDetail.setDiscount(order.getDiscount()); // E率
            orderSalesDetail.setUserPrice(Optional.ofNullable(order.getUserPrice()).orElse(BigDecimal.ZERO)); // 用户价格
            orderSalesDetail.setUserEdiscount(order.getUserErate() != null ? order.getUserErate().doubleValue() : null); // 用户E率
            orderSalesDetail.setPriceEndUser(Optional.ofNullable(order.getPriceEnduser()).orElse(BigDecimal.ZERO)); // 最终价格
            orderSalesDetail.setDlvDate(order.getDlvdate()); // 希望交货日期
            orderSalesDetail.setCdlvDate(order.getDlvdate()); // 原交货日期
            orderSalesDetail.setRcvClassify(order.getRcvclassify()); // 订货分类
            orderSalesDetail.setStockCode(order.getStockcode()); // 出库代码
            orderSalesDetail.setCproductNo(order.getCproductno()); // 客户产品代码
            orderSalesDetail.setOrdTransType(order.getOrdtranstype()); // 海外运输方式
            orderSalesDetail.setSpcPrice(order.getSpcprice()); // 特价号
            orderSalesDetail.setShikomiNo(order.getShikomi()); // shikomi号
            orderSalesDetail.setNoPrice(order.getNoprice()); // 产品无单价
            orderSalesDetail.setAccount(new BigDecimal(order.getQuantity().toString()).multiply(order.getPrice())); // 总金额
            orderSalesDetail.setSpecMark(order.getSpecmark()); // 阀板标识
            orderSalesDetail.setWarehouseSendDate(order.getWarehousesenddate()); // 物流发货日期
            orderSalesDetail.setCproductName(order.getCproductname()); // 客户产品名称
            orderSalesDetail.setSalesInfoNo(order.getPresaleorderno()); // 营业情报号
            orderSalesDetail.setOpponent(order.getOpponent());
            orderSalesDetail.setOrOrderNo(order.getHddno()); // 合同订单号
            orderSalesDetail.setPreSalesOrderNo(order.getNo()); // 门户发注号
            orderSalesDetail.setEPrice(Optional.ofNullable(order.getEprice()).orElse(BigDecimal.ZERO)); // E价
            orderSalesDetail.setRecNo(order.getRecno()); // 格式化后的明细项
            orderSalesDetail.setExpDlvType(this.getExpDlvType(order)); // 特殊发货标识
            if (StringUtils.isNotBlank(order.getBomNo())) { // ppl
                orderSalesDetail.setPplNo(order.getBomNo().trim().replaceAll("[\\u4e00-\\u9fa5]", ""));
                if (orderSalesDetail.getPplNo().length() > 30) {
                    orderSalesDetail.setPplNo(orderSalesDetail.getPplNo().substring(0, 30));
                }
            }
            if (StringUtils.isNotBlank(order.getPrjcode())) { // 项目号
                orderSalesDetail.setProjectNo(order.getPrjcode().trim());
                if (orderSalesDetail.getProjectNo().length() > 30) {
                    orderSalesDetail.setProjectNo(order.getPrjcode().substring(0, 30));
                }
            }
            orderSalesDetail.setCorderNo(order.getPurchaseno());//客户采购订单号
            // 最低售价
            orderSalesDetail.setHasLowestPrice(order.getHasLowestPrice());
            // 附件列表
            orderSalesDetail.setFileList(order.getFileList());
            // 行业代码
            orderSalesDetail.setIndustryId(order.getIndustryId());
            // 客户类型
            orderSalesDetail.setCustomerType(order.getCustomerType());
            // 附件类型
            orderSalesDetail.setFileType(order.getFileType());

            orderSalesDetail.setInqbApplyNo(order.getInqbApplyNo());

            orderSalesDetail.setSpecialModelFlag(order.isSpecialModelFlag());

            orderSalesDTO.getItems().add(orderSalesDetail);

        }
        log.info("接入门户订单 {} {}项", orderList.get(0).getHddno(), orderList.size());
        // ResultVo<List<AdapterOrderResult>> addResult = receiveOrderServiceFeignApi.addOrderSalesFromSMS(orderSalesDTO);
        ResultVo<List<AdapterOrderResult>> addResult = rcvOrderService.addOrderSalesFromSMS(orderSalesDTO);
        log.info("接入门户订单 {} {}项 开始时间-{} 耗时{}(s) 结果---{}", orderList.get(0).getHddno(), orderList.size(),
                DateUtil.dateToDateTimeString(startTime), DateUtil.getDiffSecond(startTime, new Date()), addResult);

        if (!addResult.isSuccess()) {
            throw new BusinessException("门户订单接入失败: " + addResult.getMessage());
        }
        return addResult.getData();
    }

    @Override
    public AdapterReturn handleOrderEditMQ(OrderDeliveryModel orderDelivery) {
        AdapterReturn result = new AdapterReturn();
        result.setNo(orderDelivery.getNo());
        result.setModiModel(orderDelivery);

        List<String> orderNoList = orderDelivery.getOrderNo();
        OpsOrderModifyDto opsOrderModifyDto;
        CommonResult commonResult;

        for (String orderNo : orderNoList) {
            // 修改的是订单主要信息
            opsOrderModifyDto = new OpsOrderModifyDto();
            opsOrderModifyDto.setOrderId(orderNo);
            opsOrderModifyDto.setMaster(Boolean.TRUE);
            opsOrderModifyDto.setDlvType(orderDelivery.getIntensiveNo()); // 集约方式
            opsOrderModifyDto.setDlvEntire(orderDelivery.getDeliveryEntireNo()); // 发货方式

            // 是否修改货期
            opsOrderModifyDto.setUpdateDate(false);
            // 是否修改收货地址信息
            opsOrderModifyDto.setUpdateAddress(false);

            // 操作人
            UserDto userDto = new UserDto();
            userDto.setUserName(orderDelivery.getLoginUserId());
            opsOrderModifyDto.setUserDto(userDto);
            opsOrderModifyDto.setReason("门户申请变更");

            log.info("订单主单修改 参数 = {}", JSON.toJSONString(opsOrderModifyDto));
            commonResult = opsOrderFeignApi.modifyRcvOrder(opsOrderModifyDto);
            log.info("订单主单修改 结果 = {}", JSON.toJSONString(commonResult));
            if (!commonResult.isSuccess()) {
                result.setERPno(orderNo);
                result.setResult(false);
            } else {
                result.setResult(true);
                result.setERPno(orderNo);
            }
            Map<String, String> resultMap = (Map<String, String>) commonResult.getData();
            for (String key : resultMap.keySet()) {
                if (!resultMap.get(key).equals("成功")) {
                    resultMap.put(key, "失败");
                }
            }
            result.setMessage(JSONObject.toJSONString(commonResult.getData()));
            redisManager.set("sale:rabbitmq:message:order:" + orderNo, JSONObject.toJSONString(resultMap), 3600);
        }
        return result;
    }

    @Override
    public List<AdapterReturn> handleOrderDlvDateModifyMQ(List<OrderDelivery> orderDeliveryDate) {
        List<AdapterReturn> result = new ArrayList<>();
        AdapterReturn temp;
        OpsOrderModifyDto opsOrderModifyDto;
        CommonResult commonResult;
        Orderdlvdata orderdlvdata = new Orderdlvdata();

        for (OrderDelivery item : orderDeliveryDate) {
            temp = new AdapterReturn();
            temp.setERPno(item.getOrderNo());
            temp.setModiDate(item);
            temp.setNo(item.getNo());

            OrderNoInfo orderNoInfo = new OrderNoInfo().convertFullOrderNo(item.getOrderNo());
            opsOrderModifyDto = new OpsOrderModifyDto();
            opsOrderModifyDto.setOrderId(orderNoInfo.getOrderNo());
            opsOrderModifyDto.setOrderItem(orderNoInfo.getItemNo());

            /**
             * 批量变更
             */
            if (StringUtils.isBlank(item.getAssemble()) && StringUtils.isBlank(item.getOptCarrier())
                    && StringUtils.isBlank(item.getSpecialNormal()) && item.getDeliveryAddressInfo() == null
                    && StringUtils.isBlank(item.getDeptNo())) {
                opsOrderModifyDto.setMaster(false);
                if (item.getCustomerChangeDate() != null || item.getWarehouseSendDate() != null) {
                    // 是否修改货期
                    opsOrderModifyDto.setUpdateDate(true);
                    // 设置客户货期
                    if (item.getCustomerChangeDate() == null) {
                        opsOrderModifyDto.setDlvDate(item.getDlvDate());
                    } else {
                        opsOrderModifyDto.setDlvDate(item.getCustomerChangeDate());
                    }
                    // 设置物流货期
                    opsOrderModifyDto.setWmsDlvDate(item.getWarehouseSendDate());
                }
                opsOrderModifyDto.setUpdateAddress(false);
                UserDto userDto = new UserDto();
                userDto.setUserName(item.getLoginUserId());
                opsOrderModifyDto.setUserDto(userDto);
                opsOrderModifyDto.setReason("门户申请变更");
                log.info("订单子单修改(批量) 参数 = {}", JSONObject.toJSONString(opsOrderModifyDto));
                commonResult = opsOrderFeignApi.modifyRcvOrder(opsOrderModifyDto);

            } else {
                // 是否修改主项
                opsOrderModifyDto.setMaster(Boolean.FALSE);
                if (item.getCustomerChangeDate() != null || item.getWarehouseSendDate() != null) {
                    // 是否修改货期
                    opsOrderModifyDto.setUpdateDate(true);
                    // 设置客户货期
                    if (item.getCustomerChangeDate() == null) {
                        opsOrderModifyDto.setDlvDate(item.getDlvDate());
                    } else {
                        opsOrderModifyDto.setDlvDate(item.getCustomerChangeDate());
                    }
                    // 设置物流货期
                    opsOrderModifyDto.setWmsDlvDate(item.getWarehouseSendDate());
                }
                // 设置承运商
                opsOrderModifyDto.setCarrier(opsCommonService.getCarrierCodeByName(item.getOptCarrier()));
                // 是否特发
                if ("普通".equals(item.getSpecialNormal())) {
                    opsOrderModifyDto.setDlvSpecial(false);
                } else if ("特发".equals(item.getSpecialNormal())) {
                    opsOrderModifyDto.setDlvSpecial(true);
                }

                // 是否拆分子项发货
                if ("子项特发".equals(item.getAssemble())) {
                    opsOrderModifyDto.setDlvSplit(Boolean.TRUE);
                } else {
                    opsOrderModifyDto.setDlvSplit(Boolean.FALSE);
                }
                // 变更发货地址
                DeliveryAddressInfo addressInfo = item.getDeliveryAddressInfo();
                if (null == addressInfo) {
                    opsOrderModifyDto.setUpdateAddress(false);
                } else {
                    Class<? extends DeliveryAddressInfo> aClass = addressInfo.getClass();
                    Field[] fields = aClass.getDeclaredFields();
                    try {
                        for (Field field : fields) {
                            field.setAccessible(true);
                            if (null != field.get(addressInfo) && StringUtils.isBlank(field.get(addressInfo).toString())) {
                                field.set(addressInfo, null);
                            }
                        }
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }

                    // 是否修改地址
                    opsOrderModifyDto.setUpdateAddress(true);
                    orderdlvdata.setOrderno(orderNoInfo.getOrderNo());
                    orderdlvdata.setItemno(orderNoInfo.getItemNo());
                    // 收货地址
                    orderdlvdata.setDlvaddress(addressInfo.getDlvAddress());
                    // 联系人
                    orderdlvdata.setContactpsn(addressInfo.getContactPerson());
                    // 联系电话
                    orderdlvdata.setTelno(addressInfo.getContactPhone());
                    // 省.市.区
                    orderdlvdata.setProvince(addressInfo.getProvince());
                    orderdlvdata.setCity(addressInfo.getCity());
                    orderdlvdata.setDistrict(addressInfo.getRegion());
                    // 身份证号
                    orderdlvdata.setIdcard(addressInfo.getPersonId());
                    // 客户名称
                    orderdlvdata.setCstmname(addressInfo.getCustomerName());
                    orderdlvdata.setPostcode(addressInfo.getPostCode());
                    // 发货方式  发货方式-直发客户/直发营业所（1：直发客户；2：直发营业所;3:自提)
                    orderdlvdata.setDlvflag(addressInfo.getDlvFlag());
                    opsOrderModifyDto.setAddress(orderdlvdata);
                }
                // 操作人
                UserDto userDto = new UserDto();
                userDto.setUserName(item.getLoginUserId());
                opsOrderModifyDto.setUserDto(userDto);
                // 变更原因
                opsOrderModifyDto.setReason("门户申请变更");
                log.info("订单子单修改 参数 = {}", JSONObject.toJSONString(opsOrderModifyDto));
                commonResult = opsOrderFeignApi.modifyRcvOrder(opsOrderModifyDto);
            }

            log.info("订单子单修改 结果 = {}", JSON.toJSONString(commonResult));
            if (commonResult.isSuccess()) {
                temp.setResult(true);
            } else {
                temp.setResult(false);
            }
            Map<String, String> resultMap = (Map<String, String>) commonResult.getData();
            for (String key : resultMap.keySet()) {
                if (!resultMap.get(key).equals("成功")) {
                    resultMap.put(key, "失败");
                }
            }
            temp.setMessage(JSONObject.toJSONString(commonResult.getData()));
            redisManager.set("sale:rabbitmq:message:order:" + item.getOrderNo(), JSONObject.toJSONString(resultMap), 3600);
            result.add(temp);
        }
        return result;
    }


//    @Override
//    public List<AdapterReturn> handleOrderDlvDateModifyMQ(List<OrderDelivery> orderDeliveryDate) {
//        List<AdapterReturn> result = new ArrayList<>();
//        AdapterReturn temp;
//        String delimiter = "-";
//        String[] orderId;
//        UpdateForOrderDto updateForOrderDto;
//        CommonResult commonResult;
//
//        for (OrderDelivery item : orderDeliveryDate) {
//            temp = new AdapterReturn();
//            temp.setERPno(item.getOrderNo());
//            temp.setModiDate(item);
//            temp.setNo(item.getNo());
//
//            if (item.getOrderNo().contains(delimiter)) {
//                orderId = item.getOrderNo().split(delimiter);
//            } else {
//                temp.setResult(false);
//                temp.setMessage(item.getOrderNo() + "修改失败: 订单号格式不正确");
//                result.add(temp);
//                continue;
//            }
//
//            updateForOrderDto = new UpdateForOrderDto();
//            updateForOrderDto.setOrderId(orderId[0]);
//            updateForOrderDto.setOrderItem(Integer.parseInt(orderId[1]));
//            updateForOrderDto.setMaster(Boolean.FALSE);
//            // 客户货期
//            if (item.getCustomerChangeDate() == null) {
//                updateForOrderDto.setDlvDate(item.getDlvDate());
//            } else {
//                updateForOrderDto.setDlvDate(item.getCustomerChangeDate());
//            }
//            // 物流货期
//            updateForOrderDto.setWmsDlvDate(item.getWarehouseSendDate());
//            updateForOrderDto.setCarried(convertCarriterFromName(item.getOptCarrier()));
//
//            if ("普通".equals(item.getSpecialNormal())) {
//                // 时效标记 true 特发  false  普通
//                updateForOrderDto.setExpDlvTypeFlag(false);
//                //1 特发订单 2 普通订单 3 自提
//                updateForOrderDto.setSpecialFlag(2);
//            } else {
//                updateForOrderDto.setExpDlvTypeFlag(true);
//                updateForOrderDto.setSpecialFlag(1);
//            }
//
//            if ("子项特发".equals(item.getAssemble())) {
//                updateForOrderDto.setAssModelChangeDo(Boolean.TRUE);
//            } else {
//                updateForOrderDto.setAssModelChangeDo(Boolean.FALSE);
//            }
//
//            if (item.getDeliveryAddressInfo() != null) {
//                DeliveryAddressInfo addressInfo = item.getDeliveryAddressInfo();
//                updateForOrderDto.setDlvAddress(addressInfo.getDlvAddress());
//                updateForOrderDto.setLinkman(addressInfo.getContactPerson());
//                updateForOrderDto.setMobile(addressInfo.getContactPhone());
//                updateForOrderDto.setPhone(addressInfo.getContactPhone());
//                updateForOrderDto.setCustomerName(addressInfo.getCustomerName());
//                // 发货方式
//                updateForOrderDto.setDlvEntire(addressInfo.getDlvFlag());
//
//                updateForOrderDto.setProvince(addressInfo.getProvince());
//                updateForOrderDto.setCity(addressInfo.getCity());
//                updateForOrderDto.setDistrict(addressInfo.getRegion());
//                updateForOrderDto.setPostcode(addressInfo.getPostCode());
//                if ("客户自提".equalsIgnoreCase(addressInfo.getDlvAddress())) {
//                    updateForOrderDto.setSpecialFlag(3);
//                }
//            }
//
//            // 操作人
//            UserDto userDto = new UserDto();
//            userDto.setUserName(item.getLoginUserId());
//            updateForOrderDto.setUserDto(userDto);
//            // 变更原因
//            updateForOrderDto.setRemark1("门户申请变更");
//            log.error("订单子单修改 参数 = {}", JSON.toJSONString(updateForOrderDto));
//            commonResult = opsOrderFengnApi.modifyRcvOrder(updateForOrderDto);
//            log.error("订单子单修改 结果 = {}", JSON.toJSONString(commonResult));
//            if (commonResult.isSuccess()) {
//                temp.setResult(true);
//                StringBuilder msg = new StringBuilder();
//                Map<String, String> resultMap = (Map<String, String>) commonResult.getData();
//                for (Map.Entry<String, String> map : resultMap.entrySet()) {
//                    if ("1".equals(map.getValue())) {
//                        msg.append(map.getKey()).append("修改成功;");
//                    } else {
//                        msg.append(map.getKey()).append("修改失败: ").append(map.getValue()).append(";");
//                    }
//                }
//                temp.setMessage(msg.toString());
//                redisManager.set("sale:rabbitmq:message:order:" + item.getOrderNo(), temp.getMessage(), 3600);
//            } else {
//                temp.setResult(false);
//                temp.setMessage(commonResult.getMessage());
//                redisManager.set("sale:rabbitmq:message:order:" + item.getOrderNo(), temp.getMessage(), 3600);
//            }
//            result.add(temp);
//        }
//
//        return result;
//    }

    private String convertCarriterFromName(String name) {

        if (StringUtils.isEmpty(name)) {
            return "NON";
        }
        if ("物流自选".contains(name)) {
            return "NON";
        }
        if ("顺丰特快".contains(name)) {
            return "SF";
        }
        return name;
    }

    /**
     * 查询接单发注单对应的订单号
     *
     * @param noLists 发注单
     * @return 订单信息
     */
    private List<OrderSalesDO> listAddOrderInfo(String hddno, List<List<String>> noLists) {
        List<OrderSalesDO> infoList = new ArrayList<>(noLists.size() * 200);
        LambdaQueryWrapper<OrderSalesDO> query;
        for (List<String> nos : noLists) {
            query = new LambdaQueryWrapper<>();
            query.select(OrderSalesDO::getFullOrderNo, OrderSalesDO::getPreSalesOrderNo);
            query.eq(OrderSalesDO::getOrOrderNo, hddno).in(OrderSalesDO::getPreSalesOrderNo, nos);
            infoList.addAll(orderSalesMapper.selectList(query));
        }
        return infoList;
    }

    public AdapterReturn handleOrderEditMQold(OrderDeliveryModel orderDelivery) {
        if (orderDelivery == null) {
            return null;
        }
        AdapterReturn result = new AdapterReturn();
        result.setNo(orderDelivery.getNo());
        List<String> orderNoList = new ArrayList<>();
        Map<String, Boolean> noMap = new HashMap<>();
        for (String i : orderDelivery.getOrderNo()) {
            i = i.substring(0, 7);
            if (!noMap.containsKey(i)) {
                orderNoList.add(i);
                noMap.put(i, true);
            }
        }
        int flag = 0;
        List<String> orderItemNo = new ArrayList<>();
        ResultVo<List<OrderItem>> itemListResult;
        for (String orderNo : orderNoList) {
            // 根据7位订单号获取子项
            itemListResult = receiveOrderServiceFeignApi.getOrderItemStatus(orderNo);
            if (!itemListResult.isSuccess()) {
                throw new BusinessException(itemListResult.getMessage());
            }
            if (itemListResult.getData() == null) {
                result.setERPno(orderNo);
                result.setResult(false);
                result.setMessage(orderNo + SpringContextUtil.getMessage(OrderExceptionCode.NO_ORDER_ITEM));
                result.setModiModel(orderDelivery);
                return result;
            }

            for (OrderItem item : itemListResult.getData()) {
                if (item.isCanChangeDeliveryModel()) {
                    flag = 1;
                }
                orderItemNo.add(item.getOrderNo());
            }
        }
        if (flag == 0) {
            result.setERPno(orderNoList.get(0));
            result.setResult(false);
            result.setMessage(SpringContextUtil.getMessage(OrderExceptionCode.HAS_ITEM_INVALID));
            result.setModiModel(orderDelivery);
            return result;
        }
        orderDelivery.setOrderNo(orderItemNo);
        // 变更订单信息.收货信息
        this.updateDeliveryModel(orderDelivery);
        result.setERPno(orderNoList.get(0));
        result.setResult(true);
        result.setModiModel(orderDelivery);

        return result;
    }

    public List<AdapterReturn> handleOrderDlvDateModifyMQold(List<OrderDelivery> orderDeliveryDate) {
        // 判断变更货期是否大于今天
        Date today = DateUtils.truncate(new Date(), Calendar.DATE);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        List<AdapterReturn> result = new ArrayList<>();
        AdapterReturn temp;
        String orderNo;
        ResultVo<List<OrderItem>> orderItem;
        OrderItem order;

        for (OrderDelivery item : orderDeliveryDate) {
            temp = new AdapterReturn();
            orderNo = item.getOrderNo();
            temp.setERPno(orderNo);
            temp.setModiDate(item);

            if (item.getWarehouseSendDate() == null) {
                temp.setResult(false);
                temp.setMessage(SpringContextUtil.getMessage(OrderExceptionCode.DELIVERY_CANT_EMPTY));
            } else if (!item.getWarehouseSendDate().after(today)) {
                temp.setResult(false);
                temp.setMessage(SpringContextUtil.getMessage(OrderExceptionCode.DATE_SHOULD_BEFORE_TODAY) + dateFormat.format(today));
            } else {
                if (orderNo.length() != 10) {
                    temp.setResult(false);
                    temp.setMessage(SpringContextUtil.getMessage(OrderExceptionCode.ORDERNO_SHOULD_BE_TEN));
                } else {
                    orderItem = receiveOrderServiceFeignApi.getOrderItemStatus(orderNo);
                    if (!orderItem.isSuccess()) {
                        throw new BusinessException(orderItem.getMessage());
                    }
                    if (orderItem.getData() == null) {
                        temp.setResult(false);
                        temp.setMessage(orderNo + SpringContextUtil.getMessage(OrderExceptionCode.ORDER_NOT_EXIST));
                    }
                    order = orderItem.getData().get(0);
                    if (!order.getOrderStatusCode().equals(String.valueOf(RcvOrderStatusEnum.CKED.getType())) ||
                            !order.getOrderStatusCode().equals(String.valueOf(RcvOrderStatusEnum.RETURN.getType()))) {
                        // 判断更新物流日期：是否为工作日，若不是则调整为工作日
                        Date dlvDate = commonServiceFeignApi.calDlvDate(DateUtil.dateToDateString(item.getWarehouseSendDate()), DateUtil.dateToDateString(today));
                        // 发货期校验
                        boolean valid = validDlvDate2(dlvDate, today);
                        if (valid) {
                            // 更新物流发货日期
                            String dlv = dateFormat.format(dlvDate);
                            String remark = "[" + dateFormat.format(today) + SpringContextUtil.getMessage(OrderExceptionCode.REMARK) + dlv + "]";
                            this.updateDeliveryDate(orderNo, dlvDate, remark, item.getDlvDate());
                            temp.setResult(true);
                            temp.setMessage(remark);

                        } else {
                            temp.setResult(false);
                            temp.setMessage(SpringContextUtil.getMessage(OrderExceptionCode.CHANGE_DELIVERYDATE_INVALID));
                        }

                    } else {
                        temp.setResult(false);
                        temp.setMessage(SpringContextUtil.getMessage(OrderExceptionCode.CANNOT_CHANGE_DLVDATE));
                    }
                }
            }
            result.add(temp);
        }
        return result;
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
     * 信息校验
     */
    private String validData(AdapterOrder order) {
        String result = "";
        // 型号的校验，需要用到GetProduct存储过程，查找产品信息 子单校验 缺少：型号，特价号,E率校验
        result = result + detailValid(order);
        return result;
    }

    /**
     * 信息校验
     */
    private String validData(AdapterOrder order, CustomerVO customer) {
        String result = "";
        // 客户信息校验
        // result = result + customerValid(order, customer);
        // 用户信息校验以及出货方式校验
//        if (StringUtils.isNotBlank(order.getUserno())) {
//            result = result + userValid(order, customer);
//        }
        // 判断用户是否是限制输出用户
        //result = result + isForbidden(order);
        // 型号的校验，需要用到GetProduct存储过程，查找产品信息 子单校验 缺少：型号，特价号,E率校验
        result = result + detailValid(order);
        // 特价校验
        // result = specValid(order);
        return result;
    }

    /**
     * 客户信息校验
     */
    private String customerValid(AdapterOrder ordersales, CustomerVO customer) {
        // 判断客户开票类型
        String invoiceType = customer.getInvoiceType();
        if (StringUtils.isBlank(invoiceType)) {
            return "客户开票类型（普通发票、增值税发票）不可为空！";
        } else if (!StringUtils.equals("0", invoiceType) && !StringUtils.equals("1", invoiceType)) {
            return "客户开票类型应为普通发票或增值税发票！";
        }
        /*// 判断客户是否为垃圾客户
        if (!StringUtils.equals(customer.getLaji(), ConstantsAdapter.LAJI)) {
            return SpringContextUtil.getMessage(OrderExceptionCode.INVALID_CUSTOMER);
        }
        // 判断客户类型
        if (StringUtils.equals(customer.getCstmType(), ConstantsAdapter.Agent)) {
            if (ordersales.getUserno().length() != ConstantsAdapter.LENGTH) {
                return SpringContextUtil.getMessage(OrderExceptionCode.END_USER_NEED);
            }
            // 判断代理店客户的发货方式，是否为随到随发
            if (!StringUtils.equals(ordersales.getDlventire(), ConstantsAdapter.DLVENTIRE_ANY)) {
                return SpringContextUtil.getMessage(OrderExceptionCode.EXPFLAG_ERROR);
            }
        } else if (StringUtils.equals(customer.getCstmType(), ConstantsAdapter.Direct)) {
            // 判断是否为最终客户
            if (StringUtils.isNotBlank(customer.getAgentNo())
                    && !customer.getAgentNo().equalsIgnoreCase(ordersales.getCustomerno())) {
                return SpringContextUtil.getMessage(OrderExceptionCode.END_USER_CANOOT_QUOTE);
            }
        }*/
        // 客户开票信息校验
        if (StringUtils.isBlank(customer.getBank())) {
            return "客户开户行不能为空!";
        } else {
            if (customer.getBank().contains("*")) {
                return "客户开户行不能包含*号！";
            }
        }
        if (StringUtils.isBlank(customer.getTaxNo())) {
            return "客户税号不能为空!";
        } else {
            if (customer.getTaxNo().matches("\\s+")) {
                return "税号不能包含有任何空格等空字符";
            }
            if (!customer.getTaxNo().matches("^[a-z0-9A-Z]{15}$|^[a-z0-9A-Z]{17}$|^[a-z0-9A-Z]{18}$|^[a-z0-9A-Z]{20}$")) {
                return "税号只能为15/17/18/20位的数字或字母!";
            }
        }
        if (StringUtils.isBlank(customer.getAccountNo())) {
            return "客户账号不能为空!";
        } else {
            if (customer.getAccountNo().contains("*")) {
                return "客户账号不能包含*号！";
            }
        }
        if (StringUtils.isBlank(customer.getInvoiceAddress())) {
            return "客户开票地址不能为空!";
        } else {
            if (customer.getInvoiceAddress().contains("*")) {
                return "客户开票地址不能包含*号！";
            }
        }
        return "";
    }

    /**
     * 用户信息校验
     */
    private String userValid(AdapterOrder ordersales, CustomerVO customer) {
        ResultVo<CustomerVO> userInfo = commonServiceFeignApi.findCustomerByCustomerNo(ordersales.getUserno());
        if (!userInfo.isSuccess()) {
            log.error("获取接单用户信息失败: {}", userInfo);
            return "获取用户信息失败: " + userInfo.getMessage();
        }
        if (userInfo.getData() == null || !userInfo.getData().getAgentNo().equals(customer.getCustomerNo())) {
            return SpringContextUtil.getMessage(OrderExceptionCode.USERNO_ERROR_BLONG);
        }
        return "";
    }

    /**
     * 数量，单价校验
     */
    private String detailValid(AdapterOrder orderSales) {
        if (orderSales.getQuantity() == null || orderSales.getQuantity() < 1) {
            return "数量必须大于零";
        }
        if (orderSales.getPrice() == null) {
            return "单价不能为空";
        }
        if (orderSales.getPrice().compareTo(BigDecimal.ZERO) < 1) {
            return "单价不能为零";
        }
        if (orderSales.getDiscount() == null) {
            return "E率格式错误";
        }
        if (orderSales.getWorkday() == null) {
            return "订货日期不能为空";
        }
        // 交货期校验，必须大于今日，且小于最晚交货期
        if (!validDlvDate(orderSales.getDlvdate())) {
            return "预交货期必须大于当日或者早于最晚交货期";
        }
        // 物流发货日期校验，必须大于今日，且小于最晚交货期
        if (!validDlvDate(orderSales.getWarehousesenddate())) {
            return "物流发货日期必须大于当日或者早于最晚交货期";
        }
        // 有合同时候必须输入最终客户成交价,
//		if (orderSales.getPriceEnduser() == null || orderSales.getPriceEnduser().equals(BigDecimal.ZERO)) {
//			return "最终客户成交价格必须大于零";
//		}
        // 订单区分,竞争对手校验
//		if (StringUtils.isBlank(orderSales.getRcvclassify())) {
//			return "订单区分不能为空";
//		}
//		if (orderSales.getRcvclassify().equals("1") && StringUtils.isNotBlank(orderSales.getOpponent())) {
//			return "订单区分为正常订单,请勿选择竞争对手";
//		}
        return "";
    }

    /**
     * @param dlvdate 交货日期，发货日校验
     */
    private boolean validDlvDate(Date dlvdate) {
        Date today = DateUtils.truncate(new Date(), Calendar.DATE);
        // 判断是否在最晚发货日之前，最晚发货日为六个月后
        // Date last = DateUtils.addMonths(today, 6);
        // 物流发货时间不能小于当天
        // if (dlvdate.before(today) || dlvdate.after(last)) {
        if (dlvdate.before(today)) {
            return false;
        }
        return true;
    }

    /**
     * 对变更的货期进行有效性校验
     */
    private Boolean validDlvDate2(Date deliveryDate, Date today) {
        // 判断若修改发货期为当天，需在每天两点前变更
        if (deliveryDate.equals(today)) {
            LocalTime localTime = LocalTime.now();
            LocalTime dealTime = LocalTime.of(14, 0, 0);
            if (localTime.isAfter(dealTime)) {
                return false;
            }
        }
        // 判断是否在最晚发货日之前，最晚发货日为六个月后
        Date last = DateUtils.addMonths(today, 6);
        if (deliveryDate.after(last)) {
            return false;
        }
        return true;
    }


    /**
     * 获取交易主体ID
     *
     * @param tradeCompanyId 交易主体名称
     * @return tradeCompanyId
     */
    private String getTradeCompanyId(String tradeCompanyId) {
        if ("SMC自动化有限公司北京分公司".equals(tradeCompanyId)) {
            return "CN0-B";
        }
        if ("SMC自动化有限公司广州分公司".equals(tradeCompanyId)) {
            return "CN0-G";
        }
        if ("SMC自动化有限公司上海分公司".equals(tradeCompanyId)) {
            return "CN0-S";
        }
        if ("SMC自动化有限公司".equals(tradeCompanyId)) {
            return "CN0";
        }
        if ("SMC（中国）有限公司".equals(tradeCompanyId)) {
            return "CNC";
        }
        if ("SMC（中国）有限公司上海分公司".equals(tradeCompanyId)) {
            return "CNS";
        }
        if ("SMC自动化有限公司行业开发部".equals(tradeCompanyId)) {
            return "CN0";
        }
        return null;
    }

    /**
     * 特殊发货标识
     */
    private Integer getExpDlvType(AdapterOrder adapterOrder) {
        Integer expDlvType = null;
        if (adapterOrder.getUseWarehouseType() == 2) {
            expDlvType = OrderSpecExpType.NotOrderToCSStock.code(); // 不出委托在库
        }

        if ("后补订单".equals(adapterOrder.getTypecode())) {
            expDlvType = expDlvType == null ? OrderSpecExpType.MustOrderToCSStock.code()
                    : OrderSpecExpType.add(expDlvType, OrderSpecExpType.MustOrderToCSStock); // 必须出委托在库
        }
        if (Optional.ofNullable(adapterOrder.getRohs10()).orElse(Boolean.FALSE)) {
            expDlvType = expDlvType == null ? OrderSpecExpType.ROSH10Product.code()
                    : OrderSpecExpType.add(expDlvType, OrderSpecExpType.ROSH10Product); // Rohs10
        }
        if (Optional.ofNullable(adapterOrder.getAssemblyInstruction()).orElse(Boolean.FALSE)) {
            expDlvType = expDlvType == null ? OrderSpecExpType.WholeOrderToAssemble.code()
                    : OrderSpecExpType.add(expDlvType, OrderSpecExpType.WholeOrderToAssemble); // 组装指令
        }
        if (adapterOrder.getHasLowestPrice() != null && adapterOrder.getHasLowestPrice()) {
            expDlvType = expDlvType == null ? OrderSpecExpType.MINPRICE.code()
                    : OrderSpecExpType.add(expDlvType, OrderSpecExpType.MINPRICE); // 最低售价
        }
        if (adapterOrder.getAppointAirTrans() != null && adapterOrder.getAppointAirTrans()) {
            expDlvType = expDlvType == null ? OrderSpecExpType.AIRTRANS.code()
                    : OrderSpecExpType.add(expDlvType, OrderSpecExpType.AIRTRANS); // 指定空运
        }
        boolean specialModel = opsCommonService.isSpecialModel(adapterOrder.getModelno());
        if (specialModel) {
            expDlvType = expDlvType == null ? OrderSpecExpType.SPECIAL_WAREHOUSE.code()
                    : OrderSpecExpType.add(expDlvType, OrderSpecExpType.SPECIAL_WAREHOUSE); // 特殊仓库
        }
        return expDlvType;
    }

    /**
     * 设置客户群号
     *
     * @param orderSalesDTO        订单客户信息
     * @param customerClusterBinds 客户群号信息
     * @return 客户群号
     */
    private String getGroupCustomerNo(OrderSalesDTO orderSalesDTO, List<SalesCustomerClusterBind> customerClusterBinds) {
        if (CollectionUtils.isEmpty(customerClusterBinds)) {
            return null;
        }

        StringBuilder groupCustomerNo = new StringBuilder();
        for (SalesCustomerClusterBind item : customerClusterBinds) {
            if (orderSalesDTO.getEndUser().equals(item.getCustomerNo())) {
                groupCustomerNo.append(item.getCustomerBaseNo()).append(",");
            }
        }
        if (StringUtils.isBlank(groupCustomerNo)) {
            return null;
        }

//        Map<String, String> groupCustomerMap = new HashMap<>(customerClusterBinds.size());
//        for (SalesCustomerClusterBind bind : customerClusterBinds) {
//            groupCustomerMap.put(bind.getCustomerNo(), bind.getCustomerBaseNo());
//        }
//
//        StringBuilder groupCustomerNo = new StringBuilder();
//        if (groupCustomerMap.containsKey(orderSalesDTO.getEndUser())) {
//            groupCustomerNo.append(groupCustomerMap.get(orderSalesDTO.getEndUser()));
//        }
//        if (!orderSalesDTO.getEndUser().equals(orderSalesDTO.getUserNo()) && groupCustomerMap.containsKey(orderSalesDTO.getUserNo())) {
//            if (groupCustomerNo.length() > 0) {
//                groupCustomerNo.append(",");
//            }
//            groupCustomerNo.append(groupCustomerMap.get(orderSalesDTO.getUserNo()));
//        }
//        if (!orderSalesDTO.getEndUser().equals(orderSalesDTO.getCustomerNo()) && groupCustomerMap.containsKey(orderSalesDTO.getCustomerNo())) {
//            if (groupCustomerNo.length() > 0) {
//                groupCustomerNo.append(",");
//            }
//            groupCustomerNo.append(groupCustomerMap.get(orderSalesDTO.getCustomerNo()));
//        }
        return groupCustomerNo.toString().substring(0, groupCustomerNo.length() - 1);
    }

    /**
     * 变更发货方式：数据库操作
     */
    private void updateDeliveryModel(OrderDeliveryModel orderDelivery) {
        OrderDeliveryModifyInfo info = new OrderDeliveryModifyInfo();
        List<String> orderNo = orderDelivery.getOrderNo();
        List<String> list = new ArrayList<>();
        orderNo.forEach(item -> {
            String no = item.substring(0, 7);
            if (!list.contains(no)) {
                list.add(no);
            }
        });
        info.setOrderNoList(list);

        // 更新订单信息(交货地点.出货方式.承运商.费用承担方.集约方式.操作担当.操作时间)
        RcvMasterVO rcvMasterVO = new RcvMasterVO();
        rcvMasterVO.setDlvSite(orderDelivery.getDeliveryModeNo());
        rcvMasterVO.setDlvEntire(orderDelivery.getDeliveryEntireNo());
        rcvMasterVO.setTransFee(orderDelivery.getFeeNo());
        // rcvMasterVO.setTransChannel(orderDelivery.getCarrierNo());
        rcvMasterVO.setDlvType(orderDelivery.getIntensiveNo());
        rcvMasterVO.setOperator(orderDelivery.getLoginUserId());
        rcvMasterVO.setOptTime(new Date());
        info.setRcvMasterVO(rcvMasterVO);
        // 更新发货地址
        OrderDlvDataVO orderDlvDataVO = new OrderDlvDataVO();
        orderDlvDataVO.setCstmName(orderDelivery.getAddressInfo().getCustomerName());
        orderDlvDataVO.setDlvAddress(orderDelivery.getAddressInfo().getDlvAddress());
        orderDlvDataVO.setContactPsn(orderDelivery.getAddressInfo().getContactPerson());
        orderDlvDataVO.setTelNo(orderDelivery.getAddressInfo().getContactPhone());
        orderDlvDataVO.setPostCode(orderDelivery.getAddressInfo().getPostCode());
        orderDlvDataVO.setDlvFlag(orderDelivery.getAddressInfo().getDlvFlag());
        // orderDlvDataVO.setDlvType(orderDelivery.getAddressInfo().getIntensiveNo());
        info.setOrderDlvDataVO(orderDlvDataVO);
        ResultVo<String> resultVo = receiveOrderServiceFeignApi.updateOrderDeliveryInfo(info);
        if (!resultVo.isSuccess()) {
            throw new BusinessException("修改订单发货信息处理失败: " + resultVo.getMessage());
        }
    }

    /**
     * 变更订单货期：数据库操作
     */
    private void updateDeliveryDate(String orderNo, Date deliveryDate, String remark, Date dlvDate) {
        RcvDetailVO rcvDetailVO = new RcvDetailVO();
        rcvDetailVO.setRorderNo(orderNo);
        rcvDetailVO.setDlvDate(deliveryDate);
        rcvDetailVO.setRemark(remark);
        if (dlvDate != null) {
            rcvDetailVO.setCdlvDate(dlvDate);
        }
        ResultVo<Integer> resultVo = receiveOrderServiceFeignApi.updateOrderDetail(rcvDetailVO);
        if (!resultVo.isSuccess()) {
            throw new BusinessException("修改订单货期处理失败: " + resultVo.getMessage());
        }
    }

    @Override
    public List<OrderModifyVO> listOrderModify() {
        return orderSalesMapper.listOrderModify();
    }

}
