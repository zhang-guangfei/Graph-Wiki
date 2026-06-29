package com.sales.ops.web.controller;

import com.github.pagehelper.PageInfo;
import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.db.entity.OpsRequestpurchaseInterceptConfig;
import com.sales.ops.db.entity.OpsWarehouse;
import com.sales.ops.dto.purchase.OpsRequestPurchaseInterceptConfigVO;
import com.sales.ops.dto.util.CommonResult;
import com.sales.ops.dto.util.PageModel;
import com.sales.ops.service.purchase.InterceptService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author B91717
 * @deprecated 自定义拦截采购规则维护,增删改查功能
 */
@CrossOrigin
@RestController
@RequestMapping("/intercept")
public class InterceptController {

    @Resource
    private InterceptService interceptService;


    /**
     * 自定义拦截采购，查询
     *
     *
     */
    @RequestMapping(value = "/findList")
    @ResponseBody
    public CommonResult findList(@RequestBody PageModel<OpsRequestPurchaseInterceptConfigVO> pageModel) {
        PageInfo<OpsRequestpurchaseInterceptConfig> list = interceptService.findAll(pageModel);
        CommonResult commonResult = list.getList().isEmpty()
                ? CommonResult.success(list)
                : CommonResult.success(list);
        return commonResult;
    }


    /**
     * 自定义拦截采购规则维护，变更
     *
     * @return
     */
    @RequestMapping(value = "/updateData")
    @ResponseBody
    public CommonResult updateSuppily(@RequestBody OpsRequestpurchaseInterceptConfig opsRequestpurchaseInterceptConfig) {
        try {
            Integer result = interceptService.updateData(opsRequestpurchaseInterceptConfig);
            CommonResult commonResult = result.equals(0) || result == null ? CommonResult.failure("更新失败")
                    : CommonResult.success(result);
            return commonResult;
        } catch (OpsException e) {
            // TODO Auto-generated catch block
            return CommonResult.failure(e.getMessage());
        }

    }

    /**
     * 自定义拦截采购规则维护，新增
     *
     * @return
     */
    @RequestMapping(value = "/insertData", method = RequestMethod.POST)
    public CommonResult insert(@RequestBody OpsRequestpurchaseInterceptConfig opsRequestpurchaseInterceptConfig) {
        try {
            Integer result = interceptService.insertData(opsRequestpurchaseInterceptConfig);
            CommonResult commonResult = result.equals(0) || result == null ? CommonResult.failure("写入失败")
                    : CommonResult.success(result);
            return commonResult;
        } catch (OpsException e) {
            // TODO Auto-generated catch block
            return CommonResult.failure(e.getMessage());
        }

    }

    /**
     * 自定义拦截采购规则维护，删除
     *
     * @return
     */
    @RequestMapping(value = "/deleteData")
    @ResponseBody
    public CommonResult delete(@RequestBody OpsRequestpurchaseInterceptConfig opsRequestpurchaseInterceptConfig) {
        try {
            interceptService.deleteData(opsRequestpurchaseInterceptConfig);
            return CommonResult.success();
        } catch (OpsException e) {
            // TODO Auto-generated catch block
            return CommonResult.failure(e.getMessage());
        }
    }

    /**
     * 获取仓库名称
     */
    @RequestMapping(value = "/getWarehouse")
    @ResponseBody
    public CommonResult findSuppily() {
        List<OpsWarehouse> list =  interceptService.findWarehouse();
        CommonResult commonResult = list.size() == 0 ? CommonResult.success("没有记录")
                : CommonResult.success(list);
        return commonResult;
    }

    /**
     * 批量编辑功能
     * bug 10559 拦截配置 增加筛选，批量修改，删除，导出功能 B91717
     */
    @RequestMapping(value = "/editDataBatch", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult updateDataBatch(@RequestBody List<OpsRequestpurchaseInterceptConfig> list) {
        try {
            Integer result = interceptService.updateDataBatch(list);
            CommonResult commonResult = result.equals(0) || result == null ? CommonResult.failure("更新失败")
                    : CommonResult.success(result);
            return commonResult;
        } catch (OpsException e) {
            // TODO Auto-generated catch block
            return CommonResult.failure(e.getMessage());
        }
    }

    /**
     * 批量删除功能
     * bug 10559 拦截配置 增加筛选，批量修改，删除，导出功能 B91717
     */
    @RequestMapping(value = "/deleteDataBatch")
    @ResponseBody
    public CommonResult deleteAll(@RequestBody List<OpsRequestpurchaseInterceptConfig> list) {
        try {
            interceptService.deleteBatch(list);
            return CommonResult.success();
        } catch (OpsException e) {
            // TODO Auto-generated catch block
            return CommonResult.failure(e.getMessage());
        }
    }


}
