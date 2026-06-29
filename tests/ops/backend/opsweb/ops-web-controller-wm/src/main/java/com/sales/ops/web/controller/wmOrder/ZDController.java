package com.sales.ops.web.controller.wmOrder;

import cn.hutool.json.JSONUtil;
import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.common.until.DateUtil;
import com.sales.ops.db.dao.BindataMapper;
import com.sales.ops.db.dao.OpsOrderUpdateLogMapper;
import com.sales.ops.db.entity.*;
import com.sales.ops.dto.purchase.PurchaseInfoForCancel;
import com.sales.ops.dto.replacement.NotifyShipmentCondition;
import com.sales.ops.dto.util.CommonResult;
import com.sales.ops.dto.zd.*;
import com.sales.ops.enums.OrderSplitTypeEnum;
import com.sales.ops.redisson.OpsRedissonLock;
import com.sales.ops.service.inventory.WmRouterOrderService;
import com.sales.ops.service.order.CancelCustomerOrderService;
import com.sales.ops.service.order.CreateTransferPlanService;
import com.sales.ops.service.wmOrder.DoPcoLogicCenterService;
import com.sales.ops.service.wmOrder.ZDService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author ：C14717
 * @version: 1.0$
 * @description： 转定处理
 * @date ：Created in 2023/5/12 10:31
 */
@CrossOrigin
@RestController
@RequestMapping("/zd")
public class ZDController {
    private static Logger log = LoggerFactory.getLogger(ZDController.class);
    @Autowired
    private ZDService zdService;
    @Autowired
    private OpsRedissonLock opsRedissonLock;
    @Autowired
    private OpsOrderUpdateLogMapper opsOrderUpdateLogMapper;
    @Autowired
    private BindataMapper bindataMapper;
    @Autowired
    private CancelCustomerOrderService cancelCustomerOrderService;
    @Autowired
    private WmRouterOrderService wmRouterOrderService;
    @Autowired
    private CreateTransferPlanService createTransferPlanService;

    @Autowired
    private DoPcoLogicCenterService doPcoLogicCenterService;


    /**
     * 是否部分收货 用于调库计划
     * bugid:14473 c14717 20240719
     * @return
     */
    @RequestMapping(value = "/getRoPartStatus", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult<Boolean> getRoPartStatus(@RequestBody PurchaseInfoForCancel param) {
        try {
            boolean roPartStatus = zdService.getRoPartStatus(param);
            return CommonResult.success(roPartStatus);
        } catch (Exception e) {
            return CommonResult.failure(e.getMessage());
        }
    }

    //获取订单与库存的关系关系，返回转定目标
    @PostMapping("/showItem")
    public CommonResult<List<ZDPageShowOrderBindInvDto>> showItem(@RequestBody ZDFindItemInvDto orderStatus) {
        CommonResult<List<ZDPageShowOrderBindInvDto>> commonResult = null;
        List<ZDPageShowOrderBindInvDto> outPutZD = new ArrayList<ZDPageShowOrderBindInvDto>();
        try {
            boolean assModelFlag = OrderSplitTypeEnum.ASSModelNo.getSplitType().equals(orderStatus.getSplitType());
            doPcoLogicCenterService.showItemInvZD(orderStatus.getOrderId(),orderStatus.getOrderItem(),orderStatus.getSplitNo(),
                    orderStatus.getPcoItem(),assModelFlag,outPutZD);
            commonResult = CommonResult.success(outPutZD);
        } catch (OpsException e) {
            commonResult = CommonResult.failure(e.getMessage());
        }catch (Exception e) {
            log.error("转定加载关联关系",e);
            commonResult = CommonResult.failure("可转库存加载异常");
        }

        return commonResult;
    }

    //获取订单可用库存
    @PostMapping("/showInvByItemQty")
    public CommonResult<List<ZDInventoryInfoDTO>> showInvByItemQty(@RequestBody ZDfindInvDto param) {
        CommonResult<List<ZDInventoryInfoDTO>> commonResult = null;
        List<ZDInventoryInfoDTO> out = new ArrayList<ZDInventoryInfoDTO>();
        try {
            zdService.showInvByItemQty(param.getOrderId(),param.getOrderItem(),param.getQty(),param.getModelNo(),out);
            commonResult = CommonResult.success(out);
        } catch (OpsException e) {
            commonResult = CommonResult.failure(e.getMessage());
        }catch (Exception e){
            log.error("加载可用库存",e);
            commonResult = CommonResult.failure("可用库存加载异常");
        }
        return commonResult;
    }

    //获取订单可用库存 订单还原用
    @PostMapping("/showInvByItemQtyReset")
    public CommonResult<List<ZDInventoryInfoDTO>> showInvByItemQtyReset(@RequestBody ZDfindInvDto param) {
        CommonResult<List<ZDInventoryInfoDTO>> commonResult = null;
        List<ZDInventoryInfoDTO> out = new ArrayList<ZDInventoryInfoDTO>();
        try {
            zdService.showInvByItemQty(param.getOrderId(),param.getOrderItem(),param.getQty(),param.getModelNo(),out);
            for (ZDInventoryInfoDTO dto : out) {
                dto.setDk("否");
                BindataExample example = new BindataExample();
                example.createCriteria().andDelflagEqualTo((short) 0).andModelnoEqualTo(param.getModelNo())
                        .andWarehouseCodeEqualTo(dto.getWarehouseCode()).andStocktypeEqualTo(1);
                long isBin = bindataMapper.countByExample(example);
                if (isBin > 0){
                    dto.setDk("是");
                }
            }
            commonResult = CommonResult.success(out);
        } catch (OpsException e) {
            commonResult = CommonResult.failure(e.getMessage());
        }catch (Exception e){
            log.error("订单还原加载可用库存",e);
            commonResult = CommonResult.failure("可用库存加载异常");
        }
        return commonResult;
    }

    @PostMapping("/handleZDToPo")
    public CommonResult<String> handleZDToPo(@RequestBody ZDHandToPoParamDto param){
        //定义变量，初始化数据
        boolean lock = false;
        String key = "ops:order:key:" + param.getOrderId();
        CommonResult<String> commonResult = null;
        // 返回页面信息
        StringBuilder returnPageMsg = new StringBuilder();
        returnPageMsg.append("转定采购：");
        // 持久化日志初始化
        StringBuilder updateLog = new StringBuilder();
        updateLog.append("转定采购：");
        updateLog.append(param.getUserName());
        updateLog.append(";");
        try {
            // 锁
            lock = opsRedissonLock.addLock(key);
            zdService.handleOrderBindPOZD(param);
            // 持久化日志
            updateLog.append("成功;");
            updateLog.append(param.getResultLog());
            // 返回标识
            commonResult = CommonResult.success();
            // 返回信息
            returnPageMsg.append("成功;");
        } catch (OpsException e) {
            log.error(param.getOrderId(),e);
            // 持久化日志
            updateLog.append("失败;");
            updateLog.append(e.getMessage());
            updateLog.append(param.getResultLog());
            // 返回标识
            commonResult = CommonResult.failure(e.getMessage());
            // 返回页面信息
            returnPageMsg.append("失败:");
            returnPageMsg.append(e.getMessage());
            returnPageMsg.append(";");
        }
        catch (Exception e) {
            log.error(param.getOrderId(),e);
            // 持久化日志
            updateLog.append("失败-系统异常;");
            updateLog.append(param.getResultLog());
            // 返回标识
            commonResult = CommonResult.failure("转定失败");
            // 返回页面信息
            returnPageMsg.append("失败：失败-系统异常;");
        }finally {
            // 释放锁
            if (lock) {
                opsRedissonLock.releaseLock(key);
            }
        }
        //4.记录日志 持久化日志信息
        OpsOrderUpdateLog log = new OpsOrderUpdateLog();
        log.setOrderid(param.getOrderId());
        log.setOrderItem(Integer.parseInt(param.getOrderItem()));
        log.setParams(JSONUtil.toJsonStr(param));
        log.setResult(updateLog.toString());
        log.setCreateTime(DateUtil.getNow());
        opsOrderUpdateLogMapper.insertSelective(log);
        //返回页面信息
        commonResult.setMessage(returnPageMsg.toString());
        return commonResult;
    }

    /**
     * bugid:12654 加锁 c14717 20231121
     * @param param
     * @return
     */
    //转定绑定新的关联关系
    @PostMapping("/handleZD")
    public CommonResult<String> handleZD(@RequestBody ZDPageShowOrderBindInvDto param) {
        //return CommonResult.failure("功能没开放");
        //定义变量，初始化数据
        boolean lock = false;
        String key = "ops:order:key:" + param.getOrderId();

        CommonResult<String> commonResult = null;
        // 返回页面信息
        StringBuilder returnPageMsg = new StringBuilder();
        returnPageMsg.append("转定：");
        // 持久化日志初始化
        StringBuilder updateLog = new StringBuilder();
        updateLog.append("转定：");
        updateLog.append(param.getUserName());
        updateLog.append(";");
        //1.转定处理
        try {
            // 锁
            lock = opsRedissonLock.addLock(key);
            // 转定处理
            //zdService.handleOrderBindInvZD(param);
            doPcoLogicCenterService.handleOrderBindInvZD(param);
            // 持久化日志
            updateLog.append("成功;");
            updateLog.append(param.getResultLog());
            // 返回标识
            commonResult = CommonResult.success();
            // 返回信息
            returnPageMsg.append("成功;");
            doPcoLogicCenterService.notifyShipmentPlanMatchOrder(new NotifyShipmentCondition(param.getOrderId(),param.getOrderItem(),"转订"));
        }catch (OpsException e) {
            log.error(param.getOrderId(),e);
            // 持久化日志
            updateLog.append("失败;");
            updateLog.append(e.getMessage());
            updateLog.append(param.getResultLog());
            // 返回标识
            commonResult = CommonResult.failure(e.getMessage());
            // 返回页面信息
            returnPageMsg.append("失败:");
            returnPageMsg.append(e.getMessage());
            returnPageMsg.append(";");
        }
        catch (Exception e) {
            log.error(param.getOrderId(),e);
            // 持久化日志
            updateLog.append("失败-系统异常;");
            updateLog.append(param.getResultLog());
            // 返回标识
            commonResult = CommonResult.failure("转定失败");
            // 返回页面信息
            returnPageMsg.append("失败：失败-系统异常;");
        }finally {
            // 释放锁
            if (lock) {
                opsRedissonLock.releaseLock(key);
            }
        }
        //2.删除采购单 返回是否成功不影响接单处理
        if(Objects.nonNull(param.getDelPoFlag()) && param.getDelPoFlag()){
            String poFlag = "失败;";
            // 返回页面信息
            returnPageMsg.append("采购取消：");
            // 持久化信息
            updateLog.append("采购取消：");
            try {
                //采购单取消
                CommonResult<Boolean> poResult = zdService.ZDDelPo(param.getPoInfo());
                if(poResult.isSuccess()){
                    poFlag = "成功;";
                }
                log.info("转定取消采购单:{}",poResult.isSuccess());
            } catch (Exception e) {
                log.error("转定取消采购单失败:", e);
            }
            // 返回页面信息
            returnPageMsg.append(poFlag);
            // 持久化信息
            updateLog.append(poFlag);
        }
        // 3创建调库计划 不删采购单创建调库计划
        if(Objects.nonNull(param.getDelPoFlag()) &&  !param.getDelPoFlag() && Objects.nonNull(param.getPoInfo())){
            String endUser = param.getPoInfo().getEndUser();
            if(StringUtils.isBlank(param.getPoInfo().getEndUser())){
                Rcvmaster rcvmaster = wmRouterOrderService.getRcvmaster(param.getOrderId());
                endUser = rcvmaster.getEndUser();
            }
            List<PurchaseInfoForCancel> poList = new ArrayList<>();
            poList.add(param.getPoInfo());
            Boolean transferPlan = null;
            try {
                transferPlan = createTransferPlanService.createTransferPlan(poList, param.getUserName(), endUser);
            } catch (Exception e) {
                log.error("转定生成调库计划失败",e);
                transferPlan = false;
            }
            String planReustMsg = "";
            if(Objects.nonNull(transferPlan)){
                planReustMsg = ";调库计划：失败";
                if(transferPlan){
                    planReustMsg = ";调库计划：成功";
                }
            }
            returnPageMsg.append(planReustMsg);
            updateLog.append(planReustMsg);
        }
        //bugid:14883 c14717 20240730
        if(Objects.isNull(param.getDelPoFlag())){
            updateLog.append(";采购标识为空");
        }
        //4.记录日志 持久化日志信息
        OpsOrderUpdateLog log = new OpsOrderUpdateLog();
        log.setOrderid(param.getOrderId());
        log.setOrderItem(Integer.parseInt(param.getOrderItem()));
        log.setParams(JSONUtil.toJsonStr(param));
        log.setResult(updateLog.toString());
        log.setCreateTime(DateUtil.getNow());
        opsOrderUpdateLogMapper.insertSelective(log);
        //返回页面信息
        commonResult.setMessage(returnPageMsg.toString());
        return commonResult;
    }
}
