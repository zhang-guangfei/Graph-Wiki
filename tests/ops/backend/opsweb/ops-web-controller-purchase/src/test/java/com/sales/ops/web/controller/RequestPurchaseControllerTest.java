package com.sales.ops.web.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.sales.ops.common.until.OkHttpAddTokenUtil;
import com.sales.ops.service.purchase.PurchaseCancelService;

/**
 * @author ：C14717
 * @version: $ @description：
 * @date ：Created in 2021/11/17 10:39
 */

public class RequestPurchaseControllerTest extends BaseTest {

	private static Logger log = LoggerFactory.getLogger(RequestPurchaseControllerTest.class);

	@Autowired
	private OkHttpAddTokenUtil okHttpAddTokenUtil;

	@Autowired
	private PurchaseCancelService purchaseCancelService;

	@Test
	public void tt12() {
		try {
			// purchaseApiService.impdata("Y32154", 7346);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 测试备案数据
	 */
	@Test
	public void tt() {
		/*
		 *
		 * @param dataUrl
		 * http://10.116.1.234:10100/service/sales/queryRecordInfoByNoBonded
		 * 
		 * @param plantMark 厂别：CN/BJ/AM.. 必填
		 * 
		 * @param materialType 物料类型：I:料件，E:成品，2:设备 非必填
		 * 
		 * @param list 型号集合，最大支持 500 个型
		 * 
		 * @param tokenUrl http://10.116.1.234:10300/auth/buoath/token
		 * 
		 * @param userName sales
		 * 
		 * @param password ZTZjZjY0NTM0ZmRhNGRiZmJlZDhhNjZjYjMyNmYzOWU=
		 */
		List list = new ArrayList();
		list.add("KQ2N04-99");
		list.add("10-KQN11-99");
		List arr = okHttpAddTokenUtil.getData(" http://10.116.1.234:10100/service/sales/queryRecordInfoByNoBonded",
				"XX", "XX", list, "http://10.116.1.234:10300/auth/buoath/token", "sales",
				"ZTZjZjY0NTM0ZmRhNGRiZmJlZDhhNjZjYjMyNmYzOWU=");
		// return opsCustomerFeignApi.getCustomerInfo("95001");
	}

	@Test
	public void mt() {
		if (Pattern.matches("(.*)-DN[A-Z][A-Z0-9][0-9][0-9][0-9](.*)", "A-DNAA999")) {
			log.info("");
			;
		}
	}

}