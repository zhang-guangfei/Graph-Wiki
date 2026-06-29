package com.sales.ops.feign.purchase;

import com.sales.ops.db.entity.OpsPurchaseorder;
import com.sales.ops.dto.inquiry.InquiryQueryPurchaseDto;
import com.sales.ops.dto.purchase.PurchaseModifyApplyInfoDto;
import com.sales.ops.dto.purchase.RequestCancelDto;
import com.sales.ops.dto.util.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(name = "po-service", contextId = "PurchaseBatchEdit")
public interface PurchaseBatchEditFeignApi {


    @RequestMapping(value = "/purchaseBatchModify/getRequestInfo", method = RequestMethod.POST)
    CommonResult<List<PurchaseModifyApplyInfoDto>> getRequestInfo(@RequestBody List<String> ordernos);

    // 采购转定接口
    @RequestMapping(value = "/purchase/transOrder",method = RequestMethod.POST)
    public CommonResult updateTrans(@RequestBody OpsPurchaseorder opsPurchaseorder);


    // 采购删单接口
    @RequestMapping(value = "/purchaseApi/cancelPurchase",method = RequestMethod.POST)
    public CommonResult cancelPurchase(@RequestBody RequestCancelDto requestCancelDto);

    @RequestMapping(value = "/purchaseBatchModify/getPurchaseOrder", method = RequestMethod.POST)
    CommonResult<List<InquiryQueryPurchaseDto>> getPurchaseOrder(@RequestBody List<String> ordernos);

}
