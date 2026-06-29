package com.sales.ops.service.wmOrder;

import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.db.entity.OpsDoItemInventory;
import com.sales.ops.dto.inventory.WmOrderByInventoryDto;
import com.sales.ops.dto.purchase.PurchaseInfoForCancel;
import com.sales.ops.dto.util.CommonResult;
import com.sales.ops.dto.util.UserDto;
import com.sales.ops.dto.zd.ZDHandToPoParamDto;
import com.sales.ops.dto.zd.ZDInventoryInfoDTO;
import com.sales.ops.dto.zd.ZDPageShowOrderBindInvDto;

import java.util.List;
import java.util.Map;

/**
 * @author ：C14717
 * @version: 1.0$
 * @description：转定服务
 * @date ：Created in 2023/5/11 16:04
 */
public interface ZDService {

    // 是否部分收货 用于调库计划
    boolean getRoPartStatus(PurchaseInfoForCancel param);

    //获取订单可用库存
    void showInvByItemQty(String orderId, Integer orderItem, Integer qty,String modelNo, List<ZDInventoryInfoDTO> out) throws OpsException;

    void handleOrderBindPOZD(ZDHandToPoParamDto param) throws OpsException;

    //转定绑定新的关联关系
//    void handleOrderBindInvZD(ZDPageShowOrderBindInvDto param) throws OpsException;

    CommonResult<Boolean> ZDDelPo(PurchaseInfoForCancel info) throws OpsException;

    void createPcoItemInvAddPre(String orderFno, Boolean toInvRiskFlag, String pcoId, Integer pcoItem, Integer qty, String invTableType, Long invId,
                                UserDto userDto) throws OpsException;

    void createDoItemInvAddPre(String orderFno,Boolean toInvRiskFlag,String doId, Integer qty, String invTableType, Long invId,
                               UserDto userDto) throws OpsException;

    OpsDoItemInventory initOpsDoItemInventory(Integer useQty, String doid, Long toInvId, String invTableType, UserDto userDto);

    void persistentLogisticsDoc(Map<Long, Boolean> riskMap, WmOrderByInventoryDto wmOrderByInventoryDto, UserDto userDto) throws OpsException;
}
