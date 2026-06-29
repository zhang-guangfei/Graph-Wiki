package com.sales.ops.web.controller.wmOrder;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.sales.ops.aop.annotation.Log;
import com.sales.ops.common.conf.XXLJOBHandleIdProp;
import com.sales.ops.common.errorEnum.SalesInfoErrorCodeEnum;
import com.sales.ops.common.opsexception.Exceptions;
import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.common.until.DateUtil;
import com.sales.ops.db.dao.OpsOrderUpdateLogMapper;
import com.sales.ops.db.entity.*;
import com.sales.ops.db.extdao.NotifyShipmentPlanDao;
import com.sales.ops.dto.flux.HandConfirm;
import com.sales.ops.dto.flux.RoConfirm;
import com.sales.ops.dto.flux.RoSignConfirmDto;
import com.sales.ops.dto.inventory.*;
import com.sales.ops.dto.order.*;
import com.sales.ops.dto.replacement.NotifyShipmentCondition;
import com.sales.ops.dto.util.CommonResult;
import com.sales.ops.dto.util.ReturnXxlJobResult;
import com.sales.ops.dto.util.UserDto;
import com.sales.ops.enums.*;
import com.sales.ops.event.publisher.enums.EventSourceEnum;
import com.sales.ops.feign.OpsCallWmsFeignApi;
import com.sales.ops.feign.OpsWmFeignApi;
import com.sales.ops.feign.PurchaseFinishFeignApi;
import com.sales.ops.feign.XxlJobFeignApi;
import com.sales.ops.redisson.OpsRedissonLock;
import com.sales.ops.service.dispatch.PoDispatcherService;
import com.sales.ops.service.dispatch.rodispatch.RoDispatcherService;
import com.sales.ops.service.inventory.AdjustInventoryService;
import com.sales.ops.service.inventory.OpsInventoryService;
import com.sales.ops.service.inventory.WmDispatchService;
import com.sales.ops.service.inventory.WmRouterOrderService;
import com.sales.ops.service.log.OpsHandOverService;
import com.sales.ops.service.order.BaseOrderAssignResultService;
import com.sales.ops.service.order.CancelCustomerOrderService;
import com.sales.ops.service.order.CreateTransferPlanService;
import com.sales.ops.service.order.TransOrderService;
import com.sales.ops.service.order.state.OrderStateService;
import com.sales.ops.service.po.BasePoService;
import com.sales.ops.service.wmOrder.*;
import com.sales.ops.serviceimpl.event.v3.CustomerEventPublisher;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.model.DataTypeVO;
import com.smc.smccloud.model.orderlog.OrderLogVO;
import com.smc.smccloud.model.orderstate.OrderStateVO;
import com.smc.smccloud.service.DictDataServiceFeignApi;
import com.smc.smccloud.service.OrderLogFeignApi;
import com.smc.smccloud.service.OrderStateServiceFeignApi;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author C02483
 * @version 1.0
 * @description: TODO
 * @date 2021/10/25 11:35
 */
@CrossOrigin
@RestController
@RequestMapping("/order")
public class WmDispatchController {

    private static Logger log = LoggerFactory.getLogger(WmDispatchController.class);
    @Autowired
    private WmDispatchService wmDispatchService;
    @Autowired
    private OpsDoService opsDoService;
    @Autowired
    private BaseDoService baseDoService;
    @Autowired
    private OpsPcoService opsPcoService;
    @Autowired
    private WmRouterOrderService wmRouterOrderService;

    @Autowired
    private OrderStateServiceFeignApi orderStateServiceFeignApi;

    @Autowired
    private OrderLogFeignApi orderLogFeignApi;

    @Autowired
    private OpsHandOverService opsHandOverService;


    @Autowired
    private OpsRedissonLock opsRedissonLock;


    @Autowired
    private OpsCallWmsFeignApi opsCallWmsFeignApi;

    @Autowired
    private OpsWmFeignApi opsWmFeignApi;

    @Autowired
    private OpsInventoryService opsInventoryService;

    @Resource
    private OrderStateService orderStateService;

    @Autowired
    private PoDispatcherService poDispatcherService;

    @Autowired
    private OpsChangeOrderService opsChangeOrderService;


    @Autowired
    private BasePoService basePoServer;

    @Autowired
    private DictDataServiceFeignApi dictDataServiceFeignApi;
    @Autowired
    private OpsOrderUpdateLogMapper opsOrderUpdateLogMapper;
    @Autowired
    private RoDispatcherService roDispatcherService;
    @Autowired
    private WmDoService wmDoService;

    @Resource
    private XxlJobFeignApi xxlJobFeignApi;
    @Resource
    private XXLJOBHandleIdProp xxljobHandleIdProp;

    @Resource
    private PurchaseFinishFeignApi poFinFeignApi;
    @Autowired
    private TransOrderService transOrderService;
    @Autowired
    private CancelCustomerOrderService cancelCustomerOrderService;
    @Autowired
    private BaseOrderAssignResultService baseOrderAssignResultService;
    @Autowired
    private CustomerEventPublisher customerEventPublisher;

    @Autowired
    private CreateTransferPlanService createTransferPlanService;

    @Autowired
    private NotifyShipmentPlanDao notifyShipmentPlanDao;

    @Autowired
    private DoPcoLogicCenterService doPcoLogicCenterService;

    @Autowired
    private AdjustInventoryService adjustInventoryService;

    /**
     * bugid:17771 c14717 20250630
     * @return
     */
    @GetMapping("/finishPlanJob")
    public CommonResult<String> finishPlanJob(){
        try {
            adjustInventoryService.finishPlanJob();
            return CommonResult.success();
        } catch (Exception e) {
            log.error("调库计划完纳", e);
            return CommonResult.failure(e.getMessage());
        }
    }

    /**
     * bugid:11487 C14717 20230811 反算物流发货日接口修改
     *
     * @return
     */
    @GetMapping("/getOrderMaxWlday")
    public CommonResult<String> getOrderMaxWlday(@RequestParam(value = "orderId") String orderId,
                                                 @RequestParam(value = "orderItem") String orderItem,
                                                 @RequestParam(value = "dlvDateParam") Date dlvDateParam) {
        try {
            String maxWlday = opsDoService.getOrderMaxWlday(orderId, orderItem, dlvDateParam);
            if (StringUtils.isBlank(maxWlday)) {
                return CommonResult.failure("不存在的订单号");
            }
            return CommonResult.success(maxWlday);
        } catch (Exception e) {
            log.error("反算物流发货日接口修改", e);
            return CommonResult.failure(e.getMessage());
        }
    }

    /**
     * 接单处理 订单执行DIY时间	定时任务状态 状态；
     */
    @GetMapping("/xxlJobAutoOrderOneDayHandlerStatus")
    public ResultVo<String> xxlJobAutoOrderHandler() {
        try {
            ReturnXxlJobResult result = xxlJobFeignApi.getStatusById(xxljobHandleIdProp.getOnceid());
            if (result != null) {
                return ResultVo.success(result.getContent().toString());
            }
        } catch (Exception e) {
            return ResultVo.failure();
        }
        return ResultVo.failure();
    }

    /**
     * 接单处理 订单执行DIY时间	 定时任务状态 开关
     */
    @GetMapping("/xxlJobAutoOrderOneDayHandlerSwitch")
    public ResultVo<String> xxlJobAutoOrderHandlerSwitch(@RequestParam("flag") Boolean flag) {
        try {
            if (flag) {
                xxlJobFeignApi.startById(xxljobHandleIdProp.getOnceid());
            } else {
                xxlJobFeignApi.stopById(xxljobHandleIdProp.getOnceid());
            }
        } catch (Exception e) {
            return ResultVo.failure();
        }
        return ResultVo.success();
    }

    /**
     * 自动订单还原 bugid:14473 c14717 20240626
     *
     * @return 是否还原成功
     */
    @PostMapping("/autoOrderChangeToInitStatus")
    public ResultVo<String> autoOrderChangeToInitStatus(@RequestBody AutoOrderChangeToInitStatusDto param) {
        OrderChangeToInitStatusDto inputDto = new OrderChangeToInitStatusDto(param);
        // 1.物流指令取消
        boolean delDoflag = false;
        ResultVo<String> commonResult = null;        // 记录日志
        StringBuffer resultLog = new StringBuffer();
        resultLog.append("订单还原:");
        resultLog.append(inputDto.getUserName());
        Rcvdetail rcvdetail = null;
        boolean lock = false;
        try {
            log.info("订单还原：{}", JSONUtil.toJsonPrettyStr(inputDto));
            rcvdetail = wmRouterOrderService.getRcvdetail(inputDto.getOrderId(), inputDto.getOrderItem());
            if (Objects.nonNull(rcvdetail)) {
                lock = opsRedissonLock.addLock("ops:dispatch:" + rcvdetail.getRorderFno());
                // --------------- 风险值验证 start----------------------------
                int i = cancelCustomerOrderService.validatePurchaseInfoToAutoToInitStatus(rcvdetail, param, inputDto);
                // 采购高风险需要人工处理
                if (i == 2) {
                    if (lock) {
                        opsRedissonLock.releaseLock("ops:dispatch:" + rcvdetail.getRorderFno());
                    }
                    return ResultVo.failure("501", "自动还原失败，需要人工确认");
                    // 订单状态不可还原
                } else if (i == 3) {
                    if (lock) {
                        opsRedissonLock.releaseLock("ops:dispatch:" + rcvdetail.getRorderFno());
                    }
                    return ResultVo.failure("订单当前状态不允许取消订单");
                }
                // --------------- 风险值验证 end----------------------------
                // 物理指令取消；备注:存在多余调拨单，handConfirm会关联新的指令，存在bug，因此删单时需要同时取消调拨单；
                // 调拨单和交易出库单同时删除成功，才返回成功；
                delDoflag = opsChangeOrderService.orderChangeToInitStatusDelDo(inputDto.getOrderId(),
                        inputDto.getOrderItem(), inputDto.getUserName());
            } else {
                resultLog.append(";无rcvdetail;");
            }
            log.info("订单还原删单标识：{}", delDoflag);
        } catch (OpsException ex) {
            resultLog.append(";物流指令取消：").append(ex.getMessage());
            log.error("订单还原删单", ex);
        } catch (Exception ex) {
            log.error("订单还原删单", ex);
        }
        // 记录日志
        resultLog.append(";物流指令取消：").append(delDoflag);
        // 2.物流指令取消成功
        if (delDoflag) {
            // 3.删除采购单 返回是否成功不影响接单处理
            if (inputDto.isDelPo()) {
                resultLog.append(";采购取消：");
                boolean poFlag = false;
                try {
                    // 采购单取消
                    CommonResult<Boolean> poResult = opsChangeOrderService
                            .orderChangeToInitStatusDelPo(inputDto.getPo().get(0));
                    if (poResult.isSuccess()) {
                        poFlag = true;
                    }
                    log.info("订单还原取消采购单:{}", poResult.isSuccess());
                } catch (Exception e) {
                    log.error("订单还原取消采购单失败:", e);
                }
                resultLog.append(poFlag);
            }
            // 3.1清空状态
            try {
                baseOrderAssignResultService.deleteResultOrder(inputDto.getOrderId(), Integer.valueOf(inputDto.getOrderItem()));
            } catch (OpsException e) {
                log.error("订单还原清空状态失败:", e);
            }
            // bugid:10570 20230424 C14717
            RcvdetailExample exaSttatus = new RcvdetailExample();
            exaSttatus.createCriteria().andRorderNoEqualTo(inputDto.getOrderId())
                    .andRorderItemEqualTo(Integer.parseInt(inputDto.getOrderItem()))
                    .andStatusEqualTo(RcvOrderStatusEnum.CKING.getType());
            Rcvdetail upStatus = new Rcvdetail();
            upStatus.setStatus(RcvOrderStatusEnum.WAITCK.getType());
            wmRouterOrderService.updateRcvdetail(upStatus, exaSttatus);

            Rcvmaster rcvmaster = wmRouterOrderService.getRcvmaster(inputDto.getOrderId());
            // 4.创建调库计划 不删采购单创建调库计划
            if (!inputDto.isDelPo() && org.apache.commons.collections.CollectionUtils.isNotEmpty(inputDto.getPo())) {
                Boolean transferPlan = null;
                try {
                    transferPlan = createTransferPlanService.createTransferPlan(inputDto.getPo(), inputDto.getUserName(), rcvmaster.getEndUser());
                } catch (Exception e) {
                    log.error("自动订单还原生成调库计划失败",e);
                    transferPlan = false;
                }
                String planReustMsg = ";调库计划：失败";
                if (Objects.nonNull(transferPlan)) {
                    planReustMsg = ";调库计划：失败";
                    if (transferPlan) {
                        planReustMsg = ";调库计划：成功";
                    }
                }
                resultLog.append(planReustMsg);
            }
            // 5.接单处理，生成新的物流指令，
            try {
                // bugid:10691 20230509 c14717
                retryCreateOrder(rcvmaster, rcvdetail);
                resultLog.append(";订单分配：成功");
                if(CKTYPEEnum.NOTIFY_UNLIMIT.getCode().equals(rcvmaster.getDlvEntire())){
                    doPcoLogicCenterService.releaseNotify(inputDto.getOrderId(),inputDto.getOrderItem(),"订单还原");
                    doPcoLogicCenterService.notifyShipmentPlanMatchOrder(new NotifyShipmentCondition(inputDto.getOrderId(),inputDto.getOrderItem(),"订单还原"));
                }
                commonResult = ResultVo.success();
            } catch (OpsException ex) {
                resultLog.append(";订单分配：").append(ex.getMessage());
                // 异常处理 改状态
                String msg = "创建订单失败";
                if(Objects.nonNull(ex.getMessage())){
                    if(ex.getMessage().contains("请购单已存在")){
                        msg = "请购单已存在，请先处理请购单";
                    }
                }
                RcvdetailExample example = new RcvdetailExample();
                example.createCriteria().andRorderNoEqualTo(rcvdetail.getRorderNo())
                        .andRorderItemEqualTo(rcvdetail.getRorderItem());
                Rcvdetail up = new Rcvdetail();
                up.setStatus((short) 10);//
                up.setUpdateTime(DateUtil.getNow());
                up.setExpMsg("订单还原-"+msg);
                wmRouterOrderService.updateRcvdetail(up, example);
                log.error(rcvdetail.getRorderFno(), ex);
                resultLog.append(";订单分配：失败");
                commonResult = ResultVo.failure("订单还原-"+msg);
            } catch (Exception ex) {
                // 异常处理 改状态
                RcvdetailExample example = new RcvdetailExample();
                example.createCriteria().andRorderNoEqualTo(rcvdetail.getRorderNo())
                        .andRorderItemEqualTo(rcvdetail.getRorderItem());
                Rcvdetail up = new Rcvdetail();
                up.setStatus((short) 10);//
                up.setUpdateTime(DateUtil.getNow());
                up.setExpMsg("订单还原-创建订单失败");
                wmRouterOrderService.updateRcvdetail(up, example);
                log.error(rcvdetail.getRorderFno(), ex);
                resultLog.append(";订单分配：失败");
                commonResult = ResultVo.failure("订单还原-创建订单失败");
            }

        } else {
            commonResult = ResultVo.failure("订单还原失败-指令不可取消");
        }
        // 记录日志 //bugid:10691 20230509 c14717
        OpsOrderUpdateLog logU = new OpsOrderUpdateLog();
        logU.setOrderid(inputDto.getOrderId());
        logU.setOrderItem(Integer.parseInt(inputDto.getOrderItem()));
        logU.setParams(JSONUtil.toJsonStr(inputDto));
        logU.setResult(resultLog.toString());
        logU.setCreateTime(DateUtil.getNow());
        try {
            opsOrderUpdateLogMapper.insertSelective(logU);
            customerEventPublisher.customerOrderEvent(EventSourceEnum.CUSTOMER_ORDER_REALLOT, inputDto.getOrderId(), Integer.valueOf(inputDto.getOrderItem()));
        }finally {
            // 释放锁
            if (lock) {
                opsRedissonLock.releaseLock("ops:dispatch:" + rcvdetail.getRorderFno());
            }
        }
        return commonResult;
    }


    /**
     * 订单还原 bugid:10034 c14717 20230320
     *
     * @param inputDto
     * @return 是否还原成功
     */
    @PostMapping("/orderChangeToInitStatus")
    public CommonResult<String> orderChangeToInitStatus(@RequestBody OrderChangeToInitStatusDto inputDto) {
        // 1.物流指令取消
        boolean delDoflag = false;
        CommonResult<String> commonResult = null;
        // 记录日志
        StringBuffer resultLog = new StringBuffer();
        resultLog.append("订单还原:");
        resultLog.append(inputDto.getUserName());
        Rcvdetail rcvdetail = null;
        boolean lock = false;
        try {
            log.info("订单还原：{}", JSONUtil.toJsonPrettyStr(inputDto));
            rcvdetail = wmRouterOrderService.getRcvdetail(inputDto.getOrderId(), inputDto.getOrderItem());
            if (Objects.nonNull(rcvdetail)) {
                lock = opsRedissonLock.addLock("ops:dispatch:" + rcvdetail.getRorderFno());
                // 物理指令取消；备注:存在多余调拨单，handConfirm会关联新的指令，存在bug，因此删单时需要同时取消调拨单；
                // 调拨单和交易出库单同时删除成功，才返回成功；
                delDoflag = opsChangeOrderService.orderChangeToInitStatusDelDo(inputDto.getOrderId(),
                        inputDto.getOrderItem(), inputDto.getUserName());
            } else {
                resultLog.append(";无rcvdetail;");
            }
            log.info("订单还原删单标识：{}", delDoflag);
        } catch (OpsException ex) {
            resultLog.append(";物流指令取消：").append(ex.getMessage());
            log.error("订单还原删单", ex);
        } catch (Exception ex) {
            log.error("订单还原删单", ex);
        }
        // 记录日志
        resultLog.append(";物流指令取消：").append(delDoflag);
        // 2.物流指令取消成功
        if (delDoflag) {
            // 3.删除采购单 返回是否成功不影响接单处理
            if (inputDto.isDelPo()) {
                resultLog.append(";采购取消：");
                boolean poFlag = false;
                try {
                    // 采购单取消
                    CommonResult<Boolean> poResult = opsChangeOrderService
                            .orderChangeToInitStatusDelPo(inputDto.getPo().get(0));
                    if (poResult.isSuccess()) {
                        poFlag = true;
                    }
                    log.info("订单还原取消采购单:{}", poResult.isSuccess());
                } catch (Exception e) {
                    log.error("订单还原取消采购单失败:", e);
                }
                resultLog.append(poFlag);
            }
            // 3.1清空状态
            try {
                baseOrderAssignResultService.deleteResultOrder(inputDto.getOrderId(), Integer.valueOf(inputDto.getOrderItem()));
            } catch (OpsException e) {
                log.error("订单还原清空状态失败:", e);
            }
            // bugid:10570 20230424 C14717
            RcvdetailExample exaSttatus = new RcvdetailExample();
            exaSttatus.createCriteria().andRorderNoEqualTo(inputDto.getOrderId())
                    .andRorderItemEqualTo(Integer.parseInt(inputDto.getOrderItem()))
                    .andStatusEqualTo(RcvOrderStatusEnum.CKING.getType());
            Rcvdetail upStatus = new Rcvdetail();
            upStatus.setStatus(RcvOrderStatusEnum.WAITCK.getType());
            wmRouterOrderService.updateRcvdetail(upStatus, exaSttatus);

            Rcvmaster rcvmaster = wmRouterOrderService.getRcvmaster(inputDto.getOrderId());
            //4.创建调库计划 不删采购单创建调库计划
            String planReustMsg = "";
            if (!inputDto.isDelPo() && org.apache.commons.collections.CollectionUtils.isNotEmpty(inputDto.getPo())) {
                Boolean transferPlan = null;
                try {
                    transferPlan = createTransferPlanService.createTransferPlan(inputDto.getPo(), inputDto.getUserName(), rcvmaster.getEndUser());
                } catch (Exception e) {
                    log.error("手动订单还原生成调库计划失败",e);
                    transferPlan = false;
                }
                planReustMsg = ";调库计划：失败";
                if (Objects.nonNull(transferPlan)) {
                    planReustMsg = ";调库计划：失败";
                    if (transferPlan) {
                        planReustMsg = ";调库计划：成功";
                    }
                }
                resultLog.append(planReustMsg);
            }
            // 5.接单处理，生成新的物流指令，
            try {
                // bugid:10691 20230509 c14717
                retryCreateOrder(rcvmaster, rcvdetail);
                resultLog.append(";订单分配：true");
                commonResult = CommonResult.success();
                if(CKTYPEEnum.NOTIFY_UNLIMIT.getCode().equals(rcvmaster.getDlvEntire())){
                    doPcoLogicCenterService.releaseNotify(inputDto.getOrderId(),inputDto.getOrderItem(),"订单还原");
                    doPcoLogicCenterService.notifyShipmentPlanMatchOrder(new NotifyShipmentCondition(inputDto.getOrderId(),inputDto.getOrderItem(),"订单还原"));
                }
            } catch (OpsException ex) {
                String msg = "创建订单失败";
                if(Objects.nonNull(ex.getMessage())){
                    if(ex.getMessage().contains("请购单已存在")){
                        msg = "请购单已存在，请先处理请购单";
                    }
                }
                resultLog.append(";订单分配：").append(ex.getMessage());
                // 异常处理 改状态
                RcvdetailExample example = new RcvdetailExample();
                example.createCriteria().andRorderNoEqualTo(rcvdetail.getRorderNo())
                        .andRorderItemEqualTo(rcvdetail.getRorderItem());
                Rcvdetail up = new Rcvdetail();
                up.setStatus((short) 10);//
                up.setUpdateTime(DateUtil.getNow());
                up.setExpMsg("订单还原-"+msg);
                wmRouterOrderService.updateRcvdetail(up, example);
                log.error(rcvdetail.getRorderFno(), ex);
                resultLog.append(";订单分配：失败");
                if(StringUtils.isNotBlank(planReustMsg)){
                    msg = msg + planReustMsg;
                }
                commonResult = CommonResult.failure("订单还原-"+msg);
            } catch (Exception ex) {
                // 异常处理 改状态
                RcvdetailExample example = new RcvdetailExample();
                example.createCriteria().andRorderNoEqualTo(rcvdetail.getRorderNo())
                        .andRorderItemEqualTo(rcvdetail.getRorderItem());
                Rcvdetail up = new Rcvdetail();
                up.setStatus((short) 10);//
                up.setUpdateTime(DateUtil.getNow());
                up.setExpMsg("订单还原-创建订单失败");
                wmRouterOrderService.updateRcvdetail(up, example);
                log.error(rcvdetail.getRorderFno(), ex);
                resultLog.append(";订单分配：失败");
                commonResult = CommonResult.failure("订单还原-创建订单失败");
            }
        } else {
            commonResult = CommonResult.failure("订单还原失败-指令不可取消");
        }
        // 记录日志 //bugid:10691 20230509 c14717
        OpsOrderUpdateLog logUpdate = new OpsOrderUpdateLog();
        logUpdate.setOrderid(inputDto.getOrderId());
        logUpdate.setOrderItem(Integer.parseInt(inputDto.getOrderItem()));
        logUpdate.setParams(JSONUtil.toJsonStr(inputDto));
        logUpdate.setResult(resultLog.toString());
        logUpdate.setCreateTime(DateUtil.getNow());
        try {
            opsOrderUpdateLogMapper.insertSelective(logUpdate);
            customerEventPublisher.customerOrderEvent(EventSourceEnum.CUSTOMER_ORDER_REALLOT, inputDto.getOrderId(), Integer.valueOf(inputDto.getOrderItem()));
        }
        finally {
            // 释放锁
            if (lock) {
                opsRedissonLock.releaseLock("ops:dispatch:" + rcvdetail.getRorderFno());
            }
        }
        return commonResult;
    }

    /**
     * bugid 10961 订单还原重试次数
     *
     * @param rcvmaster
     * @param rcvdetail
     * @throws OpsException
     */
    public void retryCreateOrder(Rcvmaster rcvmaster, Rcvdetail rcvdetail) throws OpsException {
        ResultVo<DataTypeVO> info = dictDataServiceFeignApi.getDataTypeCodesInfo("1025", "count");
        int countFlag = Integer.parseInt(info.getData().getExtNote1());
        for (int i = 0; i < countFlag; i++) {
            try {
                Date allotStartTime = DateUtil.getNow();
                List<OpsOrderAssignResult> list = wmDispatchService.OrderdispatchForOrderItem(rcvmaster, rcvdetail,
                        true);
                Date allotEndTime = DateUtil.getNow();
                // 接单处理状态
                try {
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("allotStartTime", allotStartTime);
                    map.put("allotEndTime", allotEndTime);
                    map.put("assignResult", list);
                    customerEventPublisher.customerOrderEvent(EventSourceEnum.CUSTOMER_ORDER_ALLOT, rcvdetail.getRorderNo(), rcvdetail.getRorderItem(), map);
                } catch (Exception ex) {
                    log.error(rcvdetail.getRorderFno(), ex);
                }
                return;
            } catch (OpsException ex) {
                if (StringUtils.isNotBlank(ex.getMessage()) && ex.getMessage().contains("库存更新异常")) {
                    log.info("订单还原，库存更新异常重新跑单");
                } else {
                    throw ex;
                }
            }
        }
        throw Exceptions.OpsException("订单还原库存更新异常");
    }

    /**
     * 委托在库出库调用库存属性
     *
     * @param doId
     * @return
     */
    @GetMapping("/wtInventoryType")
    public CommonResult<List<OpsWTInventoryDTO>> getWtInventoryType(String doId) {
        try {
            List<OpsWTInventoryDTO> list = opsInventoryService.findInventoryIdAndInventoryTypeByDoId(doId);
            return CommonResult.success(list);
        } catch (Exception e) {
            return CommonResult.failure("失败");
        }
    }

    /**
     * 委托在库出库调用库存属性
     *
     * @param
     * @return
     */
    @GetMapping("/wtInventoryTypePco")
    public CommonResult<List<OpsWTInventoryDTO>> getWtInventoryTypePco(String pcoId, Integer pcoItem) {
        try {
            List<OpsWTInventoryDTO> list = opsInventoryService.findInventoryIdAndInventoryTypeByPcoId(pcoId, pcoItem);
            return CommonResult.success(list);
        } catch (Exception e) {
            return CommonResult.failure("失败");
        }
    }

    @PostMapping("/cust/dispatch")
    public CommonResult<String> dispatch(@RequestBody DispatchForOrderItemInputDto inputDto) {
        boolean lock = false;
        try {
            lock = opsRedissonLock.addLock("ops:dispatch:" + inputDto.getRcvdetail().getRorderFno());
            //bugid:15747 c14717 20241114
            Rcvdetail rcvdetail = wmRouterOrderService.getRcvdetail(inputDto.getRcvdetail().getRorderNo(), inputDto.getRcvdetail().getRorderItem().toString());
            Rcvmaster rcvmaster = wmRouterOrderService.getRcvmaster( inputDto.getRcvdetail().getRorderNo());
            Date allotStartTime =  DateUtil.getNow();
            List<OpsOrderAssignResult> list = wmDispatchService.OrderdispatchForOrderItem(rcvmaster,
                    rcvdetail, false);
            Date allotEndTime =  DateUtil.getNow();
            try {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("allotStartTime", allotStartTime);
                map.put("allotEndTime", allotEndTime);
                map.put("assignResult", list);
                customerEventPublisher.customerOrderEvent(EventSourceEnum.CUSTOMER_ORDER_ALLOT, rcvdetail.getRorderNo(), rcvdetail.getRorderItem(), map);
            } catch (Exception ex) {
                log.error(inputDto.getRcvdetail().getRorderFno(), ex);
            }
            return CommonResult.success();
        } catch (OpsException ex) {
            // 异常处理 改状态
            RcvdetailExample example = new RcvdetailExample();
            example.createCriteria().andRorderNoEqualTo(inputDto.getRcvdetail().getRorderNo())
                    .andRorderItemEqualTo(inputDto.getRcvdetail().getRorderItem());
            Rcvdetail up = new Rcvdetail();
            up.setStatus((short) 10);//
            up.setUpdateTime(DateUtil.getNow());
            up.setExpMsg(ex.getMessage());
            // bugid:10691 20230509 c14717
            if (StringUtils.isNotBlank(ex.getMessage()) && ex.getMessage().contains("库存更新异常")) {
                log.info("库存更新异常 重新跑单");
            } else {
                wmRouterOrderService.updateRcvdetail(up, example);
            }
            log.error(inputDto.getRcvdetail().getRorderFno(), ex);
            return CommonResult.failure(ex.getMessage());
        } catch (Exception ex) {

            // 异常处理 改状态
            RcvdetailExample example = new RcvdetailExample();
            example.createCriteria().andRorderNoEqualTo(inputDto.getRcvdetail().getRorderNo())
                    .andRorderItemEqualTo(inputDto.getRcvdetail().getRorderItem());
            Rcvdetail up = new Rcvdetail();
            up.setStatus((short) 10);//
            up.setUpdateTime(DateUtil.getNow());
            up.setExpMsg("系统异常，请联系管理员");
            wmRouterOrderService.updateRcvdetail(up, example);
            log.error(inputDto.getRcvdetail().getRorderFno(), ex);
            return CommonResult.failure(ex.getMessage());
        } finally {
            // 释放锁
            if (lock) {
                opsRedissonLock.releaseLock("ops:dispatch:" + inputDto.getRcvdetail().getRorderFno());
            }
        }
    }

    @PostMapping("/create")
    public CommonResult<String> create() {
        try {
            List<Rcvdetail> list = wmRouterOrderService.getRcvdetailList(1, RcvOrderStatusEnum.INIT.getType());
            for (Rcvdetail rcvdetail : list) {
                Rcvmaster rcvmaster = wmRouterOrderService.getRcvmaster(rcvdetail.getRorderNo());
                Date allotStartTime = DateUtil.getNow();
                List<OpsOrderAssignResult> list1 = wmDispatchService.OrderdispatchForOrderItem(rcvmaster, rcvdetail,
                        false);
                Date allotEndTime = DateUtil.getNow();
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("allotStartTime", allotStartTime);
                map.put("allotEndTime", allotEndTime);
                map.put("assignResult", list1);
                customerEventPublisher.customerOrderEvent(EventSourceEnum.CUSTOMER_ORDER_ALLOT, rcvdetail.getRorderNo(), rcvdetail.getRorderItem(), map);
            }
            return CommonResult.success();
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            return CommonResult.failure(null, ex.getMessage());
        }
    }

    @PostMapping("/create/order")
    public CommonResult<String> createByorder(@RequestParam("rorderNo") String rorderNo,
                                              @RequestParam("rorderItem") String rorderItem) {
        Rcvmaster rcvmaster = wmRouterOrderService.getRcvmaster(rorderNo);
        Rcvdetail rcvdetail = null;
        try {
            rcvdetail = wmRouterOrderService.getRcvdetail(rorderNo, rorderItem);
        } catch (Exception e) {
            RcvdetailExample example = new RcvdetailExample();
            example.createCriteria().andRorderNoEqualTo(rcvdetail.getRorderNo())
                    .andRorderItemEqualTo(rcvdetail.getRorderItem());
            Rcvdetail up = new Rcvdetail();
            up.setStatus((short) 10);
            up.setUpdateTime(DateUtil.getNow());
            // up.setExpMsg("异常");
            if (e.getMessage() != null && e.getMessage().length() > 200) {
                up.setExpMsg(e.getMessage().substring(0, 200));
            } else {
                up.setExpMsg(StringUtils.isBlank(e.getMessage()) ? e.toString() : e.getMessage());
            }
            wmRouterOrderService.updateRcvdetail(up, example);
            log.error(e.getMessage(), e);
            return CommonResult.failure(e.getMessage());
        }

        Boolean lock = opsRedissonLock.addLock("ops:dispatch:" + rcvdetail.getRorderFno());
        try {

            Date allotStartTime = DateUtil.getNow();
            List<OpsOrderAssignResult> list1 = wmDispatchService.OrderdispatchForOrderItem(rcvmaster, rcvdetail, false);
            Date allotEndTime = DateUtil.getNow();
            try {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("allotStartTime", allotStartTime);
                map.put("allotEndTime", allotEndTime);
                map.put("assignResult", list1);
                customerEventPublisher.customerOrderEvent(EventSourceEnum.CUSTOMER_ORDER_ALLOT, rcvdetail.getRorderNo(), rcvdetail.getRorderItem(), map);
            } catch (Exception ex) {
                log.error(rcvdetail.getRorderFno(), ex);
            }

            return CommonResult.success();
        } catch (OpsException ex) {//
            // 异常处理 改状态
            RcvdetailExample example = new RcvdetailExample();
            example.createCriteria().andRorderNoEqualTo(rcvdetail.getRorderNo())
                    .andRorderItemEqualTo(rcvdetail.getRorderItem());
            Rcvdetail up = new Rcvdetail();
            up.setStatus((short) 10);//
            up.setUpdateTime(DateUtil.getNow());
            up.setExpMsg(ex.getMessage());
            // bugid:10691 20230509 c14717
            if (StringUtils.isNotBlank(ex.getMessage()) && ex.getMessage().contains("库存更新异常")) {
                log.info("库存更新异常 重新跑单");
            } else {
                wmRouterOrderService.updateRcvdetail(up, example);
            }
            log.error(rcvdetail.getRorderFno(), ex);
            return CommonResult.failure(ex.getMessage());
        } catch (Exception ex) {

            // 异常处理 改状态
            RcvdetailExample example = new RcvdetailExample();
            example.createCriteria().andRorderNoEqualTo(rcvdetail.getRorderNo())
                    .andRorderItemEqualTo(rcvdetail.getRorderItem());
            Rcvdetail up = new Rcvdetail();
            up.setStatus((short) 10);//
            up.setUpdateTime(DateUtil.getNow());
            up.setExpMsg("系统异常，请联系管理员");
            wmRouterOrderService.updateRcvdetail(up, example);
            log.error(rcvdetail.getRorderFno(), ex);
            return CommonResult.failure(ex.getMessage());
        } finally {
            // 释放锁
            if (lock) {
                opsRedissonLock.releaseLock("ops:dispatch:" + rcvdetail.getRorderFno());

            }
        }
    }

    // 获得一定数量的偶数id的rcvdetail
    @GetMapping("/cust/orderitems/evenNumber")
    public CommonResult<List<Rcvdetail>> getcustdetailByEvenNumberId(@RequestParam(value = "limit") int limit) {

        try {

            List<Rcvdetail> list = wmRouterOrderService.getRcvdetailByLimitAndModEvenNumberId(limit,
                    RcvOrderStatusEnum.INIT.getType());
            return CommonResult.success(list);
        } catch (Exception e) {
            return CommonResult.failure(e.getMessage());
        }
    }

    // 获得一定数量的奇数id的rcvdetail
    @GetMapping("/cust/orderitems/oddNumber")
    public CommonResult<List<Rcvdetail>> getcustdetailByOddNumber(@RequestParam(value = "limit") int limit) {

        try {
            List<Rcvdetail> list = wmRouterOrderService.getRcvdetailByLimitAndModOddNumberId(limit,
                    RcvOrderStatusEnum.INIT.getType());
            return CommonResult.success(list);
        } catch (Exception e) {

            return CommonResult.failure(e.getMessage());
        }
    }

    @GetMapping("/cust/orderitems")
    public CommonResult<List<Rcvdetail>> getcustdetail(@RequestParam(value = "limit") int limit) {
        try {
            List<Rcvdetail> list = wmRouterOrderService.getRcvdetailList(limit, RcvOrderStatusEnum.INIT.getType());
            return CommonResult.success(list);
        } catch (Exception e) {
            return CommonResult.failure(e.getMessage());
        }
    }

    /**
     * bugid:10618 C14717 20230808 订单自动处理增加补偿程序
     *
     * @param limit
     * @param beforeMonth
     * @return
     */
    @GetMapping("/cust/orderitemsByBeforeMonth")
    public CommonResult<List<Rcvdetail>> getRcvdetailListByBeforeMoth(@RequestParam(value = "limit") int limit, @RequestParam(value = "beforeMonth") int beforeMonth) {
        try {
            List<Rcvdetail> list = wmRouterOrderService.getRcvdetailListByBeforeMoth(limit, RcvOrderStatusEnum.INIT.getType(), beforeMonth);
            return CommonResult.success(list);
        } catch (Exception e) {
            log.error("接单处理", e);
            return CommonResult.failure(e.getMessage());
        }
    }

    @GetMapping("/cust/getRcvdetail")
    public CommonResult<Rcvdetail> getRcvdetail(@RequestParam(value = "rorderNo") String rorderNo,
                                                @RequestParam(value = "rorderItem") Integer rorderItem) {

        try {
            Rcvdetail rcvdetail = wmRouterOrderService.getRcvdetail(rorderNo, rorderItem.toString());
            return CommonResult.success(rcvdetail);
        } catch (Exception e) {

            return CommonResult.failure(e.getMessage());
        }
    }

    @GetMapping("/cust/master")
    public CommonResult<Rcvmaster> getCustOrder(@RequestParam("rorderNo") String rorderNo) {

        try {
            Rcvmaster rcvmaster = wmRouterOrderService.getRcvmaster(rorderNo);
            DispatchForOrderItemInputDto dto = new DispatchForOrderItemInputDto(rcvmaster, new Rcvdetail());
            log.info(JSON.toJSONString(dto));
            return CommonResult.success(rcvmaster);
        } catch (Exception e) {
            return CommonResult.failure(e.getMessage());
        }
    }


    @Log("预到货")
    @PostMapping("/import/init/Invoice")
    public CommonResult<String> InvoniceForPoInit(@RequestBody List<OpsPurchaseinvoice> list) {
        try {
            log.info("InvoniceForPoInit params = {}", JSON.toJSONString(list));
            ImpInvoiceEventLog log = new ImpInvoiceEventLog();
            log.setOpType("/order/import/init/Invoice");
            log.setRequestParam(JSON.toJSONString(list));
            wmDispatchService.addImpInvoiceEventLog(log);
            wmDispatchService.invoiceForPoInit(list);
            return CommonResult.success();
        } catch (Exception ex) {
            log.error("InvoniceForPoInit params = {}", JSON.toJSONString(list));
            return CommonResult.failure(ex.getMessage());
        }
    }

    @PostMapping("/import/update/Invoice")
    public CommonResult<String> InvoniceForPoUpdate(@RequestBody List<OpsPurchaseinvoice> list) {
        try {
            log.info("InvoniceForPoUpdate params = {}", JSON.toJSONString(list));
            ImpInvoiceEventLog log = new ImpInvoiceEventLog();
            log.setOpType("/order/import/update/Invoice");
            log.setRequestParam(JSON.toJSONString(list));
            wmDispatchService.addImpInvoiceEventLog(log);
            wmDispatchService.invoiceForPoUpdate(list);
            return CommonResult.success();
        } catch (Exception ex) {
            log.error("InvoniceForPoUpdate params = {}", JSON.toJSONString(list));
            return CommonResult.failure(ex.getMessage());
        }
    }


    @Log("发票导入")
    @PostMapping("/import/confirm/Invoice")
    public CommonResult<String> InvoniceForPoConfirm(@RequestBody List<OpsImpdata> list) {
        try {
            log.info("InvoniceForPoConfirm params = {}", JSON.toJSONString(list));
            ImpInvoiceEventLog log = new ImpInvoiceEventLog();
            log.setOpType("/order/import/confirm/Invoice");
            log.setRequestParam(JSON.toJSONString(list));
            wmDispatchService.addImpInvoiceEventLog(log);
            wmDispatchService.invoiceForPoConfirm(list);
            return CommonResult.success();
        } catch (Exception ex) {
            log.error("InvoniceForPoConfirm params = {}", JSON.toJSONString(list));
            return CommonResult.failure(ex.getMessage());
        }
    }

    @Log("发票签收")
    @PostMapping("/signInvoice")
    public CommonResult signInvoiceForWms(@RequestBody RoSignConfirmDto param) {
        try {
            ImpInvoiceEventLog log = new ImpInvoiceEventLog();
            log.setOpType("/order/signInvoice");
            log.setRequestParam(param.getInvoice());
            wmDispatchService.addImpInvoiceEventLog(log);
            wmDispatchService.invoiceForSignWms(param);
            return CommonResult.success();
        } catch (OpsException e) {
            log.error("signInvoiceForWms params = {} {}", param.getInvoice(), param.getWarehouse());
            return CommonResult.failure(e.getMessage());
        }
    }

    @Log("到货确认")
    @PostMapping("/Confirmgoods")
    public RoConfirm Confirmgoods(@RequestBody RoSignConfirmDto param) {
        try {
            ImpInvoiceEventLog log = new ImpInvoiceEventLog();
            log.setOpType("/order/confirmgoods");
            log.setRequestParam(param.getInvoice());
            wmDispatchService.addImpInvoiceEventLog(log);
            GoodsConfirmDto dto = wmDispatchService.goodsConfirm(param);
            wmDoService.sendToWmsForGoodsConfirm(dto.getDoSendMap(), dto.getPcoSendMap());
            return new RoConfirm(FluxMsgFlagEnum.SUCCESS.getFlag(), param.getWarehouse(), null, dto.getList());
        } catch (OpsException e) {
            return new RoConfirm(FluxMsgFlagEnum.FAILURE.getFlag(), param.getWarehouse(), e.getMessage(), null);
        }
    }

    // 交接确认用户调拨出时回传ops发票（承运商母单号）信息
    @PostMapping("/handconfirm")
    public CommonResult handconfirm(@RequestBody HandConfirm handConfirm) {
        try {
            log.info("handconfirm params = {} ", handConfirm);
            ImpInvoiceEventLog log = new ImpInvoiceEventLog();
            log.setOpType("/order/handconfirm");
            log.setRequestParam(JSON.toJSONString(handConfirm));
            wmDispatchService.addImpInvoiceEventLog(log);
            roDispatcherService.handConfirm(handConfirm);
            return CommonResult.success();
        } catch (NullPointerException e) {
            log.error("handconfirm params = {} ,ex:{}", JSON.toJSONString(handConfirm), e);
            return CommonResult.failure("空指针异常");
        } catch (Exception e) {
            log.error("handconfirm params = {} ,ex:{}", JSON.toJSONString(handConfirm), e);
            return CommonResult.failure(e.getMessage());
        }
    }
/*

    // 交接确认用户调拨出时回传ops发票（承运商母单号）信息
    @GetMapping("/handCsOrderConfirm")
    public CommonResult handCsOrderConfirm() {
        try {
            ImpInvoiceEventLog log = new ImpInvoiceEventLog();
            log.setOpType("/order/handCsOrderConfirm");
            log.setRequestParam("");
            wmDispatchService.addImpInvoiceEventLog(log);
            wmDispatchService.handCsOrderConfirm();
            return CommonResult.success();
        } catch (Exception e) {
            return CommonResult.failure(e.getMessage());
        }
    }
*/

    /**
     * 调用wms看交易单是否可以删除（不包括调拨单） C14717 20221017确认废弃方法
     */
    /*
     * @Log("客户单取消")
     *
     * @PostMapping("/cancel") public
     * CommonResult<List<CancelResultForOrderDto>> cancel(@RequestBody
     * CancelForOrderDto inputDto) { try { boolean successCancelFlag =
     * wmDispatchService.cancellationOfOrder(inputDto); if (successCancelFlag) {
     * return CommonResult.success(); } else { return CommonResult.failure(); }
     * } catch (OpsException ex) { return CommonResult.failure(ex.getMessage());
     * } }
     */

    /**
     * 删除主动调拨单
     */
    @Log("主动调拨单取消")
    @PostMapping("/tkck/cancel")
    public CommonResult<List<CancelResultForOrderDto>> cancelTKCKByOrder(@RequestBody CancelForOrderDto inputDto) {
        try {
            boolean successCancelFlag = wmDispatchService.cancelTKCKByOrder(inputDto);
            if (successCancelFlag) {
                return CommonResult.success();
            } else {
                return CommonResult.failure();
            }
        } catch (OpsException ex) {
            return CommonResult.failure(ex.getMessage());
        } catch (Exception ex) {
            log.error("{}", ex);
            return CommonResult.failure("系统报错");
        }
    }

    /**
     * bugid:14094 20240506 c14717
     * 主动调拨单完纳
     *
     * @param inputDto
     * @return
     */
    @Log("调库出库完纳问询")
    @PostMapping("/askTKCKFinish")
    public CommonResult<FinishPageForOrderDto> askTKCKFinish(@RequestBody FinishForOrderDto inputDto) {
        try {
            List<OpsDo> doList = baseDoService.findDoListByOrder(inputDto.getOrderNo(), inputDto.getOrderItem(),
                    null, null);
            if (CollectionUtils.isEmpty(doList)) {
                return CommonResult.failure("无指令");
            }
            // 页面报文
            FinishPageForOrderDto pageObj = new FinishPageForOrderDto(inputDto.getOrderNo(),
                    inputDto.getOrderItem(), inputDto.getOrderFulNo(), inputDto.getModelNo());

            // 1.查询可完纳指令 outqty!= userqty
            List<FinishOrderWmsReqDto> reqList = new ArrayList<>();
            opsDoService.getTKCKFinishDo(doList, reqList, pageObj);

            if (org.apache.commons.collections.CollectionUtils.isEmpty(reqList)) {
                return CommonResult.failure("无可完纳指令");
            }
            // 2.问询wms接口 返回可完纳数量和doid
            CommonResult<String> askResult = opsCallWmsFeignApi.askFinish(reqList);
            if (!askResult.isSuccess()) {
                log.info("完纳问询不可完纳订单：{},返回信息{}", inputDto.getOrderNo(), askResult.getMessage());
                return CommonResult.failure("物流作业中，不可完纳");
            }
            List<FinishOrderWmsResDto> askRepList = JSONArray.parseArray(askResult.getData(), FinishOrderWmsResDto.class);
            // 3.比较完纳数量和doItem一致,验证请求已响应报文数量是否一致
            opsDoService.checkFinishDo(reqList, askRepList);
            return CommonResult.success("成功", pageObj);
        } catch (OpsException e) {
            log.error("完纳问询失败", e);
            return CommonResult.failure(e.getMessage());
        } catch (Exception e) {
            log.error("完纳问询失败", e);
            return CommonResult.failure("失败");
        }
    }

    /**
     * bugid:14094 20240506 c14717
     * 主动调拨完纳
     *
     * @param inputDto
     * @return
     */
    @Log("调库出库完纳执行")
    @PostMapping("/exeTKCKFinish")
    public CommonResult<String> orderTKCKFinish(@RequestBody FinishPageForOrderDto inputDto) {
        String lockKey = "ops:orderfinish:" + inputDto.getOrderNo() + "-" + inputDto.getOrderItem();
        boolean lock = opsRedissonLock.addLock(lockKey);
        try {
            List<OpsDo> doList = baseDoService.findDoListByOrder(inputDto.getOrderNo(), inputDto.getOrderItem(),
                    null, null);
            if (CollectionUtils.isEmpty(doList)) {
                return CommonResult.failure("无指令");
            }
            // 1.查询可完纳指令 outqty!= userqty
            List<FinishOrderWmsReqDto> reqList = new ArrayList<>();
            Integer jyckDoOutQty = opsDoService.getTKCKFinishDo(doList, reqList, null);
            if (org.apache.commons.collections.CollectionUtils.isEmpty(reqList)) {
                return CommonResult.failure("无可完纳指令");
            }
            if (jyckDoOutQty > inputDto.getFinishQty()) {
                return CommonResult.failure("完纳失败，出库数量大于完纳数量");
            }
            // 4.调用wms接口进行完纳
            CommonResult<String> exeResult = opsCallWmsFeignApi.exeFinish(reqList);
            if (!exeResult.isSuccess()) {
                log.info("完纳执行不可完纳订单：{},返回信息{}", inputDto.getOrderNo(), exeResult.getMessage());
                OpsOrderUpdateLog log = new OpsOrderUpdateLog();
                log.setOrderid(inputDto.getOrderNo());
                log.setOrderItem(Integer.parseInt(inputDto.getOrderItem()));
                log.setParams(exeResult.getData());
                log.setResult("失败：" + exeResult.getMessage());
                log.setCreateTime(DateUtil.getNow());
                opsOrderUpdateLogMapper.insertSelective(log);
                return CommonResult.failure("物流作业中，不可完纳");
            }

            // 5.更新指令 以及rcv
            StringBuffer logBuf = new StringBuffer();
            logBuf.append("调库单完纳：");
            opsDoService.finishTKCKDo(doList, inputDto.getOrderNo(), inputDto.getOrderItem(), logBuf, inputDto.getUserName());

            // 8.记录日志 持久化日志信息
            OpsOrderUpdateLog log = new OpsOrderUpdateLog();
            log.setOrderid(inputDto.getOrderNo());
            log.setOrderItem(Integer.parseInt(inputDto.getOrderItem()));
            log.setParams(exeResult.getData());
            log.setResult(logBuf.toString());
            log.setCreateTime(DateUtil.getNow());
            opsOrderUpdateLogMapper.insertSelective(log);
            return CommonResult.success("完纳成功；", null);
        } catch (OpsException e) {
            log.error("完纳失败", e);
            return CommonResult.failure(e.getMessage());
        } catch (Exception e) {
            log.error("完纳失败", e);
            return CommonResult.failure("失败");
        } finally {
            if (lock) {
                opsRedissonLock.releaseLock(lockKey);
            }
        }
    }

    /**
     * bugid:11758 20230814 c14717
     *
     * @param inputDto
     * @return
     */
    @Log("完纳问询")
    @PostMapping("/askOrderFinish")
    public CommonResult<FinishPageForOrderDto> askOrderFinish(@RequestBody FinishForOrderDto inputDto) {
        try {
            List<OpsDo> doList = baseDoService.findDoListByOrder(inputDto.getOrderNo(), inputDto.getOrderItem(),
                    null, null);
            if (CollectionUtils.isEmpty(doList)) {
                return CommonResult.failure("无指令");
            }
            // 页面报文
            FinishPageForOrderDto pageObj = new FinishPageForOrderDto(inputDto.getOrderNo(),
                    inputDto.getOrderItem(), inputDto.getOrderFulNo(), inputDto.getModelNo());

            // 1.查询可完纳指令 outqty!= userqty
            List<FinishOrderWmsReqDto> reqList = new ArrayList<>();
            opsDoService.getFinishDo(doList, reqList, pageObj);

            if (org.apache.commons.collections.CollectionUtils.isEmpty(reqList)) {
                return CommonResult.failure("无可完纳指令");
            }
            // 2.问询wms接口 返回可完纳数量和doid
            CommonResult<String> askResult = opsCallWmsFeignApi.askFinish(reqList);
            if (!askResult.isSuccess()) {
                log.info("完纳问询不可完纳订单：{},返回信息{}", inputDto.getOrderNo(), askResult.getMessage());
                return CommonResult.failure("物流作业中，不可完纳");
            }
            List<FinishOrderWmsResDto> askRepList = JSONArray.parseArray(askResult.getData(), FinishOrderWmsResDto.class);
            // 3.比较完纳数量和doItem一致,验证请求已响应报文数量是否一致
            opsDoService.checkFinishDo(reqList, askRepList);

            // 4.拼接页面报文 采购信息 关联p
            List<FinishPoListForDto> poPageList = new ArrayList<>();
            opsDoService.getFinishPo(reqList, poPageList, pageObj);

            return CommonResult.success("成功", pageObj);
        } catch (OpsException e) {
            log.error("完纳问询失败", e);
            return CommonResult.failure(e.getMessage());
        } catch (Exception e) {
            log.error("完纳问询失败", e);
            return CommonResult.failure("失败");
        }
    }


    /**
     * bugid:11758 20230814 c14717
     *
     * @param inputDto
     * @return
     */
    @Log("完纳执行")
    @PostMapping("/exeFinish")
    public CommonResult<String> orderFinish(@RequestBody FinishPageForOrderDto inputDto) {
        String lockKey = "ops:orderfinish:" + inputDto.getOrderNo() + "-" + inputDto.getOrderItem();
        Boolean lock = opsRedissonLock.addLock(lockKey);
        try {
            List<OpsDo> doList = baseDoService.findDoListByOrder(inputDto.getOrderNo(), inputDto.getOrderItem(),
                    null, null);
            if (CollectionUtils.isEmpty(doList)) {
                return CommonResult.failure("无指令");
            }
            // 1.查询可完纳指令 outqty!= userqty
            List<FinishOrderWmsReqDto> reqList = new ArrayList<>();
            Integer jyckDoOutQty = opsDoService.getFinishDo(doList, reqList, null);
            if (org.apache.commons.collections.CollectionUtils.isEmpty(reqList)) {
                return CommonResult.failure("无可完纳指令");
            }
            if (jyckDoOutQty > inputDto.getFinishQty()) {
                return CommonResult.failure("完纳失败，出库数量大于完纳数量");
            }

            // 4.调用wms接口进行完纳
            CommonResult<String> exeResult = opsCallWmsFeignApi.exeFinish(reqList);
            if (!exeResult.isSuccess()) {
                log.info("完纳执行不可完纳订单：{},返回信息{}", inputDto.getOrderNo(), exeResult.getMessage());
                OpsOrderUpdateLog log = new OpsOrderUpdateLog();
                log.setOrderid(inputDto.getOrderNo());
                log.setOrderItem(Integer.parseInt(inputDto.getOrderItem()));
                log.setParams(exeResult.getData());
                log.setResult("失败：" + exeResult.getMessage());
                log.setCreateTime(DateUtil.getNow());
                opsOrderUpdateLogMapper.insertSelective(log);
                return CommonResult.failure("物流作业中，不可完纳");
            }

            // 5.更新指令 以及rcv
            StringBuffer logBuf = new StringBuffer();
            logBuf.append("订单完纳：");
            opsDoService.finishDo(doList, inputDto.getOrderNo(), inputDto.getOrderItem(), logBuf, inputDto.getUserName());
            // 6.调用状态代码
            customerEventPublisher.customerOrderEvent(EventSourceEnum.CUSTOMER_ORDER_FINISH, inputDto.getOrderNo(), Integer.valueOf(inputDto.getOrderItem()));
            // 7.调用采购完纳
            StringBuffer poLogBuf = new StringBuffer();
            if (org.apache.commons.collections.CollectionUtils.isNotEmpty(inputDto.getPoList())) {
                for (FinishPoListForDto item : inputDto.getPoList()) {
                    if (item.getDoStatus().equals(2)) {//采购完纳
                        int pQty = item.getpQty();
                        int arrQty = item.getArrQty();
                        int fQty = item.getFinishPoQty();
                        poLogBuf.append("-采购完纳：[");
                        poLogBuf.append(item.getpFullNo());
                        poLogBuf.append("-");
                        if (fQty >= arrQty && fQty < pQty) {
                            item.setOperator(inputDto.getUserName());
                            CommonResult poFinResult = poFinFeignApi.finishPoHandel(item);
                            poLogBuf.append(poFinResult.getMessage());
                            poLogBuf.append("]");
                        } else {
                            poLogBuf.append("采购完纳数量不符");
                            poLogBuf.append("]");
                        }
                    } else if (item.getDoStatus().equals(1)) {//采购删单
                        CommonResult poFinResult = poFinFeignApi.finishPoHandel(item);
                        poLogBuf.append("-采购删单：[");
                        poLogBuf.append(item.getpFullNo());
                        poLogBuf.append("-");
                        poLogBuf.append(poFinResult.getMessage());
                        poLogBuf.append("]");
                    }
                }
            }
            // 8.记录日志 持久化日志信息
            OpsOrderUpdateLog log = new OpsOrderUpdateLog();
            log.setOrderid(inputDto.getOrderNo());
            log.setOrderItem(Integer.parseInt(inputDto.getOrderItem()));
            log.setParams(exeResult.getData());
            log.setResult(logBuf.append(poLogBuf).toString());
            log.setCreateTime(DateUtil.getNow());
            opsOrderUpdateLogMapper.insertSelective(log);
            //通知发货  bugid:17720 c14717 20250522
            notifyShipmentPlanDao.updateNotifyShipmentFinish(inputDto.getOrderNo(),inputDto.getOrderItem(),"完纳");
            return CommonResult.success("客单完纳成功；" + poLogBuf.toString(), null);
        } catch (OpsException e) {
            log.error("完纳失败", e);
            return CommonResult.failure(e.getMessage());
        } catch (Exception e) {
            log.error("完纳失败", e);
            return CommonResult.failure("失败");
        } finally {
            if (lock) {
                opsRedissonLock.releaseLock(lockKey);
            }
        }
    }


    @Log("调账")
    @PostMapping("/Adjust")
    public CommonResult<String> Adjust(@RequestBody InventoryForAdjustInputDto inputDto) {
        try {
            wmDispatchService.InventoryForAdjust(inputDto);
            log.debug("====调账操作成功=====");
            return CommonResult.success("调账成功");
        } catch (OpsException ex) {
            log.error("====调账操作出错=====");
            return CommonResult.failure(Arrays.deepToString(ex.getArgs()), ex.getMessage());
        }

    }

    @Log("wms调账")
    @PostMapping("/wms/adjust")
    public CommonResult<String> wmsAdjust(@RequestBody InventoryForAdjustInputDto inputDto) {
        try {
            wmDispatchService.InventoryForWMSAdjust(inputDto);
            log.info("====调账操作成功=====");
            return CommonResult.success("调账成功");
        } catch (OpsException ex) {
            log.error("====调账操作出错=====");
            return CommonResult.failure(Arrays.deepToString(ex.getArgs()), ex.getMessage());
        }

    }


    // 调库申请查询Move库存
    @PostMapping("/trans/query/move")
    public CommonResult<List<TransOrderQueryMoveResult>> findMoveForCreateTransOrder(@RequestBody TransOrderQueryMoveParam dto) {
        try {
            List<TransOrderQueryMoveResult> list = opsInventoryService.findAvailableMoveForTransOrder(dto);
            return CommonResult.success("查询成功", list);
        } catch (Exception e) {
            return CommonResult.failure(Collections.emptyList(), "查询失败" + e.getMessage());
        }
    }

    // 调库申请预约Move库存
    @Log("调库申请预约在途库存")
    @PostMapping("/trans/create/move")
    public CommonResult<String> createTransOrderForMove(@RequestBody TransOrderDtoForMove dto) {
        log.info("/order/trans/create/move ==> {}", JSONUtil.toJsonPrettyStr(dto));
        try {
            transOrderService.createTransOrderForMove(dto);
        } catch (Exception e) {
            log.error("{}", e);
            return CommonResult.failure(e.getMessage(), "调库申请失败");
        }
        return CommonResult.success("调库申请成功", null);
    }

    // 调库申请预约在库库存，单条开事务，失败单条回滚
    // 调库申请预约在库库存
    @PostMapping("/trans/create")
    public CommonResult<String> createTransOrder(@RequestBody TransOrderDto dto) {
        Map<String, String> result = new HashMap<>();
        for (TransOrder transOrder : dto.getTransOrderList()) {
            String remark = "成功";
            try {
                transOrderService.createTransOrderOld(transOrder, dto.getUserDto());
            } catch (OpsException e) {
                remark = e.getMessage();
            } catch (Exception e) {
                remark = e.getMessage();
            }
            result.put(transOrder.getTransNo() + "-" + transOrder.getItemNo(), remark);
            log.info(transOrder.getTransNo() + "-" + transOrder.getItemNo() + ':' + remark);
        }
        return CommonResult.success("调库申请成功", JSONUtil.toJsonStr(result));
    }

    /**
     * 整体加事务版，整体回滚
     * @param dto
     * @return
     */
    @PostMapping("/trans/create/transaction")
    public CommonResult<String> createTransOrderTransaction(@RequestBody TransOrderDto dto) {
        try {
            transOrderService.createTransOrderBatch(dto);
        } catch (Exception e) {
            return CommonResult.failure(null, "调库申请失败," + e.getMessage());
        }
        return CommonResult.success("调库申请成功", null);
    }

    /**
     * 1.插入transOrder，2.同仓调库，3.修改transOrder状态，4.返回结果
     * @param dto
     * @return
     */
    @PostMapping("/trans/create/preorder")
    public CommonResult<Map> createTransOrderForPreorderAccount(@RequestBody PreorderAccountTransOrderDto dto) {
        HashMap<String, String> map = new HashMap<>();
        map.put("transNo", dto.getTransNo());
        map.put("itemNo", dto.getItemNo().toString());
        TransOrder transOrder = dto.toTransOrder();
        try {
            transOrderService.insertTransOrderForNormal(transOrder);
            int transQty = transOrderService.createTransOrderForPreorder(transOrder);
            map.put("quantity", transQty + "");
            transOrderService.updateStatus(transOrder.getTransNo(), dto.getItemNo(), OrderTransStatusEnum.DB_FINISHED, transQty);
        } catch (OpsException e) {
            log.error("{}", e);
            map.put("errMsg", e.getMessage());
            try {
                transOrderService.updateStatus(transOrder.getTransNo(), dto.getItemNo(), OrderTransStatusEnum.DB_FAIL, null);
            } catch (OpsException ex) {
                return CommonResult.failure(e.getMessage());
            }
            return CommonResult.failure(e.getMessage());
        }
        return CommonResult.success("调库申请成功", map);
    }


    /**
     * 创建组换单生成do指令
     *
     * @param inputDto
     * @return
     */
    @Log("组换")
    @PostMapping("/producChange/dispatch")
    public CommonResult<String> ProducChange(@RequestBody InventoryForProducChangeDto inputDto) {
        try {
            if (Objects.nonNull(inputDto.getDoTypeEnum())) {
                if (DoTypeEnum.ZHCK.getType().equals(inputDto.getDoTypeEnum().getType())) {
                    wmDispatchService.InventoryForProducChange(inputDto, false);
                } else if (DoTypeEnum.ZHCKOW.getType().equals(inputDto.getDoTypeEnum().getType())) {
                    wmDispatchService.InventoryForProducChange(inputDto, true);
                } else {
                    return CommonResult.failure("组换类别错误:" + inputDto.getDoTypeEnum().getType());
                }
            } else {
                return CommonResult.failure("没有组换类别");
            }
            return CommonResult.success();
        } catch (Exception ex) {
            log.error("{}", ex);
            return CommonResult.failure(ex.getMessage());
        }
    }

    /*
     * 1.wms发运单页面点击"组换无法取消按钮"
     * 2.ops验证状态是否可以取消
     * 3.取消调用wms取消do指令
     * 4.wms取消成功后，ops取消相应指令
     * 5.调用变更状态接口
     */
    @Log("组换状态")
    @PostMapping("/producChange/wmsStatus")
    public CommonResult<String> WmsProducStatus(@RequestParam("orderId") String orderId,
                                                @RequestParam("msg") String msg) {
        CancelForOrderDto inputDto = new CancelForOrderDto(orderId, "1", "1", UserDto.WMS, msg);
        try {
            long cancelId = opsDoService.insertOrderCancel(inputDto);
            wmDispatchService.wmsProducChangeStatus(orderId, msg, cancelId);
            return CommonResult.success();
        } catch (Exception ex) {
            inputDto.setReason(ex.getMessage());
            inputDto.setUserDto(new UserDto("取消失败"));
            opsDoService.insertOrderCancel(inputDto);
            return CommonResult.failure(ex.getMessage());
        }
    }

    @Log("情报占用")
    @PostMapping("/salesInfo")
    public CommonResult<String> salesInfo(@RequestBody InventoryForAdjustDto inputDto) {
        try {
            log.info(JSONUtil.toJsonStr(inputDto));
            wmDispatchService.InventoryForSalesInfoNo(inputDto);
            log.info("====情报预约成功=====");
            return CommonResult.success("情报预约成功");
        } catch (OpsException ex) {
            log.error("====情报预约出错=====");
            return CommonResult.failure(Arrays.deepToString(ex.getArgs()), ex.getMessage());
        } catch (Exception ex) {
            log.info("====发生未知异常=====");
            return CommonResult.failure(ex.getMessage());
        }
    }

    /**
     * @params orderId 出入库指令号(自定义)
     * @params salesInfoNo 情报号
     * @author C12961
     * @date 2022/1/6 16:27
     */
    @Log("情报释放")
    @GetMapping("/salesInfo/undo")
    public CommonResult<String> undoSalesInfo(@RequestParam("salesInfoNo") String salesInfoNo) {
        log.info("========开始营业情报取消:" + salesInfoNo);
        try {
            InventoryForAdjustDto inputDto = new InventoryForAdjustDto();
            inputDto.setSalesInfoNo(salesInfoNo);
            inputDto.setOrderId(salesInfoNo);
            Integer code = wmDispatchService.undoInventoryForSalesInfo(inputDto);
            String info = SalesInfoErrorCodeEnum.getDescByCode(code);
            log.info(info);
            log.info("====情报预约取消成功=====");
            return CommonResult.success(info);
        } catch (OpsException ex) {
            log.error("====情报预约取消出错=====");
            log.info(ex.getMessage());
            return CommonResult.failure(ex.getMessage());
        } catch (Exception ex) {
            log.info("====发生未知异常=====");
            log.info(ex.getMessage());
            return CommonResult.failure(ex.getMessage());
        }
    }

    /**
     * 生成在途数据（按退货数据） 1，生成在途 2，生成RO 3，写入下发物流表
     *
     * @param list
     * @return
     */
    @Log("生成退货在途")
    @PostMapping("/handReturnOrderConfirm")
    public CommonResult handReturnOrderConfirm(@RequestBody List<CreInvMoveForReturnOrderDto> list) {
        try {
            ImpInvoiceEventLog eventLog = new ImpInvoiceEventLog();
            eventLog.setOpType("/order/handReturnOrderConfirm");
            eventLog.setRequestParam(JSON.toJSONString(list));
            wmDispatchService.addImpInvoiceEventLog(eventLog);
            log.info("handReturnOrderConfirm params = {} ", JSON.toJSONString(list));
            roDispatcherService.returnConfirm(list);
            log.info("====生成在途数据成功=====");
            return CommonResult.success("生成在途数据成功");
        } catch (OpsException ex) {
            log.error("====生成在途数据错误=====");
            return CommonResult.failure(Arrays.deepToString(ex.getArgs()), ex.getMessage());
        } catch (Exception ex) {
            log.info("====发生未知异常=====");
            log.error("发生未知异常:{}", ex);
            return CommonResult.failure(ex.getMessage());
        }
    }

    /**
     * 按历史数据生成在途（中间表） 1，生成在途 2，生成RO 3，写入下发物流表
     *
     * @return
     */
    /*
    @PostMapping("/handOldOrderConfirm")
    public CommonResult handOldOrderConfirm(@RequestParam("inventoryStatusEnum") String inventoryStatusEnum) {
        try {
            ImpInvoiceEventLog eventLog = new ImpInvoiceEventLog();
            eventLog.setOpType("/order/handOldOrderConfirm");
            eventLog.setRequestParam(inventoryStatusEnum);
            wmDispatchService.addImpInvoiceEventLog(eventLog);
            log.info("handOldOrderConfirm params = {} ", inventoryStatusEnum);
            wmDispatchService.handOldOrderConfirm(inventoryStatusEnum);
            return CommonResult.success("生成在途数据成功");
        } catch (OpsException ex) {
            log.error("====生成在途数据错误=====");
            return CommonResult.failure(Arrays.deepToString(ex.getArgs()), ex.getMessage());
        } catch (Exception ex) {
            log.info("====发生未知异常=====");
            log.info(ex.getMessage());
            return CommonResult.failure(ex.getMessage());
        }
    }
*/

    /**
     * 纳期客户清单
     *
     * @return
     */
    @GetMapping("/listDlvCustomers")
    public CommonResult<List<String>> listDlvCustomers() {
        try {
            ImpInvoiceEventLog eventLog = new ImpInvoiceEventLog();
            eventLog.setOpType("/order/listDlvCustomers");
            eventLog.setRequestParam("");
            wmDispatchService.addImpInvoiceEventLog(eventLog);
            List<String> list = wmDispatchService.listDlvCustomers();
            return CommonResult.success(list);
        } catch (OpsException ex) {
            log.error("====生成在途数据错误=====");
            return CommonResult.failure(null);
        } catch (Exception ex) {
            log.info("====发生未知异常=====");
            log.info(ex.getMessage());
            return CommonResult.failure(ex.getMessage());
        }
    }

    @Log("入库收货数量调整处理")
    @GetMapping("/handOpsRoQtyAdjust")
    public CommonResult<String> handOpsRoQtyAdjust(@RequestParam Integer flag) {
        try {
            wmDispatchService.handOpsRoQtyAdjust(flag);
            return CommonResult.success("处理入库数量调整成功");
        } catch (OpsException ex) {
            log.error("====处理入库数量调整错误=====");
            return CommonResult.failure(Arrays.deepToString(ex.getArgs()), ex.getMessage());
        } catch (Exception ex) {
            log.info("====发生未知异常=====");
            log.info(ex.getMessage());
            return CommonResult.failure(ex.getMessage());
        }
    }

}
