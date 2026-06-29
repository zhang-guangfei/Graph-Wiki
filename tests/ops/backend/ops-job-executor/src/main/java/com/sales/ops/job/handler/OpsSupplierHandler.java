package com.sales.ops.job.handler;

import com.sales.ops.dto.util.CommonResult;
import com.sales.ops.feign.OPSSupplierFeignApi;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author ：C14717
 * @version: 1.0$
 * @description：
 * @date ：Created in 2021/12/21 15:19
 */
@Component
public class OpsSupplierHandler {

    @Resource
    private OPSSupplierFeignApi opsSupplierFeignApi;

    @XxlJob("opsRefreshSupplier")
    public void opsRefreshWarehouseJobHandler() throws Exception {
        XxlJobHelper.log("执行 opsRefreshSupplier ----------");
        String param = XxlJobHelper.getJobParam();//获得任务参数 分钟
        if (param == null || param.trim().length() == 0) {
            param = "60";//默认60分钟
            XxlJobHelper.log("param[" + param + "] invalid.");
        }
        CommonResult<List<String>> str = opsSupplierFeignApi.refreshSupplier(param);
        XxlJobHelper.log("定时更新缓存 opsRefreshSupplierIds----" + str);
    }
}
