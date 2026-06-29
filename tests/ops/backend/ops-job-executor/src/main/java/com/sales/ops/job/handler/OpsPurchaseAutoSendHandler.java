package com.sales.ops.job.handler;

import java.util.List;

import com.smc.smccloud.core.model.ResultVo.ResultVo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sales.ops.dto.purchase.OpsPurchaseStatusToWMDto;
import com.sales.ops.dto.util.CommonResult;
import com.sales.ops.feign.OpsMailApi;
import com.sales.ops.feign.OpsWmDispatchForOrderFeignApi;
import com.sales.ops.feign.PurchaseAutoSendFeignApi;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;

@Component
public class OpsPurchaseAutoSendHandler {

	private static Logger logger = LoggerFactory.getLogger(OpsPurchaseAutoSendHandler.class);

	@Autowired
	private PurchaseAutoSendFeignApi purchaseAutoSendFeignApi;

	@Autowired
	private OpsWmDispatchForOrderFeignApi opsWmDispatchForOrderFeignApi;

	//打印错误信息，jobAdminWeb提示失败，发邮件用
	public void errorInfo(ResultVo<List<OpsPurchaseStatusToWMDto>> resultVo){
		if(!resultVo.isSuccess()){
			if(StringUtils.isNotBlank(resultVo.getMessage()) && !resultVo.getMessage().equals("无数据")){
				XxlJobHelper.handleFail(resultVo.getMessage());
			}
		}
	}

	@XxlJob("autoSendJPJobHandler")
	public void autoSendJP() throws Exception {
		XxlJobHelper.log("执行开始");
		try {
			ResultVo<List<OpsPurchaseStatusToWMDto>> mrwmResult = purchaseAutoSendFeignApi.requestToReadyJP();
			ResultVo<List<OpsPurchaseStatusToWMDto>> opsSendWmListResult = purchaseAutoSendFeignApi.readyToPurchaseJP();
			ResultVo<Integer> result = purchaseAutoSendFeignApi.purchaseAutoJP();
			// 写入wm状态
			// 16392 发单事件优化
			if(mrwmResult.isSuccess()){
				opsWmDispatchForOrderFeignApi.sendForPo(mrwmResult.getData());
			}
			if(opsSendWmListResult.isSuccess()){
				opsWmDispatchForOrderFeignApi.sendForPo(opsSendWmListResult.getData());
			}
			if (result.isSuccess()) {
//				// 写入wm状态
//				if (!CollectionUtils.isEmpty(mrwm)) {
//					opsWmDispatchForOrderFeignApi.sendForPo(mrwm);
//				}
//				if (!CollectionUtils.isEmpty(opsSendWmList)) {
//					opsWmDispatchForOrderFeignApi.sendForPo(opsSendWmList);
//				}
				XxlJobHelper.log("更新了 ： " + result.getData().toString());
				XxlJobHelper.log("readyToPurchaseJP ： " + opsSendWmListResult.getMessage());
				// 打印错误信息，排除无数据情况
				errorInfo(mrwmResult);
				errorInfo(opsSendWmListResult);
			} else {
				throw new Exception(result.getMessage());
			}
		} catch (Exception e) {
			XxlJobHelper.log("执行失败 : {}", e);
			logger.error("自动发单--日本--错误：{}", e);
			throw e;
		}
	}

	@XxlJob("autoSendGZJobHandler")
	public void autoSendGZ() throws Exception {
		XxlJobHelper.log("执行开始");
		try {
			ResultVo<List<OpsPurchaseStatusToWMDto>> mrwmResult = purchaseAutoSendFeignApi.requestToReadyGZ();
			ResultVo<List<OpsPurchaseStatusToWMDto>> opsSendWmListResult = purchaseAutoSendFeignApi.readyToPurchaseGZ();
			ResultVo<Integer> result = purchaseAutoSendFeignApi.purchaseAutoGZ();
			// 写入wm状态
			// 16392 自动发单优化
			if(mrwmResult.isSuccess()){
				opsWmDispatchForOrderFeignApi.sendForPo(mrwmResult.getData());
			}
			if(opsSendWmListResult.isSuccess()){
				opsWmDispatchForOrderFeignApi.sendForPo(opsSendWmListResult.getData());
			}
			if (result.isSuccess()) {
//				// 写入wm状态
//				if (!CollectionUtils.isEmpty(mrwm)) {
//					opsWmDispatchForOrderFeignApi.sendForPo(mrwm);
//				}
//				if (!CollectionUtils.isEmpty(opsSendWmList)) {
//					opsWmDispatchForOrderFeignApi.sendForPo(opsSendWmList);
//				}
				XxlJobHelper.log("更新了 ： " + result.getData().toString());
				// 打印错误信息，排除无数据情况
				errorInfo(mrwmResult);
				errorInfo(opsSendWmListResult);
			} else {
				throw new Exception(result.getMessage());
			}
		} catch (Exception e) {
			XxlJobHelper.log("执行失败 : {}", e);
			logger.error("自动发单--广州--错误：{}", e);
			throw e;
		}
	}

	@XxlJob("autoSendManufactureJobHandler")
	public void autoSendManufacture() throws Exception {
		XxlJobHelper.log("执行开始");
		try {
			ResultVo<List<OpsPurchaseStatusToWMDto>> mrwmResult = purchaseAutoSendFeignApi.requestToReadyManufacture();
			ResultVo<List<OpsPurchaseStatusToWMDto>> opsSendWmListResult = purchaseAutoSendFeignApi.readyToPurchaseManufacture();
			ResultVo<Integer> result = purchaseAutoSendFeignApi.purchaseAutoManufacture();
			// 写入wm状态
			// 16392 自动发单优化
			if (mrwmResult.isSuccess()){
				opsWmDispatchForOrderFeignApi.sendForPo(mrwmResult.getData());
			}
			if(opsSendWmListResult.isSuccess()){
				opsWmDispatchForOrderFeignApi.sendForPo(opsSendWmListResult.getData());
			}
			if (result.isSuccess()) {
//				// 写入wm状态
//				if (!CollectionUtils.isEmpty(mrwm)) {
//					opsWmDispatchForOrderFeignApi.sendForPo(mrwm);
//				}
//				if (!CollectionUtils.isEmpty(opsSendWmList)) {
//					opsWmDispatchForOrderFeignApi.sendForPo(opsSendWmList);
//				}
				XxlJobHelper.log("更新了 ： " + result.getData().toString());
				// 打印错误信息，排除无数据情况
				errorInfo(mrwmResult);
				errorInfo(opsSendWmListResult);
			} else {
				throw new Exception(result.getMessage());
			}
		} catch (Exception e) {
			XxlJobHelper.log("执行失败 : {}", e);
			logger.error("自动发单--制造--错误：{}", e);
			throw e;
		}
	}

	// bug12330增加早九点海外发单供应商限制
	@XxlJob("autoSendOverSeaInJobHandler")
	public void autoSendOverSeaIn() throws Exception {
		XxlJobHelper.log("执行开始");
		String param = XxlJobHelper.getJobParam();
		try {
			ResultVo<List<OpsPurchaseStatusToWMDto>> mrwmResult = purchaseAutoSendFeignApi.requestToReadyOverSeaIn(param);
			ResultVo<List<OpsPurchaseStatusToWMDto>> opsSendWmListResult = purchaseAutoSendFeignApi.readyToPurchaseOverSeaIn(param);
			ResultVo<Integer> result = purchaseAutoSendFeignApi.purchaseAutoOverSeaIn(param);
			// 写入wm状态//bug15148变更函数返回值，在发单最后调用事件，避免缺失事件
			// 16392 自动发单优化
			if(mrwmResult.isSuccess()){
				opsWmDispatchForOrderFeignApi.sendForPo(mrwmResult.getData());
			}
			if(opsSendWmListResult.isSuccess()){
				opsWmDispatchForOrderFeignApi.sendForPo(opsSendWmListResult.getData());
			}
			if (result.isSuccess()) {
//				// 写入wm状态//bug15148变更函数返回值，在发单最后调用事件，避免缺失事件
//				if (!CollectionUtils.isEmpty(mrwm)) {
//					opsWmDispatchForOrderFeignApi.sendForPo(mrwm);
//				}
//				if (!CollectionUtils.isEmpty(opsSendWmList)) {
//					opsWmDispatchForOrderFeignApi.sendForPo(opsSendWmList);
//				}
				XxlJobHelper.log("更新了 ： " + result.getData().toString());
				// 打印错误信息，排除无数据情况
				errorInfo(mrwmResult);
				errorInfo(opsSendWmListResult);
			} else {
				throw new Exception(result.getMessage());
			}
		} catch (Exception e) {
			XxlJobHelper.log("执行失败 : {}", e);
			logger.error("自动发单--海外早--错误：{}", e);
			throw e;
		}
	}

	// bug13940 使用字典参数控制发单的供应商
	@XxlJob("autoSendOverSeaNotInJobHandler")
	public void autoSendOverSeaNotIn() throws Exception {
		XxlJobHelper.log("执行开始");
		String param = XxlJobHelper.getJobParam();
		try {
			ResultVo<List<OpsPurchaseStatusToWMDto>>  mrwmResult = purchaseAutoSendFeignApi.requestToReadyOverSeaNotIn(param);
			ResultVo<List<OpsPurchaseStatusToWMDto>>  opsSendWmListResult = purchaseAutoSendFeignApi.readyToPurchaseOverSeaNotIn(param);
			ResultVo<Integer> result = purchaseAutoSendFeignApi.purchaseAutoOverSeaNotIn(param);
			// 写入wm状态
			// 16392 自动发单优化
			if(mrwmResult.isSuccess()){
				opsWmDispatchForOrderFeignApi.sendForPo(mrwmResult.getData());
			}
			if(opsSendWmListResult.isSuccess()){
				opsWmDispatchForOrderFeignApi.sendForPo(opsSendWmListResult.getData());
			}
			if (result.isSuccess()) {
//				// 写入wm状态
//				if (!CollectionUtils.isEmpty(mrwm)) {
//					opsWmDispatchForOrderFeignApi.sendForPo(mrwm);
//				}
//				if (!CollectionUtils.isEmpty(opsSendWmList)) {
//					opsWmDispatchForOrderFeignApi.sendForPo(opsSendWmList);
//				}
				XxlJobHelper.log("更新了 ： " + result.getData().toString());
				// 打印错误信息，排除无数据情况
				errorInfo(mrwmResult);
				errorInfo(opsSendWmListResult);
			} else {
				throw new Exception(result.getMessage());
			}
		} catch (Exception e) {
			XxlJobHelper.log("执行失败 : {}", e);
			logger.error("自动发单--海外早--错误：{}", e);
			throw e;
		}
	}

	@XxlJob("autoSendOverSeaJobHandler")
	public void autoSendOverSea() throws Exception {
		XxlJobHelper.log("执行开始");
		try {
			ResultVo<List<OpsPurchaseStatusToWMDto>> mrwmResult = purchaseAutoSendFeignApi.requestToReadyOverSea();
			ResultVo<List<OpsPurchaseStatusToWMDto>> opsSendWmListResult = purchaseAutoSendFeignApi.readyToPurchaseOverSea();
			ResultVo<Integer> result = purchaseAutoSendFeignApi.purchaseAutoOverSea();
			// 写入wm状态
			// 16392 自动发单优化
			if(mrwmResult.isSuccess()){
				opsWmDispatchForOrderFeignApi.sendForPo(mrwmResult.getData());
			}
			if(opsSendWmListResult.isSuccess()){
				opsWmDispatchForOrderFeignApi.sendForPo(opsSendWmListResult.getData());
			}
			if (result.isSuccess()) {
//				// 写入wm状态
//				if (!CollectionUtils.isEmpty(mrwm)) {
//					opsWmDispatchForOrderFeignApi.sendForPo(mrwm);
//				}
//				if (!CollectionUtils.isEmpty(opsSendWmList)) {
//					opsWmDispatchForOrderFeignApi.sendForPo(opsSendWmList);
//				}
				XxlJobHelper.log("更新了 ： " + result.getData().toString());
				// 打印错误信息，排除无数据情况
				errorInfo(mrwmResult);
				errorInfo(opsSendWmListResult);
			} else {
				throw new Exception(result.getMessage());
			}
		} catch (Exception e) {
			XxlJobHelper.log("执行失败 : {}", e);
			logger.error("自动发单--海外--错误：{}", e);
			throw e;
		}
	}

	@Autowired
	private OpsMailApi opsMailApi;

	@XxlJob("sendPurchaseJobHandler")
	public void sendPurchaseJob() throws Exception {
		XxlJobHelper.log("执行开始");
		try {
			CommonResult<Object> result = opsMailApi.sendPurchaseJob();
			if (result.isSuccess()) {
				XxlJobHelper.log("更新了 ： " + result.getData().toString());
			} else {
				throw new Exception(result.getMessage());
			}
		} catch (Exception e) {
			XxlJobHelper.log("执行失败 : {}", e);
			logger.error("发送邮件--错误：{}", e);
			throw e;
		}
	}

}
