package com.sales.ops.feign;

import com.alibaba.fastjson.JSONArray;
import com.sales.ops.db.entity.OpsRoBarcode;
import com.sales.ops.db.entity.OpsWmOrderTask;
import com.sales.ops.dto.flux.HandConfirm;
import com.sales.ops.dto.flux.RoConfirm;
import com.sales.ops.dto.flux.RoSignConfirmDto;
import com.sales.ops.dto.inventory.*;
import com.sales.ops.dto.order.*;
import com.sales.ops.dto.util.CommonResult;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @author ：C14717
 * @version: 1.0$
 * @description：Opswm
 * @date ：Created in 2021/10/13 13:05
 */
@FeignClient(name = "wm-service", contextId = "WmCallOps")
public interface OpsWmFeignApi {

    /**
     * bugid:17771 c14717 20250630 调库计划完纳
     * @return
     */
    @RequestMapping(value = "/order/finishPlanJob", method = RequestMethod.GET)
    CommonResult<String> finishPlanJob();



    /**
     * 5.6 组装确认回传 富勒
     *
     * @param param
     * @return
     */
    @RequestMapping(value = "/warehouseManage/pco/wmPCOConfirm", method = RequestMethod.POST)
    CommonResult wmPCOConfirm(@RequestBody WmPCOConfirmDto param);

    /**
     * 5.4 出库状态回传接口 富勒
     *
     * @param param
     * @return
     */
    @RequestMapping(value = "/warehouseManage/do/wmDoStatus", method = RequestMethod.POST)
    CommonResult wmDoStatus(@RequestBody WmDoStatusDto param);

    /**
     * 委托出库发货确认接口
     *
     * @param param
     * @return
     */
    @RequestMapping(value = "/warehouseManage/do/wmWTDoConfirm", method = RequestMethod.POST)
    CommonResult wmWTDoConfirm(@RequestBody WmWTDoConfirmDto param);


    /**
     * 5.3 出库发货确认接口
     *
     * @param param
     * @return
     */
    @RequestMapping(value = "/warehouseManage/do/wmDoConfirm", method = RequestMethod.POST)
    CommonResult wmDoConfirm(@RequestBody WmDoConfirmDto param);

    @RequestMapping(value = "/warehouseManage/ro/wmRoConfirmHandle", method = RequestMethod.POST)
    CommonResult wmRoConfirmHandle(@RequestBody WmRoConfirmDto param);


    /**
     * ops提交wms searchTask
     *
     * @param param
     * @return
     */
    @RequestMapping(value = "/wmOrderTask/search", method = RequestMethod.POST)
    CommonResult<List<OpsWmOrderTask>> findWmOrderTask(@RequestBody OpsWmOrderTaskCondition param);

    /**
     * ops提交wms searchTask
     *
     * @param param
     * @return
     */
    @RequestMapping(value = "/wmOrderTask/search/one", method = RequestMethod.POST)
    CommonResult<List<OpsWmOrderTask>> findWmOrderTaskOne(@RequestBody OpsWmOrderTaskCondition param);


    /**
     * ops提交wms searchTask
     *
     * @param param
     * @return
     */
    @RequestMapping(value = "/wmOrderTask/search/two", method = RequestMethod.POST)
    CommonResult<List<OpsWmOrderTask>> findWmOrderTaskTwo(@RequestBody OpsWmOrderTaskCondition param);



    /**
     * ops提交wms searchTask
     *
     * @param param
     * @return
     */
    @RequestMapping(value = "/wmOrderTask/search/three", method = RequestMethod.POST)
    CommonResult<List<OpsWmOrderTask>> findWmOrderTaskThree(@RequestBody OpsWmOrderTaskCondition param);


    /**
     * ops提交wms searchTask
     *
     * @param param
     * @return
     */
    @RequestMapping(value = "/wmOrderTask/search/four", method = RequestMethod.POST)
    CommonResult<List<OpsWmOrderTask>> findWmOrderTaskFour(@RequestBody OpsWmOrderTaskCondition param);


    /**
     * ops提交wms searchTask
     *
     * @param param
     * @return
     */
    @RequestMapping(value = "/wmOrderTask/search/five", method = RequestMethod.POST)
    CommonResult<List<OpsWmOrderTask>> findWmOrderTaskFive(@RequestBody OpsWmOrderTaskCondition param);


    /**
     * ops提交wms searchTask
     *
     * @param param
     * @return
     */
   /* @RequestMapping(value = "/wmOrderTask/update", method = RequestMethod.POST)
    CommonResult updateWmOrderTask(@RequestBody OpsWmOrderTaskCondition param);*/

    /**
     * ops提交wmsDO和DOitem
     *
     * @param doId
     * @return
     */
    @RequestMapping(value = "/warehouseManage/do/findDoToWms", method = RequestMethod.POST)
    CommonResult<OpsDoAndItemDto> findDoToWms(@RequestBody String doId);

    /**
     * ops提交wmsDO和DOitem
     * bugid:12601 c14717 20231212
     * @param opsWmOrderTask
     * @return
     */
    @RequestMapping(value = "/warehouseManage/do/handPostDo", method = RequestMethod.POST)
    CommonResult<OpsDoAndItemDto> handPostDo(@RequestBody OpsWmOrderTask opsWmOrderTask);

    /**
     * ops提交wmsRO和ROitem
     *
     * @param roId
     * @return
     */
    @RequestMapping(value = "/warehouseManage/ro/findRoToWms", method = RequestMethod.POST)
    CommonResult<OpsRoAddItemDto> findRoToWms(@RequestBody String roId);

    /**
     * ops提交wmsBarCode
     *
     * @param roId
     * @return
     */
    @RequestMapping(value = "/warehouseManage/ro/findRoBarCodeToWms", method = RequestMethod.POST)
    CommonResult<List<OpsRoBarcode>> findRoBarCodeToWms(@RequestBody String roId);

    /**
     * ops提交wmsPco和Item
     *
     * @param pcoId
     * @return
     */
    @RequestMapping(value = "/warehouseManage/pco/findPcoToWms", method = RequestMethod.POST)
    CommonResult<OpsPcoAddItemDto> findPcoToWms(@RequestBody String pcoId);

    /**
     * 发票签收
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/order/signInvoice", method = RequestMethod.POST)
    CommonResult signInvoiceForWms(@RequestBody RoSignConfirmDto param);

    /**
     * 到货确认
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/order/Confirmgoods", method = RequestMethod.POST)
    RoConfirm Confirmgoods(@RequestBody RoSignConfirmDto param);


    /**
     * 交易出库指令下发wms之后，变更订单表状态
     * @author C12961
     * @date 2022/3/5 14:18
     */
    @GetMapping("/warehouseManage/do/prepare")
    CommonResult prepareOperation(@RequestParam("doId") String doId);

    /**
     * 物流开始出库
     *
     * @author C12961
     * @date 2022/3/5 10:01
     */
    @PostMapping("/warehouseManage/do/start")
    CommonResult start(@RequestBody StartWaveParam startWaveParam);

    @PostMapping("/warehouseManage/do/startOperation")
    CommonResult startOperation(@RequestBody DoWaveParam waveParam);

    @PostMapping("/warehouseManage/do/releaseOperation")
    CommonResult releaseOperation(@RequestBody DoWaveParam waveParam);


    /**
     * 出库货齐判断 下发wms状态 出库单
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/wmDo/do", method = RequestMethod.GET)
    CommonResult sendDoToWMSChangeStatus(@RequestParam("num") String num);

    /**
     * 出库货齐判断 下发wms状态 出库单 拆分id one
     * @param
     * @return
     */
    @RequestMapping(value = "/wmDo/do/one", method = RequestMethod.GET)
    CommonResult sendDoToWMSChangeStatusOne(@RequestParam("num") String num);


    /**
     * 出库货齐判断 下发wms状态 出库单 拆分id two
     * @param
     * @return
     */
    @RequestMapping(value = "/wmDo/do/two", method = RequestMethod.GET)
    CommonResult sendDoToWMSChangeStatusTwo(@RequestParam("num") String num);


    /**
     * 出库货齐判断 下发wms状态 出库单 拆分id three
     * @param
     * @return
     */
    @RequestMapping(value = "/wmDo/do/three", method = RequestMethod.GET)
    CommonResult sendDoToWMSChangeStatusThree(@RequestParam("num") String num);


    /**
     * 出库货齐判断 下发wms状态 出库单 拆分id four
     * @param
     * @return
     */
    @RequestMapping(value = "/wmDo/do/four", method = RequestMethod.GET)
    CommonResult sendDoToWMSChangeStatusFour(@RequestParam("num") String num);


    /**
     * 出库货齐判断 下发wms状态 出库单 拆分id five
     * @param
     * @return
     */
    @RequestMapping(value = "/wmDo/do/five", method = RequestMethod.GET)
    CommonResult sendDoToWMSChangeStatusFive(@RequestParam("num") String num);


    /**
     * 出库货齐判断 下发wms状态 加工单
     * @param
     * @return
     */
    @RequestMapping(value = "/wmDo/pco", method = RequestMethod.GET)
    CommonResult sendPcoToWMSChangeStatus(@RequestParam("num") String num);



    /**
     * 出库货齐判断 下发wms状态 加工单 拆分id one
     * @param
     * @return
     */
    @RequestMapping(value = "/wmDo/pco/one", method = RequestMethod.GET)
    CommonResult sendPcoToWMSChangeStatusOne(@RequestParam("num") String num);

    /**
     * 出库货齐判断 下发wms状态 加工单
     * @param
     * @return
     */
    @RequestMapping(value = "/wmDo/pco/two", method = RequestMethod.GET)
    CommonResult sendPcoToWMSChangeStatusTwo(@RequestParam("num") String num);

    /**
     * 出库货齐判断 下发wms状态 加工单
     * @param
     * @return
     */
    @RequestMapping(value = "/wmDo/pco/three", method = RequestMethod.GET)
    CommonResult sendPcoToWMSChangeStatusThree(@RequestParam("num") String num);

    /**
     * 出库货齐判断 下发wms状态 加工单
     * @param
     * @return
     */
    @RequestMapping(value = "/wmDo/pco/four", method = RequestMethod.GET)
    CommonResult sendPcoToWMSChangeStatusFour(@RequestParam("num") String num);

    /**
     * 出库货齐判断 下发wms状态 加工单
     * @param
     * @return
     */
    @RequestMapping(value = "/wmDo/pco/five", method = RequestMethod.GET)
    CommonResult sendPcoToWMSChangeStatusFive(@RequestParam("num") String num);



    /**
     * 交接确认回传
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/order/handconfirm", method = RequestMethod.POST)
    CommonResult handconfirm(@RequestBody HandConfirm handconfirm);


    /**
     * 退货确认数据确认回传
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/order/handReturnOrderConfirm", method = RequestMethod.POST)
    CommonResult handReturnOrderConfirm(@RequestBody List<CreInvMoveForReturnOrderDto> list);


    /**
     * 写入委托在库出库表 cs_expdetail 用中转站 避免401
     * @param csExpDataDOS
     * @return
     */
    @RequestMapping(value = "/middle/shareapp/cs/addExpData", method = RequestMethod.POST)
    Boolean addExpData(@RequestBody JSONArray csExpDataDOS);

    /**
     * 自动订单还原
     */
    @RequestMapping(value = "/order/autoOrderChangeToInitStatus", method = RequestMethod.POST)
    ResultVo<String> autoOrderChangeToInitStatus(@RequestBody AutoOrderChangeToInitStatusDto param);

    /**
     * 订单还原
     */
    @RequestMapping(value = "/order/orderChangeToInitStatus", method = RequestMethod.POST)
    CommonResult<String> orderChangeToInitStatus(@RequestBody OrderChangeToInitStatusDto inputDto);


    /**
     * 委托在库出库 调用库存
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/order/wtInventoryType", method = RequestMethod.GET)
    CommonResult<List<OpsWTInventoryDTO>> getWtInventoryType(@RequestParam("doId") String doId);


    /**
     * 委托在库出库 调用库存
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/order/wtInventoryTypePco", method = RequestMethod.GET)
    CommonResult<List<OpsWTInventoryDTO>> getWtInventoryTypePco(@RequestParam("pcoId") String pcoId,@RequestParam("pcoItem") Integer pcoItem);

    /**
     * 纳期客户清单
     * 严格要求按客户纳期发货的客户代码清单
     * @return
     */
    @RequestMapping(value = "/order/listDlvCustomers", method = RequestMethod.GET)
    CommonResult<List<String>> listDlvCustomers();

    /**
     * 重置task已读状态 为3 重新计算货齐     *
     * @param
     * @return
     */
    @RequestMapping(value = "/wmOrderTask/updateFlagToThree", method = RequestMethod.GET)
    void updateTaskFlagToThree();

    /**
     * bugid: 17600 c14717 20250514
     * 更新rcvdetail异常状态为初始状态
     */
    @RequestMapping(value = "/wmOrderTask/updateRcvdetailStatusTenToInit", method = RequestMethod.GET)
    void updateRcvdetailStatusTenToInit();


}
