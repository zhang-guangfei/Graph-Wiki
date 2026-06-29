package com.sales.ops.serviceimpl.event.v3.order.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONUtil;
import com.sales.ops.common.opsexception.Exceptions;
import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.db.entity.*;
import com.sales.ops.enums.*;
import com.sales.ops.service.inventory.OpsInventoryPropertyService;
import com.sales.ops.service.log.OpsHandOverService;
import com.sales.ops.service.order.BaseCustomerOrderService;
import com.sales.ops.service.wmOrder.BaseDoService;
import com.sales.ops.service.wmOrder.OpsPcoService;
import com.sales.ops.serviceimpl.event.v3.order.entity.*;
import com.sales.ops.serviceimpl.event.v3.status.enums.RelationErrorCode;
import com.sales.ops.serviceimpl.inventory.BaseInventoryService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.sales.ops.serviceimpl.event.v3.status.enums.RelationErrorCode.*;


/**
 * 领域服务
 * 1.加载Order实体
 * 2.加载Jyck实体
 * 3.加载关联关系
 * 4.操作数据库状态表
 */
@Slf4j
@Service
@AllArgsConstructor
public class OrderRelationService {

    private BaseDoService baseDoService;
    private OpsPcoService opsPcoService;
    private BaseInventoryService baseInventoryService;
    private OpsInventoryPropertyService opsInventoryPropertyService;
    private BaseCustomerOrderService baseCustomerOrderService;
    private OpsHandOverService opsHandOverService;


    public Order getOrder(String orderNo, Integer orderItem) throws OpsException {
        // 创建客户订单实体
        Rcvdetail rcvdetail = baseCustomerOrderService.findRcvDetailByKey(orderNo, orderItem);
        if (rcvdetail == null) {
            throw Exceptions.OpsException(RelationErrorCode.NOT_FOUND_RCV.getDesc());
        }
        if (rcvdetail.getStatus().equals(RcvOrderStatusEnum.CANCEL.getType())) {
            throw Exceptions.OpsException(RelationErrorCode.RCV_CANCELED.getDesc());
        }
        Rcvmaster rcvmaster = baseCustomerOrderService.findRcvMaster(orderNo);
        if (rcvmaster == null) {
            throw Exceptions.OpsException(RelationErrorCode.NOT_FOUND_RCV.getDesc());
        }
        Order order = new Order(rcvmaster, rcvdetail);
        List<JYCK> jycKs = getJYCKs(order.getOrderId(), order.getOrderItem());
        if (CollectionUtils.isEmpty(jycKs)) {
            throw Exceptions.OpsException(NOT_FOUND_JYCK.getDesc());
        }
        order.setJycks(jycKs);
        return order;
    }

    public List<JYCK> getJYCKs(String orderNo, Integer orderItem) throws OpsException {
        // 加载客户订单的交易出库实体
        List<JYCK> jycks = loadJycks(orderNo, orderItem);
        for (JYCK jyck : jycks) {
            // 加载交易出库指令实体的关联关系
            loadRelations(jyck);
        }
        return jycks;
    }

    // 查询并加载交易出库实体
    private List<JYCK> loadJycks(String orderId, Integer orderItem) throws OpsException {
        List<JYCK> jyckList = new ArrayList<>();
        List<OpsDo> jyDoList = baseDoService.findAllJYCKByOrder(orderId, orderItem.toString());
        if (CollectionUtils.isEmpty(jyDoList)) {
            return jyckList;
        }
        // 全为型号拆分
        boolean isAssModel = jyDoList.stream().allMatch(jyDo -> DoSourceEnum.isAssModel(jyDo.getDoSource()));
        // 全为数量拆分或不拆分
        boolean notAssModel = jyDoList.stream()
                .allMatch(jyDo -> DoSourceEnum.ALL.getType().equalsIgnoreCase(jyDo.getDoSource())
                        || DoSourceEnum.CG.getType().equalsIgnoreCase(jyDo.getDoSource())
                        || DoSourceEnum.ASSQTY.getType().equalsIgnoreCase(jyDo.getDoSource())
                );
        // 1.数量拆分或不拆分
        if (notAssModel) {
            for (OpsDo jyDo : jyDoList) {
                OpsDoItem jyDoItem = baseDoService.getDoItemByDoId(jyDo.getDoId());
                JYCK jyck = new JYCK(jyDo, jyDoItem);
                jyckList.add(jyck);
            }
        }
        // 2.型号拆分
        else if (isAssModel) {
            for (OpsDo jyDo : jyDoList) {
                OpsPco pco = opsPcoService.findPcoByOrder(jyDo.getOrderId(), jyDo.getOrderItem(), jyDo.getNum());
                List<OpsPcoItem> pcoItems = opsPcoService.findPcoItemByPcoId(pco.getPcoId());
                for (OpsPcoItem pcoItem : pcoItems) {
                    JYCK jyck = new JYCK(jyDo, pcoItem);
                    jyckList.add(jyck);
                }
            }
        }
        // 3.既有型号拆分也有数量拆分
        else {
            throw Exceptions.OpsException(PARSE_FAIL_DO_SOURCE.getDesc());
        }
        return jyckList;
    }

    private List<Relation> loadRelations(JYCK jyck) throws OpsException {
        // 数量拆分或不拆分
        if (DoSourceEnum.ASSModelNo != jyck.getDoSource()) {
            getRelationFromDo(jyck);
        }
        // 型号拆分
        else {
            getRelationFromPco(jyck);
        }
        // 检测搜索到的关联关系总数量是否充足
        if (!jyck.isFullAllot()) {
            log.error("{},{}", NOT_FULL_RELATION_QTY.getDesc(), JSONUtil.toJsonStr(jyck));
            throw Exceptions.OpsException(NOT_FULL_RELATION_QTY.getDesc());
        }
        for (Relation relation : jyck.getRelations()) {
            // 填充关联关系的库存详细信息
            fillInventoryDescForRelation(jyck, relation);
        }
        return jyck.getRelations();
    }

    public void fillInventoryDescForRelation(JYCK jyck, Relation relation) throws OpsException {
        if (relation instanceof OKRelation) {
            OKRelation okRelation = (OKRelation) relation;
            OpsInventory normal = baseInventoryService.getInventoryById(okRelation.getInvId());
            if (normal == null) {
                throw Exceptions.OpsException(NOT_FOUND_INVENTORY.getDesc());
            }
            OpsInventoryProperty property = opsInventoryPropertyService.findById(normal.getInventoryPropertyId());
            okRelation.fillInventoryInfo(normal, property);
        }
        // 采购
        else if (relation instanceof CGRelation) {
            CGRelation cgRelation = (CGRelation) relation;
            OpsInventoryMove cgMove = null;
            OpsInventoryProperty property = null;
            if (relation.getInvId() != null) {
                cgMove = baseInventoryService.getInventoryMoveById(cgRelation.getInvId());
                if (cgMove == null) {
                    throw Exceptions.OpsException(NOT_FOUND_INVENTORY.getDesc());
                }
                property = opsInventoryPropertyService.findById(cgMove.getInventoryPropertyId());
            }
            cgRelation.fillInventoryInfo(cgMove, property);
        }
        // 调拨
        else if (relation instanceof DBRelation) {
            DBRelation dbRelation = (DBRelation) relation;
            // 在库调拨库存，还没调拨出的库存
            if (InventoryTableTypeEnum.NORMAL.getType().equals(dbRelation.getInvTable().getType())) {
                OpsInventory normal = baseInventoryService.getInventoryById(dbRelation.getInvId());
                if (normal == null) {
                    throw Exceptions.OpsException(NOT_FOUND_INVENTORY.getDesc());
                }
                OpsInventoryProperty property = opsInventoryPropertyService.findById(normal.getInventoryPropertyId());
                dbRelation.fillInventoryInfo(normal, property);
            }
            // 在途调库库存，已经调拨出的库存
            else if (InventoryTableTypeEnum.TRANS.getType().equals(dbRelation.getInvTable().getType())) {
                if (dbRelation.getInvId() == null) {
                    dbRelation.fillInventoryInfo();
                } else {
                    OpsInventoryMove dbMove = baseInventoryService.getInventoryMoveById(dbRelation.getInvId());
                    if (dbMove == null) {
                        throw Exceptions.OpsException(NOT_FOUND_INVENTORY.getDesc());
                    }
                    OpsInventoryProperty property = opsInventoryPropertyService.findById(dbMove.getInventoryPropertyId());
                    dbRelation.fillInventoryInfo(dbMove, property);
                }

            }
        }
        // 异常
        else if (relation instanceof EXRelation) {
            //  无
            EXRelation eXRelation = (EXRelation) relation;
        }
    }

    private void getRelationFromDo(JYCK jyck) throws OpsException {
        // 查询关联关系表
        List<OpsDoItemInventory> okItemInvs = baseDoService.getDoItemInventoryByDoId(jyck.getJyDoId(), InventoryTableTypeEnum.NORMAL);
        List<OpsDoItemInventory> transItemInvs = baseDoService.getDoItemInventoryByDoId(jyck.getJyDoId(), InventoryTableTypeEnum.TRANS);
        // 无关联关系
        if (CollectionUtils.isEmpty(okItemInvs) && CollectionUtils.isEmpty(transItemInvs)) {
            // 等待类型异常
            if (jyck.getWaitType() == DoWaitTypeEnum.EXCEPTION) {
                jyck.addRelation(new EXRelation(jyck, InventoryStatusEnum.X));
            }
            // 等待类型取消
            else if (jyck.getWaitType() == DoWaitTypeEnum.CANCEL) {
                jyck.addRelation(new EXRelation(jyck, null));
            }
            // 等待类型采购
            else if (jyck.getWaitType() == DoWaitTypeEnum.WaitCG) {
                // 一定有请购单，但不一定有采购单
                jyck.addRelation(new CGRelation(jyck.getJyckItem(), jyck.getOrderId(), jyck.getOrderItem(), jyck.getNum()));
            }
            // 如果等待类型是调拨，就用调拨单号查询调拨单
            else if (jyck.getWaitType() == DoWaitTypeEnum.WaitDB || jyck.getWaitType() == DoWaitTypeEnum.OK) {
                getRelationFromDBCK(jyck);
                //如果没有调拨单，则补足一定数量的关联关系，虚拟一条调拨
                //比如当前jyck单共用了另一条jyck的调拨单
                int qty = jyck.getQty() - jyck.getAllotQty();
                if (qty > 0) {
                    jyck.addRelation(new DBRelation(jyck.getJyckItem(), qty, jyck.getJyckDo()));
                    jyck.setWaitType(DoWaitTypeEnum.WaitDB);
                }
            }
        }
        // 有关联关系
        else {
            // 在途关联关系
            if (CollectionUtils.isNotEmpty(transItemInvs)) {
                for (OpsDoItemInventory doItemInv : transItemInvs) {
                    OpsInventoryMove move = baseInventoryService.getInventoryMoveById(doItemInv.getInventoryId());
                    // 采购在途
                    if (SourceTypeEnum.PURCHASE.getType().equals(move.getSourceType())) {
                        // 交易关联关系
                        jyck.addRelation(new CGRelation(doItemInv, move.getOrderno(), move.getItemno(), move.getSplititemno()));
                        jyck.setWaitType(DoWaitTypeEnum.WaitCG);
                    }
                    // 调拨在途 T3
                    if (SourceTypeEnum.DB.getType().equals(move.getSourceType())) {
                        //不应该得到list，应该得到一条Do,因为该move只能属于一个DB单， 但是一个JYCK可能存在两个DB单
                        List<OpsDo> dbList = baseDoService.findAllTypeDBCKListByOrder(move.getAssociateNo(), move.getAssociateNoItem().toString(), move.getAssociateNoSplitno(), DoSourceEnum.ASSQTY);
                        for (OpsDo dbDo : dbList) {
                            //用move发票号查询该moveT3的调拨单doId，如果能查到，说明此条db单是该moveT3的调拨单,如果查不到，则跳过该dbDo
                            List<OpsHandover> handOvers = opsHandOverService.findHandOvers(dbDo.getDoId(), move.getInvoiceno());
                            if (CollectionUtils.isNotEmpty(handOvers)) {
                                OpsDoItem doItem = baseDoService.getDoItemByDoId(dbDo.getDoId());
                                jyck.addRelation(new DBRelation(doItem, doItemInv, dbDo));
                                jyck.setWaitType(DoWaitTypeEnum.WaitDB);
                                break;
                            }
                        }
                    }
                }
            }
            // 在库关联关系
            if (CollectionUtils.isNotEmpty(okItemInvs)) {
                for (OpsDoItemInventory doItemInv : okItemInvs) {
                    int outQty = Optional.ofNullable(doItemInv.getOutQty()).orElse(0);
                    // 没发货的交易出库单
                    if (outQty == 0) {
                        OKRelation okRelation = new OKRelation(jyck.getJyckDo(), doItemInv);
                        jyck.addRelation(okRelation);
                    }
                    // 发完货的交易出库单
                    else if (doItemInv.getUseQty().equals(outQty)) {
                        OKRelation okRelation = new OKRelation(jyck.getJyckDo(), doItemInv);
                        jyck.addRelation(okRelation);
                    }
                    // 如果部分已发货，则按照运单号创建关联关系?
                    else if (outQty > 0 && outQty < doItemInv.getUseQty()) {
                        OKRelation okRelation = new OKRelation(jyck.getJyckDo(), doItemInv);
                        // 没发货的部分
                        int useQty = okRelation.getUseQty() - outQty;
                        if (useQty > 0) {
                            okRelation.setUseQty(useQty);
                            okRelation.setOutQty(0);
                            jyck.addRelation(okRelation);
                        }
                        OKRelation outRelation = new OKRelation();
                        BeanUtil.copyProperties(okRelation, outRelation);
                        outRelation.setUseQty(outQty);
                        outRelation.setOutQty(outQty);
                        jyck.addRelation(outRelation);
                    }
                }
                //等待类型
                if (CollectionUtils.isEmpty(transItemInvs)) {
                    jyck.setWaitType(DoWaitTypeEnum.OK);
                }
            }
            // 如果关联关系不全，就查调拨单
            if (!jyck.isFullAllot()) {
                getRelationFromDBCK(jyck);
            }
        }

    }

    private void getRelationFromPco(JYCK jyck) throws OpsException {
        OpsPcoItem pcoItem = jyck.getPcoItem();
        // 查询关联关系
        List<OpsPcoItemInventory> okItemInvs = opsPcoService.findPcoItemInventoryByPcoIdAndItemAndTableType(pcoItem.getPcoId(), pcoItem.getPcoItem(), InventoryTableTypeEnum.NORMAL);
        List<OpsPcoItemInventory> transItemInvs = opsPcoService.findPcoItemInventoryByPcoIdAndItemAndTableType(pcoItem.getPcoId(), pcoItem.getPcoItem(), InventoryTableTypeEnum.TRANS);
        // 无关联关系
        if (CollectionUtils.isEmpty(okItemInvs) && CollectionUtils.isEmpty(transItemInvs)) {
            // 等待类型异常
            if (jyck.getWaitType() == DoWaitTypeEnum.EXCEPTION) {
                jyck.addRelation(new EXRelation(jyck, InventoryStatusEnum.X));
            }
            // 等待类型取消
            else if (jyck.getWaitType() == DoWaitTypeEnum.CANCEL) {
                jyck.addRelation(new EXRelation(jyck, null));
            }
            // 等待类型采购
            else if (jyck.getWaitType() == DoWaitTypeEnum.WaitCG) {
                // 一定有请购单，但不一定有采购单
                jyck.addRelation(new CGRelation(pcoItem, jyck.getOrderId(), jyck.getOrderItem(), jyck.getAssNum()));
                jyck.setWaitType(DoWaitTypeEnum.WaitCG);
            }
            // 如果等待类型是调拨，就用调拨单号查询调拨单
            else if (jyck.getWaitType() == DoWaitTypeEnum.WaitDB || jyck.getWaitType() == DoWaitTypeEnum.OK) {
                getRelationFromDBCK(jyck);
            }
        }
        // 有关联关系
        else {
            // 在途关联关系
            if (CollectionUtils.isNotEmpty(transItemInvs)) {
                for (OpsPcoItemInventory pcoItemInv : transItemInvs) {
                    OpsInventoryMove move = baseInventoryService.getInventoryMoveById(pcoItemInv.getInventoryId());
                    // 采购在途
                    if (SourceTypeEnum.PURCHASE.getType().equals(move.getSourceType())) {
                        jyck.addRelation(new CGRelation(pcoItemInv, move.getOrderno(), move.getItemno(), move.getSplititemno()));
                        jyck.setWaitType(DoWaitTypeEnum.WaitCG);
                    }
                    // 调拨在途
                    if (SourceTypeEnum.DB.getType().equals(move.getSourceType())) {
                        List<OpsDo> dbList = baseDoService.findAllTypeDBCKListByOrder(move.getAssociateNo(), move.getAssociateNoItem().toString(), move.getAssociateNoSplitno(), DoSourceEnum.ASSModelNo);
                        //不应该得到list，应该得到一条Do,因为该move只能属于一个DB单， 但是一个JYCK可能存在两个DB单
                        for (OpsDo dbDo : dbList) {
                            //用move发票号查询该moveT3的调拨单doId，如果能查到，说明此条db单是该moveT3的调拨单,如果查不到，则跳过该dbDo
                            List<OpsHandover> handOvers = opsHandOverService.findHandOvers(dbDo.getDoId(), move.getInvoiceno());
                            if (CollectionUtils.isNotEmpty(handOvers)) {
                                jyck.addRelation(new DBRelation(pcoItemInv, dbDo));
                                jyck.setWaitType(DoWaitTypeEnum.WaitDB);
                                break;
                            }
                        }
                    }
                }
            }
            // 在库关联关系
            if (CollectionUtils.isNotEmpty(okItemInvs)) {
                for (OpsPcoItemInventory pcoItemInv : okItemInvs) {
                    jyck.addRelation(new OKRelation(jyck.getJyckDo(), pcoItemInv));
                }
                if (CollectionUtils.isEmpty(transItemInvs)) {
                    jyck.setWaitType(DoWaitTypeEnum.OK);
                }
            }
            // 如果关联关系不全，就查调拨单
            if (!jyck.isFullAllot()) {
                getRelationFromDBCK(jyck);
            }
        }
    }

    /**
     * 一个交易单可以关联多个调拨单，一个调拨单也可以被多个交易单共用
     *
     * @param jyck
     * @throws OpsException
     */
    private void getRelationFromDBCK(JYCK jyck) throws OpsException {
        // 不查CGDBCK，因为CGDBCK是补库单的调拨单,调拨单号写的是补库单号，不能被客户预约，调拨单一定是客户的调拨单
        List<OpsDo> dbList = baseDoService.findDoListByOrder(jyck.getOrderId(), jyck.getOrderItem().toString(), jyck.getNum(), jyck.getAssNum(), DoTypeEnum.DBCK);
        for (OpsDo dbck : dbList) {
            OpsDoItem doItem = baseDoService.getDoItemByDoId(dbck.getDoId());
            // 18421 2025-09-12，因为越库时先组波次后入库，分界点在入库后，又没有拣货流程，所以不设置分界点，不判断是否全都为N
            // 在途关联关系
            List<OpsDoItemInventory> doItemInvs = baseDoService.getDoItemInventoryByDoId(dbck.getDoId(), InventoryTableTypeEnum.TRANS);
            for (OpsDoItemInventory doItemInv : doItemInvs) {
                OpsInventoryMove move = baseInventoryService.getInventoryMoveById(doItemInv.getInventoryId());
                if (SourceTypeEnum.PURCHASE.getType().equals(move.getSourceType())) {
                    jyck.addRelation(new CGRelation(doItemInv, move.getOrderno(), move.getItemno(), move.getSplititemno()));
                    jyck.setWaitType(DoWaitTypeEnum.WaitCG);
                } else if (SourceTypeEnum.DB.getType().equals(move.getSourceType())) {
                    jyck.addRelation(new DBRelation(doItem, doItemInv, dbck));
                    jyck.setWaitType(DoWaitTypeEnum.WaitDB);
                } else {
                    throw Exceptions.OpsException("暂不支持该关联关系：moveId" + move.getInventoryId());
                }
            }
            //在库关联关系
            List<OpsDoItemInventory> dbItemInvs = baseDoService.getDoItemInventoryByDoId(dbck.getDoId(), InventoryTableTypeEnum.NORMAL);
            for (OpsDoItemInventory dbItemInv : dbItemInvs) {
                // 未发数量
                int qty = dbItemInv.getUseQty() - Optional.ofNullable(dbItemInv.getOutQty()).orElse(0);
                if (qty > 0) {
                    jyck.addRelation(new DBRelation(doItem, dbItemInv, dbck));
                    jyck.setWaitType(DoWaitTypeEnum.WaitDB);
                }
            }
            //有可能一条调拨单被多个交易单预约，可能调拨数量远大于交易未分配数量
            //如果交易单还缺关联关系
            int jyNotAllotQty = jyck.getQty() - jyck.getAllotQty();
            if (jyNotAllotQty > 0) {
                //已发，但未handConfirm的数量
                Integer outQty = Optional.ofNullable(doItem.getOutQty()).orElse(0);
                if (outQty > 0) {
                    String dbDoId = dbck.getDoId();
                    //该调拨单T3的数量
                    int T3Qty = jyck.getDBT3Qty(dbDoId);
                    //说明已经handover过，应该存在关联关系
                    List<OpsHandover> handOvers = opsHandOverService.findHandOvers(dbck.getDoId(), null);
                    if (CollectionUtils.isNotEmpty(handOvers) && T3Qty == 0) {
                        // 如果不存在关联关系，说明此调拨单和此JYCK无关，应该忽略
                        continue;
                    }
                    //outQty-T3Qty-（T3已转NQty）
                    int qty = outQty - T3Qty;
                    jyck.addRelation(new DBRelation(doItem, Math.min(qty, jyNotAllotQty), dbck));
                    jyck.setWaitType(DoWaitTypeEnum.WaitDB);
                }
            }
            if (jyck.isFullAllot()) {
                break;
            }
        }
    }
}




