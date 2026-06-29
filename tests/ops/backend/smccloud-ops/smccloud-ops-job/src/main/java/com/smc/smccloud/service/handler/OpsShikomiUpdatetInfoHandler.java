package com.smc.smccloud.service.handler;

import com.smc.smccloud.core.model.constants.GlobalConstant;
import com.smc.smccloud.core.utils.SMCApp;
import com.smc.smccloud.core.utils.ThreadLocalMapUtil;
import com.smc.smccloud.service.ProductServiceFeignApi;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@Slf4j
public class OpsShikomiUpdatetInfoHandler {

    @Resource
    private ProductServiceFeignApi productServiceFeignApi;

    public void init() {
        SMCApp.setGrantType(GlobalConstant.GrantType.CLIANT_CREDENTIALS);
    }

    public void destroy() {
        ThreadLocalMapUtil.clear();
    }

    @XxlJob(value = "autoUpdateShikomiInfo", init = "init", destroy = "destroy")
    public void autoShikomiInspect() throws Exception {

        System.out.println("==> 进入自动更新shikomi在库在途数量执行器");
        XxlJobHelper.log("==> 进入自动更新shikomi在库在途数量执行器");

        try {
            productServiceFeignApi.calShikomiUpdateInfo();
        } catch (Exception e) {
            e.printStackTrace();
            XxlJobHelper.log("出现异常:"+e.getMessage());
            XxlJobHelper.handleFail("更新shikomi在库在途数量异常");
            return;
        }
        XxlJobHelper.log("执行成功");
        XxlJobHelper.handleSuccess("执行成功");
    }

    @XxlJob(value = "saveShikomiModelCache", init = "init", destroy = "destroy")
    public void saveShikomiModelCache() throws Exception {
        XxlJobHelper.log("==> 进入自动更新shikomi模糊型号执行器");

        try {
            productServiceFeignApi.saveShikomiCache();
        } catch (Exception e) {
            e.printStackTrace();
            XxlJobHelper.log("出现异常:"+e.getMessage());
            XxlJobHelper.handleFail("更新shikomi模糊型号异常");
            return;
        }

        XxlJobHelper.log("执行成功");
        XxlJobHelper.handleSuccess("执行成功");
    }

    @XxlJob(value = "calcShikomiQtyWarning", init = "init", destroy = "destroy")
    public void calcShikomiQtyWarning() throws Exception {

        log.info("==> 进入自动计算SHIKOMI警告数量执行器");
        XxlJobHelper.log("==> 进入自动计算SHIKOMI警告数量执行器");

        try {
            productServiceFeignApi.calcShikomiQtyWarning();
        } catch (Exception e) {
            e.printStackTrace();
            XxlJobHelper.log("出现异常:"+e.getMessage());
            XxlJobHelper.handleFail("计算SHIKOMI警告数量异常");
            return;
        }
        XxlJobHelper.log("计算警告数完成");
        XxlJobHelper.handleSuccess("计算警告数完成");
    }


    @XxlJob(value = "syncCNShikomiNoordQty", init = "init", destroy = "destroy")
    public void syncCNShikomiNoordQty() throws Exception {

        log.info("==> 进入自动计算SHIKOMI未订货月数执行器");
        XxlJobHelper.log("==> 进入自动计算SHIKOMI未订货月数执行器");

        try {
            productServiceFeignApi.syncCNShikomiNoordQty();
        } catch (Exception e) {
            e.printStackTrace();
            XxlJobHelper.log("出现异常:"+e.getMessage());
            XxlJobHelper.handleFail("计算SHIKOMI未订货月数异常");
            return;
        }
        XxlJobHelper.log("SHIKOMI未订货月数计算完成");
        XxlJobHelper.handleSuccess("SHIKOMI未订货月数计算完成");
    }
}
