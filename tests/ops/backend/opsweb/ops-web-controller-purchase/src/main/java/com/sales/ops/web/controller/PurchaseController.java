package com.sales.ops.web.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import com.sales.ops.dto.po.PoOrderNoDto;
import com.sales.ops.dto.po.core.TransTypeDto;
import com.sales.ops.dto.po.core.TransTypeParam;
import com.sales.ops.dto.purchase.ImpDataDto;
import com.sales.ops.service.core.TransService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.db.entity.OpsImpdata;
import com.sales.ops.db.entity.OpsPoTranstype;
import com.sales.ops.db.entity.OpsPurchaseorder;
import com.sales.ops.db.entity.OpsRequestpurchase;
import com.sales.ops.db.entity.PurchaseorderView;
import com.sales.ops.dto.order.FinishPoDto;
import com.sales.ops.dto.order.FinishPoListForDto;
import com.sales.ops.dto.purchase.DlvModDateDto;
import com.sales.ops.dto.query.PurchaseQO;
import com.sales.ops.dto.util.CommonResult;
import com.sales.ops.dto.util.PageModel;
import com.sales.ops.service.po.FinishPoService;
import com.sales.ops.service.purchase.PurchaseCompleteService;
import com.sales.ops.service.purchase.PurchaseService;
import com.sales.ops.webutil.BaseController;
import com.smc.smccloud.core.utils.FileUtil;

/**
 * @author B91717
 */
@CrossOrigin
@RestController
@RequestMapping("/purchase")
public class PurchaseController extends BaseController {

	@Resource
	private PurchaseService purchaseService;

	@Resource
	private FinishPoService finishPoService;

	@Resource
	private PurchaseCompleteService purchaseCompleteService;

    @Resource
    private TransService transService;

    @RequestMapping(value = "/getTransIds")
    @ResponseBody
    public CommonResult<List<OpsPoTranstype>> getTransIds(@RequestBody TransTypeParam param) {
        try {
            List<OpsPoTranstype> list = new ArrayList<>();
            List<TransTypeDto> transIds = transService.getTransIds(param.getSupplierId(), param.getWarehouse()
                    , param.getModelNo(), param.getOrderQty(), param.getOrdType(), param.getTranslation());
            if(CollectionUtils.isEmpty(transIds)){
                return CommonResult.failure("无数据");
            }
            if(CollectionUtils.isNotEmpty(transIds)){
                List<TransTypeDto> uniqueList = new ArrayList<>(transIds.stream()
                        .collect(Collectors.toMap(
                                TransTypeDto::getTransId,      // key：transId
                                tdto -> tdto,                    // value：整个对象
                                (existing, replacement) -> existing // 冲突时保留第一个
                        ))
                        .values());

                for(TransTypeDto obj : uniqueList){
                    OpsPoTranstype pt = new OpsPoTranstype();
                    pt.setId(obj.getTransId());
                    pt.setName(obj.getTransName());
                    list.add(pt);
                }
            }

            return CommonResult.success(list);
        } catch (Exception e) {
            return CommonResult.failure(e.getMessage());
        }
    }




	/**
	 * 采购单查询-初始页面
	 */
	@RequestMapping(value = "/findList")
	@ResponseBody
	public CommonResult findAll(@RequestBody PageModel<PurchaseQO> pageModel) {
		PageInfo<PurchaseorderView> OpsPurchaseList = purchaseService.findAll(pageModel);
		CommonResult commonResult = OpsPurchaseList.getList().isEmpty() ? CommonResult.success(OpsPurchaseList)
				: CommonResult.success(OpsPurchaseList);
		return commonResult;
	}

	/**
	 * 采购单转定采购单转定 修改供应商,运输方式，指定出库日的方法
	 */
	@RequestMapping(value = "/transOrder")
	@ResponseBody
	public CommonResult updateTrans(@RequestBody OpsPurchaseorder opsPurchaseorder) {
		try {
			Integer result = purchaseService.updateTrans(opsPurchaseorder);
			CommonResult commonResult = result.equals(0) || result == null ? CommonResult.failure("更新失败")
					: CommonResult.success(result);
			return commonResult;
		} catch (OpsException e) {
			// TODO Auto-generated catch block
			return CommonResult.failure(e.getMessage());
		}
	}

	/**
	 * 采购单变更交货期，只支持供应商为日本
	 */
	@RequestMapping(value = "/updateDelivery")
	@ResponseBody
	public CommonResult updateDelivery(@RequestBody OpsPurchaseorder opsPurchaseorder) {
		try {
			Integer result = purchaseService.updateDeliveryDate(opsPurchaseorder);
			CommonResult commonResult = result.equals(0) || result == null ? CommonResult.failure("更新失败")
					: CommonResult.success(result);
			return commonResult;
		} catch (OpsException e) {
			// TODO Auto-generated catch block
			return CommonResult.failure(e.getMessage());
		}
	}

	/**
	 * 采购单查询-采购单详情
	 */
	@RequestMapping(value = "/findByid")
	@ResponseBody
	public OpsPurchaseorder findByid(@RequestBody Long id) {
		OpsPurchaseorder result = purchaseService.getByid(id);
		return result;
	}

	/**
	 * 采购单详情-获取请购单信息
	 */
	@RequestMapping(value = "/findRequest")
	@ResponseBody
	public CommonResult findByRequest(@RequestBody Long id) {
		try {
			List<OpsRequestpurchase> result = purchaseService.getRequest(id);
			CommonResult commonResult = result.size() == 0 ? CommonResult.failure("无数据") : CommonResult.success(result);
			return commonResult;
		} catch (OpsException e) {
			// TODO Auto-generated catch block
			return CommonResult.failure(e.getMessage());
		}
	}

	/**
	 * 强制完纳功能
	 */
	@RequestMapping(value = "/compelComplete")
	@ResponseBody
	public CommonResult compelComplete(@RequestBody OpsPurchaseorder opsPurchaseorder) {
		try {
			purchaseService.compel(opsPurchaseorder);
			CommonResult commonResult = CommonResult.success();
			return commonResult;
		} catch (OpsException e) {
			// TODO Auto-generated catch block
			return CommonResult.failure(e.getMessage());
		}
	}

    /**
     * 采购单详情，查询到货信息
     * ADD订单
     */
    @RequestMapping(value = "/findGoodsDetailByOrder")
    @ResponseBody
    public CommonResult findGoods(@RequestBody PoOrderNoDto param) {
        try {
            List<ImpDataDto> result = purchaseService.getGoodsDetailByOrder(param);
            CommonResult commonResult = result.size() == 0 ? CommonResult.success(result)
                    : CommonResult.success(result);
            return commonResult;
        } catch (OpsException e) {
            return CommonResult.failure(e.getMessage());
        }
    }

	/**
	 * 采购单详情，查询到货信息
	 */
	@RequestMapping(value = "/findGoodsDetail")
	@ResponseBody
	public CommonResult findGoods(@RequestBody Long id) {
		try {
			List<ImpDataDto> result = purchaseService.getGoodsDetail(id);
			CommonResult commonResult = result.size() == 0 ? CommonResult.success(result)
					: CommonResult.success(result);
			return commonResult;
		} catch (OpsException e) {
			return CommonResult.failure(e.getMessage());
		}
	}

	/**
	 * 手工调用到货api
	 */
	@RequestMapping(value = "/apiRepo")
	@ResponseBody
	public CommonResult api(@RequestBody Map<String, Object> list) {
		try {
			// bug 8938,从wm服务调用接口返回成功失败
			CommonResult<String> result = purchaseService.apiRepo(list);
			return result;
		} catch (OpsException e) {
			return CommonResult.failure(e.getMessage());
		}
	}

	@RequestMapping(value = "/dlvDate")
	@ResponseBody
	public CommonResult dlvDate(@RequestBody OpsPurchaseorder purchase) {
		try {
			List<DlvModDateDto> dlvModDate = purchaseService.getDlvModDate(purchase.getOrderno(), purchase.getItemno(),
					purchase.getSplititemno());
			return CommonResult.success(dlvModDate);
		} catch (OpsException e) {
			return CommonResult.failure(e.getMessage());
		}
	}

	/**
	 * bug 11836 采购完纳，查询采购完纳实体信息
	 */
	@RequestMapping(value = "/getFinishPo")
	@ResponseBody
	public CommonResult getFinishPo(@RequestBody FinishPoDto info) {
		List<FinishPoListForDto> finishPoListForDtoList = purchaseCompleteService.getPoListPoNo(info);
		if (CollectionUtils.isEmpty(finishPoListForDtoList)) {
			return CommonResult.failure("根据采购单号，未能查到对应的完纳信息，请重试！");
		}
		return CommonResult.success(finishPoListForDtoList);
	}

	/**
	 * bug 11836 采购单完纳流程 1.前端校验输入完纳数量与已发数量的关系 2.调用订单接口，更改move表的P状态quantity
	 * 3.更新采购相关表的数量和状态（ops_requestpurchase,ops_purchaseOrder,ops_purchaseInvoice）
	 *
	 * @param finishDto
	 * @return
	 */
	@RequestMapping(value = "/finishPoHandel")
	@ResponseBody
	public CommonResult<String> finishPoHandel(@RequestBody FinishPoListForDto finishDto) {
		try {
			String result = purchaseCompleteService.doFinishPo(finishDto);
			if (StringUtils.isBlank(result)) {
				return CommonResult.success("采购完纳执行成功", null);
			}
			return CommonResult.failure("采购完纳失败：" + result);
		} catch (Exception e) {
			return CommonResult.failure("采购完纳失败：" + e);
		}
	}

	// 采购发单文件下载操作
	@RequestMapping(value = "/downloadFile", method = RequestMethod.GET)
	public void downloadFile(@RequestParam("filepath") String filepath, HttpServletResponse response) {
		FileUtil.downloadFileToResponse(filepath, response);
	}

}
