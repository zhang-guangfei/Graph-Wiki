package com.sales.ops.web.controller.wmOrder;

import com.alibaba.nacos.client.naming.utils.CollectionUtils;
import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.db.entity.OpsWmOrderTask;
import com.sales.ops.dto.inventory.DispatchForOrderItemInputDto;
import com.sales.ops.dto.order.OpsWmOrderTaskCondition;
import com.sales.ops.dto.util.CommonResult;
import com.sales.ops.service.wmOrder.WmOrderTaskFindService;
import com.sales.ops.service.wmOrder.WmOrderTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author ：C14717
 * @version: 1.0$
 * @description：ops提交wms要上传的数据
 * @date ：Created in 2021/10/27 17:31
 */
@CrossOrigin
@RestController
@RequestMapping("/wmOrderTask")
public class WmOrderTaskController {
    @Autowired
    private WmOrderTaskService wmOrderTaskService;

    @Autowired
    private WmOrderTaskFindService findOrderTaskService;

    @GetMapping("/updateFlagToThree")
    public void updateFlagToThree() {
        wmOrderTaskService.updateTaskFlagToThree();
    }

    /**
     * bugid: 17600 c14717 20250514
     */
    @GetMapping("/updateRcvdetailStatusTenToInit")
    public void updateRcvdetailStatusTenToInit() {
        wmOrderTaskService.updateRcvdetailStatusTenToInit();
    }
    @PostMapping("/search")
    public CommonResult<List<OpsWmOrderTask>> search(@RequestBody OpsWmOrderTaskCondition condition) {
        try {
            List<OpsWmOrderTask> result = findOrderTaskService.searchOpsWmOrderTaskByCondition(condition);
            return CollectionUtils.isEmpty(result) ?
                    CommonResult.failure("没有记录") : CommonResult.success(result);
        } catch (Exception e) {
            return CommonResult.failure("查询wm_order_task失败 __ "+e.getMessage());
        }
    }


    @PostMapping("/search/one")
    public CommonResult<List<OpsWmOrderTask>> searchOne(@RequestBody OpsWmOrderTaskCondition condition) {
        try {
            List<OpsWmOrderTask> result = findOrderTaskService.searchOpsWmOrderTaskByConditionOne(condition);
            return CollectionUtils.isEmpty(result) ?
                    CommonResult.failure("没有记录") : CommonResult.success(result);
        } catch (Exception e) {
            return CommonResult.failure("查询wm_order_task失败 __ "+e.getMessage());
        }
    }


    @PostMapping("/search/two")
    public CommonResult<List<OpsWmOrderTask>> searchTwo(@RequestBody OpsWmOrderTaskCondition condition) {
        try {
            List<OpsWmOrderTask> result = findOrderTaskService.searchOpsWmOrderTaskByConditionTwo(condition);
            return CollectionUtils.isEmpty(result) ?
                    CommonResult.failure("没有记录") : CommonResult.success(result);
        } catch (Exception e) {
            return CommonResult.failure("查询wm_order_task失败 __ "+e.getMessage());
        }
    }


    @PostMapping("/search/three")
    public CommonResult<List<OpsWmOrderTask>> searchThree(@RequestBody OpsWmOrderTaskCondition condition) {
        try {
            List<OpsWmOrderTask> result = findOrderTaskService.searchOpsWmOrderTaskByConditionThree(condition);
            return CollectionUtils.isEmpty(result) ?
                    CommonResult.failure("没有记录") : CommonResult.success(result);
        } catch (Exception e) {
            return CommonResult.failure("查询wm_order_task失败 __ "+e.getMessage());
        }
    }


    @PostMapping("/search/four")
    public CommonResult<List<OpsWmOrderTask>> searchFour(@RequestBody OpsWmOrderTaskCondition condition) {
        try {
            List<OpsWmOrderTask> result = findOrderTaskService.searchOpsWmOrderTaskByConditionFour(condition);
            return CollectionUtils.isEmpty(result) ?
                    CommonResult.failure("没有记录") : CommonResult.success(result);
        } catch (Exception e) {
            return CommonResult.failure("查询wm_order_task失败 __ "+e.getMessage());
        }
    }


    @PostMapping("/search/five")
    public CommonResult<List<OpsWmOrderTask>> searchFive(@RequestBody OpsWmOrderTaskCondition condition) {
        try {
            List<OpsWmOrderTask> result = findOrderTaskService.searchOpsWmOrderTaskByConditionFive(condition);
            return CollectionUtils.isEmpty(result) ?
                    CommonResult.failure("没有记录") : CommonResult.success(result);
        } catch (Exception e) {
            return CommonResult.failure("查询wm_order_task失败 __ "+e.getMessage());
        }
    }



    /*@PostMapping("/update")
    public CommonResult<String> update(@RequestBody OpsWmOrderTaskCondition condition) {
        try {
            wmOrderTaskService.updateOpsWmOrderTaskFlagByCondition(condition);
            return CommonResult.success();
        } catch (Exception ex) {
            return CommonResult.failure(null, ex.getMessage());
        }
    }*/


}
