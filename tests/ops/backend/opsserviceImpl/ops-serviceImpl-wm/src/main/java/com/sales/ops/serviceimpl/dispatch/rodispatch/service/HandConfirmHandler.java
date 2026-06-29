package com.sales.ops.serviceimpl.dispatch.rodispatch.service;

import cn.hutool.json.JSONUtil;
import com.sales.ops.common.opsexception.Exceptions;
import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.db.dao.OpsRoItemInventoryMapper;
import com.sales.ops.db.dao.OpsRoItemMapper;
import com.sales.ops.db.dao.OpsRoMapper;
import com.sales.ops.db.dao.OpsWmOrderTaskMapper;
import com.sales.ops.db.entity.*;
import com.sales.ops.dto.flux.HandConfirm;
import com.sales.ops.dto.flux.HandItem;
import com.sales.ops.dto.inventory.CreDoByInventoryDto;
import com.sales.ops.dto.inventory.OpsDoDto;
import com.sales.ops.dto.inventory.OpsPcoItemDto;
import com.sales.ops.dto.inventory.OpsRoDto;
import com.sales.ops.dto.replacement.NotifyShipmentCondition;
import com.sales.ops.dto.util.CreateInfoDto;
import com.sales.ops.dto.util.OrderNoInfo;
import com.sales.ops.dto.util.UserDto;
import com.sales.ops.enums.*;
import com.sales.ops.event.publisher.enums.EventSourceEnum;
import com.sales.ops.service.ba.BaseWarehouseService;
import com.sales.ops.service.inventory.OpsInventoryPropertyService;
import com.sales.ops.service.log.OpsHandOverService;
import com.sales.ops.service.log.OpsRoBarcodeService;
import com.sales.ops.service.order.TransOrderService;
import com.sales.ops.service.wmOrder.*;
import com.sales.ops.serviceimpl.dispatch.rodispatch.domain.HandConfirmContext;
import com.sales.ops.serviceimpl.dispatch.rodispatch.domain.HandConfirmContextItem;
import com.sales.ops.serviceimpl.event.v3.CustomerEventPublisher;
import com.sales.ops.serviceimpl.inventory.BaseInventoryService;
import com.sales.ops.serviceimpl.inventory.factory.InventoryFactory;
import com.sales.ops.serviceimpl.inventory.factory.PropertyFactory;
import com.sales.ops.utils.WmOrderNoFactory;
import com.sales.ops.utils.WmTaskFactory;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.model.csstock.CsImportDataDTO;
import com.smc.smccloud.service.ConsignmentStockFeignApi;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.sales.ops.event.publisher.enums.EventSourceEnum.WAREHOUSE_DELIVERY_HANDOVER;

/**
 * @author C12961
 * @date 2023/4/17 16:44
 */
@Slf4j
@Service
public class HandConfirmHandler {
    @Autowired
    private OpsRoMapper opsRoMapper;
    @Autowired
    private OpsRoItemMapper opsRoItemMapper;
    @Autowired
    private OpsRoItemInventoryMapper opsRoItemInventoryMapper;
    @Autowired
    private OpsWmOrderTaskMapper opsWmOrderTaskMapper;
    @Autowired
    private BaseWarehouseService baseWarehouseService;
    @Autowired
    private BaseInventoryService baseInventoryService;
    @Autowired
    private BaseDoService baseDoService;
    @Autowired
    private BaseRoService baseRoService;
    @Autowired
    private OpsInventoryPropertyService opsInventoryPropertyService;
    @Autowired
    private OpsDoService opsDoService;
    @Autowired
    private OpsRoService opsRoService;
    @Autowired
    private OpsPcoService opsPcoService;
    @Autowired
    private WmOrderTaskService wmOrderTaskService;
    @Autowired
    private TransOrderService transOrderService;
    @Autowired
    private ConsignmentStockFeignApi consignmentStockFeignApi;
    @Autowired
    private OpsHandOverService opsHandOverService;
    @Autowired
    private OpsRoBarcodeService OpsRoBarcodeService;
    @Autowired
    private DoPcoLogicCenterService doPcoLogicCenterService;
    @Autowired
    private CustomerEventPublisher customerEventPublisher;


    /**
     * @description 出一个发票调用一次此接口
     * 一次发票会出多个do,并且一个do会分多次发票出完
     * @author C12961
     * @date 2023/5/9 9:13
     */
    public void handConfirm(HandConfirm handConfirm) throws OpsException {
        // 数据异常检测
        checkParamsForHandConfirm(handConfirm);
        // 初始化上下文
        HandConfirmContext context = initContext(handConfirm);
        // 开始执行
        handle(context);
        // 写入委托在库收货表ImpData
        insertImpDatasForHandConfirm(context.getCsImportDataDTOS());
        // 写入箱码信息表ops_ro_barcode
        insertBarCodeForHandConfirm(context);
        // 写入物流发票交接信息ops_handover
        insertHandOverForHandConfirm(handConfirm, context.getInvoiceId());
        log.info("context: {}", JSONUtil.toJsonPrettyStr(context));
    }

    private void checkParamsForHandConfirm(HandConfirm handConfirm) throws OpsException {
        log.info("接口：HandConfirm \n{}", JSONUtil.toJsonPrettyStr(handConfirm));
        if (Objects.isNull(handConfirm)) {
            throw Exceptions.OpsException("参数解析失败--handconfirm");
        }
        if (StringUtils.isEmpty(handConfirm.getInvoice())) {
            throw Exceptions.OpsException("发票号不可空。");
        }
        if (CollectionUtils.isEmpty(handConfirm.getHandlist())) {
            throw Exceptions.OpsException("发票清单不可空。");
        }
        for (HandItem item : handConfirm.getHandlist()) {
            if (item.getQty() < 1) {
                throw Exceptions.OpsException(handConfirm.getInvoice() + "，请求调拨出库发票数据项数量不可小于1,发票号：" + handConfirm.getInvoice());
            }
        }
        // 已导入过不重新导入
        List<OpsHandover> opsHandoversList = opsHandOverService.findHandOvers(handConfirm.getInvoice());
        if (!CollectionUtils.isEmpty(opsHandoversList)) {
            throw Exceptions.OpsException(handConfirm.getInvoice() + "，已导入发票号,不可重复导入。");
        }
        if (StringUtils.isEmpty(handConfirm.getToWarehouseCode()) ||
                StringUtils.isEmpty(handConfirm.getFromWarehouseCode())) {
            throw Exceptions.OpsException("仓库号不可空。");
        }
    }

    private HandConfirmContext initContext(HandConfirm handConfirm) throws OpsException {
        HandConfirmContext context = new HandConfirmContext(handConfirm);
        for (HandConfirmContextItem item : context.getItems()) {
            // 查询调拨出库单 根据调拨DoId
            OpsDoDto opsDoDto = getDoAndItemForHandConfirm(item.getDoId(), context.getInvoiceNo(), context.getInvoiceId());
            // 查询调拨入库单 根据调拨出库单13位单号
            OpsRoDto opsRoDto = getRoAndItemForHandConfirm(opsDoDto.getOpsDo(), opsDoDto.getDoItem(), item.getHandQty(), context.getInvoiceNo());
            item.setDoAndRo(opsDoDto, opsRoDto);
        }
        OpsWarehouse warehouse = baseWarehouseService.getWarehouseByCode(context.getItems().get(0).getDBCK().getGatherWarehouseCode());
        context.setTargetWarehouse(warehouse);
        return context;
    }

    private void handle(HandConfirmContext context) throws OpsException {
        String invoiceNo = context.getInvoiceNo();
        Long invoiceId = context.getInvoiceId();
        //一个item代表一个调拨doId，handQty代表一个调拨单的本次handover数量
        for (HandConfirmContextItem item : context.getItems()) {
            // 更新ro发票号和roItem数量,以实际调拨的数量为准
            updateRoQtyForRoConfirm(item, invoiceNo, invoiceId);
            // 创建调拨在途库存，预约数设默认值0
            createTransInventoryForHandConfirm(item, invoiceNo, invoiceId);
            // 创建调拨入库单的关联关系，调拨入库单的ro和roItemInventory是一对一的
            for (OpsInventoryMove move : item.getMoveList()) {
                log.info(JSONUtil.toJsonStr(move));
                createRoItemInventory(item.getRoId(), move.getInventoryId(), move.getQuantity());
            }
            // 如果是客户单的调拨出库，创建交易出库单的关联关系
            if (DoTypeEnum.DBCK.getType().equals(item.getDBCK().getDoType())) {
                OpsDo dbck = item.getDBCK();
                // do是调拨出库单按14位订单号去找交易出库单
                OpsDo jyck = baseDoService.findJYCKByOrder(dbck.getOrderId(), dbck.getOrderItem(), dbck.getNum(), DoSourceEnum.parse(dbck.getDoSource()));
                if (jyck != null) {
                    // 创建关联关系，包括随发分批拆分do,并修改move预占数量
                    createDoOrPcoItemInventory(dbck, jyck, item.getMoveList());
                    context.getJycks().add(jyck);
                } else {
                    // 强制完纳了，或数据异常调拨单没有交易出库单
                    log.info("此调拨出库单没有绑定交易出库单：{},{}", dbck.getDoId(), JSONUtil.toJsonPrettyStr(dbck));
                }
            }
            // 主动调库更新发票号（运单号）,发运日期，发运数量
            else if (DoTypeEnum.TKCK.getType().equals(item.getDBCK().getDoType())) {
                TransOrder transOrder = transOrderService.findTransOrder(item.getDBCK().getOrderId(), Integer.valueOf(item.getDBCK().getOrderItem()));
                transOrderService.updateTransOrderShipById(transOrder.getId(), invoiceNo, invoiceId,
                        transOrder.getShipQty() + item.getHandQty(), new Date());
                // 异仓调库需要创建ro指令
                if (!StringUtils.equals(item.getDBCK().getGatherWarehouseCode(), item.getDBCK().getWarehouseCode())) {
                    // bug14149 在调库创建task时，要先检测是否已经创建
                    // 实际上只有本调库单第一次handconfirm时才会走到这，因为后续的分纳handconfirm都是在拆分ro时创建的
                    Integer taskFlag = wmOrderTaskService.getTaskFlag(item.getRoId());
                    if (taskFlag == null) {
                        wmOrderTaskService.addRoTask(item.getRoId());
                    }
                }
                // 如果目的仓是委托在库
                if (context.isWT()) {
                    handleWTForRoConfirm(context, item);
                }
            }
            // 采购调拨出库并且目的仓是委托在库
            else if (DoTypeEnum.CGDBCK.getType().equals(item.getDBCK().getDoType())) {
                if (context.isWT()) {
                    handleWTForRoConfirm(context, item);
                }
                for (OpsInventoryMove move : item.getMoveList()) {
                    // 调拨出的时候要强制加满预约数
                    baseInventoryService.UpdatePreQtyInventory(move.getInventoryId(), move.getQuantity(), InventoryTableTypeEnum.TRANS.getType(),
                            new UserDto("handover"));
                }
            }
            // 写入ROID 准备下发ROID
            context.addRoId(item.getRoId());
        }

        // 批量下发task
        updateTasksForHandConfirm(context.getRoIds());
        //调用通知发货检测
        for (OpsDo jyck : context.getJycks()) {
            NotifyShipmentCondition notify = new NotifyShipmentCondition(jyck, "handConfirm");
            doPcoLogicCenterService.splitOrderByDlvEntry(notify);
        }
    }

    private void insertBarCodeForHandConfirm(HandConfirmContext context) {
        List<OpsRoBarcode> barcodes = new ArrayList<>();
        for (HandConfirmContextItem item : context.getItems()) {
            for (HandItem handItem : item.getHandItems()) {
                OpsRo opsRo = item.getDBRK();
                OpsRoItem opsRoItem = item.getDBRKItem();
                String gatherWarehouseCode = context.getWarehouseCode();
                OpsRoBarcode opsRoBarcode = new OpsRoBarcode();
                opsRoBarcode.setInvoiceno(context.getInvoiceNo());
                opsRoBarcode.setInvoiceid(context.getInvoiceId());
                opsRoBarcode.setRoId(opsRo.getRoId());
                opsRoBarcode.setWarehouseCode(gatherWarehouseCode);// 目的仓
                opsRoBarcode.setBarcode(handItem.getBarcode());
                opsRoBarcode.setPackageCode(handItem.getCaseno());
                opsRoBarcode.setModelno(handItem.getModelno());
                opsRoBarcode.setQty(handItem.getQty());
                opsRoBarcode.setDelflag(0);
                opsRoBarcode.setCreTime(new Date());
                opsRoBarcode.setCreator(UserDto.WMS.getUserName());
                opsRoBarcode.setOrderno(opsRo.getOrderId());
                opsRoBarcode.setItemno(Integer.valueOf(opsRo.getOrderItem()));
                // opsRoBarcode.setSplititemno(null == opsRo.getNum() ? 0 : Integer.valueOf(opsRo.getNum()));
                opsRoBarcode.setNum(opsRo.getNum());
                opsRoBarcode.setAssNum(opsRo.getAssNum());
                opsRoBarcode.setRoItem(opsRoItem.getRoItem());
                opsRoBarcode.setScan(1);
                barcodes.add(opsRoBarcode);
            }
        }
        OpsRoBarcodeService.insertBatchBarcode(barcodes);
    }

    private void insertImpDatasForHandConfirm(List<CsImportDataDTO> csImportDataDTOS) throws OpsException {
        if (CollectionUtils.isNotEmpty(csImportDataDTOS)) {
            ResultVo<String> resultVo = consignmentStockFeignApi.addImpData(csImportDataDTOS);
            if (!resultVo.isSuccess()) {
                throw Exceptions.OpsException("调用写入委托在库表错误consignmentStockFeignApi.addImpData：" + resultVo.getMessage());
            }
        }
    }

    private void insertHandOverForHandConfirm(HandConfirm handConfirm, Long invoiceId) throws OpsException {
        List<OpsHandover> opsHandovers = new ArrayList<>();
        for (HandItem item : handConfirm.getHandlist()) {
            OpsHandover opsHandover = createHandoverForHandConfirm(handConfirm, item);
            opsHandover.setInvoiceId(invoiceId);
            opsHandovers.add(opsHandover);
        }
        if (CollectionUtils.isEmpty(opsHandovers)) {
            throw Exceptions.OpsException("发票清单不可空。");
        }
        opsHandOverService.addHandOver(opsHandovers);
    }

    private void handleWTForRoConfirm(HandConfirmContext context, HandConfirmContextItem item) {
        for (HandItem handItem : item.getHandItems()) {
            CsImportDataDTO importData = createImportData(handItem, item.getDBCK(), item.getDBCKItem(), item.getDBRK(),
                    context.getTargetWarehouse(), context.getInvoiceNo(), context.getInvoiceId());
            context.addImportData(importData);
        }
    }

    private void createTransInventoryForHandConfirm(HandConfirmContextItem item, String invoiceNo, Long invoiceId) throws OpsException {
        // 调库单，查询TransOrder的toPropertyId
        if (DoTypeEnum.TKCK.getType().equals(item.getDBCK().getDoType())) {
            // 创建调拨在途库存，预约数设默认值0
            long inventoryPropertyId = getPropertyIdFromTransOrder(item.getDBCK());
            OpsInventoryMove transInventory = InventoryFactory.transferInventory(
                    item.getDBCK(), item.getDBCKItem(), inventoryPropertyId, item.getHandQty(), invoiceNo, invoiceId);
            Long inventoryId = baseInventoryService.createInvMoveReturnId(transInventory, UserDto.WMS);
            OpsInventoryMove move = baseInventoryService.getInventoryMoveById(inventoryId);
            item.addMove(move);
        }
        // 调拨出库
        /** bug17549 2025-05-15为了防止doConfirm的库存id和数量与handConfirm的库存Id和数量对不上的问题，采用id顺序出库原则，
         调拨出库时，如果有分纳出的情况，按照order by inventory_table_type asc, inventory_id asc的顺序出库，
         HandConfirm会查询出N的dbItemInventory，按照inventory_id 顺序排序,然后再匹配T的doItemInventory,顺序排序，
         然后按照ops_handover表的已handover数跳过一定数量的库存，
         然后再根据后续的dbItemInventory的N库存属性，生成一定数量的moveT3
        */
        else if (DoTypeEnum.DBCK.getType().equals(item.getDBCK().getDoType()) || DoTypeEnum.CGDBCK.getType().equals(item.getDBCK().getDoType())) {
            String doId = item.getDBCK().getDoId();
            //调拨单本次要调拨的总数量
            int dbTargetQty = item.getHandQty();
            List<OpsHandover> handOvers = opsHandOverService.findHandOvers(doId, null);
            //需要跳过的历史已handover数量
            int historyHandedQty = 0;
            for (OpsHandover handOver : handOvers) {
                Integer qty = Optional.ofNullable(handOver.getQty()).orElse(0);
                historyHandedQty += qty;
            }
            // 查询在库库存
            List<OpsDoItemInventory> opsDoItemInventories = baseDoService.getDoItemInventoryByDoId(doId, InventoryTableTypeEnum.NORMAL);
            opsDoItemInventories.sort(Comparator.comparing(OpsDoItemInventory::getInventoryId));
            if (CollectionUtils.isNotEmpty(opsDoItemInventories)) {
                for (OpsDoItemInventory opsDoItemInventory : opsDoItemInventories) {
                    //如果要handover的数量已经为0，则跳出循环
                    if(dbTargetQty==0){
                        break;
                    }
                    //调拨单已出的数量
                    Integer outQty = Optional.ofNullable(opsDoItemInventory.getOutQty()).orElse(0);
                    if (outQty <= 0) {
                        continue;
                    }
                    //先跳过已handover的数量
                    if (outQty <= historyHandedQty) {
                        historyHandedQty -= outQty;
                        continue;
                    }
                    if (outQty > historyHandedQty) {
                        outQty -= historyHandedQty;
                        historyHandedQty = 0;
                    }
                    OpsInventory opsInventory = baseInventoryService.getInventoryById(opsDoItemInventory.getInventoryId());
                    if (Objects.isNull(opsInventory)) {
                        throw Exceptions.OpsException(doId + "，opsDoItemInventories,无在库表ID数据：" + opsDoItemInventories.get(0).getInventoryId());
                    }
                    // 本doItemInv实际能调拨hand的数量，生成move的数量
                    int qty = dbTargetQty > outQty ? outQty : dbTargetQty;
                    dbTargetQty -= qty;
                    OpsInventoryMove transInventory = InventoryFactory.transferInventory(
                            item.getDBCK(), item.getDBCKItem(), opsInventory.getInventoryPropertyId(), qty, invoiceNo, invoiceId);
                    Long inventoryId = baseInventoryService.createInvMoveReturnId(transInventory, UserDto.WMS);
                    OpsInventoryMove move = baseInventoryService.getInventoryMoveById(inventoryId);
                    item.addMove(move);
                }
            }
            if (dbTargetQty > 0) {
                // 如果不存在在库关联关系，可能是因为时间差，调拨发货仓先出后入了，
                // 所以取T行 如T行也没，报错不处理
                opsDoItemInventories = baseDoService.getDoItemInventoryByDoId(doId, InventoryTableTypeEnum.TRANS);
                // 无opsDoItemInventories数据
                if (CollectionUtils.isEmpty(opsDoItemInventories)) {
                    throw Exceptions.OpsException(doId + "，无opsDoItemInventories数据");
                }
                opsDoItemInventories.sort(Comparator.comparing(OpsDoItemInventory::getInventoryId));
                for (OpsDoItemInventory opsDoItemInventory : opsDoItemInventories) {
                    if(dbTargetQty==0){
                        break;
                    }
                    OpsInventoryMove opsInventoryMove = baseInventoryService.getInventoryMoveById(opsDoItemInventory.getInventoryId());
                    if (Objects.isNull(opsInventoryMove)) {
                        throw Exceptions.OpsException(doId + "，opsDoItemInventories,无在途表ID数据：" + opsDoItemInventory.getInventoryId());
                    }
                    Integer outQty = Optional.ofNullable(opsDoItemInventory.getOutQty()).orElse(0);
                    //先跳过已handover的数量
                    if (outQty <= historyHandedQty) {
                        historyHandedQty -= outQty;
                        continue;
                    }
                    if (outQty > historyHandedQty) {
                        outQty -= historyHandedQty;
                        historyHandedQty = 0;
                    }
                    //本doItemInv实际能调拨hand的数量，生成move的数量
                    int qty = dbTargetQty > outQty ? outQty : dbTargetQty;
                    dbTargetQty -= qty;
                    OpsInventoryMove transInventory = InventoryFactory.transferInventory(
                            item.getDBCK(), item.getDBCKItem(), opsInventoryMove.getInventoryPropertyId(), qty, invoiceNo, invoiceId);
                    Long inventoryId = baseInventoryService.createInvMoveReturnId(transInventory, UserDto.WMS);
                    OpsInventoryMove move = baseInventoryService.getInventoryMoveById(inventoryId);
                    item.addMove(move);
                }
            }
        }
    }

    private long getPropertyId(OpsDo opsDo) throws OpsException {
        // 调库单，查询TransOrder的toPropertyId
        if (DoTypeEnum.TKCK.getType().equals(opsDo.getDoType())) {
            return getPropertyIdFromTransOrder(opsDo);
        }
        // 调拨出库
        else if (DoTypeEnum.DBCK.getType().equals(opsDo.getDoType()) || DoTypeEnum.CGDBCK.getType().equals(opsDo.getDoType())) {
            return getPropertyIdFromDBCKorCGDBCK(opsDo);
        }
        // 其他则返回通用
        else {
            // throw Exceptions.OpsException("无法识别调拨类型:" + opsDo.getDoId());
            return 1L;
        }
    }

    private void updateRoQtyForRoConfirm(HandConfirmContextItem item, String invoiceNo, Long invoiceId) throws OpsException {
        // 如果收货数量大于调拨的数量，则拆分ro，意思是，有几个发票号就有几个ro
        OpsRoItem opsRoItem = item.getDBRKItem();
        int qty = item.getHandQty();
        if (opsRoItem.getQty() > qty) {
            String remark = "实际调拨数小于原调拨数，调拨入库原数量" + opsRoItem.getQty() + ",被拆分出" + qty;
            //原ro,传入要改成的数量
            updateQtyForRoItem(opsRoItem, opsRoItem.getQty() - qty, remark);
            //创建新ro,传入要创建的数量
            OpsRoDto opsRoDto = splitRoAndItemAndTaskFromRo(item.getDBRK(), opsRoItem, qty);
            item.setRo(opsRoDto);
        }
        // 如果收货数量小于调拨的数量，则更新收货数
        else if (opsRoItem.getQty() < qty) {
            updateQtyForRoItem(opsRoItem, qty, "实际调拨数大于原调拨数");
        }
        // 不论数量如何，都要更新发票号
        updateInvoiceNoForRo(item.getDBRK(), invoiceNo, invoiceId);
    }

    private CsImportDataDTO createImportData(HandItem handItem, OpsDo opsDo, OpsDoItem opsDoItem, OpsRo opsRo,
                                             OpsWarehouse opsCsWarehouse, String invoiceNo, Long invoiceId) {
        CsImportDataDTO csImportDataDTO = new CsImportDataDTO();
        csImportDataDTO.setAgentNo(opsCsWarehouse.getCustomerNo());
        csImportDataDTO.setBarcode(handItem.getBarcode());
        csImportDataDTO.setCaseNo("");
        csImportDataDTO.setCreateUser(UserDto.WMS.getUserName());
        csImportDataDTO.setImpType(1);
        csImportDataDTO.setOrderNo(opsDo.getOrderId() + "-" + opsDo.getOrderItem());
        csImportDataDTO.setStatus(1);
        csImportDataDTO.setInvoiceNo(invoiceNo);
        csImportDataDTO.setInvoiceId(invoiceId);
        csImportDataDTO.setModelNo(opsDoItem.getModelno());
        csImportDataDTO.setWarehouseCode(opsCsWarehouse.getWarehouseCode());
        csImportDataDTO.setQuantity(handItem.getQty());
        csImportDataDTO.setImpDate(new Date());
        csImportDataDTO.setDeliveryNo(invoiceNo);
        csImportDataDTO.setShipDate(new Date());
        csImportDataDTO.setRoId(opsRo.getRoId());
        return csImportDataDTO;
    }

    private int createDoOrPcoItemInventory(OpsDo dbck, OpsDo jyck, List<OpsInventoryMove> moveList) throws OpsException {
        int dbQty = 0;
        for (OpsInventoryMove move : moveList) {
            dbQty += move.getQuantity();
        }
        if (!DoSourceEnum.ASSModelNo.getType().equals(jyck.getDoSource())) {
            int jyckNeedQty = getNeedQty(jyck.getDoId());
            // 如果交易出库不缺关联关系了，直接退出
            if (jyckNeedQty <= 0) {
                return 0;
            }
            // 如果jyck需要的数量比调拨出的少，则绑定需要的关联关系
            else if (jyckNeedQty <= dbQty) {
                // 写入ops_do_item_inventory
                if (moveList.size() == 1) {
                    Long inventoryId = moveList.get(0).getInventoryId();
                    OpsDoItemInventory doItemInventory = createTransDoItemInventory(jyck.getDoId(), inventoryId, jyckNeedQty);
                    opsDoService.insertDoItemInventory(doItemInventory);
                    baseInventoryService.UpdatePreQtyInventory(inventoryId, jyckNeedQty, InventoryTableTypeEnum.TRANS.getType(),
                            new UserDto("handover"));
                }
                if (moveList.size() > 1) {
                    moveList.sort(Comparator.comparing(OpsInventoryMove::getQuantity).reversed());
                    for (OpsInventoryMove move : moveList) {
                        //          10 < 12+1
                        //          10 < 5+8
                        int qty = jyckNeedQty < move.getQuantity() ? jyckNeedQty : move.getQuantity();
                        if (qty > 0) {
                            OpsDoItemInventory doItemInventory = createTransDoItemInventory(jyck.getDoId(), move.getInventoryId(), qty);
                            opsDoService.insertDoItemInventory(doItemInventory);
                            baseInventoryService.UpdatePreQtyInventory(move.getInventoryId(), qty, InventoryTableTypeEnum.TRANS.getType(),
                                    new UserDto("handover"));
                            jyckNeedQty -= qty;
                        }
                    }
                }
                customerEventPublisher.customerOrderEvent(EventSourceEnum.WAREHOUSE_DELIVERY_HANDOVER, jyck);
                return jyckNeedQty;
            }
            // 如果调拨出的数量比需要的少，则为调拨分纳，绑本次调拨的数量
            //8 > 5+1
            else if (jyckNeedQty > dbQty) {// 如果不齐，则不给jyck绑定关联关系，而是拆分jyck,给新jyck绑定关联关系
                // 如果是随发分批 bugid 11568 2023-07-25 C12961
                if (CKTYPEEnum.ITEM_UNLIMIT.getCode().equals(jyck.getDlvEntire())) {
                    // 如果是调拨分纳出，也就是判断这次入库后，jyck能不能货齐
                    // 插入分纳交易出的Do、DoItem
                    String newJyckId = opsDoService.createAssDoForRo(jyck, dbQty, new UserDto("调拨分纳拆分do"), DoTypeEnum.JYCK);
                    CreDoByInventoryDto doDto = baseDoService.getOpsDoDto(newJyckId).toCreDo();
                    // 创建分纳交易出的doItemInventory
                    for (OpsInventoryMove move : moveList) {
                        OpsDoItemInventory doItemInventory = createTransDoItemInventory(newJyckId, move.getInventoryId(), move.getQuantity());
                        opsDoService.insertDoItemInventory(doItemInventory);
                        baseInventoryService.UpdatePreQtyInventory(move.getInventoryId(), move.getQuantity(), InventoryTableTypeEnum.TRANS.getType(),
                                new UserDto("handover"));
                    }
                } else {
                    for (OpsInventoryMove move : moveList) {
                        OpsDoItemInventory doItemInventory = createTransDoItemInventory(jyck.getDoId(), move.getInventoryId(), move.getQuantity());
                        opsDoService.insertDoItemInventory(doItemInventory);
                        baseInventoryService.UpdatePreQtyInventory(move.getInventoryId(), move.getQuantity(), InventoryTableTypeEnum.TRANS.getType(),
                                new UserDto("handover"));
                    }
                }
                customerEventPublisher.customerOrderEvent(EventSourceEnum.WAREHOUSE_DELIVERY_HANDOVER, jyck);
                return dbQty;
            }
            return 0;
        } else {
            // 写入pco_item_inventory
            OpsPcoItemDto pcoItemDto = getOpsPcoAndItem(jyck.getOrderId(), jyck.getOrderItem(), jyck.getNum(), dbck.getAssNum());
            int needQty = getNeedQty(jyck.getOrderId(), jyck.getOrderItem(), jyck.getNum(), dbck.getAssNum());
            if (needQty <= 0) {// 如果不缺关联关系了，直接退出
                return 0;
            }
            // 如果需要的数量比调拨出的少，则绑定需要的关联关系
            else if (needQty <= dbQty) {
                // 写入ops_do_item_inventory
                if (moveList.size() == 1) {
                    Long inventoryId = moveList.get(0).getInventoryId();
                    insertPcoItemInventory(pcoItemDto.getOpsPco(), dbck.getAssNum(), inventoryId, needQty);
                    baseInventoryService.UpdatePreQtyInventory(inventoryId, needQty, InventoryTableTypeEnum.TRANS.getType(),
                            new UserDto("handover"));
                }
                if (moveList.size() > 1) {
                    moveList.sort(Comparator.comparing(OpsInventoryMove::getQuantity).reversed());
                    for (OpsInventoryMove move : moveList) {
                        //8 < 5+4
                        //8 < 10+1
                        int qty = needQty < move.getQuantity() ? needQty : move.getQuantity();
                        if (qty > 0) {
                            insertPcoItemInventory(pcoItemDto.getOpsPco(), dbck.getAssNum(), move.getInventoryId(), qty);
                            baseInventoryService.UpdatePreQtyInventory(move.getInventoryId(), qty, InventoryTableTypeEnum.TRANS.getType(),
                                    new UserDto("handover"));
                            needQty -= qty;
                        }
                    }
                }
                customerEventPublisher.customerOrderEvent(EventSourceEnum.WAREHOUSE_DELIVERY_HANDOVER, jyck);
                return needQty;
            }// 如果调拨出的数量比需要的少，则为调拨分纳，绑本次调拨的数量
            //8 > 5+1
            else if (needQty > dbQty) {
                for (OpsInventoryMove move : moveList) {
                    insertPcoItemInventory(pcoItemDto.getOpsPco(), dbck.getAssNum(), move.getInventoryId(), move.getQuantity());
                    baseInventoryService.UpdatePreQtyInventory(move.getInventoryId(), move.getQuantity(), InventoryTableTypeEnum.TRANS.getType(),
                            new UserDto("handover"));
                }
                customerEventPublisher.customerOrderEvent(EventSourceEnum.WAREHOUSE_DELIVERY_HANDOVER, jyck);
                return dbQty;
            }
        }
        return 0;
    }

    private void insertPcoItemInventory(OpsPco pco, Integer pcoItem, Long inventoryId, Integer prepareQty) throws OpsException {
        OpsPcoItemInventory pcoItemInventory = new OpsPcoItemInventory();
        pcoItemInventory.setInventoryTableType(InventoryTableTypeEnum.TRANS.getType());
        pcoItemInventory.setSortnum(1);
        pcoItemInventory.setInventoryId(inventoryId);
        pcoItemInventory.setPcoId(pco.getPcoId());
        pcoItemInventory.setPcoItem(pcoItem);// 调拨单对应的assnum
        pcoItemInventory.setUseQty(prepareQty);
        pcoItemInventory.setDelflag(0);
        pcoItemInventory.setVersion(0L);
        pcoItemInventory.setCreTime(new Date());
        pcoItemInventory.setPcoType(pco.getPcoType());
        opsPcoService.insertPcoItemInventory(pcoItemInventory);
    }


    private static OpsDoItemInventory createTransDoItemInventory(String jyckId, long inventoryId, Integer prepareQty) {
        OpsDoItemInventory doItemInventory = new OpsDoItemInventory();
        doItemInventory.setDoId(jyckId);
        doItemInventory.setDoItem(1);
        doItemInventory.setInventoryTableType(InventoryTableTypeEnum.TRANS.getType());
        doItemInventory.setInventoryId(inventoryId);
        doItemInventory.setUseQty(prepareQty);// 没有校验：交易出库绑定的数量是否超出DoItem数量 todo 是否校验？ 答复：需要加校验，校验doItem的数量和doItemInventory的总数量
        doItemInventory.setDelflag(0);
        doItemInventory.setVersion(0L);
        doItemInventory.setCreTime(new Date());
        return doItemInventory;
    }

    public int getNeedQty(String jyckId) throws OpsException {
        OpsDoDto opsDoDto = baseDoService.getOpsDoDto(jyckId);
        Integer useQty = opsDoDto.getDoItemInventoryList().stream().map(OpsDoItemInventory::getUseQty).reduce(Integer::sum).orElse(0);
        return opsDoDto.getDoItem().getQty() - useQty;
    }

    public int getNeedQty(String orderId, String orderItem, int num,int pcoItem) throws OpsException {
        OpsPcoItemDto pcoItemDto = getOpsPcoAndItem(orderId, orderItem, num, pcoItem);
        Integer useQty = pcoItemDto.getPcoItemInventoryList().stream().map(OpsPcoItemInventory::getUseQty).reduce(Integer::sum).orElse(0);
        return pcoItemDto.getPcoItem().getSubQty() - useQty;
    }

    private OpsPcoItemDto getOpsPcoAndItem(String orderId, String orderItem, int num, int pcoItem) throws OpsException {
        OpsPco opsPco = opsPcoService.findPcoByOrder(orderId, orderItem, num);
        if (null == opsPco) {
            throw Exceptions.OpsException("对应PCOID无数据。order" + orderId);
        }
        // 调拨Doid的AssNum对应pcoitem的pcoid
        OpsPcoItem opsPcoItem = opsPcoService.getPcoItemByPcoIdAndItem(opsPco.getPcoId(), pcoItem);
        if (null == opsPcoItem) {
            throw Exceptions.OpsException("对应PCOItem无数据。pcoId" + opsPco.getPcoId());
        }
        List<OpsPcoItemInventory> pcoItemInventories = opsPcoService.getOpsPcoItemInventoryByPcoId(opsPcoItem.getPcoId(), opsPcoItem.getPcoItem());
        OpsPcoItemDto pcoItemDto = new OpsPcoItemDto(opsPco, opsPcoItem, pcoItemInventories);
        return pcoItemDto;
    }

    private long getPropertyIdFromTransOrder(OpsDo opsDo) throws OpsException {
        TransOrder transOrder = transOrderService.findTransOrder(opsDo.getOrderId(), Integer.valueOf(opsDo.getOrderItem()));
        if (transOrder.getToInventoryPropertyId() != null) {
            return transOrder.getToInventoryPropertyId();
        }
        OpsInventoryProperty property = PropertyFactory.fromTransOrder(transOrder);
        return opsInventoryPropertyService.findPropertyWithInsertByExample(property, new UserDto("handconfirm"));
    }

    private void createRoItemInventory(String roId, long inventoryId, Integer qty) {
        OpsRoItemInventory opsRoItemInventory = new OpsRoItemInventory();
        opsRoItemInventory.setRecQty(0);
        opsRoItemInventory.setInventoryId(inventoryId);
        opsRoItemInventory.setQty(qty);
        opsRoItemInventory.setRoId(roId);
        opsRoItemInventory.setRoItem(1);
        opsRoItemInventory.setDelflag(0);
        opsRoItemInventory.setVersion(0L);
        opsRoItemInventory.setCreator(UserDto.WMS.getUserName());
        opsRoItemInventory.setCreTime(new Date());
        opsRoItemInventoryMapper.insertSelective(opsRoItemInventory);
    }

    private void updateInvoiceNoForRo(OpsRo opsRo, String invoiceNo, Long invoiceId) throws OpsException {
        OpsRoExample roExample = new OpsRoExample();
        roExample.createCriteria().andRoIdEqualTo(opsRo.getRoId()).andVersionEqualTo(opsRo.getVersion()).andDelflagEqualTo(0);
        OpsRo updateOpsRo = new OpsRo();
        updateOpsRo.setInvoiceNo(invoiceNo);
        updateOpsRo.setInvoiceId(invoiceId);
        updateOpsRo.setVersion(opsRo.getVersion() + 1);
        updateOpsRo.setModifyTime(new Date());
        updateOpsRo.setModifier(UserDto.WMS.getUserName());
        if (1 != opsRoMapper.updateByExampleSelective(updateOpsRo, roExample)) {
            throw Exceptions.OpsException(opsRo.getRoId() + "，更新RO行发票更新错误");
        }
    }

    // begin bug:9685 库存批次属性ID获取，DO关联库存如有N采用N行批次ID，如因时间差导致入库后调拨出库前，取在途ID批次库存ID b28029 2023-2-16
    private Long getPropertyIdFromDBCKorCGDBCK(OpsDo opsDo) throws OpsException {
        String doId = opsDo.getDoId();
        // 查询在库库存
        List<OpsDoItemInventory> opsDoItemInventories = baseDoService.getDoItemInventoryByDoId(opsDo.getDoId(), InventoryTableTypeEnum.NORMAL);
        if (CollectionUtils.isNotEmpty(opsDoItemInventories)) {
            // 存在关联关系在库行(N行)
            OpsDoItemInventory opsDoItemInventory = opsDoItemInventories.get(0);
            // 在库ID
            OpsInventory opsInventory = baseInventoryService.getInventoryById(opsDoItemInventory.getInventoryId());
            if (Objects.isNull(opsInventory)) {
                throw Exceptions.OpsException(doId + "，opsDoItemInventories,无在库表ID数据：" + opsDoItemInventories.get(0).getInventoryId());
            }
            // 取原始出库的批属性ID
            return opsInventory.getInventoryPropertyId();
        } else {
            // 如果不存在在库关联关系，可能是因为时间差，调拨发货仓先出后入了，
            // 所以取T行 如T行也没，报错不处理
            opsDoItemInventories = baseDoService.getDoItemInventoryByDoId(opsDo.getDoId(), InventoryTableTypeEnum.TRANS);
            // 无opsDoItemInventories数据
            if (CollectionUtils.isEmpty(opsDoItemInventories)) {
                throw Exceptions.OpsException(doId + "，无opsDoItemInventories数据");
            }
            // 在途ID
            Long moveId = opsDoItemInventories.get(0).getInventoryId();
            OpsInventoryMove opsInventoryMove = baseInventoryService.getInventoryMoveById(moveId);
            if (Objects.isNull(opsInventoryMove)) {
                throw Exceptions.OpsException(doId + "，opsDoItemInventories,无在途表ID数据：" + moveId);
            }
            // 取在途的批属性ID
            return opsInventoryMove.getInventoryPropertyId();
        }
    }

    private void updateQtyForRoItem(OpsRoItem opsRoItem, Integer qty, String remark) throws OpsException {
        OpsRoItemExample roItemExample = new OpsRoItemExample();
        roItemExample.createCriteria().andIdEqualTo(opsRoItem.getId())
                .andVersionEqualTo(opsRoItem.getVersion()).andDelflagEqualTo(0);
        OpsRoItem updateOpsRoItem = new OpsRoItem();
        updateOpsRoItem.setQty(qty);// 当前发出的数量
        updateOpsRoItem.setVersion(opsRoItem.getVersion() + 1);
        updateOpsRoItem.setModifyTime(new Date());
        updateOpsRoItem.setModifier(UserDto.WMS.getUserName());
        updateOpsRoItem.setRemark(remark);
        int result = opsRoItemMapper.updateByExampleSelective(updateOpsRoItem, roItemExample);
        if (result != 1) {
            throw Exceptions.OpsException("修改roItem数量失败:" + opsRoItem.getId() + "修改原因" + remark);
        }
    }

    private OpsRoDto splitRoAndItemAndTaskFromRo(OpsRo opsRo, OpsRoItem opsRoItem, Integer qty) throws OpsException {
        // 拆分新ro
        String orderNo = opsRo.getOrderId();
        String orderItem = opsRo.getOrderItem();
        Integer num = opsRo.getNum();
        Integer assNum = opsRo.getAssNum();
        // 写入RO+orderId+orderItem+num+uuid(8字符)
        String roNewId = WmOrderNoFactory.DBRK_ID_Random(orderNo, orderItem, num, assNum);
        OpsRo opsNewRo = new OpsRo();
        opsNewRo.setRoId(roNewId);
        opsNewRo.setInvoiceNo(null);// 默认不给发票号
        opsNewRo.setInvoiceId(null);
        opsNewRo.setOrderId(opsRo.getOrderId());
        opsNewRo.setOrderItem(String.valueOf(opsRo.getOrderItem()));
        opsNewRo.setNum(opsRo.getNum());
        opsNewRo.setAssNum(opsRo.getAssNum());
        opsNewRo.setExtNum(opsRo.getExtNum());
        opsNewRo.setRoSource(RoSourceEnum.EMPTY.getType());
        opsNewRo.setRoType(opsRo.getRoType());
        opsNewRo.setWarehouseCode(opsRo.getWarehouseCode());
        opsNewRo.setRoStatus(RoStatusEnum.WAIT.getStatus());// 待收货
        opsNewRo.setCustomerNo(opsRo.getCustomerNo());
        opsNewRo.setRemark("调拨回传数量拆分");
        opsNewRo.setCreator("调拨回传数量拆分");
        baseRoService.insertOpsRo(opsNewRo);
        // 拆分新roItem
        OpsRoItem opsNewRoItem = new OpsRoItem();
        opsNewRoItem.setRoId(opsNewRo.getRoId());
        opsNewRoItem.setRoItem(1);
        opsNewRoItem.setModelno(opsRoItem.getModelno());
        opsNewRoItem.setQty(qty);
        opsNewRoItem.setRecQty(0);
        opsNewRoItem.setRemark("调拨回传数量拆分");
        opsNewRoItem.setCreator("调拨回传数量拆分");
        baseRoService.insertOpsRoItem(opsNewRoItem);
        // 新增RO 写入下发表
        // 下发目的仓RO barcode 指令（当前有RO order）
        addRoTaskForHandConfirm(roNewId);
        //重新返回新的ro和roItem
        opsNewRo = baseRoService.getRoByRoId(roNewId);
        opsNewRoItem = baseRoService.findRoItemByRoId(roNewId);
        OpsRoDto opsRoDto = new OpsRoDto(opsNewRo, opsNewRoItem);
        return opsRoDto;
    }

    private void addRoTaskForHandConfirm(String roNewId) {
        OpsWmOrderTask task = WmTaskFactory.RoTask(roNewId, SendStatusEnum.SUSPENDED, new CreateInfoDto(UserDto.WMS));
        wmOrderTaskService.addOpsWmOrderTask(task);
    }

    private OpsRoDto getRoAndItemForHandConfirm(OpsDo opsDo, OpsDoItem opsDoItem, Integer qty, String invoiceNo) throws OpsException {
        OpsRo opsRo = null;
        List<OpsRo> opsRos = baseRoService.findRoByOrderItemNum(opsDo.getOrderId(), Integer.valueOf(opsDo.getOrderItem()),
                opsDo.getNum(), opsDo.getAssNum(), opsDo.getExtNum());
        if (CollectionUtils.isEmpty(opsRos)) {
            throw Exceptions.OpsException(String.format("%s，无RO数据。%s-%s-%d", opsDo.getDoId(), opsDo.getOrderId(), opsDo.getOrderItem(), opsDo.getNum()));
        } else if (opsRos.size() == 1) {
            opsRo = opsRos.get(0);
        } else if (opsRos.size() > 1) {
            opsRo = opsRos.stream()
                    .filter(item -> RoTypeEnum.isDB(item.getRoType()))
                    .filter(item -> StringUtils.isBlank(item.getInvoiceNo()))
                    .findFirst().orElse(null);
            // 需要一条无发票号的ro来被操作，所以如果没有空发票号的ro，则创建一条
            if (null == opsRo || StringUtils.isEmpty(opsRo.getRoId())) {
                opsRo = createRoAndRoItemAndTaskIfNull(opsDo, opsDoItem, qty);
            }
        }
        if (null == opsRo) {
            throw Exceptions.OpsException(opsDo.getDoId() + "，RO无数据。发票号：" + invoiceNo);
        }
        OpsRoItem opsRoItem = baseRoService.findRoItemByRoId(opsRo.getRoId());
        if (null == opsRoItem) {
            throw Exceptions.OpsException(opsDo.getDoId() + "，opsRoItem无数据：" + opsRo.getRoId() + ".发票号：" + invoiceNo);
        }
        return new OpsRoDto(opsRo, opsRoItem);
    }

    private OpsDoDto getDoAndItemForHandConfirm(String doId, String invoiceNo, Long invoiceId) throws OpsException {
        OpsDo opsDo = baseDoService.getDoByDoId(doId);
        if (null == opsDo) {
            throw Exceptions.OpsException(doId + "，无DO数据.发票号：" + invoiceNo + "发票ID:" + invoiceId);
        }
        OpsDoItem opsDoItem = baseDoService.getDoItemByDoId(doId);
        if (null == opsDoItem) {
            throw Exceptions.OpsException(doId + "，无DOItem数据，发票号：" + invoiceNo + "发票ID:" + invoiceId);
        }
        if (!DoTypeEnum.isDB(opsDo.getDoType())) {
            throw Exceptions.OpsException("无法识别调拨单类型：" + opsDo.getDoType() + "，单号：" + opsDo.getDoId());
        }
        return new OpsDoDto(opsDo, opsDoItem);
    }

    private OpsRo createRoAndRoItemAndTaskIfNull(OpsDo opsDo, OpsDoItem opsDoItem, Integer qty) throws OpsException {
        String orderNo = opsDo.getOrderId();
        String orderItem = opsDo.getOrderItem();
        Integer num = opsDo.getNum();
        Integer assNum = opsDo.getAssNum();
        DoTypeEnum doType = DoTypeEnum.getType(opsDo.getDoType());
        if (doType == null) throw Exceptions.OpsException("无法解析调拨出库类型");
        // 通过do类型获取ro类型
        RoTypeEnum roTypeEnum = doType.getRoTypeEnum();
        // RO规则DBR+订单+子项+拆分+随机（为防止重复）
        String roNewId = WmOrderNoFactory.DBRK_ID_Random(orderNo, orderItem, num, assNum);
        // RO调拨只有一条写入RO-Item
        OpsRo opsNewRo = new OpsRo();
        opsNewRo.setRoId(roNewId);
        opsNewRo.setInvoiceNo(null);// 默认无发票号
        opsNewRo.setInvoiceId(null);
        opsNewRo.setOrderId(orderNo);
        opsNewRo.setOrderItem(orderItem);
        opsNewRo.setNum(num);
        opsNewRo.setAssNum(assNum);
        opsNewRo.setRoSource(RoSourceEnum.EMPTY.getType());
        opsNewRo.setRoType(roTypeEnum.getType());
        opsNewRo.setWarehouseCode(opsDo.getGatherWarehouseCode());
        opsNewRo.setRoStatus(RoStatusEnum.WAIT.getStatus());
        opsNewRo.setCustomerNo(opsDo.getCustomerNo());
        opsNewRo.setRemark("调拨回传无RO,新写入");
        baseRoService.insertOpsRo(opsNewRo);
        // RO调拨只有一条写入RO-Item
        OpsRoItem opsNewRoItem = new OpsRoItem();
        opsNewRoItem.setRoId(roNewId);
        opsNewRoItem.setRoItem(1);
        opsNewRoItem.setModelno(opsDoItem.getModelno());
        opsNewRoItem.setQty(qty);
        opsNewRoItem.setRecQty(0);
        opsNewRoItem.setRemark("调拨回传无RO,新写入");
        opsNewRoItem.setCreator(UserDto.WMS.getUserName());
        opsNewRoItem.setCreTime(new Date());
        baseRoService.insertOpsRoItem(opsNewRoItem);
        // 新增RO 写入下发表
        // 下发目的仓RO barcode 指令（当前有RO order）
        addRoTaskForHandConfirm(roNewId);
        OpsRo opsRo = baseRoService.getRoByRoId(roNewId);
        return opsRo;
    }

    private void updateTasksForHandConfirm(List<String> roIds) throws OpsException {
        // 调拨没有task barcode
        // update wmtask ro-order=0 (默认=3)
        // insert wmtask ro-barcode=3
        List<OpsWmOrderTask> taskList = new ArrayList<OpsWmOrderTask>();
        for (String roId : roIds) {
            // 更新order_task order状态
            OpsWmOrderTaskExample exa = new OpsWmOrderTaskExample();
            OpsWmOrderTaskExample.Criteria cri = exa.createCriteria();
            cri.andFlagBetween(3, 4);// 将挂起状态设置为 0初始状态
            cri.andWmOrderTypeEqualTo(WmOrderTaskEnum.RO.getType());
            cri.andTaskTypeEqualTo(WmOrderTaskEnum.ORDER.getType());
            cri.andWmOrderIdEqualTo(roId);
            cri.andDelflagEqualTo(0);
            OpsWmOrderTask record = new OpsWmOrderTask();
            record.setFlag(0);
            record.setModifyTime(new Date());
            record.setModifier("调拨");
            opsWmOrderTaskMapper.updateByExampleSelective(record, exa);
            // 下发目的仓RO barcode 指令（当前有RO order）
            OpsWmOrderTask opsWmOrderTaskBarcode = new OpsWmOrderTask();
            opsWmOrderTaskBarcode.setFlag(Integer.valueOf(SendStatusEnum.SUSPENDED.getType()));
            opsWmOrderTaskBarcode.setCreator(UserDto.WMS.getUserName());
            opsWmOrderTaskBarcode.setCreTime(new Date());
            opsWmOrderTaskBarcode.setWmOrderId(roId);
            opsWmOrderTaskBarcode.setWmOrderType(WmOrderTaskEnum.RO.getType());
            opsWmOrderTaskBarcode.setTaskType(WmOrderTaskEnum.BARCODE.getType());
            taskList.add(opsWmOrderTaskBarcode);
        }
        wmOrderTaskService.addBatchOpsWmOrderTask(taskList);
    }

    private OpsHandover createHandoverForHandConfirm(HandConfirm handConfirm, HandItem item) throws OpsException {
        if (item.getQty() < 1) {
            throw Exceptions.OpsException("数量不可空。");
        }
        OpsHandover opsHandover = new OpsHandover();
        opsHandover.setInvoiceno(handConfirm.getInvoice());
        opsHandover.setHandDate(handConfirm.getHangdate());
        opsHandover.setDoId(item.getDoid());
        opsHandover.setModelno(item.getModelno());
        opsHandover.setQty(item.getQty());
        opsHandover.setCaseno(item.getCaseno());
        opsHandover.setBarcode(item.getBarcode());
        opsHandover.setCreTime(new Date());
        opsHandover.setCreator(UserDto.WMS.getUserName());
        opsHandover.setModifyTime(new Date());
        opsHandover.setModifier(UserDto.WMS.getUserName());
        opsHandover.setDelflag(0);
        opsHandover.setFromwarehousecode(handConfirm.getFromWarehouseCode());
        opsHandover.setTowarehousecode(handConfirm.getToWarehouseCode());
        opsHandover.setLogisticscode(handConfirm.getLogisticsCode());
        opsHandover.setExpresscode(handConfirm.getExpressCode());
        return opsHandover;
    }

}
