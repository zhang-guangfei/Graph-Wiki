package com.sales.ops.job.handler;

import com.sales.ops.dto.util.CommonResult;
import com.sales.ops.feign.OPSWarehouseFeignApi;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author ：C14717
 * @version: 1.0$
 * @description：ops仓库相关定时任务
 * @date ：Created in 2021/10/19 8:30
 */
@Component
public class OPSWarehouseHandler {

    @Autowired
    private OPSWarehouseFeignApi opsWarehouseFeignApi;

    //定时更新仓库Warehouse表 获取任务参数 分钟
    @XxlJob("opsRefreshWarehouseJobHandler")
    public void opsRefreshWarehouseJobHandler() throws Exception{
        XxlJobHelper.log("执行opsRefreshWarehouseJobHandler----------");
        String param = XxlJobHelper.getJobParam();//获得任务参数 分钟
        if (param==null || param.trim().length()==0) {
            param = "60";//默认60分钟
            XxlJobHelper.log("param["+ param +"] invalid.");

        }
        CommonResult str = opsWarehouseFeignApi.refreshWarehouse(param);
        XxlJobHelper.log("定时更新缓存 Warehouse----" +str);
    }

    //定时更新仓库供应商表WarehouseSupplierConfig表 获取任务参数分钟
    @XxlJob("opsRefreshWarehouseSupplierConfigJobHandler")
    public void opsRefreshWarehouseSupplierConfigJobHandler() throws Exception{
        XxlJobHelper.log("执行opsRefreshWarehouseSupplierConfigJobHandler----------");
        String param = XxlJobHelper.getJobParam();//获得任务参数 分钟
        if (param==null || param.trim().length()==0) {
            param = "60";//默认60分钟
            XxlJobHelper.log("param["+ param +"] invalid.");

        }
        CommonResult str = opsWarehouseFeignApi.refreshWarehouseSupplierConfig(param);
        XxlJobHelper.log("定时更新缓存 WarehouseSupplierConfig----" +str);
    }

    //定时更新仓库营业所表 warehouseSalesbranchConfig表 获取任务参数分钟
    @XxlJob("opsRefreshWarehouseSalesbranchConfigJobHandler")
    public void opsRefreshWarehouseSalesbranchConfigJobHandler() throws Exception{
        XxlJobHelper.log("执行opsRefreshWarehouseSupplierConfigJobHandler----------");
        String param = XxlJobHelper.getJobParam();//获得任务参数 分钟
        if (param==null || param.trim().length()==0) {
            param = "60";//默认60分钟
            XxlJobHelper.log("param["+ param +"] invalid.");

        }
        CommonResult str = opsWarehouseFeignApi.refreshWarehouseSalesbranchConfig(param);
        XxlJobHelper.log("定时更新缓存 WarehouseSupplierConfig----" +str);
    }
}
