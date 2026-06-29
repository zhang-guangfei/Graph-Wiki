package com.sales.ops.job.handler.purchaseDeliver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sales.ops.dto.util.CommonResult;
import com.sales.ops.feign.PurchaseDeliverFeignApi;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;

@Component
public class PurchaseDeliverHandler {

	private static Logger logger = LoggerFactory.getLogger(PurchaseDeliverHandler.class);

	@Autowired
	private PurchaseDeliverFeignApi purchaseDeliverFeignApi;

	@XxlJob("operateMainLogJobHandler")
	public void operateMainLog() throws Exception {
		XxlJobHelper.log("执行开始");
		try {
			if (purchaseDeliverFeignApi == null) {
				throw new Exception("调用采购交付服务失败: feign客户端未注入");
			}

			CommonResult<Integer> result = purchaseDeliverFeignApi.operateMainLog();
			if (result == null) {
				throw new Exception("调用采购交付服务失败: 返回结果为空");
			}

			if (Boolean.TRUE.equals(result.isSuccess())) {
				Integer updatedCount = result.getData();
				if (updatedCount == null) {
					XxlJobHelper.log("更新了 ： 0 (返回data为空)");
				} else {
					XxlJobHelper.log("更新了 ： " + updatedCount);
				}
			} else {
				throw new Exception("调用采购交付服务失败: " + result.getMessage());
			}
		} catch (Exception e) {
			XxlJobHelper.log("执行失败: " + e.getMessage());
			logger.error("交付系统处理供应商变更数据--错误：{}", e);
			throw e;
		}
	}
}
