package com.sales.ops.web.controller.wmOrder;


import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.db.entity.OpsRo;
import com.sales.ops.db.entity.OpsRoBarcode;
import com.sales.ops.db.entity.OpsRoItem;
import com.sales.ops.dto.inventory.WmRoConfirmDto;
import com.sales.ops.dto.order.OpsRoAddItemDto;
import com.sales.ops.dto.order.OpsWarehouseRoDto;
import com.sales.ops.dto.util.CommonResult;
import com.sales.ops.dto.util.PageModel;
import com.sales.ops.service.dispatch.rodispatch.RoDispatcherService;
import com.sales.ops.service.log.OpsExceptionHandleService;
import com.sales.ops.service.log.OpsRoBarcodeService;
import com.sales.ops.service.wmOrder.BaseRoService;
import com.sales.ops.service.wmOrder.OpsRoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

/**
 * @author ：C14717
 * @version: 1.0$
 * @description：收货指令
 * @date ：Created in 2021/9/26 11:22
 */
@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/warehouseManage/ro")
public class OpsRoController {

    @Autowired
    private OpsRoService opsRoService;
    @Autowired
    private OpsExceptionHandleService opsExceptionHandleService;
    @Autowired
    private OpsRoBarcodeService opsRoBarcodeService;
    @Autowired
    private BaseRoService baseRoService;
    @Autowired
    private RoDispatcherService roDispatcherService;

    /**
     * 条件查询
     */
    @PostMapping("/search")
    public CommonResult search(@RequestBody PageModel<OpsWarehouseRoDto> pageModel) {
        PageInfo<OpsRo> result = opsRoService.findRoByPage(pageModel);
        return CollectionUtils.isEmpty(result.getList()) ?
                CommonResult.success("没有记录") : CommonResult.success(result);
    }

    /**
     * 查询明细
     *
     * @param id
     * @return
     */
    @PostMapping("/search_item")
    public CommonResult searchItem(@RequestBody String id) {
        List<OpsRoItem> result = baseRoService.findRoItemsByRoId(id);
        return CollectionUtils.isEmpty(result) ?
                CommonResult.success("没有记录") : CommonResult.success(result);
    }


    /**
     * ops提交wmsRO和ROitem
     *
     * @param roId
     * @return
     */
    @PostMapping("/findRoToWms")
    public CommonResult<OpsRoAddItemDto> findRoToWms(@RequestBody String roId) {
        OpsRoAddItemDto result = opsRoService.findRoToWms(roId);
        return result == null ?
                CommonResult.failure("没有记录") : CommonResult.success(result);
    }

    /**
     * ops提交wmsBarcode
     *
     * @param limit
     * @return
     */
    @PostMapping("/findRoBarCodeToWms")
    public CommonResult<List<OpsRoBarcode>> findRoBarCodeToWms(@RequestBody String limit) {
        List<OpsRoBarcode> result = opsRoBarcodeService.findRoBarcodeByRoId(limit);
        return CollectionUtils.isEmpty(result) ?
                CommonResult.failure("没有记录") : CommonResult.success(result);
    }

    @PostMapping("/wmRoConfirmHandle")
    public CommonResult wmRoConfirmHandle(@RequestBody WmRoConfirmDto param) {
        try {
            roDispatcherService.roConfirm(param);
            return CommonResult.success("成功");
        } catch (OpsException e) {
            log.error("入库扫描异常{}:,{}", param.getReceiveOrderCode(), e);
            // Add by B28029, 2022-10-26 for 禅道任务：2071
            // 写入入库确认错误日志记录
            String errflag = "0";
            if (e.getArgs() != null && e.getArgs().length > 0 && Objects.nonNull(e.getArgs()[0])) {
                errflag = e.getArgs()[0].toString();
            }
            opsExceptionHandleService.addRoConfirmExceptionHandle(param.getReceiveOrderCode(), errflag, param.getWmsOrderCode(), JSON.toJSONString(param), e.getFullMessage(), param.getModelNo(), param.getQty(), param.getWarehouseCode(), param.getUsername());
            // End
            return CommonResult.failure(e.getFullMessage());
        }
    }
}
