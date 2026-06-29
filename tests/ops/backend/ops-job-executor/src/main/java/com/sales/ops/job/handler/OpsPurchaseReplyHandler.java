package com.sales.ops.job.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sales.ops.dto.util.CommonResult;
import com.sales.ops.feign.PurchaseReplyFeignApi;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;

@Component
public class OpsPurchaseReplyHandler {

	private static Logger logger = LoggerFactory.getLogger(OpsPurchaseReplyHandler.class);

	@Autowired
	private PurchaseReplyFeignApi purchaseReplyFeignApi;

	@XxlJob("poDelayEventJobHandler")
	public void poDelayEvent() throws Exception {
		XxlJobHelper.log("执行开始采购返信延期自动作业失败");
		try {
			CommonResult<Integer> result = purchaseReplyFeignApi.poDelayEvent();
			if (result.isSuccess()) {
				XxlJobHelper.log("更新了：" + result.getData().toString());
			} else {
				XxlJobHelper.handleFail("采购返信延期自动作业失败");
				throw new Exception(result.getMessage());
			}
		} catch (Exception e) {
			XxlJobHelper.log("执行失败 : {}", e);
			XxlJobHelper.handleFail("执行失败，采购返信延期自动作业失败");
			throw e;
		}
	}
}
