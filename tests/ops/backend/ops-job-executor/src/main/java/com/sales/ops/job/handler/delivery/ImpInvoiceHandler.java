package com.sales.ops.job.handler.delivery;

import com.sales.ops.feign.purchase.OpsImpInvoiceSysnFeignApi;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author B91717
 * @description: 同步po适配器的发票数据，到impInvoiceMaster\detail\pack表中
 */
@Component
public class ImpInvoiceHandler {

    @Autowired
    private OpsImpInvoiceSysnFeignApi impInvoiceSysnFeignApi;


    /**
     * 写入impInvoiceMaster\detail\pack表中
     */
    @XxlJob("InsertImpInvoice")
    public void insertImpInvoice() throws Exception {
        XxlJobHelper.log("开始执行新发票导入写入impInvoice表");
        try {
            ResultVo<String> resultVo = impInvoiceSysnFeignApi.InsertImpInvoice();
            if (resultVo.isSuccess()) {
                XxlJobHelper.handleSuccess("新发票导入写入impInvoice表成功: " + resultVo.getData());
            } else {
                XxlJobHelper.handleFail("新发票导入写入impInvoice表失败: " + resultVo.getMessage());
                throw new Exception(resultVo.getMessage());
            }
        } catch (Exception e) {
            XxlJobHelper.log("新发票导入写入impInvoice表失败: {}", e.getMessage());
            XxlJobHelper.handleFail("新发票导入写入impInvoice表失败");
            throw e;
        }
    }
}
