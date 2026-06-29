package com.sales.ops.web.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.sales.ops.db.entity.OpsPurchaseinvoice;
import com.sales.ops.dto.poDeliver.PoDeliverSelectDto;
import com.sales.ops.dto.poDeliver.SelectFilter;
import com.sales.ops.dto.purchase.PurchaseReplyInfo;
import com.sales.ops.dto.util.CommonResult;
import com.sales.ops.dto.util.PageModel;
import com.sales.ops.service.deliver.ChangeLogService;
import com.sales.ops.service.deliver.PoDeliverSelectService;
import com.sales.ops.service.deliver.PoFactService;

@CrossOrigin
@RestController
@RequestMapping("/purchaseDeliver")
public class PurchaseDeliverController {

	private final static Logger logger = LoggerFactory.getLogger(PurchaseDeliverController.class);

	@Autowired
	private ChangeLogService changeLogService;

	@Autowired
	private PoFactService poFactService;

	@Autowired
	private PoDeliverSelectService poDeliverSelectService;

	// 交付系统-供应商采购数据变更
	@RequestMapping(value = "/operateMainLog")
	public CommonResult<Integer> operateMainLog() {
		return changeLogService.operateMainLog();
	}

	// 查询purchaseinvoice数据
	@RequestMapping(value = "/getPurchaseInvoice")
	public CommonResult<List<OpsPurchaseinvoice>> getPurchaseInvoice(@RequestParam("orderno") String orderno,
			@RequestParam("itemno") Integer itemno, @RequestParam("splitno") Integer splitno) {
		List<OpsPurchaseinvoice> info = changeLogService.getPurchaseInvoice(orderno, itemno, splitno);
		return CommonResult.success(info);
	}

	@RequestMapping(value = "/getPurchaseInvoiceByPono")
	public CommonResult<List<OpsPurchaseinvoice>> getPurchaseInvoiceByPono(@RequestParam("pono") String pono,
			@RequestParam("lineitem") Integer lineitem) {
		List<OpsPurchaseinvoice> info = changeLogService.getPurchaseInvoiceByPono(pono, lineitem);
		return CommonResult.success(info);
	}

	// 预到货时发票写入fact表
	@RequestMapping(value = "/insertFactPre")
	public void insertFactPre(@RequestBody PurchaseReplyInfo o) {
		try {
			poFactService.insertFactPre(o);
		} catch (Exception e) {
			logger.error("insertFactPre invoice:" + o.getInvoiceno() + "(" + o.getInvoiceid() + ")" + " orderno:"
					+ o.getOrderno() + "-" + o.getItemno().toString()
					+ (o.getSplitno() == null ? "" : "-" + o.getSplitno().toString()) + " error:", e);
		}
	}

	// 前端查询采购交付信息
	@RequestMapping(value = "/selectDeliverInfo")
	public CommonResult<PageInfo<PoDeliverSelectDto>> selectDeliverInfo(@RequestBody PageModel<SelectFilter> info) {
		try {
			PageInfo<PoDeliverSelectDto> pageInfo = poDeliverSelectService.selectDeliverInfo(info);
			return CommonResult.success(pageInfo);
		} catch (Exception e) {
			return CommonResult.failure(e.getMessage());
		}
	}

	// 获取采购交付当前页详细数据
	@RequestMapping(value = "/selectDetailInfo")
	public CommonResult<List<PoDeliverSelectDto>> selectDetailInfo(@RequestBody List<PoDeliverSelectDto> list) {
		return CommonResult.success(poDeliverSelectService.selectDetailInfo(list));
	}

}
