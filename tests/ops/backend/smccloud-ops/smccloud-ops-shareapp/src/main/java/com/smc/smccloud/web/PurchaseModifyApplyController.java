package com.smc.smccloud.web;


import com.github.pagehelper.PageInfo;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.model.page.Page;
import com.smc.smccloud.model.purchase.PurchaseModifyApproveVo;
import com.smc.smccloud.model.purchase.PurchaseModifyDO;
import com.smc.smccloud.model.purchase.PurchaseModifyRequest;
import com.smc.smccloud.service.PurchaseModifyApplyService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 *
 * @author smc
 * @since 2023-10-24
 */
@RestController
@RequestMapping("/shareapp/purchasemodify")
public class PurchaseModifyApplyController {
    @Resource
    private PurchaseModifyApplyService purchaseModifyService;

    @RequestMapping(value = "/findAll",method = RequestMethod.POST)
    public ResultVo<PageInfo<PurchaseModifyDO>> findAll(@RequestBody PurchaseModifyRequest purchaseModifyRequest, Page page){
        return purchaseModifyService.findAll(purchaseModifyRequest,page);
    }

    /**
     * 由门户发起的请求，李营超写到 采购modify表
     * 当业务处理完成后，记录处理结果，同时回调给tisk记录
     * 采购自己处理时，需要后台自己写回调接口 采购的转定方法等
     * 剩余采购的信息，需要写入时，自己查询再写入
     *
     * @param info
     * @return
     */
    // 运输方式转定，处理接口
    @PostMapping("/transTypeApproveData")
    public ResultVo<String> upApproveReplay(@RequestBody PurchaseModifyApproveVo info) {
        return purchaseModifyService.transTypeDeal(info);
    }

    // 变更供应商，指定出荷日，处理接口
    @PostMapping("/suppilyDataApproveData")
    public ResultVo<String> suppilyDateReplay(@RequestBody PurchaseModifyApproveVo info) {
        return purchaseModifyService.suppilyDateDeal(info);
    }

    // 采购删单处理接口
    @PostMapping("/deleteApproveData")
    public ResultVo<String> deleteApproveData(@RequestBody PurchaseModifyApproveVo info) {
        return purchaseModifyService.deleteDeal(info);
    }

    // 处理、暂不处理的接口
    @PostMapping("/layasideData")
    public ResultVo<String> layasideDataDeal(@RequestBody PurchaseModifyApproveVo info) {
        return purchaseModifyService.layasideData(info);
    }

    /**
     * 批量导入
     */
    @RequestMapping(value = "/importBatchData", method = RequestMethod.POST)
    public ResultVo<String> importBatchData(@RequestParam("file") MultipartFile file, @RequestParam("loginUser") String loginUser) {
        try {
            return purchaseModifyService.importFile(file,loginUser);
        } catch (Exception e) {
            return ResultVo.failure(e.getMessage());
        }
    }



}
