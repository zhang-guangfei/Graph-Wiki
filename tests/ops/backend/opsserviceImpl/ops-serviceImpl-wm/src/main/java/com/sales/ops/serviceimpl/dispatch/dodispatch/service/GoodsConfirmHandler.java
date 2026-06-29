package com.sales.ops.serviceimpl.dispatch.dodispatch.service;

import com.sales.ops.common.opsexception.Exceptions;
import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.db.entity.*;
import com.sales.ops.dto.flux.RoConfirmItem;
import com.sales.ops.dto.flux.RoSignConfirmDto;
import com.sales.ops.dto.order.OpsDoAndItemDto;
import com.sales.ops.dto.order.OpsSendPcoAndDoMidIDDto;
import com.sales.ops.dto.util.CommonResult;
import com.sales.ops.enums.*;
import com.sales.ops.feign.OpsCallWmsFeignApi;
import com.sales.ops.service.ba.BaseWarehouseService;
import com.sales.ops.service.log.InvoiceEventLogService;
import com.sales.ops.service.log.OpsRoBarcodeService;
import com.sales.ops.service.wmOrder.*;
import com.sales.ops.serviceimpl.dispatch.dodispatch.domain.goodsconfirm.GoodsConfirmContext;
import com.sales.ops.serviceimpl.dispatch.dodispatch.domain.goodsconfirm.GoodsConfirmContextItem;
import com.sales.ops.serviceimpl.dispatch.dodispatch.domain.goodsconfirm.ResultDto;
import com.sales.ops.serviceimpl.inventory.BaseInventoryService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author C12961
 * @date 2023/3/14 12:13
 */
@Slf4j
@Service
public class GoodsConfirmHandler {

    @Autowired
    private BaseInventoryService baseInventoryService;
    @Autowired
    private BaseWarehouseService baseWarehouseService;
    @Autowired
    private OpsRoService opsRoService;
    @Autowired
    private OpsDoService opsDoService;
    @Autowired
    private OpsPcoService opsPcoService;
    @Autowired
    private BaseDoService baseDoService;
    @Autowired
    private GoodsReadyJudgement goodsReadyJudgement;
    @Autowired
    private OpsRoBarcodeService opsRoBarcodeService;
    @Autowired
    private InvoiceEventLogService invoiceEventLogService;
    @Autowired
    private BaseRoService baseRoService;
    @Autowired
    private OpsCallWmsFeignApi opsCallWmsFeignApi;
    @Autowired
    private WmOrderTaskFindService wmOrderTaskFindService;
    @Autowired
    private WmDoService wmDoService;


    /**
     * 一个发票有多条move
     * 一条move只对应一条roId，
     * 一条roId可能对应多条move,
     * 一条roId可能对应多条barcode
     * barcode的总数代表（当前采购单号+发票号）的总分配数量
     * 如果barcode数大于move数，则代表一条roId可能对应多条move，采购单是合并采购单
     */
    public List<RoConfirmItem> goodsConfirm(RoSignConfirmDto confirm) throws OpsException {
        // 通过发票号，查询是否为组换单，如果为组换单，则跳过
        List<OpsRo> roIdsForZH = baseRoService.isExchangeOrder(confirm.getInvoice());
        if (CollectionUtils.isNotEmpty(roIdsForZH)) {
            List<RoConfirmItem> results = roIdsForZH.stream()
                    .map(ro -> new RoConfirmItem(RoConfirmRecTypeEnum.INZK.getType(), ro.getRoId(), null, null, confirm.getInvoice(),confirm.getInvoiceId()))
                    .collect(Collectors.toList());
            return results;
        }
        // 1. 校验参数
        checkParam(confirm);
        // 2. 组织数据
        GoodsConfirmContext context = initContext(confirm);
        // 3. 计算库存操作
        calculateResult(context);
        // 3. 处理操作结果
        handleResult(context);
        // 4. 记录日志
        invoiceEventLogService.insertInvoiceConfirmLog(context.getInvoiceNo(), context.getReturnDtoList(), context.getStartTime());
        // 5.返回数据
        return context.getReturnDtoList();
    }

    public void checkParam(RoSignConfirmDto confirm) throws OpsException {
        String invoice = confirm.getInvoice();

        String warehouse = confirm.getWarehouse();
        if (StringUtils.isEmpty(invoice)) {
            throw Exceptions.OpsException("发票号不可为空");
        }
        if (StringUtils.isEmpty(warehouse)) {
            throw Exceptions.OpsException("签收仓不可为空");
        }
        List<OpsInventoryMove> list = baseInventoryService.getOpsInventoryMoveListByInvoice(confirm.getInvoice(), confirm.getInvoiceId(), null);
        if (org.springframework.util.CollectionUtils.isEmpty(list)) {
            throw Exceptions.OpsException("无到货确认,发票号：" + invoice);
        }
        // 库存必须全是W
        // 如果有一条不是到货未入库，则抛异常
        boolean NOT_ALL_W = list.stream().anyMatch(move -> !StringUtils.equals(move.getInventoryStatus(), InventoryStatusEnum.W.getCode()));
        if (NOT_ALL_W) {
            throw Exceptions.OpsException("未签收发票，请先签收。" + invoice, invoice);
        }
        // 库存状态不能有5
        // 操作状态（1已接单 2初次导入 3关务导入 4已发票签收 5已到货确认）
        // 如果有一条已到货确认，则抛异常
        boolean INVONICE_CONFIRM = list.stream().anyMatch(move -> OptStatusEnum.GOODS_CONFIRM.getCode().equals(move.getOptStatus()));
        if (INVONICE_CONFIRM) {
            throw Exceptions.OpsException("已到货确认，请勿重复处理。发票号：" + invoice);
        }
        // 库存的签收仓必须是同一个
        List<String> signWarehouseCodes = list.stream().map(OpsInventoryMove::getSignWarehouseCode).distinct().collect(Collectors.toList());
        if (signWarehouseCodes.size() > 1) {
            throw Exceptions.OpsException("数据异常。单票仅允许单仓签收代码，发票号：" + invoice + ",请联系IT部门");
        }
        // 并且库存的签收仓必须与报文的仓库一直
        String signWarehouse = signWarehouseCodes.get(0);
        if (!warehouse.equalsIgnoreCase(signWarehouse)) {
            throw Exceptions.OpsException("发票号：" + invoice + ",签收仓(" + signWarehouse + ")与实际签收仓库(" + warehouse + ")不一致,请切换仓库代码");
        }
    }

    public GoodsConfirmContext initContext(RoSignConfirmDto param) throws OpsException {
        String invoiceNo = param.getInvoice();
        Long invoiceId = param.getInvoiceId();
        // 1. 通过仓库号查询仓库信息
        OpsWarehouse warehouse = baseWarehouseService.getWarehouseByCode(param.getWarehouse());
        // 2. 通过发票号查询move库存
        List<OpsInventoryMove> moves = baseInventoryService.getOpsInventoryMoveListByInvoice(invoiceNo, invoiceId, null);
        GoodsConfirmContext context = new GoodsConfirmContext(invoiceNo, invoiceId, warehouse, moves);
        for (OpsInventoryMove move : moves) {
            // 3. 遍历每条库存，查询roId
            String roId = getRoIdByInventoryIdFromRoItemInventory(move.getInventoryId());
            // 4. 查询roId的roBarcode
            int barCodeTotalQty = getBarCodeTotalQty(roId);
            // 5. 按ro分组move
            GoodsConfirmContextItem item = new GoodsConfirmContextItem(invoiceNo, move, warehouse, roId, barCodeTotalQty);
            context.putItem(item);
            // 5. 查询move的预约信息
            List<OpsDoItemInventory> doItemInventoryList = baseDoService.getDoItemInventoryByInventoryId(move.getInventoryId(), InventoryTableTypeEnum.TRANS);
            for (OpsDoItemInventory opsDoItemInventory : doItemInventoryList) {
                OpsDoItem opsDoItem = baseDoService.getDoItemByDoId(opsDoItemInventory.getDoId());
                OpsDo opsDo = baseDoService.getDoByDoId(opsDoItemInventory.getDoId());
                if (StringUtils.equals(opsDo.getDoType(), DoTypeEnum.JYCK.getType())) {
                    item.putDo(opsDo, opsDoItem, opsDoItemInventory);
                } else {
                    item.putDB(opsDo, opsDoItem, opsDoItemInventory);
                }
            }
            List<OpsPcoItemInventory> pcoItemInventoryList = opsPcoService.getOpsPcoItemInventoryByInventoryId(move.getInventoryId(), InventoryTableTypeEnum.TRANS);
            for (OpsPcoItemInventory opsPcoItemInventory : pcoItemInventoryList) {
                OpsPcoItem pcoItem = opsPcoService.getPcoItemByPcoIdAndItem(opsPcoItemInventory.getPcoId(), opsPcoItemInventory.getPcoItem());
                OpsPco pco = opsPcoService.getPcoByPcoId(opsPcoItemInventory.getPcoId());
                OpsDo jyck = baseDoService.getJYCKByOrder(pco.getOrderId(), pco.getOrderItem(), pco.getNum(), DoSourceEnum.ASSModelNo);
                item.putPco(jyck, pco, pcoItem, opsPcoItemInventory);
            }
        }
        return context;
    }


    public void calculateResult(GoodsConfirmContext context) throws OpsException {
        for (Map.Entry<String, List<GoodsConfirmContextItem>> entry : context.getItems().entrySet()) {
            List<GoodsConfirmContextItem> items = entry.getValue();
            GoodsConfirmContextItem item = items.get(0);
            ResultDto result = null;
            if (items.size() > 1) {                 // 两条move关联了一条roId（合并采购）
                result = ResultDto.INZK(item);
            } else if (item.notMasterWarehouse()    // 如果不是物流中心，则入库存
                    || item.isMergePurchase()       // 合并采购
                    || item.notFullPrepareForMove() // move预约数不满
                    || item.noPrepareByDoOrPco()    // move没有被do或pco预约
                    || item.prepareByDoAndPco()     // move被多条do和pco预约
            ) {// 结果：入在库
                result = ResultDto.INZK(item);
            }
            // 只被一个调拨单预约
            else if (item.prepareByOneDB()) {
                result = ResultDto.DBYY(item);
                // result = ResultDto.DBYK(item);
            }
            // 只被一个交易出库单预约
            else if (item.prepareByOneDo()) {
                result = ResultDto.DoYY(item);
                // result = ResultDto.DoYK(item);
            }
            // 只被一个加工单预约
            else if (item.prepareByOnePco()) {
                result = ResultDto.PcoYY(item);
                // result = ResultDto.PcoYK(item);
            }
            context.putResult(result);
        }
        // 处理返回结果：返回结果非空检测
        if (context.getResults().isEmpty()) {
            throw Exceptions.OpsException("无到货确认" + context.getInvoiceNo(), context.getInvoiceNo());
        }
    }

    private void handleResult(GoodsConfirmContext context) throws OpsException {
        // 生成返回值
        context.createRoConfirmItem();
        // 更新越库标识
        // 过滤出DB\Do\Pco的结果集
        context.filterResult();
        context.getDbResults().forEach(result -> opsDoService.updateOpsDoForCrossRoId(result.getDoId(), result.getConfirmType().getCode(), result.getRoId()));
        context.getDoResults().forEach(result -> opsDoService.updateOpsDoForCrossRoId(result.getDoId(), result.getConfirmType().getCode(), result.getRoId()));
        context.getPcoResults().forEach(result -> {
            if (result.isCross()) {
                opsDoService.updateOpsDoForCrossRoId(result.getDoId(), result.getConfirmType().getCode(), result.getRoId());
            }
            opsPcoService.updateOpsPcoForCrossRoId(result.getPcoId(), result.getRoId());
            opsPcoService.updatePcoItemIsCrossByPcoItem(result.getPcoId(), result.getSplitNo(), true);
        });
        // 写入事件表
        // 下发越库do
        for (ResultDto result : context.getDbResults()) {
            if (result.isCross()) {
                sendDoToWms(result.getDoId(), result.getConfirmType().getCode(), result.getRoId());
            }
        }
        for (ResultDto result : context.getDoResults()) {
            if (result.isCross()) {
                sendDoToWms(result.getDoId(), result.getConfirmType().getCode(), result.getRoId());
            }
        }
        // 下发越库do\pco
        for (ResultDto result : context.getPcoResults()) {
            if (result.isCross()) {
                sendPcoToWms(result);
            }
        }
        // 更新optStatus为发票确认
        baseInventoryService.updateInventoryMoveOptStatusByInvoiceNo(context.getInvoiceNo(), context.getInvoiceId(), OptStatusEnum.GOODS_CONFIRM);
    }


    public void sendPcoToWms(ResultDto result) throws OpsException {
        Map<String, String> yukuMap = new HashMap<>();
        yukuMap.put(result.getModelno(), result.getPcoId());
        // 查外采购
        // PCO 是外部采购的才传lot13
        // 外部到货的PCO项号需要传PCOID，库存的不需要传
        // 预约的、越库的PCO带上YuKuMap，越库的YuKuRoId （不需要YuKuRoId）
        // 以上最后一项货齐判断是否符合下发是否越库或预约
        // 越库需带上此标记
        // 越库的时候带上自己这箱以及之前的外部先来货的
        OpsSendPcoAndDoMidIDDto pcoAndDoMidIDDto = new OpsSendPcoAndDoMidIDDto();
        pcoAndDoMidIDDto.setDoId(result.getDoId());
        pcoAndDoMidIDDto.setPcoId(result.getPcoId());
        pcoAndDoMidIDDto.setYuKuMap(yukuMap);
        pcoAndDoMidIDDto.setYuKuRoId(result.getRoId());
        wmDoService.updateWMSPcoAddDoTwo(pcoAndDoMidIDDto);
    }


    private boolean sendDoToWms(String doId, int crossFlag, String yuekuRoId) throws OpsException {
        OpsDoAndItemDto opsDoAndItemDto = opsDoService.findDoToWms(doId);
        // 无数据
        if (null == opsDoAndItemDto) {
            StringBuffer buf = new StringBuffer();
            buf.append("doid:").append(doId).append(" res: 没有记录");
            log.error(buf.toString());
            return false;
        }
        // yuekuRoId 上预约不带ROID，ROID作用越库且传给富勒
        // 1上预约 2越库 0上库存
        if (1 == crossFlag) {
            opsDoAndItemDto.setYuekuDoId(doId);
        } else if (2 == crossFlag) {
            // 越库传roid,以及DOID作用越库且传给富勒
            opsDoAndItemDto.setYuKuRoId(yuekuRoId);
            opsDoAndItemDto.setYuekuDoId(doId);
        }
        // 直接下发
        OpsWmOrderTask wmOrderTask = wmOrderTaskFindService.findWmsOrderTaskByWmOrderId(doId, WmOrderTaskEnum.DO);
        if (null == wmOrderTask) {
            return false;
        }
        opsDoAndItemDto.setWmOrderTaskId(wmOrderTask.getId());
        // 3.post to wms
        CommonResult resWm = opsCallWmsFeignApi.updateDoToWms(opsDoAndItemDto);
        if (!resWm.isSuccess()) {
            return false;
        }
        return true;
    }

    private String getRoIdByInventoryIdFromRoItemInventory(Long inventoryId) throws OpsException {
        // 拿到关联的ro_id
        List<OpsRoItemInventory> roItemInventoryList = baseRoService.findRoItemInventoryByInventoryId(inventoryId);
        if (CollectionUtils.isEmpty(roItemInventoryList)) {
            throw Exceptions.OpsException("无对应的OpsRoItemInventory:InventoryId=>" + inventoryId);
        }
        return roItemInventoryList.get(0).getRoId();
    }

    private int getBarCodeTotalQty(String roId) throws OpsException {
        List<OpsRoBarcode> roBarcodes = opsRoBarcodeService.findRoBarcodeByRoId(roId);
        if (org.springframework.util.CollectionUtils.isEmpty(roBarcodes)) {
            throw Exceptions.OpsException("无对应的箱码：ROID:" + roId);
        }
        // 箱码对应数量汇总（用于判断能否预约）
        return roBarcodes.stream().map(OpsRoBarcode::getQty).reduce(0, Integer::sum);
    }

/*

    public List<RoConfirmItem> goodsConfirmOld(RoSignConfirmDto paramDto) throws OpsException {
        String invoice = paramDto.getInvoice();
        Long invoiceId = paramDto.getInvoiceId();
        String warehouse = paramDto.getWarehouse();
        Date fromDate = new Date();
        // 在途中
        List<OpsInventoryMove> list = baseInventoryService.getOpsInventoryMoveListByInvoice(invoice, invoiceId, null);
        // do越库更新 key:Doid, value<1预约2越库,roid>
        Map<String, Map<Integer, String>> doYueKu = new HashMap<>();
        // pco越库更新
        Map<String, String> pcoYueKu = new HashMap<>();
        // PO 对应 OpsInventoryMove行(PO，orderno,item,split)
        Map<OpsInventoryMove, List<OpsInventoryMove>> mapPoInvMove = new HashMap<>();
        for (OpsInventoryMove inventoryMove : list) {
            OpsInventoryMove inventoryMovePO = new OpsInventoryMove();
            inventoryMovePO.setAssociateNo(inventoryMove.getAssociateNo());
            inventoryMovePO.setAssociateNoItem(inventoryMove.getAssociateNoItem());
            inventoryMovePO.setAssociateNoSplitno(inventoryMove.getAssociateNoSplitno());
            inventoryMovePO.setModelno(inventoryMove.getModelno());
            inventoryMovePO.setInvoiceno(inventoryMove.getInvoiceno());
            if (!mapPoInvMove.containsKey(inventoryMove)) {
                List<OpsInventoryMove> implist = new ArrayList();
                implist.add(inventoryMovePO);
                mapPoInvMove.put(inventoryMove, implist);
            } else {
                mapPoInvMove.get(inventoryMove).add(inventoryMovePO);
            }
        }
        // 返回给富勒的值
        List<RoConfirmItem> roConfirmItemList = new ArrayList<>();
        // 判断合并采购时，上货架且按箱重复RO 不继续发
        List<String> roIds = new ArrayList<>();
        // 已发送的DOID，会存在一个发票2在途分别2 DOID交叉预约，剔除已下发
        List<String> doIds = new ArrayList<>();
        // begin:bug9278 判断物流中心库房 true为物流中心 b28029 2023-1-10
        ResultVo<String> opsWarehouseType = wmCommonService.getWarehouseTypeByCode(warehouse);
        // end bug:9278 非物流中心，不存在越库，预约。只可上库存
        if (!WarehouseTypeEnum.RDC.getHouseTypeCode().equalsIgnoreCase(opsWarehouseType.getData())) {
            for (Map.Entry<OpsInventoryMove, List<OpsInventoryMove>> entry : mapPoInvMove.entrySet()) {
                OpsInventoryMove inventoryMove = entry.getKey();
                List<OpsRoItemInventory> roItemInventoryList = baseRoService.findRoItemInventoryByInventoryId(inventoryMove.getInventoryId());
                OpsRoItemInventory roItemInventory = roItemInventoryList.get(0);
                String roId = roItemInventory.getRoId();
                // 返回上货架库存
                RoConfirmRecTypeEnum roConfirmRecTypeEnum = RoConfirmRecTypeEnum.INZK;
                RoConfirmItem roConfirmItem = new RoConfirmItem(roConfirmRecTypeEnum.getType(), roId, null, null, invoice);
                roConfirmItemList.add(roConfirmItem);
            }
            // 操作状态改发票确认
            baseInventoryService.updateInventoryMoveOptStatusByInvoiceNo(invoice, invoiceId, InventoryMoveOpsStatusEnum.INVONICE_GOODCONFIRM);
            ImpInvoiceEventLog eventLog = new ImpInvoiceEventLog();
            eventLog.setOpType("/order/confirmgoods");
            eventLog.setRequestParam(invoice);
            eventLog.setOpStartTime(fromDate);
            eventLog.setReturnData(JSON.toJSONString(roConfirmItemList));
            this.addImpInvoiceEventLog(eventLog);
            // 写入到货确认返回结果
            List<OpsRoConfirmLog> confirmLogs = new ArrayList<>();
            for (RoConfirmItem roConfirmItem : roConfirmItemList) {
                OpsRoConfirmLog confirmLog = new OpsRoConfirmLog();
                confirmLog.setRoId(roConfirmItem.getRoId());
                confirmLog.setDoId(roConfirmItem.getDoid());
                confirmLog.setReceiveType(roConfirmItem.getReceiveType());
                confirmLog.setPcoId(roConfirmItem.getPcoid());
                confirmLog.setInvoiceNo(roConfirmItem.getInvoiceNo());
                confirmLog.setStatus(1);
                confirmLog.setCreator(UserDto.WMS.getUserName());
                confirmLog.setCreTime(new Date());
                confirmLogs.add(confirmLog);
            }
            roConfirmLogService.insertBatchConfirmLog(confirmLogs);
            return roConfirmItemList;
        }
        // 逐个一个PO
        for (Map.Entry<OpsInventoryMove, List<OpsInventoryMove>> entry : mapPoInvMove.entrySet()) {
            OpsInventoryMove inventoryMove = entry.getKey();
            List<OpsInventoryMove> inventoryMoves = entry.getValue();
            // DOID，DoItemInventory
            Map<String, List<OpsDoItemInventory>> mapdo = new HashMap<>();
            // PCOID，PcoItemInventory
            Map<String, List<OpsPcoItemInventory>> mappco = new HashMap<>();
            // RoItemInventory
            List<OpsRoItemInventory> roItemInventoryList = baseRoService.findRoItemInventoryByInventoryId(inventoryMove.getInventoryId());
            if (CollectionUtils.isEmpty(roItemInventoryList)) {
                throw Exceptions.OpsException("无对应的OpsRoItemInventory:InventoryId=>" + String.valueOf(inventoryMove.getInventoryId()));
            }
            OpsRoItemInventory roItemInventory = roItemInventoryList.get(0);
            String roId = roItemInventory.getRoId();
            List<OpsRoBarcode> roBarcodes = opsRoBarcodeService.findRoBarcodeByRoId(roId);
            if (CollectionUtils.isEmpty(roBarcodes)) {
                throw Exceptions.OpsException("无对应的箱码：ROID:" + roId);
            }
            // 箱码对应数量汇总（用于判断能否预约）
            int barCodeTotalQty = 0;
            for (OpsRoBarcode opsRoBarcode : roBarcodes) {
                barCodeTotalQty += opsRoBarcode.getQty();
            }
            // DO在途库存关联  key=doid,value=opsDoItemInventory
            List<OpsDoItemInventory> doItemInventoryList = baseDoService.getDoItemInventoryByInventoryId(inventoryMove.getInventoryId(), InventoryTableTypeEnum.TRANS);
            for (OpsDoItemInventory doItemInventory : doItemInventoryList) {
                if (mapdo.containsKey(doItemInventory.getDoId())) {
                    mapdo.get(doItemInventory.getDoId()).add(doItemInventory);
                } else {
                    List<OpsDoItemInventory> ItemInventoryList = new ArrayList<>();
                    ItemInventoryList.add(doItemInventory);
                    mapdo.put(doItemInventory.getDoId(), ItemInventoryList);
                }
            }
            // 组装在途库存关联 key=pcoid,value=opsPcoItemInventorys
            List<OpsPcoItemInventory> pcoItemInventoryList = opsPcoService.getOpsPcoItemInventoryByInventoryId(inventoryMove.getInventoryId(), InventoryTableTypeEnum.TRANS);
            for (OpsPcoItemInventory pcoItemInventory : pcoItemInventoryList) {
                if (mappco.containsKey(pcoItemInventory.getPcoId())) {
                    mappco.get(pcoItemInventory.getPcoId()).add(pcoItemInventory);
                } else {
                    List<OpsPcoItemInventory> ItemInventoryList = new ArrayList<>();
                    ItemInventoryList.add(pcoItemInventory);
                    mappco.put(pcoItemInventory.getPcoId(), ItemInventoryList);
                }
            }
            // 在途PO有两种判断 =1行与>1行
            // 在途行数只有1行(
            if (inventoryMoves.size() == 1) {
                // 箱码对应数量大于收货数说明合并采购，上在库 或者箱码数大于被预约数
                // 一在途被多个DO或PCO分配，则合并采购
                if (barCodeTotalQty > inventoryMove.getQuantity()
                        || (inventoryMove.getPrepareQuantity() > 0 && barCodeTotalQty > inventoryMove.getPrepareQuantity())
                        || (mapdo.size() > 0 && mappco.size() > 0)) {
                    // 返回上货架在库
                    if (!roIds.contains(roId)) {
                        // 返回上货架库存
                        RoConfirmRecTypeEnum roConfirmRecTypeEnum = RoConfirmRecTypeEnum.INZK;
                        RoConfirmItem roConfirmItem = new RoConfirmItem(roConfirmRecTypeEnum.getType(), roId, null, null, invoice);
                        roConfirmItemList.add(roConfirmItem);
                        roIds.add(roId);
                    }
                    // 合并采购下发DO
                    for (Map.Entry<String, List<OpsDoItemInventory>> doEntry : mapdo.entrySet()) {
                        String doId = doEntry.getKey();
                        OpsDo opsDo = baseDoService.findDoByDoId(doId);
                        if (null == opsDo) {
                            throw Exceptions.OpsException("无opsDo:doId=>" + doId);
                        }
                        OpsDoItem opsDoItem = baseDoService.getDoItemByDoId(doId);
                        if (null == opsDoItem) {
                            throw Exceptions.OpsException("无对应的opsDoItem:doId=>" + doId);
                        }
                        // 判断是否货齐
                        boolean isEnough = this.isEnoughToCrossForDo(opsDo, opsDoItem, invoice);
                        // 收齐才下发DO
                        if (isEnough) {
                            // 判断是否货齐下发条件
                            if (DoTypeEnum.DBCK.getType().equals(opsDo.getDoType())) {
                                if (!doIds.contains(doId)) {
                                    doIds.add(doId);
                                    this.sendDoToWms(doId, 0, "");
                                }
                            }
                            // 客户交易出库单
                            else if (DoTypeEnum.JYCK.getType().equals(opsDo.getDoType())) {
                                // 2单项分批随到发货(默认就越库)只判断信用拦截
                                if (CKTYPEEnum.ITEM_UNLIMIT.getCode().equals(opsDo.getDlvEntire())) {
                                    // 默认下发DO
                                    if (!doIds.contains(doId)) {
                                        doIds.add(doId);
                                        this.sendDoToWms(doId, 0, "");
                                    }
                                }
                                // 0单项单仓货齐发货(按10位数单号齐否判断)
                                else if (CKTYPEEnum.ITEM_GETHER_SIGNLE_HOUSE.getCode().equals(opsDo.getDlvEntire())) {
                                    List<OpsDo> doList = baseDoService.findDoListByOrder(opsDo.getOrderId(),
                                            opsDo.getOrderItem(), null, DoTypeEnum.JYCK);
                                    // 子项满足条件默认获齐全
                                    boolean itemEnough = true;
                                    isEnough = false;
                                    for (OpsDo subDo : doList) {
                                        OpsDoItem subDoItem = baseDoService.getDoItemByDoId(subDo.getDoId());
                                        if (null == subDoItem) {
                                            throw Exceptions.OpsException("无对应的opsDoItem:doId=>" + subDo.getDoId());
                                        }
                                        // 是否货齐
                                        isEnough = isEnoughToCrossForDo(subDo, subDoItem, invoice);
                                        // 其中有一DO不货齐，则不满足越库
                                        if (!isEnough) {
                                            itemEnough = false;
                                        }
                                    }
                                    // 满足下发DO条件
                                    if (itemEnough) {
                                        for (OpsDo subdo : doList) {
                                            // do 按订单10位一起下发得使用10位订单包含的DOid号
                                            if (!doIds.contains(subdo.getDoId())) {
                                                doIds.add(subdo.getDoId());
                                                this.sendDoToWms(subdo.getDoId(), 0, "");
                                            }
                                        }
                                    }
                                }
                                // 1-整单单仓货齐发货,3-整单多仓货齐发货 7位订单齐了才可以越库（如果越库需要七位数下发WMS）
                                else if (CKTYPEEnum.ORDER_GETHER_SIGNLE_HOUSE.getCode().equals(opsDo.getDlvEntire())
                                        || CKTYPEEnum.ORDER_GETHER_MULTI_HOUSE.getCode().equals(opsDo.getDlvEntire())) {
                                    // 初始
                                    isEnough = true;
                                    List<OpsDo> doList = baseDoService.findDoListByOrder(opsDo.getOrderId(), null, null, DoTypeEnum.JYCK);
                                    // 越库，判断订单其他项是否已经越库
                                    isEnough = isEnoughTocrossForFullOrder(doList, opsDo);
                                    // 符合货齐条件
                                    if (isEnough) {
                                        // 下发DO（按7位数）下发到WMS
                                        for (OpsDo subDo : doList) {
                                            if (!doIds.contains(subDo.getDoId())) {
                                                doIds.add(subDo.getDoId());
                                                this.sendDoToWms(subDo.getDoId(), 0, "");
                                            }
                                        }
                                    }

                                }
                            } else if (DoTypeEnum.CGDBCK.getType().equals(opsDo.getDoType())) {// 采购调拨出库 直接下发DO（预到货时已拆分DOID）
                                // 下发DO
                                if (!doIds.contains(doId)) {
                                    doIds.add(doId);
                                    this.sendDoToWms(doId, 0, "");
                                }
                            }
                        }
                    }
                    // 以上合并采购执行下发执行DO完成
                    // 开始执行合并采购执行下发PCO
                    for (Map.Entry<String, List<OpsPcoItemInventory>> pcoEntry : mappco.entrySet()) {
                        String pcoid = pcoEntry.getKey();
                        Integer pcoItem = 0;
                        List<OpsPcoItemInventory> pcoItemInventories = pcoEntry.getValue();
                        OpsPco opsPco = opsPcoService.getPcoByPcoId(pcoid);
                        if (null == opsPco) {
                            throw Exceptions.OpsException("无pcoid：" + pcoid);
                        }
                        if (CollectionUtils.isEmpty(pcoItemInventories)) {
                            throw Exceptions.OpsException("无OpsPcoItemInventory：" + pcoid);
                        }
                        pcoItem = pcoItemInventories.get(0).getPcoItem();
                        List<OpsDo> doList = baseDoService.findDoListByOrder(opsPco.getOrderId(),
                                opsPco.getOrderItem(), null, DoTypeEnum.JYCK);
                        // pco orderId orderItem 对应只有1个DO
                        if (CollectionUtils.isEmpty(doList)) {
                            throw Exceptions.OpsException("无对应的交易出库单：" + opsPco.getOrderId() + "-" + String.valueOf(opsPco.getOrderItem()));
                        }
                        OpsDo opsDo = doList.get(0);
                        if (null == opsDo) {
                            throw Exceptions.OpsException("无对应opsDo：对应订单" + opsPco.getOrderId() + "-" + String.valueOf(opsPco.getOrderItem()));
                        }
                        String doId = opsDo.getDoId();
                        List<OpsPcoItem> pcoItemList = opsPcoService.selectItemBypcoId(pcoid);
                        if (CollectionUtils.isEmpty(pcoItemList)) {
                            throw Exceptions.OpsException("OpsPcoItem无数据：" + pcoid);
                        }
                        // 加工单可否可越库(单项DO的PCOITEM齐了)
//                        boolean isEnough = isEnoughTocrossForPco(opsPco, pcoItemList, invoice);
                        OpsPcoItem opsPcoItem = opsPcoService.getPcoItemByPcoIdAndItem(pcoid, pcoItem);
                        if (null == opsPcoItem) {
                            throw Exceptions.OpsException("OpsPcoItem无数据：" + pcoid + "-" + String.valueOf(pcoItem));
                        }
                        // 判断当行PCO-ITEM是否货齐
                        boolean isEnough = isEnoughTocrossForPcoItem(opsPcoItem, invoice);
                        if (isEnough) {
                            // 2-单项分批随到发货，需要10位订单都齐了才可以下发DO,PCO
                            // 0-单项单仓货齐发货(订单号10位数判断)
                            if (CKTYPEEnum.ITEM_UNLIMIT.getCode().equals(opsDo.getDlvEntire())
                                    || CKTYPEEnum.ITEM_GETHER_SIGNLE_HOUSE.getCode().equals(opsDo.getDlvEntire())) {
                                // todo 不越库不下发
                            }
                            //  1-整单单仓货齐发货，3-整单多仓货齐发货，需要7位订单都齐才同时下发DO ,PCO 7位全部
                            // 1和3, 暂定上库存(2022-9-7)，后续改善后再判断。需要7位数订单内所有ITEM全齐，才一次下发
                            else if (CKTYPEEnum.ORDER_GETHER_SIGNLE_HOUSE.getCode().equals(opsDo.getDlvEntire())
                                    || CKTYPEEnum.ORDER_GETHER_MULTI_HOUSE.getCode().equals(opsDo.getDlvEntire())) {
                                // 上库存INZK（且不下发DO,pco）
                            }
                        }
                    }
                    // 以上执行PCO完成
                }
                // 以上执行箱码大于在途总数（上库存）
                // 1个在途行1个do占用
                else if (mapdo.size() == 1) {
                    RoConfirmRecTypeEnum roConfirmRecTypeEnum = RoConfirmRecTypeEnum.INYY;
                    int crossFlag = 0; // 1上预约 2越库
                    String doId = "";
                    // 只有一个DO
                    for (Map.Entry<String, List<OpsDoItemInventory>> doEntry : mapdo.entrySet()) {
                        doId = doEntry.getKey();
                    }
                    OpsDo opsDo = baseDoService.findDoByDoId(doId);
                    if (null == opsDo) {
                        throw Exceptions.OpsException("无opsDo:doId=>" + doId);
                    }
                    OpsDoItem opsDoItem = baseDoService.getDoItemByDoId(doId);
                    if (null == opsDoItem) {
                        throw Exceptions.OpsException("无opsDoItem:doId=>" + doId);
                    }
                    // 按交货期客户(true 的需按do.wldate日期判断大于等于当天，判断信用拦截（判断越库），格式yyyy-MM-dd)
                    boolean isUseWlDate = this.exitsCustmerWldateByCustomerNo(opsDo.getCustomerNo());
                    // 一共判断DO类型 调拨出库、交易出库、采购调拨出库
                    // 调拨出库(判断交货期，不判断信用拦截)
                    if (DoTypeEnum.DBCK.getType().equals(opsDo.getDoType())) {
                        boolean isEnough = false;
                        // 是否货齐准备好，可以下发
                        boolean isReady = this.isEnoughToCrossForDo(opsDo, opsDoItem, invoice);
                        // 默认上预约货架
                        roConfirmRecTypeEnum = RoConfirmRecTypeEnum.INYY;
                        crossFlag = 1;
                        // 货齐代表可下发,继续判断上预约或越库指令
                        if (isReady) {
                            isEnough = this.isEnoughTocrossForDoFlag(opsDo, opsDoItem, invoice);
                        }
                        // 符合越库条件
                        if (isEnough) {
                            crossFlag = 2;
                            // 越库指令
                            roConfirmRecTypeEnum = RoConfirmRecTypeEnum.YK;
                        }
                        if (isReady) {// 货齐符合发货
                            if (!doIds.contains(doId)) {
                                doIds.add(doId);
                                this.sendDoToWms(doId, crossFlag, roId);
                                // 符合越库条件（不信用拦截）
                                if (roConfirmRecTypeEnum == RoConfirmRecTypeEnum.YK) {
                                    Map<Integer, String> roMap = new HashMap<>();
                                    roMap.put(2, roId);
                                    doYueKu.put(doId, roMap);
                                }
                            }
                        }
                    }
                    // 客户交易出库单
                    else if (DoTypeEnum.JYCK.getType().equals(opsDo.getDoType())) {
                        // 2单项分批随到发货(默认就越库)只判断信用拦截
                        if (CKTYPEEnum.ITEM_UNLIMIT.getCode().equals(opsDo.getDlvEntire())) {
                            boolean isReady = false;// 是否货齐准备好，可以下发
                            // 信用拦截是否越库
                            boolean isEnough = false;
                            // 默认上预约
                            roConfirmRecTypeEnum = RoConfirmRecTypeEnum.INYY;
                            crossFlag = 1;
                            isReady = this.isEnoughToCrossForDo(opsDo, opsDoItem, invoice);
                            // 货齐（可以下发DO）
                            if (isReady) {
                                if (isUseWlDate) {
                                    // 当天>=物流发货日期，可越库。 小于发货日不越库
                                    if (this.isArriveWlDate(opsDo.getWlDate())) {
                                        isEnough = this.checkCreditCross(opsDo.getOrderId(), opsDo.getOrderItem());
                                    } else {
                                        isEnough = false;
                                    }
                                } else {// 不考虑纳期
                                    isEnough = this.checkCreditCross(opsDo.getOrderId(), opsDo.getOrderItem());
                                }
                                if (isEnough) {
                                    crossFlag = 2;
                                    roConfirmRecTypeEnum = RoConfirmRecTypeEnum.YK;
                                }
                            }
                            if (isReady) {
                                this.sendDoToWms(doId, crossFlag, roId);
                                // 下发越库
                                if (RoConfirmRecTypeEnum.YK == roConfirmRecTypeEnum) {
                                    Map<Integer, String> roMap = new HashMap<>();
                                    roMap.put(2, roId);
                                    doYueKu.put(doId, roMap);
                                }
                            }
                        }
                        // 0单项单仓货齐发货(按10位数单号齐否判断)
                        else if (CKTYPEEnum.ITEM_GETHER_SIGNLE_HOUSE.getCode().equals(opsDo.getDlvEntire())) {
                            List<OpsDo> doList = baseDoService.findDoListByOrder(opsDo.getOrderId(),
                                    opsDo.getOrderItem(), null, DoTypeEnum.JYCK);
                            // 是否货齐(默认货齐，有其中一个不齐，就不下发)
                            boolean isReady = true;
                            for (OpsDo subDo : doList) {
                                OpsDoItem subDoItem = baseDoService.getDoItemByDoId(subDo.getDoId());
                                if (null == subDoItem) {
                                    throw Exceptions.OpsException("无对应的subDo.opsDoItem:doId=>" + subDo.getDoId());
                                }
                                // 是否货齐
                                boolean isItemEnough = this.isEnoughToCrossForDo(subDo, subDoItem, invoice);
                                // 其中有一DO不货齐，则不满足越库
                                if (!isItemEnough) {
                                    isReady = false;
                                }
                            }
                            boolean isEnough = false;
                            // 判断信用拦截，是否越库与上预约
                            if (isReady) {
                                isEnough = true;
                                // 信用拦截
                                boolean isItemEnough = true;
                                for (OpsDo subDo : doList) {
                                    // 纳期客户
                                    if (isUseWlDate) {
                                        // 到交货期
                                        if (this.isArriveWlDate(subDo.getWlDate())) {
                                            isItemEnough = this.checkCreditCross(opsDo.getOrderId(), opsDo.getOrderItem());
                                        } else {
                                            // 没到交货期（默认上预约）
                                            isItemEnough = false;
                                        }
                                    } else {
                                        isItemEnough = this.checkCreditCross(opsDo.getOrderId(), opsDo.getOrderItem());
                                    }
                                    // 信用拦截有一项拦截则不越库
                                    if (!isItemEnough) {
                                        isEnough = false;
                                    }
                                }
                            }
                            crossFlag = 1;
                            roConfirmRecTypeEnum = RoConfirmRecTypeEnum.INYY;
                            // 上面已经判断货齐并下发 1通过 0 不通过
                            if (isEnough) {
                                // 判断越库指令还是预约指令
                                isEnough = this.isEnoughTocrossForDoFlag(opsDo, opsDoItem, invoice);
                            }
                            // 是否越库 true=越库
                            if (isEnough) {
                                crossFlag = 2;
                                roConfirmRecTypeEnum = RoConfirmRecTypeEnum.YK;
                            }
                            // 符合下发条件
                            if (isReady) {
                                // 2023-04-07 新增 遍历按10位订单DO数组  b28029
                                // 如存在一个发票两个或以上DOID（如调拨），在第一个DOID就下发1一个DOID同时，把剩余DOID一起下发。A B C doid
                                // 遍历到下一个DOID，因为已在第一次下发过，就不再下发了（只有同一票来货且）
                                for (OpsDo subdo : doList) {
                                    if (!doIds.contains(subdo.getDoId())) {
                                        // 当前行越库或预约指令
                                        if (subdo.getDoId().equals(doId)) {
                                            doIds.add(subdo.getDoId());
                                            this.sendDoToWms(subdo.getDoId(), crossFlag, roId);
                                            // 不信用拦截(符合越库条件)
                                            if (roConfirmRecTypeEnum == RoConfirmRecTypeEnum.YK) {
                                                Map<Integer, String> roMap = new HashMap<>();
                                                roMap.put(2, roId);
                                                doYueKu.put(doId, roMap);
                                            }
                                        } else {
                                            // 其他DOID直接下发指令（非越库）
                                            // 检测当前DOID 存不存在同发票，存在 1、修改doyueku,2、下发越库标记 （不需要发票号判断，因上面代码以进行判断）
                                            List<OpsDoItemInventory> subDoItemInventoryList = baseDoService.getDoItemInventoryByDoId(subdo.getDoId(),
                                                    InventoryTableTypeEnum.TRANS);
                                            doIds.add(subdo.getDoId());
                                            // 表示无在途，直接下发在库
                                            if (CollectionUtils.isEmpty(subDoItemInventoryList)) {
                                                this.sendDoToWms(subdo.getDoId(), 0, "");
                                            } else {
                                                // 需越库或预约
                                                this.sendDoToWms(subdo.getDoId(), crossFlag, roId);
                                                // 如果同一个发票，2个调拨单订单里，第一个下发的时候，会用ROID-1，那第二个也会一起下发，用ROID-1,这就不对了，应该第二个订单用ROID-2
                                                if (roConfirmRecTypeEnum == RoConfirmRecTypeEnum.YK || roConfirmRecTypeEnum == RoConfirmRecTypeEnum.INYY) {
                                                    // 找到对应ROid
                                                    String subRoId = roId;
                                                    Long subDoInventoryId = subDoItemInventoryList.get(0).getInventoryId();
                                                    List<OpsRoItemInventory> opsRoItemInventories = baseRoService.findRoItemInventoryByInventoryId(subDoInventoryId);
                                                    if (!CollectionUtils.isEmpty(opsRoItemInventories)) {
                                                        subRoId = opsRoItemInventories.get(0).getRoId();
                                                    }
                                                    Map<Integer, String> roMap = new HashMap<>();
                                                    roMap.put(roConfirmRecTypeEnum == RoConfirmRecTypeEnum.YK ? 2 : 1, subRoId);
                                                    doYueKu.put(subdo.getDoId(), roMap);
                                                }
                                            }
                                        }
                                    }
                                }
                                // 备份2023-4-7 11:50
//                                //当前行DOID越库其他不越库
//                                for (OpsDo subdo : doList) {
//                                    //当前行越库或预约指令
//                                    if (subdo.getDoId().equals(doId)) {
//                                        if (!doIds.contains(subdo.getDoId())) {
//                                            doIds.add(subdo.getDoId());
//                                            this.sendDoToWms(subdo.getDoId(), crossFlag, roId);
//                                            //不信用拦截(符合越库条件)
//                                            if (roConfirmRecTypeEnum == RoConfirmRecTypeEnum.YK) {
//                                                Map<Integer, String> roMap = new HashMap<>();
//                                                roMap.put(2, roId);
//                                                doYueKu.put(doId, roMap);
//                                            }
//                                        }
//                                    } else {
//                                        //其他DOID直接下发指令（非越库）
//                                        if (!doIds.contains(subdo.getDoId())) {
//                                            doIds.add(subdo.getDoId());
//                                            this.sendDoToWms(subdo.getDoId(), 0, "");
//                                        }
//                                    }
//                                }
                            }
                        }
                        // 1-整单单仓货齐发货,3-整单多仓货齐发货 7位订单齐了才可以越库（如果越库需要七位数下发WMS）
                        else if (CKTYPEEnum.ORDER_GETHER_SIGNLE_HOUSE.getCode().equals(opsDo.getDlvEntire())
                                || CKTYPEEnum.ORDER_GETHER_MULTI_HOUSE.getCode().equals(opsDo.getDlvEntire())) {
                            // 判断当前DO是否越库
                            boolean isEnough = this.isEnoughToCrossForDo(opsDo, opsDoItem, invoice);
                            // 是否货齐(默认货齐，有其中一个不齐，就不下发)
                            boolean isReady = true;
                            if (isEnough) {
                                // 越库，判断订单其他项是否已经越库（DOType=交易出库）
                                List<OpsDo> doList = baseDoService.findDoListByOrder(opsDo.getOrderId(), null, null, DoTypeEnum.JYCK);
                                // 是否货齐（7位数）
                                isReady = isEnoughTocrossForFullOrder(doList, opsDo);
                                // 符合货齐条件
                                if (isReady) {
                                    // 考虑纳期
                                    for (OpsDo subDo : doList) {
                                        boolean itemEnough = false;// 子项信用拦截
                                        if (isUseWlDate) {
                                            if (this.isArriveWlDate(subDo.getWlDate())) {
                                                itemEnough = this.checkCreditCross(opsDo.getOrderId(), opsDo.getOrderItem());
                                            } else {
                                                // 没到交货期（默认上预约）
                                                itemEnough = false;
                                            }
                                        } else {
                                            itemEnough = this.checkCreditCross(opsDo.getOrderId(), opsDo.getOrderItem());
                                        }
                                        // 信用拦截有一项拦截则不越库
                                        if (!itemEnough) {
                                            isEnough = false;
                                        }
                                    }
                                    // 默认预约
                                    roConfirmRecTypeEnum = RoConfirmRecTypeEnum.INYY;
                                    crossFlag = 1;
                                    // 通过信用拦截
                                    if (isEnough) {
                                        // 判断预约指令还是越库指令
                                        isEnough = this.isEnoughTocrossForDoFlag(opsDo, opsDoItem, invoice);
                                        if (isEnough) {
                                            crossFlag = 2;
                                            roConfirmRecTypeEnum = RoConfirmRecTypeEnum.YK;
                                        }
                                    }
                                    // 是要下发DOID
                                    if (isReady) {
                                        // 下发DO（按7位数）先下发到WMS
                                        for (OpsDo subDo : doList) {
                                            if (subDo.getDoId().equals(doId)) {
                                                // 带上批次属性标记
                                                if (!doIds.contains(subDo.getDoId())) {
                                                    doIds.add(subDo.getDoId());
                                                    this.sendDoToWms(subDo.getDoId(), crossFlag, roId);
                                                    // 符合越库条件（不拦截）
                                                    if (roConfirmRecTypeEnum == RoConfirmRecTypeEnum.YK) {
                                                        Map<Integer, String> roMap = new HashMap<>();
                                                        roMap.put(2, roId);
                                                        doYueKu.put(doId, roMap);
                                                    }
                                                }
                                            } else {
                                                // 直接下发DOID
                                                if (!doIds.contains(subDo.getDoId())) {
                                                    doIds.add(subDo.getDoId());
                                                    this.sendDoToWms(subDo.getDoId(), 0, roId);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }

                    }
                    // 采购调拨出库 直接下发DO（预到货时已拆分DOID）
                    else if (DoTypeEnum.CGDBCK.getType().equals(opsDo.getDoType())) {
                        boolean isEnough = false;
                        // 是否货齐
                        isEnough = this.isEnoughToCrossForDo(opsDo, opsDoItem, invoice);
                        roConfirmRecTypeEnum = RoConfirmRecTypeEnum.INYY;
                        crossFlag = 1;
                        // 采购调拨出库越库（如分批来，前端已拆分DOID）
                        if (isEnough) {
                            // 判断上预约还是越库
                            isEnough = this.isEnoughTocrossForDoFlag(opsDo, opsDoItem, invoice);
                            // 下发越库指令还是预约指令
                            if (isEnough) {
                                crossFlag = 2;
                                roConfirmRecTypeEnum = RoConfirmRecTypeEnum.YK;
                            }
                            if (isEnough) {
                                if (!doIds.contains(doId)) {
                                    // 带上批次属性
                                    this.sendDoToWms(doId, crossFlag, roId);
                                    doIds.add(doId);
                                    Map<Integer, String> roMap = new HashMap<>();
                                    roMap.put(2, roId);
                                    doYueKu.put(doId, roMap);
                                }
                            }
                        }
                    }
                    // 单行结束
                    if (!roIds.contains(roId)) {
                        // 返回富勒
                        RoConfirmItem roConfirmItem = new RoConfirmItem(roConfirmRecTypeEnum.getType(), roId, doId, null, invoice);
                        roConfirmItemList.add(roConfirmItem);
                        roIds.add(roId);
                        if (RoConfirmRecTypeEnum.INYY == roConfirmRecTypeEnum) {
                            Map<Integer, String> roMap = new HashMap<>();
                            roMap.put(1, roId);
                            doYueKu.put(doId, roMap);
                        }
                    }
                }// 以上处理1个在途行1个do占用 完毕
                // 1个在途行多个do占用（不等于1）,返回上库存
                else if (mapdo.size() > 1) {
                    if (!roIds.contains(roId)) {
                        // 上货架指令
                        RoConfirmRecTypeEnum roConfirmRecTypeEnum = RoConfirmRecTypeEnum.INZK;
                        RoConfirmItem roConfirmItem = new RoConfirmItem(roConfirmRecTypeEnum.getType(), roId, null, null, invoice);
                        roConfirmItemList.add(roConfirmItem);
                        roIds.add(roId);
                    }
                    // 判断越库
                    for (Map.Entry<String, List<OpsDoItemInventory>> doEntry : mapdo.entrySet()) {
                        String doId = doEntry.getKey();
                        OpsDo opsDo = baseDoService.findDoByDoId(doId);
                        if (null == opsDo) {
                            throw Exceptions.OpsException("无对应的opsDo:doId=>" + doId);
                        }
                        OpsDoItem opsDoItem = baseDoService.getDoItemByDoId(doId);
                        if (null == opsDoItem) {
                            throw Exceptions.OpsException("无对应的opsDoItem:doId=>" + doId);
                        }

                        boolean isReady = this.isEnoughToCrossForDo(opsDo, opsDoItem, invoice);
                        // 单DOID货齐，继续执行下面逻辑判断
                        if (isReady) {
                            // 按交货期客户(true 的需按do.wldate日期判断大于等于当天，判断信用拦截（判断越库），格式yyyy-MM-dd)
                            // 调拨出库(判断交货期，不判断信用拦截)
                            if (DoTypeEnum.DBCK.getType().equals(opsDo.getDoType())) {
                                if (!doIds.contains(doId)) {
                                    doIds.add(doId);
                                    this.sendDoToWms(doId, 0, "");
                                }
                            }
                            // 客户交易出库单
                            else if (DoTypeEnum.JYCK.getType().equals(opsDo.getDoType())) {
                                // 2单项分批随到发货(默认就越库)只判断信用拦截
                                if (CKTYPEEnum.ITEM_UNLIMIT.getCode().equals(opsDo.getDlvEntire())) {
                                    // 下发DO
                                    if (!doIds.contains(doId)) {
                                        doIds.add(doId);
                                        this.sendDoToWms(doId, 0, "");
                                    }
                                }
                                // 0单项单仓货齐发货(按10位数单号齐否判断)
                                else if (CKTYPEEnum.ITEM_GETHER_SIGNLE_HOUSE.getCode().equals(opsDo.getDlvEntire())) {
                                    List<OpsDo> doList = baseDoService.findDoListByOrder(opsDo.getOrderId(),
                                            opsDo.getOrderItem(), null, DoTypeEnum.JYCK);
                                    // 默认货齐（其中一条不齐就当不齐）
                                    isReady = true;
                                    for (OpsDo subdo : doList) {
                                        OpsDoItem subOpsDoItem = baseDoService.getDoItemByDoId(subdo.getDoId());
                                        // 判断货齐
                                        boolean isEnough = this.isEnoughToCrossForDo(subdo, subOpsDoItem, invoice);
                                        // 其中有一DO不货齐，则不满足越库
                                        if (!isEnough) {
                                            isReady = false;
                                        }
                                    }
                                    // 以上货齐判断
                                    if (isReady) {
                                        for (OpsDo subdo : doList) {
                                            if (!doIds.contains(subdo.getDoId())) {
                                                doIds.add(subdo.getDoId());
                                                this.sendDoToWms(subdo.getDoId(), 0, "");
                                            }
                                        }
                                    }
                                }
                                // 1-整单单仓货齐发货,3-整单多仓货齐发货 10位订单齐了才可以越库（如果越库需要七位数下发WMS）
                                else if (CKTYPEEnum.ORDER_GETHER_SIGNLE_HOUSE.getCode().equals(opsDo.getDlvEntire())
                                        || CKTYPEEnum.ORDER_GETHER_MULTI_HOUSE.getCode().equals(opsDo.getDlvEntire())) {
                                    boolean isEnough = true;
                                    // 越库，判断订单其他项是否已经越库
                                    List<OpsDo> doList = baseDoService.findDoListByOrder(opsDo.getOrderId(), null, null, DoTypeEnum.JYCK);
                                    // 货齐（按7位数）
                                    isEnough = this.isEnoughTocrossForFullOrder(doList, opsDo);
                                    if (isEnough) {
                                        // 下发DO （不考虑纳期和信用拦截）
                                        for (OpsDo opsSubDo : doList) {
                                            if (!doIds.contains(opsSubDo.getDoId())) {
                                                doIds.add(opsSubDo.getDoId());
                                                this.sendDoToWms(opsSubDo.getDoId(), 0, "");
                                            }
                                        }
                                    }
                                }
                            } else if (DoTypeEnum.CGDBCK.getType().equals(opsDo.getDoType())) {// 采购调拨出库 直接下发DO（预到货时已拆分DOID）
                                // 且越库（如分批来，前端已拆分DOID）
                                // 判断是否货齐
                                if (!doIds.contains(doId)) {
                                    doIds.add(doId);
                                    this.sendDoToWms(doId, 0, "");
                                }
                            }
                        }
                    }
                }
                // 以上处理DO 1个在途行1个do 1个在途行N个do占用,返回上库存结束
                // PCO是否越库条件 1个在途1个PCO
                else if (mappco.size() == 1) {
                    String pcoid = "";
                    Integer pcoItem = 0;
                    // 有且仅有1行PcoItemInventory
                    List<OpsPcoItemInventory> pcoItemInventories = new ArrayList<>();
                    // 有且仅有1行PCO
                    for (Map.Entry<String, List<OpsPcoItemInventory>> pcoEntry : mappco.entrySet()) {
                        pcoid = pcoEntry.getKey();
                        pcoItemInventories = pcoEntry.getValue();
                    }
                    if (CollectionUtils.isEmpty(pcoItemInventories)) {
                        throw Exceptions.OpsException("pcoItemInventories无数据：" + pcoid + "-" + String.valueOf(pcoItem));
                    }
                    OpsPco opsPco = opsPcoService.selectPcoBypcoId(pcoid);
                    if (null == opsPco) {
                        throw Exceptions.OpsException("opsPco无数据：" + pcoid);
                    }
                    List<OpsPcoItem> pcoItemList = opsPcoService.selectItemBypcoId(pcoid);
                    if (CollectionUtils.isEmpty(pcoItemList)) {
                        throw Exceptions.OpsException("OpsPcoItem无数据：" + pcoid);
                    }
                    // 预到货改标识符（子项到货）
                    pcoItem = pcoItemInventories.get(0).getPcoItem();
                    // PCO-pcoItemInventory 一行
                    OpsPcoItemInventory pcoItemInventory = pcoItemInventories.get(0);
                    opsPcoService.updatePcoItemIsCrossByPcoItem(pcoid, pcoItemInventory.getPcoItem(), true);
                    // 当前行PCOITEM
                    OpsPcoItem opsPcoItem = opsPcoService.getPcoItemByPcoIdAndItem(pcoid, pcoItem);
                    if (null == opsPcoItem) {
                        throw Exceptions.OpsException("OpsPcoItem无数据：" + pcoid + ".Item:" + String.valueOf(pcoItem));
                    }
                    // PCO越库与否取决于10位数订单是否全部到齐，齐了下发DO，PCO
                    List<OpsDo> doList = baseDoService.findDoListByOrder(opsPco.getOrderId(),
                            opsPco.getOrderItem(), null, DoTypeEnum.JYCK);
                    // pco orderId orderItem 对应只有1个DO
                    if (CollectionUtils.isEmpty(doList)) {
                        throw Exceptions.OpsException("无对应的交易出库单：" + opsPco.getOrderId() + "-" + String.valueOf(opsPco.getOrderItem()));
                    }
                    OpsDo opsDo = doList.get(0);
                    if (null == opsDo) {
                        throw Exceptions.OpsException("无对应opsDo：对应订单" + opsPco.getOrderId() + "-" + String.valueOf(opsPco.getOrderItem()));
                    }
                    String doId = opsDo.getDoId();
                    boolean isEnough = false;
                    // 是否货齐发货（当前行Item）
                    boolean ready = this.isEnoughTocrossForPcoItem(opsPcoItem, invoice);
                    // 当前行满足发货条件（不拦截，纳期符合）
                    if (ready) {
                        // 0-单项单仓货齐发货、2-单项分批随到发货 (订单号10位数判断)
                        if (CKTYPEEnum.ITEM_UNLIMIT.getCode().equals(opsDo.getDlvEntire())
                                || CKTYPEEnum.ITEM_GETHER_SIGNLE_HOUSE.getCode().equals(opsDo.getDlvEntire())) {
                            // 判断PCO其他子项是否都货齐（十位数）
                            isEnough = this.isEnoughTocrossForPcoId(opsDo, pcoItemList, opsPcoItem);
                            if (isEnough) {
                                // 纳期客户
                                boolean isUseWlDate = this.exitsCustmerWldateByCustomerNo(opsDo.getCustomerNo());
                                // 纳期客户
                                if (isUseWlDate) {
                                    // 符合纳期在判断信用拦截
                                    if (this.isArriveWlDate(opsDo.getWlDate())) {
                                        isEnough = this.checkCreditCross(opsDo.getOrderId(), opsDo.getOrderItem());
                                    } else {
                                        isEnough = false;
                                    }
                                } else {
                                    isEnough = this.checkCreditCross(opsDo.getOrderId(), opsDo.getOrderItem());
                                }
                                // 默认预约
                                RoConfirmRecTypeEnum roConfirmRecTypeEnum = RoConfirmRecTypeEnum.PCOYY;
                                // 货齐且不信用拦截，越库
                                if (isEnough) {
                                    roConfirmRecTypeEnum = RoConfirmRecTypeEnum.PCOYK;
                                }
                                Map<String, String> yukuMap = new HashMap<>();
                                for (OpsPcoItem subPcoItem : pcoItemList) {
                                    // 查外采购
                                    List<OpsPcoItemInventory> subPcoItemInventories = this.opsPcoService.findPcoItemInventoryByPcoIdAndItem(subPcoItem.getPcoId(), subPcoItem.getPcoItem());
                                    // PCO 是外部采购的才传lot13
                                    // 外部到货的PCO项号需要传PCOID，库存的不需要传
                                    Boolean isLot13 = false;
                                    for (OpsPcoItemInventory opsPcoItemInventory : subPcoItemInventories) {
                                        isLot13 = false;
                                        if (InventoryTableTypeEnum.TRANS.getType().equals(opsPcoItemInventory.getInventoryTableType())) {
                                            isLot13 = true;
                                        }
                                        if (isLot13) {
                                            yukuMap.put(subPcoItem.getSubModelno(), subPcoItem.getPcoId());
                                        }
                                    }
                                }
                                if (!roIds.contains(roId)) {
                                    // 单项单仓货齐发货越库（下发PCO do） 按DO order(按10位数) 批量DOID下发
                                    RoConfirmItem roConfirmItem = new RoConfirmItem(roConfirmRecTypeEnum.getType(),
                                            roId, opsDo.getDoId(), pcoid, invoice);
                                    roConfirmItemList.add(roConfirmItem);
                                }
                                doIds.add(doId);
                                // 预约的、越库的PCO带上YuKuMap，越库的YuKuRoId （不需要YuKuRoId）
                                OpsSendPcoAndDoMidIDDto pcoAndDoMidIDDto = new OpsSendPcoAndDoMidIDDto();
                                pcoAndDoMidIDDto.setDoId(opsDo.getDoId());
                                pcoAndDoMidIDDto.setPcoId(opsPco.getPcoId());
                                pcoAndDoMidIDDto.setYuKuMap(yukuMap);
                                // 以上最后一项货齐判断是否符合下发是否越库或预约
                                // 越库需带上此标记
                                if (isEnough) {
                                    pcoAndDoMidIDDto.setYuKuRoId(roId);
                                }
                                // 越库的时候带上自己这箱以及之前的外部先来货的
                                if (roConfirmRecTypeEnum == RoConfirmRecTypeEnum.PCOYK) {
                                    wmDoService.updateWMSPcoAddDoTwo(pcoAndDoMidIDDto);
                                    // bug:10269 C12961 2023-03-31
                                    // 延迟下发问题：pco延迟下发时，不在pco表中写ro_id
                                    // 最后一箱（也就是越库需更新）
                                    pcoYueKu.put(pcoid, roId);
                                    Map<Integer, String> roMap = new HashMap<>();
                                    roMap.put(2, roId);
                                    doYueKu.put(doId, roMap);
                                }
                            } else {
                                // PCOITEM没货齐不下发
                                if (!roIds.contains(roId)) {
                                    RoConfirmRecTypeEnum roConfirmRecTypeEnum = RoConfirmRecTypeEnum.PCOYY;
                                    // add BUG 8501 B28029
                                    RoConfirmItem roConfirmItem = new RoConfirmItem(roConfirmRecTypeEnum.getType(), roId, doId, pcoid, invoice);
                                    // end
                                    roConfirmItemList.add(roConfirmItem);
                                }
                            }
                        }
                        //  1-整单单仓货齐发货，3-整单多仓货齐发货，需要7位订单都齐才同时下发DO ,PCO 7位全部
                        // 1和3, 暂定上库存(2022-9-7)，后续改善后再判断。需要7位数订单内所有ITEM全齐，才一次下发
                        else if (CKTYPEEnum.ORDER_GETHER_SIGNLE_HOUSE.getCode().equals(opsDo.getDlvEntire())
                                || CKTYPEEnum.ORDER_GETHER_MULTI_HOUSE.getCode().equals(opsDo.getDlvEntire())) {
                            // 上库存INZK（且不下发DO,pco）
                            if (!roIds.contains(roId)) {
                                RoConfirmRecTypeEnum roConfirmRecTypeEnum = RoConfirmRecTypeEnum.PCOYY;
                                RoConfirmItem roConfirmItem = new RoConfirmItem(roConfirmRecTypeEnum.getType(), roId, opsDo.getDoId(), pcoid, invoice);
                                roConfirmItemList.add(roConfirmItem);
                            }
                        }
                    } else {// PCO （当前行Item） 货不齐，上预约
                        if (!roIds.contains(roId)) {
                            RoConfirmRecTypeEnum roConfirmRecTypeEnum = RoConfirmRecTypeEnum.PCOYY;
                            RoConfirmItem roConfirmItem = new RoConfirmItem(roConfirmRecTypeEnum.getType(), roId, opsDo.getDoId(), pcoid, invoice);
                            roConfirmItemList.add(roConfirmItem);
                        }
                    }
                }
                // 以上处理1个PCO
                // 1个在途多个多个PCO
                else if (mappco.size() > 1) {
                    // 提示返回上库存
                    RoConfirmRecTypeEnum roConfirmRecTypeEnum = RoConfirmRecTypeEnum.INZK;
                    RoConfirmItem roConfirmItem = new RoConfirmItem(roConfirmRecTypeEnum.getType(), roId, null, null, invoice);
                    roConfirmItemList.add(roConfirmItem);
                    // 执行判断能否下发DO,PCO指令
                    for (Map.Entry<String, List<OpsPcoItemInventory>> pcoEntry : mappco.entrySet()) {
                        String pcoid = pcoEntry.getKey();
                        Integer pcoItem = 0;
                        List<OpsPcoItemInventory> pcoItemInventories = pcoEntry.getValue();
                        if (CollectionUtils.isEmpty(pcoItemInventories)) {
                            throw Exceptions.OpsException("pcoItemInventories无数据：" + pcoid);
                        }
                        OpsPco opsPco = opsPcoService.selectPcoBypcoId(pcoid);
                        if (null == opsPco) {
                            throw Exceptions.OpsException("opsPco无数据：" + pcoid);
                        }
                        List<OpsPcoItem> pcoItemList = opsPcoService.selectItemBypcoId(pcoid);
                        if (CollectionUtils.isEmpty(pcoItemList)) {
                            throw Exceptions.OpsException("OpsPcoItem无数据：" + pcoid);
                        }
                        // 预到货改标识符（子项到货）
                        OpsPcoItemInventory pcoItemInventory = pcoItemInventories.get(0);
                        opsPcoService.updatePcoItemIsCrossByPcoItem(pcoid, pcoItemInventory.getPcoItem(), true);
                        pcoItem = pcoItemInventories.get(0).getPcoItem();
                        OpsPcoItem opsPcoItem = opsPcoService.getPcoItemByPcoIdAndItem(pcoid, pcoItem);
                        if (null == opsPcoItem) {
                            throw Exceptions.OpsException("opsPcoItem无数据：" + pcoid);
                        }
                        // 加工单可否可越库(单项DO的PCOITEM齐了)
                        boolean isEnough = this.isEnoughTocrossForPcoItem(opsPcoItem, invoice);
                        if (isEnough) {
                            // PCO越库与否取决于10位数订单是否全部到齐，齐了下发DO，PCO
                            List<OpsDo> doList = baseDoService.findDoListByOrder(opsPco.getOrderId(),
                                    opsPco.getOrderItem(), null, DoTypeEnum.JYCK);
                            // pco orderId orderItem 对应只有1个DO
                            if (CollectionUtils.isEmpty(doList)) {
                                throw Exceptions.OpsException("无对应的交易出库单：" + opsPco.getOrderId() + "-" + String.valueOf(opsPco.getOrderItem()));
                            }
                            OpsDo opsDo = doList.get(0);
                            if (null == opsDo) {
                                throw Exceptions.OpsException("无对应opsDo：对应订单" + opsPco.getOrderId() + "-" + String.valueOf(opsPco.getOrderItem()));
                            }
                            // 0-单项单仓货齐发货 or 2-单项分批随到发货(订单号10位数判断)
                            if (CKTYPEEnum.ITEM_GETHER_SIGNLE_HOUSE.getCode().equals(opsDo.getDlvEntire())
                                    || CKTYPEEnum.ITEM_UNLIMIT.getCode().equals(opsDo.getDlvEntire())) {
                                // todo 不越库不下发
                            }
                            // 1-整单单仓货齐发货，3-整单多仓货齐发货，需要7位订单都齐才同时下发DO ,PCO 7位全部
                            // 1和3, 暂定上库存(2022-9-7)，后续改善后再判断。需要7位数订单内所有ITEM全齐，才一次下发
                            else if (CKTYPEEnum.ORDER_GETHER_SIGNLE_HOUSE.getCode().equals(opsDo.getDlvEntire())
                                    || CKTYPEEnum.ORDER_GETHER_MULTI_HOUSE.getCode().equals(opsDo.getDlvEntire())) {
                                // 上库存INZK（且不下发DO,pco）
                            }
                        }
                    }
                }
                // 以上执行完1单po在途行数DO-PCO判断下发
                if (mapdo.size() == 0 && mappco.size() == 0) {
                    // 无DO/PCO数据只能上库存INZK（且不下发DO）
                    // 补库采购入库收货
                    RoConfirmRecTypeEnum roConfirmRecTypeEnum = RoConfirmRecTypeEnum.INZK;
                    RoConfirmItem roConfirmItem = new RoConfirmItem(roConfirmRecTypeEnum.getType(), roId, null, null, invoice);
                    roConfirmItemList.add(roConfirmItem);
                    continue;
                }
            }
            // 以上单行po在途行数 inventoryMoves.sise=1 执行结束
            else if (inventoryMoves.size() > 1) {
                // 大于1 都必走上货架INZK
                // PCOID，PcoItemInventory(合并采购)
                Map<String, List<OpsPcoItemInventory>> mappcoMerge = new HashMap<>();
                // 关联DO(上库存)
                for (Map.Entry<String, List<OpsDoItemInventory>> doEntry : mapdo.entrySet()) {
                    // 下发DO（越库）
                    String doId = doEntry.getKey();
                    OpsDo opsDo = baseDoService.findDoByDoId(doId);
                    if (null == opsDo) {
                        throw Exceptions.OpsException("无DOID：" + doId);
                    }
                    // 一个DO对应一个ITEM
                    OpsDoItem opsDoItem = baseDoService.getDoItemByDoId(doId);
                    if (null == opsDoItem) {
                        throw Exceptions.OpsException("无opsDoItem：" + doId);
                    }
                    // 是否货齐
                    boolean isEnough = isEnoughToCrossForDo(opsDo, opsDoItem, invoice);
                    if (isEnough) {
                        // 调拨出库(判断交货期，不判断信用拦截)
                        if (DoTypeEnum.DBCK.getType().equals(opsDo.getDoType())) {
                            if (!doIds.contains(doId)) {
                                doIds.add(doId);
                                this.sendDoToWms(doId, 0, "");
                            }
                        }
                        // 客户交易出库单
                        else if (DoTypeEnum.JYCK.getType().equals(opsDo.getDoType())) {
                            // 2单项分批随到发货,默认下发
                            if (CKTYPEEnum.ITEM_UNLIMIT.getCode().equals(opsDo.getDlvEntire())) {
                                // 下发WMS-DO
                                if (!doIds.contains(doId)) {
                                    doIds.add(doId);
                                    this.sendDoToWms(doId, 0, "");
                                }
                            }
                            // 0单项单仓货齐发货(按10位数单号齐否判断)
                            else if (CKTYPEEnum.ITEM_GETHER_SIGNLE_HOUSE.getCode().equals(opsDo.getDlvEntire())) {
                                List<OpsDo> doList = baseDoService.findDoListByOrder(opsDo.getOrderId(),
                                        opsDo.getOrderItem(), null, DoTypeEnum.JYCK);
                                // 子项满足条件默认获齐全
                                boolean itemEnough = true;
                                for (OpsDo subdo : doList) {
                                    OpsDoItem subOpsDoItem = baseDoService.getDoItemByDoId(doId);
                                    if (null == opsDoItem) {
                                        throw Exceptions.OpsException("无对应的opsDoItem:doId=>" + doId);
                                    }
                                    // 是否齐
                                    isEnough = this.isEnoughToCrossForDo(subdo, subOpsDoItem, invoice);
                                    // 其中有一DO不货齐，则不满足下发
                                    if (!isEnough) {
                                        itemEnough = false;
                                    }
                                }
                                // 满足下发DO条件
                                if (itemEnough) {
                                    for (OpsDo subdo : doList) {
                                        // do 按订单10位一起下发
                                        if (!doIds.contains(subdo.getDoId())) {
                                            doIds.add(subdo.getDoId());
                                            this.sendDoToWms(subdo.getDoId(), 0, roId);
                                        }
                                    }
                                }
                            }
                            // 1-整单单仓货齐发货,3-整单多仓货齐发货 7位订单齐了才可以越库（如果越库需要七位数下发WMS）
                            else if (CKTYPEEnum.ORDER_GETHER_SIGNLE_HOUSE.getCode().equals(opsDo.getDlvEntire())
                                    || CKTYPEEnum.ORDER_GETHER_MULTI_HOUSE.getCode().equals(opsDo.getDlvEntire())) {
                                // 默认货齐（判断有其中一项不齐就退出）
                                isEnough = true;
                                // 七位订单号判断
                                List<OpsDo> doList = baseDoService.findDoListByOrder(opsDo.getOrderId(), null, null, DoTypeEnum.JYCK);
                                // 判断货齐按七位数
                                isEnough = this.isEnoughTocrossForFullOrder(doList, opsDo);
                                if (isEnough) {
                                    // 按7位数订单下发
                                    for (OpsDo opsDoSub : doList) {
                                        // 下发WMS-DO
                                        if (!doIds.contains(opsDoSub.getDoId())) {
                                            doIds.add(opsDoSub.getDoId());
                                            this.sendDoToWms(opsDoSub.getDoId(), 0, roId);
                                        }
                                    }
                                }
                            }
                        } else if (DoTypeEnum.CGDBCK.getType().equals(opsDo.getDoType())) {// 采购调拨出库 直接下发DO（预到货时已拆分DOID）
                            // 如分批来，前端已拆分DOID
                            // 收齐才下发DO
                            if (!doIds.contains(doId)) {
                                doIds.add(doId);
                                this.sendDoToWms(doId, 0, roId);
                            }
                        }
                    }
                }
                // 关联PCO
                // Pco不越库不下发
                // 返回上货架库存
                RoConfirmRecTypeEnum roConfirmRecTypeEnum = RoConfirmRecTypeEnum.INZK;
                RoConfirmItem roConfirmItem = new RoConfirmItem(roConfirmRecTypeEnum.getType(), roId, null, null, invoice);
                roConfirmItemList.add(roConfirmItem);
                roIds.add(roId);
                continue;
            }
        }
        // 以上PO遍历结束
        if (CollectionUtils.isEmpty(roConfirmItemList)) {
            throw Exceptions.OpsException("无到货确认" + invoice, invoice, null, null);
        }
        // do越库更新 key:Doid, value<1预约2越库,roid>
        for (Map.Entry<String, Map<Integer, String>> doEntry : doYueKu.entrySet()) {
            String doId = doEntry.getKey();
            for (Map.Entry<Integer, String> doTypeEntry : doEntry.getValue().entrySet()) {
                Integer crossType = doTypeEntry.getKey();
                String roId = doTypeEntry.getValue();
                opsDoService.updateOpsDoForCrossRoId(doId, crossType, roId);
            }
        }
        // 更新PCO，RO字段
        for (Map.Entry<String, String> pcoEntry : pcoYueKu.entrySet()) {
            String pcoId = pcoEntry.getKey();
            String roId = pcoEntry.getValue();
            opsPcoService.updateOpsPcoForCrossRoId(pcoId, roId);
        }
        // 操作状态改发票确认
        baseInventoryService.updateInventoryMoveOptStatusByInvoiceNo(invoice, invoiceId, InventoryMoveOpsStatusEnum.INVONICE_GOODCONFIRM);
        ImpInvoiceEventLog eventLog = new ImpInvoiceEventLog();
        eventLog.setOpType("/order/confirmgoods");
        eventLog.setRequestParam(invoice);
        eventLog.setOpStartTime(fromDate);
        eventLog.setReturnData(JSON.toJSONString(roConfirmItemList));
        this.addImpInvoiceEventLog(eventLog);

        // 写入到货确认返回结果
        List<OpsRoConfirmLog> confirmLogs = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSSS");
        String batchNo = String.format("%s%s", warehouse, sdf.format(System.currentTimeMillis()));
        for (RoConfirmItem roConfirmItem : roConfirmItemList) {
            OpsRoConfirmLog confirmLog = new OpsRoConfirmLog();
            confirmLog.setBatchNo(batchNo);
            confirmLog.setRoId(roConfirmItem.getRoId());
            confirmLog.setDoId(roConfirmItem.getDoid());
            confirmLog.setReceiveType(roConfirmItem.getReceiveType());
            confirmLog.setPcoId(roConfirmItem.getPcoid());
            confirmLog.setInvoiceNo(roConfirmItem.getInvoiceNo());
            confirmLog.setStatus(1);
            confirmLog.setCreator(UserDto.WMS.getUserName());
            confirmLog.setCreTime(new Date());
            confirmLogs.add(confirmLog);
        }
        roConfirmLogService.insertBatchConfirmLog(confirmLogs);
        return roConfirmItemList;
    }

*/

}
