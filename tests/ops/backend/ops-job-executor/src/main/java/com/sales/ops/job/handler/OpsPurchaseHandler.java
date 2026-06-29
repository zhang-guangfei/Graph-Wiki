package com.sales.ops.job.handler;

import com.sales.ops.dto.util.CommonResult;
import com.sales.ops.feign.PurchaseFeignApi;
import com.sales.ops.feign.RequestRestoreFeignApi;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OpsPurchaseHandler {
	private static Logger logger = LoggerFactory.getLogger(OpsPurchaseHandler.class);

	@Autowired
	private PurchaseFeignApi purchaseFeignApi;

	@Autowired
	private RequestRestoreFeignApi requestRestoreFeignApi;

	@XxlJob("opsPurchasePreJobHandler")
	public void pre() throws Exception {
		XxlJobHelper.log("执行开始");
		try {
			CommonResult result = purchaseFeignApi.pre();
			if (result.isSuccess()) {
				XxlJobHelper.log("更新了 ： " + result.getData().toString());
			}else {
				XxlJobHelper.log("失败 ： " + result.getMessage());
                XxlJobHelper.handleFail("失败 ： " + result.getMessage());
			}
		} catch (Exception e) {
			XxlJobHelper.log("执行失败 : " + e.getMessage());
            XxlJobHelper.handleFail("执行失败 ： " + e.getMessage());
			logger.error(e.getMessage());
			throw e;
		}
//		XxlJobHelper.log("执行完成");
	}

	@XxlJob("opsPurchasePreBinJobHandler")
	public void preBin() throws Exception {
		XxlJobHelper.log("执行开始");
		try {
			CommonResult result = purchaseFeignApi.preBin();
			if (result.isSuccess()) {
				XxlJobHelper.log("更新了 ： " + result.getData().toString());
			}
		} catch (Exception e) {
			XxlJobHelper.log("执行失败 : " + e.getMessage());
			logger.error(e.getMessage());
			throw e;
		}
//		XxlJobHelper.log("执行完成");
	}

	@XxlJob("opsPurchaseSendManuJobHandler")
	public void sendManu() throws Exception {
		XxlJobHelper.log("执行sendPurchaseToManu----------");
		try {
			CommonResult result = purchaseFeignApi.sendManu();
			XxlJobHelper.log(result.getMessage());
		} catch (Exception e) {
			XxlJobHelper.log("执行失败" + e.getMessage());
			logger.error(e.getMessage());
			throw e;
		}
	}

	@XxlJob("opsPurchaseSendGZJobHandler")
	public void sendGZ() throws Exception {
		XxlJobHelper.log("执行开始");
		try {
			CommonResult result = purchaseFeignApi.sendGZ();
			XxlJobHelper.log(result.getMessage());
		} catch (Exception e) {
			XxlJobHelper.log("执行失败" + e.getMessage());
			logger.error(e.getMessage());
			throw e;
		}
	}

	@XxlJob("opsPurchaseSendJobHandler")
	public void send() throws Exception {
		XxlJobHelper.log("执行开始");
		try {
			CommonResult result = purchaseFeignApi.send();
			XxlJobHelper.log(result.getMessage());
		} catch (Exception e) {
			XxlJobHelper.log("执行失败" + e.getMessage());
			logger.error(e.getMessage());
			throw e;
		}
	}

	@XxlJob("opsCtcFinishJobHandler")
	public void ctcFinish() throws Exception {
		XxlJobHelper.log("执行开始");
		try {
			CommonResult result = purchaseFeignApi.ctcFinish();
			XxlJobHelper.log(result.getMessage());
		} catch (Exception e) {
			XxlJobHelper.log("执行失败" + e.getMessage());
			logger.error(e.getMessage());
			throw e;
		}
	}

	@XxlJob("opsCtcDeleteobHandler")
	public void ctcDelete() throws Exception {
		XxlJobHelper.log("执行开始");
		try {
			CommonResult result = purchaseFeignApi.ctcDelete();
			XxlJobHelper.log(result.getMessage());
		} catch (Exception e) {
			XxlJobHelper.log("执行失败" + e.getMessage());
			logger.error(e.getMessage());
			throw e;
		}
	}

	/**
	 *  采购拦截自动还原,7:00 AM-20:00 1h/次
	 */
	@XxlJob("opsRequestInterceptRestore")
	public void interceptRestore() {
		XxlJobHelper.log("==> 开始执行采购拦截订单自动还原 =>");
		CommonResult<String> commonResult = requestRestoreFeignApi.autoRestore();
		if (!commonResult.isSuccess()) {
			XxlJobHelper.log("采购拦截订单自动还原任务 执行失败 ==> {}", commonResult.getMessage());
			XxlJobHelper.handleFail("采购拦截订单自动还原任务 执行失败");
		}
		XxlJobHelper.handleSuccess(commonResult.getMessage());
	}

}
