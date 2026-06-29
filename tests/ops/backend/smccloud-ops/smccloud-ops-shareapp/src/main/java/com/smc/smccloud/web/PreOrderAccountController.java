package com.smc.smccloud.web;

import com.github.pagehelper.PageInfo;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.utils.BeanCopyUtil;
import com.smc.smccloud.model.preaccount.HandlePreOrderAccountParam;
import com.smc.smccloud.model.prestock.*;
import com.smc.smccloud.service.PreOrderAccountConfigService;
import com.smc.smccloud.service.PreOrderAccountHandService;
import com.smc.smccloud.service.PreOrderAccountService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author wuweidong
 * @create 2023/12/29 10:14
 * @description
 */
@Slf4j
@RequestMapping(value = "/shareapp/preorderaccount")
@RestController
public class PreOrderAccountController {
    @Resource
    private PreOrderAccountService preOrderAccountService;

    @Resource
    private PreOrderAccountConfigService preOrderAccountConfigService;

    @Resource
    private PreOrderAccountHandService preOrderAccountHandService;

    /**
     * 查询主表
     *
     * @param request 查询条件
     * @return 申请列表
     */
    @RequestMapping(value = "/listPreOrderAccount", method = RequestMethod.POST)
    public ResultVo<PageInfo<PreOrderAccountDTO>> listPreOrderAccount(@RequestBody PreOrderAccountRequest request) {
        return preOrderAccountService.listPreOrderAccount(request);
    }

    @RequestMapping(value = "/exportPreOrderAccount", method = RequestMethod.POST)
    public void exportPreOrderAccount(@RequestBody PreOrderAccountRequest request, HttpServletResponse response) {
        preOrderAccountService.exportPreOrderAccount(request, response);
    }

    /**
     * 查询详细
     *
     * @param request 查询条件
     * @return 申请列表
     */
    @RequestMapping(value = "/listPreOrderDetail", method = RequestMethod.POST)
    public ResultVo<PageInfo<PreOrderAccountDetailDTO>> listPreOrderDetail(@RequestBody PreOrderAccountRequest request) {
        return preOrderAccountService.listPreOrderDetail(request);
    }

    @RequestMapping(value = "/exportPreOrderDetail", method = RequestMethod.POST)
    public void exportPreOrderDetail(@RequestBody PreOrderAccountRequest request, HttpServletResponse response) {
        preOrderAccountService.exportPreOrderDetail(request, response);
    }

    /**
     * 调拨处理
     *
     * @param ids
     * @return
     */
    @RequestMapping(value = "/handleTransferByIds", method = RequestMethod.POST)
    public ResultVo<String> handleTransferByIds(@RequestParam("ids") List<Long> ids) {
        return preOrderAccountService.handleTransferByIds(ids);
    }

    /**
     * 查询延期设置
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/listPreOrderAccountConfig", method = RequestMethod.POST)
    public ResultVo<PageInfo<PreOrderAccountConfigDO>> listPreOrderAccountConfig(@RequestBody PreOrderAccountRequest request) {
        return preOrderAccountConfigService.listPreOrderAccountConfig(request);
    }

    /**
     * 追加更新
     *
     * @param dto
     * @return
     */
    @RequestMapping(value = "/writeAndUpdatePreOrderAccountConfig", method = RequestMethod.POST)
    public ResultVo<String> writeAndUpdatePreOrderAccountConfig(@RequestBody PreOrderAccountConfigDTO dto) {
        PreOrderAccountConfigDO configDO = BeanCopyUtil.copy(dto, PreOrderAccountConfigDO.class);
        return preOrderAccountConfigService.writeAndUpdatePreOrderAccountConfig(configDO);
    }

    @RequestMapping(value = "/importPreOrderAccountConfig", method = RequestMethod.POST)
    public ResultVo<String> importPreOrderAccountConfig(@RequestParam("file") MultipartFile file) {
        return preOrderAccountConfigService.importPreOrderAccountConfig(file);
    }

    @RequestMapping(value = "/exportPreOrderAccountConfig", method = RequestMethod.POST)
    public void exportPreOrderAccountConfig(@RequestBody PreOrderAccountRequest request, HttpServletResponse response) {
        preOrderAccountConfigService.exportPreOrderAccountConfig(request, response);
    }

    /**
     * 门户提交清算和延期数据。
     */
    @RequestMapping(value = "/updatePreOrderAccountFromSales", method = RequestMethod.POST)
    public ResultVo<String> updatePreOrderAccountFromSales(@RequestBody PreOrderAccountDetailDTO detailDTO) {
        return preOrderAccountService.updatePreOrderAccountFromSales(detailDTO);
    }

    @PostMapping("/handlePreOrderAccountByIds")
    public ResultVo<String> handlePreOrderAccountByIds(@RequestBody HandlePreOrderAccountParam param) {
        return preOrderAccountHandService.handlePreOrderAccountByIds(param);
    }

    /**
     * 先行在库预决算处理(不推送门户的数据)
     * @param param
     * @return
     */
    @PostMapping("/handNotPushSalePreAccount")
    public ResultVo<String> handNotPushSalePreAccount(@RequestBody HandlePreOrderAccountParam param) {
        return preOrderAccountHandService.handNotPushSalePreAccount(param);
    }

    /**
     * 申请记录明细查询
     * @param request
     * @return
     */
    @RequestMapping(value = "/listPreOrderApplyDetail", method = RequestMethod.POST)
    public ResultVo<PageInfo<PreOrderAccountDetailDTO>> listPreOrderApplyDetail(@RequestBody PreOrderAccountRequest request) {
        return preOrderAccountHandService.listPreOrderApplyDetail(request);
    }

    /**
     * 申请记录明细导出
     * @param request
     * @param response
     */
    @RequestMapping(value = "/exportPreOrderApplyDetail", method = RequestMethod.POST)
    public void exportPreOrderApplyDetail(@RequestBody PreOrderAccountRequest request, HttpServletResponse response) {
        preOrderAccountHandService.exportPreOrderApplyDetail(request, response);
    }

    /**
     * 提前决算
     */
    @RequestMapping(value = "/advancedHand", method = RequestMethod.POST)
    public ResultVo<String> advancedHand(@RequestBody HandlePreOrderAccountParam param) {
        return preOrderAccountHandService.advancedHand(param);
    }

    @GetMapping("/downYQPZTemplate")
    public void downYQPZTemplate(HttpServletResponse response) {
        preOrderAccountHandService.downYQPZTemplate(response);
    }

    @GetMapping("/deletePreOrderAccountConfigById")
    public ResultVo<String> deletePreOrderAccountConfigById(@RequestParam("id") Long id) {
        return preOrderAccountConfigService.deletePreOrderAccountConfigById(id);
    }
}
