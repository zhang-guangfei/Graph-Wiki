package com.sales.ops.serviceimpl.dispatch.podispatch.service;

import cn.hutool.json.JSONUtil;
import com.sales.ops.common.opsexception.Exceptions;
import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.db.entity.*;
import com.sales.ops.dto.inventory.OpsDoDto;
import com.sales.ops.dto.inventory.OpsPcoItemDto;
import com.sales.ops.dto.purchase.PoToWmDto;
import com.sales.ops.dto.util.OrderNoInfo;
import com.sales.ops.dto.util.UserDto;
import com.sales.ops.enums.DoSourceEnum;
import com.sales.ops.enums.DoWaitTypeEnum;
import com.sales.ops.enums.InventoryTableTypeEnum;
import com.sales.ops.enums.SourceTypeEnum;
import com.sales.ops.service.inventory.OpsInventoryPropertyService;
import com.sales.ops.service.po.BasePoService;
import com.sales.ops.service.wmOrder.BaseDoService;
import com.sales.ops.service.wmOrder.BaseWMOrderService;
import com.sales.ops.service.wmOrder.OpsDoService;
import com.sales.ops.service.wmOrder.OpsPcoService;
import com.sales.ops.serviceimpl.dispatch.podispatch.domain.PurchaseAcceptContext;
import com.sales.ops.serviceimpl.dispatch.podispatch.domain.PurchaseAcceptContextItem;
import com.sales.ops.serviceimpl.dispatch.podispatch.domain.PurchaseParamsValidator;
import com.sales.ops.serviceimpl.inventory.BaseInventoryService;
import com.sales.ops.serviceimpl.inventory.factory.InventoryFactory;
import com.sales.ops.serviceimpl.inventory.factory.PropertyFactory;
import com.sales.ops.utils.PoNoUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @author C12961
 * @date 2023/3/3 14:30
 */
@Slf4j
@Service
@AllArgsConstructor
public class PurchaseAcceptHandler {

    private final OpsInventoryPropertyService opsInventoryPropertyService;
    private final BasePoService basePoService;
    private final BaseInventoryService baseInventoryService;
    private final BaseDoService baseDoService;
    private final BaseWMOrderService baseWMOrderService;
    private final OpsDoService opsDoService;
    private final OpsPcoService opsPcoService;

    @Transactional(rollbackFor = Exception.class)
    public void acceptForPo(PoToWmDto dto) throws OpsException {
        //log.info("采购接单：{}", PoNoUtil.getFullPoNo(dto.getPurchase()));
        // 数据校验和转换
        PurchaseParamsValidator.accept(dto);
        PurchaseAcceptContext context = new PurchaseAcceptContext(dto);
        // 逐条处理请购单，1.创建生产中库存 2.给库存绑定物流指令 3.添加move库存预占数
        handle(context);
        // 以下是处理采购多订的逻辑 bugid:11810 C12961 2023-08-21
        handExtralPurchaseMove(context);
    }

    private void handle(PurchaseAcceptContext context) throws OpsException {
        for (PurchaseAcceptContextItem item : context.getItems()) {
            // 通过请购单号查询move库存
            List<OpsInventoryMove> moves = baseInventoryService.findInventoryMoveByRequestPurchase(item.getOrderNo(),
                    item.getItemNo(), item.getSplitNo());
            // 如果没有库存，则是正常采购接单逻辑，创建move和关联关系
            if (CollectionUtils.isEmpty(moves)) {
                // 生成在途数据
                createProductInventory(item);
                // 创建关联关系
                createRelation(item);
            }
            // 如果有库存，则修改move
            else {
                // 检测是否为生产在途库存
                PurchaseParamsValidator.moveForProduct(item, moves);
                // 如果move采购单号等于新采购单号,则修改move信息
                boolean samePoNo = StringUtils.equals(PoNoUtil.getFullPoNo(item.getOldProduct()), item.getFullPoNo());
                if (samePoNo) {
                    updateProductWithSupplierAndWarehouse(item);
                }
                // 如果采购单号不相等，则删除原move,并创建新move，绑定新库存
                else {
                    // 删除原move
                    deleteMoveAndRelation(item);
                    // 生成在途数据
                    createProductInventory(item);
                    // 创建关联关系
                    createRelation(item);
                }
            }
        }
    }

    /**
     * 采购单的数量可能大于请购单的数量之和，此情况叫做采购多订
     * 如果采购单的数量 - 已创建的生产中库存数量 = 采购多订数量 > 0，则创建一条无预占的生产库存
     */
    private void handExtralPurchaseMove(PurchaseAcceptContext context) throws OpsException {
        // 删除原采购多订move
        for (PurchaseAcceptContextItem item : context.getItems()) {
            // 拿到当前请购单的move的原采购单号
            OpsInventoryMove oldProduct = item.getOldProduct() == null ? item.getProduct() : item.getOldProduct();
            deleteMorePurchaseMoveByPoNo(oldProduct, item.getCreator());
        }
        // 生成采购多订的move
        if (context.getMorePurchaseQty() > 0) {
            OpsInventoryMove product = InventoryFactory.productInventory(context.getPurchase(), context.getMorePurchaseQty());
            baseInventoryService.createInvMoveReturnId(product, context.getCreator());
        }
        // 校验
        OpsPurchaseorder purchase = context.getPurchase();
        List<OpsInventoryMove> moves = baseInventoryService.findInventoryMoveByPo(purchase.getOrderno(), purchase.getItemno(), purchase.getSplititemno());
        Integer sumQty = moves.stream()
                .filter(move -> StringUtils.equals(move.getSourceType(), SourceTypeEnum.PURCHASE.getType()))
                .map(OpsInventoryMove::getQuantity).reduce(Integer::sum).orElse(0);
        if (sumQty - purchase.getQuantity() != 0) {
            log.info("采购接单数量错误{}!={}：{}\n{}", sumQty, purchase.getQuantity(), JSONUtil.toJsonPrettyStr(moves), JSONUtil.toJsonPrettyStr(context.getPurchase()));
            throw Exceptions.OpsException("接单失败，生成库存总数不等于采购数量");
        }
    }

    // 删除原采购多订的move，通过空请购单号和原采购单号查询 bugid:11810 C12961 2023-08-21
    private void deleteMorePurchaseMoveByPoNo(OpsInventoryMove oldProduct, UserDto creator) throws OpsException {
        List<OpsInventoryMove> moves = baseInventoryService.findInventoryMoveWithEmptyRequestNoByPurchase(
                oldProduct.getAssociateNo(), oldProduct.getAssociateNoItem(), oldProduct.getAssociateNoSplitno());
        // 删除采购多订move
        for (OpsInventoryMove moreMove : moves) {
            baseInventoryService.deleteInventoryMove(moreMove.getInventoryId(), moreMove.getVersion(), creator);
        }
    }

    private void updateProductWithSupplierAndWarehouse(PurchaseAcceptContextItem item) throws OpsException {
        OpsInventoryMove move = item.getOldProduct();
        OpsPurchaseorder purchase = item.getPurchase();
        baseInventoryService.updateInventoryMoveConversionByInventoryId(move.getInventoryId(), purchase.getSupplierid(),
                purchase.getReceivewarehouseid(), move.getVersion());
        item.setProduct(move);
        // 更新在途数据，修改供应商和收获仓
        log.info("采购转订：{},更新供应商或者签收仓{}=》{},{}=》{}", PoNoUtil.getFullRePoNo(item.getOldProduct()), move.getSupplierid(),
                purchase.getSupplierid(), move.getSignWarehouseCode(), purchase.getReceivewarehouseid());
    }

    private void deleteMoveAndRelation(PurchaseAcceptContextItem item) throws OpsException {
        OpsInventoryMove move = item.getOldProduct();
        opsDoService.deleteDoItemInventoryByInventoryId(move.getInventoryId(), InventoryTableTypeEnum.TRANS.getType());
        opsPcoService.deletePcoItemInventoryByInventoryId(move.getInventoryId(), InventoryTableTypeEnum.TRANS.getType());
        baseInventoryService.deleteInventoryMove(move.getInventoryId(), move.getVersion(), item.getCreator());
    }

    // 创建生产中库存
    // 1.查询或创建批属性
    // 2.初始化生产中库存,但预占数为0
    // 3.插入库存，返回库存id
    private void createProductInventory(PurchaseAcceptContextItem item) throws OpsException {
        // 创建生产中库存
        // 1.查询或创建批属性
        OpsInventoryProperty property = PropertyFactory.fromRequestPurchase(item.getRequest());
        long propertyId = opsInventoryPropertyService.findPropertyWithInsertByExample(property, item.getCreator());
        // 2.初始化生产中库存,但预占数为0
        OpsInventoryMove product = InventoryFactory.productInventory(item.getRequest(), item.getPurchase(), propertyId);
        // 3.插入库存，返回库存id
        baseInventoryService.createInvMoveReturnId(product, item.getCreator());
        item.setProduct(product);
    }


    // 通过13位单号，查关联关系，如果有关联关系，则跳过，
    // 如果没有关联关系，则创建关联关系
    public void createRelation(PurchaseAcceptContextItem item) throws OpsException {
        // 绑定物流指令
        // 如果订单类型是销售类【销售订单10开头，一般贸易CM，样品S】,则查询交易出库单和加工单
        if (basePoService.isSalesPurchaseType(item.getRequest().getPurchasetype())) {
            //先查询交易单有没有关联关系
            List<OpsDo> jycks = baseDoService.findAllJYCKByOrder(item.getOrderNo(), item.getItemNo().toString());
            if (CollectionUtils.isNotEmpty(jycks)) {
                OpsDo opsDo = jycks.get(0);
                //判断交易单的拆分方式
                boolean assModel = DoSourceEnum.isAssModel(opsDo.getDoSource());
                // 如果是整型号采购
                if (!assModel) {
                    //筛选num
                    OpsDo jyck = jycks.stream().filter(a -> a.getNum().equals(item.getSplitNo())).findFirst().orElse(null);
                    // 验证有没有其他关联库存
                    if (jyck != null) {
                        // 一级关联关系
                        OpsDoItem doItem = baseDoService.getDoItemByDoId(jyck.getDoId());
                        if (doItem == null) {
                            throw Exceptions.OpsException("没有查询到doItem{}", jyck.getDoId());
                        }
                        List<OpsDoItemInventory> doItemInventoryList = baseDoService.getDoItemInventoryByDoId(jyck.getDoId());
                        // 查询交易出库单有没有关联调拨单
                        // 最好不用客户单号查调拨单，因为以后的需求，调拨单可能和客户单没有关联关系
                        // （客户单之前是调拨，订单还原后采购了，调拨单是不删的入在库，此时调拨单和采购单没有关联关系，采购又接单了）
                        boolean hasRelation = CollectionUtils.isNotEmpty(doItemInventoryList);
                        // bugid12003 C12961 2023-09-06 判断是否有其他（二级）关联关系，如果等待类型为调拨或异常，则有关联关系
                        boolean hasOtherRelation = DoWaitTypeEnum.WaitDB.getType().equals(jyck.getWaitType()) || DoWaitTypeEnum.EXCEPTION.getType().equals(jyck.getWaitType());
                        if (hasOtherRelation) {
                            hasRelation = true;
                            log.info("{}交易单的等待类型：{}", OrderNoInfo.getFromDo(jyck).getFullNo(), jyck.getWaitType());
                        }
                        item.setHasRelation(hasRelation);
                        // 组装关联关系实体
                        item.setJYCK(jyck, doItem, doItemInventoryList);
                        // 如果没有其他关联库存
                        if (!item.hasRelation()) {
                            // 创建关联关系,并增加预约数
                            // 预占数量
                            OpsDoDto jyckDto = item.getJYCK();
                            int useQty = Math.min(jyckDto.getDoItem().getQty(), item.getRequestQty());
                            // 创建关联关系表,但没有数量和库存
                            OpsDoItemInventory doItemInventory = createDoItemInventoryForMove(jyckDto.getOpsDo(), jyckDto.getDoItem());
                            doItemInventory.setInventoryId(item.getProduct().getInventoryId());// 刚创建的生产库存
                            doItemInventory.setUseQty(useQty);
                            // 插入do_item_inventory关联表
                            opsDoService.insertDoItemInventory(doItemInventory);
                            // 添加move预占库存
                            opsDoService.updatePreQtyForDo(jyckDto.getOpsDo(), Collections.singletonList(doItemInventory), item.getCreator(),null);
                            item.setPrepareQuantity(useQty);
                        }
                        // 如果有关联关系，说明客户单已经订单还原或转订，直接跳过
                        else {
                            log.info("该请购单的交易出库单已经绑定了其他库存:{}", JSONUtil.toJsonPrettyStr(item.getJYCK()));
                        }
                    }
                }
                // 如果是拆分型号采购
                else {
                    for (OpsDo jyck : jycks) {
                        List<OpsPco> pcos = baseWMOrderService.getPcos(jyck.getOrderId(), jyck.getOrderItem(), jyck.getNum());
                        if (CollectionUtils.isNotEmpty(pcos)) {
                            OpsPco opsPco = pcos.get(0);
                            OpsPcoItem pcoItem = opsPcoService.getPcoItemByPcoId(opsPco.getPcoId(), item.getSplitNo());
                            if (pcoItem == null) {
                                throw Exceptions.OpsException("没有查询到pcoItem{}-{}", opsPco.getPcoId(), item.getSplitNo());
                            }
                            List<OpsPcoItemInventory> pcoItemInventoryList = opsPcoService.getOpsPcoItemInventoryByPcoId(opsPco.getPcoId(),
                                    item.getSplitNo());
                            boolean hasRelation = CollectionUtils.isNotEmpty(pcoItemInventoryList);
                            // bugid12003 C12961 2023-09-06 判断是否有其他(二级)关联关系，如果等待类型为调拨或异常，则有关联关系
                            boolean hasOtherRelation = DoWaitTypeEnum.WaitDB.getType().equals(pcoItem.getWaitType()) || DoWaitTypeEnum.EXCEPTION.getType().equals(pcoItem.getWaitType());
                            if (hasOtherRelation) {
                                hasRelation = true;
                                log.info("{}交易单的等待类型：{}", OrderNoInfo.getFromDo(jyck).getFullNo(), pcoItem.getWaitType());
                            }
                            //只要有一次为true，则以后都为true
                            boolean lastHasRelation = item.hasRelation() == null ? false : item.hasRelation();
                            item.setHasRelation(hasRelation || lastHasRelation);
                            // 组装关联关系实体
                            item.addPco(opsPco, pcoItem, pcoItemInventoryList);
                        }
                    }
                    // 如果现在没有其他关联库存
                    if (!item.hasRelation()) {
                        // 创建关联关系,并增加预约数
                        List<OpsPcoItemDto> pcoDtoList = item.getPco();
                        //可创建的关联关系数量，类减
                        int qty = item.getRequestQty();
                        //已创建的关联关系数量，累加
                        int prepareQty = 0;
                        for (OpsPcoItemDto pco : pcoDtoList) {
                            if (qty <= 0) {
                                break;
                            }
                            // 预占数量
                            final int useQty = Math.min(pco.getPcoItem().getSubQty(), qty);
                            // 创建关联关系,但没有绑定库存
                            OpsPcoItemInventory pcoItemInventory = createPcoItemInventoryForMove(pco.getOpsPco(), pco.getPcoItem());
                            pcoItemInventory.setInventoryId(item.getProduct().getInventoryId());// 刚创建的生产库存
                            pcoItemInventory.setUseQty(useQty);
                            // 插入pco_item_inventory关联表
                            opsPcoService.insertPcoItemInventory(pcoItemInventory);
                            // 添加move预占库存
                            opsPcoService.updatePreQtyForPco(pco.getOpsPco(), Collections.singletonList(pcoItemInventory),
                                    item.getCreator());
                            qty = qty - useQty;
                            prepareQty = prepareQty + useQty;
                        }
                        item.setPrepareQuantity(prepareQty);
                    }
                    // 如果有关联关系，说明客户单已经订单还原或转订，直接跳过
                    else {
                        log.info("该请购单的交易出库单已经绑定了其他库存:{}", JSONUtil.toJsonPrettyStr(item.getPco()));
                    }
                }
            }
            // 如果交易出库单为空，则说明客单被删除了，正常
            else {
                log.info("该请购单的交易出库单为空：请购单:{}", JSONUtil.toJsonPrettyStr(item.getRequest()));
            }
        }
        // 2.如果订单类型是补库类，查询采购调拨出库单
        else if (basePoService.isSupplyPurchaseType(item.getRequest().getPurchasetype())) {
            OpsDo cgdbck = baseDoService.findCGDBCKByOrder(item.getOrderNo(), item.getItemNo().toString(),item.getSplitNo());
            if (cgdbck != null) {
                // 验证调拨单有没有关联库存
                OpsDoItem doItem = baseDoService.getDoItemByDoId(cgdbck.getDoId());
                List<OpsDoItemInventory> doItemInventoryList = baseDoService.getDoItemInventoryByDoId(cgdbck.getDoId());
                boolean hasRelation = CollectionUtils.isNotEmpty(doItemInventoryList);
                item.setHasRelation(hasRelation);
                // 组装关联关系实体
                item.setCGDBCK(cgdbck, doItem, doItemInventoryList);
                // 如果现在没有其他关联库存
                if (!item.hasRelation()) {
                    // 创建关联关系,并增加预约数
                    createCGDBCKRelation(item);
                }
                // 如果有关联库存，说明客户单已经订单还原或转订，直接跳过
                else {
                    log.info("该补库单的采购调拨出库单已经绑定了其他库存:{}", JSONUtil.toJsonPrettyStr(item.getCGDBCK()));
                }
            }
            // 如果采购调拨出库单为空，则说明客单被删除了，正常
            else {
                log.info("该补库单的采购调拨出库单为空，请购单:{}", JSONUtil.toJsonPrettyStr(item.getRequest()));
            }
        } else {
            log.error("无法识别该采购单类型");
        }
    }


    private void createCGDBCKRelation(PurchaseAcceptContextItem item) throws OpsException {
        OpsDoDto cgdbck = item.getCGDBCK();
        int useQty = Math.min(cgdbck.getDoItem().getQty(), item.getRequestQty());
        // 创建关联关系,但没有绑定库存
        OpsDoItemInventory doItemInventory = createDoItemInventoryForMove(cgdbck.getOpsDo(), cgdbck.getDoItem());
        doItemInventory.setInventoryId(item.getProduct().getInventoryId());
        doItemInventory.setUseQty(useQty);
        // 插入do_item_inventory关联表
        opsDoService.insertDoItemInventory(doItemInventory);
        // 添加move预占库存
        opsDoService.updatePreQtyForDo(cgdbck.getOpsDo(), Collections.singletonList(doItemInventory),
                item.getCreator(),null);
        item.setPrepareQuantity(useQty);
    }

    private OpsDoItemInventory createDoItemInventoryForMove(OpsDo opsDo, OpsDoItem opsDoItem) {
        OpsDoItemInventory doItemInventory = new OpsDoItemInventory();
        doItemInventory.setDoId(opsDo.getDoId());
        doItemInventory.setDoItem(opsDoItem.getDoItem());
        doItemInventory.setInventoryId(null);
        doItemInventory.setInventoryTableType(InventoryTableTypeEnum.TRANS.getType());
        doItemInventory.setUseQty(0);
        doItemInventory.setOutQty(0);
        return doItemInventory;
    }

    private OpsPcoItemInventory createPcoItemInventoryForMove(OpsPco opsPco, OpsPcoItem pcoItem) {
        OpsPcoItemInventory pcoItemInventory = new OpsPcoItemInventory();
        pcoItemInventory.setPcoId(opsPco.getPcoId());
        pcoItemInventory.setPcoItem(pcoItem.getPcoItem());
        pcoItemInventory.setVersion(0L);
        pcoItemInventory.setDelflag(0);
        pcoItemInventory.setCreator(UserDto.ADMIN.getUserName());
        pcoItemInventory.setCreTime(new Date());
        pcoItemInventory.setPcoType(opsPco.getPcoType());// 1拆分2组装
        pcoItemInventory.setInventoryTableType(InventoryTableTypeEnum.TRANS.getType());
        return pcoItemInventory;
    }

}
