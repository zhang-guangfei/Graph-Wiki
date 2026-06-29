package com.sales.ops.web.controller.order;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.github.pagehelper.PageInfo;
import com.sales.ops.common.errorEnum.OrderCancelCodeEnum;
import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.common.until.AssertUtil;
import com.sales.ops.common.until.OPSRedisUtils;
import com.sales.ops.db.dao.OpsOrderUpdateLogMapper;
import com.sales.ops.db.entity.*;
import com.sales.ops.dto.order.CancelForOrderDto;
import com.sales.ops.dto.order.CancelInputForOrderDto;
import com.sales.ops.dto.order.OpsOrderModifyDto;
import com.sales.ops.dto.order.ReceiveCondition;
import com.sales.ops.dto.util.CommonResult;
import com.sales.ops.dto.util.PageModel;
import com.sales.ops.dto.util.UserDto;
import com.sales.ops.enums.DlvSiteEnum;
import com.sales.ops.event.publisher.enums.EventSourceEnum;
import com.sales.ops.redisson.OpsRedissonLock;
import com.sales.ops.service.order.*;
import com.sales.ops.service.order.state.OrderStateService;
import com.sales.ops.serviceimpl.event.v3.CustomerEventPublisher;
import com.sales.ops.webutil.BaseController;
import com.smc.smccloud.core.exception.BusinessException;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.model.constants.Constants;
import com.smc.smccloud.model.DataCodeVO;
import com.smc.smccloud.service.DictDataServiceFeignApi;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author C12961
 * @date 2021/11/25 16:30
 */
@CrossOrigin
@RestController
@RequestMapping("/order")
@Slf4j
public class ReceiveOrderController extends BaseController {
    @Autowired
    private BaseCustomerOrderService baseCustomerOrderService;
    @Autowired
    private OpsCustomerOrderService opsCustomerOrderService;
    @Resource
    OpsOrderUpdateLogMapper opsOrderUpdateLogMapper;
    @Autowired
    private CancelCustomerOrderService cancelCustomerOrderService;
    @Autowired
    private ModifyCustomerOrderService modifyCustomerOrderService;
    @Autowired
    private OpsRedissonLock opsRedissonLock;
    @Autowired
    private OrderStateService orderStateService;
    @Resource
    private DictDataServiceFeignApi dictDataServiceFeignApi;
    @Autowired
    private CustomerEventPublisher customerEventPublisher;
    @Autowired
    private OPSRedisUtils opsRedisUtils;

    /**
     * 前端接单查询界面使用
     */
    @PostMapping("/rcvorder/rcvView")
    public CommonResult searchRcvView(@RequestBody PageModel<ReceiveCondition> pageModel) {
        try {
            PageInfo<RcvView> pageInfo = baseCustomerOrderService.searchByPage(pageModel);
            return CommonResult.success(pageInfo);
        } catch (OpsException ex) {
            return CommonResult.failure(ex.getArgs(), ex.getErrorMessage());
        } catch (Exception ex) {
            return CommonResult.failure(ex, ex.getMessage());
        }
    }

    /**
     * @description 前端订单修改页面调用
     * @author C12961
     * @date 2023/2/20 14:07
     */
    @GetMapping("/rcvdetail")
    public CommonResult findOrder(@RequestParam String orderFno) {
        try {
            Rcvdetail rcvDetail = baseCustomerOrderService.findRcvDetailByFno(orderFno);
            return CommonResult.success(rcvDetail);
        } catch (Exception e) {
            e.printStackTrace();
            return CommonResult.failure(e.getMessage());
        }
    }


    /**
     * bugid:12654 加锁 c14717 20231121
     * @param calcelDto
     * @return
     */
    @PostMapping("/rcvorder/cancel")
    public CommonResult cancelRcvOrder(@RequestBody CancelInputForOrderDto calcelDto) {
        boolean lock = false;
        String  key = "ops:order:key:" + calcelDto.getCancelForOrderDto().getOrderId();
        try {
            lock = opsRedissonLock.addLock(key);
            log.info("/order/rcvorder/cancel：{}", JSONUtil.toJsonPrettyStr(calcelDto));
            CancelForOrderDto dto = calcelDto.getCancelForOrderDto();
            Rcvdetail rcvdetail = baseCustomerOrderService.findRcvDetailByNo(dto.getOrderId(), dto.getOrderItem());
            Rcvmaster rcvmaster = baseCustomerOrderService.findRcvMaster(dto.getOrderId());
            UserDto userDto = dto.getUserDto();
            if (Objects.isNull(userDto) || StringUtils.isBlank(userDto.getUserName())) {
                dto.setUserDto(getUserDto());
            }
            OrderCancelCodeEnum result = cancelCustomerOrderService.cancelRcvOrder(calcelDto);
            if (result == OrderCancelCodeEnum.SUCCESS_CANCEL ) {
                log.info("/order/rcvorder/cancel：订单取消成功");
                orderStateService.sendOrderStateForCancelCustomerOrder(dto, rcvdetail, rcvmaster);
                return CommonResult.success(result.getDesc());
            }
            if (result == OrderCancelCodeEnum.SUCCESS_CANCEL_AND_LOW_RISK) {
                log.info("/order/rcvorder/cancel/auto：订单自动取消成功，但是因为是低风险订单,所以采购单没有删除");
                orderStateService.sendOrderStateForCancelCustomerOrder(dto, rcvdetail, rcvmaster);
                return CommonResult.success(result.getDesc());
            }
            if (result == OrderCancelCodeEnum.SUCCESS_CANCEL_AND_FAIL_TRANS) {
                log.info("/order/rcvorder/cancel/auto：订单自动取消成功，没有生成调库计划");
                orderStateService.sendOrderStateForCancelCustomerOrder(dto, rcvdetail, rcvmaster);
                return CommonResult.success(result.getDesc());
            }
            if (result == OrderCancelCodeEnum.WM_NOT_ALLOWED) {// 物流不允许取消
                log.info("/order/rcvorder/cancel：物流不允许取消");
                return CommonResult.failure(201, OrderCancelCodeEnum.WM_NOT_ALLOWED.getDesc());
            }
            if (result == OrderCancelCodeEnum.STATUS_NOT_ALLOWED) {// 状态不允许取消
                log.info("/order/rcvorder/cancel：状态不允许取消");
                return CommonResult.failure(201, OrderCancelCodeEnum.STATUS_NOT_ALLOWED.getDesc());
            }
            log.info("/order/rcvorder/cancel：失败{}", result);
            return CommonResult.failure(result);
        } catch (Exception ex) {
            log.info("/order/rcvorder/cancel：失败{}", ex);
            return CommonResult.failure(ex.getMessage());
        }finally {
            // 释放锁
            if (lock) {
                opsRedissonLock.releaseLock(key);
            }
        }
    }

    /**
     * bugid:12654 加锁 c14717 20231121
     * @param cancelForOrderDto
     * @return
     */
    @PostMapping("/rcvorder/cancel/auto")
    public CommonResult autoCancelRcvOrder(@RequestBody CancelForOrderDto cancelForOrderDto) {
        boolean lock = false;
        String key = "ops:order:key:" + cancelForOrderDto.getOrderId();
        try {
            lock = opsRedissonLock.addLock(key);
            log.info("/order/rcvorder/cancel/auto：{}", JSONUtil.toJsonPrettyStr(cancelForOrderDto));
            // 如果rcv表里查不到，抛出异常
            Rcvdetail rcvDetail = baseCustomerOrderService.findRcvDetailByNo(cancelForOrderDto.getOrderId(), cancelForOrderDto.getOrderItem());
            AssertUtil.notNull(rcvDetail, OrderCancelCodeEnum.NOT_RECEIVED.getDesc());
            Rcvmaster rcvmaster = baseCustomerOrderService.findRcvMaster(cancelForOrderDto.getOrderId());
            // 初始化
            cancelForOrderDto.setOrderFno(rcvDetail.getRorderFno());
            cancelForOrderDto.setOrigin("自动删单");
            OrderCancelCodeEnum result = cancelCustomerOrderService.autoCancelRcvOrder(cancelForOrderDto);
            if (result == OrderCancelCodeEnum.AUTO_SUCCESS_CANCEL) {
                log.info("/order/rcvorder/cancel/auto：订单自动取消成功");
                orderStateService.sendOrderStateForCancelCustomerOrder(cancelForOrderDto, rcvDetail, rcvmaster);
                return CommonResult.success(OrderCancelCodeEnum.AUTO_SUCCESS_CANCEL.getDesc());
            }
            if (result == OrderCancelCodeEnum.SUCCESS_CANCEL_AND_LOW_RISK) {
                log.info("/order/rcvorder/cancel/auto：订单自动取消成功，但是因为是低风险订单,所以采购单没有删除");
                orderStateService.sendOrderStateForCancelCustomerOrder(cancelForOrderDto, rcvDetail, rcvmaster);
                return CommonResult.success(OrderCancelCodeEnum.SUCCESS_CANCEL_AND_LOW_RISK.getDesc());
            }
            if (result == OrderCancelCodeEnum.SUCCESS_CANCEL_AND_FAIL_TRANS) {
                log.info("/order/rcvorder/cancel/auto：订单自动取消成功，但是因为采购单状态原因，没有生成调库计划");
                orderStateService.sendOrderStateForCancelCustomerOrder(cancelForOrderDto, rcvDetail, rcvmaster);
                return CommonResult.success(OrderCancelCodeEnum.SUCCESS_CANCEL_AND_FAIL_TRANS.getDesc());
            }
            if (result == OrderCancelCodeEnum.PURCHASE_NOT_ALLOWED) {
                log.info("/order/rcvorder/cancel/auto：" + OrderCancelCodeEnum.PURCHASE_NOT_ALLOWED.getDesc());
                return CommonResult.failure(201, OrderCancelCodeEnum.PURCHASE_NOT_ALLOWED.getDesc());
            }
            if (result == OrderCancelCodeEnum.STATUS_NOT_ALLOWED) {
                log.info("/order/rcvorder/cancel/auto：" + OrderCancelCodeEnum.STATUS_NOT_ALLOWED.getDesc());
                return CommonResult.failure(202, OrderCancelCodeEnum.STATUS_NOT_ALLOWED.getDesc());
            }
            if (result == OrderCancelCodeEnum.WM_NOT_ALLOWED) {
                log.info("/order/rcvorder/cancel/auto：" + OrderCancelCodeEnum.WM_NOT_ALLOWED.getDesc());
                return CommonResult.failure(203, OrderCancelCodeEnum.WM_NOT_ALLOWED.getDesc());
            }
            return CommonResult.failure();
        } catch (OpsException ex) {
            log.info("/order/rcvorder/cancel/auto OpsException：{}", ex);
            return CommonResult.failure(500, "取消异常：" + ex.getMessage());
        } catch (Exception ex) {
            log.info("/order/rcvorder/cancel/auto Exception： {}", ex);
            return CommonResult.failure(500, "取消异常：" + ex.getMessage());
        }finally {
            // 释放锁
            if (lock) {
                opsRedissonLock.releaseLock(key);
            }
        }
    }

    @PostMapping("/rcvorder/cancel/enable")
    public CommonResult enableCancel(@RequestBody Rcvdetail rcvdetail) {
        boolean result = cancelCustomerOrderService.enableCancel(rcvdetail);
        return CommonResult.success("查询成功", result);
    }

    /**
     * bugid:12654 加锁 c14717 20231121
     * @param dto
     * @return
     */
    @PostMapping("/rcvorder/modify/handle")
    public CommonResult modifyRcvOrder(@RequestBody OpsOrderModifyDto dto) {
        boolean lock = false;
        //分布式锁
        String lockKey = "ops:order:key:" + dto.getOrderId();
        Map<String, String> failureMap = new HashMap<>();
        Map<String, String> successMap = new HashMap<>();
        Map<String, String> resultMap = new HashMap<>();
        OpsOrderUpdateLog logInfo = new OpsOrderUpdateLog();
        //重复提交key
        String key = null;
        if (dto.isMaster()) {
            key = "ops:rcvorder:modify:" + dto.getOrderId();
            dto.setOrderItem(null);
        } else {
            key = "ops:rcvorder:modify:" + dto.getOrderId() + "-" + dto.getOrderItem();
        }
        try {
            Boolean exist = opsRedisUtils.hasKey(key);
            if (exist != null && exist) {
                Object redisObj = opsRedisUtils.get(key);
                if (redisObj != null && redisObj instanceof OpsOrderModifyDto) {
                    OpsOrderModifyDto obj = (OpsOrderModifyDto) redisObj;
                    if (dto.equals(obj)) {
                        //17004 重复提交了，直接跳过 返回成功
                        log.error("订单修改重复提交了，直接跳过: {}", JSONUtil.toJsonStr(dto));
                        return CommonResult.success("变更成功", successMap);
                    }
                }
            }
            // 记入redis，期限30分
            opsRedisUtils.setKeyAndTimeout(key, dto, 30, TimeUnit.MINUTES);

            lock = opsRedissonLock.addLock(lockKey);
            logInfo.setParams(JSONUtil.toJsonStr(dto));
            logInfo.setOrderid(dto.getOrderId());
            logInfo.setOrderItem(dto.getOrderItem());
            logInfo.setMaster(dto.isMaster());
            if (dto.isMaster()) {
                Rcvmaster rcvMaster = baseCustomerOrderService.findRcvMaster(dto.getOrderId());
                // 变更出库方式和集约方式
                if (ObjectUtil.isNotNull(rcvMaster)) {
                    if (dto.getDlvEntire() != null) {
                        try {
                            modifyCustomerOrderService.modifyDlvEntireAndType(dto.getOrderId(), dto.getDlvEntire(), null, dto.getUserDto());
                            successMap.put("变更出库方式", "成功");
                        } catch (OpsException e) {
                            e.printStackTrace();
                            failureMap.put("变更出库方式", "失败：" + StringUtils.substring(e.getMessage(), 0, 200));
                        } catch (Exception e) {
                            e.printStackTrace();
                            failureMap.put("变更出库方式", "失败：发生系统异常，" + StringUtils.substring(e.getMessage(), 0, 200));
                        }
                    }
                    if (dto.getDlvType() != null) {
                        try {
                            modifyCustomerOrderService.modifyDlvEntireAndType(dto.getOrderId(), null, dto.getDlvType(), dto.getUserDto());
                            successMap.put("变更集约方式", "成功");
                        } catch (OpsException e) {
                            e.printStackTrace();
                            failureMap.put("变更集约方式", "失败：" + StringUtils.substring(e.getMessage(), 0, 200));
                        } catch (Exception e) {
                            e.printStackTrace();
                            failureMap.put("变更集约方式", "失败：发生系统异常，" + StringUtils.substring(e.getMessage(), 0, 200));
                        }
                    }
                    if (dto.getPurchaseNo() != null) {
                        try {
                            modifyCustomerOrderService.modifyPurchaseNo(dto.getOrderId(), dto.getPurchaseNo(), dto.getUserDto());
                            successMap.put("变po号", "成功");
                        } catch (OpsException e) {
                            e.printStackTrace();
                            failureMap.put("变po号", "失败：" + StringUtils.substring(e.getMessage(), 0, 200));
                        } catch (Exception e) {
                            e.printStackTrace();
                            failureMap.put("变po号", "失败：发生系统异常，" + StringUtils.substring(e.getMessage(), 0, 200));
                        }
                    }
                    List<Rcvdetail> rcvDetailList = baseCustomerOrderService.findRcvDetailList(rcvMaster.getRorderNo());
                    for (Rcvdetail rcvdetail : rcvDetailList) {
                        customerEventPublisher.customerOrderEvent(EventSourceEnum.CUSTOMER_ORDER_MODIFY_DELIVERY_CONFIG, rcvdetail);
                    }
                }
            }
            else {
                if (dto.getCproductNo() != null) {
                    try {
                        modifyCustomerOrderService.modifyCproductNo(dto.getOrderId(), dto.getOrderItem(), dto.getCproductNo(), dto.getUserDto());
                        successMap.put("变更物料号", "成功");
                    } catch (OpsException e) {
                        e.printStackTrace();
                        failureMap.put("变更物料号", "失败：" + StringUtils.substring(e.getMessage(), 0, 200));
                    } catch (Exception e) {
                        e.printStackTrace();
                        failureMap.put("变更物料号", "失败：发生系统异常，" + StringUtils.substring(e.getMessage(), 0, 200));
                    }
                }
                // 变更客户交货期、物流交货期
                if (dto.isUpdateDate()) {
                    try {
                        logInfo.setDlvDate(dto.getDlvDate());
                        logInfo.setWmsDlvData(dto.getWmsDlvDate());
                        //dlvDate修改标识，1.不做限制，2.entryTime + 180天，3.now + 7自动化工作日
                        int dlvDateType = modifyCustomerOrderService.modifyDlvDate(dto.getOrderId(), dto.getOrderItem(), dto.getDlvDate(), dto.getWmsDlvDate(), dto.getUserDto());
                        if (ObjectUtil.isNotNull(dto.getDlvDate())) {
                            Rcvdetail rcvdetail = baseCustomerOrderService.findRcvDetailByNo(dto.getOrderId(), dto.getOrderItem());
                            orderStateService.sendOrderStateForUpdateRcvDlvDate(rcvdetail, dto.getUserDto().getUserName());
                        }
                        successMap.put("变更交货期", "成功");
                        // 若系统日期大于或等于入库日期加 180 天，且订单未曾修改过，则允许一次性修改，计算最晚可修改的客户货期为系统日期+7个自动化工作日，确认修改后记录修改标识
                        // 只有满足规则3的情况才记录修改标识
                        if (dlvDateType == 3) {
                            logInfo.setChangeFlag(true);
                        }
                    } catch (OpsException e) {
                        e.printStackTrace();
                        failureMap.put("变更交货期", "失败：" + StringUtils.substring(e.getMessage(), 0, 200));
                    } catch (Exception e) {
                        e.printStackTrace();
                        failureMap.put("变更交货期", "失败：发生系统异常，" + StringUtils.substring(e.getMessage(), 0, 200));
                    }
                }
                // 变更承运商
                if (StringUtils.isNotBlank(dto.getCarrier())) {
                    try {
                        // 验证承运商
                        ResultVo<List<DataCodeVO>> dataCodes = dictDataServiceFeignApi.getDataCodes(Constants.carrier_1024);
                        if (!dataCodes.isSuccess() || dataCodes.getData() == null) {
                            throw new BusinessException("获取承运商列表失败");
                        }
                        List<DataCodeVO> data = dataCodes.getData();
                        List<String> collect = data.stream().map(DataCodeVO::getCode).collect(Collectors.toList());
                        if (!collect.contains(dto.getCarrier())) {
                            throw new BusinessException(dto.getCarrier()+"承运商不存在");
                        }
                        modifyCustomerOrderService.modifyCarrier(dto.getOrderId(), dto.getOrderItem(), dto.getCarrier(), dto.getDlvSpecial(), dto.getUserDto());
                        successMap.put("变更承运商", "成功");
                    } catch (OpsException e) {
                        e.printStackTrace();
                        failureMap.put("变更承运商", "失败：" + StringUtils.substring(e.getMessage(), 0, 200));
                    } catch (Exception e) {
                        e.printStackTrace();
                        failureMap.put("变更承运商", "失败：发生系统异常，" + StringUtils.substring(e.getMessage(), 0, 200));
                    }
                }
                // 变更发货地址和联系人
                if (dto.isUpdateAddress()) {
                    //如果地址为自提，则发货方式也改为自提
                    String dlvaddress = dto.getAddress().getDlvaddress();
                    if ("客户自提".equals(dlvaddress) && StringUtils.isBlank(dto.getAddress().getDlvflag())){
                        dto.getAddress().setDlvflag(DlvSiteEnum.SELF.getCode().toString());
                    }
                    boolean isUpdateDlvFlag = StringUtils.isNotBlank(dto.getAddress().getDlvflag());
                    try {
                        logInfo.setDlvAddress(JSONUtil.toJsonStr(dto.getAddress()));
                        modifyCustomerOrderService.modifyDlvAddress(dto.getOrderId(), dto.getOrderItem(), dto.getAddress(), dto.getDlvSpecial(), dto.getUserDto());
                        successMap.put("变更发货地址", "成功");
                        if (isUpdateDlvFlag) {
                            // 变更发货自提
                            logInfo.setDlvSelf(true);
                            successMap.put("变更交货方式", "成功");
                        }
                    } catch (OpsException e) {
                        e.printStackTrace();
                        failureMap.put("变更发货地址", "失败：" + StringUtils.substring(e.getMessage(), 0, 200));
                        if (isUpdateDlvFlag) {
                            failureMap.put("变更交货方式", "失败：" + StringUtils.substring(e.getMessage(), 0, 200));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        failureMap.put("变更发货地址", "失败：发生系统异常，" + StringUtils.substring(e.getMessage(), 0, 200));
                        if (isUpdateDlvFlag) {
                            failureMap.put("变更交货方式", "失败：" + StringUtils.substring(e.getMessage(), 0, 200));
                        }
                    }
                }
                // 变更特发
                if (dto.getDlvSpecial() != null) {
                    try {
                        logInfo.setDlvSpecial(dto.getDlvSpecial());
                        modifyCustomerOrderService.modifyDlvSpecial(dto.getOrderId(), dto.getOrderItem(), dto.getDlvSpecial(), dto.getUserDto());
                        successMap.put("变更特发", "成功");
                    } catch (OpsException e) {
                        e.printStackTrace();
                        failureMap.put("变更特发", "失败：" + StringUtils.substring(e.getMessage(), 0, 200));
                    } catch (Exception e) {
                        e.printStackTrace();
                        failureMap.put("变更特发", "失败：发生系统异常，" + StringUtils.substring(e.getMessage(), 0, 200));
                    }
                }

                // 变更拆分子型号发货
                if (dto.getDlvSplit() != null) {
                    logInfo.setDlvSplit(dto.getDlvSplit());
                    if (dto.getDlvSplit()) {
                        try {
                            modifyCustomerOrderService.modifyDlvSplit(dto.getOrderId(), dto.getOrderItem(), dto.getUserDto());
                            successMap.put("拆分子型号", "成功");
                        } catch (OpsException e) {
                            e.printStackTrace();
                            failureMap.put("拆分子型号", "失败：" + StringUtils.substring(e.getMessage(), 0, 200));
                        } catch (Exception e) {
                            e.printStackTrace();
                            failureMap.put("拆分子型号", "失败：发生系统异常，" + StringUtils.substring(e.getMessage(), 0, 200));
                        }
                    }
                }
                customerEventPublisher.customerOrderEvent(EventSourceEnum.CUSTOMER_ORDER_MODIFY_DELIVERY_CONFIG, dto.getOrderId(), dto.getOrderItem());
            }

            // 处理完，整理变更结果
            // 全成功
            if (failureMap.size() == 0) {
                resultMap.putAll(successMap);
                logInfo.setResult(JSONUtil.toJsonStr(resultMap));
                opsOrderUpdateLogMapper.insertSelective(logInfo);
                return CommonResult.success("变更成功", successMap);
            }
            resultMap.putAll(failureMap);
            resultMap.putAll(successMap);
            logInfo.setResult(JSONUtil.toJsonStr(resultMap));
            opsOrderUpdateLogMapper.insertSelective(logInfo);
            return CommonResult.failure(resultMap, "变更失败");
        } catch (Exception e) {
            e.printStackTrace();
            resultMap.put("系统异常", StringUtils.substring(e.getMessage(), 0, 200));
            logInfo.setResult(JSONUtil.toJsonStr(resultMap));
            opsOrderUpdateLogMapper.insertSelective(logInfo);
            return CommonResult.failure(resultMap, "变更失败");
        } finally {
            // 释放锁
            if (lock) {
                opsRedissonLock.releaseLock(lockKey);
            }
        }
    }

    @GetMapping("/rcvorder/modify/dlvdate/limit")
    public CommonResult<Date> getModifyDlvDateLimit(@RequestParam String orderNo, @RequestParam Integer orderItem) {
        try {
            CommonResult<Date> result = modifyCustomerOrderService.calMaxUpdateDlvDateLimit2(orderNo, orderItem);
            List<Integer> success = Arrays.asList(1, 2, 3);
            List<Integer> failure = Arrays.asList(4,5);
            if (success.contains(result.getCode())) {
                return CommonResult.success("查询成功", result.getData());
            } else if (failure.contains(result.getCode())) {
                return CommonResult.failure("查询失败："+result.getMessage());
            }
            return CommonResult.failure("查询失败："+result.getMessage());
        } catch (Exception e) {
            log.error("订单修改获取交货期限制失败",e);
            return CommonResult.failure(e.getMessage());
        }
    }






    @PostMapping("/rcvorder/credit/interceptFlag")
    public CommonResult getCreditInterceptFlagByOrderFno(@RequestBody List<String> orderFnoList) {
        try {
            Map<String, String> result = opsCustomerOrderService.checkCreditList(orderFnoList);
            for (Map.Entry<String, String> entry : result.entrySet()) {
                String orderFno = entry.getKey();
                String intercept = entry.getValue();
                String splitChar = "-";
                if (orderFno.contains(splitChar)) {
                    String[] orderFnoArr = orderFno.split(splitChar);
                    if ("1".equals(intercept)) {
                        customerEventPublisher.customerOrderEvent(EventSourceEnum.WAREHOUSE_DELIVERY_CREDIT_INTERCEPT, orderFnoArr[0], Integer.parseInt(orderFnoArr[1]));
                    } else {
                        customerEventPublisher.customerOrderEvent(EventSourceEnum.WAREHOUSE_DELIVERY_CREDIT_RELEASE, orderFnoArr[0], Integer.parseInt(orderFnoArr[1]));
                    }
                } else {
                    log.error("订单号{}无法解析", orderFno);
                }
            }
            return CommonResult.success("查询成功", result);
        } catch (OpsException e) {
            return CommonResult.failure(e);
        }
    }

    //查询rcv中信用拦截状态的订单，并发送事件
    @GetMapping("/rcvorder/credit")
    public CommonResult checkCredit() {
        try {
            // 查询OPS中被拦截的订单
            List<String> rorderFnoList = baseCustomerOrderService.findCreditInterceptedOrderFno();
            Map<String, String> result = opsCustomerOrderService.checkCreditList(rorderFnoList);
            for (Map.Entry<String, String> entry : result.entrySet()) {
                String orderFno = entry.getKey();
                String intercept = entry.getValue();
                String splitChar = "-";
                if (orderFno.contains(splitChar)) {
                    String[] orderFnoArr = orderFno.split(splitChar);
                    if ("1".equals(intercept)) {
                        customerEventPublisher.customerOrderEvent(EventSourceEnum.WAREHOUSE_DELIVERY_CREDIT_INTERCEPT, orderFnoArr[0], Integer.parseInt(orderFnoArr[1]));
                    } else {
                        customerEventPublisher.customerOrderEvent(EventSourceEnum.WAREHOUSE_DELIVERY_CREDIT_RELEASE, orderFnoArr[0], Integer.parseInt(orderFnoArr[1]));
                    }
                } else {
                    log.error("订单号{}无法解析", orderFno);
                }
            }
            return CommonResult.success("查询条数" + rorderFnoList.size(), rorderFnoList);
        } catch (Exception e) {
            return CommonResult.failure(e.getMessage());
        }
    }


}
