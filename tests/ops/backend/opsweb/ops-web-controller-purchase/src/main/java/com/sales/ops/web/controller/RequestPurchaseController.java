package com.sales.ops.web.controller;

import java.util.List;

import com.sales.ops.dto.po.PoMergeRuleConfigDto;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.db.entity.OpsPoTranstype;
import com.sales.ops.db.entity.OpsRequestpurchase;
import com.sales.ops.db.entity.RequestpurchaseView;
import com.sales.ops.db.entity.Supplier;
import com.sales.ops.dto.purchase.OpsRequestpurchaseDto;
import com.sales.ops.dto.purchase.RequestFilterCondition;
import com.sales.ops.dto.purchase.SplitCondition;
import com.sales.ops.dto.query.RequestPurchaseQO;
import com.sales.ops.dto.util.CommonResult;
import com.sales.ops.dto.util.PageModel;
import com.sales.ops.service.purchase.PurchaseExportTxtService;
import com.sales.ops.service.purchase.RequestHandleService;
import com.sales.ops.service.purchase.RequestPurchaseService;
import com.sales.ops.webutil.BaseController;

@CrossOrigin
@RestController
@RequestMapping("/requestPurchase")
public class RequestPurchaseController extends BaseController {

	private final static Logger logger = LoggerFactory.getLogger(RequestPurchaseController.class);

	@Autowired
	private RequestPurchaseService requestPurchaseService;

	@Autowired
	private PurchaseExportTxtService purchaseExportTxtService;

	@Autowired
	private RequestHandleService requestIntercept;

	/**
	 * bugid:17619
	 * 查询合并采购配置
	 * @return
	 */
	@RequestMapping(value = "/findMergeConfig")
	public CommonResult<List<PoMergeRuleConfigDto>> findMergeConfig(){
		List<PoMergeRuleConfigDto> mergeConfigList = requestPurchaseService.getMergeConfig();
		return CollectionUtils.isNotEmpty(mergeConfigList)?CommonResult.success(mergeConfigList):CommonResult.failure("无数据");
	}

	/**
	 * bugid:17619
	 * 更新合并采购配置
	 * @return
	 */
	@RequestMapping(value = "/updateMergeConfig")
	@ResponseBody
	public CommonResult<String> updateMergeConfig(@RequestBody List<PoMergeRuleConfigDto> list){
		try {
			requestPurchaseService.updateMergeConfig(list);
			return CommonResult.success();
		} catch (OpsException e) {
			return CommonResult.failure(e.getMessage());
		} catch (Exception ee){
			return CommonResult.failure(ee.getMessage());
		}

	}


	/**
	 * 请购单查询-初始页面
	 *
	 * @return
	 */
	@RequestMapping(value = "/findRequestList")
	@ResponseBody
	public CommonResult findAll(@RequestBody PageModel<RequestPurchaseQO> pageModel) {
		PageInfo<OpsRequestpurchase> OpsRequestpurchaseList = requestPurchaseService.findAll(pageModel);
		CommonResult commonResult = OpsRequestpurchaseList.getList().isEmpty()
				? CommonResult.success(OpsRequestpurchaseList) : CommonResult.success(OpsRequestpurchaseList);
		return commonResult;
	}

	/**
	 * 查询待处理请购单，statecode=0
	 *
	 * @return
	 */
	@RequestMapping(value = "/preProccessList")
	@ResponseBody
	public CommonResult findPreProccess(@RequestBody PageModel<RequestPurchaseQO> pageModel) {
		PageInfo<RequestpurchaseView> OpsRequestpurchaseList = requestPurchaseService.findPreProccessList(pageModel);
		CommonResult commonResult = OpsRequestpurchaseList.getList().isEmpty()
				? CommonResult.success(OpsRequestpurchaseList) : CommonResult.success(OpsRequestpurchaseList);
		return commonResult;
	}

	/**
	 * 请购单全部处理
	 */
	@RequestMapping(value = "/requestConfirmAll")
	@ResponseBody
	public CommonResult requestConfirmAll(@RequestBody RequestPurchaseQO requestPurchaseQO) {
		try {
			Integer result = requestPurchaseService.ConfirmAll(requestPurchaseQO);
			CommonResult commonResult = result.equals(0) || result == null ? CommonResult.failure("更新失败")
					: CommonResult.success(result);
			return commonResult;
		} catch (OpsException e) {
			// TODO Auto-generated catch block
			return CommonResult.failure(e.getMessage());
		}
	}

	/**
	 * 请购单直接转到采购表，不经过合并采购页面
	 */
	@RequestMapping(value = "/requestToPurchase")
	@ResponseBody
	public CommonResult requestToPurchase(@RequestBody RequestPurchaseQO requestPurchaseQO) {
		try {
			Integer result = requestPurchaseService.toPurchase(requestPurchaseQO);
			// bug14442 调整或语句前后顺序，先判断是否为null
			CommonResult commonResult = result == null || result.equals(0) ? CommonResult.failure("更新失败")
					: CommonResult.success(result);
			return commonResult;
		} catch (Exception e) {
			return CommonResult.failure(e.getMessage());
		}
	}

	/**
	 * shikomi查询
	 *
	 * @return
	 */
	@RequestMapping(value = "/findRequestShikomiList")
	@ResponseBody
	public CommonResult findShikomiList(@RequestBody PageModel<RequestPurchaseQO> pageModel) {
		PageInfo<OpsRequestpurchase> opsRequestpurchaseList = requestPurchaseService.findShikomiList(pageModel);
		return CommonResult.success(opsRequestpurchaseList);
	}

	/**
	 * 变更shikomi拦截状态
	 *
	 * @return
	 */
	@RequestMapping(value = "/shikomiPass")
	@ResponseBody
	public CommonResult shikomiPass(@RequestBody OpsRequestpurchase opsRequestpurchase) {
		try {
			Integer result = requestPurchaseService.shikomiPass(opsRequestpurchase);
			CommonResult commonResult = result.equals(0) || result == null ? CommonResult.failure("shikomi放行失败，请重试！")
					: CommonResult.success(result);
			return commonResult;
		} catch (OpsException e) {
			return CommonResult.failure(e.getMessage());
		}

	}

	/**
	 * 请购单查询-拦截请购单放行
	 *
	 * @return
	 */
	@RequestMapping(value = "/orderPass")
	@ResponseBody
	public CommonResult orderPass(@RequestBody OpsRequestpurchase opsRequestpurchase) {
		try {
			Integer result = requestPurchaseService.requestPass(opsRequestpurchase, false);
			CommonResult commonResult = result.equals(0) || result == null ? CommonResult.failure("拦截放行失败")
					: CommonResult.success(result);
			return commonResult;
		} catch (OpsException e) {
			// TODO Auto-generated catch block
			return CommonResult.failure(e.getMessage());
		}
	}

	/**
	 * bug12272 拦截订单批量放行 只变更采购单的指定出荷日
	 *
	 * @return
	 */
	@RequestMapping(value = "/orderpassBatch")
	@ResponseBody
	public CommonResult orderPassBatch(@RequestBody List<OpsRequestpurchase> list) {
		try {
			Integer result = requestPurchaseService.requestPassBatch(list);
			CommonResult commonResult = result.equals(0) || result == null ? CommonResult.failure("更新失败")
					: CommonResult.success(result);
			return commonResult;
		} catch (OpsException e) {
			// TODO Auto-generated catch block
			return CommonResult.failure(e.getMessage());
		}
	}

	/**
	 * 拦截订单还原功能
	 *
	 * @return
	 */
	@RequestMapping(value = "/interceptRestore")
	@ResponseBody
	public CommonResult interceptRestore(@RequestBody OpsRequestpurchase opsRequestpurchase) {
		try {
			Integer result = requestPurchaseService.restore(opsRequestpurchase);
			CommonResult commonResult = result.equals(0) || result == null ? CommonResult.failure("更新失败")
					: CommonResult.success(result);
			return commonResult;
		} catch (OpsException e) {
			// TODO Auto-generated catch block
			return CommonResult.failure(e.getMessage());
		}
	}

	/**
	 * bug12272 拦截订单批量还原功能
	 *
	 * @return
	 */
	@RequestMapping(value = "/restoreBatch")
	@ResponseBody
	public CommonResult restoreBatch(@RequestBody List<OpsRequestpurchase> list) {
		try {
			Integer result = requestPurchaseService.restoreBatch(list);
			CommonResult commonResult = result.equals(0) || result == null ? CommonResult.failure("更新失败")
					: CommonResult.success(result);
			return commonResult;
		} catch (OpsException e) {
			// TODO Auto-generated catch block
			return CommonResult.failure(e.getMessage());
		}
	}

	/**
	 * 请购单查询-变更供应商，运输方式，交货期
	 *
	 * @return
	 */
	@RequestMapping(value = "/updateSuppily")
	@ResponseBody
	public CommonResult updateSuppily(@RequestBody OpsRequestpurchase opsRequestpurchase) {
		try {
			Integer result = requestPurchaseService.updateSuppilyTrans(opsRequestpurchase);
			CommonResult commonResult = result.equals(0) || result == null ? CommonResult.failure("更新失败")
					: CommonResult.success(result);
			return commonResult;
		} catch (OpsException e) {
			// TODO Auto-generated catch block
			return CommonResult.failure(e.getMessage());
		}

	}

	/**
	 * 请购单查询-请购单详情
	 *
	 * @return
	 */
	@RequestMapping(value = "/findByOrderNo")
	@ResponseBody
	public List<OpsRequestpurchase> findByOrderNo(@RequestBody String orderno) {
		List<OpsRequestpurchase> result = requestPurchaseService.getByOrderNo(orderno);
		return result;
	}

	/**
	 * 获取供应商列表
	 */
	@RequestMapping(value = "/getSuppily")
	@ResponseBody
	public CommonResult findSuppily() {
		List<Supplier> list = requestPurchaseService.findSuppily();
		CommonResult commonResult = list.size() == 0 ? CommonResult.success("没有记录") : CommonResult.success(list);
		return commonResult;
	}

	/**
	 * 获取运输方式列表
	 */
	@RequestMapping(value = "/getTrans")
	@ResponseBody
	public CommonResult findTrans() {
		List<OpsPoTranstype> list = requestPurchaseService.findTrans();
		CommonResult commonResult = list.size() == 0 ? CommonResult.success("没有记录") : CommonResult.success(list);
		return commonResult;
	}

	/**
	 * 变更待处理清单状态到待采购状态 单条接口
	 */
	@RequestMapping(value = "/requestConfirmOne")
	@ResponseBody
	public CommonResult requestConfirmOne(@RequestBody OpsRequestpurchase opsRequestpurchase) {
		Integer result = requestPurchaseService.requestConfirmOne(opsRequestpurchase);
		CommonResult commonResult = result.equals(0) || result == null ? CommonResult.failure("更新失败")
				: CommonResult.success(result);
		return commonResult;
	}

	/**
	 * 变更待处理清单状态到待采购状态 批量接口
	 */
	@RequestMapping(value = "/requestConfirm")
	@ResponseBody
	public CommonResult requestConfirmBatch(@RequestBody List<OpsRequestpurchase> list) {
		Integer result = requestPurchaseService.requestConfirm(list);
		CommonResult commonResult = result.equals(0) || result == null ? CommonResult.failure("更新失败")
				: CommonResult.success(result);
		return commonResult;
	}

	/**
	 * 待处理采购单，批量编辑功能
	 */
	@RequestMapping(value = "/updateRequestBatch", method = RequestMethod.POST)
	public CommonResult updateRequestBatch(@RequestBody List<OpsRequestpurchase> list) {
		try {
			Integer result = requestPurchaseService.updateRequestPurchaseBatch(list);
			CommonResult commonResult = result.equals(0) || result == null ? CommonResult.failure("更新失败")
					: CommonResult.success(result);
			return commonResult;
		} catch (OpsException e) {
			// TODO Auto-generated catch block
			return CommonResult.failure(e.getMessage());
		}
	}

	/**
	 * 合并采购初始方法
	 *
	 * @return
	 * @throws OpsException
	 */
	@RequestMapping("search")
	public CommonResult search() {
		try {
			JSONObject OpsRequestpurchaseList = requestPurchaseService.mergePurchase();
			CommonResult commonResult = OpsRequestpurchaseList.size() == 0 ? CommonResult.success("没有记录")
					: CommonResult.success(OpsRequestpurchaseList);
			return commonResult;
		} catch (Exception ex) {
			// TODO Auto-generated catch block
			return CommonResult.failure(ex.getMessage());
		}
	}

	/**
	 * 获取拆分节点
	 *
	 * @return
	 */
	@RequestMapping(value = "/splitPurchase")
	@ResponseBody
	public CommonResult splitPurchase(@RequestBody SplitCondition condition) {
		List<OpsRequestpurchaseDto> splitNewList = requestPurchaseService.splitPurchase(condition);
		CommonResult commonResult = splitNewList.size() == 0 ? CommonResult.success("没有记录")
				: CommonResult.success(splitNewList);
		return commonResult;
	}

	/**
	 * 手工合并方法
	 *
	 * @return
	 */
	@RequestMapping(value = "/artificialMerge")
	@ResponseBody
	public CommonResult artificialMergeData(@RequestBody List<OpsRequestpurchaseDto> list) {
		List<OpsRequestpurchaseDto> mergeList;
		try {
			mergeList = requestPurchaseService.artificialMerge(list);
			CommonResult commonResult = mergeList.size() == 0 ? CommonResult.success("没有记录")
					: CommonResult.success(mergeList);
			return commonResult;
		} catch (OpsException ex) {
			// TODO Auto-generated catch block
			return CommonResult.failure(ex.getMessage());
		}

	}

	/**
	 * 单条执行采购的方法
	 */
	@RequestMapping(value = "/toPurchaseOne")
	@ResponseBody
	public CommonResult mergeList(@RequestBody List<OpsRequestpurchaseDto> list) {
		Integer result;
		try {
			result = requestPurchaseService.executePurchaseOne(list);
			/**
			 * 2022-05-07 废弃之前的采购发送任务，改为点击采购摁扭后调用采购发送方法
			 */
			CommonResult commonResult = result == 0 ? CommonResult.failure("采购单发送异常，请重试")
					: CommonResult.success(result);
			return commonResult;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("toPurchaseOne error:", e);
			return CommonResult.failure(e.getMessage());
		}

	}

	/**
	 * 合并执行采购方法
	 */
	@RequestMapping(value = "/toPurchase")
	@ResponseBody
	public CommonResult executePurchaseMerge(@RequestBody List<OpsRequestpurchaseDto> list) {
		Integer mergeSize;
		try {
			mergeSize = requestPurchaseService.executePurchaseMerge(list, "M");
			/**
			 * 2022-05-07 废弃之前的采购发送任务，改为点击采购摁扭后调用采购发送方法
			 */
			// if (mergeSize !=0) {
			// try {
			// purchaseExportTxtService.sendOrder();
			// } catch (Exception e) {
			// e.printStackTrace();
			// }
			// }
			CommonResult commonResult = mergeSize == 0 ? CommonResult.failure("采购单发送异常，请重试")
					: CommonResult.success(mergeSize);
			return commonResult;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("toPurchase error:", e);
			return CommonResult.failure(e.getMessage());
		}
	}

	/**
	 * 获取可用SHIKOMI列表
	 *
	 * @return
	 */
	@RequestMapping(value = "/getShikomiList")
	@ResponseBody
	public CommonResult findShikomiList() {
		List<String> shikomiList = requestPurchaseService.getShikomiList();
		CommonResult commonResult = shikomiList.size() == 0 ? CommonResult.success("没有记录")
				: CommonResult.success(shikomiList);
		return commonResult;
	}

	/**
	 * 采购拦截编辑备注信息
	 *
	 * @return
	 */
	@RequestMapping(value = "/editRemark")
	@ResponseBody
	public CommonResult editRemark(@RequestBody OpsRequestpurchase opsRequestpurchase) {
		Integer result = requestPurchaseService.editInterceptRemark(opsRequestpurchase);
		CommonResult commonResult = result == 0 ? CommonResult.success("没有记录") : CommonResult.success(result);
		return commonResult;
	}

	/**
	 * 后端筛选方法
	 */
	@RequestMapping(value = "/filterData")
	@ResponseBody
	public CommonResult filterData(@RequestBody RequestFilterCondition condition) {
		RequestFilterCondition reasultList;
		try {
			reasultList = requestPurchaseService.filterChange(condition);
			CommonResult commonResult = reasultList == null ? CommonResult.failure("筛选失败")
					: CommonResult.success(reasultList);
			return commonResult;
		} catch (OpsException ex) {
			// TODO Auto-generated catch block
			return CommonResult.failure(ex.getMessage());
		}

	}

	/**
	 * 后端排序方法
	 */
	@RequestMapping(value = "/sortData")
	@ResponseBody
	public CommonResult sortData(@RequestBody RequestFilterCondition condition) {
		RequestFilterCondition reasultList;
		try {
			reasultList = requestPurchaseService.sortChange(condition);
			CommonResult commonResult = reasultList == null ? CommonResult.failure("筛选失败")
					: CommonResult.success(reasultList);
			return commonResult;
		} catch (OpsException ex) {
			// TODO Auto-generated catch block
			return CommonResult.failure(ex.getMessage());
		}

	}

	/**
	 * bug 11997 采购自动发单，暂不处理功能
	 */
	@RequestMapping(value = "/unHandle")
	@ResponseBody
	public CommonResult unHandleRequest(@RequestBody List<OpsRequestpurchase> list) {
		try {
			Integer result = requestIntercept.requestIntercept(list);
			CommonResult commonResult = result == 0 || result == null ? CommonResult.failure("更新失败")
					: CommonResult.success(result);
			return commonResult;
		} catch (OpsException ex) {
			return CommonResult.failure(ex.getMessage());
		}
	}

}
