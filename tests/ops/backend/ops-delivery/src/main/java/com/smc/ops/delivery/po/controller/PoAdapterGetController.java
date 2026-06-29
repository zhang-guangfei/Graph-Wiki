package com.smc.ops.delivery.po.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sales.ops.dto.poDeliver.ChangeFromSupplier;
import com.sales.ops.dto.util.CommonResult;
import com.smc.ops.delivery.po.service.PoAdapterGetService;

@RestController
@CrossOrigin
@RequestMapping("/po/poadapter")
public class PoAdapterGetController {

	private final static Logger logger = LoggerFactory.getLogger(PoAdapterGetController.class);

	@Autowired
	private PoAdapterGetService poAdapterGetService;

	@RequestMapping("/getWaitOperateChange")
	public CommonResult<List<ChangeFromSupplier>> getWaitOperateChange(@RequestParam("id") long id,
			@RequestParam("endid") long endid) {
		try {
			return CommonResult.success(poAdapterGetService.getWaitOperateChange(id, endid));
		} catch (Exception e) {
			logger.error("PoAdapterGetService,error:", e);
			return CommonResult.failure("getWaitOperateChange,error:" + e.getMessage());
		}

	}
}
