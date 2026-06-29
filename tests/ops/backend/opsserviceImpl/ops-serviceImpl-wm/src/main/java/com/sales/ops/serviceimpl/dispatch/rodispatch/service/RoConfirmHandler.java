package com.sales.ops.serviceimpl.dispatch.rodispatch.service;

import cn.hutool.json.JSONUtil;
import com.sales.ops.common.opsexception.Exceptions;
import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.db.dao.BranchInventoryTransactionMapper;
import com.sales.ops.db.entity.*;
import com.sales.ops.dto.inventory.OpsRoDto;
import com.sales.ops.dto.inventory.WmRoConfirmDto;
import com.sales.ops.dto.order.StatusParamDto;
import com.sales.ops.dto.util.DateUtil;
import com.sales.ops.dto.util.OrderNoInfo;
import com.sales.ops.dto.util.UserDto;
import com.sales.ops.enums.*;
import com.sales.ops.event.publisher.enums.EventSourceEnum;
import com.sales.ops.service.inventory.OpsInventoryService;
import com.sales.ops.service.log.*;
import com.sales.ops.service.order.TransOrderService;
import com.sales.ops.service.order.state.OrderStateService;
import com.sales.ops.service.po.BasePoService;
import com.sales.ops.service.purchase.PurchaseCtcFinishService;
import com.sales.ops.service.wmOrder.*;
import com.sales.ops.serviceimpl.dispatch.rodispatch.domain.PreDoItemInventory;
import com.sales.ops.serviceimpl.dispatch.rodispatch.domain.PrePcoItemInventory;
import com.sales.ops.serviceimpl.dispatch.rodispatch.domain.RoConfirmContext;
import com.sales.ops.serviceimpl.dispatch.rodispatch.domain.RoConfirmContextItem;
import com.sales.ops.serviceimpl.event.v3.CustomerEventPublisher;
import com.sales.ops.serviceimpl.event.v3.PurchaseEventPublisher;
import com.sales.ops.serviceimpl.event.v3.TransferEventPublisher;
import com.sales.ops.serviceimpl.inventory.BaseInventoryService;
import com.sales.ops.utils.PoNoUtil;
import com.smc.smccloud.service.StockAssemblyFeignApi;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.sales.ops.event.publisher.enums.EventSourceEnum.WAREHOUSE_RECEIVE_CONFIRM;

/**
 * @author C12961
 * @date 2023/4/26 14:20
 */
@Slf4j
@Service
public class RoConfirmHandler {

    @Autowired
    private OpsRoBarcodeService opsRoBarcodeService;
    @Autowired
    private OpsRoPostService opsRoPostService;
    @Autowired
    private StockAssemblyFeignApi stockAssemblyFeignApi;
    @Autowired
    private BaseInventoryService baseInventoryService;
    @Autowired
    private BaseRoService baseRoService;
    @Autowired
    private OpsInventoryLogService opsInventoryLogService;
    @Autowired
    private BaseDoService baseDoService;
    @Autowired
    private OpsPcoService opsPcoService;
    @Autowired
    private OpsDoService opsDoService;
    @Autowired
    private WmOrderTaskService wmOrderTaskService;
    @Autowired
    private BasePoService basePoService;
    @Autowired
    private OrderStateService OrderStateService;
    @Autowired
    private PurchaseCtcFinishService purchaseCtcFinishService;
    @Autowired
    private OpsImpdataService opsImpdataService;
    @Autowired
    private TransOrderService transOrderService;
    @Autowired
    private OpsInventoryService opsInventoryService;
    @Autowired
    private BranchInventoryTransactionMapper branchInventoryTransactionMapper;
    @Autowired
    private CustomerEventPublisher customerEventPublisher;
    @Autowired
    private TransferEventPublisher transferEventPublisher;
    @Autowired
    private PurchaseEventPublisher purchaseEventPublisher;


    /**
     * @description 一次收一箱，一个发票号有多箱
     * @author C12961
     * @date 2023/4/27 10:48
     */
    public void roConfirm(WmRoConfirmDto wmRoConfirmDto) throws OpsException {
        // 组换单过滤器
        List<OpsRo> roList = baseRoService.isExchangeOrder(wmRoConfirmDto.getInvoiceNo());
        if (CollectionUtils.isNotEmpty(roList)) {
            int barQty = Integer.valueOf(wmRoConfirmDto.getQty());
            // 处理组换ro
            handleVirtualExchangeRo(roList, barQty);
            stockAssemblyFeignApi.updateAssemblyStatus(roList.get(0).getOrderId(), true);
            return;
        }
        // 1.校验参数
        checkParam(wmRoConfirmDto);
        // 创建模型
        RoConfirmContext context = new RoConfirmContext(wmRoConfirmDto);
        // 算法：分配入库数给库存
        calculateAllocateQtyForInNormal(context);
        // 执行库存入库
        handle(context);
        // 写入事件，客单的次为单位
        insertEvent(context);
        OpsRo opsRo = context.getOpsRoDto().getOpsRo();
        // 更新请购单，次为单位：已收货数量，操作事件，请购单为单位：状态和完成时间
        if (RoTypeEnum.CGRKBK.getType().equals(context.getRoType())) {
            handlePurchaseOrderForRoConfirm(context, opsRo);
        }
        // 写入主动调拨事件
        if (RoTypeEnum.TKRK.getType().equals(context.getRoType())) {
            transferEventPublisher.transferOrderEvent(EventSourceEnum.INVENTORY_TRANS_ORDER_RECEIVE_CONFIRM, opsRo);
        }
        // 组换成功确认回传
        if (RoTypeEnum.ZHRK.getType().equals(context.getRoType())) {
            handleExchangeOrderForRoConfirm(opsRo);
        }
        // 存入对账表，次为单位
        opsRoPostService.insertRoPostForRoConfirm(wmRoConfirmDto);
        // 完全收货，票为单位，更新ro-barcode扫描时间，
        opsRoBarcodeService.updateOpsRoBarcodeForRoConfirm(context.getRoId(), context.getUserDto().getUserName());
        // 更新impdata表,箱/托为单位，2已入库状态
        opsImpdataService.updateImpdataForRoConfirm(context.getInvoiceNo(), context.getInvoiceId(), opsRo, context.getScanType(), context.getBarCode(), context.getCaseNo());
        // 处理调库计划，如果是采购入库，并且ro收完后，按照调库计划表，将已入库的库存转为指定库存属性
        OpsRoDto opsRoDto = context.getOpsRoDto();
        Map<String, Object> param = new HashMap<>();
        param.put("ro", opsRoDto.getOpsRo());
        param.put("roItem", opsRoDto.getOpsRoItem());
        transferEventPublisher.transferOrderEvent(EventSourceEnum.INVENTORY_TRANS_ORDER_DELIVERY_PLAN, opsRo, param);
        // 执行调库操作
        handleTransOrder(context);
    }

    // 执行调库逻辑
    private void handleTransOrder(RoConfirmContext context) throws OpsException {
        // bugid：12563 调库单执行调库操作 2023-12-06 C12961
        for (RoConfirmContextItem item : context.getItems()) {
            List<PreDoItemInventory> preDoItemInvs = item.getPreDoItemInvs();
            for (PreDoItemInventory preDoItemInv : preDoItemInvs) {
                OpsDo opsDo = preDoItemInv.getOpsDo();
                handleInventoryForTransOrder(opsDo);
            }
        }
    }

    public void handleInventoryForTransOrder(OpsDo opsDo) throws OpsException {
        UserDto userDto = new UserDto("roConfirm");
        if (DoTypeEnum.TKCK.getType().equals(opsDo.getDoType()) && opsDo.getWarehouseCode().equals(opsDo.getGatherWarehouseCode())) {
            opsDo = baseDoService.getDoByDoId(opsDo.getDoId());
            OpsDoItem opsDoItem = baseDoService.getDoItemByDoId(opsDo.getDoId());
            List<OpsDoItemInventory> opsNormalItemInventoryList = baseDoService.getDoItemInventoryByDoId(opsDo.getDoId(), InventoryTableTypeEnum.NORMAL);
            // 1.查询调库单据
            TransOrder transOrder = transOrderService.getTransOrderByTransNo(opsDo.getOrderId(), Integer.valueOf(opsDo.getOrderItem()));
            List<OpsRo> opsRoList = baseRoService.findRoByOrderItemNum(opsDo.getOrderId(), Integer.valueOf(opsDo.getOrderItem()),
                    opsDo.getNum(), opsDo.getAssNum(), opsDo.getExtNum(), RoTypeEnum.TKRK.getType());
            if (CollectionUtils.isEmpty(opsRoList)) {
                throw Exceptions.OpsException("调库查不到ro");
            }
            OpsRo ro = opsRoList.get(0);
            OpsRoItem roItem = baseRoService.findRoItemByRoId(ro.getRoId());
            // 2.创建入库库存
            Long inventoryInput = opsInventoryService.getInventoryIdForTransOrder(transOrder.getToWarehouseCode(), transOrder.getModelNo(),
                    transOrder.getToInventoryTypeCode(), transOrder.getToCustomerNo(), transOrder.getToPpl(), transOrder.getToProjectCode(), transOrder.getToGroupCustomerNo(), transOrder.getToSalesInfoNo(),
                    userDto);
            // 3.扣减库存数量 增加库存数量
            int allQty = 0;
            for (OpsDoItemInventory opsDoItemInventory : opsNormalItemInventoryList) {
                Integer outQty = opsDoItemInventory.getUseQty() - opsDoItemInventory.getOutQty();
                if (outQty > 0) {
                    Long inventoryId = opsDoItemInventory.getInventoryId();
                    baseInventoryService.subQtyInventoryForPre(inventoryId, outQty, outQty, opsDoItemInventory.getInventoryTableType(), userDto);
                    opsInventoryLogService.insertOpsInventoryLogForDo(opsDo, inventoryId, opsDoItemInventory.getInventoryTableType(), outQty, userDto);
                    opsDoService.updateOpsDoItemInventoryOutQty(opsDoItemInventory, outQty + opsDoItemInventory.getOutQty(), "transOrder");
                    allQty += outQty;
                }
            }
            baseInventoryService.addQtyInventory(inventoryInput, allQty, InventoryTableTypeEnum.NORMAL.getType(), userDto);
            opsInventoryLogService.insertOpsInventoryLogForRo(ro, roItem.getModelno(), inventoryInput, allQty, userDto);

            // 4.更新指令发收数量和状态
            opsDoService.updateOpsDoItemOutQty(opsDo.getDoId(), allQty + opsDoItem.getOutQty(), opsDoItem.getVersion(), "transOrder");
            baseRoService.updateOpsRoItemRecQty(roItem.getRoId(), allQty + roItem.getRecQty(), roItem.getVersion(), "transOrder");
            // 更新do ro为已完成
            opsDoItem = baseDoService.getDoItemByDoId(opsDo.getDoId());
            roItem = baseRoService.findRoItemByRoId(ro.getRoId());
            if (opsDoItem.getQty().equals(opsDoItem.getOutQty())) {
                opsDoService.updateOpsDoStatus(opsDoItem.getDoId(), DoStatusEnum.FINISH.getStatus(), opsDo.getVersion(), "transOrder");
            }
            if (roItem.getQty().equals(roItem.getRecQty())) {
                baseRoService.updateOpsRoStatus(ro.getRoId(), RoStatusEnum.FINISH.getStatus(), ro.getVersion(), "transOrder");
            }
            transferEventPublisher.transferOrderEvent(EventSourceEnum.INVENTORY_TRANS_ORDER_RECEIVE_CONFIRM, ro);
        }
    }

    private void handleVirtualExchangeRo(List<OpsRo> roList, int allQty) throws OpsException {
        for (OpsRo opsRo : roList) {
            OpsRoItem roItem = baseRoService.findRoItemByRoId(opsRo.getRoId());
            if (roItem.getQty() - roItem.getRecQty() > 0) {
                int qty = Math.min(allQty, roItem.getQty() - roItem.getRecQty());
                baseRoService.updateOpsRoItemRecQty(roItem.getRoId(), roItem.getRecQty() + qty, roItem.getVersion(), "组换入库");
                allQty -= qty;
                RoStatusEnum status = roItem.getRecQty() + qty >= roItem.getQty() ? RoStatusEnum.FINISH : RoStatusEnum.PART;
                baseRoService.updateOpsRoStatus(opsRo.getRoId(), status.getStatus(), opsRo.getVersion(), "组换入库");
            }
        }
    }
    // 1.1 减在途库存
    // 1.2 增在库库存
    // 1.3 增加ro收货数，更新ro收货状态
    // 2.1 调整关联表do\pcoItemInventory表N->T
    private void handle(RoConfirmContext context) throws OpsException {
        for (RoConfirmContextItem item : context.getItems()) {
            if (item.getSubQty() == 0) {
                continue;
            }
            OpsInventoryMove move = item.getMove();
            OpsInventory inventory = item.getInventory();
            UserDto userDto = context.getUserDto();
            //================================================//
            // 1.1 减在途库库存数和预约数
            baseInventoryService.subQtyInventoryForPre(move.getInventoryId(), item.getSubQty(), item.getSubQty(), InventoryTableTypeEnum.TRANS.getType(), userDto);
            // 如果库存数量清零则删除库存
            move = baseInventoryService.getInventoryMoveById(move.getInventoryId());
            if (move.getQuantity() == 0) {
                baseInventoryService.deleteInventoryMove(move.getInventoryId(), move.getVersion(), userDto);
            }
            //================================================//
            // 1.2 增加在库库存数和预约数
            baseInventoryService.UpdateQtyInventoryWithPreQty(inventory.getInventoryId(), item.getSubQty(), item.getSubPreQty(),
                    InventoryTableTypeEnum.NORMAL.getType(), userDto);
            // 记录日志
            opsInventoryLogService.insertOpsInventoryLogForRo(context.getRo(), move.getModelno(), inventory.getInventoryId(), item.getSubQty(), userDto);
            //================================================//
            // 1.3 重要算法：增加ro收货数和状态
            updateRoForRoConfirm(context, item, userDto);
            //================================================//
            // 2.1.1 调整doItemInventory表的预占数量
            for (PreDoItemInventory preDoItemInv : item.getPreDoItemInvs()) {
                // 要转移的数量
                int preQty = preDoItemInv.getPreQty();
                OpsDo opsDo = preDoItemInv.getOpsDo();
                OpsDoItemInventory from = preDoItemInv.getFromItemInventory();
                OpsDoItemInventory target = preDoItemInv.getTargetItemInventory();
                // 扣减Do在途预占数量 useqty 递减 outqty不变
                int i = opsDoService.updateOpsDoItemInventory(from.getId(), from.getVersion(), from.getUseQty() - preQty, from.getInventoryId(), InventoryTableTypeEnum.TRANS, userDto);
                // 增加DO在库预占数量 useqty 递增 outqty不变
                int j = opsDoService.updateOpsDoItemInventory(target.getId(), target.getVersion(), target.getUseQty() + preQty, target.getInventoryId(), InventoryTableTypeEnum.NORMAL, userDto);
                if (i < 1 || j < 1) {
                    throw Exceptions.OpsException("服务忙，请稍后再试");
                }
                OpsDoItemInventory doItemInventoryById = baseDoService.getDoItemInventoryById(from.getId());
                // 当前在途行收齐改OK
                if (doItemInventoryById.getUseQty() < 1) {
                    // 分配完成，T改删除标记
                    int k = opsDoService.deleteDoItemInventoryByPrimaryKeySelective(doItemInventoryById.getId(), userDto);
                    if (k < 1) {
                        throw Exceptions.OpsException("服务忙，请稍后再试");
                    }
                    updateTaskToCheckForDo(doItemInventoryById.getDoId(), userDto);
                }
            }
            // 2.1.2调整pcoItemInventory表的预占数量
            for (PrePcoItemInventory prePcoItemInv : item.getPrePcoItemInvs()) {
                int preQty = prePcoItemInv.getPreQty();
                OpsPco opsPco = prePcoItemInv.getOpsPco();
                OpsPcoItem opsPcoItem = prePcoItemInv.getOpsPcoItem();
                OpsPcoItemInventory from = prePcoItemInv.getFromItemInventory();
                OpsPcoItemInventory target = prePcoItemInv.getTargetItemInventory();
                // 扣减Pco在途预占数量 useqty 递减 outqty不变
                int i = opsPcoService.updatetOpsPcoItemInventory(from.getId(), from.getVersion(), from.getUseQty() - preQty, from.getInventoryId(), InventoryTableTypeEnum.TRANS, userDto);
                // 增加PcO在库预占数量 useqty 递增 outqty不变
                int j = opsPcoService.updatetOpsPcoItemInventory(target.getId(), target.getVersion(), target.getUseQty() + preQty, target.getInventoryId(), InventoryTableTypeEnum.NORMAL, userDto);
                if (i < 1 || j < 1) {
                    throw Exceptions.OpsException("服务忙，请稍后再试");
                }
                OpsPcoItemInventory pcoItemInventoryById = opsPcoService.findPcoItemInventoryById(from.getId());
                if (pcoItemInventoryById.getUseQty() < 1) {
                    // 分配完成，T改删除标记，
                    int k = opsPcoService.deletePcoItemInventoryByPrimaryKeySelective(pcoItemInventoryById.getId(), userDto);
                    if (k < 1) {
                        throw Exceptions.OpsException("服务忙，请稍后再试");
                    }
                    OpsDo jyck = baseDoService.getJYCKByOrder(opsPco.getOrderId(), opsPco.getOrderItem(), opsPco.getNum(), DoSourceEnum.ASSModelNo);
                    updateTaskToCheckForPco(jyck, pcoItemInventoryById.getPcoId(), pcoItemInventoryById.getPcoItem(), userDto);
                }
            }


        }
    }

    private void updateRoForRoConfirm(RoConfirmContext context, RoConfirmContextItem item, UserDto userDto) throws OpsException {
        OpsRo ro = context.getRo();
        OpsRoItem roItem = context.getRoItem();
        OpsInventoryMove move = item.getMove();
        OpsInventory inventory = item.getInventory();
        int recQty = roItem.getRecQty() + item.getSubQty();
        int result = baseRoService.updateOpsRoItemRecQty(roItem.getRoId(), recQty, roItem.getVersion(), userDto.getUserName());
        if (result < 1) {
            throw Exceptions.OpsException("更新RoItem收货数量错误。入库指令：" + roItem.getRoId(), RoConfirmErrTypeFlagEnum.UPDATE_ROSTATUS.getFlag());
        }
        boolean roFinish = recQty >= roItem.getQty();
        RoStatusEnum status = roFinish ? RoStatusEnum.FINISH : RoStatusEnum.PART;
        result = baseRoService.updateOpsRoStatus(ro.getRoId(), status.getStatus(), ro.getVersion(), userDto.getUserName());
        if (result < 1) {
            throw Exceptions.OpsException("更新Ro状态错误。入库指令：" + ro.getRoId(), RoConfirmErrTypeFlagEnum.UPDATE_ROSTATUS.getFlag());
        }
        insertBranchInventoryTransaction(ro, roFinish, recQty, move, inventory, context.getUserDto());
        updateOpsRoDto(context);
    }

    private void insertBranchInventoryTransaction(OpsRo ro, boolean roFinish, int recQty, OpsInventoryMove move, OpsInventory inventory, UserDto userDto) {
        //写入制造出入库账目表
        boolean CGRKBK = ro.getRoType().equals(RoTypeEnum.CGRKBK.getType());
        boolean supplierContains = Arrays.asList("GZ").contains(move.getSupplierid());
        boolean afterTime = ro.getSignTime().after(DateUtil.stringToDate("2025-01-01"));
        if (CGRKBK & roFinish && supplierContains && afterTime) {
            Date date = new Date();
            BranchInventoryTransaction inventoryTransaction = new BranchInventoryTransaction();
            inventoryTransaction.setCompanyId("CNG");
            inventoryTransaction.setModelNo(move.getModelno());
            inventoryTransaction.setWarehouseCode(move.getSignWarehouseCode());
            inventoryTransaction.setVoucherCode(ro.getRoId());
            inventoryTransaction.setInventoryType(ro.getRoType());
            inventoryTransaction.setQuantity(recQty);
            inventoryTransaction.setFromInventoryId(move.getInventoryId().toString());
            inventoryTransaction.setToInventoryId(inventory.getInventoryId().toString());
            inventoryTransaction.setOrderNo(move.getAssociateNo());
            inventoryTransaction.setOrderItem(move.getAssociateNoItem());
            inventoryTransaction.setOrderSplitNo(move.getAssociateNoSplitno());
            inventoryTransaction.setInvoiceNo(move.getInvoiceno());
            inventoryTransaction.setInvoiceId(move.getInvoiceid());
            inventoryTransaction.setSignTime(ro.getSignTime());
            inventoryTransaction.setDelFlag(0);
            inventoryTransaction.setCreateUser(userDto.getUserName());
            inventoryTransaction.setCreateTime(date);
            inventoryTransaction.setUpdateUser(userDto.getUserName());
            inventoryTransaction.setUpdateTime(date);
            branchInventoryTransactionMapper.insertSelective(inventoryTransaction);
        }
    }

    public void checkParam(WmRoConfirmDto wmRoConfirmDto) throws OpsException {
        if (Objects.isNull(wmRoConfirmDto)) {
            throw Exceptions.OpsException("参数解析失败--wmRoConfirmDto 或 UserDto");
        }
        String roId = wmRoConfirmDto.getReceiveOrderCode();// ROID入库指令
        String barCode = wmRoConfirmDto.getReceiveCode();// 箱码
        String warehouseCode = wmRoConfirmDto.getWarehouseCode();// 仓库号
        String wmsASNCode = wmRoConfirmDto.getWmsOrderCode();// 富勒入库单号（唯一码）
        String modelNo = wmRoConfirmDto.getModelNo();// 型号
        String scanType = wmRoConfirmDto.getScanType();// 类型 1 箱码 2卡板号
        String invoiceNo = wmRoConfirmDto.getInvoiceNo();
        int qty = Integer.parseInt(wmRoConfirmDto.getQty());
        if (StringUtils.isEmpty(roId)) {
            throw Exceptions.OpsException("ROID不可为空.", RoConfirmErrTypeFlagEnum.PARAM_ERROR.getFlag());
        }
        if (StringUtils.isEmpty(barCode)) {
            throw Exceptions.OpsException("箱码不可为空." + roId, RoConfirmErrTypeFlagEnum.PARAM_ERROR.getFlag());
        }
        if (StringUtils.isEmpty(wmRoConfirmDto.getQty())) {
            throw Exceptions.OpsException("数量不可为空." + roId, RoConfirmErrTypeFlagEnum.PARAM_ERROR.getFlag());
        }
        if (qty < 1) {
            throw Exceptions.OpsException("数量需大于0." + roId, RoConfirmErrTypeFlagEnum.PARAM_ERROR.getFlag());
        }
        // 判断回传
        OpsRoPost opsRoPost = opsRoPostService.getRoPostByAsnId(roId, wmsASNCode);
        if (Objects.nonNull(opsRoPost)) {
            throw Exceptions.OpsException("已扫描,无重复扫描." + roId, RoConfirmErrTypeFlagEnum.RO_FINISH_ERROR.getFlag());
        }
        OpsRo opsRo = baseRoService.findRoByRoId(roId);
        if (Objects.isNull(opsRo)) {
            throw Exceptions.OpsException("无此收货单据." + roId, RoConfirmErrTypeFlagEnum.NO_RO_ERROR.getFlag());
        }
        if (!opsRo.getInvoiceNo().equalsIgnoreCase(invoiceNo)) {
            throw Exceptions.OpsException("发票号不一致." + roId + ",RO发票号：" + opsRo.getInvoiceNo() + "，请求发票号：" + invoiceNo, RoConfirmErrTypeFlagEnum.WAREHOUSE_ERROR.getFlag());
        }
        if (!opsRo.getWarehouseCode().equalsIgnoreCase(warehouseCode)) {
            throw Exceptions.OpsException("非本仓货物,不可收货扫描." + roId + ",应由：" + opsRo.getWarehouseCode() + "收货", RoConfirmErrTypeFlagEnum.WAREHOUSE_ERROR.getFlag());
        }
        // 无签收后标记表示还没发票签收
        if (Objects.isNull(opsRo.getIsSign()) || !opsRo.getIsSign()) {
            throw Exceptions.OpsException("无法收货，仓库未签收发票.发票号：" + opsRo.getInvoiceNo(), RoConfirmErrTypeFlagEnum.NO_SIGN_ERROR.getFlag());
        }
        OpsRoItem opsRoItem = baseRoService.findRoItemByRoId(opsRo.getRoId());
        if (Objects.isNull(opsRoItem)) {
            throw Exceptions.OpsException("无此收货单." + roId, RoConfirmErrTypeFlagEnum.NO_RO_ERROR.getFlag());
        }
        // 超额收货判断
        if (opsRoItem.getRecQty() + qty > opsRoItem.getQty()) {
            throw Exceptions.OpsException("入库单." + roId + "，不可超额收货。应收：" + opsRoItem.getQty() + ",已收：" + opsRoItem.getRecQty() + ",本次：" + qty, RoConfirmErrTypeFlagEnum.EXCESS_ERROR.getFlag());
        }
        // 型号判断
        if (!modelNo.equals(opsRoItem.getModelno())) {
            throw Exceptions.OpsException("型号错误.应收型号：" + opsRoItem.getModelno() + "，收货型号：" + modelNo + ".ROID：" + roId, RoConfirmErrTypeFlagEnum.PARAM_ERROR.getFlag());
        }
        // ROID关联在途
        List<OpsRoItemInventory> roItemInventories = baseRoService.findRoItemInventoryByRoId(opsRo.getRoId());
        if (CollectionUtils.isEmpty(roItemInventories)) {
            throw Exceptions.OpsException("无需分配库存ID。入库指令：" + roId, RoConfirmErrTypeFlagEnum.NO_RO_ERROR.getFlag());
        }
        // 按箱码传值
        if ("1".equals(scanType)) {
            // 判断OpsRoBarcode
            OpsRoBarcode opsRoBarcode = opsRoBarcodeService.getRoBarcodeByBarcode(roId, barCode);
            // RO箱码barcode
            if (Objects.isNull(opsRoBarcode)) {
                throw Exceptions.OpsException("无此收货箱码:" + roId + ",箱码：" + barCode, RoConfirmErrTypeFlagEnum.NO_BARCODE_ERROR.getFlag());
            }
            if (!opsRoBarcode.getQty().equals(qty)) {
                throw Exceptions.OpsException("箱码数量与请求数量不一致。" + roId + ",箱码：" + barCode, RoConfirmErrTypeFlagEnum.BARCODE_QTY_UNEQUAL_ERROR.getFlag());
            }
        }
    }

    public OpsRoDto getOpsRoDto(String roId) throws OpsException {
        OpsRo opsRo = baseRoService.findRoByRoId(roId);
        OpsRoItem opsRoItem = baseRoService.findRoItemByRoId(opsRo.getRoId());
        List<OpsRoItemInventory> roItemInventories = baseRoService.findRoItemInventoryByRoId(opsRo.getRoId());
        OpsRoDto opsRoDto = new OpsRoDto(opsRo, opsRoItem, roItemInventories);
        return opsRoDto;
    }


    public void updateOpsRoDto(RoConfirmContext context) throws OpsException {
        String roId = context.getRoId();
        OpsRo opsRo = baseRoService.findRoByRoId(roId);
        OpsRoItem opsRoItem = baseRoService.findRoItemByRoId(opsRo.getRoId());
        context.refreshRoDto(opsRo, opsRoItem);
    }

    private void checkMove(OpsRoItemInventory opsRoItemInventory, OpsInventoryMove move) throws OpsException {
        String roId = opsRoItemInventory.getRoId();
        Long inventoryId = opsRoItemInventory.getInventoryId();
        if (null == move) {
            throw Exceptions.OpsException(String.format("无在途数据OpsInventoryMove,InventoryId:%d。入库指令：%s",
                    inventoryId, roId), RoConfirmErrTypeFlagEnum.NO_MOVE_ERROR.getFlag());
        }
        if (move.getPrepareQuantity() > move.getQuantity()) {
            throw Exceptions.OpsException(String.format("在途数据OpsInventoryMove预约数不可大于在途数,InventoryId:%d。预约数：%d，在途数：%d。入库指令：%s", inventoryId, move.getPrepareQuantity(), move.getQuantity(), roId), RoConfirmErrTypeFlagEnum.PRE_QTY_EXCESS_ERROR.getFlag());
        }
    }

    private void checkPco(OpsPcoItemInventory transPcoItemInventory, OpsPco opsPco, OpsPcoItem opsPcoItem) throws OpsException {
        // 写日志
        if (null == opsPco) {
            throw Exceptions.OpsException(String.format("无出库指令PCOID:%s数据。", transPcoItemInventory.getPcoId()), RoConfirmErrTypeFlagEnum.NO_DO_ERROR.getFlag());
        }
        if (null == opsPcoItem) {
            throw Exceptions.OpsException(String.format("无出OpsPcoItem数据。PCOID:%s", transPcoItemInventory.getPcoId()), RoConfirmErrTypeFlagEnum.NO_DO_ERROR.getFlag());
        }
    }

    private static void checkDo(OpsDoItemInventory transDoItemInventory, OpsDo opsDo, OpsDoItem opsDoItem) throws OpsException {
        if (null == opsDo) {
            throw Exceptions.OpsException("无出库指令DOID:" + transDoItemInventory.getDoId() + "数据。", RoConfirmErrTypeFlagEnum.NO_DO_ERROR.getFlag());
        }
        if (null == opsDoItem) {
            throw Exceptions.OpsException("无出库指令opsDoItem数据， DOID:" + transDoItemInventory.getDoId(), RoConfirmErrTypeFlagEnum.NO_DO_ERROR.getFlag());
        }
    }

    private void handleExchangeOrderForRoConfirm(OpsRo opsRo) {
        stockAssemblyFeignApi.updateAssemblyStatus(opsRo.getOrderId(), true);
    }

    private void handlePurchaseOrderForRoConfirm(RoConfirmContext context, OpsRo opsRo) throws OpsException {
        OpsPurchaseorder purchase = basePoService.getPurchase(opsRo.getOrderId(), Integer.valueOf(opsRo.getOrderItem()), opsRo.getNum());
        if (purchase == null) {
            return;
        }
        List<OpsRequestpurchase> updateRequest = new ArrayList<>();
        for (RoConfirmContextItem item : context.getItems()) {
            OpsInventoryMove move = item.getMove();
            OpsInventory inventory = item.getInventory();
            int subQty = item.getSubQty();
            // 类型为采购，处理请购表
            if (SourceTypeEnum.PURCHASE.getType().equals(move.getSourceType())) {
                // 请购单号不可为空，为空为多到数量
                if (Objects.nonNull(move.getOrderno())) {
                    // 请购表数据记录已收数
                    OpsRequestpurchase opsRequestpurchase = basePoService.getRequestPurchase(move.getOrderno(), move.getItemno(), move.getSplititemno());
                    if (opsRequestpurchase != null && StringUtils.equals(opsRequestpurchase.getModelno(), move.getModelno())) {
                        // 请购表只有一行数据
                        // 3.更变请购表入库数量字段ops_requestPurchase.qtyImport
                        OpsRequestpurchase update = updateRequestpurchaseForImpQty(opsRequestpurchase, subQty);
                        OpsRequestpurchase request = basePoService.getRequestPurchase(update.getId());
                        updateRequest.add(request);
                    }
                }
            }
        }
        // 更新采购单和采购发票表
        updatePurchaseForRecQty(purchase, context.getBarCodeQty(), context.getInvoiceNo(), context.getInvoiceId());
        purchase = basePoService.getPurchase(purchase.getId());
        // 更新ctc的完成状态
        // 2022-10-25 B91717,采购收齐入库同步CTC判断，如果供应商是制造公司，写入CTC中间表，由定时任务每天定时抽取 bug8426
        // BUG15422 增加广州订单发送至CTC
        List<String> suppilyList = Arrays.asList("CN", "CM", "YZ", "CT", "GZ");
        if (purchase.getQtyreceive() >= purchase.getQuantity() && suppilyList.contains(purchase.getSupplierid())) {
            purchaseCtcFinishService.insertMid(purchase.getOrderno(), purchase.getItemno(), purchase.getSplititemno());
        }
        // 更新交货期表
        if (purchase.getQtyreceive() >= purchase.getQuantity()) {
            OrderStateService.sendOrderStateForFinishPurchase(purchase);
        }
        for (OpsRequestpurchase request : updateRequest) {
            OrderStateService.sendOrderStateForFinishRequestPurchase(request);
        }
    }

    private void updatePurchaseForRecQty(OpsPurchaseorder purchase, int recQty, String invoiceNo, Long invoiceId) throws OpsException {
        Date date = new Date();
        OpsPurchaseorder update = new OpsPurchaseorder();
        update.setId(purchase.getId());
        int qtyReceive = purchase.getQtyreceive() + recQty;
        update.setQtyreceive(qtyReceive);
        update.setUpdatetime(date);
        // 如果订单完成
        if (qtyReceive >= purchase.getQuantity()) {
            // 请购收齐入库标记
            update.setStatecode(PurchaseOrderStatusEnum.YIWANCHENG);
            update.setFinishdate(date);
            // bug 12181 2023-09-27 C12961 此时将已完成的采购单对应的请购单的状态也修改为已完成
            List<OpsRequestpurchase> repoList = basePoService.getRequestPurchaseByPurchase(purchase.getOrderno(), purchase.getItemno(), purchase.getSplititemno());
            for (OpsRequestpurchase request : repoList) {
                OpsRequestpurchase updateRepo = new OpsRequestpurchase();
                updateRepo.setId(request.getId());
                updateRepo.setStatecode(RequestPurchaseStatusEnum.YIWANCHENG);
                basePoService.updateRequestPurchaseById(updateRepo);
            }
        }
        basePoService.updatePurchaseById(update);
        updatePurchaseInvoiceForRecQty(purchase, recQty, date, invoiceNo, invoiceId);
        //如果采购完全入库，则发布采购入库事件
        if (qtyReceive >= purchase.getQuantity()) {
            purchaseEventPublisher.purchaseOrderEvent(EventSourceEnum.PURCHASE_ORDER_RO_CONFIRM, purchase, purchase);
        }
    }

    private void updatePurchaseInvoiceForRecQty(OpsPurchaseorder purchase, int recQty, Date date, String invoiceNo, Long invoiceId) throws OpsException {
        List<OpsPurchaseinvoice> purchaseInvoices = basePoService.getPurchaseInvoices(purchase);
        if (CollectionUtils.isEmpty(purchaseInvoices)) {
            log.info("没有查询到purchaseInvoice:" + PoNoUtil.getFullPoNo(purchase));
        } else if (purchaseInvoices.size() > 1) {
            throw Exceptions.OpsException("查询出多条purchaseInvoice:" + PoNoUtil.getFullPoNo(purchase));
        } else {
            OpsPurchaseinvoice invoice = purchaseInvoices.get(0);
            OpsPurchaseinvoice update = new OpsPurchaseinvoice();
            update.setId(invoice.getId());
            int qtyReceive = purchase.getQtyreceive() + recQty;
            update.setQtyreceive(qtyReceive);
            update.setUpdatetime(date);
            // 如果订单完成
            if (qtyReceive >= purchase.getQuantity()) {
                // 请购收齐入库标记
                update.setStatecode(PurchaseOrderStatusEnum.YIWANCHENG);
                //补齐发票号
                if (StringUtils.isBlank(invoice.getInvoiceno())) {
                    update.setInvoiceno(invoiceNo);
                }
                if (invoice.getInvoiceid() == null) {
                    update.setInvoiceid(invoiceId);
                }
            }
            basePoService.updatePurchaseInvoiceById(update);
        }
    }

    private OpsRequestpurchase updateRequestpurchaseForImpQty(OpsRequestpurchase opsRequestpurchase, int subQty) {
        OpsRequestpurchase update = new OpsRequestpurchase();
        update.setId(opsRequestpurchase.getId());
        int qtyImport = opsRequestpurchase.getQtyimport() + subQty;
        update.setQtyimport(qtyImport);// 已收货数量
        update.setUpdatetime(new Date());// 操作时间
        if (qtyImport >= opsRequestpurchase.getQuantity()) {
            // 请购收齐入库标记
            update.setStatecode(RequestPurchaseStatusEnum.YIWANCHENG);
            update.setFinishtime(new Date());
        }
        basePoService.updateRequestPurchaseById(update);
        return update;
    }

    private void insertEvent(RoConfirmContext context) throws OpsException {
        for (RoConfirmContextItem item : context.getItems()) {
            OpsInventoryMove move = item.getMove();
            for (PreDoItemInventory preItemInv : item.getPreDoItemInvs()) {
                OpsDo opsDo = preItemInv.getOpsDo();
                if (DoTypeEnum.JYCK.getType().equals(opsDo.getDoType()) || DoTypeEnum.DBCK.getType().equals(opsDo.getDoType())) {
                    StatusParamDto param = new StatusParamDto();
                    param.setInvoiceNo(context.getInvoiceNo());
                    param.setInvoiceId(context.getInvoiceId());
                    param.setWarehouseCode(context.getWarehouseCode());
                    param.setQuantity(preItemInv.getPreQty());// 转移的数量
                    if (SourceTypeEnum.PURCHASE.getType().equals(move.getSourceType())) {
                        param.setPoNo(move.getAssociateNo());
                        param.setPoItem(move.getAssociateNoItem());
                        param.setPoSplitNo(move.getAssociateNoSplitno());
                    }
                    customerEventPublisher.customerOrderEvent(WAREHOUSE_RECEIVE_CONFIRM,
                            opsDo.getOrderId(), Integer.valueOf(opsDo.getOrderItem()), OrderNoInfo.getFromDo(opsDo).getSplitNoNotNull(), param);
                }
            }
            for (PrePcoItemInventory preItemInv : item.getPrePcoItemInvs()) {
                OpsPco opsPco = preItemInv.getOpsPco();
                OpsPcoItem opsPcoItem = preItemInv.getOpsPcoItem();
                StatusParamDto param = new StatusParamDto();
                param.setInvoiceNo(context.getInvoiceNo());
                param.setInvoiceId(context.getInvoiceId());
                param.setWarehouseCode(context.getWarehouseCode());
                param.setQuantity(preItemInv.getPreQty());// 转移的数量
                if (SourceTypeEnum.PURCHASE.getType().equals(move.getSourceType())) {
                    param.setPoNo(move.getAssociateNo());
                    param.setPoItem(move.getAssociateNoItem());
                    param.setPoSplitNo(move.getAssociateNoSplitno());
                }
                customerEventPublisher.customerOrderEvent(WAREHOUSE_RECEIVE_CONFIRM,
                        opsPco.getOrderId(), Integer.valueOf(opsPco.getOrderItem()), opsPcoItem.getPcoItem(), param);
            }
        }
    }

    /**
     * @description 通过roId查询move库存，把barcode的入库数量分配给这些库存
     * @author C12961
     * @date 2023/4/27 14:19
     */
    private void calculateAllocateQtyForInNormal(RoConfirmContext context) throws OpsException {
        int allocatableQty = context.getBarCodeQty();
        OpsRoDto opsRoDto = getOpsRoDto(context.getRoId());
        context.setOpsRoDto(opsRoDto);
        for (OpsRoItemInventory opsRoItemInventory : context.getOpsRoDto().getRoItemInventoryList()) {
            // 查询并检查库存数量
            OpsInventoryMove move = baseInventoryService.getInventoryMoveById(opsRoItemInventory.getInventoryId());
            checkMove(opsRoItemInventory, move);
            // 如果move库存为0，则跳过
            Integer moveQuantity = move.getQuantity();
            if (moveQuantity < 1) continue;
            // 查询要转到的在库库存
            OpsInventory inventory = getInventoryId(move, context.getUserDto());
            // 创建库存转移统计信息
            RoConfirmContextItem item = new RoConfirmContextItem(opsRoItemInventory, move, inventory);
            context.addItems(item);
        }
        for (RoConfirmContextItem item : context.getItems()) {
            if (allocatableQty < 1) break;
            // 计算要转移的在途库存数量
            int subQty = Math.min(allocatableQty, item.getMove().getQuantity());
            item.setSubQty(subQty);
            // 计算要转移的在途库存的总预约数
            int subPreQty = calculateSubPreQty(context, item);
            item.setSubPreQty(subPreQty);
            // 箱数量递减
            allocatableQty -= subQty;
        }
        // move库存入库数分配完，还有剩余可分配数
        if (allocatableQty > 0) {
            throw Exceptions.OpsException(String.format("箱码：%s，数量：%d,剩余数量：%d无法分配。", context.getBarCode(),
                    context.getBarCodeQty(), allocatableQty), RoConfirmErrTypeFlagEnum.OTHER_ERROR.getFlag());
        }
        log.info("计算完成：{}", JSONUtil.toJsonPrettyStr(context));
    }

    /**
     * @description 输入一条move, 计算要分配给哪些do
     * @author C12961
     * @date 2023/5/4 16:59
     */
    private int calculateSubPreQty(RoConfirmContext context, RoConfirmContextItem item) throws OpsException {
        // 最多可转移的预占数量
        int availableQty = item.getSubQty();
        // 查询do表的关联关系
        List<OpsDoItemInventory> opsDoItemInventories = baseDoService.getDoItemInventoryByInventoryId(item.getMove().getInventoryId(), InventoryTableTypeEnum.TRANS);
        for (OpsDoItemInventory transDoItemInventory : opsDoItemInventories) {
            // 该库存无可分配数量给订单时，直接跳过
            if (availableQty < 1) break;
            int useQty = transDoItemInventory.getUseQty();
            if (useQty < 1) continue;
            OpsDo opsDo = baseDoService.getDoByDoId(transDoItemInventory.getDoId());
            OpsDoItem opsDoItem = baseDoService.getDoItemByDoId(transDoItemInventory.getDoId());
            OpsDoItemInventory targetDoItemInventory = getTargetDoItemInventory(item, transDoItemInventory, context.getUserDto());
            checkDo(transDoItemInventory, opsDo, opsDoItem);
            // 该doItemInventory要扣减的T的数量,也是该doItemInventory要增加的N的数量
            int preQty = Math.min(availableQty, useQty);
            item.addPreDoItemInventory(opsDo, opsDoItem, transDoItemInventory, targetDoItemInventory, preQty);
            availableQty -= preQty;
        }
        List<OpsPcoItemInventory> opsPcoItemInventories = opsPcoService.getOpsPcoItemInventoryByInventoryId(item.getMove().getInventoryId(), InventoryTableTypeEnum.TRANS);
        for (OpsPcoItemInventory transPcoItemInventory : opsPcoItemInventories) {
            // 该库存无可分配数量给订单时，直接跳过
            if (availableQty < 1) break;
            int useQty = transPcoItemInventory.getUseQty();
            if (useQty < 1) continue;
            OpsPco opsPco = opsPcoService.getPcoByPcoId(transPcoItemInventory.getPcoId());
            OpsPcoItem opsPcoItem = opsPcoService.getPcoItemByPcoId(transPcoItemInventory.getPcoId(), transPcoItemInventory.getPcoItem());
            checkPco(transPcoItemInventory, opsPco, opsPcoItem);
            OpsPcoItemInventory targetPcoItemInventory = getTargetPcoItemInventory(item, transPcoItemInventory, context.getUserDto());
            int preQty = Math.min(availableQty, useQty);
            item.addPrePcoItemInventory(opsPco, opsPcoItem, transPcoItemInventory, targetPcoItemInventory, preQty);
            availableQty -= preQty;
        }
        // prepareQty数量是N库存要增加的预占数,即多条preQty的和
        return item.getPrepareQty();
    }

    private void updateTaskToCheckForDo(String doId, UserDto userDto) throws OpsException {
        // 判断DOID有没有T，没就下发(货齐)
        List<OpsDoItemInventory> opsDoItemInventoryList = baseDoService.getDoItemInventoryByDoId(doId, InventoryTableTypeEnum.TRANS);
        if (CollectionUtils.isNotEmpty(opsDoItemInventoryList)) {
            return;
        }
        OpsDoItem doItem = baseDoService.getDoItemByDoId(doId);
        List<OpsDoItemInventory> opsNormalItemInventoryList = baseDoService.getDoItemInventoryByDoId(doId, InventoryTableTypeEnum.NORMAL);
        Integer qty = opsNormalItemInventoryList.stream().map(inv -> inv.getUseQty()).reduce(Integer::sum).orElse(0);
        if (!doItem.getQty().equals(qty)) {
            return;
        }
        // do的wait改OK & task 4改3
        // doWaitType改OK
        OpsDo opsDo = baseDoService.getDoByDoId(doId);
        opsDoService.updateDoWaitTypeForDoId(opsDo.getDoId(), DoWaitTypeEnum.OK.getType(), opsDo.getVersion(), userDto.getUserName());
        // doId 4 => 3
        if (DoTypeEnum.JYCK.getType().equals(opsDo.getDoType())) {
            // 货齐单仓，货齐多仓 按照七位单号判断货齐
            if (CKTYPEEnum.ORDER_GETHER_SIGNLE_HOUSE.getCode().equals(opsDo.getDlvEntire())
                    || CKTYPEEnum.ORDER_GETHER_MULTI_HOUSE.getCode().equals(opsDo.getDlvEntire())) {
                wmOrderTaskService.changeTaskFlagToTreeByOrder(opsDo.getOrderId(), null);
            } else {
                // 12843 随发、随发单仓、随发分批   按照十位订单号判断货齐 2023-12-20
                wmOrderTaskService.changeTaskFlagToTreeByOrder(opsDo.getOrderId(), opsDo.getOrderItem());
            }
        } else if (DoTypeEnum.TKCK.getType().equals(opsDo.getDoType()) || DoTypeEnum.DBCK.getType().equals(opsDo.getDoType())) {
            if (!opsDo.getWarehouseCode().equals(opsDo.getGatherWarehouseCode())) {
                // 4改3，算货齐
                wmOrderTaskService.changeTaskFlagToTreeByDOIdOrPCOId(opsDo.getDoId(), WmOrderTaskPriorityEnum.EIGHT);
            }
        }
    }

    private void updateTaskToCheckForPco(OpsDo jyck, String pcoId, Integer pcoItem, UserDto userDto) throws OpsException {
        List<OpsPcoItemInventory> opsPcoItemInventoryList = opsPcoService.findPcoItemInventoryByPcoIdAndItemAndTableType(pcoId, pcoItem, InventoryTableTypeEnum.TRANS);
        if (CollectionUtils.isEmpty(opsPcoItemInventoryList)) {
            // 查N的数据
            List<OpsPcoItemInventory> opsNormalItemInventoryList = opsPcoService.findPcoItemInventoryByPcoIdAndItemAndTableType(pcoId, pcoItem, InventoryTableTypeEnum.NORMAL);
            Integer qty = opsNormalItemInventoryList.stream().map(inv -> inv.getUseQty()).reduce(Integer::sum).orElse(0);
            OpsPcoItem opsPcoItem = opsPcoService.getPcoItemByPcoIdAndItem(pcoId, pcoItem);
            // 数量齐才会改ok,不齐不会改ok
            if (!opsPcoItem.getSubQty().equals(qty)) {
                return;
            }
            // pcoItem的WaitType改OK
            opsPcoService.updatePcoItemWaitTypeForItem(pcoId, pcoItem, DoWaitTypeEnum.OK.getType(), userDto.getUserName());
            if (CKTYPEEnum.ORDER_GETHER_SIGNLE_HOUSE.getCode().equals(jyck.getDlvEntire())
                    || CKTYPEEnum.ORDER_GETHER_MULTI_HOUSE.getCode().equals(jyck.getDlvEntire())) {
                //七位单号 4 => 3
                wmOrderTaskService.changeTaskFlagToTreeByOrder(jyck.getOrderId(), null);
            } else {
                // pcoId 4 => 3
                wmOrderTaskService.changeTaskFlagToTreeByDOIdOrPCOId(pcoId, WmOrderTaskPriorityEnum.EIGHT);
            }
        }
    }

    private OpsDoItemInventory getTargetDoItemInventory(RoConfirmContextItem item, OpsDoItemInventory srcItemInventory, UserDto userDto) throws OpsException {
        OpsInventory inventory = item.getInventory();
        OpsDoItemInventory targetItemInventory = opsDoService.getOpsDoItemInventoryBySrcInventoryId(srcItemInventory.getDoId(),
                srcItemInventory.getInventoryId(), InventoryTableTypeEnum.NORMAL);
        if (Objects.isNull(targetItemInventory)) {
            OpsDoItemInventory doItemInventory = new OpsDoItemInventory();
            doItemInventory.setDoId(srcItemInventory.getDoId());
            doItemInventory.setDoItem(srcItemInventory.getDoItem());
            doItemInventory.setInventoryId(inventory.getInventoryId());
            doItemInventory.setInventoryTableType(InventoryTableTypeEnum.NORMAL.getType());
            doItemInventory.setSrcInventoryId(srcItemInventory.getInventoryId());
            doItemInventory.setSrcInventoryTableType(InventoryTableTypeEnum.TRANS.getType());
            doItemInventory.setUseQty(0);// 当前分配的数量
            doItemInventory.setOutQty(0);
            doItemInventory.setSortnum(1);
            doItemInventory.setModifier(userDto.getUserName());
            doItemInventory.setModifyTime(new Date());
            opsDoService.insertDoItemInventory(doItemInventory);
            targetItemInventory = opsDoService.getOpsDoItemInventoryBySrcInventoryId(srcItemInventory.getDoId(),
                    srcItemInventory.getInventoryId(), InventoryTableTypeEnum.NORMAL);
        }
        return targetItemInventory;
    }

    private OpsPcoItemInventory getTargetPcoItemInventory(RoConfirmContextItem item, OpsPcoItemInventory srcItemInventory, UserDto userDto) throws OpsException {
        OpsInventory inventory = item.getInventory();
        OpsPcoItemInventory targetItemInventory = opsPcoService.getOpsPcoItemInventoryBySrcInventoryId(srcItemInventory.getPcoId(), srcItemInventory.getPcoItem(),
                srcItemInventory.getInventoryId(), InventoryTableTypeEnum.NORMAL);
        // 写入行
        if (Objects.isNull(targetItemInventory)) {
            OpsPcoItemInventory pcoItemInventory = new OpsPcoItemInventory();
            pcoItemInventory.setPcoId(srcItemInventory.getPcoId());
            pcoItemInventory.setPcoItem(srcItemInventory.getPcoItem());
            pcoItemInventory.setPcoType(1);// 加工类型：1拆分、2组装
            pcoItemInventory.setInventoryId(inventory.getInventoryId());
            pcoItemInventory.setInventoryTableType(InventoryTableTypeEnum.NORMAL.getType());
            pcoItemInventory.setSrcInventoryId(srcItemInventory.getInventoryId());
            pcoItemInventory.setSrcInventoryTableType(InventoryTableTypeEnum.TRANS.getType());
            pcoItemInventory.setUseQty(0);// 当前分配的数量
            pcoItemInventory.setOutQty(0);
            pcoItemInventory.setSortnum(1);
            pcoItemInventory.setModifier(userDto.getUserName());
            pcoItemInventory.setModifyTime(new Date());
            opsPcoService.insertPcoItemInventory(pcoItemInventory);
            targetItemInventory = opsPcoService.getOpsPcoItemInventoryBySrcInventoryId(srcItemInventory.getPcoId(), srcItemInventory.getPcoItem(),
                    srcItemInventory.getInventoryId(), InventoryTableTypeEnum.NORMAL);
        }
        return targetItemInventory;
    }

    private OpsInventory getInventoryId(OpsInventoryMove move, UserDto userDto) throws OpsException {
        OpsInventory condition = new OpsInventory();
        condition.setWarehouseCode(move.getSignWarehouseCode());// 签收仓加库存数
        condition.setModelno(move.getModelno());
        condition.setInventoryPropertyId(move.getInventoryPropertyId());
        List<OpsInventory> opsInventoryList = baseInventoryService.findOpsInventory(condition);
        if (CollectionUtils.isEmpty(opsInventoryList)) {
            // 新增行
            OpsInventory sourceInventory = createInventoryNormal(move, 0, 0);
            Long inventoryId = baseInventoryService.createInvReturnId(sourceInventory, userDto);
            return baseInventoryService.getInventoryById(inventoryId);
        } else {
            return opsInventoryList.get(0);
        }
    }

    private static OpsInventory createInventoryNormal(OpsInventoryMove opsInventoryMove, int moveQty, int prepareQty) {
        OpsInventory sourceInventory = new OpsInventory();
        sourceInventory.setWarehouseCode(opsInventoryMove.getSignWarehouseCode());// 签收仓加库存数
        sourceInventory.setInventoryStatus(InventoryStatusEnum.NORMAL.getCode());// 正常在库
        sourceInventory.setQuantity(moveQty);// 当前
        sourceInventory.setPrepareQuantity(prepareQty);
        sourceInventory.setUnit(opsInventoryMove.getUnit());
        sourceInventory.setQaStatus(QAStatusEnum.NORMAL.getType());
        sourceInventory.setModelno(opsInventoryMove.getModelno());
        sourceInventory.setInventoryPropertyId(opsInventoryMove.getInventoryPropertyId());
        sourceInventory.setPrice(opsInventoryMove.getPrice());
        sourceInventory.setInTime(new Date());
        return sourceInventory;
    }


}
