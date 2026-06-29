package com.sales.ops.api.service.wms;

import com.alibaba.fastjson.JSONObject;
import com.sales.ops.db.entity.OpsWarehouse;
import com.sales.ops.db.entity.ProductBom;
import com.sales.ops.dto.flux.*;
import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.db.entity.OpsRoBarcode;
import com.sales.ops.dto.order.*;
import com.sales.ops.dto.util.CommonResult;

import java.util.List;

/**
 * @author ：C14717
 * @version: 1.0$
 * @description：ops提交数据到wms
 * @date ：Created in 2021/10/27 16:52
 */
public interface OpsPostApiToWmsService {

    CommonResult<String> postWTdo(OpsDoAndItemDto param, OpsWarehouse opsWarehouse);

    CommonResult<String> checkTempWmsApi(List<FinishOrderWmsReqDto> params);

    //完纳问询 bugid:11758 20230814 c14717
    CommonResult<JSONObject> askFinishDo(List<FinishOrderWmsReqDto> params) throws OpsException;
    //完纳执行 bugid:11758 20230814 c14717
    CommonResult<String> opsToWmsReData(JSONObject obj, String url) throws OpsException;

    // 接口调用
    CommonResult<String> opsToWms(JSONObject obj, String url) throws OpsException;

    // 变更状态
    CommonResult<String> updateOpsWmOrderTaskStatus(Long wmOrderTaskId,String wmOrderType,String taskType,String wmOrderId,String msg) throws OpsException;

    // 变更状态
    CommonResult<String> updateOpsBomStatus(String type,String param,boolean flag,String msg) throws OpsException;

    // 变更状态
    CommonResult<String> updateOpsROStatus(Long wmOrderTaskId,String type,String roTableId,String roId,boolean flag,String msg) throws OpsException;

    //// 变更状态 下发委托在库RO
    CommonResult<String> updateOpsROStatusToWT(Long roTableId, String roId, String msg) throws OpsException;

    // 变更状态
    CommonResult<String> updateOpsBARCODEStatus(Long wmOrderTaskId,String type,String param,boolean flag,String msg) throws OpsException;

    // 变更状态
    CommonResult<String> updateOpsDOStatus(Long wmOrderTaskId,String type,String doTableId,String doId,boolean flag,String msg) throws OpsException;

    // 变更状态
    CommonResult<String> updateOpsPCOStatus(Long wmOrderTaskId, String type,String pcoTableId,String pcoId,boolean flag,String msg) throws OpsException;

    // 指令下发失败重试信息表 校验
    boolean checkErrorMsg(String msg);

    // 组装报文
    CommonResult<JSONObject> updateBomToWms(ProductBom param) throws OpsException;

    // 组装报文
    JSONObject updateBomToWms(String bomId,String modelNo) throws OpsException;

    // 组装报文
    CommonResult<JSONObject> updateRoToWms(OpsRoAddItemDto param) throws OpsException;

    // 组装报文
    CommonResult<JSONObject> updateRoBarCodeToWms(List<OpsRoBarcode> param) throws OpsException;
    //do下发报文
    CommonResult<JSONObject> updateDoToWms(OpsDoAndItemDto param) throws OpsException;
    //do下发报文
    CommonResult<JSONObject> updateDoToWmsBatch(List<OpsDoAndItemDto> params) throws OpsException;
    //do下发报文 和pco报文
    CommonResult<JSONObject> updateDoToAndPcoWms(OpsDoAndItemDto paramDo, OpsPcoAddItemDto paramPco) throws OpsException;
    //pco下发报文
    CommonResult<JSONObject> updatePcoToWms(OpsPcoAddItemDto param) throws OpsException;

    CommonResult<String> cancelDocAsn(CancelDocAsnDto param) throws OpsException;

    //bugid:14135 c14717 2024-5-9 取消ro
    CommonResult<String> cancelDocAsnV2(CancelDocAsnV2Dto param) throws OpsException;

    //bugid:12714 c14717 2023-11-27
    CommonResult<List<JSONObject>> cancelDocOrderV2(List<CancelDocOrderV2Dto>  param) throws OpsException;

    CommonResult<List<JSONObject>> cancelDocOrderNew(List<CancelDocOrderDto> param) throws OpsException;

    CommonResult<String> changeConsigneeAddress(ChangeConsigneeAddressDto param) throws OpsException;

    //指令下发 更新状态 用于首次下发
    CommonResult<String> updateDoPcoToWmsOrToWT(OpsSendPcoAndDoMidDto obj) throws OpsException;

    // 指令下发 不更状态 用于二次下发
    CommonResult<String> postWmsDoAndPcoNew(OpsSendPcoAndDoMidDto obj) throws OpsException;

    /**
     * 用于首次下发
     * @param doParam
     * @param pcoParam
     * @return
     * @throws OpsException
     */
    CommonResult<String> sendPcoAndDoToWmsNew(OpsDoAndItemDto doParam, OpsPcoAddItemDto pcoParam) throws OpsException;

    /**
     * 用于二次下发 订单修改
     * @param doParam
     * @param pcoParam
     * @return
     * @throws OpsException
     */
    CommonResult<String> sendPcoAndDoToWms(OpsDoAndItemDto doParam, OpsPcoAddItemDto pcoParam) throws OpsException;
}
