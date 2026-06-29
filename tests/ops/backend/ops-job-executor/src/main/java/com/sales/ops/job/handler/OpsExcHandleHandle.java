package com.sales.ops.job.handler;

import com.sales.ops.dto.util.CommonResult;
import com.sales.ops.feign.inventory.OpsExcHandleFeignApi;
import com.sales.ops.job.util.CommonUtil;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author ：C14717
 * @version: 1.0$
 * @description：异常处理执行器
 * @date ：Created in 2022/11/21 12:28
 */
@Component
public class OpsExcHandleHandle {

    @Autowired
    private OpsExcHandleFeignApi opsExcHandleFeignApi;


    //逐条处理订单
    @XxlJob("doExcHandle")
    public void doExcHandle() throws Exception {

        try {
            String param = XxlJobHelper.getJobParam();//获得任务参数--每次取状态限制记录数
            int limit = 100;
            if (param != null && param.trim().length() != 0 && CommonUtil.isInteger(param)){
                limit = Integer.parseInt(param);
            }
            CommonResult<String> result =  opsExcHandleFeignApi.sendDoToWMSChangeStatusTwo(limit);
            if(result.isSuccess()){
                XxlJobHelper.log(result.getData());
            }else {
                XxlJobHelper.log(result.getMessage());
            }

        } catch (Exception ex) {
            XxlJobHelper.log(ex.getMessage());
        }
    }
}
