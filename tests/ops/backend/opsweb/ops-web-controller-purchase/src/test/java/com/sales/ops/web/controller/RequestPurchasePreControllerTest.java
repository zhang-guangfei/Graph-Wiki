package com.sales.ops.web.controller;

import java.util.List;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.dto.purchase.PurchaseInfoForCancel;
import com.sales.ops.dto.util.CommonResult;
import com.sales.ops.feign.OPSProductFeignApi;
import com.sales.ops.feign.OpsCustomerFeignApi;
import com.sales.ops.service.purchase.PurchaseCancelService;
import com.sales.ops.service.purchase.PurchaseExportTxtService;
import com.sales.ops.service.purchase.RequestPurchasePreService;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.model.shikomi.ShikomiVO;
import com.smc.smccloud.service.ProductServiceFeignApi;

import lombok.extern.slf4j.Slf4j;

/**
 * @author ：C14717
 * @version: $ @description：
 * @date ：Created in 2021/11/17 16:32
 */
@Slf4j
class RequestPurchasePreControllerTest extends BaseTest {

	@Resource
	private RequestPurchasePreService requestPurchasePreService;
	@Autowired
	private OPSProductFeignApi opsProductFeignApi;
	@Autowired
	private ProductServiceFeignApi productServiceFeignApi;

	@Autowired
	private OpsCustomerFeignApi opsCustomerFeignApi;

	@Autowired
	private PurchaseExportTxtService purchaseExportTxtService;

	@Autowired
	private PurchaseCancelService purchaseCancelService;
//    @Autowired
//    private ClientAuthFeginConfiguration clientAuthFeginConfiguration;

	RequestPurchasePreControllerTest() {
	}

	@Test
	public void tt12() {
		try {
			// purchaseApiService.impdata("Y32154", 7346);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	void pre() {
		// LocalDate today = new LocalDate("2022-3-22");
		// 营业所别运输天数+仓库间运输天数
//		int transOther = 4;
//		LocalDate de = new LocalDate("2022-4-16");
//		int day = Days.daysBetween(today, de).getDays();
//		day = day - 12 - 1 - transOther;
//
//		Date w = today.plusDays(day).toDate();

//        clientAuthFeginConfiguration.apply(new RequestTemplate());
//        Feign.builder().requestInterceptor((RequestInterceptor) clientAuthFeginConfiguration);

		// ResultVo<List<ShikomiVO>> shikomi =
		// productServiceFeignApi.listCustomerShikomi("AFF70D-14", "A2J77");
		// log.info("");

		requestPurchasePreService.updateRequestPurchase();

		try {
			// purchaseApiService.impdata("Y32154", 7346);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	void mt() {
		String a = "ü";
		log.info(a.toUpperCase());
		if (Pattern.matches("GZ(.*)", "GZ98")) {
			log.info("");
			;
		}
	}

	@Test
	void feignApi() {

		// 获取多段价格
		// CommonResult r1 =
		// opsProductFeignApi.findProductPriceByModelNo("10-AFD3000-F02-J");
		// 获取错误型号
		/*
		 * CommonResult r2
		 * =opsProductFeignApi.findProductErrorByModelNo("10-AFD3000-F02-J"); //获取收敛品
		 * CommonResult r3
		 * =opsProductFeignApi.findProductEosByModelNo("10-AFD3000-F02-J");
		 * 
		 * 
		 * 
		 * CommonResult r = opsProductFeignApi.searchProduct("*EX600-DMNF");
		 */

		// CommonResult r = opsCustomerFeignApi.getCustomerInfoByName("北京");
//获取贩卖限制
		CommonResult r4 = opsProductFeignApi.findProductRestrictModelNoByModelNo("10-AFD3000-F02-J");
		int a = 0;
		System.out.println(123);
	}

	@Test
	void feignGZApi() {
		ResultVo<ShikomiVO> shikomiApi = productServiceFeignApi.canUseShikomi("3", "3", "123");

		int a = 0;
	}

	@Test
	void send() {
		try {
			purchaseExportTxtService.sendOrder();

		} catch (Exception e) {
			log.error("" + e);
		}
	}

	@Test
	public void getp() {
		try {
			List<PurchaseInfoForCancel> i = purchaseCancelService.getPurchase("H01TLA1", 230);
			log.info("i=" + i);
		} catch (OpsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void api() {

	}
}