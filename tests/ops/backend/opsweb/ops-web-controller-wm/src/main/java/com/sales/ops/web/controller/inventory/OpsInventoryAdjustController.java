package com.sales.ops.web.controller.inventory;


import cn.hutool.json.JSONUtil;
import com.github.pagehelper.PageInfo;
import com.sales.ops.common.opsexception.AdjustOrderException;
import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.db.entity.StockTransferPlan;
import com.sales.ops.db.entity.StockTransferPlanItem;
import com.sales.ops.dto.inventory.AdjustItemDTO;
import com.sales.ops.dto.inventory.AdjustParam;
import com.sales.ops.dto.inventory.AdjustType;
import com.sales.ops.dto.query.OpsStockTransferPlanQO;
import com.sales.ops.dto.util.CommonResult;
import com.sales.ops.dto.util.PageModel;
import com.sales.ops.service.inventory.AdjustInventoryService;
import com.sales.ops.serviceimpl.annotation.SysLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping(value = "/inventory")
public class OpsInventoryAdjustController {





    @Autowired
    private AdjustInventoryService adjustInventoryService;


    @PostMapping(value = "/getStockTransferPlanList")
    public CommonResult<PageInfo<StockTransferPlan>> getStockTransferPlanList(@RequestBody PageModel<OpsStockTransferPlanQO> pageModel) {
        try {
            PageInfo<StockTransferPlan> result = adjustInventoryService.searchStockTransferPlanByPage(pageModel);
            return  CommonResult.success(result);
        } catch (OpsException e) {
            return CommonResult.failure(e.getMessage());
        }
    }

    @PostMapping(value = "/getStockTransferPlanItemList")
    public CommonResult<List<StockTransferPlanItem>> getStockTransferPlanItemList(@RequestBody String planNo) {
        List<StockTransferPlanItem> list = adjustInventoryService.getStockTransferPlanItemList(planNo);
        return  CommonResult.success(list);

    }

    @PostMapping(value = "/delStockTransferPlan")
    public CommonResult<String> getStockTransferPlanItemList(@RequestBody List<String> planNoList ,@RequestParam("userName") String userName) {
        try {
            Integer list = adjustInventoryService.delPlan(planNoList,userName);
            return  CommonResult.success("删除成功");
        } catch (Exception e) {
            return  CommonResult.failure(e.getMessage());
        }

    }
    @SysLog("调账")
    @PostMapping(value = "/adjust")
    public CommonResult<AdjustParam> adjustInventory(@RequestBody AdjustParam param) {
        log.info("调账参数：==》{}", JSONUtil.toJsonStr(param));
        for (AdjustItemDTO adjustItem : param.getAdjustItems()) {
            if (adjustItem.getAdjustType() == AdjustType.Addition) {
                try {
                    adjustInventoryService.adjustItemForAdd(adjustItem, param.getUserDto());
                    adjustItem.setResult(1);//处理成功
                } catch (AdjustOrderException e) {
                    adjustItem.setResult(2);//重复提交
                    adjustItem.setMessage(e.getMessage());
                } catch (OpsException e) {//处理失败
                    log.error(e.getMessage(), e);
                    adjustItem.setResult(0);
                    adjustItem.setMessage(e.getMessage());
                } catch (Exception e) {//处理失败
                    log.error(e.getMessage(), e);
                    adjustItem.setResult(0);
                    adjustItem.setMessage(e.getMessage());
                }
            } else if (adjustItem.getAdjustType() == AdjustType.Subtraction) {
                try {
                    adjustInventoryService.adjustItemForSub(adjustItem, adjustItem.isAdjustAvailableInventory(), param.getUserDto());
                    adjustItem.setResult(1);//处理成功
                } catch (AdjustOrderException e) {//重复提交
                    adjustItem.setResult(2);
                    adjustItem.setMessage(e.getMessage());
                } catch (OpsException e) {//处理失败
                    log.error(e.getMessage(), e);
                    adjustItem.setResult(0);
                    adjustItem.setMessage(e.getMessage());
                } catch (Exception e) {//处理失败
                    log.error(e.getMessage(), e);
                    adjustItem.setResult(0);
                    adjustItem.setMessage(e.getMessage());
                }
            }
        }
        return CommonResult.success(param);
    }


}
