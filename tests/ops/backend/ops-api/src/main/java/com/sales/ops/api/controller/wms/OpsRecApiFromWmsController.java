package com.sales.ops.api.controller.wms;

import com.sales.ops.api.annotation.Log;
import com.sales.ops.api.service.wms.OpsRecApiFromWmsService;
import com.sales.ops.dto.ba.ChangeYYDto;
import com.sales.ops.dto.expdetail.TransferVO;
import com.sales.ops.dto.flux.HandConfirm;
import com.sales.ops.dto.flux.RoConfirm;
import com.sales.ops.dto.flux.RoSignConfirmDto;
import com.sales.ops.dto.inventory.*;
import com.sales.ops.dto.order.DoWaveParam;
import com.sales.ops.dto.order.StartWaveParam;
import com.sales.ops.dto.util.CommonResult;
import com.sales.ops.enums.FluxMsgFlagEnum;
import com.sales.ops.feign.OpsWmDispatchForOrderFeignApi;
import com.sales.ops.feign.OpsWmFeignApi;
import com.sales.ops.feign.inventory.OpsExcHandleFeignApi;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

/**
 * @author ：C14717
 * @version: 1.0$
 * @description：Ops接收Wms的数据 / WMS 调用 ops数据
 * 5.1.	到货确认  /ConfirmgoodsConfirmgoods
 * 5.2.	入库确认回传 ro/confirm
 * 5.3.	出库确认回传 /do/confirm
 * 5.4.	出库状态回传 /do/status
 * 5.6.	组装确认回传 /pco/confirm
 * 5.8 发票签收 /signInvoice
 * 5.9.	调拨出库发票数据回传
 * 5.10.波次创建状态回传
 * 5.11.BOM信息拉取接口
 *  物流开始出库
 * @date ：Created in 2021/10/27 16:49
 */
@CrossOrigin
@RestController
@RequestMapping(value = "/OpsRecApiFromWms")
public class OpsRecApiFromWmsController {

    @Autowired
    private OpsRecApiFromWmsService opsRecApiFromWmsService;


    @Autowired
    private OpsWmFeignApi opsWmFeignApi;

    @Autowired
    private OpsWmDispatchForOrderFeignApi opsWmDispatchForOrderFeignApi;

    @Autowired
    private OpsExcHandleFeignApi opsExcHandleFeignApi;

    /**
     *  转运回传接口
     *  bugid:19186 c14717 20251127 合并smccode 转运信息确认
     * @param
     * @return
     */
    @Log(apiName = "转运回传接口", type = "transfer")
    @RequestMapping("/transfer")
    public CommonResult transfer(@RequestBody TransferVO param) {
        try {
            if(StringUtils.isBlank(param.getOriginalInvoiceNo())){
                return CommonResult.failure(400,"发票号不可为空");
            }
            if(Objects.isNull(param.getStatus())){
                return CommonResult.failure(400,"转运状态不可为空");
            }
            if(StringUtils.isBlank(param.getUpdator())){
                return CommonResult.failure(400,"操作人不可为空");
            }
            if(Objects.isNull(param.getOperationDate())){
                return CommonResult.failure(400,"操作时间不可为空");
            }
            if(StringUtils.isBlank(param.getSignWarehouse())){
                return CommonResult.failure(400,"签收仓必填");
            }
            if(param.getStatus() == 2){
                if(StringUtils.isBlank(param.getEndReceiveWarehouse())){
                    return CommonResult.failure(400,"目的仓不可为空");
                }
                if(StringUtils.isBlank(param.getCarried())){
                    return CommonResult.failure(400,"承运商不可为空");
                }
                if(StringUtils.isBlank(param.getExpressCode())){
                    return CommonResult.failure(400,"运单号不可为空");
                }
            }
            return opsWmDispatchForOrderFeignApi.transferConfrim(param);
        } catch (Exception ex) {
            return CommonResult.failure( 500,ex.getMessage());
        }
    }


    /**
     *  组换回传确认
     * @param orderId
     * @return
     */
    /*@Log(apiName = "组换失败回传",type = "ZH")*/
    @RequestMapping("/producChange/status")
    public CommonResult wmsProducChangeStatus(@RequestParam("orderId") String orderId,@RequestParam("msg") String msg) {
        try {
            return opsWmDispatchForOrderFeignApi.wmsProducChangeStatus(orderId,msg);
        } catch (Exception ex) {
            return CommonResult.failure(ex.getMessage());
        }
    }

    /**
     *  5.2入库确认回传
     * @param param
     * @return
     */
    /*@Log(apiName = "5.2入库确认回传",type = "RO")*/
    @RequestMapping("/ro/confirm")
    public CommonResult roConfirm(@RequestBody WmRoConfirmDto param) {
        try {
            return  opsRecApiFromWmsService.roConfirm(param);
        } catch (Exception ex) {
            return CommonResult.failure(ex.getMessage());
        }
    }
    /**
     * 5.3 出库确认回传
     * @param param
     * @return
     */
    /*@Log(apiName = "5.3 出库确认回传",type = "DO")*/
    @RequestMapping("/do/confirm")
    public CommonResult doConfirm(@RequestBody WmDoConfirmDto param) {
        try {
            return opsRecApiFromWmsService.doConfirm(param);
        } catch (Exception ex) {
            return CommonResult.failure(ex.getMessage());
        }
    }

    /**
     * 5.4 出库状态
     * @param param
     * @return
     */
    /*@Log(apiName = "5.4 出库状态",type = "DOSTATUS")*/
    @RequestMapping("/do/status")
    public CommonResult wmDoStatus(@RequestBody WmDoStatusDto param) {
        try {
            return opsRecApiFromWmsService.wmDoStatus(param);
        } catch (Exception ex) {
            return CommonResult.failure(ex.getMessage());
        }
    }

    /**
     *  5.6 组装确认回传 富勒
     * @param param
     * @return
     */
    /*@Log(apiName = "5.6组装确认回传富勒",type = "PCO")*/
    @RequestMapping("/pco/confirm")
    public CommonResult roConfirm(@RequestBody WmPCOConfirmDto param) {
        try {
            return  opsRecApiFromWmsService.wmPCOConfirm(param);
        } catch (Exception ex) {
            return CommonResult.failure(ex.getMessage());
        }
    }


    /**
     *  发票签收
     * @param   ,
     * 2023-2-7 请求参数改封装对象@RequestParam改@RequestBody
     * @return
     */
    /*@Log(apiName = "5.8 发票签收",type = "invoice")*/
    @RequestMapping("/signInvoice")
    public CommonResult signInvoiceForWms(@RequestBody RoSignConfirmDto param ) {
        try {
            return  opsWmFeignApi.signInvoiceForWms(param);
        } catch (Exception ex) {
            return CommonResult.failure(ex.getMessage());
        }
    }

    /**
     *  到货确认
     * @param
     * @return
     */
    /*@Log(apiName = "5.1到货确认 ",type = "Confirmgoods")*/
    @RequestMapping("/Confirmgoods")
    public RoConfirm Confirmgoods(@RequestBody RoSignConfirmDto param) {
        try {
            return  opsWmFeignApi.Confirmgoods(param);
        } catch (Exception ex) {
            return new RoConfirm(FluxMsgFlagEnum.FAILURE.getFlag(),param.getWarehouse(), ex.getMessage(), null);
        }
    }


    /**
     *  新增交接确认
     * @param handConfirm
     * @return
     */
    /*@Log(apiName = "5.9调拨出库发票数据回传 ",type = "handconfirm")*/
    @RequestMapping("/handconfirm")
    public CommonResult handconfirm(@RequestBody HandConfirm handConfirm) {
        try {
            return opsWmFeignApi.handconfirm(handConfirm);
        } catch (Exception ex) {
             return CommonResult.failure(ex);
        }
    }


    /**
     * 5.10物流开始出库
     *
     * @return
     */
    /*@Log(apiName = "5.10物流开始出库",type = "startOperation")*/
    @Deprecated
    @RequestMapping("/startOperation")
    public CommonResult startOperation(@RequestBody StartWaveParam startWaveParam) {
        try {
            return opsWmFeignApi.start(startWaveParam);
        } catch (Exception ex) {
            return CommonResult.failure(ex);
        }
    }

    /**
     * 新接口 开始作业（组波次）
     *
     * @param waveParam
     * @return
     */
    @RequestMapping("/do/startOperation")
    public CommonResult startOperation(@RequestBody DoWaveParam waveParam) {
        try {
            return opsWmFeignApi.startOperation(waveParam);
        } catch (Exception ex) {
            return CommonResult.failure(ex);
        }
    }

    /**
     * 新接口 释放作业（推出波次）
     *
     * @param waveParam
     * @return
     */
    @RequestMapping("/do/releaseOperation")
    public CommonResult releaseOperation(@RequestBody DoWaveParam waveParam) {
        try {
            return opsWmFeignApi.releaseOperation(waveParam);
        } catch (Exception ex) {
            return CommonResult.failure(ex);
        }
    }


    /**
     *  5.11委托在库，给门户WMS用
     * @param wmWTDoconfirm
     * @return
     */
    /*@Log(apiName = "5.11委托在库调拨发票回传 ",type = "wmWTDoConfirm")*/
    @RequestMapping("/wmWTDoConfirm")
    public CommonResult wmWTDoConfirm(@RequestBody WmWTDoConfirmDto wmWTDoconfirm) {
        try {
            return  opsWmFeignApi.wmWTDoConfirm(wmWTDoconfirm);
        } catch (Exception ex) {
            return CommonResult.failure(ex);
        }
    }

    /**
     *  出库追加barcode
     * @param
     * @return
     */
    @RequestMapping("/updateDoBarcode")
    public CommonResult updateDoBarcode(@RequestBody WmDoBarcodeDto param) {
        try {
            opsRecApiFromWmsService.saveDoBarcode(param);
            return CommonResult.success();
        } catch (Exception ex) {
            return CommonResult.failure(ex);
        }
    }


    /**
     * bugid:10804 下预约接口
     * @param list
     * @return
     */
    @RequestMapping("/changeYY")
    public CommonResult<List<ChangeYYDto>> changeYY(@RequestBody List<ChangeYYDto> list) {
        try {
            return opsExcHandleFeignApi.changeYY(list);
        } catch (Exception ex) {
            return CommonResult.failure(ex.getMessage());
        }
    }

}
