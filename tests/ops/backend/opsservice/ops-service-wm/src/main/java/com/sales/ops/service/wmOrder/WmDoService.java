package com.sales.ops.service.wmOrder;

import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.db.entity.OpsDo;
import com.sales.ops.db.entity.OpsPcoItem;
import com.sales.ops.db.entity.OpsWmOrderTask;
import com.sales.ops.dto.order.OpsDoAndItemDto;
import com.sales.ops.dto.order.OpsSendPcoAndDoMidIDDto;
import com.sales.ops.dto.util.CommonResult;

import java.util.List;
import java.util.Map;

public interface WmDoService {

    Integer sendDoToWMSChangeStatus(Integer limit, OpsWmOrderTask opsWmOrderTask) throws OpsException;

    Integer sendPcoToWMSChangeStatus(Integer limit, OpsWmOrderTask opsWmOrderTask) throws OpsException;

    void collectDo(List<String> wmOrderIdList, OpsWmOrderTask opsWmOrderTask) throws OpsException;

    boolean checkPCOAndDoList(List<OpsDo> doList) throws OpsException;

    boolean checkDoItemInvQtyEnough(String doId, Integer doItemQty);

    boolean checkPcoItemInvQtyEnough(List<OpsPcoItem> pcoItems);

    boolean checkInitStatus(String orderId);

    boolean checkWaitTypeAndQty(String orderId, List<OpsDo> opsDos)  throws OpsException;

    // 批量下发
    CommonResult<String> updateWMSPcoAddDoTwo(OpsSendPcoAndDoMidIDDto obj) throws OpsException;

    //收集pco数据 返回是否是越库指令
    void collectPco(OpsWmOrderTask opsWmOrderTask, List<OpsSendPcoAndDoMidIDDto> doIdAndPcoId) throws OpsException;

    void sendToWmsForGoodsConfirm(Map<String, String> doSendMap, Map<String, OpsSendPcoAndDoMidIDDto> pcoSendMap) throws OpsException;

    //bugids:12187 c14717 20230921
    CommonResult<String> postWmsDoNew(OpsDoAndItemDto dto) throws OpsException;

    CommonResult<String> postWmsDoAndPcoNew(OpsSendPcoAndDoMidIDDto obj) throws OpsException;
}
