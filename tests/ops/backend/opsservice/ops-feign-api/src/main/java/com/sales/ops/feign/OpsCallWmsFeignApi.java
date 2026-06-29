package com.sales.ops.feign;

import com.alibaba.fastjson.JSONObject;
import com.sales.ops.db.entity.OpsRoBarcode;
import com.sales.ops.db.entity.ProductBom;
import com.sales.ops.dto.flux.*;
import com.sales.ops.dto.inventory.WmRoBarcodeDto;
import com.sales.ops.dto.order.*;
import com.sales.ops.dto.util.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author ：C14717
 * @version: 1.0$
 * @description：OPS 调用 WMS 接口
 * @date ：Created in 2021/10/13 9:48
 */
@FeignClient(name = "opsApi", path = "opsApi", contextId = "opsPostWms")
public interface OpsCallWmsFeignApi {


    /**
     * 完纳询问 bugid:11758 20230814 c14717
     * @param params
     * @return
     */
    @RequestMapping(value = "/OpsPostApiToWms/do/askFinish", method = RequestMethod.POST)
    CommonResult<String> askFinish(@RequestBody List<FinishOrderWmsReqDto> params);

    /**
     * 完纳执行 bugid:11758 20230814 c14717
     * @param params
     * @return
     */
    @RequestMapping(value = "/OpsPostApiToWms/do/exeFinish", method = RequestMethod.POST)
    CommonResult<String> exeFinish(@RequestBody List<FinishOrderWmsReqDto> params);

    /**
     * updateBomToWms
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/OpsPostApiToWms/do/updateBomToWms", method = RequestMethod.POST)
    CommonResult updateBomToWms(@RequestBody ProductBom param);

    /**
     * 到货通知单下发
     *
     * @return
     */
    @RequestMapping(value = "/OpsCoordinate/insertrecord/addArriveGoods", method = RequestMethod.POST)
    CommonResult addArriveGoods(@RequestParam("json") String json);

    //cancelShipmentOrder
    @RequestMapping(value = "/OpsCoordinate/insertrecord/cancelShipmentOrder", method = RequestMethod.POST)
    CommonResult cancelShipmentOrder(@RequestParam("json") String json);

    /**
     * ops提交wms 失败变更状态
     * @param
     * @return
     */
    @RequestMapping(value = "/OpsPostApiToWms/updateRoStatus", method = RequestMethod.POST)
    public CommonResult<String> updateOpsWmOrderTaskStatus(@RequestParam("wmOrderTaskId") Long wmOrderTaskId,@RequestParam("wmOrderType") String wmOrderType,@RequestParam("taskType")
            String taskType,@RequestParam("wmOrderId") String wmOrderId,@RequestParam("msg")  String msg);

    /**
     * 下发do指令 用于首次下发 更新状态
     * @param param
     * @return
     */
    @RequestMapping(value = "/OpsPostApiToWms/do/updateDoToWms", method = RequestMethod.POST)
    CommonResult<String> updateDoToWms(@RequestBody OpsDoAndItemDto param);

    /**
     * bugids:12187 c14717 20230921
     * 下发do指令 用于二次下发 不更新状态
     * @param param
     * @return
     */
    @RequestMapping(value = "/OpsPostApiToWms/do/updateDoToWmsNew", method = RequestMethod.POST)
    CommonResult<String> updateDoToWmsNew(@RequestBody OpsDoAndItemDto param);



    /**
     * 批量下发 用于二次下发 应对订单修改地址 不更新状态
     * @param params
     * @return
     */
    @RequestMapping(value = "/OpsPostApiToWms/do/updateDoToWmsBatch", method = RequestMethod.POST)
    CommonResult<String> updateDoToWmsBatch(@RequestBody List<OpsDoAndItemDto> params);
    /**
     * ops提交wmsRO和ROitem
     *
     * @param param
     * @return
     */
    @RequestMapping(value = "/OpsPostApiToWms/ro/updateRoToWms", method = RequestMethod.POST)
    CommonResult updateRoToWms(@RequestBody OpsRoAddItemDto param);

    /**
     * ops提交wmsBarCode 4.4.	入库对应箱号/序列号下发
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/OpsPostApiToWms/ro/updateRoBarCodeToWms", method = RequestMethod.POST)
    CommonResult updateRoBarCodeToWms(@RequestBody WmRoBarcodeDto wmObj);

    /**
     * 下发pco指令 用于首次下发
     * @param param
     * @return
     */
    @RequestMapping(value = "/OpsPostApiToWms/pco/updatePcoToWms", method = RequestMethod.POST)
    CommonResult updatePcoToWms(@RequestBody OpsPcoAddItemDto param);

    /**
     * 4.5.	预期到货通知（采购入库/仓间调拨/退货入库/盘点差异调账）  取消(整单)
     *
     * @param param
     * @return
     */
    @RequestMapping(value = "/OpsPostApiToWms/cancelDocAsn", method = RequestMethod.POST)
    CommonResult cancelDocAsn(@RequestBody CancelDocAsnDto param);

    /**
     * bugid:14135 c14717 2024-5-9 取消ro
     * @param param
     * @return
     */
    @RequestMapping(value = "/OpsPostApiToWms/cancelDocAsnV2", method = RequestMethod.POST)
    CommonResult cancelDocAsnV2(@RequestBody CancelDocAsnV2Dto param);

    /**
     * 4.7.	销售/发运订单/加工单/盘点差异调账取消（整单）
     *
     * @param param
     * @return
     */
    @RequestMapping(value = "/OpsPostApiToWms/cancelDocOrder", method = RequestMethod.POST)
    CommonResult<List<JSONObject>> cancelDocOrder(@RequestBody List<CancelDocOrderDto>  param);

    /**
     * 4.7.	销售/发运订单/加工单/盘点差异调账取消（整单）
     *
     * @param param
     * @return
     */
    @RequestMapping(value = "/OpsPostApiToWms/cancelDocOrderV2", method = RequestMethod.POST)
    CommonResult<List<JSONObject>> cancelDocOrderV2(@RequestBody List<CancelDocOrderV2Dto>  param);

    /**
     * 4.9.	收货人地址变更 承运商
     *
     * @param param
     * @return
     */
    @RequestMapping(value = "/OpsPostApiToWms/changeConsigneeAddress", method = RequestMethod.POST)
    CommonResult changeConsigneeAddress(@RequestBody ChangeConsigneeAddressDto param);

    /**
     * do和pco捆绑下发 指令下发 更新状态 用于首次下发
     * @param param
     * @return
     */
    @PostMapping(value = "/OpsPostApiToWms/updateDoPcoToWmsOrToWT")
    CommonResult<String> updateDoPcoToWmsOrToWT(@RequestBody OpsSendPcoAndDoMidDto param);


    /**
     * bugid:12187 c14717 20230921
     *do和pco捆绑下发  指令下发 不更状态 用于二次下发
     * @param param
     * @return
     */
    @PostMapping(value = "/OpsPostApiToWms/postWmsDoAndPcoNew")
    CommonResult<String> postWmsDoAndPcoNew(@RequestBody OpsSendPcoAndDoMidDto param);
}
