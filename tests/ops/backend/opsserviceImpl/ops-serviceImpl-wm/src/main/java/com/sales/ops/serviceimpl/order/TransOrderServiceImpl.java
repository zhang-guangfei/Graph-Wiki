package com.sales.ops.serviceimpl.order;

import cn.hutool.json.JSONUtil;
import com.sales.ops.common.opsexception.Exceptions;
import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.db.dao.TransOrderMapper;
import com.sales.ops.db.entity.*;
import com.sales.ops.dto.inventory.InventoryForTransDto;
import com.sales.ops.dto.inventory.OpsDoDto;
import com.sales.ops.dto.inventory.OpsRoDto;
import com.sales.ops.dto.order.InventoryForTransInputDto;
import com.sales.ops.dto.order.TransOrderDto;
import com.sales.ops.dto.order.TransOrderDtoForMove;
import com.sales.ops.dto.util.DateUtil;
import com.sales.ops.dto.util.UserDto;
import com.sales.ops.enums.*;
import com.sales.ops.service.ba.OpsWarehouseService;
import com.sales.ops.service.inventory.OpsInventoryPropertyService;
import com.sales.ops.service.inventory.OpsInventoryService;
import com.sales.ops.service.inventory.WmDispatchService;
import com.sales.ops.service.log.OpsInventoryLogService;
import com.sales.ops.service.order.TransOrderService;
import com.sales.ops.service.wmOrder.BaseDoService;
import com.sales.ops.service.wmOrder.BaseRoService;
import com.sales.ops.service.wmOrder.OpsDoService;
import com.sales.ops.service.wmOrder.OpsRoService;
import com.sales.ops.serviceimpl.inventory.BaseInventoryService;
import com.sales.ops.serviceimpl.inventory.factory.PropertyFactory;
import com.sales.ops.utils.DoBuilder;
import com.sales.ops.utils.RoBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Slf4j
@Service
public class TransOrderServiceImpl implements TransOrderService {


    @Autowired
    private BaseInventoryService baseInventoryService;
    @Autowired
    private OpsInventoryPropertyService opsInventoryPropertyService;
    @Autowired
    private OpsDoService opsDoService;
    @Autowired
    private OpsRoService opsRoService;
    @Autowired
    private TransOrderMapper transOrderMapper;
    @Autowired
    private WmDispatchService wmDispatchService;
    @Autowired
    private BaseDoService baseDoService;
    @Autowired
    private BaseRoService baseRoService;
    @Autowired
    private OpsWarehouseService opsWarehouseService;
    @Autowired
    private OpsInventoryService opsInventoryService;
    @Autowired
    private OpsInventoryLogService opsInventoryLogService;

    /**
     * 此方法1.总是同仓调库
     * 2.总是将专备调入通用
     * 3.不在意有效库存是否满足，如果不满足，则有多少调多少
     *
     * @param transOrder
     * @throws OpsException
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int createTransOrderForPreorder(TransOrder transOrder) throws OpsException {
        UserDto userDto = new UserDto("先行在库预决算调库");
        // 匹配出库库存
        OpsInventory inv = baseInventoryService.getInventoryById(transOrder.getFromInventoryId());
        if (inv == null) {
            throw Exceptions.OpsException("库存不存在：" + transOrder.getFromInventoryId());
        }
        int availableQty = inv.getQuantity() - inv.getPrepareQuantity();
        if (availableQty <= 0) {
            throw Exceptions.OpsException("库存不足：" + transOrder.getFromInventoryId());
        }
        if (availableQty <= transOrder.getQuantity()) {
            transOrder.setQuantity(availableQty);
        }
        //匹配入库库存
        Long invTo = getInventoryForTransOrderIn(transOrder.getToWarehouseCode(), transOrder.getModelNo(), transOrder.getToInventoryPropertyId());
        // 创建调库指令
        OpsDoDto opsDoDto = DoBuilder.createTKCK(transOrder.getTransNo(), transOrder.getItemNo())
                .warehouseInfo(transOrder.getFromWarehouseCode(), transOrder.getToWarehouseCode())
                .addInventoryInfo(inv, transOrder.getQuantity())
                .statusInfo(DoStatusEnum.INIT, DoWaitTypeEnum.WaitDB, 0)
                .build();
        OpsRoDto opsRoDto = RoBuilder.createTKRK(transOrder.getTransNo(), transOrder.getItemNo())
                .warehouseInfo(transOrder.getToWarehouseCode())
                .invInfo(transOrder.getModelNo(), transOrder.getQuantity())
                .customerInfo(transOrder.getToCustomerNo())
                .build();
        // 执行调库指令
        // 调整指令数量
        opsDoDto.getDoItem().setOutQty(opsDoDto.getDoItem().getQty());
        for (OpsDoItemInventory itemInventory : opsDoDto.getDoItemInventoryList()) {
            itemInventory.setOutQty(itemInventory.getUseQty());
        }
        opsRoDto.getOpsRoItem().setRecQty(opsRoDto.getOpsRoItem().getRecQty());
        // 修改Do状态为已完成
        opsDoDto.getOpsDo().setWaitType(DoWaitTypeEnum.OK.getType());
        opsDoDto.getOpsDo().setDoStatus(DoStatusEnum.FINISH.getStatus());
        opsRoDto.getOpsRo().setRoStatus(RoStatusEnum.FINISH.getStatus());
        // 写入数据库
        opsDoService.insertDo(opsDoDto.getOpsDo(), opsDoDto.getDoItem(), opsDoDto.getDoItemInventoryList(), userDto);
        opsRoService.insertRo(opsRoDto.getOpsRo(), opsRoDto.getOpsRoItem(), null, userDto);
        // 扣减库存数量
        opsDoService.updateQtyForDo(opsDoDto.getOpsDo(), opsDoDto.getDoItemInventoryList(), userDto);
        // 增加库存数量
        baseInventoryService.addQtyInventory(invTo, transOrder.getQuantity(),
                InventoryTableTypeEnum.NORMAL.getType(), userDto);
        opsInventoryLogService.insertOpsInventoryLogForRo(opsRoDto.getOpsRo(),transOrder.getModelNo(), invTo, transOrder.getQuantity(), userDto);
        return transOrder.getQuantity();
    }


    public Long getInventoryForTransOrderIn(String warehouseCode, String modelno, Long inventoryPropertyId) throws OpsException {
        OpsInventory condition = new OpsInventory();
        condition.setWarehouseCode(warehouseCode);
        condition.setModelno(modelno);
        condition.setInventoryPropertyId(inventoryPropertyId);
        List<OpsInventory> invList = baseInventoryService.findOpsInventory(condition);
        Long inventoryIdForIn = null;
        // 如果没有查询到库存，则创建一条0数量的库存
        if (CollectionUtils.isEmpty(invList)) {
            condition.setInventoryStatus(InventoryStatusEnum.NORMAL.getCode());
            condition.setQuantity(0);
            condition.setPrepareQuantity(0);
            inventoryIdForIn = baseInventoryService.createInvReturnId(condition, UserDto.AUTO);
        } else {
            inventoryIdForIn = invList.get(0).getInventoryId();
        }
        return inventoryIdForIn;
    }

    @Override
    public TransOrder getTransOrderByTransNo(String transNo, Integer itemNo) {
        TransOrderExample ex = new TransOrderExample();
        ex.createCriteria().andTransNoEqualTo(transNo).andItemNoEqualTo(itemNo);

        List<TransOrder> list = transOrderMapper.selectByExample(ex);
        if (CollectionUtils.isNotEmpty(list)) {
            return list.get(0);
        } else {
            return null;
        }
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public void createTransOrder(TransOrder transOrder, UserDto userDto) throws OpsException {
        // 1.插入transOrder
        insertTransOrderForNormal(transOrder);
        // 2.生成物流指令
        InventoryForTransInputDto inputDto = getTransInputDtoFromTransOrder(transOrder, userDto);
        // 创建物流指令
        wmDispatchService.inventoryForTrans(inputDto);
        // 3.如果成功了,更新物流交货期结果和状态
        updateTransOrderStatusNormalToInit(transOrder, inputDto);
    }



    @Transactional(rollbackFor = Exception.class)
    @Override
    public void createTransOrderBatch(TransOrderDto dto) throws OpsException {
        for (TransOrder transOrder : dto.getTransOrderList()) {
            createTransOrder(transOrder, dto.getUserDto());
        }
    }




    @Override
    public void createTransOrderOld(TransOrder transOrder, UserDto userDto) throws OpsException {
        // 1.插入transOrder
        insertTransOrderForNormal(transOrder);
        // 2.生成物流指令
        InventoryForTransInputDto inputDto = getTransInputDtoFromTransOrder(transOrder, userDto);
        try {
            // 创建物流指令
            log.info("创建物流指令:" + JSONUtil.toJsonStr(inputDto));
            wmDispatchService.inventoryForTrans(inputDto);
        } catch (Exception e) {
            // 3.如果处理失败了，更新为处理失败
            log.info("失败:" + JSONUtil.toJsonStr(e));
            updateTransOrderStatusToFailure(transOrder, e);
        }
        // 3.如果成功了,更新物流交货期结果和状态
        updateTransOrderStatusNormalToInit(transOrder, inputDto);
    }

    /**
     * 更新为预约在库库存失败状态
     *
     * @param transOrder
     * @param e
     * @throws OpsException
     */
    private void updateTransOrderStatusToFailure(TransOrder transOrder, Exception e) throws OpsException {
        TransOrderExample example = new TransOrderExample();
        example.createCriteria().andTransNoEqualTo(transOrder.getTransNo()).andItemNoEqualTo(transOrder.getItemNo());
        TransOrder update = new TransOrder();
        update.setStatus(OrderTransStatusEnum.DB_FAIL.getStatus());// 处理失败
        transOrderMapper.updateByExampleSelective(update, example);
        throw Exceptions.OpsException(e.getMessage());
    }

    /**
     * 在库transOrder的状态初始化
     *
     * @param transOrder
     * @param inputDto
     */
    private void updateTransOrderStatusNormalToInit(TransOrder transOrder, InventoryForTransInputDto inputDto) {
        TransOrderExample example = new TransOrderExample();
        example.createCriteria().andTransNoEqualTo(transOrder.getTransNo()).andItemNoEqualTo(transOrder.getItemNo());
        TransOrder update = new TransOrder();
        OrderTransStatusEnum transStatusEnum = transOrder.getFromWarehouseCode().equals(transOrder.getToWarehouseCode()) ? OrderTransStatusEnum.DB_FINISHED : OrderTransStatusEnum.DB_WAIT;
        update.setStatus(transStatusEnum.getStatus());
        update.setWmsDlvDate(inputDto.getWmsDlvDate());
        transOrderMapper.updateByExampleSelective(update, example);
    }

    @Override
    public void insertTransOrderForNormal(TransOrder transOrder) throws OpsException {
        TransOrderExample example = new TransOrderExample();
        example.createCriteria().andTransNoEqualTo(transOrder.getTransNo()).andItemNoEqualTo(transOrder.getItemNo());
        long count = transOrderMapper.countByExample(example);
        if (count != 0) {
            throw Exceptions.OpsException("trans_order已存在：" + transOrder.getTransNo() + "-" + transOrder.getItemNo());
        }
        // 设置状态
        transOrder.setStatus(OrderTransStatusEnum.DB_INIT.getStatus());// 初始化
        transOrder.setCreateTime(new Date());
        // 插入trans_order表
        transOrderMapper.insertSelective(transOrder);
    }

    private InventoryForTransInputDto getTransInputDtoFromTransOrder(TransOrder transOrder, UserDto userDto) {
        InventoryForTransDto transDto = new InventoryForTransDto();
        transDto.setOrderId(transOrder.getTransNo());
        transDto.setItemNo(transOrder.getItemNo());
        transDto.setCgFlag(Boolean.FALSE);
        transDto.setSmodelno(transOrder.getModelNo());
        transDto.setSwarehouseCode(transOrder.getFromWarehouseCode());
        transDto.setsPropertyId(transOrder.getFromInventoryPropertyId());
        transDto.setsPropertyTypeCode(transOrder.getFromInventoryTypeCode());
        transDto.setScustomerNo(transOrder.getFromCustomerNo());
        transDto.setSppl(transOrder.getFromPpl());
        transDto.setSprojectCode(transOrder.getFromProjectCode());
        transDto.setSgroupCustomerNo(transOrder.getFromGroupCustomerNo());
        transDto.setSsalesInfoNo(transOrder.getFromSalesInfoNo());
        transDto.setSqty(transOrder.getQuantity());
        transDto.setTmodelno(transOrder.getModelNo());
        transDto.setTwarehouseCode(transOrder.getToWarehouseCode());
        transDto.settPropertyId(transOrder.getToInventoryPropertyId());
        transDto.settPropertyTypeCode(transOrder.getToInventoryTypeCode());
        transDto.setTcustomerNo(transOrder.getToCustomerNo());
        transDto.setTppl(transOrder.getToPpl());
        transDto.setTprojectCode(transOrder.getToProjectCode());
        transDto.setTgroupCustomerNo(transOrder.getToGroupCustomerNo());
        transDto.setTsalesInfoNo(transOrder.getToSalesInfoNo());
        transDto.setTqty(transOrder.getQuantity());
        transDto.setWlDdate(transOrder.getWmsDlvDate());
        transDto.setCorderNo(transOrder.getCorderNo());
        transDto.setCproductNo(transOrder.getCproductNo());
        InventoryForTransInputDto inputDto = new InventoryForTransInputDto();
        inputDto.setDtoList(Collections.singletonList(transDto));
        inputDto.setUserDto(userDto);
        return inputDto;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createTransOrderForMove(TransOrderDtoForMove dto) throws OpsException {
        // 1.校验库存状态
        OpsInventoryMove move = baseInventoryService.getInventoryMoveById(dto.getFromInventoryId());
        validateMoveStatus(dto, move);
        // 2.插入transOrder
        insertTransOrderForMove(dto, move);
        // 3.创建物流指令(包含物流指令和预占库存)
        wmDispatchService.inventoryMoveForTransOrder(dto, move);
        // 不用改transOrder状态
    }

    private void validateMoveStatus(TransOrderDtoForMove dto, OpsInventoryMove move) throws OpsException {
        if (Objects.equals(move.getDelflag(), 1)) {
            throw Exceptions.OpsException("库存状态为删除，请重新申请调库");
        }
        if (!StringUtils.equals(move.getInventoryStatus(), dto.getFromInventoryStatus())) {
            throw Exceptions.OpsException(String.format("库存状态不正确，目前为%s，请重新申请调库", move.getInventoryStatus()));
        }
        int availableQty = move.getQuantity() - move.getPrepareQuantity();
        if (availableQty < dto.getHopeQty()) {
            throw Exceptions.OpsException(String.format("可用库存数量不足，目前可用%s，请重新申请调库", availableQty));
        }

    }

    private void insertTransOrderForMove(TransOrderDtoForMove dto, OpsInventoryMove move) throws OpsException {
        TransOrderExample ex = new TransOrderExample();
        ex.createCriteria().andTransNoEqualTo(dto.getTransNo()).andItemNoEqualTo(dto.getItemNo());
        long count = transOrderMapper.countByExample(ex);
        if (count > 0) {
            throw Exceptions.OpsException("transOrder已存在");
        }
        OpsInventoryProperty property = opsInventoryPropertyService.findById(move.getInventoryPropertyId());

        TransOrder transOrder = new TransOrder();
        transOrder.setTransType(TransOrderTypeEnum.TRANS.getCode());
        transOrder.setTransNo(dto.getTransNo());
        transOrder.setItemNo(dto.getItemNo());
        transOrder.setModelNo(dto.getModelNo());
        transOrder.setQuantity(dto.getHopeQty());
        transOrder.setStatus(OrderTransStatusEnum.DB_INIT.getStatus());
        transOrder.setTransFlag(dto.getTransFlag());
        transOrder.setFromInventoryId(dto.getFromInventoryId());
        transOrder.setFromAssociateNo(dto.getFromAssociateNo());
        transOrder.setFromAssociateNoItem(dto.getFromAssociateNoItem());
        transOrder.setFromAssociateNoSplit(dto.getFromAssociateNoSplit());
        transOrder.setFromNo(dto.getFromNo());
        transOrder.setFromId(dto.getFromId());
        transOrder.setFromType(dto.getFromType());
        transOrder.setFromInventoryPropertyId(move.getInventoryPropertyId());
        transOrder.setFromWarehouseCode(move.getWarehouseCode());
        transOrder.setFromInventoryTypeCode(property.getInventoryTypeCode());
        transOrder.setFromPpl(property.getPpl());
        transOrder.setFromProjectCode(property.getProjectCode());
        transOrder.setFromGroupCustomerNo(property.getGroupCustomerNo());
        transOrder.setFromSalesInfoNo(property.getSalesInfoNo());
        transOrder.setFromCustomerNo(property.getCustomerNo());
        transOrder.setToInventoryPropertyId(null);
        transOrder.setToWarehouseCode(dto.getToWarehouseCode());
        transOrder.setToInventoryTypeCode(dto.getToInventoryTypeCode());
        transOrder.setToPpl(dto.getToPpl());
        transOrder.setToProjectCode(dto.getToProjectNo());
        transOrder.setToGroupCustomerNo(dto.getToGroupCustomerNo());
        transOrder.setToSalesInfoNo(null);
        transOrder.setToCustomerNo(dto.getToCustomerNo());
        transOrder.setInQuantity(0);
        transOrder.setWmsDlvDate(null);
        transOrder.setFinishTime(null);
        transOrder.setCreateTime(new Date());
        transOrder.setUpdateTime(new Date());
        transOrder.setCreateUser("wm-service");
        transOrder.setUpdateUser("wm-service");
        transOrder.setInvoiceNo(null);
        transOrder.setShipQty(0);
        transOrder.setShipDate(null);
        transOrderMapper.insertSelective(transOrder);
    }

    // 【暂时不启用】 重构后的调库在库库存，等做完调库在途库存后再优化
    public void inventoryForTransOrder(InventoryForTransInputDto inputDto) throws OpsException {
        List<OpsWmOrderTask> taskList = new ArrayList<OpsWmOrderTask>();
        for (InventoryForTransDto transDto : inputDto.getDtoList()) {
            // 1.校验物流指令是否存在
            List<OpsDo> doS = baseDoService.findDoListByOrder(transDto.getOrderId(), transDto.getItemNo().toString(), null, null);
            if (!CollectionUtils.isEmpty(doS)) {
                throw Exceptions.OpsException("调拨出库指令已存在");
            }
            List<OpsRo> ros = baseRoService.findRoByOrderNo(transDto.getOrderId(), transDto.getItemNo().toString());
            if (!CollectionUtils.isEmpty(ros)) {
                throw Exceptions.OpsException("调拨入库指令已存在");
            }
            // 2.准备前置数据
            // (1)查询仓库和地址信息
            OpsWarehouse opsWarehouse = opsWarehouseService.findById(transDto.getTwarehouseCode());
            if (Objects.isNull(opsWarehouse)) {
                throw Exceptions.OpsException("不存在此仓库号：" + transDto.getTwarehouseCode());
            }
            // bugid:11445 c14717 2023/07/26 仓库多地址
            opsWarehouseService.getMultAdressSetAddress(opsWarehouse, DoTypeEnum.TKCK.getType());
            // (2)计算指定物流交货期
            Date date = opsDoService.tkckWlDay(transDto.getWlDdate(), transDto.getSwarehouseCode(), transDto.getTwarehouseCode(), transDto.getOrderId() + "-" + transDto.getItemNo());
            Date wlDate = transDto.getSwarehouseCode().equals(transDto.getTwarehouseCode()) ? transDto.getWlDdate() : date;
            inputDto.setWmsDlvDate(wlDate);
            Date HopeDate = Objects.isNull(transDto.getWlDdate()) ? com.sales.ops.common.until.DateUtil.getNow() : transDto.getWlDdate();

            // 3.查询并分配可用库存
            Map<OpsInventory, Integer> inventoryMapForOUT = opsInventoryService.findInventoryListByTransOrderInfo(transDto);
            // 4.创建物流指令
            OpsDoDto opsDoDto = DoBuilder.createTKCK(transDto.getOrderId(), transDto.getItemNo())
                    .warehouseInfo(transDto.getSwarehouseCode(), transDto.getTwarehouseCode())
                    .addressInfo(opsWarehouse)
                    .addInventoryInfo(inventoryMapForOUT)
                    .statusInfo(DoStatusEnum.INIT, DoWaitTypeEnum.WaitDB, 0)
                    .build();
            opsDoDto.getOpsDo().setWlDate(wlDate);
            opsDoDto.getOpsDo().setHopeDate(HopeDate);// 增加指定物流交货期

            OpsRoDto opsRoDto = RoBuilder.createTKRK(transDto.getOrderId(), transDto.getItemNo())
                    .warehouseInfo(transDto.getTwarehouseCode())
                    .invInfo(transDto.getTmodelno(), transDto.getTqty())
                    .customerInfo(transDto.getTcustomerNo())
                    .build();
            // 判断是否同仓调拨
            boolean sameWarehouse = StringUtils.equals(transDto.getSwarehouseCode(), transDto.getTwarehouseCode());
            UserDto userDto = inputDto.getUserDto();
            if (!sameWarehouse) {
                // 异仓调拨
                // 1.修改指令状态为初始化
                opsDoDto.getOpsDo().setDoStatus(DoStatusEnum.INIT.getStatus());
                opsDoDto.getOpsDo().setWaitType(DoWaitTypeEnum.WaitDB.getType());
                opsDoDto.getDoItem().setOutQty(0);
                opsRoDto.getOpsRo().setRoStatus(RoStatusEnum.INIT.getStatus());
                opsRoDto.getOpsRoItem().setRecQty(0);
                // 2.插入指令
                opsDoService.insertDo(opsDoDto.getOpsDo(), opsDoDto.getDoItem(), opsDoDto.getDoItemInventoryList(), userDto);
                opsRoService.insertRo(opsRoDto.getOpsRo(), opsRoDto.getOpsRoItem(), null, userDto);
                // 3.修改库存预约数
                opsDoService.updatePreQtyForDo(opsDoDto.getOpsDo(), opsDoDto.getDoItemInventoryList(), userDto,null);
                // 4.创建task
                OpsWmOrderTask doTask = new OpsWmOrderTask();
                doTask.setWmOrderId(opsDoDto.getOpsDo().getDoId());
                doTask.setWmOrderType(WmOrderTaskEnum.DO.getType());
                doTask.setTaskType(WmOrderTaskEnum.ORDER.getType());
                doTask.setFlag(Integer.valueOf(SendStatusEnum.INIT.getType()));
                doTask.setCreator("");
                doTask.setCreTime(new Date());
                taskList.add(doTask);
                OpsWmOrderTask roTask = new OpsWmOrderTask();
                roTask.setFlag(Integer.valueOf(SendStatusEnum.SUSPENDED.getType()));
                roTask.setCreator(userDto.getUserName());
                roTask.setCreTime(new Date());
                roTask.setWmOrderId(opsRoDto.getOpsRo().getRoId());
                roTask.setWmOrderType(WmOrderTaskEnum.RO.getType());
                roTask.setTaskType(WmOrderTaskEnum.ORDER.getType());
                taskList.add(roTask);
            } else {
                // 同仓调库
                // 查询要入到哪个在库库存
                Long inventoryInput = findInventoryIdForTransOrderInput(transDto, userDto);
                // 1.修改Do状态为已完成
                opsDoDto.getOpsDo().setDoStatus(DoStatusEnum.FINISH.getStatus());
                opsDoDto.getOpsDo().setWaitType(DoWaitTypeEnum.OK.getType());
                opsDoDto.getDoItem().setOutQty(opsDoDto.getDoItem().getQty());
                for (OpsDoItemInventory itemInventory : opsDoDto.getDoItemInventoryList()) {
                    itemInventory.setOutQty(itemInventory.getUseQty());
                }
                opsRoDto.getOpsRo().setRoStatus(RoStatusEnum.FINISH.getStatus());
                opsRoDto.getOpsRoItem().setRecQty(opsRoDto.getOpsRoItem().getRecQty());

                // 2.插入指令
                opsDoService.insertDo(opsDoDto.getOpsDo(), opsDoDto.getDoItem(), opsDoDto.getDoItemInventoryList(), userDto);
                opsRoService.insertRo(opsRoDto.getOpsRo(), opsRoDto.getOpsRoItem(), null, userDto);
                // 3.扣减库存数量
                opsDoService.updateQtyForDo(opsDoDto.getOpsDo(), opsDoDto.getDoItemInventoryList(), userDto);
                // 4.增加库存数量
                baseInventoryService.addQtyInventory(inventoryInput, transDto.getTqty(),
                        InventoryTableTypeEnum.NORMAL.getType(), userDto);
                opsInventoryLogService.insertOpsInventoryLogForRo(opsRoDto.getOpsRo(), opsDoDto.getDoItem().getModelno(), inventoryInput, transDto.getTqty(), userDto);
            }

        }
    }

    @Override
    public Long findInventoryIdForTransOrderInput(InventoryForTransDto transDto, UserDto userDto) throws OpsException {
        OpsInventory condition = new OpsInventory();
        condition.setWarehouseCode(transDto.getTwarehouseCode());
        condition.setModelno(transDto.getTmodelno());
        condition.setInventoryPropertyId(getPropertyId(transDto, userDto));
        List<OpsInventory> invList = baseInventoryService.findOpsInventory(condition);
        Long inventoryIdForIn = null;
        // 如果没有查询到库存，则创建一条0数量的库存
        if (invList.size() == 0) {
            condition.setInventoryStatus(InventoryStatusEnum.NORMAL.getCode());
            condition.setQuantity(0);
            condition.setPrepareQuantity(0);
            inventoryIdForIn = baseInventoryService.createInvReturnId(condition, UserDto.AUTO);
        } else {
            inventoryIdForIn = invList.get(0).getInventoryId();
        }
        return inventoryIdForIn;
    }

    /**
     * 查询或创建propertyId，根据transOrderTo信息
     *
     * @param transDto
     * @return
     * @throws OpsException
     */
    private Long getPropertyId(InventoryForTransDto transDto, UserDto userDto) throws OpsException {
        Long propertyId;
        if (transDto.gettPropertyId() != null) {
            propertyId = transDto.gettPropertyId();
        } else {
            OpsInventoryProperty property = PropertyFactory.createCondition(transDto.gettPropertyTypeCode(),
                    transDto.getTcustomerNo(), transDto.getTppl(), transDto.getTprojectCode(), transDto.getTgroupCustomerNo(), transDto.getSsalesInfoNo());
            propertyId = opsInventoryPropertyService.findPropertyWithInsertByExample(property, userDto);
        }
        return propertyId;
    }

    @Override
    public int updateWarehouse(Long id, String fromWarehouse, String toWarehouse) {
        TransOrder transOrder = new TransOrder();
        transOrder.setId(id);
        if (fromWarehouse != null) {
            transOrder.setFromWarehouseCode(fromWarehouse);
        }
        if (toWarehouse != null) {
            transOrder.setToWarehouseCode(toWarehouse);
        }
        transOrder.setUpdateTime(new Date());
        transOrder.setUpdateUser("发票导入修改目的仓");
        return transOrderMapper.updateByPrimaryKeySelective(transOrder);
    }

    @Override
    public int updateTransOrderById(TransOrder transOrder) {
        transOrder.setUpdateTime(DateUtil.getNow());
        return transOrderMapper.updateByPrimaryKeySelective(transOrder);
    }

    @Override
    public int updateStatus(Long id, OrderTransStatusEnum status) throws OpsException {
        TransOrder update = new TransOrder();
        update.setId(id);
        update.setStatus(status.getStatus());
        update.setUpdateTime(new Date());
        int i = transOrderMapper.updateByPrimaryKeySelective(update);
        if (i == 1) {
            return i;
        } else {
            throw Exceptions.TransOrderException("更新调拨单状态异常：" + status.getDesc());
        }
    }

    @Override
    public int updateStatus(String transNo, Integer itemNo, OrderTransStatusEnum status, Integer qty) throws OpsException {
        TransOrderExample example = new TransOrderExample();
        example.createCriteria().andTransNoEqualTo(transNo).andItemNoEqualTo(itemNo);
        TransOrder update = new TransOrder();
        if (qty != null) {
            update.setQuantity(qty);
        }
        update.setStatus(status.getStatus());
        update.setUpdateTime(new Date());
        update.setUpdateUser("wm-service");
        int i = transOrderMapper.updateByExampleSelective(update, example);
        if (i == 1) {
            return i;
        } else {
            throw Exceptions.TransOrderException("更新调拨单状态异常：" + status.getDesc());
        }
    }

    @Override
    public int updateInQuantity(Long id, Integer qty) throws OpsException {
        TransOrder update = new TransOrder();
        update.setId(id);
        update.setInQuantity(qty);
        update.setUpdateTime(new Date());
        int i = transOrderMapper.updateByPrimaryKeySelective(update);
        if (i == 1) {
            return i;
        } else {
            throw Exceptions.TransOrderException("更新调拨单入库数异常：" + id);
        }
    }

    @Override
    public int updateTransOrderShipById(Long id, String invoiceNo, Long invoiceId, int qty, Date shipDate) throws OpsException {
        TransOrder update = new TransOrder();
        update.setId(id);
        update.setInvoiceNo(invoiceNo);
        update.setInvoiceId(invoiceId);
        update.setUpdateTime(new Date());
        update.setShipQty(qty);
        update.setShipDate(shipDate);
        int i = transOrderMapper.updateByPrimaryKeySelective(update);
        if (i == 1) {
            return i;
        } else {
            throw Exceptions.OpsException("更新调拨单状态异常：调拨ID" + id);
        }
    }


    @Override
    public TransOrder findTransOrder(String transNo, int transItem) throws OpsException {
        TransOrderExample example = new TransOrderExample();
        example.createCriteria().andTransNoEqualTo(transNo).andItemNoEqualTo(transItem);
        List<TransOrder> transOrderList = transOrderMapper.selectByExample(example);
        if (transOrderList.size() == 1) {
            return transOrderList.get(0);
        } else {
            throw Exceptions.TransOrderException("查询到" + transOrderList.size() + "条trans_order：" + transNo + "-" + transItem);
        }
    }


}
