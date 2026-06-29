package com.smc.smccloud.web.controller;

import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.model.adapter.DataAuthoritySearchCondition;
import com.smc.smccloud.model.adapter.*;
import com.smc.smccloud.service.SMSAdapterService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Author: B90034
 * Date: 2022-03-17 13:54
 * Description:
 */
@RequestMapping("/cpfr/SMSAdapter")
@RestController
public class SMSAdapterController {

    @Resource
    private SMSAdapterService smsAdapterService;

    /**
     * 在库补库-
     * 根据客户代码和型号获取型号的BIN数、通用在库和专备 （北京bin数量、上海bin数量、广州bin数、通用在库、顾客在库数量）
     *
     * @param condition 客户代码和型号列表
     * @return 型号的BIN数、通用在库和专备
     */
    @RequestMapping(value = "/advanceInfo/findBinModelInfo", method = RequestMethod.POST)
    public ResultVo<Map<String, BinModelInfo>> findBinModelInfo(@RequestBody BinModelCondition condition) {
        return ResultVo.success(smsAdapterService.findBinModelInfo(condition));
    }

    /**
     * 查询BIN可补库客户
     *
     * @param condition DataAuthoritySearchCondition
     * @return BIN补库客户信息
     */
    @RequestMapping(value = "/advanceInfo/findAutoBinCustomerInfo", method = RequestMethod.POST)
    public ResultVo<List<AutoBinInfo>> findAutoBinCustomerInfo(@RequestBody DataAuthoritySearchCondition condition) {
        return ResultVo.success(smsAdapterService.findAutoBinCustomerInfo(condition));
    }

    /**
     * 客户在库补库--查询某客户的自动补库信息 （建议补库数量、Bom号、项目号）
     */
    @RequestMapping(value = "/advanceInfo/findAutoBinInfoByCustomer", method = RequestMethod.POST)
    public ResultVo<List<BinModelInfo>> findAutoBinInfoByCustomer(
            @RequestBody DataAuthoritySearchCondition dataAuthoritySearchCondition,
            @RequestParam("customerNo") String customerNo) {
        if (StringUtils.isBlank(customerNo)) {
            return ResultVo.failure("客户不可为空");
        }
        return ResultVo.success(smsAdapterService.findAutoBinInfoByCustomer(dataAuthoritySearchCondition, customerNo));
    }

    /**
     * 取消客户BIN自动补库
     */
    @RequestMapping(value = "/advanceInfo/findCancelAuto", method = RequestMethod.POST)
    public ResultVo<Boolean> findCancelAuto(@RequestBody UpdateAutoStatusCondition condition) {
        boolean result = smsAdapterService.cancelCustomerBinAutoReple(condition);
        if (result) {
            return ResultVo.success(true);
        }
        return ResultVo.failure("取消自动补库失败");
    }

    /**
     * 更新客户BIN的自动补库状态
     * @param condition 更新条件
     * @return 更新结果
     */
    @RequestMapping(value = "/advanceInfo/findUpdateAuto", method = RequestMethod.POST)
    public ResultVo<Boolean> findUpdateAuto(@RequestBody UpdateAutoStatusCondition condition) {
        boolean result = smsAdapterService.updateCustomerBinAutoRepleStatus(condition);
        if (result) {
            return ResultVo.success(true);
        }
        return ResultVo.failure("更新自动补库状态失败");
    }

    /**
     * 根据型号查询BinInfo
     *
     * @param modelNo 型号
     * @return BinInfo
     */
    @RequestMapping(value = "/product/findBinInfo", method = RequestMethod.GET)
    public ResultVo<List<BinInfo>> binInfo(@RequestParam("modelNo") String modelNo) {
        if (StringUtils.isNotBlank(modelNo)) {
            return ResultVo.success(smsAdapterService.getBinInfoByModelNo(modelNo));
        }
        return ResultVo.failure("查询型号不可为空");
    }

    /**
     * 查询型号的BIN或GSS品信息
     *
     */
    // Add by dengdenghui 2022-11-22 for bug-8738
    @RequestMapping(value = "/product/findBinProductInfo", method = RequestMethod.POST)
    public ResultVo<Map<String, List<Product>>> findBinModel(@RequestBody ProductCondition condition) {
        return smsAdapterService.listModelBinOrGSSInfo(condition);
    } // End

    /**
     * 查询型号的BIN或GSS品信息
     */
    @RequestMapping(value = "/product/findBinProduct", method = RequestMethod.POST)
    public ResultVo<List<Product>> listBinOrGSS(@RequestBody ProductCondition condition) {
        return smsAdapterService.listBinOrGSS(condition);
    }

    /**
     * 查询型号bin或gss品信息、bin安全库存数及有效库存数量
     */
    @RequestMapping(value = "/product/findProduct", method = RequestMethod.POST)
    public ResultVo<List<Product>> productInfo(@RequestBody ProductCondition condition) {
        return smsAdapterService.productInfo(condition);
    }

    /**
     *  add by A78027 2023-07-03 for bug-11274
     * @param dto
     * @return
     */
    @RequestMapping(value = "/stock/listStockForRepl", method = RequestMethod.POST)
    public ResultVo<List<StockInfoForReplVO>> listStockForRepl(@RequestBody StockQueryForReplDTO dto)
    {
        return smsAdapterService.listStockForRepl(dto);
    }

}
