package com.sales.ops.web.controller;

import com.github.pagehelper.PageInfo;
import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.db.entity.HrOrganization;
import com.sales.ops.db.entity.OpsPoAutosendRemarkConfig;
import com.sales.ops.dto.query.PoAutoSendRemarkQO;
import com.sales.ops.dto.util.CommonResult;
import com.sales.ops.dto.util.PageModel;
import com.sales.ops.service.purchase.PoAutoSendRemarkService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author B91717
 * 采购自动发单，备注配置
 */
@CrossOrigin
@RestController
@RequestMapping("/po/autosendremark")
public class PoAutoSendRemarkController {

    @Resource
    private PoAutoSendRemarkService poAutoSendRemarkService;


    /**
     * 查询
     */
    @RequestMapping(value = "/findList")
    @ResponseBody
    public CommonResult findList(@RequestBody PageModel<PoAutoSendRemarkQO> pageModel) {
        PageInfo<OpsPoAutosendRemarkConfig> list = poAutoSendRemarkService.findAll(pageModel);
        CommonResult commonResult = list.getList().isEmpty()
                ? CommonResult.success(list)
                : CommonResult.success(list);
        return commonResult;
    }


    /**
     * 更新
     *
     * @return
     */
    @RequestMapping(value = "/updateData")
    @ResponseBody
    public CommonResult updateSuppily(@RequestBody OpsPoAutosendRemarkConfig opsPoAutosendRemarkConfig) {
        try {
            Integer result = poAutoSendRemarkService.updateData(opsPoAutosendRemarkConfig);
            CommonResult commonResult = result.equals(0) || result == null ? CommonResult.failure("更新失败")
                    : CommonResult.success(result);
            return commonResult;
        } catch (OpsException e) {
            // TODO Auto-generated catch block
            return CommonResult.failure(e.getMessage());
        }

    }

    /**
     * 新增
     *
     * @return
     */
    @RequestMapping(value = "/insertData", method = RequestMethod.POST)
    public CommonResult insert(@RequestBody OpsPoAutosendRemarkConfig opsPoAutosendRemarkConfig) {
        try {
            Integer result = poAutoSendRemarkService.insertData(opsPoAutosendRemarkConfig);
            CommonResult commonResult = result.equals(0) || result == null ? CommonResult.failure("写入失败")
                    : CommonResult.success(result);
            return commonResult;
        } catch (OpsException e) {
            // TODO Auto-generated catch block
            return CommonResult.failure(e.getMessage());
        }

    }

    /**
     * 删除
     *
     * @return
     */
    @RequestMapping(value = "/deleteData")
    @ResponseBody
    public CommonResult delete(@RequestBody OpsPoAutosendRemarkConfig opsPoAutosendRemarkConfig) {
        try {
            poAutoSendRemarkService.deleteData(opsPoAutosendRemarkConfig);
            return CommonResult.success();
        } catch (OpsException e) {
            // TODO Auto-generated catch block
            return CommonResult.failure(e.getMessage());
        }
    }


    /**
     *  bug13477,采购发单配置编辑功能修改
     * 恢复单据为正常状态，与删除方法同理
     * @param opsPoAutosendRemarkConfig
     * @return
     */
    @RequestMapping(value = "/restoreData")
    @ResponseBody
    public CommonResult restore(@RequestBody OpsPoAutosendRemarkConfig opsPoAutosendRemarkConfig) {
        try {
            poAutoSendRemarkService.restoreData(opsPoAutosendRemarkConfig);
            return CommonResult.success();
        } catch (OpsException e) {
            // TODO Auto-generated catch block
            return CommonResult.failure(e.getMessage());
        }
    }

    /**
     * 批量删除功能
     */
    @RequestMapping(value = "/deleteDataBatch")
    @ResponseBody
    public CommonResult deleteAll(@RequestBody List<OpsPoAutosendRemarkConfig> list) {
        try {
            poAutoSendRemarkService.deleteBatch(list);
            return CommonResult.success();
        } catch (OpsException e) {
            // TODO Auto-generated catch block
            return CommonResult.failure(e.getMessage());
        }
    }

    /**
     * 批量导入
     */
    @RequestMapping(value = "/importBatchData", method = RequestMethod.POST)
    public CommonResult importBindata(@RequestParam("file") MultipartFile file, @RequestParam("loginUser") String loginUser) {
        try {
            poAutoSendRemarkService.importFile(file,loginUser);
            return CommonResult.success();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            return CommonResult.failure(e.getMessage());
        }
    }

    @GetMapping(value = "/getDepartment")
    public List<HrOrganization> getDepartment() {
        return  poAutoSendRemarkService.findDepartment();
    }


}
