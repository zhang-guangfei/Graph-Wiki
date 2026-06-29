package com.sales.ops.job.handler;

import com.sales.ops.dto.ba.CustomerInfo;
import com.sales.ops.dto.util.CommonResult;
import com.sales.ops.feign.OpsCustomerFeignApi;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @description
 * @date 2021/10/29 16:13
 * @auther C12961
 */
@Component
public class OpsCustomerAddressHandler {

    @Resource
    OpsCustomerFeignApi opsCustomerFeignApi;


    @XxlJob("opsRefreshCustomerJobHandler")
    public void opsRefreshWarehouseJobHandler() throws Exception {
        XxlJobHelper.log("执行opsRefreshCustomerJobHandler----------");
        String param = XxlJobHelper.getJobParam();//获得任务参数 分钟
        if (param == null || param.trim().length() == 0) {
            param = "60";//默认60分钟
            XxlJobHelper.log("param[" + param + "] invalid.");

        }
        CommonResult<List<String>> str = opsCustomerFeignApi.refreshCustomer(param);
        XxlJobHelper.log("定时更新缓存 customerNOs----" + str);
    }

    //定时更 刷新 委托在库和客户关系表信息 ops_customer_warehouse 获取任务参数 分钟
    @XxlJob("opsRefreshOpsCustomerWarehouseJobHandler")
    public void refreshOpsCustomerWarehouseData() throws Exception{
        XxlJobHelper.log("执行opsRefreshOpsCustomerWarehouseJobHandler----------");
        String param = XxlJobHelper.getJobParam();//获得任务参数 分钟
        if (param==null || param.trim().length()==0) {
            param = "60";//默认60分钟
            XxlJobHelper.log("param["+ param +"] invalid.");

        }
        CommonResult str = opsCustomerFeignApi.refreshOpsCustomerWarehouseData(param);
        XxlJobHelper.log("定时更新缓存 opsRefreshOpsCustomerWarehouseJobHandler----" +str);
    }
}
