package com.sales.ops.job.handler;

import com.sales.ops.dto.util.CommonResult;
import com.sales.ops.feign.OpsAdjustFeignApi;
import com.sales.ops.job.util.CommonUtil;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author C12961
 * @date 2022/11/7 16:51
 */
@Component
public class OpsAdjustHandler {

    @Autowired
    private OpsAdjustFeignApi opsDebugFeignApi;


    @XxlJob("createInventoryDiff")
    public void createOpsInventoryDiff() {
        CommonResult commonResult = opsDebugFeignApi.createOpsInventoryDiff();
        XxlJobHelper.log("执行完成");
    }

    @XxlJob("createInventoryAdj")
    public void createOpsInventoryAdj() {
        String param = XxlJobHelper.getJobParam();//获得任务参数--每次取状态限制记录数
        int limit = 100;
        if (param != null && param.trim().length() != 0 && CommonUtil.isInteger(param)) {
            limit = Integer.parseInt(param);
        }
        CommonResult commonResult = opsDebugFeignApi.createOpsInventoryAdj(limit);
        XxlJobHelper.log("执行完成");
    }

    @XxlJob("handleInventoryAdj")
    public void handleOpsInventoryAdj() {
        String param = XxlJobHelper.getJobParam();//获得任务参数--每次取状态限制记录数
        int limit = 100;
        if (param != null && param.trim().length() != 0 && CommonUtil.isInteger(param)) {
            limit = Integer.parseInt(param);
        }
        CommonResult commonResult = opsDebugFeignApi.handleOpsInventoryAdj(limit);
        XxlJobHelper.log("执行完成");
    }


}
