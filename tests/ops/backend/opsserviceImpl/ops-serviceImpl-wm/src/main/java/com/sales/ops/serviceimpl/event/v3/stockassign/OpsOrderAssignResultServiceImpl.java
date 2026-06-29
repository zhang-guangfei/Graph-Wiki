package com.sales.ops.serviceimpl.event.v3.stockassign;

import cn.hutool.json.JSONUtil;
import com.sales.ops.common.opsexception.Exceptions;
import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.db.dao.OpsOrderAssignResultMapper;
import com.sales.ops.db.entity.*;
import com.sales.ops.dto.order.ResultForOrderAdjustDto;
import com.sales.ops.enums.*;
import com.sales.ops.service.inventory.OpsInventoryPropertyService;
import com.sales.ops.service.order.BaseOrderAssignResultService;
import com.sales.ops.service.wmOrder.BaseDoService;
import com.sales.ops.serviceimpl.inventory.BaseInventoryService;
import com.sales.ops.serviceimpl.order.result.NewResult;
import com.sales.ops.serviceimpl.order.result.Result;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class OpsOrderAssignResultServiceImpl implements OpsOrderAssignResultService {

    @Autowired
    private OpsOrderAssignResultMapper opsOrderAssignResultMapper;
    @Autowired
    private BaseOrderAssignResultService baseOrderAssignResultService;
    @Autowired
    private BaseInventoryService baseInventoryService;
    @Autowired
    private OpsInventoryPropertyService opsInventoryPropertyService;

    private static final List<String> TRANSIT_STOCK_TYPES = Arrays.asList("T", "P", "T1", "T3", "W", "CG");
    private static final List<String> TRANSIT_STOCK_TYPE_PRIORITY = Arrays.asList("T", "P", "T1", "T3", "W", "CG");
    private static final List<String> NORMAL_STOCK_TYPE_PRIORITY = Arrays.asList("N", "T", "P", "T1", "T3", "W", "CG");
    private static final List<String> GROBLE_STOCK_TYPE_PRIORITY = Arrays.asList("N", "T", "P", "T1", "T3", "W", "CG", "EXCEPTION");


    /**
     * 目标 to 类型：采购。
     */
    private static final String TARGET_PURCHASE = "CG";
    /**
     * 目标 to 类型：在途。
     */
    private static final String TARGET_TRANSIT = "T";
    /**
     * 目标 to 类型：在库。
     */
    private static final String TARGET_NORMAL = "N";

    /**
     * 来源 from 类型：采购。
     */
    private static final String SOURCE_PURCHASE = "CG";
    /**
     * 来源 from 类型：在库。
     */
    private static final String SOURCE_NORMAL = "N";
    /**
     * 来源 from 类型：异常。
     */
    private static final String SOURCE_EXCEPTION = "EXCEPTION";
    /**
     * 来源 from 类型：在途。
     */
    private static final String SOURCE_TRANSIT = "T";
    @Autowired
    private BaseDoService baseDoService;

    //订单分配事件，插入订单分配结果
    @Override
    public void insertForOrderAllot(List<OpsOrderAssignResult> list, Date allotStartTime, Date allotEndTime) throws OpsException {
        baseOrderAssignResultService.insertOrderAssignResult(list, allotStartTime, allotEndTime);
    }

    //采购发单事件，关联单号更新
    @Override
    public void updateForPoSend(String orderId, Integer orderItem, OpsPurchaseorder po) throws OpsException {
        baseOrderAssignResultService.updateByPurchaseOrder(orderId, orderItem, po.getModelno(), po.getOrderno(), po.getItemno(), po.getSplititemno(), po.getSupplierid());
    }

    @Override
    public void updateForPoUpdateSupplier(String orderId, Integer orderItem, OpsPurchaseorder po) throws OpsException {
        baseOrderAssignResultService.updateByPurchaseOrderUpdateSupplier(orderId, orderItem, po.getModelno(), po.getOrderno(), po.getItemno(), po.getSplititemno(), po.getSupplierid());
    }

    /**
     * 新转订方法。
     *
     * <p>结构按 {@code orderAssignResultAdjust-flow.mmd} 展开：</p>
     * <pre>
     * updateResultForOrderAdjustNew
     *   -> 解析 adjustType 为 target(to) + source(from)
     *   -> to=CG: handleToPurchase
     *   -> to=T : handleToTransit
     *   -> to=N : handleToNormal
     *   -> 统一刷新 rcvdetail / 接单库存信息
     * </pre>
     */
    @Override
    public void updateResultForOrderAdjustNew(String orderId, String orderItem, ResultForOrderAdjustDto resultDTO) throws OpsException {
        // 第一步：只解析方向，不做查询；后续所有处理都围绕 source(from) 和 target(to) 展开。
        AdjustDirection direction = resolveAdjustDirection(resultDTO.getAdjustType());
        // 记录基础范围内转订前总量，用于后置一致性校验。
        OpsOrderAssignResultExample validateExample = buildBaseAssignResultExample(resultDTO);
        Integer sumQty = sumQuantity(opsOrderAssignResultMapper.selectByExample(validateExample));
        // 第二步：按目标 to=CG/T/N 分发，让入口代码和流程图的三条主线保持一致。
        if (TARGET_PURCHASE.equals(direction.target)) {
            // to=CG：目标是采购，内部再区分 from=N/EX/T。
            handleToPurchase(resultDTO, direction);
        } else if (TARGET_TRANSIT.equals(direction.target)) {
            // to=T：目标是在途，需要先构造目标在途库存上下文。
            handleToTransit(resultDTO, direction);
        } else if (TARGET_NORMAL.equals(direction.target)) {
            // to=N：目标是在库，需要先构造目标在库库存上下文。
            handleToNormal(resultDTO, direction);
        } else {
            // 理论上 resolveAdjustDirection 已经兜底，这里保留防御式异常。
            throw Exceptions.OpsException("不支持的转订目标类型:" + resultDTO.getAdjustType());
        }
        // 第三步：校验修改前后数量是否一致
        validateQuantityConsistency(validateExample, sumQty);
        // 第四步：所有分支完成后统一刷新接单库存信息，避免各分支重复刷新。
        refreshRcvStockInfo(orderId, orderItem);
    }

    /**
     * adjustType -> to/from。
     *
     * <p>入口只负责解析方向，不在入口里掺入查询细节；后续各功能块再按 from 查询来源 result。</p>
     */
    private AdjustDirection resolveAdjustDirection(String adjustType) throws OpsException {
        if (ResultForOrderAdjustDto.PURCHASE_TO_NORMAL.equals(adjustType)) {
            // CG -> N：采购转在库。
            return new AdjustDirection(SOURCE_PURCHASE, TARGET_NORMAL, "采购转在库");
        } else if (ResultForOrderAdjustDto.NORMAL_TO_PURCHASE.equals(adjustType)) {
            // N -> CG：在库转采购，当前 V3 按 from=N 查询来源 result。
            return new AdjustDirection(SOURCE_NORMAL, TARGET_PURCHASE, "在库转采购");
        } else if (ResultForOrderAdjustDto.EX_TO_NORMAL.equals(adjustType)) {
            // EX -> N：异常转在库。
            return new AdjustDirection(SOURCE_EXCEPTION, TARGET_NORMAL, "异常转在库");
        } else if (ResultForOrderAdjustDto.EX_TO_PURCHASE.equals(adjustType)) {
            // EX -> CG：异常转采购，当前 V3 按 from=EX 查询来源 result。
            return new AdjustDirection(SOURCE_EXCEPTION, TARGET_PURCHASE, "异常转采购");
        } else if (ResultForOrderAdjustDto.TRANSIT_TO_PURCHASE.equals(adjustType)) {
            // T -> CG：在途转采购，当前 V3 按通用 from=T 查询来源 result。
            return new AdjustDirection(SOURCE_TRANSIT, TARGET_PURCHASE, "在途转采购");
        } else if (ResultForOrderAdjustDto.PURCHASE_TO_TRANSIT.equals(adjustType)) {
            // CG -> T：采购转在途。
            return new AdjustDirection(SOURCE_PURCHASE, TARGET_TRANSIT, "采购转在途");
        } else if (ResultForOrderAdjustDto.EX_TO_TRANSIT.equals(adjustType)) {
            // EX -> T：异常转在途。
            return new AdjustDirection(SOURCE_EXCEPTION, TARGET_TRANSIT, "异常转在途");
        } else if (ResultForOrderAdjustDto.TRANSIT_TO_TRANSIT.equals(adjustType)) {
            // T -> T：在途转在途，来源和目标都是 move，但 old/new move 不同。
            return new AdjustDirection(SOURCE_TRANSIT, TARGET_TRANSIT, "在途转在途");
        } else if (ResultForOrderAdjustDto.TRANSIT_TO_NORMAL.equals(adjustType)) {
            // T -> N：在途转在库；当前 V3 实现按通用 from=T 查询来源，再写入目标在库字段。
            return new AdjustDirection(SOURCE_TRANSIT, TARGET_NORMAL, "在途转在库");
        }
        throw Exceptions.OpsException("不支持的转订类型:" + adjustType);
    }

    private void refreshRcvStockInfo(String orderId, String orderItem) throws OpsException {
        // 根据订单重新查一次分配结果，拿到转订后的最终状态。
        List<OpsOrderAssignResult> list = baseOrderAssignResultService.findByOrder(orderId, Integer.valueOf(orderItem));
        // 用最终 result 刷新 rcvdetail / 接单库存信息。
        baseOrderAssignResultService.updateRcvStockInfoByResult(list);
    }

    // ----------------------------------------------------------------------
    // 1. to=CG：转采购主线
    // ----------------------------------------------------------------------

    /**
     * 转采购主线。
     *
     * <pre>
     * to=CG
     *   -> from=N  : 在库转采购，按 stock_type=N 查询来源 result
     *   -> from=EX : 异常转采购，按 stock_type=EXCEPTION 查询来源 result
     *   -> from=T  : 在途转采购，按当前 V3 通用 from=T 规则查询 result
     * </pre>
     */
    private void handleToPurchase(ResultForOrderAdjustDto resultDTO, AdjustDirection direction) throws OpsException {
        // 1.构建目标采购上下文：目标 stock_type=CG，并清空库存定位字段。
        TargetContext targetContext = TargetContext.toPurchase(direction.remark);
        // 2.再按 from=EX/T/N 查询来源 result 候选；当前不支持 CG -> CG。
        List<OpsOrderAssignResult> results;
        if (SOURCE_EXCEPTION.equals(direction.source)) {
            results = findExceptionSourceResults(resultDTO, direction.remark);
        }
        //else if (SOURCE_PURCHASE.equals(direction.source)) {
        // 采购不能转采购，无操作
        //}
        // from=T：在途转采购是新增独立路径，必须按来源在途 move 找 result。
        else if (SOURCE_TRANSIT.equals(direction.source)) {
            // from=T：按 oldMoveId + 在途 stock_type 集合 + 关联单号定位来源 result。
            results = findTransitSourceResults(resultDTO, direction.remark);
        }
        // from=N：在库转采购，按在库 stock_type=N 查询来源 result。
        else if (SOURCE_NORMAL.equals(direction.source)) {
            // from=N：在库转采购，按 stock_type=N 查询来源 result。
            results = findNormalSourceResults(resultDTO, direction.remark);
        } else {
            throw Exceptions.OpsException("不支持的转采购来源类型:" + resultDTO.getAdjustType());
        }
        // 3.按数量筛选候选，并把目标采购字段写入 result。
        processResultToTarget(results, resultDTO.getQty(), direction.source, targetContext, direction.remark);
    }


    /**
     * source 对应的 stock_type 选择优先级：
     * <ul>
     *     <li>from=EX 优先消费异常 result。</li>
     *     <li>from=CG 优先消费采购 result。</li>
     *     <li>from=T 优先消费在途相关 result。</li>
     *     <li>from=N 优先消费在库 result。</li>
     * </ul>
     */
    private Comparator<OpsOrderAssignResult> buildStockTypeComparator(String sourceCode) {
        // 先拿到 stock_type 的业务优先级列表。
        List<String> stockTypeSortList = getStockTypePriority(sourceCode);
        return Comparator.comparing(OpsOrderAssignResult::getStockType, Comparator.comparingInt(stockType -> {
            // stock_type 在列表里的位置越靠前，优先级越高。
            int index = stockTypeSortList.indexOf(stockType);
            // 未出现在优先级列表里的类型排到最后，保持兼容兜底。
            return index < 0 ? Integer.MAX_VALUE : index;
        }));
    }

    private List<String> getStockTypePriority(String source) {
        if (SOURCE_EXCEPTION.equals(source)) {
            return Arrays.asList("EXCEPTION");
        } else if (SOURCE_PURCHASE.equals(source)) {
            return Arrays.asList("CG");
        } else if (SOURCE_TRANSIT.equals(source)) {
            return TRANSIT_STOCK_TYPE_PRIORITY;
        } else if (SOURCE_NORMAL.equals(source)) {
            return NORMAL_STOCK_TYPE_PRIORITY;
        }
        return GROBLE_STOCK_TYPE_PRIORITY;
    }


    /**
     * 转在途主线。
     *
     * <pre>
     * to=T
     *   -> 先查目标在途 targetMove，并构建目标字段上下文
     *   -> from=CG : 按采购 result 查询
     *   -> from=EX : 按异常 result 查询
     *   -> from=T  : oldMoveId 必填，按来源在途查询
     * </pre>
     */
    private void handleToTransit(ResultForOrderAdjustDto resultDTO, AdjustDirection direction) throws OpsException {
        // 1.先查目标 move，并把目标在途需要写回 result 的字段封装起来。
        TargetContext targetContext = buildTransitTargetContext(resultDTO, direction.remark);
        // 2.再按 from=EX/CG/T 查询来源 result 候选。
        List<OpsOrderAssignResult> results;
        if (SOURCE_EXCEPTION.equals(direction.source)) {
            // from=EX：异常来源；只按 EXCEPTION stock_type 查询。
            results = findExceptionSourceResults(resultDTO, direction.remark);
        } else if (SOURCE_PURCHASE.equals(direction.source)) {
            // from=CG：采购来源；当前 V3 只按 CG stock_type 查询采购 result。
            results = findPurchaseSourceResults(resultDTO, direction.remark);
        } else if (SOURCE_TRANSIT.equals(direction.source)) {
            // from=T：在途来源；oldMoveId 有值时按来源 move 关联单过滤，无值时只按在途 stock_type 集合查询。
            results = findTransitSourceResults(resultDTO, direction.remark);
        } else {
            throw Exceptions.OpsException("不支持的转在途来源类型:" + resultDTO.getAdjustType());
        }
        // 3.按数量筛选候选，并把目标在途字段写入 result。
        processResultToTarget(results, resultDTO.getQty(), direction.source, targetContext, direction.remark);

    }

    private TargetContext buildTransitTargetContext(ResultForOrderAdjustDto resultDTO, String remark) throws OpsException {
        // newInvId 表示目标在途库存 id；转在途没有目标库存就无法落字段。
        Long newInvId = resultDTO.getNewInvId();
        if (newInvId == null) {
            throw Exceptions.OpsException("转在途操作目标库存不能为空");
        }
        // 如果前端传了 tableType，则必须明确是 TRANS，避免把在库 id 当成在途 id 用。
        if (resultDTO.getTableType() != null && !InventoryTableTypeEnum.TRANS.getType().equals(resultDTO.getTableType())) {
            throw Exceptions.OpsException("转在途操作目标库存类型不正确:" + resultDTO.getTableType());
        }
        // 查询目标在途库存，后面所有 stockCode/associate/supplier 都从这里取。
        OpsInventoryMove targetMove = baseInventoryService.getInventoryMoveById(newInvId);
        if (targetMove == null) {
            throw Exceptions.OpsException("转在途操作未查询到目标在途库存:" + newInvId);
        }
        // 目标库存类别需要从库存属性表取，允许属性为空时 inventoryTypeCode 为空。
        OpsInventoryProperty targetProperty = opsInventoryPropertyService.findById(targetMove.getInventoryPropertyId());
        // 封装目标字段，后续 update/split 都统一使用这个上下文落值。
        return TargetContext.toTransit(targetMove, targetProperty, remark);
    }

    private TargetContext buildNormalTargetContext(ResultForOrderAdjustDto resultDTO, String remark) throws OpsException {
        // newInvId 表示目标在库库存 id；转在库没有目标库存就无法落字段。
        Long newInvId = resultDTO.getNewInvId();
        if (newInvId == null) {
            throw Exceptions.OpsException("转在库操作目标库存不能为空");
        }
        if (resultDTO.getTableType() != null && !InventoryTableTypeEnum.NORMAL.getType().equals(resultDTO.getTableType())) {
            throw Exceptions.OpsException("转在库操作目标库存类型不正确:" + resultDTO.getTableType());
        }
        // 查询目标在库库存，后面所有 stockCode/supplierId/risk 都从目标上下文取。
        OpsInventory newNormal = baseInventoryService.getInventoryById(newInvId);
        if (newNormal == null) {
            throw Exceptions.OpsException("转在库操作未查询到目标在途库存:" + newInvId);
        }
        // 目标库存类型来自库存属性；riskFlag 由入参透传到 result.inventoryRisk。
        OpsInventoryProperty property = opsInventoryPropertyService.findById(newNormal.getInventoryPropertyId());
        return TargetContext.toNormal(newNormal, property, remark, resultDTO.getRiskFlag());
    }

    // ----------------------------------------------------------------------
    // 3. to=N：转在库主线
    // ----------------------------------------------------------------------

    /**
     * 转在库主线。
     *
     * <pre>
     * to=N
     *   -> 先查目标在库 newNormal，并构建目标在库字段
     *   -> from=CG : 采购转在库，按 CG 来源查询 result
     *   -> from=EX : 异常转在库，按 EXCEPTION 来源查询
     *   -> from=T  : 在途转在库，按当前 V3 通用 from=T 规则查询 result
     * </pre>
     */
    private void handleToNormal(ResultForOrderAdjustDto resultDTO, AdjustDirection direction) throws OpsException {
        // 先构造目标在库上下文：目标仓、目标库存类型、风险标记。
        TargetContext targetContext = buildNormalTargetContext(resultDTO, direction.remark);
        List<OpsOrderAssignResult> results;
        if (SOURCE_EXCEPTION.equals(direction.source)) {
            // from=EX：异常来源；只按 EXCEPTION stock_type 查询。
            results = findExceptionSourceResults(resultDTO, direction.remark);
        } else if (SOURCE_PURCHASE.equals(direction.source)) {
            // from=CG：采购来源；当前 V3 只按 CG stock_type 查询采购 result。
            results = findPurchaseSourceResults(resultDTO, direction.remark);
        } else if (SOURCE_TRANSIT.equals(direction.source)) {
            // from=T：在途来源；oldMoveId 有值时按来源 move 关联单过滤，无值时只按在途 stock_type 集合查询。
            results = findTransitSourceResults(resultDTO, direction.remark);
        } else {
            throw Exceptions.OpsException("不支持的转在库来源类型:" + resultDTO.getAdjustType());
        }
        // 3.按数量筛选候选，并把目标在库字段写入 result。
        processResultToTarget(results, resultDTO.getQty(), direction.source, targetContext, direction.remark);
    }


    /**
     * 所有来源查询共同基础条件：delflag + order_no + order_item + modelno。
     */
    private OpsOrderAssignResultExample buildBaseAssignResultExample(ResultForOrderAdjustDto resultDTO) {
        // MyBatis Example：所有 from 查询都先从这组基础条件开始。
        OpsOrderAssignResultExample ex = new OpsOrderAssignResultExample();
        OpsOrderAssignResultExample.Criteria criteria = ex.createCriteria();
        // delflag=0：只查有效记录；order/model 条件限定到当前客单行和型号。
        criteria.andDelflagEqualTo(0).andOrderNoEqualTo(resultDTO.getOrderId()).andOrderItemEqualTo(Integer.valueOf(resultDTO.getOrderItem())).andModelnoEqualTo(resultDTO.getModelno());
        return ex;
    }

    /**
     * from=EX：异常来源，只追加 stock_type=EXCEPTION。
     */
    private List<OpsOrderAssignResult> findExceptionSourceResults(ResultForOrderAdjustDto resultDTO, String remark) throws OpsException {
        return findSourceResultsByStockType(resultDTO, Collections.singletonList(SOURCE_EXCEPTION), remark);
    }

    /**
     * from=N/CG/EX 的基础 stock_type 查询。
     */
    private List<OpsOrderAssignResult> findSourceResultsByStockType(ResultForOrderAdjustDto resultDTO, List<String> stockType, String remark) throws OpsException {
        // 先构建订单/行/型号基础条件。
        OpsOrderAssignResultExample ex = buildBaseAssignResultExample(resultDTO);
        // 再追加单一来源 stock_type 条件。
        ex.getOredCriteria().get(0).andStockTypeIn(stockType);
        List<OpsOrderAssignResult> results = opsOrderAssignResultMapper.selectByExample(ex);
        if (CollectionUtils.isEmpty(results)) {
            throw Exceptions.OpsException(remark + "，未查询到匹配的订单分配记录");
        }
        return results;
    }

    /**
     * from=CG：采购来源。
     *
     * <p>按 stock_type=CG 找候选
     */
    private List<OpsOrderAssignResult> findPurchaseSourceResults(ResultForOrderAdjustDto resultDTO, String remark) throws OpsException {
        // 按 stock_type=CG 找采购来源候选。
        return findSourceResultsByStockType(resultDTO, Collections.singletonList(OrderStockTypeEnum.CG.getType()), remark);
    }

    /**
     * from=T：在途来源。
     *
     * <p>当前 V3 允许 oldMoveId 为空：为空时只按在途 stock_type 集合查询；
     * 有值时查询 oldMove，并在关联单完整时追加关联单条件。</p>
     */
    private List<OpsOrderAssignResult> findTransitSourceResults(ResultForOrderAdjustDto resultDTO, String remark) throws OpsException {
        if (resultDTO.getOldMoveId() == null) {
            // 兼容可不传 oldMoveId 的场景：只按在途 stock_type 集合查。
            return findTransitSourceResultsWithoutMove(resultDTO, remark);
        }
        // 有 oldMoveId 时先查来源 move，再用 move 上的关联单收窄候选。
        OpsInventoryMove oldMove = baseInventoryService.getInventoryMoveById(resultDTO.getOldMoveId());
        if (oldMove == null) {
            throw Exceptions.OpsException(remark + "，未查询到来源在途库存:" + resultDTO.getOldMoveId());
        }
        return findTransitSourceResultsByMove(resultDTO, oldMove, remark);
    }

    private List<OpsOrderAssignResult> findTransitSourceResultsWithoutMove(ResultForOrderAdjustDto resultDTO, String remark) throws OpsException {
        // 无 oldMove 时只能用基础条件 + 在途 stock_type 集合查询。
        OpsOrderAssignResultExample ex = buildBaseAssignResultExample(resultDTO);
        // 再限制到在途相关 stock_type：CG
        ex.getOredCriteria().get(0).andStockTypeEqualTo(OrderStockTypeEnum.CG.getType());
        List<OpsOrderAssignResult> results = opsOrderAssignResultMapper.selectByExample(ex);
        if (CollectionUtils.isEmpty(results)) {
            throw Exceptions.OpsException(remark + "，未查询到匹配的在途订单分配记录");
        }
        return results;
    }

    private List<OpsOrderAssignResult> findTransitSourceResultsByMove(ResultForOrderAdjustDto resultDTO, OpsInventoryMove oldMove, String remark) throws OpsException {
        // 先限制到当前订单/行/型号。
        OpsOrderAssignResultExample ex = buildBaseAssignResultExample(resultDTO);
        OpsOrderAssignResultExample.Criteria criteria = ex.getOredCriteria().get(0);

        // oldMove 有完整关联单时，追加关联单过滤，避免命中其他在途来源。
        if (hasCompleteAssociate(oldMove)) {
            // 再限制到在途相关 stock_type：CG/T/P/T1/T3/W。
            criteria.andStockTypeIn(TRANSIT_STOCK_TYPE_PRIORITY);
            // 只有关联单完整时才追加条件，避免 null 条件把候选误过滤掉。
            criteria.andAssociateNoEqualTo(oldMove.getAssociateNo())
                    .andAssociateNoItemEqualTo(oldMove.getAssociateNoItem())
                    .andAssociateNoSplitNoEqualTo(oldMove.getAssociateNoSplitno());
        }else if(isDBMove(oldMove)){
            //原出库区分可能是调拨在途，也可能是调拨未发货
            List<String> T3AndN = Arrays.asList(OrderStockTypeEnum.ZK.getType(), InventoryStatusEnum.DBTRANS.getCode());
            criteria.andStockTypeIn(T3AndN);
            //如果是调拨在途，则查调拨出库仓，并作为出库区分仓库
            List<OpsDo> dbckList = baseDoService.findAllTypeDBCKListByOrder(oldMove.getOrderno(), String.valueOf(oldMove.getItemno()), oldMove.getSplititemno(), DoSourceEnum.ALL);
            if(CollectionUtils.isEmpty(dbckList)){
                dbckList = baseDoService.findAllTypeDBCKListByOrder(oldMove.getOrderno(), String.valueOf(oldMove.getItemno()), oldMove.getSplititemno(), DoSourceEnum.ASSModelNo);
            }
            if(CollectionUtils.isNotEmpty(dbckList)){
                OpsDo dbck = dbckList.get(0);
                criteria.andStockCodeEqualTo(dbck.getWarehouseCode());
            }


        }
        // 执行来源在途查询。
        List<OpsOrderAssignResult> results = opsOrderAssignResultMapper.selectByExample(ex);
        if (CollectionUtils.isEmpty(results)) {
            throw Exceptions.OpsException(remark + "，未查询到匹配的在途订单分配记录");
        }
        return results;
    }

    private boolean isDBMove(OpsInventoryMove move) {
        return move != null
                && Objects.equals(move.getSourceType(), SourceTypeEnum.DB.getType())
                ;
    }

    /**
     * from=N：在库来源。
     *
     * <p>当前主要用于在库转采购：在基础订单/行/型号条件上追加 stock_type=N。</p>
     */
    private List<OpsOrderAssignResult> findNormalSourceResults(ResultForOrderAdjustDto resultDTO, String remark) throws OpsException {
        return findSourceResultsByStockType(resultDTO, NORMAL_STOCK_TYPE_PRIORITY, remark);
    }
    // ----------------------------------------------------------------------
    // 5. 候选筛选器：从 result 候选中选择更新/拆分对象
    // ----------------------------------------------------------------------

    /**
     * 通用数量筛选器：先精确数量，再按 source 对应 stock_type 优先级选择可拆分候选。
     *
     * <pre>
     * quantity == qty -> 整体更新
     * quantity  > qty -> 原记录扣减 + 新增目标记录
     * quantity  < qty -> 报错
     * </pre>
     */
    private void processResultToTarget(List<OpsOrderAssignResult> results, int qty, String sourceCode, TargetContext targetContext, String remark) throws OpsException {
        if (CollectionUtils.isEmpty(results)) {
            throw Exceptions.OpsException(remark + "，未查询到匹配的订单分配记录");
        }
        //确认排序顺序
        Comparator<OpsOrderAssignResult> stockTypeComparator = buildStockTypeComparator(sourceCode);
        // 第一层筛选：优先找数量正好等于本次转订数量的 result。
        List<OpsOrderAssignResult> exactList = results.stream().filter(item -> item.getQuantity() - qty == 0).sorted(stockTypeComparator).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(exactList)) {
            // 精确命中时直接整条更新为目标状态，不拆分。
            updateToTarget(exactList.get(0), targetContext);
            return;
        }
        // 第二层筛选：找数量大于本次转订数量的 result，并按 source 对应 stock_type 优先级选择一条拆分。
        List<OpsOrderAssignResult> greaterList = results.stream().filter(item -> item.getQuantity() - qty > 0).sorted(stockTypeComparator).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(greaterList)) {
            // 可拆分时，原 result 扣减，新 result 写入目标上下文。
            splitToTarget(greaterList.get(0), qty, targetContext);
            return;
        }
        // 没有候选数量足够时，取最大数量用于错误提示。
        results.sort(Comparator.comparing(OpsOrderAssignResult::getQuantity).reversed());
        throw Exceptions.OpsException(remark + "，要转订的数量大于订单分配表中的最大数量" + results.get(0).getQuantity() + "==>" + qty);
    }


    // ----------------------------------------------------------------------
    // 6. 目标字段落值：to=CG / to=T / to=N 统一上下文
    // ----------------------------------------------------------------------

    private void updateToTarget(OpsOrderAssignResult update, TargetContext targetContext) {
        // 将目标状态统一落到当前 result。
        applyTargetContext(update, targetContext);
        // 标记更新时间和操作来源。
        update.setUpdateTime(new Date());
        update.setUpdateUser(targetContext.remark);
        // 整条更新：原主键记录直接变成目标状态。
        opsOrderAssignResultMapper.updateByPrimaryKey(update);
    }

    private void splitToTarget(OpsOrderAssignResult result, int qty, TargetContext targetContext) {
        // 原 result 扣减本次转订数量。
        result.setQuantity(result.getQuantity() - qty);
        result.setUpdateTime(new Date());
        result.setUpdateUser(targetContext.remark + ",扣减数量");
        opsOrderAssignResultMapper.updateByPrimaryKey(result);

        // 新建一条 result 承接被拆出来的 qty。
        OpsOrderAssignResult newResult = new OpsOrderAssignResult();
        // 订单维度字段从原 result 继承，保证仍属于同一客单行。
        newResult.setOrderNo(result.getOrderNo());
        newResult.setOrderItem(result.getOrderItem());
        newResult.setSeqno(result.getSeqno());
        newResult.setModelno(result.getModelno());
        // 新 result 的数量就是本次转订数量。
        newResult.setQuantity(qty);
        newResult.setDelflag(0);
        newResult.setSourceType(2);
        newResult.setCreateTime(new Date());
        newResult.setCreateUser(targetContext.remark + ",拆分库存数量");
        newResult.setUpdateTime(new Date());
        newResult.setUpdateUser(targetContext.remark + ",拆分库存数量");
        // 新 result 写入目标状态：to=CG、to=T 或 to=N。
        applyTargetContext(newResult, targetContext);
        opsOrderAssignResultMapper.insertSelective(newResult);
    }

    private void applyTargetContext(OpsOrderAssignResult result, TargetContext targetContext) {
        // stock_type 决定 result 最终落在采购/在途等哪类库存池。
        result.setStockType(targetContext.stockType);
        // stock_code 对在途/在库有意义；转采购时上下文中该字段为空。
        result.setStockCode(targetContext.stockCode);
        // 库存类型跟随目标库存属性。
        result.setInventoryTypeCode(targetContext.inventoryTypeCode);
        // 关联单字段用于在途来源/目标的 PO、调拨等关联关系。
        result.setAssociateNo(targetContext.associateNo);
        result.setAssociateNoItem(targetContext.associateNoItem);
        result.setAssociateNoSplitNo(targetContext.associateNoSplitNo);
        // supplierId 按目标上下文写入：在途取目标 move 供应商，在库取目标仓，采购为空。
        result.setSupplierid(targetContext.supplierId);
        // 风险标记按目标上下文写入：to=CG/to=T 为 false，to=N 取 resultDTO.riskFlag。
        result.setInventoryRisk(targetContext.inventoryRisk);
    }

    private void validateQuantityConsistency(OpsOrderAssignResultExample ex, Integer expectedSumQty) throws OpsException {
        // 用同一个查询范围重新查转订后的 result。
        List<OpsOrderAssignResult> results = opsOrderAssignResultMapper.selectByExample(ex);
        // 汇总更新/拆分后的总数量。
        Integer afterSumQty = sumQuantity(results);
        if (!expectedSumQty.equals(afterSumQty)) {
            // 总量不一致说明拆分/更新影响了数量平衡，直接抛错阻断。
            throw Exceptions.OpsException("转订前后总数量不一致:" + JSONUtil.toJsonStr(results));
        }
    }

    private Integer sumQuantity(List<OpsOrderAssignResult> results) {
        return results.stream().map(OpsOrderAssignResult::getQuantity).reduce(Integer::sum).orElse(0);
    }

    private boolean hasCompleteAssociate(OpsInventoryMove move) {
        // 关联单三个字段必须同时存在，才允许作为查询过滤条件。
        return move != null
                && Objects.equals(move.getSourceType(),  SourceTypeEnum.PURCHASE.getType())
                && move.getAssociateNo() != null && move.getAssociateNoItem() != null && move.getAssociateNoSplitno() != null;
    }

    private static class AdjustDirection {
        private final String source;
        private final String target;
        private final String remark;

        private AdjustDirection(String source, String target, String remark) {
            this.source = source;
            this.target = target;
            this.remark = remark;
        }
    }


    private static class TargetContext {
        private String stockType;
        private String stockCode;
        private String inventoryTypeCode;
        private String associateNo;
        private Integer associateNoItem;
        private Integer associateNoSplitNo;
        private String supplierId;
        private Boolean inventoryRisk;
        private String remark;

        private static TargetContext toPurchase(String remark) {
            TargetContext context = new TargetContext();
            // to=CG 只需要 stock_type=CG，其余库存定位字段保持为空。
            context.stockType = OrderStockTypeEnum.CG.getType();
            // 转采购不继承库存风险，固定 false。
            context.inventoryRisk = false;
            context.remark = remark;
            return context;
        }

        private static TargetContext toTransit(OpsInventoryMove targetMove, OpsInventoryProperty targetProperty, String remark) {
            TargetContext context = new TargetContext();
            // to=T 的 stock_type 使用目标 move 的 inventoryStatus。
            context.stockType = targetMove.getInventoryStatus();
            // 目标仓库、关联单、供应商都跟随目标在途 move。
            context.stockCode = targetMove.getWarehouseCode();
            // 库存类型来自目标 move 绑定的库存属性。
            context.inventoryTypeCode = targetProperty == null ? null : targetProperty.getInventoryTypeCode();
            context.associateNo = targetMove.getAssociateNo();
            context.associateNoItem = targetMove.getAssociateNoItem();
            context.associateNoSplitNo = targetMove.getAssociateNoSplitno();
            context.supplierId = targetMove.getSupplierid();
            // 转在途不继承库存风险，固定 false。
            context.inventoryRisk = false;
            context.remark = remark;
            return context;
        }

        private static TargetContext toNormal(OpsInventory targetNormal, OpsInventoryProperty targetProperty, String remark, Boolean inventoryRisk) {
            TargetContext context = new TargetContext();
            // to=N 的 stock_type 使用目标在库库存的 inventoryStatus，通常为 N。
            context.stockType = OrderStockTypeEnum.ZK.getType();
            // 目标仓库跟随目标在库库存；在库目标不写关联单字段。
            context.stockCode = targetNormal.getWarehouseCode();
            // 库存类型来自目标 normal 绑定的库存属性。
            context.inventoryTypeCode = targetProperty == null ? null : targetProperty.getInventoryTypeCode();
            context.supplierId = targetNormal.getWarehouseCode();
            // 转在库风险标记由入参 riskFlag 透传。
            context.inventoryRisk = inventoryRisk;
            context.remark = remark;
            return context;
        }
    }

    //客单转订事件
    @Override
    public void updateResultForOrderAdjust(String orderId, String orderItem, String modelno, int qty, Long oldMoveId, Long newInventoryId, InventoryTableTypeEnum tableType) throws OpsException {
        Result result = null;
        if (oldMoveId == null) {
            result = Result.create(orderId, orderItem, modelno, new OpsInventoryMove(), qty);
        } else {
            OpsInventoryMove oldMove = baseInventoryService.getInventoryMoveById(oldMoveId);
            result = Result.create(orderId, orderItem, modelno, oldMove, qty);
        }
        NewResult newResult = null;
        if (tableType == InventoryTableTypeEnum.NORMAL) {
            OpsInventory newNormal = baseInventoryService.getInventoryById(newInventoryId);
            OpsInventoryProperty property = opsInventoryPropertyService.findById(newNormal.getInventoryPropertyId());
            newResult = NewResult.create(orderId, orderItem, qty, newNormal, property.getInventoryTypeCode());
        } else {
            if (newInventoryId == null) {
                newResult = NewResult.create(orderId, orderItem, qty, new OpsInventoryMove(), null);
            } else {
                OpsInventoryMove newMove = baseInventoryService.getInventoryMoveById(newInventoryId);
                OpsInventoryProperty property = opsInventoryPropertyService.findById(newMove.getInventoryPropertyId());
                newResult = NewResult.create(orderId, orderItem, qty, newMove, property.getInventoryTypeCode());
            }
        }
        baseOrderAssignResultService.updateResultForOrderAdjust(result, newResult);
    }

    //采购删单事件，客单转异常
    @Override
    public void updateForPoCancelToException(String orderId, Integer orderItem, String modelno, String poNo, Integer poItem, Integer poSplitNo) throws OpsException {
        baseOrderAssignResultService.updateToException(orderId, orderItem, modelno, poNo, poItem, poSplitNo);
    }

    //采购删单事件，客单重新处理
    @Override
    public void updateForPoCancelToReAllot(String orderId, Integer orderItem) throws OpsException {
        baseOrderAssignResultService.deleteResultOrder(orderId, orderItem);
    }

    @Override
    public void updateForOrderForceFinished(String orderId, Integer orderItem) throws OpsException {
        baseOrderAssignResultService.updateResultForForceFinish(orderId, orderItem);
    }


}
