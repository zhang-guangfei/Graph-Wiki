package com.sales.ops.job.handler.po;

import com.sales.ops.dto.util.CommonResult;
import com.sales.ops.feign.WmPurchaseFeignApi;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InterceptPoAutoHandler {


	@Autowired
	private WmPurchaseFeignApi wmPurchaseFeignApi;


	/**
     * bugid: 17646 c14717 20250526
	 * @throws Exception
	 */
	@XxlJob("interceptPoAutoInvHandler")
	public void interceptPoAutoInv() throws Exception {
		XxlJobHelper.log("执行开始");
		try {
			CommonResult<String> result = wmPurchaseFeignApi.interceptPoAutoInv();
			if (result.isSuccess()) {
				XxlJobHelper.log("成功 ： " + result.getData());
			} else {
				throw new Exception(result.getMessage());
			}
		} catch (Exception e) {
			XxlJobHelper.log("执行失败 : {}", e);
			throw e;
		}
	}
}
