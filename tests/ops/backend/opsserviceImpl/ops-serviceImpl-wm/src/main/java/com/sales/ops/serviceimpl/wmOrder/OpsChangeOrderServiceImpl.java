package com.sales.ops.serviceimpl.wmOrder;

import cn.hutool.json.JSONUtil;
import com.sales.ops.common.conf.OpsCommonConfig;
import com.sales.ops.common.opsexception.Exceptions;
import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.db.dao.*;
import com.sales.ops.db.entity.*;
import com.sales.ops.dto.order.*;
import com.sales.ops.dto.purchase.PurchaseInfoForCancel;
import com.sales.ops.dto.purchase.RequestCancelDto;
import com.sales.ops.dto.util.CommonResult;
import com.sales.ops.dto.util.UserDto;
import com.sales.ops.enums.*;
import com.sales.ops.feign.RequestPurchaseFeignApi;
import com.sales.ops.service.ba.BaseWarehouseService;
import com.sales.ops.service.inventory.WmDispatchService;
import com.sales.ops.service.order.BaseCustomerOrderService;
import com.sales.ops.service.wmOrder.*;
import com.sales.ops.serviceimpl.inventory.BaseInventoryService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author ：C14717
 * @version: 1.0$
 * @description：订单转定服务
 * @date ：Created in 2022/4/12 8:39
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class OpsChangeOrderServiceImpl implements OpsChangeOrderService {

    @Resource
    private OpsRoService opsRoService;
    @Resource
    private OpsDoService opsDoService;
    @Resource
    private BaseDoService baseDoService;
    @Resource
    private OpsPcoService opsPcoService;
    @Resource
    private BaseInventoryService baseInventoryService;

    @Resource
    private RequestPurchaseFeignApi requestPurchaseFeignApi;

    @Resource
    private OpsWmOrderTaskMapper opsWmOrderTaskMapper;

    @Autowired
    private OpsCommonConfig opsCommonConfig;
    @Autowired
    private BaseCustomerOrderService baseCustomerOrderService;
    @Autowired
    private WmDispatchService wmDispatchService;
    @Autowired
    private BaseWarehouseService baseWarehouseService;
    @Autowired
    private BaseRoService baseRoService;
    @Autowired
    private DoPcoLogicCenterService doPcoLogicCenterService;

    @Autowired
    private WmOrderTaskService wmOrderTaskService;



    /**
     * 订单还原 取消物流指令
     * bugid:10034 c14717 20230320
     * @param orderId  订单号
     * @param orderItem 订单项号
     * @param userName  操作人名
     * @return 是否还原成功
     * @throws OpsException
     */

    @Override
    public boolean orderChangeToInitStatusDelDo(String orderId, String orderItem, String userName) throws OpsException{
        //1.委托在库不还原 ;2.整单可还原
        Rcvdetail rcvdetail = baseCustomerOrderService.findRcvDetailByNo(orderId, orderItem);
        if(Objects.isNull(rcvdetail)){
            throw Exceptions.OpsException("rcv表无值，orderId:"+orderId+"-"+orderItem);
        }
        //拆分类型判断
        if(org.apache.commons.lang3.StringUtils.isEmpty(rcvdetail.getProdFlag()) || !(OrderSplitTypeEnum.ALL.getSplitType().equals(rcvdetail.getProdFlag()))){
            throw Exceptions.OpsException("rcv拆分不可用，ProdFlag:"+rcvdetail.getProdFlag());
        }
        //委托在库判断 ,无出库区分认为不是委托在库
        boolean isWT = false;
        if (org.apache.commons.lang3.StringUtils.isNotBlank(rcvdetail.getStockCode())) {
            OpsWarehouse opsWarehouse = baseWarehouseService.getWarehouseByCode(rcvdetail.getStockCode());
            if (Objects.nonNull(opsWarehouse) && WarehouseTypeEnum.WT.getHouseTypeCode().equals(opsWarehouse.getWarehouseType())) {
                isWT = true;
            }
        }
        if (isWT) {
            throw Exceptions.OpsException("出服务备库无法删单");
        }
        //订单状态判断
        if(rcvdetail.getStatus() > RcvOrderStatusEnum.CKING.getType()){
            if(RcvOrderStatusEnum.CREDIT.getType() != (rcvdetail.getStatus())){
                throw Exceptions.OpsException("当前状态无法进行订单还原");
            }
        }
        //3.调取删单接口
        CancelForOrderDto obj = new CancelForOrderDto();
        obj.setReason("订单还原");
        obj.setUserDto(new UserDto(userName));
        obj.setOrderId(orderId);
        obj.setOrderItem(orderItem);
        //调拨单和交易出库单都需要取消
        return wmDispatchService.orderChangeToInitDelOrder(obj);
    }


    /**
     * 订单还原 取消po
     * bugid:10034 c14717 20230320
     * @param info
     * @return
     */
    @Override
    public CommonResult<Boolean> orderChangeToInitStatusDelPo(PurchaseInfoForCancel info) throws OpsException{
        if (Objects.nonNull(info) && !info.isMerge()) {
            RequestCancelDto dto = new RequestCancelDto();
            //请购单号
            dto.setOrderno(info.getRequestno());
            dto.setItemno(info.getRequestItemno());
            dto.setSplititemno(info.getRequestSplitno());
            //订单还原取消采购单
            dto.setSourceType(PurchaseCancelSourceEnum.RESET_CUSTOMER_ORDER.getType());
            log.info("取消采购单：{}", JSONUtil.toJsonPrettyStr(dto));
            return requestPurchaseFeignApi.cancelPurchase(dto);
        } else {
            throw Exceptions.OpsException("无采购数据");
        }
    }


    /**
     * 是否能够删除补库单，如果补库单被客单占用 应该优先转定，返回fasle ;如果没有被客单占用返回true 可以删除客单
     * @return false 可以删除 true 需要转定后删除
     */
    @Override
    public boolean askWmServerDelPo(HashMap map, String orderNo, Integer itemNo, Integer splitItemNo,
                                    List<OpsRequestpurchase> requestpurchaseList) throws OpsException {
        List<OpsInventoryMove> opsInventoryMoveList = baseInventoryService.findInventoryMoveByPo(orderNo, itemNo, splitItemNo, InventoryStatusEnum.PRODUCE);
        for (OpsRequestpurchase requestPurchase : requestpurchaseList) {
            if (OrderTypeEnum.BIN.equals(requestPurchase.getOrdtype()) || OrderTypeEnum.PRE.equals(requestPurchase.getOrdtype()) ||
                    OrderTypeEnum.WT.equals(requestPurchase.getOrdtype())) {
                for (OpsInventoryMove invMove : opsInventoryMoveList) {

                    //只考虑拆分数量的情况
                    List<OpsDoItemInventory> doItemInventoryList = baseDoService.getDoItemInventoryByInventoryId(invMove.getInventoryId(), InventoryTableTypeEnum.TRANS);
                    if (CollectionUtils.isNotEmpty(doItemInventoryList)) {
                        //OpsDo findDoByDoId(String doId) throws OpsException;
                        for (OpsDoItemInventory opsDoItemInventory : doItemInventoryList) {
                            OpsDo opsDo = baseDoService.getDoByDoId(opsDoItemInventory.getDoId());
                            if (Objects.nonNull(opsDo) && DoSourceEnum.ASSQTY.getType().equals(opsDo.getDoSource())) {
                                map.put("orderIdAndOrderItem", opsDo.getOrderId() + "-" + opsDo.getOrderItem());
                                return false;
                            }
                        }
                    }
                    //处理do
                   /* List<OpsDoItemInventory> doItemInventoryList = baseDoService.getDoItemInventoryByInventoryId(invMove.getInventoryId(), InventoryTableTypeEnum.TRANS);
                    if(CollectionUtils.isNotEmpty(doItemInventoryList)){
                        return false;
                    }
                    //处理pco
                    List<OpsPcoItemInventory> pcoInvList = opsPcoService.getOpsPcoItemInventoryByInventoryId(invMove.getInventoryId(), InventoryTableTypeEnum.TRANS);
                    if(CollectionUtils.isNotEmpty(pcoInvList)){
                        return false;
                    }*/
                }
            }
        }
        return true;
    }

    /**
     * 删采购事件
     * SELECT * from ops_event_purchase_order where order_id ='22802955' and order_item =1;
     * bugid:12991 c14717 20240112
     * 请购没发单的时候处理指令
     * 1.cgdbck 无关联关系删指令
     * 2.jyck 无关联关系 整出删指令 非整出更新二次处理状态
     * 3.拆分型号 无关联关系更新为二次处理状态
     * @param requestPurchase
     * @param resutlList
     * @throws OpsException
     */
    @Override
    public void delPoSoDelDoNotItemInv(OpsRequestpurchase requestPurchase, List<OrderInventoryQueryDto> resutlList) throws OpsException{
        OrderInventoryQueryDto dto = new OrderInventoryQueryDto();
        dto.setDelDo(false);
        dto.setModelno(requestPurchase.getModelno());
        // 验证是否有关联关系
        boolean invFlag = false;
        //整型号采购
        if(WMPurchaseTagEnum.WHOLE.getType().equals(requestPurchase.getWmtag())){
            initOrderInventoryQueryDto(dto, requestPurchase.getOrderno(), requestPurchase.getItemno(), requestPurchase.getSplititemno(),
                    requestPurchase.getOrderno(), requestPurchase.getItemno().toString(), 0, "");
            OpsDo opsDo = baseDoService.findJYCKByOrder(requestPurchase.getOrderno(), requestPurchase.getItemno().toString(),Optional.ofNullable(requestPurchase.getSplititemno()).orElse(0), DoSourceEnum.CG);
            if(Objects.isNull(opsDo)){//查询CGDBCK
                OpsDo cgdbckDO = baseDoService.findCGDBCKByOrder(requestPurchase.getOrderno(), requestPurchase.getItemno().toString(), requestPurchase.getSplititemno());
                if(Objects.nonNull(cgdbckDO)){
                    List<OpsDoItemInventory> doDBItemInv = baseDoService.getDoItemInventoryByDoId(cgdbckDO.getDoId());
                    if(CollectionUtils.isEmpty(doDBItemInv)){
                        //删除调拨do
                        Long cancelId = delOrderReturnCancelId(cgdbckDO.getOrderId(), cgdbckDO.getOrderItem(), "1", "采购取消", UserDto.ADMIN);
                        delDo(cgdbckDO, cancelId, DoTypeEnum.CGDBCK);
                        //删除调拨ro
                        delDBRo(cgdbckDO, cancelId,RoTypeEnum.CGDBRK);
                    }else {
                        invFlag = true;
                    }
                }
            }
            //存在交易单
            if(Objects.nonNull(opsDo)){
                List<OpsDoItemInventory> doItemInv = baseDoService.getDoItemInventoryByDoId(opsDo.getDoId());
                if(CollectionUtils.isEmpty(doItemInv)){
                    if(DoWaitTypeEnum.WaitCG.getType().equals(opsDo.getWaitType())){
                        //处理do
                        dto.setSplitNo(opsDo.getNum());
                        if (DoSourceEnum.ALL.getType().equals(opsDo.getDoSource()) || DoSourceEnum.CG.getType().equals(opsDo.getDoSource())) {//全数量出
                            //删除交易出库
                            dto.setDelDo(true);
                            Long cancelId = delOrderReturnCancelId(opsDo.getOrderId(), opsDo.getOrderItem(), "1", "采购取消", UserDto.ADMIN);
                            delDo(opsDo, cancelId, DoTypeEnum.JYCK);
                        } else {//非全数量出
                            //更新状态为异常状态
                            opsDoService.updateDoToException(opsDo.getId());
                        }
                        resutlList.add(dto);
                    }else {
                        List<OpsDo> dbckList = baseDoService.findDBCKListByOrder(requestPurchase.getOrderno(), requestPurchase.getItemno().toString(),
                                requestPurchase.getSplititemno(), DoSourceEnum.ALL);
                        if(CollectionUtils.isEmpty(dbckList)){
                            //处理do
                            dto.setSplitNo(opsDo.getNum());
                            if (DoSourceEnum.ALL.getType().equals(opsDo.getDoSource()) || DoSourceEnum.CG.getType().equals(opsDo.getDoSource())) {//全数量出
                                //删除交易出库
                                dto.setDelDo(true);
                                Long cancelId = delOrderReturnCancelId(opsDo.getOrderId(), opsDo.getOrderItem(), "1", "采购取消", UserDto.ADMIN);
                                delDo(opsDo, cancelId, DoTypeEnum.JYCK);
                            } else {//非全数量出
                                //更新状态为异常状态
                                opsDoService.updateDoToException(opsDo.getId());
                            }
                            resutlList.add(dto);
                        }else { // 存在调拨单 既代表有关联关系
                            invFlag = true;
                        }
                    }
                }else { // 有关联关系
                    invFlag = true;
                }
            }
        } else if(WMPurchaseTagEnum.ASS.getType().equals(requestPurchase.getWmtag())){// 拆分型号采购
            initOrderInventoryQueryDto(dto, requestPurchase.getOrderno(), requestPurchase.getItemno(), requestPurchase.getSplititemno(),
                    requestPurchase.getOrderno(), requestPurchase.getItemno().toString(), requestPurchase.getSplititemno(), "");
            // 查看pcoItem
            OpsPco opsPco = opsPcoService.findPcoByOrder(requestPurchase.getOrderno(), requestPurchase.getItemno().toString());
            if (Objects.isNull(opsPco)) {
                throw Exceptions.OpsException("无pco数据");
            }
            OpsPcoItem opsPcoItem = opsPcoService.getPcoItemByPcoIdAndItem(opsPco.getPcoId(),requestPurchase.getSplititemno());
            if (Objects.isNull(opsPcoItem)) {
                throw Exceptions.OpsException("无pcoItem数据");
            }
            List<OpsPcoItemInventory> pcoItemInvs = opsPcoService.getOpsPcoItemInventoryByPcoId(opsPco.getPcoId(), requestPurchase.getSplititemno());
            if(CollectionUtils.isEmpty(pcoItemInvs)){
                if( DoWaitTypeEnum.WaitCG.getType().equals(opsPcoItem.getWaitType())){
                    opsPcoService.updatePcoItemToExcepiton(opsPcoItem.getId());
                    resutlList.add(dto);
                }else {
                    List<OpsDo> dbckList = baseDoService.findDBCKListByOrder(requestPurchase.getOrderno(), requestPurchase.getItemno().toString(),
                            requestPurchase.getSplititemno(), DoSourceEnum.ASSModelNo);
                    if(Objects.isNull(dbckList)){// 不存在调拨单
                        opsPcoService.updatePcoItemToExcepiton(opsPcoItem.getId());
                        resutlList.add(dto);
                    } else {// 存在调拨单
                        invFlag = true;
                    }
                }
            }else {
                invFlag = true;
            }
        }
        //记录异常
        if(invFlag) {
            opsDoService.saveDOopsExceptionHandle("delPoSoDelDo",DoConfirmErrorCodeEnum.DEL_ReqPO_HAVE_ITEM_INV.getCode(), 0,
                    "",String.format("orderno: %s itemNo %s", requestPurchase.getOrderno(), requestPurchase.getItemno().toString() ) ,
                    DoConfirmErrorCodeEnum.DEL_ReqPO_HAVE_ITEM_INV.getDesc(),""
                    , "", 0,"",""
                    , "", "", "");
        }

    }

    /**
     *bugid:10555 20230421 C14717
     *  通过采购单查询在途表 删除doItemInventory 更改do状态
     * 1.bin 先行 委托分库 补库删除调拨单
     * 2.根据move表关联关系删除指令
     * 3.验证关联指令已下发不删除物流指令，记录到异常表
     * @param orderNo 合并采购单号
     * @param itemNo
     * @param splitItemNo
     * @param requestpurchaseList
     * @return
     * @throws OpsException
     */
    @Override
    public List<OrderInventoryQueryDto> delPoSoDelDoNew(String orderNo, Integer itemNo, Integer splitItemNo,
                                                                      List<OpsRequestpurchase> requestpurchaseList,
                                                        PurchaseCancelSourceEnum cancelSourceEnum) throws OpsException{
        //通知发货 分支
        Boolean aBoolean = doPcoLogicCenterService.checkDlvEntireAssShipment(orderNo);
        if(aBoolean){
            return doPcoLogicCenterService.delPoSoDelDoNew(orderNo,itemNo,splitItemNo,requestpurchaseList,cancelSourceEnum);
        }
        List<OrderInventoryQueryDto> resutlList = new ArrayList<>();
        List<OpsInventoryMove> opsInventoryMoveList = baseInventoryService.findInventoryMoveByPo(orderNo, itemNo, splitItemNo, null);
        if(CollectionUtils.isEmpty(opsInventoryMoveList)){
            //bugid:12991 c14717 20240112
            // 删po无move且来源为请购单删单页面删除对应指令操作
            if(PurchaseCancelSourceEnum.CANCEL_REQUEST_PURCHASE.getType().equals(cancelSourceEnum.getType()) ){
                if(requestpurchaseList.size() ==1 ){
                    delPoSoDelDoNotItemInv(requestpurchaseList.get(0), resutlList);
                    return resutlList;
                }
                //记录异常
                opsDoService.saveDOopsExceptionHandle("delPoSoDelDo",DoConfirmErrorCodeEnum.DEL_ReqPO_SIZE_ONE.getCode(), 0,
                        "",String.format("orderno: %s itemNo %s", orderNo, itemNo.toString() ) , DoConfirmErrorCodeEnum.DEL_ReqPO_SIZE_ONE.getDesc(),""
                        , "", 0,"",""
                        , "", "", "");
                return resutlList;
            }else {
                //todo 后续去掉异常记录 记录异常
                opsDoService.saveDOopsExceptionHandle("delPoSoDelDo",DoConfirmErrorCodeEnum.DEL_PO_NOT_MOVE.getCode(), 0,
                        "",String.format("orderno: %s itemNo %s", orderNo, itemNo.toString() ) , DoConfirmErrorCodeEnum.DEL_PO_NOT_MOVE.getDesc(),""
                        , "", 0,"",""
                        , "", "", "");
                return resutlList;
            }
        }
        //根据move表关联关系删除指令
        for (OpsInventoryMove invMove : opsInventoryMoveList) {
            if (!"0".equals(invMove.getSourceType())){
                continue;
            }
            if(!InventoryStatusEnum.PRODUCE.getCode().equals(invMove.getInventoryStatus())){
                // 记录异常
                opsDoService.saveDOopsExceptionHandle("delPoSoDelDo",DoConfirmErrorCodeEnum.DEL_PO_HAVE_NOT_P.getCode(), 0,
                        "",String.format("orderno: %s itemNo %s", orderNo, itemNo.toString() ) , DoConfirmErrorCodeEnum.DEL_PO_HAVE_NOT_P.getDesc(),""
                        , "", 0,"",invMove.getInventoryId().toString()
                        , invMove.getInventoryStatus(), "", "");
                continue;
            }
                //处理do
            List<OpsDoItemInventory> doItemInventoryList = baseDoService.getDoItemInventoryByInventoryId(invMove.getInventoryId(), InventoryTableTypeEnum.TRANS);
            for (OpsDoItemInventory doItemInventory : doItemInventoryList) {
                OpsDo opsDo = baseDoService.getDoByDoId(doItemInventory.getDoId());
                if (Objects.nonNull(opsDo)) {
                    if(opsDo.getIsWms() == 1) {
                        // 增加下发状态保护记录异常表 记录异常表
                        opsDoService.saveDOopsExceptionHandle("delPoSoDelDo",DoConfirmErrorCodeEnum.DEL_PO.getCode(), 0,
                                "", "", DoConfirmErrorCodeEnum.DEL_PO.getDesc(),
                                opsDo.getDoId(), "", 0, opsDo.getWarehouseCode(),
                                doItemInventory.getInventoryId().toString(), "", "", "");
                        continue;
                    }
                    OrderInventoryQueryDto dto = new OrderInventoryQueryDto();
                    initOrderInventoryQueryDto(dto, orderNo, itemNo, splitItemNo, opsDo.getOrderId(),
                                opsDo.getOrderItem(), opsDo.getNum(), "");
                    if (!DoWaitTypeEnum.WaitCG.getType().equals(opsDo.getWaitType())) {
                        dto.setSortNo(doItemInventory.getSortnum());
                    }
                    //是否拆分 整单 还原重跑用
                    dto.setDelDo(false);
                    Long cancelId = delOrderReturnCancelId(opsDo.getOrderId(), opsDo.getOrderItem(), "1", "采购取消", UserDto.ADMIN);
                    if (DoTypeEnum.DBCK.getType().equals(opsDo.getDoType())) {//调拨出库
                        if (DoSourceEnum.ALL.getType().equals(opsDo.getDoSource())) {//全数量出
                            //交易出库单
                            OpsDo opsDoJYCK = baseDoService.getJYCKByOrder(opsDo.getOrderId(), opsDo.getOrderItem(), opsDo.getNum()
                                    , DoSourceEnum.ALL);
                            //已下发指令不删
                            if(opsDoJYCK.getIsWms() == 1) {
                                // 增加下发状态保护记录异常表 记录异常表
                                opsDoService.saveDOopsExceptionHandle("delPoSoDelDo",DoConfirmErrorCodeEnum.DEL_PO.getCode(), 0,
                                        "", "", DoConfirmErrorCodeEnum.DEL_PO.getDesc(),
                                        opsDoJYCK.getDoId(), "", 0, opsDoJYCK.getWarehouseCode(),
                                        doItemInventory.getInventoryId().toString(), "", "", "");
                                continue;
                            }
                            //删除交易出库
                            delDo(opsDoJYCK, cancelId, DoTypeEnum.JYCK);
                            dto.setDelDo(true);
                        } else if (DoSourceEnum.ASSModelNo.getType().equals(opsDo.getDoSource())) {//调拨单为拆分型号
                            OpsPco opsPco = opsPcoService.findPcoByOrder(opsDo.getOrderId(), opsDo.getOrderItem());
                            if (Objects.isNull(opsPco)) {
                                throw Exceptions.OpsException("无pco数据");
                            }
                            OpsPcoItem opsPcoItem = opsPcoService.getPcoItemByPcoIdAndItem(opsPco.getPcoId(),opsDo.getAssNum());
                            if (Objects.isNull(opsPcoItem)) {
                                throw Exceptions.OpsException("无pcoItem数据");
                            }
                            dto.setModelno(opsPcoItem.getSubModelno());
                            //已下发加工单状态不改
                            if(opsPco.getIsWms() == 1) {
                                // 增加下发状态保护记录异常表 记录异常表
                                opsDoService.saveDOopsExceptionHandle("delPoSoDelDo",DoConfirmErrorCodeEnum.DEL_PO.getCode(), 0,
                                        "", "", DoConfirmErrorCodeEnum.DEL_PO.getDesc(),
                                        "", "", 0, opsPco.getWarehouseCode(),
                                        doItemInventory.getInventoryId().toString(), "", opsPco.getPcoId(), opsPcoItem.getPcoItem().toString());
                                continue;
                            }
                            opsPcoService.updatePcoItemToExcepiton(opsPcoItem.getId());
                        } else {
                            //交易出库单
                            OpsDo opsDoJYCK = baseDoService.getJYCKByOrder(opsDo.getOrderId(), opsDo.getOrderItem(), opsDo.getNum()
                                    , DoSourceEnum.ALL);
                            //已下发指令状态不改
                            if(opsDoJYCK.getIsWms() == 1) {
                                // 增加下发状态保护记录异常表 记录异常表
                                opsDoService.saveDOopsExceptionHandle("delPoSoDelDo",DoConfirmErrorCodeEnum.DEL_PO.getCode(), 0,
                                        "", "", DoConfirmErrorCodeEnum.DEL_PO.getDesc(),
                                        opsDoJYCK.getDoId(), "", 0, opsDoJYCK.getWarehouseCode(),
                                        doItemInventory.getInventoryId().toString(), "", "", "");
                                continue;
                            }
                            //1.创建新的do
                            opsDoService.updateDoToException(opsDoJYCK.getId());
                        }
                        //删除调拨do
                        delDo(opsDo, cancelId, DoTypeEnum.DBCK);
                        //删除调拨ro
                        delDBRo(opsDo, cancelId,RoTypeEnum.DBRK);
                        resutlList.add(dto);
                    } else if (DoTypeEnum.JYCK.getType().equals(opsDo.getDoType())) {//交易出库
                        if (DoSourceEnum.ALL.getType().equals(opsDo.getDoSource()) || DoSourceEnum.CG.getType().equals(opsDo.getDoSource())) {//全数量出
                            //删除交易出库
                            dto.setDelDo(true);
                            delDo(opsDo, cancelId, DoTypeEnum.JYCK);
                        } else {//非全数量出
                            //1.创建新的do
                            opsDoService.updateDoToException(opsDo.getId());
                            opsDoService.deleteDoItemInventoryByPrimaryKeySelective(doItemInventory.getId(),UserDto.ADMIN);
                            baseInventoryService.releasePreQtyInventory(doItemInventory.getInventoryId(), doItemInventory.getUseQty(), doItemInventory.getInventoryTableType(), UserDto.ADMIN);
                        }
                        resutlList.add(dto);
                    }else if(DoTypeEnum.CGDBCK.getType().equals(opsDo.getDoType())){
                        //删除调拨do
                        delDo(opsDo, cancelId, DoTypeEnum.CGDBCK);
                        //删除调拨ro
                        delDBRo(opsDo, cancelId,RoTypeEnum.CGDBRK);
                    }else if(DoTypeEnum.TKCK.getType().equals(opsDo.getDoType())){
                        // bugid:20412 c14717 20260316 确认是否是先行在库删单，无需处理com.smc.smccloud.service.impl.PreStockServiceImpl#handlePreStockStatus，仅删除指令即可
                        //删除调拨do
                        delDo(opsDo, cancelId, DoTypeEnum.TKCK);
                        //删除调拨ro
                        delDBRo(opsDo, cancelId,RoTypeEnum.TKRK);
                    }
                }
            }
            //处理pco
            List<OpsPcoItemInventory> pcoInvList = opsPcoService.getOpsPcoItemInventoryByInventoryId(invMove.getInventoryId(), InventoryTableTypeEnum.TRANS);
            for (OpsPcoItemInventory item : pcoInvList) {
                OpsPcoItem opsPcoItem = opsPcoService.getPcoItemByPcoIdAndItem(item.getPcoId(), item.getPcoItem());
                OpsPco opsPco = opsPcoService.selectPcoBypcoId(opsPcoItem.getPcoId());
                if (Objects.nonNull(opsPco)) {
                    if(opsPco.getIsWms() == 1) {
                        // 增加下发状态保护记录异常表 记录异常表
                        opsDoService.saveDOopsExceptionHandle("delPoSoDelDo",DoConfirmErrorCodeEnum.DEL_PO.getCode(), 0,
                                "", "", DoConfirmErrorCodeEnum.DEL_PO.getDesc(),
                                "", "", 0, "",
                                item.getInventoryId().toString(), "", item.getPcoId(), item.getPcoItem().toString());
                        continue;
                    }
                    OrderInventoryQueryDto dto = new OrderInventoryQueryDto();
                    initOrderInventoryQueryDto(dto, orderNo, itemNo, splitItemNo, opsPco.getOrderId(),
                            opsPco.getOrderItem(), opsPcoItem.getPcoItem(), "");
                    if (!DoWaitTypeEnum.WaitCG.getType().equals(opsPcoItem.getWaitType())) {
                        dto.setSortNo(item.getSortnum());
                    }
                    dto.setDelDo(false);
                    dto.setModelno(opsPcoItem.getSubModelno());
                    resutlList.add(dto);
                    opsPcoService.updatePcoItemToExcepiton(opsPcoItem.getId());
                    opsPcoService.deletePcoItemInventoryByPrimaryKeySelective(item.getId(),UserDto.ADMIN);
                    baseInventoryService.releasePreQtyInventory(item.getInventoryId(), item.getUseQty(), item.getInventoryTableType(), new UserDto());
                }
            }
        }
        //删除剩余在途库存 bugid 6393 c14717 20221122
        int res = delInvMoveByPo(orderNo, itemNo, splitItemNo, InventoryStatusEnum.PRODUCE.getCode());
        if (res == 2) {
            throw Exceptions.OpsException("delete库存更新异常ID");
        }
        return resutlList;

    }

    //并发异常3次处理
    public int delInvMoveByPo(String orderNo, Integer itemNo, Integer splitItemNo,
                              String inventoryStatus) throws OpsException {
        //重试次数读取配置文件3次
        for (int i = 0; i < opsCommonConfig.getCoInvRetryFrequency(); i++) {
            //删除剩余在途库存
            try {
                List<OpsInventoryMove> lastOpsInventoryMoveList = baseInventoryService.findInventoryMoveByPo(orderNo, itemNo, splitItemNo, InventoryStatusEnum.PRODUCE);
                for (OpsInventoryMove invMove : lastOpsInventoryMoveList) {
                    baseInventoryService.deleteInventoryMoveNotPreQty(invMove.getInventoryId(), invMove.getVersion(), UserDto.ADMIN);
                }
                //处理成功
                return 1;
            } catch (OpsException e) {
                log.error("采购删单,取消物流指令", e.getMessage());
            }
        }
        //处理失败抛出异常
        return 2;

    }

    /**
     * 删除订单物流指令日志主表
     */
    private Long delOrderReturnCancelId(String orderId, String orderItem, String orderType, String msg, UserDto userDto) throws OpsException {
        CancelForOrderDto inputDto = new CancelForOrderDto();
        inputDto.setOrderId(orderId);
        inputDto.setOrderItem(orderItem);
        inputDto.setOrderType(orderType);
        inputDto.setReason(msg);
        inputDto.setUserDto(userDto);
        return opsDoService.insertOrderCancel(inputDto);

    }

    /**
     * 删除do
     */
    private void delDo(OpsDo opsDo, Long cancelId, DoTypeEnum doTypeEnum) throws OpsException {
        OpsWmOrderTaskExample exa = new OpsWmOrderTaskExample();
        OpsWmOrderTaskExample.Criteria cri = exa.createCriteria();
        cri.andWmOrderIdEqualTo(opsDo.getDoId());
        cri.andWmOrderTypeEqualTo(WmOrderTaskEnum.DO.getType());
        cri.andTaskTypeEqualTo(WmOrderTaskEnum.ORDER.getType());
        cri.andDelflagEqualTo(0);
        List<OpsWmOrderTask> opsWmOrderTasks = opsWmOrderTaskMapper.selectByExample(exa);
        if(CollectionUtils.isNotEmpty(opsWmOrderTasks)){
            OpsWmOrderTask opsWmOrderTask =opsWmOrderTasks.get(0);
            updateDelFlagWmOrderTask(opsWmOrderTask.getId(), "采购取消", 1);
        }
        opsDoService.CancelDo(opsDo.getId(), opsDo.getDoId(), opsDo.getOrderId(), opsDo.getOrderItem(), UserDto.ADMIN);
        opsDoService.insertOrderCancelItem(cancelId, opsDo.getDoId(), doTypeEnum.getType(), "取消成功", UserDto.ADMIN);
    }


    /**
     * 初始化报文
     */
    private void initOrderInventoryQueryDto(OrderInventoryQueryDto dto, String orderNo, Integer itemNo, Integer splitItemNo,
                                            String orderId, String orderItem, Integer SplitNo, String OrderFullNo) {
        dto.setPoNo(orderNo);
        dto.setPoItemNo(itemNo);
        dto.setPoSplitNo(splitItemNo);
        dto.setOrderId(orderId);
        dto.setOrderItem(orderItem);
        dto.setSplitNo(SplitNo);
        dto.setOrderFullNo(OrderFullNo);
    }

    /**
     * 删除ro调拨单
     */
    private void delDBRo(OpsDo opsDo, Long cancelId,RoTypeEnum roTypeEnum) throws OpsException {
        List<OpsRo> opsRos = baseRoService.findRoByOrderItemNum(opsDo.getOrderId(), Integer.valueOf(opsDo.getOrderItem()),
                opsDo.getNum(), opsDo.getAssNum(),opsDo.getExtNum(),roTypeEnum.getType());
        if (org.springframework.util.CollectionUtils.isEmpty(opsRos)) {
            throw Exceptions.OpsException(opsDo.getDoId() + "，无RO数据");
        }
        if (opsRos.size() > 1) {
            throw Exceptions.OpsException(opsDo.getDoId() + "，RO数据重复" + opsRos.get(0).getRoId());
        }
        opsRoService.delOpsRoOrder(cancelId, opsRos.get(0), "采购取消", UserDto.ADMIN);
    }

    //更新task
    private void updateDelFlagWmOrderTask(Long wmOrderTaskId, String msg, Integer delflag) throws OpsException {
        OpsWmOrderTask record = new OpsWmOrderTask();
        record.setId(wmOrderTaskId);
        record.setDelflag(delflag);
        record.setMsg(msg);
        record.setModifyTime(new Date());
        record.setModifier("wms");
        opsWmOrderTaskMapper.updateByPrimaryKeySelective(record);
    }

}
