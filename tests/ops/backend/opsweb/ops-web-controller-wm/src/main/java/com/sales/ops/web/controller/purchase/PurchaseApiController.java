package com.sales.ops.web.controller.purchase;

import cn.hutool.json.JSONUtil;
import com.alibaba.nacos.api.utils.StringUtils;
import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.dto.po.InterceptPoAutoReleaseInvDto;
import com.sales.ops.dto.po.PoOrderInfoDto;
import com.sales.ops.dto.purchase.PurchaseReplyInfo;
import com.sales.ops.dto.util.CommonResult;
import com.sales.ops.service.purchase.InterceptPoAutoReleaseInventoryService;
import com.sales.ops.service.purchase.PurchaseApiService;
import com.sales.ops.service.purchase.PurchaseInfoToPSIService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/wmPurchase")
public class PurchaseApiController {

    private final static Logger logger = LoggerFactory.getLogger(PurchaseApiController.class);

    @Autowired
    private PurchaseApiService purchaseApiService;

    @Autowired
    private InterceptPoAutoReleaseInventoryService interceptPoAutoService;
    @Autowired
    private PurchaseInfoToPSIService purchaseInfoToPSIService;

    /**
     * bugid: 17646 c14717 20250526
     */
    @RequestMapping(value = "/interceptPoAutoInv", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<String> interceptPoAutoInv() {
        try {
            String result = "base数据：";
            // 0.获取采购拦截状态能自动出库存的客单
            List<PoOrderInfoDto> basePoList = interceptPoAutoService.getBasePoList();
            result = result + basePoList.size();
            int sucessNum = 0;
            // 1.验证过滤采购和do的关联关系
            List<InterceptPoAutoReleaseInvDto> filterDatas = interceptPoAutoService.checkPoAndDoRelation(basePoList);
            if (CollectionUtils.isNotEmpty(filterDatas)) {
                for (InterceptPoAutoReleaseInvDto obj : filterDatas) {
                    try {
                        int i = interceptPoAutoService.handleDataDetail(obj);
                        sucessNum = sucessNum + i;
                    } catch (OpsException e) {
                        logger.error("interceptPoAutoInv失败：", e);
                    }
                }
            }
            result = result + ";处理成功：" + sucessNum;
            return CommonResult.success(result);
        } catch (Exception e) {
            logger.error("interceptPoAutoInv失败：", e);
            return CommonResult.failure("interceptPoAutoInv失败：" + e);
        }
    }


    /**
     * 提供广州更新 采购发送表的接口 ：供应商接单、预到货
     */
    @RequestMapping(value = "/updateInvoice", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult<String> updateInvoice(@RequestBody List<PurchaseReplyInfo> list) {
        try {
            Map<String, String> errorInfo = purchaseApiService.updateInvoice(list);
            return CommonResult.success(JSONUtil.toJsonStr(errorInfo));
        } catch (Exception e) {
            logger.error("updateInvoice失败：", e);
            return CommonResult.failure("updateInvoice失败：" + e);
        }
    }

    // 采购发票到货接口
    @RequestMapping(value = "/impdata", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult<String> impdata(@RequestParam("invoiceNo") String invoiceNo,
                                        @RequestParam("invoiceId") long invoiceId) {
        try {
            Integer result = purchaseApiService.impdata(invoiceNo, invoiceId);
            if (result != null) {
                return CommonResult.success("已处理：" + result + "条");
            } else {
                return CommonResult.failure("无数据");
            }
        } catch (Exception e) {
            logger.error("impdata失败：", e);
            return CommonResult.failure("impdata失败：" + e);
        }
    }

    // 补充缺失的采购单
    @RequestMapping(value = "/addDeletePurchase", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult<String> addDeletePurchase(@RequestParam("invoiceNo") String invoiceNo,
                                                  @RequestParam("invoiceId") long invoiceId) {
        if (StringUtils.isBlank(invoiceNo) || invoiceId <= 0) {
            return CommonResult.failure("发票号不能为空");
        }
        try {
            boolean result = purchaseApiService.addDeletePurchase(invoiceNo, invoiceId);
            if (result) {
                return CommonResult.success("已补充采购数据。");
            } else {
                return CommonResult.failure("无数据可补充");
            }
        } catch (Exception e) {
            logger.error("addDeletePurchase失败：", e);
            return CommonResult.failure("addDeletePurchase失败：" + e);
        }
    }


    @RequestMapping(value = "/pushPurchaseInvoiceInfoToPSI", method = RequestMethod.GET)
    public CommonResult<String> pushPurchaseInvoiceInfoToPSI() {
        try {
            String summary = purchaseInfoToPSIService.pushPurchaseInvoiceInfoToPSI();
            return CommonResult.success(summary);
        } catch (Exception e) {
            logger.error("pushPurchaseInvoiceInfoToPSI失败：", e);
            return CommonResult.failure("pushPurchaseInvoiceInfoToPSI失败：" + e);
        }
    }


}
