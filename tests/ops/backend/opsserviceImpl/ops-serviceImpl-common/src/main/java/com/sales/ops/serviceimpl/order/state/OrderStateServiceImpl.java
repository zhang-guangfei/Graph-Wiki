package com.sales.ops.serviceimpl.order.state;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.sales.ops.common.opsexception.Exceptions;
import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.common.rabbitmq.RabbitMqMessage;
import com.sales.ops.db.dao.OrderStateLogMapper;
import com.sales.ops.db.dao.RcvdetailMapper;
import com.sales.ops.db.entity.*;
import com.sales.ops.dto.order.CancelForOrderDto;
import com.sales.ops.dto.util.CommonResult;
import com.sales.ops.enums.RcvOrderStatusEnum;
import com.sales.ops.enums.RequestPurchaseStatusEnum;
import com.sales.ops.rabbitmq.SendMessage;
import com.sales.ops.service.order.state.OrderStateService;
import com.sales.ops.utils.PoNoUtil;
import com.smc.smccloud.core.model.constants.GlobalConstant;
import com.smc.smccloud.core.model.enums.OrderStateStatusEnum;
import com.smc.smccloud.core.model.enums.OrderTypeEnum;
import com.smc.smccloud.core.rabbitmq.constants.Constants;
import com.smc.smccloud.core.utils.SMCApp;
import com.smc.smccloud.core.utils.ThreadLocalMapUtil;
import com.smc.smccloud.model.orderstate.OrderStateEnum;
import com.smc.smccloud.model.orderstate.OrderStateVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author C12961
 * @date 2022/9/28 14:06
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class, propagation = Propagation.NOT_SUPPORTED)
public class OrderStateServiceImpl implements OrderStateService {

    @Resource
    private SendMessage sendMessage;

    @Resource
    private OrderStateLogMapper orderStateLogMapper;
    @Resource
    private RcvdetailMapper rcvdetailMapper;


    @Override
    public CommonResult<String> addOrderState(OrderStateVO orderStateVO) throws OpsException {
        if (null == orderStateVO) {
            return CommonResult.failure("保存订单状态失败");
        }
        // log.info("发送消息：\n" + JSONUtil.toJsonPrettyStr(orderStateVO));
        RabbitMqMessage rabbitMqMessage = new RabbitMqMessage();
        rabbitMqMessage.setContent(JSON.toJSONString(orderStateVO));
        rabbitMqMessage.setFlag(Constants.OPS_ORDER_STATE);
        rabbitMqMessage.setDataType(Constants.OPS_ORDER);
        rabbitMqMessage.setSystem(Constants.OPS);
        boolean sendResult = sendMessage.sendOrderStateMsg(rabbitMqMessage);
        if (sendResult) {
            return CommonResult.success("发送成功");
        }
        return CommonResult.failure("发送失败");
    }

    @Override
    public String insertOrderStateLog(OrderStateVO vo) {
        try {
            OrderStateLog stateLog = new OrderStateLog();
            stateLog.setOrderNo(vo.getOrderNo());
            stateLog.setOrderId(vo.getRorderNo());
            stateLog.setOrderItem(vo.getItemNo());
            stateLog.setSplitNo(vo.getSplitNo());
            stateLog.setCustomerNo(vo.getCustomerNo());
            stateLog.setUserNo(vo.getUserNo());
            stateLog.setOrderType(vo.getLogDataProvider());
            stateLog.setOrderDate(vo.getOrderDate());
            stateLog.setDescription(vo.getStateDes());
            stateLog.setCreateTime(new Date());
            stateLog.setCreateUser(vo.getOptUserNo());
            stateLog.setData(JSONUtil.toJsonStr(vo));
            orderStateLogMapper.insertSelective(stateLog);
            log.info("写入消息日志表成功:" + JSONUtil.toJsonPrettyStr(stateLog));
            return null;
        } catch (Exception e) {
            log.info("写入消息日志表失败:{}", e);
            return e.getMessage();
        }
    }

    /**
     * @description 请购单取消时，发送消息给交货期查询
     * @author C12961
     * @date 2023/3/20 13:39
     */
    @Override
    public void sendOrderStateForCancelRequestPurchase(OpsRequestpurchase po) {
        OrderStateVO orderStateVO = new OrderStateVO();
        String fullPoNo = PoNoUtil.getFullRePoNo(po);
        log.info("请购删单发送消息：{}", fullPoNo);
        orderStateVO.setOrderNo(fullPoNo);
        orderStateVO.setRorderNo(po.getOrderno());
        orderStateVO.setItemNo(po.getItemno());
        orderStateVO.setSplitNo(PoNoUtil.getSplitNo(po));
        orderStateVO.setModelNo(po.getModelno());
        orderStateVO.setQuantity(po.getQuantity());
        orderStateVO.setCustDlvDate(po.getHopedeliverydate());
        orderStateVO.setProvider("po");
        orderStateVO.setStateDate(new Date());
        orderStateVO.setShikomiNo(po.getShikomianswerno());
        orderStateVO.setSupplierCode(po.getSupplierid());
        orderStateVO.setPurchaseType(po.getPurchasetype());
        // 订单类型
        orderStateVO.setPurchaseFlag(1);
        orderStateVO.setOrderType(Integer.parseInt(po.getOrdtype()));
        orderStateVO.setStateCode(OrderStateEnum.CanceledNotConfirm.getCode());
        orderStateVO.setStateDes(DateUtil.formatDate(new Date()) + "请购单已取消");
        orderStateVO.setUpdateTime(new Date());
        orderStateVO.setLogDataProvider("请购单删单：cancelPurchase");
        try {
            insertOrderStateLog(orderStateVO);
            SMCApp.setGrantType(GlobalConstant.GrantType.CLIANT_CREDENTIALS);
            log.info("请购删单发送消息：{}", JSONUtil.toJsonPrettyStr(orderStateVO));
            CommonResult<String> result = addOrderState(orderStateVO);
            if (!result.isSuccess()) {
                throw Exceptions.OpsException(result.getMessage(), orderStateVO);
            }
        } catch (Exception e) {
            log.info("请购删单发送消息失败：" + fullPoNo);
            log.error("请购删单发送消息失败: {},{}", fullPoNo, e);
        } finally {
            ThreadLocalMapUtil.remove(GlobalConstant.GrantType.CLIANT_CREDENTIALS);
        }
    }

    /**
     * @description 采购单取消时，发送消息给交货期查询
     * @author C12961
     * @date 2023/3/20 13:39
     */
    @Override
    public void sendOrderStateForCancelPurchase(OpsPurchaseorder po) {
        OrderStateVO orderStateVO = new OrderStateVO();
        String fullPoNo = PoNoUtil.getFullPoNo(po);
        log.info("采购删单发送消息：{}", fullPoNo);
        orderStateVO.setOrderNo(fullPoNo);
        orderStateVO.setRorderNo(po.getOrderno());
        orderStateVO.setItemNo(po.getItemno());
        orderStateVO.setSplitNo(PoNoUtil.getSplitNo(po));
        orderStateVO.setModelNo(po.getModelno());
        orderStateVO.setQuantity(po.getQuantity());
        orderStateVO.setCustDlvDate(po.getHopedeliverydate());
        orderStateVO.setProvider("po");
        orderStateVO.setStateDate(new Date());
        orderStateVO.setShikomiNo(po.getShikomianswerno());
        orderStateVO.setSupplierCode(po.getSupplierid());
        orderStateVO.setPurchaseType(po.getPurchasetype());
        // 订单类型
        orderStateVO.setPurchaseFlag(1);
        orderStateVO.setOrderType(Integer.parseInt(po.getOrdtype()));
        orderStateVO.setStateCode(OrderStateEnum.CanceledNotConfirm.getCode());
        orderStateVO.setStateDes(DateUtil.formatDate(new Date()) + "采购单已取消");
        orderStateVO.setUpdateTime(new Date());
        orderStateVO.setLogDataProvider("采购单删单：cancelPurchase");
        try {
            insertOrderStateLog(orderStateVO);
            SMCApp.setGrantType(GlobalConstant.GrantType.CLIANT_CREDENTIALS);
            log.info("采购删单发送消息：{}", JSONUtil.toJsonPrettyStr(orderStateVO));
            CommonResult<String> result = addOrderState(orderStateVO);
            if (!result.isSuccess()) {
                throw Exceptions.OpsException(result.getMessage(), orderStateVO);
            }
        } catch (Exception e) {
            log.info("采购删单发送消息失败：" + fullPoNo);
            log.error("采购删单发送消息失败: {},{}", fullPoNo, e);
        } finally {
            ThreadLocalMapUtil.remove(GlobalConstant.GrantType.CLIANT_CREDENTIALS);
        }
    }

    @Override
    public void sendOrderStateForCancelPurchaseByPoReset(OpsPurchaseorder po) {
        OrderStateVO orderStateVO = new OrderStateVO();
        String fullPoNo = PoNoUtil.getFullPoNo(po);
        log.info("采购删单发送消息：{}", fullPoNo);
        orderStateVO.setOrderNo(fullPoNo);
        orderStateVO.setRorderNo(po.getOrderno());
        orderStateVO.setItemNo(po.getItemno());
        orderStateVO.setSplitNo(PoNoUtil.getSplitNo(po));
        orderStateVO.setModelNo(po.getModelno());
        orderStateVO.setQuantity(po.getQuantity());
        orderStateVO.setCustDlvDate(po.getHopedeliverydate());
        orderStateVO.setProvider("po");
        orderStateVO.setStateDate(new Date());
        orderStateVO.setShikomiNo(po.getShikomianswerno());
        orderStateVO.setSupplierCode(po.getSupplierid());
        orderStateVO.setPurchaseType(po.getPurchasetype());
        // 订单类型
        orderStateVO.setPurchaseFlag(3);
        orderStateVO.setOrderType(Integer.parseInt(po.getOrdtype()));
        orderStateVO.setStateCode(OrderStateEnum.CanceledNotConfirm.getCode());
        orderStateVO.setStateDes(DateUtil.formatDate(new Date()) + "采购单已取消");
        orderStateVO.setUpdateTime(new Date());
        orderStateVO.setLogDataProvider("采购单删单：cancelPurchase");
        try {
            insertOrderStateLog(orderStateVO);
            SMCApp.setGrantType(GlobalConstant.GrantType.CLIANT_CREDENTIALS);
            log.info("采购删单发送消息：{}", JSONUtil.toJsonPrettyStr(orderStateVO));
            CommonResult<String> result = addOrderState(orderStateVO);
            if (!result.isSuccess()) {
                throw Exceptions.OpsException(result.getMessage(), orderStateVO);
            }
        } catch (Exception e) {
            log.info("采购删单发送消息失败：" + fullPoNo);
            log.error("采购删单发送消息失败: {},{}", fullPoNo, e);
        } finally {
            ThreadLocalMapUtil.remove(GlobalConstant.GrantType.CLIANT_CREDENTIALS);
        }
    }


    /**
     * @description 请购拦截时，发送消息给货期状态
     * @author C12961
     * @date 2022/12/12 10:30
     */
    @Override
    public void sendOrderStateForInterceptPurchase(OpsRequestpurchase repo) {
        // 完整订单号
        String fullPoNo = PoNoUtil.getFullRePoNo(repo);
        // 状态描述
        StringBuilder stateDes = new StringBuilder();
        stateDes.append(DateUtil.formatDate(new Date())).append(OrderStateEnum.PurchaseFault.getStateName()).append("，拦截原因：");
        if (RequestPurchaseStatusEnum.SHIKOMILANJIE.equals(repo.getStatecode())) {
            stateDes.append("SHIKOMI");
        }
        stateDes.append(repo.getInterceptmsg());
        // 供应商
        String supplier = repo.getSupplierid();
        if (StringUtils.isNotBlank(supplier) && supplier.length() > 2) {
            supplier = supplier.substring(0, 2);
        }
        log.info("请购拦截发送消息：" + fullPoNo);
        try {
            OrderStateVO orderStateVO = new OrderStateVO();
            // 订单号
            orderStateVO.setOrderNo(fullPoNo);
            orderStateVO.setRorderNo(repo.getOrderno()); // 订单号，不包括项号
            orderStateVO.setItemNo(repo.getItemno());
            orderStateVO.setSplitNo(PoNoUtil.getSplitNo(repo));
            orderStateVO.setCorderNo(repo.getCorderno());
            // 订单类型
            orderStateVO.setOrderType(Integer.parseInt(repo.getOrdtype()));
            orderStateVO.setModelNo(repo.getModelno());
            orderStateVO.setQuantity(repo.getQuantity());
            // 订单货期状态
            orderStateVO.setStateCode(Integer.valueOf(OrderStateStatusEnum.PO_INTERCEPT.getCode()));
            orderStateVO.setStateDes(stateDes.toString());
            // 日期
            orderStateVO.setStateDate(new Date());
            orderStateVO.setCreateTime(new Date());
            orderStateVO.setUpdateTime(new Date());
            // 指定工厂出荷日
            orderStateVO.setPoDlvDate(repo.getHopeexportdate());
            // 客户交货期
            orderStateVO.setCustDlvDate(repo.getHopedeliverydate());

            // 运输方式
            orderStateVO.setPoTransType(repo.getTranstype());
            // 供应商编码
            orderStateVO.setSupplierCode(supplier);
            orderStateVO.setUserNo(repo.getUserno()); // 用户编码
            orderStateVO.setCustomerNo(repo.getCustomerno()); // 客户编码
            orderStateVO.setDeptNo(repo.getDeptno()); // 营业所编码
            orderStateVO.setProvider("po");
            orderStateVO.setLogDataProvider("请购拦截回执：interceptForRequestPo");
            insertOrderStateLog(orderStateVO);
            SMCApp.setGrantType(GlobalConstant.GrantType.CLIANT_CREDENTIALS);
            log.info("请购拦截发送消息：{}", JSONUtil.toJsonPrettyStr(orderStateVO));
            CommonResult<String> result = addOrderState(orderStateVO);
            if (!result.isSuccess()) {
                throw Exceptions.OpsException(result.getMessage() + orderStateVO);
            }
        } catch (Exception e) {
            log.info("请购拦截发送消息失败：" + fullPoNo);
            log.error("请购拦截发送消息失败: {},{}", fullPoNo, e);
        } finally {
            ThreadLocalMapUtil.remove(GlobalConstant.GrantType.CLIANT_CREDENTIALS);
        }
    }

    /**
     * @description 请购单创建时，发送消息给货期状态
     * @author C12961
     */
    @Override
    public void sendOrderStateForCreateRequestPurchase(OpsRequestpurchase po) {
        OrderStateVO orderStateVO = new OrderStateVO();
        String fullPoNo = PoNoUtil.getFullRePoNo(po);
        try {
            // 订单号
            orderStateVO.setOrderNo(fullPoNo);
            orderStateVO.setRorderNo(po.getOrderno());
            orderStateVO.setItemNo(po.getItemno());
            orderStateVO.setSplitNo(PoNoUtil.getSplitNo(po));
            // 订单类型
            orderStateVO.setPurchaseFlag(1);
            orderStateVO.setOrderType(Integer.parseInt(po.getOrdtype()));
            orderStateVO.setModelNo(po.getModelno());
            orderStateVO.setQuantity(po.getQuantity());
            // 订单货期状态
            orderStateVO.setStateCode(OrderStateEnum.Purchareing.getCode());
            orderStateVO.setStateDes(DateUtil.formatDate(new Date()) + "创建请购单");
            orderStateVO.setStateDate(new Date());
            orderStateVO.setCreateTime(new Date());
            orderStateVO.setUpdateTime(new Date());
            // 客户代码
            orderStateVO.setCustomerNo(po.getCustomerno());
            orderStateVO.setUserNo(po.getUserno());
            if (StringUtils.isNotBlank(orderStateVO.getRorderNo()) && !orderStateVO.getRorderNo().startsWith("88")) {
                orderStateVO.setDeptNo(po.getDeptno());
            }
            orderStateVO.setTransType(po.getTranstype());
            orderStateVO.setPoTransType(po.getTranstype());
            orderStateVO.setSupplierCode(po.getSupplierid());
            orderStateVO.setPurchaseType(po.getPurchasetype());
            orderStateVO.setPoDlvDate(po.getHopeexportdate());
            orderStateVO.setPoDate(new Date());
            orderStateVO.setProvider("po");
            orderStateVO.setLogDataProvider("请购单创建：sendForPo");
            insertOrderStateLog(orderStateVO);
            SMCApp.setGrantType(GlobalConstant.GrantType.CLIANT_CREDENTIALS);
            log.info("请购单创建发送消息：{}", JSONUtil.toJsonPrettyStr(orderStateVO));
            CommonResult<String> result = addOrderState(orderStateVO);
            if (!result.isSuccess()) {
                throw Exceptions.OpsException(result.getMessage(), orderStateVO);
            }
        } catch (Exception e) {
            log.info("请购单创建发送消息失败：" + fullPoNo);
            log.error("请购单创建发送消息失败: {},{}", fullPoNo, e);
        } finally {
            ThreadLocalMapUtil.remove(GlobalConstant.GrantType.CLIANT_CREDENTIALS);
        }

    }

    /**
     * @description 采购发单时，发送消息给货期状态
     * @author C12961
     * @date 2022/12/12 10:30
     */
    @Override
    public void sendOrderStateForSendPurchase(OpsPurchaseorder po) {
        OrderStateVO orderStateVO = new OrderStateVO();
        String fullPoNo = PoNoUtil.getFullPoNo(po);
        try {
            // 订单号
            orderStateVO.setOrderNo(fullPoNo);
            orderStateVO.setRorderNo(po.getOrderno());
            orderStateVO.setItemNo(po.getItemno());
            orderStateVO.setSplitNo(PoNoUtil.getSplitNo(po));
            // 订单类型
            orderStateVO.setPurchaseFlag(1);
            orderStateVO.setOrderType(Integer.parseInt(po.getOrdtype()));
            orderStateVO.setModelNo(po.getModelno());
            orderStateVO.setQuantity(po.getQuantity());
            // 订单货期状态
            orderStateVO.setStateCode(OrderStateEnum.Purchareing.getCode());
            orderStateVO.setStateDes(DateUtil.formatDate(new Date()) + "发出采购单");
            orderStateVO.setStateDate(new Date());
            orderStateVO.setCreateTime(new Date());
            orderStateVO.setUpdateTime(new Date());
            // 客户代码
            orderStateVO.setCustomerNo(po.getCustomerno());
            orderStateVO.setUserNo(po.getUserno());
            if (StringUtils.isNotBlank(orderStateVO.getRorderNo()) && !orderStateVO.getRorderNo().startsWith("88")) {
                orderStateVO.setDeptNo(po.getDeptno());
            }
            orderStateVO.setTransType(po.getTranstype());
            orderStateVO.setPoTransType(po.getTranstype());
            orderStateVO.setSupplierCode(po.getSupplierid());
            orderStateVO.setPurchaseType(po.getPurchasetype());
            orderStateVO.setPoDlvDate(po.getHopeexportdate());
            orderStateVO.setPoDate(new Date());
            orderStateVO.setProvider("po");
            orderStateVO.setOrderDate(po.getPurchasedate());
            orderStateVO.setLogDataProvider("采购单发单：sendForPo");
            insertOrderStateLog(orderStateVO);
            SMCApp.setGrantType(GlobalConstant.GrantType.CLIANT_CREDENTIALS);
            log.info("采购单发单发送消息：{}", JSONUtil.toJsonPrettyStr(orderStateVO));
            CommonResult<String> result = addOrderState(orderStateVO);
            if (!result.isSuccess()) {
                throw Exceptions.OpsException(result.getMessage(), orderStateVO);
            }
        } catch (Exception e) {
            log.info("采购单发单发送消息失败：" + fullPoNo);
            log.error("采购单发单发送消息失败: {},{}", fullPoNo, e);
        } finally {
            ThreadLocalMapUtil.remove(GlobalConstant.GrantType.CLIANT_CREDENTIALS);
        }

    }

    /**
     * @description 请购单完成时，发送消息给交货期表
     * @author C12961
     * @date 2023/3/8 16:02
     */
    @Override
    public void sendOrderStateForFinishRequestPurchase(OpsRequestpurchase po) {
        OrderStateVO orderStateVO = new OrderStateVO();
        String fullRePoNo = PoNoUtil.getFullRePoNo(po);
        try {
            orderStateVO.setOrderNo(fullRePoNo);
            orderStateVO.setRorderNo(po.getOrderno());
            orderStateVO.setItemNo(po.getItemno());
            orderStateVO.setSplitNo(PoNoUtil.getSplitNo(po));
            orderStateVO.setStateCode(OrderStateEnum.InStock.code());// 状态42已入库
            orderStateVO.setStateDes(DateUtil.formatDate(new Date()) + "请购单已完成");// 时间+请购单已完成
            orderStateVO.setTransType(po.getTranstype());// 运输方式
            orderStateVO.setPoTransType(po.getTranstype());// 与上相同
            orderStateVO.setSupplierCode(po.getSupplierid());// 供应商
            orderStateVO.setPurchaseFlag(1);// 区别客户单和采购单的   1采购单 0客户单  (要求填写固定值1)
            insertOrderStateLog(orderStateVO);
            SMCApp.setGrantType(GlobalConstant.GrantType.CLIANT_CREDENTIALS);
            CommonResult<String> result = addOrderState(orderStateVO);
            if (!result.isSuccess()) {
                throw Exceptions.OpsException(result.getMessage(), orderStateVO);
            }
            log.info("请购单完成时发送消息：{}", JSONUtil.toJsonPrettyStr(orderStateVO));
        } catch (OpsException e) {
            log.info("请购完成发送消息失败：" + fullRePoNo);
            log.error("请购单完成发送消息失败: {},{}", fullRePoNo, e);
        } finally {
            ThreadLocalMapUtil.remove(GlobalConstant.GrantType.CLIANT_CREDENTIALS);
        }
    }

    /**
     * @description 采购单完成时，发送消息给交货期表
     * @author C12961
     * @date 2023/3/8 16:02
     */
    @Override
    public void sendOrderStateForFinishPurchase(OpsPurchaseorder po) {
        OrderStateVO orderStateVO = new OrderStateVO();
        String fullPoNo = PoNoUtil.getFullPoNo(po);
        try {
            orderStateVO.setOrderNo(fullPoNo);
            orderStateVO.setRorderNo(po.getOrderno());
            orderStateVO.setItemNo(po.getItemno());
            orderStateVO.setSplitNo(PoNoUtil.getSplitNo(po));
            orderStateVO.setStateCode(OrderStateEnum.InStock.code());// 状态42已入库
            orderStateVO.setStateDes(DateUtil.formatDate(new Date()) + "采购单已完成");// 时间+已采购完成
            orderStateVO.setTransType(po.getTranstype());// 运输方式
            orderStateVO.setPoTransType(po.getTranstype());// 与上相同
            orderStateVO.setSupplierCode(po.getSupplierid());// 供应商
            orderStateVO.setPurchaseFlag(1);// 区别客户单和采购单的   1采购单 0客户单  (要求填写固定值1)
            insertOrderStateLog(orderStateVO);
            SMCApp.setGrantType(GlobalConstant.GrantType.CLIANT_CREDENTIALS);
            CommonResult<String> result = addOrderState(orderStateVO);
            if (!result.isSuccess()) {
                throw Exceptions.OpsException(result.getMessage(), orderStateVO);
            }
            log.info("采购单完成时发送消息：{}", JSONUtil.toJsonPrettyStr(orderStateVO));
        } catch (OpsException e) {
            log.info("采购单完成发送消息失败：" + fullPoNo);
            log.error("采购单完成发送消息失败: {},{}", fullPoNo, e);
        } finally {
            ThreadLocalMapUtil.remove(GlobalConstant.GrantType.CLIANT_CREDENTIALS);
        }
    }

    /**
     * @description 更新rcvdetail状态时，发送消息给货期状态
     * @author C12961
     * @date 2022/12/12 10:30
     */
    @Override
    public void sendOrderStateForUpdateRcvStatus(Rcvdetail rcvdetail) {
        try {
            OrderStateVO orderState = new OrderStateVO();
            RcvOrderStatusEnum rcvOrderStatusEnum = RcvOrderStatusEnum.getEnumByType(rcvdetail.getStatus());
            if (rcvOrderStatusEnum == null) {
                throw Exceptions.OpsException("订单状态异常：" + rcvdetail.getStatus());
            }
            orderState.setOrderNo(rcvdetail.getRorderFno());
            orderState.setRorderNo(rcvdetail.getRorderNo());
            orderState.setItemNo(rcvdetail.getRorderItem());
            orderState.setSplitNo(0);
            Integer stateCode = OrderStateEnum.getFromRcvState(Integer.valueOf(rcvdetail.getStatus())).code();
            orderState.setStateCode(stateCode);
            orderState.setProvider("订单状态变更");
            orderState.setIntercept(rcvdetail.getIntercept());
            orderState.setOptUserName(rcvdetail.getUpdateUser());
            orderState.setQuantity(rcvdetail.getQuantity());
            orderState.setModelNo(rcvdetail.getModelNo());
            orderState.setStateDate(new Date());
            orderState.setCustDlvDate(rcvdetail.getDlvDate());
            orderState.setWmsDlvDate(rcvdetail.getWmsDlvDate());
            orderState.setUpdateTime(new Date());
            orderState.setStateDes(DateUtil.formatDate(new Date()) + "订单状态变更:" + rcvOrderStatusEnum.getDesc());
            orderState.setSupplierNo(rcvdetail.getStockCode());
            orderState.setRcvStatus((int) rcvdetail.getStatus());
            orderState.setLogDataProvider("变更订单状态:updateRcvDetail");
            try {
                log.info("开始发送消息");
                SMCApp.setGrantType(GlobalConstant.GrantType.CLIANT_CREDENTIALS);
                // todo 订单状态变更消息不记录日志，因为会影响事件执行效率
                // opsOrderStateService.insertOrderStateLog(orderState);
                CommonResult<String> resultVo = addOrderState(orderState);
                if (resultVo.isSuccess()) {
                    log.info("发送消息成功：{}", JSONUtil.toJsonPrettyStr(resultVo));
                } else {
                    log.info("发送消息失败：{}", JSONUtil.toJsonPrettyStr(resultVo));
                }
            } catch (Exception e) {
                log.info("发送消息失败：{}", e);
                e.printStackTrace();
            } finally {
                ThreadLocalMapUtil.remove(GlobalConstant.GrantType.CLIANT_CREDENTIALS);
            }

        } catch (Exception e) {
            log.error("发送order state errorMsg: {}", e);
        }
        log.info("【推送状态变更MQ结束】");
    }

    /**
     * @description 更新rcvdetail的dlvDate时，发送消息给货期状态
     * @author C12961
     * @date 2023/12/20
     */
    @Override
    public void sendOrderStateForUpdateRcvDlvDate(Rcvdetail rcvdetail,String updateUser) {
        try {
            OrderStateVO orderState = new OrderStateVO();
            RcvOrderStatusEnum rcvOrderStatusEnum = RcvOrderStatusEnum.getEnumByType(rcvdetail.getStatus());
            if (rcvOrderStatusEnum == null) {
                throw Exceptions.OpsException("订单状态异常：" + rcvdetail.getStatus());
            }
            orderState.setOrderNo(rcvdetail.getRorderFno());
            orderState.setRorderNo(rcvdetail.getRorderNo());
            orderState.setItemNo(rcvdetail.getRorderItem());
            orderState.setSplitNo(0);
            Integer stateCode = OrderStateEnum.getFromRcvState(Integer.valueOf(rcvdetail.getStatus())).code();
            orderState.setStateCode(stateCode);
            orderState.setProvider("订单修改交货期");
            orderState.setIntercept(rcvdetail.getIntercept());
            orderState.setOptUserName(rcvdetail.getUpdateUser());
            orderState.setStateDate(new Date());
            orderState.setCustDlvDate(rcvdetail.getDlvDate());
            orderState.setWmsDlvDate(rcvdetail.getWmsDlvDate());
            orderState.setUpdateTime(new Date());
            String remark = String.format("%s在%s修改订单交货期:%s", updateUser, DateUtil.formatDate(new Date()), DateUtil.formatDate(rcvdetail.getDlvDate()));
            orderState.setStateDes(remark);
            orderState.setSupplierNo(rcvdetail.getStockCode());
            orderState.setRcvStatus((int) rcvdetail.getStatus());
            orderState.setLogDataProvider("订单修改交货期:updateRcvDetail");
            try {
                log.info("开始发送消息");
                SMCApp.setGrantType(GlobalConstant.GrantType.CLIANT_CREDENTIALS);
                insertOrderStateLog(orderState);
                CommonResult<String> resultVo = addOrderState(orderState);
                if (resultVo.isSuccess()) {
                    log.info("发送消息成功：{}", JSONUtil.toJsonPrettyStr(resultVo));
                } else {
                    log.info("发送消息失败：{}", JSONUtil.toJsonPrettyStr(resultVo));
                }
            } catch (Exception e) {
                log.info("发送消息失败：{}", e);
                e.printStackTrace();
            } finally {
                ThreadLocalMapUtil.remove(GlobalConstant.GrantType.CLIANT_CREDENTIALS);
            }

        } catch (Exception e) {
            log.error("发送order state errorMsg: {}", e);
        }
        log.info("【推送状态变更MQ结束】");
    }


    /**
     * @description 取消客户订单时，发送消息给货期状态
     * @author C12961
     * @date 2022/12/12 10:30
     */
    @Override
    public void sendOrderStateForCancelCustomerOrder(CancelForOrderDto cancelDto, Rcvdetail rcvdetail, Rcvmaster rcvmaster) {
        // 如果门户自动删单，则操作人写System
        String origin = "门户提交自动删除";
        if (StringUtils.equals(cancelDto.getOrigin(), origin)) {
            cancelDto.getUserDto().setUserName("System");
        }
        StringBuilder desc = new StringBuilder();
        if (ObjectUtil.isNotNull(cancelDto.getUserDto())) {
            desc.append(cancelDto.getUserDto().getUserName());
        }
        if (StringUtils.isNotBlank(cancelDto.getOrderType()) && OrderTypeEnum.getCodeName(cancelDto.getOrderType()) != null) {
            desc.append("做" + OrderTypeEnum.getCodeName(cancelDto.getOrderType()));
        }
        desc.append("取消订单");
        if (StringUtils.isNotBlank(cancelDto.getDuty())) {
            desc.append(" 责任人：" + cancelDto.getDuty());
        }
        if (StringUtils.isNotBlank(cancelDto.getReason())) {
            desc.append(" 原因：" + cancelDto.getReason());
        }

        log.info("【开始向门户发送信息】");
        OrderStateVO stateVO = new OrderStateVO();
        // 订单号
        stateVO.setOrderNo(rcvdetail.getRorderFno());
        stateVO.setRorderNo(rcvdetail.getRorderNo());
        stateVO.setItemNo(rcvdetail.getRorderItem());
        stateVO.setSplitNo(0);
        stateVO.setCorderNo(rcvmaster.getOrorderno());
        // 订单类型
        stateVO.setPurchaseFlag(0);
        stateVO.setOrderType(Integer.valueOf(rcvdetail.getOrderType()));
        stateVO.setModelNo(rcvdetail.getModelNo());
        stateVO.setQuantity(rcvdetail.getQuantity());
        // 订单状态
        stateVO.setStateCode(Integer.parseInt(OrderStateStatusEnum.YSDDQR.getCode()));
        stateVO.setStateDes(desc.toString());
        stateVO.setStateDate(new Date());
        // 客户代码
        stateVO.setCustomerNo(rcvmaster.getCustomerNo());
        stateVO.setUserNo(rcvmaster.getUserNo());
        stateVO.setOrderPsnNo(rcvmaster.getEmployeeno()); // 下单人
        stateVO.setOptUserNo(cancelDto.getUserDto().getUserName());
        stateVO.setOptUserName(cancelDto.getUserDto().getUserName());
        stateVO.setLogDataProvider("取消客户订单:cancelRcvOrder");
        log.info("消息已生成：" + JSONUtil.toJsonStr(stateVO));
        try {
            insertOrderStateLog(stateVO);
            log.info("发送消息:" + stateVO.getOrderNo());
            addOrderState(stateVO);
        } catch (OpsException e) {
            log.info("发送消息失败：{}", e);
        }
        log.info("【向门户发送信息结束】");
    }


}
