package com.sales.ops.job.handler;

import com.alibaba.fastjson.JSON;
import com.sales.ops.dto.util.CommonResult;
import com.sales.ops.feign.event.OpsOrderEventFeignApi;
import com.sales.ops.job.util.CommonUtil;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author C12961
 * @date 2022/2/23 8:16
 */
@Component
public class OpsOrderEventHandler {


    @Resource
    private OpsOrderEventFeignApi opsOrderEventFeignApi;

    @XxlJob("checkOrderCredit")
    public void checkOrderCredit() {
        XxlJobHelper.log("执行开始");
        try {
            CommonResult commonResult = opsOrderEventFeignApi.checkCredit();
            XxlJobHelper.log(commonResult.getMessage());
            if (commonResult.isSuccess()) {
                List<String> orderList = (List<String>) commonResult.getData();
                for (String orderNo : orderList) {
                    XxlJobHelper.log(orderNo);
                }
            }else{
                XxlJobHelper.log("执行失败 : " + commonResult.getMessage());
                XxlJobHelper.handleFail("执行失败 : " + commonResult.getMessage());
            }
        } catch (Exception e) {
            XxlJobHelper.log("执行失败 : " + e.getMessage());
            XxlJobHelper.handleFail("执行失败 : " + e.getMessage());
        }
        XxlJobHelper.log("执行完成");
    }

    @XxlJob("EventPreprocessorV3")
    public void eventPreprocessorV3() {
        XxlJobHelper.log("执行开始");
        try {
            String param = XxlJobHelper.getJobParam();// 获得任务参数--每次取状态限制记录数
            int limit = 100;
            if (param != null && param.trim().length() != 0 && CommonUtil.isInteger(param)) {
                limit = Integer.parseInt(param);
            }
            CommonResult result = opsOrderEventFeignApi.preprocess(limit);
            XxlJobHelper.log(result.getMessage());
            if (!result.isSuccess()) {
                if (result.getData() != null) {
                    XxlJobHelper.log((String) result.getData());
                }
            }
        } catch (Exception e) {
            XxlJobHelper.log("执行失败 : " + e.getMessage());
            XxlJobHelper.handleFail("执行失败 : " + e.getMessage());
        }
        XxlJobHelper.log("执行完成");
    }

    @XxlJob("EventExchangeV3")
    public void eventExchangeV3() {
        XxlJobHelper.log("执行开始");
        try {
            String param = XxlJobHelper.getJobParam();// 获得任务参数--每次取状态限制记录数
            int limit = 100;
            if (param != null && param.trim().length() != 0 && CommonUtil.isInteger(param)) {
                limit = Integer.parseInt(param);
            }
            CommonResult result = opsOrderEventFeignApi.exchange(limit);
            XxlJobHelper.log(result.getMessage());
            if (!result.isSuccess()) {
                if (result.getData() != null) {
                    XxlJobHelper.log((String) result.getData());
                }
                XxlJobHelper.handleFail("执行失败 : " + JSON.toJSONString(result));
            }
        } catch (Exception e) {
            XxlJobHelper.log("执行失败 : " + e.getMessage());
            XxlJobHelper.handleFail("执行失败 : " + e.getMessage());
        }
        XxlJobHelper.log("执行完成");
    }


    @XxlJob("eventOrderStatusV3")
    public void eventOrderStatusV3() {
        XxlJobHelper.log("执行开始");
        try {
            String param = XxlJobHelper.getJobParam();// 获得任务参数--每次取状态限制记录数
            int limit = 100;
            if (param != null && param.trim().length() != 0 && CommonUtil.isInteger(param)) {
                limit = Integer.parseInt(param);
            }
            //默认分组1
            CommonResult result = opsOrderEventFeignApi.orderStatusQueue(limit, 1);
            XxlJobHelper.log(result.getMessage());
            if (!result.isSuccess()) {
                if (result.getData() != null) {
                    XxlJobHelper.log((String) result.getData());
                }
                XxlJobHelper.handleFail("执行失败 : " + JSON.toJSONString(result));
            }
        } catch (Exception e) {
            XxlJobHelper.log("执行失败 : " + e.getMessage());
            XxlJobHelper.handleFail("执行失败 : " + e.getMessage());
        }
        XxlJobHelper.log("执行完成");
    }

    @XxlJob("eventOrderStatusV3-group2")
    public void eventOrderStatusV3Group2() {
        XxlJobHelper.log("执行开始");
        try {
            String param = XxlJobHelper.getJobParam();// 获得任务参数--每次取状态限制记录数
            int limit = 100;
            if (param != null && param.trim().length() != 0 && CommonUtil.isInteger(param)) {
                limit = Integer.parseInt(param);
            }
            //分组2
            CommonResult result = opsOrderEventFeignApi.orderStatusQueue(limit, 2);
            XxlJobHelper.log(result.getMessage());
            if (!result.isSuccess()) {
                if (result.getData() != null) {
                    XxlJobHelper.log((String) result.getData());
                }
                XxlJobHelper.handleFail("执行失败 : " + JSON.toJSONString(result));
            }
        } catch (Exception e) {
            XxlJobHelper.log("执行失败 : " + e.getMessage());
            XxlJobHelper.handleFail("执行失败 : " + e.getMessage());
        }
        XxlJobHelper.log("执行完成");
    }

    @XxlJob("eventOrderAssignV3")
    public void eventOrderAssignV3() {
        XxlJobHelper.log("执行开始");
        try {
            String param = XxlJobHelper.getJobParam();// 获得任务参数--每次取状态限制记录数
            int limit = 100;
            if (param != null && param.trim().length() != 0 && CommonUtil.isInteger(param)) {
                limit = Integer.parseInt(param);
            }
            CommonResult result = opsOrderEventFeignApi.orderAssignQueue(limit);
            XxlJobHelper.log(result.getMessage());
            if (!result.isSuccess()) {
                if (result.getData() != null) {
                    XxlJobHelper.log((String) result.getData());
                }
                XxlJobHelper.handleFail("执行失败 : " + JSON.toJSONString(result));
            }
        } catch (Exception e) {
            XxlJobHelper.log("执行失败 : " + e.getMessage());
            XxlJobHelper.handleFail("执行失败 : " + e.getMessage());
        }
        XxlJobHelper.log("执行完成");
    }


    @XxlJob("eventOrderDeliveryPlanV3")
    public void eventOrderDeliveryPlanV3() {
        XxlJobHelper.log("执行开始");
        try {
            String param = XxlJobHelper.getJobParam();// 获得任务参数--每次取状态限制记录数
            int limit = 100;
            if (param != null && param.trim().length() != 0 && CommonUtil.isInteger(param)) {
                limit = Integer.parseInt(param);
            }
            CommonResult result = opsOrderEventFeignApi.orderDeliveryPlanQueue(limit);
            XxlJobHelper.log(result.getMessage());
            if (!result.isSuccess()) {
                if (result.getData() != null) {
                    XxlJobHelper.log((String) result.getData());
                }
                XxlJobHelper.handleFail("执行失败 : " + JSON.toJSONString(result));
            }
        } catch (Exception e) {
            XxlJobHelper.log("执行失败 : " + e.getMessage());
            XxlJobHelper.handleFail("执行失败 : " + e.getMessage());
        }
        XxlJobHelper.log("执行完成");
    }

    @XxlJob("eventPurchaseOrderV3")
    public void eventPurchaseOrderV3() {
        XxlJobHelper.log("执行开始");
        try {
            String param = XxlJobHelper.getJobParam();// 获得任务参数--每次取状态限制记录数
            int limit = 100;
            if (param != null && param.trim().length() != 0 && CommonUtil.isInteger(param)) {
                limit = Integer.parseInt(param);
            }
            CommonResult result = opsOrderEventFeignApi.purchaseOrderQueue(limit);
            XxlJobHelper.log(result.getMessage());
            if (!result.isSuccess()) {
                if (result.getData() != null) {
                    XxlJobHelper.log((String) result.getData());
                }
                XxlJobHelper.handleFail("执行失败 : " + JSON.toJSONString(result));
            }
        } catch (Exception e) {
            XxlJobHelper.log("执行失败 : " + e.getMessage());
            XxlJobHelper.handleFail("执行失败 : " + e.getMessage());
        }
        XxlJobHelper.log("执行完成");
    }
    @XxlJob("eventPoDeliveryV3")
    public void eventPoDeliveryQueueV3() {
        XxlJobHelper.log("执行开始");
        try {
            String param = XxlJobHelper.getJobParam();// 获得任务参数--每次取状态限制记录数
            int limit = 100;
            if (param != null && param.trim().length() != 0 && CommonUtil.isInteger(param)) {
                limit = Integer.parseInt(param);
            }
            CommonResult result = opsOrderEventFeignApi.poDeliveryQueue(limit);
            XxlJobHelper.log(result.getMessage());
            if (!result.isSuccess()) {
                if (result.getData() != null) {
                    XxlJobHelper.log((String) result.getData());
                }
                XxlJobHelper.handleFail("执行失败 : " + JSON.toJSONString(result));
            }
        } catch (Exception e) {
            XxlJobHelper.log("执行失败 : " + e.getMessage());
            XxlJobHelper.handleFail("执行失败 : " + e.getMessage());
        }
        XxlJobHelper.log("执行完成");
    }

    @XxlJob("eventStockAdjustV3")
    public void eventStockAdjustV3() {
        XxlJobHelper.log("执行开始");
        try {
            String param = XxlJobHelper.getJobParam();// 获得任务参数--每次取状态限制记录数
            int limit = 100;
            if (param != null && param.trim().length() != 0 && CommonUtil.isInteger(param)) {
                limit = Integer.parseInt(param);
            }
            CommonResult result = opsOrderEventFeignApi.stockAdjustQueue(limit);
            XxlJobHelper.log(result.getMessage());
            if (!result.isSuccess()) {
                if (result.getData() != null) {
                    XxlJobHelper.log((String) result.getData());
                }
                XxlJobHelper.handleFail("执行失败 : " + JSON.toJSONString(result));
            }
        } catch (Exception e) {
            XxlJobHelper.log("执行失败 : " + e.getMessage());
            XxlJobHelper.handleFail("执行失败 : " + e.getMessage());
        }
        XxlJobHelper.log("执行完成");
    }

    @XxlJob("eventDeliveryPushV3")
    public void eventDeliveryPushV3() {
        XxlJobHelper.log("执行开始");
        try {
            String param = XxlJobHelper.getJobParam();// 获得任务参数--每次取状态限制记录数
            int limit = 100;
            if (param != null && param.trim().length() != 0 && CommonUtil.isInteger(param)) {
                limit = Integer.parseInt(param);
            }
            CommonResult result = opsOrderEventFeignApi.orderDeliveryPushQueue(limit);
            XxlJobHelper.log(result.getMessage());
            if (!result.isSuccess()) {
                if (result.getData() != null) {
                    XxlJobHelper.log((String) result.getData());
                }
                XxlJobHelper.handleFail("执行失败 : " + JSON.toJSONString(result));
            }
        } catch (Exception e) {
            XxlJobHelper.log("执行失败 : " + e.getMessage());
            XxlJobHelper.handleFail("执行失败 : " + e.getMessage());
        }
        XxlJobHelper.log("执行完成");
    }



    @XxlJob("eventDeliveryDateV3")
    public void eventDeliveryDateV3() {
        XxlJobHelper.log("执行开始");
        try {
            String param = XxlJobHelper.getJobParam();// 获得任务参数--每次取状态限制记录数
            int limit = 100;
            if (param != null && param.trim().length() != 0 && CommonUtil.isInteger(param)) {
                limit = Integer.parseInt(param);
            }
            CommonResult result = opsOrderEventFeignApi.orderDeliveryDateQueue(limit);
            XxlJobHelper.log(result.getMessage());
            if (!result.isSuccess()) {
                if (result.getData() != null) {
                    XxlJobHelper.log((String) result.getData());
                }
                XxlJobHelper.handleFail("执行失败 : " + JSON.toJSONString(result));
            }
        } catch (Exception e) {
            XxlJobHelper.log("执行失败 : " + e.getMessage());
            XxlJobHelper.handleFail("执行失败 : " + e.getMessage());
        }
        XxlJobHelper.log("执行完成");
    }


}
