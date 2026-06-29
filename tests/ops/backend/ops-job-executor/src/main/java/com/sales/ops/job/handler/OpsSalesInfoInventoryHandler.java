package com.sales.ops.job.handler;

import com.alibaba.fastjson.JSON;
import com.sales.ops.dto.util.CommonResult;
import com.sales.ops.feign.OpsWmDispatchForOrderFeignApi;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author C12961
 * @date 2022/1/7 10:09
 */
@Component
public class OpsSalesInfoInventoryHandler {

    @Resource
    private OpsWmDispatchForOrderFeignApi opsWmDispatchForOrderFeignApi;

    /**
     * 取消占用过期的情报库存
     * @author C12961
     * @date 2022/1/7 10:39
     */
    @XxlJob("opsSalesInfoUndoJobHandler")
    public void undoExpSalesInfoInventory() {
        XxlJobHelper.log("执行开始");
        try {
            CommonResult result = opsWmDispatchForOrderFeignApi.findExpireSalesInfo();
            if (result.isSuccess()) {
                List<String> expireNoList = (List<String>) result.getData();
                XxlJobHelper.log("过期情报号条数："+expireNoList.size());
                for (String salesInfo : expireNoList) {
                    CommonResult undoResult = opsWmDispatchForOrderFeignApi.undoSalesInfo(salesInfo);
                    XxlJobHelper.log(salesInfo+"取消完成:"+ JSON.toJSONString(undoResult));
                    if (!undoResult.isSuccess()) {
                        XxlJobHelper.handleFail();
                    }
                }
            }
            //查询已删除n天内的过期情报库存
            String jobParam = XxlJobHelper.getJobParam();
            XxlJobHelper.log("查询已删除三天内的过期情报库存");
            int days = 0;// 默认不开启
            //jobParam如果是数字则格式为days
            if (StringUtils.isNumeric(jobParam)) {
                 days = Integer.parseInt(jobParam);
            }
            if (days != 0) {
                result = opsWmDispatchForOrderFeignApi.findExpireSalesInfoDeleted(days);
                if (result.isSuccess()) {
                    List<String> expireNoList = (List<String>) result.getData();
                    XxlJobHelper.log("过期情报号条数：" + expireNoList.size());
                    for (String salesInfo : expireNoList) {
                        CommonResult undoResult = opsWmDispatchForOrderFeignApi.undoSalesInfo(salesInfo);
                        XxlJobHelper.log(salesInfo + "取消完成:" + JSON.toJSONString(undoResult));
                        if (!undoResult.isSuccess()) {
                            XxlJobHelper.handleFail();
                        }
                    }
                }
            }
        } catch (Exception e) {
            XxlJobHelper.log("执行失败 : " + e.getMessage());
            XxlJobHelper.handleFail();
        }
        XxlJobHelper.log("执行完成");
    }


}
